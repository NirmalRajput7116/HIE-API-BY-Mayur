package com.cellbeans.hspa.mstsuperspeciality;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstSuperSpecialityRepository extends JpaRepository<MstSuperSpeciality, Long> {

    Page<MstSuperSpeciality> findBySsNameContainsAndIsActiveTrueAndIsDeletedFalseOrSsSpecialityIdSpecialityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderBySsName(String name, String name1, Pageable page);

    Page<MstSuperSpeciality> findAllByIsActiveTrueAndIsDeletedFalseOrderBySsName(Pageable page);

    List<MstSuperSpeciality> findBySsNameContains(String key);

    List<MstSuperSpeciality> findByssSpecialityIdSpecialityId(Long key);

    MstSuperSpeciality findBySsNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
