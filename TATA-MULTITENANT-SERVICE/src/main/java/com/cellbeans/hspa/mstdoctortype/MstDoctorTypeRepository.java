package com.cellbeans.hspa.mstdoctortype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDoctorTypeRepository extends JpaRepository<MstDoctorType, Long> {

    Page<MstDoctorType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstDoctorType> findByDtNameContainsAndIsActiveTrueAndIsDeletedFalse(String qString, Pageable page);

    List<MstDoctorType> findByDtNameContains(String key);

}