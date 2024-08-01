package com.cellbeans.hspa.tinvitemdisease;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_item_disease")
public class TinvItemDiseaseController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvItemDiseaseRepository tinvItemDiseaseRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvItemDisease> tinvItemDisease) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvItemDisease != null) {
            tinvItemDiseaseRepository.saveAll(tinvItemDisease);
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
        List<TinvItemDisease> records;
        records = tinvItemDiseaseRepository.findByIdItemIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{idId}")
    public TinvItemDisease read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("idId") Long idId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemDisease tinvItemDisease = tinvItemDiseaseRepository.getById(idId);
        return tinvItemDisease;
    }

    @RequestMapping("update")
    public TinvItemDisease update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvItemDisease tinvItemDisease) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvItemDiseaseRepository.save(tinvItemDisease);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvItemDisease> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "idId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvItemDiseaseRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvItemDiseaseRepository.findByidItemIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{idId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("idId") Long idId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemDisease tinvItemDisease = tinvItemDiseaseRepository.getById(idId);
        if (tinvItemDisease != null) {
            tinvItemDisease.setIsDeleted(true);
            tinvItemDiseaseRepository.save(tinvItemDisease);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
