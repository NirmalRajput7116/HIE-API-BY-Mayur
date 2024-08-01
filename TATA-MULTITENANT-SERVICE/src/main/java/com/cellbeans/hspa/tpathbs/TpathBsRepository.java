package com.cellbeans.hspa.tpathbs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TpathBsRepository extends JpaRepository<TpathBs, Long> {

    TpathBs findByPsCsIdCsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long psCsId);

    Page<TpathBs> findByPsBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long Id, Pageable page);

    @Query(value = "select * from tpath_bs ORDER BY ps_id DESC LIMIT 1", nativeQuery = true)
    TpathBs findAllSampleNo();

    //Lab Order List user based
    @Query(value = "select bs from TpathBs bs where bs.psBillId.billVisitId.visitUnitId.unitId =:unitid or bs.isPerformedBy=:performedby group by bs.psBillId.billId")
    Page<TpathBs> findAll(@Param("unitid") long unitid, @Param("performedby") String performedby, Pageable page);

    @Query(value = "select bs from TpathBs bs where bs.psBillId.billVisitId.visitUnitId.unitId=:unitid or bs.isPerformedBy=:performedby and (bs.psBillId.billNumber like CONCAT('%',:qstring,'%') OR bs.psBillId.billVisitId.visitPatientId.patientMrNo like CONCAT('%',:qstring,'%') OR bs.psBillId.billVisitId.visitPatientId.patientUserId.userFirstname like CONCAT('%',:qstring,'%'))  group by bs.psBillId.billId")
    Page<TpathBs> findByUnitIdAndPerformedByPathology(@Param("unitid") long unitid, @Param("performedby") String performedby, @Param("qstring") String qstring, Pageable page);

    @Query(value = "select bs from TpathBs bs where (bs.psBillId.billVisitId.visitUnitId.unitId =:unitid or bs.isPerformedBy =:performedby) and bs.isFinalized=:isfinalized group by bs.psBillId.billId")
    Page<TpathBs> findAllFinalizedReport(@Param("unitid") long unitid, @Param("performedby") String performedby, @Param("isfinalized") Boolean isfinalized, Pageable page);

    @Query(value = "select bs from TpathBs bs where (bs.psBillId.billVisitId.visitUnitId.unitId=:unitid or bs.isPerformedBy =:performedby) and bs.isFinalized=:isfinalized and (bs.psBillId.billNumber like CONCAT('%',:qstring,'%') OR bs.psBillId.billVisitId.visitPatientId.patientMrNo like CONCAT('%',:qstring,'%') OR bs.psBillId.billVisitId.visitPatientId.patientUserId.userFirstname like CONCAT('%',:qstring,'%')) group by bs.psBillId.billId")
    Page<TpathBs> findByUnitIdPathologyFinalizedReport(@Param("unitid") long unitid, @Param("performedby") String performedby, @Param("isfinalized") Boolean isfinalized, @Param("qstring") String qstring, Pageable page);

    List<TpathBs> findByPsBillIdBillIdAndIsActiveTrueAndIsDeletedFalse(long billId);

    List<TpathBs> findByMbillIPDChargeIpdchargeIdEqualsAndIsActiveTrueAndIsDeletedFalse(long billChargeId);

    //Others
    Page<TpathBs> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TpathBs> findByPsBillIdBillIdEqualsAndIsFinalizedFalseAndIsActiveTrueAndIsDeletedFalse(Long billId);

    List<TpathBs> findByPsBillIdBillIdEqualsAndIsFinalizedFalseAndMbillIPDChargeIsNullAndIsActiveTrueAndIsDeletedFalse(Long billId);

    //by Bill IdSELECT count(*) FROM tpath_bs where ps_bill_id= 21 and is_finalized = 0 and is_deleted = 0
    @Query(value = "SELECT count(*) FROM tpath_bs where ps_bill_id=:billId and is_finalized = 0 and is_deleted = 0", nativeQuery = true)
    int findByPsBillId(@Param("billId") Long billId);

    List<TpathBs> findByMbillIPDChargeIpdchargeIdEqualsAndIsFinalizedFalseAndIsActiveTrueAndIsDeletedFalse(Long ipdChargeId);

    List<TpathBs> findByPsBillIdBillNumberContains(String key);

    //MIS start
    @Query(value = "SELECT * FROM tpath_bs t INNER JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id where t.is_active = 1 and t.is_deleted = 0 and DATE_FORMAT(t.created_date,'%y-%m-%d') = CURDATE()  and tb.tbill_unit_id in (:unitid)  \n#pageable\n", countQuery = "Select count(*) from tpath_bs t INNER JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id where t.is_active = 1 and t.is_deleted = 0 and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and tb.tbill_unit_id in (:unitid)", nativeQuery = true)
    Page<TpathBs> findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("unitid") Long[] unitid, Pageable page);
    //   Page<TpathBs>findByPsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsOrPsBsIdBsServiceIdServiceSgIdSgNameContainsOrPsBsIdBsStaffIdStaffUserIdUserFirstnameContainsOrPsBsIdBsStaffIdStaffUserIdUserLastnameContainsAndPsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(String mrno, String fn, String mn, String lnm, String subgrp, String dfn, String dln, long unitid, Pageable page);

    @Query(value = "SELECT * FROM  tpath_bs  t INNER JOIN tbill_bill tb ON tb.bill_id = t. ps_bill_id where t.is_active = 1 and t.is_deleted = 0 and tb.tbill_unit_id in (:unitid) and DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from tpath_bs  t INNER JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id where t.is_active = 1 and t.is_deleted = 0 and tb.tbill_unit_id in (:unitid) and DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)", nativeQuery = true)
    Page<TpathBs> findAllByToFromCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long[] unitid, Pageable page);
    //print MIS
    // List<TpathBs> findByPsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsOrPsBsIdBsServiceIdServiceSgIdSgNameContainsOrPsBsIdBsStaffIdStaffUserIdUserFirstnameContainsOrPsBsIdBsStaffIdStaffUserIdUserLastnameContainsAndPsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(String mrno, String fn, String mn, String lnm, String subgrp, String dfn, String dln, long unitid);

    @Query(value = "SELECT * FROM tpath_bs  t INNER JOIN tbill_bill tb ON tb.bill_id = t. ps_bill_id where t.is_active = 1 and t.is_deleted = 0 and tb.tbill_unit_id in :unitid and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() ", nativeQuery = true)
    List<TpathBs> findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("unitid") Long[] unitid);

    List<TpathBs> findAllByPsBillIdTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(Long[] unitid);

    @Query(value = "SELECT * FROM tpath_bs t INNER JOIN tbill_bill tb ON tb.bill_id = t. ps_bill_id where t.is_active = 1 and t.is_deleted = 0 and tb.tbill_unit_id in:unitid and DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)", nativeQuery = true)
    List<TpathBs> findAllByToFromCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long[] unitid);

    Page<TpathBs> findAllByPsBillIdTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(Long[] unitid, Pageable page);
    //MIS end

    List<TpathBs> findAllByPsBillIdBillWorkOrderNumberAndIsActiveTrueAndIsDeletedFalse(String workorderNumber);

    //OPD filter by patient name
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill inner join psBill.billVisitId v  inner join v.visitPatientId p inner join p.patientUserId u on u.userId=p.patientUserId  where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and (u.userFirstname like %:patientName% or u.userLastname like %:patientName%) and bs.isDeleted=false and bs.isIPD=false  order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPathologyPatientName(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientName") String patientName);

    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill inner join psBill.billVisitId v  inner join v.visitPatientId p inner join p.patientUserId u on u.userId=p.patientUserId  where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and (u.userDrivingNo like %:empNo%) and bs.isDeleted=false and bs.isIPD=false  order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPathologyEmpNo(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("empNo") String empNo);

    //OPD filter by Mr no
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill inner join psBill.billVisitId v  inner join v.visitPatientId p where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and p.patientMrNo like %:patientMrno% and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPathologyMrNo(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientMrno") String patientMrno);

    //OPD filter by Mr no for user role
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill inner join psBill.billVisitId v  inner join v.visitPatientId p where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and p.patientMrNo like %:patientMrno% and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterOpdMrnoPathologySuperUsers(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientMrno") String patientMrno);

    //OPD filter by Work Order no
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill  where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and psBill.billWorkOrderNumber like %:workOrderNumber% and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPathologyWorkOrderNo(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("workOrderNumber") String patientName);

    //OPD filter by Work Order no for user role
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and psBill.billWorkOrderNumber like %:workOrderNumber% and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterOpdWorkOrderNoPathologySuperUsers(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("workOrderNumber") String workOrderNumber);

    //OPD filter by Bill no
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill  where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and psBill.billNumber like %:billNo% and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPathologyBillNo(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("billNo") String patientName);

    //OPD filter by Bill no for user role
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and psBill.billNumber like %:billNo% and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterOpdBillNoPathologySuperUsers(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("billNo") String patientName);

    //OPD filter by is final
    @Query(value = "select bs from TpathBs bs where 0=(select count(Bs) from TpathBs Bs where Bs.psBillId= bs.psBillId and Bs.isFinalized=0) and ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPathologyIsFinal(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo);

    //OPD filter by is final for user role
    @Query(value = "select bs from TpathBs bs where 0=(select count(Bs) from TpathBs Bs where Bs.psBillId= bs.psBillId and Bs.isFinalized=0) and (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterCompletedPathologySuperUsers(@Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo);
//    @Query(value = "select bs from TpathBs bs where 0=(select count(Bs) from TpathBs Bs where Bs.psBillId= bs.psBillId and Bs.isFinalized=0) and (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false", nativeQuery = false)
//    List<TpathBs> findByMultiFilterCompletedPathologySuperUsers(@Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo);

    //OPD
    @Query(value = "select bs from TpathBs bs where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPathology(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo);

    @Query(value = "select bs from TpathBs bs where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologySuperUsers(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    // to get total no of element
    @Query(value = "select bs from TpathBs bs where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPathologySuperUsersToGetTotalSize(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo);

    @Query(value = "select bs from TpathBs bs where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false group by bs.psBillId.billId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologySuperUsersNew(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    @Query(value = "select bs from TpathBs bs where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false order by psId desc", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologySuperUsersToGetTotalSize1(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    //opd patient name filter for user role
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill inner join psBill.billVisitId v  inner join v.visitPatientId p inner join p.patientUserId u on u.userId=p.patientUserId where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and (u.userFirstname like %:patientName% or u.userLastname like %:patientName%) and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterOpdPatientNamePathologySuperUsers(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientName") String patientName);

    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill inner join psBill.billVisitId v  inner join v.visitPatientId p inner join p.patientUserId u on u.userId=p.patientUserId where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and (u.userDrivingNo like %:empNo%) and bs.isDeleted=false and bs.isIPD=false order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterOpdEmpNoPathologySuperUsers(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("empNo") String empNo);

    @Query(value = "select bs from TpathBs bs where((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and bs.isFinalized=:isfinalized and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false group by bs.psBillId.billId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologyReport(@Param("performedby") String performedby, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("isfinalized") Boolean isFinalized, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    @Query(value = "select bs from TpathBs bs where((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and bs.isFinalized=:isfinalized and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false and bs.psBillId.billVisitId.visitPatientId.patientMrNo like :mrNo group by bs.psBillId.billId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologyReport(@Param("mrNo") String mrNo, @Param("performedby") String performedby, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("isfinalized") Boolean isFinalized, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    @Query(value = "select bs from TpathBs bs where(bs.isPerformedByUnitId=:isPerformedByUnitId) and bs.isFinalized=:isfinalized and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false group by bs.psBillId.billId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologyReportSuperUsers(@Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("isfinalized") Boolean isFinalized, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    @Query(value = "select bs from TpathBs bs where(bs.isPerformedByUnitId=:isPerformedByUnitId) and bs.isFinalized=:isfinalized and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=false and bs.psBillId.billVisitId.visitPatientId.patientMrNo like :mrNo group by bs.psBillId.billId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologyReportSuperUsers(@Param("mrNo") String mrNo, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("isfinalized") Boolean isFinalized, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    //IPD filter by patient name
    @Query(value = "select bs from TpathBs bs inner join bs.mbillIPDCharge as psMBill inner join psMBill.chargeAdmissionId as a  inner join a.admissionPatientId p inner join p.patientUserId u on u.userId=p.patientUserId where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and (u.userFirstname like %:patientName% or u.userLastname like %:patientName%) and bs.isDeleted=false and bs.isIPD=true order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPatientNamePathologyForIPDCharge(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientName") String patientName);

    @Query(value = "select bs from TpathBs bs inner join bs.mbillIPDCharge as psMBill inner join psMBill.chargeAdmissionId as a  inner join a.admissionPatientId p inner join p.patientUserId u on u.userId=p.patientUserId where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and (u.userFirstname like %:patientName% or u.userLastname like %:patientName%) and bs.isDeleted=false and bs.isIPD=true order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPatientNamePathologyForIPDBill(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientName") String patientName);

    //IPD filter by patient name user role
    @Query(value = "select bs from TpathBs bs inner join bs.mbillIPDCharge as psMBill inner join psMBill.chargeAdmissionId as a inner join a.admissionPatientId p inner join p.patientUserId u on u.userId=p.patientUserId where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and (u.userFirstname like %:patientName% or u.userLastname like %:patientName%) and bs.isDeleted=false and bs.isIPD=true order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPatientNameSuperUserForIPDCharge(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientName") String patientName);

    @Query(value = "select bs from TpathBs bs INNER JOIN bs.psBillId AS pBill INNER JOIN pBill.billAdmissionId AS a INNER JOIN a.admissionPatientId  p inner join p.patientUserId u on u.userId=p.patientUserId where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and (u.userFirstname like %:patientName% or u.userLastname like %:patientName%) and bs.isDeleted=false and bs.isIPD=true order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPatientNameSuperUserForIPDBill(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientName") String patientName);

    //IPD filter by  Mr no
    @Query(value = "select bs from TpathBs bs inner join bs.mbillIPDCharge as psMBill inner join psMBill.chargeAdmissionId as a  inner join a.admissionPatientId p inner join p.patientUserId u on u.userId=p.patientUserId where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and p.patientMrNo like %:patientMrno% and bs.isDeleted=false and bs.isIPD=true order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterpatientMrnoPathologyForIPDChange(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientMrno") String patientMrno);

    @Query(value = "SELECT bs FROM TpathBs bs INNER JOIN bs.psBillId AS pBill INNER JOIN pBill.billAdmissionId AS a INNER JOIN a.admissionPatientId p INNER JOIN p.patientUserId u ON u.userId=p.patientUserId WHERE ((bs.isPerformedByUnitId=:isPerformedByUnitId AND bs.isPerformedBy=:performedby) OR (bs.isTestForwardedBy=:performedby AND bs.isTestForwarded= TRUE)) AND (bs.isFinalized=:isfinalized OR bs.isFinalized= FALSE) AND (bs.createdDate BETWEEN :datefrom AND :dateto) AND p.patientMrNo LIKE %:patientMrno% AND bs.isDeleted= FALSE AND bs.isIPD= TRUE ORDER BY bs.psId DESC", nativeQuery = false)
    List<TpathBs> findByMultiFilterpatientMrnoPathologyForIPDBill(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientMrno") String patientMrno);
    //IPD filter by MR no for super user

    @Query(value = "select bs from TpathBs bs inner join bs.mbillIPDCharge as psMBill inner join psMBill.chargeAdmissionId as a  inner join a.admissionPatientId p inner join p.patientUserId u on u.userId=p.patientUserId where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and p.patientMrNo like %:patientMrno% and bs.isDeleted=false and bs.isIPD=true order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterMrNoSuperUsersForIPDCharge(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientMrno") String patientMrno);

    @Query(value = "SELECT bs FROM TpathBs bs INNER JOIN bs.psBillId AS pBill INNER JOIN pBill.billAdmissionId AS ba INNER JOIN ba.admissionPatientId ap INNER JOIN ap.patientUserId u ON u.userId=ap.patientUserId WHERE (bs.isPerformedByUnitId=:isPerformedByUnitId) AND (bs.isFinalized=:isfinalized) AND (bs.createdDate BETWEEN :datefrom AND :dateto) AND ap.patientMrNo LIKE %:patientMrno% AND bs.isDeleted= FALSE AND bs.isIPD= TRUE ORDER BY bs.psId DESC", nativeQuery = false)
    List<TpathBs> findByMultiFilterMrNoSuperUsersForIPDBill(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("patientMrno") String patientMrno);

    //IPD filter by Work Order no
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and psBill.billWorkOrderNumber like %:workOrderNumber% and bs.isDeleted=false and bs.isIPD=true group by bs.mbillIPDCharge.ipdchargeId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterWorkOrderNoPathologyForIPD(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("workOrderNumber") String workOrderNumber, Pageable page);

    //IPD filter by Work Order no for Super User
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and psBill.billWorkOrderNumber like %:workOrderNumber% and bs.isDeleted=false and bs.isIPD=true group by bs.mbillIPDCharge.ipdchargeId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterWorkOrderNoSuperUsersForIPD(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("workOrderNumber") String workOrderNumber, Pageable page);

    //IPD filter by Bill no
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and psBill.billNumber like %:billNo% and bs.isDeleted=false and bs.isIPD=true group by bs.mbillIPDCharge.ipdchargeId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterBillNoPathologyForIPD(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("billNo") String billNo, Pageable page);

    //IPD filter by Bill no
    @Query(value = "select bs from TpathBs bs inner join bs.psBillId as psBill where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and psBill.billNumber like %:billNo% and bs.isDeleted=false and bs.isIPD=true group by bs.mbillIPDCharge.ipdchargeId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterBillNoSuperUsersForIPD(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("billNo") String billNo, Pageable page);

    //IPD filter by is final
    @Query(value = "select bs from TpathBs bs where 0=(select count(Bs) from TpathBs Bs where Bs.mbillIPDCharge= bs.mbillIPDCharge and Bs.isFinalized=0) and ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=true order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterIsFinalPathologyForIPD(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo);

    //IPD filter byis final Super User
    @Query(value = "select bs from TpathBs bs where 0=(select count(Bs) from TpathBs Bs where Bs.mbillIPDCharge= bs.mbillIPDCharge and Bs.isFinalized=0) and (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=true order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterFinalSuperUserForIPD(@Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, @Param("isfinalized") Boolean isfinalized);

    //IPD
    @Query(value = "select bs from TpathBs bs where ((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=true order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPathologyForIPD(@Param("performedby") String performedBy, @Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo);

    @Query(value = "select bs from TpathBs bs where (bs.isPerformedByUnitId=:isPerformedByUnitId) and (bs.isFinalized=:isfinalized or bs.isFinalized=false) and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=true order by bs.psId desc", nativeQuery = false)
    List<TpathBs> findByMultiFilterPathologySuperUsersForIPD(@Param("isfinalized") Boolean isFinalized, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo);

    @Query(value = "select bs from TpathBs bs where((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and bs.isFinalized=:isfinalized and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=true group by bs.mbillIPDCharge.ipdchargeId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologyReportForIPD(@Param("performedby") String performedby, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("isfinalized") Boolean isFinalized, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    @Query(value = "select bs from TpathBs bs where((bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isPerformedBy=:performedby) or (bs.isTestForwardedBy=:performedby and bs.isTestForwarded=true)) and bs.isFinalized=:isfinalized and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=true and bs.mbillIPDCharge.chargeAdmissionId.admissionPatientId.patientMrNo like :mrNo group by bs.mbillIPDCharge.ipdchargeId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologyReportForIPD(@Param("mrNo") String mrNo, @Param("performedby") String performedby, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("isfinalized") Boolean isFinalized, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    @Query(value = "select bs from TpathBs bs where(bs.isPerformedByUnitId=:isPerformedByUnitId) and bs.isFinalized=:isfinalized and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=true group by bs.psBillId.billId,bs.mbillIPDCharge.ipdchargeId order by bs.lastModifiedDate DESC", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologyReportSuperUsersForIPD(@Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("isfinalized") Boolean isFinalized, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    @Query(value = "select bs from TpathBs bs where(bs.isPerformedByUnitId=:isPerformedByUnitId) and bs.isFinalized=:isfinalized and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isIPD=true and bs.mbillIPDCharge.chargeAdmissionId.admissionPatientId.patientMrNo like :mrNo group by bs.mbillIPDCharge.ipdchargeId", nativeQuery = false)
    Page<TpathBs> findByMultiFilterPathologyReportSuperUsersForIPD(@Param("mrNo") String mrNo, @Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("isfinalized") Boolean isFinalized, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    //EMR dignosis view
    Page<TpathBs> findAllByPsBillIdBillVisitIdVisitPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long patientId, Pageable page);

    @Query(value = "select count(bs) from TpathBs bs where bs.isPerformedByUnitId=:isPerformedByUnitId and bs.isFinalized=:isfinalized and bs.psTestId.mbillServiceId.serviceSgId.sgId=:subgroup and (bs.createdDate BETWEEN :datefrom and :dateto) and bs.isDeleted=false and bs.isActive=true", nativeQuery = false)
    int findByPendingReportCount(@Param("isPerformedByUnitId") String isPerformedByUnitId, @Param("isfinalized") Boolean isFinalized, @Param("subgroup") long subGroup, @Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo);

    @Query(value = "SELECT bs.* FROM tpath_bs bs inner join tbill_bill tb on tb.bill_id=bs.ps_bill_id inner join mst_visit mv on mv.visit_id=tb.bill_visit_id inner join mst_patient mp on mp.patient_id = mv.visit_patient_id where mv.visit_patient_id = :patientId", nativeQuery = true)
    List<TpathBs> findByPatientId(@Param("patientId") Long patientId);

    @Query(value = "SELECT bs.* FROM tpath_bs bs inner join mbill_ipd_charge mic  on mic.ipdcharge_id=bs.mbillipdcharge inner join trn_admission ta on ta.admission_id=mic.charge_admission_id where mic.charge_admission_id= :admissionId", nativeQuery = true)
    List<TpathBs> findByAdmissionId(@Param("admissionId") Long admissionId);

    @Query(value = "select count(t.ps_id) from tpath_bs t left join mpath_test mt on t.ps_test_id = mt.test_id left join mbill_service ms on mt.m_bill_service_id = ms.service_id left join mbill_sub_group msg on ms.service_sg_id = msg.sg_id left join mbill_group mg on msg.sg_group_id = mg.group_id left join mst_unit u on t.is_performed_by_unit_id = u.unit_id where msg.sg_name = :subGroupName and is_finalized = 0 and t.is_active = 1 and t.is_deleted = 0 and t.is_performed_by_unit_id=:isPerformedByUnitId", nativeQuery = true)
    int findByPendingTestReportCount(@Param("subGroupName") String subGroupName, @Param("isPerformedByUnitId") long isPerformedByUnitId);

    TpathBs findByIsActiveTrueAndIsDeletedFalseAndPsBillIdBillIdEqualsAndPsTestIdMbillServiceIdServiceIdEquals(Long billId, Long seiviceId);

    TpathBs findByIsActiveTrueAndIsDeletedFalseAndMbillIPDChargeIpdchargeIdEqualsAndPsCsIdCsIdEquals(Long billId, Long csId);
}
