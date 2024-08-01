package com.cellbeans.hspa.temrvisitallergy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TemrVisitAllergyRepository extends JpaRepository<TemrVisitAllergy, Long> {

    Page<TemrVisitAllergy> findByVaIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrVisitAllergy> findByVaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long patientId, Pageable page);

    Page<TemrVisitAllergy> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    @Modifying
    @Transactional
    @Query(value = " DELETE FROM `temr_visit_allergy` WHERE va_timeline_id =?1 and va_inv_item_id =?2;", nativeQuery = true)
    int deleteallergy(@Param("timelineID") Long timelineID, @Param("itemID") Long itemID);
    // Page<TemrVisitAllergy> findAllByVaPatientIdPatientId
    //  List<TemrVisitAllergy> findByAllergyNameContains(String key);

    List<TemrVisitAllergy> findByVaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long id);

    @Query(value = " select * from temr_visit_allergy tva where tva.is_deleted = 0 and tva.va_patient_id = :patientId  group by tva.va_compound_id", nativeQuery = true)
    List<TemrVisitAllergy> findByVaPatientIdPatientIdEqualsAndVaAsIdAsIdEqualsAndIsDeletedFalse(@Param("patientId") Long patientId);
}
            
