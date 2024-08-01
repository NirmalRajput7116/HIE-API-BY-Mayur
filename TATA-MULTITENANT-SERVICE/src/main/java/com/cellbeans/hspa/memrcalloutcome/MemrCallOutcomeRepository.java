package com.cellbeans.hspa.memrcalloutcome;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrCallOutcomeRepository extends JpaRepository<MemrCallOutcome, Long> {
    MemrCallOutcome findByCallOutcomeId(Long id);

    Page<MemrCallOutcome> findByCallOutcomeNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCallOutcomeName(String name, Pageable page);

    Page<MemrCallOutcome> findAllByIsActiveTrueAndIsDeletedFalseOrderByCallOutcomeName(Pageable page);

    List<MemrCallOutcome> findByCallOutcomeNameContainsAndIsActiveTrueAndIsDeletedFalse(String key);

    MemrCallOutcome findByCallOutcomeNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
