package com.cellbeans.hspa.mstUnitType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstUnitTypeRepository extends JpaRepository<MstUnitType, Long> {
    Page<MstUnitType> findByunittypeNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstUnitType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstUnitType> findByunittypeNameContains(String key);

    MstUnitType findByunittypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
