package com.cellbeans.hspa.tnstpostmortem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TnstPostMortemRepository extends JpaRepository<TnstPostMortem, Long> {
    //Page<TnstPostMortem> findByAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TnstPostMortem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TnstPostMortem> findByPcIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    // List<TnstPostMortem> findByContains(String key);
    Page<TnstPostMortem> findAllByPcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    Page<TnstPostMortem> findAllByPcAuthorizedStaffIdStaffUserIdUserFullnameContainsOrPcPatientNameContainsAndPcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String search, String childname, Long untid, Pageable page);

}
            
