package com.cellbeans.hspa.actionmodule;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstorg.MstOrg;
import com.cellbeans.hspa.mstorg.MstOrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/action_module_mst")
public class ActionModuleMstController {

    @Autowired
    ActionModuleMstRepository actionModuleMstRepository;

    @Autowired
    MstOrgRepository mstOrgRepository;

    Map<String, String> respMap = new HashMap<String, String>();

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody ActionModuleMst actionModuleMst) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionModuleMst != null) {
            if (actionModuleMstRepository.findByAllOrderByAmName(actionModuleMst.getAmName()) == 0) {
                actionModuleMst.setCreatedDate(new Date());
                actionModuleMst.setModifiedDate(new Date());
                actionModuleMstRepository.save(actionModuleMst);
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
    public ActionModuleMst read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("actionId") Long actionId) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionId != null) {
            ActionModuleMst actionModuleMst = actionModuleMstRepository.getById(actionId);
            return actionModuleMst;
        } else {
            return null;
        }
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody ActionModuleMst actionModuleMst) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("actionModuleMst :" + actionModuleMst);
        if (actionModuleMst != null) {
            if (actionModuleMstRepository.findByAllOrderByAmName(actionModuleMst.getAmName()) == 0) {
                actionModuleMst.setModifiedDate(new Date());
                actionModuleMstRepository.save(actionModuleMst);
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
    public Iterable<ActionModuleMst> list(@RequestHeader("X-tenantId") String tenantName,
                                          @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                          @RequestParam(value = "size", required = false, defaultValue = "10") String size,
                                          @RequestParam(value = "qString", required = false) String qString,
                                          @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                          @RequestParam(value = "col", required = false, defaultValue = "amId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return actionModuleMstRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByAmName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return actionModuleMstRepository.findAllByAmNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByAmName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @GetMapping
    @RequestMapping("listByTenentId")
    public MstOrg listByTenentId(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstOrgRepository.findAllByOrgIdEquals(Long.parseLong(tenantName));

    }

    @PutMapping("delete/{actionId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("actionId") Long actionId) {
        TenantContext.setCurrentTenant(tenantName);
        if (actionId != null) {
            ActionModuleMst actionModuleMst = actionModuleMstRepository.getById(actionId);
            actionModuleMst.setIsDeleted(true);
            actionModuleMstRepository.save(actionModuleMst);
            respMap.put("success", "1");
            respMap.put("msg", "Record deleted Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to deleted record");
            return respMap;
        }
    }

}
