package com.cellbeans.hspa.mstdesignation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDesignationRepository extends JpaRepository<MstDesignation, Long> {

    Page<MstDesignation> findByDesignationNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByDesignationNameAsc(String name, Pageable page);

    Page<MstDesignation> findAllByIsActiveTrueAndIsDeletedFalseOrderByDesignationNameAsc(Pageable page);

    List<MstDesignation> findByDesignationNameContains(String key);

    MstDesignation findByDesignationNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
