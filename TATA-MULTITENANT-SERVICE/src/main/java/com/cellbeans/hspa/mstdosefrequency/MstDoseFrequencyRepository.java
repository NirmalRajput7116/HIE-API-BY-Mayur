package com.cellbeans.hspa.mstdosefrequency;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDoseFrequencyRepository extends JpaRepository<MstDoseFrequency, Long> {

    Page<MstDoseFrequency> findByDfNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDoseFrequency> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDoseFrequency> findByDfNameContains(String key);

    MstDoseFrequency findByDfNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
