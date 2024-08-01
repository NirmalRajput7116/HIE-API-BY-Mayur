package com.cellbeans.hspa.tinvenquiryitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvEnquiryItemRepository extends JpaRepository<TinvEnquiryItem, Long> {

    Page<TinvEnquiryItem> findByEiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvEnquiryItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvEnquiryItem> findByEiItemIdContains(String key);

    List<TinvEnquiryItem> findByEiPieIdPieIdAndIsActiveTrueAndIsDeletedFalse(Long key);

}
            
