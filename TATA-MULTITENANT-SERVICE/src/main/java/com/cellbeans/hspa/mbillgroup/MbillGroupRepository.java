package com.cellbeans.hspa.mbillgroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MbillGroupRepository extends JpaRepository<MbillGroup, Long> {

    Page<MbillGroup> findByGroupNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillGroup> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillGroup> findByGroupNameContains(String key);

    List<MbillGroup> findAllByIsActiveTrueAndIsDeletedFalse();

    @Query(value = "SELECT count(mc.group_name) FROM mbill_group mc WHERE mc.group_name=:groupName and mc.group_sd_id=:groupSdId and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByGroupName(@Param("groupName") String groupName, @Param("groupSdId") Long groupSdId);

    MbillGroup findByGroupNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
