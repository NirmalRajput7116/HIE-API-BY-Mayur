package com.cellbeans.hspa.TrnAdmissionRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
//import java.util.Date;

public interface TrnAdmissionRequestRepository extends JpaRepository<TrnAdmissionRequest, Long> {

    @Transactional
    @Modifying
    @Query("update TrnAdmissionRequest set admissionrequestIsConfirm=true where admissionrequestId=:admissionrequestId")
    Integer updateAdmissionrequestIsConfirmed(@Param("admissionrequestId") Long admissionrequestId);

    Page<TrnAdmissionRequest> findByAdmissionrequestUserIdUserFirstnameContainsAndAdmissionrequestUnitIdUnitIdOrAdmissionrequestUserIdUserLastnameContainsAndAdmissionrequestUnitIdUnitIdAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(String fname, long unitId1, String lname, long unitId2, Pageable page);

    //
//    Page<TrnAppointment> findAllByAppointmentMrNoContainsAndAppointmentUnitIdUnitIdEqualsAndIsActiveTrueAndAppointmentIsCancelledFalse(String mrno, Long unitId, Pageable page);
//
    Page<TrnAdmissionRequest> findAllByAdmissionrequestPatientIdPatientMrNoContainsAndAdmissionrequestUnitIdUnitIdEqualsAndIsActiveTrueAndAdmissionrequestIsCancelledTrue(String mrno, Long unitId, Pageable page);

    //
    Page<TrnAdmissionRequest> findByAdmissionrequestUnitIdUnitIdAndAdmissionrequestUserIdUserMobileContainsAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(long unitId, String mobile, Pageable page);

    //
//    Page<TrnAdmissionRequest> findByAdmissionrequestUnitIdUnitIdAndAdmissionrequestArIdArIdAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(long unitId, long arid, Pageable page);
//
    Page<TrnAdmissionRequest> findByAdmissionrequestUnitIdUnitIdEqualsAndAdmissionrequestStaffIdStaffIdAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(long unitId, long userId, Pageable page);

    //
//    Page<TrnAppointment> findByAppointmentUnitIdUnitIdEqualsAndAppointmentStaffIdStaffIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNullAndIsActiveTrueAndAppointmentIsCancelledFalse(long unitId, long userId, Pageable page);
//
//    Page<TrnAppointment> findByAppointmentUnitIdUnitIdEqualsAndAppointmentStaffIdStaffIdAndAppointmentIsConfirmFalseAndAppointmentIsBlockFalseAndIsActiveTrueAndAppointmentIsCancelledFalse(long unitId, long userId, Pageable page);
//
    Page<TrnAdmissionRequest> findByAdmissionrequestUnitIdUnitIdAndAdmissionrequestDepartmentIdDepartmentIdAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(long unitId, long departmentId, Pageable page);

    //
    Page<TrnAdmissionRequest> findByAdmissionrequestUnitIdUnitIdAndAdmissionrequestUserIdUserUidContainsAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(long unitId, String uid, Pageable page);

    //
    Page<TrnAdmissionRequest> findByAdmissionrequestDateBetweenAndIsActiveTrueAndIsDeletedFalseAndAdmissionrequestIsCancelledFalse(Date strdate, Date enddate, Pageable Page);

    //
    Page<TrnAdmissionRequest> findByCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalseAndAdmissionrequestIsCancelledFalse(Date bookedstrdate, Date bookedenddate, Pageable Page);

    //
////    Page<TrnAppointment> findByAppointmentUserIdUserFirstnameContainsOrAppointmentUserIdUserLastnameContainsOrAppointmentUserIdUserMobileContainsOrAppointmentUserIdUserUidContainsAndIsActiveTrueAndAppointmentIsCancelledFalse(String fname,String lname,String mobile,String uid, Pageable Page);
////    Page<TrnAppointment> findByAppointmentPatientIdPatientMrNoContainsOrAppointmentUserIdUserFirstnameContainsOrAppointmentUserIdUserLastnameContainsOrAppointmentUserIdUserMobileContainsOrAppointmentUserIdUserUidContainsAndIsActiveTrueAndAppointmentIsCancelledFalse(String mrno,String fname,String lname,String mobile,String nationalid, Pageable Page);
////main list
    Page<TrnAdmissionRequest> findAllByAdmissionrequestUnitIdUnitIdAndIsActiveTrueAndAndAdmissionrequestIsCancelledFalse(long unitId, Pageable page);

    //
//    Page<TrnAppointment> findAllByAppointmentUnitIdUnitIdAndAppointmentIsBlockFalseAndIsActiveTrueAndIsDeletedFalse(long unitId, Pageable page);
//
    Page<TrnAdmissionRequest> findAllByAdmissionrequestUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(long unitId, Pageable page);
//
//
////    @Query(value = "select appointmentSlot from TrnAppointment tp where tp.appointmentDate =:appDate")
////    List<String> findByAppAndAppointmentDate(@Param("appDate") Date appDate,@Param("appEndDate") Date appEndDate);
//
//    @Query(value = "select appointment_Slot from trn_appointment tp where tp.appointment_Date = :appDate AND appointment_Staff_Id =:staffId AND tp.appointment_is_cancelled = 0", nativeQuery = true)
//    List<String> findAllByAppointmentDateAndIsDeletedIsFalse(@Param("appDate") String appDate, @Param("staffId") Long staffId);
//
//    @Query(value = "select appointment_Slot from trn_appointment tp where tp.appointment_Date = :appDate AND appointment_Staff_Id =:staffId AND tp.appointment_is_cancelled = 0 AND tp.appointment_is_block = 1", nativeQuery = true)
//    List<String> findAllByAppointmentDateAndIsDeletedIsFalseAndAppointmentIsBlockTrue(@Param("appDate") String appDate, @Param("staffId") Long staffId);
//
//    @Query(value = "select appointment_Slot from trn_appointment tp where tp.appointment_Date = :appDate AND appointment_Staff_Id =:staffId AND tp.appointment_is_cancelled = 0 AND tp.appointment_is_block = 0", nativeQuery = true)
//    List<String> findAllByAppointmentDateAndIsDeletedIsFalseAndAppointmentIsBlockFalse(@Param("appDate") String appDate, @Param("staffId") Long staffId);
//
//    /*        List<TrnAppointment> findByUserFullnameContains(String key);*/
//
//    //appoinment list for MIS
//    //normal list
//    Page<TrnAppointment> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//
//    //today list
//    @Query(value = "SELECT * FROM trn_appointment  where is_active = 1 and is_deleted = 0  and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()  \n#pageable\n", countQuery = "Select count(*) from trn_appointment  where is_active = 1 and appointment_is_cancelled = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() ", nativeQuery = true)
//    Page<TrnAppointment> findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse(Pageable page);
//
//    //all search
//    Page<TrnAppointment> findAllByAppointmentMrNoContainsOrAppointmentUserIdUserFirstnameContainsOrAppointmentUserIdUserMiddlenameContainsOrAppointmentUserIdUserLastnameContainsOrAppointmentStaffIdStaffUserIdUserFirstnameContainsOrAppointmentStaffIdStaffUserIdUserMiddlenameContainsOrAppointmentStaffIdStaffUserIdUserLastnameContainsOrAppointmentDepartmentIdDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalse(String mrno, String ufn, String umd, String uln, String sfn, String smn, String sln, String dept, Pageable page);
//
//    //from to date search
//    @Query(value = "SELECT * FROM trn_appointment  where is_active = 1 and is_deleted = 0  and  DATE_FORMAT(created_date,'%y-%m-%d')  BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from trn_appointment  where is_active = 1 and appointment_is_cancelled = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE ) and CAST(:ed as DATE)  ", nativeQuery = true)
//    Page<TrnAppointment> findAllByCreatedDateAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, Pageable page);
//
//    //print MIS
//    //all search
//    List<TrnAppointment> findAllByAppointmentMrNoContainsOrAppointmentUserIdUserFirstnameContainsOrAppointmentUserIdUserMiddlenameContainsOrAppointmentUserIdUserLastnameContainsOrAppointmentStaffIdStaffUserIdUserFirstnameContainsOrAppointmentStaffIdStaffUserIdUserMiddlenameContainsOrAppointmentStaffIdStaffUserIdUserLastnameContainsOrAppointmentDepartmentIdDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalse(String mrno, String ufn, String umd, String uln, String sfn, String smn, String sln, String dept);
//
//
//    //all search
//    Page<TrnAppointment> findAllByAppointmentUnitIdUnitIdInAndAppointmentMrNoContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserFirstnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserMiddlenameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserLastnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserFirstnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserMiddlenameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserLastnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentDepartmentIdDepartmentNameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalse(Long[] unitId, String mrno, String ufn, String umd, String uln, String sfn, String smn, String sln, String dept, Pageable page);
//
//    //today list
//    @Query(value = "SELECT * FROM trn_appointment  where appointment_is_cancelled = 1 and is_active = 1 and is_deleted = 0  and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() and admission_unit_id in (:unitId)  \n#pageable\n", countQuery = "Select count(*) from trn_appointment  where is_active = 1 and appointment_is_cancelled = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() and admission_unit_id in (:unitId) ", nativeQuery = true)
//    Page<TrnAppointment> findAllByCreatedDateContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalse(@Param("unitId") Long[] unitId, Pageable page);
//
//    //normal list
//    Page<TrnAppointment> findAllByAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndAppointmentUnitIdUnitIdIn(Long[] unitId, Pageable page);
//
//    //from to date search
//    @Query(value = "SELECT * FROM trn_appointment a where a.is_active = 1 and a.is_deleted = 0 and a.appointment_is_cancelled = 1 and a.appointment_unit_id in (:unitId) and DATE_FORMAT(a.created_date,'%y-%m-%d')  BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from trn_appointment a where a.is_active = 1 and a.is_deleted = 0 and a.appointment_is_cancelled = 1 and  a.appointment_unit_id in (:unitId) and  DATE_FORMAT(a.created_date,'%y-%m-%d')  BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  ", nativeQuery = true)
//    Page<TrnAppointment> findAllByCreatedDateAndIsCanceledAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitId") Long[] unitId, Pageable page);
//
//    //print Canceled MIS
//    //all search
//    List<TrnAppointment> findAllByAppointmentMrNoContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserFirstnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserMiddlenameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserLastnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserFirstnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserMiddlenameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserLastnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentDepartmentIdDepartmentNameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndAppointmentUnitIdUnitIdIn(String mrno, String ufn, String umd, String uln, String sfn, String smn, String sln, String dept, Long[] unitId);
//
//    //today list
//    @Query(value = "SELECT * FROM trn_appointment  where appointment_is_cancelled = 1 and is_active = 1 and is_deleted = 0  and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() and admission_unit_id in (:unitId) ", nativeQuery = true)
//    List<TrnAppointment> findAllByCreatedDateContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalse(@Param("unitId") Long[] unitId);
//
//    //normal list
//    List<TrnAppointment> findAllByAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndAppointmentUnitIdUnitIdIn(Long[] unitId);
//
//    //from to date search
//    @Query(value = "SELECT * FROM trn_appointment where is_active = 1 and is_deleted = 0 and appointment_is_cancelled = 1 and  DATE_FORMAT(created_date,'%y-%m-%d')  BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) and admission_unit_id in (:unitId) ", nativeQuery = true)
//    List<TrnAppointment> findAllByCreatedDateAndIsCanceledAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitId") Long[] unitId);
//
//    //today search
//    @Query(value = "SELECT * FROM trn_appointment  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()", nativeQuery = true)
//    List<TrnAppointment> findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse();
//
//    //normal list
//    List<TrnAppointment> findAllByIsActiveTrueAndIsDeletedFalse();
//
//    //from to date search
//    @Query(value = "SELECT * FROM trn_appointment  where is_active = 1 and is_deleted = 0  and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE ) and CAST(:ed as DATE)  ", nativeQuery = true)
//    List<TrnAppointment> findAllByCreatedDatePrintAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed);
//
//    //appoinment list for MIS
//    //normal list
//    Page<TrnAppointment> findAllByAppointmentUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(Long[] unitid, Pageable page);
//
//    //today list
//    @Query(value = "SELECT * FROM trn_appointment  where is_active = 1 and is_deleted = 0 and  appointment_unit_id in (:unitid)  and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()  \n#pageable\n", countQuery = "Select count(*) from trn_appointment  where is_active = 1 and appointment_is_cancelled = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() ", nativeQuery = true)
//    Page<TrnAppointment> findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse(@Param("unitid") Long[] unitid, Pageable page);
//
//    //all search
//    Page<TrnAppointment> findAllByAppointmentMrNoContainsOrAppointmentUserIdUserFirstnameContainsOrAppointmentUserIdUserMiddlenameContainsOrAppointmentUserIdUserLastnameContainsOrAppointmentStaffIdStaffUserIdUserFirstnameContainsOrAppointmentStaffIdStaffUserIdUserMiddlenameContainsOrAppointmentStaffIdStaffUserIdUserLastnameContainsOrAppointmentDepartmentIdDepartmentNameContainsAndAppointmentUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(String mrno, String ufn, String umd, String uln, String sfn, String smn, String sln, String dept, Long[] id, Pageable page);
//
//    //from to date search
//    @Query(value = "SELECT * FROM trn_appointment  where is_active = 1 and is_deleted = 0  and  appointment_unit_id in (:unitid)  and  DATE_FORMAT(created_date,'%y-%m-%d')  BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  \n#pageable\n", countQuery = "Select count(*) from trn_appointment  where is_active = 1 and appointment_is_cancelled = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE ) and CAST(:ed as DATE)  ", nativeQuery = true)
//    Page<TrnAppointment> findAllByCreatedDateAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long[] unitid, Pageable page);
//
//    //print MIS
//    //all search
//    List<TrnAppointment> findAllByAppointmentMrNoContainsOrAppointmentUserIdUserFirstnameContainsOrAppointmentUserIdUserMiddlenameContainsOrAppointmentUserIdUserLastnameContainsOrAppointmentStaffIdStaffUserIdUserFirstnameContainsOrAppointmentStaffIdStaffUserIdUserMiddlenameContainsOrAppointmentStaffIdStaffUserIdUserLastnameContainsOrAppointmentDepartmentIdDepartmentNameContainsAndAppointmentUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(String mrno, String ufn, String umd, String uln, String sfn, String smn, String sln, String dept, Long[] unitid);
//
//    //today search
//    @Query(value = "SELECT * FROM trn_appointment  where is_active = 1 and is_deleted = 0 and  appointment_unit_id in (:unitid) and DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE()", nativeQuery = true)
//    List<TrnAppointment> findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse(@Param("unitid") Long[] unitid);
//
//    //normal list
//    List<TrnAppointment> findAllByAppointmentUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(Long[] unitid);
//
//    //from to date search
//    @Query(value = "SELECT * FROM trn_appointment  where is_active = 1 and is_deleted = 0  and  appointment_unit_id in (:unitid) and DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE ) and CAST(:ed as DATE)  ", nativeQuery = true)
//    List<TrnAppointment> findAllByCreatedDatePrintAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long[] unitid);
//
//    TrnAppointment findOneByAppointmentPatientIdPatientIdAndAppointmentDateAndAppointmentIsCancelledFalseAndIsActiveTrue(long id, Date date);
//    //new pop up list
//    @Query(value = "select * from trn_appointment p where p.appointment_is_confirm=0 and p.appointment_is_cancelled=0 and p.appointment_is_block=0 and p.appointment_unit_id=:unitId  \n#pageable\n", countQuery = "Select count(*) from trn_appointment p where p.appointment_is_confirm=0 and p.appointment_is_block=0 and p.appointment_is_cancelledreasone is null and p.appointment_unit_id=:unitId ", nativeQuery = true)
//    Page<TrnAppointment> findAllAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNullAndAppointmentUnitIdUnitIdAndAppointmentIsBlockFalseAndIsActiveTrueAndIsDeletedFalse(@Param("unitId") long unitId, Pageable page);
//
//    //Appointment status Filters
//    //Booked Status
//   // @Query(value = "select * from trn_appointment p1 where p1.appointment_is_confirm=0 and p1.appointment_is_cancelledreasone is null and p1.appointment_is_cancelled=0 and p1.appointment_unit_id \n#pageable\n", nativeQuery = true)
//    Page<TrnAppointment> findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsBlockFalseAndAppointmentIsCancelledFalse(long unitId, Pageable page);
//    //Confirmed Status
//  //  @Query(value = "select * from trn_appointment p2 where p2.appointment_is_confirm=1 and p2.appointment_is_cancelledreasone is null and p2.appointment_is_cancelled=0 and p2.appointment_unit_id \n#pageable\n", nativeQuery = true)
//    Page<TrnAppointment> findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmTrueAndAppointmentIsCancelledreasoneIsNullAndAppointmentIsCancelledFalse(long unitId, Pageable page);
//    //Cancelled Status
//   // @Query(value = "select * from trn_appointment p3 where p3.appointment_is_confirm=0 and p3.appointment_is_cancelledreasone is not null and p3.appointment_is_cancelled=1 and p3.appointment_unit_id \n#pageable\n", nativeQuery = true)
//    Page<TrnAppointment> findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsCancelledTrue(long unitId, Pageable page);
//    //Block Status
// //   @Query(value = "select * from trn_appointment p4 where p4.appointment_is_confirm=0 and p4.appointment_is_cancelledreasone is null and p4.appointment_is_block=1 and p4.appointment_is_cancelled=0 and p4.appointment_unit_id=:unitId \n#pageable\n", countQuery = "Select count(*) from trn_appointment p4 where p4.appointment_is_confirm=0 and p4.appointment_is_cancelledreasone is null and p4.appointment_is_block=1 and p4.appointment_is_cancelled=0 and p4.appointment_unit_id=:unitId ", nativeQuery = true)
//    Page<TrnAppointment> findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsBlockTrueAndAppointmentIsCancelledFalse(long unitId, Pageable page);
//
//    @Query(value = "select * from trn_appointment p where p.appointment_is_confirm=0 and p.appointment_is_cancelled=0 and p.appointment_is_block=0 and p.appointment_unit_id=:unitId and DATE_FORMAT(appointment_date,'%y-%m-%d') = CURDATE() group by appointment_staff_id", nativeQuery = true)
//    List<TrnAppointment> findAllAppointmentIsConfirmFalseAndAppointmentreasoneIsNullAndAppointmentUnitIdUnitIdAndAppointmentIsBlockFalseAndIsActiveTrueAndIsDeletedFalse(@Param("unitId") long unitId);
//
//    @Query(value = "select * from trn_appointment p where p.appointment_is_confirm=0 and p.appointment_is_cancelled=0 and p.appointment_is_block=0 and p.appointment_unit_id=:unitId and p.appointment_Staff_Id =:staffId and DATE_FORMAT(appointment_date,'%y-%m-%d') = CURDATE()", nativeQuery = true)
//    List<TrnAppointment> findAllAppointmentIsConfirmFalseAndIsNullAndAppointmentUnitIdUnitIdAndAppointmentIsBlockFalseAndIsActiveTrueAndIsDeletedFalse(@Param("unitId") long unitId, @Param("staffId") long staffId);
}

