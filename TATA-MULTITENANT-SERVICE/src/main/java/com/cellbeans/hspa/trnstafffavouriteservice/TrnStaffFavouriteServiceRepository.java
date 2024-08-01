package com.cellbeans.hspa.trnstafffavouriteservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnStaffFavouriteServiceRepository extends JpaRepository<TrnStaffFavouriteService, Long> {
    //Page<TrnStaffFavouriteService> findByUnitNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnStaffFavouriteService> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnStaffFavouriteService> findAllByIsActiveTrueAndIsDeletedFalse();

}
            
