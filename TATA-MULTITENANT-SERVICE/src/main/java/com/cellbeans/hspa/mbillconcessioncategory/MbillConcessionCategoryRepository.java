package com.cellbeans.hspa.mbillconcessioncategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillConcessionCategoryRepository extends JpaRepository<MbillConcessionCategory, Long> {

    Page<MbillConcessionCategory> findByCcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillConcessionCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillConcessionCategory> findByCcNameContains(String key);

}
            
