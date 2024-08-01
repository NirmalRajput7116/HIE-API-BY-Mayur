package com.cellbeans.hspa.mstassociatedcompany;

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
@RequestMapping("/mst_associated_company")
public class MstAssociatedCompanyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstAssociatedCompanyRepository mstAssociatedCompanyRepository;

    @Autowired
    private MstAssociatedCompanyService mstAssociatedCompanyService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAssociatedCompany mstAssociatedCompany) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstAssociatedCompany.getAcName() != null) {
            mstAssociatedCompanyRepository.save(mstAssociatedCompany);
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
        List<MstAssociatedCompany> records;
        records = mstAssociatedCompanyRepository.findByAcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{acId}")
    public MstAssociatedCompany read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("acId") Long acId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAssociatedCompany mstAssociatedCompany = mstAssociatedCompanyRepository.getById(acId);
        return mstAssociatedCompany;
    }

    @RequestMapping("update")
    public MstAssociatedCompany update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAssociatedCompany mstAssociatedCompany) {
        TenantContext.setCurrentTenant(tenantName);
        return mstAssociatedCompanyRepository.save(mstAssociatedCompany);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstAssociatedCompany> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "acId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstAssociatedCompanyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstAssociatedCompanyRepository.findByAcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{acId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("acId") Long acId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAssociatedCompany mstAssociatedCompany = mstAssociatedCompanyRepository.getById(acId);
        if (mstAssociatedCompany != null) {
            mstAssociatedCompany.setIsDeleted(true);
            mstAssociatedCompanyRepository.save(mstAssociatedCompany);
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
        List<Tuple> items = mstAssociatedCompanyService.getMstAssociatedCompanyForDropdown(page, size, globalFilter);
        return items;
    }

}
            
