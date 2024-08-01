package com.cellbeans.hspa.tinvitemtax;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_item_tax")
public class TinvItemTaxController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvItemTaxRepository tinvItemTaxRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvItemTax> tinvItemTax) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvItemTax != null) {
            tinvItemTaxRepository.saveAll(tinvItemTax);
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
        List<TinvItemTax> records;
        records = tinvItemTaxRepository.findByItItemIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{itId}")
    public TinvItemTax read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itId") Long itId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemTax tinvItemTax = tinvItemTaxRepository.getById(itId);
        return tinvItemTax;
    }

    @RequestMapping("update")
    public TinvItemTax update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvItemTax tinvItemTax) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvItemTaxRepository.save(tinvItemTax);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvItemTax> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "itId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvItemTaxRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvItemTaxRepository.findByItNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{itId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itId") Long itId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemTax tinvItemTax = tinvItemTaxRepository.getById(itId);
        if (tinvItemTax != null) {
            tinvItemTax.setIsDeleted(true);
            tinvItemTaxRepository.save(tinvItemTax);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
