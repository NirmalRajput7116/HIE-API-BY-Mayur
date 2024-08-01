package com.cellbeans.hspa.tinvpharmacysaleitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvPharmacySaleItemRepository extends JpaRepository<TinvPharmacySaleItem, Long> {

    Page<TinvPharmacySaleItem> findByPsiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvPharmacySaleItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvPharmacySaleItem> findByPsiItemIdContains(String key);

    List<TinvPharmacySaleItem> findByPsiPsIdPsId(Long key);

}
            
