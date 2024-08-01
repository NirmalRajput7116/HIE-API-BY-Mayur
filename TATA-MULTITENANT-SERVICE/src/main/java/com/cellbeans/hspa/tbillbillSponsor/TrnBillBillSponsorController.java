package com.cellbeans.hspa.tbillbillSponsor;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("trn_bill_bill_sponsor")
public class TrnBillBillSponsorController {

    @Autowired
    TrnBillBillSponsorRepository trnBillBillSponsorRepository;
    Map<String, String> respMap = new HashMap<String, String>();

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnBillBillSponsor> trnBillBillSponsorList) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnBillBillSponsorList.size() > 0) {
            if ((trnBillBillSponsorList.get(0).getBbsBillId().getBillId() > 0)) {
                trnBillBillSponsorRepository.saveAll(trnBillBillSponsorList);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
                return respMap;
            }
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("listbybillid")
    public List<TrnBillBillSponsor> listByBillId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "billId", required = false, defaultValue = "1") Long billId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBillBillSponsorRepository.findByBbsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(billId);
    }

}