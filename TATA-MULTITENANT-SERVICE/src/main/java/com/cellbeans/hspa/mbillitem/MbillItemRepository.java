package com.cellbeans.hspa.mbillitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillItemRepository extends JpaRepository<MbillItem, Long> {

    Page<MbillItem> findByItemNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillItem> findByItemNameContains(String key);

}
            
