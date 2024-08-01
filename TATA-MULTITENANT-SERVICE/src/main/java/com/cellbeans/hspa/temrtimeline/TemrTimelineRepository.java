package com.cellbeans.hspa.temrtimeline;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface TemrTimelineRepository extends JpaRepository<TemrTimeline, Long> {

    @Modifying
    @Query("Update TemrTimeline t SET t.timelineIsHistory=:timelineIsHistory WHERE t.id=:id")
    public void updateTimelineIsHistory(@Param("id") Long id, @Param("timelineIsHistory") Boolean timelineIsSymptoms);

    TemrTimeline findTopByTimelineVisitIdVisitId(Long visitId);

    @Query(value = "SELECT * FROM temr_timeline tt left JOIN trn_appointment ta ON tt.timeline_id = ta.appointment_timeline_id WHERE tt.timeline_id =:timelineId", nativeQuery = true)
    TemrTimeline findByTimelineIdAndIsActiveTrueAndIsDeletedFalse(@Param("timelineId") Long timelineId);

    Page<TemrTimeline> findByTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long patientId, Pageable page);

    Page<TemrTimeline> findByTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalseAndTimelineSIdServiceIsConsultionTrue(Long patientId, Pageable page);

    Page<TemrTimeline> findByTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalseAndTimelineServiceIdBsServiceIdServiceIsConsultionTrue(Long patientId, Pageable page);

    TemrTimeline findByTimelineVisitIdVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long visitId);

    Page<TemrTimeline> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    TemrTimeline findByTimelineVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long visitId);

    @Query(value = "SELECT * FROM temr_timeline tt WHERE tt.timeline_visit_id= :visitId ORDER BY tt.timeline_id DESC LIMIT 1", nativeQuery = true)
    TemrTimeline findByLastTimelineByVisitId(@Param("visitId") Long visitId);

    TemrTimeline findByTimelineVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalseAndTimelineServiceIdBsServiceIdServiceIsConsultionTrue(Long visitId);

    List<TemrTimeline> findByTimelineVisitIdContains(String key);

    TemrTimeline findByTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long timelineid);

    @Transactional
    @Modifying
    @Query(value = "update temr_timeline set timeline_is_history=true where timeline_id=:timelineid", nativeQuery = true)
    int updateTimeLineStatus(@Param("timelineid") Long timelineid);

    List<TemrTimeline> findAllByTimelineAdmissionIdAdmissionIdEqualsAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(Long admissionid, Date sdate, Date edate);

    List<TemrTimeline> findAllByTimelinePatientIdPatientIdEquals(Long admissionid);

    @Query(value = "select * from temr_timeline tt where tt.timeline_follow_date IS NOT NULL AND DATE(tt.timeline_follow_date) = CURDATE()", nativeQuery = true)
    List<TemrTimeline> findAllByTimelineFollowDate();

    @org.springframework.transaction.annotation.Transactional(readOnly = false)
    @Modifying
    @Query("Update TemrTimeline t SET t.timelineIsSymptoms=:timelineIsSymptoms WHERE t.id=:id")
    public void updateTimelineIsSymptoms(@Param("id") Long id, @Param("timelineIsSymptoms") Boolean timelineIsSymptoms);

    @Query(value = "SELECT tt.* FROM temr_timeline tt INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tt.timeline_service_id WHERE tbs.bs_id =:bsId", nativeQuery = true)
    TemrTimeline findBybsId(@Param("bsId") Long bsId);

//    List <TemrTimeline> findAllByIsEMRFinalTrueAndTemrFAndTimelineFinalizedDateBetween(Date startDate, Date endDate);

}
            
