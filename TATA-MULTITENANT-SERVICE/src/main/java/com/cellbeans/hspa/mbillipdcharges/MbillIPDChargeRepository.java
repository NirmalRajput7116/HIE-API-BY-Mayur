package com.cellbeans.hspa.mbillipdcharges;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public interface MbillIPDChargeRepository extends JpaRepository<MbillIPDCharge, Long> {
    default String makeBillNumber() {
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        MbillIPDCharge lastBill = findTopByOrderByIpdchargeIdDesc();
        StringBuilder billNumber = new StringBuilder("OPD/");
        billNumber = new StringBuilder("IPDCharge/");
        try {
            if (lastBill != null) {
                String number = lastBill.getChargeNumber().substring(21);
                int newNumber = Integer.parseInt(number);
                String incNumber = String.format("%08d", newNumber + 1);
                billNumber.append(dateFormat.format(todayDate)).append("/" + incNumber);
            } else {
                billNumber.append(dateFormat.format(todayDate)).append("/00000001");
            }
        } catch (Exception e) {
            billNumber.append(dateFormat.format(todayDate)).append("/00000001");
        }
        return billNumber.toString();
    }

    MbillIPDCharge findTopByOrderByIpdchargeIdDesc();

    default String makeWorkOrderNumber(String department) {
        if (department == null || department.equals("")) {
            department = "IPD";
        }
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        MbillIPDCharge lastBill = findTopByOrderByIpdchargeIdDesc();
        StringBuilder billNumber = new StringBuilder("WKOD/");
        try {
            if (lastBill.getBillWorkOrderNumber() != null) {
                // String number = lastBill.getBillWorkOrderNumber().substring(44);
                String number = lastBill.getBillWorkOrderNumber().substring(lastBill.getBillWorkOrderNumber().lastIndexOf("/") + 1);
                int newNumber = Integer.parseInt(number);
                // String incNumber = String.format("%08d", newNumber + 1);
                billNumber.append(dateFormat.format(todayDate)).append("/" + department.subSequence(0, 3).toString().toUpperCase() + "/" + String.valueOf(newNumber + 1));
            } else {
                billNumber.append(dateFormat.format(todayDate)).append("/" + department.substring(0, 3).toString().toUpperCase() + "/00000001");
            }
        } catch (Exception e) {
            // System.out.println("Null Pointer Exception on Line Number: 42");
            billNumber.append(dateFormat.format(todayDate)).append("/" + department.substring(0, 2).toString().toUpperCase() + "/00000001");
        }
        return billNumber.toString();
    }

    MbillIPDCharge findAllByIpdchargeIdAndIsActiveTrueAndIsDeletedFalse(long id);

    Page<MbillIPDCharge> findAllByChargeAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(long admissionId, Pageable page);

    List<MbillIPDCharge> findAllByChargeAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(long admissionId);

    @Query(value = "select SUM(mic.charge_net_payable) as total_co_pay from mbill_ipd_charge mic WHERE mic.charge_admission_id = :admissionId and mic.is_active = 1 and mic.is_deleted = 0", nativeQuery = true)
    Double getTotalCoPay(@Param("admissionId") long admissionId);

}
