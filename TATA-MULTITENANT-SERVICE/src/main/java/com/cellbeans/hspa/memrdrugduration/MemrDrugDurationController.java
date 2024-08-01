package com.cellbeans.hspa.memrdrugduration;

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
@RequestMapping("/memr_drug_duration")
public class MemrDrugDurationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrDrugDurationRepository memrDrugDurationRepository;

    @Autowired
    private MemrDrugDurationService memrDrugDurationService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrDrugDuration memrDrugDuration) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrDrugDuration.getDdName() != null) {
            if (memrDrugDurationRepository.findByAllOrderByDdName(memrDrugDuration.getDdName()) == 0) {
                memrDrugDurationRepository.save(memrDrugDuration);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
            }
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
        List<MemrDrugDuration> records;
        records = memrDrugDurationRepository.findByDdNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ddId}")
    public MemrDrugDuration read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ddId") Long ddId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrDrugDuration memrDrugDuration = memrDrugDurationRepository.getById(ddId);
        return memrDrugDuration;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrDrugDuration memrDrugDuration) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrDrugDurationRepository.findByAllOrderByDdName(memrDrugDuration.getDdName()) == 0) {
            memrDrugDurationRepository.save(memrDrugDuration);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Already Added");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrDrugDuration> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ddId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrDrugDurationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrDrugDurationRepository.findByDdNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ddId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ddId") Long ddId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrDrugDuration memrDrugDuration = memrDrugDurationRepository.getById(ddId);
        if (memrDrugDuration != null) {
            memrDrugDuration.setIsDeleted(true);
            memrDrugDurationRepository.save(memrDrugDuration);
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
        List<Tuple> items = memrDrugDurationService.getMemrDrugDurationForDropdown(page, size, globalFilter);
        return items;
    }

}
            
