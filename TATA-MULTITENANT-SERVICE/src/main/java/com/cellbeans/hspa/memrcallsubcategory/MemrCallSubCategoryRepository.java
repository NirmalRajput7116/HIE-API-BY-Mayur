package com.cellbeans.hspa.memrcallsubcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemrCallSubCategoryRepository extends JpaRepository<MemrCallSubCategory, Long> {


    Page<MemrCallSubCategory> findByCcsNameContainsAndIsActiveTrueAndIsDeletedFalseOrCcsCcIdCallCategoryNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);


    Page<MemrCallSubCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrCallSubCategory> findByCcsNameContains(String key);

    @Query(value = "SELECT count(cc.ccs_name) FROM memr_call_sub_category cc WHERE cc.ccs_name=:ccs_name and ccs_cc_id=:ccs_cc_id and cc.is_deleted=false" , nativeQuery = true)
    int findByAllOrderByCallCategoryName(@Param("ccs_name") String ccs_name, @Param("ccs_cc_id") Long ccs_cc_id);

    List<MemrCallSubCategory> findByCcsCcIdCallCategoryId(Long id);

}


