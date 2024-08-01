package com.cellbeans.hspa.tbillplanservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbillPlanServiceRepository extends JpaRepository<TbillPlanService, Long> {
//    Page<TbillPlanService> findByPsServiceIdServiceNameContainsOrPsPlanIdPlanIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TbillPlanService> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillPlanService> findByPsServiceIdServiceNameContains(String key);

}
            
