package com.cellbeans.hspa.temrclinicalform;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_clinical_form")
public class TemrClinicalFormController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrClinicalFormRepository temrClinicalFormRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrClinicalForm temrClinicalForm) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrClinicalForm.getTcfFormName() != null) {
            temrClinicalFormRepository.save(temrClinicalForm);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TemrClinicalForm> records;
        records = temrClinicalFormRepository.findByTcfIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{tcfId}")
    public TemrClinicalForm read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tcfId") Long tcfId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrClinicalForm temrClinicalForm = temrClinicalFormRepository.getById(tcfId);
        return temrClinicalForm;
    }

    @RequestMapping("update")
    public TemrClinicalForm update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrClinicalForm temrClinicalForm) {
        TenantContext.setCurrentTenant(tenantName);
        return temrClinicalFormRepository.save(temrClinicalForm);
    }

    @GetMapping
    @RequestMapping("list")
    public ResponseEntity list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tcfId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("Patient ID==>" + qString);
//        if (qString == null || qString.equals("")) {
//            return temrClinicalFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
//        else {
//            String Sqlquery = "SELECT DISTINCT formdate,tcf_form_name  FROM temr_clinical_form where tcf_patient_id = " + qString;
//            return entityManager.createNativeQuery(Sqlquery).getResultList();
//        }
        String columnName = "";
        String Query = "SELECT distinct formdate,tcf_form_name FROM temr_clinical_form where  is_active = 1 and is_deleted = 0 and tcf_patient_id = " + qString;
        columnName = "formdate,tcf_form_name";
        try {
            return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, null));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    @RequestMapping("byformid/{formId}/{formName}/{tcfPatientId}")
    public List<TemrClinicalForm> byformid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("formId") String formId, @PathVariable("formName") String formName, @PathVariable("tcfPatientId") String tcfPatientId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrClinicalFormRepository.findAllByFormdateAndTcfFormNameAndTcfPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(formId, formName, tcfPatientId);
    }

    @RequestMapping("delete/{tcfId}/{tcf_form_name}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tcfId") String tcfId, @PathVariable("tcf_form_name") String tcf_form_name) {
        TenantContext.setCurrentTenant(tenantName);
        temrClinicalFormRepository.deleteUsersByFirstName(tcfId, tcf_form_name);
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

}

