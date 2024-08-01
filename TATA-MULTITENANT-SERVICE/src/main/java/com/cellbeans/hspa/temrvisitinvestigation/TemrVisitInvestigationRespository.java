package com.cellbeans.hspa.temrvisitinvestigation;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.cellbeans.hspa.trnadmission.TrnAdmissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
public interface TemrVisitInvestigationRespository extends JpaRepository<TemrVisitInvestigation, Long> {
    @Query(value = "select * from temr_visit_investigation tvi inner join mst_visit mv on tvi.vi_visit_id = mv.visit_id inner join mst_patient mp on mv.visit_patient_id = mp.patient_id inner join mbill_service ms on ms.service_id = tvi.vi_service_id where tvi.vi_visit_id=:visitId and tvi.is_active=1 and tvi.is_deleted=0", nativeQuery = true)
    List<TemrVisitInvestigation> findAllByViVisitIdTemrVisitInvestigation(@Param("visitId") Long visitId);

    List<TemrVisitInvestigation> findAllByIsActiveTrueAndIsDeletedFalse();

    List<TemrVisitInvestigation> findByViTimelineIdTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(long id);

    Page<TemrVisitInvestigation> findByViVisitIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrVisitInvestigation> findByViVisitIdVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long qString, Pageable page);

    Page<TemrVisitInvestigation> findByViVisitIdVisitIdEqualsAndIsServiceBilledAndIsActiveTrueAndIsDeletedFalse(Long qString, Integer i, Pageable page);

    //Author: Mohit
    Page<TemrVisitInvestigation> findAllByViVisitIdVisitIdAndViRegistrationSourceEqualsAndIsActiveTrueAndIsDeletedFalse(Long viVisitId, Integer source, Pageable page);

    Page<TemrVisitInvestigation> findByViTimelineIdTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long qString, Pageable page);

    List<TemrVisitInvestigation> findByViTimelineIdTimelinePatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long qString);

    List<TemrVisitInvestigation> findByViTimelineIdTimelineIdEqualsAndIsServiceBilledEqualsAndIsActiveTrueAndIsDeletedFalse(Long qString, Integer i);

    //Author: Mohit
    @Modifying
    @Query(value = "SELECT temrVi.viServiceId FROM TemrVisitInvestigation temrVi WHERE temrVi.viVisitId.visitId =:visitId AND temrVi.isActive =:isActive AND temrVi.isDeleted =:isDeleted")
    List<MbillService> findServicesByViVisitId(@Param("visitId") Long visitId, @Param("isActive") Boolean isActive, @Param("isDeleted") Boolean isDeleted);

    @Modifying
    @Query(value = "SELECT temrVi FROM TemrVisitInvestigation temrVi WHERE temrVi.viTimelineId.timelineAdmissionId.admissionId =:admissionId AND temrVi.isActive = 1 AND temrVi.isDeleted = 0 AND temrVi.isServiceBilled =:isBilled")
    List<TemrVisitInvestigation> findServicesByAdmissionId(@Param("admissionId") Long admissionId, @Param("isBilled") Integer isBilled);

    Page<TemrVisitInvestigation> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TemrVisitInvestigation> findAllByIsServiceBilledAndIsActiveTrueAndIsDeletedFalse(Integer i, Pageable page);

    List<TemrVisitInvestigation> findByViVisitIdContains(String key);

    @Modifying
    @Query(value = "update temr_visit_investigation tvi set tvi.is_service_billed =:isServiceBilled where tvi.vi_id =:viId", nativeQuery = true)
    int updateTemrVisitInvestigationForBill(@Param("viId") Long viId, @Param("isServiceBilled") Integer isServiceBilled);

    default Boolean updateDoctorOrderForBill(List<TemrVisitInvestigation> temrVisitInvestigations) {
        try {
            for (TemrVisitInvestigation temrVisitInvestigation : temrVisitInvestigations) {
                updateTemrVisitInvestigationForBill(temrVisitInvestigation.getViId(), temrVisitInvestigation.getIsServiceBilled());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Authod: Mohit
    default List<MbillService> findDoctorOrderForIPD(Long patientId, Integer patientRegistrationSource, String fromDate, MstVisitRepository mstVisitRepository) {
        //1. find visits from admission date
        //2. find all visitinvestingation by visits of admission
        List<MstVisit> mstVisits = mstVisitRepository.findByVisitPatientIdPatientIdAndVisitRegistrationSourceEqualsAndVisitDateGreaterThanEqualAndIsActiveTrueAndIsDeletedFalse(patientId, patientRegistrationSource, fromDate);
        List<MbillService> services = new ArrayList<>();
        if (mstVisits.size() > 0) {
            for (MstVisit mstVisit : mstVisits) {
                services.addAll(findServicesByViVisitId(mstVisit.getVisitId(), true, false));
            }
        }
        return services;
    }

    //Author: Jay
    default List<TemrVisitInvestigation> findunbilledDoctorOrderForIPD(Long patientId, Integer patientRegistrationSource, String fromDate, TrnAdmissionRepository trnAdmissionRepository) {
        //1. find visits from admission date
        //2. find all visitinvestingation by visits of admission
        List<TrnAdmission> mstAdmission = trnAdmissionRepository.findByadmitedPatientIdPatientIdAndadmitedRegistrationSourceEqualsAndadmitedDateGreaterThanEqualAndIsActiveTrueAndIsDeletedFalse(patientId, fromDate);
        List<TemrVisitInvestigation> services = new ArrayList<>();
        if (mstAdmission.size() > 0) {
            for (TrnAdmission trnAdmission : mstAdmission) {
                services.addAll(findServicesByAdmissionId(trnAdmission.getAdmissionId(), 0));
            }
        }
        return services;
    }

    List<TemrVisitInvestigation> findAllByViTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long timeline);

    List<TemrVisitInvestigation> findAllByViServiceIdServiceIsLaboratoryAndViTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Boolean status, Long timeline);

    Page<TemrVisitInvestigation> findAllByViCreatedDateBetweenAndViTimelineIdTimelineServiceIdBsBillIdTbillUnitIdUnitIdOrViTimelineIdTimelineAdmissionIdAdmissionUnitIdUnitIdAndViServiceIdServiceIsRadiologyAndViServiceIdServiceNameContains(Date sdate, Date edate, long uid, long unitid, Boolean isservice, String search, Pageable page);

    Page<TemrVisitInvestigation> findAllByViCreatedDateBetweenAndViTimelineIdTimelineServiceIdBsBillIdTbillUnitIdUnitIdOrViTimelineIdTimelineAdmissionIdAdmissionUnitIdUnitIdAndViServiceIdServiceIsRadiology(Date sdate, Date edate, long uid, long unitid, Boolean isservice, Pageable page);
    //  List<TemrVisitInvestigation> findAllByViServiceIdServiceIsLaboratotryAndViTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Boolean val,Long timeline);

    TemrVisitInvestigation findByViTimelineIdTimelineIdEqualsAndViVisitIdVisitIdEqualsAndViServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long timelineId, Long visitId, Long serviceId);

}
