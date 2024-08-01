package com.cellbeans.hspa.memrallergy;

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
@RequestMapping("/memr_allergy")
public class MemrAllergyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrAllergyRepository memrAllergyRepository;

    @Autowired
    private MemrAllergyService memrAllergyService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrAllergy memrAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrAllergy.getAllergyName() != null) {
            memrAllergyRepository.save(memrAllergy);
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
        List<MemrAllergy> records;
        records = memrAllergyRepository.findByAllergyNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{allergyId}")
    public MemrAllergy read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("allergyId") Long allergyId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrAllergy memrAllergy = memrAllergyRepository.getById(allergyId);
        return memrAllergy;
    }

    @RequestMapping("update")
    public MemrAllergy update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrAllergy memrAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        return memrAllergyRepository.save(memrAllergy);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrAllergy> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "allergyId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrAllergyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrAllergyRepository.findByAllergyNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{allergyId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("allergyId") Long allergyId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrAllergy memrAllergy = memrAllergyRepository.getById(allergyId);
        if (memrAllergy != null) {
            memrAllergy.setIsDeleted(true);
            memrAllergyRepository.save(memrAllergy);
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
        List<Tuple> items = memrAllergyService.getMemrAllergyForDropdown(page, size, globalFilter);
        return items;
    }

}
            
