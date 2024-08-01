package com.cellbeans.hspa.temrvisitdiagnosis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TemrVisitDiagnosisRepository extends JpaRepository<TemrVisitDiagnosis, Long> {
    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM `temr_visit_diagnosis` LEFT JOIN memr_icd_code ON memr_icd_code.ic_id = temr_visit_diagnosis.vd_ic_id WHERE vd_visit_id = :visitId", nativeQuery = true)
    List<TemrVisitDiagnosis> findByVdVisitId(@Param("visitId") Long visitId);

    Page<TemrVisitDiagnosis> findByVdStatusContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrVisitDiagnosis> findByVdVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    Page<TemrVisitDiagnosis> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrVisitDiagnosis> findByVdStatusContains(String key);

    Page<TemrVisitDiagnosis> findByVdTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    List<TemrVisitDiagnosis> findByVdTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long id);

//    Page<TemrVisitDiagnosis> findByVdTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    List<TemrVisitDiagnosis> findAllByVdTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrVisitDiagnosis> findByVdTimelineIdTimelinePatientIdPatientIdEquals(Long id);

}
            
