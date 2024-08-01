package com.cellbeans.hspa.mstanaesthesiatype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstAnaesthesiaTypeRepository extends JpaRepository<MstAnaesthesiaType, Long> {

    Page<MstAnaesthesiaType> findByAtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstAnaesthesiaType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAnaesthesiaType> findByAtNameContains(String key);

}
            
