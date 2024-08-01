package com.cellbeans.hspa.licenseAgreement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LicenseAgreementRepository extends JpaRepository<LicenseAgreement, Long> {

    @Query(value = "select * from mst_temp where licence_id=:license_id", nativeQuery = true)
    LicenseAgreement findLicenseId(@Param("license_id") String license_id);

}
