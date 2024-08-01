package com.cellbeans.hspa.minvmodescrapsale;

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
@RequestMapping("/minv_mode_scrapsale")
public class MinvModeScrapsaleController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MinvModeScrapsaleRepository minvModeScrapsaleRepository;

    @Autowired
    private MinvModeScrapsaleService minvModeScrapsaleService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvModeScrapsale minvModeScrapsale) {
        TenantContext.setCurrentTenant(tenantName);
        if (minvModeScrapsale.getMcName() != null) {
            minvModeScrapsaleRepository.save(minvModeScrapsale);
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
        List<MinvModeScrapsale> records;
        records = minvModeScrapsaleRepository.findByMcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{mcId}")
    public MinvModeScrapsale read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mcId") Long mcId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvModeScrapsale minvModeScrapsale = minvModeScrapsaleRepository.getById(mcId);
        return minvModeScrapsale;
    }

    @RequestMapping("update")
    public MinvModeScrapsale update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvModeScrapsale minvModeScrapsale) {
        TenantContext.setCurrentTenant(tenantName);
        return minvModeScrapsaleRepository.save(minvModeScrapsale);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MinvModeScrapsale> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "mcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return minvModeScrapsaleRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return minvModeScrapsaleRepository.findByMcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{mcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mcId") Long mcId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvModeScrapsale minvModeScrapsale = minvModeScrapsaleRepository.getById(mcId);
        if (minvModeScrapsale != null) {
            minvModeScrapsale.setIsDeleted(true);
            minvModeScrapsaleRepository.save(minvModeScrapsale);
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
        List<Tuple> items = minvModeScrapsaleService.getMinvModeScrapsaleForDropdown(page, size, globalFilter);
        return items;
    }

}
            
