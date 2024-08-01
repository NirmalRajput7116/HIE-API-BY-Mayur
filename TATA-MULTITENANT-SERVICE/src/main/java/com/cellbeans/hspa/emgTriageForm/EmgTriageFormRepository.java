package com.cellbeans.hspa.emgTriageForm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmgTriageFormRepository extends JpaRepository<EmgTriageForm, Long> {

    EmgTriageForm findByEtfIdAndIsActiveTrueAndIsDeletedFalse(long id);

    List<EmgTriageForm> findByEtfPatientMrNoAndEtfPatientErNoAndEtfVisitIdAndIsActiveTrueAndIsDeletedFalse(String mrNo, String erNo, String visitId);

    EmgTriageForm findByEtfVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(String visitId);

}
