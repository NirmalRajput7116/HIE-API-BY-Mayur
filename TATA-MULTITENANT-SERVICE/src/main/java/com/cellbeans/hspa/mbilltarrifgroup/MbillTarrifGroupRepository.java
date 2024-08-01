package com.cellbeans.hspa.mbilltarrifgroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillTarrifGroupRepository extends JpaRepository<MbillTarrifGroup, Long> {

    Page<MbillTarrifGroup> findByTgGroupIdGroupNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillTarrifGroup> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillTarrifGroup> findByTgGroupIdGroupNameContains(String key);

}
            
