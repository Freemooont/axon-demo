package com.codefactory.demo.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Getter
public class ConfirmPresentationCommand {
    @TargetAggregateIdentifier
    private final UUID presentationId;

    @JsonCreator
    public ConfirmPresentationCommand(@JsonProperty("presentationId") UUID presentationId) {
        this.presentationId = presentationId;
    }
}
