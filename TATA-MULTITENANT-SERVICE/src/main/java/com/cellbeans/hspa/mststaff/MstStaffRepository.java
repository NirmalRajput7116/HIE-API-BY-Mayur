package com.cellbeans.hspa.mststaff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface MstStaffRepository extends JpaRepository<MstStaff, Long> {

    //    MstStaff findByStaffUserIdUserIdAndIsActiveTrueAndIsDeletedFalse(Long id);
    MstStaff findByStaffUserIdUserIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    Page<MstStaff> findByStaffUserIdUserFullnameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstStaff> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    // order by first name:   by Jay
    Page<MstStaff> findAllByIsActiveTrueAndIsDeletedFalseOrderByStaffIdAsc(Pageable page);

    List<MstStaff> findAllByStaffServiceIdSsServiceIdContainsAndIsActiveTrueAndIsDeletedFalseOrderByStaffIdAsc(Long ssId);

    Page<MstStaff> findByStaffUserIdUserFullnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(String name, Pageable page);

    List<MstStaff> findByIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc();

    List<MstStaff> findByStaffUserIdUserFirstnameContainsOrStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(String userFirstname, String userLastname);

    List<MstStaff> findByStaffSdIdSdIdAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long sdId);

    List<MstStaff> findByStaffSdIdSdIdAndStaffUnitUnitIdEqualsAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long sdId, Long unitId);

    List<MstStaff> findByStaffUnitUnitIdAndIsMedicaldepartmentTrueAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long unitId);

    List<MstStaff> findByStaffDepartmentIdDepartmentIdEqualsAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long sdId);

    List<MstStaff> findByStaffDepartmentIdDepartmentIdEqualsAndStaffUnitUnitIdEqualsAndIsMedicaldepartmentTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long sdId, Long unitId);

    List<MstStaff> findAllByStaffDepartmentIdDepartmentIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long departmentId);

    List<MstStaff> findByStaffDepartmentIdDepartmentIdAndIsConcessionAuthorityTrueAndIsDeletedFalseAndIsActiveTrueOrderByStaffUserIdUserFirstnameAsc(Long departmentId);

    List<MstStaff> findByIsMedicaldepartmentTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc();

    List<MstStaff> findByStaffDepartmentIdDepartmentIdAndStaffUnitUnitIdEqualsAndIsConcessionAuthorityTrueAndIsDeletedFalseAndIsActiveTrueOrderByStaffUserIdUserFirstnameAsc(Long departmentId, Long unitId);

    List<MstStaff> findAllByStaffTypeEqualsAndStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(int stafftype, Long unitid);

    List<MstStaff> findAllByStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long unitId);

    @Query(value = "select s.staff_id, s.staff_type, s.staff_user_id, s.staff_ss_id,s.created_by, s.created_date from mst_staff s inner join mst_staff_staff_unit su on su.mst_staff_staff_id = s.staff_id inner join mst_user u on u.user_id = s.staff_user_id where su.staff_unit_unit_id =:unitId and s.is_active =1 and s.is_deleted = 0 ORDER by u.user_firstname asc", nativeQuery = true)
    List<Object[]> findAllByStaffUnitUnitId(@Param("unitId") Long unitId);

    List<MstStaff> findAllByIsMedicaldepartmentTrueAndStaffUnitUnitIdEqualsAndDoctorShairTrueAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long unitId);

    List<MstStaff> findByStaffTypeEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Integer key);

    List<MstStaff> findByStaffTypeEqualsAndStaffUserIdUserFirstnameContainsOrStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Integer key, String fstring, String lstring);

    List<MstStaff> findAllByStaffUnitUnitIdEqualsAndStaffTypeEqualsOrStaffTypeEqualsOrStaffTypeEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long unitId, int type, int type1, int type2);

    List<MstStaff> findAllByStaffUnitUnitIdAndStaffRoleRoleIdAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long unitId, Long roleId);

    // List<MstStaff> findAllByStaffUnitUnitIdAndStaffRoleRoleIdAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAscGroupByStaffId(Long unitId, Long roleId);

    List<MstStaff> findAllByStaffUserIdUserFirstnameContainsOrStaffUserIdUserLastnameContainsAndStaffUnitUnitIdAndStaffRoleRoleIdAndIsDeletedFalseAndIsActiveTrueOrderByStaffUserIdUserFirstnameAsc(String fname, String lname, Long unitId, Long roleId);

    List<MstStaff> findByStaffSdIdSdIdAndIsDeletedFalse(Long sdId);

    List<MstStaff> findByStaffSdIdSdIdAndStaffUnitUnitIdEqualsAndIsDeletedFalse(Long sdId, Long unitId);

    List<MstStaff> findByStaffDepartmentIdDepartmentIdEqualsAndIsDeletedFalse(Long sdId);

    List<MstStaff> findByStaffDepartmentIdDepartmentIdEqualsAndStaffUnitUnitIdEqualsAndIsMedicaldepartmentTrueAndIsDeletedFalse(Long sdId, Long unitId);

    List<MstStaff> findByIsActiveTrueAndIsDeletedFalse();

    List<MstStaff> findByStaffUserIdUserFirstnameContainsOrStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(String userFirstname, String userLastname);

    MstStaff findByStaffUserIdUserNameEqualsAndStaffUserIdPasswordEqualsAndStaffUserIdIsActiveTrueAndStaffUserIdIsDeletedFalse(String username, String password);

    MstStaff findByStaffUserIdUserNameEqualsAndStaffUserIdIsActiveTrueAndStaffUserIdIsDeletedFalse(String username);

    List<MstStaff> findByIsMedicaldepartmentTrueAndIsDeletedFalse();  // Author: Priyanka

    List<MstStaff> findByIsMedicaldepartmentTrueAndIsDeletedFalseAndDoctorShairTrueAndStaffUnitUnitIdEquals(long unitId);

    List<MstStaff> findByStaffDepartmentIdDepartmentIdAndIsConcessionAuthorityTrueAndIsDeletedFalseAndIsActiveTrue(Long departmentId);//Author: Mohit

    List<MstStaff> findByStaffTypeEqualsAndIsActiveTrueAndIsDeletedFalse(Integer key);

    //Author: vijay
    MstStaff findByStaffUserIdUserNameEqualsAndStaffUserIdPasswordEqualsAndStaffUnitUnitIdAndStaffUserIdIsActiveTrueAndStaffUserIdIsDeletedFalse(String username, String password, long unitId);

    List<MstStaff> findAllByStaffDepartmentIdDepartmentIdEqualsAndStaffUnitUnitIdEqualsAndIsMedicaldepartmentTrueAndIsActiveTrueAndIsDeletedFalse(Long departmentId, Long unitId);

    List<MstStaff> findByStaffDepartmentIdDepartmentIdAndStaffUnitUnitIdEqualsAndIsConcessionAuthorityTrueAndIsDeletedFalseAndIsActiveTrue(Long departmentId, Long unitId);

    List<MstStaff> findAllByIsMedicaldepartmentTrueAndStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long unitId);

    List<MstStaff> findAllByStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long unitId);

    List<MstStaff> findAllByIsMedicaldepartmentTrueAndStaffUnitUnitIdEqualsAndDoctorShairTrueAndIsActiveTrueAndIsDeletedFalse(Long unitId);

    List<MstStaff> findAllByStaffUnitUnitIdEqualsAndStaffTypeEqualsOrStaffTypeEqualsOrStaffTypeEqualsAndIsActiveTrueAndIsDeletedFalse(Long unitId, int type, int type1, int type2);

    //Author : Rohan Doctor wise list by Unit
    List<MstStaff> findByStaffUnitUnitIdAndIsMedicaldepartmentTrueAndIsActiveTrueAndIsDeletedFalse(Long unitId);

    //for staff drop down
    //@Query(value = "SELECT new com.cellbeans.hspa.mststaff.MstStaffDto(t.staffId,t.staffUserId.userFirstname,t.staffUserId.userLastname) from MstStaff t join staffUnit where (t.staffUserId.userLastname like %:staffname% or t.staffUserId.userFirstname like %:staffname%) and t.isActive=true AND t.isDeleted = false AND t.isMedicaldepartment = True AND  t.staffUnit.unitId IN :unitId order by t.staffId DESC ")
    @Query(value = "select new  com.cellbeans.hspa.mststaff.MstStaffDto(s.staff_id,mu.user_firstname,mu.user_lastname ) from mst_staff s " + "inner join mst_staff_staff_unit u on u.mst_staff_staff_id = s.staff_id " + "inner join mst_user mu on mu.user_id = s.staff_user_id " + "where s.is_active = true and s.is_deleted = false and is_medicaldepartment = true and " + "u.staff_unit_unit_id = :unitId and (mu.user_firstname like %:staffname% or  mu.user_lastname like %:staffname%) " + "order by s.staff_id DESC   #pageable ", countQuery = "select count(t.staff_id)  from mst_staff s " + "inner join mst_staff_staff_unit u on u.mst_staff_staff_id = s.staff_id " + "inner join mst_user mu on mu.user_id = s.staff_user_id " + "where s.is_active = true and s.is_deleted = false and is_medicaldepartment = true and " + "u.staff_unit_unit_id = :unitId and (mu.user_firstname like %:staffname% or  mu.user_lastname like %:staffname%)", nativeQuery = true)
    List<MstStaffDto> getStaffForDropDown(Pageable page, @Param("staffname") String staffname, @Param("unitId") long unitId);

    List<MstStaff> findAllByStaffUnitUnitIdAndStaffRoleRoleIdAndIsActiveTrueAndIsDeletedFalse(Long unitId, Long roleId);

    List<MstStaff> findAllByStaffTypeEqualsAndStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(int stafftype, Long unitid);

    // used at other purposes
    List<MstStaff> findByStaffUserIdUserFullnameContains(String key);

    List<MstStaff> findByStaffErNoContains(String key);

    MstStaff findOneByStaffUserIdUserIdAndIsActiveTrueAndIsDeletedFalse(Long key);
//    MstStaff findByStaffUserIdUserIdAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<MstStaff> findAllByStaffDepartmentIdDepartmentIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long departmentId);

    List<MstStaff> findAllByStaffDepartmentIdDepartmentIdEqualsAndStaffUnitUnitIdAndStaffTypeEqualsAndIsActiveTrueAndIsDeletedFalse(Long departmentId, Long unitId, int stafftype);

    @Query(value = "SELECT distinct unit_id, unit_name, unit_org_id " +
            "FROM mst_staff_staff_unit " +
            "LEFT JOIN mst_staff ON mst_staff.staff_id =   mst_staff_staff_unit.mst_staff_staff_id " +
            "LEFT JOIN mst_unit on mst_unit.unit_id = mst_staff_staff_unit.staff_unit_unit_id " +
            "LEFT JOIN mst_user on mst_user.user_id = mst_staff.staff_user_id " +
            "LEFT JOIN mst_org ON mst_org.org_id = mst_unit.unit_org_id " +
            "WHERE mst_org.org_id = :tenantName " +
            "AND mst_user.user_name= :username " +
            "AND mst_unit.is_active = 1 " +
            "AND mst_unit.is_deleted = 0; ", nativeQuery = true)
    List<Object[]> findAllByStaffUnitUnitIdAndOrgData(@Param("tenantName") Long tenantName, @Param("username") String username);

    @Query(value = "SELECT distinct unit_id, unit_name, unit_org_id " +
            "FROM mst_patient " +
            "LEFT JOIN mst_user on mst_user.user_id = mst_patient.patient_user_id " +
            "LEFT JOIN mst_unit on mst_unit.unit_id = mst_user.user_unit_id  " +
            "LEFT JOIN mst_org ON mst_org.org_id = mst_unit.unit_org_id " +
            "WHERE mst_org.org_id = :tenantName " +
            "AND mst_user.user_name= :username " +
            "AND mst_unit.is_active = 1 " +
            "AND mst_unit.is_deleted = 0; ", nativeQuery = true)
    List<Object[]> findAllByPatientUnitIdAndOrgData(@Param("tenantName") Long tenantName, @Param("username") String username);

    MstStaff findByStaffUserIdUserNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

    MstStaff findByStaffUserIdUserNameContainsAndIsActiveTrueAndIsDeletedFalse(String name);
//    MstStaff findByStaffUserIdUserNameContainsAndStaffUnitUnitIdOrgUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(String name , Long orgId);

    //Author: vijay
    MstStaff findByStaffUserIdUserNameEqualsAndStaffUserIdPasswordEqualsAndStaffUnitUnitIdAndStaffUserIdIsActiveTrueAndStaffUserIdIsDeletedFalseAndStaffUnitIsActiveTrueAndStaffUnitIsDeletedFalse(String username, String password, long unitId);

    MstStaff findAllByStaffIdAndIsActiveTrueAndIsDeletedFalse(Long staffId);

    MstStaff findAllByUUID(Long UUID);

    //Method By chetan 02.08.2019
    MstStaff findByStaffUserIdUserNameEqualsAndStaffUserIdPasswordEqualsAndStaffUnitUnitIdAndStaffUserIdIsActiveTrueAndStaffUnitIsActiveTrue(
            String username, String password, long unitId);

    MstStaff findByStaffUserIdUserNameEqualsAndStaffUnitUnitIdAndStaffUserIdIsActiveTrueAndStaffUnitIsActiveTrue(
            String username, long unitId);

    //Method For userbystaffid rohan
    MstStaff findOneByStaffId(Long staffId);

    //staff list of nonmedical and non doc by Neha
    List<MstStaff> findAllByStaffUnitUnitIdEqualsAndStaffTypeNotAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(Long unitId, int type);

    @Query(value = "select ssi.* FROM staff_service_id ssi INNER join mst_staff ms on ms.staff_id = ssi.mst_staff_staff_id where ssi.mst_staff_staff_id= :staffId ", nativeQuery = true)
    List<Object[]> findBystaffServicesStaffId(@Param("staffId") Long staffId);

    @Query(value = "select * from mst_staff_services where ss_id= :ssid ", nativeQuery = true)
    Object findBystaffServiceslist(@Param("ssid") Long ssid);

    //    @Query(value = "select * from mst_staff_staff_unit mu inner join mst_staff ms where ms.staff_id = mu.mst_staff_staff_id and mu.staff_unit_unit_id  in:unitList ", nativeQuery = true)
    @Query(value = "SELECT ms.staff_user_id as staffId ,msu.user_firstname as  userFirstname ,msu.user_lastname as userLastname FROM mst_staff ms LEFT JOIN mst_staff_staff_unit sun ON ms.staff_id = sun.mst_staff_staff_id " +
            "LEFT JOIN mst_unit ut ON ut.unit_id = sun.staff_unit_unit_id " +
            "LEFT JOIN mst_user msu ON ms.staff_user_id=msu.user_id " +
            "WHERE ms.is_active = 1 AND ms.is_deleted = 0 AND ut.is_active = 1 " +
            "AND ut.is_deleted = 0 AND ut.unit_id IN:unitList ", nativeQuery = true)
    List<Object[]> findByUnitId(@Param("unitList") List<Long> unitList);

}

