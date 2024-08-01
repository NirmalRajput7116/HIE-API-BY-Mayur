package com.cellbeans.hspa.mstpatienttype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstPatientTypeRepository extends JpaRepository<MstPatientType, Long> {

    Page<MstPatientType> findByPtNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByPtNameAsc(String name, Pageable page);

    Page<MstPatientType> findAllByIsActiveTrueAndIsDeletedFalseOrderByPtNameAsc(Pageable page);

    List<MstPatientType> findByPtNameContains(String key);

    MstPatientType findByPtNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
