package com.cellbeans.hspa.mipdautocharge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdAutoChargeRepository extends JpaRepository<MipdAutoCharge, Long> {

    Page<MipdAutoCharge> findByAtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdAutoCharge> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdAutoCharge> findByAtNameContains(String key);

}
            
