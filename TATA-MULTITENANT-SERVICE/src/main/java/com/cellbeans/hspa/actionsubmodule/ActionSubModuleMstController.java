package com.cellbeans.hspa.actionsubmodule;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/action_submodule_mst")
public class ActionSubModuleMstController {

    @Autowired
    ActionSubModuleMstRepository actionSubModuleMstRepository;

    Map<String, String> respMap = new HashMap<String, String>();

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody ActionSubModuleMst actionSubModuleMst) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionSubModuleMst != null) {
            if (actionSubModuleMstRepository.findByAllOrderByAmName(actionSubModuleMst.getAsmName()) == 0) {
                actionSubModuleMst.setCreatedDate(new Date());
                actionSubModuleMst.setModifiedDate(new Date());
                actionSubModuleMstRepository.save(actionSubModuleMst);
                respMap.put("success", "1");
                respMap.put("msg", "Record added Successfully.");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added.");
            }
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{actionId}")
    public ActionSubModuleMst read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("actionId") Long actionId) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionId != null) {
            ActionSubModuleMst actionSubModuleMst = actionSubModuleMstRepository.getById(actionId);
            return actionSubModuleMst;
        } else {
            return null;
        }
    }

    @PutMapping("delete/{asmId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("asmId") Long asmId) {
        TenantContext.setCurrentTenant(tenantName);
        if (asmId != null) {
            ActionSubModuleMst actionSubModuleMst = actionSubModuleMstRepository.getById(asmId);
            actionSubModuleMst.setIsDeleted(true);
            actionSubModuleMstRepository.save(actionSubModuleMst);
            respMap.put("success", "1");
            respMap.put("msg", "Record deleted Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to deleted record");
            return respMap;
        }
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody ActionSubModuleMst actionSubModuleMst) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionSubModuleMst != null) {
            actionSubModuleMst.setModifiedDate(new Date());
            actionSubModuleMstRepository.save(actionSubModuleMst);
            respMap.put("success", "1");
            respMap.put("msg", "Record updated Successfully.");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to updated Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<ActionSubModuleMst> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "asmId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return actionSubModuleMstRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return actionSubModuleMstRepository.findAllByAsmNameContainsAndIsActiveTrueAndIsDeletedFalseOrAsmActionModuleIdAmNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @RequestMapping("/actionsubmodulelist")
    public List<ActionSubModuleMst> actionsubmodulelist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String asmList) {
        TenantContext.setCurrentTenant(tenantName);
        StringTokenizer st = new StringTokenizer(asmList, "[,]");
        ArrayList<ActionSubModuleMst> subModuleList = new ArrayList<ActionSubModuleMst>();
        while (st.hasMoreTokens()) {
            Long amId = Long.parseLong(st.nextToken());
            subModuleList.addAll(actionSubModuleMstRepository.findAllByAsmActionModuleIdAmIdAndIsActiveTrueAndIsDeletedFalse(amId));
        }
        return subModuleList;
    }

}
