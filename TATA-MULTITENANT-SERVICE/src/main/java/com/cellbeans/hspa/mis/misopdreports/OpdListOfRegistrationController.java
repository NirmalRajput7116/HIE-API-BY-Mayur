package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
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
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/mis_Opd-Registration-Report")
public class OpdListOfRegistrationController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CreateReport createReport;

    @Autowired
    MstUnitRepository mstUnitRepository;
    @Autowired
    MstUserRepository mstUserRepository;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    MstPatientRepository mstPatientRepository;

    // New Service Start
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getopdlistofregReport")
    public ResponseEntity searchBillRefund(@RequestHeader("X-tenantId") String tenantName,
                                           @RequestBody OpdListOfRegistrationSearchDTO opdListOfRegistrationSearchDTO) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        long tcount = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String CountQuery = "";
        String columnName = "";
        // Main Query
        String Query = " SELECT IFNULL(un.unit_name,'') AS unit_name, IFNULL(mp.created_date,'') AS created_date, IFNULL(mp.patient_mr_no,'') AS patient_mr_no, " +
                " IFNULL(CONCAT(IFNULL(CONCAT(mt.title_name,' '), ''),mu.user_firstname,' ',mu.user_lastname), '') AS FullName, IFNULL(mu.user_age,'') AS user_age, " +
                " IFNULL(mg.gender_name,'') AS gender_name, IFNULL(cz.cip_name,'') AS cip_name, IFNULL(mu.user_uid,'') AS user_uid, " +
                " IFNULL(mu.user_residence_phone,'') AS user_office_phone, IFNULL(mu.user_mobile,'') AS user_mobile, IFNULL(mu.user_email,'') AS user_email, " +
                " IFNULL(ms.ms_name,'') AS ms_name, IFNULL(bg.bloodgroup_name,'') AS bloodgroup_name, IFNULL(mu.user_dob,'') AS user_dob, " +
                " IFNULL(mu.user_address,'') AS user_address, IFNULL(c.city_name,'') AS city_name, IFNULL(s.state_name,'') AS state_name, IFNULL(co.country_name,'') AS country_name " +
                " FROM mst_visit mv " +
                " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                " LEFT JOIN mst_unit un ON mv.visit_unit_id = un.unit_id " +
                " LEFT JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                " LEFT JOIN mst_citizen_id_proof cz ON mu.user_citizen_id = cz.cip_id " +
                " LEFT JOIN mst_city c ON mu.user_city_id = c.city_id " +
                " LEFT JOIN mst_state s ON c.city_state_id = s.state_id " +
                " LEFT JOIN mst_country co ON s.state_country_id = co.country_id " +
                " LEFT JOIN mst_marital_status ms ON mu.user_ms_id = ms.ms_id " +
                " LEFT JOIN mst_bloodgroup bg ON mu.user_bloodgroup_id = bg.bloodgroup_id " +
                " WHERE mp.is_active = 1 AND mp.is_deleted = 0 ";
        CountQuery = "";
        if (opdListOfRegistrationSearchDTO.DORfromdate.equals("") || opdListOfRegistrationSearchDTO.DORfromdate.equals("null")) {
            opdListOfRegistrationSearchDTO.DORfromdate = "1990-06-07";
            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");
        }
        if (opdListOfRegistrationSearchDTO.DORtodate.equals("") || opdListOfRegistrationSearchDTO.DORtodate.equals("null")) {
            opdListOfRegistrationSearchDTO.DORtodate = strDate;
            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
        }
        if (opdListOfRegistrationSearchDTO.getTodaydate()) {
            Query += " and (date(mp.created_date)=curdate()) ";
            //   CountQuery += " where (date(v.created_date)=curdate()) ";
        } else {
            Query += " and (date(mp.created_date) between '" + opdListOfRegistrationSearchDTO.DORfromdate + "' and '" + opdListOfRegistrationSearchDTO.DORtodate + "')  ";
            CountQuery += " and (date(mp.created_date) between '" + opdListOfRegistrationSearchDTO.DORfromdate + "' and '" + opdListOfRegistrationSearchDTO.DORtodate + "')  ";
        }
        if (opdListOfRegistrationSearchDTO.unitList != null) {
            String values = opdListOfRegistrationSearchDTO.unitList[0];
            for (int i = 1; i < opdListOfRegistrationSearchDTO.unitList.length; i++) {
                values += "," + opdListOfRegistrationSearchDTO.unitList[i];
            }
            Query += " and un.unit_id in (" + values + ") ";
            CountQuery += " and un.unit_id in (" + values + ") ";

        }

        if (opdListOfRegistrationSearchDTO.mstgenderlist != null) {
            String values = opdListOfRegistrationSearchDTO.mstgenderlist[0];
            for (int i = 1; i < opdListOfRegistrationSearchDTO.mstgenderlist.length; i++) {
                values += "," + opdListOfRegistrationSearchDTO.mstgenderlist[i];
            }
            Query += " and mu.user_gender_id in (" + values + ") ";
            CountQuery += " and mu.user_gender_id in (" + values + ") ";
        }
        if (opdListOfRegistrationSearchDTO.mstmaritalstatuslist != null) {
            String values = opdListOfRegistrationSearchDTO.mstmaritalstatuslist[0];
            for (int i = 1; i < opdListOfRegistrationSearchDTO.mstmaritalstatuslist.length; i++) {
                values += "," + opdListOfRegistrationSearchDTO.mstmaritalstatuslist[i];
            }
            Query += " and mu.user_ms_id in (" + values + ") ";
            CountQuery += " and mu.user_ms_id in (" + values + ") ";
        }
        if (opdListOfRegistrationSearchDTO.MstBloodgrouplist != null) {
            String values = opdListOfRegistrationSearchDTO.MstBloodgrouplist[0];
            for (int i = 1; i < opdListOfRegistrationSearchDTO.MstBloodgrouplist.length; i++) {
                values += "," + opdListOfRegistrationSearchDTO.MstBloodgrouplist[i];
            }
            Query += " and mu.user_bloodgroup_id in (" + values + ") ";
            CountQuery += " and mu.user_bloodgroup_id in (" + values + ") ";
        }
        if (opdListOfRegistrationSearchDTO.Mstagerangelist  != null) {
            Query += " and";
            CountQuery += " and";
            for (int i = 0; i < opdListOfRegistrationSearchDTO.Mstagerangelist.length; i++) {
                String[] splitvalue = String.valueOf(opdListOfRegistrationSearchDTO.Mstagerangelist[i]).split("-");
                if (((opdListOfRegistrationSearchDTO.Mstagerangelist.length - 1) == (i))) {
                    Query += "(mu.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ")";
                    CountQuery += "(mu.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ")";
                } else {
                    Query += "(mu.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ") or";
                    CountQuery += "(mu.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ") or";
                }
            }
        }
        if (opdListOfRegistrationSearchDTO.DOBfromdate  != null && opdListOfRegistrationSearchDTO.DOBtodate  != null && opdListOfRegistrationSearchDTO.DOBfromdate  != null && opdListOfRegistrationSearchDTO.DOBtodate  != null) {
            Query += " and (date(mu.user_dob) between '" + opdListOfRegistrationSearchDTO.DOBfromdate + "' and '" + opdListOfRegistrationSearchDTO.DOBtodate + "') ";
        }
        Query += " GROUP BY mv.visit_patient_id ";
        System.out.println("List Of Registration Report"+Query);
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,created_date,patient_mr_no,FullName,user_age,gender_name,cip_name,user_uid,user_office_phone,user_mobile,user_email,ms_name,bloodgroup_name,user_dob,user_address,city_name,state_name,country_name ";
        Query += " limit " + ((opdListOfRegistrationSearchDTO.getOffset() - 1) * opdListOfRegistrationSearchDTO.getLimit()) + "," + opdListOfRegistrationSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }
    // New Service End

    // New Service Start
    // @Produces(MediaType.APPLICATION_JSON)


        @RequestMapping("getopdlistofregReportPrint")
        public String searsearchBillRefundPrintchBillRefund(@RequestHeader("X-tenantId") String tenantName,
                                                            @RequestBody OpdListOfRegistrationSearchDTO opdListOfRegistrationSearchDTO) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        long tcount = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String CountQuery = "";
        String columnName = "";
        // Main Query
        String Query = " SELECT IFNULL(un.unit_name,'') AS unit_name, IFNULL(mp.created_date,'') AS created_date, IFNULL(mp.patient_mr_no,'') AS patient_mr_no, " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname), '') AS FullName, IFNULL(mu.user_age,'') AS user_age, " +
                " IFNULL(mg.gender_name,'') AS gender_name, IFNULL(cz.cip_name,'') AS cip_name, IFNULL(mu.user_uid,'') AS user_uid, " +
                " IFNULL(mu.user_residence_phone,'') AS user_office_phone, IFNULL(mu.user_mobile,'') AS user_mobile, IFNULL(mu.user_email,'') AS user_email, " +
                " IFNULL(ms.ms_name,'') AS ms_name, IFNULL(bg.bloodgroup_name,'') AS bloodgroup_name, IFNULL(mu.user_dob,'') AS user_dob, " +
                " IFNULL(mu.user_address,'') AS user_address, IFNULL(c.city_name,'') AS city_name, IFNULL(s.state_name,'') AS state_name, IFNULL(co.country_name,'') AS country_name " +
                " FROM mst_visit mv " +
                " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                " LEFT JOIN mst_unit un ON mv.visit_unit_id = un.unit_id " +
                " LEFT JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                " LEFT JOIN mst_citizen_id_proof cz ON mu.user_citizen_id = cz.cip_id " +
                " LEFT JOIN mst_city c ON mu.user_city_id = c.city_id " +
                " LEFT JOIN mst_state s ON c.city_state_id = s.state_id " +
                " LEFT JOIN mst_country co ON s.state_country_id = co.country_id " +
                " LEFT JOIN mst_marital_status ms ON mu.user_ms_id = ms.ms_id " +
                " LEFT JOIN mst_bloodgroup bg ON mu.user_bloodgroup_id = bg.bloodgroup_id " +
                " WHERE mp.is_active = 1 AND mp.is_deleted = 0 ";
        CountQuery = "";
        if (opdListOfRegistrationSearchDTO.DORfromdate.equals("") || opdListOfRegistrationSearchDTO.DORfromdate.equals("null")) {
            opdListOfRegistrationSearchDTO.DORfromdate = "1990-06-07";
            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");
        }
        if (opdListOfRegistrationSearchDTO.DORtodate.equals("") || opdListOfRegistrationSearchDTO.DORtodate.equals("null")) {
            opdListOfRegistrationSearchDTO.DORtodate = strDate;
            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
        }
        if (opdListOfRegistrationSearchDTO.getTodaydate()) {
            Query += " and (date(mp.created_date)=curdate()) ";
            //   CountQuery += " where (date(v.created_date)=curdate()) ";
        } else {
            Query += " and (date(mp.created_date) between '" + opdListOfRegistrationSearchDTO.DORfromdate + "' and '" + opdListOfRegistrationSearchDTO.DORtodate + "')  ";
            CountQuery += " and (date(mp.created_date) between '" + opdListOfRegistrationSearchDTO.DORfromdate + "' and '" + opdListOfRegistrationSearchDTO.DORtodate + "')  ";
        }
        if (opdListOfRegistrationSearchDTO.unitList != null) {
            String values = opdListOfRegistrationSearchDTO.unitList[0];
            for (int i = 1; i < opdListOfRegistrationSearchDTO.unitList.length; i++) {
                values += "," + opdListOfRegistrationSearchDTO.unitList[i];
            }
            Query += " and un.unit_id in (" + values + ") ";
            CountQuery += " and un.unit_id in (" + values + ") ";

        }
        if (opdListOfRegistrationSearchDTO.mstgenderlist != null) {
            String values = opdListOfRegistrationSearchDTO.mstgenderlist[0];
            for (int i = 1; i < opdListOfRegistrationSearchDTO.mstgenderlist.length; i++) {
                values += "," + opdListOfRegistrationSearchDTO.mstgenderlist[i];
            }
            Query += " and mu.user_gender_id in (" + values + ") ";
            CountQuery += " and mu.user_gender_id in (" + values + ") ";
        }
        if (opdListOfRegistrationSearchDTO.mstmaritalstatuslist != null) {
            String values = opdListOfRegistrationSearchDTO.mstmaritalstatuslist[0];
            for (int i = 1; i < opdListOfRegistrationSearchDTO.mstmaritalstatuslist.length; i++) {
                values += "," + opdListOfRegistrationSearchDTO.mstmaritalstatuslist[i];
            }
            Query += " and mu.user_ms_id in (" + values + ") ";
            CountQuery += " and mu.user_ms_id in (" + values + ") ";
        }
        if (opdListOfRegistrationSearchDTO.MstBloodgrouplist != null) {
            String values = opdListOfRegistrationSearchDTO.MstBloodgrouplist[0];
            for (int i = 1; i < opdListOfRegistrationSearchDTO.MstBloodgrouplist.length; i++) {
                values += "," + opdListOfRegistrationSearchDTO.MstBloodgrouplist[i];
            }
            Query += " and mu.user_bloodgroup_id in (" + values + ") ";
            CountQuery += " and mu.user_bloodgroup_id in (" + values + ") ";
        }
        if (opdListOfRegistrationSearchDTO.Mstagerangelist  != null) {
            Query += " and";
            CountQuery += " and";
            for (int i = 0; i < opdListOfRegistrationSearchDTO.Mstagerangelist.length; i++) {
                String[] splitvalue = String.valueOf(opdListOfRegistrationSearchDTO.Mstagerangelist[i]).split("-");
                if (((opdListOfRegistrationSearchDTO.Mstagerangelist.length - 1) == (i))) {
                    Query += "(mu.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ")";
                    CountQuery += "(mu.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ")";
                } else {
                    Query += "(mu.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ") or";
                    CountQuery += "(mu.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ") or";
                }
            }
        }
        if (opdListOfRegistrationSearchDTO.DOBfromdate  != null && opdListOfRegistrationSearchDTO.DOBtodate  != null&& opdListOfRegistrationSearchDTO.DOBfromdate  != null&& opdListOfRegistrationSearchDTO.DOBtodate  != null) {
            Query += " and (date(mu.user_dob) between '" + opdListOfRegistrationSearchDTO.DOBfromdate + "' and '" + opdListOfRegistrationSearchDTO.DOBtodate + "') ";
        }
        Query += " GROUP BY mv.visit_patient_id ";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,created_date,patient_mr_no,FullName,user_age,gender_name,cip_name,user_uid,user_office_phone,user_mobile,user_email,ms_name,bloodgroup_name,user_dob,user_address,city_name,state_name,country_name ";
        //Query += " limit " + ((opdListOfRegistrationSearchDTO.getOffset() - 1) * opdListOfRegistrationSearchDTO.getLimit()) + "," + opdListOfRegistrationSearchDTO.getLimit();
        //  return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        System.out.print(Query);



        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(opdListOfRegistrationSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(opdListOfRegistrationSearchDTO.getUserId());
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            JSONObject jsonObjectUser = null;
            try {
                jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        if (opdListOfRegistrationSearchDTO.getPrint()) {
            columnName = "unit_name,created_date,patient_mr_no,FullName,user_age,gender_name,cip_name,user_uid,user_office_phone,user_mobile,user_email,ms_name,bloodgroup_name,user_dob,user_address,city_name,state_name,country_name ";
            return createReport.generateExcel(columnName, "ListOfRegistrationReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("ListOfRegistrationReport", jsonArray.toString());
        }
    }
    // New Service End

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getopdlistofregDashbord/{fromdate}/{todate}/{offset}/{limit}")
    public ResponseEntity opdlistofregDashbord(@RequestHeader("X-tenantId") String tenantName, @PathVariable String fromdate, @PathVariable String todate, @PathVariable("offset") Integer offset, @PathVariable("limit") Integer limit) {
        TenantContext.setCurrentTenant(tenantName);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String CountQuery = "";
        String columnName = "";
        // Main Query
        String Query = " SELECT IFNULL(un.unit_name,'') AS unit_name,IFNULL(mp.created_date,'') AS created_date,IFNULL(mp.patient_mr_no,'') AS patient_mr_no, " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname), '') AS FullName, " +
                " IFNULL(mu.user_age,'') AS user_age,IFNULL(mg.gender_name,'') AS gender_name,IFNULL(cz.cip_name,'') AS cip_name,IFNULL(mu.user_uid,'') AS user_uid, " +
                " IFNULL(mu.user_office_phone,'') AS user_office_phone,IFNULL(mu.user_mobile,'') AS user_mobile,IFNULL(mu.user_email,'') AS user_email, " +
                " IFNULL(ms.ms_name,'') AS ms_name,IFNULL(bg.bloodgroup_name,'') AS bloodgroup_name,IFNULL(mu.user_dob,'') AS user_dob,IFNULL(mu.user_address,'') AS user_address, " +
                " IFNULL(c.city_name,'') AS city_name,IFNULL(s.state_name,'') AS state_name,IFNULL(co.country_name,'') AS country_name " +
                " FROM mst_visit mv " +
                " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                " LEFT JOIN mst_unit un ON mv.visit_unit_id = un.unit_id " +
                " LEFT JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                " LEFT JOIN mst_citizen_id_proof cz ON mu.user_citizen_id = cz.cip_id " +
                " LEFT JOIN mst_city c ON mu.user_city_id = c.city_id " +
                " LEFT JOIN mst_state s ON c.city_state_id = s.state_id " +
                " LEFT JOIN mst_country co ON s.state_country_id = co.country_id " +
                " LEFT JOIN mst_marital_status ms ON mu.user_ms_id = ms.ms_id  " +
                " LEFT JOIN mst_bloodgroup bg ON mu.user_bloodgroup_id = bg.bloodgroup_id " +
                " WHERE mp.is_active = 1 AND mp.is_deleted = 0 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = ("1990-06-07");

        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        Query += " and (date(mp.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        Query += " GROUP BY mv.visit_patient_id ";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,created_date,patient_mr_no,FullName,user_age,gender_name,cip_name,user_uid,user_office_phone,user_mobile,user_email,ms_name,bloodgroup_name,user_dob,user_address,city_name,state_name,country_name ";
        Query += " limit " + ((offset - 1) * limit) + "," + limit;
        System.out.println("Query" + Query);
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    /*@RequestMapping("getopdlistofregReport/{unitList}/{mstgenderlist}/{MstBloodgrouplist}/{mstmaritalstatuslist}/{Mstagerangelist}/{DORfromdate}/{DORtodate}/{DOBfromdate}/{DOBtodate}/{limit}/{offset}")
    public List<OpdListOfRegistrationListDTO> searchBillRefund(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdListOfRegistrationSearchDTO opdListOfRegistrationSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstgenderlist, @PathVariable String[] MstBloodgrouplist, @PathVariable String[] mstmaritalstatuslist, @PathVariable String[] Mstagerangelist, @PathVariable String DORfromdate, @PathVariable String DORtodate, @PathVariable String DOBfromdate, @PathVariable String DOBtodate, @PathVariable("limit") String limit, @PathVariable("offset") String offset) {
        long count = 0;


        List<OpdListOfRegistrationListDTO> opdListOfregistrationlistDTOList = new ArrayList<OpdListOfRegistrationListDTO>();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);

        String query = " SELECT patient_details.RegistrationDate, IFNULL(vistDeatils.unit_name,'') AS unit_name,patient_details.patient_mr_no, " +
                " patient_details.Userfullname,patient_details.user_dob,patient_details.user_age,patient_details.gender_name, " +
                " patient_details.nationality_name, " +
                " patient_details.user_uid,patient_details.user_office_phone, " +
                " patient_details.user_mobile,patient_details.user_email, patient_details.maritalstatus, " +
                " patient_details.bloodgroup_name " +
                "FROM " +
                "(" +
                " SELECT v.patient_id,(v.created_date) AS RegistrationDate, IFNULL(un.unit_name,'') AS unit_name,v.patient_mr_no, CONCAT(u.user_firstname,' ',u.user_lastname) AS Userfullname,u.user_dob,u.user_age,g.gender_name, " +
                " nl.nationality_name, IFNULL(u.user_uid,'') AS user_uid, IFNULL(u.user_office_phone,'') AS user_office_phone, IFNULL(u.user_mobile,'') AS user_mobile, IFNULL(u.user_email,'') AS user_email, IFNULL(s.ms_name,'') AS maritalstatus, IFNULL(b.bloodgroup_name,'') AS bloodgroup_name " +
                " FROM mst_patient v " +
                " LEFT JOIN mst_user u ON v.patient_user_id=u.user_id " +
                " LEFT JOIN mst_bloodgroup b ON u.user_bloodgroup_id=bloodgroup_id " +
                " LEFT JOIN mst_gender g ON u.user_gender_id=g.gender_id " +
                " LEFT JOIN mst_marital_status s ON u.user_ms_id=s.ms_id " +
                " LEFT JOIN mst_unit un ON u.user_unit_id=un.unit_id " +
                " LEFT JOIN mst_nationality nl ON u.user_nationality_id=nl.nationality_id ";

        String unitCheck = "SELECT DISTINCT(p.visit_patient_id) AS pid,p.visit_unit_id,ui.unit_name FROM mst_visit p  INNER JOIN  mst_unit ui ON p.visit_unit_id=ui.unit_id";

                *//*" SELECT date(p.created_date) as visitDate,ifnull(un.unit_name,'') as unit_name,p.patient_mr_no, " +
                " concat(u.user_firstname,' ',u.user_lastname) as Userfullname,u.user_dob,u.user_age,g.gender_name, " +
                " ifnull(u.user_uid,'')as user_uid,ifnull(u.user_office_phone,'')as user_office_phone, " +
                " ifnull(u.user_mobile,'')as user_mobile,ifnull(u.user_email,'')as user_email, " +
                " ifnull(s.ms_name,'')as maritalstatus,ifnull(b.bloodgroup_name,'')as bloodgroup_name,ifnull(un.unit_id,0) as unit_id ,nl.nationality_name " +
                " FROM mst_visit v " +
                " left join mst_patient p on v.visit_patient_id=p.patient_id " +
                " left join mst_user u on p.patient_user_id=u.user_id " +
                " left join mst_bloodgroup b on u.user_bloodgroup_id=bloodgroup_id " +
                " left join mst_gender g on u.user_gender_id=g.gender_id " +
                " left join mst_marital_status s on u.user_ms_id=s.ms_id " +
                " left join mst_unit un on v.visit_unit_id=un.unit_id  " +
                " left join mst_nationality nl on u.user_nationality_id=nl.nationality_id ";*//*

        //String query = " SELECT date(v.created_date) as visitDate,ifnull(un.unit_name,'') as unit_name,p.patient_mr_no, " + " concat(u.user_firstname,' ',u.user_lastname) as Userfullname,u.user_dob,u.user_age,g.gender_name, " + " ifnull(u.user_uid,'')as user_uid,ifnull(u.user_office_phone,'')as user_office_phone, " + " ifnull(u.user_mobile,'')as user_mobile,ifnull(u.user_email,'')as user_email, " + " ifnull(s.ms_name,'')as maritalstatus,ifnull(b.bloodgroup_name,'')as bloodgroup_name,ifnull(un.unit_id,0) as unit_id ,nl.nationality_name " + " FROM mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_bloodgroup b on u.user_bloodgroup_id=bloodgroup_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_marital_status s on u.user_ms_id=s.ms_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id  " + " left join mst_nationality nl on u.user_nationality_id=nl.nationality_id ";

        String Countquery = " select count(*) FROM " +
                "(" +
                " SELECT v.patient_id,(v.created_date) AS RegistrationDate, IFNULL(un.unit_name,'') AS unit_name,v.patient_mr_no, CONCAT(u.user_firstname,' ',u.user_lastname) AS Userfullname,u.user_dob,u.user_age,g.gender_name, " +
                " nl.nationality_name, IFNULL(u.user_uid,'') AS user_uid, IFNULL(u.user_office_phone,'') AS user_office_phone, IFNULL(u.user_mobile,'') AS user_mobile, IFNULL(u.user_email,'') AS user_email, IFNULL(s.ms_name,'') AS maritalstatus, IFNULL(b.bloodgroup_name,'') AS bloodgroup_name " +
                " FROM mst_patient v " +
                " LEFT JOIN mst_user u ON v.patient_user_id=u.user_id " +
                " LEFT JOIN mst_bloodgroup b ON u.user_bloodgroup_id=bloodgroup_id " +
                " LEFT JOIN mst_gender g ON u.user_gender_id=g.gender_id " +
                " LEFT JOIN mst_marital_status s ON u.user_ms_id=s.ms_id " +
                " LEFT JOIN mst_unit un ON u.user_unit_id=un.unit_id " +
                " LEFT JOIN mst_nationality nl ON u.user_nationality_id=nl.nationality_id ";
        if (DORfromdate.equals("") || DORfromdate.equals("null")) {
            DORfromdate = "1990-06-07";
            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");

        }
        if (DORtodate.equals("") || DORtodate.equals("null")) {
            DORtodate = strDate;
            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
        }
        if (opdListOfRegistrationSearchDTO.getTodaydate()) {
            query += " where (date(v.created_date)=curdate()) ";
            Countquery += " where (date(v.created_date)=curdate()) ";
        } else {
            query += " where (date(v.created_date) between '" + DORfromdate + "' and '" + DORtodate + "')  ";
            Countquery += " where (date(v.created_date) between '" + DORfromdate + "' and '" + DORtodate + "')  ";
        }


        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];

            }
            unitCheck += " where  p.visit_unit_id in (" + values + ") ";
//            Countquery += " and v.visit_unit_id in (" + values + ") ";
        }

        if (!mstgenderlist[0].equals("null")) {
            String values = String.valueOf(mstgenderlist[0]);
            for (int i = 1; i < mstgenderlist.length; i++) {
                values += "," + mstgenderlist[i];
            }
            query += " and u.user_gender_id in (" + values + ") ";
            Countquery += " and u.user_gender_id in (" + values + ") ";
        }

        if (!mstmaritalstatuslist[0].equals("null")) {
            String values = String.valueOf(mstmaritalstatuslist[0]);
            for (int i = 1; i < mstmaritalstatuslist.length; i++) {
                values += "," + mstmaritalstatuslist[i];
            }
            query += " and u.user_ms_id in (" + values + ") ";
            Countquery += " and u.user_ms_id in (" + values + ") ";
        }


        if (!MstBloodgrouplist[0].equals("null")) {
            String values = String.valueOf(MstBloodgrouplist[0]);
            for (int i = 1; i < MstBloodgrouplist.length; i++) {
                values += "," + MstBloodgrouplist[i];
            }
            query += " and  u.user_bloodgroup_id in (" + values + ") ";
            Countquery += " and  u.user_bloodgroup_id in (" + values + ") ";
        }


        if (!Mstagerangelist[0].equals("null")) {
            query += " and";
            Countquery += " and";
            for (int i = 0; i < Mstagerangelist.length; i++) {
                String splitvalue[] = String.valueOf(Mstagerangelist[i]).split("-");
                if(((Mstagerangelist.length-1 )== (i)) ){
                    query += "(u.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ")";
                    Countquery += "(u.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ")";
                }else{
                    query += "(u.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ") or";
                    Countquery += "(u.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ") or";
                }
            }
        }

        if (!DOBfromdate.equals("null") && !DOBtodate.equals("null") && !DOBfromdate.equals("") && !DOBtodate.equals("")) {
            query += " and (date(u.user_dob) between '" + DOBfromdate + "' and '" + DOBtodate + "') ";
        }

        try {
//            query +=

            query += " ) AS patient_details, (" + unitCheck + ") AS vistDeatils " +
                    " WHERE patient_details.patient_id=vistDeatils.pid " + " ORDER BY patient_details.RegistrationDate DESC limit " + ((Integer.parseInt(offset) - 1) * Integer.parseInt(limit)) + "," + limit;

            Countquery += " ) AS patient_details, (" + unitCheck + ") AS vistDeatils " +
                    " WHERE patient_details.patient_id=vistDeatils.pid ";

            //    query += " limit " + ((Integer.parseInt(opdListOfRegistrationSearchDTO.getOffset()) - 1) * Integer.parseInt(opdListOfRegistrationSearchDTO.getLimit())) + "," + opdListOfRegistrationSearchDTO.getLimit();

            System.out.println("Mainquery" + query);

            List<Object[]> listOpdListOfRegistration = entityManager.createNativeQuery(query).getResultList();

            BigInteger cc = (BigInteger) entityManager.createNativeQuery(Countquery).getSingleResult();
            count = cc.longValue();

            for (Object[] obj : listOpdListOfRegistration) {
                OpdListOfRegistrationListDTO objopdListOfregistrationlistDTO = new OpdListOfRegistrationListDTO();
                objopdListOfregistrationlistDTO.setDateofRegistration("" + obj[0]);
                objopdListOfregistrationlistDTO.setUnitName("" + obj[1]);
                objopdListOfregistrationlistDTO.setMrNO("" + obj[2]);
                objopdListOfregistrationlistDTO.setPatientName("" + obj[3]);
                objopdListOfregistrationlistDTO.setdOB("" + obj[4]);
                objopdListOfregistrationlistDTO.setAge("" + obj[5]);
                objopdListOfregistrationlistDTO.setGender("" + obj[6]);
                objopdListOfregistrationlistDTO.setIdProofDocNo("" + obj[7]);
                objopdListOfregistrationlistDTO.setPhoneNo("" + obj[8]);
                objopdListOfregistrationlistDTO.setMobNo("" + obj[10]);
                objopdListOfregistrationlistDTO.setEmail("" + obj[11]);
                objopdListOfregistrationlistDTO.setMaritalStatus("" + obj[12]);
                objopdListOfregistrationlistDTO.setBloodGroup("" + obj[13]);
//                objopdListOfregistrationlistDTO.setUnitId(Long.parseLong("" + obj[13]));
                objopdListOfregistrationlistDTO.setNationalityName("" + obj[07]);
                objopdListOfregistrationlistDTO.setCount(count);
                opdListOfregistrationlistDTOList.add(objopdListOfregistrationlistDTO);
            }

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        return opdListOfregistrationlistDTOList;
    }


    @RequestMapping("getopdlistofregReportPrint/{unitList}/{mstgenderlist}/{MstBloodgrouplist}/{mstmaritalstatuslist}/{Mstagerangelist}/{DORfromdate}/{DORtodate}/{DOBfromdate}/{DOBtodate}/{limit}/{offset}")
    public String  searchBillRefundPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdListOfRegistrationSearchDTO opdListOfRegistrationSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstgenderlist, @PathVariable String[] MstBloodgrouplist, @PathVariable String[] mstmaritalstatuslist, @PathVariable String[] Mstagerangelist, @PathVariable String DORfromdate, @PathVariable String DORtodate, @PathVariable String DOBfromdate, @PathVariable String DOBtodate, @PathVariable("limit") String limit, @PathVariable("offset") String offset) {


        long count = 0;


        List<OpdListOfRegistrationListDTO> opdListOfregistrationlistDTOList = new ArrayList<OpdListOfRegistrationListDTO>();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);

        String query = " SELECT patient_details.RegistrationDate, IFNULL(vistDeatils.unit_name,'') AS unit_name,patient_details.patient_mr_no, " +
                " patient_details.Userfullname,patient_details.user_dob,patient_details.user_age,patient_details.gender_name, " +
                " patient_details.nationality_name, " +
                " patient_details.user_uid,patient_details.user_office_phone, " +
                " patient_details.user_mobile,patient_details.user_email, patient_details.maritalstatus, " +
                " patient_details.bloodgroup_name " +
                "FROM " +
                "(" +
                " SELECT v.patient_id,(v.created_date) AS RegistrationDate, IFNULL(un.unit_name,'') AS unit_name,v.patient_mr_no, CONCAT(u.user_firstname,' ',u.user_lastname) AS Userfullname,u.user_dob,u.user_age,g.gender_name, " +
                " nl.nationality_name, IFNULL(u.user_uid,'') AS user_uid, IFNULL(u.user_office_phone,'') AS user_office_phone, IFNULL(u.user_mobile,'') AS user_mobile, IFNULL(u.user_email,'') AS user_email, IFNULL(s.ms_name,'') AS maritalstatus, IFNULL(b.bloodgroup_name,'') AS bloodgroup_name " +
                " FROM mst_patient v " +
                " LEFT JOIN mst_user u ON v.patient_user_id=u.user_id " +
                " LEFT JOIN mst_bloodgroup b ON u.user_bloodgroup_id=bloodgroup_id " +
                " LEFT JOIN mst_gender g ON u.user_gender_id=g.gender_id " +
                " LEFT JOIN mst_marital_status s ON u.user_ms_id=s.ms_id " +
                " LEFT JOIN mst_unit un ON u.user_unit_id=un.unit_id " +
                " LEFT JOIN mst_nationality nl ON u.user_nationality_id=nl.nationality_id ";

        String unitCheck = "SELECT DISTINCT(p.visit_patient_id) AS pid,p.visit_unit_id,ui.unit_name FROM mst_visit p  INNER JOIN  mst_unit ui ON p.visit_unit_id=ui.unit_id";

                *//*" SELECT date(p.created_date) as visitDate,ifnull(un.unit_name,'') as unit_name,p.patient_mr_no, " +
                " concat(u.user_firstname,' ',u.user_lastname) as Userfullname,u.user_dob,u.user_age,g.gender_name, " +
                " ifnull(u.user_uid,'')as user_uid,ifnull(u.user_office_phone,'')as user_office_phone, " +
                " ifnull(u.user_mobile,'')as user_mobile,ifnull(u.user_email,'')as user_email, " +
                " ifnull(s.ms_name,'')as maritalstatus,ifnull(b.bloodgroup_name,'')as bloodgroup_name,ifnull(un.unit_id,0) as unit_id ,nl.nationality_name " +
                " FROM mst_visit v " +
                " left join mst_patient p on v.visit_patient_id=p.patient_id " +
                " left join mst_user u on p.patient_user_id=u.user_id " +
                " left join mst_bloodgroup b on u.user_bloodgroup_id=bloodgroup_id " +
                " left join mst_gender g on u.user_gender_id=g.gender_id " +
                " left join mst_marital_status s on u.user_ms_id=s.ms_id " +
                " left join mst_unit un on v.visit_unit_id=un.unit_id  " +
                " left join mst_nationality nl on u.user_nationality_id=nl.nationality_id ";*//*

        //String query = " SELECT date(v.created_date) as visitDate,ifnull(un.unit_name,'') as unit_name,p.patient_mr_no, " + " concat(u.user_firstname,' ',u.user_lastname) as Userfullname,u.user_dob,u.user_age,g.gender_name, " + " ifnull(u.user_uid,'')as user_uid,ifnull(u.user_office_phone,'')as user_office_phone, " + " ifnull(u.user_mobile,'')as user_mobile,ifnull(u.user_email,'')as user_email, " + " ifnull(s.ms_name,'')as maritalstatus,ifnull(b.bloodgroup_name,'')as bloodgroup_name,ifnull(un.unit_id,0) as unit_id ,nl.nationality_name " + " FROM mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_bloodgroup b on u.user_bloodgroup_id=bloodgroup_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_marital_status s on u.user_ms_id=s.ms_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id  " + " left join mst_nationality nl on u.user_nationality_id=nl.nationality_id ";

        String Countquery = " select count(*) FROM " +
                "(" +
                " SELECT v.patient_id,(v.created_date) AS RegistrationDate, IFNULL(un.unit_name,'') AS unit_name,v.patient_mr_no, CONCAT(u.user_firstname,' ',u.user_lastname) AS Userfullname,u.user_dob,u.user_age,g.gender_name, " +
                " nl.nationality_name, IFNULL(u.user_uid,'') AS user_uid, IFNULL(u.user_office_phone,'') AS user_office_phone, IFNULL(u.user_mobile,'') AS user_mobile, IFNULL(u.user_email,'') AS user_email, IFNULL(s.ms_name,'') AS maritalstatus, IFNULL(b.bloodgroup_name,'') AS bloodgroup_name " +
                " FROM mst_patient v " +
                " LEFT JOIN mst_user u ON v.patient_user_id=u.user_id " +
                " LEFT JOIN mst_bloodgroup b ON u.user_bloodgroup_id=bloodgroup_id " +
                " LEFT JOIN mst_gender g ON u.user_gender_id=g.gender_id " +
                " LEFT JOIN mst_marital_status s ON u.user_ms_id=s.ms_id " +
                " LEFT JOIN mst_unit un ON u.user_unit_id=un.unit_id " +
                " LEFT JOIN mst_nationality nl ON u.user_nationality_id=nl.nationality_id ";
        if (DORfromdate.equals("") || DORfromdate.equals("null")) {
            DORfromdate = "1990-06-07";
            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");

        }
        if (DORtodate.equals("") || DORtodate.equals("null")) {
            DORtodate = strDate;
            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
        }
        if (opdListOfRegistrationSearchDTO.getTodaydate()) {
            query += " where (date(v.created_date)=curdate()) ";
            Countquery += " where (date(v.created_date)=curdate()) ";
        } else {
            query += " where (date(v.created_date) between '" + DORfromdate + "' and '" + DORtodate + "')  ";
            Countquery += " where (date(v.created_date) between '" + DORfromdate + "' and '" + DORtodate + "')  ";
        }


        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];

            }
            unitCheck += " where  p.visit_unit_id in (" + values + ") ";
//            Countquery += " and v.visit_unit_id in (" + values + ") ";
        }

        if (!mstgenderlist[0].equals("null")) {
            String values = String.valueOf(mstgenderlist[0]);
            for (int i = 1; i < mstgenderlist.length; i++) {
                values += "," + mstgenderlist[i];
            }
            query += " and u.user_gender_id in (" + values + ") ";
            Countquery += " and u.user_gender_id in (" + values + ") ";
        }

        if (!mstmaritalstatuslist[0].equals("null")) {
            String values = String.valueOf(mstmaritalstatuslist[0]);
            for (int i = 1; i < mstmaritalstatuslist.length; i++) {
                values += "," + mstmaritalstatuslist[i];
            }
            query += " and u.user_ms_id in (" + values + ") ";
            Countquery += " and u.user_ms_id in (" + values + ") ";
        }


        if (!MstBloodgrouplist[0].equals("null")) {
            String values = String.valueOf(MstBloodgrouplist[0]);
            for (int i = 1; i < MstBloodgrouplist.length; i++) {
                values += "," + MstBloodgrouplist[i];
            }
            query += " and  u.user_bloodgroup_id in (" + values + ") ";
            Countquery += " and  u.user_bloodgroup_id in (" + values + ") ";
        }


        if (!Mstagerangelist[0].equals("null")) {
            query += " and";
            Countquery += " and";
            for (int i = 0; i < Mstagerangelist.length; i++) {
                String splitvalue[] = String.valueOf(Mstagerangelist[i]).split("-");
                if(((Mstagerangelist.length-1 )== (i)) ){
                    query += "(u.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ")";
                    Countquery += "(u.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ")";
                }else{
                    query += "(u.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ") or";
                    Countquery += "(u.user_age between " + splitvalue[0] + " and  " + splitvalue[1] + ") or";
                }
            }
        }

        if (!DOBfromdate.equals("null") && !DOBtodate.equals("null") && !DOBfromdate.equals("") && !DOBtodate.equals("")) {
            query += " and (date(u.user_dob) between '" + DOBfromdate + "' and '" + DOBtodate + "') ";
        }

        try {
//            query +=

            query += " ) AS patient_details, (" + unitCheck + ") AS vistDeatils " +
                    " WHERE patient_details.patient_id=vistDeatils.pid " + " ORDER BY patient_details.RegistrationDate DESC ";

            Countquery += " ) AS patient_details, (" + unitCheck + ") AS vistDeatils " +
                    " WHERE patient_details.patient_id=vistDeatils.pid ";

            //    query += " limit " + ((Integer.parseInt(opdListOfRegistrationSearchDTO.getOffset()) - 1) * Integer.parseInt(opdListOfRegistrationSearchDTO.getLimit())) + "," + opdListOfRegistrationSearchDTO.getLimit();

            System.out.println("Mainquery" + query);

            List<Object[]> listOpdListOfRegistration = entityManager.createNativeQuery(query).getResultList();

            BigInteger cc = (BigInteger) entityManager.createNativeQuery(Countquery).getSingleResult();
            count = cc.longValue();

            for (Object[] obj : listOpdListOfRegistration) {
                OpdListOfRegistrationListDTO objopdListOfregistrationlistDTO = new OpdListOfRegistrationListDTO();
                objopdListOfregistrationlistDTO.setDateofRegistration("" + obj[0]);
                objopdListOfregistrationlistDTO.setUnitName("" + obj[1]);
                objopdListOfregistrationlistDTO.setMrNO("" + obj[2]);
                objopdListOfregistrationlistDTO.setPatientName("" + obj[3]);
                objopdListOfregistrationlistDTO.setdOB("" + obj[4]);
                objopdListOfregistrationlistDTO.setAge("" + obj[5]);
                objopdListOfregistrationlistDTO.setGender("" + obj[6]);
                objopdListOfregistrationlistDTO.setIdProofDocNo("" + obj[7]);
                objopdListOfregistrationlistDTO.setPhoneNo("" + obj[8]);
                objopdListOfregistrationlistDTO.setMobNo("" + obj[10]);
                objopdListOfregistrationlistDTO.setEmail("" + obj[11]);
                objopdListOfregistrationlistDTO.setMaritalStatus("" + obj[12]);
                objopdListOfregistrationlistDTO.setBloodGroup("" + obj[13]);
//                objopdListOfregistrationlistDTO.setUnitId(Long.parseLong("" + obj[13]));
                objopdListOfregistrationlistDTO.setNationalityName("" + obj[07]);
                objopdListOfregistrationlistDTO.setCount(count);
                opdListOfregistrationlistDTOList.add(objopdListOfregistrationlistDTO);
            }

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }

//        return opdListOfregistrationlistDTOList;
//        MstUnit HeaderObject = mstUnitRepository.findByUnitId(opdListOfRegistrationSearchDTO.getUnitId());
//        opdListOfRegistrationSearchDTO.setObjHeaderData(HeaderObject);
//        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(opdListOfregistrationlistDTOList);

        MstUnit HeaderObject = mstUnitRepository.findByUnitId(opdListOfRegistrationSearchDTO.getUnitId());
        opdListOfregistrationlistDTOList.get(0).setObjHeaderData(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(opdListOfregistrationlistDTOList);

        // return createReport.createJasperReportXLS("OutwardReferralReport", ds);
        if(opdListOfRegistrationSearchDTO.getPrint()){
            return createReport.createJasperReportXLS("ListOfRegistrationReport", ds);
        }else {
            return createReport.createJasperReportPDF("ListOfRegistrationReport", ds);
        }
    }

*/
    //code for opd unitwise list of registration report by rohan 22.08.2019

    @RequestMapping("getOpdunitwiseregistrationReport/{unitList}/{fromDate}/{toDate}")
    public HashMap<String, Object> searchOpdunitwiseregistrationList(@RequestHeader("X-tenantId") String tenantName, @PathVariable Long[] unitList, @PathVariable String fromDate, @PathVariable String toDate) {
        TenantContext.setCurrentTenant(tenantName);
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = formatter.format(date);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromDate.equals("") || fromDate.equals("null")) {
            fromDate = "1990-06-07";
        }
        if (toDate.equals("") || toDate.equals("null")) {
            toDate = strDate;
        }
        // String strDate = formatter.format(date);
        HashMap<String, Object> response = new HashMap<>();
//      List<MstPatient> mstPatients =  mstPatientRepository.findAllByIsActiveTrueAndIsDeletedFalseAndPatientUserIdUserUnitIdUnitId(unitList);
        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        HashMap<String, Integer> ageWiseCount = new HashMap<>();
        ageWiseCount.put("a0T1M", 0);
        ageWiseCount.put("a0T1F", 0);
        ageWiseCount.put("a0T1O", 0);
        ageWiseCount.put("a1T5M", 0);
        ageWiseCount.put("a1T5F", 0);
        ageWiseCount.put("a1T5O", 0);
        ageWiseCount.put("a5T12M", 0);
        ageWiseCount.put("a5T12F", 0);
        ageWiseCount.put("a5T12O", 0);
        ageWiseCount.put("a12T18M", 0);
        ageWiseCount.put("a12T18F", 0);
        ageWiseCount.put("a12T18O", 0);
        ageWiseCount.put("a18T30M", 0);
        ageWiseCount.put("a18T30O", 0);
        ageWiseCount.put("a18T30F", 0);
        ageWiseCount.put("a30T60M", 0);
        ageWiseCount.put("a30T60F", 0);
        ageWiseCount.put("a30T60O", 0);
        ageWiseCount.put("a60T120M", 0);
        ageWiseCount.put("a60T120F", 0);
        ageWiseCount.put("a60T120O", 0);
        String unitListStr = " ";
        for (int i = 0; i < unitList.length; i++) {
            unitListStr += unitList[i];
            if (i < unitList.length - 1) {
                unitListStr += ",";
            }
        }


/*
        String query = "SELECT u.user_age,g.gender_name " +
                "from mst_patient p " +
                "inner join mst_user u on p.patient_user_id=u.user_id " +
                "inner join mst_gender g on u.user_gender_id=g.gender_id  "  +
                "WHERE u.user_unit_id=" + unitList + " AND u.user_age IS NOT NULL AND DATE(p.created_date) BETWEEN '" + fromDate + "' AND '" + toDate + "'";*/
        String query = "SELECT  DISTINCT(v.visit_patient_id),ifnull(u.user_age,0)as user_age,g.gender_name " +
                "from mst_visit v " +
                "inner join mst_patient p on v.visit_patient_id = p.patient_id " +
                "inner join mst_user u on p.patient_user_id = u.user_id " +
                "inner join mst_gender g on u.user_gender_id=g.gender_id  " +
                "WHERE v.visit_unit_id IN (" + unitListStr + ") AND DATE(p.created_date) BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        System.out.println("Unit-Wise Registration Report " +query);
        List<Object[]> list = entityManager.createNativeQuery(query).getResultList();
        for (int i = 0; i < list.size(); i++) {
            Object[] data = list.get(i);
            if (data[0] != null) {
                String temp = "" + data[1];
                if (data[1] == null) {
                    temp = "0";
                }
                Float age = 0.0f;
                try {
                    age = Float.parseFloat(temp);
                } catch (Exception e) {
                    age = 0.0f;
                    e.printStackTrace();
                }
                String gender = "" + data[2];
                if (age <= 1) {
                    if (data[1] != null) {
                        if (gender.equalsIgnoreCase("male")) {
                            int ageCount = ageWiseCount.get("a0T1M");
                            ageWiseCount.put("a0T1M", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("female")) {
                            int ageCount = ageWiseCount.get("a0T1F");
                            ageWiseCount.put("a0T1F", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("other")) {
                            int ageCount = ageWiseCount.get("a0T1O");
                            ageWiseCount.put("a0T1O", ++ageCount);
                        }
                    }
                }
                if (age > 1 && age <= 5) {
                    if (data[1] != null) {
                        if (gender.equalsIgnoreCase("male")) {
                            int ageCount = ageWiseCount.get("a1T5M");
                            ageWiseCount.put("a1T5M", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("female")) {
                            int ageCount = ageWiseCount.get("a1T5F");
                            ageWiseCount.put("a1T5F", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("other")) {
                            int ageCount = ageWiseCount.get("a1T5O");
                            ageWiseCount.put("a1T5O", ++ageCount);
                        }
                    }

                }
                if (age > 5 && age <= 12) {
                    if (data[1] != null) {
                        if (gender.equalsIgnoreCase("male")) {
                            int ageCount = ageWiseCount.get("a5T12M");
                            ageWiseCount.put("a5T12M", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("female")) {
                            int ageCount = ageWiseCount.get("a5T12F");
                            ageWiseCount.put("a5T12F", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("other")) {
                            int ageCount = ageWiseCount.get("a5T12O");
                            ageWiseCount.put("a5T12O", ++ageCount);
                        }
                    }

                }
                if (age > 12 && age <= 18) {
                    if (data[1] != null) {
                        if (gender.equalsIgnoreCase("male")) {
                            int ageCount = ageWiseCount.get("a12T18M");
                            ageWiseCount.put("a12T18M", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("female")) {
                            int ageCount = ageWiseCount.get("a12T18F");
                            ageWiseCount.put("a12T18F", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("other")) {
                            int ageCount = ageWiseCount.get("a12T18O");
                            ageWiseCount.put("a12T18O", ++ageCount);
                        }
                    }
                }
                if (age > 18 && age <= 30) {
                    if (data[1] != null) {
                        if (gender.equalsIgnoreCase("male")) {
                            int ageCount = ageWiseCount.get("a18T30M");
                            ageWiseCount.put("a18T30M", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("female")) {
                            int ageCount = ageWiseCount.get("a18T30F");
                            ageWiseCount.put("a18T30F", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("other")) {
                            int ageCount = ageWiseCount.get("a18T30O");
                            ageWiseCount.put("a18T30O", ++ageCount);
                        }
                    }
                }
                if (age > 30 && age <= 60) {
                    if (data[1] != null) {
                        if (gender.equalsIgnoreCase("male")) {
                            int ageCount = ageWiseCount.get("a30T60M");
                            ageWiseCount.put("a30T60M", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("female")) {
                            int ageCount = ageWiseCount.get("a30T60F");
                            ageWiseCount.put("a30T60F", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("other")) {
                            int ageCount = ageWiseCount.get("a30T60O");
                            ageWiseCount.put("a30T60O", ++ageCount);
                        }
                    }
                }
                if (age > 60) {
                    if (data[1] != null) {
                        if (gender.equalsIgnoreCase("male")) {
                            int ageCount = ageWiseCount.get("a60T120M");
                            ageWiseCount.put("a60T120M", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("female")) {
                            int ageCount = ageWiseCount.get("a60T120F");
                            ageWiseCount.put("a60T120F", ++ageCount);
                        }
                        if (gender.equalsIgnoreCase("other")) {
                            int ageCount = ageWiseCount.get("a60T120O");
                            ageWiseCount.put("a60T120O", ++ageCount);
                        }
                    }

                }

            }
        }
//        / System.out.println("ageWiseCount.."+ageWiseCount);
        response.put("age_wise", ageWiseCount);
        //System.out.println("ageWiseCount.."+ageWiseCount);
        //System.out.println("response.." + response);
        return response;

    }

}
