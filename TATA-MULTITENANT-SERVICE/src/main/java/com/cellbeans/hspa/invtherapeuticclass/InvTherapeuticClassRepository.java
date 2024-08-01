package com.cellbeans.hspa.invtherapeuticclass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvTherapeuticClassRepository extends JpaRepository<InvTherapeuticClass, Long> {

    Page<InvTherapeuticClass> findByTcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvTherapeuticClass> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvTherapeuticClass> findByTcNameContains(String key);

}
            
