package com.codefactory.demo.rest;

import com.codefactory.demo.command.CancelPresentationCommand;
import com.codefactory.demo.command.ConfirmPresentationCommand;
import com.codefactory.demo.command.RefinePresentationCommand;
import com.codefactory.demo.query.PresentationByIdQuery;
import com.codefactory.demo.query.PresentationListQuery;
import com.codefactory.demo.command.RegisterPresentationCommand;
import com.codefactory.demo.domain.entity.PresentationEntity;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf;
import static org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf;

@RestController
@AllArgsConstructor
public class PresentationController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping(value = "/presentations")
    public ResponseEntity<UUID> savePresentation(@RequestBody RegisterPresentationCommand cmd) {
        CommandMessage<RegisterPresentationCommand> command =
                new GenericCommandMessage<>(cmd);
        UUID presentationId = commandGateway.sendAndWait(command);

        return ResponseEntity.ok(presentationId);
    }

    @PutMapping(value = "/presentations/{presentationId}")
    public ResponseEntity<UUID> update(@RequestBody RefinePresentationCommand cmd,
                                       @PathVariable("presentationId") UUID presentationId) {
        CommandMessage<RefinePresentationCommand> command =
                new GenericCommandMessage<>(cmd);
        commandGateway.sendAndWait(command);

        return ResponseEntity.ok(presentationId);
    }

    @PostMapping(value = "/presentations/{presentationId}/cancel")
    public ResponseEntity<UUID> cancel(@PathVariable("presentationId") UUID presentationId) {
        CommandMessage<CancelPresentationCommand> command =
                new GenericCommandMessage<>(new CancelPresentationCommand(presentationId));

        commandGateway.sendAndWait(command);

        return ResponseEntity.ok(presentationId);
    }

    @PostMapping(value = "/presentations/{presentationId}/confirm")
    public ResponseEntity<UUID> confirm(@PathVariable("presentationId") UUID presentationId) {
        CommandMessage<ConfirmPresentationCommand> command =
                new GenericCommandMessage<>(new ConfirmPresentationCommand(presentationId));

        commandGateway.sendAndWait(command);

        return ResponseEntity.ok(presentationId);
    }
    @GetMapping(value = "/presentations")
    public ResponseEntity<List<PresentationEntity>> getPresentations() {
        CompletableFuture<List<PresentationEntity>> criteria = queryGateway.query(new PresentationListQuery(), multipleInstancesOf(PresentationEntity.class));

        return ResponseEntity.ok(criteria.getNow(Collections.emptyList()));
    }

    @SneakyThrows
    @GetMapping(value = "/presentations/{presentationId}")
    public ResponseEntity<PresentationEntity> getPresentation(@PathVariable("presentationId") UUID presentationId) {
        CompletableFuture<PresentationEntity> criteria = queryGateway.query(new PresentationByIdQuery(presentationId), instanceOf(PresentationEntity.class));

        return ResponseEntity.ok(criteria.get());
    }
}
