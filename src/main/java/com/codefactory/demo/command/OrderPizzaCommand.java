package com.codefactory.demo.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Getter
public class OrderPizzaCommand {

    @TargetAggregateIdentifier
    private final UUID presentationId;
    private final int pizzas;

    @JsonCreator
    public OrderPizzaCommand(@JsonProperty("presentationId") UUID presentationId,
                             @JsonProperty("pizzas") int pizzas) {
        this.presentationId = presentationId;
        this.pizzas = pizzas;
    }
}
