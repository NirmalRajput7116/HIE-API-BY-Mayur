package com.cellbeans.hspa.trncompanydrug;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnCompanyDrugRepository extends JpaRepository<TrnCompanyDrug, Long> {
//    Page<TrnCompanyDrug> findByCdDrugIdDrugNameContainsOrCdCompanyIdCompanyIdAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnCompanyDrug> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*        List<TrnCompanyDrug> findByCdDrugIdDrugNameContains(String key);*/
}
            
