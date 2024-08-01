package com.cellbeans.hspa.mstsection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstSectionRepository extends JpaRepository<MstSection, Long> {

    Page<MstSection> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstSection> findBySectionNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    @Query(value = "select DISTINCT ms.* from mst_dsf_field mdf inner join mst_section ms on ms.section_id = mdf.df_section_id where mdf.df_dsf_id= :DsfId and mdf.is_active = 1 and mdf.is_deleted = 0", nativeQuery = true)
    List<MstSection> findSectionListByFormId(@Param("DsfId") Long DsfId);
}
