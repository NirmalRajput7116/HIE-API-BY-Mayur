package com.cellbeans.hspa.mstschedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstScheduleRepository extends JpaRepository<MstSchedule, Long> {

    Page<MstSchedule> findByScheduleNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstSchedule> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstSchedule> findByScheduleNameContains(String key);

}
            
