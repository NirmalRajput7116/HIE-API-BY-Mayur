package com.cellbeans.hspa.tbillserviceradiology;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface TbillServiceRadiologyRepository extends JpaRepository<TbillServiceRadiology, Long> {

    Page<TbillServiceRadiology> findByBsrBsIdBsBillIdBillNumberContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TbillServiceRadiology> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    @Query("SELECT a FROM TbillServiceRadiology a where a.isActive = 1 and a.isDeleted = 0 and a.createdDate = :date ")
    Page<TbillServiceRadiology> findAllByBsrIsCancelFalseAndBsrIsScanCompletedFalseAndIsActiveTrueAndIsDeletedFalse(Pageable page, @Param("date") Date date);

    Page<TbillServiceRadiology> findAllByBsrIsScanCompletedFalseAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillServiceRadiology> findByBsrBsIdBsBillIdBillNumberContainsAndIsDeletedFalseAndIsActiveTrue(String key);

    List<TbillServiceRadiology> findByBsrBsIdBsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long billId);

    List<TbillServiceRadiology> findByBsrBsIdBsBillIdBillAdmissionIdAdmissionPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long patientId);

    TbillServiceRadiology findByBsrBsIdBsIdAndIsActiveTrueAndIsDeletedFalse(Long key);

    TbillServiceRadiology findByBsrIsCallTrueAndBsrIsScanCompletedFalseAndIsActiveTrueAndIsDeletedFalse();

    //reported or non reported search
    Page<TbillServiceRadiology> findByBsrIsReportedEqualsAndIsActiveTrueAndIsDeletedFalse(Boolean status, Pageable page);

    //cancel service or non cancelled service search
    Page<TbillServiceRadiology> findByBsrIsCancelEqualsAndIsActiveTrueAndIsDeletedFalse(Boolean cancelstatus, Pageable page);

    Page<TbillServiceRadiology> findByBsrBsIdBsBillIdTbillUnitIdUnitIdAndBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsOrBsrBsIdBsServiceIdServiceCodeContainsOrBsrBsIdBsServiceIdServiceNameContainsOrBsrBsIdBsStaffIdStaffSdIdSdNameContainsAndIsActiveTrueAndIsDeletedFalse(long unitid, String mrno, String fn, String mn, String lnm, String scode, String sname, String sdname, Pageable page);
    //test count for MIS

    Page<TbillServiceRadiology> findAllByBsrIsReportedEqualsAndIsActiveTrueAndIsDeletedFalse(Boolean reporttype, Pageable page);

    Page<TbillServiceRadiology> findByBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsOrBsrBsIdBsServiceIdServiceSgIdSgNameContainsOrBsrBsIdBsStaffIdStaffUserIdUserFirstnameContainsOrBsrBsIdBsStaffIdStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(String mrno, String fn, String mn, String lnm, String subgrp, String dfn, String dln, Pageable page);

    @Query(value = "SELECT * FROM  tbill_service_radiology where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d')   = CURDATE()  \n#pageable\n", countQuery = "Select count(*) from tbill_service_radiology  where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d')   = CURDATE()", nativeQuery = true)
    Page<TbillServiceRadiology> findAllByTodayCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    @Query(value = "SELECT * FROM  tbill_service_radiology where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from tbill_service_radiology  where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)", nativeQuery = true)
    Page<TbillServiceRadiology> findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, Pageable page);

    //print MIS
    List<TbillServiceRadiology> findAllByBsrIsReportedEqualsAndIsActiveTrueAndIsDeletedFalse(Boolean reporttype);

    List<TbillServiceRadiology> findByBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsOrBsrBsIdBsServiceIdServiceSgIdSgNameContainsOrBsrBsIdBsStaffIdStaffUserIdUserFirstnameContainsOrBsrBsIdBsStaffIdStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(String mrno, String fn, String mn, String lnm, String subgrp, String dfn, String dln);

    @Query(value = "SELECT * FROM  tbill_service_radiology where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d')  = CURDATE() ", nativeQuery = true)
    List<TbillServiceRadiology> findAllByTodayCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse();

    List<TbillServiceRadiology> findAllByIsActiveTrueAndIsDeletedFalse();

    @Query(value = "SELECT * FROM  tbill_service_radiology where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    List<TbillServiceRadiology> findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed);
    //by romil

    Page<TbillServiceRadiology> findByBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientId(Long patientId, Pageable page);

    
    /*@Query(value = "select new com.cellbeans.hspa.tbillserviceradiology.TbillServiceRadiologyDTO(tbillbillServiceRadiology.bsrBsId.bsServiceId.serviceCode, tbillbillServiceRadiology.bsrBsId.bsServiceId.serviceName,tbillbillServiceRadiology.bsrBsId.bsStaffId.staffUserId.userFirstname,tbillbillServiceRadiology.bsrBsId.bsrIsServiceDone,tbillbillServiceRadiology.bsrBsId.bsrIsPaid, tbillbillServiceRadiology.bsrBsId.createdDate,tbillbillServiceRadiology.bsrBsId.bsrReportPath) from TbillServiceRadiology tbillbillServiceRadiology where tbillbillServiceRadiology.bsrBsId.billId.billVisitId.visitPatientId.patientId=:patientId")
    List<TbillServiceRadiologyDTO> findByPatientId(@Param("patientId") Long patientId);*/

    default Map<String, Object> findByPatientId(Long patientId, Pageable page) {
        Page<TbillServiceRadiology> tbillServiceRadiologies = findByBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientId(patientId, page);
        List<TbillServiceRadiologyDTO> tbillServiceRadiologyDTOS = tbillServiceRadiologies.getContent().stream().map(this::toDo).collect(Collectors.toList());
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("totalPages", tbillServiceRadiologies.getTotalPages());
        stringObjectMap.put("totalElements", tbillServiceRadiologies.getTotalElements());
        stringObjectMap.put("content", tbillServiceRadiologyDTOS);
        return stringObjectMap;
    }

    Page<TbillServiceRadiology> findAllByBsrBsIdBsBillIdBillVisitIdVisitIdEquals(Long visitId, Pageable page);

    // by GS
    default Map<String, Object> findByPatientVisitId(Long visitId, Pageable page) {
        //bsr_id        bsr_bs_idbs_bill_id bill_id bill_visit_id visit_id
        Page<TbillServiceRadiology> tbillServiceRadiologies = findAllByBsrBsIdBsBillIdBillVisitIdVisitIdEquals(visitId, page);
        List<TbillServiceRadiologyDTO> tbillServiceRadiologyDTOS = tbillServiceRadiologies.getContent().stream().map(this::toDo1).collect(Collectors.toList());
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("totalPages", tbillServiceRadiologies.getTotalPages());
        stringObjectMap.put("totalElements", tbillServiceRadiologies.getTotalElements());
        stringObjectMap.put("content", tbillServiceRadiologyDTOS);
//        stringObjectMap.put("content", tbillServiceRadiologies);
        return stringObjectMap;
    }

    default TbillServiceRadiologyDTO toDo1(TbillServiceRadiology tbillServiceRadiology) {
        TbillServiceRadiologyDTO tbillServiceRadiologyDTO = new TbillServiceRadiologyDTO();
        tbillServiceRadiologyDTO.setServiceCode(tbillServiceRadiology.getBsrBsId().getBsServiceId().getServiceCode());
        tbillServiceRadiologyDTO.setServiceName(tbillServiceRadiology.getBsrBsId().getBsServiceId().getServiceName());
        tbillServiceRadiologyDTO.setServiceBilled(tbillServiceRadiology.getBsrIsPaid());
        tbillServiceRadiologyDTO.setServiceDone(tbillServiceRadiology.getBsrIsServiceDone());
        tbillServiceRadiologyDTO.setPerformedBy(tbillServiceRadiology.getBsrBsId().getBsStaffId().getStaffUserId().getUserFirstname() + " " + tbillServiceRadiology.getBsrBsId().getBsStaffId().getStaffUserId().getUserLastname());
        tbillServiceRadiologyDTO.setReportPath(tbillServiceRadiology.getBsrReportPath());
        tbillServiceRadiologyDTO.setServiceDate(tbillServiceRadiology.getCreatedDate());
//        tbillServiceRadiologyDTO.setReportId(tbillServiceRadiology.getBsrBsId().getBsId());
        tbillServiceRadiologyDTO.setReportId(tbillServiceRadiology.getBsrReportId());
        tbillServiceRadiologyDTO.setReported(tbillServiceRadiology.getBsrIsReported());
        tbillServiceRadiologyDTO.setBsrId(tbillServiceRadiology.getBsrId());
        return tbillServiceRadiologyDTO;
    }

    default TbillServiceRadiologyDTO toDo(TbillServiceRadiology tbillServiceRadiology) {
        TbillServiceRadiologyDTO tbillServiceRadiologyDTO = new TbillServiceRadiologyDTO();
        tbillServiceRadiologyDTO.setServiceCode(tbillServiceRadiology.getBsrBsId().getBsServiceId().getServiceCode());
        tbillServiceRadiologyDTO.setServiceName(tbillServiceRadiology.getBsrBsId().getBsServiceId().getServiceName());
        tbillServiceRadiologyDTO.setServiceBilled(tbillServiceRadiology.getBsrIsPaid());
        tbillServiceRadiologyDTO.setServiceDone(tbillServiceRadiology.getBsrIsServiceDone());
        tbillServiceRadiologyDTO.setPerformedBy(tbillServiceRadiology.getBsrBsId().getBsStaffId().getStaffUserId().getUserFirstname() + " " + tbillServiceRadiology.getBsrBsId().getBsStaffId().getStaffUserId().getUserLastname());
        tbillServiceRadiologyDTO.setReportPath(tbillServiceRadiology.getBsrReportPath());
        tbillServiceRadiologyDTO.setServiceDate(tbillServiceRadiology.getCreatedDate());
//        tbillServiceRadiologyDTO.setReportId(tbillServiceRadiology.getBsrBsId().getBsId());
        tbillServiceRadiologyDTO.setReportId(tbillServiceRadiology.getBsrReportId());
        tbillServiceRadiologyDTO.setReported(tbillServiceRadiology.getBsrIsReported());
        return tbillServiceRadiologyDTO;
    }

    Page<TbillServiceRadiology> findAllByBsrBsIdBsBillIdTbillUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long unitId, Pageable page);

    Page<TbillServiceRadiology> findByBsrBsIdBsBillIdTbillUnitIdUnitIdAndBsrIsReportedEqualsAndIsActiveTrueAndIsDeletedFalse(Long unitId, Boolean status, Pageable page);

    Page<TbillServiceRadiology> findByBsrBsIdBsBillIdTbillUnitIdUnitIdAndBsrIsCancelEqualsAndIsActiveTrueAndIsDeletedFalse(Long unitId, Boolean cancelstatus, Pageable page);

    Page<TbillServiceRadiology> findAllByBsrBsIdBsDateBetweenAndBsrBsIdBsBillIdTbillUnitIdUnitId(Date sdate, Date edate, Long unitId, Pageable page);

    Page<TbillServiceRadiology> findAllByBsrBsIdBsBillIdBillVisitIdVisitId(Long visitId, Pageable page);

    Page<TbillServiceRadiology> findAllByBsrBsIdBsBillIdBillAdmissionIdAdmissionId(Long admissionId, Pageable page);

    Page<TbillServiceRadiology> findAllByBsrBsIdBsBillIdTbillUnitIdUnitIdAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(Long unitId, Date sdate, Date edate, Pageable page);

    TbillServiceRadiology findByBsrIdAndIsActiveTrueAndIsDeletedFalse(Long unitId);

    TbillServiceRadiology findByBsrCsIdCsIdAndIsActiveTrueAndIsDeletedFalse(Long ipdChargeServiceId);
//    List<TbillServiceRadiology> findAllByBsrIdContainsOrBsrBsIdBsBillIdBillAdmissionIdAdmissionPatientIdPatientIdContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientIdContainsAndIsActiveTrueAndIsDeletedFalse(Long id);
    //List<TbillServiceRadiology> findAllByBsrIdOrBsrBsIdBsBillIdBillAdmissionIdAdmissionPatientIdPatientIdOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long id);
}
