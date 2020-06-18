package com.codefactory.demo.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PresentationRefinedEvent {
    @TargetAggregateIdentifier
    private final UUID presentationId;
    private final LocalDateTime dateTime;
    private final long durationMinutes;

    @JsonCreator
    public PresentationRefinedEvent(@JsonProperty("presentationId") UUID presentationId,
                                       @JsonProperty("dateTime") LocalDateTime dateTime,
                                       @JsonProperty("durationMinutes") long durationMinutes) {
        this.presentationId = presentationId;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
    }
}
