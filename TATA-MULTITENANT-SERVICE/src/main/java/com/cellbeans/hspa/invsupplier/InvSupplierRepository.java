package com.cellbeans.hspa.invsupplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvSupplierRepository extends JpaRepository<InvSupplier, Long> {

    Page<InvSupplier> findBySupplierNameContainsAndIsActiveTrueAndIsDeletedFalseOrSupplierCityIdCityNameContainsAndIsActiveTrueAndIsDeletedFalseOrSupplierContactPersonOneNameContainsAndIsActiveTrueAndIsDeletedFalseOrSupplierContactPersonTwoNameContainsAndIsActiveTrueAndIsDeletedFalseOrSupplierPhoneNumberContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name4, String name3, String name2, String name1, Pageable page);

    Page<InvSupplier> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvSupplier> findBySupplierNameContains(String key);

}
            
