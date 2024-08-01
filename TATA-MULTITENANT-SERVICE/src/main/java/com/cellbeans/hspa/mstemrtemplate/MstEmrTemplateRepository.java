package com.cellbeans.hspa.mstemrtemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstEmrTemplateRepository extends JpaRepository<MstEmrTemplate, Long> {

    Page<MstEmrTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstEmrTemplate> findAllByIsActiveTrueAndIsDeletedFalse();

    Page<MstEmrTemplate> findAllByEtNameContainsAndIsActiveTrueAndIsDeletedFalse(String search, Pageable page);

    Page<MstEmrTemplate> findAllByEtStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(long staffId, Pageable page);

    Page<MstEmrTemplate> findAllByEtNameContainsAndEtStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(String search, long staffId, Pageable page);

}
            
