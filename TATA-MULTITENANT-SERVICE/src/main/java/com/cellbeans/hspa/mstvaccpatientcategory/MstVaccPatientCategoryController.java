package com.cellbeans.hspa.mstvaccpatientcategory;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_vacc_patient_category")
public class MstVaccPatientCategoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstVaccPatientCategoryRepository mstVaccPatientCategoryRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVaccPatientCategory mstVaccPatientCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstVaccPatientCategory.getVpcVccPatientType() != null) {
            mstVaccPatientCategory.setVpcVccPatientType(mstVaccPatientCategory.getVpcVccPatientType().trim());
            MstVaccPatientCategory mstVaccPatientCategoryObject = mstVaccPatientCategoryRepository.findByVpcVccPatientTypeEqualsAndIsActiveTrueAndIsDeletedFalse(mstVaccPatientCategory.getVpcVccPatientType());
            if (mstVaccPatientCategoryObject == null) {
                mstVaccPatientCategoryRepository.save(mstVaccPatientCategory);
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
        List<MstVaccPatientCategory> records;
        records = mstVaccPatientCategoryRepository.findByVpcIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{vpcId}")
    public MstVaccPatientCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vpcId") Integer vpcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVaccPatientCategory mstVaccPatientCategory = mstVaccPatientCategoryRepository.getById(vpcId);
        return mstVaccPatientCategory;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVaccPatientCategory mstVaccPatientCategory) {
        TenantContext.setCurrentTenant(tenantName);
        mstVaccPatientCategory.setVpcVccPatientType(mstVaccPatientCategory.getVpcVccPatientType().trim());
        MstVaccPatientCategory mstVaccPatientCategoryObject = mstVaccPatientCategoryRepository.findByVpcVccPatientTypeEqualsAndIsActiveTrueAndIsDeletedFalse(mstVaccPatientCategory.getVpcVccPatientType());
        if (mstVaccPatientCategoryObject == null) {
            mstVaccPatientCategoryRepository.save(mstVaccPatientCategory);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstVaccPatientCategoryObject.getVpcId() == mstVaccPatientCategory.getVpcId()) {
            mstVaccPatientCategoryRepository.save(mstVaccPatientCategory);
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
    public Iterable<MstVaccPatientCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vpcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstVaccPatientCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstVaccPatientCategoryRepository.findByVpcVccPatientTypeContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @GetMapping
    @RequestMapping("listOFCategory")
    public List<MstVaccPatientCategory> list(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstVaccPatientCategoryRepository.findAllByIsDeletedFalse();

    }

    @PutMapping("delete/{vpcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vpcId") Integer vpcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVaccPatientCategory mstVaccPatientCategory = mstVaccPatientCategoryRepository.getById(vpcId);
        if (mstVaccPatientCategory != null) {
            mstVaccPatientCategory.setIsDeleted(true);
            mstVaccPatientCategoryRepository.save(mstVaccPatientCategory);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
