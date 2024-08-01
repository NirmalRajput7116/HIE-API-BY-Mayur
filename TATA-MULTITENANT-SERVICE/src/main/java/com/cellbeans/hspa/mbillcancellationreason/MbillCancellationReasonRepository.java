package com.cellbeans.hspa.mbillcancellationreason;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillCancellationReasonRepository extends JpaRepository<MbillCancellationReason, Long> {

    Page<MbillCancellationReason> findByCrNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillCancellationReason> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillCancellationReason> findByCrNameContains(String key);

}
            
