package com.cellbeans.hspa.mstethnicity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstEthnicityRepository extends JpaRepository<MstEthnicity, Long> {

    Page<MstEthnicity> findByEthnicityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByEthnicityName(String name, Pageable page);

    Page<MstEthnicity> findAllByIsActiveTrueAndIsDeletedFalseOrderByEthnicityName(Pageable page);

    List<MstEthnicity> findByEthnicityNameContains(String key);

}
            
