package com.cellbeans.hspa.memrreferraltemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemrReferralTemplateRepository extends JpaRepository<MemrReferralTemplate, Long> {

    Page<MemrReferralTemplate> findByRtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrReferralTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrReferralTemplate> findByRtNameContains(String key);

    @Query(value = "SELECT count(mc.rt_name) FROM memr_referral_template mc WHERE mc.rt_name=:rt_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByClusterName(@Param("rt_name") String rt_name);

}
            
