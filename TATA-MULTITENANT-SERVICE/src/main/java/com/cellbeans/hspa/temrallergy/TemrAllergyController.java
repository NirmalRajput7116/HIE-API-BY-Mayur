package com.cellbeans.hspa.temrallergy;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_allergy")
public class TemrAllergyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrAllergyRepository temrAllergyRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrAllergy temrAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrAllergy.getAllergyVisitId().getVisitId() > 0) {
            temrAllergyRepository.save(temrAllergy);
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
        List<TemrAllergy> records;
        records = temrAllergyRepository.findByAllergyAllergyIdAllergyName(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{allergyId}")
    public TemrAllergy read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("allergyId") Long allergyId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrAllergy temrAllergy = temrAllergyRepository.getById(allergyId);
        return temrAllergy;
    }

    @RequestMapping("update")
    public TemrAllergy update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrAllergy temrAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        return temrAllergyRepository.save(temrAllergy);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrAllergy> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "allergyId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrAllergyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrAllergyRepository.findByAllergyAllergyIdAllergyNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{allergyId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("allergyId") Long allergyId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrAllergy temrAllergy = temrAllergyRepository.getById(allergyId);
        if (temrAllergy != null) {
            temrAllergy.setDeleted(true);
            temrAllergyRepository.save(temrAllergy);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
