package com.cellbeans.hspa.mstcashcounter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstCashCounterRepository extends JpaRepository<MstCashCounter, Long> {

    Page<MstCashCounter> findByCcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstCashCounter> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstCashCounter> findByCcNameContains(String key);

    List<MstCashCounter> findByCcUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(long key);

    @Query(value = "SELECT count(mc.cc_name) FROM mst_cash_counter mc WHERE mc.cc_name=:cc_name and cc_unit_id=:unitId and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByCashCounter(@Param("cc_name") String cc_name, @Param("unitId") Long unitId);

}
            
