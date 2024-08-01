package com.cellbeans.hspa.invitemcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemCategoryRepository extends JpaRepository<InvItemCategory, Long> {

    Page<InvItemCategory> findByIcNameContainsAndIsActiveTrueAndIsDeletedFalseOrIcMbillServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalseOrIcLedgerNameContainsAndIsActiveTrueAndIsDeletedFalseOrIcGroupIdIgNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIcName(String name, String name3, String name2, String name1, Pageable page);

    Page<InvItemCategory> findAllByIsActiveTrueAndIsDeletedFalseOrderByIcName(Pageable page);

    List<InvItemCategory> findByIcNameContains(String key);

}

