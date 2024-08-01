package com.cellbeans.hspa.memrdosefrequency;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrDoseFrequencyRepository extends JpaRepository<MemrDoseFrequency, Long> {

    Page<MemrDoseFrequency> findByDfNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrDoseFrequency> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrDoseFrequency> findByDfNameContains(String key);

}
            
