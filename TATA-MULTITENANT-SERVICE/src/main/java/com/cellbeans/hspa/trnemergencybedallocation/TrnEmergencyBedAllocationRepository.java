package com.cellbeans.hspa.trnemergencybedallocation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnEmergencyBedAllocationRepository extends JpaRepository<TrnEmergencyBedAllocation, Long> {
    //  Page<TrnEmergencyBedAllocation> findByRouteNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnEmergencyBedAllocation> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    //advoice discharged list
    Page<TrnEmergencyBedAllocation> findAllByEbaPatientStatusEqualsOrEbaPatientStatusEqualsAndIsActiveTrueAndIsDeletedFalse(int patientstatus, int patientstatus2, Pageable page);

    // List<TrnEmergencyBedAllocation> findByRouteNameContains(String key);
    TrnEmergencyBedAllocation findOneByEbaVisitIdVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long visitid);
    //list by unit id

    Page<TrnEmergencyBedAllocation> findAllByEbaBedIdBedUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long unitid, Pageable page);

    //find by visit id
    TrnEmergencyBedAllocation findAllByEbaVisitIdVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long visitid);

    //advoice discharged list unit wise
    Page<TrnEmergencyBedAllocation> findAllByEbaVisitIdVisitUnitIdUnitIdEqualsAndEbaPatientStatusEqualsOrEbaPatientStatusEqualsAndIsActiveTrueAndIsDeletedFalse(long unitid, int patientstatus, int patientstatus2, Pageable page);

}
            
