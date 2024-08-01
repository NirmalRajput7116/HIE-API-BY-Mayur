package com.cellbeans.hspa.mstsurgerynote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstSurgeryNoteRepository extends JpaRepository<MstSurgeryNote, Long> {

    Page<MstSurgeryNote> findBysnShortDescriptionContainsAndIsActiveTrueAndIsDeletedFalseOrSnLongDescriptionAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    Page<MstSurgeryNote> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstSurgeryNote> findBySnShortDescriptionContains(String key);

}
            
