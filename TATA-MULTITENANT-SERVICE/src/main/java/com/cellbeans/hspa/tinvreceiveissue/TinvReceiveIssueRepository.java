package com.cellbeans.hspa.tinvreceiveissue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvReceiveIssueRepository extends JpaRepository<TinvReceiveIssue, Long> {

    Page<TinvReceiveIssue> findByRiIssueDateContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvReceiveIssue> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvReceiveIssue> findByRiIssueDateContains(String key);

}
            
