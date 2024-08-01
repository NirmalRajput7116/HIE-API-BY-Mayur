package com.cellbeans.hspa.tbillsponcer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbillSponcerRepository extends JpaRepository<TbillSponcer, Long> {

    Page<TbillSponcer> findByBsBillIdBillNumberContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TbillSponcer> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillSponcer> findByBsBillIdBillNumberContains(String key);

}
            
