package com.codefactory.demo.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class RegisterPresentationCommand {

    private final LocalDateTime dateTime;
    private final long durationMinutes;

    @JsonCreator
    public RegisterPresentationCommand(@JsonProperty("dateTime") LocalDateTime dateTime,
                                       @JsonProperty("durationMinutes") long durationMinutes) {
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
    }
}
