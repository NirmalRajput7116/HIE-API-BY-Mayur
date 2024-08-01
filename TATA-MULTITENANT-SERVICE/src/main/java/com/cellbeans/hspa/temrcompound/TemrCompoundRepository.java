package com.cellbeans.hspa.temrcompound;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemrCompoundRepository extends JpaRepository<TemrCompound, Long> {


    /*  Page<TemrCompound> findByCompoundCompoundIdCompoundNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);*/

    Page<TemrCompound> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*  List<TemrCompound> findByCompoundCompoundIdCompoundNameContains(String key);*/
}
            
