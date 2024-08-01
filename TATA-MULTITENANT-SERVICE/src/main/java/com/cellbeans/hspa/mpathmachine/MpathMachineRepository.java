package com.cellbeans.hspa.mpathmachine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathMachineRepository extends JpaRepository<MpathMachine, Long> {

    Page<MpathMachine> findByMachineNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MpathMachine> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathMachine> findByMachineNameContains(String key);

}
            
