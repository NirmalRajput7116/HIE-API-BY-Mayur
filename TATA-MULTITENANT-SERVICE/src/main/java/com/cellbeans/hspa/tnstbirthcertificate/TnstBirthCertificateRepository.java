package com.cellbeans.hspa.tnstbirthcertificate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TnstBirthCertificateRepository extends JpaRepository<TnstBirthCertificate, Long> {

    Page<TnstBirthCertificate> findByBcIdAndIsActiveTrueAndIsDeletedFalse(Long name, Pageable page);

    Page<TnstBirthCertificate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    // List<TnstBirthCertificate> findByContains(String key);
    Page<TnstBirthCertificate> findAllByBcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long untid, Pageable page);

    Page<TnstBirthCertificate> findAllByBcAuthorizedStaffIdStaffUserIdUserFullnameContainsOrBcChildNameContainsAndBcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String search, String childname, Long untid, Pageable page);
}
            
