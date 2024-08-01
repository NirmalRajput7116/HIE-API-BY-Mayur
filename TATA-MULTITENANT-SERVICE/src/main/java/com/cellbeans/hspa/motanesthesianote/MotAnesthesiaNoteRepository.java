package com.cellbeans.hspa.motanesthesianote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotAnesthesiaNoteRepository extends JpaRepository<MotAnesthesiaNote, Long> {

    Page<MotAnesthesiaNote> findByAnNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotAnesthesiaNote> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotAnesthesiaNote> findByAnNameContains(String key);

}
            
