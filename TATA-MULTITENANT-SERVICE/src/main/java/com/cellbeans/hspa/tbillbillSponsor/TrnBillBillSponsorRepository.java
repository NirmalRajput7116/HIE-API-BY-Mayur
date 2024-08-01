package com.cellbeans.hspa.tbillbillSponsor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrnBillBillSponsorRepository extends JpaRepository<TrnBillBillSponsor, Long> {

    Page<TrnBillBillSponsor> findAllByBbsBillIdIpdBillTrueAndIsDeletedFalse(Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsSCIdScCompanyIdCompanyNameContainsAndBbsBillIdIpdBillTrueAndIsDeletedFalse(String com, Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsBillIdBillNumberContainsAndBbsBillIdIpdBillTrueAndIsDeletedFalse(String com, Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsBillIdBillAdmissionIdAdmissionPatientIdPatientMrNoContainsAndBbsBillIdIpdBillTrueAndIsDeletedFalse(String com, Pageable page);

    List<TrnBillBillSponsor> findByBbsBillIdBillId(Long billId);

    Page<TrnBillBillSponsor> findAllByBbsBillIdIpdBillFalseAndBbsBillIdEmrbillFalseAndIsDeletedFalse(Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsBillIdIpdBillFalseAndBbsBillIdEmrbillTrueAndIsDeletedFalse(Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsSCIdScCompanyIdCompanyNameContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillFalseAndIsDeletedFalse(String comp, Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsSCIdScCompanyIdCompanyNameContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillTrueAndIsDeletedFalse(String comp, Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsBillIdBillNumberContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillFalseAndIsDeletedFalse(String comp, Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsBillIdBillNumberContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillTrueAndIsDeletedFalse(String comp, Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillFalseAndIsDeletedFalse(String comp, Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillTrueAndIsDeletedFalse(String comp, Pageable page);

    @Query(value = "SELECT ts from TrnBillBillSponsor ts where ts.bbsBillId.isSettle=true and ts.isDeleted=false and ts.bbsBillId.ipdBill=false")
    Page<TrnBillBillSponsor> findAllByBbsBillIdANDBbsBillIdBillIpdBillFalseAndIsDeletedFalse(Pageable page);

    @Query(value = "SELECT ts from TrnBillBillSponsor ts where ts.bbsBillId.isSettle=true and ts.isDeleted=false and ts.bbsBillId.ipdBill=true ")
    Page<TrnBillBillSponsor> findAllByBbsBillIdBillCompanyOutStandingANDBbsBillIdBillIpdBillTrueAndIsDeletedFalse(Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsSCIdScCompanyIdCompanyIdAndBbsBillIdIpdBillFalseAndIsDeletedFalse(Long companyId, Pageable page);

    Page<TrnBillBillSponsor> findAllByBbsSCIdScCompanyIdCompanyIdAndBbsBillIdIpdBillTrueAndIsDeletedFalse(Long companyId, Pageable page);

    List<TrnBillBillSponsor> findByBbsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long billId);

    @Query(value = "select tbbs.bbs_advanced_amount from trn_bill_bill_sponsor as tbbs where bbs_bill_id =:billId", nativeQuery = true)
    List<Number> getBbsAdvancedAmountByBbsBillIdAndIsActiveTrueAndIsDeletedFalse(@Param("billId") Long billId);

    @Query(value = "select tbbs.bbs_agst_amount from trn_bill_bill_sponsor as tbbs where bbs_bill_id =:billId", nativeQuery = true)
    List<Number> getBbsAgstAmountByBbsBillIdAndIsActiveTrueAndIsDeletedFalse(@Param("billId") Long billId);

    @Query(value = "select tbbs.bbs_remaing_amount from trn_bill_bill_sponsor as tbbs where bbs_bill_id =:billId", nativeQuery = true)
    List<Number> getBbsRemaingAmountBbsBillIdAndIsActiveTrueAndIsDeletedFalse(@Param("billId") Long billId);

    default double getTotalCompanyPayable(Long billId) {
        double totalCompany = 0;
        List<Number> listCompanyPayable = getBbsAdvancedAmountByBbsBillIdAndIsActiveTrueAndIsDeletedFalse(billId);
        for (int i = 0; i < listCompanyPayable.size(); i++) {
            totalCompany = totalCompany + listCompanyPayable.get(i).doubleValue();
        }
        return totalCompany;
    }

    default double getTotalCompanyPaid(Long billId) {
        double totalCompanyPaid = 0;
        List<Number> listCompanyPaid = getBbsAgstAmountByBbsBillIdAndIsActiveTrueAndIsDeletedFalse(billId);
        for (Number compPaid : listCompanyPaid) {
            totalCompanyPaid = totalCompanyPaid + compPaid.doubleValue();
        }
        return totalCompanyPaid;
    }

    default double getTotalCompanyOutstanding(Long billId) {
        double totalCompanyOut = 0;
        List<Number> listCompanyOut = getBbsRemaingAmountBbsBillIdAndIsActiveTrueAndIsDeletedFalse(billId);
        for (Number compOut : listCompanyOut) {
            totalCompanyOut = totalCompanyOut + compOut.doubleValue();
        }
        return totalCompanyOut;
    }

}