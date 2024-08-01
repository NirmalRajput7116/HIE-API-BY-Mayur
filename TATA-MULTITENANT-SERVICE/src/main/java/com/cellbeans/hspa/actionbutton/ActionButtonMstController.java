package com.cellbeans.hspa.actionbutton;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.actionsubmodule.ActionSubModuleMst;
import com.cellbeans.hspa.actionsubmodule.ActionSubModuleMstRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/action_button_mst")
public class ActionButtonMstController {

    @Autowired
    ActionButtonMstRepository actionButtonMstRepository;

    @Autowired
    ActionSubModuleMstRepository actionSubModuleMstRepository;

    Map<String, String> respMap = new HashMap<String, String>();

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody ActionButtonMst actionButtonMst) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionButtonMst != null) {
            if (actionButtonMstRepository.findByAllOrderByButtonName(actionButtonMst.getAbName()) == 0) {
                actionButtonMst.setCreatedDate(new Date());
                actionButtonMst.setModifiedDate(new Date());
                actionButtonMstRepository.save(actionButtonMst);
                respMap.put("success", "1");
                respMap.put("msg", "Record added Successfully");

            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Addedd");
            }
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{actionId}")
    public ActionButtonMst read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("actionId") Long actionId) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionId != null) {
            ActionButtonMst actionButtonMst = actionButtonMstRepository.getById(actionId);
            return actionButtonMst;
        } else {
            return null;
        }
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody ActionButtonMst actionButtonMst) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("actionButtonMst :" + actionButtonMst);
        if (actionButtonMst != null) {
            if (actionButtonMstRepository.findByAllOrderByButtonName(actionButtonMst.getAbName()) == 0) {
                actionButtonMst.setModifiedDate(new Date());
                actionButtonMstRepository.save(actionButtonMst);
                respMap.put("success", "1");
                respMap.put("msg", "Record updated Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
            }
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to updated Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<ActionButtonMst> list(@RequestHeader("X-tenantId") String tenantName,
                                          @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                          @RequestParam(value = "size", required = false, defaultValue = "10") String size,
                                          @RequestParam(value = "qString", required = false) String qString,
                                          @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                          @RequestParam(value = "col", required = false, defaultValue = "abId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return actionButtonMstRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByAbName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return actionButtonMstRepository.findAllByAbNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByAbName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

/*
    @RequestMapping("/actionsubmodulelist")
    public List<ActionButtonMst> actionsubmodulelist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String asmList) {
    TenantContext.setCurrentTenant(tenantName);
        System.out.println("actionsubmodulelist :" + asmList);

        StringTokenizer st = new StringTokenizer(asmList, "[,]");
        ArrayList<ActionButtonMst> subModuleList = new ArrayList<ActionButtonMst>();
        while (st.hasMoreTokens()) {
            Long amId = Long.parseLong(st.nextToken());
            subModuleList.addAll(actionButtonMstRepository.findAllByAsmActionModuleIdAmIdAndIsActiveTrueAndIsDeletedFalse(amId));
        }
        return subModuleList;
    }
*/

    @RequestMapping("buttonlistbyasmid/{roleid}/{rolename}")
    public List<ActionButtonMst> updatePreUser(@RequestHeader("X-tenantId") String tenantName,
                                               @RequestBody String submoduleList, @PathVariable("roleid") String roleId,
                                               @PathVariable("rolename") String roleName) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("submoduleList :" + submoduleList + " roleId" + roleId + " roleName :" + roleName);
        StringTokenizer st = new StringTokenizer(submoduleList, "[,]");
        List<ActionButtonMst> buttonList = new ArrayList<ActionButtonMst>();
        while (st.hasMoreTokens()) {
            Long asmId = Long.parseLong(st.nextToken());
            ActionSubModuleMst asmObj = actionSubModuleMstRepository.findAllByAsmIdAndIsActiveTrueAndIsDeletedFalse(asmId);
            buttonList.addAll(asmObj.getActionButton());

        }
        // to avoid duplicate entries
        //  Set<ActionButtonMst> hs = new HashSet<ActionButtonMst>();
        //    hs.addAll(buttonList);
        //   buttonList.clear();
        //    buttonList.addAll(hs);
        return buttonList;
    }

    @PutMapping("delete/{actionId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("actionId") Long actionId) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionId != null) {
            ActionButtonMst actionButtonMst = actionButtonMstRepository.getById(actionId);
            actionButtonMst.setIsDeleted(true);
            actionButtonMstRepository.save(actionButtonMst);
            respMap.put("success", "1");
            respMap.put("msg", "Record deleted Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to deleted record");
            return respMap;
        }
    }

    @RequestMapping("/actionbuttonlistbysubmoduleid")
    public List<ActionButtonMst> actionButtonListbyDubmoduleId(@RequestHeader("X-tenantId") String tenantName, @RequestBody String buttonList) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("action buttonlist  :" + buttonList);
        StringTokenizer st = new StringTokenizer(buttonList, "[,]");
        ArrayList<ActionButtonMst> subModuleList = new ArrayList<ActionButtonMst>();
        while (st.hasMoreTokens()) {
            Long abId = Long.parseLong(st.nextToken());
            subModuleList.addAll(actionButtonMstRepository.findAllByAbIdAndIsActiveTrueAndIsDeletedFalse(abId));
        }
        return subModuleList;
    }

}
