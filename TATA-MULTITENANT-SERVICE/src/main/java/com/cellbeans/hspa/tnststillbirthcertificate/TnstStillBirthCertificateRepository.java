package com.cellbeans.hspa.tnststillbirthcertificate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TnstStillBirthCertificateRepository extends JpaRepository<TnstStillBirthCertificate, Long> {
    // Page<TnstStillBirthCertificate> findByAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TnstStillBirthCertificate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TnstStillBirthCertificate> findBySbcIdAndIsActiveTrueAndIsDeletedFalse(Long name, Pageable page);

    Page<TnstStillBirthCertificate> findAllBySbcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long unitid, Pageable page);

    Page<TnstStillBirthCertificate> findAllBySbcAuthorizedStaffIdStaffUserIdUserFullnameContainsOrSbcChildNameContainsAndSbcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String search, String childname, Long untid, Pageable page);
    // List<TnstStillBirthCertificate> findByContains(String key);
}
            
