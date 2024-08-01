package com.cellbeans.hspa.mstUnitType;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_unit_type")
public class MstUnitTypeController {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstUnitTypeRepository mstunittypeRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstUnitType mstUnitType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstUnitType.getUnittypeName() != null) {
            mstUnitType.setUnittypeName(mstUnitType.getUnittypeName().trim());
            MstUnitType mstUnittypeObject = mstunittypeRepository.findByunittypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstUnitType.getUnittypeName());
            if (mstUnittypeObject == null) {
                mstunittypeRepository.save(mstUnitType);
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
        List<MstUnitType> records;
        records = mstunittypeRepository.findByunittypeNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{unittypeId}")
    public MstUnitType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unittypeId") Long unittypeId) {
        TenantContext.setCurrentTenant(tenantName);
        MstUnitType mstUnitType = mstunittypeRepository.getById(unittypeId);
        return mstUnitType;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstUnitType mstUnitType) {
        TenantContext.setCurrentTenant(tenantName);
        /*  return mstunittypeRepository.save(mstCluster);*/
        mstUnitType.setUnittypeName(mstUnitType.getUnittypeName().trim());
        MstUnitType mstUnittypeObject = mstunittypeRepository.findByunittypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstUnitType.getUnittypeName());
        if (mstUnittypeObject == null) {
            mstunittypeRepository.save(mstUnitType);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstUnittypeObject.getUnittypeName() == mstUnitType.getUnittypeName()) {
            mstunittypeRepository.save(mstUnitType);
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
    public Iterable<MstUnitType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "unittypeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstunittypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstunittypeRepository.findByunittypeNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{unittypeId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unittypeId") Long unittypeId) {
        TenantContext.setCurrentTenant(tenantName);
        MstUnitType mstUnitType = mstunittypeRepository.getById(unittypeId);
        if (mstUnitType != null) {
            mstUnitType.setDeleted(true);
            mstunittypeRepository.save(mstUnitType);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}
