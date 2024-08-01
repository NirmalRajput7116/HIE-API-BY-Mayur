package com.cellbeans.hspa.msttriage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstTriageRepository extends JpaRepository<MstTriage, Long> {

    Page<MstTriage> findByTriageNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstTriage> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstTriage> findByTriageNameContains(String key);

    MstTriage findByTriageNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
