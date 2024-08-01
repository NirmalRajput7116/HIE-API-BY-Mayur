package com.cellbeans.hspa.mstdrug;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDrugRepository extends JpaRepository<MstDrug, Long> {

    Page<MstDrug> findByDrugNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDrug> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDrug> findByDrugNameContains(String key);

}
            
