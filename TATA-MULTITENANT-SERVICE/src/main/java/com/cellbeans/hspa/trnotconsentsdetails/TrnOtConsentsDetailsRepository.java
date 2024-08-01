package com.cellbeans.hspa.trnotconsentsdetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtConsentsDetailsRepository extends JpaRepository<TrnOtConsentsDetails, Long> {
//    Page<TrnOtConsentsDetails> findByocdIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//    Page<TrnOtConsentsDetails> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//    List<TrnOtConsentsDetails> findByOcdIdContains(String key);
//
//    List<TrnOtConsentsDetails> findByocdPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtConsentsDetails> findAllByOcdOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtConsentsDetails> findAllByOcdOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtConsentsDetails> findAllByOcdProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

}
            
