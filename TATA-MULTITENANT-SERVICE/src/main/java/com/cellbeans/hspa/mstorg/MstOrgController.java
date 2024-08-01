package com.cellbeans.hspa.mstorg;

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
@RequestMapping("/mst_org")
public class MstOrgController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstOrgRepository mstOrgRepository;

    @Autowired
    private MstOrgService mstOrgService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstOrg mstOrg) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstOrg.getOrgName() != null) {
            mstOrg.setOrgName(mstOrg.getOrgName().trim());
            MstOrg mOrgObj = mstOrgRepository.findByOrgNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstOrg.getOrgName());
            if (mOrgObj == null) {
                mstOrgRepository.save(mstOrg);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed Already Added");
                return respMap;
            }
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
        List<MstOrg> records;
        records = mstOrgRepository.findByOrgNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{orgId}")
    public MstOrg read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("orgId") Long orgId) {
        TenantContext.setCurrentTenant(tenantName);
        MstOrg mstOrg = mstOrgRepository.getById(orgId);
        return mstOrg;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstOrg mstOrg) {
        TenantContext.setCurrentTenant(tenantName);
        //        return mstOrgRepository.save(mstOrg);
        if (mstOrg.getOrgName() != null) {
            mstOrg.setOrgName(mstOrg.getOrgName().trim());
            MstOrg mOrgObj = mstOrgRepository.findByOrgNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstOrg.getOrgName());
            if (mOrgObj == null) {
                mstOrgRepository.save(mstOrg);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else if (mOrgObj.getOrgId() == mstOrg.getOrgId()) {
                mstOrgRepository.save(mstOrg);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed Already Added");
                return respMap;
            }
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstOrg> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "orgId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstOrgRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByOrgNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstOrgRepository.findByOrgNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByOrgNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{orgId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("orgId") Long orgId) {
        TenantContext.setCurrentTenant(tenantName);
        MstOrg mstOrg = mstOrgRepository.getById(orgId);
        if (mstOrg != null) {
            mstOrg.setIsDeleted(true);
            mstOrgRepository.save(mstOrg);
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
        List<Tuple> items = mstOrgService.getMstOrgForDropdown(page, size, globalFilter);
        return items;
    }

}
            
