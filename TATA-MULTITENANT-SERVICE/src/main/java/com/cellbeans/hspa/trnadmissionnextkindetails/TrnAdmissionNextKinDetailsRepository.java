package com.cellbeans.hspa.trnadmissionnextkindetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnAdmissionNextKinDetailsRepository extends JpaRepository<TrnAdmissionNextKinDetails, Long> {

    Page<TrnAdmissionNextKinDetails> findByAnkdFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnAdmissionNextKinDetails> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnAdmissionNextKinDetails> findByAnkdFirstnameContains(String key);

}
            
