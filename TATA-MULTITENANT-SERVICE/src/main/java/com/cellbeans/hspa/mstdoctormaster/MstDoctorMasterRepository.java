package com.cellbeans.hspa.mstdoctormaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstDoctorMasterRepository extends JpaRepository<MstDoctorMaster, Long> {

    Page<MstDoctorMaster> findByDmFirstNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDoctorMaster> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

}
            
