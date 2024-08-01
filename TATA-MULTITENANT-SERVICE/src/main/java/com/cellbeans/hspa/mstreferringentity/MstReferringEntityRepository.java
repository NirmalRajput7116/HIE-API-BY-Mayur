package com.cellbeans.hspa.mstreferringentity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstReferringEntityRepository extends JpaRepository<MstReferringEntity, Long> {
    Page<MstReferringEntity> findByReNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByReNameAsc(String name, Pageable page);

    Iterable<MstReferringEntity> findByReRetIdRetIdAndIsDeletedOrderByReName(Long rety, Boolean isDeleted);

    Page<MstReferringEntity> findAllByIsActiveTrueAndIsDeletedFalseOrderByReNameAsc(Pageable page);

    List<MstReferringEntity> findByReNameContains(String key);

    List<MstReferringEntity> findByReRetIdRetIdEqualsAndReNameContainingAndIsActiveTrueAndIsDeletedFalse(Long entitytype, String qString, Pageable page);

    MstReferringEntity findByReNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
            
