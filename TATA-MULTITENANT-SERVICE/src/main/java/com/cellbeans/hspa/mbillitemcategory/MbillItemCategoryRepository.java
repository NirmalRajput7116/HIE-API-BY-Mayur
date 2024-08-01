package com.cellbeans.hspa.mbillitemcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillItemCategoryRepository extends JpaRepository<MbillItemCategory, Long> {

    Page<MbillItemCategory> findByIcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillItemCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillItemCategory> findByIcNameContains(String key);

}
            
