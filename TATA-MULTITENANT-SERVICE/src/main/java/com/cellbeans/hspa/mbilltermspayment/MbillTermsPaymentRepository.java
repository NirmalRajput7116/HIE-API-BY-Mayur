package com.cellbeans.hspa.mbilltermspayment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillTermsPaymentRepository extends JpaRepository<MbillTermsPayment, Long> {

    Page<MbillTermsPayment> findByTpNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillTermsPayment> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillTermsPayment> findByTpNameContains(String key);

}
            
