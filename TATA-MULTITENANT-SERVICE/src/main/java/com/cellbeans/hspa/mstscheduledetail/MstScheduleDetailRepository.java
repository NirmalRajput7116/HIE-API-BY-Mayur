package com.cellbeans.hspa.mstscheduledetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstScheduleDetailRepository extends JpaRepository<MstScheduleDetail, Long> {

    Page<MstScheduleDetail> findBySdScheduleIdScheduleNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstScheduleDetail> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstScheduleDetail> findBySdScheduleIdScheduleNameContains(String key);

}
            
