package com.cellbeans.hspa.tinvreturnissueitem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvReturnIssueItemRepository extends JpaRepository<TinvReturnIssueItem, Long> {

    List<TinvReturnIssueItem> findAllByReiItReiIdReiIdAndIsActiveTrueAndIsDeletedFalse(Long id);

}
