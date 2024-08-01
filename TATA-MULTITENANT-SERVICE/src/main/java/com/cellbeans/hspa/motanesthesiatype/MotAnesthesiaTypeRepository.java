package com.cellbeans.hspa.motanesthesiatype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotAnesthesiaTypeRepository extends JpaRepository<MotAnesthesiaType, Long> {

    Page<MotAnesthesiaType> findByAtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotAnesthesiaType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotAnesthesiaType> findByAtNameContains(String key);

}
            
