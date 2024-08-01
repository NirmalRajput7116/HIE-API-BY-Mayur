package com.cellbeans.hspa.mstautocharges;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mst_auto_charges")
public class MstAutoChargesController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstAutoChargesRepository mstAutoChargesRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAutoCharges mstAutoCharges) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstAutoCharges.getAcServiceId() != null) {
            mstAutoChargesRepository.save(mstAutoCharges);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{acId}")
    public MstAutoCharges read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("acId") Long acId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAutoCharges mstAutoCharges = mstAutoChargesRepository.getById(acId);
        return mstAutoCharges;
    }

    @RequestMapping("update")
    public MstAutoCharges update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAutoCharges mstAutoCharges) {
        TenantContext.setCurrentTenant(tenantName);
        return mstAutoChargesRepository.save(mstAutoCharges);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstAutoCharges> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "acId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstAutoChargesRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstAutoChargesRepository.findByAcUnitIdUnitNameContainsOrAcServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{acId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("acId") Long acId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAutoCharges mstAutoCharges = mstAutoChargesRepository.getById(acId);
        if (mstAutoCharges != null) {
            mstAutoCharges.setDeleted(true);
            mstAutoChargesRepository.save(mstAutoCharges);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
