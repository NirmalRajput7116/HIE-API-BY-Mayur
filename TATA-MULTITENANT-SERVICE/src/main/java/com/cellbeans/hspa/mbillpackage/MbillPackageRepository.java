package com.cellbeans.hspa.mbillpackage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MbillPackageRepository extends JpaRepository<MbillPackage, Long> {

    Page<MbillPackage> findAllByIsActiveTrue(Pageable page);

    Page<MbillPackage> findAllByPackageNameContainsAndIsActiveTrue(String key, Pageable page);

    List<MbillPackage> findAllByPackageNameEqualsAndIsActiveTrue(String key);

    @Transactional
    @Modifying
    @Query(value = "update MbillPackage set isActive=false where packageId=:packageId")
    public void deletePackage(@Param("packageId") Long packageId);

    List<MbillPackage> findAllByPackageNameContainsAndTariifId(String key, Integer tariffId);

}