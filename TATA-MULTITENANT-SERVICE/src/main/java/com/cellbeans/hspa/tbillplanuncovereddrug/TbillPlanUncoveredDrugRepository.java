package com.cellbeans.hspa.tbillplanuncovereddrug;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbillPlanUncoveredDrugRepository extends JpaRepository<TbillPlanUncoveredDrug, Long> {
//    Page<TbillPlanUncoveredDrug> findByPudDrugIdDrugNameContainsOrPudPlanIdPlanIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TbillPlanUncoveredDrug> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillPlanUncoveredDrug> findByPudDrugIdDrugNameContains(String key);

}
            
