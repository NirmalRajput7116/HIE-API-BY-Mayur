package com.cellbeans.hspa.mstunit;

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
@RequestMapping("/mst_unit")
public class MstUnitController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstUnitRepository mstUnitRepository;
    @Autowired
    private MstUnitService mstUnitService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstUnit mstUnit) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstUnit.getUnitName() != null) {
            mstUnit.setUnitName(mstUnit.getUnitName().trim());
            MstUnit mstUnitObject = mstUnitRepository.findByUnitNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstUnit.getUnitName());
            if (mstUnitObject == null) {
                mstUnitRepository.save(mstUnit);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Duplicate Unit Name");
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
        List<MstUnit> records;
        records = mstUnitRepository.findByUnitNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{unitId}")
    public MstUnit read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        MstUnit mstUnit = mstUnitRepository.getById(unitId);
        return mstUnit;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstUnit mstUnit) {
        TenantContext.setCurrentTenant(tenantName);
//        return mstUnitRepository.save(mstUnit);
        mstUnit.setUnitName(mstUnit.getUnitName().trim());
        MstUnit mstUnitObject = mstUnitRepository.findByUnitNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstUnit.getUnitName());
        if (mstUnitObject == null) {
            mstUnitRepository.save(mstUnit);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstUnitObject.getUnitId() == mstUnit.getUnitId()) {
            mstUnitRepository.save(mstUnit);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Duplicate Unit Found");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstUnit> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "15000") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "unitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstUnitRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByUnitNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstUnitRepository.findByUnitNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByUnitNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listbyunitid")
    public Iterable<MstUnit> listbyunitid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "1500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "unitid", required = false) long unitId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "unitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstUnitRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByUnitNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstUnitRepository.findByUnitIdAndIsActiveTrueAndIsDeletedFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{unitId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        MstUnit mstUnit = mstUnitRepository.getById(unitId);
        if (mstUnit != null) {
            mstUnit.setIsDeleted(true);
            mstUnitRepository.save(mstUnit);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("unitlistuserwise")
    public List<MstUnit> unitlistByUsername(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("unitId :" + unitId);
        return mstUnitRepository.findByUnitIdAndIsActiveTrueAndIsDeletedFalse(unitId);
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName,
                            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                            @RequestParam(value = "size", defaultValue = "500", required = false) Integer size,
                            @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstUnitService.getMstUnitForDropdown(page, size, globalFilter);
        return items;
    }

}
            
