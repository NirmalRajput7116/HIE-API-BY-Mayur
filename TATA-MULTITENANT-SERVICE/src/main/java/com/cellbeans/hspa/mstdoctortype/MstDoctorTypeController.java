package com.cellbeans.hspa.mstdoctortype;

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
@RequestMapping("/mst_doctortype")
public class MstDoctorTypeController {

    @Autowired
    MstDoctorTypeRepository mstDoctorTypeRepository;
    private Map<String, String> respMap = new HashMap<>();
    @Autowired
    private MstDoctorTypeService mstDoctorTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoctorType mstDoctorType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDoctorType.getDtName() != null) {
            mstDoctorTypeRepository.save(mstDoctorType);
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
        Map<String, Object> automap = new HashMap<>();
        List<MstDoctorType> records;
        records = mstDoctorTypeRepository.findByDtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dtId}")
    public MstDoctorType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dtId") Long dtId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoctorType mstDoctorType = mstDoctorTypeRepository.getById(dtId);
        return mstDoctorType;
    }

    @RequestMapping("update")
    public MstDoctorType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoctorType mstDoctorType) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDoctorTypeRepository.save(mstDoctorType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDoctorType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dtId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDoctorTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDoctorTypeRepository.findByDtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dtId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dtId") Long dtId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoctorType mstDoctorType = mstDoctorTypeRepository.getById(dtId);
        if (mstDoctorType != null) {
            mstDoctorType.setDeleted(true);
            mstDoctorTypeRepository.save(mstDoctorType);
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
        List<Tuple> items = mstDoctorTypeService.getMstDoctorTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
