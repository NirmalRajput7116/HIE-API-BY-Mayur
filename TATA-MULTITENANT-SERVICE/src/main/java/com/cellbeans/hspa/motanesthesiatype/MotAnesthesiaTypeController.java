package com.cellbeans.hspa.motanesthesiatype;

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
@RequestMapping("/mot_anesthesia_type")
public class MotAnesthesiaTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MotAnesthesiaTypeRepository motAnesthesiaTypeRepository;

    @Autowired
    private MotAnesthesiaTypeService motAnesthesiaTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotAnesthesiaType motAnesthesiaType) {
        TenantContext.setCurrentTenant(tenantName);
        if (motAnesthesiaType.getAtName() != null) {
            motAnesthesiaTypeRepository.save(motAnesthesiaType);
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
        List<MotAnesthesiaType> records;
        records = motAnesthesiaTypeRepository.findByAtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{atId}")
    public MotAnesthesiaType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("atId") Long atId) {
        TenantContext.setCurrentTenant(tenantName);
        MotAnesthesiaType motAnesthesiaType = motAnesthesiaTypeRepository.getById(atId);
        return motAnesthesiaType;
    }

    @RequestMapping("update")
    public MotAnesthesiaType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotAnesthesiaType motAnesthesiaType) {
        TenantContext.setCurrentTenant(tenantName);
        return motAnesthesiaTypeRepository.save(motAnesthesiaType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MotAnesthesiaType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "atId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return motAnesthesiaTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return motAnesthesiaTypeRepository.findByAtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{atId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("atId") Long atId) {
        TenantContext.setCurrentTenant(tenantName);
        MotAnesthesiaType motAnesthesiaType = motAnesthesiaTypeRepository.getById(atId);
        if (motAnesthesiaType != null) {
            motAnesthesiaType.setIsDeleted(true);
            motAnesthesiaTypeRepository.save(motAnesthesiaType);
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
        List<Tuple> items = motAnesthesiaTypeService.getMotAnesthesiaTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
