package com.cellbeans.hspa.mstdoctorroaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public interface MstDoctorRoasterRepository extends JpaRepository<MstDoctorRoaster, Long> {

    Page<MstDoctorRoaster> findByDrDmIdDmIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    Page<MstDoctorRoaster> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    @Query(value = "SELECT dr from MstDoctorRoaster dr where dr.drDate = :date")
    List<MstDoctorRoaster> findByDate(@Param("date") @Temporal(TemporalType.DATE) Date date);

}
            
