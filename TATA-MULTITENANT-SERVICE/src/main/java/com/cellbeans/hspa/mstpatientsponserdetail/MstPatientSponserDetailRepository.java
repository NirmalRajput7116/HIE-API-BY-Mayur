package com.cellbeans.hspa.mstpatientsponserdetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstPatientSponserDetailRepository extends JpaRepository<MstPatientSponserDetail, Long> {

    Page<MstPatientSponserDetail> findByPsdCompanyIdCompanyNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstPatientSponserDetail> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstPatientSponserDetail> findByPsdCompanyIdCompanyNameContains(String key);

}
            
