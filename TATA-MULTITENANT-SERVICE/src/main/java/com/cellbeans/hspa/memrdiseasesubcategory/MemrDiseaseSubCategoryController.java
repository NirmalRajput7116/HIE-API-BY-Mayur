package com.cellbeans.hspa.memrdiseasesubcategory;

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
@RequestMapping("/memr_disease_sub_category")
public class MemrDiseaseSubCategoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrDiseaseSubCategoryRepository memrDiseaseSubCategoryRepository;

    @Autowired
    private MemrDiseaseSubCategoryService memrDiseaseSubCategoryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrDiseaseSubCategory memrDiseaseSubCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrDiseaseSubCategory.getDcsName() != null) {
            if (memrDiseaseSubCategoryRepository.findByAllOrderByDcName(memrDiseaseSubCategory.getDcsName(), memrDiseaseSubCategory.getDcsDcId().getDcId()) == 0) {
                memrDiseaseSubCategoryRepository.save(memrDiseaseSubCategory);
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
        List<MemrDiseaseSubCategory> records;
        records = memrDiseaseSubCategoryRepository.findByDcsNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dscId}")
    public MemrDiseaseSubCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dscId") Long dscId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrDiseaseSubCategory memrDiseaseSubCategory = memrDiseaseSubCategoryRepository.getById(dscId);
        return memrDiseaseSubCategory;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrDiseaseSubCategory memrDiseaseSubCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrDiseaseSubCategoryRepository.findByAllOrderByDcName(memrDiseaseSubCategory.getDcsName(), memrDiseaseSubCategory.getDcsDcId().getDcId()) == 0) {
            memrDiseaseSubCategoryRepository.save(memrDiseaseSubCategory);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Already Added");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrDiseaseSubCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dscId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrDiseaseSubCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrDiseaseSubCategoryRepository.findByDcsNameContainsAndIsActiveTrueAndIsDeletedFalseOrDcsDcIdDcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dscId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dscId") Long dscId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrDiseaseSubCategory memrDiseaseSubCategory = memrDiseaseSubCategoryRepository.getById(dscId);
        if (memrDiseaseSubCategory != null) {
            memrDiseaseSubCategory.setIsDeleted(true);
            memrDiseaseSubCategoryRepository.save(memrDiseaseSubCategory);
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
        List<Tuple> items = memrDiseaseSubCategoryService.getMemrDiseaseSubCategoryForDropdown(page, size, globalFilter);
        return items;
    }

}
            
