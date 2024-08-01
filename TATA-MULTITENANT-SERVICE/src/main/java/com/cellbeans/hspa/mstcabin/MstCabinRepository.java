package com.cellbeans.hspa.mstcabin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstCabinRepository extends JpaRepository<MstCabin, Long> {

    Page<MstCabin> findByCabinNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstCabin> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstCabin> findByCabinNameContains(String key);

    @Query(value = "SELECT count(mc.cabin_name) FROM mst_cabin mc WHERE mc.cabin_name=:cabin_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByCabinName(@Param("cabin_name") String cabin_name);

}
            
