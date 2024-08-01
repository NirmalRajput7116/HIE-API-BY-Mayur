package com.cellbeans.hspa.temrreferraloutsource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemrReferralOutSourceRepository extends JpaRepository<TemrReferralOutSource, Long> {

    Page<TemrReferralOutSource> findByRosReIdReNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrReferralOutSource> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*        List<TemrReferralOutSource> findByRetNameContains(String key);*/
}
            
