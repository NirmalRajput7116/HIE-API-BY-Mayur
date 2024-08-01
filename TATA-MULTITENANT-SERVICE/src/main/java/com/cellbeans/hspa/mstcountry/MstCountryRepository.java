package com.cellbeans.hspa.mstcountry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstCountryRepository extends JpaRepository<MstCountry, Long> {

    Page<MstCountry> findByCountryNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstCountry> findAllByIsActiveTrueAndIsDeletedFalseOrderByCountryNameAsc(Pageable page);

    List<MstCountry> findByCountryNameContains(String key);

    MstCountry findByCountryNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
