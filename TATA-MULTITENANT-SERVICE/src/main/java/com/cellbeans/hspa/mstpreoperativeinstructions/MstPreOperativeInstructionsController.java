package com.cellbeans.hspa.mstpreoperativeinstructions;

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
@RequestMapping("/mst_pre_operative_instructions")
public class MstPreOperativeInstructionsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstPreOperativeInstructionsRepository mstPreOperativeInstructionsRepository;

    @Autowired
    private MstPreOperativeInstructionsService mstPreOperativeInstructionsService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPreOperativeInstructions mstPreOperativeInstructions) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstPreOperativeInstructions.getPoiName() != null) {
            mstPreOperativeInstructionsRepository.save(mstPreOperativeInstructions);
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
        List<MstPreOperativeInstructions> records;
        records = mstPreOperativeInstructionsRepository.findByPoiNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{poiId}")
    public MstPreOperativeInstructions read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("poiId") Long poiId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPreOperativeInstructions mstPreOperativeInstructions = mstPreOperativeInstructionsRepository.getById(poiId);
        return mstPreOperativeInstructions;
    }

    @RequestMapping("update")
    public MstPreOperativeInstructions update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPreOperativeInstructions mstPreOperativeInstructions) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPreOperativeInstructionsRepository.save(mstPreOperativeInstructions);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstPreOperativeInstructions> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "poiId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstPreOperativeInstructionsRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstPreOperativeInstructionsRepository.findByPoiNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{poiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("poiId") Long poiId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPreOperativeInstructions mstPreOperativeInstructions = mstPreOperativeInstructionsRepository.getById(poiId);
        if (mstPreOperativeInstructions != null) {
            mstPreOperativeInstructions.setIsDeleted(true);
            mstPreOperativeInstructionsRepository.save(mstPreOperativeInstructions);
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
        List<Tuple> items = mstPreOperativeInstructionsService.getMstPreOperativeInstructionsForDropdown(page, size, globalFilter);
        return items;
    }

}
            
