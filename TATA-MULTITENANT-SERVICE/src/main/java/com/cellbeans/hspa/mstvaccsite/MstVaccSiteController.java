package com.cellbeans.hspa.mstvaccsite;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_vacc_site")
public class MstVaccSiteController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstVaccSiteRepository mstVaccSiteRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVaccSite mstVaccSite) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstVaccSite.getVsSite() != null) {
            mstVaccSite.setVsSite(mstVaccSite.getVsSite().trim());
            MstVaccSite mstVaccSiteObject = mstVaccSiteRepository.findByVsSiteEqualsAndIsDeletedFalse(mstVaccSite.getVsSite());
            if (mstVaccSiteObject == null) {
                mstVaccSite.setIsDeleted(false);
                mstVaccSiteRepository.save(mstVaccSite);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Already Added");
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
        List<MstVaccSite> records;
        records = mstVaccSiteRepository.findByVsIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{vsId}")
    public MstVaccSite read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vsId") Long vsId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVaccSite mstVaccSite = mstVaccSiteRepository.getById(vsId);
        return mstVaccSite;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVaccSite mstVaccSite) {
        TenantContext.setCurrentTenant(tenantName);
        mstVaccSite.setVsSite(mstVaccSite.getVsSite().trim());
        MstVaccSite mstVaccSiteObject = mstVaccSiteRepository.findByVsSiteEqualsAndIsDeletedFalse(mstVaccSite.getVsSite());
        if (mstVaccSiteObject == null) {
            mstVaccSite.setIsDeleted(false);
            mstVaccSiteRepository.save(mstVaccSite);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstVaccSiteObject.getVsId() == mstVaccSite.getVsId()) {
            mstVaccSite.setIsDeleted(false);
            mstVaccSiteRepository.save(mstVaccSite);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstVaccSite> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstVaccSiteRepository.findAllByIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstVaccSiteRepository.findByVsSiteContainsAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("vaccine_site")
    public List<MstVaccSite> list(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstVaccSiteRepository.findAllByIsDeletedFalse();
    }

    @PutMapping("delete/{vsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vsId") Long vsId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVaccSite mstVaccSite = mstVaccSiteRepository.getById(vsId);
        if (mstVaccSite != null) {
            mstVaccSite.setIsDeleted(true);
            mstVaccSiteRepository.save(mstVaccSite);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
