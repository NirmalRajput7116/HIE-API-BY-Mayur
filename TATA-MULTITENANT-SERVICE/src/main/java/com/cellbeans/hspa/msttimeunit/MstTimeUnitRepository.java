package com.cellbeans.hspa.msttimeunit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstTimeUnitRepository extends JpaRepository<MstTimeUnit, Long> {

    Page<MstTimeUnit> findByTuNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstTimeUnit> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstTimeUnit> findByTuNameContains(String key);

}
            
