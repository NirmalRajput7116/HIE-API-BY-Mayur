package com.cellbeans.hspa.EmgBodyAssessment;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vijay Patil
 */

@RestController
@RequestMapping("/emg_body_assessment")
public class EmgBodyAssessmentController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    EmgBodyAssessmentRepository emgTriageFormRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody EmgBodyAssessment emgBodyAssessment) {
        TenantContext.setCurrentTenant(tenantName);
        if (emgBodyAssessment != null) {
            emgTriageFormRepository.save(emgBodyAssessment);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully ");
            respMap.put("ebaId", String.valueOf(emgBodyAssessment.getEbaId()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

}
