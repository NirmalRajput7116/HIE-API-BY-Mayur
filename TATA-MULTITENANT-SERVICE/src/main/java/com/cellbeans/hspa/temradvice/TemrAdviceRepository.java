package com.cellbeans.hspa.temradvice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemrAdviceRepository extends JpaRepository<TemrAdvice, Long> {


    /*    Page<TemrAdvice> findByAdviceAdviceIdAdviseNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);*/

    Page<TemrAdvice> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*    List<TemrAdvice> findByAdviceAdviceIdAdviseNameContains(String key);*/
}
            
