package com.cellbeans.hspa.trndischargesummary;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_discharge_summary")
public class TrnDischargeSummaryController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TrnDischargeSummaryRepository trnDischargeSummaryRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDischargeSummary trnDischargeSummary) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnDischargeSummary.getDsAdmissionID().getAdmissionId() != 0L) {
            trnDischargeSummaryRepository.save(trnDischargeSummary);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("admissionbyid/{admissionId}")
    public List<TrnDischargeSummary> admissionbyid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        String Queary = "Select dsa from TrnDischargeSummary dsa where dsa.dsAdmissionID.admissionId=" + admissionId;
        return entityManager.createQuery(Queary).getResultList();
    }

}
