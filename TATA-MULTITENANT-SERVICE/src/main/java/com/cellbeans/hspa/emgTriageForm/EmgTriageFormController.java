package com.cellbeans.hspa.emgTriageForm;

import com.cellbeans.hspa.EmgPatientList.EmgPatientList;
import com.cellbeans.hspa.EmgPatientList.EmgPatientListRepository;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.temrvital.TemrVital;
import com.cellbeans.hspa.temrvital.TemrVitalRepository;
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
@RequestMapping("/emg_triage_form")
public class EmgTriageFormController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    EmgTriageFormRepository emgTriageFormRepository;
    @Autowired
    EmgPatientListRepository emgPatientListRepository;
    @Autowired
    TemrVitalRepository temrVitalRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody EmgTriageForm emgTriageForm) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVital tvTemp = new TemrVital();
        TemrVital tv = new TemrVital();
        if (emgTriageForm != null) {
            emgTriageFormRepository.save(emgTriageForm);
            EmgPatientList obj = emgPatientListRepository.findAllByEplVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(emgTriageForm.getEtfVisitId()));
            obj.setEplTriageFormId(emgTriageForm);
            emgPatientListRepository.save(obj);
            //    System.out.println("hello ob 0:"+obj);
            MstVisit v = new MstVisit();
            v.setVisitId(Long.parseLong(emgTriageForm.getEtfVisitId()));
            tv.setVitalVisitId(v);
            tv.setIsDeleted(false);
            tv.setIsActive(true);
            MstStaff s = new MstStaff();
            s.setStaffId(Long.parseLong(emgTriageForm.getCreatedBy()));
            tv.setVitalStaffId(s);
            tv.setVitalBodyTemp(emgTriageForm.getEtfVitalDetailsId().getEvdBodyTemp());
            tv.setVitalDiaBp(emgTriageForm.getEtfVitalDetailsId().getEvdDiaBp());
            tv.setVitalPulse(emgTriageForm.getEtfVitalDetailsId().getEvdPulse());
            tv.setVitalSpo2(emgTriageForm.getEtfVitalDetailsId().getEvdSpo2());
            tv.setVitalSysBp(emgTriageForm.getEtfVitalDetailsId().getEvdSysBp());
            tv.setVitalWeight(emgTriageForm.getEtfVitalDetailsId().getEvdWeight());
            tv.setVitalBmi(emgTriageForm.getEtfVitalDetailsId().getEvdBmi());
            tv.setVitalHeight(emgTriageForm.getEtfVitalDetailsId().getEvdHeight());
            // tv.setVitalTimelineId();
            // tv.setVitalMap();
            // tv.setVitalBloodGlucose();
            // tv.setVitalFat();
            // tv.setVitalHimoglobin();
            // tv.setVitalMuscleMass();
            // tv.setVitalPainScore( );
            // tv.setVitalStaffId();
            // tv.setVitalRespirationRate();
            // tv.setVitalRemark();
            if (emgTriageForm.getEtfEmrVitalDetailsId() != null) {
                tvTemp = temrVitalRepository.getById(Long.parseLong(emgTriageForm.getEtfEmrVitalDetailsId()));
                if (tvTemp != null) {
                    tv.setVitalId(Long.parseLong(emgTriageForm.getEtfEmrVitalDetailsId()));
                    temrVitalRepository.save(tv);
                } else {
                    temrVitalRepository.save(tv);
                    emgTriageForm.setEtfEmrVitalDetailsId(String.valueOf(tv.getVitalId()));
                    emgTriageFormRepository.save(emgTriageForm);
                    EmgPatientList ob = emgPatientListRepository.findAllByEplVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(emgTriageForm.getEtfVisitId()));
                    ob.setEplTriageFormId(emgTriageForm);
                    emgPatientListRepository.save(ob);
                    //        System.out.println("hello ob 1:"+ob);
                }
            } else {
                temrVitalRepository.save(tv);
                emgTriageForm.setEtfEmrVitalDetailsId(String.valueOf(tv.getVitalId()));
                emgTriageFormRepository.save(emgTriageForm);
                EmgPatientList ob = emgPatientListRepository.findAllByEplVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(emgTriageForm.getEtfVisitId()));
                ob.setEplTriageFormId(emgTriageForm);
                emgPatientListRepository.save(ob);
                //        System.out.println("hello ob 2:"+ob);
            }
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully ");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field ");
            return respMap;
        }
    }

    @RequestMapping("triagebyermrvisitid")
    public List<EmgTriageForm> getTriageByErMrVisitId(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject obj) {
        TenantContext.setCurrentTenant(tenantName);
        String patientErNo = String.valueOf(obj.get("patientErNo"));
        String patientMrNo = String.valueOf(obj.get("patientMrNo"));
        String visitId = String.valueOf(obj.get("visitId"));
        // System.out.println("patientErNo :" + patientErNo + " patientMrNo :" + patientMrNo + " visitId :" + visitId);
        return emgTriageFormRepository.findByEtfPatientMrNoAndEtfPatientErNoAndEtfVisitIdAndIsActiveTrueAndIsDeletedFalse(patientMrNo, patientErNo, visitId);
    }

    @RequestMapping("triagebyvisitid")
    public EmgTriageForm getTriageByVisitId(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject obj) {
        TenantContext.setCurrentTenant(tenantName);
        String visitId = String.valueOf(obj.get("visitId"));
        // System.out.println("patientErNo :" + patientErNo + " patientMrNo :" + patientMrNo + " visitId :" + visitId);
        return emgTriageFormRepository.findByEtfVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(visitId);
    }

}
