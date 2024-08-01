package com.cellbeans.hspa.msttimeunit;

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
@RequestMapping("/mst-timeunit")
public class MstTimeUnitController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstTimeUnitRepository mstTimeUnitRepository;
    @Autowired
    private MstTimeUnitService mstTimeUnitService;

    //
//    @RequestMapping("create")
//    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTimeUnit mstTimeUnit) {
//        if (mstTimeUnit.getTuName() != null) {
//            mstTimeUnitRepository.save(mstTimeUnit);
//            respMap.put("success", "1");
//            respMap.put("msg", "Added Successfully");
//            return respMap;
//        } else {
//            respMap.put("success", "0");
//            respMap.put("msg", "Failed To Add Null Field");
//            return respMap;
//        }
//    }
//
//    @RequestMapping("/autocomplete/{key}")
//    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//        Map<String, Object> automap = new HashMap<String, Object>();
//        List<MstTimeUnit> records;
//        records = mstTimeUnitRepository.findBySampleNameContains(key);
//        automap.put("record", records);
//        return automap;
//    }
//
//    @RequestMapping("byid/{sampleId}")
//    public MstTimeUnit read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sampleId") Long sampleId) {
//        MstTimeUnit mstTimeUnit = mstTimeUnitRepository.getById(sampleId);
//        return mstTimeUnit;
//    }
//
//    @RequestMapping("update")
//    public MstTimeUnit update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTimeUnit mstTimeUnit) {
//        return mstTimeUnitRepository.save(mstTimeUnit);
//    }
//
    @GetMapping
    @RequestMapping("list")
    public Iterable<MstTimeUnit> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tuId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstTimeUnitRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));

        } else {
            return mstTimeUnitRepository.findByTuNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));

        }

    }
//    @PutMapping("delete/{sampleId}")
//    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sampleId") Long sampleId) {
//        MstTimeUnit mstTimeUnit = mstTimeUnitRepository.getById(sampleId);
//        if (mstTimeUnit != null) {
//            mstTimeUnit.setIsDeleted(true);
//            mstTimeUnitRepository.save(mstTimeUnit);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        } else {
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstTimeUnitService.getMstTimeUnitForDropdown(page, size, globalFilter);
        return items;
    }

}
            
