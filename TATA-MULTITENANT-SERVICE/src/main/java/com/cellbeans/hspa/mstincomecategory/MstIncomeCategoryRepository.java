package com.cellbeans.hspa.mstincomecategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstIncomeCategoryRepository extends JpaRepository<MstIncomeCategory, Long> {

    Page<MstIncomeCategory> findByIcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstIncomeCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstIncomeCategory> findByIcNameContains(String key);

    MstIncomeCategory findByIcNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
