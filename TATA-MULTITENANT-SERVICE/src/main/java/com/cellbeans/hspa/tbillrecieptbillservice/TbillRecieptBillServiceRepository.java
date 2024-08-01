package com.cellbeans.hspa.tbillrecieptbillservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbillRecieptBillServiceRepository extends JpaRepository<TbillRecieptBillService, Long> {

    Page<TbillRecieptBillService> findByRbsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TbillRecieptBillService> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillRecieptBillService> findByRbsServiceIdServiceNameContains(String key);

}
            
