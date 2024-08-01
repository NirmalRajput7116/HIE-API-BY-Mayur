package com.cellbeans.hspa.mradtestcategory;

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
@RequestMapping("/mrad_test_category")
public class MradTestCategoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MradTestCategoryRepository mradTestCategoryRepository;

    @Autowired
    private MradTestCategoryService mradTestCategoryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MradTestCategory mradTestCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (mradTestCategory.getTcName() != null) {
            mradTestCategoryRepository.save(mradTestCategory);
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
        List<MradTestCategory> records;
        records = mradTestCategoryRepository.findByTcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{tcId}")
    public MradTestCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tcId") Long tcId) {
        TenantContext.setCurrentTenant(tenantName);
        MradTestCategory mradTestCategory = mradTestCategoryRepository.getById(tcId);
        return mradTestCategory;
    }

    @RequestMapping("update")
    public MradTestCategory update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MradTestCategory mradTestCategory) {
        TenantContext.setCurrentTenant(tenantName);
        return mradTestCategoryRepository.save(mradTestCategory);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MradTestCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mradTestCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mradTestCategoryRepository.findByTcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{tcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tcId") Long tcId) {
        TenantContext.setCurrentTenant(tenantName);
        MradTestCategory mradTestCategory = mradTestCategoryRepository.getById(tcId);
        if (mradTestCategory != null) {
            mradTestCategory.setIsDeleted(true);
            mradTestCategoryRepository.save(mradTestCategory);
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
        List<Tuple> items = mradTestCategoryService.getMradTestCategoryForDropdown(page, size, globalFilter);
        return items;
    }

}
            
