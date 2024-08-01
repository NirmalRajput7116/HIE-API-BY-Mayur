package com.cellbeans.hspa.memrhistorytemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemrHistoryTemplateRepository extends JpaRepository<MemrHistoryTemplate, Long> {

    Page<MemrHistoryTemplate> findByHtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrHistoryTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrHistoryTemplate> findByHtNameContains(String key);

    @Query(value = "SELECT count(mc.ht_name) FROM memr_history_template mc WHERE mc.ht_name=:ht_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByHtName(@Param("ht_name") String ht_name);

}
            
