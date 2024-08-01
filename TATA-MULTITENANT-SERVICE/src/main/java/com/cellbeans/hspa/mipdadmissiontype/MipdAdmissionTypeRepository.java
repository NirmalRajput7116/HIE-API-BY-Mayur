package com.cellbeans.hspa.mipdadmissiontype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdAdmissionTypeRepository extends JpaRepository<MipdAdmissionType, Long> {

    Page<MipdAdmissionType> findByAtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdAdmissionType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdAdmissionType> findByAtNameContains(String key);

}
            
