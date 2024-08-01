package com.cellbeans.hspa.trnotequipmentdetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtEquipmentDetailsRepository extends JpaRepository<TrnOtEquipmentDetails, Long> {
//    Page<TrnOtEquipmentDetails> findByoedIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//
//
//    Page<TrnOtEquipmentDetails> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//
//    List<TrnOtEquipmentDetails> findByOedIdContains(String key);
//
//    List<TrnOtEquipmentDetails> findAllByOedPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtEquipmentDetails> findAllByOedOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtEquipmentDetails> findAllByOedOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtEquipmentDetails> findAllByOedProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

}
            
