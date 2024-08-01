package com.cellbeans.hspa.trndischargesummarynew;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnDischargeSummaryNewRepository extends JpaRepository<TrnDischargeSummaryNew, Long> {

    TrnDischargeSummaryNew findByDsnAdmissionIdAdmissionIdEqualsAndDsnDsfIdDsfIdEqualsAndDsnFieldIdFieldIdEquals(Long admissionId, Long dsfId, Long fieldId);

    TrnDischargeSummaryNew findByDsnVisitIdVisitIdEqualsAndDsnDsfIdDsfIdEqualsAndDsnFieldIdFieldIdEquals(Long visitId, Long dsfId, Long fieldId);

}
