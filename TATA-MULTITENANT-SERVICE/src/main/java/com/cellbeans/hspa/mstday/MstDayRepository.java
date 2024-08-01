package com.cellbeans.hspa.mstday;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstDayRepository extends JpaRepository<MstDay, Long> {

    Page<MstDay> findByDayNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDay> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDay> findByDayNameContains(String key);

    MstDay findByDayNameContainsAndIsActiveTrueAndIsDeletedFalse(String dayname);

    MstDay getOneByIsActiveTrueAndIsDeletedFalseAndStartDayTrue();

    MstDay getOneByIsActiveTrueAndIsDeletedFalseAndEndDayTrue();

    @Query(value = "SELECT count(mc.day_name) FROM mst_day mc WHERE mc.day_name=:day_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByDayName(@Param("day_name") String day_name);

}
            
