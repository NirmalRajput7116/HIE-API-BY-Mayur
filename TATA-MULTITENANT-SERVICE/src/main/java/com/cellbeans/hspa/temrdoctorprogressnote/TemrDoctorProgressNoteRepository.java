package com.cellbeans.hspa.temrdoctorprogressnote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemrDoctorProgressNoteRepository extends JpaRepository<TemrDoctorProgressNote, Long> {

    Page<TemrDoctorProgressNote> findByDpnIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrDoctorProgressNote> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrDoctorProgressNote> findByDpnIdContains(String key);

}
            
