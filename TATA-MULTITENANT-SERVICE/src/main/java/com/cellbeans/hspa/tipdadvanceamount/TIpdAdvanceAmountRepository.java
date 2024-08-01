package com.cellbeans.hspa.tipdadvanceamount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface TIpdAdvanceAmountRepository extends JpaRepository<TIpdAdvanceAmount, Long> {
    TIpdAdvanceAmount findByIpdadvancedId(Long id);

    List<TIpdAdvanceAmount> findAllByIsActiveTrueAndIsDeletedFalse();

    Page<TIpdAdvanceAmount> findByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TIpdAdvanceAmount> findAllByAaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(long patientId, Pageable page);

    @Query(value = "select * from trn_ipd_advanced i where i.aa_patient_id = :patientId and created_date >= :date", nativeQuery = true)
    List<TIpdAdvanceAmount> findAllByAaPatientIdPatientIdAdmissionidEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("patientId") long patientid, @Param("date") String date);

    @Query(value = "select * from trn_ipd_advanced i where i.aa_patient_id = :patientId and i.created_date >= :date and i.created_date <= :date1", nativeQuery = true)
    List<TIpdAdvanceAmount> findAllByAaPatientIdPatientIdAdmissionidanddischargeEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("patientId") long patientid, @Param("date") String date, @Param("date1") String date1);

    Page<TIpdAdvanceAmount> findAllByAaPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(String Mrno, Pageable page);

    Page<TIpdAdvanceAmount> findAllByIpdAdmissionIdAdmissionPatientIdPatientMrNoContainsOrIpdAdmissionIdAdmissionPatientIdPatientUserIdUserFirstnameContainsOrIpdAdmissionIdAdmissionPatientIdPatientUserIdUserLastnameContainsOrIpdAdmissionIdAdmissionPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String mrNumber, String fistName, String lastName, String mobile, Pageable page);

    Page<TIpdAdvanceAmount> findAllByAdvanceAgainstIdAaNameAndIsActiveTrueAndIsDeletedFalseOrAaCompanyIdCompanyNameAndIsActiveTrueAndIsDeletedFalseOrAaRecieptNumberContainsAndIsActiveTrueAndIsDeletedFalse(String Advancedagainst, String company, String Reciept, Pageable page);
    /*List<TIpdAdvanceAmount> findAllByIpdAdmissionIdAdmissionPatientIdPatientMrNoContainsOrIpdAdmissionIdAdmissionPatientIdPatientUserIdUserFirstnameContainsOrIpdAdmissionIdAdmissionPatientIdPatientUserIdUserLastnameContainsOrIpdAdmissionIdAdmissionPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String mrNumber, String firstname, String lastName, String mobile);*/

    TIpdAdvanceAmount findByAaPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long patientId);

    TIpdAdvanceAmount findByIpdadvancedIdAndIsActiveTrueAndIsDeletedFalse(long id);

    TIpdAdvanceAmount findTopByOrderByIpdadvancedIdDesc();

    default String makeRecieptNumber() {
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        TIpdAdvanceAmount lastReciept = findTopByOrderByIpdadvancedIdDesc();
        StringBuilder receiptNumber = new StringBuilder("RECPT/");
        try {
            if (lastReciept.getAaRecieptNumber() != null) {
                String number = lastReciept.getAaRecieptNumber().substring(17);
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

    //date filter for advance list by Rohit
    Page<TIpdAdvanceAmount> findByCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(Date fdate, Date tdate, Pageable page);

}
