package com.cellbeans.hspa.tnstdrugadmin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TnstDrugAdminRepository extends JpaRepository<TnstDrugAdmin, Long> {

    Page<TnstDrugAdmin> findByAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TnstDrugAdmin> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TnstDrugAdmin> findByDaAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(Long admissionId, Pageable page);

}
            
