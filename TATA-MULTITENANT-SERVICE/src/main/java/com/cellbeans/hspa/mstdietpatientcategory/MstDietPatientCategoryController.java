package com.cellbeans.hspa.mstdietpatientcategory;

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
@RequestMapping("/mst_diet_patient_category")
public class MstDietPatientCategoryController {

    @Autowired
    MstDietPatientCategoryRepository mstDietPatientCategoryRepository;
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    private MstDietPatientCategoryService mstDietPatientCategoryService;

    @GetMapping
    @RequestMapping("category_list")
    public List<MstDietPatientCategory> list() {
        return mstDietPatientCategoryRepository.findAll();
    }

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDietPatientCategory mstDietPatientCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDietPatientCategory.getDpcPatientType() != null) {
            mstDietPatientCategory.setDpcPatientType(mstDietPatientCategory.getDpcPatientType().trim());
            MstDietPatientCategory mstDietPatientCategoryObject = mstDietPatientCategoryRepository.findByDpcPatientTypeEqualsAndIsActiveTrueAndIsDeletedFalse(mstDietPatientCategory.getDpcPatientType());
            if (mstDietPatientCategoryObject == null) {
                mstDietPatientCategory.setIsActive(true);
                mstDietPatientCategory.setIsDeleted(false);
                mstDietPatientCategoryRepository.save(mstDietPatientCategory);
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
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") Integer key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstDietPatientCategory> records;
        records = mstDietPatientCategoryRepository.findByDpcIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dpcId}")
    public MstDietPatientCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dpcId") Integer dpcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDietPatientCategory mstDietPatientCategory = mstDietPatientCategoryRepository.getById(dpcId);
        return mstDietPatientCategory;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDietPatientCategory mstDietPatientCategory) {
        TenantContext.setCurrentTenant(tenantName);
        mstDietPatientCategory.setDpcPatientType(mstDietPatientCategory.getDpcPatientType().trim());
        MstDietPatientCategory mstDietPatientCategoryObject = mstDietPatientCategoryRepository.findByDpcPatientTypeEqualsAndIsActiveTrueAndIsDeletedFalse(mstDietPatientCategory.getDpcPatientType());
        if (mstDietPatientCategoryObject == null) {
            mstDietPatientCategory.setIsActive(true);
            mstDietPatientCategory.setIsDeleted(false);
            mstDietPatientCategoryRepository.save(mstDietPatientCategory);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstDietPatientCategoryObject.getDpcId() == mstDietPatientCategory.getDpcId()) {
            mstDietPatientCategory.setIsActive(true);
            mstDietPatientCategory.setIsDeleted(false);
            mstDietPatientCategoryRepository.save(mstDietPatientCategory);
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
    public Iterable<MstDietPatientCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dpcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDietPatientCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDietPatientCategoryRepository.findByDpcPatientTypeContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{dpcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dpcId") Integer dpcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDietPatientCategory mstDietPatientCategory = mstDietPatientCategoryRepository.getById(dpcId);
        if (mstDietPatientCategory != null) {
            mstDietPatientCategory.setIsDeleted(true);
            mstDietPatientCategoryRepository.save(mstDietPatientCategory);
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
        List<Tuple> items = mstDietPatientCategoryService.getMstDietPatientCategoryForDropdown(page, size, globalFilter);
        return items;
    }

}
