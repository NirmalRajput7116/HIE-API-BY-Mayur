package com.cellbeans.hspa.tnstdeathcertificate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TnstDeathCertificateRepository extends JpaRepository<TnstDeathCertificate, Long> {

    //Page<TnstDeathCertificate> findByAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
    Page<TnstDeathCertificate> findByDcIdAndIsActiveTrueAndIsDeletedFalse(Long name, Pageable page);

    Page<TnstDeathCertificate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
    // List<TnstDeathCertificate> findByContains(String key);

    Page<TnstDeathCertificate> findAllByDcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long unit, Pageable page);

    Page<TnstDeathCertificate> findAllByDcAuthorizedStaffIdStaffUserIdUserFullnameContainsOrDcPatientNameContainsAndDcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String search, String childname, Long untid, Pageable page);

}
            
