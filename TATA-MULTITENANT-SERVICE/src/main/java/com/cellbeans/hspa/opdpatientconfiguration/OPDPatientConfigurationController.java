package com.cellbeans.hspa.opdpatientconfiguration;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/opdpatientconfig")
public class OPDPatientConfigurationController implements Serializable {

    Map<String, String> respMap = new HashMap<String, String>();
    boolean flag = false;
    @Autowired
    OPDPatientConfigurationRepository opdPatientConfigurationRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("saveConfig")
    public Map<String, String> saveConfig(@RequestHeader("X-tenantId") String tenantName, @RequestBody OPDPatientConfiguration objOPDPatientConfiguration) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            opdPatientConfigurationRepository.save(objOPDPatientConfiguration);
            respMap.put("msg1", "Operation Successful");
            respMap.put("success1", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("/listByUnitId")
    public Iterable<OPDPatientConfiguration> read(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "opdPatientConfigId") String col, @RequestParam(value = "unitId", required = false) String unitId) {
        TenantContext.setCurrentTenant(tenantName);
        //        System.out.println("===================Search before=============");
//        System.out.println("===================Search else=============");
//        System.out.println(qString);
        if (qString == null || qString.equals("")) {
//            System.out.println("===================Search if=============");
            return opdPatientConfigurationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return opdPatientConfigurationRepository.findByConfigUnitIdUnitNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @RequestMapping("listbyConfigId/{opdPatientConfigId}")
    public OPDPatientConfiguration read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdPatientConfigId") Long opdPatientConfigId) {
        TenantContext.setCurrentTenant(tenantName);
        OPDPatientConfiguration objOPDPatientConfiguration = opdPatientConfigurationRepository.getById(opdPatientConfigId);
        return objOPDPatientConfiguration;
    }

    @PutMapping("checkById/{unitId}")
    public Map<String, String> checkById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            String Query = "select p from OPDPatientConfiguration p where p.configUnitId.unitId=" + unitId;
            OPDPatientConfiguration objOPDPatientConfiguration = entityManager.createQuery(Query, OPDPatientConfiguration.class).getSingleResult();
            if (objOPDPatientConfiguration != null) {
                respMap.put("msg", "Operation Successful");
                respMap.put("success", "1");
            }
        } catch (Exception e) {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("delete/{opdPatientConfigId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdPatientConfigId") Long opdPatientConfigId) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            opdPatientConfigurationRepository.deleteById(opdPatientConfigId);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } catch (Exception e) {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("recordByUnitId/{unitId}")
    public OPDPatientConfiguration getRecordByUnitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("unitid :" + unitId);
        OPDPatientConfiguration objOPDPatientConfiguration = new OPDPatientConfiguration();
        try {
            String Query = "select p from OPDPatientConfiguration p where p.configUnitId.unitId=" + unitId;
            objOPDPatientConfiguration = entityManager.createQuery(Query, OPDPatientConfiguration.class).getSingleResult();
            return objOPDPatientConfiguration;
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

    }

}
