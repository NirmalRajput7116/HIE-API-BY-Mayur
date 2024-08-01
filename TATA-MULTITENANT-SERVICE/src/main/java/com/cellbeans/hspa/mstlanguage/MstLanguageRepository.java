package com.cellbeans.hspa.mstlanguage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstLanguageRepository extends JpaRepository<MstLanguage, Long> {

    Page<MstLanguage> findByLanguageNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByLanguageName(String name, Pageable page);

    List<MstLanguage> findAllByIsDeletedFalse();

    Page<MstLanguage> findAllByIsActiveTrueAndIsDeletedFalseOrderByLanguageName(Pageable page);

    List<MstLanguage> findByLanguageNameContains(String key);

    MstLanguage findByLanguageNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
            
