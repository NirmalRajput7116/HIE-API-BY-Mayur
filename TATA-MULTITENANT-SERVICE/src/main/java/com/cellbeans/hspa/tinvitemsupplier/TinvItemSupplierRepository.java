package com.cellbeans.hspa.tinvitemsupplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvItemSupplierRepository extends JpaRepository<TinvItemSupplier, Long> {

    Page<TinvItemSupplier> findByIsNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvItemSupplier> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvItemSupplier> findByIsItemIdContains(String key);

}
            
