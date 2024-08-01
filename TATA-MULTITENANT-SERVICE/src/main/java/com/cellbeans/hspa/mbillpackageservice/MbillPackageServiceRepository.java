package com.cellbeans.hspa.mbillpackageservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillPackageServiceRepository extends JpaRepository<MbillPackageService, Long> {

    List<MbillPackageService> findAllByPkgIdPackageId(Long pkgId);

}
            
