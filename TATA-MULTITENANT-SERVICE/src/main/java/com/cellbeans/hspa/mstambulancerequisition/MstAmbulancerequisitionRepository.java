package com.cellbeans.hspa.mstambulancerequisition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstAmbulancerequisitionRepository extends JpaRepository<MstAmbulancerequisition, Long> {

    Page<MstAmbulancerequisition> findByApplicantNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstAmbulancerequisition> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAmbulancerequisition> findByApplicantNameContains(String key);

}
            
