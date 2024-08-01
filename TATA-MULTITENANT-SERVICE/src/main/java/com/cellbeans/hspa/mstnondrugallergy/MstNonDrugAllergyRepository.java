package com.cellbeans.hspa.mstnondrugallergy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstNonDrugAllergyRepository extends JpaRepository<MstNonDrugAllergy, Long> {

    Page<MstNonDrugAllergy> findByNdaNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstNonDrugAllergy> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstNonDrugAllergy> findByNdaNameContains(String key);

    MstNonDrugAllergy findByNdaNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
            
