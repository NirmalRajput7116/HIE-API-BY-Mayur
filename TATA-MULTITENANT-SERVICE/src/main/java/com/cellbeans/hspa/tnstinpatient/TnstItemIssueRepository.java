package com.cellbeans.hspa.tnstinpatient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TnstItemIssueRepository extends JpaRepository<TnstItemIssue, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update tnst_item_issue ti set ti.is_billed =:isBilled where ti.ii_id =:iiId", nativeQuery = true)
    int updateItemIssueForBilling(@Param("isBilled") Boolean isBilled, @Param("iiId") long iiId);

}