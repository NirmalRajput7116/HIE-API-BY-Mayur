package com.cellbeans.hspa.tbillplanitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbillPlanItemRepository extends JpaRepository<TbillPlanItem, Long> {
//    Page<TbillPlanItem> findByPiItemIdItemNameContainsOrPiPlanIdPlanIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TbillPlanItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillPlanItem> findByPiItemIdItemNameContains(String key);

}
            
