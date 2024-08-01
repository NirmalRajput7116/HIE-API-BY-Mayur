package com.cellbeans.hspa.memrsymptom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrSymptomRepository extends JpaRepository<MemrSymptom, Long> {

    Page<MemrSymptom> findBySymptomNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrSymptom> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrSymptom> findBySymptomNameContains(String key);

    MemrSymptom findBySymptomNameAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
