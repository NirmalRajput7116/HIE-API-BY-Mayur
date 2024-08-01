package com.cellbeans.hspa.mstdoctorschedulemaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDoctorScheduleMasterRepository extends JpaRepository<MstDoctorScheduleMaster, Long> {

    Page<MstDoctorScheduleMaster> findByDsmDayNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDoctorScheduleMaster> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//    @Query(value = "select dm,dsm from MstDoctorMaster dm join MstDoctorScheduleMaster dsm where dm.dmId = dsm.dmDoctorScheduleMasterList and dsm.dsmDayName like :dayName")
//    List<MstDoctorScheduleMaster> findAllByDsmDayNameAndDoc(@Param("dayName") String dayName);
//
//    @Query(value = "select new com.cellbeans.hspa.mstdoctorschedulemaster.DoctorScheduleDto(dm,dsm) from MstDoctorMaster dm join MstDoctorScheduleMaster dsm where dsm.dsmDayName like :dayName")
//    List<DoctorScheduleDto> findAllByDsmDayName(@Param("dayName") String dayName);

    List<MstDoctorScheduleMaster> findAllByDsmDmIdDmIdAndIsActiveTrueAndIsDeletedFalse(Long dmId);

    List<MstDoctorScheduleMaster> findAllByDsmDayNameContainsAndDsmDmIdDmIdAndIsActiveTrueAndIsDeletedFalse(String dayName, Long dmId);

    List<MstDoctorScheduleMaster> findAllByDsmDayNameContainsAndIsActiveTrueAndIsDeletedFalse(String dayName);

}
