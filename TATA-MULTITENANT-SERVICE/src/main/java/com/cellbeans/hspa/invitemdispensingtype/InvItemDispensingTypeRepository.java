package com.cellbeans.hspa.invitemdispensingtype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemDispensingTypeRepository extends JpaRepository<InvItemDispensingType, Long> {

    Page<InvItemDispensingType> findByIdtNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIdtName(String name, Pageable page);

    Page<InvItemDispensingType> findAllByIsActiveTrueAndIsDeletedFalseOrderByIdtName(Pageable page);

    List<InvItemDispensingType> findByIdtNameContains(String key);

}
            
