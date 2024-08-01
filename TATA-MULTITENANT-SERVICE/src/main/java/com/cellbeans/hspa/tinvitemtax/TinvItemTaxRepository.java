package com.cellbeans.hspa.tinvitemtax;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvItemTaxRepository extends JpaRepository<TinvItemTax, Long> {

    Page<TinvItemTax> findByItNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvItemTax> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvItemTax> findByItItemIdContains(String key);

}
            
