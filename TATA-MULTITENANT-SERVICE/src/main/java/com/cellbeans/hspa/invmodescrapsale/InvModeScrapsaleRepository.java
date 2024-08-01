package com.cellbeans.hspa.invmodescrapsale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvModeScrapsaleRepository extends JpaRepository<InvModeScrapsale, Long> {

    Page<InvModeScrapsale> findByMcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvModeScrapsale> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvModeScrapsale> findByMcNameContains(String key);

}
            
