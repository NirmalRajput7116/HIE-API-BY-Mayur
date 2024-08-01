package com.cellbeans.hspa.temrvital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemrVitalRepository extends JpaRepository<TemrVital, Long> {
    @Query(value = "select * from temr_vital tv where tv.vital_visit_id = ?1", nativeQuery = true)
    List<TemrVital> findByVitalVisitId(Long id);

    TemrVital findByVitalVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long visitId);

    Page<TemrVital> findByVitalRemarkContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrVital> findByVitalVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    Page<TemrVital> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrVital> findByVitalRemarkContains(String key);

    Page<TemrVital> findByVitalTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    Page<TemrVital> findByVitalTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    @Query(value = "select * from temr_vital tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vital_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE_FORMAT(tv.created_date,'%y-%m-%d')  BETWEEN  :sd and :ed  \n#pageable\n", countQuery = "select count(vital_id) from temr_vital tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vital_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE_FORMAT(tv.created_date,'%y-%m-%d')  BETWEEN  :sd and :ed   ", nativeQuery = true)
    Page<TemrVital> findAllByVitalTimelineIdTimelinePatientIdPatientIdAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id, @Param("sd") String sd, @Param("ed") String ed, Pageable page);

    @Query(value = "select * from temr_vital tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vital_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE_FORMAT(tv.created_date,'%y-%m-%d')= CURDATE()  \n#pageable\n", countQuery = "select count(vital_id) from temr_vital tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vital_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE_FORMAT(tv.created_date,'%y-%m-%d')= CURDATE()    ", nativeQuery = true)
    Page<TemrVital> findAllByVitalTimelineIdTimelinePatientIdPatientIdAndCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id, Pageable page);

    List<TemrVital> findAllByVitalTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    @Query(value = "SELECT * FROM temr_vital tv LEFT JOIN mst_visit mv ON mv.visit_id = tv.vital_visit_id WHERE tv.is_active = 1 AND tv.is_deleted = 0 AND tv.vital_visit_id = :id ORDER BY tv.vital_id DESC LIMIT 1", nativeQuery = true)
    TemrVital findOneByVitalVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id);

    @Query(value = "select * from temr_vital tv " +
            "LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vital_timeline_id " +
            " where tv.is_active = 1 and tv.is_deleted = 0 and " +
            " tv.vital_timeline_id = :id order by tv.vital_id desc limit  1  ", nativeQuery = true)
    TemrVital findTimelineIdAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id);

    TemrVital findByVitalTimelineIdTimelineIdEquals(Long Id);

    @Query(value = "select * from temr_vital tv LEFT JOIN mst_visit tt ON tt.visit_id = tv.vital_visit_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.visit_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.visit_patient_id = :id and DATE_FORMAT(tv.created_date,'%y-%m-%d')  BETWEEN  :sd and :ed  \n#pageable\n", countQuery = "select count(vital_id) from temr_vital tv LEFT JOIN mst_visit tt ON tt.visit_id = tv.vital_visit_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.visit_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.visit_patient_id = :id and DATE_FORMAT(tv.created_date,'%y-%m-%d')  BETWEEN  :sd and :ed   ", nativeQuery = true)
    Page<TemrVital> findAllByVitalVisitIdVisitPatientIdPatientIdAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id, @Param("sd") String sd, @Param("ed") String ed, Pageable page);

    @Query(value = "select * from temr_vital tv LEFT JOIN mst_visit tt ON tt.visit_id = tv.vital_visit_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.visit_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.visit_patient_id = :id and DATE_FORMAT(tv.created_date,'%y-%m-%d')= CURDATE()  \n#pageable\n", countQuery = "select count(vital_id) from temr_vital tv LEFT JOIN mst_visit tt ON tt.visit_id = tv.vital_visit_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.visit_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.visit_patient_id = :id and DATE_FORMAT(tv.created_date,'%y-%m-%d')= CURDATE()    ", nativeQuery = true)
    Page<TemrVital> findAllByVitalVisitIdVisitPatientIdPatientIdAndCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id, Pageable page);

    List<TemrVital> findByVitalTimelineIdTimelinePatientIdPatientIdEquals(Long key);

    TemrVital findTopByVitalTimelineIdTimelinePatientIdPatientIdEquals(Long key);

}
            


