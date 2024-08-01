package com.cellbeans.hspa.mipddischargedestination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdDischargeDestinationRepository extends JpaRepository<MipdDischargeDestination, Long> {

    Page<MipdDischargeDestination> findByDdNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdDischargeDestination> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdDischargeDestination> findByDdNameContains(String key);

}
            
