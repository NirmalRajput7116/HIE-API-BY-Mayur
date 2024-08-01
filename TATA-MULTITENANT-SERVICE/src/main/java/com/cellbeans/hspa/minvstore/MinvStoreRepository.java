package com.cellbeans.hspa.minvstore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinvStoreRepository extends JpaRepository<MinvStore, Long> {

    Page<MinvStore> findByStoreNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MinvStore> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MinvStore> findByStoreNameContains(String key);

}
            
