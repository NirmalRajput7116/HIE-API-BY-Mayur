package com.cellbeans.hspa.tinvissueclinicitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvIssueClinicItemRepository extends JpaRepository<TinvIssueClinicItem, Long> {

    Page<TinvIssueClinicItem> findByIciItemIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvIssueClinicItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvIssueClinicItem> findByIciItemIdContains(String key);

    List<TinvIssueClinicItem> findByIciIcIdIcIdAndIsActiveTrueAndIsDeletedFalse(long key);

    List<TinvIssueClinicItem> findByIciRiIdRiIdAndIsActiveTrueAndIsDeletedFalse(long key);

}
            
