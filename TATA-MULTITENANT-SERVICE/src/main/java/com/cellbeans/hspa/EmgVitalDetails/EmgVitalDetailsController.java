package com.cellbeans.hspa.EmgVitalDetails;

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
@RequestMapping("/emg_vital_details")
public class EmgVitalDetailsController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    EmgVitalDetailsRepository emgVitalDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody EmgVitalDetails emgVitalDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (emgVitalDetails != null) {
            emgVitalDetailsRepository.save(emgVitalDetails);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("evdId", String.valueOf(emgVitalDetails.getEvdId()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field ");
            return respMap;
        }
    }

}
