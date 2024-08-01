package com.cellbeans.hspa.minvtherapeuticclass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinvTherapeuticClassRepository extends JpaRepository<MinvTherapeuticClass, Long> {

    Page<MinvTherapeuticClass> findByTcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MinvTherapeuticClass> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MinvTherapeuticClass> findByTcNameContains(String key);

}
            
