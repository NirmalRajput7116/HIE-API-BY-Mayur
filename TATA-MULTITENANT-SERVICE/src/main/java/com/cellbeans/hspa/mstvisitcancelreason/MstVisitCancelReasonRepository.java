package com.cellbeans.hspa.mstvisitcancelreason;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstVisitCancelReasonRepository extends JpaRepository<MstVisitCancelReason, Long> {

    Page<MstVisitCancelReason> findByVisitcancelreasonNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitcancelreasonName(String name, Pageable page);

    Page<MstVisitCancelReason> findAllByIsActiveTrueAndIsDeletedFalseOrderByVisitcancelreasonName(Pageable page);

    List<MstVisitCancelReason> findByVisitcancelreasonNameContainsAndIsActiveTrueAndIsDeletedFalse(String key);
//    MstVisitCancelReason findByVisitcancelreasonNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

    MstVisitCancelReason findByVisitcancelreasonNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
