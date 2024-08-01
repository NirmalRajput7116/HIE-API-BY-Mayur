package com.cellbeans.hspa.trnissuemanagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnIssueManagementRepository extends JpaRepository<TrnIssueManagement, Long> {

    Page<TrnIssueManagement> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TrnIssueManagement> findAllByIssueTitleContainsAndIsActiveTrueAndIsDeletedFalse(String actionName, Pageable page);
//    List<TrnIssueManagement.java> findByActionNameContainsAndIsActiveTrueAndIsDeletedFalse(String actionName);

}
