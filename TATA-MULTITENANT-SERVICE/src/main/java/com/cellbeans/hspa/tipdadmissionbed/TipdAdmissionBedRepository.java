package com.cellbeans.hspa.tipdadmissionbed;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipdAdmissionBedRepository extends JpaRepository<TipdAdmissionBed, Long> {

    Page<TipdAdmissionBed> findByAbAdmissionIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TipdAdmissionBed> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TipdAdmissionBed> findByAbAdmissionIdContains(String key);

}
            
