package com.cellbeans.hspa.tinvpharmacyreturnbillrecepit;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_pharmacy_bill_return_receipt")
public class PharmacyReturnBillRecepitController {

    Map<String, String> respMap = new HashMap<>();
    @Autowired
    PharmacyReturnBillRecepitRepository pharmacyReturnBillRecepitRepository;

    @RequestMapping("create")
    public Map<String, String> onCreareAddListRecepit(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<PharmacyReturnBillRecepit> pharmacyReturnBillRecepit) {
        TenantContext.setCurrentTenant(tenantName);
        if (pharmacyReturnBillRecepit.size() > 0) {
            pharmacyReturnBillRecepitRepository.saveAll(pharmacyReturnBillRecepit);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("returnRecepitlist/{psid}")
    public List<PharmacyReturnBillRecepit> getPharmacyBillrecepitByPsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psid") Long psid) {
        TenantContext.setCurrentTenant(tenantName);
        return pharmacyReturnBillRecepitRepository.PbrrPsIdPsIdAndIsActiveTrueAndIsDeletedFalse(psid);
    }

    //By Neha
    @RequestMapping("byid/{pbrrId}")
    public PharmacyReturnBillRecepit getPharmacyBillrecepitBypbrrId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pbrrId") Long pbrrId) {
        TenantContext.setCurrentTenant(tenantName);
        PharmacyReturnBillRecepit PharmacyReturnBillRecepit = pharmacyReturnBillRecepitRepository.getById(pbrrId);
        return PharmacyReturnBillRecepit;
    }

}
