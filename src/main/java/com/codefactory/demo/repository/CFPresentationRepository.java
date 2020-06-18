package com.codefactory.demo.repository;

import com.codefactory.demo.domain.entity.PresentationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CFPresentationRepository extends JpaRepository<PresentationEntity, UUID> {
}
