package com.cellbeans.hspa.trnadditionaldoctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnAdditionalDoctorRepository extends JpaRepository<TrnAdditionalDoctor, Long> {

    Page<TrnAdditionalDoctor> findByAdAdmissionIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnAdditionalDoctor> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnAdditionalDoctor> findByAdAdmissionIdContains(String key);

}
            
