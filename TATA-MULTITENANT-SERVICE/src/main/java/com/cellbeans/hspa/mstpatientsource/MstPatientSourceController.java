package com.cellbeans.hspa.mstpatientsource;

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
@RequestMapping("/mst_patient_source")
public class MstPatientSourceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstPatientSourceRepository mstPatientSourceRepository;

    @Autowired
    private MstPatientSourceService mstPatientSourceService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPatientSource mstPatientSource) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstPatientSource.getPsName() != null) {
            mstPatientSource.setPsName(mstPatientSource.getPsName().trim());
            MstPatientSource mstPatientSourceObject = mstPatientSourceRepository.findByPsNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstPatientSource.getPsName());
            if (mstPatientSourceObject == null) {
                mstPatientSourceRepository.save(mstPatientSource);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Duplicate Patient Sourc Name");
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
        List<MstPatientSource> records;
        records = mstPatientSourceRepository.findByPsNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{psId}")
    public MstPatientSource read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatientSource mstPatientSource = mstPatientSourceRepository.getById(psId);
        return mstPatientSource;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPatientSource mstPatientSource) {
        TenantContext.setCurrentTenant(tenantName);
        mstPatientSource.setPsName(mstPatientSource.getPsName().trim());
        MstPatientSource mstPatientSourceObject = mstPatientSourceRepository.findByPsNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstPatientSource.getPsName());
        if (mstPatientSourceObject == null) {
            mstPatientSourceRepository.save(mstPatientSource);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstPatientSourceObject.getPsId() == mstPatientSource.getPsId()) {
            mstPatientSourceRepository.save(mstPatientSource);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Duplicate Patient Source Found");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstPatientSource> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstPatientSourceRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByPsNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstPatientSourceRepository.findByPsNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByPsNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{psId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatientSource mstPatientSource = mstPatientSourceRepository.getById(psId);
        if (mstPatientSource != null) {
            mstPatientSource.setDeleted(true);
            mstPatientSourceRepository.save(mstPatientSource);
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
        List<Tuple> items = mstPatientSourceService.getMstPatientSourceForDropdown(page, size, globalFilter);
        return items;
    }

}
            
