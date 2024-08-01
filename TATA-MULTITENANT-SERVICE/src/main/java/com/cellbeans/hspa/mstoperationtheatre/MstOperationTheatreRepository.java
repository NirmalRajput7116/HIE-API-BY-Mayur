package com.cellbeans.hspa.mstoperationtheatre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstOperationTheatreRepository extends JpaRepository<MstOperationTheatre, Long> {

    Page<MstOperationTheatre> findByOtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstOperationTheatre> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstOperationTheatre> findByOtNameContains(String key);

}
            
