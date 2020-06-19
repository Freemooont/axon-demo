package com.codefactory.demo.domain;

import com.codefactory.demo.command.*;
import com.codefactory.demo.event.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Aggregate
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Presentation {

    @AggregateIdentifier
    private UUID presentationId;
    private LocalDateTime presentationDate;
    private long durationMinutes;
    private PresentationStatus status;
    private int pizzas;

    @CommandHandler
    public Presentation(RegisterPresentationCommand cmd) {
        log.info("RegisterPresentationCommand received");
        if (cmd.getDurationMinutes() < 30) {
            throw new IllegalArgumentException("30 is the min value");
        }

        AggregateLifecycle.apply(new PresentationRegisteredEvent(randomUUID(), cmd.getDateTime(), cmd.getDurationMinutes()));
    }

    @CommandHandler
    public void handle(RefinePresentationCommand cmd) {
        log.info("RefinePresentationCommand received");
        if (cmd.getDurationMinutes() < 30) {
            throw new IllegalArgumentException("30 is the min value");
        }
        AggregateLifecycle.apply(new PresentationRefinedEvent(presentationId, cmd.getDateTime(), cmd.getDurationMinutes()));
    }

    @CommandHandler
    public void handle(CancelPresentationCommand cmd) {
        log.info("RefinePresentationCommand received");
        if (status != PresentationStatus.REGISTERED) {
            throw new IllegalArgumentException("Presentation can not be CANCELED");
        }
        AggregateLifecycle.apply(new PresentationCanceledEvent(cmd.getPresentationId()));
    }

    @CommandHandler
    public void handle(ConfirmPresentationCommand cmd){
        if (presentationDate.plusMinutes(durationMinutes).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Presentation isn't finished in expected time");
        }
        AggregateLifecycle.apply(new PresentationConfirmedEvent(cmd.getPresentationId()));
    }

    @CommandHandler
    public void handle(OrderPizzaCommand cmd) {
        if (pizzas > 10) {
            throw new IllegalArgumentException("to many pizzas");
        }
        if (status != PresentationStatus.CONFIRMED) {
            throw new IllegalArgumentException("presentation isn't confirmed");
        }

        AggregateLifecycle.apply(new PizzaOrdered(cmd.getPresentationId(), cmd.getPizzas()));
    }

    @EventSourcingHandler
    public void on(PresentationRegisteredEvent evt) {
        this.presentationId = evt.getPresentationId();
        this.status = PresentationStatus.REGISTERED;
        this.presentationDate = evt.getDateTime();
        this.durationMinutes = evt.getDurationMinutes();
    }

    @EventSourcingHandler
    public void on(PresentationRefinedEvent evt) {
        this.presentationDate = evt.getDateTime();
        this.durationMinutes = evt.getDurationMinutes();
    }

    @EventSourcingHandler
    public void on(PresentationCanceledEvent evt) {
        status = PresentationStatus.CANCELED;
    }

    @EventSourcingHandler
    public void on(PresentationConfirmedEvent evt) {
        status = PresentationStatus.CONFIRMED;
    }
}
