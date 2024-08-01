package com.cellbeans.hspa.invstore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvStoreRepository extends JpaRepository<InvStore, Long> {

    Page<InvStore> findByStoreNameContainsAndIsActiveTrueAndIsDeletedFalseOrStoreCodeContainsAndIsActiveTrueAndIsDeletedFalseOrStoreAddressContainsAndIsActiveTrueAndIsDeletedFalseOrStoreContactContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, String name2, String name3, Pageable page);
//    Page<InvStore> findByStoreNameContainsAndStoreUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseOrStoreCodeContainsAndStoreUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseOrStoreAddressContainsAndStoreUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseOrStoreContactContainsAndStoreUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String name, String name1, String name2, String name3, long unitId, Pageable page);

    Page<InvStore> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvStore> findAllByStoreUnitIdUnitId(Long key);

    Page<InvStore> findAllByStoreUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Pageable page, long unitId);

    List<InvStore> findByStoreNameContains(String key);

}
            
