package com.cellbeans.hspa.memrdiseasetype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemrDiseaseTypeRepository extends JpaRepository<MemrDiseaseType, Long> {

    Page<MemrDiseaseType> findByDtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrDiseaseType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrDiseaseType> findByDtNameContains(String key);

    @Query(value = "SELECT count(dc.dt_name) FROM memr_disease_type dc WHERE dc.dt_name=:dt_name  and dc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByDcName(@Param("dt_name") String dt_name);

}
            
