package com.cellbeans.hspa.mstnationality;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstNationalityRepository extends JpaRepository<MstNationality, Long> {

    Page<MstNationality> findByNationalityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByNationalityNameAsc(String name, Pageable page);

    Page<MstNationality> findAllByIsActiveTrueAndIsDeletedFalseOrderByNationalityNameAsc(Pageable page);

    List<MstNationality> findByNationalityNameContains(String key);

    MstNationality findByNationalityNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
            
