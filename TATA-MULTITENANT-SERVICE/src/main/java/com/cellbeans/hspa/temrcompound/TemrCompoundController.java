package com.cellbeans.hspa.temrcompound;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/temr_compound")
public class TemrCompoundController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrCompoundRepository temrCompoundRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrCompound temrCompound) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrCompound.getCompoundVisitId().getVisitId() > 0) {
            temrCompoundRepository.save(temrCompound);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

  /*  @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TemrCompound> records;
        records = temrCompoundRepository.findByCompoundCompoundIdCompoundNameContains(key);
        automap.put("record", records);
        return automap;
    }*/

    @RequestMapping("byid/{compoundId}")
    public TemrCompound read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("compoundId") Long compoundId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrCompound temrCompound = temrCompoundRepository.getById(compoundId);
        return temrCompound;
    }

    @RequestMapping("update")
    public TemrCompound update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrCompound temrCompound) {
        TenantContext.setCurrentTenant(tenantName);
        return temrCompoundRepository.save(temrCompound);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrCompound> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "compoundId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrCompoundRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {

            /*    return temrCompoundRepository.findByCompoundCompoundIdCompoundNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));*/
            return null;
        }

    }

    @PutMapping("delete/{compoundId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("compoundId") Long compoundId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrCompound temrCompound = temrCompoundRepository.getById(compoundId);
        if (temrCompound != null) {
            temrCompound.setIsDeleted(true);
            temrCompoundRepository.save(temrCompound);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
