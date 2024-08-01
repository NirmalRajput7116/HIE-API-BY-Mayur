package com.cellbeans.hspa.invServiceWiseConsumption;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvServiceWisePackageRepository extends JpaRepository<InvServiceWiseConsumptionPackage, Long> {

    Page<InvServiceWiseConsumptionPackage> findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvServiceWiseConsumptionPackage> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvServiceWiseConsumptionPackage> findByServiceNameContainsAndServiceCodeContainsAndIsActiveTrueAndIsDeletedFalse(String packageName, String packageCode);

    InvServiceWiseConsumptionPackage findAllByServiceIdAndIsDeletedFalse(Long serviceId);

    InvServiceWiseConsumptionPackage findByServiceCodeContainsAndIsActiveTrueAndIsDeletedFalse(String name);

}