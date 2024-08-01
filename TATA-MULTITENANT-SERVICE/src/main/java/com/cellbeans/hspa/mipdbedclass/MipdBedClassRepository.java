package com.cellbeans.hspa.mipdbedclass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdBedClassRepository extends JpaRepository<MipdBedClass, Long> {

    Page<MipdBedClass> findByBcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdBedClass> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdBedClass> findByBcNameContains(String key);

}
            
