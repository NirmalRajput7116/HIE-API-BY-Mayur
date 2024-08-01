package com.cellbeans.hspa.actionuserrole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionUserRoleMstRepository extends JpaRepository<ActionUserRoleMst, Long> {

    Page<ActionUserRoleMst> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<ActionUserRoleMst> findAllByAurStaffNameContainsAndIsActiveTrueAndIsDeletedFalseOrAurRoleNameContainsAndIsActiveTrueAndIsDeletedFalseOrAurActionModuleNameContainsAndIsActiveTrueAndIsDeletedFalseOrAurActionSubModuleNameContainsAndIsActiveTrueAndIsDeletedFalse(
            String staffName, String roleName, String amName, String asmName, Pageable page);

    List<ActionUserRoleMst> findAllByAurStaffIdAndIsActiveTrue(String staffId);

    List<ActionUserRoleMst> findAllByAurStaffIdAndIsDeletedFalse(String staffId);

    //	@Query(value = "select au from ActionUserRoleMst au where au.aurStaffId=:staffId and au.isActive=true and au.isDeleted=false group by au.aurActionModuleId")
    List<ActionUserRoleMst> findAllByAurStaffIdAndIsActiveTrueAndIsDeletedFalse(String staffId);

//    List<UserActionsForLogin> findAllByAurStaffIdAndIsActiveTrueAndIsDeletedFalse(String staffId);

    List<ActionUserRoleMst> findAllByAurStaffIdEqualsAndAurRoleIdEqualsAndIsActiveTrueAndIsDeletedFalse(String staffId,
                                                                                                        String roleId);

    List<ActionUserRoleMst> findAllByAurStaffIdEqualsAndAurRoleId(String staffId, String roleId);

    @Query(value = "delete from ActionUserRoleMst aur where aur.aurStaffId=:staffId and aur.aurRoleId=:roleId", nativeQuery = false)
    int deleteRolePrevilegeForUser(@Param("staffId") long staffId, @Param("roleId") long roleId);

}
