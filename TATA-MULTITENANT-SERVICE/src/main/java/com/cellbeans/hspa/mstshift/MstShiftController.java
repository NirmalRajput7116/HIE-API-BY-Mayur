package com.cellbeans.hspa.mstshift;

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
@RequestMapping("/mst_shift")
public class MstShiftController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstShiftRepository mstShiftRepository;
    @Autowired
    private MstShiftService mstShiftService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstShift mstShift) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstShift.getShiftName() != null) {
            mstShiftRepository.save(mstShift);
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
        List<MstShift> records;
        records = mstShiftRepository.findByShiftNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{shiftId}")
    public MstShift read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("shiftId") Long shiftId) {
        TenantContext.setCurrentTenant(tenantName);
        MstShift mstShift = mstShiftRepository.getById(shiftId);
        return mstShift;
    }

    @RequestMapping("update")
    public MstShift update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstShift mstShift) {
        TenantContext.setCurrentTenant(tenantName);
        return mstShiftRepository.save(mstShift);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstShift> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "shiftId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstShiftRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstShiftRepository.findByShiftNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{shiftId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("shiftId") Long shiftId) {
        TenantContext.setCurrentTenant(tenantName);
        MstShift mstShift = mstShiftRepository.getById(shiftId);
        if (mstShift != null) {
            mstShift.setIsDeleted(true);
            mstShiftRepository.save(mstShift);
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
        List<Tuple> items = mstShiftService.getMstShiftForDropdown(page, size, globalFilter);
        return items;
    }

}
            
