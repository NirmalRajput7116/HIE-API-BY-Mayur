package com.cellbeans.hspa.mis.misipdreports;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
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
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/mis_ipd_reports")
public class MisAdmissionController {

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

    @Autowired
    MstVisitRepository mstVisitRepository;

    //IPD Admission Reports Start
    @RequestMapping("searchIpdAdmission/{unitList}/{fromdate}/{todate}")
    public List<MisadmissionListDTO> searchConsoltant(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisadmissionSearchDTO misadmissionSearchDTO, @PathVariable Long[] unitList,
                                                      @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<MisadmissionListDTO> misadmissionlistDTOList = new ArrayList<MisadmissionListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query2 = " select p.patient_mr_no,ifnull(a.admission_date,'')as admission_date, " + " ifnull(a.admission_time,'')as admission_time,ifnull(a.admission_ipd_no,'') as admission_ipd_no, " + " ifnull(g.gender_name,'')as gender_name,ifnull(b.bed_name,'')as bed_name, " + " ifnull(un.unit_name,'')as unit_name,ifnull(re.re_name,'') as referalname , " + " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname,  " + " CONCAT( ifnull( su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as docname, " + " ifnull(u.user_mobile,'') as user_mobile ,ifnull(u.user_age,'')as user_age, " + " ifnull(u.user_address,'') as user_address,ifnull(a.createduser,'') as UserName, " + " ifnull(w.ward_name,'')as ward_name,a.admission_patient_id  from trn_admission a " + " left join mst_patient p on  a.admission_patient_id =p.patient_id " + " left join mst_user u on p.patient_user_id = u.user_id " + " left join mst_gender g on u.user_gender_id = g.gender_id " + " left join mst_staff s on a.admission_staff_id = s.staff_id " + " left join mst_user su on s.staff_user_id = su.user_id " + " left join mipd_bed b on a.admission_patient_bed_id = b.bed_id " + " left join mipd_ward w on b.bed_ward_id=w.ward_id " + " left join mst_unit un on a.admission_unit_id = un.unit_id " + " left join mst_referring_entity re on a.admission_re_id = re.re_id ";
        String countQuery = "select count(a.admission_id) from trn_admission a " + " left join mst_patient p on  a.admission_patient_id =p.patient_id " + " left join mst_user u on p.patient_user_id = u.user_id " + " left join mst_gender g on u.user_gender_id = g.gender_id " + " left join mst_staff s on a.admission_staff_id = s.staff_id " + " left join mst_user su on s.staff_user_id = su.user_id " + " left join mipd_bed b on a.admission_patient_bed_id = b.bed_id " + " left join mipd_ward w on b.bed_ward_id=w.ward_id " + " left join mst_unit un on a.admission_unit_id = un.unit_id " + " left join mst_referring_entity re on a.admission_re_id = re.re_id ";
        if (!misadmissionSearchDTO.getPatientName().equals("")) {
            query2 += " where (u.user_firstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";
            countQuery += " where (u.user_firstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";

        } else {
            query2 += " where (u.user_firstname like '%%' or u.user_lastname like '%%') ";
            countQuery += " where (u.user_firstname like '%%' or u.user_lastname like '%%') ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = strDate;
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misadmissionSearchDTO.getTodaydate()) {
            query2 += " and (date(a.admission_date)=curdate()) ";
            countQuery += " and (date(a.admission_date)=curdate()) ";
        } else {
            query2 += " and (date(a.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
            countQuery += " and (date(a.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!misadmissionSearchDTO.getUserId().equals("0")) {
            query2 += " and a.createduser like '%" + misadmissionSearchDTO.getUserId() + "%' ";
            countQuery += " and a.createduser like '%" + misadmissionSearchDTO.getUserId() + "%' ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getDoctorId()).equals("0")) {
            query2 += " and a.admission_staff_id=" + misadmissionSearchDTO.getDoctorId() + " ";
            countQuery += " and a.admission_staff_id=" + misadmissionSearchDTO.getDoctorId() + " ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getWardId()).equals("0")) {
            query2 += " and b.bed_ward_id=" + misadmissionSearchDTO.getWardId() + " ";
            countQuery += " and b.bed_ward_id=" + misadmissionSearchDTO.getWardId() + " ";
        }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and a.admission_unit_id in (" + values + ") ";
            countQuery += " and a.admission_unit_id in  (" + values + ") ";
        }
        if (!misadmissionSearchDTO.getMrNo().equals("")) {
            query2 += " and  p.patient_mr_no like  '%" + misadmissionSearchDTO.getMrNo() + "%' ";
            countQuery += " and  p.patient_mr_no like '%" + misadmissionSearchDTO.getMrNo() + "%' ";
        }
        if (misadmissionSearchDTO.getTariffId() != 0) {
            query2 += " and  a.admission_patient_id in ( select v.visit_patient_id from mst_visit v where v.visit_registration_source=1 and v.visit_tariff_id=" + misadmissionSearchDTO.getTariffId() + ") ";
            countQuery += " and  a.admission_patient_id in ( select v.visit_patient_id from mst_visit v where v.visit_registration_source=1 and v.visit_tariff_id=" + misadmissionSearchDTO.getTariffId() + ") ";
        }
        try {
            query2 += " limit " + ((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()) + "," + misadmissionSearchDTO.getLimit();
            System.out.println("Admission Report" + query2);
            List<Object[]> listTrnAdmissionService = entityManager.createNativeQuery(query2).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listTrnAdmissionService) {
                MisadmissionListDTO objmisadmissionlistDTO = new MisadmissionListDTO();
                objmisadmissionlistDTO.setMrNo(obj[0].toString());
                objmisadmissionlistDTO.setPatientName(obj[8].toString());
                if (obj[11] != null && !obj[11].equals("")) {
                    objmisadmissionlistDTO.setAge(Long.parseLong(obj[11].toString()));
                } else {
                    objmisadmissionlistDTO.setAge(Long.parseLong("0"));
                }
                objmisadmissionlistDTO.setGender(obj[4].toString());
                objmisadmissionlistDTO.setAdmitDate(obj[1].toString());
                objmisadmissionlistDTO.setAdmitTime(obj[2].toString());
                objmisadmissionlistDTO.setIpdNo(obj[3].toString());
                objmisadmissionlistDTO.setDoctorName(obj[9].toString());
                objmisadmissionlistDTO.setPatientAddress(obj[12].toString());
                objmisadmissionlistDTO.setMobNo(obj[10].toString());
                objmisadmissionlistDTO.setBedNo(obj[5].toString());
                objmisadmissionlistDTO.setRefDoctorName(obj[7].toString());
//                objmisadmissionlistDTO.setUserName(obj[14].toString());
                objmisadmissionlistDTO.setUnitName(obj[6].toString());
                objmisadmissionlistDTO.setWardName(obj[14].toString());
                objmisadmissionlistDTO.setUserName(obj[13].toString());
                objmisadmissionlistDTO.setPatientId(Long.parseLong(obj[15].toString()));
                objmisadmissionlistDTO.setCount(count);
                misadmissionlistDTOList.add(objmisadmissionlistDTO);
            }
            if (!String.valueOf(misadmissionSearchDTO.getTariffId()).equals("0")) {
                for (MisadmissionListDTO obj : misadmissionlistDTOList) {
                    MstVisit objVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsAndVisitTariffIdTariffIdEqualsOrderByVisitIdDesc(obj.getPatientId(), misadmissionSearchDTO.getTariffId());
                    if (objVisit != null) {
                        obj.setTariffName(objVisit.getVisitTariffId().getTariffName());
                    }
                }
            } else {
                for (MisadmissionListDTO obj : misadmissionlistDTOList) {
                    MstVisit objVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsOrderByVisitIdDesc(obj.getPatientId());
                    if (objVisit != null) {
                        obj.setTariffName(objVisit.getVisitTariffId().getTariffName());
                    }
                }
            }

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        return misadmissionlistDTOList;

    }
//IPD Admission Reports End

    @RequestMapping("currentAdmissionReport/{unitList}/{fromdate}/{todate}")
    public String searchListofgetCurrentAdmissionList(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisadmissionSearchDTO misadmissionSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        double grandTotal = 0.0;
        JSONArray resultArrayResp = new JSONArray();
        HashMap<String, List<MisadmissionListDTO>> drvisitMap = new HashMap<String, List<MisadmissionListDTO>>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = "";
        String Query1 = "select  ifnull(mp.patient_mr_no, '') as regNo, ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as PatientName, mg.gender_name, ms.staff_er_no, mu.user_age, ta.admission_date, ta.admission_ipd_no, " +
                " mb.bed_name, ifnull(concat(mu1.user_firstname,' ', mu1.user_lastname), '') as doctorName, mw.ward_name, mw.ward_id, ifnull(tia.advanced_amount, '') as advanceAmount, ifnull(mic.charge_amount_tobe_paid, '') as chargeAmount, ifnull( mc.company_name, '') as company " +
                " from trn_admission ta " +
                " left join mbill_ipd_charge mic on mic.charge_admission_id = ta.admission_id " +
                " left join trn_ipd_advanced tia on tia.ipd_admission_id = ta.admission_id " +
                " inner join mipd_bed mb on mb.bed_id = ta.admission_patient_bed_id " +
                " left join mst_patient mp on mp.patient_id = ta.admission_patient_id " +
                " left join  mst_user mu ON mu.user_id = mp.patient_user_id  " +
                " left join  mst_title mt ON mt.title_id = mu.user_title_id " +
                " left join mst_gender mg on mg.gender_id = mu.user_gender_id " +
                " left join mst_staff ms on ms.staff_id = ta.admission_staff_id " +
                " left join  mst_user mu1 ON mu1.user_id = ms.staff_user_id " +
                " left join mipd_ward mw on mw.ward_id = mb.bed_ward_id " +
                " left join trn_sponsor_combination tsc on tsc.sc_id = ta.sponsor_combination " +
                " left join mst_company mc on mc.company_id = tsc.sc_company_id " +
                " where ta.is_active =1 and ta.is_deleted = 0 and ta.admission_is_cancel = 0 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misadmissionSearchDTO.getTodaydate()) {
            Query1 += " and (date(ta.admission_date)=curdate()) ";

        } else {
            Query1 += " and (date(ta.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (misadmissionSearchDTO.getPatientName() != null && !misadmissionSearchDTO.getPatientName().equals("")) {
            Query1 += " and CONCAT(mu.user_firstname,' ',mu.user_lastname) LIKE '%" + misadmissionSearchDTO.getPatientName() + "%'";

        }
        if (misadmissionSearchDTO.getMrNo() != null && !misadmissionSearchDTO.getMrNo().equals("")) {
            Query1 += " and mp.patient_mr_no LIKE '%" + misadmissionSearchDTO.getMrNo() + "%'";

        }
        if (misadmissionSearchDTO.getDoctorName() != null && !misadmissionSearchDTO.getDoctorName().equals("")) {
            Query1 += " and concat(mu1.user_firstname,' ', mu1.user_lastname) LIKE '%" + misadmissionSearchDTO.getDoctorName() + "%'";

        }
        String CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        Query1 += " limit " + ((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()) + "," + misadmissionSearchDTO.getLimit();
        try {
            System.out.println(MainQuery);
            System.out.println("Current Admitted List Report"+Query1);
            List<Object[]> currentAdmissionListReport = entityManager.createNativeQuery(Query1).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : currentAdmissionListReport) {
                if (("" + obj[10] != null) && !("".equalsIgnoreCase(("" + obj[10])))) {
                    MisadmissionListDTO misadmissionListDTO = new MisadmissionListDTO();
                    List<MisadmissionListDTO> listOfAdmissionListDTOList = new ArrayList<MisadmissionListDTO>();
                    String key44 = "" + obj[4];
                    misadmissionListDTO.setRegNo("" + obj[0]);
                    misadmissionListDTO.setPatientName("" + obj[1]);
                    misadmissionListDTO.setGender("" + obj[2]);
                    misadmissionListDTO.setAge(Long.parseLong("" + obj[4]));
                    misadmissionListDTO.setAdmitDate("" + obj[5]);
                    misadmissionListDTO.setIpdNo("" + obj[6]);
                    misadmissionListDTO.setComSponsor("" + obj[13]);
                    misadmissionListDTO.setBedNo("" + obj[7]);
                    misadmissionListDTO.setDoctorName("" + obj[8]);
                    misadmissionListDTO.setWardName("" + obj[9]);
                    misadmissionListDTO.setAdvAmount("" + obj[11]);
                    misadmissionListDTO.setChargeAmount("" + obj[12]);
                    misadmissionListDTO.setCount(count);
                    if (drvisitMap.containsKey(key44)) {
                        listOfAdmissionListDTOList = drvisitMap.get(key44);
                        listOfAdmissionListDTOList.add(misadmissionListDTO);
                        drvisitMap.put(key44, listOfAdmissionListDTOList);
                    } else {
                        listOfAdmissionListDTOList.add(misadmissionListDTO);
                        drvisitMap.put(key44, listOfAdmissionListDTOList);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        for (Map.Entry<String, List<MisadmissionListDTO>> entry : drvisitMap.entrySet()) {
            List<MisadmissionListDTO> value = entry.getValue();
            resultArrayResp.put(value);
        }
        return resultArrayResp.toString();
    }

    //IPD Admission Reports Print Start
    @RequestMapping("searchIpdAdmissionPrint/{unitList}/{fromdate}/{todate}")
    public String searchConsoltantPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisadmissionSearchDTO misadmissionSearchDTO, @PathVariable Long[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<MisadmissionListDTO> misadmissionlistDTOList = new ArrayList<MisadmissionListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query2 = " select p.patient_mr_no,ifnull(a.admission_date,'')as admission_date, " + " ifnull(a.admission_time,'')as admission_time,ifnull(a.admission_ipd_no,'') as admission_ipd_no, " + " ifnull(g.gender_name,'')as gender_name,ifnull(b.bed_name,'')as bed_name, " + " ifnull(un.unit_name,'')as unit_name,ifnull(re.re_name,'') as referalname , " + " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname,  " + " CONCAT( ifnull( su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as docname, " + " ifnull(u.user_mobile,'') as user_mobile ,ifnull(u.user_age,'')as user_age, " + " ifnull(u.user_address,'') as user_address,ifnull(a.createduser,'') as UserName, " + " ifnull(w.ward_name,'')as ward_name,a.admission_patient_id  from trn_admission a " + " left join mst_patient p on  a.admission_patient_id =p.patient_id " + " left join mst_user u on p.patient_user_id = u.user_id " + " left join mst_gender g on u.user_gender_id = g.gender_id " + " left join mst_staff s on a.admission_staff_id = s.staff_id " + " left join mst_user su on s.staff_user_id = su.user_id " + " left join mipd_bed b on a.admission_patient_bed_id = b.bed_id " + " left join mipd_ward w on b.bed_ward_id=w.ward_id " + " left join mst_unit un on a.admission_unit_id = un.unit_id " + " left join mst_referring_entity re on a.admission_re_id = re.re_id ";
        String countQuery = "select count(a.admission_id) from trn_admission a " + " left join mst_patient p on  a.admission_patient_id =p.patient_id " + " left join mst_user u on p.patient_user_id = u.user_id " + " left join mst_gender g on u.user_gender_id = g.gender_id " + " left join mst_staff s on a.admission_staff_id = s.staff_id " + " left join mst_user su on s.staff_user_id = su.user_id " + " left join mipd_bed b on a.admission_patient_bed_id = b.bed_id " + " left join mipd_ward w on b.bed_ward_id=w.ward_id " + " left join mst_unit un on a.admission_unit_id = un.unit_id " + " left join mst_referring_entity re on a.admission_re_id = re.re_id ";
        if (!misadmissionSearchDTO.getPatientName().equals("")) {
            query2 += " where (u.user_firstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";
            countQuery += " where (u.user_firstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";

        } else {
            query2 += " where (u.user_firstname like '%%' or u.user_lastname like '%%') ";
            countQuery += " where (u.user_firstname like '%%' or u.user_lastname like '%%') ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = strDate;
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misadmissionSearchDTO.getTodaydate()) {
            query2 += " and (date(a.admission_date)=curdate()) ";
            countQuery += " and (date(a.admission_date)=curdate()) ";
        } else {
            query2 += " and (date(a.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
            countQuery += " and (date(a.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!misadmissionSearchDTO.getUserId().equals("0")) {
            query2 += " and a.createduser like '%" + misadmissionSearchDTO.getUserId() + "%' ";
            countQuery += " and a.createduser like '%" + misadmissionSearchDTO.getUserId() + "%' ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getDoctorId()).equals("0")) {
            query2 += " and a.admission_staff_id=" + misadmissionSearchDTO.getDoctorId() + " ";
            countQuery += " and a.admission_staff_id=" + misadmissionSearchDTO.getDoctorId() + " ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getWardId()).equals("0")) {
            query2 += " and b.bed_ward_id=" + misadmissionSearchDTO.getWardId() + " ";
            countQuery += " and b.bed_ward_id=" + misadmissionSearchDTO.getWardId() + " ";
        }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and a.admission_unit_id in (" + values + ") ";
            countQuery += " and a.admission_unit_id in  (" + values + ") ";
        }
        if (!misadmissionSearchDTO.getMrNo().equals("")) {
            query2 += " and  p.patient_mr_no like  '%" + misadmissionSearchDTO.getMrNo() + "%' ";
            countQuery += " and  p.patient_mr_no like '%" + misadmissionSearchDTO.getMrNo() + "%' ";
        }
        if (misadmissionSearchDTO.getTariffId() != 0) {
            query2 += " and  a.admission_patient_id in ( select v.visit_patient_id from mst_visit v where v.visit_registration_source=1 and v.visit_tariff_id=" + misadmissionSearchDTO.getTariffId() + ") ";
            countQuery += " and  a.admission_patient_id in ( select v.visit_patient_id from mst_visit v where v.visit_registration_source=1 and v.visit_tariff_id=" + misadmissionSearchDTO.getTariffId() + ") ";
        }
        JSONArray array = new JSONArray();
        try {
            //  query2 += " limit " + ((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()) + "," + misadmissionSearchDTO.getLimit();
            System.out.println("Main Query" + query2);
            List<Object[]> listTrnAdmissionService = entityManager.createNativeQuery(query2).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listTrnAdmissionService) {
                MisadmissionListDTO objmisadmissionlistDTO = new MisadmissionListDTO();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("mrNo", "" + obj[0]);
                jsonObject.put("admitDate", "" + obj[1]);
                jsonObject.put("admitTime", "" + obj[2]);
                jsonObject.put("ipdNo", "" + obj[3]);
                jsonObject.put("gender", "" + obj[4]);
                jsonObject.put("bedNo", "" + obj[5]);
                jsonObject.put("unitName", "" + obj[6]);
                jsonObject.put("refDoctorName", "" + obj[7]);
                jsonObject.put("patientName", "" + obj[8]);
                jsonObject.put("doctorName", "" + obj[9]);
                jsonObject.put("mobNo", "" + obj[10]);
                //jsonObject.put("age", "" + obj[11]);
                if (obj[11] != null && !obj[11].equals("")) {
                    jsonObject.put("age", obj[11]);
                } else {
                    jsonObject.put("age", ("0"));
                }
                jsonObject.put("patientAddress", "" + obj[12]);
                jsonObject.put("userName", "" + obj[13]);
                jsonObject.put("wardName", "" + obj[14]);
                jsonObject.put("patientId", "" + obj[15]);
                array.put(jsonObject);
            }
            if (!String.valueOf(misadmissionSearchDTO.getTariffId()).equals("0")) {
                for (int i = 0; i < array.length(); i++) {
                    MstVisit objVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsAndVisitTariffIdTariffIdEqualsOrderByVisitIdDesc(array.getJSONObject(i).getLong("patientId"), misadmissionSearchDTO.getTariffId());
                    if (objVisit != null) {
                        array.getJSONObject(i).put("tariffName", objVisit.getVisitTariffId().getTariffName());
                    }
                }
            } else {
                for (int i = 0; i < array.length(); i++) {
                    MstVisit objVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsOrderByVisitIdDesc(array.getJSONObject(i).getLong("patientId"));
                    if (objVisit != null) {
                        array.getJSONObject(i).put("tariffName", objVisit.getVisitTariffId().getTariffName());
                    }
                }
            }
        } catch (Exception e) {
        }
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misadmissionSearchDTO.getUnitId());
//            MstUser HeaderObjectUser = mstUserRepository.findbyUserID(misadmissionSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
//            JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        array.getJSONObject(0).put("objHeaderData", jsonObject);
//            array.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (misadmissionSearchDTO.getPrint()) {
            String columnName = "unitName,mrNo,patientName,age,gender,admitDate,admitTime,ipdNo,doctorName,patientAddress,mobNo,tariffName,wardName,bedNo,refDoctorName,userName,patientId";
            return createReport.generateExcel(columnName, "AdmissionReport", array);
        } else {
            return createReport.createJasperReportPDFByJson("AdmissionReport", array.toString());
        }

    }
////Print IPD Admission Reports End

    //IPD Department Wise Reports Start
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getipddeptwiseReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchDeptWiseReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisIpdDeptWiseSearchDTO misipddeptwiseSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " select ifnull(un.unit_name,'')as unit_name ,p.patient_mr_no,ifnull(a.admission_ipd_no,'') as admission_ipd_no, " +
                " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname, " +
                " ifnull(a.admission_date,'')as admission_date, ifnull(a.admission_discharge_date,'')As DischargeDate, " +
                " ifnull(d.department_name,'')as DepartmentName, " +
                " CONCAT( ifnull( su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as docname, " +
                " ifnull(re.re_name,'') as referalname ,ifnull(dy.dt_name,'')as DischargeType, " +
                " CONCAT(FLOOR(HOUR(TIMEDIFF(a.admission_date,  ifNull(a.admission_discharge_date,now()))) / 24), ' days ', " +
                " MOD(HOUR(TIMEDIFF(a.admission_date, ifNull(a.admission_discharge_date,now()))), 24), ' hours ', " +
                " MINUTE(TIMEDIFF(a.admission_date, ifNull(a.admission_discharge_date,now()))), ' minutes') as ALOS, " +
                " Type_patient.pt_name,Type_patient.tariff_name " +
                " from trn_admission a " +
                " left join mst_patient p on  a.admission_patient_id =p.patient_id " +
                " inner join mst_user u on p.patient_user_id = u.user_id " +
                " left join mst_staff s on a.admission_staff_id = s.staff_id " +
                " inner join mst_user su on s.staff_user_id = su.user_id " +
                " left join mst_unit un on a.admission_unit_id = un.unit_id " +
                " left join mst_referring_entity re on a.admission_re_id = re.re_id " +
                " left join mst_department d on a.admission_department_id=d.department_id " +
                " inner join trn_discharge dc on dc.discharge_admission_id=a.admission_patient_id " +
                " inner join mipd_discharge_type dy on dc.discharge_type=dy.dt_id " +
                " inner join " +
                " (select visit_patient_id,t.tariff_name,admission_patient_id,pt_name from trn_admission a " +
                " inner join mst_visit v on v.visit_patient_id = a.admission_patient_id " +
                " inner join mst_patient_type pt on pt.pt_id  = v.patient_type, " +
                " mbill_tariff t " +
                " where  " +
                " v.visit_id in " +
                " (select MAX(visit_id)  from mst_visit " +
                " where visit_patient_id in (select admission_patient_id from trn_admission) group by visit_patient_id) and v.visit_tariff_id=t.tariff_id ) as Type_patient " +
                " on a.admission_patient_id=Type_patient.visit_patient_id ";
        String CountQuery = "";
        String columnName = "";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " where a.admission_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("null") || fromdate.equals("")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("null") || todate.equals("")) {
            todate = strDate;
        }
        if (misipddeptwiseSearchDTO.getTodaydate()) {
            Query += " and (date(a.admission_date)=curdate()) ";
        } else {
            Query += " and (date(a.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!misipddeptwiseSearchDTO.getStaffId().equals("null") && !misipddeptwiseSearchDTO.getStaffId().equals("0")) {
            Query += " and s.staff_user_id=" + misipddeptwiseSearchDTO.getStaffId() + " ";
        }
        if (!misipddeptwiseSearchDTO.getDeptId().equals("0")) {
            Query += " and a.admission_department_id =  " + misipddeptwiseSearchDTO.getDeptId() + " ";
        }
        if (!misipddeptwiseSearchDTO.getDtId().equals("null") && !misipddeptwiseSearchDTO.getDtId().equals("0")) {
            Query += " and dc.discharge_type =  " + misipddeptwiseSearchDTO.getDtId() + " ";
        }
        if (!misipddeptwiseSearchDTO.getMrNo().equals("")) {
            Query += " and  p.patient_mr_no like  '%" + misipddeptwiseSearchDTO.getMrNo() + "%' ";
        }
        if (!misipddeptwiseSearchDTO.getPatientName().equals("")) {
            Query += " and (u.user_firstname like '%" + misipddeptwiseSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + misipddeptwiseSearchDTO.getPatientName() + "%') ";

        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,patient_mr_no,admission_ipd_no,patientname,admission_date,DischargeDate,DepartmentName,docname,referalname,DischargeType,ALOS,pt_name,tariff_name";
        Query += " limit " + ((misipddeptwiseSearchDTO.getOffset() - 1) * misipddeptwiseSearchDTO.getLimit()) + "," + misipddeptwiseSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }
//IPD Department Wise Reports End

    //IPD Department Wise Reports Print Start
    @RequestMapping("getipddeptwiseReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchDeptWiseReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisIpdDeptWiseSearchDTO misipddeptwiseSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " select ifnull(un.unit_name,'')as unit_name ,p.patient_mr_no,ifnull(a.admission_ipd_no,'') as admission_ipd_no, " +
                " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname, " +
                " ifnull(a.admission_date,'')as admission_date, ifnull(a.admission_discharge_date,'')As DischargeDate, " +
                " ifnull(d.department_name,'')as DepartmentName, " +
                " CONCAT( ifnull( su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as docname, " +
                " ifnull(re.re_name,'') as referalname ,ifnull(dy.dt_name,'')as DischargeType, " +
                " CONCAT(FLOOR(HOUR(TIMEDIFF(a.admission_date,  ifNull(a.admission_discharge_date,now()))) / 24), ' days ', " +
                " MOD(HOUR(TIMEDIFF(a.admission_date, ifNull(a.admission_discharge_date,now()))), 24), ' hours ', " +
                " MINUTE(TIMEDIFF(a.admission_date, ifNull(a.admission_discharge_date,now()))), ' minutes') as ALOS, " +
                " Type_patient.pt_name,Type_patient.tariff_name " +
                " from trn_admission a " +
                " left join mst_patient p on  a.admission_patient_id =p.patient_id " +
                " inner join mst_user u on p.patient_user_id = u.user_id " +
                " left join mst_staff s on a.admission_staff_id = s.staff_id " +
                " inner join mst_user su on s.staff_user_id = su.user_id " +
                " left join mst_unit un on a.admission_unit_id = un.unit_id " +
                " left join mst_referring_entity re on a.admission_re_id = re.re_id " +
                " left join mst_department d on a.admission_department_id=d.department_id " +
                " inner join trn_discharge dc on dc.discharge_admission_id=a.admission_patient_id " +
                " inner join mipd_discharge_type dy on dc.discharge_type=dy.dt_id " +
                " inner join " +
                " (select visit_patient_id,t.tariff_name,admission_patient_id,pt_name from trn_admission a " +
                " inner join mst_visit v on v.visit_patient_id = a.admission_patient_id " +
                " inner join mst_patient_type pt on pt.pt_id  = v.patient_type, " +
                " mbill_tariff t " +
                " where  " +
                " v.visit_id in " +
                " (select MAX(visit_id)  from mst_visit " +
                " where visit_patient_id in (select admission_patient_id from trn_admission) group by visit_patient_id) and v.visit_tariff_id=t.tariff_id ) as Type_patient " +
                " on a.admission_patient_id=Type_patient.visit_patient_id ";
        String CountQuery = "";
        String columnName = "";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " where a.admission_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("null") || fromdate.equals("")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("null") || todate.equals("")) {
            todate = strDate;
        }
        if (misipddeptwiseSearchDTO.getTodaydate()) {
            Query += " and (date(a.admission_date)=curdate()) ";
        } else {
            Query += " and (date(a.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!misipddeptwiseSearchDTO.getStaffId().equals("null") && !misipddeptwiseSearchDTO.getStaffId().equals("0")) {
            Query += " and s.staff_user_id=" + misipddeptwiseSearchDTO.getStaffId() + " ";
        }
        if (!misipddeptwiseSearchDTO.getDeptId().equals("0")) {
            Query += " and a.admission_department_id =  " + misipddeptwiseSearchDTO.getDeptId() + " ";
        }
        if (!misipddeptwiseSearchDTO.getDtId().equals("null") && !misipddeptwiseSearchDTO.getDtId().equals("0")) {
            Query += " and dc.discharge_type =  " + misipddeptwiseSearchDTO.getDtId() + " ";
        }
        if (!misipddeptwiseSearchDTO.getMrNo().equals("")) {
            Query += " and  p.patient_mr_no like  '%" + misipddeptwiseSearchDTO.getMrNo() + "%' ";
        }
        if (!misipddeptwiseSearchDTO.getPatientName().equals("")) {
            Query += " and (u.user_firstname like '%" + misipddeptwiseSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + misipddeptwiseSearchDTO.getPatientName() + "%') ";

        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,patient_mr_no,admission_ipd_no,patientname,admission_date,DischargeDate,DepartmentName,docname,referalname,DischargeType,ALOS,pt_name,tariff_name";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misipddeptwiseSearchDTO.getUnitId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        // return createReport.createJasperReportPDFByJson("IpdDepartmentWiseReport", jsonArray.toString());
        if (misipddeptwiseSearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("IpdDepartmentWiseReport", jsonArray.toString());
        } else {
            return createReport.createJasperReportPDFByJson("IpdDepartmentWiseReport", jsonArray.toString());
        }

    }
//IPD Department Wise Reports Print End

    //IPD-OPD Pharmacy Detail Bill Reports Start
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getipdopdpharmdetailReport/{unitList}/{clclId}/{fromdate}/{todate}/{IPDFlag}")
    public ResponseEntity searchPharmDetailReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisOpdIpdPharmDetailReportSearchDTO misopdipdpharmDetailReportSearchDTO, @PathVariable String[] unitList, @PathVariable String clclId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "  ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query = " SELECT ps.ps_id ,u.unit_name,ps.created_date as PDate,'', p.patient_mr_no,ps.ps_id as PharmBillNo,pr.pbr_ps_id as PharmRecieptNo, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " ps.ps_co_pay_from_tarrif as NetCompanyPay,ps_concsssion_amount as Discount,ps_tax_amount as TaxAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif as NetPatientPay,pr.cash as PaymentAmount,ifnull(pm.pm_name,'')as PaymentMode, " +
                    " ps.ps_out_standing_amount_for_patient as OSA, " +
                    " (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)as AmountCollected " +
                    " FROM tinv_pharmacy_sale ps " +
                    " inner join tinv_pharmacy_bill_recepit pr on pr.pbr_ps_id=ps.ps_id " +
                    " inner join mst_payment_mode pm on pr.pbr_pm_id=pm.pm_id " +
                    " inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id " +
                    " inner join mst_visit a on ps.ps_visit_id=a.visit_id " +
                    " inner join mst_patient p on a.visit_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id where ps.ps_admission_id is null and ps.pharmacy_type=" + IPDFlag;
        } else {
            Query = " SELECT ps.ps_id,u.unit_name,ps.created_date as PDate,a.admission_ipd_no, p.patient_mr_no,ps.ps_id as PharmBillNo,pr.pbr_ps_id as PharmRecieptNo, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " ps.ps_co_pay_from_tarrif as NetCompanyPay,ps_concsssion_amount as Discount,ps_tax_amount as TaxAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif as NetPatientPay,pr.cash as PaymentAmount,ifnull(pm.pm_name,'')as PaymentMode, " +
                    " ps.ps_out_standing_amount_for_patient as OSA, " +
                    " (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)as AmountCollected " +
                    " FROM tinv_pharmacy_sale ps " +
                    " inner join tinv_pharmacy_bill_recepit pr on pr.pbr_ps_id=ps.ps_id " +
                    " inner join mst_payment_mode pm on pr.pbr_pm_id=pm.pm_id " +
                    " inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id " +
                    " inner join trn_admission a on ps.ps_admission_id=a.admission_id " +
                    " inner join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id where ps.pharmacy_type=" + IPDFlag;
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and ps.pharmacy_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("null") || fromdate.equals("")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("null") || todate.equals("")) {
            todate = strDate;
        }
        if (misopdipdpharmDetailReportSearchDTO.getTodaydate()) {
            Query += " and (date(ps.created_date)=curdate()) ";
        } else {
            Query += " and (date(ps.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!misopdipdpharmDetailReportSearchDTO.getPatientName().equals("")) {
            Query += " and (us.user_firstname like '%" + misopdipdpharmDetailReportSearchDTO.getPatientName() + "%' or us.user_lastname like '%" + misopdipdpharmDetailReportSearchDTO.getPatientName() + "%') ";

        }
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query += " and (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)!=0 and  ps.ps_out_standing_amount_for_patient=0 ";
            } else if (clclId.equals("2")) {
                Query += " and (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)!=0 and ps.ps_out_standing_amount_for_patient!=0 ";
            } else {
                Query += " and (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)=0 and ps.ps_out_standing_amount_for_patient!=0 ";
            }
        }

        System.out.println("OPD-IPD Pharmacy Detail Bill Report"+Query);
        CountQuery = " select count(*) from ( " + Query + " group by ps.ps_id ) as combine ";
        columnName = "ps_id,unit_name,PDate,admission_ipd_no,patient_mr_no,PharmBillNo,PharmRecieptNo,patientname,NetCompanyPay,Discount,TaxAmount,NetPatientPay,PaymentAmount,PaymentMode,OSA,AmountCollected";
        Query += " group by ps.ps_id limit " + ((misopdipdpharmDetailReportSearchDTO.getOffset() - 1) * misopdipdpharmDetailReportSearchDTO.getLimit()) + "," + misopdipdpharmDetailReportSearchDTO.getLimit();
//        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        for (int j = 0; j < jsonArray.length(); j++) {
            if (!jsonArray.getJSONObject(j).get("PaymentMode").toString().equals("")) {
                if (jsonArray.getJSONObject(j).get("ps_id") != null) {
                    String Query5 = " SELECT pr.pbr_pm_id,sum(pr.cash) " +
                            " FROM tinv_pharmacy_bill_recepit pr where pbr_ps_id =" + jsonArray.getJSONObject(j).get("ps_id") + " group by  pr.pbr_pm_id";
                    List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query5).getResultList();
                    if (list.size() > 0) {
                        for (Object[] obj : list) {
                            if (obj[0] != null) {
                                if (obj[0].toString().equals("1")) {
                                    jsonArray.getJSONObject(j).put("Cash", obj[1].toString());
                                } else if (obj[0].toString().equals("2")) {
                                    jsonArray.getJSONObject(j).put("Cheque", obj[1].toString());
                                } else if (obj[0].toString().equals("3")) {
                                    jsonArray.getJSONObject(j).put("Card", obj[1].toString());
                                } else if (obj[0].toString().equals("4")) {
                                    jsonArray.getJSONObject(j).put("Transfer", obj[1].toString());
                                } else if (obj[0].toString().equals("5")) {
                                    jsonArray.getJSONObject(j).put("DebitCard", obj[1].toString());
                                } else if (obj[0].toString().equals("6")) {
                                    jsonArray.getJSONObject(j).put("Credit", obj[1].toString());
                                }
                            }
                        }
                    }
                }
            }

        }
        return ResponseEntity.ok(jsonArray.toString());

    }
    //IPD-OPD Pharmacy Detail Bill Reports End

    //IPD-OPD Pharmacy Detail Bill Reports Print Start
    @RequestMapping("getipdopdpharmdetailReportPrint/{unitList}/{clclId}/{fromdate}/{todate}/{IPDFlag}")
    public String searchPharmDetailReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisOpdIpdPharmDetailReportSearchDTO misopdipdpharmDetailReportSearchDTO, @PathVariable String[] unitList, @PathVariable String clclId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "  ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query = " SELECT ps.ps_id,u.unit_name,ps.created_date as PDate,'', p.patient_mr_no,ps.ps_id as PharmBillNo,pr.pbr_ps_id as PharmRecieptNo, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " ps.ps_co_pay_from_tarrif as NetCompanyPay,ps_concsssion_amount as Discount,ps_tax_amount as TaxAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif as NetPatientPay,pr.cash as PaymentAmount,ifnull(pm.pm_name,'')as PaymentMode, " +
                    " ps.ps_out_standing_amount_for_patient as OSA, " +
                    " (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)as AmountCollected " +
                    " FROM tinv_pharmacy_sale ps " +
                    " inner join tinv_pharmacy_bill_recepit pr on pr.pbr_ps_id=ps.ps_id " +
                    " inner join mst_payment_mode pm on pr.pbr_pm_id=pm.pm_id " +
                    " inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id " +
                    " inner join mst_visit a on ps.ps_visit_id=a.visit_id " +
                    " inner join mst_patient p on a.visit_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id where ps.ps_admission_id is null and ps.pharmacy_type=" + IPDFlag;
        } else {
            Query = " SELECT ps.ps_id,u.unit_name,ps.created_date as PDate,a.admission_ipd_no, p.patient_mr_no,ps.ps_id as PharmBillNo,pr.pbr_ps_id as PharmRecieptNo, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " ps.ps_co_pay_from_tarrif as NetCompanyPay,ps_concsssion_amount as Discount,ps_tax_amount as TaxAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif as NetPatientPay,pr.cash as PaymentAmount,ifnull(pm.pm_name,'')as PaymentMode, " +
                    " ps.ps_out_standing_amount_for_patient as OSA, " +
                    " (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)as AmountCollected " +
                    " FROM tinv_pharmacy_sale ps " +
                    " inner join tinv_pharmacy_bill_recepit pr on pr.pbr_ps_id=ps.ps_id " +
                    " inner join mst_payment_mode pm on pr.pbr_pm_id=pm.pm_id " +
                    " inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id " +
                    " inner join trn_admission a on ps.ps_admission_id=a.admission_id " +
                    " inner join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id where ps.pharmacy_type=" + IPDFlag;
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and ps.pharmacy_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("null") || fromdate.equals("")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("null") || todate.equals("")) {
            todate = strDate;
        }
        if (misopdipdpharmDetailReportSearchDTO.getTodaydate()) {
            Query += " and (date(ps.created_date)=curdate()) ";
        } else {
            Query += " and (date(ps.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!misopdipdpharmDetailReportSearchDTO.getPatientName().equals("")) {
            Query += " and (ps.ps_patient_first_name like '%" + misopdipdpharmDetailReportSearchDTO.getPatientName() + "%' or ps.ps_patient_last_name like '%" + misopdipdpharmDetailReportSearchDTO.getPatientName() + "%') ";

        }
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query += " and (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)!=0 and  ps.ps_out_standing_amount_for_patient=0 ";
            } else if (clclId.equals("2")) {
                Query += " and (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)!=0 and ps.ps_out_standing_amount_for_patient!=0 ";
            } else {
                Query += " and (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)=0 and ps.ps_out_standing_amount_for_patient!=0 ";
            }
        }
        CountQuery = " select count(*) from ( " + Query + " group by ps.ps_id ) as combine ";
        columnName = "ps_id,unit_name,PDate,admission_ipd_no,patient_mr_no,PharmBillNo,PharmRecieptNo,patientname,NetCompanyPay,Discount,TaxAmount,NetPatientPay,PaymentAmount,PaymentMode,OSA,AmountCollected";
        Query += " group by ps.ps_id ";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        //   JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        for (int j = 0; j < jsonArray.length(); j++) {
            if (!jsonArray.getJSONObject(j).get("PaymentMode").toString().equals("")) {
                if (jsonArray.getJSONObject(j).get("ps_id") != null) {
                    String Query5 = " SELECT pr.pbr_pm_id,sum(pr.cash) " +
                            " FROM tinv_pharmacy_bill_recepit pr where pbr_ps_id =" + jsonArray.getJSONObject(j).get("ps_id") + " group by  pr.pbr_pm_id";
                    List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query5).getResultList();
                    if (list.size() > 0) {
                        for (Object[] obj : list) {
                            if (obj[0] != null) {
                                if (obj[0].toString().equals("1")) {
                                    jsonArray.getJSONObject(j).put("Cash", obj[1].toString());
                                } else if (obj[0].toString().equals("2")) {
                                    jsonArray.getJSONObject(j).put("Cheque", obj[1].toString());
                                } else if (obj[0].toString().equals("3")) {
                                    jsonArray.getJSONObject(j).put("Card", obj[1].toString());
                                } else if (obj[0].toString().equals("4")) {
                                    jsonArray.getJSONObject(j).put("Transfer", obj[1].toString());
                                } else if (obj[0].toString().equals("5")) {
                                    jsonArray.getJSONObject(j).put("DebitCard", obj[1].toString());
                                } else if (obj[0].toString().equals("6")) {
                                    jsonArray.getJSONObject(j).put("Credit", obj[1].toString());
                                }
                            }
                        }
                    }
                }
            }

        }
        // return ResponseEntity.ok(jsonArray.toString());
//        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misopdipdpharmDetailReportSearchDTO.getUnitId());
//        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
//        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
//        return createReport.createJasperReportPDFByJson("IpdOpdPharmacyDetailReport", jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misopdipdpharmDetailReportSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(Long.parseLong(misopdipdpharmDetailReportSearchDTO.getUserId()));
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (misopdipdpharmDetailReportSearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("IpdOpdPharmacyDetailReport", jsonArray.toString());
        } else {
            return createReport.createJasperReportPDFByJson("IpdOpdPharmacyDetailReport", jsonArray.toString());
        }
    }

    //IPD-OPD Pharmacy Detail Bill Reports PrintAll Start - recentally one month data generated excel and then excel attach to mail body send it mail auto-matically.
    @GetMapping("getipdopdpharmdetailReportPrintAll/{fromdate}/{todate}")
    public String searchPharmDetailReportPrintAll(@RequestHeader("X-tenantId") String tenantName, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "  ";
        String Query1 = "  ";
        String Query2 = "  ";
        String CountQuery = "";
        String columnName = "";
        Query = " SELECT ps.ps_id,u.unit_name,ps.created_date as PDate,'', p.patient_mr_no,ps.ps_id as PharmBillNo,pr.pbr_ps_id as PharmRecieptNo, " +
                " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                " ps.ps_co_pay_from_tarrif as NetCompanyPay,ps_concsssion_amount as Discount,ps_tax_amount as TaxAmount, " +
                " ps.ps_patient_pay_from_from_tarrif as NetPatientPay,pr.cash as PaymentAmount,ifnull(pm.pm_name,'')as PaymentMode, " +
                " ps.ps_out_standing_amount_for_patient as OSA, " +
                " (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)as AmountCollected " +
                " FROM tinv_pharmacy_sale ps " +
                " inner join tinv_pharmacy_bill_recepit pr on pr.pbr_ps_id=ps.ps_id " +
                " inner join mst_payment_mode pm on pr.pbr_pm_id=pm.pm_id " +
                " inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id " +
                " inner join mst_visit a on ps.ps_visit_id=a.visit_id " +
                " inner join mst_patient p on a.visit_patient_id=p.patient_id " +
                " inner join mst_user us on p.patient_user_id=us.user_id where ps.ps_admission_id is null and ps.pharmacy_type=0 and (date(ps.created_date) between '" + fromdate + "' and '" + todate + "'  ) ";
        Query1 = " SELECT ps.ps_id,u.unit_name,ps.created_date as PDate,a.admission_ipd_no, p.patient_mr_no,ps.ps_id as PharmBillNo,pr.pbr_ps_id as PharmRecieptNo, " +
                " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                " ps.ps_co_pay_from_tarrif as NetCompanyPay,ps_concsssion_amount as Discount,ps_tax_amount as TaxAmount, " +
                " ps.ps_patient_pay_from_from_tarrif as NetPatientPay,pr.cash as PaymentAmount,ifnull(pm.pm_name,'')as PaymentMode, " +
                " ps.ps_out_standing_amount_for_patient as OSA, " +
                " (ps.ps_patient_pay_from_from_tarrif-ps.ps_out_standing_amount_for_patient)as AmountCollected " +
                " FROM tinv_pharmacy_sale ps " +
                " inner join tinv_pharmacy_bill_recepit pr on pr.pbr_ps_id=ps.ps_id " +
                " inner join mst_payment_mode pm on pr.pbr_pm_id=pm.pm_id " +
                " inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id " +
                " inner join trn_admission a on ps.ps_admission_id=a.admission_id " +
                " inner join mst_patient p on a.admission_patient_id=p.patient_id " +
                " inner join mst_user us on p.patient_user_id=us.user_id where ps.pharmacy_type=1 and (date(ps.created_date) between '" + fromdate + "' and '" + todate + "'  ) ";
        Query2 = Query + " union all " + Query1;
        System.out.println("Query2 : " + Query2);
        CountQuery = " select count(*) from ( " + Query + " group by ps.ps_id ) as combine ";
        columnName = "ps_id,unit_name,PDate,admission_ipd_no,patient_mr_no,PharmBillNo,PharmRecieptNo,patientname,NetCompanyPay,Discount,TaxAmount,NetPatientPay,PaymentAmount,PaymentMode,OSA,AmountCollected";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query2, CountQuery));
        //JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        for (int j = 0; j < jsonArray.length(); j++) {
            if (!jsonArray.getJSONObject(j).get("PaymentMode").toString().equals("")) {
                if (jsonArray.getJSONObject(j).get("ps_id") != null) {
                    String Query5 = " SELECT pr.pbr_pm_id,sum(pr.cash) " +
                            " FROM tinv_pharmacy_bill_recepit pr where pbr_ps_id =" + jsonArray.getJSONObject(j).get("ps_id") + " group by  pr.pbr_pm_id";
                    List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query5).getResultList();
                    if (list.size() > 0) {
                        for (Object[] obj : list) {
                            if (obj[0] != null) {
                                if (obj[0].toString().equals("1")) {
                                    jsonArray.getJSONObject(j).put("Cash", obj[1].toString());
                                } else if (obj[0].toString().equals("2")) {
                                    jsonArray.getJSONObject(j).put("Cheque", obj[1].toString());
                                } else if (obj[0].toString().equals("3")) {
                                    jsonArray.getJSONObject(j).put("Card", obj[1].toString());
                                } else if (obj[0].toString().equals("4")) {
                                    jsonArray.getJSONObject(j).put("Transfer", obj[1].toString());
                                } else if (obj[0].toString().equals("5")) {
                                    jsonArray.getJSONObject(j).put("DebitCard", obj[1].toString());
                                } else if (obj[0].toString().equals("6")) {
                                    jsonArray.getJSONObject(j).put("Credit", obj[1].toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        return createReport.generateExcel("ps_id,unit_name,PDate,admission_ipd_no,patient_mr_no,PharmBillNo,PharmRecieptNo,patientname,NetCompanyPay,Discount,TaxAmount,NetPatientPay,PaymentAmount,PaymentMode,Cash,Cheque,Card,Transfer,DebitCard,Credit,OSA,AmountCollected", "IpdOpdPharmacyDetailReport", jsonArray);
    }

    //IPD-OPD Return Pharmacy Detail Bill Reports Start
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getipdopdreturnpharmdetailReport/{unitList}/{clclId}/{fromdate}/{todate}/{IPDFlag}")
    public ResponseEntity searchReturnPharmDetailReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisOpdIpdReturnPharmDetailReportSearchDTO misopdipdreturnPharmDetailReportSearchDTO, @PathVariable String[] unitList, @PathVariable String clclId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "  ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query = " SELECT ps.ps_id,u.unit_name,sr.created_date as RDate,p.patient_mr_no,'',ps.ps_id as PharmBillNo,sr.isr_id as RecieptNo, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " ps.ps_co_pay_from_tarrif as NetCompanyPay,ps_concsssion_amount as Discount,ps_tax_amount as TaxAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif as NetPatientPay,ifnull(sr.isr_total_amount,'')as ReturnAmount, " +
                    " ifnull(m.pm_name,'')as PaymentMode,ifnull(rr.cash,'')as PaymentAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif-sr.isr_total_amount as OSA " +
                    " FROM tinv_item_sales_return sr " +
                    " inner join tinv_pharmacy_sale ps on sr.isr_ps_id=ps.ps_id " +
                    " inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id  " +
                    " inner join mst_visit a on ps.ps_visit_id=a.visit_id  " +
                    " inner join mst_patient p on a.visit_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id  " +
                    " left join tinv_pharmacy_bill_return_recepit rr on rr.pbrr_ps_id=ps.ps_id " +
                    " inner join mst_payment_mode m on rr.pbrr_pm_id=m.pm_id where ps.ps_admission_id is null and ps.pharmacy_type=" + IPDFlag;
        } else {
            Query = " SELECT ps.ps_id,u.unit_name,sr.created_date as RDate,p.patient_mr_no,a.admission_ipd_no,ps.ps_id as PharmBillNo, " +
                    " sr.isr_id as RecieptNo, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " ps.ps_co_pay_from_tarrif as NetCompanyPay,ps_concsssion_amount as Discount,ps_tax_amount as TaxAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif as NetPatientPay,ifnull(sr.isr_total_amount,'')as ReturnAmount, " +
                    " ifnull(m.pm_name,'')as PaymentMode,ifnull(rr.cash,'') as PaymentAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif-sr.isr_total_amount as OSA " +
                    " FROM tinv_item_sales_return sr " +
                    " inner join tinv_pharmacy_sale ps on sr.isr_ps_id=ps.ps_id " +
                    " inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id " +
                    " inner join trn_admission a on ps.ps_admission_id=a.admission_id " +
                    " inner join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id " +
                    " left join tinv_pharmacy_bill_return_recepit rr on rr.pbrr_ps_id=ps.ps_id " +
                    " inner join mst_payment_mode m on rr.pbrr_pm_id=m.pm_id where ps.pharmacy_type=" + IPDFlag;
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and ps.pharmacy_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("null") || fromdate.equals("")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("null") || todate.equals("")) {
            todate = strDate;
        }
        if (misopdipdreturnPharmDetailReportSearchDTO.getTodaydate()) {
            Query += " and (date(sr.created_date)=curdate()) ";
        } else {
            Query += " and (date(sr.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!misopdipdreturnPharmDetailReportSearchDTO.getPatientName().equals("")) {
            Query += " and (us.user_firstname like '%" + misopdipdreturnPharmDetailReportSearchDTO.getPatientName() + "%' or us.user_lastname like '%" + misopdipdreturnPharmDetailReportSearchDTO.getPatientName() + "%') ";
        }
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query += " and sr.isr_total_amount!=0 and (ps.ps_patient_pay_from_from_tarrif-sr.isr_total_amount)=0 ";
            } else if (clclId.equals("2")) {
                Query += " and sr.isr_total_amount!=0 and (ps.ps_patient_pay_from_from_tarrif-sr.isr_total_amount)=!0 ";
            } else {
                Query += " and sr.isr_total_amount=0 and (ps.ps_patient_pay_from_from_tarrif-sr.isr_total_amount)=!0 ";
            }
        }

        System.out.println("OPD-IPD Return Pharmacy Detail Bill Reports"+Query);
        CountQuery = " select count(*) from ( " + Query + " group by ps.ps_id  ) as combine ";
        columnName = "ps_id,unit_name,RDate,patient_mr_no,admission_ipd_no,PharmBillNo,RecieptNo,patientname,NetCompanyPay,Discount,TaxAmount,NetPatientPay,ReturnAmount,PaymentMode,PaymentAmount,OSA";
        Query += "group by ps.ps_id  limit " + ((misopdipdreturnPharmDetailReportSearchDTO.getOffset() - 1) * misopdipdreturnPharmDetailReportSearchDTO.getLimit()) + "," + misopdipdreturnPharmDetailReportSearchDTO.getLimit();
//        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        for (int j = 0; j < jsonArray.length(); j++) {
            if (!jsonArray.getJSONObject(j).get("PaymentMode").toString().equals("")) {
                if (jsonArray.getJSONObject(j).get("ps_id") != null) {
                    String Query5 = " SELECT rr.pbrr_pm_id,sum(rr.cash) " +
                            " FROM tinv_pharmacy_bill_return_recepit rr where pbrr_ps_id =" + jsonArray.getJSONObject(j).get("ps_id") + " group by  rr.pbrr_pm_id";
                    List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query5).getResultList();
                    if (list.size() > 0) {
                        for (Object[] obj : list) {
                            if (obj[0] != null) {
                                if (obj[0].toString().equals("1")) {
                                    jsonArray.getJSONObject(j).put("Cash", obj[1].toString());
                                } else if (obj[0].toString().equals("2")) {
                                    jsonArray.getJSONObject(j).put("Cheque", obj[1].toString());
                                } else if (obj[0].toString().equals("3")) {
                                    jsonArray.getJSONObject(j).put("Card", obj[1].toString());
                                } else if (obj[0].toString().equals("4")) {
                                    jsonArray.getJSONObject(j).put("Transfer", obj[1].toString());
                                } else if (obj[0].toString().equals("5")) {
                                    jsonArray.getJSONObject(j).put("DebitCard", obj[1].toString());
                                } else if (obj[0].toString().equals("6")) {
                                    jsonArray.getJSONObject(j).put("Credit", obj[1].toString());
                                }
                            }
                        }
                    }
                }
            }

        }
        return ResponseEntity.ok(jsonArray.toString());
    }
    //IPD-OPD Return Pharmacy Detail Bill Reports End

    @RequestMapping("getipdopdreturnpharmdetailReportPrint/{unitList}/{clclId}/{fromdate}/{todate}/{IPDFlag}")
    public String searchReturnPharmDetailReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisOpdIpdReturnPharmDetailReportSearchDTO misopdipdreturnPharmDetailReportSearchDTO, @PathVariable String[] unitList, @PathVariable String clclId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "  ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query = " SELECT ps.ps_id,u.unit_name,sr.created_date as RDate,p.patient_mr_no,'',ps.ps_id as PharmBillNo,sr.isr_id as RecieptNo, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " ps.ps_co_pay_from_tarrif as NetCompanyPay,ps_concsssion_amount as Discount,ps_tax_amount as TaxAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif as NetPatientPay,ifnull(sr.isr_total_amount,'')as ReturnAmount, " +
                    " ifnull(m.pm_name,'')as PaymentMode,ifnull(rr.cash,'')as PaymentAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif-sr.isr_total_amount as OSA " +
                    " FROM tinv_item_sales_return sr " +
                    " inner join tinv_pharmacy_sale ps on sr.isr_ps_id=ps.ps_id " +
                    " inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id  " +
                    " inner join mst_visit a on ps.ps_visit_id=a.visit_id  " +
                    " inner join mst_patient p on a.visit_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id  " +
                    " left join tinv_pharmacy_bill_return_recepit rr on rr.pbrr_ps_id=ps.ps_id " +
                    " inner join mst_payment_mode m on rr.pbrr_pm_id=m.pm_id where ps.ps_admission_id is null and ps.pharmacy_type=" + IPDFlag;
        } else {
            Query = " SELECT ps.ps_id,u.unit_name,sr.created_date as RDate,p.patient_mr_no,a.admission_ipd_no,ps.ps_id as PharmBillNo, " +
                    " sr.isr_id as RecieptNo, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " ps.ps_co_pay_from_tarrif as NetCompanyPay,ps_concsssion_amount as Discount,ps_tax_amount as TaxAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif as NetPatientPay,ifnull(sr.isr_total_amount,'')as ReturnAmount, " +
                    " ifnull(m.pm_name,'')as PaymentMode,ifnull(rr.cash,'') as PaymentAmount, " +
                    " ps.ps_patient_pay_from_from_tarrif-sr.isr_total_amount as OSA " +
                    " FROM tinv_item_sales_return sr " +
                    " inner join tinv_pharmacy_sale ps on sr.isr_ps_id=ps.ps_id " +
                    " inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id " +
                    " inner join trn_admission a on ps.ps_admission_id=a.admission_id " +
                    " inner join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id " +
                    " left join tinv_pharmacy_bill_return_recepit rr on rr.pbrr_ps_id=ps.ps_id " +
                    " inner join mst_payment_mode m on rr.pbrr_pm_id=m.pm_id where ps.pharmacy_type=" + IPDFlag;
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and ps.pharmacy_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("null") || fromdate.equals("")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("null") || todate.equals("")) {
            todate = strDate;
        }
        if (misopdipdreturnPharmDetailReportSearchDTO.getTodaydate()) {
            Query += " and (date(sr.created_date)=curdate()) ";
        } else {
            Query += " and (date(sr.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!misopdipdreturnPharmDetailReportSearchDTO.getPatientName().equals("")) {
            Query += " and (us.user_firstname like '%" + misopdipdreturnPharmDetailReportSearchDTO.getPatientName() + "%' or us.user_lastname like '%" + misopdipdreturnPharmDetailReportSearchDTO.getPatientName() + "%') ";
        }
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query += " and sr.isr_total_amount!=0 and (ps.ps_patient_pay_from_from_tarrif-sr.isr_total_amount)=0 ";
            } else if (clclId.equals("2")) {
                Query += " and sr.isr_total_amount!=0 and (ps.ps_patient_pay_from_from_tarrif-sr.isr_total_amount)=!0 ";
            } else {
                Query += " and sr.isr_total_amount=0 and (ps.ps_patient_pay_from_from_tarrif-sr.isr_total_amount)=!0 ";
            }
        }
        CountQuery = " select count(*) from ( " + Query + " group by ps.ps_id  ) as combine ";
        columnName = "ps_id,unit_name,RDate,patient_mr_no,admission_ipd_no,PharmBillNo,RecieptNo,patientname,NetCompanyPay,Discount,TaxAmount,NetPatientPay,ReturnAmount,PaymentMode,PaymentAmount,OSA";
        Query += "group by ps.ps_id ";
//        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        for (int j = 0; j < jsonArray.length(); j++) {
            if (!jsonArray.getJSONObject(j).get("PaymentMode").toString().equals("")) {
                if (jsonArray.getJSONObject(j).get("ps_id") != null) {
                    String Query5 = " SELECT rr.pbrr_pm_id,sum(rr.cash) " +
                            " FROM tinv_pharmacy_bill_return_recepit rr where pbrr_ps_id =" + jsonArray.getJSONObject(j).get("ps_id") + " group by  rr.pbrr_pm_id";
                    List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query5).getResultList();
                    if (list.size() > 0) {
                        for (Object[] obj : list) {
                            if (obj[0] != null) {
                                if (obj[0].toString().equals("1")) {
                                    jsonArray.getJSONObject(j).put("Cash", obj[1].toString());
                                } else if (obj[0].toString().equals("2")) {
                                    jsonArray.getJSONObject(j).put("Cheque", obj[1].toString());
                                } else if (obj[0].toString().equals("3")) {
                                    jsonArray.getJSONObject(j).put("Card", obj[1].toString());
                                } else if (obj[0].toString().equals("4")) {
                                    jsonArray.getJSONObject(j).put("Transfer", obj[1].toString());
                                } else if (obj[0].toString().equals("5")) {
                                    jsonArray.getJSONObject(j).put("DebitCard", obj[1].toString());
                                } else if (obj[0].toString().equals("6")) {
                                    jsonArray.getJSONObject(j).put("Credit", obj[1].toString());
                                }
                            }
                        }
                    }
                }
            }

        }
        // return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misopdipdreturnPharmDetailReportSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(misopdipdreturnPharmDetailReportSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (misopdipdreturnPharmDetailReportSearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("OPD-IPDReturnPharmacyDetailBillReports", jsonArray.toString());
        } else {
            return createReport.createJasperReportPDFByJson("OPD-IPDReturnPharmacyDetailBillReports", jsonArray.toString());
        }
    }
    //IPD-OPD Return Pharmacy Detail Bill Reports End

    // External pharmacy Patient Report start
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("searchExtenalPharmDetailReport/{today}/{patientName}/{pmId}/{unitList}/{fromdate}/{todate}/{limit}/{offset}")
    public ResponseEntity searchExtenalPharmDetailReport(@RequestHeader("X-tenantId") String tenantName, @PathVariable String today, @PathVariable String patientName, @PathVariable String pmId, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int limit, @PathVariable int offset) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "  ";
        String CountQuery = "";
        String columnName = "";
        Query = "select ps.ps_id,u.unit_name,ps.created_by_name,date(ps.created_date) as PDate,ifnull(ps.ps_id,0) as PharmBillNo," +
                "CONCAT( ifnull(ps.ps_patient_first_name,'') ,' ', ifnull(ps.ps_patient_middle_name,''),' ',ifnull(ps.ps_patient_last_name,'') ) as patientname," +
                "sum(pr.cash) as PaymentAmount,ps_tax_amount as TaxAmount,ps.ps_net_amount as NetPatientPay,ps_concsssion_amount as Discount," +
                "ifnull(pm.pm_name,'')as PaymentMode,ps.ps_out_standing_amount_for_patient as OSA, " +
                "(ps.ps_net_amount-ps.ps_out_standing_amount_for_patient)as AmountCollected " +
                "from tinv_pharmacy_sale ps left join tinv_pharmacy_bill_recepit pr on pr.pbr_ps_id=ps.ps_id " +
                "inner join mst_payment_mode pm on pr.pbr_pm_id=pm.pm_id " +
                "inner join mst_unit u on ps.pharmacy_unit_id=u.unit_id where ps.ps_visit_id is null and ps.ps_admission_id is null and ps.pharmacy_type=2 ";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and ps.pharmacy_unit_id in (" + values + ") ";
        }
        if (fromdate.equals("null") || fromdate.equals("")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("null") || todate.equals("")) {
            todate = strDate;
        }
        if (today.equals("true")) {
            Query += " and (date(ps.created_date)=curdate()) ";
        } else {
            Query += " and (date(ps.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!patientName.equals("null") && !patientName.equals("")) {
            Query += " and (ps.ps_patient_first_name like '%" + patientName + "%' or ps.ps_patient_last_name like '%" + patientName + "%') ";
        }
        // Payment-mode list
        if (!pmId.equals("null") && !pmId.equals("0")) {
            Query += " and pr.pbr_pm_id =  " + pmId + " ";

        }
        Query += " group by pr.pbr_id ";
        System.out.println("External patient Pharmacy Detail Bill List" + Query );
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "ps_Id,unit_name,created_by_name,PDate,PharmBillNo,patientname,PaymentAmount,TaxAmount,NetPatientPay,Discount,PaymentMode,OSA,AmountCollected";
        Query += " limit " + ((offset - 1) * limit) + "," + limit;
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }
    // External Pharmacy Patient Report End

    @RequestMapping("CovidAdmissionReport/{fromdate}/{todate}")
    public List<MisCovidAdmissionListDTO> CovidAdmissionReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisadmissionSearchDTO misadmissionSearchDTO,
                                                               @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<MisCovidAdmissionListDTO> misCovidAdmissionlistDTOList = new ArrayList<MisCovidAdmissionListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query2 = " SELECT a.admission_patient_id AS Patient_Id, p.patient_mr_no AS MR_No, CONCAT(IFNULL(u.user_firstname,''),' ', IFNULL(u.user_lastname,'')) AS patientname,  " +
                " IFNULL(u.user_age,'') AS user_age, IFNULL(g.gender_name,'') AS gender_name,  " +
                " IFNULL(u.user_address,'') AS user_address, c.city_name AS City, " +
                " IFNULL(u.user_mobile,'') AS user_mobile, IFNULL(cop.occupation_name,'') AS Occupation, " +
                " IFNULL(a.admission_date,'') AS admission_date, IFNULL(a.admission_time,'') AS admission_time,  " +
                " IFNULL(a.admission_ipd_no,'') AS admission_ipd_no,  " +
                " IF(a.admission_status=0,'Admitted','Discharged') AS Current_Status, " +
                " IFNULL(w.ward_name,'') AS ward_name,IFNULL(b.bed_name,'') AS bed_name,  " +
                " IFNULL(un.unit_name,'') AS unit_name, IFNULL(re.re_name,'') AS referalname,  " +
                " CONCAT(IFNULL(su.user_firstname,''),' ', IFNULL(su.user_lastname,'')) AS docname, " +
                " IFNULL(td.discharge_date,'') AS discharge_date,IFNULL(dtype.dt_name,'') AS discharge_type, " +
                " IFNULL(ddest.dd_name,'') AS Transfer_to_Other " +
                " FROM trn_admission a " +
                " LEFT JOIN mst_patient p ON a.admission_patient_id =p.patient_id " +
                " LEFT JOIN mst_user u ON p.patient_user_id = u.user_id " +
                " LEFT JOIN mst_city c ON u.user_city_id = c.city_id " +
                " LEFT JOIN mst_occupation cop ON u.user_occupation = cop.occupation_id " +
                " LEFT JOIN mst_gender g ON u.user_gender_id = g.gender_id " +
                " LEFT JOIN mst_staff s ON a.admission_staff_id = s.staff_id " +
                " LEFT JOIN mst_user su ON s.staff_user_id = su.user_id " +
                " LEFT JOIN mipd_bed b ON a.admission_patient_bed_id = b.bed_id " +
                " LEFT JOIN mipd_ward w ON b.bed_ward_id=w.ward_id " +
                " LEFT JOIN mst_unit un ON a.admission_unit_id = un.unit_id " +
                " LEFT JOIN mst_referring_entity re ON a.admission_re_id = re.re_id " +
                " LEFT JOIN trn_discharge td ON a.admission_id = td.discharge_admission_id " +
                " LEFT JOIN mipd_discharge_type dtype ON td.discharge_type = dtype.dt_id " +
                " LEFT JOIN mipd_discharge_destination ddest ON td.discharge_destination = ddest.dd_id " +
                " WHERE (u.user_firstname LIKE '%%' OR u.user_lastname LIKE '%%')  " +
                " AND a.is_active=1 AND a.is_deleted=0 AND a.admission_is_cancel=0 ";
        String countQuery = " SELECT COUNT(a.admission_id) FROM trn_admission a " +
                " WHERE a.is_active=1 AND a.is_deleted=0 AND a.admission_is_cancel=0 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = strDate;
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        } else {
            query2 += " and (date(a.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
            countQuery += " and (date(a.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        try {
            query2 += " limit " + ((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()) + "," + misadmissionSearchDTO.getLimit();
            System.out.println("List Of COVID Admission Report" + query2);
            List<Object[]> listTrnAdmissionService = entityManager.createNativeQuery(query2).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listTrnAdmissionService) {
                MisCovidAdmissionListDTO objMisCovidAdmissionListDTO = new MisCovidAdmissionListDTO();
                objMisCovidAdmissionListDTO.setMrNo(obj[1].toString());
                objMisCovidAdmissionListDTO.setPatientFullName(obj[2].toString());
                if (obj[3] != null && !obj[3].equals("")) {
                    objMisCovidAdmissionListDTO.setPatientAge(Long.parseLong(obj[3].toString()));
                } else {
                    objMisCovidAdmissionListDTO.setPatientAge(Long.parseLong("0"));
                }
                objMisCovidAdmissionListDTO.setPatientSex(obj[4].toString());
                objMisCovidAdmissionListDTO.setPatientAddress(obj[5].toString());
                objMisCovidAdmissionListDTO.setPatientCity(obj[6].toString());
                objMisCovidAdmissionListDTO.setPatientContact(obj[7].toString());
                objMisCovidAdmissionListDTO.setPatientOccupation(obj[8].toString());
                objMisCovidAdmissionListDTO.setDateOfAdmission(obj[9].toString());
                objMisCovidAdmissionListDTO.setAdmissionIpdNo(obj[11].toString());
                objMisCovidAdmissionListDTO.setCurrentStatus(obj[12].toString());
                objMisCovidAdmissionListDTO.setWardName(obj[13].toString());
                objMisCovidAdmissionListDTO.setBedName(obj[14].toString());
                objMisCovidAdmissionListDTO.setNameOfHospital(obj[15].toString());
                objMisCovidAdmissionListDTO.setDateOfDischarge(obj[18].toString());
                objMisCovidAdmissionListDTO.setDischargeType(obj[19].toString());
                objMisCovidAdmissionListDTO.setPatientId(Long.parseLong(obj[0].toString()));
                objMisCovidAdmissionListDTO.setCount(count);
                misCovidAdmissionlistDTOList.add(objMisCovidAdmissionListDTO);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        return misCovidAdmissionlistDTOList;
    }

    @RequestMapping("CovidAdmissionReportPrint/{fromdate}/{todate}")
    public String CovidAdmissionReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisadmissionSearchDTO misadmissionSearchDTO,
                                            @PathVariable String fromdate, @PathVariable String todate) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        JSONArray array = new JSONArray();
        List<MisCovidAdmissionListDTO> misCovidAdmissionlistDTOList = new ArrayList<MisCovidAdmissionListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query2 = " SELECT a.admission_patient_id AS Patient_Id, p.patient_mr_no AS MR_No, CONCAT(IFNULL(u.user_firstname,''),' ', IFNULL(u.user_lastname,'')) AS patientname,  " +
                " IFNULL(u.user_age,'') AS user_age, IFNULL(g.gender_name,'') AS gender_name,  " +
                " IFNULL(u.user_address,'') AS user_address, c.city_name AS City, " +
                " IFNULL(u.user_mobile,'') AS user_mobile, IFNULL(cop.occupation_name,'') AS Occupation, " +
                " IFNULL(a.admission_date,'') AS admission_date, IFNULL(a.admission_time,'') AS admission_time,  " +
                " IFNULL(a.admission_ipd_no,'') AS admission_ipd_no,  " +
                " IF(a.admission_status=0,'Admitted','Discharged') AS Current_Status, " +
                " IFNULL(w.ward_name,'') AS ward_name,IFNULL(b.bed_name,'') AS bed_name,  " +
                " IFNULL(un.unit_name,'') AS unit_name, IFNULL(re.re_name,'') AS referalname,  " +
                " CONCAT(IFNULL(su.user_firstname,''),' ', IFNULL(su.user_lastname,'')) AS docname, " +
                " IFNULL(td.discharge_date,'') AS discharge_date,IFNULL(dtype.dt_name,'') AS discharge_type, " +
                " IFNULL(ddest.dd_name,'') AS Transfer_to_Other " +
                " FROM trn_admission a " +
                " LEFT JOIN mst_patient p ON a.admission_patient_id =p.patient_id " +
                " LEFT JOIN mst_user u ON p.patient_user_id = u.user_id " +
                " LEFT JOIN mst_city c ON u.user_city_id = c.city_id " +
                " LEFT JOIN mst_occupation cop ON u.user_occupation = cop.occupation_id " +
                " LEFT JOIN mst_gender g ON u.user_gender_id = g.gender_id " +
                " LEFT JOIN mst_staff s ON a.admission_staff_id = s.staff_id " +
                " LEFT JOIN mst_user su ON s.staff_user_id = su.user_id " +
                " LEFT JOIN mipd_bed b ON a.admission_patient_bed_id = b.bed_id " +
                " LEFT JOIN mipd_ward w ON b.bed_ward_id=w.ward_id " +
                " LEFT JOIN mst_unit un ON a.admission_unit_id = un.unit_id " +
                " LEFT JOIN mst_referring_entity re ON a.admission_re_id = re.re_id " +
                " LEFT JOIN trn_discharge td ON a.admission_id = td.discharge_admission_id " +
                " LEFT JOIN mipd_discharge_type dtype ON td.discharge_type = dtype.dt_id " +
                " LEFT JOIN mipd_discharge_destination ddest ON td.discharge_destination = ddest.dd_id " +
                " WHERE (u.user_firstname LIKE '%%' OR u.user_lastname LIKE '%%')  " +
                " AND a.is_active=1 AND a.is_deleted=0 AND a.admission_is_cancel=0 ";
        String countQuery = " SELECT COUNT(a.admission_id) FROM trn_admission a " +
                " WHERE a.is_active=1 AND a.is_deleted=0 AND a.admission_is_cancel=0 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = strDate;
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        } else {
            query2 += " and (date(a.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
            countQuery += " and (date(a.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        try {
            query2 += " limit " + ((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()) + "," + misadmissionSearchDTO.getLimit();
            System.out.println("Main Query" + query2);
            List<Object[]> listTrnAdmissionService = entityManager.createNativeQuery(query2).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = cc.longValue();

           /* for (Object[] obj : listTrnAdmissionService) {

                MisCovidAdmissionListDTO objMisCovidAdmissionListDTO = new MisCovidAdmissionListDTO();

                objMisCovidAdmissionListDTO.setMrNo(obj[1].toString());
                objMisCovidAdmissionListDTO.setPatientFullName(obj[2].toString());
                if (obj[3] != null && !obj[3].equals("")) {
                    objMisCovidAdmissionListDTO.setPatientAge(Long.parseLong(obj[3].toString()));
                } else {
                    objMisCovidAdmissionListDTO.setPatientAge(Long.parseLong("0"));
                }
                objMisCovidAdmissionListDTO.setPatientSex(obj[4].toString());
                objMisCovidAdmissionListDTO.setPatientAddress(obj[5].toString());
                objMisCovidAdmissionListDTO.setPatientCity(obj[6].toString());
                objMisCovidAdmissionListDTO.setPatientContact(obj[7].toString());
                objMisCovidAdmissionListDTO.setPatientOccupation(obj[8].toString());
                objMisCovidAdmissionListDTO.setDateOfAdmission(obj[9].toString());
                objMisCovidAdmissionListDTO.setAdmissionIpdNo(obj[11].toString());
                objMisCovidAdmissionListDTO.setCurrentStatus(obj[12].toString());
                objMisCovidAdmissionListDTO.setWardName(obj[13].toString());
                objMisCovidAdmissionListDTO.setBedName(obj[14].toString());
                objMisCovidAdmissionListDTO.setNameOfHospital(obj[15].toString());
                objMisCovidAdmissionListDTO.setDateOfDischarge(obj[18].toString());
                objMisCovidAdmissionListDTO.setDischargeType(obj[19].toString());
                objMisCovidAdmissionListDTO.setPatientId(Long.parseLong(obj[0].toString()));
                objMisCovidAdmissionListDTO.setCount(count);
                misCovidAdmissionlistDTOList.add(objMisCovidAdmissionListDTO);
                array.put(objMisCovidAdmissionListDTO);

            }*/
            for (Object[] obj : listTrnAdmissionService) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("mrNo", "" + obj[1]);
                jsonObject.put("patientFullName", "" + obj[2]);
                if (obj[3] != null && !obj[3].equals("")) {
                    jsonObject.put("patientAge", "" + obj[3]);
                } else {
                    jsonObject.put("patientAge", "" + Long.parseLong("0"));
                }
                jsonObject.put("patientSex", "" + obj[4]);
                jsonObject.put("patientAddress", "" + obj[5]);
                jsonObject.put("patientCity", "" + obj[6]);
                jsonObject.put("patientContact", "" + obj[7]);
                jsonObject.put("patientOccupation", "" + obj[8]);
                jsonObject.put("dateOfAdmission", "" + obj[9]);
                jsonObject.put("admissionIpdNo", "" + obj[11]);
                jsonObject.put("currentStatus", "" + obj[12]);
                jsonObject.put("wardName", "" + obj[13]);
                jsonObject.put("bedName", "" + obj[14]);
                jsonObject.put("nameOfHospital", "" + obj[15]);
                jsonObject.put("dateOfDischarge", "" + obj[18]);
                jsonObject.put("dischargeType", "" + obj[19]);
                jsonObject.put("patientId", "" + obj[0]);
                array.put(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
//        return misCovidAdmissionlistDTOList;
        if (misadmissionSearchDTO.getPrint()) {
            String columnName = "icmrNo,mrNo,patientFullName,patientAge,patientSex,patientAddress,patientCity,patientContact,patientOccupation,dateOfAdmission," +
                    "dateOfAdmissionCcc,admissionIpdNo,currentStatus,dateOfDischarge,dischargeType,wardName,bedName,nameOfHospital,homeIsolation,homeIsolationDate,sampleCollectedDate,coMorbidity," +
                    "labAgency,labId,patientCameFrom,otherStateName,wardOfficeName";
            return createReport.generateExcel(columnName, "CovidPatientReport", array);
        } else {
            return createReport.createJasperReportPDFByJson("AdmissionReport", array.toString());
        }
    }

    @RequestMapping("bedMovementReport/{fromdate}/{todate}")
    public List<MisadmissionListDTO> bedMovementReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisadmissionSearchDTO misadmissionSearchDTO, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<MisadmissionListDTO> misadmissionlistDTOList = new ArrayList<MisadmissionListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = "";
        String Query1 = "select ifnull(mp.patient_mr_no, '') as mrNo, ifnull(ta.admission_ipd_no, '') as ipdNo, " +
                " ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as PatientName, " +
                " ifnull(mu.user_age,'') as age, ifnull(mg.gender_name,'') as gender, " +
                " ifnull(ta.admission_date,'') as admissionDate, ifnull(mw.ward_name,'') as ward, ifnull(mr.room_name,'')," +
                " ifnull(mb.bed_name,'') as bed, tbt.created_date as TransferDate, tbt.transfer_remark as remark" +
                " from trn_bed_transfer tbt  " +
                " left join trn_admission ta on ta.admission_id = tbt.transfer_admission_id " +
                " left join mipd_bed mb on mb.bed_id = tbt.transferpatientbed_id " +
                " left join mst_patient mp on mp.patient_id = ta.admission_patient_id " +
                " left join mst_user mu ON mu.user_id = mp.patient_user_id  " +
                " left join mst_title mt ON mt.title_id = mu.user_title_id " +
                " left join mst_gender mg on mg.gender_id = mu.user_gender_id " +
                " left join mipd_ward mw on mw.ward_id = mb.bed_ward_id " +
                " left join mipd_room mr on mr.room_id = mb.bed_room_id " +
                " where tbt.is_active =1 and tbt.is_deleted = 0 ";
        String countQuery = "select count(*) from trn_bed_transfer tbt " +
                " left join trn_admission ta on ta.admission_id = tbt.transfer_admission_id " +
                " left join mipd_bed mb on mb.bed_id = tbt.transferpatientbed_id " +
                " left join mst_patient mp on mp.patient_id = ta.admission_patient_id " +
                " left join mst_user mu ON mu.user_id = mp.patient_user_id  " +
                " left join mst_title mt ON mt.title_id = mu.user_title_id " +
                " left join mst_gender mg on mg.gender_id = mu.user_gender_id " +
                " left join mipd_ward mw on mw.ward_id = mb.bed_ward_id " +
                " left join mipd_room mr on mr.room_id = mb.bed_room_id " +
                " where tbt.is_active =1 and tbt.is_deleted = 0 ";
        if (!misadmissionSearchDTO.getPatientName().equals("")) {
            Query1 += " and (mu.user_firstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";
            countQuery += " and (mu.user_firstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";

        } else {
            Query1 += " and (mu.user_firstname like '%%' or mu.user_lastname like '%%')";
            countQuery += " and (mu.user_firstname like '%%' or mu.user_lastname like '%%')";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = strDate;
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misadmissionSearchDTO.getTodaydate()) {
            Query1 += " and (date(ta.admission_date)=curdate()) ";
            countQuery += " and (date(ta.admission_date)=curdate()) ";
        } else {
            Query1 += " and (date(ta.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
            countQuery += " and (date(ta.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!misadmissionSearchDTO.getUserId().equals("0")) {
            Query1 += " and ta.createduser like '%" + misadmissionSearchDTO.getUserId() + "%' ";
            countQuery += " and ta.createduser like '%" + misadmissionSearchDTO.getUserId() + "%' ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getDoctorId()).equals("0")) {
            Query1 += " and ta.admission_staff_id=" + misadmissionSearchDTO.getDoctorId() + " ";
            countQuery += " and ta.admission_staff_id=" + misadmissionSearchDTO.getDoctorId() + " ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getWardId()).equals("0")) {
            Query1 += " and mb.bed_ward_id=" + misadmissionSearchDTO.getWardId() + " ";
            countQuery += " and mb.bed_ward_id=" + misadmissionSearchDTO.getWardId() + " ";
        }
        if (!misadmissionSearchDTO.getMrNo().equals("")) {
            Query1 += " and mp.patient_mr_no like  '%" + misadmissionSearchDTO.getMrNo() + "%' ";
            countQuery += " and mp.patient_mr_no like '%" + misadmissionSearchDTO.getMrNo() + "%' ";
        }
        try {
            Query1 += " limit " + ((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()) + "," + misadmissionSearchDTO.getLimit();
            System.out.println("Bed Movement Report " + Query1);
            List<Object[]> listTrnAdmissionService = entityManager.createNativeQuery(Query1).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listTrnAdmissionService) {
                MisadmissionListDTO objmisadmissionlistDTO = new MisadmissionListDTO();
                objmisadmissionlistDTO.setMrNo(obj[0].toString());
                objmisadmissionlistDTO.setIpdNo(obj[1].toString());
                objmisadmissionlistDTO.setPatientName(obj[2].toString());
                if (obj[3] != null && !obj[3].equals("")) {
                    objmisadmissionlistDTO.setAge(Long.parseLong(obj[3].toString()));
                } else {
                    objmisadmissionlistDTO.setAge(Long.parseLong("0"));
                }
                objmisadmissionlistDTO.setGender(obj[4].toString());
                objmisadmissionlistDTO.setAdmitDate(obj[5].toString());
                objmisadmissionlistDTO.setWardName(obj[6].toString());
                objmisadmissionlistDTO.setRoomName(obj[7].toString());
                objmisadmissionlistDTO.setBedNo(obj[8].toString());
                objmisadmissionlistDTO.setBedTransferDate(obj[9].toString());
                if (obj[10] != null && !obj[10].equals("")) {
                    objmisadmissionlistDTO.setBedTransferRemark(obj[10].toString());
                } else {
                    objmisadmissionlistDTO.setBedTransferRemark("");
                }
                objmisadmissionlistDTO.setCount(count);
                misadmissionlistDTOList.add(objmisadmissionlistDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        return misadmissionlistDTOList;
    }

    @RequestMapping("bedMovementReportPrint/{fromdate}/{todate}")
    public String bedMovementReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisadmissionSearchDTO misadmissionSearchDTO, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<MisadmissionListDTO> misadmissionlistDTOList = new ArrayList<MisadmissionListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = "";
        String Query1 = "select ifnull(mp.patient_mr_no, '') as mrNo, ifnull(ta.admission_ipd_no, '') as ipdNo, " +
                " ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as PatientName, " +
                " ifnull(mu.user_age,'') as age, ifnull(mg.gender_name,'') as gender, " +
                " ifnull(ta.admission_date,'') as admissionDate, ifnull(mw.ward_name,'') as ward, ifnull(mr.room_name,'') as room," +
                " ifnull(mb.bed_name,'') as bed, ifnull(tbt.created_date,'') as TransferDate, tbt.transfer_remark as remark" +
                " from trn_bed_transfer tbt  " +
                " left join trn_admission ta on ta.admission_id = tbt.transfer_admission_id " +
                " left join mipd_bed mb on mb.bed_id = tbt.transferpatientbed_id " +
                " left join mst_patient mp on mp.patient_id = ta.admission_patient_id " +
                " left join mst_user mu ON mu.user_id = mp.patient_user_id  " +
                " left join mst_title mt ON mt.title_id = mu.user_title_id " +
                " left join mst_gender mg on mg.gender_id = mu.user_gender_id " +
                " left join mipd_ward mw on mw.ward_id = mb.bed_ward_id " +
                " left join mipd_room mr on mr.room_id = mb.bed_room_id " +
                " where tbt.is_active =1 and tbt.is_deleted = 0 ";
        String countQuery = "select count(*) from trn_bed_transfer tbt " +
                " left join trn_admission ta on ta.admission_id = tbt.transfer_admission_id " +
                " left join mipd_bed mb on mb.bed_id = tbt.transferpatientbed_id " +
                " left join mst_patient mp on mp.patient_id = ta.admission_patient_id " +
                " left join mst_user mu ON mu.user_id = mp.patient_user_id  " +
                " left join mst_title mt ON mt.title_id = mu.user_title_id " +
                " left join mst_gender mg on mg.gender_id = mu.user_gender_id " +
                " left join mipd_ward mw on mw.ward_id = mb.bed_ward_id " +
                " left join mipd_room mr on mr.room_id = mb.bed_room_id " +
                " where tbt.is_active =1 and tbt.is_deleted = 0 ";
        if (!misadmissionSearchDTO.getPatientName().equals("")) {
            Query1 += " and (mu.user_firstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";
            countQuery += " and (mu.user_firstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";

        } else {
            Query1 += " and (mu.user_firstname like '%%' or mu.user_lastname like '%%')";
            countQuery += " and (mu.user_firstname like '%%' or mu.user_lastname like '%%')";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = strDate;
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misadmissionSearchDTO.getTodaydate()) {
            Query1 += " and (date(ta.admission_date)=curdate()) ";
            countQuery += " and (date(ta.admission_date)=curdate()) ";
        } else {
            Query1 += " and (date(ta.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
            countQuery += " and (date(ta.admission_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!misadmissionSearchDTO.getUserId().equals("0")) {
            Query1 += " and ta.createduser like '%" + misadmissionSearchDTO.getUserId() + "%' ";
            countQuery += " and ta.createduser like '%" + misadmissionSearchDTO.getUserId() + "%' ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getDoctorId()).equals("0")) {
            Query1 += " and ta.admission_staff_id=" + misadmissionSearchDTO.getDoctorId() + " ";
            countQuery += " and ta.admission_staff_id=" + misadmissionSearchDTO.getDoctorId() + " ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getWardId()).equals("0")) {
            Query1 += " and mb.bed_ward_id=" + misadmissionSearchDTO.getWardId() + " ";
            countQuery += " and mb.bed_ward_id=" + misadmissionSearchDTO.getWardId() + " ";
        }
        if (!misadmissionSearchDTO.getMrNo().equals("")) {
            Query1 += " and mp.patient_mr_no like  '%" + misadmissionSearchDTO.getMrNo() + "%' ";
            countQuery += " and mp.patient_mr_no like '%" + misadmissionSearchDTO.getMrNo() + "%' ";
        }
        JSONArray array = new JSONArray();
        try {
//            Query1 += " limit " + ((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()) + "," + misadmissionSearchDTO.getLimit();
            System.out.println("Main Query " + Query1);
            List<Object[]> listTrnAdmissionService = entityManager.createNativeQuery(Query1).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listTrnAdmissionService) {
                MisadmissionListDTO objmisadmissionlistDTO = new MisadmissionListDTO();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("mrNo", "" + obj[0]);
                jsonObject.put("ipdNo", "" + obj[1]);
                jsonObject.put("patientName", "" + obj[2]);
                if (obj[3] != null && !obj[3].equals("")) {
                    jsonObject.put("age", obj[3]);
                } else {
                    jsonObject.put("age", ("0"));
                }
                jsonObject.put("gender", "" + obj[4]);
                jsonObject.put("admitDate", "" + obj[5]);
                jsonObject.put("wardName", "" + obj[6]);
                jsonObject.put("roomName", "" + obj[7]);
                jsonObject.put("bedNo", "" + obj[8]);
                jsonObject.put("bedTrasnsferDate", "" + obj[9]);
                if (obj[10] != null && !obj[10].equals("")) {
                    jsonObject.put("bedTrasnsferRemark", "" + obj[10]);
                } else {
                    jsonObject.put("bedTrasnsferRemark", "");
                }
                array.put(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misadmissionSearchDTO.getUnitId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        array.getJSONObject(0).put("objHeaderData", jsonObject);
        if (misadmissionSearchDTO.getPrint()) {
            String columnName = "mrNo,ipdNo,patientName,age,gender,admitDate,wardName,roomName,bedNo,transferDate,transferRemark";
            return createReport.generateExcel(columnName, "BedTransferReport", array);
        } else {
            return createReport.createJasperReportPDFByJson("BedTransferReport", array.toString());
        }
    }

}
