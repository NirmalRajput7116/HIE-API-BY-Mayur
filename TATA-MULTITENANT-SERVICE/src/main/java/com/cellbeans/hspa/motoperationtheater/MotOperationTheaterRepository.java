package com.cellbeans.hspa.motoperationtheater;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotOperationTheaterRepository extends JpaRepository<MotOperationTheater, Long> {

    Page<MotOperationTheater> findByOtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotOperationTheater> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotOperationTheater> findByOtNameContains(String key);

}
            
