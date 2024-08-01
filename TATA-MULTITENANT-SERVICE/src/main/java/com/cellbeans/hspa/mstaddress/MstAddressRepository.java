package com.cellbeans.hspa.mstaddress;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstAddressRepository extends JpaRepository<MstAddress, Long> {

    Page<MstAddress> findByAddressAddressContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstAddress> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAddress> findByAddressAddressContains(String key);

}
            
