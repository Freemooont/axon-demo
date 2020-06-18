package com.codefactory.demo.domain.entity;

import com.codefactory.demo.domain.PresentationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PresentationEntity {

    @Id
    private UUID id;
    private LocalDateTime date;
    @Enumerated(value = EnumType.STRING)
    private PresentationStatus status;
    private long duration;
    private int pizzasOrdered;

}
