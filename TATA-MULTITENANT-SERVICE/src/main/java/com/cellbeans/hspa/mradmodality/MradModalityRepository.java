package com.cellbeans.hspa.mradmodality;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MradModalityRepository extends JpaRepository<MradModality, Long> {

    Page<MradModality> findByModalityNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MradModality> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MradModality> findByModalityNameContains(String key);

}
            
