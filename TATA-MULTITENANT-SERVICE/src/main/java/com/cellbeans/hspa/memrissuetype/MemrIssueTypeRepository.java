package com.cellbeans.hspa.memrissuetype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrIssueTypeRepository extends JpaRepository<MemrIssueType, Long> {
    MemrIssueType findByIssueTypeId(Long id);

    Page<MemrIssueType> findByIssueTypeNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIssueTypeName(String name, Pageable page);

    Page<MemrIssueType> findAllByIsActiveTrueAndIsDeletedFalseOrderByIssueTypeName(Pageable page);

    List<MemrIssueType> findByIssueTypeNameContainsAndIsActiveTrueAndIsDeletedFalse(String key);

    MemrIssueType findByIssueTypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}