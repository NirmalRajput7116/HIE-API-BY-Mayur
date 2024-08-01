package com.cellbeans.hspa.mis.CountReprotMIS;
//import com.cellbeans.hspa.mis.CountReportMIS.*;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/mis_report_count")
public class CountReportsMisController {

    @PersistenceContext
    EntityManager objEntityManager;

    @Autowired
    MstUnitRepository mstUnitRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    MstUserRepository mstUserRepository;
    @Autowired
    CreateJSONObject createJSONObject;
    @Autowired
    CreateReport createReport;

    @RequestMapping("agewisepatientcount/{fromdate}/{todate}")
    public List<UnitGenderWisePatientCountDto> getActivityPatientCount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object[]> respMapFinal = new HashMap<String, Object[]>();
        List<MstUnit> mstUnitlist = new ArrayList<MstUnit>();
        mstUnitlist = mstUnitRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        List<UnitGenderWisePatientCountDto> UnitGenderWisePatientCountlist = new ArrayList<UnitGenderWisePatientCountDto>();
        for (int i = 0; i < mstUnitlist.size(); i++) {
            Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();
            UnitGenderWisePatientCountDto u = new UnitGenderWisePatientCountDto();
            BigInteger from0To1Count;
            BigInteger from2to6Count;
            BigInteger from7To12Count;
            BigInteger from13To18Count;
            BigInteger from19to30Count;
            BigInteger from31To45Count;
            BigInteger from46To60Count;
            BigInteger from61AndAboveCount;
            Long genderid;
            String query1 = "select count(mv.visit_id) from mst_visit mv inner join mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join mst_user mu ON mu.user_id = mp.patient_user_id left join mst_unit m on m.unit_id = mv.visit_unit_id where mv.visit_unit_id = " + mstUnitlist.get(i).getUnitId() + " and DATE_FORMAT(mv.created_date,'%y-%m-%d') between CAST('" + fromdate + "' as DATE) and CAST('" + todate + "' as DATE) and mu.user_age between 0 and 1 and mv.is_active = 1 and mv.is_deleted = 0 ";
            System.out.println("1 " + query1);
            from0To1Count = new BigInteger(objEntityManager.createNativeQuery(query1).getSingleResult().toString());
            System.out.println("fromdate " + fromdate + "todate  " + todate + "from0To1Count " + from0To1Count);
            String query2 = "select count(mv.visit_id) from mst_visit mv inner join mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join mst_user mu ON mu.user_id = mp.patient_user_id left join mst_unit m on m.unit_id = mv.visit_unit_id where mv.visit_unit_id = " + mstUnitlist.get(i).getUnitId() + " and DATE_FORMAT(mv.created_date,'%y-%m-%d') between CAST('" + fromdate + "' as DATE) and CAST('" + todate + "' as DATE) and mu.user_age between 2 and 6 and mv.is_active = 1 and mv.is_deleted = 0 ";
            System.out.println("2" + query2);
            from2to6Count = new BigInteger(objEntityManager.createNativeQuery(query2).getSingleResult().toString());
            String query3 = "select count(mv.visit_id) from mst_visit mv inner join mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join mst_user mu ON mu.user_id = mp.patient_user_id left join mst_unit m on m.unit_id = mv.visit_unit_id where mv.visit_unit_id = " + mstUnitlist.get(i).getUnitId() + " and DATE_FORMAT(mv.created_date,'%y-%m-%d') between CAST('" + fromdate + "' as DATE) and CAST('" + todate + "' as DATE) and mu.user_age between 7 and 12 and mv.is_active = 1 and mv.is_deleted = 0 ";
            System.out.println("3" + query3);
            from7To12Count = new BigInteger(objEntityManager.createNativeQuery(query3).getSingleResult().toString());
            String query4 = "select count(mv.visit_id) from mst_visit mv inner join mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join mst_user mu ON mu.user_id = mp.patient_user_id left join mst_unit m on m.unit_id = mv.visit_unit_id where mv.visit_unit_id = " + mstUnitlist.get(i).getUnitId() + " and DATE_FORMAT(mv.created_date,'%y-%m-%d') between CAST('" + fromdate + "' as DATE) and CAST('" + todate + "' as DATE) and mu.user_age between 13 and 18 and mv.is_active = 1 and mv.is_deleted = 0 ";
            System.out.println("4" + query4);
            from13To18Count = new BigInteger(objEntityManager.createNativeQuery(query4).getSingleResult().toString());
            String query5 = "select count(mv.visit_id) from mst_visit mv inner join mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join mst_user mu ON mu.user_id = mp.patient_user_id left join mst_unit m on m.unit_id = mv.visit_unit_id where mv.visit_unit_id = " + mstUnitlist.get(i).getUnitId() + " and DATE_FORMAT(mv.created_date,'%y-%m-%d') between CAST('" + fromdate + "' as DATE) and CAST('" + todate + "' as DATE) and mu.user_age between 19 and 30 and mv.is_active = 1 and mv.is_deleted = 0 ";
            System.out.println("5" + query5);
            from19to30Count = new BigInteger(objEntityManager.createNativeQuery(query5).getSingleResult().toString());
            System.out.println("from19to30Count " + from19to30Count);
            String query6 = "select count(mv.visit_id) from mst_visit mv inner join mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join mst_user mu ON mu.user_id = mp.patient_user_id left join mst_unit m on m.unit_id = mv.visit_unit_id where mv.visit_unit_id = " + mstUnitlist.get(i).getUnitId() + " and DATE_FORMAT(mv.created_date,'%y-%m-%d') between CAST('" + fromdate + "' as DATE) and CAST('" + todate + "' as DATE) and mu.user_age between 31 and 45 and mv.is_active = 1 and mv.is_deleted = 0 ";
            System.out.println("6" + query6);
            from31To45Count = new BigInteger(objEntityManager.createNativeQuery(query6).getSingleResult().toString());
            String query7 = "select count(mv.visit_id) from mst_visit mv inner join mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join mst_user mu ON mu.user_id = mp.patient_user_id left join mst_unit m on m.unit_id = mv.visit_unit_id where mv.visit_unit_id = " + mstUnitlist.get(i).getUnitId() + " and DATE_FORMAT(mv.created_date,'%y-%m-%d') between CAST('" + fromdate + "' as DATE) and CAST('" + todate + "' as DATE) and mu.user_age between 46 and 60 and mv.is_active = 1 and mv.is_deleted = 0 ";
            System.out.println("7" + query7);
            from46To60Count = new BigInteger(objEntityManager.createNativeQuery(query7).getSingleResult().toString());
            String query8 = "select count(mv.visit_id) from mst_visit mv inner join mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join mst_user mu ON mu.user_id = mp.patient_user_id left join mst_unit m on m.unit_id = mv.visit_unit_id where mv.visit_unit_id = " + mstUnitlist.get(i).getUnitId() + " and DATE_FORMAT(mv.created_date,'%y-%m-%d') between CAST('" + fromdate + "' as DATE) and CAST('" + todate + "' as DATE) and  mu.user_age > 60  and  mv.is_active = 1 and mv.is_deleted = 0 ";
            System.out.println("8" + query8);
            from61AndAboveCount = new BigInteger(objEntityManager.createNativeQuery(query8).getSingleResult().toString());
            respMap.put("from0To1Count", from0To1Count);
            respMap.put("from2to6Count", from2to6Count);
            respMap.put("from7To12Count", from7To12Count);
            respMap.put("from13To18Count", from13To18Count);
            respMap.put("from19to30Count", from19to30Count);
            respMap.put("from31To45Count", from31To45Count);
            respMap.put("from46To60Count", from46To60Count);
            respMap.put("from61AndAboveCount", from61AndAboveCount);
            u.setUnitName(mstUnitlist.get(i).getUnitName());
            u.setAgecount(respMap);
            UnitGenderWisePatientCountlist.add(u);
        }
        return UnitGenderWisePatientCountlist;
    }

    @RequestMapping("noofbillgenerated/{fromdate}/{todate}")
    public List<NoOfBillGeneratedCountDto> noofbillgenerated(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object[]> respMapFinal = new HashMap<String, Object[]>();
        List<MstUnit> mstUnitlist = new ArrayList<MstUnit>();
        mstUnitlist = mstUnitRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        List<NoOfBillGeneratedCountDto> noOfBillGeneratedCountlist = new ArrayList<NoOfBillGeneratedCountDto>();
        for (int i = 0; i < mstUnitlist.size(); i++) {
            BigInteger cnt;
            NoOfBillGeneratedCountDto n = new NoOfBillGeneratedCountDto();
            Long genderid;
            String query1 = "select count(t.bill_id) from tbill_bill t  left join  mst_unit mu ON mu.unit_id = t.tbill_unit_id where  t.tbill_unit_id = " + mstUnitlist.get(i).getUnitId() + " and DATE_FORMAT(t.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE)  ";
            System.out.println(query1);
            cnt = new BigInteger(objEntityManager.createNativeQuery(query1).getSingleResult().toString());
            n.setUnitName(mstUnitlist.get(i).getUnitName());
            n.setCount(cnt);
            noOfBillGeneratedCountlist.add(n);
        }
        return noOfBillGeneratedCountlist;
    }

    @RequestMapping("diagnosiswisepatientreport/{fromdate}/{todate}/{diagnosisType}")
    public List<DiagnosisWisePatientCountDto> diagnosiswisepatientreport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("diagnosisType") String diagnosisType) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object[]> respMapFinal = new HashMap<String, Object[]>();
        List<MstUnit> mstUnitlist = new ArrayList<MstUnit>();
        boolean type = false;
        mstUnitlist = mstUnitRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        List<DiagnosisWisePatientCountDto> DiagnosisWisePatientCountList = new ArrayList<DiagnosisWisePatientCountDto>();
        List<DiagnosisDetailsDto> diagnosisDetailsList = null;
        DiagnosisWisePatientCountDto d = null;
        DiagnosisDetailsDto diagnosisDetailsDto = null;
        for (int i = 0; i < mstUnitlist.size(); i++) {
            diagnosisDetailsList = new ArrayList<DiagnosisDetailsDto>();
            String query2 = "SELECT COUNT(tvd.vd_id) as count ,mic.ic_description as description,mic.ic_code as code from temr_visit_diagnosis tvd INNER JOIN memr_icd_code mic ON mic.ic_id = tvd.vd_ic_id INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id INNER JOIN mst_unit mu ON mu.unit_id = mv.visit_unit_id  where date(tvd.created_date) between '" + fromdate + "'   and  '" + todate + "'   ";
            if (diagnosisType.equals("0")) {
                query2 += " and tvd.vd_is_final_diagnosis = 0 ";
            } else if (diagnosisType.equals("1")) {
                query2 += " and  tvd.vd_is_final_diagnosis = 1 ";
            }
            query2 += " and mv.visit_unit_id =" + mstUnitlist.get(i).getUnitId() + "  GROUP BY vd_ic_id ORDER BY count DESC limit 10 ";
            System.out.println("Diagnosis Wise Patient Count Report" + query2);
            d = new DiagnosisWisePatientCountDto();
            List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
            //String query1 = "SELECT COUNT(tvd.vd_id) as count ,mic.ic_description as description,mic.ic_code as code  from temr_visit_diagnosis tvd LEFT JOIN memr_icd_code mic ON mic.ic_id = tvd.vd_ic_id LEFT JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id LEFT JOIN mst_unit mu ON mu.unit_id = mv.visit_unit_id where tvd.vd_is_final_diagnosis = true and mv.visit_unit_id = "+mstUnitlist.get(i).getUnitId()+" and date(tvd.created_date) between '"+fromdate+"'   and  '"+todate+"'  GROUP BY `vd_ic_id` ORDER BY count DESC limit 10";
            finalDignosisCount = objEntityManager.createNativeQuery(query2).getResultList();
            for (Object[] obj : finalDignosisCount) {
                diagnosisDetailsDto = new DiagnosisDetailsDto();
                System.out.println("-----gender count-----------");
                System.out.println(" count:" + obj[0]);
                System.out.println("description:" + obj[1]);
                System.out.println("code:" + obj[2]);
                diagnosisDetailsDto.setDiagnosisCount(Integer.parseInt(obj[0].toString()));
                diagnosisDetailsDto.setDiagnosisDescriptionName(obj[1].toString());
                diagnosisDetailsDto.setDiagnosisCode(obj[2].toString());
                diagnosisDetailsList.add(diagnosisDetailsDto);
            }
            d.setDignosisDetails(diagnosisDetailsList);
            d.setUnitName(mstUnitlist.get(i).getUnitName());
            DiagnosisWisePatientCountList.add(d);
        }
        return DiagnosisWisePatientCountList;
    }

    @RequestMapping("getgenderwisepatientcountservice/{fromdate}/{todate}")
    public List<GenderwisePatientCountDto> getgenderwisepatientcountservice(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object[]> respMapFinal = new HashMap<String, Object[]>();
        List<MstUnit> mstUnitlist = new ArrayList<MstUnit>();
        mstUnitlist = mstUnitRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        List<GenderwisePatientCountDto> genderWisePatientCountList = new ArrayList<GenderwisePatientCountDto>();
        List<GenderDetailsDto> genderDetailsList = null;
        GenderwisePatientCountDto g = null;
        GenderDetailsDto genderDetailsDto = null;
        for (int i = 0; i < mstUnitlist.size(); i++) {
            genderDetailsList = new ArrayList<GenderDetailsDto>();
            g = new GenderwisePatientCountDto();
            List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
            String query2 = "select count(mv.visit_id) ,ifnull(mg.gender_name,'') from mst_visit mv LEFT join mst_unit u ON u.unit_id = mv.visit_unit_id " + "LEFT join  mst_patient mp ON mp.patient_id = mv.visit_patient_id " + "LEFT join  mst_user mu ON mu.user_id = mp.patient_user_id " + "LEFT JOIN mst_gender mg on mg.gender_id = mu.user_gender_id " + " where date(mv.created_date) between '" + fromdate + "'   and  '" + todate + "' and   mv.visit_unit_id =" + mstUnitlist.get(i).getUnitId() + " and  mv.is_active = 1 and mv.is_deleted = 0 group by  mu.user_gender_id";
            finalDignosisCount = objEntityManager.createNativeQuery(query2).getResultList();
            for (Object[] obj : finalDignosisCount) {
                genderDetailsDto = new GenderDetailsDto();
                genderDetailsDto.setGenderCount(Integer.parseInt(obj[0].toString()));
                genderDetailsDto.setGenderName(obj[1].toString() != null ? obj[1].toString() : "");
                genderDetailsList.add(genderDetailsDto);
            }
            g.setGenderCount(genderDetailsList);
            g.setUnitName(mstUnitlist.get(i).getUnitName());
            genderWisePatientCountList.add(g);
        }
        return genderWisePatientCountList;
    }

    @RequestMapping("appoinmenttypewisepatientcount/{fromdate}/{todate}")
    public List<AppoinmentwisePatientCountDto> appoinmenttypewisepatientcount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object[]> respMapFinal = new HashMap<String, Object[]>();
        List<MstUnit> mstUnitlist = new ArrayList<MstUnit>();
        mstUnitlist = mstUnitRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        AppoinmentwisePatientCountDto u;
        List<AppoinmentwisePatientCountDto> appoinmentWisePatientCountlist = new ArrayList<AppoinmentwisePatientCountDto>();
        for (int i = 0; i < mstUnitlist.size(); i++) {
            u = new AppoinmentwisePatientCountDto();
            Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();
            BigInteger booked, confirmed, cancelled;
            Long genderid;
            String query1 = "SELECT count(tp.appointment_id) FROM trn_appointment tp LEFT JOIN mst_unit mu ON mu.unit_id = tp.appointment_unit_id " + " where tp.appointment_unit_id  =" + mstUnitlist.get(i).getUnitId() + "  and date(tp.created_date) between '" + fromdate + "' and '" + todate + "' " + " and tp.appointment_is_confirm = false and tp.appointment_is_cancelled = false " + " and tp.is_active = 1 and tp.is_deleted = 0";
            booked = new BigInteger(objEntityManager.createNativeQuery(query1).getSingleResult().toString());
            String query2 = "SELECT count(tp.appointment_id) FROM trn_appointment tp LEFT JOIN mst_unit mu ON mu.unit_id = tp.appointment_unit_id " + " where tp.appointment_unit_id  =" + mstUnitlist.get(i).getUnitId() + "  and date(tp.created_date) between '" + fromdate + "' and '" + todate + "' " + " and tp.appointment_is_confirm = true and tp.appointment_is_cancelled = false " + " and tp.is_active = 1 and tp.is_deleted = 0";
            confirmed = new BigInteger(objEntityManager.createNativeQuery(query2).getSingleResult().toString());
            String query3 = "SELECT count(tp.appointment_id) FROM trn_appointment tp LEFT JOIN mst_unit mu ON mu.unit_id = tp.appointment_unit_id " + " where tp.appointment_unit_id  =" + mstUnitlist.get(i).getUnitId() + "  and date(tp.created_date) between '" + fromdate + "' and '" + todate + "' " + " and tp.appointment_is_confirm = false and tp.appointment_is_cancelled = true " + " and tp.is_active = 1 and tp.is_deleted = 0";
            cancelled = new BigInteger(objEntityManager.createNativeQuery(query3).getSingleResult().toString());
            System.out.println("booked" + query1);
            System.out.println("confirmed" + query2);
            System.out.println("cancelled" + query3);
            respMap.put("booked", booked);
            respMap.put("confirmed", confirmed);
            respMap.put("cancelled", cancelled);
            u.setUnitName(mstUnitlist.get(i).getUnitName());
            u.setAppoinmentcount(respMap);
            appoinmentWisePatientCountlist.add(u);
        }
        return appoinmentWisePatientCountlist;
    }

    @RequestMapping("registrationTariffWisePatientCount/{unitId}/{fromdate}/{todate}")
    public List<RegistrationTariffPatientCountDto> registrationTariffWisePatientCount(@RequestHeader("X-tenantId") String tenantName, @PathVariable String unitId, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object[]> respMapFinal = new HashMap<String, Object[]>();
        List<RegistrationTariffPatientCountDto> registrationTariffWisePatientCountList = new ArrayList<RegistrationTariffPatientCountDto>();
        List<RegistrationTariffDetailsDto> registraionTariffDetailsList = null;
        RegistrationTariffPatientCountDto g = null;
        RegistrationTariffDetailsDto registrationTariffDetailsDto = null;
        registraionTariffDetailsList = new ArrayList<RegistrationTariffDetailsDto>();
        g = new RegistrationTariffPatientCountDto();
        List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
        //String query2 = "select count(mv.visit_id),ifnull(mt.tariff_name,'Without Tariff') from mst_visit mv " + "LEFT JOIN mst_unit mu ON mu.unit_id = mv.visit_unit_id " + "LEFT JOIN mbill_tariff mt ON mt.tariff_id = mv.visit_tariff_id " + "where mv.visit_unit_id =" + unitId + " and " + "date(mv.created_date) between  '" + fromdate + "'   and  '" + todate + "'" + "group by mv.visit_tariff_id";
        String query2 = "select count(mv.visit_id),ifnull(mt.tariff_name,'Without Tariff') from mst_visit mv " + "LEFT JOIN mst_unit mu ON mu.unit_id = mv.visit_unit_id " + "LEFT JOIN mbill_tariff mt ON mt.tariff_id = mv.visit_tariff_id " + "where mv.visit_unit_id =" + unitId + " and " + "date(mv.created_date) between  '" + fromdate + "'   and  '" + todate + "'" + " and " + " mv.visit_tariff_id IS NOT NULL " + " group by mv.visit_tariff_id";
        finalDignosisCount = objEntityManager.createNativeQuery(query2).getResultList();
        for (Object[] obj : finalDignosisCount) {
            registrationTariffDetailsDto = new RegistrationTariffDetailsDto();
            registrationTariffDetailsDto.setRegistrationCount(Integer.parseInt(obj[0].toString()));
            registrationTariffDetailsDto.setTariffName(obj[1].toString());
            registraionTariffDetailsList.add(registrationTariffDetailsDto);
        }
        g.setRegistrationCount(registraionTariffDetailsList);
        g.setUnitName("");
        registrationTariffWisePatientCountList.add(g);
        return registrationTariffWisePatientCountList;
    }

    @RequestMapping("registrationLandMarkwisepatientreport/{fromdate}/{todate}")
    public List<RegistrationLandmarkatientCountDto> diagnosiswisepatientreport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object[]> respMapFinal = new HashMap<String, Object[]>();
        List<MstUnit> mstUnitlist = new ArrayList<MstUnit>();
        mstUnitlist = mstUnitRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        List<RegistrationLandmarkatientCountDto> landmarkWisePatientCountList = new ArrayList<RegistrationLandmarkatientCountDto>();
        List<RegistrationLandmarkDetailsDto> landmarkDetailsList = null;
        RegistrationLandmarkatientCountDto d = null;
        RegistrationLandmarkDetailsDto landmarkDetailsDto = null;
        for (int i = 0; i < mstUnitlist.size(); i++) {
            landmarkDetailsList = new ArrayList<RegistrationLandmarkDetailsDto>();
            d = new RegistrationLandmarkatientCountDto();
            List<Object[]> landmarkCount = new ArrayList<Object[]>();
            // String query2 = "SELECT COUNT(mv.visit_id) as count,ifnull(mu.user_area,'') as user_area " + "from mst_visit mv " + "left join  mst_patient mp ON mp.patient_id = mv.visit_patient_id  " + "left join  mst_user mu ON mu.user_id = mp.patient_user_id " + "left JOIN mst_unit u ON u.unit_id = mv.visit_unit_id  " + "where date(mv.created_date) between  '" + fromdate + "'   and  '" + todate + "' and " + "  mv.is_active = 1 and mv.is_deleted = 0" + " and mv.visit_unit_id =" + mstUnitlist.get(i).getUnitId() + "  " + " GROUP BY mu.user_area ORDER BY count DESC limit 10";
            String query2 = "SELECT COUNT(mv.visit_id) as count,ifnull(ml.landmark_name,'') as user_area " + "from mst_visit mv " + "left join  mst_patient mp ON mp.patient_id = mv.visit_patient_id  " + "left join  mst_user mu ON mu.user_id = mp.patient_user_id " + "left JOIN mst_unit u ON u.unit_id = mv.visit_unit_id  " + " LEFT JOIN mst_landmark ml ON mu.user_landmark_id = ml.landmark_id " + "where date(mv.created_date) between  '" + fromdate + "'   and  '" + todate + "' and " + "  mv.is_active = 1 and mv.is_deleted = 0" + " and mv.visit_unit_id =" + mstUnitlist.get(i).getUnitId() + "  " + " GROUP BY ml.landmark_name ORDER BY count DESC limit 10";
            landmarkCount = objEntityManager.createNativeQuery(query2).getResultList();
            for (Object[] obj : landmarkCount) {
                landmarkDetailsDto = new RegistrationLandmarkDetailsDto();
                landmarkDetailsDto.setLandmarkCount(Integer.parseInt(obj[0].toString()));
                landmarkDetailsDto.setLandmarkName(obj[1].toString());
                landmarkDetailsList.add(landmarkDetailsDto);
            }
            d.setRegLandmarkcount(landmarkDetailsList);
            d.setUnitName(mstUnitlist.get(i).getUnitName());
            landmarkWisePatientCountList.add(d);
        }
        return landmarkWisePatientCountList;
    }

    @RequestMapping("getVisitTrendWisePatientCount/{fromdate}/{todate}")
    public List<VisitTrendPatientCountDto> getVisitTrendWisePatientCount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object[]> respMapFinal = new HashMap<String, Object[]>();
        List<MstUnit> mstUnitlist = new ArrayList<MstUnit>();
        mstUnitlist = mstUnitRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        List<VisitTrendPatientCountDto> visitTrendPatientCountList = new ArrayList<VisitTrendPatientCountDto>();
        List<VisitTrendDetailsDto> visitTrendDetailsList = null;
        VisitTrendPatientCountDto g = null;
        VisitTrendDetailsDto visitTrendDetailsDto = null;
        for (int i = 0; i < mstUnitlist.size(); i++) {
            visitTrendDetailsList = new ArrayList<VisitTrendDetailsDto>();
            //Jitendra & Gayatri
            g = new VisitTrendPatientCountDto();
            List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
            String query2 = "select count(mv.visit_id) ,MONTHNAME(mv.created_date) " + "from mst_visit mv " + "left join  mst_unit u ON u.unit_id = mv.visit_unit_id " + " where   mv.is_active = 1 and mv.is_deleted = 0 and  mv.visit_unit_id = " + mstUnitlist.get(i).getUnitId() + " and mv.created_date >= DATE(NOW() - INTERVAL 5 MONTH) group by MONTHNAME(mv.created_date) ORDER BY FIELD('January','February','March','April','May','June','July','August','September','October','November','December')";
            finalDignosisCount = objEntityManager.createNativeQuery(query2).getResultList();
            System.out.print("  " + finalDignosisCount);
            for (Object[] obj : finalDignosisCount) {
                visitTrendDetailsDto = new VisitTrendDetailsDto();
                visitTrendDetailsDto.setMonthCount(Integer.parseInt(obj[0].toString()));
                visitTrendDetailsDto.setMonthName(obj[1].toString());
                visitTrendDetailsList.add(visitTrendDetailsDto);
            }
            g.setVtcount(visitTrendDetailsList);
            g.setUnitName(mstUnitlist.get(i).getUnitName());
            visitTrendPatientCountList.add(g);
        }
        return visitTrendPatientCountList;
    }

    @RequestMapping("diagnosisCountanAlysisReport/{today}/{fromdate}/{todate}/{diagnosisType}/{itemName}/{unitList}/{mstuserlist}/{page}/{size}")
    public ResponseEntity diagnosisCountanAlysisReport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("today") Boolean today, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("diagnosisType") String diagnosisType, @PathVariable("itemName") String itemName, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable Integer page, @PathVariable Integer size) {
        TenantContext.setCurrentTenant(tenantName);
        String query2 = "SELECT unit_name,date(tvd.created_date),COUNT(tvd.vd_id) as count ,mic.ic_description as description,mic.ic_code as code ," +
                "tvd.created_by,CAST(tvd.vd_is_final_diagnosis AS UNSIGNED) as status from temr_visit_diagnosis tvd " +
                "INNER JOIN memr_icd_code mic ON mic.ic_id = tvd.vd_ic_id " +
                "INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id " +
                "INNER JOIN mst_unit mu ON mu.unit_id = mv.visit_unit_id  where 1 ";
        String summeryQuery = "select COUNT(tvd.vd_id),mg.gender_name from temr_visit_diagnosis tvd " +
                "INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id " +
                "inner join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                "inner join mst_user mu on mp.patient_user_id=mu.user_id " +
                "inner join mst_gender mg on mu.user_gender_id=mg.gender_id where 1 ";
        if (today) {
            query2 += " and date(tvd.created_date)=curdate() ";
            summeryQuery += " and date(tvd.created_date)=curdate() ";
        } else {
            query2 += " and (date(tvd.created_date) between '" + fromdate + "' and '" + todate + "') ";
            summeryQuery += " and (date(tvd.created_date) between '" + fromdate + "' and '" + todate + "') ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and mv.visit_unit_id in (" + values + ") ";
            summeryQuery += " and mv.visit_unit_id in (" + values + ") ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            query2 += " and tvd.created_by_user_id in (" + values + ") ";
            summeryQuery += " and tvd.created_by_user_id in (" + values + ") ";
        }
        if (!itemName.equals("null") && !itemName.equals("")) {
            query2 += " and  mic.ic_description like '%" + itemName + "%' ";
            //summeryQuery += " and  mic.ic_description like '%" + itemName + "%' ";
        }
        if (diagnosisType.equals("0")) {
            query2 += " and tvd.vd_is_final_diagnosis = 0 ";
            summeryQuery += " and tvd.vd_is_final_diagnosis = 0 ";
        } else if (diagnosisType.equals("1")) {
            query2 += " and  tvd.vd_is_final_diagnosis = 1 ";
            summeryQuery += " and  tvd.vd_is_final_diagnosis = 1 ";
        }
        query2 += "  GROUP BY mv.visit_unit_id,tvd.vd_ic_id,date(tvd.created_date),tvd.created_by_user_id ORDER BY mv.visit_unit_id asc  ";
        System.out.println("Diagnostics Count Analysis" + query2);
        //  summeryQuery += " group by mu.user_gender_id  ";
        String countQuery = "select count(*) from (" + query2 + ") as temp";
        query2 += " limit " + ((page - 1) * size) + "," + size;
        String columnName = "unitName,created_date,patientCount,description,code,user,status";
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, query2, countQuery));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int patientTotalCount = 0;
        if (jsonArray.length() > 0) {
            //for get summery
            int[] a = {0, 12, 13, 17, 18, 60, 61, 120};
            for (int i = 0; i < a.length; i += 2) {
                String tempQuery = summeryQuery;
                tempQuery += " and mu.user_age>" + a[i] + " and mu.user_age<=" + a[i + 1] + " group by mu.user_gender_id  ";
                List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(tempQuery).getResultList();
                for (Object[] temp : obj) {
                    if (temp[1].toString().toLowerCase().equals("male")) {
                        try {
                            jsonArray.getJSONObject(0).put("M" + a[i] + "_" + a[i + 1], temp[0].toString());
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (temp[1].toString().toLowerCase().equals("female")) {
                        try {
                            jsonArray.getJSONObject(0).put("F" + a[i] + "_" + a[i + 1], temp[0].toString());
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            jsonArray.getJSONObject(0).put("O" + a[i] + "_" + a[i + 1], temp[0].toString());
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    patientTotalCount += Integer.parseInt(temp[0].toString());
                }
            }
        }
        //Total Patient Summery Count
        try {
            jsonArray.getJSONObject(0).put("patientTotalCount", patientTotalCount);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok(jsonArray.toString());
    }

    @RequestMapping("diagnosisCountanAlysisReportPrint/{today}/{fromdate}/{todate}/{diagnosisType}/{itemName}/{unitList}/{mstuserlist}/{page}/{size}/{unitId}/{userId}/{print}")
    public String diagnosisCountanAlysisReportPrint(@RequestHeader("X-tenantId") String tenantName, @PathVariable("today") Boolean today, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("diagnosisType") String diagnosisType, @PathVariable("itemName") String itemName, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable Integer page, @PathVariable Integer size, @PathVariable("unitId") long unitId, @PathVariable("userId") long userId, @PathVariable("print") Boolean print) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        String query2 = "SELECT unit_name,date(tvd.created_date),COUNT(tvd.vd_id) as count ,mic.ic_description as description,mic.ic_code as code ," +
                "tvd.created_by,CAST(tvd.vd_is_final_diagnosis AS UNSIGNED) as status from temr_visit_diagnosis tvd " +
                "INNER JOIN memr_icd_code mic ON mic.ic_id = tvd.vd_ic_id " +
                "INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id " +
                "INNER JOIN mst_unit mu ON mu.unit_id = mv.visit_unit_id  where 1 ";
        String summeryQuery = "select COUNT(tvd.vd_id),mg.gender_name from temr_visit_diagnosis tvd " +
                "INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id " +
                "inner join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                "inner join mst_user mu on mp.patient_user_id=mu.user_id " +
                "inner join mst_gender mg on mu.user_gender_id=mg.gender_id where 1 ";
        if (today) {
            query2 += " and date(tvd.created_date)=curdate() ";
            summeryQuery += " and date(tvd.created_date)=curdate() ";
        } else {
            query2 += " and (date(tvd.created_date) between '" + fromdate + "' and '" + todate + "') ";
            summeryQuery += " and (date(tvd.created_date) between '" + fromdate + "' and '" + todate + "') ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and mv.visit_unit_id in (" + values + ") ";
            summeryQuery += " and mv.visit_unit_id in (" + values + ") ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            query2 += " and tvd.created_by_user_id in (" + values + ") ";
            summeryQuery += " and tvd.created_by_user_id in (" + values + ") ";
        }
        if (!itemName.equals("null") && !itemName.equals("")) {
            query2 += " and  mic.ic_description like '%" + itemName + "%' ";
            //summeryQuery += " and  mic.ic_description like '%" + itemName + "%' ";
        }
        if (diagnosisType.equals("0")) {
            query2 += " and tvd.vd_is_final_diagnosis = 0 ";
            summeryQuery += " and tvd.vd_is_final_diagnosis = 0 ";
        } else if (diagnosisType.equals("1")) {
            query2 += " and  tvd.vd_is_final_diagnosis = 1 ";
            summeryQuery += " and  tvd.vd_is_final_diagnosis = 1 ";
        }
        query2 += "  GROUP BY mv.visit_unit_id,tvd.vd_ic_id,date(tvd.created_date),tvd.created_by_user_id ORDER BY mv.visit_unit_id asc  ";
        //  summeryQuery += " group by mu.user_gender_id  ";
        String countQuery = "select count(*) from (" + query2 + ") as temp";
        query2 += " limit " + ((page - 1) * size) + "," + size;
        String columnName = "unitName,created_date,patientCount,description,code,user,status";
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, query2, countQuery));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int patientTotalCount = 0;
        if (jsonArray.length() > 0) {
            //for get summery
            int[] a = {0, 12, 13, 17, 18, 60, 61, 120};
            for (int i = 0; i < a.length; i += 2) {
                String tempQuery = summeryQuery;
                tempQuery += " and mu.user_age>" + a[i] + " and mu.user_age<=" + a[i + 1] + " group by mu.user_gender_id  ";
                List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(tempQuery).getResultList();
                for (Object[] temp : obj) {
                    if (temp[1].toString().toLowerCase().equals("male")) {
                        try {
                            jsonArray.getJSONObject(0).put("M" + a[i] + "_" + a[i + 1], temp[0].toString());
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (temp[1].toString().toLowerCase().equals("female")) {
                        try {
                            jsonArray.getJSONObject(0).put("F" + a[i] + "_" + a[i + 1], temp[0].toString());
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            jsonArray.getJSONObject(0).put("O" + a[i] + "_" + a[i + 1], temp[0].toString());
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    patientTotalCount += Integer.parseInt(temp[0].toString());
                }
            }
        }
        //   return ResponseEntity.ok(jsonArray.toString());
        //  JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(unitId);
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(userId);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject jsonObjectUser = null;
        try {
            jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (print) {
            return createReport.createJasperReportXLSByJson("DiagnosticCountAnalysisReport", jsonArray.toString());
        } else {
            // return ResponseEntity.ok(jsonArray.toString());
            return createReport.createJasperReportPDFByJson("DiagnosticCountAnalysisReport", jsonArray.toString());
        }
    }


    private String getCaredeskLiveAppointmentTotalCount()
    {
        final String uri = "http://65.1.194.186:8082/caredesk_live/mis/get_patient_appointment_count";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        JSONArray jsonArray = new JSONArray(result);
        String total_appointment_count  = jsonArray.getJSONObject(0).getString("total_appointment_count");
        return total_appointment_count;
    }

    private String getCaredeskLiveAppointmentTodayCount()
    {
        final String uri = "http://65.1.194.186:8082/caredesk_live/mis/get_patient_appointment_count";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        JSONArray jsonArray = new JSONArray(result);
        String todays_appointment_count  = jsonArray.getJSONObject(1).getString("todays_appointment_count");
        return todays_appointment_count;
    }

    @RequestMapping("getFinalizedPatientCountToday")
    public HashMap<String, Object> searchTeleconsultationsCountToday(HttpServletRequest request) {
        String tokan = request.getHeader("token");

        System.out.println("getCaredeskLiveAppointmentTodayCount : " + getCaredeskLiveAppointmentTodayCount());
        TenantContext.setCurrentTenant("1");
        HashMap<String, Object> hashMap = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        if (tokan.equals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGVzYWFnYXIiLCJleHAiOjE2NDU0NTI5MjYsImlhdCI6MTY0NTQzNDkyNn0.5LKwZ4mvPeljOVsqaftwDGGjzx4C81733v9U-bDWtWMhqw91PyWX3tX1Q6RcdyauYglBDwVoASJ8a93qUKN49g")) {
            MainQuery = " SELECT COUNT(*) " +
                    "FROM temr_timeline tt " +
                    "INNER JOIN mst_visit mv ON tt.timeline_visit_id = mv.visit_id " +
                    "WHERE tt.isemrfinal = 1 AND mv.is_virtual = true AND tt.is_active=1 " +
                    "AND tt.is_deleted=0 AND date(tt.timeline_date) = CURDATE() ";
            BigInteger list = (BigInteger) entityManager.createNativeQuery(MainQuery).getSingleResult();
            System.out.println("list "+ list);
            if (list != null) {

                hashMap.put("status", "SUCCESS");
                hashMap.put("message", "Total count of Teleconsultations for Today ");
                hashMap.put("date", strDate);
                hashMap.put("New_Count", list);
                hashMap.put("Old_Count", Long.parseLong(getCaredeskLiveAppointmentTodayCount()));
//                System.out.println("total" + list + getCaredeskLiveAppointmentTotalCount());
//                hashMap.put("Total_Count", list.intValue() + Integer.parseInt(getCaredeskLiveAppointmentTotalCount()));
            } else {
                hashMap.put("status", "Failed");
                hashMap.put("message", "Total count of Teleconsultations for Today");
                hashMap.put("message", "No Records found");

            }

        } else {
//            hashMap.get("Invalid access token");
            hashMap.put("status", "Failed");
            hashMap.put("message", "Invalid access token");
//                 hashMap.put("message", "No Records found");

        }

        return hashMap;
    }

//    @GetMapping("getFinalizedPatientCount")
//    public HashMap<String, Object> searchTeleconsultationsCount(HttpServletRequest request) {
//
//        String tokan = request.getHeader("token");
//        System.out.println(tokan);
//        TenantContext.setCurrentTenant("1");
//        HashMap<String, Object> hashMap = new HashMap<>();
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ");
//        String strDate = formatter.format(date);
//        String MainQuery = " ";
//        if (tokan.equals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGVzYWFnYXIiLCJleHAiOjE2NDU0NTI5MjYsImlhdCI6MTY0NTQzNDkyNn0.5LKwZ4mvPeljOVsqaftwDGGjzx4C81733v9U-bDWtWMhqw91PyWX3tX1Q6RcdyauYglBDwVoASJ8a93qUKN49g")) {
//
//            MainQuery = " SELECT COUNT(*) " +
//                    "FROM temr_timeline tt " +
//                    "INNER JOIN mst_visit mv ON tt.timeline_visit_id = mv.visit_id " +
//                    "WHERE tt.isemrfinal = 1 AND mv.is_virtual = true AND tt.is_active=1 AND tt.is_deleted=0  ";
//            BigInteger con = (BigInteger) entityManager.createNativeQuery(MainQuery).getSingleResult();
//            System.out.println("con"+ con);
//            if (con != null) {
//
//                hashMap.put("status", "SUCCESS");
//                hashMap.put("message", "Total count of Teleconsultations till date");
//                hashMap.put("date", strDate);
//                hashMap.put("count", con.longValue());
//                hashMap.put("caredeskLiveAppointmentTotalCount", Long.parseLong(getCaredeskLiveAppointmentTotalCount()));
//                hashMap.put("Total_Count", con.longValue() + Long.parseLong(getCaredeskLiveAppointmentTotalCount()));
//            } else {
//                hashMap.put("status", "Failed");
//                hashMap.put("message", "Total count of Teleconsultations till date");
//                hashMap.put("message", "No Records found");
//            }
//        } else {
//            hashMap.put("error", "Invalid requrest");
//        }
//        return hashMap;
//    }

    @GetMapping("getFinalizedPatientCount")
    public HashMap<String, Object> searchTeleconsultationsCount(HttpServletRequest request) {

        String tokan = request.getHeader("token");
        System.out.println(tokan);
        TenantContext.setCurrentTenant("1");
        HashMap<String, Object> hashMap = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String MainQuery2 = " ";
        if (tokan.equals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGVzYWFnYXIiLCJleHAiOjE2NDU0NTI5MjYsImlhdCI6MTY0NTQzNDkyNn0.5LKwZ4mvPeljOVsqaftwDGGjzx4C81733v9U-bDWtWMhqw91PyWX3tX1Q6RcdyauYglBDwVoASJ8a93qUKN49g")) {

            MainQuery = " SELECT COUNT(*) " +
                    "FROM temr_timeline tt " +
                    "INNER JOIN mst_visit mv ON tt.timeline_visit_id = mv.visit_id " +
                    "WHERE tt.isemrfinal = 1 AND mv.is_virtual = true AND tt.is_active=1 AND tt.is_deleted=0  ";

            MainQuery2 = " SELECT COUNT(*) " +
                    "FROM temr_timeline tt " +
                    "INNER JOIN mst_visit mv ON tt.timeline_visit_id = mv.visit_id " +
                    "WHERE tt.isemrfinal = 1 AND mv.is_virtual = true AND tt.is_active=1 " +
                    "AND tt.is_deleted=0 AND date(tt.timeline_date) = CURDATE() ";

            BigInteger con = (BigInteger) entityManager.createNativeQuery(MainQuery).getSingleResult();
            BigInteger con2 = (BigInteger) entityManager.createNativeQuery(MainQuery2).getSingleResult();
            System.out.println("con"+ con);
            System.out.println("con2 "+ con2);
            if (con != null) {

                hashMap.put("status", "SUCCESS");
                hashMap.put("message", "Total count of Teleconsultations ");
                hashMap.put("date", strDate);
//                hashMap.put("Till_count", con.longValue());
//                hashMap.put("Todays_count", con2.longValue());
//                hashMap.put("caredeskLiveAppointmentTotalCount_Till", Long.parseLong(getCaredeskLiveAppointmentTotalCount()));
//                hashMap.put("caredeskLiveAppointmentTotalCount_Today", Long.parseLong(getCaredeskLiveAppointmentTodayCount()));
                hashMap.put("Total_Count", con.longValue() + Long.parseLong(getCaredeskLiveAppointmentTotalCount()));
                hashMap.put("Todays_Total_Count", con2.longValue() + Long.parseLong(getCaredeskLiveAppointmentTodayCount()));
            } else {
                hashMap.put("status", "Failed");
                hashMap.put("message", "Total count of Teleconsultations till date");
                hashMap.put("message", "No Records found");
            }
        } else {
            hashMap.put("error", "Invalid requrest");
        }
        return hashMap;
    }





  /*  @RequestMapping("diagnosisCountanAlysisReport/{today}/{fromdate}/{todate}/{diagnosisType}/{itemName}/{unitList}/{staffList}/{page}/{size}")
    public ResponseEntity diagnosisCountanAlysisReport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("today") Boolean today, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("diagnosisType") String diagnosisType, @PathVariable("itemName") String itemName, @PathVariable String[] unitList, @PathVariable String[] staffList, @PathVariable Integer page, @PathVariable Integer size) {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);

        String query2 = "SELECT unit_name,date(tvd.created_date),COUNT(tvd.vd_id) as count ,mic.ic_description as description,mic.ic_code as code ," +
                "tvd.created_by,CAST(tvd.vd_is_final_diagnosis AS UNSIGNED) as status from temr_visit_diagnosis tvd " +
                "INNER JOIN memr_icd_code mic ON mic.ic_id = tvd.vd_ic_id " +
                "INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id " +
                "INNER JOIN mst_unit mu ON mu.unit_id = mv.visit_unit_id  where 1 ";
        String summeryQuery = "select COUNT(tvd.vd_id),mg.gender_name from temr_visit_diagnosis tvd " +
                "INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id " +
                "inner join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                "inner join mst_user mu on mp.patient_user_id=mu.user_id " +
                "inner join mst_gender mg on mu.user_gender_id=mg.gender_id where 1 ";

        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }

        if (today) {
            query2 += " and date(tvd.created_date)=curdate() ";
            summeryQuery += " and date(tvd.created_date)=curdate() ";
        } else {
            query2 += " and (date(tvd.created_date) between '" + fromdate + "' and '" + todate + "') ";
            summeryQuery += " and (date(tvd.created_date) between '" + fromdate + "' and '" + todate + "') ";
        }


        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and mv.visit_unit_id in (" + values + ") ";
            summeryQuery += " and mv.visit_unit_id in (" + values + ") ";
        }

        if (!staffList[0].equals("null")) {
            String values = String.valueOf(staffList[0]);
            for (int i = 1; i < staffList.length; i++) {
                values += "," + staffList[i];
            }
            query2 += " and tvd.created_by_user_id in (" + values + ") ";
            summeryQuery += " and tvd.created_by_user_id in (" + values + ") ";
        }

        if (!itemName.equals("null") && !itemName.equals("")) {
            query2 += " and  mic.ic_description like '%" + itemName + "%' ";
            //summeryQuery += " and  mic.ic_description like '%" + itemName + "%' ";
        }

        if (diagnosisType.equals("0")) {
            query2 += " and tvd.vd_is_final_diagnosis = 0 ";
            summeryQuery += " and tvd.vd_is_final_diagnosis = 0 ";
        } else if (diagnosisType.equals("1")) {
            query2 += " and  tvd.vd_is_final_diagnosis = 1 ";
            summeryQuery += " and  tvd.vd_is_final_diagnosis = 1 ";
        }
        query2 += "  GROUP BY mv.visit_unit_id,tvd.vd_ic_id,date(tvd.created_date),tvd.created_by_user_id ORDER BY mv.visit_unit_id asc  ";
        //  summeryQuery += " group by mu.user_gender_id  ";

        String countQuery = "select count(*) from (" + query2 + ") as temp";
        query2 += " limit " + ((page - 1) * size) + "," + size;
        String columnName = "unitName,created_date,patientCount,description,code,user,status";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, query2, countQuery));


        int patientTotalCount=0;
        if (jsonArray.length() > 0) {

            //for get summery
            int[] a = {0, 12, 13, 17, 18, 60, 61, 120};
            for (int i = 0; i < a.length; i += 2) {
                String tempQuery = summeryQuery;
                tempQuery += " and mu.user_age>" + a[i] + " and mu.user_age<=" + a[i + 1] + " group by mu.user_gender_id  ";
                List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(tempQuery).getResultList();
                for (Object[] temp : obj) {
                    if (temp[1].toString().toLowerCase().equals("male")) {
                        jsonArray.getJSONObject(0).put("M" + a[i] + "_" + a[i + 1], temp[0].toString());
                    } else if (temp[1].toString().toLowerCase().equals("female")) {
                        jsonArray.getJSONObject(0).put("F" + a[i] + "_" + a[i + 1], temp[0].toString());
                    } else {
                        jsonArray.getJSONObject(0).put("O" + a[i] + "_" + a[i + 1], temp[0].toString());
                    }
                    patientTotalCount+=Integer.parseInt(temp[0].toString());
                }
            }
        }

        //Total Patient Summery Count
        jsonArray.getJSONObject(0).put("patientTotalCount", patientTotalCount);

        return ResponseEntity.ok(jsonArray.toString());
    }



    @RequestMapping("diagnosisCountanAlysisReportPrint/{today}/{fromdate}/{todate}/{diagnosisType}/{itemName}/{unitList}/{staffList}/{page}/{size}/{unitId}/{userId}/{print}")
    public String diagnosisCountanAlysisReportPrint(@RequestHeader("X-tenantId") String tenantName, @PathVariable("today") Boolean today, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("diagnosisType") String diagnosisType, @PathVariable("itemName") String itemName, @PathVariable String[] unitList, @PathVariable String[] staffList, @PathVariable Integer page, @PathVariable Integer size, @PathVariable("unitId") long unitId, @PathVariable("userId") long userId, @PathVariable("print") Boolean print) throws JsonProcessingException {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);

        String query2 = "SELECT unit_name,date(tvd.created_date),COUNT(tvd.vd_id) as count ,mic.ic_description as description,mic.ic_code as code ," +
                "tvd.created_by,CAST(tvd.vd_is_final_diagnosis AS UNSIGNED) as status from temr_visit_diagnosis tvd " +
                "INNER JOIN memr_icd_code mic ON mic.ic_id = tvd.vd_ic_id " +
                "INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id " +
                "INNER JOIN mst_unit mu ON mu.unit_id = mv.visit_unit_id  where 1 ";
        String summeryQuery = "select COUNT(tvd.vd_id),mg.gender_name from temr_visit_diagnosis tvd " +
                "INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id " +
                "inner join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                "inner join mst_user mu on mp.patient_user_id=mu.user_id " +
                "inner join mst_gender mg on mu.user_gender_id=mg.gender_id where 1 ";


        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }

        if (today) {
            query2 += " and date(tvd.created_date)=curdate() ";
            summeryQuery += " and date(tvd.created_date)=curdate() ";
        } else {
            query2 += " and (date(tvd.created_date) between '" + fromdate + "' and '" + todate + "') ";
            summeryQuery += " and (date(tvd.created_date) between '" + fromdate + "' and '" + todate + "') ";
        }


        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and mv.visit_unit_id in (" + values + ") ";
            summeryQuery += " and mv.visit_unit_id in (" + values + ") ";
        }

        if (!staffList[0].equals("null")) {
            String values = String.valueOf(staffList[0]);
            for (int i = 1; i < staffList.length; i++) {
                values += "," + staffList[i];
            }
            query2 += " and tvd.created_by_user_id in (" + values + ") ";
            summeryQuery += " and tvd.created_by_user_id in (" + values + ") ";
        }

        if (!itemName.equals("null") && !itemName.equals("")) {
            query2 += " and  mic.ic_description like '%" + itemName + "%' ";
            //summeryQuery += " and  mic.ic_description like '%" + itemName + "%' ";
        }

        if (diagnosisType.equals("0")) {
            query2 += " and tvd.vd_is_final_diagnosis = 0 ";
            summeryQuery += " and tvd.vd_is_final_diagnosis = 0 ";
        } else if (diagnosisType.equals("1")) {
            query2 += " and  tvd.vd_is_final_diagnosis = 1 ";
            summeryQuery += " and  tvd.vd_is_final_diagnosis = 1 ";
        }
        query2 += "  GROUP BY mv.visit_unit_id,tvd.vd_ic_id,date(tvd.created_date),tvd.created_by_user_id ORDER BY mv.visit_unit_id asc  ";
        //  summeryQuery += " group by mu.user_gender_id  ";

        String countQuery = "select count(*) from (" + query2 + ") as temp";
        query2 += " limit " + ((page - 1) * size) + "," + size;
        String columnName = "unitName,created_date,patientCount,description,code,user,status";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, query2, countQuery));


        int patientTotalCount=0;
        if (jsonArray.length() > 0) {

            //for get summery
            int[] a = {0, 12, 13, 17, 18, 60, 61, 120};
            for (int i = 0; i < a.length; i += 2) {
                String tempQuery = summeryQuery;
                tempQuery += " and mu.user_age>" + a[i] + " and mu.user_age<=" + a[i + 1] + " group by mu.user_gender_id  ";
                List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(tempQuery).getResultList();
                for (Object[] temp : obj) {
                    if (temp[1].toString().toLowerCase().equals("male")) {
                        jsonArray.getJSONObject(0).put("M" + a[i] + "_" + a[i + 1], temp[0].toString());
                    } else if (temp[1].toString().toLowerCase().equals("female")) {
                        jsonArray.getJSONObject(0).put("F" + a[i] + "_" + a[i + 1], temp[0].toString());
                    } else {
                        jsonArray.getJSONObject(0).put("O" + a[i] + "_" + a[i + 1], temp[0].toString());
                    }
                    patientTotalCount+=Integer.parseInt(temp[0].toString());
                }
            }
        }

        //   return ResponseEntity.ok(jsonArray.toString());
        //  JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(unitId);
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(userId);
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);

        if (print) {
            return createReport.createJasperReportXLSByJson("DiagnosticCountAnalysisReport", jsonArray.toString());
        } else {
            // return ResponseEntity.ok(jsonArray.toString());
            return createReport.createJasperReportPDFByJson("DiagnosticCountAnalysisReport", jsonArray.toString());
        }
    }
*/
}
