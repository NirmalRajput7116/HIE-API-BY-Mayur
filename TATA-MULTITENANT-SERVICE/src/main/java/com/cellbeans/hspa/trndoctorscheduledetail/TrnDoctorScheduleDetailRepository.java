package com.cellbeans.hspa.trndoctorscheduledetail;

import com.cellbeans.hspa.mststaff.MstStaffDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.List;

public interface TrnDoctorScheduleDetailRepository extends JpaRepository<TrnDoctorScheduleDetail, Long> {

    TrnDoctorScheduleDetail findByDsdDayIdDayIdAndDsdStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(Long dayId, Long staffId);

    Page<TrnDoctorScheduleDetail> findByDsdStaffIdStaffUserIdUserFullnameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnDoctorScheduleDetail> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    TrnDoctorScheduleDetail findOneByDsdDayIdDayIdAndDsdStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(Long dayId, Long staffId);

    @Query(value = "  SELECT  * from trn_doctor_schedule_detail t LEFT JOIN mst_cabin m ON m.cabin_id = t.dsd_cabin_id" +
            " where  t.dsd_day_id = :day and t.dsd_cabin_id = :cabin and t.is_active = 1 and t.is_deleted = 0  and  CAST(:time as time) between STR_TO_DATE( t.dsd_start_time ,'%h:%i %p') and STR_TO_DATE( t.dsd_end_time , '%h:%i %p')  order by t.dsd_id desc ", nativeQuery = true)
    List<TrnDoctorScheduleDetail> findAllByDsdDayIdDayIdEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("cabin") Long cabin, @Param("time") Time time, @Param("day") Long day);

    Page<TrnDoctorScheduleDetail> findAllByDsdStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(Long staffId, Pageable page);

    TrnDoctorScheduleDetail findOneByDsdStaffIdStaffIdAndDsdDayIdDayNameContainsAndIsActiveTrueAndIsDeletedFalse(Long staffId, String dayName);

    @Query("SELECT DISTINCT t.dsdStaffId.staffId FROM TrnDoctorScheduleDetail t where t.isActive = 1 and t.isDeleted = 0 group by  t.dsdStaffId.staffId")
    List<MstStaffDto> findAllDistinctStaff(@Param("limit") int limit, @Param("offset") int offset);

    List<TrnDoctorScheduleDetail> findAllByDsdStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(Long staffId);

    @Query(value = "  SELECT  * from trn_doctor_schedule_detail  where  dsd_day_id = :day and dsd_staff_id = :staffid  and is_active = 1 and is_deleted = 0  and :time  between STR_TO_DATE( dsd_start_time ,'%h:%i %p') and STR_TO_DATE( dsd_end_time , '%h:%i %p')   ", nativeQuery = true)
    TrnDoctorScheduleDetail findOneByIsActiveTrueAndIsDeletedFalse(@Param("staffid") Long staffid, @Param("time") String time, @Param("day") Long day);

    @Query(value = "  SELECT  *  from trn_doctor_schedule_detail  where  dsd_day_id = :day and dsd_staff_id = :staffid  and is_active = 1 and is_deleted = 0  and  CAST(:time as time) between dsd_start_time  and dsd_end_time ", nativeQuery = true)
    TrnDoctorScheduleDetail findOneByStaffSchedule(@Param("staffid") Long staffid, @Param("time") String time, @Param("day") Long day);

    @Query(value = "SELECT  CONCAT(mu.user_firstname,' ',mu.user_lastname) AS name,sp.speciality_name AS sname,ms.staff_education,mc.city_name, ms.staffexprience,ms.staff_id,mss.ss_base_rate,(SELECT sc.sc_unit_id FROM staff_configuration sc WHERE sc.sc_staff_id=ms.staff_id) as unit, ms.staff_min_duration FROM mst_staff ms  INNER JOIN mst_user mu INNER JOIN mst_speciality sp INNER JOIN mst_city mc INNER JOIN staff_service_id ss INNER JOIN mst_staff_services mss INNER JOIN mst_staff_staff_unit mssu WHERE   mu.user_id=ms.staff_user_id AND ms.staff_speciality_id=sp.speciality_id and mu.user_city_id=mc.city_id AND ss.mst_staff_staff_id=ms.staff_id AND mss.ss_id=ss.staff_service_id_ss_id AND mss.ss_service_id=:serviceId AND mssu.mst_staff_staff_id = ms.staff_id AND  ms.is_virtual = true AND ((mssu.staff_unit_unit_id = :unitId) or (mssu.staff_unit_unit_id = (SELECT mu.unit_parent_id FROM mst_unit mu WHERE mu.unit_id = :unitId)) OR (mssu.staff_unit_unit_id IN (SELECT mu.unit_id FROM mst_unit mu WHERE mu.unit_parent_id = (SELECT mu.unit_parent_id FROM mst_unit mu WHERE mu.unit_id = :unitId)) OR (ms.staff_cluster_id = (SELECT mu.unit_cluster_id FROM mst_unit mu WHERE mu.unit_id = :unitId))))    GROUP BY ms.staff_id", nativeQuery = true)
    List<Object[]> getDoctorList(@Param("serviceId") Long serviceId, @Param("unitId") Long unitId);

    @Query(value = "SELECT  CONCAT(mu.user_firstname,' ',mu.user_lastname) AS name,sp.speciality_name AS sname,ms.staff_education,mc.city_name, ms.staffexprience,ms.staff_id,mss.ss_base_rate," +
            "(SELECT sc.sc_unit_id FROM staff_configuration sc WHERE sc.sc_staff_id=ms.staff_id) as unit, ms.staff_min_duration FROM mst_staff ms  INNER JOIN mst_user mu INNER JOIN mst_speciality sp INNER JOIN mst_city mc INNER JOIN staff_service_id ss INNER JOIN mst_staff_services mss INNER JOIN mst_staff_staff_unit mssu INNER JOIN trn_doctor_schedule_detail sd WHERE sd.dsd_staff_id=ms.staff_id AND mu.user_id=ms.staff_user_id AND ms.staff_speciality_id=sp.speciality_id and mu.user_city_id=mc.city_id AND ss.mst_staff_staff_id=ms.staff_id AND mss.ss_id=ss.staff_service_id_ss_id AND mss.ss_service_id=:serviceId AND mssu.mst_staff_staff_id = ms.staff_id AND mssu.staff_unit_unit_id = :unitId AND ms.is_active=1 AND ms.is_deleted=0 AND sd.dsd_day_id= :appday AND sd.is_deleted=0 GROUP BY ms.staff_id", nativeQuery = true)
    List<Object[]> getDoctorListByServiceAndUnitId(@Param("serviceId") Long serviceId, @Param("unitId") Long unitId, @Param("appday") Long appday);
}
