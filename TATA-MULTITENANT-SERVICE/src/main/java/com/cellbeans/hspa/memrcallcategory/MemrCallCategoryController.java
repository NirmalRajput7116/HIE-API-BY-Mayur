package com.cellbeans.hspa.memrcallcategory;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memr_callcategory")
public class MemrCallCategoryController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrCallCategoryRepository memrCallCategoryRepository;

    @Autowired
    MemrCallCategoryService memrCallCategoryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrCallCategory memrCallCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrCallCategory.getCallCategoryName() != null) {
            memrCallCategory.setCallCategoryName(memrCallCategory.getCallCategoryName().trim());
            MemrCallCategory memrCallCategoryObject = memrCallCategoryRepository.findByCallCategoryNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrCallCategory.getCallCategoryName());
            if (memrCallCategoryObject == null) {
                memrCallCategoryRepository.save(memrCallCategory);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Already Added");
                return respMap;
            }
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
        List<MemrCallCategory> records;
        records = memrCallCategoryRepository.findByCallCategoryNameContainsAndIsActiveTrueAndIsDeletedFalse(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bloodgroupId}")
    public MemrCallCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrCallCategory memrCallCategory = memrCallCategoryRepository.getById(bloodgroupId);
        return memrCallCategory;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrCallCategory memrCallCategory) {
        TenantContext.setCurrentTenant(tenantName);
        memrCallCategory.setCallCategoryName(memrCallCategory.getCallCategoryName().trim());
        MemrCallCategory memrCallCategoryObject = memrCallCategoryRepository.findByCallCategoryNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrCallCategory.getCallCategoryName());
        if (memrCallCategoryObject == null) {
            memrCallCategoryRepository.save(memrCallCategory);
            respMap.put("success", "1");
            respMap.put("msg", "updated Successfully");
            return respMap;
        } else if (memrCallCategoryObject.getCallCategoryId() == memrCallCategory.getCallCategoryId()) {
            memrCallCategoryRepository.save(memrCallCategory);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;

        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrCallCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "callCategoryId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrCallCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByCallCategoryName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return memrCallCategoryRepository.findByCallCategoryNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCallCategoryName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{bloodgroupId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrCallCategory memrCallCategory = memrCallCategoryRepository.getById(bloodgroupId);
        if (memrCallCategory != null) {
            memrCallCategory.setIsDeleted(true);
            memrCallCategoryRepository.save(memrCallCategory);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}
//test