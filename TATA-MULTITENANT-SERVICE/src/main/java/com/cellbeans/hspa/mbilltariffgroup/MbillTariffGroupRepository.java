package com.cellbeans.hspa.mbilltariffgroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillTariffGroupRepository extends JpaRepository<MbillTariffGroup, Long> {

    Page<MbillTariffGroup> findByTgGroupIdGroupNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillTariffGroup> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillTariffGroup> findByTgGroupIdGroupNameContains(String key);

}
            
