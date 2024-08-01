package com.cellbeans.hspa.memrresolutioncategory;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memr_resolutioncategory")
public class MemrResolutionCategoryController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrResolutionCategoryRepository memrResolutionCategoryRepository;

    @Autowired
    MemrResolutionCategoryService memrResolutionCategoryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrResolutionCategory memrResolutionCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrResolutionCategory.getResolutionCategoryName() != null) {
            memrResolutionCategory.setResolutionCategoryName(memrResolutionCategory.getResolutionCategoryName().trim());
            MemrResolutionCategory memrResolutionCategoryObject = memrResolutionCategoryRepository.findByResolutionCategoryNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrResolutionCategory.getResolutionCategoryName());
            if (memrResolutionCategoryObject == null) {
                memrResolutionCategoryRepository.save(memrResolutionCategory);
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
        List<MemrResolutionCategory> records;
        records = memrResolutionCategoryRepository.findByResolutionCategoryNameContainsAndIsActiveTrueAndIsDeletedFalse(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bloodgroupId}")
    public MemrResolutionCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrResolutionCategory memrResolutionCategory = memrResolutionCategoryRepository.getById(bloodgroupId);
        return memrResolutionCategory;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrResolutionCategory memrResolutionCategory) {
        TenantContext.setCurrentTenant(tenantName);
        memrResolutionCategory.setResolutionCategoryName(memrResolutionCategory.getResolutionCategoryName().trim());
        MemrResolutionCategory memrResolutionCategoryObject = memrResolutionCategoryRepository.findByResolutionCategoryNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrResolutionCategory.getResolutionCategoryName());
        if (memrResolutionCategoryObject == null) {
            memrResolutionCategoryRepository.save(memrResolutionCategory);
            respMap.put("success", "1");
            respMap.put("msg", "updated Successfully");
            return respMap;
        } else if (memrResolutionCategoryObject.getResolutionCategoryId() == memrResolutionCategory.getResolutionCategoryId()) {
            memrResolutionCategoryRepository.save(memrResolutionCategory);
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
    public Iterable<MemrResolutionCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "resolutionCategoryId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrResolutionCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByResolutionCategoryName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return memrResolutionCategoryRepository.findByResolutionCategoryNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByResolutionCategoryName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{bloodgroupId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrResolutionCategory memrResolutionCategory = memrResolutionCategoryRepository.getById(bloodgroupId);
        if (memrResolutionCategory != null) {
            memrResolutionCategory.setIsDeleted(true);
            memrResolutionCategoryRepository.save(memrResolutionCategory);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}

