package com.cellbeans.hspa.mis.misopdunitdailysheet;

import com.cellbeans.hspa.TenantContext;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mis_opd_unit_daily_sheet")
public class MisopdunitdailysheetController {

    Map<String, String> respMap = new HashMap<String, String>();

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping
    @RequestMapping("getunitdailysheeteport/{unitList}")
    public Map<String, Integer> list(@RequestHeader("X-tenantId") String tenantName, @PathVariable String[] unitList, @RequestParam(value = "fromdate", required = false) String fromdate, @RequestParam(value = "todate", required = false) String todate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Integer> respMapCount = new HashMap<String, Integer>();
        String values = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String activeFacilitiesQuery = "select count(*) as Active_facilities from (select visit_unit_id,count(*) as cout from mst_visit v where 1 ";
        String totalCosultationQuery = "select count(*) as total_consultation from mst_visit v where v.reason_visit is not null and v.is_active=1 and v.is_deleted=0 ";
        String totalfollowUpQuery = "select count(*) as follow_up from mst_visit v where v.is_active=1 and v.is_deleted=0 and v.reason_visit='Follow Up' ";
        String totalFirstQuery = "select count(*) as first_visit from mst_visit v where v.is_active=1 and v.is_deleted=0 and  v.reason_visit='First Visit' ";
        String totalEHRsQuery = "select count(*) as EHRs from temr_timeline t,mst_visit v where t.timeline_visit_id=v.visit_id and t.is_active=1 and t.is_deleted=0 and t.isemrfinal=1 ";
        String totalLabReportQuery = "select count(*) as lab_g_report from tpath_bs l where l.is_active=1 and l.is_deleted=0 and l.is_finalized=1 ";
        String consultationsCountByGenderQuery = "select g.gender_name,count(*) as gender_consultations  from mst_visit v,mst_patient p,mst_user u,mst_gender g where v.visit_patient_id=p.patient_id and p.patient_user_id=u.user_id and u.user_gender_id=g.gender_id and v.reason_visit is not null and v.is_active=1 and v.is_deleted=0 ";
        String consultationsCountByAgeQuery = "select sum(case when u.user_age>0 and u.user_age<=12 then 1 else 0 end)  AS consultations_0to12," +
                "sum(case when u.user_age>=18 and u.user_age<=60 then 1 else 0 end)  AS consultations_18to60," +
                "sum(case when u.user_age>60 then 1 else 0 end)  AS consultations_above60 " +
                "from mst_visit v,mst_patient p,mst_user u,mst_gender g where v.visit_patient_id=p.patient_id " +
                "and p.patient_user_id=u.user_id and u.user_gender_id=g.gender_id " +
                " and v.reason_visit is not null and v.is_active=1 and v.is_deleted=0 ";
        if (!fromdate.equals("null")) {
            if (!todate.equals("null")) {
                activeFacilitiesQuery += " and date(v.created_date) between '" + fromdate + "' and '" + todate + "' ";
                totalCosultationQuery += " and date(v.visit_date) between '" + fromdate + "' and '" + todate + "' ";
                totalfollowUpQuery += " and date(v.visit_date) between '" + fromdate + "' and '" + todate + "' ";
                totalFirstQuery += " and date(v.visit_date) between '" + fromdate + "' and '" + todate + "' ";
                totalEHRsQuery += " and date(t.created_date) between '" + fromdate + "' and '" + todate + "' ";
                totalLabReportQuery += " and date(l.created_date) between '" + fromdate + "' and '" + todate + "' ";
                consultationsCountByGenderQuery += " and date(v.visit_date) between '" + fromdate + "' and '" + todate + "' ";
                consultationsCountByAgeQuery += " and date(v.visit_date) between '" + fromdate + "' and '" + todate + "' ";
            } else {
                activeFacilitiesQuery += " and date(v.created_date) between '" + fromdate + "' and '" + strDate + "' ";
                totalCosultationQuery += " and date(v.visit_date) between '" + fromdate + "' and '" + strDate + "' ";
                totalfollowUpQuery += " and date(v.visit_date) between '" + fromdate + "' and '" + strDate + "' ";
                totalFirstQuery += " and date(v.visit_date) between '" + fromdate + "' and '" + strDate + "' ";
                totalEHRsQuery += " and date(t.created_date) between '" + fromdate + "' and '" + strDate + "' ";
                totalLabReportQuery += " and date(l.created_date) between '" + fromdate + "' and '" + strDate + "' ";
                consultationsCountByGenderQuery += " and date(v.visit_date) between '" + fromdate + "' and '" + strDate + "' ";
                consultationsCountByAgeQuery += " and date(v.visit_date) between '" + fromdate + "' and '" + strDate + "' ";
            }
        } else {
            activeFacilitiesQuery += " and date(v.created_date)=curdate() ";
            totalCosultationQuery += " and date(v.visit_date)=curdate() ";
            totalfollowUpQuery += " and date(v.visit_date)=curdate() ";
            totalFirstQuery += " and date(v.visit_date)=curdate() ";
            totalEHRsQuery += " and date(t.created_date)=curdate() ";
            totalLabReportQuery += " and date(l.created_date)=curdate() ";
            consultationsCountByGenderQuery += " and  date(v.visit_date)=curdate() ";
            consultationsCountByAgeQuery += " and  date(v.visit_date)=curdate() ";
        }
        activeFacilitiesQuery += " and v.is_active=1 and v.is_deleted=0 group by v.visit_unit_id) as activeUnit where activeUnit.cout>0 ";
        if (!unitList[0].equals("null")) {
            values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            activeFacilitiesQuery += " and activeUnit.visit_unit_id in (" + values + ") ";
            totalCosultationQuery += " and v.visit_unit_id in (" + values + ") ";
            totalfollowUpQuery += "  and v.visit_unit_id in (" + values + ") ";
            totalFirstQuery += "  and v.visit_unit_id in (" + values + ") ";
            totalEHRsQuery += "  and v.visit_unit_id in (" + values + ") ";
            totalLabReportQuery += " and l.is_performed_by_unit_id in (" + values + ") ";
            consultationsCountByGenderQuery += " and v.visit_unit_id in (" + values + ") ";
            consultationsCountByAgeQuery += " and v.visit_unit_id in (" + values + ") ";
        }
        consultationsCountByGenderQuery += " group by u.user_gender_id ";
        BigInteger activefac = (BigInteger) entityManager.createNativeQuery(activeFacilitiesQuery).getSingleResult();
        respMapCount.put("activeFacilities", activefac.intValue());
        BigInteger totalConsultaion = (BigInteger) entityManager.createNativeQuery(totalCosultationQuery).getSingleResult();
        respMapCount.put("totalConsultation", totalConsultaion.intValue());
        BigInteger totalFollowUp = (BigInteger) entityManager.createNativeQuery(totalfollowUpQuery).getSingleResult();
        respMapCount.put("followUpCount", totalFollowUp.intValue());
        BigInteger totalFirstConsultation = (BigInteger) entityManager.createNativeQuery(totalFirstQuery).getSingleResult();
        respMapCount.put("firstVisitCount", totalFirstConsultation.intValue());
        BigInteger totalEHRs = (BigInteger) entityManager.createNativeQuery(totalEHRsQuery).getSingleResult();
        respMapCount.put("EHRsCount", totalEHRs.intValue());
        BigInteger totalLabReports = (BigInteger) entityManager.createNativeQuery(totalLabReportQuery).getSingleResult();
        respMapCount.put("labReportCount", totalLabReports.intValue());
        List<Object[]> consultationsCountByGenderList = (List<Object[]>) entityManager.createNativeQuery(consultationsCountByGenderQuery).getResultList();
        for (Object[] obj : consultationsCountByGenderList) {
            if (obj[0].toString().toLowerCase().equals("male")) {
                respMapCount.put("consultationsMale", Integer.parseInt(obj[1].toString()));
            } else if (obj[0].toString().toLowerCase().equals("female")) {
                respMapCount.put("consultationsFemale", Integer.parseInt(obj[1].toString()));
            } else if (obj[0].toString().toLowerCase().equals("other")) {
                respMapCount.put("consultationsOther", Integer.parseInt(obj[1].toString()));
            }
        }
        List<Object[]> consultationsCountByAgeList = (List<Object[]>) entityManager.createNativeQuery(consultationsCountByAgeQuery).getResultList();
        if (consultationsCountByAgeList.get(0)[0] != null) {
            respMapCount.put("consultations0to12", Integer.parseInt(consultationsCountByAgeList.get(0)[0].toString()));
        }
        if (consultationsCountByAgeList.get(0)[1] != null) {
            respMapCount.put("consultations18to60", Integer.parseInt(consultationsCountByAgeList.get(0)[1].toString()));
        }
        if (consultationsCountByAgeList.get(0)[2] != null) {
            respMapCount.put("consultations_above60", Integer.parseInt(consultationsCountByAgeList.get(0)[2].toString()));
        }
        return respMapCount;
    }
}
