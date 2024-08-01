package com.cellbeans.hspa.mstaction;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstmodule.MstModule;
import com.cellbeans.hspa.mstmodule.MstModuleRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_action")
public class MstActionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstActionRepository mstActionRepository;
    @Autowired
    MstModuleRepository mstModuleRepository;

    @Autowired
    private MstActionService mstActionService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAction mstAction) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstAction.getActionName() != null) {
            mstActionRepository.save(mstAction);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("actiontree")
    public List<Object> actiontree(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        List<Object> automap = new ArrayList<>();
        treestructor treest = null;
        treedata treechild = null;
        List<MstAction> actionlist;
        List<MstModule> modulelist = mstModuleRepository.findAll();
        for (MstModule singlemodule : modulelist) {
            List<treedata> treedatalist = new ArrayList<>();
            treest = new treestructor();
            treest.setLabel(singlemodule.getModuleName());
            treest.setData(singlemodule.getModuleName());
            treest.setCollapsedIcon("fa-folder");
            treest.setExpandedIcon("fa-folder-open");
            actionlist = mstActionRepository.findAllByActionModuleIdModuleIdEquals(singlemodule.getModuleId());
            for (MstAction singleaction : actionlist) {
                treechild = new treedata();
                treechild.setData(singleaction.getActionId());
                treechild.setLabel(singleaction.getActionName());
                treechild.setIcon("fa-file-word-o");
                treedatalist.add(treechild);
            }
            treest.setChildren(treedatalist);
            automap.add(treest);
        }
        return automap;
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstAction> records;
        records = mstActionRepository.findByActionNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{actionId}")
    public MstAction read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("actionId") Long actionId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAction mstAction = mstActionRepository.getById(actionId);
        return mstAction;
    }

    @RequestMapping("update")
    public MstAction update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAction mstAction) {
        TenantContext.setCurrentTenant(tenantName);
        return mstActionRepository.save(mstAction);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstAction> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "400") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "actionId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstActionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstActionRepository.findByActionNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{actionId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("actionId") Long actionId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAction mstAction = mstActionRepository.getById(actionId);
        if (mstAction != null) {
            mstAction.setIsDeleted(true);
            mstActionRepository.save(mstAction);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstActionService.getMstActionForDropdown(page, size, globalFilter);
        return items;
    }

}
            
