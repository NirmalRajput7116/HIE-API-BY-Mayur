package com.cellbeans.hspa.mstintraoperativeinstructions;

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
@RequestMapping("/mst_intra_operative_instructions")
public class MstIntraOperativeInstructionsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstIntraOperativeInstructionsRepository mstIntraOperativeInstructionsRepository;
    @Autowired
    private MstIntraOperativeInstructionsService mstIntraOperativeInstructionsService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstIntraOperativeInstructions mstIntraOperativeInstructions) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstIntraOperativeInstructions.getIoiName() != null) {
            mstIntraOperativeInstructionsRepository.save(mstIntraOperativeInstructions);
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
        List<MstIntraOperativeInstructions> records;
        records = mstIntraOperativeInstructionsRepository.findByIoiNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ioiId}")
    public MstIntraOperativeInstructions read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ioiId") Long ioiId) {
        TenantContext.setCurrentTenant(tenantName);
        MstIntraOperativeInstructions mstIntraOperativeInstructions = mstIntraOperativeInstructionsRepository.getById(ioiId);
        return mstIntraOperativeInstructions;
    }

    @RequestMapping("update")
    public MstIntraOperativeInstructions update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstIntraOperativeInstructions mstIntraOperativeInstructions) {
        TenantContext.setCurrentTenant(tenantName);
        return mstIntraOperativeInstructionsRepository.save(mstIntraOperativeInstructions);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstIntraOperativeInstructions> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ioiId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstIntraOperativeInstructionsRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstIntraOperativeInstructionsRepository.findByIoiNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ioiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ioiId") Long ioiId) {
        TenantContext.setCurrentTenant(tenantName);
        MstIntraOperativeInstructions mstIntraOperativeInstructions = mstIntraOperativeInstructionsRepository.getById(ioiId);
        if (mstIntraOperativeInstructions != null) {
            mstIntraOperativeInstructions.setIsDeleted(true);
            mstIntraOperativeInstructionsRepository.save(mstIntraOperativeInstructions);
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
        List<Tuple> items = mstIntraOperativeInstructionsService.getMstIntraOperativeInstructionsForDropdown(page, size, globalFilter);
        return items;
    }

}
            
