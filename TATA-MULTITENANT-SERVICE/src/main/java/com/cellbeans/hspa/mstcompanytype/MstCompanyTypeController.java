package com.cellbeans.hspa.mstcompanytype;

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
@RequestMapping("/mst_company_type")
public class MstCompanyTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstCompanyTypeRepository mstCompanyTypeRepository;

    @Autowired
    private MstCompanyTypeService mstCompanyTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCompanyType mstCompanyType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstCompanyType.getCtName() != null) {
            mstCompanyTypeRepository.save(mstCompanyType);
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
        List<MstCompanyType> records;
        records = mstCompanyTypeRepository.findByCtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ctId}")
    public MstCompanyType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ctId") Long ctId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCompanyType mstCompanyType = mstCompanyTypeRepository.getById(ctId);
        return mstCompanyType;
    }

    @RequestMapping("update")
    public MstCompanyType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCompanyType mstCompanyType) {
        TenantContext.setCurrentTenant(tenantName);
        return mstCompanyTypeRepository.save(mstCompanyType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstCompanyType> list(@RequestHeader("X-tenantId") String tenantName,
                                         @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                         @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                         @RequestParam(value = "qString", required = false) String qString,
                                         @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort,
                                         @RequestParam(value = "col", required = false, defaultValue = "ctId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstCompanyTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstCompanyTypeRepository.findByCtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ctId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ctId") Long ctId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCompanyType mstCompanyType = mstCompanyTypeRepository.getById(ctId);
        if (mstCompanyType != null) {
            mstCompanyType.setIsDeleted(true);
            mstCompanyTypeRepository.save(mstCompanyType);
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
        List<Tuple> items = mstCompanyTypeService.getMstCompanyTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
