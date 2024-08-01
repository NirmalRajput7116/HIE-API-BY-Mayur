package com.cellbeans.hspa.EmgNursingAssessmentForm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmgNursingAssessmentFormRepository extends JpaRepository<EmgNursingAssessmentForm, Long> {

    /*

        EmgNursingAssessmentForm findByEtfIdAndIsActiveTrueAndIsDeletedFalse(long id);

        List<EmgNursingAssessmentForm> findByEtfPatientMrNoAndEtfPatientErNoAndEtfVisitIdAndIsActiveTrueAndIsDeletedFalse(String mrNo, String erNo, String visitId);
    */
    List<EmgNursingAssessmentForm> findByEnaPatientErNoAndEnaPatientMrNoAndEnaVisitIdAndIsActiveTrueAndIsDeletedFalse(String mrNo, String erNo, String visitId);
}
