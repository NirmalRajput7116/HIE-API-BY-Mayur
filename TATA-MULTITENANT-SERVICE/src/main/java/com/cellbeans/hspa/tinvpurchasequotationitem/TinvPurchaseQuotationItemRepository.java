package com.cellbeans.hspa.tinvpurchasequotationitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvPurchaseQuotationItemRepository extends JpaRepository<TinvPurchaseQuotationItem, Long> {

    Page<TinvPurchaseQuotationItem> findByPqiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvPurchaseQuotationItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvPurchaseQuotationItem> findByPqiPqIdContains(String key);

    List<TinvPurchaseQuotationItem> findByPqiPqIdPqIdAndIsActiveTrueAndIsDeletedFalse(Long key);

}
            
