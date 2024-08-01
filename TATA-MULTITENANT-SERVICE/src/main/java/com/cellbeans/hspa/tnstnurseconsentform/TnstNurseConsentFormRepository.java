package com.cellbeans.hspa.tnstnurseconsentform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TnstNurseConsentFormRepository extends JpaRepository<TnstNurseConsentForm, Long> {

    Page<TnstNurseConsentForm> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//    Page<TnstNurseConsentForm> findByNpnAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TnstNurseConsentForm> findByNcfAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(Long admissionId, Pageable page);

}
