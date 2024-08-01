package com.cellbeans.hspa.memrcareactivity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrCareActivityRepository extends JpaRepository<MemrCareActivity, Long> {
    MemrCareActivity findByCareActivityId(Long id);

    Page<MemrCareActivity> findByCareActivityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCareActivityName(String name, Pageable page);

    Page<MemrCareActivity> findAllByIsActiveTrueAndIsDeletedFalseOrderByCareActivityName(Pageable page);

    List<MemrCareActivity> findByCareActivityNameContainsAndIsActiveTrueAndIsDeletedFalse(String key);

    MemrCareActivity findByCareActivityNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
