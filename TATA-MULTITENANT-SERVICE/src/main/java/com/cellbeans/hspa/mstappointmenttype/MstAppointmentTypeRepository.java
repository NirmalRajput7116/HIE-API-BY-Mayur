package com.cellbeans.hspa.mstappointmenttype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstAppointmentTypeRepository extends JpaRepository<MstAppointmentType, Long> {

    Page<MstAppointmentType> findByAtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstAppointmentType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAppointmentType> findByAtNameContains(String key);

    MstAppointmentType findByAtNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
