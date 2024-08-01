package com.cellbeans.hspa.mstvisit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public interface MstVisitRepository extends JpaRepository<MstVisit, Long> {

    @Modifying
    @Query(value = "update mst_visit visit set visit.visit_patient_id = :visit_patient_id where visit.visit_id = :visit_id", nativeQuery = true)
    @Transactional
    int updatePatientId(@Param("visit_patient_id") Long visit_patient_id, @Param("visit_id") Long visit_id);

    @Modifying
    @Query("update MstVisit visit set visit.visitStatus = :visitStatus where visit.visitId = :visitId")
    @Transactional
    int markasvisit(@Param("visitStatus") Boolean visitStatus, @Param("visitId") Long visitId);

    Page<MstVisit> findByVisitPatientIdPatientUserIdUserFullnameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstVisit> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstVisit> findByVisitPatientIdPatientIdEqualsAndVisitUnitIdUnitIdEqualsAndVisitIsEmrFinalizedTrueAndIsActiveTrueAndIsDeletedFalse(Long patientId, Long unitId, Pageable page);

    //mohit : do not replace this method.
    List<MstVisit> findByVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(String mrNumber, String firstName, String lastName, String mobileNumber);

    //Jay : for Patient search
    List<MstVisit> findByVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(String mrNumber);

    List<MstVisit> findByVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(String firstName, String lastName);

    List<MstVisit> findByVisitPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(String mobileNumber);

    List<MstVisit> findByVisitPatientIdPatientUserIdUserResidencePhoneContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(String phoneNumber);

    List<MstVisit> findByVisitPatientIdPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserPassportNoContainsAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserDrivingNoContainsAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserPanNoContainsAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserPfNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(String IdNumber1, String IdNumber2, String IdNumber3, String IdNumber4, String IdNumber5);

    List<MstVisit> findByVisitPatientIdPatientUserIdUserEmailContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(String mail);

    //Jay : for Patient search
    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.visitId, t.visitPatientId.patientUserId.userFirstname, t.visitPatientId.patientUserId.userLastname, t.visitPatientId.patientMrNo,t.visitPatientId.patientUserId.userMobile,t.visitPatientId.patientUserId.userAge,t.visitPatientId.patientId) from MstVisit t where t.visitPatientId.patientId in (select distinct(t.visitPatientId.patientId) from MstVisit t where t.visitPatientId.patientMrNo like %:mrNumber% and t.isActive=true AND t.isDeleted = false) order by t.visitId DESC ")
    List<MstVisit> findByVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("mrNumber") String mrNumber);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.visitId, t.visitPatientId.patientUserId.userFirstname, t.visitPatientId.patientUserId.userLastname, t.visitPatientId.patientMrNo,t.visitPatientId.patientUserId.userMobile,t.visitPatientId.patientUserId.userAge,t.visitPatientId.patientId) from MstVisit t where t.visitPatientId.patientId in (select distinct(t.visitPatientId.patientId) from MstVisit t where (t.visitPatientId.patientUserId.userLastname like %:lastName% or t.visitPatientId.patientUserId.userFirstname like %:firstName%) and t.isActive=true AND t.isDeleted = false) order by t.visitId DESC ")
    List<MstVisit> findByVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.visitId, t.visitPatientId.patientUserId.userFirstname, t.visitPatientId.patientUserId.userLastname, t.visitPatientId.patientMrNo,t.visitPatientId.patientUserId.userMobile,t.visitPatientId.patientUserId.userAge,t.visitPatientId.patientId) from MstVisit t where t.visitPatientId.patientId in (select distinct(t.visitPatientId.patientId) from MstVisit t where t.visitPatientId.patientUserId.userMobile like %:mobileNumber% and t.isActive=true AND t.isDeleted = false) order by t.visitId DESC ")
    List<MstVisit> findByVisitPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("mobileNumber") String mobileNumber);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.visitId, t.visitPatientId.patientUserId.userFirstname, t.visitPatientId.patientUserId.userLastname, t.visitPatientId.patientMrNo,t.visitPatientId.patientUserId.userMobile,t.visitPatientId.patientUserId.userAge,t.visitPatientId.patientId) from MstVisit t where t.visitPatientId.patientId in (select distinct(t.visitPatientId.patientId) from MstVisit t where t.visitPatientId.patientUserId.userResidencePhone like %:phoneNumber% and t.isActive=true AND t.isDeleted = false) order by t.visitId DESC ")
    List<MstVisit> findByVisitPatientIdPatientUserIdUserResidencePhoneContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("phoneNumber") String phoneNumber);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.visitId, t.visitPatientId.patientUserId.userFirstname, t.visitPatientId.patientUserId.userLastname, t.visitPatientId.patientMrNo,t.visitPatientId.patientUserId.userMobile,t.visitPatientId.patientUserId.userAge,t.visitPatientId.patientId) from MstVisit t where t.visitPatientId.patientId in (select distinct(t.visitPatientId.patientId) from MstVisit t where (t.visitPatientId.patientUserId.userUid like %:IdNumber% or t.visitPatientId.patientUserId.userPfNo like %:IdNumber% or t.visitPatientId.patientUserId.userPassportNo like %:IdNumber% or t.visitPatientId.patientUserId.userDrivingNo like %:IdNumber% or t.visitPatientId.patientUserId.userPanNo like %:IdNumber%) and t.isActive=true AND t.isDeleted = false) order by t.visitId DESC ")
    List<MstVisit> findByVisitPatientIdPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("IdNumber") String IdNumber);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.visitId, t.visitPatientId.patientUserId.userFirstname, t.visitPatientId.patientUserId.userLastname, t.visitPatientId.patientMrNo,t.visitPatientId.patientUserId.userMobile,t.visitPatientId.patientUserId.userAge,t.visitPatientId.patientId) from MstVisit t where t.visitPatientId.patientId in (select distinct(t.visitPatientId.patientId) from MstVisit t where t.visitPatientId.patientUserId.userEmail like %:mail% and t.isActive=true AND t.isDeleted = false) order by t.visitId DESC ")
    List<MstVisit> findByVisitPatientIdPatientUserIdUserEmailContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("mail") String mail);

    List<MstVisit> findByVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseAndVisitUnitIdUnitIdOrderByVisitIdDesc(String mrNumber, String firstName, String lastName, String mobileNumber, long unitId);

    @Query(value = "select new com.cellbeans.hspa.mstvisit.MstVisitDTO(v.visitId,v.visitPatientId,v.visitTariffId) from MstVisit v where v.visitUnitId.unitId=:unitid and v.isActive=true and v.isDeleted=false and v.visitRegistrationSource=0 and (v.visitPatientId.patientMrNo like %:mrNumber% OR v.visitPatientId.patientUserId.userFirstname like %:firstName% OR v.visitPatientId.patientUserId.userLastname like %:lastName% OR v.visitPatientId.patientUserId.userMobile like %:mobileNumber% OR v.visitPatientId.patientUserId.userDrivingNo like %:EmpNo% ) Group By v.visitPatientId")
    List<MstVisitDTO> findByPatientDetailsbyUnitIDAndSearch(@Param("unitid") long unitId, @Param("mrNumber") String mrNumber, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("mobileNumber") String mobileNumber, @Param("EmpNo") String EmpNo, Pageable pageable);

    Page<MstVisit> findByVisitPatientIdPatientMrNoContains(String Mrno, Pageable page);

    @Query(value = "SELECT * FROM mst_visit INNER JOIN mst_patient ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.patient_mr_no like %?1% and mst_visit.visit_tariff_id IS NOT NULL and mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and mst_visit.visit_unit_id=?2  \n#pageable\n", countQuery = "Select count(*) FROM mst_visit INNER JOIN mst_patient ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.patient_mr_no like %?1%  and mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and mst_visit.visit_unit_id=?2 ", nativeQuery = true)
    Page<MstVisit> findAllByVisitPatientIdPatientMrNo(String Mrno, long unitId, Pageable page);
    // Page<MstVisit> findAllByVisitPatientIdPatientMrNoContains(String Mrno,Pageable page);

    //Name
    Page<MstVisit> findAllByVisitPatientIdPatientUserIdUserFirstnameContains(String Name, Pageable page);

    //Mobile No
    Page<MstVisit> findAllByVisitPatientIdPatientUserIdUserMobileContains(String Mno, Pageable page);

    //National ID
    Page<MstVisit> findAllByVisitPatientIdPatientUserIdUserUidContains(String nationalId, Pageable page);

    // Create New Eposide
    MstVisit findTop1ByVisitPatientIdPatientIdEqualsAndVisitRegistrationSourceOrderByVisitIdDesc(Long Id, Integer visitSourceRegistration);

    // Get Visit List By Patient id By Neha
    List<MstVisit> findByVisitPatientIdPatientIdEqualsAndVisitTariffIdNotNull(Long Id);

    //Author: Abhijeet For Admission
    MstVisit findTop1ByVisitPatientIdPatientIdEqualsOrderByVisitIdDesc(Long visitId);

    //Author: Seetanshu For Discharge Report
    MstVisit findTop1ByVisitPatientIdPatientIdEqualsAndVisitTariffIdTariffIdEqualsOrderByVisitIdDesc(Long visitId, Long tariffId);

    List<MstVisit> findByVisitPatientIdPatientUserIdUserFullnameContains(String key);
    //Author : Mohit

    List<MstVisit> findByVisitPatientIdPatientIdAndVisitRegistrationSourceEqualsAndVisitDateGreaterThanEqualAndIsActiveTrueAndIsDeletedFalse(Long patientId, Integer patientRegSource, @Temporal(TemporalType.DATE) Date startDate);

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and visit_patient_id = :patientId and visit_registration_source = :patientRegistrationSource and DATE_FORMAT(visit_date,'%y-%m-%d') = CAST(:fromDate as DATE)", nativeQuery = true)
    List<MstVisit> findByVisitPatientIdPatientIdAndVisitRegistrationSourceEqualsAndVisitDateGreaterThanEqualAndIsActiveTrueAndIsDeletedFalse(@Param("patientId") Long patientId, @Param("patientRegistrationSource") Integer patientRegistrationSource, @Param("fromDate") String fromDate);

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() \n#pageable\n", countQuery = "Select count(*) from mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() ", nativeQuery = true)
    Page<MstVisit> findAllByCreatedDate(Pageable pageable);

    //by today date
    Page<MstVisit> findAllByIsActiveTrueAndIsDeletedFalseAndCreatedDate(@RequestHeader("X-tenantId") String tenantName, @Temporal(TemporalType.DATE) Date date, Pageable page);

    //by todays name filter
    Page<MstVisit> findAllByVisitPatientIdPatientUserIdUserFirstnameContainsAndCreatedDateEquals(String name, Date date, Pageable page);

    //today mr no filter
    Page<MstVisit> findAllByVisitPatientIdPatientUserIdUserMobileContainsAndCreatedDateEquals(String mrno, Date date, Pageable page);

    //by uid todays filter
    Page<MstVisit> findAllByVisitPatientIdPatientUserIdUserUidContainsAndCreatedDateEquals(String uid, Date date, Pageable page);

    //start dat eend  date todat filter
    Page<MstVisit> findAllByCreatedDateBetween(Date sdate, Date edate, Pageable page);
//    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
//    Page<MstVisit> findbyStartandEndDate(@Param("sd") String sd, @Param("ed") String ed, Pageable page);
    // unitwise queary

    //by todays name filter
    Page<MstVisit> findAllByVisitPatientIdPatientUserIdUserFirstnameContainsAndVisitTariffIdIsNotNullAndVisitUnitIdUnitIdEquals(String name, Long unitid, Pageable page);

    //today mr no filter
    Page<MstVisit> findAllByVisitPatientIdPatientUserIdUserMobileContainsAndVisitTariffIdIsNotNullAndVisitUnitIdUnitIdEquals(String mrno, Long unitid, Pageable page);

    //by uid todays filter
    Page<MstVisit> findAllByVisitPatientIdPatientUserIdUserUidContainsAndVisitTariffIdIsNotNullAndVisitUnitIdUnitIdEquals(String uid, Long unitid, Pageable page);

    //start dat eend  date todat filter
    Page<MstVisit> findAllByVisitTariffIdIsNotNullAndCreatedDateBetweenAndVisitUnitIdUnitIdEquals(Date sdate, Date edate, Long unitid, Pageable page);

    //by today date
    Page<MstVisit> findAllByIsActiveTrueAndIsDeletedFalseAndCreatedDateEqualsAndVisitUnitIdUnitIdEquals(Date date, Long key, Pageable page);

    Page<MstVisit> findAllByIsActiveTrueAndIsDeletedFalseAndCreatedDateEqualsAndVisitRegistrationSourceEqualsAndVisitUnitIdUnitIdEquals(Date date, int source, Long key, Pageable page);

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() and visit_tariff_id IS NOT NULL and visit_unit_id = ?1  \n#pageable\n", countQuery = "SELECT count(*) FROM mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() and visit_unit_id = ?1 ", nativeQuery = true)
    Page<MstVisit> findAllBytodaysdate(Long sd, Pageable page);
    //unitwise end

    //MIS
    Page<MstVisit> findAllByVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserMiddlenameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserAgeContainsOrVisitPatientIdPatientUserIdUserGenderIdGenderNameContainsOrVisitStaffIdStaffUserIdUserFirstnameContainsOrVisitStaffIdStaffUserIdUserMiddlenameContainsOrVisitStaffIdStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(String mrNumber, String firstName, String middleName, String lastName, String age, String gender, String sfn, String smn, String sln, Pageable page);

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()  \n#pageable\n", countQuery = "Select count(*) from mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() ", nativeQuery = true)
    Page<MstVisit> findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    Page<MstVisit> findAllByFromToDateAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, Pageable page);

    //print mis
    List<MstVisit> findAllByVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserMiddlenameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserAgeContainsOrVisitPatientIdPatientUserIdUserGenderIdGenderNameContainsOrVisitStaffIdStaffUserIdUserFirstnameContainsOrVisitStaffIdStaffUserIdUserMiddlenameContainsOrVisitStaffIdStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(String mrNumber, String firstName, String middleName, String lastName, String age, String gender, String sfn, String smn, String sln);

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()  ", nativeQuery = true)
    List<MstVisit> findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse();

    List<MstVisit> findAllByIsActiveTrueAndIsDeletedFalse();

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  ", nativeQuery = true)
    List<MstVisit> findAllByFromToDateAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed);

    @Query(value = "SELECT count(*) FROM mst_visit where is_active = 1 and is_deleted = 0 and visit_unit_id = :unitid and visit_patient_id in(\n" + "select patient_id from mst_patient where patient_user_id in (select user_id from mst_user where user_gender_id = :genderId))", nativeQuery = true)
    long findAllByunitIsActiveTrueAndIsDeletedFalse(@Param("unitid") long unitid, @Param("genderId") long genderId);

    @Query(value = "SELECT count(*) FROM mst_visit where is_active = 1 and is_deleted = 0 and visit_unit_id = :unitid and DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() and visit_patient_id in(\n" + "select patient_id from mst_patient where patient_user_id in (select user_id from mst_user where user_gender_id = :genderId))", nativeQuery = true)
    long findAllBytodayIsActiveTrueAndIsDeletedFalse(@Param("unitid") long unitid, @Param("genderId") long genderId);

    @Query(value = "SELECT count(*) FROM mst_visit where is_active = 1 and is_deleted = 0 and visit_unit_id = :unitid and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) and visit_patient_id in(\n" + "select patient_id from mst_patient where patient_user_id in (select user_id from mst_user where user_gender_id = :genderId))", nativeQuery = true)
    long findAllByformdatetodateIsActiveTrueAndIsDeletedFalse(@Param("unitid") long unitid, @Param("genderId") long genderId, @Param("sd") String sd, @Param("ed") String ed);

    List<MstVisit> findByVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(long patientId);

    //MIS start
    Page<MstVisit> findAllByVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserMiddlenameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserAgeContainsOrVisitPatientIdPatientUserIdUserGenderIdGenderNameContainsOrVisitStaffIdStaffUserIdUserFirstnameContainsOrVisitStaffIdStaffUserIdUserMiddlenameContainsOrVisitStaffIdStaffUserIdUserLastnameContainsAndVisitUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(String mrNumber, String firstName, String middleName, String lastName, String age, String gender, String sfn, String smn, String sln, Long[] unitid, Pageable page);

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and visit_unit_id in (:unitid) and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()  \n#pageable\n", countQuery = "Select count(*) from mst_visit  where is_active = 1 and is_deleted = 0 and visit_unit_id in (:unitid) and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() ", nativeQuery = true)
    Page<MstVisit> findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse(@Param("unitid") Long[] unitid, Pageable page);

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and visit_unit_id in(:unitid) and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from mst_visit  where is_active = 1 and is_deleted = 0 and visit_unit_id in(:unitid) and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    Page<MstVisit> findAllByFromToDateAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long[] unitid, Pageable page);

    //print mis
    List<MstVisit> findAllByVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserMiddlenameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserAgeContainsOrVisitPatientIdPatientUserIdUserGenderIdGenderNameContainsOrVisitStaffIdStaffUserIdUserFirstnameContainsOrVisitStaffIdStaffUserIdUserMiddlenameContainsOrVisitStaffIdStaffUserIdUserLastnameContainsAndVisitUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(String mrNumber, String firstName, String middleName, String lastName, String age, String gender, String sfn, String smn, String sln, Long[] unitid);

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and visit_unit_id in (:unitid) and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()  ", nativeQuery = true)
    List<MstVisit> findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse(@Param("unitid") Long[] unitid);

    List<MstVisit> findAllByVisitUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(Long[] unitid);

    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and visit_unit_id = :unitid and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  ", nativeQuery = true)
    List<MstVisit> findAllByFromToDateAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long unitid);

    //Code by gayatri for mis visit report 04.09.2019
    @Query(value = "SELECT * FROM mst_visit  where is_active = 1 and is_deleted = 0 and visit_tariff_id is not null and visit_unit_id = :unitid and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  ", nativeQuery = true)
    List<MstVisit> findAllByFromToDateAndVisitTariffIdIsNotNullAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long unitid);

    List<MstVisit> findAllByVisitStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalseAndVisitUnitIdUnitIdIn(Long staffId, Long[] unitId);

    Page<MstVisit> findAllByVisitStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalseAndVisitUnitIdUnitIdIn(Long staffId, Long[] unitId, Pageable page);

    Page<MstVisit> findAllByVisitUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(Long[] unitid, Pageable page);

    //MIS end
    List<MstVisit> findByVisitPatientIdPatientIdEqualsAndVisitRegistrationSourceEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(long patientid, int source);

    List<MstVisit> findByVisitPatientIdPatientMrNoContainsAndVisitNewBornStatusTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserFirstnameContainsAndVisitNewBornStatusTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserLastnameContainsAndVisitNewBornStatusTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserMobileContainsAndVisitNewBornStatusTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(String search, String search2, String search3, String search4);

    List<MstVisit> findByVisitPatientIdPatientMrNoContainsAndVisitPatientIdPatientIsBroughtDeadTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserFirstnameContainsAndVisitPatientIdPatientIsBroughtDeadTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserLastnameContainsAndVisitPatientIdPatientIsBroughtDeadTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserMobileContainsAndVisitPatientIdPatientIsBroughtDeadTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(String search, String search2, String search3, String search4);

    @Query(value = "SELECT * FROM mst_visit mv LEFT JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id WHERE mp.patient_mr_no like %:search% AND mp.patient_is_brought_dead= TRUE  AND mp.is_active= TRUE AND mp.is_deleted= FALSE GROUP BY mp.patient_mr_no ", nativeQuery = true)
    List<MstVisit> findDistinctByVisitPatientIdPatientMrNoContainsAndVisitPatientIdPatientIsBroughtDeadTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(@Param("search") String search);

    List<MstVisit> findByVisitRegistrationSourceEqualsAndIsActiveTrueAndIsDeletedFalse(int type);

    List<MstVisit> findAllByVisitIsEmrTrueAndVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserMobileContainsAndVisitUnitIdUnitIdEquals(String mrno, String firstName, String lastName, String mbno, Long unitId);

    int findCountByVisitIsEmrTrueAndVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsAndVisitUnitIdUnitIdEqualsAndVisitIsEmrFinalizedTrue(String mrno, String firstName, String lastName, Long unitId);

    @Query(value = "select * from mst_visit v inner join mst_patient mp on mp.patient_id = v.visit_patient_id where v.visit_is_emr = 1 and v.visit_unit_id=:unitId and date(v.created_date) = :ed  \n#pageable\n", countQuery = " select count(*) from mst_visit v inner join mst_patient mp on mp.patient_id = v.visit_patient_id where v.visit_is_emr = 1 and v.visit_unit_id=:unitId and date(v.created_date) = :ed  ", nativeQuery = true)
    Page<MstVisit> findAllEMRVisitPatientListByCreatedDate(@Param("ed") String ed, @Param("unitId") long unitId, Pageable page);

    @Query(value = "select count(*) from mst_visit v inner join mst_patient mp on mp.patient_id = v.visit_patient_id where v.visit_is_emr = 1 and v.visit_is_emr_finalized = 0 and v.visit_unit_id=:unitId and date(mp.created_date) = :ed ", nativeQuery = true)
    int findInQueueCount(@Param("ed") String ed, @Param("unitId") long unitId);

    @Query(value = "select count(*) from mst_visit v inner join mst_patient mp on mp.patient_id = v.visit_patient_id where v.visit_is_emr = 1 and v.visit_is_emr_finalized = 1 and v.visit_unit_id=:unitId and date(mp.created_date) = :ed ", nativeQuery = true)
    int findInAddressedCount(@Param("ed") String ed, @Param("unitId") long unitId);

    MstVisit findByVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long id);

    @Modifying
    @org.springframework.transaction.annotation.Transactional
    @Query(value = "UPDATE mst_visit mv SET mv.is_visit_urgent=:VisitPriority,mv.is_visit_urgent_comments=:VisitPriorityComment WHERE mv.visit_id=:visitId", nativeQuery = true)
    int updateVisitPriority(@Param("visitId") Long visitId, @Param("VisitPriority") Integer VisitPriority, @Param("VisitPriorityComment") String VisitPriorityComment);

    Page<MstVisit> findAllEMRVisitPatientListByVisitDate(@Param("patientId") long patientId, @Param("followupDay") long followupDay, Pageable page);

//    @Query(value = "select * from mst_visit v inner join mst_patient mp on mp.patient_id = v.visit_patient_id where v.visit_patient_id=:patientId and v.opd_visit_type != 0", nativeQuery = true)
//    List<MstVisit> getByVisitPatientIdPatientIdEquals(@Param("patientId") Long patientId);


//    @Query(value = "select * from mst_visit v " +
//            " inner join mst_patient mp on mp.patient_id = v.visit_patient_id " +
//            " inner join trn_appointment tra on tra.appointment_patient_id = mp.patient_id" +
//            " where v.visit_patient_id= :patientId and v.opd_visit_type != 0", nativeQuery = true)
//    List<MstVisit> getByVisitPatientIdPatientIdEquals(@Param("patientId") Long patientId);


    @Query(value = "select * from mst_visit v" +
            "    inner join mst_patient mp on mp.patient_id = v.visit_patient_id" +
            "    inner join temr_timeline tt ON tt.timeline_visit_id = v.visit_id" +
            "    inner join trn_appointment tra on tra.appointment_timeline_id = tt.timeline_id" +
            "    where v.visit_patient_id= :patientId and v.opd_visit_type != 0", nativeQuery = true)
    List<MstVisit> getByVisitPatientIdPatientIdEqualsForDemographic(@Param("patientId") Long patientId);

    @Query(value = "select v.created_date as createdDate, u.unit_name as unitName, us.user_fullname as userFullname, " +
            "v.visit_type as visitType, tra.is_in_person as isInPerson, t.title_name as titleName, us.user_firstname as userFirstname, " +
            "us.user_middlename as userMullname, us.user_lastname as userLastname, v.visit_patient_id as visitPatientId from mst_visit v" +
            " inner join mst_patient mp on mp.patient_id = v.visit_patient_id" +
            " inner join temr_timeline tt ON tt.timeline_visit_id = v.visit_id" +
            " inner join mst_unit u ON u.unit_id = v.visit_unit_id" +
            " inner join mst_staff s ON s.staff_id = v.visit_staff_id" +
            " inner join mst_user us ON us.user_id = s.staff_user_id" +
            " inner join mst_title t ON t.title_id = us.user_title_id" +
            " inner join trn_appointment tra on tra.appointment_timeline_id = tt.timeline_id" +
            " where v.visit_patient_id= :patientId and v.opd_visit_type != 0", nativeQuery = true)
    List<Object[]> getByVisitPatientIdPatientIdEquals(@Param("patientId") Long patientId);
}
