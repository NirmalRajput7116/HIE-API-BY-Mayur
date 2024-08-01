package com.cellbeans.hspa.temrreferralinhouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemrReferralInHouseRepository extends JpaRepository<TemrReferralInHouse, Long> {

    Page<TemrReferralInHouse> findByRihDepartmentIdDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrReferralInHouse> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*List<TemrReferralInHouse> findByDepartmentNameContains(String key);*/
}
            
