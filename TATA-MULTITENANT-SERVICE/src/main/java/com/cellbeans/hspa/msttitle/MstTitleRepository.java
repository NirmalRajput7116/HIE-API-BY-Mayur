package com.cellbeans.hspa.msttitle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstTitleRepository extends JpaRepository<MstTitle, Long> {

    Page<MstTitle> findByTitleNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByTitleNameAsc(String name, Pageable page);

    Page<MstTitle> findAllByIsActiveTrueAndIsDeletedFalseOrderByTitleNameAsc(Pageable page);

    List<MstTitle> findByTitleNameContains(String key);

    MstTitle findByTitleNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
            
