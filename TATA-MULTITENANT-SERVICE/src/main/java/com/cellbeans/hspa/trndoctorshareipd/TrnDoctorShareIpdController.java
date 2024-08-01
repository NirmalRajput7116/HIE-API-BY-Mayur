package com.cellbeans.hspa.trndoctorshareipd;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_doctor_share_ipd")
public class TrnDoctorShareIpdController {

    Map<String, String> respMap = new HashMap<String, String>();

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TrnDoctorShareIpdRepository mbillDoctorShareRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDoctorShareIpd trnDoctorShare) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnDoctorShare != null) {
            mbillDoctorShareRepository.save(trnDoctorShare);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("update")
    public TrnDoctorShareIpd update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDoctorShareIpd trnDoctorShareIpd) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillDoctorShareRepository.save(trnDoctorShareIpd);
    }

    @RequestMapping("byid/{dsiBillId}")
    public TrnDoctorShareIpd read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsiBillId") Long dsiBillId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnDoctorShareIpd trnDoctorShare = mbillDoctorShareRepository.findFirstByDsiBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(dsiBillId);
        return trnDoctorShare;
    }
}
