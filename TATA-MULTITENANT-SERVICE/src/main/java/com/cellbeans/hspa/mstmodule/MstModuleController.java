package com.cellbeans.hspa.mstmodule;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_module")
public class MstModuleController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstModuleRepository mstModuleRepository;
    @Autowired
    private MstModuleService mstModuleService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstModule mstModule) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstModule.getModuleName() != null) {
            mstModuleRepository.save(mstModule);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{moduleId}")
    public MstModule read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("moduleId") Long moduleId) {
        TenantContext.setCurrentTenant(tenantName);
        MstModule mstModule = mstModuleRepository.getById(moduleId);
        return mstModule;
    }

    @RequestMapping("update")
    public MstModule update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstModule mstModule) {
        TenantContext.setCurrentTenant(tenantName);
        return mstModuleRepository.save(mstModule);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstModule> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "moduleId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return mstModuleRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        if (qString == null || qString.equals("")) {
//            return mstModuleRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        } else {
//
//            return mstModuleRepository.findByClassNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        }
    }

    @PutMapping("delete/{moduleId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("moduleId") Long moduleId) {
        TenantContext.setCurrentTenant(tenantName);
        MstModule mstModule = mstModuleRepository.getById(moduleId);
        if (mstModule != null) {
            mstModule.setDeleted(true);
            mstModule.setActive(false);
            mstModuleRepository.save(mstModule);
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
        List<Tuple> items = mstModuleService.getMstModuleForDropdown(page, size, globalFilter);
        return items;
    }

}

