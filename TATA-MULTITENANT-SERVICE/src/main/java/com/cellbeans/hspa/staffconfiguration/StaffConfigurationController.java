package com.cellbeans.hspa.staffconfiguration;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/staff_configuration")
public class StaffConfigurationController implements Serializable {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    StaffConfigurationRepository staffConfigurationRepository;

    Map<String, Object> respMap1 = new HashMap<String, Object>();
    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("saveConfig")
    public Map<String, String> saveConfig(@RequestHeader("X-tenantId") String tenantName, @RequestBody StaffConfiguration staffConfiguration) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            staffConfigurationRepository.save(staffConfiguration);
            respMap.put("msg1", "Operation Successful");
            respMap.put("success", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMap;
    }

    @PutMapping("checkById/{unitId}/{staffId}")
    public Map<String, Object> checkById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") long unitId, @PathVariable("staffId") long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respMap1 = new HashMap<String, Object>();
        try {
            //String Query = "select s from StaffConfiguration s where s.scUnitId=" + unitId + " and s.scStaffId=" + staffId;
            StaffConfiguration staffConfiguration = staffConfigurationRepository.findByScUnitIdUnitIdAndScStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(unitId, staffId);
            // StaffConfiguration staffConfiguration = entityManager.createQuery(Query, StaffConfiguration.class).getSingleResult();
            if (staffConfiguration != null) {
                respMap1.put("content", staffConfiguration);
                respMap1.put("msg", "Operation Successful");
                respMap1.put("success", "1");
            } else {
                respMap1.put("msg", "No match");
                respMap1.put("success", "2");
            }

        } catch (Exception e) {
            System.out.println("exception---------------------- " + e);
            respMap1.put("msg", "Operation Failed");
            respMap1.put("success", "0");
        }
        return respMap1;
    }

    @RequestMapping("byid/{staffId}")
    public Map<String, Object> read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respMap1 = new HashMap<String, Object>();
        StaffConfiguration staffConfiguration = staffConfigurationRepository.findByScStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(staffId);
        if (staffConfiguration != null) {
            respMap1.put("content", staffConfiguration);
            respMap1.put("success", "1");
        } else {
            respMap1.put("content", "");
            respMap1.put("success", "0");
        }
        return respMap1;
    }

}
