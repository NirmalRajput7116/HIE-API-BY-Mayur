package com.cellbeans.hspa.EmgPatientList;

import com.cellbeans.hspa.EmgNursingAssessmentForm.EmgNursingAssessmentFormRepository;
import com.cellbeans.hspa.TenantContext;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vijay Patil
 */

@RestController
@RequestMapping("/emg_patient_list")
public class EmgPatientListController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    EmgPatientListRepository emgPatientListRepository;

    @Autowired
    EmgNursingAssessmentFormRepository emgNursingAssessmentFormRepository;

    @GetMapping
    @RequestMapping("list")
    public Iterable<EmgPatientList> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                         @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                         @RequestParam(value = "qString", required = false) String qString,
                                         @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                         @RequestParam(value = "col", required = false, defaultValue = "eplId") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return emgPatientListRepository.findAllByEplVisitIdVisitPatientIdIsEmergencyTrueAndEplVisitIdVisitRegistrationSourceAndIsActiveTrueAndIsDeletedFalseAndIsDischargedFalseAndIsCancelledFalse(2, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return emgPatientListRepository.findAllByEplVisitIdVisitPatientIdIsEmergencyTrueAndEplVisitIdVisitRegistrationSourceAndIsActiveTrueAndIsDeletedFalseAndIsDischargedFalseAndIsCancelledFalse(2, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("erdischargelist")
    public Iterable<EmgPatientList> Dischargelist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                  @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                  @RequestParam(value = "qString", required = false) String qString,
                                                  @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                  @RequestParam(value = "col", required = false, defaultValue = "epl_id") String col,
                                                  @RequestParam(value = "type", required = false, defaultValue = "type") int type) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        if (type == 1 && (qString != null && (qString.equals("") != true))) {
            return emgPatientListRepository.findAllByPatientName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (type == 2 && (qString != null && (qString.equals("") != true))) {
            return emgPatientListRepository.findAllByPatientERNo(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (type == 7 && (qString != null && (qString.equals("") != true))) {
            String[] parts = qString.split("_");
            String fromDate = parts[0];
            String toDate = parts[1];
            return emgPatientListRepository.findAllByDate(fromDate, toDate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 8 && (qString != null && (qString.equals("") != true))) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            String currDate = sd.format(new Date());
            return emgPatientListRepository.findAllByDate(currDate, currDate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return emgPatientListRepository.findAll(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @GetMapping
    @RequestMapping("ercancellist")
    public Iterable<EmgPatientList> Cancellist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                               @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                               @RequestParam(value = "qString", required = false) String qString,
                                               @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                               @RequestParam(value = "col", required = false, defaultValue = "epl_id") String col,
                                               @RequestParam(value = "type", required = false, defaultValue = "type") int type) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        if (type == 1 && (qString != null && (qString.equals("") != true))) {
            return emgPatientListRepository.findAllByPatientName1(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (type == 2 && (qString != null && (qString.equals("") != true))) {
            return emgPatientListRepository.findAllByPatientERNo1(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (type == 7 && (qString != null && (qString.equals("") != true))) {
            String[] parts = qString.split("_");
            String fromDate = parts[0];
            String toDate = parts[1];
            return emgPatientListRepository.findAllByDate1(fromDate, toDate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 8 && (qString != null && (qString.equals("") != true))) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            String currDate = sd.format(new Date());
            return emgPatientListRepository.findAllByDate1(currDate, currDate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return emgPatientListRepository.findAll1(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    /* @GetMapping
     @RequestMapping("erdischargelist")
     public Iterable<EmgPatientList> Dischargelist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "admissionDischargeDate") String col) {

        *//* if (type == 0) {
           // return emgPatientListRepository.findAllByAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
        if (type == 1) {
          //  return emgPatientListRepository.findByAdmissionStatusIsTrueAndAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 2) {
         //   return emgPatientListRepository.findByAdmissionStatusIsTrueAndAdmissionIpdNoContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }*//*

       return emgPatientListRepository.findAllByEplVisitIdVisitPatientIdIsEmergencyTrueAndEplVisitIdVisitRegistrationSourceAndIsActiveTrueAndIsDeletedFalseAndIsDischargedTrue(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }
*/
    @GetMapping
    @RequestMapping("getEmergecyPatientById/{eplId}")
    public EmgPatientList getPatientByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("eplId") Long eplId) {
        TenantContext.setCurrentTenant(tenantName);
        return emgPatientListRepository.findByEplId(eplId);

    }

    @RequestMapping("update")
    public int patientUpdate(@RequestHeader("X-tenantId") String tenantName, @RequestBody EmgPatientList emgPatientList) {
        TenantContext.setCurrentTenant(tenantName);
        emgPatientListRepository.save(emgPatientList);
        return 1;
    }

    @RequestMapping("byerandmrno")
    public List<EmgPatientList> patientByErAndMrNo(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject obj) {
        TenantContext.setCurrentTenant(tenantName);
        String patientErNo = String.valueOf(obj.get("patientErNo"));
        String patientMrNo = String.valueOf(obj.get("patientMrNo"));
        System.out.println("patientErNo :" + patientErNo + " patientMrNo :" + patientMrNo);
        return emgPatientListRepository.findAllByEplVisitIdVisitPatientIdPatientMrNoAndEplVisitIdVisitPatientIdPatientErNoAndIsActiveTrueAndIsDeletedFalse(patientMrNo, patientErNo);
    }

    @RequestMapping("byermrnovisitid")
    public List<EmgPatientList> patientByErMrNoAndVisitId(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject obj) {
        TenantContext.setCurrentTenant(tenantName);
        String patientErNo = String.valueOf(obj.get("patientErNo"));
        String patientMrNo = String.valueOf(obj.get("patientMrNo"));
        Long visitId = Long.parseLong(String.valueOf(obj.get("visitId")));
        System.out.println("patientErNo :" + patientErNo + " patientMrNo :" + patientMrNo + " visitId :" + visitId);
        return emgPatientListRepository.findAllByEplVisitIdVisitIdAndEplVisitIdVisitPatientIdPatientMrNoAndEplVisitIdVisitPatientIdPatientErNoAndIsActiveTrueAndIsDeletedFalse(visitId, patientMrNo, patientErNo);
    }

    @GetMapping
    @RequestMapping("getpatientbyid/{visitid}")
    public EmgPatientList getPatientById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitid") Long visitid) {
        TenantContext.setCurrentTenant(tenantName);
        if (visitid != null || !visitid.equals("")) {
            return emgPatientListRepository.findAllByEplVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitid);
        } else {
            return null;
        }

    }

    @GetMapping
    @RequestMapping("checkIsAlreadyInEmgPatient/{patientId}")
    public Boolean checkIsAlreadyInEmgPatient(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        EmgPatientList EmgPatientListObj = emgPatientListRepository.findAllByPatientId(patientId);
        if (EmgPatientListObj != null) {
            return true;
        } else {
            return false;
        }
    }

}
