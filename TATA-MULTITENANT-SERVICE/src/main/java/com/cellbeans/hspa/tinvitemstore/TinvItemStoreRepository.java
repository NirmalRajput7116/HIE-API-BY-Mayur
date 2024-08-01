package com.cellbeans.hspa.tinvitemstore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvItemStoreRepository extends JpaRepository<TinvItemStore, Long> {

    Page<TinvItemStore> findByIsNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvItemStore> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvItemStore> findByIsItemIdContains(String key);

}
            
