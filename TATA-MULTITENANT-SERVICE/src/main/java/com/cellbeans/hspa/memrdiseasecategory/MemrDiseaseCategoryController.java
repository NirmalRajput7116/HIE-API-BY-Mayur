package com.cellbeans.hspa.memrdiseasecategory;

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
@RequestMapping("/memr_disease_category")
public class MemrDiseaseCategoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrDiseaseCategoryRepository memrDiseaseCategoryRepository;

    @Autowired
    private MemrDiseaseCategoryService memrDiseaseCategoryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrDiseaseCategory memrDiseaseCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrDiseaseCategory.getDcName() != null) {
            if (memrDiseaseCategoryRepository.findByAllOrderByDcName(memrDiseaseCategory.getDcName()) == 0) {
                memrDiseaseCategoryRepository.save(memrDiseaseCategory);
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
        List<MemrDiseaseCategory> records;
        records = memrDiseaseCategoryRepository.findByDcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dcId}")
    public MemrDiseaseCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dcId") Long dcId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrDiseaseCategory memrDiseaseCategory = memrDiseaseCategoryRepository.getById(dcId);
        return memrDiseaseCategory;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrDiseaseCategory memrDiseaseCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrDiseaseCategoryRepository.findByAllOrderByDcName(memrDiseaseCategory.getDcName()) == 0) {
            memrDiseaseCategoryRepository.save(memrDiseaseCategory);
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
    public Iterable<MemrDiseaseCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrDiseaseCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrDiseaseCategoryRepository.findByDcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dcId") Long dcId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrDiseaseCategory memrDiseaseCategory = memrDiseaseCategoryRepository.getById(dcId);
        if (memrDiseaseCategory != null) {
            memrDiseaseCategory.setIsDeleted(true);
            memrDiseaseCategoryRepository.save(memrDiseaseCategory);
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
        List<Tuple> items = memrDiseaseCategoryService.getMemrDiseaseCategoryForDropdown(page, size, globalFilter);
        return items;
    }

}
            
