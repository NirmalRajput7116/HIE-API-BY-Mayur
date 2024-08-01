package com.cellbeans.hspa.mstencountertype;

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
@RequestMapping("/mst_encounter_type")
public class MstEncounterTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstEncounterTypeRepository mstEncounterTypeRepository;
    @Autowired
    private MstEncounterTypeService mstEncounterTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstEncounterType mstEncounterType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstEncounterType.getEtName() != null) {
            mstEncounterType.setEtName(mstEncounterType.getEtName().trim());
            MstEncounterType mstEncounterTypeObject = mstEncounterTypeRepository.findByEtNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstEncounterType.getEtName());
            if (mstEncounterTypeObject == null) {
                mstEncounterTypeRepository.save(mstEncounterType);
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
        List<MstEncounterType> records;
        records = mstEncounterTypeRepository.findByEtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{etId}")
    public MstEncounterType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("etId") Long etId) {
        TenantContext.setCurrentTenant(tenantName);
        MstEncounterType mstEncounterType = mstEncounterTypeRepository.getById(etId);
        return mstEncounterType;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstEncounterType mstEncounterType) {
        TenantContext.setCurrentTenant(tenantName);
        mstEncounterType.setEtName(mstEncounterType.getEtName().trim());
        MstEncounterType mstEncounterTypeObject = mstEncounterTypeRepository.findByEtNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstEncounterType.getEtName());
        if (mstEncounterTypeObject == null) {
            mstEncounterTypeRepository.save(mstEncounterType);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstEncounterTypeObject.getEtId() == mstEncounterType.getEtId()) {
            mstEncounterTypeRepository.save(mstEncounterType);
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
    public Iterable<MstEncounterType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "etId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstEncounterTypeRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByEtNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstEncounterTypeRepository.findByEtNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByEtNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{etId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("etId") Long etId) {
        TenantContext.setCurrentTenant(tenantName);
        MstEncounterType mstEncounterType = mstEncounterTypeRepository.getById(etId);
        if (mstEncounterType != null) {
            mstEncounterType.setIsDeleted(true);
            mstEncounterTypeRepository.save(mstEncounterType);
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
        List<Tuple> items = mstEncounterTypeService.getMstEncounterTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
