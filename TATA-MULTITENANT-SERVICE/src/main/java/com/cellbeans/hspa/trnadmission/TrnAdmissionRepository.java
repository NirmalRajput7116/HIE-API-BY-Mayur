package com.cellbeans.hspa.trnadmission;

import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombination;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombinationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public interface TrnAdmissionRepository extends JpaRepository<TrnAdmission, Long> {

    @Modifying
    @Query("update TrnAdmission admission set admission.admissionDischargeDate = :admissionDischargeDate , admission.admissionStatus = :admissionStatus where admission.admissionId = :admissionId")
    @Transactional
    int markasdischarge(@Param("admissionDischargeDate") String admissionDischargeDate, @Param("admissionStatus") boolean admissionStatus, @Param("admissionId") Long admissionId);

    @Modifying
    @Query("update TrnAdmission admission set admission.isFinalized = :isFinalized  where admission.admissionId = :admissionId")
    @Transactional
    int updateDisachargeSummaryStatus(@Param("isFinalized") Boolean isFinalized, @Param("admissionId") Long admissionId);

    @Modifying
    @Query("update TrnAdmission admission set admission.admissionCancelDate = :admissionCancelDate , admission.admissionIsCancel = 1, admission.admissionCancelReason =:admissionCancelReason where admission.admissionId = :admissionId")
    @Transactional
    int markascancel(@Param("admissionCancelDate") String admissionCancelDate, @Param("admissionCancelReason") String admissionCancelReason, @Param("admissionId") Long admissionId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM trn_admission_admission_current_bed_id WHERE trn_admission_admission_id= ?1", nativeQuery = true)
    int dischargebedfree(@Param("admissionId") Long admissionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `trn_admission` SET `admission_expected_discharge_date` = ?1 WHERE `admission_id` = ?2", nativeQuery = true)
    int expecteddischargedate(@Param("exdate") String exdate, @Param("admissionId") Long admissionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `trn_admission` SET `admission_discharge_date` = ?1 , admission_discharge_status=1, admission_status=1 WHERE `admission_id` = ?2", nativeQuery = true)
    int dischage_patient(@Param("dechargeDate") String dechargeDate, @Param("admissionId") Long admissionId);

    // Admission List
    Page<TrnAdmission> findAllByAdmissionStatusIsFalseAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    // Discharge List
    Page<TrnAdmission> findAllByAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    // Admitted Patient List autocomplite
    List<TrnAdmission> findAllByAdmissionPatientIdPatientMrNoContainsAndAdmissionUnitIdUnitIdEqualsAndAdmissionStatusFalseAndIsActiveTrueAndIsDeletedFalse(String key, Long UnitId);

    List<TrnAdmission> findAllByAdmissionPatientIdPatientMrNoContainsOrAdmissionPatientIdPatientUserIdUserMobileContainsOrAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndAdmissionUnitIdUnitIdEqualsAndAdmissionStatusFalseAndIsActiveTrueAndIsDeletedFalse(String key, String mob, String first, String last, Long UnitId);

    List<TrnAdmission> findAllByAdmissionIsCancelFalseAndAdmissionStatusFalseAndAdmissionPatientIdPatientMrNoContainsOrAdmissionPatientIdPatientUserIdUserMobileContainsOrAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndAdmissionUnitIdUnitIdEqualsAndAdmissionStatusFalseAndIsActiveTrueAndIsDeletedFalse(String key, String mob, String first, String last, Long UnitId);

    Page<TrnAdmission> findByAdmissionPatientIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnAdmission> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnAdmission> findAllByIsActiveTrueAndIsDeletedFalse();

    List<TrnAdmission> findAllByAdmissionStatusIsFalseAndIsActiveTrueAndIsDeletedFalseOrderByAdmissionIdDesc();

    // Discharge patient name by abhi
    Page<TrnAdmission> findByAdmissionStatusIsTrueAndAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(String pname, Pageable page);

    // Discharge patient ipdno by abhi
    Page<TrnAdmission> findByAdmissionStatusIsTrueAndAdmissionIpdNoContainsAndIsActiveTrueAndIsDeletedFalse(String pname, Pageable page);

    // patient name by abhi
    @Query(value = "select ta from TrnAdmission ta where ta.admissionStatus = false and ta.admissionIsCancel = false and ta.isActive = true and ta.isDeleted = false and (ta.admissionPatientId.patientUserId.userFirstname like %:pname% or ta.admissionPatientId.patientUserId.userLastname like %:plname%) ")
    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(@Param("pname") String pname, @Param("plname") String plname, Pageable page);

    Page<TrnAdmission> findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(String pname, String plname, Pageable page);

    // Cancelld list by patient name by Neha
    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserFirstnameContainsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(String pname, Pageable page);
    // mobile noi22018

    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserMobileContainsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(String mobileno, Pageable page);
    // Cancelld list mobile noi22018

    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserMobileContainsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(String mobileno, Pageable page);
    // MR No i22018

    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientMrNoContainsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(String mrno, Pageable page);
    // Cancelld list MR No i22018

    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientMrNoContainsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(String mrno, Pageable page);
    // Staff ID (Doctor Wise)

    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionStaffIdStaffIdEqualsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(Long staffid, Pageable page);
    //Cancelld list Staff ID (Doctor Wise)

    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionStaffIdStaffIdEqualsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(Long staffid, Pageable page);

    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionIpdNoContainsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(String ipdno, Pageable page);

    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionIpdNoContainsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(String ipdno, Pageable page);

    // patient Admitted List by abhi
    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    // patient Admission Cancelled List ... Neha
    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    // by ward
    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionPatientBedIdBedWardIdWardIdEqualsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(Long wardId, Pageable page);

    // Cancelld list by ward ... Neha
    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionPatientBedIdBedWardIdWardIdEqualsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(Long wardId, Pageable page);
    // Admitted Patient By ward id

    List<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionIsCancelFalseAndAdmissionPatientBedIdBedWardIdWardIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long wardId);

    List<TrnAdmission> findByAdmissionPatientIdContains(String key);

    List<TrnAdmission> findAllByAdmissionStatusFalse();

    TrnAdmission findByAdmissionIdAndIsActiveTrueAndIsDeletedFalse(long admissionid);
    //TrnAdmission findByAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByAdmissionIdDesc(long patientId);

    List<TrnAdmission> findByAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByAdmissionIdDesc(long patientId);

    //patient search
    List<TrnAdmission> findByAdmissionPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(String Mr);

    // 28-1-2020
    List<TrnAdmission> findByAdmissionPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionStatusEqualsAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(String Mr, Boolean AddmissionStatus);

    List<TrnAdmission> findByAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(String Fname, String Lname);

    List<TrnAdmission> findByAdmissionPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(String Mobil);

    List<TrnAdmission> findByAdmissionPatientIdPatientUserIdUserResidencePhoneContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(String phone);

    List<TrnAdmission> findByAdmissionPatientIdPatientUserIdUserPassportNoContainsAndIsActiveTrueAndIsDeletedFalseOrAdmissionPatientIdPatientUserIdUserDrivingNoContainsAndIsActiveTrueAndIsDeletedFalseOrAdmissionPatientIdPatientUserIdUserPanNoContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(String Id, String Id1, String Id2);

    List<TrnAdmission> findByAdmissionPatientIdPatientUserIdUserEmailContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(String Mail);

    /**
     * @return list admissions against non-discharge and not deleted
     */
    List<TrnAdmission> findAllByAdmissionStatusFalseAndIsActiveTrueAndIsDeletedFalse();

    List<TrnAdmission> findByIsActiveTrueAndIsDeletedFalse();

    List<TrnAdmission> findAllByAdmissionPatientIdPatientMrNoContains(String key);

    List<TrnAdmission> findAllByAdmissionPatientIdPatientMrNoContainsOrAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsOrAdmissionPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByCreatedDate(String mrNumber, String firstName, String lastName, String mobileNumber);

    List<TrnAdmission> findAllByAdmissionPatientIdPatientMrNoContainsOrAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsOrAdmissionPatientIdPatientUserIdUserMobileContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseAndAdmissionStatusFalse(String mrNumber, String firstName, String lastName, String mobileNumber, long unitId, Pageable page);

    // auto search Patint by MR No, ID, Mobile, Phone, PName, Id ....Gayatri
    List<TrnAdmission> findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientMrNoContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String mrNumber, long unitId, Pageable page);

    List<TrnAdmission> findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String firstName, String lastName, long unitId, Pageable page);

    List<TrnAdmission> findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserMobileContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String mobileNumber, long unitId, Pageable page);

    List<TrnAdmission> findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserUidContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String IdNumber, long unitId, Pageable page);

    List<TrnAdmission> findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserResidencePhoneContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String IdNumber, long unitId, Pageable page);

    List<TrnAdmission> findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserEmailContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String emailId, long unitId, Pageable page);

    //query for date filters by Rohit
    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionDateBetweenAndIsActiveTrueAndIsDeletedFalse(String fdate, String tdate, Pageable page);

    //by sachin
    // @Query(value = "SELECT p FROM TrnAdmission p  where p.isActive = 1 and p.isDeleted = 0 and  DATE_FORMAT(p.admissionDischargeDate,'%y-%m-%d')=CURDATE() and  p.admissionStatus=1 and p.admissionUnitId.unitId=:unitid")
    @Query(value = "SELECT * FROM trn_admission p  where  p.is_active = 1 and p.is_deleted = 0 and  DATE_FORMAT(p.admission_discharge_date,'%y-%m-%d')=CURDATE() and  p.admission_status=1 and p.admission_unit_id=:unitid \n#pageable\n", countQuery = "Select count(*) from trn_admission p  where  p.is_active = 1 and p.is_deleted = 0 and  DATE_FORMAT(p.admission_discharge_date,'%y-%m-%d')=CURDATE() and  p.admission_status=1 and p.admission_unit_id=:unitid", nativeQuery = true)
    Page<TrnAdmission> findAllByAdmissionDischargeDateAndAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(@Param("unitid") long unitid, Pageable page);

    //@Query(value = "SELECT p FROM TrnAdmission p where p.isActive = 1 and p.isDeleted = 0 and  DATE_FORMAT(p.admissionDischargeDate,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  and  p.admissionStatus=1 and p.admissionUnitId.unitId=:unitid")
    @Query(value = "SELECT * FROM trn_admission p  where  p.is_active = 1 and p.is_deleted = 0 and  DATE_FORMAT(p.admission_discharge_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  and  p.admission_status=1 and p.admission_unit_id=:unitid \n#pageable\n", countQuery = "Select count(*) from trn_admission p  where  p.is_active = 1 and p.is_deleted = 0 and  DATE_FORMAT(p.admission_discharge_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  and  p.admission_status=1 and p.admission_unit_id=:unitid", nativeQuery = true)
    Page<TrnAdmission> findAllByAdmissionDischargeDateBetweenAndAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") long unitid, Pageable page);

    /**
     * Gives list of Admission along with their Latest Visit of same patient and Sponsors of that admissions
     *
     * @param qString                         : input from request
     * @param mstVisitRepository              : Reportsitory of MstVisit
     * @param trnSponsorCombinationRepository : repository of TrnSponsor
     * @return
     */
    default List<TrnAdmission> listTrnAdmissions(String qString, MstVisitRepository mstVisitRepository, TrnSponsorCombinationRepository trnSponsorCombinationRepository) {
        List<TrnAdmission> trnAdmissions;
        if ((qString == null) || (qString.equalsIgnoreCase(""))) {
            trnAdmissions = findAllByIsActiveTrueAndIsDeletedFalse();
        } else {
            trnAdmissions = findAllByAdmissionPatientIdPatientMrNoContainsOrAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsOrAdmissionPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByCreatedDate(qString, qString, qString, qString);
        }
        if (trnAdmissions.size() > 0) {
            for (TrnAdmission trnAdmission : trnAdmissions) {
                MstVisit mstVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsOrderByVisitIdDesc(trnAdmission.getAdmissionPatientId().getPatientId());
                List<TrnSponsorCombination> trnSponsorCombinations = trnSponsorCombinationRepository.findByScUserIdUserIdEqualsAndIsDeletedFalse(trnAdmission.getAdmissionPatientId().getPatientUserId().getUserId());
                if (mstVisit != null) {
                    trnAdmission.setMbillTariff(mstVisit.getVisitTariffId());
                }
                if (trnSponsorCombinations.size() > 0) {
                    trnAdmission.setTrnSponsorCombinationList(trnSponsorCombinations);
                }
            }
        }
        return trnAdmissions;
    }
    // Author: Mohit

    /**
     * Find Department of Admission
     *
     * @param admissionId : admission unique id
     * @return MstDepartment object
     */
    default MstDepartment findDepartmentByAdmission(Long admissionId) {
        return getById(admissionId).getAdmissionDepartmentId();
    }
    //@Query(value = "SELECT new com.cellbeans.hspa.mbilltariff.MBillTariffDTO(t.tariffId, t.tariffName, t.tariffCode, t.tariffCoPay, t.tariffDiscount, t.isActive, t.isDeleted) from MbillTariff t where t.isActive=true AND t.isDeleted = false")
    // @Query(value = "select new com.cellbeans.hspa.trnadmission.TrnAdmissionDTO(t.admissionPatientBedId.bedId) from TrnAdmission t where t.isActive=true AND t.isDeleted = false")
    // List<TrnAdmissionDTO> onlyAdmitBed();
    //  List<TrnAdmission> findAllAndIsActiveTrueAndIsDeletedFalse();
    // Author: Mohit

    /**
     * NOT YET USED:
     *
     * @param admissionId           : admssion id
     * @param transferBedId         : Bed if where patient is transferred
     * @param tbillBillRepository   : repository of TBillBill
     * @param mbillTariffRepository : Repository of Tariff
     * @return
     */
//    default Boolean addBedServicesBedTrans(Long admissionId, List<MipdBed> transferBedId, TbillBillRepository tbillBillRepository, MbillTariffRepository mbillTariffRepository) {
//        TrnAdmission trnAdmission = getById(admissionId);
//        TBillBill tBillBill = tbillBillRepository.findFirstByBillAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(admissionId);
//        MbillTariff mbillTariff = tBillBill.getBillTariffId();
//        List<MbillTariffService> tariffService = mbillTariff.getTariffServices();
//        List<MbillService> services = new ArrayList<>();
//
//        for (int i = 0; i < transferBedId.size(); i++) {
//            services.addAll(transferBedId.get(i).getMipdBedServiceList());
//        }
//        // List<MbillService> services = transferBedId.stream().findAny().get().getMipdBedServiceList();
//
//        for(MbillService mbillService: services) {
//            TbillBillService billService = new TbillBillService();
//            if(tariffService.stream().filter(it -> it.getTsServiceId().getServiceId() == mbillService.getServiceId()).collect(Collectors.toList()).size() > 0) {
//                billService.setBsServiceId(mbillService);
//                billService.setBsBedService(true);
//                billService.setBsPackageName("nop");
//                billService.setBsPackageId(0);
//                billService.setBsConcessionPercentage(0);
//                billService.setBsDiscountAmount(0);
//            }
//        }
//        return false;
//    }
    @Query(value = "SELECT * FROM trn_admission where is_active = 1 and is_deleted = 0 and admission_patient_id = :patientId and DATE_FORMAT(admission_date,'%y-%m-%d') = CAST(:fromDate as DATE)", nativeQuery = true)
    List<TrnAdmission> findByadmitedPatientIdPatientIdAndadmitedRegistrationSourceEqualsAndadmitedDateGreaterThanEqualAndIsActiveTrueAndIsDeletedFalse(@Param("patientId") Long patientId, @Param("fromDate") String fromDate);

    TrnAdmission findTopByIsActiveTrueAndIsDeletedFalseOrderByAdmissionIdDesc();

    List<TrnAdmission> findAllByAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalse(Long patientid);

    default String makeIPDNumberForAddmission() {
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(todayDate);
        int year = calendar.get(Calendar.YEAR);
        StringBuilder ipdNumber = new StringBuilder("IPD/");
        try {
            TrnAdmission lastIpdAddmision = findTopByIsActiveTrueAndIsDeletedFalseOrderByAdmissionIdDesc();
            ipdNumber = new StringBuilder("IPD/");
            if (lastIpdAddmision.getAdmissionIpdNo() != null) {
                String number = lastIpdAddmision.getAdmissionIpdNo().substring(9);
                int newNumber = Integer.parseInt(number);
                String incNumber = String.format("%06d", newNumber + 1);
                ipdNumber.append(year).append("/" + incNumber);
            } else {
                ipdNumber.append(year).append("/000001");
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
            ipdNumber.append(dateFormat.format(todayDate)).append("/000001");
        }
        return ipdNumber.toString();
    }

    @Query(value = "select * from trn_admission trnadmissi0_ where trnadmissi0_.admission_status=0 and (DATE(trnadmissi0_.admission_date) between :fdate and :tdate) and trnadmissi0_.admission_is_cancel=0 and trnadmissi0_.is_active=1 and trnadmissi0_.is_deleted=0 order by trnadmissi0_.admission_id desc \n#pageable\n", countQuery = "Select count(*) from trn_admission trnadmissi0_ where trnadmissi0_.admission_status=0 and (DATE(trnadmissi0_.admission_date) between :fdate and :tdate) and trnadmissi0_.admission_is_cancel=0 and trnadmissi0_.is_active=1 and trnadmissi0_.is_deleted=0 ", nativeQuery = true)
    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionDateBetweenAndIsActiveTrueAndIsDeletedFalse1(@Param("fdate") String fdate, @Param("tdate") String tdate, Pageable page);

    @Query(value = "select * from trn_admission trnadmissi0_ where trnadmissi0_.admission_status=0 and (DATE(trnadmissi0_.admission_date) between :fdate and :tdate) and trnadmissi0_.admission_is_cancel=1 and trnadmissi0_.is_active=1 and trnadmissi0_.is_deleted=0 order by trnadmissi0_.admission_id desc \n#pageable\n", countQuery = "Select count(*) from trn_admission trnadmissi0_ where trnadmissi0_.admission_status=0 and (DATE(trnadmissi0_.admission_date) between :fdate and :tdate) and trnadmissi0_.admission_is_cancel=1 and trnadmissi0_.is_active=1 and trnadmissi0_.is_deleted=0 ", nativeQuery = true)
    Page<TrnAdmission> findByAdmissionStatusIsFalseAndAdmissionDateBetweenAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse1(@Param("fdate") String fdate, @Param("tdate") String tdate, Pageable page);




   /* Iterable<TrnAdmission> findAllByPatientMrNoContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String qString, PageRequest pageRequest);

    Iterable<TrnAdmission> findByAdmissionStatusIsTrueAndAdmissionStaffIdStaffUserIdUserFirstnameContainsOrAdmissionStaffIdStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(String qString, PageRequest pageRequest);*/

    TrnAdmission findByAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseAndAdmissionStatusFalseAndAdmissionIsCancelFalse(Long patientId);

    @Query(value = "SELECT * FROM trn_admission ta WHERE ta.admission_status=0 AND ta.admission_is_cancel=0 AND ta.is_active=1 AND ta.is_deleted=0 \n#pageable\n", countQuery = "select count(*) from trn_admission ta where ta.admission_status=0 and ta.admission_is_cancel=0 and ta.is_active=1 and ta.is_deleted=0", nativeQuery = true)
    Page<TrnAdmission> findAdmittedPatientByCreatedDateBetween(Pageable page);

    @Query(value = "SELECT * FROM trn_admission ta WHERE ta.admission_status=0 AND ta.admission_is_cancel=0 AND ta.is_active=1 AND ta.is_deleted=0 and DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE() \n#pageable\n", countQuery = "select count(*) from trn_admission ta where ta.admission_status=0 and ta.admission_is_cancel=0 and ta.is_active=1 and ta.is_deleted=0 and DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE()", nativeQuery = true)
    Page<TrnAdmission> findTodaysAdmittedPatientByCreatedDateBetween(Pageable page);

}