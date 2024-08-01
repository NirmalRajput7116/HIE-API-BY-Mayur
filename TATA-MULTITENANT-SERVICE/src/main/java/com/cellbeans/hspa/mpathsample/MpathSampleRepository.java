package com.cellbeans.hspa.mpathsample;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathSampleRepository extends JpaRepository<MpathSample, Long> {

    Page<MpathSample> findBySampleNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MpathSample> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathSample> findBySampleNameContains(String key);

}
            
