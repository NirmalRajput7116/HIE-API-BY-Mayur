package com.cellbeans.hspa.tinvstoreindentitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvStoreIndentItemRepository extends JpaRepository<TinvStoreIndentItem, Long> {

    Page<TinvStoreIndentItem> findBySiiItemIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvStoreIndentItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvStoreIndentItem> findBySiiItemIdContains(String key);

    List<TinvStoreIndentItem> findAllBySiiSiIdSiIdAndIsActiveTrueAndIsDeletedFalse(Long id);

}
            
