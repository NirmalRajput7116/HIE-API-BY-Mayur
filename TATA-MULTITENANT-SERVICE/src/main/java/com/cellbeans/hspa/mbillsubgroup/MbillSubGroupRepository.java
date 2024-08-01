package com.cellbeans.hspa.mbillsubgroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MbillSubGroupRepository extends JpaRepository<MbillSubGroup, Long> {

    Page<MbillSubGroup> findBySgNameContainsOrSgGroupIdGroupNameEqualsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    Page<MbillSubGroup> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillSubGroup> findAllByIsActiveTrueAndIsDeletedFalse();

    List<MbillSubGroup> findAllBySgGroupIdGroupIdAndIsActiveTrueAndIsDeletedFalse(Long groupId);

    List<MbillSubGroup> findBySgNameContains(String key);

    Page<MbillSubGroup> findBySgNameContainsAndIsActiveTrueAndIsDeletedFalseOrSgGroupIdGroupNameEqualsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    @Query(value = "SELECT count(mc.sg_name) FROM mbill_sub_group mc WHERE mc.sg_name=:sgName and mc.sg_group_id=:groupId and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderBySubGroupName(@Param("sgName") String sgName, @Param("groupId") Long groupId);

}
            
