package com.cellbeans.hspa.mipdbedamenity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdBedAmenityRepository extends JpaRepository<MipdBedAmenity, Long> {

    Page<MipdBedAmenity> findByBaNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdBedAmenity> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdBedAmenity> findByBaNameContains(String key);

}
            
