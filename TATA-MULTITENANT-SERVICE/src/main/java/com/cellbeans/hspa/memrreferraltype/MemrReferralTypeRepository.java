package com.cellbeans.hspa.memrreferraltype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrReferralTypeRepository extends JpaRepository<MemrReferralType , Long> {

    MemrReferralType findByReferralTypeId(Long id);

    Page<MemrReferralType> findByReferralTypeNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByReferralTypeName(String name, Pageable page);

    Page<MemrReferralType> findAllByIsActiveTrueAndIsDeletedFalseOrderByReferralTypeName(Pageable page);

    List<MemrReferralType> findByReferralTypeNameContainsAndIsActiveTrueAndIsDeletedFalse(String key);

    MemrReferralType findByReferralTypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
