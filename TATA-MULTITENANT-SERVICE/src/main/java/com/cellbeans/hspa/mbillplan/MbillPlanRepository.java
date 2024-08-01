package com.cellbeans.hspa.mbillplan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillPlanRepository extends JpaRepository<MbillPlan, Long> {

    Page<MbillPlan> findByPlanNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillPlan> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillPlan> findByPlanNameContains(String key);

}
            
