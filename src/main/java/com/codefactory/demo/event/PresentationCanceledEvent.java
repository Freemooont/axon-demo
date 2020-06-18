package com.codefactory.demo.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Getter
public class PresentationCanceledEvent {
    @TargetAggregateIdentifier
    private final UUID presentationId;

    @JsonCreator
    public PresentationCanceledEvent(@JsonProperty("presentationId") UUID presentationId) {
        this.presentationId = presentationId;
    }
}
