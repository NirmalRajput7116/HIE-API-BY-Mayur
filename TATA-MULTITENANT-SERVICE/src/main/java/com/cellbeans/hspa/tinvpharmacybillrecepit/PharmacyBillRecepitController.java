package com.cellbeans.hspa.tinvpharmacybillrecepit;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_pharmacy_bill_recept")
public class PharmacyBillRecepitController {
    Map<String, String> respMap = new HashMap<>();
    @Autowired
    PharmacyBillReceptRepository pharmacyBillReceptRepository;

    @RequestMapping("create")
    public Map<String, String> onCreareAddListRecepit(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<PharmacyBillRecepit> pharmacyBillRecepits) {
        TenantContext.setCurrentTenant(tenantName);
        if (pharmacyBillRecepits.size() > 0) {
            pharmacyBillReceptRepository.saveAll(pharmacyBillRecepits);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("recepitlist/{psid}")
    public List<PharmacyBillRecepit> getPharmacyBillrecepitByPsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psid") Long psid) {
        TenantContext.setCurrentTenant(tenantName);
        return pharmacyBillReceptRepository.PbrPsIdPsIdAndIsActiveTrueAndIsDeletedFalse(psid);
    }

}
