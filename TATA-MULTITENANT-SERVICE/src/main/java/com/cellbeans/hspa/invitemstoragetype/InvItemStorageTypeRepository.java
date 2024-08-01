package com.cellbeans.hspa.invitemstoragetype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemStorageTypeRepository extends JpaRepository<InvItemStorageType, Long> {

    Page<InvItemStorageType> findByIstNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvItemStorageType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvItemStorageType> findByIstNameContains(String key);

}
            
