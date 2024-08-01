package com.cellbeans.hspa.tinvitemstockadjustment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TinvItemStockAdjustmentRepository extends JpaRepository<TinvItemStockAdjustment, Long> {


    /*Page<TinvItemStockAdjustment> findByAndIsActiveTrueAndIsDeletedFalse(Pageable page);*/

    Page<TinvItemStockAdjustment> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /* List<TinvItemStockAdjustment> findByContains(String key);*/
}
            
