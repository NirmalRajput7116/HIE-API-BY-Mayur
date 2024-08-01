package com.cellbeans.hspa.tbillbill;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.tbillreciept.TbillReciept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public interface TbillBillRepository extends JpaRepository<TBillBill, Long> {

    TBillBill findByBillId(Long billId);
//	Page<TBillBill> findAllByIsDeletedFalseAndIsActiveTrueAAndIsCancelledFalse(Pageable page);

    Page<TBillBill> findByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            String billNumber, String mrNo, Pageable page);
//    Page<TBillBill> findByBillNumberContainsAndTbillUnitIdUnitIdOrBillVisitIdVisitPatientIdPatientMrNoContainsAndTbillUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
//            String billNumber, String mrNo, long unitId, Pageable page);

    Page<TBillBill> findAllByIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.ipdBill, t.billVisitId.visitPatientId.patientMrNo, t.billVisitId.visitPatientId.patientUserId.userTitleId.titleName, t.billVisitId.visitPatientId.patientUserId.userFirstname, t.billVisitId.visitPatientId.patientUserId.userMiddlename, t.billVisitId.visitPatientId.patientUserId.userLastname, t.billOpdNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId,t.refundAmount,t.companyNetPay,t.grossAmount,t.isApprove,t.isRejected) from  TBillBill t where  t.tbillUnitId.unitId = :unitId And t.isActive=true and t.emrbill=false and t.ipdBill=false AND t.isDeleted = false AND t.isCancelled = false", nativeQuery = false)
    Page<TBillBillDto> findAllByTbillUnitIdUnitIdAndBillDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("unitId") long unitId, Pageable page);
//	@Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.ipdBill, t.billVisitId.visitPatientId.patientMrNo, t.billVisitId.visitPatientId.patientUserId.userTitleId.titleName, t.billVisitId.visitPatientId.patientUserId.userFirstname, t.billVisitId.visitPatientId.patientUserId.userMiddlename, t.billVisitId.visitPatientId.patientUserId.userLastname, t.billOpdNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId,t.refundAmount,t.companyNetPay,t.grossAmount,t.isApprove,t.isRejected, t.freezed, t.isCancelled) from  TBillBill t where  t.tbillUnitId.unitId = :unitId And t.isActive=true and t.emrbill=false and t.ipdBill=false AND t.isDeleted = false AND t.isCancelled = false", nativeQuery = false)
//	Page<TBillBillDto> findAllByTbillUnitIdUnitIdAndBillDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalseAndFreezed(
//			@Param("unitId") long unitId, Pageable page);


	/*@Query(value = "SELECT tb.bill_id,tb.bill_number FROM tbill_bill tb LEFT JOIN trn_bill_bill_sponsor ts  on ts.bbs_bill_id=tb.bill_id AND tb.tbill_unit_id=:unitId AND tb.is_active=TRUE AND tb.emrbill=FALSE AND tb.ipd_bill=FALSE AND tb.is_cancelled=false",nativeQuery = true)
	Page<TBillBillDto> findAllByTbillUnitIdUnitIdAndBillDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
			@Param("unitId") long unitId, Pageable page);*/

    @Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.ipdBill, t.billAdmissionId.admissionPatientId.patientMrNo, t.billAdmissionId.admissionPatientId.patientUserId.userTitleId.titleName,t.billAdmissionId.admissionPatientId.patientUserId.userFirstname, t.billAdmissionId.admissionPatientId.patientUserId.userMiddlename, t.billAdmissionId.admissionPatientId.patientUserId.userLastname, t.billIpdNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId,t.billAdmissionId.admissionIpdNo,t.refundAmount,t.companyNetPay,t.grossAmount, t.isApprove,t.isRejected) from TBillBill t where t.ipdBill=true and t.emrbill=false and t.tbillUnitId.unitId = :unitId And t.isActive=true AND t.isDeleted = false AND t.isCancelled = false")
    Page<TBillBillDto> findAllByTbillUnitIdUnitIdIPDAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("unitId") long unitId, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.ipdBill, t.billAdmissionId.admissionPatientId.patientMrNo, t.billAdmissionId.admissionPatientId.patientUserId.userTitleId.titleName,t.billAdmissionId.admissionPatientId.patientUserId.userFirstname, t.billAdmissionId.admissionPatientId.patientUserId.userMiddlename, t.billAdmissionId.admissionPatientId.patientUserId.userLastname, t.billIpdNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId,t.billAdmissionId.admissionIpdNo,t.refundAmount,t.companyNetPay,t.grossAmount, t.isApprove,t.isRejected, t.freezed, t.isCancelled) from TBillBill t where t.ipdBill=true and t.emrbill=false and t.tbillUnitId.unitId = :unitId And t.isActive=true AND t.isDeleted = false AND t.isCancelled = false")
    Page<TBillBillDto> findAllByTbillUnitIdUnitIdIPDAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalseAAndFreezedIPD(
            @Param("unitId") long unitId, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.emrbill, t.billVisitId.visitPatientId.patientMrNo,t.billVisitId.visitPatientId.patientUserId.userTitleId.titleName, t.billVisitId.visitPatientId.patientUserId.userFirstname, t.billVisitId.visitPatientId.patientUserId.userMiddlename, t.billVisitId.visitPatientId.patientUserId.userLastname, t.billEmrNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId,t.companyNetPay,t.grossAmount, t.isApprove,t.isRejected) from TBillBill t where t.emrbill=true and t.tbillUnitId.unitId = :unitId And t.isActive=true AND t.isDeleted = false AND t.isCancelled = false")
    Page<TBillBillDto> findAllByTbillUnitIdUnitIdEMRAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("unitId") long unitId, Pageable page);

    TBillBill findTopByIpdBillFalseOrderByBillIdDesc();

    TBillBill findTopByIpdBillTrueOrderByBillIdDesc();

    List<TBillBill> findByBillNumberContains(String key);

    TBillBill findTopByOrderByBillIdDesc();

    // by chandrakant for cancel bill list 10.10.2019
    List<TBillBill> findByIsActiveTrueAndIsCancelledTrueAndIsDeletedFalse();

    Page<TBillBill> findByBillVisitIdVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(String mrNumber,
                                                                                                       Pageable page);

    // Priyanka : Author
    @Query(value = "Select * from tbill_bill tb where tb.bill_date = :date \n#pageable\n", countQuery = "Select count(*) from tbill_bill tb where tb.bill_date = :date", nativeQuery = true)
    Page<TBillBill> findAllByDateAAndIsActiveTrueAndIsDeletedFalse(@Param("date") String date, Pageable page);

    default String makeBillNumber(Boolean isIpd) {
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        TBillBill lastBill = findTopByOrderByBillIdDesc();
        StringBuilder billNumber = new StringBuilder("OPD/");
        if (!isIpd) {
            billNumber = new StringBuilder("OPD/");
        } else {
            billNumber = new StringBuilder("IPD/");
        }
        try {
            if (lastBill.getBillWorkOrderNumber() != null) {
                String number = lastBill.getBillNumber().substring(16);
                int newNumber = Integer.parseInt(number);
                String incNumber = String.format("%08d", newNumber + 1);
                billNumber.append(dateFormat.format(todayDate)).append("/" + incNumber);
            } else {
                billNumber.append(dateFormat.format(todayDate)).append("/00000001");
            }
        } catch (Exception e) {
            billNumber.append(dateFormat.format(todayDate)).append("/00000001");
            // System.out.println("Null Pointer Exception on Line Number: 60");
        }
        return billNumber.toString();
    }

    default String makeOPDNumber(Boolean isIpd) {
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        StringBuilder opdNumber = new StringBuilder("OPD/");
        if (!isIpd) {
            try {
                TBillBill lastBill = findTopByIpdBillFalseOrderByBillIdDesc();
                if (lastBill.getBillOpdNumber() != null) {
                    String number = lastBill.getBillOpdNumber().substring(16);
                    int newNumber = Integer.parseInt(number);
                    String incNumber = String.format("%08d", newNumber + 1);
                    opdNumber.append(dateFormat.format(todayDate)).append("/" + incNumber);
                } else {
                    opdNumber.append(dateFormat.format(todayDate)).append("/00000001");
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
                opdNumber.append(dateFormat.format(todayDate)).append("/00000001");
            }
        } else {
            try {
                TBillBill lastBill = findTopByIpdBillTrueOrderByBillIdDesc();
                opdNumber = new StringBuilder("IPD/");
                if (lastBill.getBillIpdNumber() != null) {
                    String number = lastBill.getBillIpdNumber().substring(16);
                    int newNumber = Integer.parseInt(number);
                    String incNumber = String.format("%08d", newNumber + 1);
                    opdNumber.append(dateFormat.format(todayDate)).append("/" + incNumber);
                } else {
                    opdNumber.append(dateFormat.format(todayDate)).append("/00000001");
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
                opdNumber.append(dateFormat.format(todayDate)).append("/00000001");
            }
        }
        return opdNumber.toString();
    }

    Page<TBillBill> findAllByBillVisitIdVisitSubDepartmentIdSdDepartmentIdDepartmentNameEqualsAndIsActiveTrueAndIsDeletedFalse(
            String department, Pageable page);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tbill_bill tbill SET tbill.is_cancelled =:cancelled WHERE tbill.bill_id =:billId", nativeQuery = true)
    int updateBillForCancellation(@Param("billId") Long billId, @Param("cancelled") Boolean cancelled);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tbill_bill tbill SET tbill.bill_company_out_standing =:bill_company_out_standing,comapny_discount_percentage=:comapny_discount_percentage,company_discount_amount=:company_discount_amount,credit_note_percentage=:credit_note_percentage,credit_note_amount=:credit_note_amount,company_paid_amount=:company_paid_amount,tbill.remark=:remark,tbill.is_settle=:issettle WHERE tbill.bill_id =:billId", nativeQuery = true)
    int updateCompanyBill(@Param("billId") Long billId, @Param("bill_company_out_standing") Double bill_company_out_standing, @Param("comapny_discount_percentage") Double comapny_discount_percentage, @Param("company_discount_amount") Double company_discount_amount, @Param("credit_note_percentage") Double credit_note_percentage, @Param("credit_note_amount") Double credit_note_amount, @Param("company_paid_amount") Double company_paid_amount, @Param("remark") String remark, @Param("issettle") Boolean issettle);

    @Query(value = "SELECT bill from TBillBill bill where bill.billDate =:date")
    List<TBillBill> findByBillDate(@Param("date") @Temporal(TemporalType.DATE) Date date);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tbill_bill tbill SET tbill.bill_outstanding = :billOutstanding, tbill.bill_nill = :billNill, tbill.bill_amount_paid = :billAmountPaid AND tbill.bill_amount_tobe_paid =:billAmountTobePaid where tbill.bill_id = :billId", nativeQuery = true)
    int updateBillForOustanding(@Param("billOutstanding") double billOutstanding,
                                @Param("billAmountPaid") double billAmountPaid, @Param("billId") Long billId,
                                @Param("billNill") Boolean billNill, @Param("billAmountTobePaid") double billAmountTobePaid);

    // Author: MOHIT
    default Boolean updateBillOutstaindingMethod(Long billId, double billOutstanding, double billAmountPaid,
                                                 double billAmountTobePaid, String billNarration, Boolean finalBill) {
        Boolean billNill = false;
        if (billOutstanding == 0) {
            billNill = true;
        } else {
            billNill = false;
        }
        TBillBill tBillBill = getById(billId);
        tBillBill.setBillOutstanding(billOutstanding);
        tBillBill.setBillAmountPaid(billAmountPaid);
        tBillBill.setBillAmountTobePaid(billAmountTobePaid);
        tBillBill.setBillNill(billNill);
        tBillBill.setBillNarration(billNarration);
        tBillBill.setFinalBill(finalBill);
        save(tBillBill);
        return true;
        /*
         * int i = updateBillForOustanding(billOutstanding, billAmountPaid,
         * billId, billNill, billAmountTobePaid);
         *
         * if(i > 0) { return true; } else { return false; }
         */
    }

    // Author: MOHIT
    default Boolean updateBillOutstainding(Long billId, double billOutstanding, double billAmountPaid,
                                           double billAmountTobePaid, double billNetPayable, double billSubtotal, String billNarration,
                                           Boolean finalBill) {
        Boolean billNill = false;
        if (billOutstanding == 0) {
            billNill = true;
        } else {
            billNill = false;
        }
        TBillBill tBillBill = getById(billId);
        tBillBill.setBillSubTotal(billSubtotal);
        tBillBill.setBillNetPayable(billNetPayable);
        tBillBill.setBillOutstanding(billOutstanding);
        tBillBill.setBillAmountPaid(billAmountPaid);
        tBillBill.setBillAmountTobePaid(billAmountTobePaid);
        tBillBill.setBillNill(billNill);
        tBillBill.setBillNarration(billNarration);
        tBillBill.setFinalBill(finalBill);
        save(tBillBill);
        return true;
        /*
         * int i = updateBillForOustanding(billOutstanding, billAmountPaid,
         * billId, billNill, billAmountTobePaid);
         *
         * if(i > 0) { return true; } else { return false; }
         */
    }

    default Boolean updateBillOutstaindingMethod(Long billId, double billOutstanding, double billAmountPaid,
                                                 double billAmountTobePaid, String billNarration, Boolean finalBill, double billNetPayable,
                                                 double billSubtotal, double billDiscountPercentage, double billDiscountAmount,
                                                 double commonDiscountStaffId) {
        Boolean billNill = false;
        if (billOutstanding == 0) {
            billNill = true;
        } else {
            billNill = false;
        }
        TBillBill tBillBill = getById(billId);
        tBillBill.setBillOutstanding(billOutstanding);
        tBillBill.setBillAmountPaid(billAmountPaid);
        tBillBill.setBillSubTotal(billSubtotal);
        tBillBill.setBillAmountTobePaid(billAmountTobePaid);
        tBillBill.setBillNill(billNill);
        tBillBill.setBillNarration(billNarration);
        tBillBill.setFinalBill(finalBill);
        tBillBill.setBillNetPayable(billNetPayable);
        tBillBill.setBillDiscountPercentage(billDiscountPercentage);
        tBillBill.setBillDiscountAmount(billDiscountAmount);
        MstStaff staff = new MstStaff();
        staff.setStaffId((int) commonDiscountStaffId);
        tBillBill.setCommonDiscountStaffId(staff);
        save(tBillBill);
        return true;

    }

    // Author MOHIT
    @Modifying(clearAutomatically = true)
    @Query(value = "update TBillBill bill SET bill.billNetPayable =:billNetPayable, bill.billSubTotal =:billSubTotal, bill.billAmountPaid =:billAmountPaid, bill.billOutstanding =:billOutstanding, bill.billDiscountAmount =:billDiscountAmount, bill.billAmountTobePaid =:billAmountTobePaid, bill.billUserId =:billUserId WHERE bill.billId =:billId")
    int updateIpdBill(@Param("billId") Long billId, @Param("billNetPayable") double billNetPayable,
                      @Param("billSubTotal") double billSubTotal, @Param("billAmountPaid") double billAmountPaid,
                      @Param("billOutstanding") double billOutstanding, @Param("billDiscountAmount") double billDiscountAmount,
                      @Param("billAmountTobePaid") double billAmountTobePaid, @Param("billUserId") MstStaff billUserId);

    // Author: Mohit
    TBillBill findByBillIdAndIsActiveTrueAndIsDeletedFalse(Long billId);

    // Authoe: Neha
    List<TBillBill> findByBillVisitIdVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(long visitId);

    // Author: Mohit
    // For IPD Bill False;
    List<TBillBill> findByBillNumberContainsAndIpdBillTrueAndFinalBillFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIpdBillTrueAndFinalBillFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserLastnameContainsAndIpdBillTrueAndFinalBillFalseOrBillAdmissionIdAdmissionPatientIdPatientMrNoContainsAndFinalBillFalseAndIpdBillTrue(
            String billNumber, String firstName, String lastName, String mrNumber);

    // Author: Mohit
    // For IPD Bill
    List<TBillBill> findByFinalBillFalseAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrue();

    List<TBillBill> findByFinalBillFalseAndIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrue();

    @Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.ipdBill, t.billAdmissionId.admissionPatientId.patientMrNo,t.billAdmissionId.admissionPatientId.patientUserId.userTitleId.titleName, t.billAdmissionId.admissionPatientId.patientUserId.userFirstname, t.billAdmissionId.admissionPatientId.patientUserId.userMiddlename, t.billAdmissionId.admissionPatientId.patientUserId.userLastname, t.billIpdNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId,t.billAdmissionId.admissionIpdNo, t.refundAmount,t.companyNetPay,t.grossAmount) from TBillBill t where t.tbillUnitId.unitId = :unitid And t.isActive=true AND t.isDeleted = false and t.billAdmissionId.admissionPatientId.patientId = :patientid and t.ipdBill =true")
    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
            @Param("patientid") long patientid, @Param("unitid") long unitid);

    @Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.ipdBill, t.billAdmissionId.admissionPatientId.patientMrNo,t.billAdmissionId.admissionPatientId.patientUserId.userTitleId.titleName, t.billAdmissionId.admissionPatientId.patientUserId.userFirstname, t.billAdmissionId.admissionPatientId.patientUserId.userMiddlename, t.billAdmissionId.admissionPatientId.patientUserId.userLastname, t.billIpdNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId,t.billAdmissionId.admissionIpdNo, t.refundAmount,t.companyNetPay, t.isCancelled,t.grossAmount) from TBillBill t where t.tbillUnitId.unitId = :unitid And t.isActive=true AND t.isDeleted = false and t.billAdmissionId.admissionPatientId.patientId = :patientid and t.ipdBill =true")
    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDescNew(
            @Param("patientid") long patientid, @Param("unitid") long unitid);

    @Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.ipdBill, t.billAdmissionId.admissionPatientId.patientMrNo,t.billAdmissionId.admissionPatientId.patientUserId.userTitleId.titleName, t.billAdmissionId.admissionPatientId.patientUserId.userFirstname, t.billAdmissionId.admissionPatientId.patientUserId.userMiddlename, t.billAdmissionId.admissionPatientId.patientUserId.userLastname, t.billIpdNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId,t.billAdmissionId.admissionIpdNo, t.refundAmount,t.companyNetPay,t.grossAmount) from TBillBill t where t.tbillUnitId.unitId = :unitid And t.isActive=true AND t.isDeleted = false and t.isCancelled=true and t.billAdmissionId.admissionPatientId.patientId = :patientid and t.ipdBill =true")
    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientIdEqualsAndIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
            @Param("patientid") long patientid, @Param("unitid") long unitid);

    @Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.ipdBill, t.billVisitId.visitPatientId.patientMrNo,t.billVisitId.visitPatientId.patientUserId.userTitleId.titleName, t.billVisitId.visitPatientId.patientUserId.userFirstname, t.billVisitId.visitPatientId.patientUserId.userMiddlename, t.billVisitId.visitPatientId.patientUserId.userLastname, t.billOpdNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId,t.billAdmissionId.admissionIpdNo,t.isCancelled,t.companyNetPay,t.grossAmount) from TBillBill t where t.tbillUnitId.unitId = :unitid And t.isActive=true AND t.isDeleted = false and t.billVisitId.visitPatientId.patientMrNo = :mrno and t.ipdBill =false")
    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndIpdBillFalseOrderByBillIdDesc(
            @Param("mrno") String mrno, @Param("unitid") long unitid);

    @Query(value = "SELECT * from tbill_bill t INNER JOIN mst_visit mv where t.tbill_unit_id =:unitid And t.is_active=true AND t.is_deleted = false and  t.ipd_bill=FALSE AND mv.visit_id=t.bill_visit_id AND mv.visit_patient_id=:patientId", nativeQuery = true)
    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdAndIsActiveTrueAndIsDeletedFalseAndIpdBillFalseOrderByBillIdDesc(
            @Param("patientId") Long patientId, @Param("unitid") long unitid);

    @Query(value = "SELECT * from tbill_bill t INNER JOIN mst_visit mv where t.tbill_unit_id =:unitid And t.is_active=true AND t.is_deleted = false and t.is_cancelled =true and t.ipd_bill=FALSE AND mv.visit_id=t.bill_visit_id AND mv.visit_patient_id=:patientId", nativeQuery = true)
    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdAndIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndIpdBillFalseOrderByBillIdDesc(
            @Param("patientId") Long patientId, @Param("unitid") long unitid);

    List<TBillBill> findByIsActiveTrueAndIsDeletedFalseAndIpdBillFalse();

    List<TBillBill> findByIsActiveTrueAndIsCancelledTrueAndIsDeletedFalseAndIpdBillFalse();

    @Query(value = "SELECT new com.cellbeans.hspa.tbillbill.TBillBillDto(t.billId, t.billNumber, t.createdDate, t.ipdBill, t.billAdmissionId.admissionPatientId.patientMrNo,t.billAdmissionId.admissionPatientId.patientUserId.userTitleId.titleName,t.billAdmissionId.admissionPatientId.patientUserId.userFirstname, t.billAdmissionId.admissionPatientId.patientUserId.userMiddlename, t.billAdmissionId.admissionPatientId.patientUserId.userLastname, t.billIpdNumber, t.billTotCoPay, t.billTotCoPayOutstanding, t.finalBill, t.billSubTotal, t.billNetPayable, t.billAmountPaid, t.billOutstanding, t.billNill, t.billTariffId.tariffId, t.isCancelled, t.billDiscountAmount,t.companyNetPay,t.grossAmount) from TBillBill t where t.tbillUnitId.unitId = :unitid And t.isActive=true AND t.isDeleted = false and t.billAdmissionId.admissionId = :admissionid and t.ipdBill =true")
    List<TBillBill> findByBillAdmissionIdAdmissionIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
            @Param("admissionid") long admissionid, @Param("unitid") long unitid);

    List<TBillBill> findByBillAdmissionIdAdmissionIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
            long admissionid);

    List<TBillBill> findByBillAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
            long id);

    // For IPD bill search
    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientMrNoContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalse(
            String Mr);

    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIpdBillTrueAndFinalBillFalseAndIsActiveTrueAndIsDeletedFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserLastnameContainsAndIpdBillTrueAndFinalBillFalseAndIsActiveTrueAndIsDeletedFalse(
            String FName, String LName);

    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientUserIdUserMobileContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalse(
            String Mobile);

    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientUserIdUserResidencePhoneContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalse(
            String Phone);

    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientUserIdUserPassportNoContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserDrivingNoContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserPanNoContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalse(
            String Id, String Id1, String Id2);

    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientUserIdUserEmailContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalse(
            String mail);

    List<TBillBill> findByBillAdmissionIdAdmissionPatientIdPatientIdEqualsAndFinalBillFalseAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
            long patientid);

    // print MIS
    List<TBillBill> findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            String bno, String mrno, String fn, String mn, String lnm, Long[] unitid);

    List<TBillBill> findAllByBillDateEqualsAndTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Date today, Long[] unitid);

    List<TBillBill> findAllByTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(Long[] unitid);

    @Query(value = "SELECT * FROM tbill_bill  where is_active = 1 and is_deleted = 0 and tbill_unit_id in :unitid and   DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  ", nativeQuery = true)
    List<TBillBill> findAllByBillFromToDateAndTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long[] unitId);

    // bill list for MIS
    Page<TBillBill> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TBillBill> findAllByTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(Long[] unitid,
                                                                                                    Pageable page);

    Page<TBillBill> findAllByBillDateEqualsAndTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Date today, Long[] unitid, Pageable page);

    Page<TBillBill> findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            String bno, String mrno, String fn, String mn, String lnm, Long[] unitid, Pageable page);

    @Query(value = "SELECT SUM(COALESCE(bill_amount_paid,0.00)) FROM tbill_bill where ipd_bill = true and bill_date = CURDATE() and tbill_unit_id = :unitid", nativeQuery = true)
    double findByTodayBillipdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(@Param("unitid") long unitid);

    @Query(value = "SELECT SUM(COALESCE(bill_amount_paid,0.00)) FROM tbill_bill where ipd_bill = false and bill_date = CURDATE() and tbill_unit_id = :unitid", nativeQuery = true)
    double findByTodayBillopdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(@Param("unitid") long unitid);

    @Query(value = "SELECT SUM(COALESCE(bill_amount_paid,0.00)) FROM tbill_bill where ipd_bill = true and bill_date = CURDATE()  and tbill_unit_id = :unitid ", nativeQuery = true)
    double findByBillipdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(@Param("unitid") long unitid);

    @Query(value = "SELECT SUM(COALESCE(bill_amount_paid,0.00)) FROM tbill_bill where ipd_bill = false  and tbill_unit_id = :unitid ", nativeQuery = true)
    double findByBillopdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(@Param("unitid") long unitid);

    @Query(value = "SELECT SUM(COALESCE(bill_amount_paid,0.00)) FROM tbill_bill where ipd_bill = true and tbill_unit_id = :unitid  and bill_date  BETWEEN  :sdate and :edate  ", nativeQuery = true)
    double findByFromTodateBillipdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("sdate") String sdate, @Param("edate") String edate, @Param("unitid") long unitid);

    @Query(value = "SELECT SUM(COALESCE(bill_amount_paid,0.00)) FROM tbill_bill where ipd_bill = false and tbill_unit_id = :unitid  and bill_date  BETWEEN  :sdate and :edate ", nativeQuery = true)
    double findByFromTodateBillopdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("sdate") String sdate, @Param("edate") String edate, @Param("unitid") long unitid);

    @Query(value = "SELECT * FROM tbill_bill  where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from mst_visit  where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    Page<TBillBill> findAllByBillFromToDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(@Param("sd") String sd,
                                                                                               @Param("ed") String ed, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill  where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from mst_visit  where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    Page<TBillBill> findAllByIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed,
                                                           Pageable page);

    // Author: Mohit
    default Map<String, String> updateBillForTariffCopay(List<TbillReciept> reciepts) {
        double totalAmount = 0;
        Map<String, String> map = new HashMap<>();
        TBillBill tBillBill = getById(reciepts.get(0).getBrBillId().getBillId());
        totalAmount = tBillBill.getBillTotCoPayRecieved();
        for (TbillReciept tbillReciept : reciepts) {
            totalAmount = totalAmount + tbillReciept.getBrPaymentAmount();
        }
        if (totalAmount > 0) {
            tBillBill.setBillTotCoPayOutstanding(tBillBill.getBillTotCoPay() - totalAmount);
            tBillBill.setBillTotCoPayRecieved(totalAmount);
            map.put("success", "1");
            map.put("msg", "Tariff Company Settled !");
        } else {
            map.put("success", "0");
            map.put("msg", "Tariff Company NOT Settled !");
        }
        return map;
    }

    // Author: Mohit
    @Modifying(clearAutomatically = true)
    @Query(value = "update TBillBill bill set bill.billTotCoPayRecieved =:billTotCoPayRecieved, bill.billTotCoPayOutstanding = :billTotCoPayOutstanding where bill.billId = :billId")
    int updateBillForCoPay(@Param("billTotCoPayRecieved") double billTotCoPayRecieved,
                           @Param("billTotCoPayOutstanding") double billTotCoPayOutstanding, @Param("billId") Long billId);

    // Autor: Romil : NST
    Page<TBillBill> findByBillAdmissionIdAdmissionId(Long admissionId, Pageable page);

    default String makeWorkOrderNumber(String department) {
        if (department == null || department.equals("")) {
            department = "OPD";
        }
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        TBillBill lastBill = findTopByOrderByBillIdDesc();
        StringBuilder billNumber = new StringBuilder("WKOD/");
        try {
            if (lastBill.getBillWorkOrderNumber() != null) {
                // String number =
                // lastBill.getBillWorkOrderNumber().substring(44);
                String number = lastBill.getBillWorkOrderNumber()
                        .substring(lastBill.getBillWorkOrderNumber().lastIndexOf("/") + 1);
                int newNumber = Integer.parseInt(number);
                // String incNumber = String.format("%08d", newNumber + 1);
                billNumber.append(dateFormat.format(todayDate)).append("/"
                        + department.subSequence(0, 3).toString().toUpperCase() + "/" + String.valueOf(newNumber + 1));
            } else {
                billNumber.append(dateFormat.format(todayDate))
                        .append("/" + department.substring(0, 3).toString().toUpperCase() + "/00000001");
            }
        } catch (Exception e) {
            // System.out.println("Null Pointer Exception on Line Number: 42");
            billNumber.append(dateFormat.format(todayDate))
                    .append("/" + department.substring(0, 2).toString().toUpperCase() + "/00000001");
        }
        return billNumber.toString();
    }

    TBillBill findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(long id);

    List<TBillBill> findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(
            String bno, String mrno, String fn, String mn, String lnm);

    List<TBillBill> findAllByBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(Date today);

    List<TBillBill> findAllByIsActiveTrueAndIsDeletedFalse();

    @Query(value = "SELECT * FROM tbill_bill  where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    List<TBillBill> findAllByIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed);

    Page<TBillBill> findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(
            String bno, String mrno, String fn, String mn, String lnm, Pageable page);

    // with staffid
    Page<TBillBill> findAllByBillUserIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(long staffid, Pageable page);

    Page<TBillBill> findAllByBillUserIdStaffIdEqualsAndBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(long staffid,
                                                                                                      Date today, Pageable page);
//    Page<TBillBill> findAllBillUserIdStaffIdEqualsAndByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(
//            long staffid, String bno, String mrno, String fn, String mn, String lnm, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill  where bill_user_id = :staffid and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from mst_visit  where bill_user_id = :staffid and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    Page<TBillBill> findAllByBillUserIdBillFromToDateAndIsActiveTrueAndIsDeletedFalse(@Param("staffid") long staffid,
                                                                                      @Param("sd") String sd, @Param("ed") String ed, Pageable page);

    Page<TBillBill> findAllByBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(Date today, Pageable page);

    // with staffid
    List<TBillBill> findAllByBillUserIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(long staffid);

    List<TBillBill> findAllByBillUserIdStaffIdEqualsAndBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(long staffid,
                                                                                                      Date today);
//    List<TBillBill> findAllBillUserIdStaffIdEqualsAndByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(
//            long staffid, String bno, String mrno, String fn, String mn, String lnm);

    @Query(value = "SELECT * FROM tbill_bill  where bill_user_id = :staffid and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    List<TBillBill> findAllByBillUserIdBillFromToDateAndIsActiveTrueAndIsDeletedFalse(@Param("staffid") long staffid,
                                                                                      @Param("sd") String sd, @Param("ed") String ed);

    // with visit doctor
    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(
            long staffid1, String search, String search1, String search2, String search3, String search4,
            Pageable Page);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(
            long staffid1, Date today, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill  where bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "SELECT count(*) FROM tbill_bill  where bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(
            @Param("staffid1") long staffid, @Param("sd") String sd, @Param("ed") String ed, Pageable page);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(
            long staffid1, long staffid, String search, String search1, String search2, String search3, String search4,
            Pageable Page);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(
            long staffid, long staffid1, Date today, Pageable page);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(
            long staffid, long staffid1, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill  where bill_user_id = :staffid and bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "SELECT count(*) FROM tbill_bill  where bill_user_id = :staffid and bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdBillFromToDateAndIsActiveTrueAndIsDeletedFalse(
            @Param("staffid1") long staffid1, @Param("staffid") long staffid, @Param("sd") String sd,
            @Param("ed") String ed, Pageable page);

    // List
    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(
            long staffid1, String search, String search1, String search2, String search3, String search4);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(
            long staffid1, Date today);

    @Query(value = "SELECT * FROM tbill_bill  where bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)", nativeQuery = true)
    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(
            @Param("staffid1") long staffid1, @Param("sd") String sd, @Param("ed") String ed);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(
            long staffid1, long staffid, String search, String search1, String search2, String search3, String search4);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(
            long staffid, long staffid1, Date today);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(
            long staffid, long staffid1);

    @Query(value = "SELECT * FROM tbill_bill  where bill_user_id = :staffid and bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)", nativeQuery = true)
    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdBillFromToDateAndIsActiveTrueAndIsDeletedFalse(
            @Param("staffid1") long staffid1, @Param("staffid") long staffid, @Param("sd") String sd,
            @Param("ed") String ed);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(long staffid,
                                                                                            Pageable page);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(long staffid);
    // code by the saagar

    List<TBillBill> findAllByBillAdmissionIdAdmissionIdEqualsAndFinalBillTrue(Long admissionId);

    List<TBillBill> findAllByFinalBillFalseAndBillAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(
            Long admissionId);

    // check final bill or not :Jay
    List<TBillBill> findByBillAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueAndFinalBillTrueOrderByBillIdDesc(
            long id);
    // Vaibhav OPD Code

    List<TBillBill> findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            String bno, String mrno, String fn, String mn, String lnm);

    List<TBillBill> findAllByBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Date today);

    List<TBillBill> findAllByIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse();

    // by rohan
    List<TBillBill> findAllByTbillUnitIdUnitIdIn(long[] unitId);

    Page<TBillBill> findAllByTbillUnitIdUnitIdIn(long[] unitId, Pageable page);

    List<TBillBill> findAllByBillUserIdStaffIdEqualsAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, long[] unitId);

    Page<TBillBill> findAllByBillUserIdStaffIdEqualsAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, long[] unitId, Pageable page);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, long[] unitId);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, long[] unitId, Pageable page);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, long staffid1, long[] unitId);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, long staffid1, long[] unitId, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill  where is_active = 1 and is_deleted = 0 and ipd_bill = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)", nativeQuery = true)
    List<TBillBill> findAllByIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(@Param("sd") String sd,
                                                                                             @Param("ed") String ed);

    Page<TBillBill> findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            String bno, String mrno, String fn, String mn, String lnm, Pageable page);

    Page<TBillBill> findAllByBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Date today, Pageable page);

    Page<TBillBill> findAllByIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(Pageable page);

    @Query(value = "SELECT * FROM tbill_bill where is_active = 1 and is_deleted = 0 and ipd_bill = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "SELECT count(*) FROM tbill_bill where is_active = 1 and is_deleted = 0 and ipd_bill = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)", nativeQuery = true)
    Page<TBillBill> findAllByIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(@Param("sd") String sd,
                                                                                             @Param("ed") String ed, Pageable page);
//    List<TBillBill> findAllBillUserIdStaffIdEqualsAndByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
//            long staffid, String bno, String mrno, String fn, String mn, String lnm);

    List<TBillBill> findAllByBillUserIdStaffIdEqualsAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, Date today);

    List<TBillBill> findAllByBillUserIdStaffIdEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid);

    @Query(value = "SELECT * FROM tbill_bill where bill_user_id = :staffid and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) and ipd_bill = 0  \n#pageable\n", countQuery = "Select count(*) from tbill_bill where bill_user_id = :staffid and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) and ipd_bill = 0 ", nativeQuery = true)
    Page<TBillBill> findAllByBillUserIdBillFromToDateAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("staffid") long staffid, @Param("sd") String sd, @Param("ed") String ed, Pageable page);
//    Page<TBillBill> findAllBillUserIdStaffIdEqualsAndByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
//            long staffid, String bno, String mrno, String fn, String mn, String lnm, Pageable page);

    Page<TBillBill> findAllByBillUserIdStaffIdEqualsAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, Date today, Pageable page);

    Page<TBillBill> findAllByBillUserIdStaffIdEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill  where bill_user_id = :staffid and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) and ipd_bill = 0 ", nativeQuery = true)
    List<TBillBill> findAllByBillUserIdBillFromToDateAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("staffid") long staffid, @Param("sd") String sd, @Param("ed") String ed);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid1, String search, String search1, String search2, String search3, String search4);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid1, Date today);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid);

    @Query(value = "SELECT * FROM tbill_bill  where bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and ipd_bill = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)", nativeQuery = true)
    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("staffid1") long staffid1, @Param("sd") String sd, @Param("ed") String ed);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid1, String search, String search1, String search2, String search3, String search4,
            Pageable Page);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid1, Date today, Pageable page);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill  where bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and ipd_bill = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "SELECT count(*) FROM tbill_bill  where bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and ipd_bill = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("staffid1") long staffid, @Param("sd") String sd, @Param("ed") String ed, Pageable page);

    // List<TBillBill>
    // findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalse(long
    // staffid);
    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid1, long staffid, String search, String search1, String search2, String search3, String search4);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, long staffid1, Date today);

    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, long staffid1);

    @Query(value = "SELECT * FROM tbill_bill  where bill_user_id = :staffid and bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and ipd_bill = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "SELECT count(*) FROM tbill_bill  where bill_user_id = :staffid and bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and ipd_bill = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)", nativeQuery = true)
    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdBillFromToDateAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("staffid1") long staffid1, @Param("staffid") long staffid, @Param("sd") String sd,
            @Param("ed") String ed, Pageable page);

    // Page<TBillBill>
    // findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalse(long
    // staffid, Pageable page);
    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid1, long staffid, String search, String search1, String search2, String search3, String search4,
            Pageable Page);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, long staffid1, Date today, Pageable page);

    Page<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            long staffid, long staffid1, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill  where bill_user_id = :staffid and bill_visit_id in (SELECT visit_id FROM mst_visit where visit_staff_id =:staffid1 ) and is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) and ipd_bill = 0", nativeQuery = true)
    List<TBillBill> findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdBillFromToDateAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("staffid1") long staffid1, @Param("staffid") long staffid, @Param("sd") String sd,
            @Param("ed") String ed);

    // and ipd_bill = false
    // emrgency number
    default String makeEmrgencyNumber(int registrationSource) {
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        StringBuilder emergencyNumber = new StringBuilder("EMG/");
        System.out.println("------emergencyNumber-------" + emergencyNumber);
        try {
            TBillBill lastBill = findTopByBillVisitIdVisitRegistrationSourceEqualsOrderByBillIdDesc(registrationSource);
            System.out.println("------lastBill.getBillNumber-------" + lastBill.getBillNumber());
            if (lastBill.getBillNumber() != null) {
                String number = lastBill.getBillNumber().substring(16);
                System.out.println("------After substring-------" + number);
                int newNumber = Integer.parseInt(number);
                String incNumber = String.format("%08d", newNumber + 1);
                System.out.println("------After incNumber-------" + incNumber);
                emergencyNumber.append(dateFormat.format(todayDate)).append("/" + incNumber);
                System.out.println("------At Last emergencyNumber-------" + emergencyNumber);
            } else {
                emergencyNumber.append(dateFormat.format(todayDate)).append("/00000001");
                System.out.println("------If no i s null-------" + emergencyNumber);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
            emergencyNumber.append(dateFormat.format(todayDate)).append("/00000001");
            System.out.println("------If Exception-------" + emergencyNumber);
        }
        return emergencyNumber.toString();
    }

    // to find last emergency last bill
    TBillBill findTopByBillVisitIdVisitRegistrationSourceEqualsOrderByBillIdDesc(int source);

    // to find emrgency patient bill list
    List<TBillBill> findByBillVisitIdVisitPatientIdPatientIdAndBillVisitIdVisitRegistrationSourceAndFinalBillTrueAndIsActiveTrueAndIsDeletedFalseOrderByBillIdDesc(
            long patientId, int source);

    // get emrgency bill list by visit id
    Page<TBillBill> findByBillVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(long visitid, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill WHERE is_active = 1 AND is_deleted = 0 AND is_approve= 0 and is_rejected =0 AND DATEDIFF(date(created_date), NOW()) % 23 ", nativeQuery = true)
    List<TBillBill> findByIsActiveTrueAndIsDeletedFalseAndIsApproveFalseAndIsRejectedFalse();
}