package com.cellbeans.hspa.memrallergysensitivity;

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
@RequestMapping("/memr_allergy_sensitivity")
public class MemrAllergySensitivityController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrAllergySensitivityRepository memrAllergySensitivityRepository;

    @Autowired
    private MemrAllergySensitivityService memrAllergySensitivityService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrAllergySensitivity memrAllergySensitivity) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrAllergySensitivity.getAsName() != null) {
            memrAllergySensitivity.setAsName(memrAllergySensitivity.getAsName().trim());
            MemrAllergySensitivity mAllergySensitivity = memrAllergySensitivityRepository.findByAsNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrAllergySensitivity.getAsName());
            if (mAllergySensitivity == null) {
                memrAllergySensitivityRepository.save(memrAllergySensitivity);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
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
        List<MemrAllergySensitivity> records;
        records = memrAllergySensitivityRepository.findByAsNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{asId}")
    public MemrAllergySensitivity read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("asId") Long asId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrAllergySensitivity memrAllergySensitivity = memrAllergySensitivityRepository.getById(asId);
        return memrAllergySensitivity;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrAllergySensitivity memrAllergySensitivity) {
        TenantContext.setCurrentTenant(tenantName);
        //        return memrAllergySensitivityRepository.save(memrAllergySensitivity);
        if (memrAllergySensitivity.getAsName() != null) {
            memrAllergySensitivity.setAsName(memrAllergySensitivity.getAsName().trim());
            MemrAllergySensitivity mAllergySensitivity = memrAllergySensitivityRepository.findByAsNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrAllergySensitivity.getAsName());
            if (mAllergySensitivity == null) {
                memrAllergySensitivityRepository.save(memrAllergySensitivity);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                if (mAllergySensitivity.getAsId() == memrAllergySensitivity.getAsId()) {
                    memrAllergySensitivityRepository.save(memrAllergySensitivity);
                    respMap.put("success", "1");
                    respMap.put("msg", "Added Successfully");
                    return respMap;
                } else {
                    respMap.put("success", "0");
                    respMap.put("msg", "Failed To Add Null Field");
                    return respMap;
                }
            }
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrAllergySensitivity> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "asId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrAllergySensitivityRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrAllergySensitivityRepository.findByAsNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{asId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("asId") Long asId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrAllergySensitivity memrAllergySensitivity = memrAllergySensitivityRepository.getById(asId);
        if (memrAllergySensitivity != null) {
            memrAllergySensitivity.setIsDeleted(true);
            memrAllergySensitivityRepository.save(memrAllergySensitivity);
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
        List<Tuple> items = memrAllergySensitivityService.getMemrAllergySensitivityForDropdown(page, size, globalFilter);
        return items;
    }

}
            
