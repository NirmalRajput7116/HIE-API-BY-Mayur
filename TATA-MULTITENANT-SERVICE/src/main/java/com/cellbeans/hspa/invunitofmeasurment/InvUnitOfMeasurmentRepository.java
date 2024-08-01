package com.cellbeans.hspa.invunitofmeasurment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvUnitOfMeasurmentRepository extends JpaRepository<InvUnitOfMeasurment, Long> {

    Page<InvUnitOfMeasurment> findByUomNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvUnitOfMeasurment> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvUnitOfMeasurment> findByUomNameContains(String key);

}
            
