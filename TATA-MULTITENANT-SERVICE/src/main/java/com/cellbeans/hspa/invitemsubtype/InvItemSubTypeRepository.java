package com.cellbeans.hspa.invitemsubtype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemSubTypeRepository extends JpaRepository<InvItemSubType, Long> {

    Page<InvItemSubType> findAllByIsActiveTrueAndIsDeletedFalseOrderByIstName(Pageable page);

    Page<InvItemSubType> findByIstNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIstName(String name, Pageable page);

    List<InvItemSubType> findAllByIstTIdItIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long id);
}

