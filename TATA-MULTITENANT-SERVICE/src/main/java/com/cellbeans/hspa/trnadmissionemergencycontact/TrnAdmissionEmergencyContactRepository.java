package com.cellbeans.hspa.trnadmissionemergencycontact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnAdmissionEmergencyContactRepository extends JpaRepository<TrnAdmissionEmergencyContact, Long> {

    Page<TrnAdmissionEmergencyContact> findByAecFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnAdmissionEmergencyContact> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnAdmissionEmergencyContact> findByAecFirstnameContains(String key);

}
            
