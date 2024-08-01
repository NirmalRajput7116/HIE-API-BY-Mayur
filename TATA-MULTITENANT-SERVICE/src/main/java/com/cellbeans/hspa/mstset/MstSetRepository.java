package com.cellbeans.hspa.mstset;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstSetRepository extends JpaRepository<MstSet, Long> {

    Page<MstSet> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstSet> findBySetNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    List<MstSet> findBySetTabIdTabIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long tabId);

    @Query(value = "select DISTINCT mst.* from mst_dsf_field mdf inner join mst_set mst on mst.set_id = mdf.df_set_id where mdf.df_dsf_id=:DsfId and mdf.df_section_id =:dfSectionId and mdf.df_tab_id=:dfTabId and mdf.is_active = 1 and mdf.is_deleted = 0", nativeQuery = true)
    List<MstSet> findSetListByFormIdTabIdSetId(@Param("DsfId") Long DsfId, @Param("dfSectionId") Long dfSectionId, @Param("dfTabId") Long dfTabId);

}
