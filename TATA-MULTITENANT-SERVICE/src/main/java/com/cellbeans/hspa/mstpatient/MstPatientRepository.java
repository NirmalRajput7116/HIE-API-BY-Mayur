package com.cellbeans.hspa.mstpatient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MstPatientRepository extends JpaRepository<MstPatient, Long> {


    List<MstPatient> findByPatientUserId(Long patientId);

    MstPatient findAllBypuuId(String puuId);

    MstPatient findByPatientMrNoEqualsAndIsActiveTrueAndIsDeletedFalse(String qString);

    Page<MstPatient> findByPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    @Query(value = "select patient_id from mst_patient where patient_user_id=:patient_user_id", nativeQuery = true)
    Long findPatientIdByUserId(@Param("patient_user_id") Long userId);

    @Query(value = "SELECT mu.user_driving_no, mp.* from mst_patient mp INNER JOIN mst_user mu ON mu.user_id = mp.patient_user_id WHERE mp.is_active=1 AND mp.is_deleted=0 AND mu.user_driving_no LIKE %:name% GROUP BY mu.user_driving_no #pageable",
            countQuery = "SELECT COUNT(DISTINCT(mu.user_driving_no)) from mst_patient mp INNER JOIN mst_user mu ON mu.user_id = mp.patient_user_id WHERE mp.is_active=1 AND mp.is_deleted=0 AND mu.user_driving_no LIKE %:name% ", nativeQuery = true)
    List<MstPatient> findByDistinctDrivingNoContainsAndIsActiveTrueAndIsDeletedFalse(@Param("name") String name);

    Page<MstPatient> findByPatientMrNoEqualsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    @Query(value = "select * from mst_patient t where t.patient_user_id =:patientUserId ", nativeQuery = true)
    MstPatient findByPatientUserIdUserId(@Param("patientUserId") Long patientUserId);

    Page<MstPatient> findByPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstPatient> findByPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstPatient> findByPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstPatient> findAllByIsDeleted(Boolean isDeleted, Pageable page);

    //Repo for SMS Code For IILDS in (Confirm Admission By RohanAnd Chetan 25.07.2019) Start
    MstPatient findByPatientId(Long patientId);
//Repo for SMS Code For IILDS in (Confirm Admission By RohanAnd Chetan 25.07.2019) End
    // List<MstPatient> findByPatientMrNoContainsAndPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse()

    Page<MstPatient> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    MstPatient findByPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(String patientMrNo);

    MstPatient findByPatientUserIdUserIdEqualsAndIsActiveTrueAndIsDeletedFalse(long userId);

    MstPatient findOneByPatientMrNoEqualsAndIsActiveTrueAndIsDeletedFalse(String patientMrNo);

    //Page<MstPatient> findByPatientMrNoIsActiveTrueAndIsDeletedFalse(String mrno,Pageable page);
    List<MstPatient> findByPatientMrNoContains(String key);

    /*Page<MstPatient> findByPatientMrNoContainsOrPatienUserFirstnamtUserIdeContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String PatientMrNo, String UserFirstname,String UserLastname,String UserMobile, Pageable page);*/

    Page<MstPatient> findByPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(String firstname, String middlename, String lastname, Pageable page);

    //Authod: Mohit
    List<MstPatient> findAllByPatientMrNoContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String mrNumber, String firstName, String lastName, String mobileNumber);

    //Patient or Mr No.--Vaibhav
    Page<MstPatient> findByPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsOrPatientMrNoContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String firstname, String middlename, String lastname, String PatientMrno, String MobileNumber, Pageable page);

    //Patient or Mr No or National Id.--Vaibhav
    Page<MstPatient> findByPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsOrPatientMrNoContainsOrPatientUserIdUserMobileContainsOrPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalse(String firstname, String middlename, String lastname, String PatientMrno, String MobileNumber, String NationalId, Pageable page);

    //MIS
    //all search
    Page<MstPatient> findAllByPatientMrNoContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String mrNumber, String firstName, String middleName, String lastName, String mobileNumber, Pageable page);

    //today
    @Query(value = "SELECT * FROM mst_patient  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()  #pageable", countQuery = "Select count(*) from mst_patient  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() ", nativeQuery = true)
    Page<MstPatient> findAllByCreatedDateContainsIsActiveTrueAndIsDeletedFalse(Pageable page);

    //from to search
    @Query(value = "SELECT * FROM mst_patient  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)   #pageable", countQuery = "Select count(*) from mst_patient  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    Page<MstPatient> findAllByFromToDateAndIActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, Pageable page);

    //print MIS
    //all search
    List<MstPatient> findAllByPatientMrNoContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(String mrNumber, String firstName, String middleName, String lastName, String mobileNumber);

    //today search
    @Query(value = "SELECT * FROM mst_patient  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() ", nativeQuery = true)
    List<MstPatient> findAllByCreatedDateContainsIsActiveTrueAndIsDeletedFalse();

    //all list
    List<MstPatient> findAllByIsActiveTrueAndIsDeletedFalse();

    //from to search
    @Query(value = "SELECT * FROM mst_patient  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  ", nativeQuery = true)
    List<MstPatient> findAllByFromToDateAndIActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed);

    // new search by tata requirment
    List<MstPatient> findByPatientUserIdUserUidEqualsAndIsActiveTrueAndIsDeletedFalse(String nationalid);

    List<MstPatient> findByPatientUserIdUserMobileEqualsAndIsActiveTrueAndIsDeletedFalse(String mobileno);

    List<MstPatient> findByPatientUserIdUserEmailEqualsAndIsActiveTrueAndIsDeletedFalse(String emailid);

    List<MstPatient> findByPatientUserIdUserGpMarNoEqualsAndIsActiveTrueAndIsDeletedFalse(String mykadid);
//
//    Jay : for Patient search
//    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where t.patientMrNo like %:mrNumber% and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
//    List<MstPatient> findByVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("mrNumber") String mrNumber, Pageable page);

    // Chetan  : for Patient search 15.09.2019 for bill refund
    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge,t.patientId) from MstVisit mv inner join mv.visitPatientId t where mv.visitTariffId IS NOT NULL and mv.visitPatientId=t.patientId and t.patientMrNo like %:mrNumber% and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<MstPatient> findByVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(
            @Param("mrNumber") String mrNumber, Pageable page);

    @Query(value = "SELECT distinct t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge,t.patientId from MstVisit mv inner join mv.visitPatientId t where mv.visitTariffId IS NOT NULL and mv.visitPatientId=t.patientId and t.patientMrNo like %:mrNumber% and t.isActive=true AND t.isDeleted = false AND t.isEmergency=false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(
            @Param("mrNumber") String mrNumber);

    //OPDName
    @Query(value = "SELECT distinct t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge,t.patientId from MstVisit mv inner join mv.visitPatientId t where mv.visitTariffId IS NOT NULL and mv.visitPatientId=t.patientId and (t.patientUserId.userLastname like %:searchString% or t.patientUserId.userFirstname like %:searchString%) and t.isActive=true AND t.isDeleted = false AND t.isEmergency=false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(
            @Param("searchString") String searchString);

    //OPDPhone
    @Query(value = "SELECT distinct t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge,t.patientId from MstVisit mv inner join mv.visitPatientId t where mv.visitTariffId IS NOT NULL and mv.visitPatientId=t.patientId and t.patientUserId.userResidencePhone like %:searchString% and t.isActive=true AND t.isDeleted = false AND t.isEmergency=false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientPhoneContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(
            @Param("searchString") String searchString);

    //OPDMobile
    @Query(value = "SELECT distinct t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge,t.patientId from MstVisit mv inner join mv.visitPatientId t where mv.visitTariffId IS NOT NULL and mv.visitPatientId=t.patientId and t.patientUserId.userMobile like %:searchString% and t.isActive=true AND t.isDeleted = false AND t.isEmergency=false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(
            @Param("searchString") String searchString);

    //OPDIdNumber
    @Query(value = "SELECT distinct t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge,t.patientId from MstVisit mv inner join mv.visitPatientId t where mv.visitTariffId IS NOT NULL and mv.visitPatientId=t.patientId and (t.patientUserId.userUid like %:searchString% or t.patientUserId.userPfNo like %:searchString% or t.patientUserId.userPassportNo like %:searchString% or t.patientUserId.userDrivingNo like %:searchString% or t.patientUserId.userPanNo like %:searchString%) and t.isActive=true AND t.isDeleted = false AND t.isEmergency=false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientIdNumberContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(
            @Param("searchString") String searchString);

    //OPDEmail
    @Query(value = "SELECT distinct t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge,t.patientId from MstVisit mv inner join mv.visitPatientId t where mv.visitTariffId IS NOT NULL and mv.visitPatientId=t.patientId and (t.patientUserId.userEmail like %:searchString%) and t.isActive=true AND t.isDeleted = false AND t.isEmergency=false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientIdEmailContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(
            @Param("searchString") String searchString);

    @Query(value = "SELECT distinct t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge,t.patientId from MstVisit mv inner join mv.visitPatientId t where mv.visitTariffId IS NOT NULL and mv.visitPatientId=t.patientId and (t.patientUserId.userLastname like %:lastName% or t.patientUserId.userFirstname like %:firstName%) and t.isActive=true AND t.isDeleted = false AND t.isEmergency=false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientUserIdUserFullnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(
            @Param("firstName") String firstName, @Param("lastName") String lastName, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(0L, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge, t.patientId) from MstPatient t where (t.patientUserId.userLastname like %:lastName% or t.patientUserId.userFirstname like %:firstName%) and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("firstName") String firstName, @Param("lastName") String lastName, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where t.patientUserId.userMobile like %:mobileNumber% and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<Object []> findByVisitPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("mobileNumber") String mobileNumber, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where t.patientUserId.userResidencePhone like %:phoneNumber% and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientUserIdUserResidencePhoneContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("phoneNumber") String phoneNumber, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where (t.patientUserId.userUid like %:IdNumber% or t.patientUserId.userPfNo like %:IdNumber% or t.patientUserId.userPassportNo like %:IdNumber% or t.patientUserId.userDrivingNo like %:IdNumber% or t.patientUserId.userPanNo like %:IdNumber%) and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("IdNumber") String IdNumber, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where (t.patientUserId.userEmail like %:mail%) and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<Object[]> findByVisitPatientIdPatientUserIdUserEmailContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("mail") String mail, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where (t.patientUserId.userDrivingNo like %:mail%) and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<MstPatient> findByVisitPatientIdPatientUserIdUserDrivingNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("mail") String mail, Pageable page);

    //get emergency patient
    Page<MstPatient> findAllByIsDeletedAndPatientErNoNotNull(Boolean isDeleted, Pageable page);

    //created by seetanshu
    //get patient unique All list
    @Query(value = "select * from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId) and m.is_active=1 AND m.is_deleted=0 and  #pageable", countQuery = "select count(*) from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId) and m.is_active=1 AND m.is_deleted=0 ", nativeQuery = true)
    Page<MstPatient> findAllPatientIdAndPatientUserId(@Param("unitId") long unitId, Pageable page);

    //get patient unique All list
    @Query(value = "select * from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId) and m.is_active=1 AND m.is_deleted=0 and  #pageable", countQuery = "select count(*) from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId) and m.is_active=1 AND m.is_deleted=0 and m.is_register_at_camp=1 ", nativeQuery = true)
    Page<MstPatient> findAllCampPatientIdAndPatientUserId(@Param("unitId") long unitId, Pageable page);

    //EMR patient search by default
    @Query(value = "select * from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1) and m.is_active=1 AND m.is_deleted=0 #pageable", countQuery = "select count(*) from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1) and m.is_active=1 AND m.is_deleted=0 ", nativeQuery = true)
    Page<MstPatient> findAllEMRPatientIdAndPatientUserId(@Param("unitId") long unitId, Pageable page);

    //get patient unique list by mrno
    @Query(value = "select * from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId) and m.is_active=1 AND m.is_deleted=0 and m.patient_mr_no like %:mrno%     #pageable", countQuery = "select count(*) from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId) and m.is_active=1 AND m.is_deleted=0 and  m.patient_mr_no like %:mrno% ", nativeQuery = true)
    Page<MstPatient> findAllPatientIdAndPatientMrNo(@Param("mrno") String mrno, @Param("unitId") long unitId, Pageable page);

    //EMR patient serach by MR no
    @Query(value = "select * from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1) and  m.is_active=1 AND m.is_deleted=0 and m.patient_mr_no like %:mrno%     #pageable", countQuery = "select count(*) from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1) and m.is_active=1 AND m.is_deleted=0 and  m.patient_mr_no like %:mrno% ", nativeQuery = true)
    Page<MstPatient> findAllEMRPatientIdAndPatientMrNo(@Param("mrno") String mrno, @Param("unitId") long unitId, Pageable page);

    //get patient unique list by patinet name
    @Query(value = "select * from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and (u.user_firstname like %:pname% or u.user_lastname like %:pname%)   #pageable", countQuery = "select count(*) from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and (u.user_firstname like %:pname% or u.user_lastname like %:pname%) ", nativeQuery = true)
    Page<MstPatient> findAllPatientIdAndPatientUserIdUserFirstname(@Param("pname") String pname, @Param("unitId") long unitId, Pageable page);

    //get EMR patient unique list by patinet name
    @Query(value = "select * from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1) and m.is_active=1 AND m.is_deleted=0 and (u.user_firstname like %:pname% or u.user_lastname like %:pname%)   #pageable", countQuery = "select count(*) from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1)  and m.is_active=1 AND m.is_deleted=0 and (u.user_firstname like %:pname% or u.user_lastname like %:pname%) ", nativeQuery = true)
    Page<MstPatient> findAllEMRPatientIdAndPatientUserIdUserFirstname(@Param("pname") String pname, @Param("unitId") long unitId, Pageable page);

    //get patient unique list by mobile number
    @Query(value = "select * from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and u.user_mobile like %:mno%    #pageable", countQuery = "select count(*) from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and u.user_mobile like %:mno%  ", nativeQuery = true)
    Page<MstPatient> findAllPatientIdAndPatientUserIdUserMobile(@Param("mno") String mno, @Param("unitId") long unitId, Pageable page);

    //get patient unique list by User Driving No
    @Query(value = "select * from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and u.user_driving_no like %:empno%    #pageable", countQuery = "select count(*) from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and u.user_driving_no like %:empno%  ", nativeQuery = true)
    Page<MstPatient> findAllPatientIdAndPatientUserIdUserDrivingNo(@Param("empno") String empno, @Param("unitId") long unitId, Pageable page);

    //get patient unique list by Patient Type
    @Query(value = "select * from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and m.patient_type =:patientType  #pageable", countQuery = "select count(*) from mst_patient m,mst_user u where m.patient_user_id=u.user_id and m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and m.patient_type =:patientType ", nativeQuery = true)
    Page<MstPatient> findAllPatientIdAndPatientType(@Param("patientType") String patientType, @Param("unitId") long unitId, Pageable page);

    //get EMR patient unique list by phone number
    @Query(value = "select * from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and u.user_office_phone_code like %:mno%    #pageable", countQuery = "select count(*) from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and u.user_office_phone_code like %:mno%  ", nativeQuery = true)
    Page<MstPatient> findAllEMRPatientIdAndPatientUserIdUserPhone(@Param("mno") String mno, @Param("unitId") long unitId, Pageable page);

    //get EMR patient unique list by email
    @Query(value = "select * from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and u.user_email like %:mno%    #pageable", countQuery = "select count(*) from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and  u.user_email like %:mno%  ", nativeQuery = true)
    Page<MstPatient> findAllEMRPatientIdAndPatientUserIdUserEmail(@Param("mno") String mno, @Param("unitId") long unitId, Pageable page);

    //get EMR patient unique list by mobile number
    @Query(value = "select * from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1)  and m.is_active=1 AND m.is_deleted=0 and u.user_mobile like %:mno%    #pageable", countQuery = "select count(*) from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1)  and m.is_active=1 AND m.is_deleted=0 and u.user_mobile like %:mno%  ", nativeQuery = true)
    Page<MstPatient> findAllEMRPatientIdAndPatientUserIdUserMobile(@Param("mno") String mno, @Param("unitId") long unitId, Pageable page);

    //get patient unique list by mobile number
    @Query(value = "select * from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and u.user_uid like %:nid%  #pageable", countQuery = "select count(*) from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId)  and m.is_active=1 AND m.is_deleted=0 and u.user_uid like %:nid% ", nativeQuery = true)
    Page<MstPatient> findAllPatientIdAndPatientUserIdUserUid(@Param("nid") String nid, @Param("unitId") long unitId, Pageable page);

    //get EMR patient unique list by mobile number
    @Query(value = "select * from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1) and m.is_active=1 AND m.is_deleted=0 and u.user_uid like %:nid%  #pageable", countQuery = "select count(*) from mst_patient m,mst_user u where m.patient_user_id=u.user_id and  m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1)  and m.is_active=1 AND m.is_deleted=0 and u.user_uid like %:nid% ", nativeQuery = true)
    Page<MstPatient> findAllEMRPatientIdAndPatientUserIdUserUid(@Param("nid") String nid, @Param("unitId") long unitId, Pageable page);

    //get patient unique list by start and end date
    @Query(value = "select * from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId) and m.is_active=1 AND m.is_deleted=0 and date(created_date) between :sd and :ed  #pageable", countQuery = "select count(*) from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId) and m.is_active=1 AND m.is_deleted=0 and date(created_date) between :sd and :ed  ", nativeQuery = true)
    Page<MstPatient> findAllByCreatedDateBetween(@Param("sd") String sd, @Param("ed") String ed, @Param("unitId") long unitId, Pageable page);

    //get EMR patient unique list by start and end date
    @Query(value = "select * from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1) and m.is_active=1 AND m.is_deleted=0 and date(created_date) between :sd and :ed  #pageable", countQuery = "select count(*) from mst_patient m where m.patient_id in (select DISTINCT (v.visit_patient_id) from mst_visit v where v.visit_unit_id=:unitId and v.visit_is_emr=1) and m.is_active=1 AND m.is_deleted=0 and date(created_date) between :sd and :ed  ", nativeQuery = true)
    Page<MstPatient> findAllEMRPatientListByCreatedDateBetween(@Param("sd") String sd, @Param("ed") String ed, @Param("unitId") long unitId, Pageable page);
    /////////

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where t.patientMrNo like %:mrNumber% and t.isEmergency=true and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<MstPatient> findByVisitPatientIdPatientMrNoContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("mrNumber") String mrNumber, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where (t.patientUserId.userLastname like %:lastName% or t.patientUserId.userFirstname like %:firstName%) and t.isEmergency=true  and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<MstPatient> findByVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("firstName") String firstName, @Param("lastName") String lastName, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where t.patientUserId.userMobile like %:mobileNumber% and t.isEmergency=true  and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<MstPatient> findByVisitPatientIdPatientUserIdUserResidencePhoneContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("mobileNumber") String mobileNumber, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where t.patientUserId.userResidencePhone like %:phoneNumber% and t.isEmergency=true  and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<MstPatient> findByVisitPatientIdPatientUserIdUserMobileContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("phoneNumber") String phoneNumber, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where (t.patientUserId.userUid like %:IdNumber% or t.patientUserId.userPfNo like %:IdNumber% or t.patientUserId.userPassportNo like %:IdNumber% or t.patientUserId.userDrivingNo like %:IdNumber% or t.patientUserId.userPanNo like %:IdNumber%) and t.isEmergency=true  and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<MstPatient> findByVisitPatientIdPatientUserIdUserUidContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("IdNumber") String IdNumber, Pageable page);

    @Query(value = "SELECT new com.cellbeans.hspa.mstvisit.MstVisitSearchDto(t.patientId, t.patientUserId.userFirstname, t.patientUserId.userLastname, t.patientMrNo,t.patientUserId.userMobile,t.patientUserId.userAge) from MstPatient t where (t.patientUserId.userEmail like %:mail%) and t.isEmergency=true  and t.isActive=true AND t.isDeleted = false order by t.patientId DESC ")
    List<MstPatient> findByVisitPatientIdPatientUserIdUserEmailContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(@Param("mail") String mail, Pageable page);

    List<MstPatient> findAllByPatientMrNoContainsAndPatientUserIdUserUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String mrNumber, long unitId, Pageable page);

    List<MstPatient> findAllByPatientUserIdUserFirstnameContainsOrPatientUserIdUserLastnameContainsAndPatientUserIdUserUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String firstName, String lastName, long unitId, Pageable page);

    List<MstPatient> findAllByPatientUserIdUserMobileContainsAndPatientUserIdUserUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String mobileNumber, long unitId, Pageable page);

    List<MstPatient> findAllByPatientMrNoContainsOrPatientUserIdUserMobileContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserLastnameContainsAndPatientUserIdUserUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(String key, String mob, String first, String last, Long UnitId);

    @Query(value = "SELECT * FROM mst_patient mp " +
            "LEFT JOIN mst_user mu ON mu.user_id = mp.patient_user_id " +
            "LEFT JOIN mst_unit mun ON mun.unit_id = mu.user_unit_id " +
            "LEFT JOIN mst_org mo ON mo.org_id = mun.unit_org_id " +
            "WHERE mu.user_name = :username AND mu.password = :password AND mun.unit_org_id = :org_id AND mu.is_deleted=0 ", nativeQuery = true)
    MstPatient findByPatientUserIdUserNameEqualsAndPatientUserIdPasswordEqualsAndAndPatientUserIdIsActiveTrue(
            @Param("username") String username, @Param("password") String password, @Param("org_id") String org_id);
}
            
