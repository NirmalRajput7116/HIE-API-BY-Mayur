package com.cellbeans.hspa.patientemrdashboard;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.tbillserviceradiology.TbillServiceRadiology;
import com.cellbeans.hspa.temritemprescription.TemrItemPrescription;
import com.cellbeans.hspa.temritemprescription.TemrItemPrescriptionRepository;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.cellbeans.hspa.temrtimeline.TemrTimelineRepository;
import com.cellbeans.hspa.temrvisitallergy.TemrVisitAllergy;
import com.cellbeans.hspa.temrvisitallergy.TemrVisitAllergyRepository;
import com.cellbeans.hspa.temrvisitchiefcomplaint.TemrVisitChiefComplaint;
import com.cellbeans.hspa.temrvisitchiefcomplaint.TemrVisitChiefComplaintRepository;
import com.cellbeans.hspa.temrvisitdiagnosis.TemrVisitDiagnosis;
import com.cellbeans.hspa.temrvisitdiagnosis.TemrVisitDiagnosisRepository;
import com.cellbeans.hspa.temrvisitdoctorsnote.TemrVisitDoctorsNote;
import com.cellbeans.hspa.temrvisitdoctorsnote.TemrVisitDoctorsNoteRepository;
import com.cellbeans.hspa.temrvisithistory.TemrVisitHistory;
import com.cellbeans.hspa.temrvisithistory.TemrVisitHistoryRepository;
import com.cellbeans.hspa.temrvisitinvestigation.TemrVisitInvestigation;
import com.cellbeans.hspa.temrvisitinvestigation.TemrVisitInvestigationRespository;
import com.cellbeans.hspa.temrvisitnondrugallergy.TemrVisitNonDrugAllergy;
import com.cellbeans.hspa.temrvisitnondrugallergy.TemrVisitNonDrugAllergyRepository;
import com.cellbeans.hspa.temrvisitsymptom.TemrVisitSymptom;
import com.cellbeans.hspa.temrvisitsymptom.TemrVisitSymptomRepository;
import com.cellbeans.hspa.temrvital.TemrVital;
import com.cellbeans.hspa.temrvital.TemrVitalRepository;
import com.cellbeans.hspa.tpathbs.TpathBs;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/patientDashboard")
public class PatientEmrDashbaordController {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    MstVisitRepository mstVisitRepository;

    @Autowired
    TemrTimelineRepository temrTimelineRepository;

    @Autowired
    TemrVisitChiefComplaintRepository temrVisitChiefComplaintRepository;

    @Autowired
    TemrVisitHistoryRepository temrVisitHistoryRepository;

    @Autowired
    TemrVisitSymptomRepository temrVisitSymptomRepository;

    @Autowired
    TemrVisitNonDrugAllergyRepository temrVisitNonDrugAllergyRepository;

    @Autowired
    TemrVisitAllergyRepository temrVisitAllergyRepository;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    TemrVitalRepository temrVitalRepository;

    @Autowired
    TemrVisitDiagnosisRepository temrVisitDiagnosisRepository;

    @Autowired
    TemrVisitInvestigationRespository temrVisitInvestigationRespository;

    @Autowired
    TemrItemPrescriptionRepository temrItemPrescriptionRepository;

    @Autowired
    TemrVisitDoctorsNoteRepository temrVisitDoctorsNoteRepository;

    @Value("${paitentFiles}")
    private String fileWritePath;

    @Value("${paitentFilesBasePath}")
    private String fileDoctorDocumentPath;

    @Value("${paitentFiles}")
    private String filePath;

    @Value("${pdfStoreUrl}")
    private String pdfStoreUrl;

    @RequestMapping("getDetail/{patientId}")
    public String getPatientDto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        JSONArray allergy = new JSONArray();
        JSONArray nonDrug = new JSONArray();
        JSONArray chiefComplaint = new JSONArray();
        JSONArray history = new JSONArray();
        JSONArray symptom = new JSONArray();
        JSONArray diagnosis = new JSONArray();
        JSONArray investigation = new JSONArray();
        JSONArray prescription = new JSONArray();
        JSONArray dList = new JSONArray();
        JSONArray rList = new JSONArray();
        List<Object[]> allergyList = (List<Object[]>) entityManager.createNativeQuery("SELECT ic.compound_name,ta.created_date,ta.created_by FROM temr_visit_allergy ta INNER JOIN inv_compound_name ic WHERE ic.compound_id=ta.va_compound_id AND ta.va_patient_id=" + patientId + " AND ta.is_active=1 and ta.is_deleted=0 ORDER BY ta.created_date desc").getResultList();
        for (int i = 0; i < allergyList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + allergyList.get(i)[0]);
            object.put("date", "" + allergyList.get(i)[1]);
            object.put("by", "" + allergyList.get(i)[2]);
            allergy.put(object);
        }
        List<Object[]> nonDrugAllergy = (List<Object[]>) entityManager.createNativeQuery("SELECT nd.nda_name,td.created_date,td.created_by FROM temr_visit_non_drug_allergy td INNER JOIN mst_non_drug_allergy nd INNER JOIN mst_visit mv WHERE td.vnda_nda_id=nd.nda_id AND td.vnda_visit_id=mv.visit_id AND mv.visit_patient_id=" + patientId + " AND td.is_active=1 and td.is_deleted=0 ORDER BY td.created_date desc;").getResultList();
        for (int i = 0; i < nonDrugAllergy.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + nonDrugAllergy.get(i)[0]);
            object.put("date", "" + nonDrugAllergy.get(i)[1]);
            object.put("by", "" + nonDrugAllergy.get(i)[2]);
            nonDrug.put(object);
        }
        List<Object[]> chiefComplaintList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT tc.vs_chief_complaint,tc.created_by,tc.created_date,tc.vcc_cc_unit,tc.vcc_cc_no FROM temr_visit_chief_complaint tc " +
                        " INNER JOIN mst_visit mv " +
                        " WHERE mv.visit_id=tc.vcc_visit_id AND mv.visit_patient_id=" + patientId + " " +
                        " AND tc.is_active=1 and tc.is_deleted=0 ORDER BY tc.created_date desc;").getResultList();
        for (int i = 0; i < chiefComplaintList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + chiefComplaintList.get(i)[0]);
            object.put("date", "" + chiefComplaintList.get(i)[2]);
            object.put("by", "" + chiefComplaintList.get(i)[1]);
            object.put("unit", "" + chiefComplaintList.get(i)[3]);
            object.put("duration", "" + chiefComplaintList.get(i)[4]);
            chiefComplaint.put(object);
        }
        List<Object[]> historyList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT th.created_by,th.vh_content,th.created_date FROM temr_visit_history th INNER JOIN mst_visit mv " +
                        " WHERE th.vh_visit_id=mv.visit_id AND mv.visit_patient_id=" + patientId +
                        " AND th.is_active=1 and th.is_deleted=0 GROUP BY mv.visit_patient_id ORDER BY th.vh_id desc;").getResultList();
        for (int i = 0; i < historyList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + historyList.get(i)[1]);
            object.put("date", "" + historyList.get(i)[2]);
            object.put("by", "" + historyList.get(i)[0]);
            history.put(object);
        }
        List<Object[]> symptomsList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT ts.created_by,ts.vs_sign_symptoms,ts.created_date,ts.vs_unit,ts.vs_no FROM temr_visit_symptom ts INNER JOIN mst_visit mv WHERE mv.visit_id=ts.vs_visit_id AND mv.visit_patient_id=" + patientId + " AND ts.is_active=1 and ts.is_deleted=0 ORDER BY ts.created_date desc").getResultList();
        for (int i = 0; i < symptomsList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + symptomsList.get(i)[1]);
            object.put("date", "" + symptomsList.get(i)[2]);
            object.put("by", "" + symptomsList.get(i)[0]);
            object.put("unit", "" + symptomsList.get(i)[3]);
            object.put("duration", "" + symptomsList.get(i)[4]);
            symptom.put(object);
        }
        List<Object[]> diagnosisList = (List<Object[]>) entityManager.createNativeQuery("" +
                "SELECT vd.created_by,ic.ic_code,ic.ic_description,IF(vd.vd_is_final_diagnosis=1,'Final','Provisional')," +
                " vd.created_date, dc_name,dcs_name,dt_name FROM temr_visit_diagnosis vd " +
                " INNER JOIN mst_visit mv INNER JOIN memr_icd_code ic " +
                " INNER JOIN memr_disease_category dc INNER JOIN memr_disease_sub_category dsc " +
                " INNER JOIN memr_disease_type dt" +
                " WHERE ic.ic_id = vd.vd_ic_id AND ic.ic_dc_id = dc.dc_id AND ic.ic_dt_id = dt.dt_id " +
                " AND ic.ic_dsc_id = dsc.dsc_id AND mv.visit_id=vd.vd_visit_id AND mv.visit_patient_id=" + patientId + " " +
                "AND vd.is_active=1 and vd.is_deleted=0 ORDER BY vd.created_date desc").getResultList();
        for (int i = 0; i < diagnosisList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("code", "" + diagnosisList.get(i)[1]);
            object.put("desc", "" + diagnosisList.get(i)[2]);
            object.put("is_final", "" + diagnosisList.get(i)[3]);
            object.put("by", "" + diagnosisList.get(i)[0]);
            object.put("date", "" + diagnosisList.get(i)[4]);
            object.put("dc_name", "" + diagnosisList.get(i)[5]);
            object.put("dsc_name", "" + diagnosisList.get(i)[6]);
            object.put("dt_name", "" + diagnosisList.get(i)[7]);
            diagnosis.put(object);
        }
//        String tpathQuery = " (SELECT ps.*  " +
//                "FROM tpath_bs ps  " +
//                "LEFT JOIN tbill_bill tb ON tb.bill_id = ps.ps_bill_id  " +
//                "LEFT JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id  " +
//                "LEFT JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id  " +
//                "WHERE tb.ipd_bill = FALSE and mp.patient_id = " + patientId + " )  " +
//                "UNION All  " +
//                "(SELECT ps.*  " +
//                "FROM tpath_bs ps  " +
//                "LEFT JOIN tbill_bill tb ON tb.bill_id = ps.ps_bill_id  " +
//                "LEFT JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id  " +
//                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id  " +
//                "WHERE ps.isipd = true and mp.patient_id =" + patientId + " AND ps.ps_cs_id IS NULL)  " +
//                "UNION ALL  " +
//                "(SELECT ps.*  " +
//                "FROM tpath_bs ps  " +
//                "LEFT JOIN tbill_bill tb ON tb.bill_id = ps.ps_bill_id  " +
//                "LEFT JOIN ipd_charges_service cs ON cs.cs_id = ps.ps_cs_id  " +
//                "LEFT JOIN mbill_ipd_charge ic ON ic.ipdcharge_id = cs.cs_charge_id  " +
//                "LEFT JOIN trn_admission ta ON ta.admission_id = ic.charge_admission_id  " +
//                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id  " +
//                "WHERE ps.isipd = TRUE and mp.patient_id = " + patientId + " AND ps.ps_cs_id IS NOT NULL) ";
//        String CountQuery = " select count(*) from ( " + tpathQuery + " ) as combine ";
//        Query += "  limit " + (( Integer.parseInt(page)-1) *  Integer.parseInt(size)) + "," +  Integer.parseInt(size);
//        List<TpathBs> investigationList = entityManager.createNativeQuery(tpathQuery, TpathBs.class).getResultList();
//        JSONObject object1 = new JSONObject();
//        object1.put("investigationlist", investigationList);
//        investigation = object1.getJSONArray("investigationlist");
        List<Object[]> investigationList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT vi.created_by,ms.service_name,vi.created_date,mg.group_name,ms.service_code FROM temr_visit_investigation vi " +
                        " INNER JOIN mst_visit mv INNER JOIN mbill_service ms INNER JOIN mbill_group mg" +
                        " WHERE ms.service_id=vi.vi_service_id AND mv.visit_id=vi.vi_visit_id AND mg.group_id=ms.service_group_id" +
                        " AND mv.visit_patient_id=" + patientId + " AND vi.is_active=1 and vi.is_deleted=0 ORDER BY vi.created_date DESC").getResultList();
        for (int i = 0; i < investigationList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + investigationList.get(i)[1]);
            object.put("date", "" + investigationList.get(i)[2]);
            object.put("by", "" + investigationList.get(i)[0]);
            object.put("group", "" + investigationList.get(i)[3]);
            object.put("code", "" + investigationList.get(i)[4]);
            investigation.put(object);
        }
        List<Object[]> prescriptionList = (List<Object[]>) entityManager.createNativeQuery(
                " SELECT item.item_name,df.df_name,it.created_date,it.created_by, " +
                        " item.item_strength, idt.idt_name,it.ip_dd_id,it.ip_quantity, " +
                        " di.di_name,it.ipinstruction,mr.route_name,mas.as_name " +
                        " FROM temr_item_prescription it " +
                        " INNER JOIN temr_visit_prescription vp INNER JOIN mst_visit mv INNER JOIN inv_item item " +
                        " INNER JOIN mst_dose_frequency df " +
                        " LEFT JOIN inv_item_dispensing_type idt ON idt.idt_id = item.item_idt_id " +
                        " LEFT JOIN memr_drug_instruction di ON it.ip_di_id = di.di_id " +
                        " LEFT JOIN mst_route mr ON mr.route_id = it.ip_route_id" +
                        " LEFT JOIN temr_visit_allergy va ON va.va_id = it.ip_va_id " +
                        " LEFT JOIN memr_allergy_sensitivity mas ON mas.as_id = va.va_as_id " +
                        " WHERE vp.vp_id=it.ip_vp_id AND vp.vp_visit_id=mv.visit_id AND item.item_id=it.ip_inv_item_id " +
                        " AND df.df_id=it.ip_df_id " +
                        " AND mv.visit_patient_id= " + patientId + " " +
                        " AND it.is_active=1 and it.is_deleted=0 ORDER BY it.created_date DESC ").getResultList();
        for (int i = 0; i < prescriptionList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + prescriptionList.get(i)[0]);
            object.put("date", "" + prescriptionList.get(i)[2]);
            object.put("by", "" + prescriptionList.get(i)[3]);
            object.put("frequency", "" + prescriptionList.get(i)[1]);
            object.put("strength", "" + prescriptionList.get(i)[4]);
            object.put("idt_name", "" + prescriptionList.get(i)[5]);
            object.put("duration", "" + prescriptionList.get(i)[6]);
            object.put("qty", "" + prescriptionList.get(i)[7]);
            object.put("inst1", "" + prescriptionList.get(i)[8]);
            object.put("inst2", "" + prescriptionList.get(i)[9]);
            object.put("route", "" + prescriptionList.get(i)[10]);
            object.put("allergy", "" + prescriptionList.get(i)[11]);
            prescription.put(object);
        }
        List<Object[]> documentList = (List<Object[]>) entityManager.createNativeQuery("SELECT du.du_name,dc.dc_name,du_file_name,du_path,du.created_date FROM temr_document_upload du INNER JOIN mst_document_category dc WHERE dc.dc_id=du.du_dc_id and du.du_patient_id=" + patientId + " AND du.is_active=1 and du.is_deleted=0 ORDER BY du.created_date DESC").getResultList();
        for (int i = 0; i < documentList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + documentList.get(i)[0]);
            object.put("category", "" + documentList.get(i)[1]);
            object.put("file_name", "" + documentList.get(i)[2]);
            object.put("path", "" + documentList.get(i)[3]);
            object.put("date", "" + documentList.get(i)[4]);
            dList.put(object);
        }
        String Query = "  (   " +
                "SELECT  bsr.*   " +
                "FROM tbill_service_radiology bsr   " +
                "LEFT JOIN tbill_bill_service tbbs ON tbbs.bs_id = bsr.bsr_bs_id   " +
                "LEFT JOIN mbill_service ms ON ms.service_id =  tbbs.bs_service_id   " +
                "LEFT JOIN tbill_bill tb ON tb.bill_id = tbbs.bs_bill_id   " +
                "LEFT JOIN mst_unit mu ON mu.unit_id = tb.tbill_unit_id   " +
                "LEFT JOIN ipd_charges_service cs ON cs.cs_id = bsr.bsr_cs_id   " +
                "LEFT JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id   " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id   " +
                "LEFT JOIN mst_user muser ON muser.user_id = mp.patient_user_id   " +
                "WHERE bsr.ipd_bill = TRUE AND bsr.bsr_cs_id IS NULL and mp.patient_id = " + patientId + ")   " +
                " UNION ALL (   " +
                "SELECT  bsr.*  " +
                "FROM tbill_service_radiology bsr   " +
                "LEFT JOIN tbill_bill_service tbbs ON tbbs.bs_id = bsr.bsr_bs_id   " +
                "LEFT JOIN tbill_bill tb ON tb.bill_id = tbbs.bs_bill_id   " +
                "LEFT JOIN mst_unit mu ON mu.unit_id = tb.tbill_unit_id   " +
                "LEFT JOIN ipd_charges_service cs ON cs.cs_id = bsr.bsr_cs_id   " +
                "LEFT JOIN mbill_ipd_charge ic ON ic.ipdcharge_id = cs.cs_charge_id   " +
                "LEFT JOIN mbill_service ms ON ms.service_id =  cs.cs_service_id   " +
                "LEFT JOIN trn_admission ta ON ta.admission_id = ic.charge_admission_id   " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id   " +
                "LEFT JOIN mst_user muser ON muser.user_id = mp.patient_user_id   " +
                "WHERE bsr.ipd_bill = TRUE AND bsr.bsr_cs_id IS NOT NULL and mp.patient_id = " + patientId + ")    ";
        String CountQuery1 = " select count(*) from ( " + Query + " ) as combine ";
        System.out.println("query  " + Query);
//        Query += "  limit " + (( Integer.parseInt(page)-1) *  Integer.parseInt(size)) + "," +  Integer.parseInt(size);
//        List<Object[]> radiologyList = (List<Object[]>) entityManager.createNativeQuery("SELECT ms.service_name,rd.created_date,CONCAT(mu.user_firstname,' ',mu.user_lastname) FROM tbill_service_radiology rd INNER JOIN tbill_bill_service ts INNER JOIN mbill_service ms INNER JOIN mst_staff staff INNER JOIN mst_user mu INNER JOIN tbill_bill tb INNER JOIN mst_visit mv WHERE rd.bsr_bs_id=ts.bs_id AND ts.bs_service_id=ms.service_id AND staff.staff_id=ts.bs_staff_id AND staff.staff_user_id=mu.user_id AND ts.bs_bill_id=tb.bill_id AND tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=" + patientId + " AND rd.is_active=1 and rd.is_deleted=0 ORDER BY rd.created_date DESC ").getResultList();
        List<Object[]> radiologyList = (List<Object[]>) entityManager.createNativeQuery(Query, TbillServiceRadiology.class).getResultList();
        JSONObject object = new JSONObject();
        object.put("list", radiologyList);
        rList = object.getJSONArray("list");

       /* for (int i = 0; i < radiologyList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("serviceName", "" + radiologyList.get(i)[0]);
            object.put("date", "" + radiologyList.get(i)[1]);
            object.put("by", "" + radiologyList.get(i)[2]);
            rList.put(object);
        }
              */
        JSONObject responseObject = new JSONObject();
        responseObject.put("allergy", allergy);
        responseObject.put("nondrugallergy", nonDrug);
        responseObject.put("chiefcomplaint", chiefComplaint);
        responseObject.put("history", history);
        responseObject.put("symptom", symptom);
        responseObject.put("diagnosis", diagnosis);
        responseObject.put("investigation", investigation);
        responseObject.put("prescription", prescription);
        responseObject.put("documentList", dList);
        responseObject.put("rList", rList);
        return responseObject.toString();
    }

    @RequestMapping("getDetailByVisitId/{visitId}")
    public String getVisittDto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVisit mstVisit = mstVisitRepository.findByVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(visitId);
        JSONArray allergy = new JSONArray();
        JSONArray nonDrug = new JSONArray();
        JSONArray chiefComplaint = new JSONArray();
        JSONArray history = new JSONArray();
        JSONArray symptom = new JSONArray();
        JSONArray diagnosis = new JSONArray();
        JSONArray investigation = new JSONArray();
        JSONArray prescription = new JSONArray();
        JSONArray dList = new JSONArray();
        JSONArray rList = new JSONArray();
        List<Object[]> allergyList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT ic.compound_name,ta.created_date,ta.created_by FROM temr_visit_allergy ta " +
                        "INNER JOIN inv_compound_name ic WHERE ic.compound_id=ta.va_compound_id " +
                        "AND ta.va_patient_id=" + mstVisit.getVisitPatientId().getPatientId() + " AND ta.is_active=1 and ta.is_deleted=0 " +
                        "ORDER BY ta.created_date desc").getResultList();
        for (int i = 0; i < allergyList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + allergyList.get(i)[0]);
            object.put("date", "" + allergyList.get(i)[1]);
            object.put("by", "" + allergyList.get(i)[2]);
            allergy.put(object);
        }
        List<Object[]> nonDrugAllergy = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT nd.nda_name,td.created_date,td.created_by FROM temr_visit_non_drug_allergy td " +
                        "INNER JOIN mst_non_drug_allergy nd INNER JOIN mst_visit mv " +
                        "WHERE td.vnda_nda_id=nd.nda_id AND td.vnda_visit_id=mv.visit_id " +
                        "AND mv.visit_id=" + visitId + " AND td.is_active=1 and td.is_deleted=0 " +
                        "ORDER BY td.created_date desc;").getResultList();
        for (int i = 0; i < nonDrugAllergy.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + nonDrugAllergy.get(i)[0]);
            object.put("date", "" + nonDrugAllergy.get(i)[1]);
            object.put("by", "" + nonDrugAllergy.get(i)[2]);
            nonDrug.put(object);
        }
        List<Object[]> chiefComplaintList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT tc.vs_chief_complaint,tc.created_by,tc.created_date,tc.vcc_cc_unit,tc.vcc_cc_no FROM temr_visit_chief_complaint tc " +
                        " INNER JOIN mst_visit mv " +
                        " WHERE mv.visit_id=tc.vcc_visit_id AND mv.visit_id=" + visitId + " " +
                        " AND tc.is_active=1 and tc.is_deleted=0 ORDER BY tc.created_date desc;").getResultList();
        for (int i = 0; i < chiefComplaintList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + chiefComplaintList.get(i)[0]);
            object.put("date", "" + chiefComplaintList.get(i)[2]);
            object.put("by", "" + chiefComplaintList.get(i)[1]);
            object.put("unit", "" + chiefComplaintList.get(i)[3]);
            object.put("duration", "" + chiefComplaintList.get(i)[4]);
            chiefComplaint.put(object);
        }
        List<Object[]> historyList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT th.created_by,th.vh_content,th.created_date FROM temr_visit_history th INNER JOIN mst_visit mv " +
                        " WHERE th.vh_visit_id=mv.visit_id AND mv.visit_id=" + visitId +
                        " AND th.is_active=1 and th.is_deleted=0 GROUP BY mv.visit_patient_id ORDER BY th.vh_id desc;").getResultList();
        for (int i = 0; i < historyList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + historyList.get(i)[1]);
            object.put("date", "" + historyList.get(i)[2]);
            object.put("by", "" + historyList.get(i)[0]);
            history.put(object);
        }
        List<Object[]> symptomsList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT ts.created_by,ts.vs_sign_symptoms,ts.created_date,ts.vs_unit,ts.vs_no FROM temr_visit_symptom ts " +
                        "INNER JOIN mst_visit mv WHERE mv.visit_id=ts.vs_visit_id " +
                        "AND mv.visit_id=" + visitId + " AND ts.is_active=1 and ts.is_deleted=0 " +
                        "ORDER BY ts.created_date desc").getResultList();
        for (int i = 0; i < symptomsList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + symptomsList.get(i)[1]);
            object.put("date", "" + symptomsList.get(i)[2]);
            object.put("by", "" + symptomsList.get(i)[0]);
            object.put("unit", "" + symptomsList.get(i)[3]);
            object.put("duration", "" + symptomsList.get(i)[4]);
            symptom.put(object);
        }
        List<Object[]> diagnosisList = (List<Object[]>) entityManager.createNativeQuery("" +
                "SELECT vd.created_by,ic.ic_code,ic.ic_description,IF(vd.vd_is_final_diagnosis=1,'Final','Provisional')," +
                " vd.created_date, dc_name,dcs_name,dt_name FROM temr_visit_diagnosis vd " +
                " INNER JOIN mst_visit mv INNER JOIN memr_icd_code ic " +
                " INNER JOIN memr_disease_category dc INNER JOIN memr_disease_sub_category dsc " +
                " INNER JOIN memr_disease_type dt" +
                " WHERE ic.ic_id = vd.vd_ic_id AND ic.ic_dc_id = dc.dc_id AND ic.ic_dt_id = dt.dt_id " +
                " AND ic.ic_dsc_id = dsc.dsc_id AND mv.visit_id=vd.vd_visit_id AND mv.visit_id=" + visitId + " " +
                "AND vd.is_active=1 and vd.is_deleted=0 ORDER BY vd.created_date desc").getResultList();
        for (int i = 0; i < diagnosisList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("code", "" + diagnosisList.get(i)[1]);
            object.put("desc", "" + diagnosisList.get(i)[2]);
            object.put("is_final", "" + diagnosisList.get(i)[3]);
            object.put("by", "" + diagnosisList.get(i)[0]);
            object.put("date", "" + diagnosisList.get(i)[4]);
            object.put("dc_name", "" + diagnosisList.get(i)[5]);
            object.put("dsc_name", "" + diagnosisList.get(i)[6]);
            object.put("dt_name", "" + diagnosisList.get(i)[7]);
            diagnosis.put(object);
        }
//        String tpathQuery = " (SELECT ps.*  " +
//                "FROM tpath_bs ps  " +
//                "LEFT JOIN tbill_bill tb ON tb.bill_id = ps.ps_bill_id  " +
//                "LEFT JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id  " +
//                "LEFT JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id  " +
//                "WHERE tb.ipd_bill = FALSE and mp.patient_id = " + patientId + " )  " +
//                "UNION All  " +
//                "(SELECT ps.*  " +
//                "FROM tpath_bs ps  " +
//                "LEFT JOIN tbill_bill tb ON tb.bill_id = ps.ps_bill_id  " +
//                "LEFT JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id  " +
//                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id  " +
//                "WHERE ps.isipd = true and mp.patient_id =" + patientId + " AND ps.ps_cs_id IS NULL)  " +
//                "UNION ALL  " +
//                "(SELECT ps.*  " +
//                "FROM tpath_bs ps  " +
//                "LEFT JOIN tbill_bill tb ON tb.bill_id = ps.ps_bill_id  " +
//                "LEFT JOIN ipd_charges_service cs ON cs.cs_id = ps.ps_cs_id  " +
//                "LEFT JOIN mbill_ipd_charge ic ON ic.ipdcharge_id = cs.cs_charge_id  " +
//                "LEFT JOIN trn_admission ta ON ta.admission_id = ic.charge_admission_id  " +
//                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id  " +
//                "WHERE ps.isipd = TRUE and mp.patient_id = " + patientId + " AND ps.ps_cs_id IS NOT NULL) ";
//        String CountQuery = " select count(*) from ( " + tpathQuery + " ) as combine ";
//        Query += "  limit " + (( Integer.parseInt(page)-1) *  Integer.parseInt(size)) + "," +  Integer.parseInt(size);
//        List<TpathBs> investigationList = entityManager.createNativeQuery(tpathQuery, TpathBs.class).getResultList();
//        JSONObject object1 = new JSONObject();
//        object1.put("investigationlist", investigationList);
//        investigation = object1.getJSONArray("investigationlist");
        List<Object[]> investigationList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT vi.created_by,ms.service_name,vi.created_date,mg.group_name,ms.service_code FROM temr_visit_investigation vi " +
                        " INNER JOIN mst_visit mv INNER JOIN mbill_service ms INNER JOIN mbill_group mg" +
                        " WHERE ms.service_id=vi.vi_service_id AND mv.visit_id=vi.vi_visit_id AND mg.group_id=ms.service_group_id" +
                        " AND mv.visit_id=" + visitId + " AND vi.is_active=1 and vi.is_deleted=0 ORDER BY vi.created_date DESC").getResultList();
        for (int i = 0; i < investigationList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + investigationList.get(i)[1]);
            object.put("date", "" + investigationList.get(i)[2]);
            object.put("by", "" + investigationList.get(i)[0]);
            object.put("group", "" + investigationList.get(i)[3]);
            object.put("code", "" + investigationList.get(i)[4]);
            investigation.put(object);
        }
        List<Object[]> prescriptionList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT item.item_name,df.df_name,it.created_date,it.created_by, " +
                        " item.item_strength, idt.idt_name,it.ip_dd_id,it.ip_quantity, " +
                        " di.di_name,it.ipinstruction,mr.route_name,mas.as_name " +
                        " FROM temr_item_prescription it " +
                        " INNER JOIN temr_visit_prescription vp on vp.vp_id=it.ip_vp_id" +
                        " INNER JOIN mst_visit mv ON vp.vp_visit_id=mv.visit_id" +
                        " INNER JOIN inv_item item on item.item_id=it.ip_inv_item_id" +
                        " LEFT JOIN mst_dose_frequency df ON df.df_id=it.ip_df_id" +
                        " LEFT JOIN inv_item_dispensing_type idt ON idt.idt_id = item.item_idt_id " +
                        " LEFT JOIN memr_drug_instruction di ON it.ip_di_id = di.di_id " +
                        " LEFT JOIN mst_route mr ON mr.route_id = it.ip_route_id" +
                        " LEFT JOIN temr_visit_allergy va ON va.va_id = it.ip_va_id " +
                        " LEFT JOIN memr_allergy_sensitivity mas ON mas.as_id = va.va_as_id " +
                        " WHERE mv.visit_id= " + visitId + " " +
                        " AND it.is_active=1 and it.is_deleted=0 ORDER BY it.created_date DESC ").getResultList();
        for (int i = 0; i < prescriptionList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + prescriptionList.get(i)[0]);
            object.put("date", "" + prescriptionList.get(i)[2]);
            object.put("by", "" + prescriptionList.get(i)[3]);
            object.put("frequency", "" + prescriptionList.get(i)[1]);
            object.put("strength", "" + prescriptionList.get(i)[4]);
            object.put("idt_name", "" + prescriptionList.get(i)[5]);
            object.put("duration", "" + prescriptionList.get(i)[6]);
            object.put("qty", "" + prescriptionList.get(i)[7]);
            object.put("inst1", "" + prescriptionList.get(i)[8]);
            object.put("inst2", "" + prescriptionList.get(i)[9]);
            object.put("route", "" + prescriptionList.get(i)[10]);
            object.put("allergy", "" + prescriptionList.get(i)[11]);
            prescription.put(object);
        }
        List<Object[]> documentList = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT du.du_name,dc.dc_name,du_file_name,du_path,du.created_date FROM temr_document_upload du " +
                        "INNER JOIN mst_document_category dc WHERE dc.dc_id=du.du_dc_id " +
                        "and du.du_patient_id=" + mstVisit.getVisitPatientId().getPatientId() + " AND du.is_active=1 and du.is_deleted=0" +
                        " ORDER BY du.created_date DESC").getResultList();
        for (int i = 0; i < documentList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", "" + documentList.get(i)[0]);
            object.put("category", "" + documentList.get(i)[1]);
            object.put("file_name", "" + documentList.get(i)[2]);
            object.put("path", "" + documentList.get(i)[3]);
            object.put("date", "" + documentList.get(i)[4]);
            dList.put(object);
        }
        String Query = "  (   " +
                "SELECT  bsr.*   " +
                "FROM tbill_service_radiology bsr   " +
                "LEFT JOIN tbill_bill_service tbbs ON tbbs.bs_id = bsr.bsr_bs_id   " +
                "LEFT JOIN mbill_service ms ON ms.service_id =  tbbs.bs_service_id   " +
                "LEFT JOIN tbill_bill tb ON tb.bill_id = tbbs.bs_bill_id   " +
                "LEFT JOIN mst_unit mu ON mu.unit_id = tb.tbill_unit_id   " +
                "LEFT JOIN ipd_charges_service cs ON cs.cs_id = bsr.bsr_cs_id   " +
                "LEFT JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id   " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id   " +
                "LEFT JOIN mst_user muser ON muser.user_id = mp.patient_user_id   " +
                "WHERE bsr.ipd_bill = TRUE AND bsr.bsr_cs_id IS NULL and mp.patient_id = " + mstVisit.getVisitPatientId().getPatientId() + ")   " +
                " UNION ALL (   " +
                "SELECT  bsr.*  " +
                "FROM tbill_service_radiology bsr   " +
                "LEFT JOIN tbill_bill_service tbbs ON tbbs.bs_id = bsr.bsr_bs_id   " +
                 "LEFT JOIN tbill_bill tb ON tb.bill_id = tbbs.bs_bill_id   " +
                "LEFT JOIN mst_unit mu ON mu.unit_id = tb.tbill_unit_id   " +
                "LEFT JOIN ipd_charges_service cs ON cs.cs_id = bsr.bsr_cs_id   " +
                "LEFT JOIN mbill_ipd_charge ic ON ic.ipdcharge_id = cs.cs_charge_id   " +
                "LEFT JOIN mbill_service ms ON ms.service_id =  cs.cs_service_id   " +
                "LEFT JOIN trn_admission ta ON ta.admission_id = ic.charge_admission_id   " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id   " +
                "LEFT JOIN mst_user muser ON muser.user_id = mp.patient_user_id   " +
                "WHERE bsr.ipd_bill = TRUE AND bsr.bsr_cs_id IS NOT NULL and mp.patient_id = " + mstVisit.getVisitPatientId().getPatientId() + ")    ";
        String CountQuery1 = " select count(*) from ( " + Query + " ) as combine ";
        System.out.println("query  " + Query);
//        Query += "  limit " + (( Integer.parseInt(page)-1) *  Integer.parseInt(size)) + "," +  Integer.parseInt(size);
//        List<Object[]> radiologyList = (List<Object[]>) entityManager.createNativeQuery("SELECT ms.service_name,rd.created_date,CONCAT(mu.user_firstname,' ',mu.user_lastname) FROM tbill_service_radiology rd INNER JOIN tbill_bill_service ts INNER JOIN mbill_service ms INNER JOIN mst_staff staff INNER JOIN mst_user mu INNER JOIN tbill_bill tb INNER JOIN mst_visit mv WHERE rd.bsr_bs_id=ts.bs_id AND ts.bs_service_id=ms.service_id AND staff.staff_id=ts.bs_staff_id AND staff.staff_user_id=mu.user_id AND ts.bs_bill_id=tb.bill_id AND tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=" + patientId + " AND rd.is_active=1 and rd.is_deleted=0 ORDER BY rd.created_date DESC ").getResultList();
        List<Object[]> radiologyList = (List<Object[]>) entityManager.createNativeQuery(Query, TbillServiceRadiology.class).getResultList();
        JSONObject object = new JSONObject();
        object.put("list", radiologyList);
        rList = object.getJSONArray("list");

       /* for (int i = 0; i < radiologyList.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("serviceName", "" + radiologyList.get(i)[0]);
            object.put("date", "" + radiologyList.get(i)[1]);
            object.put("by", "" + radiologyList.get(i)[2]);
            rList.put(object);
        }
              */
        JSONObject responseObject = new JSONObject();
        responseObject.put("allergy", allergy);
        responseObject.put("nondrugallergy", nonDrug);
        responseObject.put("chiefcomplaint", chiefComplaint);
        responseObject.put("history", history);
        responseObject.put("symptom", symptom);
        responseObject.put("diagnosis", diagnosis);
        responseObject.put("investigation", investigation);
        responseObject.put("prescription", prescription);
        responseObject.put("documentList", dList);
        responseObject.put("rList", rList);
        return responseObject.toString();
    }

    @RequestMapping("generateCaseSheetPdf/{visitId}")
    public String generateCaseSheetPdf(@RequestHeader("X-tenantId") String tenantName, @PathVariable Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVisit mstVisit = mstVisitRepository.getById(visitId);
        TemrTimeline temrTimeline = temrTimelineRepository.findByTimelineVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        TemrVisitHistory temrVisitHistory = temrVisitHistoryRepository.findFirstByVhTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalseOrderByVhIdDesc(temrTimeline.getTimelineId());
        java.util.List<TemrVisitChiefComplaint> temrVisitChiefComplaintList = temrVisitChiefComplaintRepository.findAllByVccVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        java.util.List<TemrVisitSymptom> temrVisitSymptomList = temrVisitSymptomRepository.findAllByVsVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        java.util.List<TemrVisitNonDrugAllergy> temrVisitNonDrugAllergyList = temrVisitNonDrugAllergyRepository.findAllByVndaVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        java.util.List<TemrVisitAllergy> temrVisitAllergyList = temrVisitAllergyRepository.findByVaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(mstVisit.getVisitPatientId().getPatientId());
        MstUnit mstUnit = mstUnitRepository.getById(mstVisit.getVisitUnitId().getUnitId());
        java.util.List<TemrVital> temrVitalList = temrVitalRepository.findByVitalVisitId(visitId);
        java.util.List<TemrVisitDiagnosis> temrVisitDiagnosisList = temrVisitDiagnosisRepository.findByVdVisitId(visitId);
        java.util.List<TemrVisitInvestigation> temrVisitInvestigationList = temrVisitInvestigationRespository.findAllByViVisitIdTemrVisitInvestigation(visitId);
        java.util.List<TemrItemPrescription> temrItemPrescriptionList = temrItemPrescriptionRepository.findByVisitId(visitId);
        java.util.List<TemrVisitDoctorsNote> temrVisitDoctorsNotes = temrVisitDoctorsNoteRepository.findByVaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(mstVisit.getVisitPatientId().getPatientId());

        String FILE = fileWritePath + "CaseSheet-" + visitId + ".pdf";
        try {
            PdfWriter writer = new PdfWriter(FILE);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            pdfDoc.setDefaultPageSize(PageSize.A4.rotate());
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

            float[] columnWid = new float[]{160, 40};
            Table table = new Table(columnWid);
            table.setWidthPercent(100);
            File fileLogo = new File(pdfStoreUrl + "tcc1.png");
            table.addCell(new Cell()
                    .add("" + mstUnit.getUnitName() + "\n "
                            + mstUnit.getUnitAddress() + " \n Phone : " + mstUnit.getUnitPhone()
                            + " Email : " + mstUnit.getUnitEmail())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER)
                    .setFontSize(12).setFont(font));

            if(fileLogo.exists()) {
                String unitImageFile = pdfStoreUrl + "tcc1.png";
                ImageData unitImageData = ImageDataFactory.create(unitImageFile);
                Image unitImg = new Image(unitImageData);
                unitImg.setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER);
                Cell cellImage = new Cell();
                cellImage.add(unitImg).setBorder(Border.NO_BORDER);
                table.addCell(cellImage);
            }
            document.add(table);

            float[] columnWidths = new float[]{20, 40, 20, 20, 20};
            Table table1 = new Table(columnWidths);
            table1.setWidthPercent(100);
            table1.addCell(new Cell().add("Patient Name : \t ")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            table1.addCell(new Cell().add(mstVisit.getVisitPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname().toUpperCase() + " " + mstVisit.getVisitPatientId().getPatientUserId().getUserLastname().toUpperCase())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            table1.addCell(new Cell().add("")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            table1.addCell(new Cell().add("MR No. : \t")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            table1.addCell(new Cell().add(mstVisit.getVisitPatientId().getPatientMrNo())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            document.add(table1);
            Table table2 = new Table(columnWidths);
            table2.setWidthPercent(100);
            table2.addCell(new Cell().add("Mobile No. : \t")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            table2.addCell(new Cell().add(mstVisit.getVisitPatientId().getPatientUserId().getUserMobile())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            table2.addCell(new Cell().add("")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            table2.addCell(new Cell().add("Age / Gender : \t")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            table2.addCell(new Cell().add(mstVisit.getVisitPatientId().getPatientUserId().getUserAge() + " Y / " + mstVisit.getVisitPatientId().getPatientUserId().getUserGenderId().getGenderName())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            document.add(table2);

            Table tableCons = new Table(columnWidths);
            tableCons.setWidthPercent(100);
            tableCons.addCell(new Cell().add("Visit Type : \t")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            String cons_type = "";
            if (temrTimeline.getTimelineVisitId().getIsVirtual()) {
                cons_type = "Virtual Consultation";
            } else {
                cons_type = "In Person Consultation";
            }
            tableCons.addCell(new Cell().add(cons_type)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            tableCons.addCell(new Cell().add("")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            tableCons.addCell(new Cell().add("Visit Service : \t")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            tableCons.addCell(new Cell().add(temrTimeline.getTimelineServiceId().getBsServiceId().getServiceName())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            document.add(tableCons);
            //            float[] columnWidths8 = new float[] {60, 60 };

            Table table8 = new Table(columnWidths);
            table8.setWidthPercent(100);
            table8.addCell(new Cell().add("Visit Date and Time : \t ")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
            );
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat ddmmyyyyFormater = new SimpleDateFormat("dd/MM/yyyy");
//            Date visitDate = mstVisit.getVisitDate();
            Date visitDate = mstVisit.getCreatedDate();

            table8.addCell(new Cell().add("" + formatter.format(visitDate))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
            );
            table8.addCell(new Cell().add("")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            table8.addCell(new Cell().add("Doctor name")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(10f)
            );
            table8.addCell(new Cell().add(temrTimeline.getTimelineEMRFinalStaffId().getStaffUserId().getUserFullname())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)

            );


            document.add(table8);

//            float[] columnWidths6 = new float[]{8, 52, 28, 15, 17};
//            Table table6 = new Table(columnWidths6);
//            table6.setWidthPercent(100);
//            Paragraph paragraph6 = new Paragraph("Doctor : ")
//                    .setBackgroundColor(Color.BLUE)
//                    .setFontColor(Color.WHITE)
//                    .setTextAlignment(TextAlignment.LEFT)
//                    .setFontSize(14f);
//            document.add(paragraph6);
//            table6.addCell(new Cell().add("Name : \t ")
//                    .setTextAlignment(TextAlignment.LEFT)
//                    .setBorder(Border.NO_BORDER)
//            );
//            table6.addCell(new Cell().add(mstVisit.getVisitStaffId().getStaffUserId().getUserFullname())
//                    .setTextAlignment(TextAlignment.LEFT)
//                    .setBorder(Border.NO_BORDER)
//            );
//            table6.addCell(new Cell().add("")
//                    .setTextAlignment(TextAlignment.LEFT)
//                    .setBorder(Border.NO_BORDER)
//            );
//            table6.addCell(new Cell().add("Qualification : \t")
//                    .setTextAlignment(TextAlignment.RIGHT)
//                    .setBorder(Border.NO_BORDER)
//            );
//            table6.addCell(new Cell().add(mstVisit.getVisitStaffId().getStaffEducation())
//                    .setTextAlignment(TextAlignment.RIGHT)
//                    .setBorder(Border.NO_BORDER)
//            );
//            document.add(table6);
//            float[] columnWidths7 = new float[]{8, 52, 28, 15, 17};
//            Table table7 = new Table(columnWidths7);
//            table7.setWidthPercent(100);
//            table7.addCell(new Cell().add("Email : \t ")
//                    .setTextAlignment(TextAlignment.LEFT)
//                    .setBorder(Border.NO_BORDER)
//            );
//            table7.addCell(new Cell().add(mstVisit.getVisitStaffId().getStaffUserId().getUserEmail())
//                    .setTextAlignment(TextAlignment.LEFT)
//                    .setBorder(Border.NO_BORDER)
//            );
//            table7.addCell(new Cell().add("")
//                    .setTextAlignment(TextAlignment.LEFT)
//                    .setBorder(Border.NO_BORDER)
//            );
//            table7.addCell(new Cell().add("Mobile : \t")
//                    .setTextAlignment(TextAlignment.RIGHT)
//                    .setBorder(Border.NO_BORDER)
//            );
//            table7.addCell(new Cell().add(mstVisit.getVisitStaffId().getStaffUserId().getUserMobile())
//                    .setTextAlignment(TextAlignment.RIGHT)
//                    .setBorder(Border.NO_BORDER)
//            );
//            document.add(table7);
            System.out.println("In GeneratePDF ....");
            System.out.println("Visit History : " + temrVisitHistory);
            if (temrVisitHistory != null) {
                Paragraph paragraphDocAdvice = new Paragraph("Patient History: ")
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraphDocAdvice);
                String patientHistory = html2text(temrVisitHistory.getVhContent());
                Paragraph para = new Paragraph("" + patientHistory)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f)
                        .setMarginLeft(20f);
                document.add(para);
            }

            if (!temrVitalList.isEmpty() && temrVitalList != null) {
                Paragraph paragraph15 = new Paragraph("Vitals : ")
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraph15);
                float[] columnWidths5 = new float[]{5f, 8f, 8f, 10f, 8f, 10f, 10f, 8f, 10f, 10f, 8f, 10f, 10f, 10f, 10f, 15f};
                Table table15 = new Table(columnWidths5);
                table15.setWidthPercent(100);
                table15.addCell(new Cell().add("Sr. No."));
                table15.addCell(new Cell().add("Height"));
                table15.addCell(new Cell().add("Weight"));
                table15.addCell(new Cell().add("BMI"));
                table15.addCell(new Cell().add("Body Temp"));
                table15.addCell(new Cell().add("Pulse"));
                table15.addCell(new Cell().add("RR"));
                table15.addCell(new Cell().add("Sys BP"));
                table15.addCell(new Cell().add("Dia BP"));
                table15.addCell(new Cell().add("MAP"));
                table15.addCell(new Cell().add("SPO2"));
                table15.addCell(new Cell().add("Blood Glucose"));
                table15.addCell(new Cell().add("Hemoglobin"));
                table15.addCell(new Cell().add("FAT"));
                table15.addCell(new Cell().add("Muscle Mass"));
                table15.addCell(new Cell().add("Remark"));
                int index = 1;
                for (TemrVital temrVital : temrVitalList) {
                    table15.addCell(new Cell().add(String.valueOf(index)));
                    table15.addCell(new Cell().add((temrVital.getVitalHeight() != null) ? temrVital.getVitalHeight() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalWeight() != null) ? temrVital.getVitalWeight() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalBmi() != null) ? temrVital.getVitalBmi() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalBodyTemp() != null) ? temrVital.getVitalBodyTemp() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalPulse() != null) ? temrVital.getVitalPulse() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalRespirationRate() != null) ? temrVital.getVitalRespirationRate() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalSysBp() != null) ? temrVital.getVitalSysBp() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalDiaBp() != null) ? temrVital.getVitalDiaBp() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalMap() != null) ? temrVital.getVitalMap() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalSpo2() != null) ? temrVital.getVitalSpo2() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalBloodGlucose() != null) ? temrVital.getVitalBloodGlucose() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalHimoglobin() != null) ? temrVital.getVitalHimoglobin() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalFat() != null) ? temrVital.getVitalFat() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalMuscleMass() != null) ? temrVital.getVitalMuscleMass() : ""));
                    table15.addCell(new Cell().add((temrVital.getVitalRemark() != null) ? temrVital.getVitalRemark() : ""));
                    index++;
                }
                table15.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table15);
            }

            if (!temrVisitChiefComplaintList.isEmpty() && temrVisitChiefComplaintList != null) {
                Paragraph paragraph3 = new Paragraph("Chief Complaints : ")
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraph3);
                float[] columnWidths1 = new float[]{5f, 40f, 10f, 10f};
                Table table3 = new Table(columnWidths1);
                table3.setWidthPercent(100);
                table3.addCell(new Cell().add("Sr. No."));
                table3.addCell(new Cell().add("Name"));
                table3.addCell(new Cell().add("Duration"));
                table3.addCell(new Cell().add("Unit"));
                table3.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table3);
                //float[] column = new float[] {10f, 10f,10f, 10f, 5f, 10f, 20f};
                Table table4 = new Table(columnWidths1);
                table4.setWidthPercent(100);
                int index = 1;
                for (TemrVisitChiefComplaint temrVisitChiefComplaint : temrVisitChiefComplaintList) {
                    table4.addCell(new Cell().add(String.valueOf(index)));
                    System.out.println("vc_id = " + temrVisitChiefComplaint.getVccId());
                    if (temrVisitChiefComplaint.getVccCcId() != null) {
                        table4.addCell(new Cell().add((temrVisitChiefComplaint.getVccCcId().getCcName() != null) ? temrVisitChiefComplaint.getVccCcId().getCcName() : "").setTextAlignment(TextAlignment.LEFT));
                    } else {
                        table4.addCell(new Cell().add(temrVisitChiefComplaint.getVsChiefComplaint() != null ? temrVisitChiefComplaint.getVsChiefComplaint() : "").setTextAlignment(TextAlignment.LEFT));
                    }
                    table4.addCell(new Cell().add((temrVisitChiefComplaint.getVccCcNo() != null) ? temrVisitChiefComplaint.getVccCcNo() : ""));
                    table4.addCell(new Cell().add((temrVisitChiefComplaint.getVccCcUnit() != null) ? temrVisitChiefComplaint.getVccCcUnit() : ""));
                    index++;
                }
                table4.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table4);
            }
            if (!temrVisitSymptomList.isEmpty() && temrVisitSymptomList != null) {
                Paragraph paragraph9 = new Paragraph("Symptoms : ")
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraph9);
                float[] columnWidths9 = new float[]{5f, 40f, 10f, 10f};
                Table table9 = new Table(columnWidths9);
                table9.setWidthPercent(100);
                table9.addCell(new Cell().add("Sr. No."));
                table9.addCell(new Cell().add("Name"));
                table9.addCell(new Cell().add("Duration"));
                table9.addCell(new Cell().add("Unit"));
                table9.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table9);
                //float[] column = new float[] {10f, 10f,10f, 10f, 5f, 10f, 20f};
                Table table10 = new Table(columnWidths9);
                table10.setWidthPercent(100);
                int index = 1;
                for (TemrVisitSymptom temrVisitSymptom : temrVisitSymptomList) {
                    table10.addCell(new Cell().add(String.valueOf(index)));
                    if (temrVisitSymptom.getVsSymptomId() != null) {
                        table10.addCell(new Cell().add((temrVisitSymptom.getVsSymptomId().getSymptomName() != null) ? temrVisitSymptom.getVsSymptomId().getSymptomName() : "").setTextAlignment(TextAlignment.LEFT));
                    } else {
                        table10.addCell(new Cell().add((temrVisitSymptom.getVsSignSymptoms() != null) ? temrVisitSymptom.getVsSignSymptoms() : "").setTextAlignment(TextAlignment.LEFT));
                    }
                    table10.addCell(new Cell().add((temrVisitSymptom.getVsNo() != null) ? temrVisitSymptom.getVsNo() : ""));
                    table10.addCell(new Cell().add((temrVisitSymptom.getVsUnit() != null) ? temrVisitSymptom.getVsUnit() : ""));
                    index++;
                }
                table10.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table10);
            }
            if ((!temrVisitNonDrugAllergyList.isEmpty() && temrVisitNonDrugAllergyList != null) || (!temrVisitAllergyList.isEmpty() && temrVisitAllergyList != null)) {
                Paragraph paragraph11 = new Paragraph("Allergies : ")
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraph11);
                float[] columnWidths11 = new float[]{5f, 40f, 20f, 20f};
                Table table11 = new Table(columnWidths11);
                table11.setWidthPercent(100);
                table11.addCell(new Cell().add("Sr. No."));
                table11.addCell(new Cell().add("Name"));
                table11.addCell(new Cell().add("Severity"));
                table11.addCell(new Cell().add("Type"));
                table11.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table11);
                //float[] column = new float[] {10f, 10f,10f, 10f, 5f, 10f, 20f};
                Table table12 = new Table(columnWidths11);
                table12.setWidthPercent(100);
                int index = 1;
                for (TemrVisitNonDrugAllergy temrVisitNonDrugAllergy : temrVisitNonDrugAllergyList) {
                    table12.addCell(new Cell().add(String.valueOf(index)));
                    table12.addCell(new Cell().add((temrVisitNonDrugAllergy.getVndaNdaId().getNdaName() != null) ? temrVisitNonDrugAllergy.getVndaNdaId().getNdaName() : "").setTextAlignment(TextAlignment.LEFT));
                    table12.addCell(new Cell().add((temrVisitNonDrugAllergy.getVndaAsId().getAsName() != null) ? temrVisitNonDrugAllergy.getVndaAsId().getAsName() : "").setTextAlignment(TextAlignment.CENTER));
                    table12.addCell(new Cell().add("Non-Drug").setTextAlignment(TextAlignment.CENTER));
                    index++;
                }
                for (TemrVisitAllergy temrVisitAllergy : temrVisitAllergyList) {
                    table12.addCell(new Cell().add(String.valueOf(index)));
                    table12.addCell(new Cell().add((temrVisitAllergy.getVaCompoundId().getCompoundName() != null) ? temrVisitAllergy.getVaCompoundId().getCompoundName() : "").setTextAlignment(TextAlignment.LEFT));
                    table12.addCell(new Cell().add((temrVisitAllergy.getVaAsId().getAsName() != null) ? temrVisitAllergy.getVaAsId().getAsName() : "").setTextAlignment(TextAlignment.CENTER));
                    table12.addCell(new Cell().add("Drug").setTextAlignment(TextAlignment.CENTER));
                    index++;
                }
                table12.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table12);
            }
//            if(!temrVisitAllergyList.isEmpty() && temrVisitAllergyList != null) {
//                Paragraph paragraph13 = new Paragraph("Allergys : ")
//                        .setBackgroundColor(Color.BLUE)
//                        .setFontColor(Color.WHITE)
//                        .setTextAlignment(TextAlignment.LEFT)
//                        .setFontSize(14f);
//                document.add(paragraph13);
//
//                float[] columnWidths13 = new float[] {5f,40f};
//
//                Table table13 = new Table(columnWidths13);
//                table13.setWidthPercent(100);
//
//                table13.addCell(new Cell().add("Sr. No."));
//                table13.addCell(new Cell().add("Name"));
//
//                table13.setTextAlignment(TextAlignment.CENTER)
//                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
//
//                document.add(table13);
//
//                //float[] column = new float[] {10f, 10f,10f, 10f, 5f, 10f, 20f};
//
//                Table table14 = new Table(columnWidths13);
//                table14.setWidthPercent(100);
//                int index = 1;
//                for (TemrVisitAllergy temrVisitAllergy : temrVisitAllergyList)
//                {
//
//                    table14.addCell(new Cell().add(String.valueOf(index)));
//                    table14.addCell(new Cell().add(temrVisitAllergy.getVaCompoundId().getCompoundName()).setTextAlignment(TextAlignment.LEFT));
////                    table12.addCell(new Cell().add(temrVisitSymptom.getVsNo()));
////                    table12.addCell(new Cell().add(temrVisitSymptom.getVsUnit()));
//                    index++;
//                }
//
//                table14.setTextAlignment(TextAlignment.CENTER)
//                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
//
//                document.add(table14);
//            }

            System.out.println("Visit Diagnosis => " + temrVisitDiagnosisList);
            if (!temrVisitDiagnosisList.isEmpty() && temrVisitDiagnosisList != null) {
                Paragraph paragraph17 = new Paragraph("Diagnosis : ")
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraph17);
                float[] columnWidths17 = new float[]{5f, 15f, 20f, 15f, 15f, 15f, 15f};
                Table table17 = new Table(columnWidths17);
                table17.setWidthPercent(100);
                table17.addCell(new Cell().add("Sr No."));
                table17.addCell(new Cell().add("Code"));
                table17.addCell(new Cell().add("Description"));
                table17.addCell(new Cell().add("Disease Type"));
                table17.addCell(new Cell().add("Sub Category"));
                table17.addCell(new Cell().add("Category"));
                table17.addCell(new Cell().add("Status"));
                int index = 1;
                for (TemrVisitDiagnosis temrVisitDiagnosis : temrVisitDiagnosisList) {
                    table17.addCell(new Cell().add(String.valueOf(index)));
                    table17.addCell(new Cell().add((temrVisitDiagnosis.getVdIcId().getIcCode() != null) ? temrVisitDiagnosis.getVdIcId().getIcCode() : ""));
                    table17.addCell(new Cell().add((temrVisitDiagnosis.getVdIcId().getIcCode() != null) ? temrVisitDiagnosis.getVdIcId().getIcDescription() : "").setTextAlignment(TextAlignment.LEFT));
                    table17.addCell(new Cell().add((temrVisitDiagnosis.getVdIcId().getIcCode() != null) ? temrVisitDiagnosis.getVdIcId().getIcDtId().getDtName() : "").setTextAlignment(TextAlignment.LEFT));
                    table17.addCell(new Cell().add((temrVisitDiagnosis.getVdIcId().getIcCode() != null) ? temrVisitDiagnosis.getVdIcId().getIcDscId().getDcsName() : "").setTextAlignment(TextAlignment.LEFT));
                    table17.addCell(new Cell().add((temrVisitDiagnosis.getVdIcId().getIcCode() != null) ? temrVisitDiagnosis.getVdIcId().getIcDcId().getDcName() : "").setTextAlignment(TextAlignment.LEFT));
                    table17.addCell(new Cell().add((temrVisitDiagnosis.getVdIsFinalDiagnosis()) ? "Final" : "Provisional"));
                    index++;
                }
                table17.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table17);
            }

            if (!temrVisitInvestigationList.isEmpty() && temrVisitInvestigationList != null) {
                Paragraph paragraph19 = new Paragraph("Investigation : ")
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraph19);
                float[] columnWidths19 = new float[]{5f,60f,15f,15f};
                Table table19 = new Table(columnWidths19);
                table19.setWidthPercent(100);
                table19.addCell(new Cell().add("Sr. No."));
                table19.addCell(new Cell().add("Name"));
                table19.addCell(new Cell().add("Service Code"));
                table19.addCell(new Cell().add("Group"));
                table19.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table19);
                Table table20 = new Table(columnWidths19);
                table20.setWidthPercent(100);
                int index = 1;
                for (TemrVisitInvestigation temrVisitInvestigation : temrVisitInvestigationList) {
                    System.out.println("Service Name => " + temrVisitInvestigation.getViServiceId().getServiceName());
                    table20.addCell(new Cell().add(String.valueOf(index)));
                    table20.addCell(new Cell().add((temrVisitInvestigation.getViServiceId().getServiceName() != null) ? temrVisitInvestigation.getViServiceId().getServiceName() : "").setTextAlignment(TextAlignment.LEFT));
                    table20.addCell(new Cell().add((temrVisitInvestigation.getViServiceId().getServiceCode() != null) ? temrVisitInvestigation.getViServiceId().getServiceCode() : "").setTextAlignment(TextAlignment.LEFT));
                    table20.addCell(new Cell().add((temrVisitInvestigation.getViServiceId().getServiceGroupId() != null) ? temrVisitInvestigation.getViServiceId().getServiceGroupId().getGroupName() : "").setTextAlignment(TextAlignment.LEFT));
                    index++;
                }
                table20.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table20);
            }

//            System.out.println("Item Prescription => " + temrItemPrescriptionList + " < " + !temrItemPrescriptionList.isEmpty() + " < > " + (temrItemPrescriptionList != null) + " == " + (!temrItemPrescriptionList.isEmpty() && (temrItemPrescriptionList != null)));
            if (!temrItemPrescriptionList.isEmpty() && (temrItemPrescriptionList != null)) {
                Paragraph paragraph17 = new Paragraph("Prescriptions : ")
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraph17);
                float[] columnWidths17 = new float[]{5f, 15f, 10f, 8f, 6f, 10f, 20f};
                Table table17 = new Table(columnWidths17);
                table17.setWidthPercent(100);
                table17.addCell(new Cell().add("Sr No."));
                table17.addCell(new Cell().add("Name"));
//                table17.addCell(new Cell().add("Strength"));
                table17.addCell(new Cell().add("Frequency"));
                table17.addCell(new Cell().add("Route"));
                table17.addCell(new Cell().add("Quantity"));
                table17.addCell(new Cell().add("Instruction"));
                table17.addCell(new Cell().add("Notes"));
                table17.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table17);
                Table table18 = new Table(columnWidths17);
                table18.setWidthPercent(100);
                int index = 1;
                for (TemrItemPrescription item : temrItemPrescriptionList) {
                    if (item != null) {
                        table18.addCell(new Cell().add(String.valueOf(index)));
//                        table18.addCell(new Cell().add((item.getItemName() != null) ? item.getItemName() + " " + ((item.getIpInvItemId().getItemStrength() != null) ? item.getIpInvItemId().getItemStrength() : "") : "")
                        table18.addCell(new Cell().add((item.getIpInvItemId() != null) ? item.getIpInvItemId().getItemName() : "")
                                .setTextAlignment(TextAlignment.LEFT));
//                    table18.addCell(new Cell().add((item.getIpInvItemId().getItemStrength() != null) ? item.getIpInvItemId().getItemStrength() : ""));
//                        System.out.println("item.getIpDfId() => " + item.getIpDfId());
                        table18.addCell(new Cell().add((item.getIpDfId() != null) ? item.getIpDfId().getDfName() : ""));
                        table18.addCell(new Cell().add((item.getIpRouteId() != null) ? item.getIpRouteId().getRouteName() : ""));
                        table18.addCell(new Cell().add((item.getIpQuantity() != null) ? item.getIpQuantity() : ""));
                        table18.addCell(new Cell().add((item.getIpDiId() != null) ? item.getIpDiId().getDiName() : "").setTextAlignment(TextAlignment.LEFT));
                        table18.addCell(new Cell().add((item.getIpinstruction() != null) ? item.getIpinstruction() : "").setTextAlignment(TextAlignment.LEFT));
                        index++;
                    }
                }
                table18.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(table18);
            }

            System.out.println("Visit Doctor Notes => " + temrVisitDoctorsNotes);
            if (!temrVisitDoctorsNotes.isEmpty() && temrVisitDoctorsNotes != null) {
                Paragraph paragraphDocNotes = new Paragraph("Doctor Notes : ")
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraphDocNotes);
                float[] columnWidthsDocNotes = new float[]{5f, 35f};
                Table tableDocNotes = new Table(columnWidthsDocNotes);
                tableDocNotes.setWidthPercent(100);
                tableDocNotes.addCell(new Cell().add("Sr No."));
                tableDocNotes.addCell(new Cell().add("Doctor Notes"));
                int index = 1;
                for (TemrVisitDoctorsNote temrVisitDoctorsNote : temrVisitDoctorsNotes) {
                    tableDocNotes.addCell(new Cell().add(String.valueOf(index)));
                    tableDocNotes.addCell(new Cell().add((temrVisitDoctorsNote.getVadnId().getDnEngNote() != null) ? temrVisitDoctorsNote.getVadnId().getDnEngNote() : "").setTextAlignment(TextAlignment.LEFT));
                    index++;
                }
                tableDocNotes.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(tableDocNotes);
            }

            System.out.println("Doctor Addvice => " + temrTimeline.getTimelineDoctorAdvice());
            if (temrTimeline.getTimelineDoctorAdvice() != null) {
                Paragraph paragraphDocAdvice = new Paragraph("Doctor's Advice: ")
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraphDocAdvice);
                float[] columnWidthsDocNotes = new float[]{40f};
                Table tableDocAdvice = new Table(columnWidthsDocNotes);
                tableDocAdvice.setWidthPercent(100);
                tableDocAdvice.addCell(new Cell().add("Doctor Advice"));
                tableDocAdvice.addCell(new Cell().add("" + temrTimeline.getTimelineDoctorAdvice()));
//                Paragraph para = new Paragraph("" + temrTimeline.getTimelineDoctorAdvice())
//                        .setTextAlignment(TextAlignment.LEFT)
//                        .setFontSize(14f)
//                        .setMarginLeft(20f);
//                document.add(para);
                tableDocAdvice.setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                document.add(tableDocAdvice);
            }

            System.out.println("Follow Up Date => " + temrTimeline.getTimelineFollowDate());
            if (temrTimeline.getTimelineFollowDate() != null) {
                Date followDate = temrTimeline.getTimelineFollowDate();
                Paragraph paragraphFollowUp = new Paragraph("Follow Up Date : " + ddmmyyyyFormater.format(followDate))
//                        .setBackgroundColor(Color.BLUE)
                        .setBackgroundColor(new DeviceRgb(51,153,255))
                        .setFontColor(Color.WHITE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(14f);
                document.add(paragraphFollowUp);
            }

            Paragraph paragraphnist1 = new Paragraph("Instructions")
                    .setFontColor(Color.BLACK)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12f)
                    .setBold()
                    .setMarginTop(10f);
            document.add(paragraphnist1);
            Paragraph paragraphnist2 = new Paragraph("1. This is a Prescription generated by computer.")
                    .setFontColor(Color.BLACK)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12f)
                    .setItalic()
                    .setMarginTop(10f);
            document.add(paragraphnist2);
            Paragraph paragraphnist3 = new Paragraph("2. Please remember the MR No. '" + mstVisit.getVisitPatientId().getPatientMrNo() + "' for future reference.")
                    .setFontColor(Color.BLACK)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12f)
                    .setItalic()
                    .setMarginTop(10f);
            document.add(paragraphnist3);
            Paragraph paragraphnist4 = new Paragraph("3. In case of any Adverse Reaction Please visit " + mstVisit.getVisitUnitId().getUnitName())
                    .setFontColor(Color.BLACK)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12f)
                    .setItalic()
                    .setMarginTop(10f);
           document.add(paragraphnist4);




            //finalized by with signatire code
            System.out.println("Finalized Staff Signature : " + temrTimeline.getTimelineEMRFinalStaffId().getStaffSignName());
            if (temrTimeline.getTimelineEMRFinalStaffId().getStaffSignName() != null && !temrTimeline.getTimelineEMRFinalStaffId().getStaffSignName().isEmpty()) {
                File signFile = new File(filePath + temrTimeline.getTimelineEMRFinalStaffId().getStaffSignName());
                System.out.println("signFile exist => " + signFile.exists());

                float[] columnWidt = new float[]{80, 80, 80};
                com.itextpdf.layout.element.Table table5 = new Table(columnWidt);
                table5.setWidthPercent(100);

                Cell cell1 = new Cell();
                cell1.add("").setBorder(Border.NO_BORDER);
                table5.addCell(cell1);

                Cell cell2 = new Cell();
                cell2.add("").setBorder(Border.NO_BORDER);
                table5.addCell(cell2);

                Cell cell3 = new Cell();



                cell3.add("Finalized By : ").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
                if (temrTimeline.getTimelineEMRFinalStaffId().getStaffSignName() != null && !temrTimeline.getTimelineEMRFinalStaffId().getStaffSignName().isEmpty()) {
                    if (signFile.exists()) {
                        String imageFile = filePath + temrTimeline.getTimelineEMRFinalStaffId().getStaffSignName();
                        ImageData data = ImageDataFactory.create(imageFile);
                        Image img = new Image(data);
                        img.setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER);
                        cell3.add(img).setBorder(Border.NO_BORDER);

                    } else {
                        System.out.println("File Not Found in specific folder");
                    }
                    }
                String erNo = "";
                if (temrTimeline.getTimelineEMRFinalStaffId().getStaffErNo() != null && !temrTimeline.getTimelineEMRFinalStaffId().getStaffErNo().isEmpty()) {
                    erNo = temrTimeline.getTimelineEMRFinalStaffId().getStaffErNo();
                }
                cell3.add("Dr." + temrTimeline.getTimelineEMRFinalStaffId().getStaffUserId().getUserFullname() + "  " + erNo + "  " + " \n ( " + temrTimeline.getTimelineEMRFinalStaffId().getStaffEducation() + " ) ").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
                table5.addCell(cell3);

                document.add(table5);
            } else {
                float[] columnWidt = new float[]{80, 80, 80};
                com.itextpdf.layout.element.Table table5 = new Table(columnWidt);
                table5.setWidthPercent(100);
                Cell cell1 = new Cell();
                cell1.add("").setBorder(Border.NO_BORDER);
                table5.addCell(cell1);
                Cell cell2 = new Cell();
                cell2.add("").setBorder(Border.NO_BORDER);
                table5.addCell(cell2);
                Cell cell3 = new Cell();
                cell3.add("Finalized By : ").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setMarginTop(20f);
                String erNo = "";
                if (temrTimeline.getTimelineEMRFinalStaffId().getStaffErNo() != null && !temrTimeline.getTimelineEMRFinalStaffId().getStaffErNo().isEmpty()) {
                    erNo = temrTimeline.getTimelineEMRFinalStaffId().getStaffErNo();
                }
                cell3.add("Dr." + temrTimeline.getTimelineEMRFinalStaffId().getStaffUserId().getUserFullname() + "  " + erNo + "  " + " \n ( " + temrTimeline.getTimelineEMRFinalStaffId().getStaffEducation() + " ) ").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
                table5.addCell(cell3);
                document.add(table5);
            }



            Paragraph paragraph25 = new Paragraph("Technology supported by Tata Trusts")
                    .setFontColor(Color.BLACK)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12f)
                    .setItalic()
                    .setMarginTop(20f);
            document.add(paragraph25);

            document.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PDF Generated";
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}



