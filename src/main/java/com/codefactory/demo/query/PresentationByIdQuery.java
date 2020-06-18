package com.codefactory.demo.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
public class PresentationByIdQuery {
    private final UUID preasentationId;

    @JsonCreator
    public PresentationByIdQuery(@JsonProperty("preasentationId") UUID preasentationId) {
        this.preasentationId = preasentationId;
    }
}
