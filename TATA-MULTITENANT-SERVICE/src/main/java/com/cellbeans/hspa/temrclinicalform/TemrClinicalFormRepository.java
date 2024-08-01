package com.cellbeans.hspa.temrclinicalform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TemrClinicalFormRepository extends JpaRepository<TemrClinicalForm, Long> {

    Page<TemrClinicalForm> findBytcfIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrClinicalForm> findBytcfPatientIdContainsAndIsActiveTrueAndIsDeletedFalse(String patientId, Pageable page);

    Page<TemrClinicalForm> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrClinicalForm> findByTcfIdContains(String key);

    List<TemrClinicalForm> findAllByFormdateAndTcfFormNameAndTcfPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(String key, String name, String tcfPatientId);

    @Query(value = "SELECT * FROM temr_clinical_form  WHERE tcf_patient_id = ?1 GROUP BY formdate", nativeQuery = true)
    List<TemrClinicalFormDto> findformpatient(String patientID);

    @Modifying
    @Transactional
    @Query("delete from TemrClinicalForm u where u.formdate =:formdate and u.tcfFormName =:tcf_form_name")
    void deleteUsersByFirstName(@Param("formdate") String formdate, @Param("tcf_form_name") String tcf_form_name);
//	@Query(value ="SELECT DISTINCT tcf_form_name, formdate FROM temr_clinical_form where tcf_patient_id = ?1", nativeQuery = true)
//	List<TemrClinicalFormDto> findformpatient(String key);

    Page<TemrClinicalForm> findAllByTcfPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(String patientID, Pageable page);

}