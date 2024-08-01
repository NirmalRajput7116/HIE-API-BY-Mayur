package com.cellbeans.hspa.mstcancelreason;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstCancelReasonRepositry extends JpaRepository<MstCancelReason, Long> {

    Page<MstCancelReason> findByCancelrasonNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCancelrasonName(String name, Pageable page);

    Page<MstCancelReason> findAllByIsActiveTrueAndIsDeletedFalseOrderByCancelrasonName(Pageable page);

    List<MstCancelReason> findByCancelrasonNameContainsAndIsActiveTrueAndIsDeletedFalse(String key);

    MstCancelReason findByCancelrasonNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
