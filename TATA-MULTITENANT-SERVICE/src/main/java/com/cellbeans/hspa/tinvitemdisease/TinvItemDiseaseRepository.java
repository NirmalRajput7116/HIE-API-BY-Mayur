package com.cellbeans.hspa.tinvitemdisease;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvItemDiseaseRepository extends JpaRepository<TinvItemDisease, Long> {

    Page<TinvItemDisease> findByidItemIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvItemDisease> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvItemDisease> findByIdItemIdContains(String key);

}
            
