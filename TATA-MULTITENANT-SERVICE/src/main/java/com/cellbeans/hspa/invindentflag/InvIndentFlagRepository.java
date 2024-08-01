package com.cellbeans.hspa.invindentflag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvIndentFlagRepository extends JpaRepository<InvIndentFlag, Long> {

    Page<InvIndentFlag> findByIfNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvIndentFlag> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvIndentFlag> findByIfNameContains(String key);

}
            
