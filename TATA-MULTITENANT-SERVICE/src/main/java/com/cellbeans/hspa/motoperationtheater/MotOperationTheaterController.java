package com.cellbeans.hspa.motoperationtheater;

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
@RequestMapping("/mot_operation_theater")
public class MotOperationTheaterController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MotOperationTheaterRepository motOperationTheaterRepository;

    @Autowired
    private MotOperationTheaterService motOperationTheaterService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotOperationTheater motOperationTheater) {
        TenantContext.setCurrentTenant(tenantName);
        if (motOperationTheater.getOtName() != null) {
            motOperationTheaterRepository.save(motOperationTheater);
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
        List<MotOperationTheater> records;
        records = motOperationTheaterRepository.findByOtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{otId}")
    public MotOperationTheater read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("otId") Long otId) {
        TenantContext.setCurrentTenant(tenantName);
        MotOperationTheater motOperationTheater = motOperationTheaterRepository.getById(otId);
        return motOperationTheater;
    }

    @RequestMapping("update")
    public MotOperationTheater update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotOperationTheater motOperationTheater) {
        TenantContext.setCurrentTenant(tenantName);
        return motOperationTheaterRepository.save(motOperationTheater);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MotOperationTheater> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "otId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return motOperationTheaterRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return motOperationTheaterRepository.findByOtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{otId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("otId") Long otId) {
        TenantContext.setCurrentTenant(tenantName);
        MotOperationTheater motOperationTheater = motOperationTheaterRepository.getById(otId);
        if (motOperationTheater != null) {
            motOperationTheater.setIsDeleted(true);
            motOperationTheaterRepository.save(motOperationTheater);
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
        List<Tuple> items = motOperationTheaterService.getMotOperationTheaterForDropdown(page, size, globalFilter);
        return items;
    }

}
            
