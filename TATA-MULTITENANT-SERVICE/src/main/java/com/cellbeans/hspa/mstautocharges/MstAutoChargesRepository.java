package com.cellbeans.hspa.mstautocharges;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstAutoChargesRepository extends JpaRepository<MstAutoCharges, Long> {

    Page<MstAutoCharges> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstAutoCharges> findByAcUnitIdUnitNameContainsOrAcServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String unitname, String servicename, Pageable page);

}
            
