package com.cellbeans.hspa.mstpatientsource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstPatientSourceRepository extends JpaRepository<MstPatientSource, Long> {

    Page<MstPatientSource> findByPsNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByPsNameAsc(String name, Pageable page);

    Page<MstPatientSource> findAllByIsActiveTrueAndIsDeletedFalseOrderByPsNameAsc(Pageable page);

    List<MstPatientSource> findByPsNameContains(String key);

    MstPatientSource findByPsNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
