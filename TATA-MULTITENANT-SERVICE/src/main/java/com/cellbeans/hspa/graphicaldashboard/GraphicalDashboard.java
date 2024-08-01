package com.cellbeans.hspa.graphicaldashboard;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mis.CountReprotMIS.DiagnosisDetailsDto;
import com.cellbeans.hspa.mis.CountReprotMIS.DiagnosisWisePatientCountDto;
import com.cellbeans.hspa.mstgender.MstGenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.sql.Date;
@RestController
@RequestMapping("/graphicaldashboard")
public class GraphicalDashboard {
    Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();

    @PersistenceContext
    EntityManager objEntityManager;

    @Autowired
    MstGenderRepository mstGenderRepository;
//    this method is rewritten bellow by Rohit .... plz don't  delete this method
//    @RequestMapping("getwisepatientreport/{fromdate}/{todate}/{fromage}/{toage}/{unitid}")
//    public Map<String, BigInteger> getActivityPatientCount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("fromage") int fromage, @PathVariable("toage") int toage, @PathVariable("unitid") long unitid) {
//        Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();
//
//        BigInteger givenAgeCount;
//        BigInteger from0To1Count;
//        BigInteger from2to6Count;
//        BigInteger from7To12Count;
//        BigInteger from13To18Count;
//        BigInteger from19to30Count;
//        BigInteger from31To45Count;
//        BigInteger from46To60Count;
//        BigInteger from61AndAboveCount;
//
//
//        //    unitlistid = removeLastChar(unitlistid);
//        if (toage != 0) {
//
//            String query1 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between (" + fromage + " and " + toage + " ) and  mv.is_active = 1 and mv.is_deleted = 0 ";
//            givenAgeCount = new BigInteger(objEntityManager.createNativeQuery(query1).getSingleResult().toString());
//            respMap.put("givenAgeCount", givenAgeCount);
//        } else {
//
//            String query1 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 0 and  1 and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id = " + unitid;
//            System.out.println(query1);
//            from0To1Count = new BigInteger(objEntityManager.createNativeQuery(query1).getSingleResult().toString());
//            //        doctororderservice_today =new BigInteger(objEntityManager.createNativeQuery(dos1).getSingleResult().toString());
//            String query2 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 1 and  6 and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id = " + unitid;
//            from2to6Count = new BigInteger(objEntityManager.createNativeQuery(query2).getSingleResult().toString());
//
//
//            String query3 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 6 and  12 and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id = " + unitid;
//            from7To12Count = new BigInteger(objEntityManager.createNativeQuery(query3).getSingleResult().toString());
//
//
//            String query4 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 12 and 18  and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id = " + unitid;
//            from13To18Count = new BigInteger(objEntityManager.createNativeQuery(query4).getSingleResult().toString());
//
//
//            String query5 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 18 and  30  and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id = " + unitid;
//            from19to30Count = new BigInteger(objEntityManager.createNativeQuery(query5).getSingleResult().toString());
//
//            String query6 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 30 and 45  and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id = " + unitid;
//            from31To45Count = new BigInteger(objEntityManager.createNativeQuery(query6).getSingleResult().toString());
//            String query7 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 45 and 60  and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id = " + unitid;
//            from46To60Count = new BigInteger(objEntityManager.createNativeQuery(query7).getSingleResult().toString());
//
//            String query8 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age > 60  and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id = " + unitid;
//            from61AndAboveCount = new BigInteger(objEntityManager.createNativeQuery(query8).getSingleResult().toString());
//
//            respMap.put("from0To1Count", from0To1Count);
//            respMap.put("from2to6Count", from2to6Count);
//
//
//            respMap.put("from7To12Count", from7To12Count);
//            respMap.put("from13To18Count", from13To18Count);
//            respMap.put("from19to30Count", from19to30Count);
//            respMap.put("from31To45Count", from31To45Count);
//            respMap.put("from46To60Count", from46To60Count);
//            respMap.put("from61AndAboveCount", from61AndAboveCount);
//
//        }
//
//
//        return respMap;
//    }

    // Rewritten because needed all units count in single method
    @RequestMapping("getwisepatientreport/{fromdate}/{todate}/{fromage}/{toage}/{unitid}")
    public Map<String, BigInteger> getActivityPatientCount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("fromage") int fromage, @PathVariable("toage") int toage, @PathVariable("unitid") Long[] unitid) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();
        BigInteger givenAgeCount;
        BigInteger from0To1Count;
        BigInteger from2to6Count;
        BigInteger from7To12Count;
        BigInteger from13To18Count;
        BigInteger from19to30Count;
        BigInteger from31To45Count;
        BigInteger from46To60Count;
        BigInteger from61AndAboveCount;
        String unitString = unitid[0].toString();
        for (int i = 1; i < unitid.length; i++) {
            unitString += "," + unitid[i];
        }
        //    unitlistid = removeLastChar(unitlistid);
        if (toage != 0) {
            String query1 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between (" + fromage + " and " + toage + " ) and  mv.is_active = 1 and mv.is_deleted = 0 ";
            givenAgeCount = new BigInteger(objEntityManager.createNativeQuery(query1).getSingleResult().toString());
            System.out.println("Age-wise Patient Count"+query1);
            respMap.put("givenAgeCount", givenAgeCount);
        } else {
            String query1 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 0 and  1 and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id in ( " + unitString + " ) ";
            from0To1Count = new BigInteger(objEntityManager.createNativeQuery(query1).getSingleResult().toString());
            //        doctororderservice_today =new BigInteger(objEntityManager.createNativeQuery(dos1).getSingleResult().toString());
            String query2 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 1 and  6 and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id in ( " + unitString + " ) ";
            from2to6Count = new BigInteger(objEntityManager.createNativeQuery(query2).getSingleResult().toString());
            String query3 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 6 and  12 and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id in ( " + unitString + " ) ";
            from7To12Count = new BigInteger(objEntityManager.createNativeQuery(query3).getSingleResult().toString());
            String query4 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 12 and 18  and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id in ( " + unitString + " ) ";
            from13To18Count = new BigInteger(objEntityManager.createNativeQuery(query4).getSingleResult().toString());
            String query5 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 18 and  30  and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id in ( " + unitString + " ) ";
            from19to30Count = new BigInteger(objEntityManager.createNativeQuery(query5).getSingleResult().toString());
            String query6 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 30 and 45  and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id in ( " + unitString + " ) ";
            from31To45Count = new BigInteger(objEntityManager.createNativeQuery(query6).getSingleResult().toString());
            String query7 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age between 45 and 60  and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id in ( " + unitString + " ) ";
            from46To60Count = new BigInteger(objEntityManager.createNativeQuery(query7).getSingleResult().toString());
            String query8 = "select count(mv.visit_id) from mst_visit mv inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id inner join  mst_user mu ON mu.user_id = mp.patient_user_id where DATE_FORMAT(mv.created_date,'%y-%m-%d') between  CAST('" + fromdate + "' as DATE)  and  CAST('" + todate + "' as DATE) and mu.user_age > 60  and  mv.is_active = 1 and mv.is_deleted = 0 and visit_unit_id in ( " + unitString + " ) ";
            from61AndAboveCount = new BigInteger(objEntityManager.createNativeQuery(query8).getSingleResult().toString());
            respMap.put("from0To1Count", from0To1Count);
            respMap.put("from2to6Count", from2to6Count);
            respMap.put("from7To12Count", from7To12Count);
            respMap.put("from13To18Count", from13To18Count);
            respMap.put("from19to30Count", from19to30Count);
            respMap.put("from31To45Count", from31To45Count);
            respMap.put("from46To60Count", from46To60Count);
            respMap.put("from61AndAboveCount", from61AndAboveCount);
        }

        return respMap;
    }

    @RequestMapping("diagnosiswisepatientreport/{fromdate}/{todate}/{diagnosisType}/{unitid}")
    public Map<String, List> diagnosiswisepatientreport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("diagnosisType") String diagnosisType, @PathVariable("unitid") Long[] unitid) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        boolean type = false;
        List<DiagnosisWisePatientCountDto> DiagnosisWisePatientCountList = new ArrayList<DiagnosisWisePatientCountDto>();
        List<DiagnosisDetailsDto> diagnosisDetailsList = null;
        DiagnosisWisePatientCountDto d = null;
        DiagnosisDetailsDto diagnosisDetailsDto = null;
        String unitString = unitid[0].toString();
        for (int i = 1; i < unitid.length; i++) {
            unitString += "," + unitid[i];
        }
        diagnosisDetailsList = new ArrayList<DiagnosisDetailsDto>();
        String query2 = "SELECT COUNT(tvd.vd_id) as count ,mic.ic_description as description,mic.ic_code as code from temr_visit_diagnosis tvd INNER JOIN memr_icd_code mic ON mic.ic_id = tvd.vd_ic_id INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id INNER JOIN mst_unit mu ON mu.unit_id = mv.visit_unit_id  where date(tvd.created_date) between '" + fromdate + "'   and  '" + todate + "'   ";
        if (diagnosisType.equals("0")) {
            query2 += " and tvd.vd_is_final_diagnosis = 0 ";
        } else if (diagnosisType.equals("1")) {
            query2 += " and  tvd.vd_is_final_diagnosis = 1 ";
        }
        query2 += " and mv.visit_unit_id in (" + unitString + ") GROUP BY vd_ic_id ORDER BY count DESC limit 5 ";
        d = new DiagnosisWisePatientCountDto();
        List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
        System.out.println("Top 5 Diagnosis (ICD-10)"+query2);
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
        respMap.put("diagnosisDetailsList", diagnosisDetailsList);
        return respMap;
    }

    @RequestMapping("getpatientcountregistrationunitwise/{fromdate}/{todate}/{unitid}/{checkzero}")
    public Map<String, List> patientcountregistrationunitwise(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("unitid") long unitid, @PathVariable("checkzero") boolean checkzero) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String query = "select unit_table.unit_name,ifnull(count_table.patient_count,0) as totalCollection  from " + " ( select mu.unit_id,mu.unit_name from mst_unit mu where mu.is_active=1 and mu.is_deleted = 0 ) as unit_table " + " left join " + " ( select mst_visit.visit_unit_id,count(*) as patient_count from  mst_visit " + " left join mst_unit on mst_unit.unit_id = mst_visit.visit_unit_id " + " where mst_visit.is_active=1 and mst_visit.is_deleted=0 " + " and  DATE(mst_visit.created_date) between '" + fromdate + "' and '" + todate + "'" + " group by mst_visit.visit_unit_id " + " ) as count_table " + "on unit_table.unit_id = count_table.visit_unit_id ";
        if (checkzero) {
            query += " where count_table.patient_count != 0";
        }
        query += " order by unit_table.unit_id";
        System.out.println("Registration Unit Wise Patient Count" +query);
        List<String> patientcount = objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("patientcount", patientcount);
        return respMap;
    }

    @RequestMapping("getalltariffnamefortariffwisereport")
    public Map<String, List> tariffnamefortariffwisereport(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String query = "select tariff_name from mbill_tariff group by replace(replace(replace(replace(tariff_name,'\\t',''),' ',''),'.',''),',','')";
        List<String> tariffNames = (List<String>) objEntityManager.createNativeQuery(query).getResultList();
        System.out.println("Registration Tariff Wise Patient Count"+query);
        respMap.put("tariffname", tariffNames);
        return respMap;
    }

    @RequestMapping("getpatientcountregistrationtariffwise/{fromdate}/{todate}/{unitids}/{tariffnames}/{checkval}")
    public Map<String, List> patientcountregistrationtariffwise(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("unitids") Long[] unitids, @PathVariable("tariffnames") String[] tariffnames, @PathVariable("checkval") boolean checkval) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String unitString = unitids[0].toString();
        for (int i = 1; i < unitids.length; i++) {
            unitString += "," + unitids[i];
        }
        String tariffString = "'" + tariffnames[0].toString().replaceAll("_", "") + "'";
        for (int i = 1; i < tariffnames.length; i++) {
            tariffString += ",'" + tariffnames[i].toString().replaceAll("_", "") + "'";
        }
        String query = "select master.UNITNAME as unitName,master.MTARIFFNAME as TariffName,ifnull(master.count,0) as patientCount from  " + " ( select * from " + " ( select u.unit_id as UNITID,u.unit_name as UNITNAME, " + " replace(replace(replace(replace(t.tariff_name,'\\t',''),' ',''),'.',''),',','') " + " as MTARIFFNAME from mbill_tariff t,mst_unit u " + " where t.tariffunit_id = u.unit_id and t.is_deleted = 0 and t.is_active = 1 ) as unit_table " + " left join ( " + " select mst_visit.visit_unit_id, " + " replace(replace(replace(replace(mbill_tariff.tariff_name,'\\t',''),' ',''),'.',''),',','') as tariffName, " + " count(*) as count from mst_visit " + " left join mbill_tariff on mbill_tariff.tariff_id = mst_visit.visit_tariff_id " + " where mst_visit.is_active=1 and mst_visit.is_deleted=0 " + " and DATE(mst_visit.created_date) between '" + fromdate + "' and ' " + todate + "'" + " group by mst_visit.visit_unit_id,tariffName order by mst_visit.visit_unit_id desc )  as count_table " + " on unit_table.UNITID=count_table.visit_unit_id and unit_table.MTARIFFNAME=count_table.tariffName )  as master " + " where master.MTARIFFNAME in (" + tariffString + ") and master.UNITID in ";
        if (checkval) {
            query += " ( select visit_unit_id from mst_visit " + " left join mbill_tariff on mbill_tariff.tariff_id = mst_visit.visit_tariff_id " + " where mst_visit.is_active=1 and mst_visit.is_deleted=0 " + " and mbill_tariff.tariff_id is not null " + " and  visit_unit_id in (" + unitString + " ) " + " and DATE(mst_visit.created_date) between '" + fromdate + "' and '" + todate + "' " + " group by mst_visit.visit_unit_id ) ";
        } else {
            query += " ( " + unitString + " )";
        }
        List<String> patientcount = (List<String>) objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("patientcount", patientcount);
        return respMap;
    }

    @RequestMapping("gettotalcollectionunitwiseservice/{fromdate}/{todate}/{unitid}/{checkzero}")
    public Map<String, List> totalcollectionunitwise(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("unitid") long unitid, @PathVariable("checkzero") boolean checkzero) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String query = "select unit_table.unit_name,ifnull(count_table.total,0) as totalCollection  from " + " ( select mu.unit_id,mu.unit_name from mst_unit mu where mu.is_active=1 and mu.is_deleted = 0 ) as unit_table " + " left join " + " ( select tb.tbill_unit_id,mu.unit_name,mpm.pm_name,sum(br_payment_amount) as total from tbill_reciept tr  " + "   left join tbill_bill tb on tb.bill_id = tr.br_bill_id " + "   left join mst_unit mu on mu.unit_id = tb.tbill_unit_id " + "   left join mst_payment_mode mpm on mpm.pm_id = tr.br_pm_id " + "   where  tr.is_active= '1' and tr.is_deleted = '0' and DATE(tr.created_date) between '" + fromdate + "' and '" + todate + "'" + "   group by tb.tbill_unit_id,tr.br_pm_id " + " ) as count_table " + "on unit_table.unit_id = count_table.tbill_unit_id ";
        if (checkzero) {
            query += " where count_table.total != 0";
        }
        query += " order by unit_table.unit_id";
        System.out.println("Collection Report"+ query);
        List<String> patientcount = objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("patientcount", patientcount);
        return respMap;
    }

    @RequestMapping("gettopfiveservices")
    public Map<String, List> gettopfiveservices(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String query = "  select ms.service_name as serviceName,count(tbbs.bs_service_id) as serviceCount  from mbill_service ms" + " left join tbill_bill_service tbbs on tbbs.bs_service_id = ms.service_id" + " group by tbbs.bs_service_id order by serviceCount desc limit 5";
       System.out.println("Service Count Unit-Wise"+query);
        List<String> topfiveservices = objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("topfiveservices", topfiveservices);
        return respMap;
    }

    @RequestMapping("getpatientcountfortopservice/{fromdate}/{todate}/{unitids}/{checkval}")
    public Map<String, List> patientcountfortopservice(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("unitids") Long[] unitids, @PathVariable("checkval") boolean checkval) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String unitString = unitids[0].toString();
        for (int i = 1; i < unitids.length; i++) {
            unitString += "," + unitids[i];
        }
        String query = " select service_table.unit_name,service_table.service_name,ifnull(calTab.serviceCount,0) as serviceCount from " + " (  select s.service_id, s.service_name,u.unit_id,u.unit_name from mst_unit u cross join mbill_service s ) as service_table " + " left join " + " ( select tb.tbill_unit_id,tbbs.bs_service_id,ms.service_name,count(tbbs.bs_service_id) as serviceCount  from mbill_service ms " + " left join tbill_bill_service tbbs on tbbs.bs_service_id = ms.service_id " + " left join tbill_bill tb on tb.bill_id = tbbs.bs_bill_id " + " where DATE(tbbs.created_date) between '" + fromdate + "' and '" + todate + "'" + " group by tbbs.bs_service_id,tb.tbill_unit_id order by tb.tbill_unit_id desc) as calTab  " + " on service_table.unit_id=calTab.tbill_unit_id and service_table.service_id=calTab.bs_service_id  " + " where service_table.service_id in ( select * from ( " + "  select bs_service_id  from tbill_bill_service tbbs " + "  left join mbill_service ms on ms.service_id = tbbs.bs_service_id " + "   GROUP BY bs_service_id  order by count(*) desc limit 5 ) as top_service ) and service_table.unit_id in ";
        if (checkval) {
            query += "( select service_table.unit_id from " + " ( select s.service_id, s.service_name,u.unit_id,u.unit_name from mst_unit u cross join mbill_service s ) as service_table " + " left join  " + " ( select tb.tbill_unit_id,tbbs.bs_service_id,ms.service_name,count(tbbs.bs_service_id) as serviceCount  from mbill_service ms " + " left join tbill_bill_service tbbs on tbbs.bs_service_id = ms.service_id " + " left join tbill_bill tb on tb.bill_id = tbbs.bs_bill_id " + " where DATE(tbbs.created_date) between '" + fromdate + "' and '" + todate + "'" + " group by tbbs.bs_service_id,tb.tbill_unit_id order by tb.tbill_unit_id desc) as calTab  " + " on service_table.unit_id=calTab.tbill_unit_id and service_table.service_id=calTab.bs_service_id  " + " where service_table.service_id in ( select * from ( " + "  select bs_service_id  from tbill_bill_service tbbs " + "  left join mbill_service ms on ms.service_id = tbbs.bs_service_id " + "  GROUP BY bs_service_id  order by count(*) desc limit 5 ) as top_service )  " + " and service_table.unit_id in ( " + unitString + " )  " + "  group by service_table.unit_name  having sum(ifnull(calTab.serviceCount,0)) != 0 )";

        } else {
            query += " ( " + unitString + ") ";
        }
        query += " order by service_table.unit_id,service_table.service_id";
        List<String> patientcount = (List<String>) objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("patientcount", patientcount);
        return respMap;
    }

    @RequestMapping("getappointmentstatuscountunitwise/{fromdate}/{todate}/{unitids}/{checkval}")
    public Map<String, List> appointmentstatuscountunitwise(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("unitids") Long[] unitids, @PathVariable("checkval") boolean checkval) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String unitString = unitids[0].toString();
        for (int i = 1; i < unitids.length; i++) {
            unitString += "," + unitids[i];
        }
        String query = " select unittab.unit_name as Unitname,ifnull(caltab.booked,0) as Booked,ifnull(caltab.confirmed,0) as Confirmed,ifnull(caltab.recheduled,0) as Rescheduled,ifnull(caltab.cancelled,0) as Cancelled from " + " ( select u.unit_id,u.unit_name from mst_unit u where u.is_active AND u.is_deleted =0 ) as unittab " + " left join " + " ( SELECT a.appointment_unit_id, " + "    COUNT(CASE WHEN a.appointment_is_cancelled = 1 and at.at_name not like '%r%e%s%c%h%e%d%u%l%e%' THEN 1 END) AS cancelled, " + "    COUNT(CASE WHEN a.appointment_is_confirm = 1 and a.appointment_is_cancelled = 0 and at.at_name not like '%r%e%s%c%h%e%d%u%l%e%'  THEN 1 END) AS confirmed, " + "    COUNT(CASE WHEN a.appointment_is_confirm = 0 and a.appointment_is_cancelled = 0 and  at.at_name not like '%r%e%s%c%h%e%d%u%l%e%' THEN 1 END) AS booked, " + "    COUNT(CASE WHEN a.appointment_is_confirm = 0 and a.appointment_is_cancelled = 0 and at.at_name like '%r%e%s%c%h%e%d%u%l%e%' THEN 1 END) AS recheduled " + " FROM trn_appointment a " + " left join mst_appointment_type at on at.at_id = a.appointment_at_id" + " where a.is_deleted = 0 and a.is_active = 1 and DATE(a.created_date) between '" + fromdate + "' and '" + todate + "' " + " group by a.appointment_unit_id ) as caltab " + " on unittab.unit_id = caltab.appointment_unit_id " + " where unittab.unit_id in ( " + unitString + " )";
        if (checkval) {
            query += " and unittab.unit_id in ( select  a.appointment_unit_id from trn_appointment  a group by a.appointment_unit_id ) ";
        } else {
        }
        query += " order by unittab.unit_id";
        System.out.println("Appointment status Patient Count"+query);
        List<String> patientcount = (List<String>) objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("patientcount", patientcount);
        return respMap;
    }

    @RequestMapping("getmonthlypatientfootfallunitwise/{fromdate}/{todate}/{unitids}/{checkval}")
    public Map<String, List> monthlypatientfootfallunitwise(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("unitids") Long[] unitids, @PathVariable("checkval") boolean checkval) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String unitString = unitids[0].toString();
        for (int i = 1; i < unitids.length; i++) {
            unitString += "," + unitids[i];
        }
        String query = " select Combo.unit_name,Combo.MONTH,ifnull(cc.count,0) as pc from " + "  ( select * from  " + " (select u.unit_id,u.unit_name from mst_unit u where u.is_deleted = 0 and u.is_active = 1 ) unittab " + " cross join ( " + " SELECT m.MONTH,m.YEAR FROM  ( " + "select MONTHNAME(DATE_SUB(now(), INTERVAL 5 MONTH)) AS MONTH ,YEAR(DATE_SUB(now(), INTERVAL 5 MONTH)) AS YEAR  UNION " + "select MONTHNAME(DATE_SUB(now(), INTERVAL 4 MONTH)) AS MONTH ,YEAR(DATE_SUB(now(), INTERVAL 5 MONTH)) AS YEAR UNION " + "select MONTHNAME(DATE_SUB(now(), INTERVAL 3 MONTH)) AS MONTH ,YEAR(DATE_SUB(now(), INTERVAL 5 MONTH)) AS YEAR UNION " + "select MONTHNAME(DATE_SUB(now(), INTERVAL 2 MONTH)) AS MONTH ,YEAR(DATE_SUB(now(), INTERVAL 5 MONTH)) AS YEAR UNION " + "select MONTHNAME(DATE_SUB(now(), INTERVAL 1 MONTH)) AS MONTH ,YEAR(DATE_SUB(now(), INTERVAL 5 MONTH)) AS YEAR UNION " + "select MONTHNAME(now()) AS MONTH ,YEAR(now()) AS YEAR) m ) as monthtab ) as Combo " + " left join ( " + "select mv.visit_unit_id,YEAR(created_date) as YEAR,MONTHNAME(created_date) as MONTH,count(*) as count from mst_visit mv " + "where mv.is_deleted = 0 and mv.is_active = 1 group by mv.visit_unit_id,MONTHNAME(created_date) order by YEAR(created_date),MONTH(created_date) ) as cc " + "on cc.visit_unit_id = Combo.unit_id and cc.YEAR = Combo.YEAR and cc.MONTH = Combo.MONTH " + " where Combo.unit_id in ( " + unitString + "  ) ";
//
//        if(checkval){
//            query+= "  ";
//        }else {
//        }
//        query+=" ";
        List<String> patientcount = (List<String>) objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("patientcount", patientcount);
        return respMap;
    }

    @RequestMapping("getindentcountunitwise/{fromdate}/{todate}/{unitids}/{checkval}")
    public Map<String, List> indentcountunitwise(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("unitids") Long[] unitids, @PathVariable("checkval") boolean checkval) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String unitString = unitids[0].toString();
        for (int i = 1; i < unitids.length; i++) {
            unitString += "," + unitids[i];
        }
        String query = " select unitTable.unit_name, " + " ifnull(comboTab.Approved,0) as Approved," + " ifnull(comboTab.Pending,0) as Pending," + " ifnull(comboTab.Intransit,0) as Intransit," + " ifnull(comboTab.Recieved,0) as Recieved," + " ifnull(comboTab.return,0) as 'Return'," + "  ifnull(comboTab.Total,0) as Total from  " + " (select mu.unit_id,mu.unit_name from mst_unit mu where mu.is_active = 1 and mu.is_deleted = 0 ) as unitTable " + " left join ( " + " select indendTab.store_indent_unit_id,indendTab.Approved," + " indendTab.Pending,indendTab.Intransit," + " indendTab.Recieved," + " ifnull(returnTab.returnCount,0) as 'return'," + " indendTab.Total from  " + "(select si.store_indent_unit_id," + "    SUM(CASE WHEN si.si_indent_approve = 1 THEN 1 ELSE 0 END) AS 'Approved'," + "    SUM(CASE WHEN si.si_indent_approve = 0 THEN 1 ELSE 0 END) AS 'Pending'," + "    SUM(CASE WHEN ic.ic_id is not NULL and si.si_indent_approve = 1 and issue_indent_status = 0  THEN 1 ELSE 0 END) AS 'Intransit'," + "    SUM(CASE WHEN ic.ic_id is not NULL and si.si_indent_approve = 1 and issue_indent_status = 1 THEN 1 ELSE 0 END) AS 'Recieved'," + "    SUM(1 ) AS 'Total' " + "    from tinv_store_indent si " + " left join tinv_issue_clinic ic on ic.ic_indent_no = si.si_id and ic.issue_clinic_unit_id = si.store_indent_unit_id " + " where DATE(si.created_date) between ' " + fromdate + " ' and ' " + todate + "'" + " group by si.store_indent_unit_id) as indendTab\n" + " left join " + " (select  si.store_indent_unit_id,count(*) as returnCount from  tinv_store_indent si where si.si_id in(" + " select sub.ici_si_id from " + "(select ici.ici_si_id,sum(ici.ici_return_quantity) as summ from  tinv_issue_clinic_item ici,tinv_issue_clinic ic where " + " ici.ici_ic_id = ic.ic_id  and ic.issue_indent_status = 1 and  ici.ici_si_id is not null group by ici.ici_si_id) as sub " + " where sub.summ!= 0) group by si.store_indent_unit_id) as returnTab " + " on indendTab.store_indent_unit_id=returnTab.store_indent_unit_id ) as comboTab " + " on unitTable.unit_id = comboTab.store_indent_unit_id " + " where unitTable.unit_id in (" + unitString + ")";
        if (checkval) {
            query += " and ifnull(comboTab.Total,0) != 0 ";
        } else {
        }

        System.out.println("Indent Count"+query);
        List<String> patientcount = (List<String>) objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("patientcount", patientcount);
        return respMap;
    }

    @RequestMapping("getmonthlypatientvisittrendwise/{fromdate}/{todate}/{unitids}/{checkval}")
    public Map<String, List> monthlypatientvisittrendwise(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("unitids") Long[] unitids, @PathVariable("checkval") boolean checkval) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String unitString = unitids[0].toString();
        for (int i = 1; i < unitids.length; i++) {
            unitString += "," + unitids[i];
        }
        String query = " select masterTable.MONTH,concat(masterTable.ar_name,' ',masterTable.gender_name) as 'type',ifnull(countTable.count,0) as pc from ( " + " select * from ( " + " SELECT m.MONTH,m.YEAR FROM  ( " + " select MONTHNAME(DATE_SUB(now(), INTERVAL 5 MONTH)) AS MONTH ,YEAR(DATE_SUB(now(), INTERVAL 5 MONTH)) AS YEAR  UNION " + " select MONTHNAME(DATE_SUB(now(), INTERVAL 4 MONTH)) AS MONTH ,YEAR(DATE_SUB(now(), INTERVAL 5 MONTH)) AS YEAR UNION " + " select MONTHNAME(DATE_SUB(now(), INTERVAL 3 MONTH)) AS MONTH ,YEAR(DATE_SUB(now(), INTERVAL 5 MONTH)) AS YEAR UNION " + " select MONTHNAME(DATE_SUB(now(), INTERVAL 2 MONTH)) AS MONTH ,YEAR(DATE_SUB(now(), INTERVAL 5 MONTH)) AS YEAR UNION " + " select MONTHNAME(DATE_SUB(now(), INTERVAL 1 MONTH)) AS MONTH ,YEAR(DATE_SUB(now(), INTERVAL 5 MONTH)) AS YEAR UNION " + " select MONTHNAME(now()) AS MONTH ,YEAR(now()) AS YEAR) as m ) as monthTable " + " cross join " + " (SELECT mg.gender_id,mg.gender_name FROM mst_gender mg where mg.is_deleted = 0 and mg.is_active = 1) gtable " + " cross join " + " (SELECT mra.ar_id,mra.ar_name FROM mst_appointment_reason mra where mra.is_deleted = 0 and mra.is_active = 1) as arTable " + " ) as masterTable " + " left join( " + " select YEAR(mv.created_date) AS YEAR,MONTHNAME(mv.created_date) AS MONTH,mra.ar_id,gender_id, count(visit_id) as count " + " from mst_visit mv " + " inner join mst_appointment_reason mra on mra.ar_id = mv.reason_visit " + " inner join mst_patient mp on mp.patient_id = visit_patient_id " + " inner join mst_user mu on mu.user_id = mp.patient_user_id " + " inner join mst_gender mg on mg.gender_id = mu.user_gender_id " + " where mv.visit_unit_id in (" + unitString + ") group by MONTHNAME(mv.created_date),mra.ar_id,mg.gender_id " + " ) as countTable " + " on countTable.YEAR = masterTable.YEAR and countTable.MONTH = masterTable.MONTH and countTable.ar_id = masterTable.ar_id and countTable.gender_id = masterTable.gender_id ";
        List<String> patientcount = (List<String>) objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("patientcount", patientcount);
        return respMap;
    }

    @RequestMapping("getgenderwisepatientcount/{fromdate}/{todate}/{unitids}/{checkval}")
    public Map<String, List> genderwisepatientcount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("unitids") Long[] unitids, @PathVariable("checkval") boolean checkval) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String unitString = unitids[0].toString();
        for (int i = 1; i < unitids.length; i++) {
            unitString += "," + unitids[i];
        }
        String query = " select gentab.gender_name,ifnull(countTab.0to1,0),ifnull(countTab.2to6,0),ifnull(countTab.7to12,0) " + ",ifnull(countTab.13to18,0),ifnull(countTab.19to30,0),ifnull(countTab.31to45,0),ifnull(countTab.46to60,0), " + "ifnull(countTab.Above60,0),ifnull(countTab.total,0)  from ( " + "select gen.gender_id,gen.gender_name from mst_gender gen where gen.is_active = 1 AND gen.is_deleted = 0 ) as genTab " + "left join ( " + "SELECT mg.gender_id,mg.gender_name, " + "    SUM(CASE WHEN mu.user_age between 0 and 1 THEN 1 ELSE 0 END) AS '0to1', " + "    SUM(CASE WHEN mu.user_age between 2 and 6 THEN 1 ELSE 0 END) AS '2to6', " + "    SUM(CASE WHEN mu.user_age between 7 and 12 THEN 1 ELSE 0 END) AS '7to12', " + "    SUM(CASE WHEN mu.user_age between 13 and 18 THEN 1 ELSE 0 END) AS '13to18', " + "    SUM(CASE WHEN mu.user_age between 19 and 30 THEN 1 ELSE 0 END) AS '19to30', " + "    SUM(CASE WHEN mu.user_age between 31 and 45 THEN 1 ELSE 0 END) AS '31to45', " + "    SUM(CASE WHEN mu.user_age between 46 and 60 THEN 1 ELSE 0 END) AS '46to60', " + "    SUM(CASE WHEN mu.user_age > 60  THEN 1 ELSE 0 END) AS 'Above60', " + "    SUM(1) as 'total' " + "FROM mst_visit mv " + "INNER JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id " + "INNER JOIN mst_user mu ON mu.user_id = mp.patient_user_id  " + "INNER JOIN mst_gender mg ON mg.gender_id = mu.user_gender_id " + "WHERE mv.is_active = 1 AND mv.is_deleted = 0  " + " and mv.visit_unit_id in (" + unitString + ")  and date(mv.created_date) between '" + fromdate + "' and '" + todate + "'" + " group by mu.user_gender_id ) as countTab  " + " on genTab.gender_id = countTab.gender_id";
        System.out.println("Gender Wise Patient Count"+query);
        List<String> patientcount = (List<String>) objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("patientcount", patientcount);
        return respMap;
    }

    @RequestMapping("getdepartmentwisepatientcount/{fromdate}/{todate}/{unitids}/{checkval}")
    public Map<String, List> departmentwisepatientcount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("unitids") Long[] unitids, @PathVariable("checkval") boolean checkval) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        String unitString = unitids[0].toString();
        for (int i = 1; i < unitids.length; i++) {
            unitString += "," + unitids[i];
        }
        String query = " select department_name,count(*) from mst_visit v " + "inner join mst_department d on d.department_id = v.visit_department_id  " + "where d.is_active = 1 and d.is_deleted=0 ";
        if (unitString != null && !unitString.isEmpty()) {
            query += "and v.visit_unit_id in (" + unitString + ") ";
        }
        if (fromdate != null && !fromdate.isEmpty() && todate != null && !todate.isEmpty()) {
            query += "and date(v.created_date) between '" + fromdate + "' and '" + todate + "'";
        }
        query += "group by visit_department_id limit 5";
        System.out.println("Department-wise Patient Count (Top 5)"+ query);
        List<String> patientcount = (List<String>) objEntityManager.createNativeQuery(query).getResultList();
        respMap.put("patientcount", patientcount);
        return respMap;
    }

}