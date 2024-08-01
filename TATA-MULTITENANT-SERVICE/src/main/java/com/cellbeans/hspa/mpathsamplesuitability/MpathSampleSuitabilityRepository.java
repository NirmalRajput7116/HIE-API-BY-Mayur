package com.cellbeans.hspa.mpathsamplesuitability;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathSampleSuitabilityRepository extends JpaRepository<MpathSampleSuitability, Long> {

    Page<MpathSampleSuitability> findBySsNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MpathSampleSuitability> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathSampleSuitability> findBySsNameContains(String key);

}
            
