package com.cellbeans.hspa.mstdoctorwaiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDoctorWaiverRepository extends JpaRepository<MstDoctorWaiver, Long> {

    Page<MstDoctorWaiver> findByDwNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDoctorWaiver> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDoctorWaiver> findByDwNameContains(String key);

}
            
