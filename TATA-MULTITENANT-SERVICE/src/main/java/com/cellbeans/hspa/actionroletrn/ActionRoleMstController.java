package com.cellbeans.hspa.actionroletrn;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.actionbutton.ActionButtonMst;
import com.cellbeans.hspa.actionmodule.ActionModuleMst;
import com.cellbeans.hspa.actionmodule.ActionModuleMstRepository;
import com.cellbeans.hspa.actionsubmodule.ActionSubModuleMstRepository;
import com.cellbeans.hspa.mstrole.MstRole;
import com.cellbeans.hspa.mstrole.MstRoleRepository;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/action_role_mst")
public class ActionRoleMstController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ActionRoleMstRepository actionRoleMstRepository;

    @Autowired
    MstRoleRepository mstRoleRepository;

    @Autowired
    ActionModuleMstRepository actionModuleMstRepository;

    @Autowired
    ActionSubModuleMstRepository actionSubModuleMstRepository;

    Map<String, String> respMap = new HashMap<String, String>();

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody ActionRoleMst actionRoleMst) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionRoleMst != null) {
            actionRoleMstRepository.save(actionRoleMst);
            respMap.put("success", "1");
            respMap.put("msg", "Record added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to Add Null Field ");
            return respMap;
        }
    }

    @RequestMapping("byid/{arId}")
    public ActionRoleMst read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("arId") Long arId) {
        TenantContext.setCurrentTenant(tenantName);
        if (arId != null) {
            ActionRoleMst actionRoleMst = actionRoleMstRepository.getById(arId);
            return actionRoleMst;
        } else {
            return null;
        }
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody ActionRoleMst actionRoleMst) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionRoleMst != null) {
            actionRoleMstRepository.save(actionRoleMst);
            respMap.put("success", "1");
            respMap.put("msg", "Record updated Successfully ");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to updated Null Field");
            return respMap;
        }
    }

    @RequestMapping("list/{page}/{size}/{sort}/{col}")
    // @Produces(MediaType.APPLICATION_JSON)
    public Iterable<ActionRoleMst> list(@RequestHeader("X-tenantId") String tenantName,
                                        @RequestBody String jsonStr, @PathVariable("page") String page,
                                        @PathVariable("size") String size, @PathVariable("sort") String sort,
                                        @PathVariable("col") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject json = new JSONObject(jsonStr);
        System.out.println("hello 11:" + json);
        System.out.println("page :" + page);
        System.out.println("size :" + size);
        System.out.println("col :" + col);
        JSONParser parser = new JSONParser();
        String moduleId = String.valueOf(json.get("moduleId"));
        String subModuleId = String.valueOf(json.get("subModuleId"));
        String roleId = String.valueOf(json.get("userRoleId"));
        String moduleList = moduleId.replace("[", "(");
        moduleList = moduleList.replace("]", ")");
        String subModuleList = subModuleId.replace("[", "(");
        subModuleList = subModuleList.replace("]", ")");
        System.out.println("moduleList :" + moduleList);
        System.out.println("moduleId :" + subModuleId);
        System.out.println("roleId :" + roleId);
        System.out.println("json.length() :" + json.length());
        if (!roleId.isEmpty() || !subModuleId.isEmpty() || !moduleId.isEmpty()) {
            String queryStr = "select am.am_name,asm.asm_name,r.* from action_role_mst r "
                    + "inner join action_sub_module_mst asm on asm.asm_id=r.ar_action_sub_module_mst "
                    + "inner join action_module_mst am on am.am_id=asm.asm_action_module_id "
                    + "where 1 and "
                    + "r.is_active=true and r.is_deleted=false "
                    + "and asm.is_active=true and asm.is_deleted=false ";
            if (roleId != "" && !roleId.isEmpty()) {
                queryStr = queryStr + " and r.ar_role_id=" + roleId;
            }
            if (moduleId != "" && !moduleId.isEmpty()) {
                queryStr = queryStr + " and asm.asm_action_module_id IN " + moduleList;
            }
            if (subModuleId != "" && !subModuleId.isEmpty()) {
                queryStr = queryStr + " and asm.asm_id IN " + subModuleList;
            }
            int p = Integer.parseInt(page) - 1;
            int offset = Integer.parseInt(size) * p;
            queryStr = queryStr + " order by ar_id desc LIMIT " + size + " OFFSET " + offset;
            System.out.println("queryStr :" + queryStr);
            List<ActionRoleMst> roleList = entityManager.createNativeQuery(queryStr, ActionRoleMst.class).getResultList();
            // count Query
            String queryStr1 = "select count(*) from action_role_mst r "
                    + "inner join action_sub_module_mst asm on asm.asm_id=r.ar_action_sub_module_mst "
                    + "inner join action_module_mst am on am.am_id=asm.asm_action_module_id "
                    + "where 1 and "
                    + "r.is_active=true and r.is_deleted=false "
                    + "and asm.is_active=true and asm.is_deleted=false ";
            if (roleId != "" && !roleId.isEmpty()) {
                queryStr1 = queryStr1 + " and r.ar_role_id=" + roleId;
            }
            if (moduleId != "" && !moduleId.isEmpty()) {
                queryStr1 = queryStr1 + " and asm.asm_action_module_id IN " + moduleList;
            }
            if (subModuleId != "" && !subModuleId.isEmpty()) {
                queryStr1 = queryStr1 + " and asm.asm_id IN " + subModuleList;
            }
            Object count = entityManager.createNativeQuery(queryStr1).getSingleResult();
            System.out.println("count :" + String.valueOf(count));
            if (roleList.size() > 0) {
                roleList.get(0).setCount(String.valueOf(count));
            }
            return roleList;
        } else {
            //    return actionRoleMstRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            String query = "select * from action_role_mst where is_deleted=false and is_active=true";
            int p = Integer.parseInt(page) - 1;
            int offset = Integer.parseInt(size) * p;
            query = query + " order by ar_id desc LIMIT " + size + " OFFSET " + offset;
            List<ActionRoleMst> armList = entityManager.createNativeQuery(query, ActionRoleMst.class).getResultList();
            Object count = entityManager.createNativeQuery("select count(*) from action_role_mst where is_deleted=false and is_active=true").getSingleResult();
            System.out.println("query all :" + query);
            System.out.println("count all :" + count);
            armList.get(0).setCount(String.valueOf(count));
            return armList;
        }

    }

    @PutMapping("deleteroleprevilege/{roleId}")
    public Map<String, String> deleteRolePrevilege(@RequestHeader("X-tenantId") String tenantName, @PathVariable("roleId") Long roleId) {
        TenantContext.setCurrentTenant(tenantName);
        boolean flag = false;
        if (roleId != null) {
            List<ActionRoleMst> actionRoleList = actionRoleMstRepository.findAllByArRoleIdRoleId(roleId);
            actionRoleMstRepository.deleteAll(actionRoleList);
//            for (int i = 0; i < actionRoleList.size(); i++) {
//                actionRoleMstRepository.deleteById(actionRoleList.get(i).getArId());
//                flag = true;
//            }
            if (flag) {
                respMap.put("success", "1");
                respMap.put("msg", "Record deleted Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed to deleted record");
                return respMap;
            }

        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to deleted record");
            return respMap;
        }
    }

    @PutMapping("delete/{arid}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("arid") Long arid) {
        TenantContext.setCurrentTenant(tenantName);
        if (arid != null) {
            ActionRoleMst actionRoleMst = actionRoleMstRepository.getById(arid);
            actionRoleMst.setIsDeleted(true);
            actionRoleMstRepository.save(actionRoleMst);
            respMap.put("success", "1");
            respMap.put("msg", "Record deleted Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to deleted record");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("actionlistbyactionname")
    public List<ActionModuleMst> actionListByName(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "actionname", required = false) String actionname) {
        TenantContext.setCurrentTenant(tenantName);
        //System.out.println("actionname :" + actionname);
        return actionModuleMstRepository.findByAmNameContainsAndIsActiveTrueAndIsDeletedFalse(actionname);

    }

    @GetMapping
    @RequestMapping("rolelistbyactionname")
    public List<MstRole> roleListByName(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "rolename", required = false) String rolename) {
        TenantContext.setCurrentTenant(tenantName);
        //System.out.println("actionname :" + rolename);
        return mstRoleRepository.findByRoleNameContainsAndIsActiveTrueAndIsDeletedFalse(rolename);

    }

    @RequestMapping("createbylist")
    public Map<String, String> createFromListlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<ActionRoleMst> actionRoleList) {
        TenantContext.setCurrentTenant(tenantName);
        boolean recordExistFlag = false;
        if (actionRoleList.size() > 0) {
            for (ActionRoleMst arm : actionRoleList) {
                //System.out.println("arm.getArRoleId().getRoleId(), arm.getArActionSubModuleMst().getAsmId() :" + arm.getArRoleId().getRoleId() + "  " + arm.getArActionSubModuleMst().getAsmId());
                List<ActionRoleMst> tempActionRole = actionRoleMstRepository
                        .findAllByArRoleIdRoleIdAndArActionSubModuleMstAsmIdAndIsActiveTrueAndIsDeletedFalse(
                                arm.getArRoleId().getRoleId(), arm.getArActionSubModuleMst().getAsmId());
                if (tempActionRole.size() <= 0) {
                    actionRoleMstRepository.save(arm);
                    respMap.put("success", "1");
                    respMap.put("msg", "Record added Successfully");
                    respMap.put("recordexist", String.valueOf(recordExistFlag));
                } else {
                    for (ActionRoleMst ar : tempActionRole) {
                        List<ActionButtonMst> actionButList = new ArrayList<ActionButtonMst>();
                        for (ActionButtonMst am : arm.getActionButton()) {
                            actionButList.add(am);
                        }
                        ar.setActionButton(actionButList);
                        actionRoleMstRepository.save(ar);
                    }
                    recordExistFlag = true;
                    respMap.put("success", "1");
                    respMap.put("msg", "Record Updated Successfully");
                    respMap.put("recordexist", String.valueOf(recordExistFlag));
                }
            }
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to Add Null Field");
            respMap.put("recordexist", String.valueOf(recordExistFlag));
            return respMap;
        }

    }

}
