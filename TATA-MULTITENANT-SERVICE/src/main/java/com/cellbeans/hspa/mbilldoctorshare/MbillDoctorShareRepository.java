package com.cellbeans.hspa.mbilldoctorshare;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbillDoctorShareRepository extends JpaRepository<MbillDoctorShare, Long> {

    Page<MbillDoctorShare> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

}
