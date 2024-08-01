package com.cellbeans.hspa.mstpatienttype;

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
@RequestMapping("/mst_patient_type")
public class MstPatientTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstPatientTypeRepository mstPatientTypeRepository;

    @Autowired
    private MstPatientTypeService mstPatientTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPatientType mstPatientType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstPatientType.getPtName() != null) {
            mstPatientType.setPtName(mstPatientType.getPtName().trim());
            MstPatientType mstPatientTypeObject = mstPatientTypeRepository.findByPtNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstPatientType.getPtName());
            if (mstPatientTypeObject == null) {
                mstPatientTypeRepository.save(mstPatientType);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Duplicate Patient Type Name");
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
        List<MstPatientType> records;
        records = mstPatientTypeRepository.findByPtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ptId}")
    public MstPatientType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ptId") Long ptId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatientType mstPatientType = mstPatientTypeRepository.getById(ptId);
        return mstPatientType;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPatientType mstPatientType) {
        TenantContext.setCurrentTenant(tenantName);
        mstPatientType.setPtName(mstPatientType.getPtName().trim());
        MstPatientType mstPatientTypeObject = mstPatientTypeRepository.findByPtNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstPatientType.getPtName());
        if (mstPatientTypeObject == null) {
            mstPatientTypeRepository.save(mstPatientType);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstPatientTypeObject.getPtId() == mstPatientType.getPtId()) {
            mstPatientTypeRepository.save(mstPatientType);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Duplicate Patient Type Found");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstPatientType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ptId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstPatientTypeRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByPtNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstPatientTypeRepository.findByPtNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByPtNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ptId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ptId") Long ptId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatientType mstPatientType = mstPatientTypeRepository.getById(ptId);
        if (mstPatientType != null) {
            mstPatientType.setIsDeleted(true);
            mstPatientTypeRepository.save(mstPatientType);
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
        List<Tuple> items = mstPatientTypeService.getMstPatientTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
