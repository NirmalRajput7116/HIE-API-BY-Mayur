package com.cellbeans.hspa.mpathagency;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathAgencyRepository extends JpaRepository<MpathAgency, Long> {

    Page<MpathAgency> findByAgencyNameContainsOrAgencyCodeContainsOrAgencyLegalNameContainsOrAgencyAddressContainsOrAgencyEmailContainsOrAgencyAdminFirstNameContainsOrAgencyAdminLastNameContainsOrAgencyAdminEmailContainsAndIsActiveTrueAndIsDeletedFalse(String name1, String name2, String name3, String name4, String name5, String name6, String name7, String name, Pageable page);

    Page<MpathAgency> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathAgency> findByAgencyNameContains(String key);

    List<MpathAgency> findByAgencyNameContainsAndIsActiveTrueAndIsDeletedFalse(String agencyName);

}
            
