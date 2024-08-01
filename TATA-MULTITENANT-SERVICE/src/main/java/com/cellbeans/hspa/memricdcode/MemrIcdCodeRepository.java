package com.cellbeans.hspa.memricdcode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemrIcdCodeRepository extends JpaRepository<MemrIcdCode, Long> {

    Page<MemrIcdCode> findByIcCodeContainsAndIsActiveTrueAndIsDeletedFalseOrIcDescriptionContainsAndIsActiveTrueAndIsDeletedFalseOrIcDtIdDtNameContainsAndIsActiveTrueAndIsDeletedFalseOrIcDscIdDcsNameContainsAndIsActiveTrueAndIsDeletedFalseOrIcDcIdDcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name4, String name3, String name2, String name1, Pageable page);

    Page<MemrIcdCode> findByIcDescriptionContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrIcdCode> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrIcdCode> findByIcCodeContains(String key);

    List<MemrIcdCode> findByIcDescriptionContains(String key);

    Page<MemrIcdCode> findByIcCodeContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    @Query(value = "SELECT count(mc.ic_code) FROM memr_icd_code mc WHERE mc.ic_code=:ic_code and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByClusterName(@Param("ic_code") String ic_code);

}
            
