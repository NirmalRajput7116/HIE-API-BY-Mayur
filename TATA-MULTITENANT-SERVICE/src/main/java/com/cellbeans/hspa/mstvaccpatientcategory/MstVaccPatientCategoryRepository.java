package com.cellbeans.hspa.mstvaccpatientcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstVaccPatientCategoryRepository extends JpaRepository<MstVaccPatientCategory, Integer> {
    MstVaccPatientCategory findByVpcId(long id);

    Page<MstVaccPatientCategory> findByVpcIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstVaccPatientCategory> findByVpcVccPatientTypeContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    List<MstVaccPatientCategory> findAllByIsDeletedFalse();

    Page<MstVaccPatientCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstVaccPatientCategory> findByVpcIdContains(String key);

    MstVaccPatientCategory findByVpcVccPatientTypeEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
