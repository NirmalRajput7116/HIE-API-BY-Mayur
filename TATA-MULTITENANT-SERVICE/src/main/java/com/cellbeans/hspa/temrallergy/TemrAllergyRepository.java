package com.cellbeans.hspa.temrallergy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemrAllergyRepository extends JpaRepository<TemrAllergy, Long> {

    Page<TemrAllergy> findByAllergyAllergyIdAllergyNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrAllergy> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrAllergy> findByAllergyAllergyIdAllergyName(String key);

}
            
