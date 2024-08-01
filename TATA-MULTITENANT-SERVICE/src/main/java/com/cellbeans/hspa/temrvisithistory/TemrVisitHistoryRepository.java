package com.cellbeans.hspa.temrvisithistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemrVisitHistoryRepository extends JpaRepository<TemrVisitHistory, Long> {

    Page<TemrVisitHistory> findByVhHtIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrVisitHistory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrVisitHistory> findByVhHtIdContains(String key);

    TemrVisitHistory findByVhVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long vhVisitId);

    TemrVisitHistory findFirstByVhTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalseOrderByVhIdDesc(Long id);

    @Query(value = "select * from temr_visit_history tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vh_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE(tv.created_date)  BETWEEN  :sd and :ed  \n#pageable\n", countQuery = "select count(vh_id) from temr_visit_history tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vh_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE(tv.created_date)  BETWEEN  :sd and :ed   ", nativeQuery = true)
    Page<TemrVisitHistory> findAllByvhTimelineIdTimelinePatientIdPatientIdAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id, @Param("sd") String sd, @Param("ed") String ed, Pageable page);

    @Query(value = "select * from temr_visit_history tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vh_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE(tv.created_date)= CURDATE()  \n#pageable\n", countQuery = "select count(vh_id) from temr_visit_history tv LEFT JOIN temr_timeline tt ON tt.timeline_id = tv.vh_timeline_id LEFT JOIN mst_patient mp ON mp.patient_id = tt.timeline_patient_id where tv.is_active = 1 and tv.is_deleted = 0 and tt.timeline_patient_id = :id and DATE(tv.created_date)= CURDATE()    ", nativeQuery = true)
    Page<TemrVisitHistory> findAllByvhTimelineIdTimelinePatientIdPatientIdAndCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id, Pageable page);

    List<TemrVisitHistory> findAllByVhTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    @Query(value = "SELECT * FROM temr_visit_history  vh " +
            "inner join mst_visit v on v.visit_id =  vh.vh_visit_id " +
            "inner join  mst_patient p on  p.patient_id = v.visit_patient_id " +
            "where   vh.is_active = 1 and vh.is_deleted = 0 and v.visit_patient_id = :id order by vh_id limit 1 ", nativeQuery = true)
    TemrVisitHistory findByPatientId(@Param("id") Long id);

    @Query(value = "SELECT * FROM temr_visit_history  vh " +
            "inner join mst_visit v on v.visit_id =  vh.vh_visit_id " +
            "inner join  mst_patient p on  p.patient_id = v.visit_patient_id " +
            "where   vh.is_active = 1 and vh.is_deleted = 0 and v.visit_patient_id = :id order by vh_id", nativeQuery = true)
    List<TemrVisitHistory> findAllByPatientId(@Param("id") Long id);
}
            
