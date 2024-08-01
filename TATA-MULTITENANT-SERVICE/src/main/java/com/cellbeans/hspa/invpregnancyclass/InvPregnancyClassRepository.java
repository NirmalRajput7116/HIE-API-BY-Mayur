package com.cellbeans.hspa.invpregnancyclass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvPregnancyClassRepository extends JpaRepository<InvPregnancyClass, Long> {

    Page<InvPregnancyClass> findByPcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvPregnancyClass> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvPregnancyClass> findByPcNameContains(String key);

}
            
