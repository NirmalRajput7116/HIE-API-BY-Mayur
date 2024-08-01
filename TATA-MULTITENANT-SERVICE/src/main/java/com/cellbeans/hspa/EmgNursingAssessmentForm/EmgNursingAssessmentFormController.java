package com.cellbeans.hspa.EmgNursingAssessmentForm;

import com.cellbeans.hspa.EmgPatientList.EmgPatientList;
import com.cellbeans.hspa.EmgPatientList.EmgPatientListRepository;
import com.cellbeans.hspa.TenantContext;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vijay Patil
 */

@RestController
@RequestMapping("/emg_nursing_assessment_form")
public class EmgNursingAssessmentFormController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    EmgNursingAssessmentFormRepository emgNursingAssessmentFormRepository;

    @Autowired
    EmgPatientListRepository emgPatientListRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody EmgNursingAssessmentForm emgNursingAssessmentForm) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("emgTriageForm :" + emgNursingAssessmentForm);
        if (emgNursingAssessmentForm != null) {
            emgNursingAssessmentFormRepository.save(emgNursingAssessmentForm);
            EmgPatientList obj = emgPatientListRepository.findAllByEplVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(emgNursingAssessmentForm.getEnaVisitId()));
            obj.setEplNursingAssessmentFormId(emgNursingAssessmentForm);
            emgPatientListRepository.save(obj);
            //     System.out.println("hello ob 0:"+obj);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field ");
            return respMap;
        }
    }

    @RequestMapping("nursingassessmentbyermrvisitid")
    public List<EmgNursingAssessmentForm> getNursingAssessmentByErMrvVsitid(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject obj) {
        TenantContext.setCurrentTenant(tenantName);
        String patientErNo = String.valueOf(obj.get("patientErNo"));
        String patientMrNo = String.valueOf(obj.get("patientMrNo"));
        String visitId = String.valueOf(obj.get("visitId"));
        //      System.out.println("patientErNo :" + patientErNo + " patientMrNo :" + patientMrNo + " visitId :" + visitId);
        //  return emgNursingAssessmentFormRepository.findByEtfPatientMrNoAndEtfPatientErNoAndEtfVisitIdAndIsActiveTrueAndIsDeletedFalse(patientMrNo, patientErNo, visitId);
        return emgNursingAssessmentFormRepository.findByEnaPatientErNoAndEnaPatientMrNoAndEnaVisitIdAndIsActiveTrueAndIsDeletedFalse(patientErNo, patientMrNo, visitId);
    }

}
