package com.cellbeans.hspa.trnotdoctordetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtDoctorDetailsRepository extends JpaRepository<TrnOtDoctorDetails, Long> {
//    Page<TrnOtDoctorDetails> findByodIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//    Page<TrnOtDoctorDetails> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//    List<TrnOtDoctorDetails> findByOdIdContains(String key);
//    List<TrnOtDoctorDetails> findAllByOdPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtDoctorDetails> findAllByOddOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtDoctorDetails> findAllByOddOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtDoctorDetails> findAllByOddProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

}
            
