package com.cellbeans.hspa.minvsupplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinvSupplierRepository extends JpaRepository<MinvSupplier, Long> {

    Page<MinvSupplier> findBySupplierNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MinvSupplier> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MinvSupplier> findBySupplierNameContains(String key);

}
            
