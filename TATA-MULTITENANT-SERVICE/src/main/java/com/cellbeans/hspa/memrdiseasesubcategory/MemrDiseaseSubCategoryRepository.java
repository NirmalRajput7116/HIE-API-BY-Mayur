package com.cellbeans.hspa.memrdiseasesubcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemrDiseaseSubCategoryRepository extends JpaRepository<MemrDiseaseSubCategory, Long> {

    Page<MemrDiseaseSubCategory> findByDcsNameContainsAndIsActiveTrueAndIsDeletedFalseOrDcsDcIdDcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    Page<MemrDiseaseSubCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrDiseaseSubCategory> findByDcsNameContains(String key);

    @Query(value = "SELECT count(dc.dcs_name) FROM memr_disease_sub_category dc WHERE dc.dcs_name=:dcs_name and dcs_dc_id=:dcs_dc_id and dc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByDcName(@Param("dcs_name") String dcs_name, @Param("dcs_dc_id") Long dcs_dc_id);

}
            
