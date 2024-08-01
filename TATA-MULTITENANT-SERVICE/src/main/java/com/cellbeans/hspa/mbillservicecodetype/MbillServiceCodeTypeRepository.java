package com.cellbeans.hspa.mbillservicecodetype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MbillServiceCodeTypeRepository extends JpaRepository<MbillServiceCodeType, Long> {

    Page<MbillServiceCodeType> findBySctNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillServiceCodeType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillServiceCodeType> findBySctNameContains(String key);

    @Query(value = "SELECT count(mc.sct_name) FROM mbill_service_code_type mc WHERE mc.sct_name=:sct_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByServiceCodeType(@Param("sct_name") String sct_name);

}
            
