package com.cellbeans.hspa.motsurgerynote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotSurgeryNoteRepository extends JpaRepository<MotSurgeryNote, Long> {

    Page<MotSurgeryNote> findBySnNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotSurgeryNote> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotSurgeryNote> findBySnNameContains(String key);

}
            
