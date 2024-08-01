package com.cellbeans.hspa.mstdocumentcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDocumentCategoryRepository extends JpaRepository<MstDocumentCategory, Long> {

    Page<MstDocumentCategory> findByDcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDocumentCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDocumentCategory> findByDcNameContains(String key);

    MstDocumentCategory findByDcNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
