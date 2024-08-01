package com.cellbeans.hspa.mipdadmissionpurpose;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdAdmissionPurposeRepository extends JpaRepository<MipdAdmissionPurpose, Long> {

    Page<MipdAdmissionPurpose> findByApNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdAdmissionPurpose> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdAdmissionPurpose> findAllByIsActiveTrueAndIsDeletedFalse();

    List<MipdAdmissionPurpose> findByApNameContains(String key);

}
            
