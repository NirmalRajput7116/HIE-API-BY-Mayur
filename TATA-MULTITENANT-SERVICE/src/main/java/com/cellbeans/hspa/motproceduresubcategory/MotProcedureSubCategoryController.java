package com.cellbeans.hspa.motproceduresubcategory;

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
@RequestMapping("/mot_procedure_sub_category")
public class MotProcedureSubCategoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MotProcedureSubCategoryRepository motProcedureSubCategoryRepository;

    @Autowired
    private MotProcedureSubCategoryService motProcedureSubCategoryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotProcedureSubCategory motProcedureSubCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (motProcedureSubCategory.getPscName() != null) {
            motProcedureSubCategoryRepository.save(motProcedureSubCategory);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
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
        List<MotProcedureSubCategory> records;
        records = motProcedureSubCategoryRepository.findByPscNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{pscId}")
    public MotProcedureSubCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pscId") Long pscId) {
        TenantContext.setCurrentTenant(tenantName);
        MotProcedureSubCategory motProcedureSubCategory = motProcedureSubCategoryRepository.getById(pscId);
        return motProcedureSubCategory;
    }

    @RequestMapping("update")
    public MotProcedureSubCategory update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotProcedureSubCategory motProcedureSubCategory) {
        TenantContext.setCurrentTenant(tenantName);
        return motProcedureSubCategoryRepository.save(motProcedureSubCategory);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MotProcedureSubCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pscId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return motProcedureSubCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return motProcedureSubCategoryRepository.findByPscNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{pscId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pscId") Long pscId) {
        TenantContext.setCurrentTenant(tenantName);
        MotProcedureSubCategory motProcedureSubCategory = motProcedureSubCategoryRepository.getById(pscId);
        if (motProcedureSubCategory != null) {
            motProcedureSubCategory.setIsDeleted(true);
            motProcedureSubCategoryRepository.save(motProcedureSubCategory);
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
        List<Tuple> items = motProcedureSubCategoryService.getMotProcedureSubCategoryForDropdown(page, size, globalFilter);
        return items;
    }

}
            
