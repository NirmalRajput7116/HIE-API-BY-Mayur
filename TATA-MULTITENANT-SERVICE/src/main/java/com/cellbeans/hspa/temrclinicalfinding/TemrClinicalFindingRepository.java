package com.cellbeans.hspa.temrclinicalfinding;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemrClinicalFindingRepository extends JpaRepository<TemrClinicalFinding, Long> {

    List<TemrClinicalFinding> findByCfNameContains(String key);

    Page<TemrClinicalFinding> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TemrClinicalFinding> findByCfNameContainsOrCfSpecialityIdSpecialityNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    List<TemrClinicalFinding> findByCfSpecialityIdSpecialityIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    List<TemrClinicalFinding> findByCfTimelineIdTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrClinicalFinding> findByCfTimelineIdTimelinePatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long id);

}
