package com.cellbeans.hspa.mstincomecategory;

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
@RequestMapping("/mst_income_category")
public class MstIncomeCategoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstIncomeCategoryRepository mstIncomeCategoryRepository;

    @Autowired
    private MstIncomeCategoryService mstIncomeCategoryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstIncomeCategory mstIncomeCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstIncomeCategory.getIcName() != null) {
            mstIncomeCategory.setIcName(mstIncomeCategory.getIcName().trim());
            MstIncomeCategory mstIncomeCategoryObject = mstIncomeCategoryRepository.findByIcNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstIncomeCategory.getIcName());
            if (mstIncomeCategoryObject == null) {
                mstIncomeCategoryRepository.save(mstIncomeCategory);
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
        List<MstIncomeCategory> records;
        records = mstIncomeCategoryRepository.findByIcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{icId}")
    public MstIncomeCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        MstIncomeCategory mstIncomeCategory = mstIncomeCategoryRepository.getById(icId);
        return mstIncomeCategory;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstIncomeCategory mstIncomeCategory) {
        TenantContext.setCurrentTenant(tenantName);
        mstIncomeCategory.setIcName(mstIncomeCategory.getIcName().trim());
        MstIncomeCategory mstIncomeCategoryObject = mstIncomeCategoryRepository.findByIcNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstIncomeCategory.getIcName());
        if (mstIncomeCategoryObject == null) {
            mstIncomeCategoryRepository.save(mstIncomeCategory);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstIncomeCategoryObject.getIcId() == mstIncomeCategory.getIcId()) {
            mstIncomeCategoryRepository.save(mstIncomeCategory);
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
    public Iterable<MstIncomeCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "icId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstIncomeCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstIncomeCategoryRepository.findByIcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{icId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        MstIncomeCategory mstIncomeCategory = mstIncomeCategoryRepository.getById(icId);
        if (mstIncomeCategory != null) {
            mstIncomeCategory.setIsDeleted(true);
            mstIncomeCategoryRepository.save(mstIncomeCategory);
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
        List<Tuple> items = mstIncomeCategoryService.getMstIncomeCategoryForDropdown(page, size, globalFilter);
        return items;
    }

}
            
