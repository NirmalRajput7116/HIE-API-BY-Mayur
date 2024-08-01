package com.cellbeans.hspa.mstpaymentmode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstPaymentModeRepository extends JpaRepository<MstPaymentMode, Long> {

    Page<MstPaymentMode> findByPmNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstPaymentMode> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstPaymentMode> findByPmNameContains(String key);

}
            
