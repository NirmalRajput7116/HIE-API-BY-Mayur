package com.cellbeans.hspa.mstrelation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstRelationRepository extends JpaRepository<MstRelation, Long> {

    Page<MstRelation> findByRelationNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstRelation> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstRelation> findByRelationNameContains(String key);

    MstRelation findByRelationNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
