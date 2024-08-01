package com.cellbeans.hspa.mstfield;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstFieldRepository extends JpaRepository<MstField, Long> {
    List<MstField> findByFieldNameContains(String key);

    @Query("SELECT DISTINCT fieldName FROM MstField")
    List<MstField> findDistinctFieldName();

    List<MstField> findByFieldNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

    Page<MstField> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstField> findByFieldNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    List<MstField> findByFieldSetIdSetIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long setId);

    @Query(value = "select mf.* from mst_dsf_field df inner join mst_field mf on mf.field_id=df.df_field_id where df.df_dsf_id= :DsfId and df.is_active = 1 and df.is_deleted = 0", nativeQuery = true)
    List<MstField> findByDfDsfIdDsfIdEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("DsfId") Long DsfId);

    @Query(value = "select mf.* from mst_dsf_field mdf inner join mst_field mf on mf.field_id = mdf.df_field_id where mdf.df_dsf_id=:DsfId and mdf.df_section_id =:dfSectionId and mdf.df_tab_id=:dfTabId and mdf.df_set_id=:dfSetId and mdf.is_active = 1 and mdf.is_deleted = 0", nativeQuery = true)
    List<MstField> findFieldListByDfDsfIdDsfIdSectionIdTabIdSetId(@Param("DsfId") Long DsfId, @Param("dfSectionId") Long dfSectionId, @Param("dfTabId") Long dfTabId, @Param("dfSetId") Long dfSetId);

}
