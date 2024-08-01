package com.cellbeans.hspa.mstemergencypatient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstEmergencyPatientRepository extends JpaRepository<MstEmergencyPatient, Long> {
//    Page<MstEmergencyPatient> findByPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//
//    Page<MstEmergencyPatient> findByPatientMrNoEqualsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//    Page<MstEmergencyPatient> findByPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//    Page<MstEmergencyPatient> findByPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//    Page<MstEmergencyPatient> findByPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstEmergencyPatient> findAllByIsDeleted(Boolean isDeleted, Pageable page);

    Page<MstEmergencyPatient> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//    MstEmergencyPatient findByPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(String patientMrNo);
    //Page<MstPatient> findByPatientMrNoIsActiveTrueAndIsDeletedFalse(String mrno,Pageable page);
//    List<MstEmergencyPatient> findByPatientMrNoContains(String key);

    /*Page<MstPatient> findByPatientMrNoContainsOrPatienUserFirstnamtUserIdeContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String PatientMrNo, String UserFirstname,String UserLastname,String UserMobile, Pageable page);*/
}
            
