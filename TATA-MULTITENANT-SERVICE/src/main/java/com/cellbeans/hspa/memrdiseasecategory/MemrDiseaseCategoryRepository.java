package com.cellbeans.hspa.memrdiseasecategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemrDiseaseCategoryRepository extends JpaRepository<MemrDiseaseCategory, Long> {

    Page<MemrDiseaseCategory> findByDcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrDiseaseCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrDiseaseCategory> findByDcNameContains(String key);

    @Query(value = "SELECT count(dc.dc_name) FROM memr_disease_category dc WHERE dc.dc_name=:dc_name and dc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByDcName(@Param("dc_name") String dc_name);

}
            
