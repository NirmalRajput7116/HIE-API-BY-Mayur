package com.cellbeans.hspa.trnotstaffdetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtStaffDetailsRepository extends JpaRepository<TrnOtStaffDetails, Long> {
//    Page<TrnOtStaffDetails> findByosdIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//
//
//    Page<TrnOtStaffDetails> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//
//    List<TrnOtStaffDetails> findByOsdIdContains(String key);
//
//    List<TrnOtStaffDetails> findAllByOsdPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtStaffDetails> findAllByOsdOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtStaffDetails> findAllByOsdOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtStaffDetails> findAllByOsdProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

}
            
