package com.cellbeans.hspa.memrdoctorsnote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemrDoctorsNoteRepository extends JpaRepository<MemrDoctorsNote, Long> {

    Page<MemrDoctorsNote> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    //   Page<MemrDiseaseType> findByDtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
    Page<MemrDoctorsNote> findByDnEngNoteContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

}
