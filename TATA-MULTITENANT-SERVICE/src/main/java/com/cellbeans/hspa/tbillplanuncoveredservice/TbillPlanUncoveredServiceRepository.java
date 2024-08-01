package com.cellbeans.hspa.tbillplanuncoveredservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbillPlanUncoveredServiceRepository extends JpaRepository<TbillPlanUncoveredService, Long> {
//    Page<TbillPlanUncoveredService> findByPusServiceIdServiceNameOrPusPlanIdPlanIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TbillPlanUncoveredService> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillPlanUncoveredService> findByPusServiceIdServiceNameContains(String key);

}
            
