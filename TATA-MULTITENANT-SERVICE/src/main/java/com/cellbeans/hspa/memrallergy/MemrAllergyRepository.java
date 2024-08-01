package com.cellbeans.hspa.memrallergy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrAllergyRepository extends JpaRepository<MemrAllergy, Long> {

    Page<MemrAllergy> findByAllergyNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrAllergy> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrAllergy> findByAllergyNameContains(String key);

}
            
