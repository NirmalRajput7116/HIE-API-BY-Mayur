package com.cellbeans.hspa.mstappointmentreason;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstAppointmentReasonRepository extends JpaRepository<MstAppointmentReason, Long> {

    Page<MstAppointmentReason> findByArNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstAppointmentReason> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAppointmentReason> findByArNameContains(String key);

    MstAppointmentReason findByArNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
