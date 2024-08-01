package com.cellbeans.hspa.nstnursingstationdefination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NstNursingStationDefinationRepository extends JpaRepository<NstNursingStationDefination, Long> {
    //   Page<NstNursingStationDefination> findByNsdNameAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<NstNursingStationDefination> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<NstNursingStationDefination> findAllByNsdNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
    //List<NstNursingStationDefination> findByContains(String key);

    NstNursingStationDefination findByNsdIdAndIsActiveTrueAndIsDeletedFalse(Long nstId);

    List<NstNursingStationDefination> findAllByNsdUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long nstId);

}
            

