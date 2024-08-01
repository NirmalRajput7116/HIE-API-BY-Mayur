package com.cellbeans.hspa.actionuserrole;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.actionbutton.ActionButtonMst;
import com.cellbeans.hspa.actionroletrn.ActionRoleMst;
import com.cellbeans.hspa.actionroletrn.ActionRoleMstRepository;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@RestController
@RequestMapping("/action_user_role_mst")
public class ActionUserRoleMstController {

    @Autowired
    ActionUserRoleMstRepository actionUserRoleMstRepository;

    @Autowired
    MstStaffRepository mstStaffRepository;

    @Autowired
    ActionRoleMstRepository actionRoleMstRepository;
    Map<String, String> respMap = new HashMap<String, String>();
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody ActionUserRoleMst actionUserRoleMst) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionUserRoleMst != null) {
            actionUserRoleMstRepository.save(actionUserRoleMst);
            respMap.put("success", "1");
            respMap.put("msg", "Record added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{arId}")
    public ActionUserRoleMst read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("arId") Long aurId) {
        TenantContext.setCurrentTenant(tenantName);
        if (aurId != null) {
            ActionUserRoleMst actionUserRoleMst = actionUserRoleMstRepository.getById(aurId);
            return actionUserRoleMst;
        } else {
            return null;
        }
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody ActionUserRoleMst actionUserRoleMst) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionUserRoleMst != null) {
            actionUserRoleMstRepository.save(actionUserRoleMst);
            respMap.put("success", "1");
            respMap.put("msg", "Record updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to updated Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<ActionUserRoleMst> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "aurId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return actionUserRoleMstRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            //System.out.println("qString :" + qString);
            return actionUserRoleMstRepository.findAllByAurStaffNameContainsAndIsActiveTrueAndIsDeletedFalseOrAurRoleNameContainsAndIsActiveTrueAndIsDeletedFalseOrAurActionModuleNameContainsAndIsActiveTrueAndIsDeletedFalseOrAurActionSubModuleNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{aurid}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("aurid") Long aurId) {
        TenantContext.setCurrentTenant(tenantName);
        if (aurId != null) {
            ActionUserRoleMst actionUserRoleMst = actionUserRoleMstRepository.getById(aurId);
            actionUserRoleMst.setIsDeleted(true);
            actionUserRoleMstRepository.save(actionUserRoleMst);
            respMap.put("success", "1");
            respMap.put("msg", "Record deleted Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to deleted record");
            return respMap;
        }
    }

    /*  @RequestMapping("updateprestaff/{roleid}/{rolename}")
      public Map<String, String> updatePreStaff(@RequestHeader("X-tenantId") String tenantName, @RequestBody String stafflist, @PathVariable("roleid") String roleId, @PathVariable("rolename") String roleName) {
      TenantContext.setCurrentTenant(tenantName);
          try {
              //      System.out.println("staffId :" + stafflist + " roleId :" + roleId + " roleName :" + roleName);
              StringTokenizer st = new StringTokenizer(stafflist, "[,]");
              ArrayList<MstStaff> staffList = new ArrayList<MstStaff>();
              while (st.hasMoreTokens()) {
                  String staffId = st.nextToken();
                  ActionRoleMst ar = null;
                  ActionUserRoleMst aur = null;
                  if (roleId != null && staffId != null) {

                      String query2 = "select * from action_user_role_mst where aur_staff_id=" + staffId + " and aur_role_id=" + roleId + " and is_deleted=false and is_active=true";//+" group by aur_staff_id";
                      List<ActionUserRoleMst> obj = entityManager.createNativeQuery(query2, ActionUserRoleMst.class).getResultList();
                      //    System.out.println("obj111 :" + obj.size());

                      if (obj.size() > 0) {
                          //   System.out.println("Existing Staff with role action - > staff id:" + staffId + "  role id :" + roleId);

                          String query = "SELECT * FROM action_role_mst WHERE ar_action_sub_module_mst " +
                                  "not IN (SELECT aur_action_sub_module_id  FROM  action_user_role_mst where aur_role_id=" + roleId + " and aur_staff_id=" + staffId + " and is_deleted=false and is_active=true) " +
                                  "and ar_role_id=" + roleId;
                          List<ActionRoleMst> pendingRoleList = entityManager.createNativeQuery(query, ActionRoleMst.class).getResultList();
                          //      System.out.println("hieello :" + pendingRoleList);
                          for (int i = 0; i < pendingRoleList.size(); i++) {
                              ar = pendingRoleList.get(i);
                              //     System.out.println("vijay :" + ar);
                              //    System.out.println("M2M :" + ar.getActionButton());
                              aur = new ActionUserRoleMst();
                              aur.setAurRoleId(roleId);
                              aur.setAurRoleName(roleName);
                              aur.setCreatedBy(staffId);
                              aur.setModifiedBy(staffId);
                              aur.setCreatedByName(obj.get(0).getCreatedByName());
                              aur.setModifiedByName(obj.get(0).getModifiedByName());
                              aur.setAurStaffId(staffId);

                              MstStaff staff = new MstStaff();
                              staff = mstStaffRepository.getById(Long.parseLong(staffId));
                              aur.setAurStaffName(staff.getStaffUserId().getUserFirstname() + ' ' + staff.getStaffUserId().getUserLastname());
                              aur.setIsDeleted(false);
                              aur.setIsActive(true);
                              aur.setCreatedDate(new Date());
                              aur.setModifiedDate(new Date());
                              aur.setAurSearch(true);
                              aur.setAurApprove(true);
                              aur.setAurDelete(true);
                              aur.setAurView(true);
                              aur.setAurEdit(true);
                              aur.setAurAdd(true);
                              aur.setAurActionModuleId(String.valueOf(ar.getArActionSubModuleMst().getAsmActionModuleId().getAmId()));
                              aur.setAurActionModuleName(String.valueOf(ar.getArActionSubModuleMst().getAsmActionModuleId().getAmName()));
                              aur.setAurActionSubModuleId(String.valueOf(ar.getArActionSubModuleMst().getAsmId()));
                              aur.setAurActionSubModuleName(String.valueOf(ar.getArActionSubModuleMst().getAsmName()));
                              if (ar.getActionButton().size() > 0) {
                                  List<ActionButtonMst> actionButList = new ArrayList<ActionButtonMst>();
                                  for (ActionButtonMst am : ar.getActionButton()) {
                                      actionButList.add(am);
                                  }
                                  aur.setActionButton(actionButList);
                              }

                              actionUserRoleMstRepository.save(aur);
                          }


                      } else {
                          //      System.out.println("New Staff without role action- > staff id:" + staffId + "  role id :" + roleId);
                          ActionUserRoleMst actionUserRoleMst = null;
                          List<ActionButtonMst> actionButList = new ArrayList<ActionButtonMst>();
                          List<ActionRoleMst> listofrole = actionRoleMstRepository.findAllByArRoleIdRoleIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(roleId));
                          for (ActionRoleMst amObj : listofrole) {
                              for (ActionButtonMst am : amObj.getActionButton()) {
                                  actionButList.add(am);
                              }
                              MstStaff staff = new MstStaff();
                              staff = mstStaffRepository.getById(Long.parseLong(staffId));
                              String staffName = staff.getStaffUserId().getUserFirstname() + ' ' + staff.getStaffUserId().getUserLastname();
                              actionUserRoleMst = new ActionUserRoleMst(String.valueOf(staffId), staffName, String.valueOf(amObj.getArActionSubModuleMst().getAsmActionModuleId().getAmId()), amObj.getArActionSubModuleMst().getAsmActionModuleId().getAmName(), String.valueOf(amObj.getArRoleId().getRoleId()), amObj.getArRoleId().getRoleName(), String.valueOf(amObj.getArActionSubModuleMst().getAsmId()), amObj.getArActionSubModuleMst().getAsmName(), amObj.getArAdd(), amObj.getArView(), amObj.getArEdit(), amObj.getArDelete(), amObj.getArSearch(), amObj.getArApprove(), amObj.getIsDeleted(), amObj.getIsActive(), amObj.getCreatedBy(), amObj.getCreatedByName(), amObj.getCreatedDate(), amObj.getModifiedBy(), amObj.getModifiedByName(), ar.getModifiedDate(), actionButList);
                              actionUserRoleMstRepository.save(actionUserRoleMst);
                              actionButList = new ArrayList<ActionButtonMst>();
                          }
                      }
                  }
              }
              respMap.put("success", "1");
              respMap.put("msg", "Record Updated Successfully");
              return respMap;
          } catch (Exception e) {
              System.err.println("error :" + e);

          }
          respMap.put("success", "0");
          respMap.put("msg", "Record Failed to Update");
          return respMap;
      }
  */
    @RequestMapping("updateprestaff/{roleid}/{rolename}")
    public Map<String, String> updatePreStaff(@RequestHeader("X-tenantId") String tenantName, @RequestBody String stafflist, @PathVariable("roleid") String roleId, @PathVariable("rolename") String roleName) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            System.out.println("staffId :" + stafflist + " roleId :" + roleId + " roleName :" + roleName);
            StringTokenizer st = new StringTokenizer(stafflist, "[,]");
            ArrayList<MstStaff> staffList = new ArrayList<MstStaff>();
            while (st.hasMoreTokens()) {
                String staffId = st.nextToken();
                if (roleId != null && staffId != null) {
                    String query = "SELECT * FROM action_role_mst WHERE ar_action_sub_module_mst and ar_role_id=" + roleId;
                    List<ActionRoleMst> updatedRolePermissionList = entityManager.createNativeQuery(query, ActionRoleMst.class).getResultList();
                    if (updatedRolePermissionList.size() > 0) {
                        for (ActionRoleMst ar : updatedRolePermissionList) {
                            String query1 = "SELECT * FROM  action_user_role_mst where aur_role_id=" + roleId + " and aur_staff_id=" + staffId + "  and aur_action_sub_module_id=" + ar.getArActionSubModuleMst().getAsmId() + "  and is_deleted=false and is_active=true";
                            List<ActionUserRoleMst> previousUserPermission = entityManager.createNativeQuery(query1, ActionUserRoleMst.class).getResultList();
                            //System.out.println("previousUserPermission :" + previousUserPermission);
                            if (previousUserPermission.size() > 0) {
                                // override previous
                                for (ActionUserRoleMst aur : previousUserPermission) {
                                    if (aur.getActionButton().size() != ar.getActionButton().size()) {
                                        aur.setModifiedBy(staffId);
                                        //    aur.setModifiedByName();
                                        aur.setAurActionModuleId(String.valueOf(ar.getArActionSubModuleMst().getAsmActionModuleId().getAmId()));
                                        aur.setAurActionModuleName(String.valueOf(ar.getArActionSubModuleMst().getAsmActionModuleId().getAmName()));
                                        aur.setAurActionSubModuleId(String.valueOf(ar.getArActionSubModuleMst().getAsmId()));
                                        aur.setAurActionSubModuleName(String.valueOf(ar.getArActionSubModuleMst().getAsmName()));
                                        if (ar.getActionButton().size() > 0) {
                                            List<ActionButtonMst> actionButList = new ArrayList<ActionButtonMst>();
                                            for (ActionButtonMst am : ar.getActionButton()) {
                                                actionButList.add(am);
                                            }
                                            aur.setActionButton(actionButList);
                                        }
                                        actionUserRoleMstRepository.save(aur);
                                    }
                                }
                            } else {
                                // add new
                                List<ActionButtonMst> actionButList = new ArrayList<ActionButtonMst>();
                                for (ActionButtonMst am : ar.getActionButton()) {
                                    actionButList.add(am);
                                }
                                MstStaff staff = new MstStaff();
                                staff = mstStaffRepository.getById(Long.parseLong(staffId));
                                String staffName = staff.getStaffUserId().getUserFirstname() + ' ' + staff.getStaffUserId().getUserLastname();
                                ActionUserRoleMst actionUserRoleMst = new ActionUserRoleMst(String.valueOf(staffId), staffName, String.valueOf(ar.getArActionSubModuleMst().getAsmActionModuleId().getAmId()), ar.getArActionSubModuleMst().getAsmActionModuleId().getAmName(), String.valueOf(ar.getArRoleId().getRoleId()), ar.getArRoleId().getRoleName(), String.valueOf(ar.getArActionSubModuleMst().getAsmId()), ar.getArActionSubModuleMst().getAsmName(), ar.getArAdd(), ar.getArView(), ar.getArEdit(), ar.getArDelete(), ar.getArSearch(), ar.getArApprove(), ar.getIsDeleted(), ar.getIsActive(), ar.getCreatedBy(), ar.getCreatedByName(), ar.getCreatedDate(), ar.getModifiedBy(), ar.getModifiedByName(), ar.getModifiedDate(), actionButList);
                                actionUserRoleMstRepository.save(actionUserRoleMst);
                                actionButList = new ArrayList<ActionButtonMst>();
                            }
                        }

                    }

                }
            }
            respMap.put("success", "1");
            respMap.put("msg", "Record Updated Successfully");
            return respMap;

        } catch (Exception e) {
            respMap.put("success", "1");
            respMap.put("msg", "Record Updated Successfully");
            return respMap;
        }
    }

    @RequestMapping("deleteallroleprivilegesforuser/{roleid}/{rolename}")
    public Map<String, String> deleteAllRolePrivilegesForUser(@RequestHeader("X-tenantId") String tenantName, @RequestBody String stafflist, @PathVariable("roleid") String roleId, @PathVariable("rolename") String roleName) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            int response = 0;
            //System.out.println("staffId :" + stafflist + " roleId :" + roleId + " roleName :" + roleName);
            StringTokenizer st = new StringTokenizer(stafflist, "[,]");
            while (st.hasMoreTokens()) {
                String staffId = st.nextToken();
                if (roleId != null && staffId != null) {
                    //System.out.println("roleId :" + roleId + "  staffId" + staffId);
                    List<ActionUserRoleMst> aurList = actionUserRoleMstRepository.findAllByAurStaffIdEqualsAndAurRoleId(staffId, roleId);
                    for (int i = 0; i < aurList.size(); i++) {
                        aurList.get(i);
                        actionUserRoleMstRepository.deleteById(aurList.get(i).getAurId());
                        response = 1;
                    }
                }
            }
            if (response == 1) {
                respMap.put("success", "1");
                respMap.put("msg", "Record Deleted Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Record Deletion Failed");
                return respMap;
            }
        } catch (Exception e) {
            System.err.println("error :" + e);
            return null;

        }
    }

}