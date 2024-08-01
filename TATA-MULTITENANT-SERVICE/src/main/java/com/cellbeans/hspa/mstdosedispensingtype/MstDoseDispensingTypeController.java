package com.cellbeans.hspa.mstdosedispensingtype;

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
@RequestMapping("/mst_dose_dispensing_type")
public class MstDoseDispensingTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstDoseDispensingTypeRepository mstDoseDispensingTypeRepository;

    @Autowired
    private MstDoseDispensingTypeService mstDoseDispensingTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoseDispensingType mstDoseDispensingType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDoseDispensingType.getDdtName() != null) {
            mstDoseDispensingTypeRepository.save(mstDoseDispensingType);
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
        List<MstDoseDispensingType> records;
        records = mstDoseDispensingTypeRepository.findByDdtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ddtId}")
    public MstDoseDispensingType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ddtId") Long ddtId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoseDispensingType mstDoseDispensingType = mstDoseDispensingTypeRepository.getById(ddtId);
        return mstDoseDispensingType;
    }

    @RequestMapping("update")
    public MstDoseDispensingType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoseDispensingType mstDoseDispensingType) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDoseDispensingTypeRepository.save(mstDoseDispensingType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDoseDispensingType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ddtId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDoseDispensingTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDoseDispensingTypeRepository.findByDdtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ddtId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ddtId") Long ddtId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoseDispensingType mstDoseDispensingType = mstDoseDispensingTypeRepository.getById(ddtId);
        if (mstDoseDispensingType != null) {
            mstDoseDispensingType.setIsDeleted(true);
            mstDoseDispensingTypeRepository.save(mstDoseDispensingType);
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
        List<Tuple> items = mstDoseDispensingTypeService.getMstDoseDispensingTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
