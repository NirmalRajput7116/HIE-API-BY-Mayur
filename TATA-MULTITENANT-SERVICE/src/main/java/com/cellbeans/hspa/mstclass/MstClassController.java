package com.cellbeans.hspa.mstclass;

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
@RequestMapping("/mst_class")
public class MstClassController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstClassRepository mstClassRepository;

    @Autowired
    private MstClassService mstClassService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstClass mstClass) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstClass.getClassName() != null) {
            if (mstClassRepository.findByAllOrderByClassName(mstClass.getClassName()) == 0) {
                mstClassRepository.save(mstClass);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
            }
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstClass> records;
        records = mstClassRepository.findByClassNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{classId}")
    public MstClass read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("classId") Long classId) {
        TenantContext.setCurrentTenant(tenantName);
        MstClass mstClass = mstClassRepository.getById(classId);
        return mstClass;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstClass mstClass) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstClassRepository.findByAllOrderByClassName(mstClass.getClassName()) == 0) {
            mstClassRepository.save(mstClass);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Already Added");
        }
        return respMap;
//        return mstClassRepository.save(mstClass);
    }

    // Author: Mohit
    @RequestMapping("byname/{className}")
    public MstClass read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("className") String className) {
        TenantContext.setCurrentTenant(tenantName);
        MstClass mstClass = mstClassRepository.findByClassNameContainsAndIsActiveTrueAndIsDeletedFalse(className);
        return mstClass;
    }

    @GetMapping("readisopd")
    public MstClass readIsOpd(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstClassRepository.findByIsOpdTrueAndIsActiveTrueAndIsDeletedFalse();
    }

    @GetMapping("readisemg")
    public MstClass readIsEmg(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstClassRepository.findByIsEmgTrueAndIsActiveTrueAndIsDeletedFalse();
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstClass> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "classId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstClassRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstClassRepository.findByClassNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{classId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("classId") Long classId) {
        TenantContext.setCurrentTenant(tenantName);
        MstClass mstClass = mstClassRepository.getById(classId);
        if (mstClass != null) {
            mstClass.setIsDeleted(true);
            mstClass.setIsActive(false);
            mstClassRepository.save(mstClass);
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
        List<Tuple> items = mstClassService.getMstClassForDropdown(page, size, globalFilter);
        return items;
    }

}
            
