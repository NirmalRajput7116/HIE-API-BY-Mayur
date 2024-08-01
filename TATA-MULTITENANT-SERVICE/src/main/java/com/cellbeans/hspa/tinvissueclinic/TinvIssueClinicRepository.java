package com.cellbeans.hspa.tinvissueclinic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvIssueClinicRepository extends JpaRepository<TinvIssueClinic, Long> {

    Page<TinvIssueClinic> findByIcDateContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvIssueClinic> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvIssueClinic> findByIcDateContains(String key);

    List<TinvIssueClinic> findAllByIcFromStoreIdStoreIdAndIcToStoreIdStoreIdAndIsActiveTrueAndIsDeletedFalse(Long fromId, Long toId);

    List<TinvIssueClinic> findAllByIcFromStoreIdStoreIdAndIcToStoreIdStoreIdAndIssueClinicUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long fromId, Long toId, Long unitId);

    Page<TinvIssueClinic> findAllByIssueClinicUnitIdUnitIdAndIssueIndentStatusFalseAndIsActiveTrueAndIsDeletedFalse(long unitId, Pageable page);

}
            
