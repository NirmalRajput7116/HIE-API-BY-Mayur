package com.cellbeans.hspa.patientdietschedule;

import com.cellbeans.hspa.mstdietitem.MstDietItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PatientDietScheduleRepository extends JpaRepository<PatientDietSchedule, Long> {

    List<PatientDietSchedule> findByIsDeletedFalseAndDietChartIdId(Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Update PatientDietSchedule pds SET pds.dietItemId=:dietItemId , pds.dietSchedule.dsId=:dietSchedule,pds.itemQuantity=:itemQuantity,pds.itemCalorie=:itemCalorie,pds.itemPrice=:itemPrice WHERE pds.id=:id")
    public void updatePatientDietSchedule(@Param("dietItemId") MstDietItem mstDietItem, @Param("dietSchedule") Long mstDietSchedule, @Param("itemQuantity") String itemQuantity, @Param("itemCalorie") String itemCalorie, @Param("itemPrice") String itemPrice, @Param("id") Long id);

}
