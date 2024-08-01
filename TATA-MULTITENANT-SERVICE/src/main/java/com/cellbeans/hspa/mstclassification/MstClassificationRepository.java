package com.cellbeans.hspa.mstclassification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstClassificationRepository extends JpaRepository<MstClassification, Long> {

    Page<MstClassification> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstClassification> findByClassificationNameContainsAndIsActiveTrueAndIsDeletedFalse(String qString, Pageable page);

    List<MstClassification> findByClassificationNameContains(String key);

}
