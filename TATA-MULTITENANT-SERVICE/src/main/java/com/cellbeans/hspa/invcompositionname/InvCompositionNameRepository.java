package com.cellbeans.hspa.invcompositionname;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvCompositionNameRepository extends JpaRepository<InvCompositionName, Long> {

    Page<InvCompositionName> findByCompositionNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvCompositionName> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvCompositionName> findByCompositionNameContains(String key);

}
            
