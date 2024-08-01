package com.cellbeans.hspa.invitemtype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvItemTypeRepository extends JpaRepository<InvItemType, Long> {

    Page<InvItemType> findAllByIsActiveTrueAndIsDeletedFalseOrderByItName(Pageable page);

    Page<InvItemType> findByItNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByItName(String name, Pageable page);
}


