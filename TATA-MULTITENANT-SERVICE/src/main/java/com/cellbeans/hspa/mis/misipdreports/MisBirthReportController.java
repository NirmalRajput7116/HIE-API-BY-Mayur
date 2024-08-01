package com.cellbeans.hspa.mis.misipdreports;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/mis_ipd_birth_report")
public class MisBirthReportController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CreateReport createReport;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @RequestMapping("getipdbirthReport/{unitList}/{doctorId}/{fromdate}/{todate}")
    public ResponseEntity searchBirthReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisBirthReportSearchDTO misbirthreportSearchDTO, @PathVariable String[] unitList, @PathVariable String doctorId, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name,mp.patient_mr_no, ifnull(ta.admission_ipd_no,'')as IpdNo, " +
                " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,''))as PatientName, " +
                " ifnull(ta.admission_date,'')as DOA, ifnull(admission_discharge_date,'')as DischardeDate, " +
                " ifnull(tbc.bc_birth_date,'')as DateOfBirth , " +
                " concat(ifnull(msu.user_firstname,''),'', ifnull(msu.user_lastname,''))as ConsultDocName, " +
                " re.re_name as RefDoctorName, " +
                " CONCAT(FLOOR(HOUR(TIMEDIFF(ta.admission_date,  ifNull(admission_discharge_date,now()))) / 24), ' days ', " +
                " MOD(HOUR(TIMEDIFF(ta.admission_date, ifNull(admission_discharge_date,now()))), 24), ' hours ', " +
                " MINUTE(TIMEDIFF(ta.admission_date, ifNull(admission_discharge_date,now()))), ' minutes') as ALOS " +
                " FROM tnst_birth_certificate tbc " +
                " left join trn_admission ta on tbc.bc_admission_id=ta.admission_id " +
                " left join mst_visit mv on tbc.bc_visit_id=mv.visit_id " +
                " left join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                " left join mst_user mu on mp.patient_user_id=mu.user_id " +
                " left join mst_referring_entity re on mv.refer_by=re.re_id " +
                " left join mst_staff ms on tbc.bc_declared_by_staff_id=ms.staff_id " +
                " left join mst_user msu on ms.staff_user_id=msu.user_id " +
                " left join mst_unit un on tbc.bc_unit_id=un.unit_id ";
        String CountQuery = "";
        String columnName = "";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " where tbc.bc_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misbirthreportSearchDTO.getTodaydate()) {
            Query += " and (date(ta.admission_date)=curdate()) ";
        } else {
            Query += " and (date(ta.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!doctorId.equals("null") && !doctorId.equals("0")) {
            Query += " and ms.staff_user_id =  " + doctorId + " ";
        }
        if (!misbirthreportSearchDTO.getMrNo().equals("")) {
            Query += " and  mp.patient_mr_no like  '%" + misbirthreportSearchDTO.getMrNo() + "%' ";
        }
        if (!misbirthreportSearchDTO.getPatientName().equals("")) {
            Query += " and (mu.user_firstname like '%" + misbirthreportSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + misbirthreportSearchDTO.getPatientName() + "%') ";

        }

        System.out.println("Birth Report"+Query);
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,patient_mr_no,IpdNo,PatientName,DOA,DischardeDate,DateOfBirth,ConsultDocName,RefDoctorName,ALOS";
        Query += " limit " + ((misbirthreportSearchDTO.getOffset() - 1) * misbirthreportSearchDTO.getLimit()) + "," + misbirthreportSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }
    //Birth Service code End
    //Birth Report Print Service code Start

    @RequestMapping("getipdbirthReportPrint/{unitList}/{doctorId}/{fromdate}/{todate}")
    public String searchBirthReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisBirthReportSearchDTO misbirthreportSearchDTO, @PathVariable String[] unitList, @PathVariable String doctorId, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name,mp.patient_mr_no, ifnull(ta.admission_ipd_no,'')as IpdNo, " +
                " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,''))as PatientName, " +
                " ifnull(ta.admission_date,'')as DOA, ifnull(admission_discharge_date,'')as DischardeDate, " +
                " ifnull(tbc.bc_birth_date,'')as DateOfBirth , " +
                " concat(ifnull(msu.user_firstname,''),'', ifnull(msu.user_lastname,''))as ConsultDocName, " +
                " re.re_name as RefDoctorName, " +
                " CONCAT(FLOOR(HOUR(TIMEDIFF(ta.admission_date,  ifNull(admission_discharge_date,now()))) / 24), ' days ', " +
                " MOD(HOUR(TIMEDIFF(ta.admission_date, ifNull(admission_discharge_date,now()))), 24), ' hours ', " +
                " MINUTE(TIMEDIFF(ta.admission_date, ifNull(admission_discharge_date,now()))), ' minutes') as ALOS " +
                " FROM tnst_birth_certificate tbc " +
                " left join trn_admission ta on tbc.bc_admission_id=ta.admission_id " +
                " left join mst_visit mv on tbc.bc_visit_id=mv.visit_id " +
                " left join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                " left join mst_user mu on mp.patient_user_id=mu.user_id " +
                " left join mst_referring_entity re on mv.refer_by=re.re_id " +
                " left join mst_staff ms on tbc.bc_declared_by_staff_id=ms.staff_id " +
                " left join mst_user msu on ms.staff_user_id=msu.user_id " +
                " left join mst_unit un on tbc.bc_unit_id=un.unit_id ";
        String CountQuery = "";
        String columnName = "";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " where tbc.bc_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misbirthreportSearchDTO.getTodaydate()) {
            Query += " and (date(ta.admission_date)=curdate()) ";
        } else {
            Query += " and (date(ta.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!doctorId.equals("null") && !doctorId.equals("0")) {
            Query += " and ms.staff_user_id =  " + doctorId + " ";
        }
        if (!misbirthreportSearchDTO.getMrNo().equals("")) {
            Query += " and  mp.patient_mr_no like  '%" + misbirthreportSearchDTO.getMrNo() + "%' ";
        }
        if (!misbirthreportSearchDTO.getPatientName().equals("")) {
            Query += " and (mu.user_firstname like '%" + misbirthreportSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + misbirthreportSearchDTO.getPatientName() + "%') ";

        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,patient_mr_no,IpdNo,PatientName,DOA,DischardeDate,DateOfBirth,ConsultDocName,RefDoctorName,ALOS";
        // Query += " limit " + ((misbirthreportSearchDTO.getOffset() - 1) * misbirthreportSearchDTO.getLimit()) + "," + misbirthreportSearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misbirthreportSearchDTO.getUnitId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        return createReport.createJasperReportPDFByJson("IpdBirthReport", jsonArray.toString());
        //return createReport.createJasperReportPDFByJson("IpdBirthReport", createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    @RequestMapping("getipddeathReport/{unitList}/{doctorId}/{fromdate}/{todate}")
    public ResponseEntity searchDeathReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisDeathReportSearchDTO misdeathreportSearchDTO, @PathVariable String[] unitList, @PathVariable String doctorId, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name,ifnull(mp.patient_mr_no,'')as MRNO,ifnull(ta.admission_ipd_no,'')as IPDNO, " +
                " concat(ifnull(u.user_firstname,''),' ',ifnull(u.user_lastname,''))as PatientName, " +
                " ifnull(ta.admission_date,'')as AdmissionDate,ifnull(tdc.dc_death_date,'')DOD, " +
                " concat(ifnull(msu.user_firstname,''),' ', ifnull(msu.user_lastname,''))as ConsultDocName, " +
                " ifnull(tdc.dc_death_time,'')DTime,ifnull(mre.re_name,'')as RefDoctorName, " +
                " CONCAT(FLOOR(HOUR(TIMEDIFF(ta.admission_date,  ifNull(tdc.dc_death_date,now()))) / 24), ' days ', " +
                " MOD(HOUR(TIMEDIFF(ta.admission_date, ifNull(tdc.dc_death_date,now()))), 24), ' hours ', " +
                " MINUTE(TIMEDIFF(ta.admission_date, ifNull(tdc.dc_death_date,now()))), ' minutes') as ALOS " +
                " FROM tnst_death_certificate tdc " +
                " left join trn_admission ta on tdc.dc_admission_id=ta.admission_id " +
                " left join mst_visit v on tdc.dc_visit_id=v.visit_id " +
                " left join mst_patient mp on v.visit_patient_id=mp.patient_id " +
                " left join mst_user u on mp.patient_user_id=u.user_id " +
                " left join mst_referring_entity mre on v.refer_by=mre.re_id " +
                " left join mst_staff ms on tdc.dc_declared_by_staff_id=ms.staff_id " +
                " left join mst_user msu on ms.staff_user_id=msu.user_id " +
                " left join mst_unit un on tdc.dc_unit_id=un.unit_id  ";
        String CountQuery = "";
        String columnName = "";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " where tdc.dc_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misdeathreportSearchDTO.getTodaydate()) {
            Query += " and (date(ta.admission_date)=curdate()) ";
        } else {
            Query += " and (date(ta.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!doctorId.equals("null") && !doctorId.equals("0")) {
            Query += " and ms.staff_user_id =  " + doctorId + " ";
        }
        if (!misdeathreportSearchDTO.getMrNo().equals("")) {
            Query += " and  mp.patient_mr_no like  '%" + misdeathreportSearchDTO.getMrNo() + "%' ";
        }
        if (!misdeathreportSearchDTO.getPatientName().equals("")) {
            Query += " and (u.user_firstname like '%" + misdeathreportSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + misdeathreportSearchDTO.getPatientName() + "%') ";

        }
        System.out.println("Death Report"+Query);
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,MRNO,IPDNO,PatientName,AdmissionDate,DOD,ConsultDocName,DTime,RefDoctorName,ALOS";
        Query += " limit " + ((misdeathreportSearchDTO.getOffset() - 1) * misdeathreportSearchDTO.getLimit()) + "," + misdeathreportSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }//Death Report Service code End

    //getIpdBirthReportPrintDeath Report Print Service code Start
    @RequestMapping("getipddeathReportPrint/{unitList}/{doctorId}/{fromdate}/{todate}")
    public String searchDeathReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisDeathReportSearchDTO misdeathreportSearchDTO, @PathVariable String[] unitList, @PathVariable String doctorId, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name,ifnull(mp.patient_mr_no,'')as MRNO,ifnull(ta.admission_ipd_no,'')as IPDNO, " +
                " concat(ifnull(u.user_firstname,''),' ',ifnull(u.user_lastname,''))as PatientName, " +
                " ifnull(ta.admission_date,'')as AdmissionDate,ifnull(tdc.dc_death_date,'')DOD, " +
                " concat(ifnull(msu.user_firstname,''),' ', ifnull(msu.user_lastname,''))as ConsultDocName, " +
                " ifnull(tdc.dc_death_time,'')DTime,ifnull(mre.re_name,'')as RefDoctorName, " +
                " CONCAT(FLOOR(HOUR(TIMEDIFF(ta.admission_date,  ifNull(tdc.dc_death_date,now()))) / 24), ' days ', " +
                " MOD(HOUR(TIMEDIFF(ta.admission_date, ifNull(tdc.dc_death_date,now()))), 24), ' hours ', " +
                " MINUTE(TIMEDIFF(ta.admission_date, ifNull(tdc.dc_death_date,now()))), ' minutes') as ALOS " +
                " FROM tnst_death_certificate tdc " +
                " left join trn_admission ta on tdc.dc_admission_id=ta.admission_id " +
                " left join mst_visit v on tdc.dc_visit_id=v.visit_id " +
                " left join mst_patient mp on v.visit_patient_id=mp.patient_id " +
                " left join mst_user u on mp.patient_user_id=u.user_id " +
                " left join mst_referring_entity mre on v.refer_by=mre.re_id " +
                " left join mst_staff ms on tdc.dc_declared_by_staff_id=ms.staff_id " +
                " left join mst_user msu on ms.staff_user_id=msu.user_id " +
                " left join mst_unit un on tdc.dc_unit_id=un.unit_id  ";
        String CountQuery = "";
        String columnName = "";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " where tdc.dc_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misdeathreportSearchDTO.getTodaydate()) {
            Query += " and (date(ta.admission_date)=curdate()) ";
        } else {
            Query += " and (date(ta.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!doctorId.equals("null") && !doctorId.equals("0")) {
            Query += " and ms.staff_user_id =  " + doctorId + " ";
        }
        if (!misdeathreportSearchDTO.getMrNo().equals("")) {
            Query += " and  mp.patient_mr_no like  '%" + misdeathreportSearchDTO.getMrNo() + "%' ";
        }
        if (!misdeathreportSearchDTO.getPatientName().equals("")) {
            Query += " and (u.user_firstname like '%" + misdeathreportSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + misdeathreportSearchDTO.getPatientName() + "%') ";

        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,MRNO,IPDNO,PatientName,AdmissionDate,DOD,ConsultDocName,DTime,RefDoctorName,ALOS";
        Query += " limit " + ((misdeathreportSearchDTO.getOffset() - 1) * misdeathreportSearchDTO.getLimit()) + "," + misdeathreportSearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misdeathreportSearchDTO.getUnitId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        return createReport.createJasperReportPDFByJson("IpdDeathReport", jsonArray.toString());
        //  return createReport.createJasperReportPDFByJson("IpdDeathReport", createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }
    //Death Report Print Service code End

}