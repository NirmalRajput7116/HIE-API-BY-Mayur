package com.cellbeans.hspa.trncompanyplan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnCompanyPlanRepository extends JpaRepository<TrnCompanyPlan, Long> {
//    Page<TrnCompanyPlan> findByCpPlanIdPlanNameContainsOrCpCompanyIdCompanyIdAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnCompanyPlan> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*        List<TrnCompanyPlan> findByCpPlanIdPlanName(String key);*/
}
            
