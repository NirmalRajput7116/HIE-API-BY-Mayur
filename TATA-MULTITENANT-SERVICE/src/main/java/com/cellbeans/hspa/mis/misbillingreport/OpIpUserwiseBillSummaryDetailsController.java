package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUserRepository;
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
@RequestMapping("/mis_opdipd_userwisebilllist_summaryDetails")
public class OpIpUserwiseBillSummaryDetailsController {

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

    //// @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getOpIpUserwiseBillSummaryDetailsReport/{unitList}/{mstuserlist}/{mststafflist}/{IPDFlag}/{LimitFlag}")
    public ResponseEntity searchOpdIpdUserBillSummaryDetailsList(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpIpUserwiseBillSummaryDetailsDTO opipUserwiseBillSummaryDetailsDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String[] mststafflist, @PathVariable int IPDFlag, @PathVariable int LimitFlag) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " ";
        String Query2 = " ";
        String CountQuery = " ";
        String columnName = " ";
        if (IPDFlag == 0) {
            Query1 = " SELECT ifnull(u.unit_name,'')as unit_name,ifnull(v.visit_tariff_id,'')as visit_tariff_id,ifnull(v.reason_visit,'')as reason_visit,ifnull(us.user_age,'')as user_age,ifnull(gn.gender_name,'')as gender_name," +
                    " ifnull(tb.bill_number,'')as bill_number,ifnull(tb.bill_date,'')as bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no," +
                    " ifnull(tr.tariff_name,'')tariff_name,  CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as ConsultDoctor, " +
                    " CONCAT( ifnull(uf.user_firstname,'') ,' ',ifnull(uf.user_lastname,'') ) as BillMadeBystaff, " +
                    " tb.bill_sub_total as totalBillAmount,tb.bill_discount_amount as ConsessionAmount, " +
                    " tb.bill_net_payable as selfAmount,(tb.bill_sub_total-tb.bill_net_payable)as NonSelfAmount, " +
                    " tb.bill_outstanding as selfBalancedAmount,tb.bill_amount_paid as PaidAmount " +
                    " FROM tbill_bill tb " +
                    " inner join mbill_tariff tr on tb.bill_tariff_id=tr.tariff_id " +
                    " inner join mst_unit u on tb.tbill_unit_id=u.unit_id " +
                    " inner join mst_visit v on tb.bill_visit_id=v.visit_id " +
                    " inner join mst_patient p on v.visit_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id " +
                    " inner join mst_gender gn on us.user_gender_id=gn.gender_id " +
                    " inner join mst_staff sf on v.visit_staff_id=sf.staff_id " +
                    " inner join mst_user su on sf.staff_user_id=su.user_id " +
                    " inner join mst_staff ff on tb.bill_user_id=ff.staff_id " +
                    " inner join mst_user uf on ff.staff_user_id=uf.user_id where tb.ipd_bill=" + IPDFlag +
                    " and tb.is_deleted=0 and tb.is_active=1";
            Query2 = " SELECT sum(tb.bill_sub_total)as GrandTotalBillAmount ,sum(tb.bill_discount_amount)as GrandConcessionAmount," +
                    " sum(tb.bill_net_payable)as GrandSelfAmount, sum(tb.bill_sub_total-tb.bill_net_payable)as NonSelfAmount, " +
                    " sum(tb.bill_outstanding)as GrandSelfBalancedAmount,sum(tb.bill_amount_paid)GrandPaidAmount " +
                    " FROM tbill_bill tb " +
                    " inner join mbill_tariff tr on tb.bill_tariff_id=tr.tariff_id " +
                    " inner join mst_unit u on tb.tbill_unit_id=u.unit_id " +
                    " inner join mst_visit v on tb.bill_visit_id=v.visit_id " +
                    " inner join mst_patient p on v.visit_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id " +
                    " inner join mst_staff sf on v.visit_staff_id=sf.staff_id " +
                    " inner join mst_user su on sf.staff_user_id=su.user_id " +
                    " inner join mst_staff ff on tb.bill_user_id=ff.staff_id " +
                    " inner join mst_user uf on ff.staff_user_id=uf.user_id where tb.ipd_bill=" + IPDFlag;
            //"and tb.is_deleted=0 and tb.is_active=1";
        } else {
            Query1 = " SELECT ifnull(u.unit_name,'')as unit_name,ifnull(v.visit_tariff_id,'')as visit_tariff_id,ifnull(v.reason_visit,'')as reason_visit,ifnull(us.user_age,'')as user_age,ifnull(gn.gender_name,'')as gender_name," +
                    " ifnull(tb.bill_number,'')as bill_number,ifnull(tb.bill_date,'')as bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no," +
                    " ifnull(tr.tariff_name,'')tariff_name,  CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as ConsultDoctor, " +
                    " CONCAT( ifnull(uf.user_firstname,'') ,' ',ifnull(uf.user_lastname,'') ) as BillMadeBystaff, " +
                    " tb.bill_sub_total as totalBillAmount,tb.bill_discount_amount as ConsessionAmount, " +
                    " tb.bill_net_payable as selfAmount,(tb.bill_sub_total-tb.bill_net_payable)as NonSelfAmount, " +
                    " tb.bill_outstanding as selfBalancedAmount,tb.bill_amount_paid as PaidAmount " +
                    " FROM tbill_bill tb " +
                    " inner join mbill_tariff tr on tb.bill_tariff_id=tr.tariff_id " +
                    " inner join mst_unit u on tb.tbill_unit_id=u.unit_id " +
                    " inner join trn_admission a on tb.bill_admission_id=a.admission_id " +
                    " inner join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id " +
                    " inner join mst_gender gn on us.user_gender_id=gn.gender_id " +
                    " inner join mst_staff sf on a.admission_staff_id=sf.staff_id " +
                    " inner join mst_user su on sf.staff_user_id=su.user_id " +
                    " inner join mst_staff ff on tb.bill_user_id=ff.staff_id " +
                    " inner join mst_user uf on ff.staff_user_id=uf.user_id where tb.ipd_bill=" + IPDFlag +
                    "and tb.is_deleted=0 and tb.is_active=1";
            Query2 = " SELECT sum(tb.bill_sub_total)as GrandTotalBillAmount ,sum(tb.bill_discount_amount)as GrandConcessionAmount," +
                    " sum(tb.bill_net_payable)as GrandSelfAmount, sum(tb.bill_sub_total-tb.bill_net_payable)as NonSelfAmount," +
                    " sum(tb.bill_outstanding)as GrandSelfBalancedAmount,sum(tb.bill_amount_paid)GrandPaidAmount" +
                    " FROM tbill_bill tb" +
                    " inner join mbill_tariff tr on tb.bill_tariff_id=tr.tariff_id" +
                    " inner join mst_unit u on tb.tbill_unit_id=u.unit_id" +
                    " inner join trn_admission a on tb.bill_admission_id=a.admission_id" +
                    " inner join mst_patient p on a.admission_patient_id=p.patient_id" +
                    " inner join mst_user us on p.patient_user_id=us.user_id" +
                    " inner join mst_staff sf on a.admission_staff_id=sf.staff_id" +
                    " inner join mst_user su on sf.staff_user_id=su.user_id" +
                    " inner join mst_staff ff on tb.bill_user_id=ff.staff_id" +
                    " inner join mst_user uf on ff.staff_user_id=uf.user_id where tb.ipd_bill=" + IPDFlag;
            // " and tb.is_deleted=0 and tb.is_active=1";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and tb.tbill_unit_id in (" + values + ") ";
            Query2 += " and tb.tbill_unit_id in (" + values + ") ";
        }
        if (opipUserwiseBillSummaryDetailsDTO.getFromdate().equals("null") || opipUserwiseBillSummaryDetailsDTO.getFromdate().equals("")) {
            opipUserwiseBillSummaryDetailsDTO.setFromdate("1990-06-07");
        }
        if (opipUserwiseBillSummaryDetailsDTO.getTodate().equals("null") || opipUserwiseBillSummaryDetailsDTO.getTodate().equals("")) {
            opipUserwiseBillSummaryDetailsDTO.setTodate(strDate);
        }
        if (!opipUserwiseBillSummaryDetailsDTO.getMrNo().equals("")) {
            Query1 += " and  p.patient_mr_no like  '%" + opipUserwiseBillSummaryDetailsDTO.getMrNo() + "%' ";
            Query2 += " and p.patient_mr_no like  '%" + opipUserwiseBillSummaryDetailsDTO.getMrNo() + "%' ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query1 += " and ff.staff_user_id in (" + values + ") ";
            Query2 += " and ff.staff_user_id in (" + values + ") ";
        }
        if (!mststafflist[0].equals("null")) {
            String values = mststafflist[0];
            for (int i = 1; i < mststafflist.length; i++) {
                values += "," + mststafflist[i];
            }
            Query1 += " and ff.staff_user_id in (" + values + ") ";
            Query2 += " and ff.staff_user_id in (" + values + ") ";
        }
//        if (!String.valueOf(opipUserwiseBillSummaryDetailsDTO.getStaffId()).equals("0")) {
//            Query1 += "  and ff.staff_user_id=" + opipUserwiseBillSummaryDetailsDTO.getStaffId() + " ";
//            Query2 += "  and ff.staff_user_id=" + opipUserwiseBillSummaryDetailsDTO.getStaffId() + " ";
//        }
//
//        if (!String.valueOf(opipUserwiseBillSummaryDetailsDTO.getStaffId1()).equals("0")) {
//            Query1 += "  and ff.staff_user_id=" + opipUserwiseBillSummaryDetailsDTO.getStaffId1() + " ";
//            Query2 += "  and ff.staff_user_id=" + opipUserwiseBillSummaryDetailsDTO.getStaffId1() + " ";
//        }
        if (!String.valueOf(opipUserwiseBillSummaryDetailsDTO.getTariffId()).equals("0")) {
            Query1 += "  and tb.bill_tariff_id=" + opipUserwiseBillSummaryDetailsDTO.getTariffId() + " ";
            Query2 += "  and tb.bill_tariff_id=" + opipUserwiseBillSummaryDetailsDTO.getTariffId() + " ";
        }
        if (opipUserwiseBillSummaryDetailsDTO.getFromdate().equals("")) {
            opipUserwiseBillSummaryDetailsDTO.setFromdate("1990-06-07");
        }
        if (opipUserwiseBillSummaryDetailsDTO.getTodate().equals("")) {
            opipUserwiseBillSummaryDetailsDTO.setTodate(strDate);
        }
        if (opipUserwiseBillSummaryDetailsDTO.getTodaydate()) {
            Query1 += "  and date(tb.created_date)=curdate() ";
            Query2 += "  and date(tb.created_date)=curdate() ";
        } else {
            Query1 += " and  (date(tb.created_date) between '" + opipUserwiseBillSummaryDetailsDTO.getFromdate() + "' and '" + opipUserwiseBillSummaryDetailsDTO.getTodate() + "') ";
            Query2 += " and  (date(tb.created_date) between '" + opipUserwiseBillSummaryDetailsDTO.getFromdate() + "' and '" + opipUserwiseBillSummaryDetailsDTO.getTodate() + "') ";
        }
        if (!opipUserwiseBillSummaryDetailsDTO.getPatientName().equals("")) {
            Query1 += " and (us.user_firstname like '%" + opipUserwiseBillSummaryDetailsDTO.getPatientName() + "%' or us.user_lastname like '%" + opipUserwiseBillSummaryDetailsDTO.getPatientName() + "%') ";
            Query2 += " and (us.user_firstname like '%" + opipUserwiseBillSummaryDetailsDTO.getPatientName() + "%' or us.user_lastname like '%" + opipUserwiseBillSummaryDetailsDTO.getPatientName() + "%') ";
        }
        CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        columnName = "unit_name,visit_tariff_id,reason_visit,user_age,gender_name,bill_number,bill_date,patient_mr_no,tariff_name,patientname,ConsultDoctor,BillMadeBystaff,totalBillAmount,ConsessionAmount,selfAmount,NonSelfAmount,selfBalancedAmount,PaidAmount";
        if (LimitFlag == 1) {
            Query1 += " limit " + ((opipUserwiseBillSummaryDetailsDTO.getOffset() - 1) * opipUserwiseBillSummaryDetailsDTO.getLimit()) + "," + opipUserwiseBillSummaryDetailsDTO.getLimit();
        }
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query2).getResultList();
        // System.out.println("reason object" + r_obj.toString());
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query1, CountQuery));
        HashMap<String, Integer> tariffWiseCount = new HashMap<>();
        HashMap<String, Integer> reasonWiseCount = new HashMap<>();
        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        HashMap<String, Integer> ageWiseCount = new HashMap<>();
        ageWiseCount.put("a0T1", 0);
        ageWiseCount.put("a1T5", 0);
        ageWiseCount.put("a5T12", 0);
        ageWiseCount.put("a12T18", 0);
        ageWiseCount.put("a18T30", 0);
        ageWiseCount.put("a30T60", 0);
        ageWiseCount.put("a60T120", 0);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj1 = jsonArray.getJSONObject(i);
            if (obj1.get("tariff_name") != null && !"".equals(obj1.get("tariff_name"))) {
                if (tariffWiseCount.get(obj1.get("tariff_name")) == null) {
                    tariffWiseCount.put(obj1.get("tariff_name").toString().replace(" ", ""), 1);
                } else {
                    int reason_count = tariffWiseCount.get(obj1.get("tariff_name").toString().replace(" ", ""));
                    tariffWiseCount.put(obj1.get("tariff_name").toString().replace(" ", ""), ++reason_count);
                }
            }
            if (obj1.get("reason_visit") != null && !"".equals(obj1.get("reason_visit"))) {
                if (reasonWiseCount.get(obj1.get("reason_visit")) == null) {
                    reasonWiseCount.put(obj1.get("reason_visit").toString().replace(" ", ""), 1);
                } else {
                    int reason_count = reasonWiseCount.get(obj1.get("reason_visit").toString().replace(" ", ""));
                    reasonWiseCount.put(obj1.get("reason_visit").toString().replace(" ", ""), ++reason_count);
                }
            }
            if (obj1.get("gender_name") != null && !"".equals(obj1.get("gender_name"))) {
                if (genderWiseCount.get(obj1.get("gender_name")) == null) {
                    genderWiseCount.put(obj1.get("gender_name").toString(), 1);
                } else {
                    int reason_count = genderWiseCount.get(obj1.get("gender_name").toString());
                    genderWiseCount.put(obj1.get("gender_name").toString(), ++reason_count);
                }
            }
            if (obj1.get("user_age") != null && !"".equals(obj1.get("user_age"))) {
                if (Float.parseFloat(obj1.get("user_age").toString()) <= 1) {
                    int ageCount = ageWiseCount.get("a0T1");
                    ageWiseCount.put("a0T1", ++ageCount);
                }
                if (Float.parseFloat(obj1.get("user_age").toString()) > 1 && Float.parseFloat(obj1.get("user_age").toString()) <= 5) {
                    int ageCount = ageWiseCount.get("a1T5");
                    ageWiseCount.put("a1T5", ++ageCount);
                }
                if (Float.parseFloat(obj1.get("user_age").toString()) > 5 && Float.parseFloat(obj1.get("user_age").toString()) <= 12) {
                    int ageCount = ageWiseCount.get("a5T12");
                    ageWiseCount.put("a5T12", ++ageCount);
                }
                if (Float.parseFloat(obj1.get("user_age").toString()) > 12 && Float.parseFloat(obj1.get("user_age").toString()) <= 18) {
                    int ageCount = ageWiseCount.get("a12T18");
                    ageWiseCount.put("a12T18", ++ageCount);
                }
                if (Float.parseFloat(obj1.get("user_age").toString()) > 18 && Float.parseFloat(obj1.get("user_age").toString()) <= 30) {
                    int ageCount = ageWiseCount.get("a18T30");
                    ageWiseCount.put("a18T30", ++ageCount);
                }
                if (Float.parseFloat(obj1.get("user_age").toString()) > 30 && Float.parseFloat(obj1.get("user_age").toString()) <= 60) {
                    int ageCount = ageWiseCount.get("a30T60");
                    ageWiseCount.put("a30T60", ++ageCount);
                }
                if (Float.parseFloat(obj1.get("user_age").toString()) > 60) {
                    int ageCount = ageWiseCount.get("a60T120");
                    ageWiseCount.put("a60T120", ++ageCount);
                }
            }
        }
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("GrandTotalBillAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("GrandConcessionAmount", ob[1].toString());
                jsonArray.getJSONObject(0).put("GrandSelfAmount", ob[2].toString());
                jsonArray.getJSONObject(0).put("GrandNonSelfAmount", ob[3].toString());
                jsonArray.getJSONObject(0).put("GrandSelfBalancedAmount", ob[4].toString());
                jsonArray.getJSONObject(0).put("GrandPaidAmount", ob[5].toString());
                jsonArray.getJSONObject(0).put("reasonWiseCount", new JSONObject(reasonWiseCount));
                jsonArray.getJSONObject(0).put("tariffWiseCount", new JSONObject(tariffWiseCount));
                jsonArray.getJSONObject(0).put("genderWiseCount", new JSONObject(genderWiseCount));
                jsonArray.getJSONObject(0).put("ageWiseCount", ageWiseCount);
            }
            JSONArray myJson = new JSONArray();
            if (LimitFlag == 0) {
                for (int i = 0; i < 20; i++) {
                    myJson.put(jsonArray.get(i));
                    System.out.print("Enter Here");
                }
                return ResponseEntity.ok(myJson.toString());
            }
//            jsonArray.put(reasonWiseCount);
//            jsonArray.put(tariffWiseCount);
//            jsonArray.put(genderWiseCount);
//            jsonArray.put(ageWiseCount);
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        return ResponseEntity.ok(jsonArray.toString());
    }
}