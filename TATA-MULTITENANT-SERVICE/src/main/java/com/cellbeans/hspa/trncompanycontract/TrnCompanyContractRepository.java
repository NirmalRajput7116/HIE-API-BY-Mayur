package com.cellbeans.hspa.trncompanycontract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnCompanyContractRepository extends JpaRepository<TrnCompanyContract, Long> {

    Page<TrnCompanyContract> findByCcCompanyIdCompanyNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnCompanyContract> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*        List<TrnCompanyContract> findByCdNameContains(String key);*/
}
            
