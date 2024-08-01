package com.cellbeans.hspa.msttab;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstTabRepository extends JpaRepository<MstTab, Long> {

    Page<MstTab> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstTab> findByTabNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    List<MstTab> findByTabSectionIdSectionIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long sectionId);

    @Query(value = "select DISTINCT mt.* from mst_dsf_field mdf inner join mst_tab mt on mt.tab_id = mdf.df_tab_id where mdf.df_dsf_id= :DsfId and mdf.df_section_id =:dfSectionId and mdf.is_active = 1 and mdf.is_deleted = 0", nativeQuery = true)
    List<MstTab> findTabListByFormIdSectionId(@Param("DsfId") Long DsfId, @Param("dfSectionId") Long dfSectionId);

}
