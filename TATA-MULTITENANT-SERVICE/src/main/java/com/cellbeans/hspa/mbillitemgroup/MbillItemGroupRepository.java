package com.cellbeans.hspa.mbillitemgroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillItemGroupRepository extends JpaRepository<MbillItemGroup, Long> {

    Page<MbillItemGroup> findByIgNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillItemGroup> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillItemGroup> findByIgNameContains(String key);

}
            
