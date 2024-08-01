package com.cellbeans.hspa.mstambulance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstAmbulanceRepository extends JpaRepository<MstAmbulance, Long> {

    Page<MstAmbulance> findByAmbulanceNumberContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstAmbulance> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAmbulance> findByAmbulanceNumberContains(String key);

}
            
