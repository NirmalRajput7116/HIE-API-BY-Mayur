package com.cellbeans.hspa.mstbookingtype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstBookingTypeRepository extends JpaRepository<MstBookingType, Long> {

    Page<MstBookingType> findByBtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstBookingType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstBookingType> findByBtNameContains(String key);

    MstBookingType findByBtNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
