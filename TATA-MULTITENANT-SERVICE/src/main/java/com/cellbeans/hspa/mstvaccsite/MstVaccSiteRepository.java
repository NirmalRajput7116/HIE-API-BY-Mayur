package com.cellbeans.hspa.mstvaccsite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstVaccSiteRepository extends JpaRepository<MstVaccSite, Long> {

    Page<MstVaccSite> findByVsIdContainsAndIsDeletedFalse(String name, Pageable page);

    Page<MstVaccSite> findByVsSiteContainsAndIsDeletedFalse(String name, Pageable page);

    Page<MstVaccSite> findAllByIsDeletedFalse(Pageable page);

    List<MstVaccSite> findByVsIdContains(String key);

    List<MstVaccSite> findAllByIsDeletedFalse();

    MstVaccSite findByVsSiteEqualsAndIsDeletedFalse(String key);

}
