package com.cellbeans.hspa.minvdispensing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinvDispensingRepository extends JpaRepository<MinvDispensing, Long> {

    Page<MinvDispensing> findByDispensingNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MinvDispensing> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MinvDispensing> findByDispensingNameContains(String key);

}
            
