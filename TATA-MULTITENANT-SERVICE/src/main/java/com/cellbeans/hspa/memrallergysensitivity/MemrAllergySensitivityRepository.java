package com.cellbeans.hspa.memrallergysensitivity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrAllergySensitivityRepository extends JpaRepository<MemrAllergySensitivity, Long> {

    Page<MemrAllergySensitivity> findByAsNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrAllergySensitivity> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrAllergySensitivity> findByAsNameContains(String key);

    MemrAllergySensitivity findByAsNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
