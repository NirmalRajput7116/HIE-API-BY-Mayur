package com.cellbeans.hspa.tbillbillrefund;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbillBillRefundRepository extends JpaRepository<TbillBillRefund, Long> {

    Page<TbillBillRefund> findByIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TbillBillRefund> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

}
            
