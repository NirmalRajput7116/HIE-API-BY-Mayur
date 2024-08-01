package com.cellbeans.hspa.mstpatientcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstPatientCategoryRepository extends JpaRepository<MstPatientCategory, Long> {

    Page<MstPatientCategory> findByPcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstPatientCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstPatientCategory> findByPcNameContains(String key);

}
            
