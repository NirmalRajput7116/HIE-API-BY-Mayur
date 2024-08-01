package com.cellbeans.hspa.invcompoundname;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvCompoundNameRepository extends JpaRepository<InvCompoundName, Long> {

    Page<InvCompoundName> findByCompoundNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvCompoundName> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvCompoundName> findByCompoundNameContains(String key);

}
            
