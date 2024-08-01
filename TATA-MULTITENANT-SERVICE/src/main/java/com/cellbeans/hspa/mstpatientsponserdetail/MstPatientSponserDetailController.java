package com.cellbeans.hspa.mstpatientsponserdetail;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_patient_sponser_detail")
public class MstPatientSponserDetailController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstPatientSponserDetailRepository mstPatientSponserDetailRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPatientSponserDetail mstPatientSponserDetail) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstPatientSponserDetail.getPsdCompanyId().getCompanyName() != null) {
            mstPatientSponserDetailRepository.save(mstPatientSponserDetail);
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
        List<MstPatientSponserDetail> records;
        records = mstPatientSponserDetailRepository.findByPsdCompanyIdCompanyNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{psdId}")
    public MstPatientSponserDetail read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psdId") Long psdId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatientSponserDetail mstPatientSponserDetail = mstPatientSponserDetailRepository.getById(psdId);
        return mstPatientSponserDetail;
    }

    @RequestMapping("update")
    public MstPatientSponserDetail update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPatientSponserDetail mstPatientSponserDetail) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientSponserDetailRepository.save(mstPatientSponserDetail);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstPatientSponserDetail> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstPatientSponserDetailRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstPatientSponserDetailRepository.findByPsdCompanyIdCompanyNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{psdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psdId") Long psdId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatientSponserDetail mstPatientSponserDetail = mstPatientSponserDetailRepository.getById(psdId);
        if (mstPatientSponserDetail != null) {
            mstPatientSponserDetail.setIsDeleted(true);
            mstPatientSponserDetailRepository.save(mstPatientSponserDetail);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
