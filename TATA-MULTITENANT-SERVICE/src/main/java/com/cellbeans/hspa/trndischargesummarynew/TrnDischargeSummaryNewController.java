package com.cellbeans.hspa.trndischargesummarynew;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.EmgPatientList.EmgPatientListRepository;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mpathresult.MpathResult;
import com.cellbeans.hspa.mpathresult.MpathResultRepository;
import com.cellbeans.hspa.mpathtestresult.MpathTestResult;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.cellbeans.hspa.temrtimeline.TemrTimelineRepository;
import com.cellbeans.hspa.temrvisitchiefcomplaint.TemrVisitChiefComplaint;
import com.cellbeans.hspa.temrvisitchiefcomplaint.TemrVisitChiefComplaintRepository;
import com.cellbeans.hspa.temrvisitdiagnosis.TemrVisitDiagnosis;
import com.cellbeans.hspa.temrvisitdiagnosis.TemrVisitDiagnosisRepository;
import com.cellbeans.hspa.temrvital.TemrVital;
import com.cellbeans.hspa.temrvital.TemrVitalRepository;
import com.cellbeans.hspa.tpathbs.TpathBs;
import com.cellbeans.hspa.tpathbs.TpathBsRepository;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.cellbeans.hspa.trnadmission.TrnAdmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_discharge_summary_new")
public class TrnDischargeSummaryNewController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TrnDischargeSummaryNewRepository trnDischargeSummaryNewRepository;

    @Autowired
    TemrVisitChiefComplaintRepository temrVisitChiefComplaintRepository;

    @Autowired
    TemrVisitDiagnosisRepository temrVisitDiagnosisRepository;

    @Autowired
    TemrVitalRepository temrVitalRepository;

    @Autowired
    TemrTimelineRepository temrTimelineRepository;

    @Autowired
    MpathResultRepository mpathResultRepository;

    @Autowired
    TpathBsRepository tpathBsRepository;

    @Autowired
    TrnAdmissionRepository trnAdmissionRepository;

    @Autowired
    EmgPatientListRepository emgPatientListRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDischargeSummaryNew trnDischargeSummaryNew) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnDischargeSummaryNew.getDsnAdmissionId().getAdmissionId() != 0L) {
            if (trnDischargeSummaryNew.getIsFinalized()) {
                trnAdmissionRepository.updateDisachargeSummaryStatus(true, trnDischargeSummaryNew.getDsnAdmissionId().getAdmissionId());
            }
            trnDischargeSummaryNewRepository.save(trnDischargeSummaryNew);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("createEMGDischargeSummary")
    public Map<String, String> createEMGDischargeSummary(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDischargeSummaryNew trnDischargeSummaryNew) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnDischargeSummaryNew.getDsnVisitId().getVisitId() != 0L) {
            if (trnDischargeSummaryNew.getIsFinalized()) {
                emgPatientListRepository.updateDisachargeSummaryStatusForEmg(true, trnDischargeSummaryNew.getDsnVisitId().getVisitId());
            }
            trnDischargeSummaryNewRepository.save(trnDischargeSummaryNew);
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
    public List<TrnDischargeSummaryNew> admissionbyid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        String Queary = "Select dsa from TrnDischargeSummaryNew dsa where dsa.dsnAdmissionId.admissionId=" + admissionId + " ORDER BY dsa.dsnFieldId.fieldId ASC ";
        return entityManager.createQuery(Queary).getResultList();
    }

    @RequestMapping("visitbyid/{visitId}")
    public List<TrnDischargeSummaryNew> visitbyid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        String Queary = "Select dsa from TrnDischargeSummaryNew dsa where dsa.dsnVisitId.visitId=" + visitId + " ORDER BY dsa.dsnFieldId.fieldId ASC ";
        return entityManager.createQuery(Queary).getResultList();
    }

    @RequestMapping("admissionbyiddsfidfieldid/{admissionId}/{dsfId}/{fieldId}")
    public Map<String, Object> admissionbyidDsfIdFieldId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId, @PathVariable("dsfId") Long dsfId, @PathVariable("fieldId") Long fieldId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> restMap = new HashMap<String, Object>();
        TrnDischargeSummaryNew trnDischargeSummaryNew = trnDischargeSummaryNewRepository.findByDsnAdmissionIdAdmissionIdEqualsAndDsnDsfIdDsfIdEqualsAndDsnFieldIdFieldIdEquals(admissionId, dsfId, fieldId);
        restMap.put("content", trnDischargeSummaryNew);
        return restMap;
    }

    @RequestMapping("visitbyiddsfidfieldid/{visitId}/{dsfId}/{fieldId}")
    public Map<String, Object> visitbyidDsfIdFieldId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId, @PathVariable("dsfId") Long dsfId, @PathVariable("fieldId") Long fieldId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> restMap = new HashMap<String, Object>();
        TrnDischargeSummaryNew trnDischargeSummaryNew = trnDischargeSummaryNewRepository.findByDsnVisitIdVisitIdEqualsAndDsnDsfIdDsfIdEqualsAndDsnFieldIdFieldIdEquals(visitId, dsfId, fieldId);
        restMap.put("content", trnDischargeSummaryNew);
        return restMap;
    }

    @RequestMapping("historybyadmissionId")
    public Map<String, Object> historyByAdmissionId(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmission admission) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> restMap = new HashMap<String, Object>();
//        MpathTestResultController mpathTestResultController = ;
        List<MpathTestResult> mpathResultListOPD = null;
        List<TpathBs> tpathbsListOPD = tpathBsRepository.findByPatientId(admission.getAdmissionPatientId().getPatientId());
        List<TpathBs> tpathbsListIPD = tpathBsRepository.findByAdmissionId(admission.getAdmissionId());
        tpathbsListOPD.addAll(tpathbsListIPD);
        List<TemrVisitDiagnosis> temrVisitDiagnosisList = temrVisitDiagnosisRepository.findByVdTimelineIdTimelinePatientIdPatientIdEquals(admission.getAdmissionPatientId().getPatientId());
        List<TemrVisitChiefComplaint> temrVisitChiefComplaintList = temrVisitChiefComplaintRepository.findByVccTimelineIdTimelinePatientIdPatientIdEquals(admission.getAdmissionPatientId().getPatientId());
        List<TemrVital> temrVitalList = temrVitalRepository.findByVitalTimelineIdTimelinePatientIdPatientIdEquals(admission.getAdmissionPatientId().getPatientId());
        List<TemrTimeline> temrTimelineList = temrTimelineRepository.findAllByTimelinePatientIdPatientIdEquals(admission.getAdmissionPatientId().getPatientId());
//        List<MpathResult> opdMpathResultList = mpathResultRepository.findOPDTestResultbyPatientId(admission.getAdmissionPatientId().getPatientId());
        List<MpathResult> opdMpathResultList = mpathResultRepository.findOPDTestResultbyPatientId(admission.getAdmissionPatientId().getPatientId());
        List<MpathResult> ipdMpathResultList = mpathResultRepository.findIPDTestResultbyPatientId(admission.getAdmissionId());
        String Queary = "Select dsn_dsf_id from trn_discharge_summary_new  where dsn_admission_id=" + admission.getAdmissionId() + " ORDER BY dsn_field_id ASC limit 0,1";
        BigInteger result = null;
        try {
            Object res = entityManager.createNativeQuery(Queary).getSingleResult();
            if (res != null) {
                result = (BigInteger) res;
            }
        } catch (Exception e) {
        }
        restMap.put("TemrVisitDiagnosis", temrVisitDiagnosisList);
        restMap.put("TemrVisitChiefComplaint", temrVisitChiefComplaintList);
        restMap.put("TemrVital", temrVitalList);
        restMap.put("TemrTimeline", temrTimelineList);
        restMap.put("tpathbsListOPD", tpathbsListOPD);
        restMap.put("tpathbsListIPD", tpathbsListIPD);
        restMap.put("IPDInvestigation", ipdMpathResultList);
        restMap.put("admissionId", result);
        return restMap;
    }

    @RequestMapping("historybyvisitId")
    public Map<String, Object> historyByVisitId(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> restMap = new HashMap<String, Object>();
//        MpathTestResultController mpathTestResultController = ;
        List<MpathTestResult> mpathResultListOPD = null;
        List<TpathBs> tpathbsListOPD = tpathBsRepository.findByPatientId(mstVisit.getVisitPatientId().getPatientId());
//                List<TpathBs> tpathbsListIPD = tpathBsRepository.findByAdmissionId(admission.getAdmissionId());
//                tpathbsListOPD.addAll(tpathbsListIPD);
        List<TemrVisitDiagnosis> temrVisitDiagnosisList = temrVisitDiagnosisRepository.findByVdTimelineIdTimelinePatientIdPatientIdEquals(mstVisit.getVisitPatientId().getPatientId());
        List<TemrVisitChiefComplaint> temrVisitChiefComplaintList = temrVisitChiefComplaintRepository.findByVccTimelineIdTimelinePatientIdPatientIdEquals(mstVisit.getVisitPatientId().getPatientId());
        TemrVital temrVitalList = temrVitalRepository.findTopByVitalTimelineIdTimelinePatientIdPatientIdEquals(mstVisit.getVisitPatientId().getPatientId());
        List<TemrTimeline> temrTimelineList = temrTimelineRepository.findAllByTimelinePatientIdPatientIdEquals(mstVisit.getVisitPatientId().getPatientId());
//        List<MpathResult> opdMpathResultList = mpathResultRepository.findOPDTestResultbyPatientId(admission.getAdmissionPatientId().getPatientId());
        List<MpathResult> opdMpathResultList = mpathResultRepository.findOPDTestResultbyPatientId(mstVisit.getVisitPatientId().getPatientId());
//        List<MpathResult> ipdMpathResultList = mpathResultRepository.findIPDTestResultbyPatientId(admission.getAdmissionId());
        String Queary = "Select distinct(dsn_dsf_id) from trn_discharge_summary_new  where dsn_visit_id=" + mstVisit.getVisitId() + " ORDER BY dsn_field_id ASC limit 0,1";
        BigInteger result = null;
        try {
            Object res = entityManager.createNativeQuery(Queary).getSingleResult();
            if (res != null) {
                result = (BigInteger) res;
            }
        } catch (Exception e) {
        }
        restMap.put("TemrVisitDiagnosis", temrVisitDiagnosisList);
        restMap.put("TemrVisitChiefComplaint", temrVisitChiefComplaintList);
        restMap.put("TemrVital", temrVitalList);
        restMap.put("TemrTimeline", temrTimelineList);
        restMap.put("tpathbsListOPD", tpathbsListOPD);
//        restMap.put("tpathbsListIPD",tpathbsListIPD);
//        restMap.put("IPDInvestigation",ipdMpathResultList);
        restMap.put("formId", result);
        return restMap;
    }
}
