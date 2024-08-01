package com.cellbeans.hspa.mipddischargetemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MipdDischargeTemplateRepository extends JpaRepository<MipdDischargeTemplate, Long> {

    Page<MipdDischargeTemplate> findByDtNameContainsAndIsActiveTrueAndIsDeletedFalseOrDtTemplateContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    Page<MipdDischargeTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdDischargeTemplate> findByDtNameContains(String key);

    List<MipdDischargeTemplate> findByDtNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

    @Query("SELECT DISTINCT dtName FROM MipdDischargeTemplate")
    List<MipdDischargeTemplate> findDistinctDtName();

}
            
