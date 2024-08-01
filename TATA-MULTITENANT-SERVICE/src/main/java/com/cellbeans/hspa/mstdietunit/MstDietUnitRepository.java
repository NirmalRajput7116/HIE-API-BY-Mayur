package com.cellbeans.hspa.mstdietunit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDietUnitRepository extends JpaRepository<MstDietUnit, Integer> {
    MstDietUnit findByduId(Integer id);

    Page<MstDietUnit> findByDuIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDietUnit> findByDuUnitNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDietUnit> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDietUnit> findByDuIdContains(String key);

    List<MstDietUnit> findAllByIsActiveTrueAndIsDeletedFalse();

    MstDietUnit findByDuUnitNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
