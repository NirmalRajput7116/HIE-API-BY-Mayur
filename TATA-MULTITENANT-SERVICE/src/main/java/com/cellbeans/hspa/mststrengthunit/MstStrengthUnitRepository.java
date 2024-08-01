package com.cellbeans.hspa.mststrengthunit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstStrengthUnitRepository extends JpaRepository<MstStrengthUnit, Long> {

    Page<MstStrengthUnit> findBySuNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstStrengthUnit> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstStrengthUnit> findBySuNameContains(String key);

}
            
