package com.cellbeans.hspa.mpathtestresult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MpathTestResultRepository extends JpaRepository<MpathTestResult, Long> {

    Page<MpathTestResult> findByTrPsIdPsTestIdTestIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
    // Page<MpathTestResult> findByTrBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrTrBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(String name1,String name2, Pageable page);

    Page<MpathTestResult> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathTestResult> findByTrPsIdPsTestIdTestIdContains(String key);

    List<MpathTestResult> findByTrPsIdPsIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByTrIdDesc(Long psId);

    Page<MpathTestResult> findByTrPsIdPsBillIdBillVisitIdVisitId(Long visitId, Pageable page);

    MpathTestResult findAllByTrPsIdPsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long billId);

    @Query(value = "select tr from MpathTestResult tr where tr.trPsId.psBillId.billId =:billId and tr.trPsId.isIPD=false and tr.isDeleted=false and tr.isActive=true group by tr.trPsId.psId", nativeQuery = false)
    List<MpathTestResult> findAllOPDReport(@Param("billId") long billId);

    @Query(value = "select tr from MpathTestResult tr where tr.trPsId.mbillIPDCharge.ipdchargeId =:chargeId or tr.trPsId.psBillId.billId =:billId and tr.trPsId.isIPD=true and tr.isDeleted=false and tr.isActive=true group by tr.trPsId.psId", nativeQuery = false)
    List<MpathTestResult> findAllIPDReport(@Param("chargeId") long chargeId, @Param("billId") long billId);

    MpathTestResult findTopByTrPsIdPsIdEqualsAndIsActiveTrueAndIsDeletedFalseAndTrPsIdIsFinalizedTrueOrderByTrIdDesc(Long psId);

}
            
