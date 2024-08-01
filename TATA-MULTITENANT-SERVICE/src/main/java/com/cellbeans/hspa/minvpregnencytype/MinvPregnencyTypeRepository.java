package com.cellbeans.hspa.minvpregnencytype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinvPregnencyTypeRepository extends JpaRepository<MinvPregnencyType, Long> {

    Page<MinvPregnencyType> findByPtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MinvPregnencyType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MinvPregnencyType> findByPtNameContains(String key);

}
            
