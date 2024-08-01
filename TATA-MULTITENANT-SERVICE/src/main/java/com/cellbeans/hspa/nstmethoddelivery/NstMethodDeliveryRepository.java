package com.cellbeans.hspa.nstmethoddelivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NstMethodDeliveryRepository extends JpaRepository<NstMethodDelivery, Long> {

    Page<NstMethodDelivery> findByMdNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<NstMethodDelivery> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<NstMethodDelivery> findByMdNameContains(String key);

}
            
