package com.cellbeans.hspa.mstspeciality;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstSpecialityRepository extends JpaRepository<MstSpeciality, Long> {

    Page<MstSpeciality> findBySpecialityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderBySpecialityName(String name, Pageable page);

    Page<MstSpeciality> findAllByIsActiveTrueAndIsDeletedFalseOrderBySpecialityName(Pageable page);

    List<MstSpeciality> findBySpecialityNameContains(String key);

    MstSpeciality findBySpecialityNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
