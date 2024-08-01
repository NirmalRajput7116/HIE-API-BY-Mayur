package com.cellbeans.hspa.mactransctiontype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacTransctionTypeRepository extends JpaRepository<MacTransctionType, Long> {

    Page<MacTransctionType> findByTtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacTransctionType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacTransctionType> findByTtNameContains(String key);

}
            
