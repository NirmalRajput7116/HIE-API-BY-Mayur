package com.cellbeans.hspa.mstanaesthesianote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstAnaesthesiaNoteRepository extends JpaRepository<MstAnaesthesiaNote, Long> {

    Page<MstAnaesthesiaNote> findByAnShortDescriptionContainsAndIsActiveTrueAndIsDeletedFalseOrAnLongDescriptionContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    Page<MstAnaesthesiaNote> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAnaesthesiaNote> findByAnShortDescriptionContains(String key);

}
            
