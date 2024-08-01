package com.cellbeans.hspa.mstorg;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstOrgRepository extends JpaRepository<MstOrg, Long> {

    Page<MstOrg> findByOrgNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByOrgNameAsc(String name, Pageable page);

    Page<MstOrg> findAllByIsActiveTrueAndIsDeletedFalseOrderByOrgNameAsc(Pageable page);

    List<MstOrg> findByOrgNameContains(String key);

    MstOrg findAllByOrgIdEquals(Long orgId);

    MstOrg findByOrgNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
