package com.cellbeans.hspa.tbillrecieptpaymentmode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbillRecieptPaymentModeRepository extends JpaRepository<TbillRecieptPaymentMode, Long> {

    Page<TbillRecieptPaymentMode> findByRpmPmIdPmNameContainsOrRpmRecieptIdBrRecieptNumberContainsAndIsActiveTrueAndIsDeletedFalse(String paymentMode, String recieptNumber, Pageable page);

    Page<TbillRecieptPaymentMode> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillRecieptPaymentMode> findByRpmRecieptIdBrRecieptNumberContains(String key);

}
            
