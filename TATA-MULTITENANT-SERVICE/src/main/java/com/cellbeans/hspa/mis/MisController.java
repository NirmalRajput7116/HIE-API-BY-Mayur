package com.cellbeans.hspa.mis;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mis.misopdreports.ListOfAppointmentReportDTO;
import com.cellbeans.hspa.mis.misopdreports.ListOfCanceledAppointmentReportDTO;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tbillbill.TbillBillRepository;
import com.cellbeans.hspa.tbillbillservice.TbillBillService;
import com.cellbeans.hspa.tbillbillservice.TbillBillServiceRepository;
import com.cellbeans.hspa.tbillreciept.TbillRecieptRepository;
import com.cellbeans.hspa.tbillserviceradiology.TbillServiceRadiology;
import com.cellbeans.hspa.tbillserviceradiology.TbillServiceRadiologyRepository;
import com.cellbeans.hspa.tinvitemsalesreturn.TinvItemSalesReturn;
import com.cellbeans.hspa.tinvitemsalesreturn.TinvItemSalesReturnRepository;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItemRepository;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySale;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySaleRepository;
import com.cellbeans.hspa.tpathbs.TpathBs;
import com.cellbeans.hspa.tpathbs.TpathBsRepository;
import com.cellbeans.hspa.trnappointment.TrnAppointmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

//import java.sql.Date;
@RestController
@RequestMapping("/mis")
public class MisController {
    Map<String, Double> respMap = new HashMap<String, Double>();

    @Autowired
    TrnAppointmentRepository trnAppointmentRepository;

    @Autowired
    MstPatientRepository mstPatientRepository;

    @Autowired
    MstVisitRepository mstVisitRepository;

    @Autowired
    TbillServiceRadiologyRepository tbillServiceRadiologyRepository;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    MstUserRepository mstUserRepository;

    @Autowired
    TbillBillRepository tBillBillRepository;

    @Autowired
    TbillBillServiceRepository tbillBillServiceRepository;

    @Autowired
    TinvPharmacySaleRepository tinvPharmacySaleRepository;

    @Autowired
    TinvItemSalesReturnRepository tinvItemSalesReturnRepository;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    TpathBsRepository tpathBsRepository;

    @Autowired
    TbillRecieptRepository tbillRecieptRepository;

    @PersistenceContext
    EntityManager objEntityManager;

    @Autowired
    CreateReport createReport;

    @Autowired
    TinvOpeningBalanceItemRepository tinvOpeningBalanceItemRepository;

   /* @RequestMapping("appointmentlist/{unitList}")
    public Iterable<TrnAppointment> appointmentlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws JSONException {
        System.out.println("---object------");

        JSONObject json = new JSONObject(search_query);
        System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "appointmentId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") ? json.getBoolean("today") : false;
        Boolean print = search_query.contains("print") ? json.getBoolean("print") : false;
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        //long unitid = search_query.contains("unitid") ? json.getLong("unitid") : null;
        System.out.println("---page------");
        System.out.println(page);
        System.out.println(size);
        System.out.println(search);

        if (print.equals(true)) {
            if (!search.equals("")) {
                System.out.println("-------------all search print------------------------");
                return trnAppointmentRepository.findAllByAppointmentMrNoContainsOrAppointmentUserIdUserFirstnameContainsOrAppointmentUserIdUserMiddlenameContainsOrAppointmentUserIdUserLastnameContainsOrAppointmentStaffIdStaffUserIdUserFirstnameContainsOrAppointmentStaffIdStaffUserIdUserMiddlenameContainsOrAppointmentStaffIdStaffUserIdUserLastnameContainsOrAppointmentDepartmentIdDepartmentNameContainsAndAppointmentUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(search, search,
                        search, search, search, search, search, search, unitList);
            } else if (today.equals(true)) {

                System.out.println("-------------today search print------------------------");
                return trnAppointmentRepository.findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse(unitList);
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                System.out.println("-------------normal print------------------------");
                return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(unitList);
            } else {

                System.out.println("-------------from to search print------------------------");
                return trnAppointmentRepository.findAllByCreatedDatePrintAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, unitList);
                // return  objEntityManager.createNativeQuery("SELECT * FROM mst_patient  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN '"+startdate+ "' and '"+enddate+"' ",MstPatient.class).getResultList();

            }
        } else {
            if (!search.equals("")) {
                System.out.println("-------------all search------------------------");
                return trnAppointmentRepository.findAllByAppointmentMrNoContainsOrAppointmentUserIdUserFirstnameContainsOrAppointmentUserIdUserMiddlenameContainsOrAppointmentUserIdUserLastnameContainsOrAppointmentStaffIdStaffUserIdUserFirstnameContainsOrAppointmentStaffIdStaffUserIdUserMiddlenameContainsOrAppointmentStaffIdStaffUserIdUserLastnameContainsOrAppointmentDepartmentIdDepartmentNameContainsAndAppointmentUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(search, search,
                        search, search, search, search, search, search, unitList, PageRequest.of(1,20));
            } else if (today.equals(true)) {
                col = "appointment_id";
                System.out.println("-------------today search------------------------");
                return trnAppointmentRepository.findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse(unitList, PageRequest.of(1,20));
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(unitList, PageRequest.of(1,20));
            } else {
                col = "appointment_id";
                System.out.println("-------------from to search ------------------------");
                return trnAppointmentRepository.findAllByCreatedDateAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, unitList, PageRequest.of(1,20));
            }
        }
    }*/
    //Jitendra 08-02-2020

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofAppointmentReport/{fromdate}/{todate}")
    public ResponseEntity searchListCancelOfAppointment(@RequestHeader("X-tenantId") String tenantName, @RequestBody ListOfAppointmentReportDTO listOfAppointmentReportDTO, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name, IFNULL(t.appointment_date,'') AS appointment_date, IFNULL(t.appointment_slot,'') AS appointment_slot, DATE(t.created_date) AS CreatedDate,t.created_by AS 'AppointmentBookBy',mr.role_name AS 'RoleName', IFNULL(pp.patient_mr_no,'') AS patient_mr_no, CONCAT(IFNULL(u.user_firstname,''),' ', IFNULL(u.user_lastname,'')) AS patientname, CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(ust.user_firstname,''),' ', IFNULL(ust.user_lastname,'')) AS Doctor_name, IFNULL(dp.department_name,'') AS department_name, IFNULL(u.user_mobile,'') AS user_mobile, IFNULL(t.appointment_is_cancelledreasone,'') AS Reason, IFNULL(msp.speciality_name,'') AS speciality_name " +
                " FROM trn_appointment t " +
                " LEFT JOIN mst_user u ON t.appointment_user_id=u.user_id " +
                " LEFT JOIN mst_staff st ON t.appointment_staff_id=st.staff_id " +
                " LEFT JOIN mst_speciality msp ON msp.speciality_id = st.staff_speciality_id " +
                " LEFT JOIN mst_user ust ON st.staff_user_id=ust.user_id " +
                " LEFT JOIN mst_title mt ON mt.title_id = ust.user_title_id " +
                " LEFT JOIN mst_department dp ON t.appointment_department_id=dp.department_id " +
                " LEFT JOIN mst_unit un ON t.appointment_unit_id=un.unit_id " +
                " LEFT JOIN mst_patient pp ON t.appointment_patient_id=pp.patient_id " +
                " INNER JOIN mst_user mu ON mu.user_fullname = t.created_by " +
                " INNER JOIN mst_staff ms ON ms.staff_user_id = mu.user_id " +
                " INNER JOIN mst_role mr ON mr.role_id = ms.staff_role " +
                " WHERE t.is_active = 1 AND t.is_deleted = 0 ";
        String CountQuery = "";
        String columnName = "";
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
        // unit Id
//        if(listOfAppointmentReportDTO.getUnitId() != 0) {
//            Query += " and t.appointment_unit_id = "+ lis
//        } else {
        if (!listOfAppointmentReportDTO.unitList[0].equals("null")) {
            String values = listOfAppointmentReportDTO.unitList[0];
            for (int i = 1; i < listOfAppointmentReportDTO.unitList.length; i++) {
                values += "," + listOfAppointmentReportDTO.unitList[i];
            }
            Query += " and t.appointment_unit_id in (" + values + ") ";
            System.out.println("List Of Appointment Report"+Query);
        }
        // for doctor
        if (listOfAppointmentReportDTO.getStaffId1() != null && !listOfAppointmentReportDTO.getStaffId1().equals("0")) {
            Query += " and st.staff_id = " + listOfAppointmentReportDTO.getStaffId1() + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,appointment_date,appointment_slot,CreatedDate,AppointmentBookBy,RoleName,patient_mr_no,patientname,Doctor_name,department_name,user_mobile,reason,speciality_name";
        Query += " limit " + ((listOfAppointmentReportDTO.getOffset() - 1) * listOfAppointmentReportDTO.getLimit()) + "," + listOfAppointmentReportDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofAppointmentReportPrint/{fromdate}/{todate}")
    public String searchListCancelOfAppointmentPrint(@RequestHeader("X-tenantId") String tenantName,
                                                     @RequestBody ListOfAppointmentReportDTO listOfAppointmentReportDTO,
                                                     @PathVariable String fromdate, @PathVariable String todate) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query =" SELECT un.unit_name, IFNULL(t.appointment_date,'') AS appointment_date, IFNULL(t.appointment_slot,'') AS appointment_slot, DATE(t.created_date) AS CreatedDate,t.created_by AS 'AppointmentBookBy',mr.role_name AS 'RoleName', IFNULL(pp.patient_mr_no,'') AS patient_mr_no, CONCAT(IFNULL(u.user_firstname,''),' ', IFNULL(u.user_lastname,'')) AS patientname, CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(ust.user_firstname,''),' ', IFNULL(ust.user_lastname,'')) AS Doctor_name, IFNULL(dp.department_name,'') AS department_name, IFNULL(u.user_mobile,'') AS user_mobile, IFNULL(t.appointment_is_cancelledreasone,'') AS Reason, IFNULL(msp.speciality_name,'') AS speciality_name " +
                " FROM trn_appointment t " +
                " LEFT JOIN mst_user u ON t.appointment_user_id=u.user_id " +
                " LEFT JOIN mst_staff st ON t.appointment_staff_id=st.staff_id " +
                " LEFT JOIN mst_speciality msp ON msp.speciality_id = st.staff_speciality_id " +
                " LEFT JOIN mst_user ust ON st.staff_user_id=ust.user_id " +
                " LEFT JOIN mst_title mt ON mt.title_id = ust.user_title_id " +
                " LEFT JOIN mst_department dp ON t.appointment_department_id=dp.department_id " +
                " LEFT JOIN mst_unit un ON t.appointment_unit_id=un.unit_id " +
                " LEFT JOIN mst_patient pp ON t.appointment_patient_id=pp.patient_id " +
                " INNER JOIN mst_user mu ON mu.user_fullname = t.created_by " +
                " INNER JOIN mst_staff ms ON ms.staff_user_id = mu.user_id " +
                " INNER JOIN mst_role mr ON mr.role_id = ms.staff_role " +
                " WHERE t.is_active = 1 AND t.is_deleted = 0 ";
        String CountQuery = "";
        String columnName = "";
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
        // unit Id
        if (!listOfAppointmentReportDTO.unitList[0].equals("null")) {
            String values = listOfAppointmentReportDTO.unitList[0];
            for (int i = 1; i < listOfAppointmentReportDTO.unitList.length; i++) {
                values += "," + listOfAppointmentReportDTO.unitList[i];
            }
            Query += " and t.appointment_unit_id in (" + values + ") ";
        }
        // for doctor
        if (listOfAppointmentReportDTO.getStaffId1() != null && !listOfAppointmentReportDTO.getStaffId1().equals("0")) {
            Query += " and st.staff_id = " + listOfAppointmentReportDTO.getStaffId1() + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,appointment_date,appointment_slot,CreatedDate,AppointmentBookBy,RoleName,patient_mr_no,patientname,Doctor_name,department_name,user_mobile,reason,speciality_name";
        //   Query += " limit " + ((listOfAppointmentReportDTO.getOffset() - 1) * listOfAppointmentReportDTO.getLimit()) + "," + listOfAppointmentReportDTO.getLimit();
        System.out.print(Query);
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(listOfAppointmentReportDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(listOfAppointmentReportDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (listOfAppointmentReportDTO.getPrint()) {
            columnName = "unit_name,appointment_date,appointment_slot,CreatedDate,AppointmentBookBy,RoleName,patient_mr_no,patientname,Doctor_name,department_name,user_mobile";
            return createReport.generateExcel(columnName, "ListOfAppointmentReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("ListOfAppointmentReport", jsonArray.toString());
        }
        //return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofEmployeeRecordsReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity getlistofEmployeeRecordsReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody ListOfAppointmentReportDTO listOfAppointmentReportDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "SELECT CONCAT(mu.user_firstname,' ',mu.user_lastname) AS EmpName, mu.user_age AS Age, ifnull(gpd.gp_department_name,'') AS Level1, " +
                " ifnull(ss.socialstatus_name,'') AS Cluster, ifnull(gps.gp_set_name,'') AS SBU, ifnull(mg.gender_name,'') AS Gender, IFNULL(mp.patient_height,'') AS Height, " +
                " IFNULL(mp.patient_weight,'') AS Weight, ifnull(mu.user_gp_mar_no,'') AS FamilyHistory, IFNULL(bg.bloodgroup_name,'') AS Bloodgroup, " +
                " ifnull(mu.user_permanent_address,'') AS OfficeAddress, ifnull(mu.user_mobile,'') AS Mobile, mv.visit_date AS visitDate, " +
                " CONCAT(mu1.user_firstname,' ',mu1.user_lastname) AS StaffVisited, ifnull(mcc.cc_name,'') AS TypeAilment, ifnull(vcc.vs_chief_complaint,'') AS DetailsAilment, " +
                " ifnull(ii.item_name,'') AS MedicinePrescribed, ifnull(ip.ip_quantity,'') AS Qty, " +
                " case " +
                " WHEN ii.item_life_saving = 1 THEN 'Acute' " +
                " WHEN ii.item_high_risk = 1 THEN 'Chronic' " +
                " ELSE '-' " +
                " END AS TypeMed, ifnull(msv.service_name,'') AS TestPrescribed " +
                " FROM mst_visit mv " +
                " LEFT JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                " LEFT JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_gp_department gpd ON mu.user_gp_department = gpd.gp_department_id " +
                " LEFT JOIN mst_social_status ss ON mu.user_social_status_id = ss.socialstatus_id " +
                " LEFT JOIN mst_gp_set gps ON mu.user_gp_set = gps.gp_set_id " +
                " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                " LEFT JOIN mst_bloodgroup bg ON mu.user_bloodgroup_id = bg.bloodgroup_id " +
                " LEFT JOIN mst_staff ms ON mv.visit_staff_id = ms.staff_id " +
                " LEFT JOIN mst_user mu1 ON ms.staff_user_id = mu1.user_id " +
                " LEFT JOIN temr_visit_prescription vp ON vp.vp_visit_id = mv.visit_id " +
                " LEFT JOIN temr_item_prescription ip ON ip.ip_vp_id = vp.vp_id " +
                " LEFT JOIN inv_item ii ON ip.ip_inv_item_id = ii.item_id " +
                " LEFT JOIN temr_visit_investigation vi ON vi.vi_visit_id = mv.visit_id " +
                " LEFT JOIN mbill_service msv ON vi.vi_service_id = msv.service_id " +
                " LEFT JOIN temr_visit_chief_complaint vcc ON vcc.vcc_visit_id = mv.visit_id " +
                " LEFT JOIN memr_chief_complaint mcc ON mcc.cc_id = vcc.vcc_cc_id " +
                " WHERE mv.is_deleted=0 AND mp.is_deleted=0 AND mu.is_deleted=0 ";
        String CountQuery = "";
        String columnName = "";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (listOfAppointmentReportDTO.getTodaydate()) {
            Query += " and (date(mv.visit_date)=curdate()) ";
        } else {
            Query += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        // unit Id
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and mv.visit_unit_id in (" + values + ") ";
        }
        // for doctor
        if (listOfAppointmentReportDTO.getStaffId1() != null && !listOfAppointmentReportDTO.getStaffId1().equals("0")) {
            Query += " and ms.staff_id = " + listOfAppointmentReportDTO.getStaffId1() + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "EmpName,Age,Level1,Cluster,SBU,Gender,Height,Weight,FamilyHistory,Bloodgroup,OfficeAddress,Mobile,visitDate,StaffVisited,TypeAilment,DetailsAilment,MedicinePrescribed,Qty,TypeMed,TestPrescribed";
        Query += " limit " + ((listOfAppointmentReportDTO.getOffset() - 1) * listOfAppointmentReportDTO.getLimit()) + "," + listOfAppointmentReportDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofMedicalStaffRecordsReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity getlistofMedicalStaffRecordsReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody ListOfAppointmentReportDTO listOfAppointmentReportDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "SELECT tt.timeline_date AS VisitDate, ifnull(tt.timeline_finalized_date,'') AS FinalizedDate, ifnull(CONCAT(mu.user_firstname, ' ', mu.user_lastname),'') AS Staff, " +
                " CONCAT(mu1.user_firstname,' ',mu1.user_lastname) AS PatientName, ifnull(mu.user_age,'') AS Age, mg.gender_name AS Gender, mp.patient_mr_no AS MrNumber, mu1.user_driving_no AS EmpNumber, " +
                " TIMESTAMPDIFF(DAY, TIMESTAMP(tt.timeline_date), IFNULL(tt.timeline_finalized_date, NOW())) as Days, " +
                " MOD(TIMESTAMPDIFF(HOUR, TIMESTAMP(tt.timeline_date), IFNULL(tt.timeline_finalized_date, NOW())), 24) as Hours , " +
                " MOD(TIMESTAMPDIFF(MINUTE, TIMESTAMP(tt.timeline_date), IFNULL(tt.timeline_finalized_date, NOW())), 60) as Minutes " +
                " FROM temr_timeline tt " +
                " LEFT JOIN mst_staff ms ON ms.staff_id = tt.timeline_staff_id " +
                " LEFT JOIN mst_user mu ON mu.user_id = ms.staff_user_id " +
                " LEFT JOIN mst_visit mv ON mv.visit_id = tt.timeline_visit_id " +
                " LEFT JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id " +
                " LEFT JOIN mst_user mu1 ON mu1.user_id = mp.patient_user_id " +
                " LEFT JOIN mst_gender mg ON mg.gender_id = mu1.user_gender_id " +
                " WHERE tt.is_deleted=0 ";
        String CountQuery = "";
        String columnName = "";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (listOfAppointmentReportDTO.getTodaydate()) {
            Query += " and (date(tt.timeline_date)=curdate()) ";
        } else {
            Query += " and (date(tt.timeline_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        // unit Id
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and mv.visit_unit_id in (" + values + ") ";
        }
        // for doctor
        if (listOfAppointmentReportDTO.getStaffId1() != null && !listOfAppointmentReportDTO.getStaffId1().equals("0")) {
            Query += " and ms.staff_id = " + listOfAppointmentReportDTO.getStaffId1() + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "VisitDate,FinalizedDate,Staff,PatientName,Age,Gender,MrNumber,EmpNumber,Days,Hours,Minutes";
        Query += " limit " + ((listOfAppointmentReportDTO.getOffset() - 1) * listOfAppointmentReportDTO.getLimit()) + "," + listOfAppointmentReportDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    // Jitendra 10-02-2020
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofCancelappointmentReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchListCancelOfAppointment(@RequestHeader("X-tenantId") String tenantName, @RequestBody ListOfCanceledAppointmentReportDTO listOfCanceledAppointmentReportDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name,t.appointment_date,t.appointment_slot,ifnull(pp.patient_mr_no,'')AS patient_mr_no," +
                " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname," +
                " CONCAT( ifnull(ust.user_firstname,'') ,' ',ifnull(ust.user_lastname,''))as Doctor_name," +
                " ifnull(dp.department_name,'')AS department_name,ifnull(u.user_mobile,'')AS user_mobile,ifnull(t.appointment_is_cancelledreasone,'')AS Reason" +
                " from trn_appointment t" +
                " LEFT JOIN mst_user u ON t.appointment_user_id=u.user_id" +
                "  LEFT JOIN mst_staff st ON t.appointment_staff_id=st.staff_id" +
                " LEFT JOIN mst_user ust ON st.staff_user_id=ust.user_id" +
                " LEFT JOIN mst_department dp ON t.appointment_department_id=dp.department_id" +
                " LEFT JOIN mst_unit un ON t.appointment_unit_id=un.unit_id" +
                " LEFT JOIN mst_patient pp ON t.appointment_patient_id=pp.patient_id" +
                " where t.appointment_is_cancelled = 1 AND t.is_active = 1 AND t.is_deleted = 0 ";
        String CountQuery = "";
        String columnName = "";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and t.appointment_unit_id in (" + values + ") ";
        }
        if (listOfCanceledAppointmentReportDTO.getTodaydate()) {
            Query += " and (date(t.appointment_date)=curdate()) ";
        } else {
            Query += " and (date(t.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        // for doctor
        if (listOfCanceledAppointmentReportDTO.getStaffId1() != null && !listOfCanceledAppointmentReportDTO.getStaffId1().equals("0")) {
            Query += " and st.staff_id = " + listOfCanceledAppointmentReportDTO.getStaffId1() + " ";
        }
        System.out.println("List Of Canceled Appointment Report"+Query);
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,appointment_date,appointment_slot,patient_mr_no,patientname,Doctor_name,department_name,user_mobile,reason";
        Query += " limit " + ((listOfCanceledAppointmentReportDTO.getOffset() - 1) * listOfCanceledAppointmentReportDTO.getLimit()) + "," + listOfCanceledAppointmentReportDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofCancelappointmentReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchListCancelOfAppointmentPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody ListOfCanceledAppointmentReportDTO listOfCanceledAppointmentReportDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name,t.appointment_date,t.appointment_slot,ifnull(pp.patient_mr_no,'')AS patient_mr_no," +
                " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname," +
                " CONCAT( ifnull(ust.user_firstname,'') ,' ',ifnull(ust.user_lastname,''))as Doctor_name," +
                " ifnull(dp.department_name,'')AS department_name,ifnull(u.user_mobile,'')AS user_mobile,ifnull(t.appointment_is_cancelledreasone,'')AS Reason" +
                " from trn_appointment t" +
                " LEFT JOIN mst_user u ON t.appointment_user_id=u.user_id" +
                "  LEFT JOIN mst_staff st ON t.appointment_staff_id=st.staff_id" +
                " LEFT JOIN mst_user ust ON st.staff_user_id=ust.user_id" +
                " LEFT JOIN mst_department dp ON t.appointment_department_id=dp.department_id" +
                " LEFT JOIN mst_unit un ON t.appointment_unit_id=un.unit_id" +
                " LEFT JOIN mst_patient pp ON t.appointment_patient_id=pp.patient_id" +
                " where t.appointment_is_cancelled = 1 AND t.is_active = 1 AND t.is_deleted = 0 ";
        String CountQuery = "";
        String columnName = "";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and t.appointment_unit_id in (" + values + ") ";
        }
        if (listOfCanceledAppointmentReportDTO.getTodaydate()) {
            Query += " and (date(t.appointment_date)=curdate()) ";
        } else {
            Query += " and (date(t.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        // for doctor
        if (listOfCanceledAppointmentReportDTO.getStaffId1() != null && !listOfCanceledAppointmentReportDTO.getStaffId1().equals("0")) {
            Query += " and st.staff_id = " + listOfCanceledAppointmentReportDTO.getStaffId1() + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,appointment_date,appointment_slot,patient_mr_no,patientname,Doctor_name,department_name,user_mobile,reason";
        Query += " limit " + ((listOfCanceledAppointmentReportDTO.getOffset() - 1) * listOfCanceledAppointmentReportDTO.getLimit()) + "," + listOfCanceledAppointmentReportDTO.getLimit();
        // return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(listOfCanceledAppointmentReportDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(listOfCanceledAppointmentReportDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (listOfCanceledAppointmentReportDTO.getPrint()) {
            columnName = "unit_name,appointment_date,appointment_slot,patient_mr_no,patientname,Doctor_name,department_name,user_mobile,reason";
            return createReport.generateExcel(columnName, "OpdListOfCancelledAppointmentReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("OpdListOfCancelledAppointmentReport", jsonArray.toString());
        }
    }



    /*@RequestMapping("canceledappointmentlist/{unitList}")
    public Iterable<TrnAppointment> canceledappointmentlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws JSONException {
        // System.out.println("---object------");

        JSONObject json = new JSONObject(search_query);
        // System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "appointmentId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") ? json.getBoolean("today") : false;
        Boolean print = search_query.contains("print") ? json.getBoolean("print") : false;
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        // System.out.println("---page------");
        // System.out.println(page);
        // System.out.println(size);
        // System.out.println(search);

        if (print.equals(true)) {
            if (!search.equals("")) {
                // System.out.println("-------------all search print------------------------");
                return trnAppointmentRepository.findAllByAppointmentMrNoContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserFirstnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserMiddlenameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserLastnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserFirstnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserMiddlenameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserLastnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentDepartmentIdDepartmentNameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndAppointmentUnitIdUnitIdIn(search, search,
                        search, search, search, search, search, search, unitList);
            } else if (today.equals(true)) {

                // System.out.println("-------------today search print------------------------");
                return trnAppointmentRepository.findAllByCreatedDateContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalse(unitList);
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                // System.out.println("-------------normal print------------------------");
                return trnAppointmentRepository.findAllByAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndAppointmentUnitIdUnitIdIn(unitList);
            } else {

                // System.out.println("-------------from to search print------------------------");
                return trnAppointmentRepository.findAllByCreatedDateAndIsCanceledAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, unitList);
                // return  objEntityManager.createNativeQuery("SELECT * FROM mst_patient  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN '"+startdate+ "' and '"+enddate+"' ",MstPatient.class).getResultList();

            }
        } else {
            if (!search.equals("")) {
                // System.out.println("-------------all search------------------------");
                return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdInAndAppointmentMrNoContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserFirstnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserMiddlenameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentUserIdUserLastnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserFirstnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserMiddlenameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentStaffIdStaffUserIdUserLastnameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseOrAppointmentDepartmentIdDepartmentNameContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalse(unitList, search, search,
                        search, search, search, search, search, search, PageRequest.of(1,20));
            } else if (today.equals(true)) {
                col = "appointment_id";
                // System.out.println("-------------today search------------------------");
                return trnAppointmentRepository.findAllByCreatedDateContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalse(unitList, PageRequest.of(1,20));
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return trnAppointmentRepository.findAllByAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndAppointmentUnitIdUnitIdIn(unitList, PageRequest.of(1,20));
            }
            if (today.equals(true)) {
                // System.out.println("-------------today search print------------------------");
                return trnAppointmentRepository.findAllByCreatedDateContainsAndAppointmentIsCancelledTrueAndIsActiveTrueAndIsDeletedFalse(unitList);
            } else {
                col = "appointment_id";
                // System.out.println("-------------from to search ------------------------");
                return trnAppointmentRepository.findAllByCreatedDateAndIsCanceledAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, unitList, PageRequest.of(1,20));
            }
        }
    }*/

    @RequestMapping("registrationlist")
    public Iterable<MstPatient> registrationlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        System.out.println(json);
        System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "patientId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        long unitid = search_query.contains("unitid") ? json.getLong("unitid") : null;
        System.out.println("---page------");
        System.out.println(page);
        System.out.println(size);
        System.out.println(search);
        if (print.equals(true)) {
            if (!search.equals("")) {
                System.out.println("-------------all search print------------------------");
                return mstPatientRepository.findAllByPatientMrNoContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(search, search,
                        search, search, search);
            } else if (today.equals(true)) {
                return mstPatientRepository.findAllByCreatedDateContainsIsActiveTrueAndIsDeletedFalse();
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return mstPatientRepository.findAllByIsActiveTrueAndIsDeletedFalse();
            } else {
                return mstPatientRepository.findAllByFromToDateAndIActiveTrueAndIsDeletedFalse(startdate, enddate);
            }
        } else {
            if (!search.equals("")) {
                System.out.println("-------------all search------------------------");
                return mstPatientRepository.findAllByPatientMrNoContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(search, search,
                        search, search, search, PageRequest.of(1, 20));
            } else if (today.equals(true)) {
                col = "patient_id";
                return mstPatientRepository.findAllByCreatedDateContainsIsActiveTrueAndIsDeletedFalse(PageRequest.of(1, 20));
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return mstPatientRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(1, 20));
            } else {
                col = "patient_id";
                return mstPatientRepository.findAllByFromToDateAndIActiveTrueAndIsDeletedFalse(startdate, enddate, PageRequest.of(1, 20));
            }
        }
    }

    @RequestMapping("visitlist/{unitList}")
    public Iterable<MstVisit> visitlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "visitId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        long staffId = search_query.contains("staffId") ? json.getLong("staffId") : 0;
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        long unitid = search_query.contains("unitid") ? json.getLong("unitid") : null;
        System.out.println("---page------");
        System.out.println(page);
        System.out.println(size);
        System.out.println(search);
        System.out.println(today);
        System.out.println(enddate);
        System.out.println(staffId);
        System.out.println(print);
        if (print.equals(true)) {
            if (!search.equals("")) {
                System.out.println("-------------all search print------------------------");
                return mstVisitRepository.findAllByVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserMiddlenameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserAgeContainsOrVisitPatientIdPatientUserIdUserGenderIdGenderNameContainsOrVisitStaffIdStaffUserIdUserFirstnameContainsOrVisitStaffIdStaffUserIdUserMiddlenameContainsOrVisitStaffIdStaffUserIdUserLastnameContainsAndVisitUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(search, search,
                        search, search, search, search, search, search, search, unitList);
            } else if (today.equals(true)) {
                return mstVisitRepository.findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse(unitList);
            } else if (staffId != 0) {
                return mstVisitRepository.findAllByVisitStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalseAndVisitUnitIdUnitIdIn(staffId, unitList);
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return mstVisitRepository.findAllByVisitUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(unitList);
            } else {
                // return mstVisitRepository.findAllByFromToDateAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, unitid);
                return mstVisitRepository.findAllByFromToDateAndVisitTariffIdIsNotNullAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, unitid);
            }

        } else {
            if (!search.equals("")) {
                System.out.println("-------------all search------------------------");
                return mstVisitRepository.findAllByVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserMiddlenameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserAgeContainsOrVisitPatientIdPatientUserIdUserGenderIdGenderNameContainsOrVisitStaffIdStaffUserIdUserFirstnameContainsOrVisitStaffIdStaffUserIdUserMiddlenameContainsOrVisitStaffIdStaffUserIdUserLastnameContainsAndVisitUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(search, search,
                        search, search, search, search, search, search, search, unitList, PageRequest.of(1, 20));
            } else if (today.equals(true)) {
                col = "visit_id";
                return mstVisitRepository.findAllByCreatedDateContainsAndIsActiveTrueAndIsDeletedFalse(unitList, PageRequest.of(1, 20));
            } else if (staffId != 0) {
                return mstVisitRepository.findAllByVisitStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalseAndVisitUnitIdUnitIdIn(staffId, unitList, PageRequest.of(1, 20));
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return mstVisitRepository.findAllByVisitUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(unitList, PageRequest.of(1, 20));
            } else {
                col = "visit_id";
                System.out.println("Enter Here " + startdate + " : " + enddate + " : " + unitid);
                //  return mstVisitRepository.findAllByFromToDateAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, unitid);
                return mstVisitRepository.findAllByFromToDateAndVisitTariffIdIsNotNullAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, unitid);

            }
        }
    }

    @RequestMapping("radiotestlist")
    public Iterable<TbillServiceRadiology> radiotestlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        // System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "bsrId";
        String radioreporttype = search_query.contains("radioreporttype") ? json.getString("radioreporttype") : "";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        // System.out.println("---page------");
        // System.out.println(page);
        // System.out.println(size);
        // System.out.println(search);
        // System.out.println(radioreporttype);
        if (print.equals(true)) {
            if (!radioreporttype.equals("")) {
                // System.out.println("-------------report type search------------------------");
                Boolean reporttype;
                reporttype = radioreporttype.equals("true");
                return tbillServiceRadiologyRepository.findAllByBsrIsReportedEqualsAndIsActiveTrueAndIsDeletedFalse(reporttype);
            } else if (!search.equals("")) {
                // System.out.println("-------------all search------------------------");
                return tbillServiceRadiologyRepository.findByBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsOrBsrBsIdBsServiceIdServiceSgIdSgNameContainsOrBsrBsIdBsStaffIdStaffUserIdUserFirstnameContainsOrBsrBsIdBsStaffIdStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(search, search,
                        search, search, search, search, search);
            } else if (today.equals(true)) {
                // System.out.println("-------------today search------------------------");
                return tbillServiceRadiologyRepository.findAllByTodayCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse();
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return tbillServiceRadiologyRepository.findAllByIsActiveTrueAndIsDeletedFalse();
            } else {
                return tbillServiceRadiologyRepository.findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(startdate, enddate);
            }
        } else {
            if (!radioreporttype.equals("")) {
                // System.out.println("-------------report type search------------------------");
                Boolean reporttype;
                reporttype = radioreporttype.equals("true");
                return tbillServiceRadiologyRepository.findAllByBsrIsReportedEqualsAndIsActiveTrueAndIsDeletedFalse(reporttype, PageRequest.of(1, 20));
            } else if (!search.equals("")) {
                // System.out.println("-------------all search------------------------");
                return tbillServiceRadiologyRepository.findByBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsOrBsrBsIdBsServiceIdServiceSgIdSgNameContainsOrBsrBsIdBsStaffIdStaffUserIdUserFirstnameContainsOrBsrBsIdBsStaffIdStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(search, search,
                        search, search, search, search, search, PageRequest.of(1, 20));
            } else if (today.equals(true)) {
                col = "bsr_id";
                // System.out.println("-------------today search------------------------");
                return tbillServiceRadiologyRepository.findAllByTodayCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(1, 20));
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return tbillServiceRadiologyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(1, 20));
            } else {
                col = "bsr_id";
                return tbillServiceRadiologyRepository.findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, PageRequest.of(1, 20));
            }
        }
    }

    @RequestMapping("billlistunit/{unitList}")
    public Iterable<TBillBill> billlistunit(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "billId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        //long unitid = search_query.contains("unitid") ? json.getLong("unitid") : null;
        System.out.println("---unitid------");
        //System.out.println(unitid);
        if (print.equals(true)) {
            if (!search.equals("")) {
                System.out.println("-------------all search------------------------");
                return tBillBillRepository.findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(search,
                        search, search, search, search, unitList);
            } else if (today.equals(true)) {
                java.sql.Date todaysdate;
                LocalDate ltoday = LocalDate.now();
                todaysdate = java.sql.Date.valueOf(ltoday);
                System.out.println("-------------today search------------------------");
                return tBillBillRepository.findAllByBillDateEqualsAndTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(todaysdate, unitList);
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return tBillBillRepository.findAllByTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList);
            } else {
                return tBillBillRepository.findAllByBillFromToDateAndTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(startdate, enddate, unitList);
            }
        } else {
            if (!search.equals("")) {
                System.out.println("-------------all search------------------------");
                return tBillBillRepository.findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(search,
                        search, search, search, search, unitList, PageRequest.of(1, 20));
            } else if (today.equals(true)) {
                java.sql.Date todaysdate;
                LocalDate ltoday = LocalDate.now();
                todaysdate = java.sql.Date.valueOf(ltoday);
                System.out.println("-------------today search------------------------");
                return tBillBillRepository.findAllByBillDateEqualsAndTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(todaysdate, unitList, PageRequest.of(1, 20));
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return tBillBillRepository.findAllByTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList, PageRequest.of(1, 20));
            } else {
                col = "bill_id";
                return tBillBillRepository.findAllByBillFromToDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(startdate, enddate, PageRequest.of(1, 20));
            }
        }
    }

    @RequestMapping("collectionsummery")
    public Map<String, Double> collectionsummery(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        JSONObject json = new JSONObject(search);
        System.out.println(json);
        Double ipdTotalCollection = 0.00;
        Double opdTotalCollection = 0.00;
        String fromdate = search.contains("fromdate") ? json.getString("fromdate") : null;
        String todate = search.contains("todate") ? json.getString("todate") : null;
        Boolean billtoday = search.contains("today") && json.getBoolean("today");
        long unitId = search.contains("unitId") ? json.getLong("unitId") : null;
        System.out.println(todate);
        try {
            if (billtoday.equals(true)) {
                System.out.println("-------------today search------------------------");
                ipdTotalCollection = tBillBillRepository.findByTodayBillipdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitId);
            } else if (todate == null || todate == "" || todate.equals("") || todate.equals(null)) {
                ipdTotalCollection = tBillBillRepository.findByBillipdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitId);
            } else {
                ipdTotalCollection = tBillBillRepository.findByFromTodateBillipdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(fromdate, todate, unitId);
            }
        } catch (Exception e) {
            ipdTotalCollection = 0.00;
        }
        try {
            if (billtoday.equals(true)) {
                System.out.println("-------------today search------------------------");
                opdTotalCollection = tBillBillRepository.findByTodayBillopdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitId);
            } else if (todate == null || todate == "" || todate.equals("") || todate.equals(null)) {
                opdTotalCollection = tBillBillRepository.findByBillopdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitId);
            } else {
                opdTotalCollection = tBillBillRepository.findByFromTodateBillopdamountAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(fromdate, todate, unitId);
            }
        } catch (Exception e) {
            opdTotalCollection = 0.00;
        }
        respMap.put("ipdTotalCollection", ipdTotalCollection);
        respMap.put("opdTotalCollection", opdTotalCollection);
        return respMap;
    }

    @RequestMapping("getpharmacyreturnlist/{unitList}")
    public Iterable<TinvItemSalesReturn> getpharmacyreturnlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        JSONArray list = new JSONArray();
        System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "isrId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        long unitid = search_query.contains("unitid") ? json.getLong("unitid") : null;
        System.out.println("---page------");
        System.out.println(page);
        System.out.println(size);
        System.out.println(search);
        if (print.equals(true)) {
            if (!search.equals("")) {
                System.out.println("-------------all search------------------------");
                return tinvItemSalesReturnRepository.findAllByIsrPsIdPsVisitIdVisitPatientIdPatientMrNoContainsOrIsrPsIdPsPatientFirstNameContainsOrIsrPsIdPsPatientMiddleNameContainsOrIsrPsIdPsPatientLastNameContainsAndIsActiveTrueAndIsDeletedFalse(search, search,
                        search, search);
            } else if (today.equals(true)) {
                return tinvItemSalesReturnRepository.findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse();
            } else if (!enddate.equals("") && !enddate.equals(null) && enddate != "" && enddate != null) {
                return tinvItemSalesReturnRepository.findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(startdate, enddate);
            } else if (unitList[0] != 0) {
                return tinvItemSalesReturnRepository.findAllByIsrUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(unitList);
            } else {
                return tinvItemSalesReturnRepository.findAllByIsActiveTrueAndIsDeletedFalse();
            }
        } else {
            if (!search.equals("")) {
                System.out.println("-------------all search------------------------");
                return tinvItemSalesReturnRepository.findAllByIsrPsIdPsVisitIdVisitPatientIdPatientMrNoContainsOrIsrPsIdPsPatientFirstNameContainsOrIsrPsIdPsPatientMiddleNameContainsOrIsrPsIdPsPatientLastNameContainsAndIsActiveTrueAndIsDeletedFalse(search, search,
                        search, search, PageRequest.of(1, 20));
            } else if (today.equals(true)) {
                col = "isr_id";
                return tinvItemSalesReturnRepository.findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(1, 20));
            } else if (!enddate.equals("") && !enddate.equals(null) && enddate != "" && enddate != null) {
                col = "isr_id";
                return tinvItemSalesReturnRepository.findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, PageRequest.of(1, 20));
            } else if (unitList[0] != 0) {
                return tinvItemSalesReturnRepository.findAllByIsrUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(unitList, PageRequest.of(1, 20));
            } else {
                return tinvItemSalesReturnRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(1, 20));
            }
        }
    }

    @RequestMapping("getpharmacybilllist/{fromdate}/{todate}/{unitList}")
    public Iterable<TinvPharmacySale> getpharmacybilllist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String model, @PathVariable String fromdate, @PathVariable String todate, @PathVariable Long[] unitList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        JSONObject json = new JSONObject(model);
        JSONArray list = new JSONArray();
        System.out.println(json);
        int page = model.contains("page") ? json.getInt("page") : 1;
        int size = model.contains("size") ? json.getInt("size") : 10;
        String sort = model.contains("sort") ? json.getString("sort") : "DESC";
        String col = model.contains("col") ? json.getString("col") : "psId";
        // String search = model.contains("search") ? json.getString("search") : "";
        String allsearch = model.contains("allsearch") ? json.getString("allsearch") : "";
        Boolean today = model.contains("today") && json.getBoolean("today");
        Boolean print = model.contains("print") && json.getBoolean("print");
        // String fromdate = model.contains("fromdate") ? json.getString("fromdate") : "";
        // String todate = model.contains("todate") ? json.getString("todate") : "";
        long unitid = model.contains("unitId") ? json.getLong("unitId") : 0;
        System.out.println("---page------");
        System.out.println(page);
        System.out.println(page);
        System.out.println(size);
        // System.out.println(search);
        if (print.equals(true)) {
            if (!allsearch.equals("")) {
                System.out.println("-------------all allsearch------------------------");
                return tinvPharmacySaleRepository.findAllByPsVisitIdVisitPatientIdPatientMrNoContainsOrPsPatientFirstNameContainsOrPsPatientMiddleNameContainsOrPsPatientLastNameContainsAndIsActiveTrueAndIsDeletedFalse(allsearch, allsearch,
                        allsearch, allsearch);
            } else if (today.equals(true)) {
                return tinvPharmacySaleRepository.findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse();
            } else if (!todate.equals("") && !todate.equals(null) && todate != "" && todate != null) {
                return tinvPharmacySaleRepository.findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(fromdate, todate);

            } else if (unitList[0] != 0) {
                return tinvPharmacySaleRepository.findAllByPharmacyUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(unitList);
            } else {
                return tinvPharmacySaleRepository.findAllByIsActiveTrueAndIsDeletedFalse();
            }
        } else {
            if (!allsearch.equals("")) {
                System.out.println("-------------all allsearch------------------------");
                return tinvPharmacySaleRepository.findAllByPsVisitIdVisitPatientIdPatientMrNoContainsOrPsPatientFirstNameContainsOrPsPatientMiddleNameContainsOrPsPatientLastNameContainsAndIsActiveTrueAndIsDeletedFalse(allsearch, allsearch,
                        allsearch, allsearch, PageRequest.of(1, 20));
            } else if (today.equals(true)) {
                col = "ps_id";
                return tinvPharmacySaleRepository.findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(1, 20));
            } else if (!todate.equals("") && !todate.equals(null) && todate != "" && todate != null && unitList[0] != 0) {
                col = "ps_id";
                return tinvPharmacySaleRepository.findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(fromdate, todate, unitid, PageRequest.of(1, 20));
            } else if (unitList[0] != 0) {
                return tinvPharmacySaleRepository.findAllByPharmacyUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(1, 20), unitList);

            } else {
                return tinvPharmacySaleRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(1, 20));
            }

        }
    }

    @RequestMapping("getpharmacybilllistPrint/{fromdate}/{todate}/{unitList}")
    public String getpharmacybilllistPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody String model, @PathVariable String fromdate, @PathVariable String todate, @PathVariable Long[] unitList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        List<TinvPharmacySale> list1 = new ArrayList<TinvPharmacySale>();
        JSONObject json = new JSONObject(model);
        JSONArray list = new JSONArray();
        System.out.println(json);
        int page = model.contains("page") ? json.getInt("page") : 1;
        int size = model.contains("size") ? json.getInt("size") : 10;
        String sort = model.contains("sort") ? json.getString("sort") : "DESC";
        String col = model.contains("col") ? json.getString("col") : "psId";
        // String sort = model.contains("sort") ? json.getString("sort") : "DESC";
        // String col = model.contains("col") ? json.getString("col") : "psId";
        String allsearch = model.contains("allsearch") ? json.getString("allsearch") : "";
        Boolean today = model.contains("today") && json.getBoolean("today");
        Boolean print = model.contains("print") && json.getBoolean("print");
        //  String fromdate = model.contains("fromdate") ? json.getString("fromdate") : null;
        // String todate = model.contains("todate") ? json.getString("todate") : null;
        long unitid = model.contains("unitId") ? json.getLong("unitId") : 0;
        System.out.println("---page------");
        System.out.println(page);
        System.out.println(page);
        System.out.println(size);
        System.out.println(allsearch);
        if (!allsearch.equals("")) {
            System.out.println("-------------all search------------------------");
            list1 = tinvPharmacySaleRepository.findAllByPsVisitIdVisitPatientIdPatientMrNoContainsOrPsPatientFirstNameContainsOrPsPatientMiddleNameContainsOrPsPatientLastNameContainsAndIsActiveTrueAndIsDeletedFalse(allsearch, allsearch,
                    allsearch, allsearch);
        } else if (today.equals(true)) {
            list1 = tinvPharmacySaleRepository.findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse();
        } else if (!todate.equals("") && !todate.equals(null) && todate != "" && todate != null) {
            list1 = tinvPharmacySaleRepository.findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(fromdate, todate, unitid);
            // list1 =  tinvPharmacySaleRepository.findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(fromdate, todate, unitid, PageRequest.of(1,20));
        } else if (unitList[0] != 0) {
            list1 = tinvPharmacySaleRepository.findAllByPharmacyUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(unitList);
        } else {
            list1 = tinvPharmacySaleRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        }
        //return null
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(unitid);
        list1.get(0).setHeaderObject(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list1);
        // return createReport.createJasperReportXLS("OutwardReferralReport", ds);
        if (print) {
            return createReport.createJasperReportXLS("ListofPharmacyBillReport", ds);
        } else {
            return createReport.createJasperReportPDF("ListofPharmacyBillReport", ds);
        }
    }

    @RequestMapping("pathlogylist/{unitList}")
    public Iterable<TpathBs> pathlogylist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "psId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        long unitid = search_query.contains("unitid") ? json.getLong("unitid") : null;
        System.out.println("---page------");
        System.out.println(page);
        System.out.println(page);
        System.out.println(size);
        System.out.println(search);
        if (print.equals(true)) {
            if (!search.equals("")) {
                System.out.println("-------------all search------------------------");
//                return tpathBsRepository.findByPsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsOrPsBsIdBsServiceIdServiceSgIdSgNameContainsOrPsBsIdBsStaffIdStaffUserIdUserFirstnameContainsOrPsBsIdBsStaffIdStaffUserIdUserLastnameContainsAndPsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(search,search,
//                       search,search,search,search,search,unitid);
                return null;
            } else if (today.equals(true)) {
                System.out.println("-------------today search------------------------");
                return tpathBsRepository.findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(unitList);
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return tpathBsRepository.findAllByPsBillIdTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(unitList);
            } else {
                return tpathBsRepository.findAllByToFromCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, unitList);
            }
        } else {
            if (!search.equals("")) {
                System.out.println("-------------all search------------------------");
                return null;
//               return tpathBsRepository.findByPsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrPsBillIdBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsOrPsBsIdBsServiceIdServiceSgIdSgNameContainsOrPsBsIdBsStaffIdStaffUserIdUserFirstnameContainsOrPsBsIdBsStaffIdStaffUserIdUserLastnameContainsAndPsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(search, search,
//                      search, search, search, search, search,unitid, PageRequest.of(1,20));
            } else if (today.equals(true)) {
                col = "ps_id";
                System.out.println("-------------today search------------------------");
                return tpathBsRepository.findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(unitList, PageRequest.of(1, 20));
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return tpathBsRepository.findAllByPsBillIdTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(unitList, PageRequest.of(1, 20));
            } else {
                col = "ps_id";
                return tpathBsRepository.findAllByToFromCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, unitList, PageRequest.of(1, 20));
            }
        }
    }

    @RequestMapping("getservicelistbytariff")
    public Iterable<TbillBillService> getservicelistbytariff(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        // System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "psId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        int tariff = search_query.contains("tariff") ? json.getInt("tariff") : 0;
        // System.out.println("---page------");
        // System.out.println(page);
        // System.out.println(page);
        // System.out.println(size);
        // System.out.println(search);
        col = "bs_id";
        if (print.equals(true)) {
            //today without tariff search
            if (today.equals(true) && tariff == 0 && (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null)) {
                return tbillBillServiceRepository.findAllbyTariffTodaySearchPrint();
            }
            //today with tariff search
            else if (today.equals(true) && tariff != 0 && (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null)) {
                return tbillBillServiceRepository.findAllbyTariffTodaySearchWithTariffPrint(tariff);
            }
            //normal list
            if ((enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) && today.equals(false) && tariff == 0) {
                return tbillBillServiceRepository.findAllbyTariffPrint();
            } else
                //from to search with tariff
                if (tariff != 0 && (!enddate.equals("") || !enddate.equals(null) || enddate != "" || enddate != null)) {
                    return tbillBillServiceRepository.findAllbyTariffFromToDatewithTariffPrint(startdate, enddate, tariff);
                }
                //from to search without tariff
                else {
                    col = "bs_id";
                    return tbillBillServiceRepository.findAllbyTariffFromToDatePrint(startdate, enddate);
                }
        } else {
            //oday with tariff search
            if (today.equals(true) && tariff == 0 && (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null)) {
                // System.out.println("------------today with tariff search----------------------");
                return tbillBillServiceRepository.findAllbyTariffTodaySearch(PageRequest.of(1, 20));
            }
            //today with tariff search
            else if (today.equals(true) && tariff != 0 && (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null)) {
                // System.out.println("-------------oday with tariff search-----------------------");
                return tbillBillServiceRepository.findAllbyTariffTodaySearchWithTariff(tariff, PageRequest.of(1, 20));
            }
            //normal list
            if ((enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) && today.equals(false) && tariff == 0) {
                // System.out.println("-------------/normal list----------------------");
                return tbillBillServiceRepository.findAllbyTariff(PageRequest.of(1, 20));
            } else
                //from to search with tariff
                if (tariff != 0 && (!enddate.equals("") || !enddate.equals(null) || enddate != "" || enddate != null)) {
                    // System.out.println("-------------from to search with tariff------------------------");
                    return tbillBillServiceRepository.findAllbyTariffFromToDatewithTariff(startdate, enddate, tariff, PageRequest.of(1, 20));
                }
                //from to search without tariff
                else {
                    // System.out.println("-------------from to search without tariff---------------------");
                    col = "bs_id";
                    return tbillBillServiceRepository.findAllbyTariffFromToDate(startdate, enddate, PageRequest.of(1, 20));
                }
        }

    }

    @RequestMapping("getrecordlist")
    public List<RegistrationRecord> getregipatientlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        // System.out.println(json);
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        List<RegistrationRecord> recordlist = new ArrayList<RegistrationRecord>();
        List<MstUnit> objunitlist = unitlist(tenantName);
        for (MstUnit obj : objunitlist) {
            RegistrationRecord objrecord = new RegistrationRecord();
            objrecord.setStrUnitname(obj.getUnitName());
            if (today) {
                objrecord.setMaleCount(mstVisitRepository.findAllBytodayIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 1));
                objrecord.setFemaleCount(mstVisitRepository.findAllBytodayIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 2));
                objrecord.setOthersCount(mstVisitRepository.findAllBytodayIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 3));
                objrecord.setNotspeCount(mstVisitRepository.findAllBytodayIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 4));
            } else if (!enddate.equals("") || !enddate.equals(null)) {
                objrecord.setMaleCount(mstVisitRepository.findAllByformdatetodateIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 1, startdate, enddate));
                objrecord.setFemaleCount(mstVisitRepository.findAllByformdatetodateIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 2, startdate, enddate));
                objrecord.setOthersCount(mstVisitRepository.findAllByformdatetodateIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 3, startdate, enddate));
                objrecord.setNotspeCount(mstVisitRepository.findAllByformdatetodateIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 4, startdate, enddate));
            }
            if (!(today) && (enddate.equals("") || enddate.equals(null))) {
                objrecord.setMaleCount(mstVisitRepository.findAllByunitIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 1));
                objrecord.setFemaleCount(mstVisitRepository.findAllByunitIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 2));
                objrecord.setOthersCount(mstVisitRepository.findAllByunitIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 3));
                objrecord.setNotspeCount(mstVisitRepository.findAllByunitIsActiveTrueAndIsDeletedFalse(obj.getUnitId(), 4));
            }
            objrecord.setPatientCount(objrecord.maleCount + objrecord.getFemaleCount() + objrecord.getOthersCount() + objrecord.getNotspeCount());
            recordlist.add(objrecord);
        }
        return recordlist;
    }

    public List<MstUnit> unitlist(@RequestHeader("X-tenantId") String tenantName) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        return mstUnitRepository.findAll();
    }

    // 03-03-2020 Unit Wise Receciept List
    @RequestMapping("unitwiserecieptlist/{unitList}")
    public ResponseEntity unitwiserecieptlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        // System.out.println(json);
        int page = search_query.contains("offset") ? json.getInt("offset") : 1;
        int size = search_query.contains("limit") ? json.getInt("limit") : 20;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "brId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String fromdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String todate = search_query.contains("enddate") ? json.getString("enddate") : null;
        long tariffId = search_query.contains("tariffId") ? json.getLong("tariffId") : 0;
        long unitId = search_query.contains("unitId") ? json.getLong("unitId") : 0;
        String query2 = " select ifnull(mu.unit_name,'') as unitName, ifnull(tb.bill_number,'') as billNumber,ifnull(tb.bill_date,'') as billDate,ifnull(tr.br_reciept_number,'') as receiptNumber," +
                " ifnull(mp.patient_mr_no,'') as patientMrNo,CONCAT(ifnull(muser.user_firstname,''),' ', ifnull(muser.user_middlename,''),' ',ifnull(muser.user_lastname,'')) as patientName," +
                " ifnull(mv.reason_visit,'') as reasonVisit, ifnull(mt.tariff_name,'') as tariffName,ifnull(tr.br_paid_by,'') as paidBy, ifnull(pm.pm_name,'') as pmName, " +
                " ifnull(tr.br_payment_amount,'') as paymentAmount, ifnull(tr.br_payment_remaing,'') as paymentRemaining from tbill_reciept  tr " +
                " LEFT JOIN tbill_bill tb on  tb.bill_id = tr.br_bill_id " +
                " LEFT JOIN mbill_tariff mt on mt.tariff_id = tb.bill_tariff_id " +
                " LEFT JOIN mst_patient mp on mp.patient_id = tr.br_patient_id " +
                " LEFT JOIN mst_user muser on muser.user_id = mp.patient_user_id " +
                " LEFT JOIN mst_unit mu on mu.unit_id = tb.tbill_unit_id " +
                " LEFT JOIN mst_visit mv on mv.visit_id = tb.bill_visit_id " +
                " LEFT JOIN mst_payment_mode pm on pm.pm_id = tr.br_pm_id " +
                " where tr.is_cancelled = false and ";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = strDate;
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        query2 += " (date(tb.created_date) between '" + fromdate + "' and '" + todate + "') ";
        if (tariffId != 0) {
            query2 += " and tb.bill_tariff_id=" + tariffId + " ";
        }
        if (!search.equals("")) {
            query2 += " and (tb.bill_number LIKE '%" + search + "%' or mp.patient_mr_no like '%" + search + "%' or concat(muser.user_firstname,' ', muser.user_lastname) like '%" + search + "%' ) ";
        }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and tb.tbill_unit_id IN (" + values + ")";
        }
        query2 += " order by tr.br_id desc ";
        String CountQuery = " select count(*) from ( " + query2 + " ) as combine ";
        query2 += " limit " + ((page - 1) * size) + "," + size;
        String columnName = "unitName,billNumber,billDate,receiptNumber,patientMrNo,patientName,reasonVisit,tariffName,paidBy,pmName,paymentAmount,paymentRemaining";
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, query2, CountQuery));
    }

    //03-03-2020 Unit Wise Reciept List Print.
    @RequestMapping("unitwiserecieptlistprint/{unitList}")
    public String unitwiserecieptlistprint(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        JSONArray jsonArray = null;
        JSONObject json = new JSONObject(search_query);
        // System.out.println(json);
        int page = search_query.contains("offset") ? json.getInt("offset") : 1;
        int size = search_query.contains("limit") ? json.getInt("limit") : 20;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "brId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String fromdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String todate = search_query.contains("enddate") ? json.getString("enddate") : null;
        long tariffId = search_query.contains("tariffId") ? json.getLong("tariffId") : 0;
        long unitId = search_query.contains("unitId") ? json.getLong("unitId") : 0;
        long currentUnitId = search_query.contains("currentUnitId") ? json.getLong("currentUnitId") : 0;
        long userId = search_query.contains("userId") ? json.getLong("userId") : 0;
        String query2 = " select ifnull(mu.unit_name,'') as unitName, ifnull(tb.bill_number,'') as billNumber,ifnull(tb.bill_date,'') as billDate,ifnull(tr.br_reciept_number,'') as receiptNumber," +
                " ifnull(mp.patient_mr_no,'') as patientMrNo,CONCAT(ifnull(muser.user_firstname,''),' ', ifnull(muser.user_middlename,''),' ',ifnull(muser.user_lastname,'')) as patientName," +
                " ifnull(mv.reason_visit,'') as reasonVisit, ifnull(mt.tariff_name,'') as tariffName,ifnull(tr.br_paid_by,'') as paidBy, ifnull(pm.pm_name,'') as pmName, " +
                " ifnull(tr.br_payment_amount,'') as paymentAmount, ifnull(tr.br_payment_remaing,'') as paymentRemaining from tbill_reciept  tr " +
                " LEFT JOIN tbill_bill tb on  tb.bill_id = tr.br_bill_id " +
                " LEFT JOIN mbill_tariff mt on mt.tariff_id = tb.bill_tariff_id " +
                " LEFT JOIN mst_patient mp on mp.patient_id = tr.br_patient_id " +
                " LEFT JOIN mst_user muser on muser.user_id = mp.patient_user_id " +
                " LEFT JOIN mst_unit mu on mu.unit_id = tb.tbill_unit_id " +
                " LEFT JOIN mst_visit mv on mv.visit_id = tb.bill_visit_id " +
                " LEFT JOIN mst_payment_mode pm on pm.pm_id = tr.br_pm_id " +
                " where tr.is_cancelled = false and ";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = strDate;
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        query2 += " (date(tb.created_date) between '" + fromdate + "' and '" + todate + "') ";
        if (tariffId != 0) {
            query2 += " and tb.bill_tariff_id=" + tariffId + " ";
        }
        if (!search.equals("")) {
            query2 += " and (tb.bill_number LIKE '%" + search + "%' or mp.patient_mr_no like '%" + search + "%' or concat(muser.user_firstname,' ', muser.user_lastname) like '%" + search + "%' ) ";
        }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and tb.tbill_unit_id IN (" + values + ")";
        }
        query2 += " order by tr.br_id desc ";
        String CountQuery = " select count(*) from ( " + query2 + " ) as combine ";
        String columnName = "unitName,billNumber,billDate,receiptNumber,patientMrNo,patientName,reasonVisit,tariffName,paidBy,pmName,paymentAmount,paymentRemaining";
        jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, query2, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(currentUnitId);
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(userId);
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        if (jsonArray != null) {
            jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
            jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        }
        if (print) {
            columnName = "unitName,billNumber,billDate,receiptNumber,patientMrNo,patientName,reasonVisit,tariffName,paidBy,pmName,paymentAmount,paymentRemaining";
            return createReport.generateExcel(columnName, "BillingPatientWiseBillReceiptReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("BillingPatientWiseBillReceiptReport", jsonArray.toString());
        }
    }

    /*@RequestMapping("unitwiserecieptlist/{unitList}")
    public Iterable<TbillReciept> unitwiserecieptlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws JSONException {
        // System.out.println("---object------");

        JSONObject json = new JSONObject(search_query);
        // System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "brId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") ? json.getBoolean("today") : false;
        Boolean print = search_query.contains("print") ? json.getBoolean("print") : false;
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        String tariffId = search_query.contains("tariffId") ? json.getString("tariffId") : null;
        long unitId = search_query.contains("unitId") ? json.getLong("unitId") : 0;
        // System.out.println("---page------");
        // System.out.println(page);
        // System.out.println(size);
        // System.out.println(search);

        if (print.equals(true)) {
            if (!search.equals("")) {

                // System.out.println("-------------all search------------------------");
                return tbillRecieptRepository.findAllByBrBillIdTbillUnitIdUnitIdInAndBrPatientIdPatientUserIdUserFirstnameContainsOrBrPatientIdPatientUserIdUserLastnameContainsOrBrPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList, search, search, search);
            } else if (today.equals(true)) {

                java.sql.Date todaysdate;
                LocalDate ltoday = LocalDate.now();
                todaysdate = java.sql.Date.valueOf(ltoday);
                // System.out.println("-------------today search------------------------");
                return tbillRecieptRepository.findAllByBrBillIdTbillUnitIdUnitIdInAndBrCheckDateEqualsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList, todaysdate);
            } else if (tariffId != null) {
                return tbillRecieptRepository.findAllByBrBillIdTbillUnitIdUnitIdInAndBrBillIdBillTariffIdTariffIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList, Long.parseLong(tariffId));
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return tbillRecieptRepository.findAllByBrBillIdTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList);
            } else {
                return tbillRecieptRepository.findAllByBrBillIdFromToDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList, startdate, enddate);
            }
        } else {
            if (!search.equals("")) {

                // System.out.println("-------------all search------------------------");
                return tbillRecieptRepository.findAllByBrBillIdTbillUnitIdUnitIdInAndBrPatientIdPatientUserIdUserFirstnameContainsOrBrPatientIdPatientUserIdUserLastnameContainsOrBrPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList, search, search, search, PageRequest.of(1,20));
            } else if (today.equals(true)) {

                java.sql.Date todaysdate;
                LocalDate ltoday = LocalDate.now();
                todaysdate = java.sql.Date.valueOf(ltoday);

                // System.out.println("-------------today search------------------------");
                return tbillRecieptRepository.findAllByBrBillIdTbillUnitIdUnitIdInAndBrCheckDateEqualsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList, todaysdate, PageRequest.of(1,20));
            } else if (tariffId != null) {
                return tbillRecieptRepository.findAllByBrBillIdTbillUnitIdUnitIdInAndBrBillIdBillTariffIdTariffIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList, Long.parseLong(tariffId), PageRequest.of(1,20));
            } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                return tbillRecieptRepository.findAllByBrBillIdTbillUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList, PageRequest.of(1,20));
            } else {
                col = "br_id";
                return tbillRecieptRepository.findAllByBrBillIdFromToDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitList, startdate, enddate, PageRequest.of(1,20));
            }
        }
    }*/


    /*@RequestMapping("enventoryexpiryReport/{unitList}")
    public Iterable<TinvOpeningBalanceItem> enventoryexpiryReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws JSONException {
        System.out.println("---object------");

        JSONObject json = new JSONObject(search_query);
        System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "obi_id";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") ? json.getBoolean("today") : false;
        Boolean print = search_query.contains("print") ? json.getBoolean("print") : false;
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        long unitId = search_query.contains("unitId") ? json.getLong("unitId") : 0;
        System.out.println("---page------");
        System.out.println(page);
        System.out.println(size);
        System.out.println(search);


        return tinvOpeningBalanceItemRepository.findAllByObiItemExpiryDateAndIsActiveTrueAndIsDeletedFalse(new Date(), unitList, PageRequest.of(1,20));
//            if (print.equals(true)) {
//                if (!search.equals("")) {
//                    System.out.println("-------------all search------------------------");
//                    return tBillBillRepository.findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(search,
//                            search, search, search, search);
//                } else if (today.equals(true)) {search
//                    java.sql.Date todaysdate;
//                    LocalDate ltoday = LocalDate.now();
//                    todaysdate = java.sql.Date.valueOf(ltoday);
//                    System.out.println("-------------today search------------------------");
//                    return tBillBillRepository.findAllByBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(todaysdate);
//                } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
//                    return tBillBillRepository.findAllByIsActiveTrueAndIsDeletedFalse();
//                } else {
//                    return tBillBillRepository.findAllByBillFromToDateAndIsActiveTrueAndIsDeletedFalse(startdate, enddate);
//                }
//            } else {
//                if (!search.equals("")) {
//
//                    System.out.println("-------------all search------------------------");
//                    return tBillBillRepository.findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(search,
//                            search, search, search, search, PageRequest.of(1,20));
//                } else if (today.equals(true)) {
//
//                    java.sql.Date todaysdate;
//                    LocalDate ltoday = LocalDate.now();
//                    todaysdate = java.sql.Date.valueOf(ltoday);
//
//                    System.out.println("-------------today search------------------------");
//                    return tBillBillRepository.findAllByBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(todaysdate, PageRequest.of(1,20));
//                } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
//                    return tBillBillRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(1,20));
//                } else {
//                    col = "bill_id";
//                    return tBillBillRepository.findAllByBillFromToDateAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, PageRequest.of(1,20));
//                }
//            }

    }*/

    // 24-02-2020
    @RequestMapping("enventoryexpiryReport/{unitList}")
    public Iterable<TinvOpeningBalanceItem> enventoryexpiryReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable Long[] unitList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "obi_id";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        long unitId = search_query.contains("unitId") ? json.getLong("unitId") : 0;
        System.out.println("---page------");
        System.out.println(page);
        System.out.println(size);
        System.out.println(search);
        return tinvOpeningBalanceItemRepository.findAllByObiItemExpiryDateAndIsActiveTrueAndIsDeletedFalse(new Date(), unitList, PageRequest.of(1, 20));
//            if (print.equals(true)) {
//                if (!search.equals("")) {
//                    System.out.println("-------------all search------------------------");
//                    return tBillBillRepository.findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(search,
//                            search, search, search, search);
//                } else if (today.equals(true)) {search
//                    java.sql.Date todaysdate;
//                    LocalDate ltoday = LocalDate.now();
//                    todaysdate = java.sql.Date.valueOf(ltoday);
//                    System.out.println("-------------today search------------------------");
//                    return tBillBillRepository.findAllByBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(todaysdate);
//                } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
//                    return tBillBillRepository.findAllByIsActiveTrueAndIsDeletedFalse();
//                } else {
//                    return tBillBillRepository.findAllByBillFromToDateAndIsActiveTrueAndIsDeletedFalse(startdate, enddate);
//                }
//            } else {
//                if (!search.equals("")) {
//
//                    System.out.println("-------------all search------------------------");
//                    return tBillBillRepository.findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(search,
//                            search, search, search, search, PageRequest.of(1,20));
//                } else if (today.equals(true)) {
//
//                    java.sql.Date todaysdate;
//                    LocalDate ltoday = LocalDate.now();
//                    todaysdate = java.sql.Date.valueOf(ltoday);
//
//                    System.out.println("-------------today search------------------------");
//                    return tBillBillRepository.findAllByBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(todaysdate, PageRequest.of(1,20));
//                } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
//                    return tBillBillRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(1,20));
//                } else {
//                    col = "bill_id";
//                    return tBillBillRepository.findAllByBillFromToDateAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, PageRequest.of(1,20));
//                }
//            }
    }
//    SELECT s FROM TinvOpeningBalanceItem s where
//    and  CAST(s.obiItemId.itemMinimumQuantity) AS INT>s.obiItemQuantity and (s.createdDate between '1998-12-21' and '2018-06-12')
//    @RequestMapping("enventoryItemMinQtyReport")
//    public Iterable<TinvOpeningBalanceItem> enventoryItemMinQtyReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query) throws JSONException {
//        System.out.println("---object------");
//
//        JSONObject json = new JSONObject(search_query);
//        System.out.println(json);
//        int page = search_query.contains("page") ? json.getInt("page") : 1;
//        int size = search_query.contains("size") ? json.getInt("size") : 100;
//        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
//        String col = search_query.contains("col") ? json.getString("col") : "obi_id";
//        String search = search_query.contains("search") ? json.getString("search") : "";
//        Boolean today = search_query.contains("today") ? json.getBoolean("today") : false;
//        Boolean print = search_query.contains("print") ? json.getBoolean("print") : false;
//        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
//        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
//        long unitId = search_query.contains("unitId") ? json.getLong("unitId") : 0;
//        System.out.println("---page------");
//        System.out.println(page);
//        System.out.println(size);
//        System.out.println(search);
//
//
//        return tinvOpeningBalanceItemRepository.findInvenItemMinQtyReport(new Date(),PageRequest.of(1,20));
////            if (print.equals(true)) {
////                if (!search.equals("")) {
////                    System.out.println("-------------all search------------------------");
////                    return tBillBillRepository.findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(search,
////                            search, search, search, search);
////                } else if (today.equals(true)) {
////                    java.sql.Date todaysdate;
////                    LocalDate ltoday = LocalDate.now();
////                    todaysdate = java.sql.Date.valueOf(ltoday);
////                    System.out.println("-------------today search------------------------");
////                    return tBillBillRepository.findAllByBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(todaysdate);
////                } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
////                    return tBillBillRepository.findAllByIsActiveTrueAndIsDeletedFalse();
////                } else {
////                    return tBillBillRepository.findAllByBillFromToDateAndIsActiveTrueAndIsDeletedFalse(startdate, enddate);
////                }
////            } else {
////                if (!search.equals("")) {
////
////                    System.out.println("-------------all search------------------------");
////                    return tBillBillRepository.findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(search,
////                            search, search, search, search, PageRequest.of(1,20));
////                } else if (today.equals(true)) {
////
////                    java.sql.Date todaysdate;
////                    LocalDate ltoday = LocalDate.now();
////                    todaysdate = java.sql.Date.valueOf(ltoday);
////
////                    System.out.println("-------------today search------------------------");
////                    return tBillBillRepository.findAllByBillDateEqualsAndIsActiveTrueAndIsDeletedFalse(todaysdate, PageRequest.of(1,20));
////                } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
////                    return tBillBillRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(1,20));
////                } else {
////                    col = "bill_id";
////                    return tBillBillRepository.findAllByBillFromToDateAndIsActiveTrueAndIsDeletedFalse(startdate, enddate, PageRequest.of(1,20));
////                }
////            }
//
//    }

    @RequestMapping("billlist/{mstunitList}")
    public Iterable<TBillBill> billlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable long[] mstunitList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        JSONObject json = new JSONObject(search_query);
        System.out.println(json);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "billId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        long staffid = search_query.contains("staff") ? json.getLong("staff") : 0;
        long staffid1 = search_query.contains("staff1") ? json.getLong("staff1") : 0;
        System.out.println("---page------");
        System.out.println(page);
        System.out.println(size);
        System.out.println(search);
        System.out.println(staffid);
        if (staffid1 == 0) { // IpdBillFalseAnd
            if (staffid == 0) {
                if (print.equals(true)) {
                    if (!search.equals("")) {
                        return tBillBillRepository.findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(search, search, search, search, search);
                    } else if (today.equals(true)) {
                        java.sql.Date todaysdate;
                        LocalDate ltoday = LocalDate.now();
                        todaysdate = java.sql.Date.valueOf(ltoday);
                        return tBillBillRepository.findAllByBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(todaysdate);
                    } else if (mstunitList[0] != 0) {
                        long[] temp = null;
//                        for(int i=0;i<mstunitList.length;i++){
//                            temp[i]=Long.parseLong(mstunitList[i]);
//                        }
                        return tBillBillRepository.findAllByTbillUnitIdUnitIdIn(mstunitList);
                    } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                        return tBillBillRepository.findAllByIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse();
                    } else {
                        return tBillBillRepository.findAllByIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(startdate, enddate);
                    }
                } else {
                    if (!search.equals("")) {
                        return tBillBillRepository.findAllByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(search, search, search, search, search, PageRequest.of(1, 20));
                    } else if (today.equals(true)) {
                        java.sql.Date todaysdate;
                        LocalDate ltoday = LocalDate.now();
                        todaysdate = java.sql.Date.valueOf(ltoday);
                        return tBillBillRepository.findAllByBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(todaysdate, PageRequest.of(1, 20));
                    } else if (mstunitList[0] != 0) {
                        long[] temp = null;
//                        for(int i=0;i<mstunitList.length;i++){
//                            temp[i]=(long)mstunitList[i];
//                        }
                        return tBillBillRepository.findAllByTbillUnitIdUnitIdIn(mstunitList, PageRequest.of(1, 20));
                    } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                        return tBillBillRepository.findAllByIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(PageRequest.of(1, 20));
                    } else {
                        col = "bill_id";
                        return tBillBillRepository.findAllByIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(startdate, enddate, PageRequest.of(1, 20));
                    }
                }
            } else {
                if (print.equals(true)) {
                    if (!search.equals("")) {
                        return null;
                        // return tBillBillRepository.findAllBillUserIdStaffIdEqualsAndByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid, search, search, search, search, search);
                    } else if (today.equals(true)) {
                        java.sql.Date todaysdate;
                        LocalDate ltoday = LocalDate.now();
                        todaysdate = java.sql.Date.valueOf(ltoday);
                        return tBillBillRepository.findAllByBillUserIdStaffIdEqualsAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid, todaysdate);
                    } else if (mstunitList[0] != 0) {
                        long[] temp = null;
//                        for(int i=0;i<mstunitList.length;i++){
//                            temp[i]=Long.parseLong(mstunitList[i]);
//                        }
                        return tBillBillRepository.findAllByBillUserIdStaffIdEqualsAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid, mstunitList);
                    } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                        return tBillBillRepository.findAllByBillUserIdStaffIdEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid);
                    } else {
                        return tBillBillRepository.findAllByBillUserIdBillFromToDateAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid, startdate, enddate);
                    }
                } else {
                    if (!search.equals("")) {
                        return null;
//                        return tBillBillRepository.findAllBillUserIdStaffIdEqualsAndByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid, search,
//                                search, search, search, search, PageRequest.of(1,20));
                    } else if (today.equals(true)) {
                        java.sql.Date todaysdate;
                        LocalDate ltoday = LocalDate.now();
                        todaysdate = java.sql.Date.valueOf(ltoday);
                        return tBillBillRepository.findAllByBillUserIdStaffIdEqualsAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid, todaysdate, PageRequest.of(1, 20));
                    } else if (mstunitList[0] != 0) {
                        long[] temp = null;
//                        for(int i=0;i<mstunitList.length;i++){
//                            temp[i]=Long.parseLong(mstunitList[i]);
//                        }
                        return tBillBillRepository.findAllByBillUserIdStaffIdEqualsAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid, mstunitList, PageRequest.of(1, 20));
                    } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                        return tBillBillRepository.findAllByBillUserIdStaffIdEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid, PageRequest.of(1, 20));
                    } else {
                        col = "bill_id";
                        return tBillBillRepository.findAllByBillUserIdBillFromToDateAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid, startdate, enddate, PageRequest.of(1, 20));
                    }
                }
            }
        } else {
            if (staffid == 0) {
                if (print.equals(true)) {
                    if (!search.equals("")) {
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, search, search, search, search, search);
                    } else if (today.equals(true)) {
                        java.sql.Date todaysdate;
                        LocalDate ltoday = LocalDate.now();
                        todaysdate = java.sql.Date.valueOf(ltoday);
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, todaysdate);
                    } else if (mstunitList[0] != 0) {
                        long[] temp = null;
//                        for(int i=0;i<mstunitList.length;i++){
//                            temp[i]=Long.parseLong(mstunitList[i]);
//                        }
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, mstunitList);
                    } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1);
                    } else {
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, startdate, enddate);
                    }
                } else {
                    if (!search.equals("")) {
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, search, search, search, search, search, PageRequest.of(1, 20));
                    } else if (today.equals(true)) {
                        java.sql.Date todaysdate;
                        LocalDate ltoday = LocalDate.now();
                        todaysdate = java.sql.Date.valueOf(ltoday);
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, todaysdate, PageRequest.of(1, 20));
                    } else if (mstunitList[0] != 0) {
                        long[] temp = null;
                        /*for(int i=0;i<mstunitList.length;i++){
                            temp[i]=Long.parseLong(mstunitList[i]);
                        }*/
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, mstunitList, PageRequest.of(1, 20));
                    } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, PageRequest.of(1, 20));
                    } else {
                        col = "bill_id";
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, startdate, enddate, PageRequest.of(1, 20));
                    }
                }
            } else {
                if (print.equals(true)) {
                    if (search.equals("") && (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) && today.equals(false) && staffid == 0)
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1);
                    if (!search.equals("")) {
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, staffid, search, search, search, search, search);
                    } else if (today.equals(true)) {
                        java.sql.Date todaysdate;
                        LocalDate ltoday = LocalDate.now();
                        todaysdate = java.sql.Date.valueOf(ltoday);
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, staffid, todaysdate);
                    } else if (mstunitList[0] != 0) {
                        long[] temp = null;
//                        for(int i=0;i<mstunitList.length;i++){
//                            temp[i]=Long.parseLong(mstunitList[i]);
//                        }
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, staffid, mstunitList);
                    } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, staffid);
                    } else {
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdBillFromToDateAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, staffid, startdate, enddate);
                    }
                } else {
                    if (search.equals("") && (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) && today.equals(false) && staffid == 0)
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, PageRequest.of(1, 20));
                    if (!search.equals("")) {
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdAndBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsOrBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, staffid, search, search, search, search, search, PageRequest.of(1, 20));
                    } else if (today.equals(true)) {
                        java.sql.Date todaysdate;
                        LocalDate ltoday = LocalDate.now();
                        todaysdate = java.sql.Date.valueOf(ltoday);
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndBillDateEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, staffid, todaysdate, PageRequest.of(1, 20));
                    } else if (mstunitList[0] != 0) {
                        long[] temp = null;
//                        for(int i=0;i<mstunitList.length;i++){
//                            temp[i]=Long.parseLong(mstunitList[i]);
//                        }
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndTbillUnitIdUnitIdInAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, staffid, mstunitList, PageRequest.of(1, 20));
                    } else if (enddate.equals("") || enddate.equals(null) || enddate == "" || enddate == null) {
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdStaffIdEqualsAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, staffid, PageRequest.of(1, 20));
                    } else {
                        col = "bill_id";
                        return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndBillUserIdBillFromToDateAndIpdBillFalseAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(staffid1, staffid, startdate, enddate, PageRequest.of(1, 20));
                    }
                }
            }
            /*if(print.equals(true))
                return tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(staffid1);
            else
                return  tBillBillRepository.findAllByBillVisitIdVisitStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(staffid1, PageRequest.of(1,20));
            }*/
        }
    }

    @RequestMapping("IPDbilllist/{mstunitList}")
    public List<TBillBill> IPDbilllist(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query, @PathVariable long[] mstunitList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject json = new JSONObject(search_query);
        int page = search_query.contains("page") ? json.getInt("page") : 1;
        int size = search_query.contains("size") ? json.getInt("size") : 100;
        String sort = search_query.contains("sort") ? json.getString("sort") : "DESC";
        String col = search_query.contains("col") ? json.getString("col") : "billId";
        String search = search_query.contains("search") ? json.getString("search") : "";
        Boolean today = search_query.contains("today") && json.getBoolean("today");
        Boolean print = search_query.contains("print") && json.getBoolean("print");
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
        long staffid = search_query.contains("staff") ? json.getLong("staff") : 0;
        long staffid1 = search_query.contains("staff1") ? json.getLong("staff1") : 0;
        long count = 0;
        List<TBillBill> listTBillBill = new ArrayList<TBillBill>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query2 = "select d from TBillBill d";
        String countQuery = "select count(d.billId) from TBillBill d";
        if (!search.equals("")) {
            query2 += " where (d.billAdmissionId.admissionPatientId.patientUserId.userFirstname like '%" + search + "%' or d.billAdmissionId.admissionPatientId.patientUserId.userLastname like '%" + search + "%') and d.ipdBill = 1";
            countQuery += " where (d.billAdmissionId.admissionPatientId.patientUserId.userFirstname like '%" + search + "%' or d.billAdmissionId.admissionPatientId.patientUserId.userLastname like '%" + search + "%') and d.ipdBill = 1";

        } else {
            query2 += " where (d.billAdmissionId.admissionPatientId.patientUserId.userFirstname like '%%' or d.billAdmissionId.admissionPatientId.patientUserId.userLastname like '%%') and d.ipdBill = 1";
            countQuery += " where (d.billAdmissionId.admissionPatientId.patientUserId.userFirstname like '%%' or d.billAdmissionId.admissionPatientId.patientUserId.userLastname like '%%') and d.ipdBill = 1";
        }
        if (startdate.equals("") || startdate.equals("null")) {
            startdate = "1990-06-07";
        }
        if (enddate.equals("") || enddate.equals("null")) {
            enddate = strDate;
        }
        if (!String.valueOf(staffid1).equals("0")) {
            query2 += " and d.billUserId.staffId=" + staffid1 + " ";
            countQuery += " and d.billUserId.staffId=" + staffid1 + " ";
        }
        if (mstunitList[0] != 0) {
            String values = String.valueOf(mstunitList[0]);
            for (int i = 1; i < mstunitList.length; i++) {
                values += "," + mstunitList[i];
            }
            query2 += " and d.tbillUnitId.unitId in (" + values + ") ";
            countQuery += " and d.tbillUnitId.unitId in (" + values + ") ";
        }
        if (!String.valueOf(staffid).equals("0")) {
            query2 += " and d.billAdmissionId.admissionStaffId.staffId=" + staffid + " ";
            countQuery += " and d.billAdmissionId.admissionStaffId.staffId=" + staffid + " ";
        }

       /* if (!String.valueOf(misadmissionSearchDTO.getUnitId()).equals("0")) {

            query2 += " and d.dischargeAdmissionId.admissionUnitId.unitId=" + misadmissionSearchDTO.getUnitId() + " ";
            countQuery += " and d.dischargeAdmissionId.admissionUnitId.unitId=" + misadmissionSearchDTO.getUnitId() + " ";
        }*/

       /* if (!misadmissionSearchDTO.getMrNo().equals("")) {
            query2 += " and d.dischargeAdmissionId.admissionPatientId.patientMrNo like  '%" + misadmissionSearchDTO.getMrNo() + "%' ";
            countQuery += " and d.dischargeAdmissionId.admissionPatientId.patientMrNo like '%" + misadmissionSearchDTO.getMrNo() + "%' ";
        }*/
        if (startdate.equals("")) {
            startdate = "1990-06-07";
        }
        if (enddate.equals("")) {
            enddate = strDate;
        }
        if (today) {
            query2 += " and date(d.billDate)='" + strDate + "' ";
            countQuery += " and date(d.billDate)='" + strDate + "' ";
        } else {
            query2 += " and (date(d.billDate) between '" + startdate + "' and '" + enddate + "') ";
            countQuery += " and (date(d.billDate) between '" + startdate + "' and '" + enddate + "') ";
        }
        try {
            //query2 += " limit " + ((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()) + "," + misadmissionSearchDTO.getLimit();
            System.out.println("Main Query" + query2);
            if (print) {
                listTBillBill = objEntityManager.createQuery(query2).getResultList();
            } else {
                listTBillBill = objEntityManager.createQuery(query2).setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
            }
            long cc = (long) objEntityManager.createQuery(countQuery).getSingleResult();
            count = cc;
            if (listTBillBill.size() > 0) {
                listTBillBill.get(0).setTotal((int) count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        return listTBillBill;
    }

    @GetMapping
    @RequestMapping("getUnitByStaffId")
    public List<MstUnit> getUnitByStaffId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "staffId") long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select * from mst_unit u,mst_staff_staff_unit su where u.is_active=1 and u.is_deleted=0 and u.unit_Id=su.staff_unit_unit_id and su.mst_staff_staff_id=" + staffId + " GROUP BY u.unit_id ";
        System.out.println("Patient Footfall Monthly Trend"+query);
        return objEntityManager.createNativeQuery(query, MstUnit.class).getResultList();

    }
//    @GetMapping
//    @RequestMapping("getDistinctTariffName")
//    public List<MstUnit> getDistinctTariffName() {
//
//        String query="select u.unit_id as UNITID,u.unit_name as UNITNAME,t.tariff_name as TARIFFNAME from mbill_tariff t,mst_unit u" +
//                "  where t.tariffunit_id = u.unit_id and t.is_deleted = 0 and t.is_active = 1";
//        return objEntityManager.createNativeQuery(query,MstUnit.class).getResultList();
//
//    }

}