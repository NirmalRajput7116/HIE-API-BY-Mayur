package com.cellbeans.hspa.temrvisitchiefcomplaint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TemrVisitChiefComplaintRepository extends JpaRepository<TemrVisitChiefComplaint, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `temr_visit_chief_complaint` WHERE vcc_timeline_id = ?1 and vcc_cc_id = ?2", nativeQuery = true)
    int deletechep(@Param("timelineID") Long timelineID, @Param("chepID") Long chepID);

    Page<TemrVisitChiefComplaint> findByVccCcIdCcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    TemrVisitChiefComplaint findByVccCcId(Long vccCCId);

    Page<TemrVisitChiefComplaint> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrVisitChiefComplaint> findAllByVccVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrVisitChiefComplaint> findAllByVccTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);
    List<TemrVisitChiefComplaint> findAllByVccTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long id);
//        List<TemrVisitChiefComplaint> findByVccNameContains(String key);

    @Query(value = "select * from temr_visit_chief_complaint tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vcc_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE(tv.created_date)  BETWEEN  :sd and :ed  \n#pageable\n", countQuery = "select count(vcc_id) from temr_visit_chief_complaint tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vcc_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE(tv.created_date)  BETWEEN  :sd and :ed   ", nativeQuery = true)
    Page<TemrVisitChiefComplaint> findAllByVccTimelineIdTimelinePatientIdPatientIdAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id, @Param("sd") String sd, @Param("ed") String ed, Pageable page);

    @Query(value = "select * from temr_visit_chief_complaint tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vcc_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE(tv.created_date)= CURDATE()  \n#pageable\n", countQuery = "select count(vcc_id) from temr_visit_chief_complaint tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vcc_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE(tv.created_date)= CURDATE()    ", nativeQuery = true)
    Page<TemrVisitChiefComplaint> findAllByVccTimelineIdTimelinePatientIdPatientIdAndCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id, Pageable page);

    List<TemrVisitChiefComplaint> findByVccTimelineIdTimelinePatientIdPatientIdEquals(Long id);
//    List<TemrVisitChiefComplaint> findAllByVccTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);
}
            
