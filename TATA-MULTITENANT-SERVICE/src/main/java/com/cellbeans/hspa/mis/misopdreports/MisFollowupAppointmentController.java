package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
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
@RequestMapping("/mis_opd_followup_appointment")
public class MisFollowupAppointmentController {

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

    @Autowired
    MstUserRepository mstUserRepository;

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getfollowupappointmentReport/{unitList}/{specialityId}/{departmentId}/{sdId}/{staffId}/{fromdate}/{todate}")
    public ResponseEntity searchFollowupAppointment(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisFollowupAppointmentSearchDTO misfollowupappointmentSearchDTO, @PathVariable String[] unitList, @PathVariable String specialityId, @PathVariable String departmentId, @PathVariable String sdId, @PathVariable String staffId, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT u.unit_name,ifnull(tt.timeline_finalized_date,'')as finalizedDate ,p.patient_mr_no, " + " concat(us.user_firstname ,' ',us.user_lastname) as patientName, " + " ifnull(tt.timeline_follow_date,'')as FollowDate,concat(mu.user_firstname,' ',mu.user_lastname) as DoctorName, " + " us.user_mobile, ifnull(sp.speciality_name,'')Speciality ,md.department_name, ifnull(sd.sd_name,'')as subdepatment " + " FROM temr_timeline tt " + " left join tbill_bill_service bs on tt.timeline_service_id=bs.bs_id " + " left join tbill_bill tb on bs.bs_bill_id=tb.bill_id " + " left join mst_unit u on tb.tbill_unit_id=u.unit_id " + " left join mst_patient p on tt.timeline_patient_id=p.patient_id " + " left join mst_user us on p.patient_user_id=us.user_id " + " left join mst_staff st on tt.timeline_staff_id=st.staff_id " + " left join mst_user mu on st.staff_user_id=mu.user_id " + " left join mst_speciality sp on st.staff_speciality_id=sp.speciality_id " + " left join mbill_service ms on bs.bs_service_id=ms.service_id " + " left join mbill_group mg on ms.service_group_id=mg.group_id " + " left join mst_department md on mg.group_department_id=md.department_id " + " left join mst_sub_department sd on mg.group_sd_id=sd.sd_id ";
        String CountQuery = "";
        String columnName = "";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " where tb.tbill_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misfollowupappointmentSearchDTO.getTodaydate()) {
            Query += " and (date(tt.timeline_follow_date)=curdate()) ";
        } else {
            Query += " and (date(tt.timeline_follow_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!specialityId.equals("null") && !specialityId.equals("0")) {
            Query += " and st.staff_speciality_id =  " + specialityId + " ";
        }
        if (!departmentId.equals("null") && !departmentId.equals("0")) {
            Query += " and mg.group_department_id =  " + departmentId + " ";
        }
        if (!sdId.equals("null") && !sdId.equals("0")) {
            Query += " and mg.group_sd_id =  " + sdId + " ";
        }
        if (!staffId.equals("null") && !staffId.equals("0")) {
            Query += " and tt.timeline_staff_id =  " + staffId + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,finalizedDate,patient_mr_no,patientName,FollowDate,DoctorName,user_mobile,Speciality,department_name,subdepatment";
        Query += " limit " + ((misfollowupappointmentSearchDTO.getOffset() - 1) * misfollowupappointmentSearchDTO.getLimit()) + "," + misfollowupappointmentSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    @RequestMapping("getfollowupappointmentReportPrint/{unitList}/{specialityId}/{departmentId}/{sdId}/{staffId}/{fromdate}/{todate}")
    public String searchFollowupAppointmentPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisFollowupAppointmentSearchDTO misfollowupappointmentSearchDTO, @PathVariable String[] unitList, @PathVariable String specialityId, @PathVariable String departmentId, @PathVariable String sdId, @PathVariable String staffId, @PathVariable String fromdate, @PathVariable String todate) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT u.unit_name,ifnull(tt.timeline_finalized_date,'')as finalizedDate ,p.patient_mr_no, " + " concat(us.user_firstname ,' ',us.user_lastname) as patientName, " + " ifnull(tt.timeline_follow_date,'')as FollowDate,concat(mu.user_firstname,' ',mu.user_lastname) as DoctorName, " + " us.user_mobile, ifnull(sp.speciality_name,'')Speciality ,md.department_name,ifnull(sd.sd_name,'')as subdepatment " + " FROM temr_timeline tt " + " left join tbill_bill_service bs on tt.timeline_service_id=bs.bs_id " + " left join tbill_bill tb on bs.bs_bill_id=tb.bill_id " + " left join mst_unit u on tb.tbill_unit_id=u.unit_id " + " left join mst_patient p on tt.timeline_patient_id=p.patient_id " + " left join mst_user us on p.patient_user_id=us.user_id " + " left join mst_staff st on tt.timeline_staff_id=st.staff_id " + " left join mst_user mu on st.staff_user_id=mu.user_id " + " left join mst_speciality sp on st.staff_speciality_id=sp.speciality_id " + " left join mbill_service ms on bs.bs_service_id=ms.service_id " + " left join mbill_group mg on ms.service_group_id=mg.group_id " + " left join mst_department md on mg.group_department_id=md.department_id " + " left join mst_sub_department sd on mg.group_sd_id=sd.sd_id ";
        String CountQuery = "";
        String columnName = "";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " where tb.tbill_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misfollowupappointmentSearchDTO.getTodaydate()) {
            Query += " and (date(tt.timeline_follow_date)=curdate()) ";
        } else {
            Query += " and (date(tt.timeline_follow_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!specialityId.equals("null") && !specialityId.equals("0")) {
            Query += " and st.staff_speciality_id =  " + specialityId + " ";
        }
        if (!departmentId.equals("null") && !departmentId.equals("0")) {
            Query += " and mg.group_department_id =  " + departmentId + " ";
        }
        if (!sdId.equals("null") && !sdId.equals("0")) {
            Query += " and mg.group_sd_id =  " + sdId + " ";
        }
        if (!staffId.equals("null") && !staffId.equals("0")) {
            Query += " and tt.timeline_staff_id =  " + staffId + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,finalizedDate,patient_mr_no,patientName,FollowDate,DoctorName,user_mobile,Speciality,department_name,subdepatment";
        // Query += " limit " + ((misfollowupappointmentSearchDTO.getOffset() - 1) * misfollowupappointmentSearchDTO.getLimit()) + "," + misfollowupappointmentSearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misfollowupappointmentSearchDTO.getUnitId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        if (misfollowupappointmentSearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("FollowupAppointmentReport", jsonArray.toString());
        } else {
            return createReport.createJasperReportPDFByJson("FollowupAppointmentReport", jsonArray.toString());
        }
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofappointmentReport/{unitList}/{departmentId}/{staffId}/{fromdate}/{todate}")
    public ResponseEntity searchListOfAppointment(@RequestHeader("X-tenantId") String tenantName, @RequestBody ListOfAppointmentReportDTO listOfAppointmentReportDTO, @PathVariable String[] unitList, @PathVariable String departmentId, @PathVariable String staffId, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name,t.appointment_date,t.appointment_slot,ifnull(pp.patient_mr_no,'')AS patient_mr_no, " +
                " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname, " +
                " CONCAT( ifnull(ust.user_firstname,'') ,' ',ifnull(ust.user_lastname,''))as Doctor_name, " +
                " ifnull(dp.department_name,'')AS department_name,ifnull(u.user_mobile,'')AS user_mobile " +
                " from trn_appointment t " +
                " LEFT JOIN mst_user u ON t.appointment_user_id=u.user_id " +
                " LEFT JOIN mst_staff st ON t.appointment_staff_id=st.staff_id " +
                " LEFT JOIN mst_user ust ON st.staff_user_id=ust.user_id " +
                " LEFT JOIN mst_department dp ON t.appointment_department_id=dp.department_id " +
                " LEFT JOIN mst_unit un ON t.appointment_unit_id=un.unit_id " +
                " LEFT JOIN mst_patient pp ON t.appointment_patient_id=pp.patient_id ";
        String CountQuery = "";
        String columnName = "";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " where t.appointment_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (listOfAppointmentReportDTO.getTodaydate()) {
            Query += " and (date(t.appointment_date)=curdate()) ";
        } else {
            Query += " and (date(t.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!staffId.equals("null") && !staffId.equals("0")) {
            Query += " and t.appointment_staff_id =  " + staffId + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,appointment_date,appointment_slot,patient_mr_no,patientname,Doctor_name,department_name,user_mobile";
        Query += " limit " + ((listOfAppointmentReportDTO.getOffset() - 1) * listOfAppointmentReportDTO.getLimit()) + "," + listOfAppointmentReportDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    @RequestMapping("getlistofappointmentReportPrint/{unitList}/{departmentId}/{staffId}/{fromdate}/{todate}")
    public String searchListOfAppointmentPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody ListOfAppointmentReportDTO listOfAppointmentReportDTO, @PathVariable String[] unitList, @PathVariable String departmentId, @PathVariable String staffId, @PathVariable String fromdate, @PathVariable String todate) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name,t.appointment_date,t.appointment_slot,ifnull(pp.patient_mr_no,'')AS patient_mr_no, " +
                " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname, " +
                " CONCAT( ifnull(ust.user_firstname,'') ,' ',ifnull(ust.user_lastname,''))as Doctor_name, " +
                " ifnull(dp.department_name,'')AS department_name,ifnull(u.user_mobile,'')AS user_mobile " +
                " from trn_appointment t " +
                " LEFT JOIN mst_user u ON t.appointment_user_id=u.user_id " +
                " LEFT JOIN mst_staff st ON t.appointment_staff_id=st.staff_id " +
                " LEFT JOIN mst_user ust ON st.staff_user_id=ust.user_id " +
                " LEFT JOIN mst_department dp ON t.appointment_department_id=dp.department_id " +
                " LEFT JOIN mst_unit un ON t.appointment_unit_id=un.unit_id " +
                " LEFT JOIN mst_patient pp ON t.appointment_patient_id=pp.patient_id ";
        String CountQuery = "";
        String columnName = "";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " where t.appointment_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (listOfAppointmentReportDTO.getTodaydate()) {
            Query += " and (date(t.appointment_date)=curdate()) ";
        } else {
            Query += " and (date(t.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!staffId.equals("null") && !staffId.equals("0")) {
            Query += " and t.appointment_staff_id =  " + staffId + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,appointment_date,appointment_slot,patient_mr_no,patientname,Doctor_name,department_name,user_mobile";
        //   Query += " limit " + ((listOfAppointmentReportDTO.getOffset() - 1) * listOfAppointmentReportDTO.getLimit()) + "," + listOfAppointmentReportDTO.getLimit();
//        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(listOfAppointmentReportDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(listOfAppointmentReportDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (listOfAppointmentReportDTO.getPrint()) {
            columnName = "unit_name,appointment_date,appointment_slot,patient_mr_no,patientname,Doctor_name,department_name,user_mobile";
            return createReport.generateExcel(columnName, "ListOfAppointmentReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("ListOfAppointmentReport", jsonArray.toString());
        }
    }
}
