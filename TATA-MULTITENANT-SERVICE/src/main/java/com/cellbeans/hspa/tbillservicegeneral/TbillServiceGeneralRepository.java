package com.cellbeans.hspa.tbillservicegeneral;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbillServiceGeneralRepository extends JpaRepository<TbillServiceGeneral, Long> {

    Page<TbillServiceGeneral> findByBsgBsIdBsBillIdBillNumberContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TbillServiceGeneral> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillServiceGeneral> findByBsgBsIdBsBillIdBillNumberContains(String key);

}
            
