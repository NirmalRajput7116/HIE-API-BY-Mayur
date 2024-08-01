package com.cellbeans.hspa.mstaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstActionRepository extends JpaRepository<MstAction, Long> {

    Page<MstAction> findByActionNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstAction> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAction> findByActionNameContains(String key);

    List<MstAction> findAllByActionModuleIdModuleIdEquals(Long key);

}
            
