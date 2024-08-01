package com.cellbeans.hspa.mstunit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstUnitRepository extends JpaRepository<MstUnit, Long> {

    Page<MstUnit> findByUnitNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByUnitNameAsc(String name, Pageable page);

    Page<MstUnit> findAllByIsActiveTrueAndIsDeletedFalseOrderByUnitNameAsc(Pageable page);

    List<MstUnit> findByUnitOrgIdOrgIdEquals(Long orgId);

    List<MstUnit> findByUnitNameContains(String key);

    Page<MstUnit> findByUnitIdAndIsActiveTrueAndIsDeletedFalse(long key, Pageable page);

    List<MstUnit> findByUnitIdAndIsActiveTrueAndIsDeletedFalse(long unitId);

    List<MstUnit> findAllByIsActiveTrueAndIsDeletedFalse();

    /*  List<MstUnit> findByCcUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(long unitId);*/
    MstUnit findByUnitId(Long unitId);
    MstUnit findByUnitNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
            
