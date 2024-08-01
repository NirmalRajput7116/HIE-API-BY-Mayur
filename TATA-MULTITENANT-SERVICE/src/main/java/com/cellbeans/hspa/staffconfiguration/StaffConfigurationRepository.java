package com.cellbeans.hspa.staffconfiguration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffConfigurationRepository extends JpaRepository<StaffConfiguration, Long> {
    Page<StaffConfiguration> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    StaffConfiguration findByScUnitIdUnitIdAndScStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(long unitId, long staffId);

    StaffConfiguration findByScStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(long staffId);
}
