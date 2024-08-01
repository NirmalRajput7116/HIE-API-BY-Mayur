package com.cellbeans.hspa.mstgpdesignation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstGpDesignationRepository extends JpaRepository<MstGpDesignation, Long> {

    Page<MstGpDesignation> findByGpDesignationNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstGpDesignation> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstGpDesignation> findByGpDesignationNameContains(String key);

}
            
