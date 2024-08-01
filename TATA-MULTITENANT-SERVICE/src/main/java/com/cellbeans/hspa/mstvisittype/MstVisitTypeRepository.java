package com.cellbeans.hspa.mstvisittype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstVisitTypeRepository extends JpaRepository<MstVisitType, Long> {

    Page<MstVisitType> findByVtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstVisitType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstVisitType> findByVtNameContains(String key);

}
            
