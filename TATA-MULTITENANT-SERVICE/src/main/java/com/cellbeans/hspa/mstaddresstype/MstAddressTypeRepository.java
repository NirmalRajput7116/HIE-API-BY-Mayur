package com.cellbeans.hspa.mstaddresstype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstAddressTypeRepository extends JpaRepository<MstAddressType, Long> {

    Page<MstAddressType> findByAtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstAddressType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAddressType> findByAtNameContains(String key);

}
            
