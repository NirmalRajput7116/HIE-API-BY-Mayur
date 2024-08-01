package com.cellbeans.hspa.tinvstoreindent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvStoreIndentRepository extends JpaRepository<TinvStoreIndent, Long> {

    Page<TinvStoreIndent> findBySiTotalAmountContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvStoreIndent> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvStoreIndent> findBySiTotalAmountContains(String key);

    List<TinvStoreIndent> findBySiFromStoreIdStoreIdAndSiToStoreIdStoreIdAndStoreIndentUnitIdUnitIdAndSiFreezeIndentTrueAndSiIndentApproveTrueAndIsActiveTrueAndIsDeletedFalse(Long from_id, Long to_id, Long unit_id);

    List<TinvStoreIndent> findBySiFromStoreIdStoreIdAndSiToStoreIdStoreIdAndSiFreezeIndentTrueAndSiIndentApproveTrueAndIsActiveTrueAndIsDeletedFalse(Long from_id, Long to_id);

}
            
