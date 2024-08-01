package com.cellbeans.hspa.temrdoctorsorder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemrDoctorsOrderRepository extends JpaRepository<TemrDoctorsOrder, Long> {

    Page<TemrDoctorsOrder> findByDoServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrDoctorsOrder> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

}
            
