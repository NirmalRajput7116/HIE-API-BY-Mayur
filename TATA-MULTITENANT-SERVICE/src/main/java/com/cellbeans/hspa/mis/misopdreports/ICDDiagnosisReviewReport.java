package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_icd_diagnosis")
public class ICDDiagnosisReviewReport {

    @PersistenceContext
    EntityManager objEntityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    CreateReport createReport;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("IcddiagnosisReviewReport/{today}/{fromdate}/{todate}/{unitList}/{IcdCode}/{staffId}/{page}/{size}")
    public ResponseEntity diagnosisCountanAlysisReport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("today") Boolean today, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable String[] unitList, @PathVariable Long IcdCode, @PathVariable Long staffId, @PathVariable Integer page, @PathVariable Integer size) throws Exception {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query2 = "select t.timelineemrfinal_staff_id,CONCAT ('Dr ',u.user_firstname,' ',u.user_lastname) as staff_name,un.unit_name,count(v.visit_id) as Total_Patient_Visit_Count,count(t.isemrfinal) as final_emr " +
                "    from temr_timeline t,mst_staff s,mst_user u,mst_visit v,mst_unit un,temr_visit_diagnosis vd " +
                "   where  t.timeline_id=vd.vd_timeline_id  and t.timelineemrfinal_staff_id=s.staff_id and s.staff_user_id=u.user_id " +
                "   and t.timeline_visit_id=v.visit_id  and v.visit_unit_id=un.unit_id and t.isemrfinal=1 ";
        String summeryTotal = "select CAST(vd.vd_is_final_diagnosis AS UNSIGNED) as status,count(vd.vd_is_final_diagnosis) as vd_is_final_diagnosis from temr_timeline t,temr_visit_diagnosis vd " +
                "  INNER JOIN mst_visit v ON v.visit_id = vd.vd_visit_id where t.timeline_id=vd.vd_timeline_id  and t.isemrfinal=1 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-03-05";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (today) {
            query2 += " and date(t.timeline_date)=curdate() ";
            summeryTotal += " and date(t.timeline_date)=curdate() ";
        } else {
            query2 += " and (date(t.timeline_date) between '" + fromdate + "' and '" + todate + "') ";
            summeryTotal += " and (date(t.timeline_date) between '" + fromdate + "' and '" + todate + "') ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and v.visit_unit_id in (" + values + ") ";
            summeryTotal += " and v.visit_unit_id in (" + values + ") ";
        }
        if (IcdCode != 0) {
            query2 += "  and  vd.vd_ic_id=" + IcdCode;
            summeryTotal += " and  vd.vd_ic_id=" + IcdCode;
        }
        if (staffId != 0) {
            query2 += "  and t.timelineemrfinal_staff_id=" + staffId;
            summeryTotal += " and t.timelineemrfinal_staff_id=" + staffId;
        }
        query2 += " group by v.visit_unit_id,t.timelineemrfinal_staff_id ";
        summeryTotal += " group by vd.vd_is_final_diagnosis ";
        String countQuery = "select count(*) from (" + query2 + ") as temp";
        query2 += " limit " + ((page - 1) * size) + "," + size;
        String columnName = "timelineemrfinal_staff_id,staff_name,unit_name,Total_Patient_Visit_Count,final_emr";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, query2, countQuery));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray dignosysStatus = new JSONArray();
            jsonArray.getJSONObject(i).put("dignosisStatus", dignosysStatus);
            JSONObject status0 = new JSONObject();
//            JSONObject status1 = new JSONObject();
            int[] a = {0, 12, 13, 17, 18, 60, 61, 120};
            for (int j = 0; j < a.length; j += 2) {
                String statusQuery = "select CAST(vd.vd_is_final_diagnosis AS UNSIGNED) as status,count(vd.vd_is_final_diagnosis) as vd_is_final_diagnosis,mg.gender_name  from temr_timeline t,temr_visit_diagnosis vd " +
                        "   INNER JOIN mst_visit v ON v.visit_id = vd.vd_visit_id inner join mst_patient mp on v.visit_patient_id=mp.patient_id inner join mst_user mu on mp.patient_user_id=mu.user_id inner join mst_gender mg on mu.user_gender_id=mg.gender_id " +
                        "  where t.timeline_id=vd.vd_timeline_id  and t.isemrfinal=1 ";
                if (today) {
                    statusQuery += " and date(t.timeline_date)=curdate() ";
                } else {
                    statusQuery += " and (date(t.timeline_date) between '" + fromdate + "' and '" + todate + "') ";
                }
                if (!unitList[0].equals("null")) {
                    String values = unitList[0];
                    for (int k = 1; k < unitList.length; k++) {
                        values += "," + unitList[k];
                    }
                    statusQuery += " and v.visit_unit_id in (" + values + ") ";
                }
                if (IcdCode != 0) {
                    statusQuery += " and vd.vd_ic_id=" + IcdCode + " ";
                }
                statusQuery += " and t.timelineemrfinal_staff_id=" + jsonArray.getJSONObject(i).get("timelineemrfinal_staff_id");
                statusQuery += " and mu.user_age>" + a[j] + " and mu.user_age<=" + a[j + 1] + " group by vd.vd_is_final_diagnosis ";
                List<Object[]> obj = (List<Object[]>) objEntityManager.createNativeQuery(statusQuery).getResultList();
                for (Object[] temp : obj) {
                    if (temp[0].toString().equals("0")) {
                        if (temp[2].toString().toLowerCase().equals("male")) {
                            status0.put("M0_" + a[j] + "_" + a[j + 1], temp[1].toString());
                        } else if (temp[1].toString().toLowerCase().equals("female")) {
                            status0.put("F0_" + a[j] + "_" + a[j + 1], temp[1].toString());
                        } else {
                            status0.put("O0_" + a[j] + "_" + a[j + 1], temp[1].toString());
                        }
                    } else {
                        if (temp[2].toString().toLowerCase().equals("male")) {
                            status0.put("M1_" + a[j] + "_" + a[j + 1], temp[1].toString());
                        } else if (temp[1].toString().toLowerCase().equals("female")) {
                            status0.put("F1_" + a[j] + "_" + a[j + 1], temp[1].toString());
                        } else {
                            status0.put("O1_" + a[j] + "_" + a[j + 1], temp[1].toString());
                        }
                    }
                }

            }
            jsonArray.getJSONObject(i).getJSONArray("dignosisStatus").put(status0);
//            jsonArray.getJSONObject(i).getJSONArray("dignosisStatus").put(status1);
        }
        String sumerrycolumnName = "status,totalcounts";
        if (jsonArray.length() != 0) {
            jsonArray.getJSONObject(0).put("summeryArray", new JSONArray(createJSONObject.createJsonObjectWithCount(sumerrycolumnName, summeryTotal, null)));
        }
        return ResponseEntity.ok(jsonArray.toString());
    }

}
