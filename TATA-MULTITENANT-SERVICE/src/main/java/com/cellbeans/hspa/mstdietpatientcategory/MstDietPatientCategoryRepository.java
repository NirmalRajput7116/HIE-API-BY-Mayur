package com.cellbeans.hspa.mstdietpatientcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDietPatientCategoryRepository extends JpaRepository<MstDietPatientCategory, Integer> {
    MstDietPatientCategory findByDpcId(Integer id);

    Page<MstDietPatientCategory> findByDpcIdContainsAndIsActiveTrueAndIsDeletedFalse(Integer name, Pageable page);

    Page<MstDietPatientCategory> findByDpcPatientTypeContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDietPatientCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDietPatientCategory> findByDpcIdContains(Integer key);

    MstDietPatientCategory findByDpcPatientTypeEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
