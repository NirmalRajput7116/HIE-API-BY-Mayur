package com.cellbeans.hspa.EmgPatientList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmgPatientListRepository extends JpaRepository<EmgPatientList, Long> {

    EmgPatientList findByEplId(Long eplId);

    EmgPatientList findAllByEplVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(long visitId);

    EmgPatientList findOneByEplId(Long eplId);

    Page<EmgPatientList> findAllByEplVisitIdVisitPatientIdIsEmergencyTrueAndEplVisitIdVisitRegistrationSourceAndIsActiveTrueAndIsDeletedFalseAndIsDischargedFalseAndIsCancelledFalse(int RegSource, Pageable page);
//    List<EmgPatientList> findAllByEplVisitIdVisitPatientIdIsEmergencyTrueAndEplVisitIdVisitRegistrationSourceAndIsActiveTrueAndIsDeletedFalseAndIsDischargedTrue(int RegSource, Pageable page);

    @Query(value = "select epl.* from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id  WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_discharged = true \n#pageable\n", countQuery = "select count(*) from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id  WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_discharged = true  ", nativeQuery = true)
    Page<EmgPatientList> findAll(Pageable page);

    @Query(value = "select epl.* from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_discharged = true  and mu.user_firstname LIKE %:qString% or mu.user_lastname LIKE %:qString%  \n#pageable\n", countQuery = "select count(*) from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_discharged = true  and mu.user_firstname LIKE %:qString% or mu.user_lastname LIKE %:qString% ", nativeQuery = true)
    Page<EmgPatientList> findAllByPatientName(@Param("qString") String qString, Pageable page);

    @Query(value = "select epl.* from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_discharged = true  and mp.patient_er_no LIKE %:qString%   \n#pageable\n", countQuery = "select count(*) from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_discharged = true  and mp.patient_er_no LIKE %qString% ", nativeQuery = true)
    Page<EmgPatientList> findAllByPatientERNo(@Param("qString") String qString, Pageable page);

    @Query(value = "select epl.* from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_discharged = true  and (DATE(epl.discharged_date) between :fdate and :tdate) \n#pageable\n", countQuery = "select count(*) from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_discharged = true and (DATE(epl.discharged_date) between :fdate and :tdate) ", nativeQuery = true)
    Page<EmgPatientList> findAllByDate(@Param("fdate") String fdate, @Param("tdate") String tdate, Pageable page);
//    List<EmgPatientList> findAllByEplVisitIdVisitPatientIdIsEmergencyTrueAndEplVisitIdVisitRegistrationSourceAndIsActiveTrueAndIsDeletedFalseAndIsDischargedTrue(Pageable page);

    List<EmgPatientList> findAllByEplVisitIdVisitPatientIdPatientMrNoAndEplVisitIdVisitPatientIdPatientErNoAndIsActiveTrueAndIsDeletedFalse(String mrNo, String erNo);

    List<EmgPatientList> findAllByEplVisitIdVisitIdAndEplVisitIdVisitPatientIdPatientMrNoAndEplVisitIdVisitPatientIdPatientErNoAndIsActiveTrueAndIsDeletedFalse(long visitId, String mrNo, String erNo);

    @Query(value = " select epl.* from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_discharged = false and mp.patient_id= :patientId ", nativeQuery = true)
    EmgPatientList findAllByPatientId(@Param("patientId") Long patientId);

    // Query for Cancelled List
    @Query(value = "select epl.* from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id  WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_cancelled = true \n#pageable\n", countQuery = "select count(*) from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id  WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_cancelled = true", nativeQuery = true)
    Page<EmgPatientList> findAll1(Pageable page);

    @Query(value = "select epl.* from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_cancelled = true  and mu.user_firstname LIKE %:qString% or mu.user_lastname LIKE %:qString% \n#pageable\n", countQuery = "select count(*) from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_cancelled = true  and mu.user_firstname LIKE %:qString% or mu.user_lastname LIKE %:qString%", nativeQuery = true)
    Page<EmgPatientList> findAllByPatientName1(@Param("qString") String qString, Pageable page);

    @Query(value = "select epl.* from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_cancelled = true  and mp.patient_er_no LIKE %:qString% \n#pageable\n", countQuery = "select count(*) from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_cancelled = true  and mp.patient_er_no LIKE %qString%", nativeQuery = true)
    Page<EmgPatientList> findAllByPatientERNo1(@Param("qString") String qString, Pageable page);

    @Query(value = "select epl.* from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_cancelled = true  and (DATE(epl.cancelled_date) between :fdate and :tdate) \n#pageable\n", countQuery = "select count(*) from emg_patient_list epl INNER JOIN mst_visit mv on mv.visit_id = epl.epl_visit_id INNER JOIN mst_patient mp on mp.patient_id = mv.visit_patient_id INNER JOIN mst_user mu on mu.user_id=mp.patient_user_id WHERE mp.is_emergency = TRUE AND mv.visit_registration_source = 2 AND epl.is_active = true and epl.is_deleted = false And epl.is_cancelled = true and (DATE(epl.cancelled_date) between :fdate and :tdate)", nativeQuery = true)
    Page<EmgPatientList> findAllByDate1(@Param("fdate") String fdate, @Param("tdate") String tdate, Pageable page);

    @Modifying
    @Query("update EmgPatientList emgPatientList set emgPatientList.isDischargeSummaryFinalized = :isFinalized  where emgPatientList.eplVisitId.visitId = :visitId")
    @Transactional
    int updateDisachargeSummaryStatusForEmg(@Param("isFinalized") Boolean isFinalized, @Param("visitId") Long visitId);

}
