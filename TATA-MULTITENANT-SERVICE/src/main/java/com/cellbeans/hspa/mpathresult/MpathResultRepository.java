package com.cellbeans.hspa.mpathresult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MpathResultRepository extends JpaRepository<MpathResult, Long> {

    Page<MpathResult> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathResult> findByResultTestResultIdTrPsIdPsBillIdBillIdEqualsAndResultTestResultIdTrPsIdPsTestIdTestIdEquals(Long billId, Long testId);

    List<MpathResult> findByResultTestResultIdTrPsIdPsBillIdBillIdAndResultTestResultIdTrPsIdPsTestIdTestIdEquals(Long billId, Long testId);

    List<MpathResult> findByResultTestResultIdTrIdAndIsActiveTrueAndIsDeletedFalse(Long trId);

    @Query(value = "SELECT * FROM mpath_result where result_test_result_id=:trid and is_active = 1 and is_deleted = 0 order by abs(result_param_sequence) asc", nativeQuery = true)
    List<MpathResult> findTestResultByTrId(@Param("trid") long trid);

    @Query(value = "SELECT mr.* FROM mpath_result mr inner join mpath_test_result mtr on mtr.tr_id=mr.result_test_result_id inner join tpath_bs bs on bs.ps_id=mtr.tr_ps_id \n" +
            "inner join tbill_bill tb on tb.bill_id=bs.ps_bill_id inner join mst_visit mv on mv.visit_id=tb.bill_visit_id inner join mst_patient mp on mp.patient_id = mv.visit_patient_id \n" +
            "where mv.visit_patient_id = :id ", nativeQuery = true)
    List<MpathResult> findOPDTestResultbyPatientId(@Param("id") long id);

    List<MpathResult> findAllByResultTestResultIdTrPsIdPsIdAndIsActiveTrueAndIsDeletedFalse(long psId);

    List<MpathResult> findByResultTestResultIdTrPsIdPsIdAndIsActiveTrueAndIsDeletedFalseOrderByResultTestResultIdDesc(long psId);

    List<MpathResult> findByResultTestResultIdTrPsIdPsIdAndIsActiveTrueAndIsDeletedFalse(long psId);

    //EMR
    List<MpathResult> findOneByResultTestResultIdTrPsIdPsIdAndIsActiveTrueAndIsDeletedFalse(Long psId);

    List<MpathResult> findOneByResultTestResultIdTrPsIdPsIdAndIsActiveTrueAndIsDeletedFalseOrderByResultTestResultIdDesc(Long psId);
//    List<MpathResult> findByResultTestResultIdTrPsIdPsBillIdBillAd(Long id);

    @Query(value = "SELECT * FROM mpath_result mr inner join mpath_test_result mtr on mtr.tr_id=mr.result_test_result_id inner join tpath_bs bs on bs.ps_id=mtr.tr_ps_id \n" +
            "inner join mbill_ipd_charge mic on mic.ipdcharge_id = bs.mbillipdcharge inner join trn_admission ta on ta.admission_id = mic.charge_admission_id\n" +
            "where mic.charge_admission_id = :id ", nativeQuery = true)
    List<MpathResult> findIPDTestResultbyPatientId(@Param("id") long id);

}
            
