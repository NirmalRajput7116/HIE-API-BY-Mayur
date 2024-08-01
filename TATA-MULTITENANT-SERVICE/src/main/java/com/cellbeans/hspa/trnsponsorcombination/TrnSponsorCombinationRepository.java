package com.cellbeans.hspa.trnsponsorcombination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrnSponsorCombinationRepository extends JpaRepository<TrnSponsorCombination, Long> {

    Page<TrnSponsorCombination> findByAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    TrnSponsorCombination findByScUserIdUserIdEqualsAndScVisitIdVisitIdEqualsAndScCtIdCtIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long userid, Long visitid, Long scid);

    //@Modifying
    //@Query("update trn_sponsor_combination ear set ear.sc_priority_id = :priority where ear.sc_user_id = :id")
    // void updatepriority(long roleId,String priority_id);
    Page<TrnSponsorCombination> findByscUserIdContainsAndIsActiveTrueAndIsDeletedFalse(String userID, Pageable page);

    Page<TrnSponsorCombination> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    //List<TrnSponsorCombination> findByscUserIdContains(String key);
    List<TrnSponsorCombination> findByScPolicyCodeContains(String key);

    @Query(value = "select * from trn_sponsor_combination sp where is_active = 1 and is_deleted = 0 and sp.sc_user_id = :u_id", nativeQuery = true)
        // and DATE(sp.sc_expiry_date) > CURDATE() line comented by bhushan for company dropdown not working in advance
    List<TrnSponsorCombination> findByScUserIdUserIdEqualsAndScExpiryDateIsGreaterThanAndIsDeletedFalse(@Param("u_id") Long u_id);

    List<TrnSponsorCombination> findByScUserIdUserIdEqualsAndIsDeletedFalse(Long key);

    List<TrnSponsorCombination> findByScVisitIdVisitIdEqualsAndIsDeletedFalse(Long visitId);

    List<TrnSponsorCombination> findByScVisitIdVisitIdEqualsAndIsDeletedFalseAndIsActiveTrue(Long visitId);

}
            
