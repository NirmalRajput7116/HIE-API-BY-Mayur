package com.cellbeans.hspa.tinvenquirysupplier;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_enquiry_supplier")
public class TinvEnquirySupplierController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvEnquirySupplierRepositry tinvEnquirySupplierRepositry;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvEnquirySupplier> tinvEnquirySupplier) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvEnquirySupplier.size() != 0) {
            tinvEnquirySupplierRepositry.saveAll(tinvEnquirySupplier);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("list/{id}")
    public List<TinvEnquirySupplier> grtSupplierList(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvEnquirySupplierRepositry.findAllByEsPieIdPieId(id);
    }

}
