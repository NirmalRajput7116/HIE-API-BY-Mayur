package com.cellbeans.hspa.mstgender;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstGenderRepository extends JpaRepository<MstGender, Long> {

    Page<MstGender> findByGenderNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByGenderNameAsc(String name, Pageable page);

    Page<MstGender> findAllByIsActiveTrueAndIsDeletedFalseOrderByGenderNameAsc(Pageable page);

    List<MstGender> findByGenderNameContains(String key);

    MstGender findByGenderNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
