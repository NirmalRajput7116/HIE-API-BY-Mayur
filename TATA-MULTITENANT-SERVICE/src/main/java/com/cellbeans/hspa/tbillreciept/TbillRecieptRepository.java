package com.cellbeans.hspa.tbillreciept;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface TbillRecieptRepository extends JpaRepository<TbillReciept, Long> {

    Page<TbillReciept> findByBrBillIdBillNumberContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    @Modifying
    @Transactional
    @Query(value = "update TbillReciept set refundAmount=:refundAmount,brRefundReason=:brRefundReason where brId=:brId")
    int updateTbillReciept(@Param("brId") Long brId, @Param("refundAmount") Double amount,
                           @Param("brRefundReason") String brRefundReason);

    Page<TbillReciept> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillReciept> findByBrBillIdBillNumberContains(String key);

    TbillReciept findTopByOrderByBrIdDesc();

    List<TbillReciept> findByBrBillIdBillIdAndIsActiveTrueAndIsDeletedFalse(Long billId);

    List<TbillReciept> findByBrBillIdBillIdAndBrTariffIdTariffIdAndIsActiveTrueAndIsDeletedFalse(Long billId,
                                                                                                 Long tariffId);

    default String setReceiptNumber(List<TbillReciept> tbillRecieptList) {
        String firstRecieptNumber = "";
        try {
            for (TbillReciept tbillReciept : tbillRecieptList) {
                if (tbillRecieptList.get(0).equals(tbillReciept)) {
                    tbillReciept.setBrRecieptNumber(makeRecieptNumber());
                    firstRecieptNumber = tbillReciept.getBrRecieptNumber();
                }
                if (!(firstRecieptNumber.equals(""))) {
                    tbillReciept.setBrRecieptNumber(firstRecieptNumber);
                }
                save(tbillReciept);
            }
            return firstRecieptNumber;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return "ERROR";
        }
    }

    default String makeRecieptNumber() {
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        TbillReciept lastReciept = findTopByOrderByBrIdDesc();
        StringBuilder receiptNumber = new StringBuilder("RECPT/");
        try {
            if (lastReciept.getBrRecieptNumber() != null) {
                String number = lastReciept.getBrRecieptNumber().substring(17);
                int newNumber = Integer.parseInt(number);
                String incNumber = String.format("%08d", newNumber + 1);
                receiptNumber.append(dateFormat.format(todayDate)).append("/" + incNumber);
            } else {
                receiptNumber.append(dateFormat.format(todayDate)).append("/00000001");
            }
        } catch (Exception exception) {
            receiptNumber.append(dateFormat.format(todayDate)).append("/00000001");
            // System.out.println("Error occurred BETWEEN LINE: 31 to 37");
        }
        return receiptNumber.toString();
    }

    // for MIS reports
    // @Query(value ="SELECT * FROM tbill_reciept r where r.is_active = 1 and
    // r.is_deleted = 0 and r.br_bill_id IN (select t.bill_id from tbill_bill t
    // where t.tbill_unit_id = :unitId) \n#pageable\n ",countQuery = "SELECT
    // count(*) from tbill_reciept r where r.is_active = 1 and r.is_deleted = 0
    // and r.br_bill_id in (select t.bill_id from tbill_bill t where
    // t.tbill_unit_id = :unitId) ", nativeQuery = true)
    Page<TbillReciept> findAllByBrBillIdTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Long[] unitId, Pageable page);

    List<TbillReciept> findAllByBrBillIdTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Long[] unitId);

    Page<TbillReciept> findAllByBrBillIdTbillUnitIdUnitIdInAndBrPatientIdPatientUserIdUserFirstnameContainsOrBrPatientIdPatientUserIdUserLastnameContainsOrBrPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Long[] unitId, String search, String search1, String search2, Pageable page);

    List<TbillReciept> findAllByBrBillIdTbillUnitIdUnitIdInAndBrPatientIdPatientUserIdUserFirstnameContainsOrBrPatientIdPatientUserIdUserLastnameContainsOrBrPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Long[] unitId, String search, String search1, String search2);

    Page<TbillReciept> findAllByBrBillIdTbillUnitIdUnitIdInAndBrCheckDateEqualsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Long[] unitId, Date today, Pageable page);

    List<TbillReciept> findAllByBrBillIdTbillUnitIdUnitIdInAndBrCheckDateEqualsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Long[] unitId, Date today);

    @Query(value = "SELECT * FROM tbill_reciept r where r.is_active = 1 and r.is_deleted = 0 and r.br_bill_id IN (select t.bill_id from tbill_bill t where t.tbill_unit_id in :unitId and DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE))  \n#pageable\n ", countQuery = "SELECT count(*) from tbill_reciept r where r.is_active = 1 and r.is_deleted = 0 and r.br_bill_id IN (select t.bill_id from tbill_bill t where t.tbill_unit_id in:unitId and DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)) ", nativeQuery = true)
    Page<TbillReciept> findAllByBrBillIdFromToDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("unitId") Long[] unitId, @Param("sd") String sd, @Param("ed") String ed, Pageable page);

    @Query(value = "SELECT * FROM tbill_reciept r where r.is_active = 1 and r.is_deleted = 0 and r.br_bill_id IN (select t.bill_id from tbill_bill t where t.tbill_unit_id in :unitId and DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)) ", nativeQuery = true)
    List<TbillReciept> findAllByBrBillIdFromToDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            @Param("unitId") Long[] unitId, @Param("sd") String sd, @Param("ed") String ed);

    // Rohan for mis
    Page<TbillReciept> findAllByBrBillIdTbillUnitIdUnitIdInAndBrBillIdBillTariffIdTariffIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Long[] unitId, Long tarrifId, Pageable page);

    List<TbillReciept> findAllByBrBillIdTbillUnitIdUnitIdInAndBrBillIdBillTariffIdTariffIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
            Long[] unitId, Long tarrifId);
}