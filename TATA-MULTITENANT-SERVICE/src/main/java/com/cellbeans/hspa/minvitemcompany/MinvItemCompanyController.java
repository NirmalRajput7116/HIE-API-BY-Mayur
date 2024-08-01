package com.cellbeans.hspa.minvitemcompany;

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
@RequestMapping("/minv_item_company")
public class MinvItemCompanyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MinvItemCompanyRepository minvItemCompanyRepository;

    @Autowired
    private MinvItemCompanyService minvItemCompanyService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvItemCompany minvItemCompany) {
        TenantContext.setCurrentTenant(tenantName);
        if (minvItemCompany.getIcName() != null) {
            minvItemCompanyRepository.save(minvItemCompany);
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
        List<MinvItemCompany> records;
        records = minvItemCompanyRepository.findByIcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{icId}")
    public MinvItemCompany read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvItemCompany minvItemCompany = minvItemCompanyRepository.getById(icId);
        return minvItemCompany;
    }

    @RequestMapping("update")
    public MinvItemCompany update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvItemCompany minvItemCompany) {
        TenantContext.setCurrentTenant(tenantName);
        return minvItemCompanyRepository.save(minvItemCompany);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MinvItemCompany> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "icId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return minvItemCompanyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return minvItemCompanyRepository.findByIcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{icId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvItemCompany minvItemCompany = minvItemCompanyRepository.getById(icId);
        if (minvItemCompany != null) {
            minvItemCompany.setIsDeleted(true);
            minvItemCompanyRepository.save(minvItemCompany);
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
        List<Tuple> items = minvItemCompanyService.getMinvItemCompanyForDropdown(page, size, globalFilter);
        return items;
    }

}
            
