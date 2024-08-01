package com.cellbeans.hspa.memrdrugduration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemrDrugDurationRepository extends JpaRepository<MemrDrugDuration, Long> {

    Page<MemrDrugDuration> findByDdNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrDrugDuration> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrDrugDuration> findByDdNameContains(String key);

    @Query(value = "SELECT count(mc.dd_name) FROM memr_drug_duration mc WHERE mc.dd_name=:dd_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByDdName(@Param("dd_name") String dd_name);

}
            
