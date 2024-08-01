package com.cellbeans.hspa.mipdroomamenity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdRoomAmenityRepository extends JpaRepository<MipdRoomAmenity, Long> {

    Page<MipdRoomAmenity> findByRaNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdRoomAmenity> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdRoomAmenity> findByRaNameContains(String key);

}
            
