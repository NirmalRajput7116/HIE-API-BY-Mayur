package com.cellbeans.hspa.mstoperationtheatretable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstOperationTheatreTableRepository extends JpaRepository<MstOperationTheatreTable, Long> {

    Page<MstOperationTheatreTable> findByOttNameContainsAndIsActiveTrueAndIsDeletedFalseOrOperationTheatreIdOtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    Page<MstOperationTheatreTable> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstOperationTheatreTable> findAllByOperationTheatreIdOtIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<MstOperationTheatreTable> findByOttNameContains(String key);

}
            
