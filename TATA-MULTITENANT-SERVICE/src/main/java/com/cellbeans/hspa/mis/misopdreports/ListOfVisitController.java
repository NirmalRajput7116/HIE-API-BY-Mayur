package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mis.misbillingreport.KeyValueDto;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.utils.URI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/listofvisitReport")
public class ListOfVisitController {

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

    // Service list of visit report Start
    @RequestMapping("getlistofvisitReport/{unitList}/{fromdate}/{todate}")
    public List<ListOfVisitListDTO> searchListofVisitreport(@RequestHeader("X-tenantId") String tenantName,
                                                            @RequestBody ListOfVisitSearchDTO listOfVisitSearchDTO,
                                                            @PathVariable String[] unitList,
                                                            @PathVariable String fromdate,
                                                            @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        long tcount = 0;
        long gcount = 0;
        List<ListOfVisitListDTO> listOfVisitListDTOList = new ArrayList<ListOfVisitListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String columnName = "";
        // Main Query
        String Query = " SELECT ifnull(un.unit_name, '') as Unit, ifnull(v.visit_date,'')as visit_date," +
                " ifnull(p.patient_mr_no,'')as patient_mr_no," +
                " CONCAT(ifnull(t.title_name,''),' ',ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patient_name ," +
                " ifnull(u.user_age,'')as user_age, " +
                " ifnull(g.gender_name,'') as Gender, ifnull(u.user_mobile,'')as user_mobile, " +
                " concat(ifnull(us.user_firstname,''),' ',ifnull(us.user_lastname,'')) as doctor_name, " +
                " ifnull(ta.tariff_name,'') as Tariff, IFNULL(chc.cc_name,'') AS chief_complaint   " +
                " from mst_visit v" +
                " inner join mst_patient p on v.visit_patient_id = p.patient_id " +
                " left join mbill_tariff ta on v.visit_tariff_id = ta.tariff_id " +
                " left join mst_user u on p.patient_user_id = u.user_id " +
                " inner join mst_unit un on v.visit_unit_id = un.unit_id " +
                " left join mst_staff s on v.visit_staff_id = s.staff_id " +
                " left join mst_user us on s.staff_user_id = us.user_id " +
                " left join mst_title t on u.user_title_id = t.title_id " +
                " left join mst_gender g on u.user_gender_id = g.gender_id " +
                " LEFT JOIN memr_chief_complaint chc ON chc.cc_id = v.visit_chief_complaint " +
                " WHERE v.visit_tariff_id IS NOT NULL AND v.is_active = 1 AND v.is_deleted = 0 ";
        // Total Count
        String CountQuery = "  SELECT count(v.visit_id) from mst_visit v inner join mst_patient p on v.visit_patient_id = p.patient_id  " +
                "left join mbill_tariff ta on v.visit_tariff_id = ta.tariff_id  left join mst_user u on p.patient_user_id = u.user_id  " +
                "inner join mst_unit un on v.visit_unit_id = un.unit_id  left join mst_staff s on v.visit_staff_id = s.staff_id  " +
                "left join mst_user us on s.staff_user_id = us.user_id  left join mst_title t on u.user_title_id = t.title_id " +
                " left join mst_gender g on u.user_gender_id = g.gender_id  WHERE v.visit_tariff_id IS NOT NULL AND v.is_active = 1 " +
                "AND v.is_deleted = 0 ";
        // Tariff Count
        String tariffCount = "SELECT count(v.visit_id),ta.tariff_name from mst_visit v inner join mst_patient p on v.visit_patient_id = p.patient_id  left join mbill_tariff ta on v.visit_tariff_id = ta.tariff_id  left join mst_user u on p.patient_user_id = u.user_id  inner join mst_unit un on v.visit_unit_id = un.unit_id  left join mst_staff s on v.visit_staff_id = s.staff_id  left join mst_user us on s.staff_user_id = us.user_id  left join mst_title t on u.user_title_id = t.title_id  left join mst_gender g on u.user_gender_id = g.gender_id  WHERE v.visit_tariff_id IS NOT NULL AND v.is_active = 1 AND v.is_deleted = 0  ";
        // Gender Count
        String genderCount = "SELECT count(v.visit_id),g.gender_name from mst_visit v inner join mst_patient p on v.visit_patient_id = p.patient_id  left join mbill_tariff ta on v.visit_tariff_id = ta.tariff_id  left join mst_user u on p.patient_user_id = u.user_id  inner join mst_unit un on v.visit_unit_id = un.unit_id  left join mst_staff s on v.visit_staff_id = s.staff_id  left join mst_user us on s.staff_user_id = us.user_id  left join mst_title t on u.user_title_id = t.title_id  left join mst_gender g on u.user_gender_id = g.gender_id  WHERE v.visit_tariff_id IS NOT NULL AND v.is_active = 1 AND v.is_deleted = 0  ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (listOfVisitSearchDTO.getTodaydate()) {
            Query += " and (date(v.visit_date)=curdate()) ";
            CountQuery += " and (date(v.visit_date)=curdate()) ";
            tariffCount += "and (date(v.visit_date)=curdate()) ";
            genderCount += " and (date(v.visit_date)=curdate()) ";
        } else {
            Query += " and (date(v.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(v.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
            tariffCount += " and (date(v.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
            genderCount += " and (date(v.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and v.visit_unit_id in (" + values + ") ";
            CountQuery += " and v.visit_unit_id in (" + values + ") ";
            tariffCount += " and v.visit_unit_id in (" + values + ") ";
            genderCount += " and v.visit_unit_id in (" + values + ") ";

        }
        if (!listOfVisitSearchDTO.getGenderId().equals("null") && !listOfVisitSearchDTO.getGenderId().equals("0")) {
            Query += " and u.user_gender_id =  " + listOfVisitSearchDTO.getGenderId() + " ";
            CountQuery += " and u.user_gender_id =  " + listOfVisitSearchDTO.getGenderId() + " ";
            tariffCount += " and u.user_gender_id =  " + listOfVisitSearchDTO.getGenderId() + " ";
            genderCount += " and u.user_gender_id =  " + listOfVisitSearchDTO.getGenderId() + " ";
        }
        // doctor
        if (!listOfVisitSearchDTO.getStaffId1().equals("null") && !listOfVisitSearchDTO.getStaffId1().equals("0")) {
            Query += " and v.visit_staff_id = " + listOfVisitSearchDTO.getStaffId1() + " ";
            CountQuery += " and v.visit_staff_id = " + listOfVisitSearchDTO.getStaffId1() + " ";
            tariffCount += " and v.visit_staff_id = " + listOfVisitSearchDTO.getStaffId1() + " ";
            genderCount += " and v.visit_staff_id = " + listOfVisitSearchDTO.getStaffId1() + " ";
        }
        // Tarrif Name
        if (!listOfVisitSearchDTO.getTariffId().equals("null") && !listOfVisitSearchDTO.getTariffId().equals("0")) {
            Query += " and v.visit_tariff_id = " + listOfVisitSearchDTO.getTariffId() + " ";
            CountQuery += " and v.visit_tariff_id =" + listOfVisitSearchDTO.getTariffId() + " ";
            tariffCount += " and v.visit_tariff_id =" + listOfVisitSearchDTO.getTariffId() + " ";
            genderCount += " and v.visit_tariff_id =" + listOfVisitSearchDTO.getTariffId() + " ";
        }
        tariffCount = tariffCount + " group by ta.tariff_name";
        genderCount = genderCount + " group by g.gender_name";
        List<Object[]> listoftariff = entityManager.createNativeQuery(tariffCount).getResultList();
        BigInteger tariff = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        tcount = tariff.longValue();
        for (int i = 0; i < listoftariff.size(); i++) {
            Object ob = listoftariff.get(i);
            System.out.println("listoftariff:" + ob);
        }
        // Gender Count
        List<Object[]> listgencount = entityManager.createNativeQuery(genderCount).getResultList();
        BigInteger gencount = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        gcount = gencount.longValue();
        List<KeyValueDto> genderWise = new ArrayList<>();
        List<KeyValueDto> tariffWise = new ArrayList<>();
        for (int i = 0; i < listgencount.size(); i++) {
            KeyValueDto keyValueDto = new KeyValueDto();
            keyValueDto.setKey("" + listgencount.get(i)[1]);
            keyValueDto.setValue("" + listgencount.get(i)[0]);
            genderWise.add(keyValueDto);
        }
        for (int i = 0; i < listoftariff.size(); i++) {
            KeyValueDto keyValueDto = new KeyValueDto();
            keyValueDto.setKey("" + listoftariff.get(i)[1]);
            keyValueDto.setValue("" + listoftariff.get(i)[0]);
            tariffWise.add(keyValueDto);
        }
        System.out.println("CountQuery : " + CountQuery);
        System.out.println("List Of Visit Report : " + Query);

        try {
            Query += " limit " + ((listOfVisitSearchDTO.getOffset() - 1) * listOfVisitSearchDTO.getLimit()) + "," + listOfVisitSearchDTO.getLimit();
            List<Object[]> listofvisitreport = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listofvisitreport) {
                ListOfVisitListDTO objListOfVisitListDTO = new ListOfVisitListDTO();
                objListOfVisitListDTO.setUnitName("" + obj[0]);
                objListOfVisitListDTO.setDateOfVisit("" + obj[1]);
                objListOfVisitListDTO.setMrNo("" + obj[2]);
                objListOfVisitListDTO.setPatientName("" + obj[3]);
                objListOfVisitListDTO.setAge("" + obj[4]);
                objListOfVisitListDTO.setGender("" + obj[5]);
                objListOfVisitListDTO.setMobileNo("" + obj[6]);
                objListOfVisitListDTO.setDoctor("" + obj[7]);
                objListOfVisitListDTO.setTariffName("" + obj[8]);
                objListOfVisitListDTO.setChiefComplaint("" + obj[9]);
                objListOfVisitListDTO.setCount(count);                   // total count
                objListOfVisitListDTO.setGenderWiserCount(genderWise); // gendderwisecount
                objListOfVisitListDTO.setTariffWiseCount(tariffWise);  // tariffwisecount
                listOfVisitListDTOList.add(objListOfVisitListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("Error:" + e);
            //return null;
        }
        return listOfVisitListDTOList;
    } // END Service
    // Service list of visit report End

    // Service list of visit report Print Start
    @RequestMapping("getlistofvisitReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchListofVisitreportPrint(@RequestHeader("X-tenantId") String tenantName,
                                               @RequestBody ListOfVisitSearchDTO listOfVisitSearchDTO,
                                               @PathVariable String[] unitList,
                                               @PathVariable String fromdate,
                                               @PathVariable String todate) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        long tcount = 0;
        long gcount = 0;
        List<ListOfVisitListDTO> listOfVisitListDTOList = new ArrayList<ListOfVisitListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String columnName = "";
        // Main Query
        String Query = " SELECT ifnull(un.unit_name, '') as Unit, ifnull(v.visit_date,'')as visit_date," +
                " ifnull(p.patient_mr_no,'')as patient_mr_no," +
                " CONCAT(ifnull(t.title_name,''),'',ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patient_name ," +
                " ifnull(u.user_age,'')as user_age, " +
                " ifnull(g.gender_name,'') as Gender, ifnull(u.user_mobile,'')as user_mobile, " +
                " concat(ifnull(us.user_firstname,''),' ',ifnull(us.user_lastname,'')) as doctor_name, " +
                " ifnull(ta.tariff_name,'') as Tariff, IFNULL(chc.cc_name,'') AS chief_complaint  " +
                " from mst_visit v" +
                " inner join mst_patient p on v.visit_patient_id = p.patient_id " +
                " left join mbill_tariff ta on v.visit_tariff_id = ta.tariff_id " +
                " left join mst_user u on p.patient_user_id = u.user_id " +
                " inner join mst_unit un on v.visit_unit_id = un.unit_id " +
                " left join mst_staff s on v.visit_staff_id = s.staff_id " +
                " left join mst_user us on s.staff_user_id = us.user_id " +
                " left join mst_title t on u.user_title_id = t.title_id " +
                " left join mst_gender g on u.user_gender_id = g.gender_id " +
                " LEFT JOIN memr_chief_complaint chc ON chc.cc_id = v.visit_chief_complaint " +
                " WHERE v.visit_tariff_id IS NOT NULL AND v.is_active = 1 AND v.is_deleted = 0 ";
        // Total Count
        String CountQuery = "  SELECT count(v.visit_id) from mst_visit v inner join mst_patient p on v.visit_patient_id = p.patient_id  " +
                "left join mbill_tariff ta on v.visit_tariff_id = ta.tariff_id  left join mst_user u on p.patient_user_id = u.user_id  " +
                "inner join mst_unit un on v.visit_unit_id = un.unit_id  left join mst_staff s on v.visit_staff_id = s.staff_id  " +
                "left join mst_user us on s.staff_user_id = us.user_id  left join mst_title t on u.user_title_id = t.title_id " +
                " left join mst_gender g on u.user_gender_id = g.gender_id  WHERE v.visit_tariff_id IS NOT NULL AND v.is_active = 1 " +
                "AND v.is_deleted = 0 ";
        // Tariff Count
        String tariffCount = "SELECT count(v.visit_id),ta.tariff_name from mst_visit v inner join mst_patient p on v.visit_patient_id = p.patient_id  left join mbill_tariff ta on v.visit_tariff_id = ta.tariff_id  left join mst_user u on p.patient_user_id = u.user_id  inner join mst_unit un on v.visit_unit_id = un.unit_id  left join mst_staff s on v.visit_staff_id = s.staff_id  left join mst_user us on s.staff_user_id = us.user_id  left join mst_title t on u.user_title_id = t.title_id  left join mst_gender g on u.user_gender_id = g.gender_id  WHERE v.visit_tariff_id IS NOT NULL AND v.is_active = 1 AND v.is_deleted = 0  ";
        // Gender Count
        String genderCount = "SELECT count(v.visit_id),g.gender_name from mst_visit v inner join mst_patient p on v.visit_patient_id = p.patient_id  left join mbill_tariff ta on v.visit_tariff_id = ta.tariff_id  left join mst_user u on p.patient_user_id = u.user_id  inner join mst_unit un on v.visit_unit_id = un.unit_id  left join mst_staff s on v.visit_staff_id = s.staff_id  left join mst_user us on s.staff_user_id = us.user_id  left join mst_title t on u.user_title_id = t.title_id  left join mst_gender g on u.user_gender_id = g.gender_id  WHERE v.visit_tariff_id IS NOT NULL AND v.is_active = 1 AND v.is_deleted = 0  ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (listOfVisitSearchDTO.getTodaydate()) {
            Query += " and (date(v.visit_date)=curdate()) ";
            CountQuery += " and (date(v.visit_date)=curdate()) ";
            tariffCount += "and (date(v.visit_date)=curdate()) ";
            genderCount += " and (date(v.visit_date)=curdate()) ";
        } else {
            Query += " and (date(v.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(v.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
            tariffCount += " and (date(v.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
            genderCount += " and (date(v.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and v.visit_unit_id in (" + values + ") ";
            CountQuery += " and v.visit_unit_id in (" + values + ") ";
            tariffCount += " and v.visit_unit_id in (" + values + ") ";
            genderCount += " and v.visit_unit_id in (" + values + ") ";

        }
        if (!listOfVisitSearchDTO.getGenderId().equals("null") && !listOfVisitSearchDTO.getGenderId().equals("0")) {
            Query += " and u.user_gender_id =  " + listOfVisitSearchDTO.getGenderId() + " ";
            CountQuery += " and u.user_gender_id =  " + listOfVisitSearchDTO.getGenderId() + " ";
            tariffCount += " and u.user_gender_id =  " + listOfVisitSearchDTO.getGenderId() + " ";
            genderCount += " and u.user_gender_id =  " + listOfVisitSearchDTO.getGenderId() + " ";
        }
        // doctor
        if (!listOfVisitSearchDTO.getStaffId1().equals("null") && !listOfVisitSearchDTO.getStaffId1().equals("0")) {
            Query += " and v.visit_staff_id = " + listOfVisitSearchDTO.getStaffId1() + " ";
            CountQuery += " and v.visit_staff_id = " + listOfVisitSearchDTO.getStaffId1() + " ";
            tariffCount += " and v.visit_staff_id = " + listOfVisitSearchDTO.getStaffId1() + " ";
            genderCount += " and v.visit_staff_id = " + listOfVisitSearchDTO.getStaffId1() + " ";
        }
        // Tarrif Name
        if (!listOfVisitSearchDTO.getTariffId().equals("null") && !listOfVisitSearchDTO.getTariffId().equals("0")) {
            Query += " and v.visit_tariff_id = " + listOfVisitSearchDTO.getTariffId() + " ";
            CountQuery += " and v.visit_tariff_id =" + listOfVisitSearchDTO.getTariffId() + " ";
            tariffCount += " and v.visit_tariff_id =" + listOfVisitSearchDTO.getTariffId() + " ";
            genderCount += " and v.visit_tariff_id =" + listOfVisitSearchDTO.getTariffId() + " ";
        }
        tariffCount = tariffCount + " group by ta.tariff_name";
        genderCount = genderCount + " group by g.gender_name";
        List<Object[]> listoftariff = entityManager.createNativeQuery(tariffCount).getResultList();
        BigInteger tariff = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        tcount = tariff.longValue();
        for (int i = 0; i < listoftariff.size(); i++) {
            Object ob = listoftariff.get(i);
            System.out.println("listoftariff:" + ob);
        }
        // Gender Count
        List<Object[]> listgencount = entityManager.createNativeQuery(genderCount).getResultList();
        BigInteger gencount = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        gcount = gencount.longValue();
        List<KeyValueDto> genderWise = new ArrayList<>();
        List<KeyValueDto> tariffWise = new ArrayList<>();
        for (int i = 0; i < listgencount.size(); i++) {
            KeyValueDto keyValueDto = new KeyValueDto();
            keyValueDto.setKey("" + listgencount.get(i)[1]);
            keyValueDto.setValue("" + listgencount.get(i)[0]);
            genderWise.add(keyValueDto);
        }
        for (int i = 0; i < listoftariff.size(); i++) {
            KeyValueDto keyValueDto = new KeyValueDto();
            keyValueDto.setKey("" + listoftariff.get(i)[1]);
            keyValueDto.setValue("" + listoftariff.get(i)[0]);
            tariffWise.add(keyValueDto);
        }
        System.out.println("CountQuery : " + CountQuery);
        JSONArray array = new JSONArray();
        try {
//            Query += " limit " + ((listOfVisitSearchDTO.getOffset() - 1) * listOfVisitSearchDTO.getLimit()) + "," + listOfVisitSearchDTO.getLimit();
            List<Object[]> listofvisitreport = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listofvisitreport) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("unitName", "" + obj[0]);
                jsonObject.put("dateOfVisit", "" + obj[1]);
                jsonObject.put("mrNo", "" + obj[2]);
                jsonObject.put("patientName", "" + obj[3]);
                jsonObject.put("age", "" + obj[4]);
                jsonObject.put("gender", "" + obj[5]);
                jsonObject.put("mobileNo", "" + obj[6]);
                jsonObject.put("doctor", "" + obj[7]);
                jsonObject.put("tariffName", "" + obj[8]);
                jsonObject.put("chiefComplaint", "" + obj[9]);
                array.put(jsonObject);
            }
            if (array.length() > 0) {
                array.getJSONObject(0).put("fromdate", fromdate);
                array.getJSONObject(0).put("todate", todate);
            }

        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("Error:" + e);
            //return null;
        }
        ObjectMapper Obj = new ObjectMapper();
        ObjectMapper Obj1 = new ObjectMapper();
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(listOfVisitSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(listOfVisitSearchDTO.getUserId());

        String jsonStr = Obj.writeValueAsString(HeaderObject);
        String jsonStr1 = Obj1.writeValueAsString(HeaderObjectUser);
        System.out.println(jsonStr);
        System.out.println(jsonStr1);
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        System.out.println(jsonObject.toString());
        System.out.println(jsonObjectUser.toString());
        array.getJSONObject(0).put("objHeaderData", jsonObject);
        array.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (listOfVisitSearchDTO.getPrint()) {
            columnName = "unitName,dateOfVisit,mrNo,patientName,age,gender,mobileNo,doctor,tariffName,chiefComplaint";
            return createReport.generateExcel(columnName, "ListofVisitReport", array);
        } else {
            return createReport.createJasperReportPDFByJson("ListofVisitReport", array.toString());
        }
    } // END Service
    // Service list of visit report Print End

    //    Teleconsultation Wait Duration Report Start 13.02.2021
    @RequestMapping("searchTeleconsultWaitDurationReport/{staffList}/{fromdate}/{todate}")
    public ResponseEntity TeleconsultWaitDurationReport(@RequestHeader("X-tenantId") String tenantName,
                                                        @RequestBody teleWaitDurationSearchDTO teleWaitdurationsearchDTO,
                                                        @PathVariable String[] staffList,
                                                        @PathVariable String fromdate,
                                                        @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";

        /*MainQuery = " SELECT DISTINCT(tt.timeline_patient_id), mun.unit_name, ifnull(mv.visit_date,'')as visit_date, " +
                "ifnull(mp.patient_mr_no, '') as patient_mr_no, "
                + " ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as Patient_Name,"
                + " ifnull(mu.user_age,'')as user_age, ifnull(mg.gender_name, '') as gender_name, mta.tariff_name as tariff_name,  "
                + " CONCAT(mt1.title_name,' ', mu1.user_firstname,' ', mu1.user_lastname) AS Finalized_By "
                + " from temr_timeline tt " + " INNER JOIN mst_patient mp on tt.timeline_patient_id = mp.patient_id "
                + " INNER JOIN mst_user mu on mp.patient_user_id = mu.user_id "
                + " LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id "
                + " LEFT JOIN mst_gender mg on mu.user_gender_id = mg.gender_id "
                + " LEFT JOIN mst_visit mv on tt.timeline_visit_id = mv.visit_id "
                + " LEFT JOIN mst_unit mun on mv.visit_unit_id = mun.unit_id "
                + " LEFT JOIN mbill_tariff mta on mv.visit_tariff_id = mta.tariff_id "
                + " INNER JOIN mst_staff ms ON tt.timelineemrfinal_staff_id = ms.staff_id "
                + " LEFT JOIN mst_user mu1 ON ms.staff_user_id = mu1.user_id "
                + " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id "
                + " WHERE mv.visit_tariff_id IS NOT NULL AND tt.isemrfinal = 1 AND tt.is_active=1 AND tt.is_deleted=0 ";*/
        MainQuery = " SELECT mun.unit_name,  " +
                " concat(MIN(date(mv.created_date)),' to ', MAX(date(mv.created_date))) AS visit_date, CONCAT(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname) AS Consulted_By, " +
                " MIN(mv.visit_consultation_duration) AS minDur, MAX(mv.visit_consultation_duration) AS maxDura, sum(mv.visit_consultation_duration) AS totConsDur, AVG(mv.visit_consultation_duration) AS avgDur, " +
                " COUNT(mv.visit_id) AS teleCount " +
                " FROM temr_timeline tt " +
                " LEFT JOIN mst_visit mv ON tt.timeline_visit_id = mv.visit_id " +
                " INNER JOIN mst_staff ms ON mv.visit_staff_id = ms.staff_id " +
                " INNER JOIN mst_user mu ON ms.staff_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_unit mun ON mv.visit_unit_id = mun.unit_id " +
                " LEFT JOIN trn_appointment ta ON ta.appointment_timeline_id = tt.timeline_id " +
                " WHERE  tt.is_active=1  " +
                " AND tt.is_deleted=0 AND ta.appointment_is_cancelled = FALSE  " +
                " AND mv.is_virtual = TRUE AND ta.appointment_is_confirm= TRUE  " +
                " AND mv.visit_is_called AND mv.visit_consultation_duration IS NOT null " +
                " AND mv.visit_unit_id =" + teleWaitdurationsearchDTO.getUnitId();


        /*if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and mv.visit_unit_id in (" + values + ") ";

        }*/
        if (!staffList[0].equals("null")) {
            String values = staffList[0];
            for (int i = 1; i < staffList.length; i++) {
                values += "," + staffList[i];
            }
//            MainQuery += " and sf.sc_unit_id in (" + values + ") ";
            MainQuery += " and mv.visit_staff_id in (" + values + ") ";

        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
//        if (teleWaitdurationsearchDTO.getFromdate().equals("null") || teleWaitdurationsearchDTO.getFromdate().equals("")) {
//            teleWaitdurationsearchDTO.setFromdate("1990-06-07");
//        }
//        if (teleWaitdurationsearchDTO.getTodate().equals("null") || teleWaitdurationsearchDTO.getTodate().equals("")) {
//            teleWaitdurationsearchDTO.setTodate(strDate);
//        }
//		if (teleWaitdurationsearchDTO.getTodaydate()) {
//			MainQuery += " and (date(mv.visit_date)=curdate()) ";
//
//		} else {
//			MainQuery += " and (date(mv.visit_date) between '" + teleWaitdurationsearchDTO.getFromdate() + "' and '" + teleWaitdurationsearchDTO.getTodate() + "')  ";
//		}


  /*      System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();

        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        for (Object[] obj : list) {

            if (obj[6].toString() != null && !"".equals(obj[6].toString())) {
                if (genderWiseCount.get(obj[6].toString()) == null) {
                    genderWiseCount.put(obj[6].toString(), 1);
                } else {
                    int gender_count = genderWiseCount.get(obj[6].toString());
                    genderWiseCount.put(obj[6].toString(), ++gender_count);
                }
            }
        }*/
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "unit_name,visit_date,Consulted_By,minDur,maxDur,totConsDur,avgDur,teleCount";
        MainQuery += "  GROUP BY mv.visit_staff_id ";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        /*try {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonArray.getJSONObject(i).put("genderCount", new JSONObject(genderWiseCount));
            }
        } catch (Exception e) {
        }*/
        return ResponseEntity.ok(jsonArray.toString());
    }
//    Teleconsultation Wait Duration Report End

    @RequestMapping("searchTeleconsultWaitDurationReportPrint/{staffList}/{fromdate}/{todate}")
    public String searchTeleconsultWaitDurationReportPrint(@RequestHeader("X-tenantId") String tenantName,
                                                           @RequestBody teleWaitDurationSearchDTO teleWaitdurationsearchDTO,
                                                           @PathVariable String[] staffList,
                                                           @PathVariable String fromdate,
                                                           @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";

        /*MainQuery = " SELECT DISTINCT(tt.timeline_patient_id), mun.unit_name, ifnull(mv.visit_date,'')as visit_date, " +
                "ifnull(mp.patient_mr_no, '') as patient_mr_no, "
                + " ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as Patient_Name,"
                + " ifnull(mu.user_age,'')as user_age, ifnull(mg.gender_name, '') as gender_name, mta.tariff_name as tariff_name,  "
                + " CONCAT(mt1.title_name,' ', mu1.user_firstname,' ', mu1.user_lastname) AS Finalized_By "
                + " from temr_timeline tt " + " INNER JOIN mst_patient mp on tt.timeline_patient_id = mp.patient_id "
                + " INNER JOIN mst_user mu on mp.patient_user_id = mu.user_id "
                + " LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id "
                + " LEFT JOIN mst_gender mg on mu.user_gender_id = mg.gender_id "
                + " LEFT JOIN mst_visit mv on tt.timeline_visit_id = mv.visit_id "
                + " LEFT JOIN mst_unit mun on mv.visit_unit_id = mun.unit_id "
                + " LEFT JOIN mbill_tariff mta on mv.visit_tariff_id = mta.tariff_id "
                + " INNER JOIN mst_staff ms ON tt.timelineemrfinal_staff_id = ms.staff_id "
                + " LEFT JOIN mst_user mu1 ON ms.staff_user_id = mu1.user_id "
                + " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id "
                + " WHERE mv.visit_tariff_id IS NOT NULL AND tt.isemrfinal = 1 AND tt.is_active=1 AND tt.is_deleted=0 ";*/
        MainQuery = " SELECT mun.unit_name,  " +
                " concat(MIN(date(mv.created_date)),' to ', MAX(date(mv.created_date))) AS visit_date, CONCAT(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname) AS Consulted_By, " +
                " MIN(mv.visit_consultation_duration) AS minDur, MAX(mv.visit_consultation_duration) AS maxDura, sum(mv.visit_consultation_duration) AS totConsDur, AVG(mv.visit_consultation_duration) AS avgDur, " +
                " COUNT(mv.visit_id) AS teleCount " +
                " FROM temr_timeline tt " +
                " LEFT JOIN mst_visit mv ON tt.timeline_visit_id = mv.visit_id " +
                " INNER JOIN mst_staff ms ON mv.visit_staff_id = ms.staff_id " +
                " INNER JOIN mst_user mu ON ms.staff_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_unit mun ON mv.visit_unit_id = mun.unit_id " +
                " LEFT JOIN trn_appointment ta ON ta.appointment_timeline_id = tt.timeline_id " +
                " WHERE  tt.is_active=1  " +
                " AND tt.is_deleted=0 AND ta.appointment_is_cancelled = FALSE  " +
                " AND mv.is_virtual = TRUE AND ta.appointment_is_confirm= TRUE  " +
                " AND mv.visit_is_called AND mv.visit_consultation_duration IS NOT null " +
                " AND mv.visit_unit_id =" + teleWaitdurationsearchDTO.getUnitId();


        /*if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and mv.visit_unit_id in (" + values + ") ";

        }*/
        if (!staffList[0].equals("null")) {
            String values = staffList[0];
            for (int i = 1; i < staffList.length; i++) {
                values += "," + staffList[i];
            }
//            MainQuery += " and sf.sc_unit_id in (" + values + ") ";
            MainQuery += " and mv.visit_staff_id in (" + values + ") ";

        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
            if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
//        if (teleWaitdurationsearchDTO.getFromdate().equals("null") || teleWaitdurationsearchDTO.getFromdate().equals("")) {
//            teleWaitdurationsearchDTO.setFromdate("1990-06-07");
//        }
//        if (teleWaitdurationsearchDTO.getTodate().equals("null") || teleWaitdurationsearchDTO.getTodate().equals("")) {
//            teleWaitdurationsearchDTO.setTodate(strDate);
//        }
//		if (teleWaitdurationsearchDTO.getTodaydate()) {
//			MainQuery += " and (date(mv.visit_date)=curdate()) ";
//
//		} else {
//			MainQuery += " and (date(mv.visit_date) between '" + teleWaitdurationsearchDTO.getFromdate() + "' and '" + teleWaitdurationsearchDTO.getTodate() + "')  ";
//		}


  /*      System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();

        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        for (Object[] obj : list) {

            if (obj[6].toString() != null && !"".equals(obj[6].toString())) {
                if (genderWiseCount.get(obj[6].toString()) == null) {
                    genderWiseCount.put(obj[6].toString(), 1);
                } else {
                    int gender_count = genderWiseCount.get(obj[6].toString());
                    genderWiseCount.put(obj[6].toString(), ++gender_count);
                }
            }
        }*/
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "unit_name,visit_date,Consulted_By,minDur,maxDur,totConsDur,avgDur,teleCount";
        MainQuery += "  GROUP BY mv.visit_staff_id ";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        /*try {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonArray.getJSONObject(i).put("genderCount", new JSONObject(genderWiseCount));
            }
        } catch (Exception e) {
        }*/
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(teleWaitdurationsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(teleWaitdurationsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (teleWaitdurationsearchDTO.getPrint()) {
            return createReport.generateExcel(columnName, "TeleconsultationDurationReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("TeleconsultationDurationReport", jsonArray.toString());
        }
    }


  /*  @RequestMapping("searchTeleconsultWaitDurationReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchOpdCollectionDetailPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody teleWaitDurationSearchDTO teleWaitdurationsearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        long count = 0;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";

        //Main EMR Finalized Query
        MainQuery = " SELECT DISTINCT(tt.timeline_patient_id), mun.unit_name, ifnull(mv.visit_date,'')as visit_date, " +
                "ifnull(mp.patient_mr_no, '') as patient_mr_no, "
                + " ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as Patient_Name,"
                + " ifnull(mu.user_age,'')as user_age, ifnull(mg.gender_name, '') as gender_name, mta.tariff_name as tariff_name,  "
                + " CONCAT(mt1.title_name,' ', mu1.user_firstname,' ', mu1.user_lastname) AS Finalized_By "
                + " from temr_timeline tt " + " INNER JOIN mst_patient mp on tt.timeline_patient_id = mp.patient_id "
                + " INNER JOIN mst_user mu on mp.patient_user_id = mu.user_id "
                + " LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id "
                + " LEFT JOIN mst_gender mg on mu.user_gender_id = mg.gender_id "
                + " LEFT JOIN mst_visit mv on tt.timeline_visit_id = mv.visit_id "
                + " LEFT JOIN mst_unit mun on mv.visit_unit_id = mun.unit_id "
                + " LEFT JOIN mbill_tariff mta on mv.visit_tariff_id = mta.tariff_id "
                + " INNER JOIN mst_staff ms ON tt.timelineemrfinal_staff_id = ms.staff_id "
                + " LEFT JOIN mst_user mu1 ON ms.staff_user_id = mu1.user_id "
                + " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id "
                + " WHERE mv.visit_tariff_id IS NOT NULL AND tt.isemrfinal = 1 AND tt.is_active=1 AND tt.is_deleted=0 ";


        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and mv.visit_unit_id in (" + values + ") ";

        }

        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }

        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
//        if (teleWaitdurationsearchDTO.getFromdate().equals("null") || teleWaitdurationsearchDTO.getFromdate().equals("")) {
//            teleWaitdurationsearchDTO.setFromdate("1990-06-07");
//        }
//        if (teleWaitdurationsearchDTO.getTodate().equals("null") || teleWaitdurationsearchDTO.getTodate().equals("")) {
//            teleWaitdurationsearchDTO.setTodate(strDate);
//        }
//		if (teleWaitdurationsearchDTO.getTodaydate()) {
//			MainQuery += " and (date(mv.visit_date)=curdate()) ";
//
//		} else {
//			MainQuery += " and (date(mv.visit_date) between '" + teleWaitdurationsearchDTO.getFromdate() + "' and '" + teleWaitdurationsearchDTO.getTodate() + "')  ";
//		}

        if (teleWaitdurationsearchDTO.getMrNo() != null && !teleWaitdurationsearchDTO.getMrNo().equals("")) {
            MainQuery += " and mp.patient_mr_no like  '%" + teleWaitdurationsearchDTO.getMrNo() + "%' ";

        }

        if (teleWaitdurationsearchDTO.getStaffId() != null && !String.valueOf(teleWaitdurationsearchDTO.getStaffId()).equals("0")) {
            MainQuery += "  and ms.staff_id=" + teleWaitdurationsearchDTO.getStaffId() + " ";

        }
        if (teleWaitdurationsearchDTO.getGenderId() != null && !teleWaitdurationsearchDTO.getGenderId().equals("0")) {
            MainQuery += " and mu.user_gender_id  =  " + teleWaitdurationsearchDTO.getGenderId() + " ";

        }
        if (teleWaitdurationsearchDTO.getPatientName() != null && !teleWaitdurationsearchDTO.getPatientName().equals("")) {
            MainQuery += " and concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname) LIKE '%" + teleWaitdurationsearchDTO.getPatientName() + "%'";

        }


        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();

        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        for (Object[] obj : list) {

            if (obj[6].toString() != null && !"".equals(obj[6].toString())) {
                if (genderWiseCount.get(obj[6].toString()) == null) {
                    genderWiseCount.put(obj[6].toString(), 1);
                } else {
                    int gender_count = genderWiseCount.get(obj[6].toString());
                    genderWiseCount.put(obj[6].toString(), ++gender_count);
                }
            }
        }
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "timeline_patient_id,unit_name,visit_date,patient_mr_no,Patient_Name,user_age,gender_name,tariff_name,Finalized_By";
        MainQuery += "  limit " + ((teleWaitdurationsearchDTO.getOffset() - 1) * teleWaitdurationsearchDTO.getLimit()) + "," + teleWaitdurationsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
//		try {
////			for (int i = 0; i < jsonArray.length(); i++) {
////				jsonArray.getJSONObject(i).put("genderCount", new JSONObject(genderWiseCount));
////			}
////		} catch (Exception e) {
////		}
////		return ResponseEntity.ok(jsonArray.toString());

        MstUnit HeaderObject = mstUnitRepository.findByUnitId(teleWaitdurationsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(teleWaitdurationsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);

        if (teleWaitdurationsearchDTO.getPrint()) {
            columnName = "timeline_patient_id,unit_name,visit_date,patient_mr_no,Patient_Name,user_age,gender_name,tariff_name,Finalized_By";
            return createReport.generateExcel(columnName, "FinalizedEmrCountReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("FinalizedEmrCountReport", jsonArray.toString());
        }

    }*/

    //    Teleconsultation Wait Duration Report Start 13.02.2021
    @RequestMapping("searchScheduleUtilizationReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity ScheduleUtilizationReport(@RequestHeader("X-tenantId") String tenantName,
                                                    @RequestBody teleWaitDurationSearchDTO teleWaitdurationsearchDTO,
                                                    @PathVariable String[] unitList,
                                                    @PathVariable String fromdate,
                                                    @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT ( " +
                "SELECT CONCAT(mu.user_firstname, ' ', mu.user_lastname) " +
                "FROM mst_staff ms " +
                "INNER JOIN mst_user mu " +
                "WHERE mu.user_id=ms.staff_user_id AND ms.staff_id=ta.appointment_staff_id) AS name, COUNT(ta.appointment_id" +
                ") AS no_of_appointment,( " +
                "SELECT COUNT(tt.timeline_id) " +
                "FROM temr_timeline tt " +
                "INNER JOIN mst_visit mv " +
                "INNER JOIN trn_appointment ta1 " +
                "WHERE tt.timeline_visit_id=mv.visit_id AND tt.isemrfinal= TRUE AND ta1.meeting_id=mv.visit_id AND ta1.appointment_staff_id=ta.appointment_staff_id";
        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(ta.appointment_date)=curdate()) ";
        } else {
            MainQuery += " and (date(ta1.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        MainQuery = MainQuery + ") AS final_appointment,( " +
                "SELECT COUNT(mv.visit_id) " +
                "FROM mst_visit mv " +
                "INNER JOIN trn_appointment app " +
                "WHERE mv.opd_visit_type=2 AND app.meeting_id=mv.visit_id AND app.appointment_staff_id=ta.appointment_staff_id ";
        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(app.appointment_date)=curdate()) ";
        } else {
            MainQuery += " and (date(app.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        MainQuery = MainQuery + " ) AS virtual_appointment,( " +
                "SELECT COUNT(mv.visit_id) " +
                "FROM mst_visit mv " +
                "INNER JOIN trn_appointment app " +
                "WHERE mv.opd_visit_type=1 AND app.meeting_id=mv.visit_id AND app.appointment_staff_id=ta.appointment_staff_id ";
        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(app.appointment_date)=curdate()) ";
        } else {
            MainQuery += " and (date(app.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        MainQuery += ") AS physical_appointment,( " +
                "SELECT mu.unit_name " +
                "FROM mst_unit mu " +
                "WHERE mu.unit_id=ta.appointment_unit_id) AS unit_name " +
                "FROM trn_appointment ta where 1";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and ta.appointment_unit_id in (" + values + ") ";

        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(ta.appointment_date)=curdate()) ";
        } else {
            MainQuery += " and (date(ta.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (teleWaitdurationsearchDTO.getStaffId1() != null && !teleWaitdurationsearchDTO.getStaffId1().equals("0")) {
            MainQuery += "  and ta.appointment_staff_id=" + teleWaitdurationsearchDTO.getStaffId1() + " ";
        }
        MainQuery += " GROUP BY ta.appointment_staff_id ";
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "name,no_of_appointment,final_appointment,virtual_appointment,physical_appointment,unit_name";
        MainQuery += "  limit " + ((teleWaitdurationsearchDTO.getOffset() - 1) * teleWaitdurationsearchDTO.getLimit()) + "," + teleWaitdurationsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        return ResponseEntity.ok(jsonArray.toString());
    }

    @RequestMapping("searchScheduleUtilizationReportPrint/{unitList}/{fromdate}/{todate}")
    public String ScheduleUtilizationReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody teleWaitDurationSearchDTO teleWaitdurationsearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT ( " +
                "SELECT CONCAT(mu.user_firstname, ' ', mu.user_lastname) " +
                "FROM mst_staff ms " +
                "INNER JOIN mst_user mu " +
                "WHERE mu.user_id=ms.staff_user_id AND ms.staff_id=ta.appointment_staff_id) AS name, COUNT(ta.appointment_id" +
                ") AS no_of_appointment,( " +
                "SELECT COUNT(tt.timeline_id) " +
                "FROM temr_timeline tt " +
                "INNER JOIN mst_visit mv " +
                "INNER JOIN trn_appointment ta1 " +
                "WHERE tt.timeline_visit_id=mv.visit_id AND tt.isemrfinal= TRUE AND ta1.meeting_id=mv.visit_id AND ta1.appointment_staff_id=ta.appointment_staff_id";
        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(ta.appointment_date)=curdate()) ";
        } else {
            MainQuery += " and (date(ta1.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        MainQuery = MainQuery + ") AS final_appointment,( " +
                "SELECT COUNT(mv.visit_id) " +
                "FROM mst_visit mv " +
                "INNER JOIN trn_appointment app " +
                "WHERE mv.opd_visit_type=2 AND app.meeting_id=mv.visit_id AND app.appointment_staff_id=ta.appointment_staff_id ";
        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(app.appointment_date)=curdate()) ";
        } else {
            MainQuery += " and (date(app.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        MainQuery = MainQuery + " ) AS virtual_appointment,( " +
                "SELECT COUNT(mv.visit_id) " +
                "FROM mst_visit mv " +
                "INNER JOIN trn_appointment app " +
                "WHERE mv.opd_visit_type=1 AND app.meeting_id=mv.visit_id AND app.appointment_staff_id=ta.appointment_staff_id ";
        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(app.appointment_date)=curdate()) ";
        } else {
            MainQuery += " and (date(app.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        MainQuery += ") AS physical_appointment,( " +
                "SELECT mu.unit_name " +
                "FROM mst_unit mu " +
                "WHERE mu.unit_id=ta.appointment_unit_id) AS unit_name " +
                "FROM trn_appointment ta where 1";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and ta.appointment_unit_id in (" + values + ") ";

        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (teleWaitdurationsearchDTO.getTodaydate()) {
            MainQuery += " and (date(ta.appointment_date)=curdate()) ";
        } else {
            MainQuery += " and (date(ta.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (teleWaitdurationsearchDTO.getStaffId1() != null && !teleWaitdurationsearchDTO.getStaffId1().equals("0")) {
            MainQuery += "  and ta.appointment_staff_id=" + teleWaitdurationsearchDTO.getStaffId1() + " ";
        }
        MainQuery += " GROUP BY ta.appointment_staff_id ";
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "name,no_of_appointment,final_appointment,virtual_appointment,physical_appointment,unit_name";
        //  MainQuery += "  limit " + ((teleWaitdurationsearchDTO.getOffset() - 1) * teleWaitdurationsearchDTO.getLimit()) + "," + teleWaitdurationsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(teleWaitdurationsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(teleWaitdurationsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (teleWaitdurationsearchDTO.getPrint()) {
            columnName = "unit_name,name,no_of_appointment,final_appointment,virtual_appointment,physical_appointment";
            return createReport.generateExcel(columnName, "ScheduleUtilizationReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("ScheduleUtilizationReport", jsonArray.toString());
        }
    }

    @RequestMapping("/clinicalHandOverAutoGenerateMail/{todayDate}")
    public String clinicalHandOverAutoGenerateMail(@RequestHeader("X-tenantId") String tenantName, @PathVariable String todayDate) {
        TenantContext.setCurrentTenant(tenantName);
        Object ob = "";
        String query = "SELECT admission_id FROM trn_admission WHERE cast(admission_date as date)=cast('" + todayDate + "' as date)";
        System.out.println("query :" + query);
        List<Object[]> list = entityManager.createNativeQuery(query).getResultList();
        System.out.println("list 1: " + list.size());
        for (int i = 0; i < list.size(); i++) {
            ob = list.get(i);
            System.out.println("list 2: " + ob);
        }
        JSONArray array = new JSONArray();
        try {
            RestTemplate restTemplate = new RestTemplate();
            final String baseUrl6 = Propertyconfig.getServiceUrl() + "trn_admission/byid/" + ob;
            URI uri6 = new URI(baseUrl6);
            String uri_to_string6 = uri6.toString();
            HttpHeaders headers6 = new HttpHeaders();
            headers6.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> requestEntity6 = new HttpEntity<>(null, headers6);
            ResponseEntity<String> trnAdmissionbyidResult = restTemplate.exchange(uri_to_string6, HttpMethod.GET, requestEntity6, String.class);
            System.out.println("trn_admission/byid : " + trnAdmissionbyidResult.getBody());

          /*final String baseUrl = Propertyconfig.getServiceUrl() + "temr_visit_non_drug_allergy/getAllRecordByTimelineList";
          URI uri = new URI(baseUrl);
          String uri_to_string = uri.toString();
          HttpHeaders headers = new HttpHeaders();
          headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
          HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
          ResponseEntity<String> temrVisitNonDrugAllergyResult = restTemplate.exchange(uri_to_string, HttpMethod.POST, requestEntity, String.class);
          System.out.println("temr_visit_non_drug_allergy List : " + temrVisitNonDrugAllergyResult.getBody());

          final String baseUrl1 = Propertyconfig.getServiceUrl() + "temr_visit_symptom/getAllRecordByTimelineList";
          URI uri1 = new URI(baseUrl1);
          String uri_to_string1 = uri1.toString();
          HttpHeaders headers1 = new HttpHeaders();
          headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
          HttpEntity<String> requestEntity1 = new HttpEntity<>(null, headers1);
          ResponseEntity<String> temrVisitSymptomResult = restTemplate.exchange(uri_to_string1, HttpMethod.GET, requestEntity1, String.class);
          System.out.println("temr_visit_symptom : " + temrVisitSymptomResult.getBody());

          final String baseUrl2 = Propertyconfig.getServiceUrl() + "temr_visit_chief_complaint/getAllRecordByTimelineList";
          URI uri2 = new URI(baseUrl2);
          String uri_to_string2 = uri2.toString();
          HttpHeaders headers2 = new HttpHeaders();
          headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
          HttpEntity<String> requestEntity2 = new HttpEntity<>(null, headers2);
          ResponseEntity<String> chiefComplaintAllRecordByTimelineListresult = restTemplate.exchange(uri_to_string2, HttpMethod.GET, requestEntity2, String.class);
          System.out.println("temr_visit_chief_complaint : " + chiefComplaintAllRecordByTimelineListresult.getBody());

          final String baseUrl3 = Propertyconfig.getServiceUrl() + "temr_visit_history/getAllRecordByTimelineList";
          URI uri3 = new URI(baseUrl3);
          String uri_to_string3 = uri3.toString();
          HttpHeaders headers3 = new HttpHeaders();
          headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
          HttpEntity<String> requestEntity3 = new HttpEntity<>(null, headers3);
          ResponseEntity<String> emr_visit_historyresult = restTemplate.exchange(uri_to_string3, HttpMethod.GET, requestEntity3, String.class);
          System.out.println("emr_visit_historyresult : " + emr_visit_historyresult.getBody());

          final String baseUrl4 = Propertyconfig.getServiceUrl() + "temr_doctors_advice/getAllRecordByTimelineList";
          URI uri4 = new URI(baseUrl4);
          String uri_to_string4 = uri4.toString();
          HttpHeaders headers4 = new HttpHeaders();
          headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
          HttpEntity<String> requestEntity4 = new HttpEntity<>(null, headers4);
          ResponseEntity<String> temrDoctorsAdviceResult = restTemplate.exchange(uri_to_string4, HttpMethod.GET, requestEntity4, String.class);
          System.out.println("temr_doctors_advice : " + temrDoctorsAdviceResult.getBody());

          final String baseUrl5 = Propertyconfig.getServiceUrl() + "temr_visit_prescription/getAllRecordByTimelineList";
          URI uri5 = new URI(baseUrl5);
          String uri_to_string5 = uri5.toString();
          HttpHeaders headers5 = new HttpHeaders();
          headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
          HttpEntity<String> requestEntity5 = new HttpEntity<>(null, headers5);
          ResponseEntity<String> temrVisitPrescriptionResult = restTemplate.exchange(uri_to_string5, HttpMethod.GET, requestEntity5, String.class);
          System.out.println("temr_visit_prescription : " + temrVisitPrescriptionResult.getBody());*/
        } catch (Exception e) {
            e.printStackTrace();
        }

         /* String Query = "SELECT mt.title_name AS Title,mu.user_firstname AS PatientFirstName, mu.user_lastname AS PatientLastName,mu.user_age AS Age,mg.gender_name AS Gender, ifnull(mu.user_mobile,'') AS Mobile, mp.patient_mr_no AS MrNo, CONCAT(mu1.user_firstname,' ',mu1.user_lastname) AS DocName, ifnull(cn.compound_name,'') AS DrugAllergy, ifnull(nda.nda_name,'') AS NonDrugAllergy, ifnull(cc.cc_name,'') AS PresentingComplaints, tt.timeline_date AS date, tt.timeline_date AS Datehealthspring_betahealthspring_beta, mss.service_name, mic.ic_description "
        + "FROM temr_timeline tt "
        + "INNER JOIN mst_visit mv ON mv.visit_id = tt.timeline_visit_id "
        + "INNER JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id "
        + "INNER JOIN mst_user mu ON mu.user_id = mp.patient_user_id "
        + "INNER JOIN mst_title mt ON mt.title_id = mu.user_title_id "
        + "INNER JOIN mst_gender mg ON mg.gender_id = mu.user_gender_id "
        + "INNER JOIN mbill_service s ON s.service_id = tt.timeline_service_id "
        + "INNER JOIN mst_staff ms ON ms.staff_id = tt.timeline_staff_id "
        + "INNER JOIN mst_user mu1 ON mu1.user_id = ms.staff_user_id "
        + "LEFT JOIN temr_visit_allergy va ON va.va_patient_id = tt.timeline_patient_id "
        + "LEFT JOIN temr_visit_non_drug_allergy vna ON vna.vnda_visit_id = tt.timeline_visit_id "
        + "LEFT JOIN inv_compound_name cn ON cn.compound_id = va.va_compound_id "
        + "LEFT JOIN mst_non_drug_allergy nda ON nda.nda_id = vna.vnda_nda_id "
        + "LEFT JOIN temr_visit_chief_complaint vcc ON vcc.vcc_visit_id = mv.visit_id "
        + "LEFT JOIN memr_chief_complaint cc ON cc.cc_id = vcc.vcc_cc_id "
        + "LEFT JOIN temr_visit_investigation vi ON vi.vi_timeline_id = tt.timeline_id "
        + "LEFT JOIN mbill_service mss ON mss.service_id= vi.vi_service_id "
        + "LEFT JOIN temr_visit_diagnosis vd ON vd.vd_timeline_id = tt.timeline_id  "
        + "LEFT JOIN memr_icd_code mic ON mic.ic_id= vd.vd_ic_id "
        + "WHERE (DATE(tt.timeline_date) BETWEEN '2020-12-14' AND '2020-12-16') AND tt.is_active=1 AND tt.is_deleted=0 ";

        *//*'" + todayDate + "'*//*
          System.out.println("Query:" + Query);
          List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query).getResultList();
          System.out.println("list :" +list.toString());*/

        /*for (int i = 0; i < list.size(); i++) {
          for (Object[] obj : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Title", obj[0].toString());
            jsonObject.put("PatientFirstName", obj[1].toString());
            //jsonObject.put("PatientLastName", obj[2].toString());
            jsonObject.put("age", obj[3].toString());
            jsonObject.put("Gender", obj[4].toString());
            jsonObject.put("Mobile", obj[5].toString());
            jsonObject.put("MrNo", obj[6].toString());
            jsonObject.put("DocName", obj[7].toString());
            jsonObject.put("DrugAllergy", obj[8].toString());
            jsonObject.put("NonDrugAllergy", obj[9].toString());
            jsonObject.put("PresentingComplaints", obj[10].toString());
            jsonObject.put("Datehealthspring_betahealthspring_beta", obj[11].toString());
            jsonObject.put("service_name", obj[12].toString());
            //jsonObject.put("ic_description", obj[13].toString());
            array.put(jsonObject);
          }
        }*/
        //System.out.println("array : " + array.toString());
        //return String.valueOf(array);
        return null;
    }

}  // END Class
