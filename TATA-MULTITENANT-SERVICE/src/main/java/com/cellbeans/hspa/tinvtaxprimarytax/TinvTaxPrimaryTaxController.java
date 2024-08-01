package com.cellbeans.hspa.tinvtaxprimarytax;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_tax_pt_id")
public class TinvTaxPrimaryTaxController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvTaxPrimaryTaxRepository tinvTaxPrimaryTaxRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvTaxPrimaryTax tinvTaxPrimaryTax) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvTaxPrimaryTax.getPtOnPurchasetaxValue() >= 0) {
            tinvTaxPrimaryTaxRepository.save(tinvTaxPrimaryTax);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{invTax}")
    public List<TinvTaxPrimaryTax> pTaxbyID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("invTax") Long invTax) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvTaxPrimaryTax> records;
        records = tinvTaxPrimaryTaxRepository.findByInvTaxTaxIdEquals(invTax);
        return records;
    /*public TinvTaxPrimaryTax read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("invTax") Long invTax) {
        TinvTaxPrimaryTax tinvTaxPrimaryTax = tinvTaxPrimaryTaxRepository.getById(invTax);
        return tinvTaxPrimaryTax;*/
    }

    @RequestMapping("update")
    public TinvTaxPrimaryTax update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvTaxPrimaryTax tinvTaxPrimaryTax) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvTaxPrimaryTaxRepository.save(tinvTaxPrimaryTax);
    }

    @PutMapping("delete/{invTax}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("invTax") Long invTax) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvTaxPrimaryTax> records;
        records = tinvTaxPrimaryTaxRepository.findByInvTaxTaxIdEquals(invTax);
        for (TinvTaxPrimaryTax tinvTaxPrimaryTax : records) {
            if (tinvTaxPrimaryTax.getInvTax() != null) {
                tinvTaxPrimaryTax.setDeleted(true);
                tinvTaxPrimaryTaxRepository.save(tinvTaxPrimaryTax);
                respMap.put("msg", "Operation Successful");
                respMap.put("success", "1");
            } else {
                respMap.put("msg", "Operation Failed");
                respMap.put("success", "0");
            }
        }
        return respMap;

    }

}
