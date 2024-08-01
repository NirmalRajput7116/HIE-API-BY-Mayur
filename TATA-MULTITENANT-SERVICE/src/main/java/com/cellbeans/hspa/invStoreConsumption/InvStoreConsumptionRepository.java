package com.cellbeans.hspa.invStoreConsumption;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvStoreConsumptionRepository extends JpaRepository<InvStoreConsumption, Long> {

    Page<InvStoreConsumption> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<InvStoreConsumption> findByScItemNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

}
