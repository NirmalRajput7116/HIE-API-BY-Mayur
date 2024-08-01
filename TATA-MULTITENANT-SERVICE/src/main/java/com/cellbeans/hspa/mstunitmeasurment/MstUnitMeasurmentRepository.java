package com.cellbeans.hspa.mstunitmeasurment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstUnitMeasurmentRepository extends JpaRepository<MstUnitMeasurment, Long> {

    Page<MstUnitMeasurment> findByUmNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstUnitMeasurment> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstUnitMeasurment> findByUmNameContains(String key);

}
            
