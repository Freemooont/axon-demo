package com.codefactory.demo.projector;

import com.codefactory.demo.domain.PresentationStatus;
import com.codefactory.demo.domain.entity.PresentationEntity;
import com.codefactory.demo.event.PresentationCanceledEvent;
import com.codefactory.demo.event.PresentationConfirmedEvent;
import com.codefactory.demo.event.PresentationRefinedEvent;
import com.codefactory.demo.event.PresentationRegisteredEvent;
import com.codefactory.demo.query.PresentationByIdQuery;
import com.codefactory.demo.query.PresentationListQuery;
import com.codefactory.demo.repository.CFPresentationRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@AllArgsConstructor
public class PresentationProjector {

    private final CFPresentationRepository presentationRepository;

    @EventHandler
    public void on(PresentationRegisteredEvent evt) {
        var entity = new PresentationEntity();

        entity.setDate(evt.getDateTime());
        entity.setStatus(PresentationStatus.REGISTERED);
        entity.setId(evt.getPresentationId());
        entity.setDuration(evt.getDurationMinutes());
        presentationRepository.save(entity);
    }

    @EventHandler
    public void on(PresentationRefinedEvent evt) {
        presentationRepository.findById(evt.getPresentationId()).ifPresent( pres -> {
            pres.setDate(evt.getDateTime());
            pres.setDuration(evt.getDurationMinutes());
        });
    }

    @EventHandler
    public void on(PresentationCanceledEvent evt) {
        presentationRepository.findById(evt.getPresentationId()).ifPresent( pres -> pres.setStatus(PresentationStatus.CANCELED));
    }

    @EventSourcingHandler
    public void on(PresentationConfirmedEvent evt) {
        presentationRepository.findById(evt.getPresentationId()).ifPresent( pres -> pres.setStatus(PresentationStatus.CONFIRMED));
    }

    @QueryHandler
    public List<PresentationEntity> query(PresentationListQuery criteria) {
        return presentationRepository.findAll();
    }

    @QueryHandler
    public PresentationEntity query(PresentationByIdQuery criteria) {
        return presentationRepository.findById(criteria.getPreasentationId())
                .orElseThrow(() -> new IllegalArgumentException("No presentation found by id: " + criteria.getPreasentationId()));
    }
}
