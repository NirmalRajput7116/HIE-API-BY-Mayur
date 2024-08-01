package com.cellbeans.hspa.trnbloodstock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnBloodStockRepository extends JpaRepository<TrnBloodStock, Long> {

    Page<TrnBloodStock> findByAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    TrnBloodStock findByBsBloodgroupIdBloodgroupIdEqualsAndBsUnitEqualsAndIsActiveTrueAndIsDeletedFalse(Long key, String unitname);

    Page<TrnBloodStock> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnBloodStock> findByBsNoOfContains(String key);

}

