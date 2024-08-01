package com.cellbeans.hspa.mstreferringentitytype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstReferringEntityTypeRepository extends JpaRepository<MstReferringEntityType, Long> {

    Page<MstReferringEntityType> findByRetNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByRetNameAsc(String name, Pageable page);

    Page<MstReferringEntityType> findAllByIsActiveTrueAndIsDeletedFalseOrderByRetNameAsc(Pageable page);

    List<MstReferringEntityType> findByRetNameContains(String key);

    MstReferringEntityType findByRetNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
            
