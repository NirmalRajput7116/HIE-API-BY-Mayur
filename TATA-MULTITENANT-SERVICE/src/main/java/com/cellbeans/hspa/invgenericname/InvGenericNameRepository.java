package com.cellbeans.hspa.invgenericname;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvGenericNameRepository extends JpaRepository<InvGenericName, Long> {

    Page<InvGenericName> findByGenericNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvGenericName> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvGenericName> findByGenericNameContains(String key);

}
            
