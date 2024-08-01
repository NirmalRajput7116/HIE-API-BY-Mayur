package com.cellbeans.hspa.temrvisitsymptom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TemrVisitSymptomRepository extends JpaRepository<TemrVisitSymptom, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `temr_visit_symptom` WHERE vs_timeline_id = ?1 and vs_symptom_id = ?2", nativeQuery = true)
    int deletesymtorm(@Param("timelineID") Long timelineID, @Param("symptomID") Long symptomID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `temr_visit_symptom` WHERE vs_timeline_id = ?1 and vs_sign_symptoms = ?2", nativeQuery = true)
    int deletesymtormByName(@Param("timelineID") Long timelineID, @Param("vs_sign_symptoms") String vs_sign_symptoms);

    Page<TemrVisitSymptom> findByVsSymptomIdSymptomNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    TemrVisitSymptom findByVsSymptomIdSymptomIdEqualsAndVsTimelineIdTimelineIdEquals(long symtermid, Long timelineId);

    void delete(TemrVisitSymptom deleted);

    Page<TemrVisitSymptom> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrVisitSymptom> findAllByVsVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrVisitSymptom> findAllByVsTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);
    List<TemrVisitSymptom> findAllByVsTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long id);
//        List<TemrVisitSymptom> findBySymptomNameContains(String key);
}
            
