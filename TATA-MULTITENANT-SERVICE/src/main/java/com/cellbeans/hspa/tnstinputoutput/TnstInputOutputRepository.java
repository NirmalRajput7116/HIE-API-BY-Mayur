package com.cellbeans.hspa.tnstinputoutput;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TnstInputOutputRepository extends JpaRepository<TnstInputOutput, Long> {

    Page<TnstInputOutput> findByAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TnstInputOutput> findByIoAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(Long admissionId, Pageable page);
    // Page<TnstDrugAdmin> findByIoAdmissionIdAdmissionIdAndIoDateBetweenAndIsActiveTrueAndIsDeletedFalse(Long admissionId, @Param("date") @Temporal(TemporalType.DATE)  Date toDate ,@Param("date") @Temporal(TemporalType.DATE)  Date fromDate , Pageable page);

    Page<TnstInputOutput> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*List<TnstDrugAdmin> findByContains(String key);*/
}
            
