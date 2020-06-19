package com.codefactory.demo.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Getter
public class PizzaOrdered {
    @TargetAggregateIdentifier
    private final UUID presentationId;
    private final int pizzas;

    @JsonCreator
    public PizzaOrdered(@JsonProperty("presentationId") UUID presentationId,
                        @JsonProperty("pizzas") int pizzas) {
        this.presentationId = presentationId;
        this.pizzas = pizzas;
    }
}
