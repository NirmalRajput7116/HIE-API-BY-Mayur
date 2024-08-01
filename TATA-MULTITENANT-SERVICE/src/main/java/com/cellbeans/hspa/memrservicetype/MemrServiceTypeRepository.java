package com.cellbeans.hspa.memrservicetype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrServiceTypeRepository extends JpaRepository<MemrServiceType, Long> {
    MemrServiceType findByServiceTypeId(Long id);

    Page<MemrServiceType> findByServiceTypeNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByServiceTypeName(String name, Pageable page);

    Page<MemrServiceType> findAllByIsActiveTrueAndIsDeletedFalseOrderByServiceTypeName(Pageable page);

    List<MemrServiceType> findByServiceTypeNameContainsAndIsActiveTrueAndIsDeletedFalse(String key);

    MemrServiceType findByServiceTypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}