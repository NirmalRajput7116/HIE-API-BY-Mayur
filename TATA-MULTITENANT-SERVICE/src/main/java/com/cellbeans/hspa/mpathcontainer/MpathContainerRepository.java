package com.cellbeans.hspa.mpathcontainer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathContainerRepository extends JpaRepository<MpathContainer, Long> {

    Page<MpathContainer> findByContainerNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MpathContainer> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathContainer> findByContainerNameContains(String key);

}
            
