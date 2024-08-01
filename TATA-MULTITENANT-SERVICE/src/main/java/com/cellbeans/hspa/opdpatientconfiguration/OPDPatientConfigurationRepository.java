package com.cellbeans.hspa.opdpatientconfiguration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OPDPatientConfigurationRepository extends JpaRepository<OPDPatientConfiguration, Long> {

    Page<OPDPatientConfiguration> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<OPDPatientConfiguration> findByConfigUnitIdUnitNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

}
