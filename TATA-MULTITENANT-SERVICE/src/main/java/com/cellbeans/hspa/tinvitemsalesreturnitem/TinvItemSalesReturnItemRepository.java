package com.cellbeans.hspa.tinvitemsalesreturnitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvItemSalesReturnItemRepository extends JpaRepository<TinvItemSalesReturnItem, Long> {

    Page<TinvItemSalesReturnItem> findByIsriNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvItemSalesReturnItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvItemSalesReturnItem> findByIsriItemNameContains(String key);

    List<TinvItemSalesReturnItem> findAllByIsriIsrIdIsrIdAndIsActiveTrueAndIsDeletedFalse(Long key);

}
            
