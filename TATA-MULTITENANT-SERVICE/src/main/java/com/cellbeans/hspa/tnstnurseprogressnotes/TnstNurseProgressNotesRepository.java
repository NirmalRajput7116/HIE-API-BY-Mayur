package com.cellbeans.hspa.tnstnurseprogressnotes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TnstNurseProgressNotesRepository extends JpaRepository<TnstNurseProgressNotes, Long> {
    //  Page<TnstNurseProgressNotes> findByNpnIdAndIsActiveTrueAndIsDeletedFalse(Long name, Pageable page);

    Page<TnstNurseProgressNotes> findByNpnAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(Long admissionId, Pageable page);

    Page<TnstNurseProgressNotes> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*  List<TnstNurseProgressNotes> findByContains(String key);*/
}


