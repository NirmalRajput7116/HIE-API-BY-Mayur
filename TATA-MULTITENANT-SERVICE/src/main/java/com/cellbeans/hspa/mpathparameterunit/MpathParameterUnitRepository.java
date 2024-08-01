package com.cellbeans.hspa.mpathparameterunit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathParameterUnitRepository extends JpaRepository<MpathParameterUnit, Long> {

    Page<MpathParameterUnit> findByPuNameContainsAndIsActiveTrueAndIsDeletedFalseOrPuCodeContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    Page<MpathParameterUnit> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathParameterUnit> findByPuNameContains(String key);

}
            
