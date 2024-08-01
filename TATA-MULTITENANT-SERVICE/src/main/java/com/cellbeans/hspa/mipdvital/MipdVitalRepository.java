package com.cellbeans.hspa.mipdvital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdVitalRepository extends JpaRepository<MipdVital, Long> {

    Page<MipdVital> findByVitalNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdVital> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdVital> findByVitalNameContains(String key);

}
            
