package com.cellbeans.hspa.minvmodescrapsale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinvModeScrapsaleRepository extends JpaRepository<MinvModeScrapsale, Long> {

    Page<MinvModeScrapsale> findByMcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MinvModeScrapsale> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MinvModeScrapsale> findByMcNameContains(String key);

}
            
