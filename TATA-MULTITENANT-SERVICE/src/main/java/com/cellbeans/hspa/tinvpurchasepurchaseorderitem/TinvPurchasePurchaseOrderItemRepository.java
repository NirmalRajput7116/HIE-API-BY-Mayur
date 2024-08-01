package com.cellbeans.hspa.tinvpurchasepurchaseorderitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvPurchasePurchaseOrderItemRepository extends JpaRepository<TinvPurchasePurchaseOrderItem, Long> {

    Page<TinvPurchasePurchaseOrderItem> findByPpotNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvPurchasePurchaseOrderItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvPurchasePurchaseOrderItem> findByPpotItemIdContains(String key);

    List<TinvPurchasePurchaseOrderItem> findAllByPpotPpoIdPpoIdAndIsActiveTrueAndIsDeletedFalse(Long id);

}
            
