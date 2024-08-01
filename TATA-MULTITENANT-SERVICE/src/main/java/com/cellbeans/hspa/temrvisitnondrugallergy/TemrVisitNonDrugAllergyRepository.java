package com.cellbeans.hspa.temrvisitnondrugallergy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TemrVisitNonDrugAllergyRepository extends JpaRepository<TemrVisitNonDrugAllergy, Long> {

    Page<TemrVisitNonDrugAllergy> findByVndaNdaIdNdaNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrVisitNonDrugAllergy> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrVisitNonDrugAllergy> findAllByVndaVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrVisitNonDrugAllergy> findAllByVnTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrVisitNonDrugAllergy> findAllByVnTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `temr_visit_non_drug_allergy` WHERE vn_timeline_id = ?1 and vnda_nda_id = ?2", nativeQuery = true)
    int deletend(@Param("timelineID") Long timelineID, @Param("ndID") Long ndID);

    @Query(value = " select * from temr_visit_non_drug_allergy tvnda inner join temr_timeline tt on tt.timeline_id = tvnda.vn_timeline_id  where tt.timeline_patient_id = :patientId and tvnda.is_deleted = 0 group by tvnda.vnda_nda_id", nativeQuery = true)
    List<TemrVisitNonDrugAllergy> findAllByVnTimelineIdTimelinePatientIdPatientIdEqualsAndVndaAsIdAsIdEqualsAndIsDeletedFalse(@Param("patientId") Long patientId);
//        List<TemrVisitNonDrugAllergy> findByVndaNameContains(String key);
}
            
