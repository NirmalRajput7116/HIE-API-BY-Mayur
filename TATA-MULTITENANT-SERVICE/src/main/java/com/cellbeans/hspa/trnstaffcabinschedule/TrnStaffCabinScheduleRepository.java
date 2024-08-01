package com.cellbeans.hspa.trnstaffcabinschedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrnStaffCabinScheduleRepository extends JpaRepository<TrnStaffCabinSchedule, Long> {
//    TrnStaffCabinSchedule findByIsActiveTrueAndIsDeletedFalse( Long staffid,String dateString, Long dayid);

    Page<TrnStaffCabinSchedule> findByCsdStartTimeContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnStaffCabinSchedule> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnStaffCabinSchedule> findByCsdStartTimeContains(String key);

    Page<TrnStaffCabinSchedule> findAllByCsdStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(Long staffid, Pageable page);

    @Query("SELECT DISTINCT t.csdStaffId FROM TrnStaffCabinSchedule t where t.isActive = 1 and t.isDeleted = 0")
    Page<TrnStaffCabinSchedule> findAllDistinctStaff(Pageable page);

    @Query(value = "  SELECT  * from trn_staff_cabin_schedule  where  csd_day_id = :day and csd_staff_id = :staffid  and is_active = 1 and is_deleted = 0  and  CAST(:time as time) between csd_start_time  and csd_end_time   ", nativeQuery = true)
    TrnStaffCabinSchedule findOneByIsActiveTrueAndIsDeletedFalse(@Param("staffid") Long staffid, @Param("time") String time, @Param("day") Long day);

}
            
