package com.cellbeans.hspa.minvstoragetype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinvStorageTypeRepository extends JpaRepository<MinvStorageType, Long> {

    Page<MinvStorageType> findByStNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MinvStorageType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MinvStorageType> findByStNameContains(String key);

}
            
