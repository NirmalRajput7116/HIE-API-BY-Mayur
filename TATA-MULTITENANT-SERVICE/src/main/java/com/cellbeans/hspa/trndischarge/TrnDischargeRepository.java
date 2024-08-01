package com.cellbeans.hspa.trndischarge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrnDischargeRepository extends JpaRepository<TrnDischarge, Long> {

    Page<TrnDischarge> findByIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnDischarge> findBydischargeIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnDischarge> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TrnDischarge> findAllByDischargeAdmissionIdAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TrnDischarge> findByDischargeAdmissionIdAdmissionStatusIsTrueAndDischargeAdmissionIdAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(String pname, Pageable page);

    Page<TrnDischarge> findByDischargeAdmissionIdAdmissionStatusIsTrueAndDischargeAdmissionIdAdmissionIpdNoContainsAndIsActiveTrueAndIsDeletedFalse(String pname, Pageable page);

    Page<TrnDischarge> findByDischargeAdmissionIdAdmissionStatusIsTrueAndDischargeAdmissionIdAdmissionPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(String pname, Pageable page);

    Page<TrnDischarge> findByDischargeAdmissionIdAdmissionStatusIsTrueAndDischargeTypeDtIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long dtId, Pageable page);

    @Query(value = "select td from TrnDischarge td where td.dischargeAdmissionId.admissionStatus = true and td.isActive = true and td.isDeleted = false and (td.dischargeAdmissionId.admissionPatientId.patientUserId.userFirstname like %:pname% or td.dischargeAdmissionId.admissionPatientId.patientUserId.userLastname like %:plname%) ")
    Page<TrnDischarge> findByDischargeAdmissionIdAdmissionStatusIsTrueAndDischargeAdmissionIdAdmissionPatientIdPatientUserIdUserFirstnameContainsOrDischargeAdmissionIdAdmissionPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(@Param("pname") String pname, @Param("plname") String plname, Pageable page);

    List<TrnDischarge> findByDischargeIdContains(String key);

    TrnDischarge findAllByDischargeAdmissionIdAdmissionIdEquals(Long admissionid);

    Page<TrnDischarge> findAllByDischargeAdmissionIdAdmissionUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(long unitId, Pageable page);

    @Query(value = "SELECT * FROM trn_discharge td inner join trn_admission ta on ta.admission_id = td.discharge_admission_id WHERE ta.admission_status=1 and td.is_active=1 AND td.is_deleted=0 and DATE_FORMAT(td.discharge_date,'%y-%m-%d') = CURDATE() \n#pageable\n", countQuery = "select count(*) from trn_discharge td inner join trn_admission ta on ta.admission_id = td.discharge_admission_id WHERE ta.admission_status=1 and td.is_active=1 AND td.is_deleted=0 and DATE_FORMAT(td.discharge_date,'%y-%m-%d') = CURDATE()", nativeQuery = true)
    Page<TrnDischarge> findTodaysDischargePatient(Pageable page);

    @Query(value = "SELECT * FROM trn_discharge td WHERE td.is_active=1 AND td.is_deleted=0 \n#pageable\n", countQuery = "select count(*) from trn_discharge td WHERE td.is_active=1 AND td.is_deleted=0", nativeQuery = true)
    Page<TrnDischarge> findDischargePatient(Pageable page);

    @Query(value = "select * from trn_discharge td INNER JOIN mipd_discharge_type mdt ON mdt.dt_id = td.discharge_type WHERE td.is_active=1 AND td.is_deleted=0 AND mdt.dt_name LIKE '%death%' \n#pageable\n", countQuery = "select count(discharge_id) from trn_discharge td INNER JOIN mipd_discharge_type mdt ON mdt.dt_id = td.discharge_type WHERE td.is_active=1 AND td.is_deleted=0 AND mdt.dt_name LIKE '%death%'", nativeQuery = true)
    Page<TrnDischarge> findDeathDischargePatient(Pageable page);

    @Query(value = "select * from trn_discharge td INNER JOIN mipd_discharge_type mdt ON mdt.dt_id = td.discharge_type WHERE td.is_active=1 AND td.is_deleted=0 AND mdt.dt_name LIKE '%normal%' \n#pageable\n", countQuery = "select count(discharge_id) from trn_discharge td INNER JOIN mipd_discharge_type mdt ON mdt.dt_id = td.discharge_type WHERE td.is_active=1 AND td.is_deleted=0 AND mdt.dt_name LIKE '%normal%'", nativeQuery = true)
    Page<TrnDischarge> findRecoveredDischargePatient(Pageable page);

    @Query(value = "SELECT * FROM trn_discharge td inner join trn_admission ta on ta.admission_id = td.discharge_admission_id WHERE ta.admission_status=1 and td.is_active=1 AND td.is_deleted=0 and (DATE(td.discharge_date) between :fdate and :tdate) \n#pageable\n", countQuery = "select count(*) from trn_discharge td inner join trn_admission ta on ta.admission_id = td.discharge_admission_id WHERE ta.admission_status=1 and td.is_active=1 AND td.is_deleted=0 and (DATE(td.discharge_date) between :fdate and :tdate)", nativeQuery = true)
    Page<TrnDischarge> findByDischargeDateBetweenAndIsActiveTrueAndIsDeletedFalse(@Param("fdate") String fdate, @Param("tdate") String tdate, Pageable page);

}
            
