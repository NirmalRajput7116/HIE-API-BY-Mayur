package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_ipd_advanced_details")
public class IpdAdvancedDetailController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    MstUserRepository mstUserRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CreateReport createReport;

    // @Produces(MediaType.APPLICATION_JSON)
    // opd Collection Details Report Start
    @RequestMapping("getopdcollectionDetailsReport/{mstuserlist}/{ccId}/{fromdate}/{todate}")
    public ResponseEntity searchOpdCollectionDetail(@RequestHeader("X-tenantId") String tenantName, @RequestBody IpdAdvancedDetailSearchDTO ipdadvanceddetailsearchDTO, @PathVariable String[] mstuserlist, @PathVariable String ccId, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT ta.aa_patient_id,date(ta.created_date)as ADate ,ifnull(p.patient_mr_no,'')as MRNO, " +
                " ta.aa_reciept_number, " +
                " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname, " +
                " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as UserName, " +
                " ifnull(pm.pm_name,'')PaymentMode,ifnull(ta.advanced_amount,0)as PaymentAmount ,ifnull(ta.advanced_amount,0)as AmountCollectd, " +
                " ifnull(ta.advanced_consumed,0)as ConsumeAmount,ifnull(ta.advanced_balance,0)as BalancedAmount,ifnull(ta.refund_amount,0)as RefundAmount " +
                " FROM trn_ipd_advanced ta " +
                " inner join mst_patient p on ta.aa_patient_id=p.patient_id " +
                " inner join mst_user u on p.patient_user_id=u.user_id " +
                " inner join mst_payment_mode pm on ta.aa_pm_id=pm.pm_id " +
                " inner join mst_staff sf on ta.aa_user_id=sf.staff_id " +
                " inner join mst_user su on sf.staff_user_id=su.user_id ";
        String CountQuery = " ";
        String columnName = " ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (ipdadvanceddetailsearchDTO.getTodaydate()) {
            Query += " and (date(ta.created_date)=curdate()) ";
        } else {
            Query += " and (date(ta.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query += " and sf.staff_user_id in (" + values + ") ";
        }
        if (!ipdadvanceddetailsearchDTO.getPatientName().equals("")) {
            Query += " and (concat(u.user_firstname,' ',u.user_lastname) like '%" + ipdadvanceddetailsearchDTO.getPatientName() + "%' or concat(u.user_lastname,' ',u.user_firstname) like '%" + ipdadvanceddetailsearchDTO.getPatientName() + "%' ) ";
        }
        Query += "group by ta.aa_patient_id ";
        System.out.println("IPD Advance Details Reports"+Query);
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "aa_patient_id,ADate,MRNO,aa_reciept_number,patientname,UserName,PaymentMode,PaymentAmount,AmountCollectd,ConsumeAmount,BalancedAmount,RefundAmount";
        Query += " limit " + ((ipdadvanceddetailsearchDTO.getOffset() - 1) * ipdadvanceddetailsearchDTO.getLimit()) + "," + ipdadvanceddetailsearchDTO.getLimit();
        //  return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        for (int j = 0; j < jsonArray.length(); j++) {
            if (!jsonArray.getJSONObject(j).get("PaymentMode").toString().equals("")) {
                String Query5 = " SELECT ta.aa_pm_id,sum(ta.advanced_amount) " +
                        " FROM trn_ipd_advanced ta where ta.aa_patient_id =" + jsonArray.getJSONObject(j).get("aa_patient_id") + " group by ta.aa_pm_id ";
                List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query5).getResultList();
                for (Object[] obj : list) {
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
        return ResponseEntity.ok(jsonArray.toString());
    }
    // opd Collection Details Report End

    // opd Collection Details Report Print Start
    @RequestMapping("getopdcollectionDetailsReportPrint/{mstuserlist}/{ccId}/{fromdate}/{todate}")
    public String searchOpdCollectionDetailPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody IpdAdvancedDetailSearchDTO ipdadvanceddetailsearchDTO, @PathVariable String[] mstuserlist, @PathVariable String ccId, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT ta.aa_patient_id,date(ta.created_date)as ADate ,ifnull(p.patient_mr_no,'')as MRNO, " +
                " ta.aa_reciept_number, " +
                " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname, " +
                " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as UserName, " +
                " ifnull(pm.pm_name,'')PaymentMode,ifnull(ta.advanced_amount,0)as PaymentAmount ,ifnull(ta.advanced_amount,0)as AmountCollectd, " +
                " ifnull(ta.advanced_consumed,0)as ConsumeAmount,ifnull(ta.advanced_balance,0)as BalancedAmount,ifnull(ta.refund_amount,0)as RefundAmount " +
                " FROM trn_ipd_advanced ta " +
                " inner join mst_patient p on ta.aa_patient_id=p.patient_id " +
                " inner join mst_user u on p.patient_user_id=u.user_id " +
                " inner join mst_payment_mode pm on ta.aa_pm_id=pm.pm_id " +
                " inner join mst_staff sf on ta.aa_user_id=sf.staff_id " +
                " inner join mst_user su on sf.staff_user_id=su.user_id ";
        String CountQuery = " ";
        String columnName = " ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (ipdadvanceddetailsearchDTO.getTodaydate()) {
            Query += " and (date(ta.created_date)=curdate()) ";
        } else {
            Query += " and (date(ta.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query += " and sf.staff_user_id in (" + values + ") ";
        }
        if (!ipdadvanceddetailsearchDTO.getPatientName().equals("")) {
            Query += " and (concat(u.user_firstname,' ',u.user_lastname) like '%" + ipdadvanceddetailsearchDTO.getPatientName() + "%' or concat(u.user_lastname,' ',u.user_firstname) like '%" + ipdadvanceddetailsearchDTO.getPatientName() + "%' ) ";
        }
        Query += "group by ta.aa_patient_id ";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "aa_patient_id,ADate,MRNO,aa_reciept_number,patientname,UserName,PaymentMode,PaymentAmount,AmountCollectd,ConsumeAmount,BalancedAmount,RefundAmount";
        // Query += " limit " + ((ipdadvanceddetailsearchDTO.getOffset() - 1) * ipdadvanceddetailsearchDTO.getLimit()) + "," + ipdadvanceddetailsearchDTO.getLimit();
        //  return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        for (int j = 0; j < jsonArray.length(); j++) {
            if (!jsonArray.getJSONObject(j).get("PaymentMode").toString().equals("")) {
                String Query5 = " SELECT ta.aa_pm_id,sum(ta.advanced_amount) " +
                        " FROM trn_ipd_advanced ta where ta.aa_patient_id =" + jsonArray.getJSONObject(j).get("aa_patient_id") + " group by ta.aa_pm_id ";
                List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query5).getResultList();
                for (Object[] obj : list) {
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
        //return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(ipdadvanceddetailsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(ipdadvanceddetailsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (ipdadvanceddetailsearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("OpdIpdAdvanceDetailsReport", jsonArray.toString());
        } else {
            // return ResponseEntity.ok(jsonArray.toString());
            return createReport.createJasperReportPDFByJson("OpdIpdAdvanceDetailsReport", jsonArray.toString());
        }
    }
    // opd Collection Details Report Print End

    // Invoice Settlement report Start
    @RequestMapping("getinvoicesettlementDetailsReport/{IPDFlag}")
    public ResponseEntity searchInvoiceSettlementDetails(@RequestHeader("X-tenantId") String tenantName, @RequestBody String jsonString, @PathVariable int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject response = new JSONObject();
        JSONObject inputParameter;
        String fromDate = null;
        String toDate = null;
        try {
            inputParameter = new JSONObject(jsonString);
            if (jsonString.contains("fromdate")) {
                fromDate = inputParameter.getString("fromdate");
            }
            if (jsonString.contains("todate")) {
                toDate = inputParameter.getString("todate");
            }
            int offset = inputParameter.getInt("offset");
            int limit = inputParameter.getInt("limit");
            JSONArray untiList = inputParameter.getJSONArray("unitList");
            String query = null, countQuery = "", query1 = "";
            long count = 0;
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(date);
            if ((fromDate == null && toDate == null) || (fromDate.isEmpty() && toDate.isEmpty())) {
                if (IPDFlag == 1) {
                    query = " SELECT  mu.user_firstname,mu.user_lastname,tb.created_date AS bill_date ,bill_net_payable AS total_pay,bill_amount_paid AS amount_paid, "
                            + "company_net_pay as company_pay,ifnull(bill_discount_percentage,'') AS discount,bill_discount_amount AS discount_amount,"
                            + "ifnull((SELECT SUM(consumed_amount) FROM advanced_history ah WHERE tb.bill_id=ah.bill_id),0) AS advance_consumed,"
                            + "ifnull((SELECT ah.balanced_amount FROM advanced_history ah INNER JOIN trn_ipd_advanced ipd_advanced "
                            + "WHERE tb.bill_id=ah.bill_id AND ipd_advanced.ipdadvanced_id=ah.advanced_amount_id ORDER BY ah.id desc LIMIT 1),0) AS advance_balanced ,mc.class_name ,mp.patient_mr_no,"
                            + "mst_user.user_fullname AS bill_user, tb.bill_number " + " FROM tbill_bill tb "
                            + "INNER JOIN trn_admission ta INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND "
                            + "ta.admission_patient_id=mp.patient_id AND mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id";
                    countQuery = " SELECT count(*) FROM tbill_bill tb "
                            + "INNER JOIN trn_admission ta INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND  ta.admission_patient_id=mp.patient_id and"
                            + "ta.admission_patient_id=mp.patient_id AND mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id";

                } else {
                    query = " SELECT  mu.user_firstname,mu.user_lastname,tb.created_date AS bill_date ,bill_net_payable AS total_pay,bill_amount_paid AS amount_paid, "
                            + "company_net_pay as company_pay,bill_discount_percentage AS discount,bill_discount_amount AS discount_amount,"
                            + "ifnull((SELECT SUM(consumed_amount) FROM advanced_history ah WHERE tb.bill_id=ah.bill_id),0) AS advance_consumed,"
                            + "ifnull((SELECT advanced_balance FROM advanced_history ah INNER JOIN trn_ipd_advanced ipd_advanced "
                            + "WHERE tb.bill_id=ah.bill_id AND ipd_advanced.ipdadvanced_id=ah.advanced_amount_id ORDER BY ah.id desc LIMIT 1),0) AS advance_balanced ,mc.class_name ,mp.patient_mr_no,"
                            + "mst_user.user_fullname AS bill_user, tb.bill_number,ifnull(tb.gross_amount, '') AS gross_amount,ifnull((SELECT sum(ts.bs_discount_amount) FROM tbill_bill_service ts  WHERE ts.bs_bill_id=tb.bill_id), 0) AS service_discount  " + " FROM tbill_bill tb "
                            + "INNER JOIN mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and  "
                            + " mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id";
                    countQuery = " SELECT count(*) FROM tbill_bill tb "
                            + "INNER JOIN mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and  "
                            + " mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id";

                }
            } else {
                if (IPDFlag == 1) {
                    query = " SELECT  mu.user_firstname,mu.user_lastname,tb.created_date AS bill_date ,bill_net_payable AS total_pay,bill_amount_paid AS amount_paid, "
                            + "company_net_pay as company_pay,ifnull(bill_discount_percentage, '') AS discount,bill_discount_amount AS discount_amount,"
                            + "ifnull((SELECT SUM(consumed_amount) FROM advanced_history ah WHERE tb.bill_id=ah.bill_id),0) AS advance_consumed,"
                            + "ifnull((SELECT ah.balanced_amount FROM advanced_history ah INNER JOIN trn_ipd_advanced ipd_advanced "
                            + "WHERE tb.bill_id=ah.bill_id AND ipd_advanced.ipdadvanced_id=ah.advanced_amount_id ORDER BY ah.id desc LIMIT 1),0) AS advance_balanced ,mc.class_name ,mp.patient_mr_no,"
                            + "mst_user.user_fullname AS bill_user, tb.bill_number,ifnull(tb.gross_amount, '') AS gross_amount, ifnull((SELECT sum(ts.bs_discount_amount) FROM tbill_bill_service ts  WHERE ts.bs_bill_id=tb.bill_id), 0) AS service_discount  " + " FROM tbill_bill tb "
                            + "INNER JOIN trn_admission ta INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND "
                            + "ta.admission_patient_id=mp.patient_id AND mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id"
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";
                    countQuery = " SELECT count(*) FROM tbill_bill tb INNER JOIN trn_admission ta "
                            + " INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id and  "
                            + "  ta.admission_patient_id=mp.patient_id and mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id"
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";

                } else {
                    query = " SELECT  mu.user_firstname,mu.user_lastname,tb.created_date AS bill_date ,bill_net_payable AS total_pay,bill_amount_paid AS amount_paid, "
                            + "company_net_pay as company_pay,ifnull(bill_discount_percentage, '') AS discount,bill_discount_amount AS discount_amount,"
                            + "ifnull((SELECT SUM(consumed_amount) FROM advanced_history ah WHERE tb.bill_id=ah.bill_id),0) AS advance_consumed,"
                            + "ifnull((SELECT ah.balanced_amount FROM advanced_history ah INNER JOIN trn_ipd_advanced ipd_advanced "
                            + "WHERE tb.bill_id=ah.bill_id AND ipd_advanced.ipdadvanced_id=ah.advanced_amount_id ORDER BY ah.id desc LIMIT 1),0) AS advance_balanced ,mc.class_name ,mp.patient_mr_no,"
                            + "mst_user.user_fullname AS bill_user, tb.bill_number,ifnull(tb.gross_amount, '') AS gross_amount, ifnull((SELECT sum(ts.bs_discount_amount) FROM tbill_bill_service ts  WHERE ts.bs_bill_id=tb.bill_id), 0) AS service_discount  " + " FROM tbill_bill tb "
                            + " INNER JOIN mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and  "
                            + " mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id"
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";
                    countQuery = " SELECT count(*) FROM tbill_bill tb  "
                            + " INNER JOIN mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE  tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and "
                            + " mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id"
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";

                }
            }
            if (untiList != null && untiList.length() != 0) {
                query = query + " and tbill_unit_id in (" + untiList.toString().replace("[", "").replace("]", "") + ")";
                countQuery = countQuery + " and tbill_unit_id in (" + untiList.toString().replace("[", "").replace("]", "") + ")";

            }
            if (IPDFlag == 1) {
                query = query + " and ipd_bill=true order by tb.bill_id desc";
                query1 = query;
                query += " limit " + ((offset - 1) * limit) + " , " + (limit) + "";
                countQuery = countQuery + " and ipd_bill=true";
            } else {
                query = query + " and ipd_bill=false order by tb.bill_id desc";
                query1 = query;
                query += " limit " + ((offset - 1) * limit) + " , " + (limit) + "";
                countQuery = countQuery + " and ipd_bill=false";
            }
            System.out.println("query1 : " + query1);
            System.out.println("query : " + query);
            System.out.println("countQuery : " + countQuery);
            List<Object[]> resultlList = (List<Object[]>) entityManager.createNativeQuery(query1).getResultList();
            List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
            BigInteger totalCount = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            JSONArray array = new JSONArray();
            Float billTotal = new Float(0.0);
            Float totalCompanyPay = new Float(0.0);
            Float totalConcessionOnBill = new Float(0.0);
            Float netCoPay = new Float(0.0);
            Float recieved = new Float(0.0);
            Float totalConsumed = new Float(0.0);
            Float totalSettlement = new Float(0.0);
            Float totalAgainstBill = new Float(0.0);
            for (int i = 0; i < resultlList.size(); i++) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("name", resultlList.get(i)[0] + " " + resultlList.get(i)[1]);
                    object.put("date", resultlList.get(i)[2]);
                    object.put("total_pay", resultlList.get(i)[3]);
                    object.put("amount_paid", resultlList.get(i)[4]);
                    object.put("company_pay", resultlList.get(i)[5]);
                    object.put("discount", resultlList.get(i)[6]);
                    float discount_amount = Float.parseFloat("" + resultlList.get(i)[7]) + Float.parseFloat("" + resultlList.get(i)[15]);
                    object.put("discount_amount", discount_amount);
                    object.put("advance_consumed", resultlList.get(i)[8]);
                    object.put("advance_balanced", resultlList.get(i)[9]);
                    object.put("classname", resultlList.get(i)[10]);
                    object.put("mrNo", resultlList.get(i)[11]);
                    object.put("billUser", resultlList.get(i)[12]);
                    object.put("billNo", resultlList.get(i)[13]);
                    object.put("gross_amount", resultlList.get(i)[14]);
                    Float amount_rec = Float.parseFloat("" + resultlList.get(i)[4]) - Float.parseFloat("" + resultlList.get(i)[8]);
                    object.put("amount_recived", amount_rec);
                    object.put("total_settlement", resultlList.get(i)[4]);
                    Float against_bill = Float.parseFloat("" + resultlList.get(i)[14]) - (Float.parseFloat("" + resultlList.get(i)[5])
                            + Float.parseFloat("" + resultlList.get(i)[4]) + discount_amount);
                    object.put("against_bill", "" + against_bill);
                    billTotal += Float.parseFloat("" + resultlList.get(i)[3]);
                    totalCompanyPay = totalCompanyPay + +Float.parseFloat("" + resultlList.get(i)[5]);
                    netCoPay = netCoPay + +Float.parseFloat("" + resultlList.get(i)[4]);
                    totalConcessionOnBill = totalConcessionOnBill + Float.parseFloat("" + resultlList.get(i)[7]) + Float.parseFloat("" + resultlList.get(i)[15]);
                    recieved = recieved + amount_rec;
                    totalConsumed = totalConsumed + Float.parseFloat("" + resultlList.get(i)[8]);
                    totalSettlement = totalSettlement + Float.parseFloat("" + resultlList.get(i)[4]);
                    totalAgainstBill = totalAgainstBill + against_bill;
                    array.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            response.put("data", array);
            response.put("billTotal", billTotal);
            response.put("totalCompanyPay", totalCompanyPay);
            response.put("totalConcessionOnBill", totalConcessionOnBill);
            response.put("netCoPay", netCoPay);
            response.put("recieved", recieved);
            response.put("totalConsumed", totalConsumed);
            response.put("totalSettlement", totalSettlement);
            response.put("totalAgainstBill", totalAgainstBill);
            response.put("total_count", totalCount);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return ResponseEntity.ok(response.toString());

    }
    // Invoice Settlement report End

    // Invoice Settlement report Print Start
    @RequestMapping("getinvoicesettlementDetailsReportPrint/{IPDFlag}")
    public String searchInvoiceSettlementDetailsPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody String jsonString, @PathVariable int IPDFlag) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject response = new JSONObject();
        JSONObject inputParameter;
        String fromDate = null;
        String toDate = null;
        Long unitId = null;
        boolean print = false;
        JSONArray array = new JSONArray();
        try {
            inputParameter = new JSONObject(jsonString);
            if (jsonString.contains("fromdate")) {
                fromDate = inputParameter.getString("fromdate");
            }
            if (jsonString.contains("todate")) {
                toDate = inputParameter.getString("todate");
            }
            print = inputParameter.getBoolean("print");
            unitId = inputParameter.getLong("unitId");
            int offset = inputParameter.getInt("offset");
            int limit = inputParameter.getInt("limit");
            JSONArray untiList = inputParameter.getJSONArray("unitList");
            String query = null, countQuery = "";
            MstUnit HeaderObject = mstUnitRepository.findByUnitId(unitId);
            JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
            jsonObject.put("fromDate", fromDate);
            jsonObject.put("toDate", toDate);
            if (IPDFlag == 1) {
                jsonObject.put("type", "IPD");
            } else {
                jsonObject.put("type", "OPD");
            }
            long count = 0;
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(date);
            if ((fromDate == null && toDate == null) || (fromDate.isEmpty() && toDate.isEmpty())) {
                if (IPDFlag == 1) {
                    query = " SELECT  mu.user_firstname,mu.user_lastname,tb.created_date AS bill_date ,bill_net_payable AS total_pay,bill_amount_paid AS amount_paid, "
                            + "company_net_pay as company_pay,bill_discount_percentage AS discount,bill_discount_amount AS discount_amount,"
                            + "ifnull((SELECT SUM(consumed_amount) FROM advanced_history ah WHERE tb.bill_id=ah.bill_id),0) AS advance_consumed,"
                            + "ifnull((SELECT ah.balanced_amount FROM advanced_history ah INNER JOIN trn_ipd_advanced ipd_advanced "
                            + "WHERE tb.bill_id=ah.bill_id AND ipd_advanced.ipdadvanced_id=ah.advanced_amount_id ORDER BY ah.id desc LIMIT 1),0) AS advance_balanced ,mc.class_name ,mp.patient_mr_no,"
                            + "mst_user.user_fullname AS bill_user, tb.bill_number,tb.gross_amount,ifnull((SELECT sum(ts.bs_discount_amount) FROM tbill_bill_service ts  WHERE ts.bs_bill_id=tb.bill_id), 0) AS service_discount " + " FROM tbill_bill tb "
                            + "INNER JOIN trn_admission ta INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND "
                            + "ta.admission_patient_id=mp.patient_id AND mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id";
                    countQuery = " SELECT count(*) FROM tbill_bill tb "
                            + "INNER JOIN trn_admission ta  INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND "
                            + "ta.admission_patient_id=mp.patient_id AND mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id";

                } else {
                    query = " SELECT  mu.user_firstname,mu.user_lastname,tb.created_date AS bill_date ,bill_net_payable AS total_pay,bill_amount_paid AS amount_paid, "
                            + "company_net_pay as company_pay,bill_discount_percentage AS discount,bill_discount_amount AS discount_amount,"
                            + "ifnull((SELECT SUM(consumed_amount) FROM advanced_history ah WHERE tb.bill_id=ah.bill_id),0) AS advance_consumed,"
                            + "ifnull((SELECT advanced_balance FROM advanced_history ah INNER JOIN trn_ipd_advanced ipd_advanced "
                            + "WHERE tb.bill_id=ah.bill_id AND ipd_advanced.ipdadvanced_id=ah.advanced_amount_id ORDER BY ah.id desc LIMIT 1),0) AS advance_balanced ,mc.class_name ,mp.patient_mr_no,"
                            + "mst_user.user_fullname AS bill_user, tb.bill_number,tb.gross_amount,ifnull((SELECT sum(ts.bs_discount_amount) FROM tbill_bill_service ts  WHERE ts.bs_bill_id=tb.bill_id), 0) AS service_discount  " + " FROM tbill_bill tb "
                            + "INNER JOIN mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and  "
                            + " mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id";
                    countQuery = " SELECT count(*) FROM tbill_bill tb "
                            + "INNER JOIN mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and  "
                            + " mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id";

                }
            } else {
                if (IPDFlag == 1) {
                    query = " SELECT  mu.user_firstname,mu.user_lastname,tb.created_date AS bill_date ,bill_net_payable AS total_pay,bill_amount_paid AS amount_paid, "
                            + "company_net_pay as company_pay,bill_discount_percentage AS discount,bill_discount_amount AS discount_amount,"
                            + "ifnull((SELECT SUM(consumed_amount) FROM advanced_history ah WHERE tb.bill_id=ah.bill_id),0) AS advance_consumed,"
                            + "ifnull((SELECT ah.balanced_amount FROM advanced_history ah INNER JOIN trn_ipd_advanced ipd_advanced "
                            + "WHERE tb.bill_id=ah.bill_id AND ipd_advanced.ipdadvanced_id=ah.advanced_amount_id ORDER BY ah.id desc LIMIT 1),0) AS advance_balanced ,mc.class_name ,mp.patient_mr_no,"
                            + "mst_user.user_fullname AS bill_user, tb.bill_number,tb.gross_amount,ifnull((SELECT sum(ts.bs_discount_amount) FROM tbill_bill_service ts  WHERE ts.bs_bill_id=tb.bill_id), 0) AS service_discount  " + " FROM tbill_bill tb "
                            + "INNER JOIN trn_admission ta INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND "
                            + "ta.admission_patient_id=mp.patient_id AND mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id"
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";
                    countQuery = " SELECT count(*) FROM tbill_bill tb INNER JOIN trn_admission ta"
                            + " INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND  "
                            + " mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id"
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";

                } else {
                    query = " SELECT  mu.user_firstname,mu.user_lastname,tb.created_date AS bill_date ,bill_net_payable AS total_pay,bill_amount_paid AS amount_paid, "
                            + "company_net_pay as company_pay,bill_discount_percentage AS discount,bill_discount_amount AS discount_amount,"
                            + "ifnull((SELECT SUM(consumed_amount) FROM advanced_history ah WHERE tb.bill_id=ah.bill_id),0) AS advance_consumed,"
                            + "ifnull((SELECT ah.balanced_amount FROM advanced_history ah INNER JOIN trn_ipd_advanced ipd_advanced "
                            + "WHERE tb.bill_id=ah.bill_id AND ipd_advanced.ipdadvanced_id=ah.advanced_amount_id ORDER BY ah.id desc LIMIT 1),0) AS advance_balanced ,mc.class_name ,mp.patient_mr_no,"
                            + "mst_user.user_fullname AS bill_user, tb.bill_number,tb.gross_amount,ifnull((SELECT sum(ts.bs_discount_amount) FROM tbill_bill_service ts  WHERE ts.bs_bill_id=tb.bill_id), 0) AS service_discount  " + " FROM tbill_bill tb "
                            + " INNER JOIN mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and  "
                            + " mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id"
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";
                    countQuery = " SELECT count(*) FROM tbill_bill tb "
                            + " INNER JOIN mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and "
                            + " mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id"
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";

                }
            }
            if (untiList != null && untiList.length() != 0) {
                query = query + " and tbill_unit_id in (" + untiList.toString().replace("[", "").replace("]", "") + ")";
                countQuery = countQuery + " and tbill_unit_id in (" + untiList.toString().replace("[", "").replace("]", "") + ")";
            }
            if (IPDFlag == 1) {
                query = query + " and ipd_bill=true";
                countQuery = countQuery + " and ipd_bill=true";
            } else {
                query = query + " and ipd_bill=false ";
                countQuery = countQuery + " and ipd_bill=false";
            }
            System.out.println("query : " + query);
            List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
            BigInteger totalCount = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            Float billTotal = new Float(0.0);
            Float totalCompanyPay = new Float(0.0);
            Float totalConcessionOnBill = new Float(0.0);
            Float netCoPay = new Float(0.0);
            Float recieved = new Float(0.0);
            Float totalConsumed = new Float(0.0);
            Float totalSettlement = new Float(0.0);
            Float totalAgainstBill = new Float(0.0);
            for (int i = 0; i < list.size(); i++) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("name", list.get(i)[0] + " " + list.get(i)[1]);
                    object.put("date", list.get(i)[2]);
                    object.put("total_pay", list.get(i)[3]);
                    object.put("amount_paid", list.get(i)[4]);
                    object.put("company_pay", list.get(i)[5]);
                    object.put("discount", list.get(i)[6]);
                    float discount_amount = Float.parseFloat("" + list.get(i)[7]) + Float.parseFloat("" + list.get(i)[15]);
                    object.put("discount_amount", discount_amount);
                    object.put("advance_consumed", list.get(i)[8]);
                    object.put("advance_balanced", list.get(i)[9]);
                    object.put("classname", list.get(i)[10]);
                    object.put("mrNo", list.get(i)[11]);
                    object.put("billUser", list.get(i)[12]);
                    object.put("billNo", list.get(i)[13]);
                    object.put("gross_amount", list.get(i)[14]);
                    Float amount_rec = Float.parseFloat("" + list.get(i)[4]) - Float.parseFloat("" + list.get(i)[8]);
                    object.put("amount_recived", amount_rec);
                    object.put("total_settlement", list.get(i)[4]);
                    Float against_bill = Float.parseFloat("" + list.get(i)[14]) - (Float.parseFloat("" + list.get(i)[5])
                            + Float.parseFloat("" + list.get(i)[4]) + discount_amount);
                    object.put("against_bill", "" + against_bill);
                    billTotal = billTotal + Float.parseFloat("" + list.get(i)[3]);
                    totalCompanyPay = totalCompanyPay + +Float.parseFloat("" + list.get(i)[5]);
                    netCoPay = netCoPay + +Float.parseFloat("" + list.get(i)[4]);
                    totalConcessionOnBill = totalConcessionOnBill + Float.parseFloat("" + list.get(i)[7]) + Float.parseFloat("" + list.get(i)[15]);
                    recieved = recieved + amount_rec;
                    totalConsumed = totalConsumed + Float.parseFloat("" + list.get(i)[8]);
                    totalSettlement = totalSettlement + Float.parseFloat("" + list.get(i)[4]);
                    totalAgainstBill = totalAgainstBill + against_bill;
                    array.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            response.put("data", array);
            response.put("billTotal", billTotal);
            response.put("totalCompanyPay", totalCompanyPay);
            response.put("totalConcessionOnBill", totalConcessionOnBill);
            response.put("netCoPay", netCoPay);
            response.put("recieved", recieved);
            response.put("totalConsumed", totalConsumed);
            response.put("totalSettlement", totalSettlement);
            response.put("totalAgainstBill", totalAgainstBill);
            response.put("total_count", totalCount);
            if (array != null && array.length() > 0) {
                array.getJSONObject(0).put("billTotal", billTotal);
                array.getJSONObject(0).put("totalCompanyPay", totalCompanyPay);
                array.getJSONObject(0).put("totalConcessionOnBill", totalConcessionOnBill);
                array.getJSONObject(0).put("netCoPay", netCoPay);
                array.getJSONObject(0).put("recieved", recieved);
                array.getJSONObject(0).put("totalConsumed", totalConsumed);
                array.getJSONObject(0).put("totalSettlement", totalSettlement);
                array.getJSONObject(0).put("totalAgainstBill", totalAgainstBill);
                array.getJSONObject(0).put("total_count", totalCount);
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        if (print) {
            String columnName = "name,date,total_pay,amount_paid,company_pay,discount,discount_amount,advance_consumed,advance_balanced,classname,mrNo,billUser,billNo,gross_amount,amount_recived,total_settlement,against_bill,data,billTotal,totalCompanyPay,totalConcessionOnBill,netCoPay,recieved,totalConsumed,totalSettlement,totalAgainstBill,total_count";
            return createReport.generateExcel(columnName, "InvoiceSettlementReport", array);
        } else {
            return createReport.createJasperReportPDFByJson("InvoiceSettlementReport", array.toString());
        }
    }
    // Invoice Settlement report Print End

    @RequestMapping("serachByPatientMrNO/{unitList}/{fromdate}/{todate}/{IPDFlag}")
    public ResponseEntity serachByPatientMrNO(@RequestHeader("X-tenantId") String tenantName, @RequestBody String jsonString, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject inputParameter;
        String fromDate = fromdate;
        String toDate = todate;
        String[] untiList = unitList;
        BigInteger count = null;
        String query = null, countQuery = "";
        JSONArray responseArray = new JSONArray();
        float totalAdvanceBalancedAmount = 0;
        float totalAdvanceConsumedAmountTotal = 0;
        float totalAdvanceRefundAmountTotal = 0;
        float paymentTotalAmount = 0;
        float paymentTotalRefunalAmount = 0;
        try {
            inputParameter = new JSONObject(jsonString);
            if (jsonString.contains("fromdate")) {
                fromDate = inputParameter.getString("fromdate");
            }
            if (jsonString.contains("todate")) {
                toDate = inputParameter.getString("todate");
            }
            int offset = inputParameter.getInt("offset");
            int limit = inputParameter.getInt("limit");
            // JSONArray untiList = inputParameter.getJSONArray("unitList");
            if ((fromDate == null && toDate == null) || (fromDate.isEmpty() && toDate.isEmpty())) {
                if (IPDFlag == 1) {
                    query = "SELECT mp.patient_id ,mp.patient_mr_no,mst_user.user_fullname AS bill_user, tb.bill_number ,"
                            + "sum(tb.refund_amount) AS refund_amount," + "sum(bill_net_payable) AS net_payable,"
                            + "sum(bill_amount_paid) AS paid_amount," + "sum(bill_tariff_co_pay) as company_pay,"
                            + "sum(bill_discount_percentage) AS discount,"
                            + "sum(bill_discount_amount) AS discount_amount,CONCAT( ifnull(mu.user_firstname,'') ,' ',ifnull(mu.user_lastname,'') ) as patientname"
                            + " FROM tbill_bill tb INNER JOIN trn_admission ta INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND ta.admission_patient_id=mp.patient_id AND mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id ";
                    countQuery = "SELECT count(*)"
                            + " FROM tbill_bill tb INNER JOIN trn_admission ta INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND ta.admission_patient_id=mp.patient_id AND mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id ";

                } else {
                    query = "SELECT mp.patient_id ,mp.patient_mr_no,mst_user.user_fullname AS bill_user, tb.bill_number ,"
                            + "sum(tb.refund_amount) AS refund_amount," + "sum(bill_net_payable) AS net_payable,"
                            + "sum(bill_amount_paid) AS paid_amount," + "sum(bill_tariff_co_pay) as company_pay,"
                            + "sum(bill_discount_percentage) AS discount,"
                            + "sum(bill_discount_amount) AS discount_amount,CONCAT( ifnull(mu.user_firstname,'') ,' ',ifnull(mu.user_lastname,'') ) as patientname"
                            + " FROM tbill_bill tb INNER JOIN mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id ";
                    countQuery = "SELECT count(*)"
                            + " FROM tbill_bill tb INNER JOIN mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id ";

                }

            } else {
                if (IPDFlag == 1) {
                    query = "SELECT mp.patient_id ,mp.patient_mr_no,mst_user.user_fullname AS bill_user, tb.bill_number ,"
                            + "sum(tb.refund_amount) AS refund_amount," + "sum(bill_net_payable) AS net_payable,"
                            + "sum(bill_amount_paid) AS paid_amount," + "sum(bill_tariff_co_pay) as company_pay,"
                            + "sum(bill_discount_percentage) AS discount,"
                            + "sum(bill_discount_amount) AS discount_amount,CONCAT( ifnull(mu.user_firstname,'') ,' ',ifnull(mu.user_lastname,'') ) as patientname"
                            + " FROM tbill_bill tb INNER JOIN trn_admission ta INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND ta.admission_patient_id=mp.patient_id AND mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id "
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";
                    countQuery = "SELECT count(*)"
                            + " FROM tbill_bill tb INNER JOIN trn_admission ta INNER JOIN mst_patient mp  INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_admission_id=ta.admission_id AND ta.admission_patient_id=mp.patient_id AND mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id "
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";

                } else {
                    query = "SELECT mp.patient_id ,mp.patient_mr_no,mst_user.user_fullname AS bill_user, tb.bill_number ,"
                            + "sum(tb.refund_amount) AS refund_amount," + "sum(bill_net_payable) AS net_payable,"
                            + "sum(bill_amount_paid) AS paid_amount," + "sum(bill_tariff_co_pay) as company_pay,"
                            + "sum(bill_discount_percentage) AS discount,"
                            + "sum(bill_discount_amount) AS discount_amount,CONCAT( ifnull(mu.user_firstname,'') ,' ',ifnull(mu.user_lastname,'') ) as patientname"
                            + " FROM tbill_bill tb INNER join mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id "
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";
                    countQuery = "SELECT count(*)"
                            + " FROM tbill_bill tb INNER join mst_visit mv INNER join mst_patient mp INNER JOIN mst_user mu INNER JOIN mst_class mc INNER JOIN mst_staff ms INNER JOIN mst_user mst_user WHERE tb.bill_visit_id=mv.visit_id AND mv.visit_patient_id=mp.patient_id and mp.patient_user_id=mu.user_id AND tb.is_deleted=FALSE AND mc.class_id=tb.bill_class_id  AND tb.bill_user_id=ms.staff_id and mst_user.user_id=ms.staff_user_id "
                            + " and bill_date >= '" + fromDate + "' and bill_date <= '" + toDate + "'";

                }

            }
//            if (untiList != null && untiList.length() != 0) {
//                query = query + " and tbill_unit_id in (" + untiList.toString().replace("[", "").replace("]", "") + ")";
//                countQuery = countQuery + " and tbill_unit_id in ("
//                        + untiList.toString().replace("[", "").replace("]", "") + ")";
//
//            }
            if (!unitList[0].equals("null")) {
                String values = unitList[0];
                for (int i = 1; i < unitList.length; i++) {
                    values += "," + unitList[i];
                }
                query += " and tb.tbill_unit_id in (" + values + ") ";
                countQuery += " and tb.tbill_unit_id in (" + values + ") ";
            }
            if (IPDFlag == 1) {
                query = query + " and ipd_bill=true GROUP BY mp.patient_mr_no limit " + (offset - 1) + " , " + (limit)
                        + "";
                countQuery = countQuery + " and ipd_bill=true";
            } else {
                query = query + " and ipd_bill=false GROUP BY mp.patient_mr_no limit " + (offset - 1) + " , " + (limit)
                        + "";
                countQuery = countQuery + " and ipd_bill=false";

            }
            List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
            count = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            for (int i = 0; i < list.size(); i++) {
                JSONObject content = new JSONObject();
                String patient_id = "" + list.get(i)[0];
                content.put("patient_id", patient_id);
                content.put("mr_no", list.get(i)[1]);
                content.put("bill_user", list.get(i)[2]);
                content.put("bill_number", list.get(i)[3]);
                content.put("refund_amount", list.get(i)[4]);
                content.put("net_payable", list.get(i)[5]);
                content.put("paid_amount", list.get(i)[6]);
                content.put("company_pay", list.get(i)[7]);
                content.put("discount", list.get(i)[8]);
                content.put("discount_amount", list.get(i)[9]);
                content.put("patientname", list.get(i)[10]);
                JSONObject balancedAmount = new JSONObject();
                JSONObject consumedAmount = new JSONObject();
                JSONObject refundAmount = new JSONObject();
                String advanceAmount = "SELECT mp.pm_name,sum(ifnull(advanced_amount,0)) AS advanced_amount,sum(ifnull(advanced_consumed,0)) AS advanced_consumed,sum(ifnull(refund_amount,0)) AS advance_refund FROM trn_ipd_advanced ipd_adv INNER JOIN mst_payment_mode mp WHERE mp.pm_id=ipd_adv.aa_pm_id and ipd_adv.aa_patient_id="
                        + patient_id + "  GROUP BY mp.pm_name;";
                List<Object[]> advanceAmountSize = (List<Object[]>) entityManager.createNativeQuery(advanceAmount)
                        .getResultList();
                float balancedAmountTotal = 0;
                float consumedAmountTotal = 0;
                float refundAmountTotal = 0;
                if (IPDFlag == 1) {
                    for (int j = 0; j < advanceAmountSize.size(); j++) {
                        String mode = "" + advanceAmountSize.get(j)[0];
                        if (mode.equalsIgnoreCase("cash")) {
                            balancedAmount.put("cash", "" + advanceAmountSize.get(j)[1]);
                            consumedAmount.put("cash", "" + advanceAmountSize.get(j)[2]);
                            refundAmount.put("cash", "" + advanceAmountSize.get(j)[3]);
                            balancedAmountTotal = balancedAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[1]);
                            consumedAmountTotal = consumedAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[2]);
                            refundAmountTotal = refundAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[3]);
                            totalAdvanceBalancedAmount = totalAdvanceBalancedAmount
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[1]);
                            totalAdvanceConsumedAmountTotal = totalAdvanceConsumedAmountTotal
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[2]);
                            totalAdvanceRefundAmountTotal = totalAdvanceRefundAmountTotal
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[3]);

                        } else if (mode.equalsIgnoreCase("cheque")) {
                            balancedAmount.put("cheque", "" + advanceAmountSize.get(j)[1]);
                            consumedAmount.put("cheque", "" + advanceAmountSize.get(j)[2]);
                            refundAmount.put("cheque", "" + advanceAmountSize.get(j)[3]);
                            balancedAmountTotal = balancedAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[1]);
                            consumedAmountTotal = consumedAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[2]);
                            refundAmountTotal = refundAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[3]);
                            totalAdvanceBalancedAmount = totalAdvanceBalancedAmount
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[1]);
                            totalAdvanceConsumedAmountTotal = totalAdvanceConsumedAmountTotal
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[2]);
                            totalAdvanceRefundAmountTotal = totalAdvanceRefundAmountTotal
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[3]);
                        } else if (mode.equalsIgnoreCase("card")) {
                            balancedAmount.put("card", "" + advanceAmountSize.get(j)[1]);
                            consumedAmount.put("card", "" + advanceAmountSize.get(j)[2]);
                            refundAmount.put("card", "" + advanceAmountSize.get(j)[3]);
                            balancedAmountTotal = balancedAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[1]);
                            consumedAmountTotal = consumedAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[2]);
                            refundAmountTotal = refundAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[3]);
                            totalAdvanceBalancedAmount = totalAdvanceBalancedAmount
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[1]);
                            totalAdvanceConsumedAmountTotal = totalAdvanceConsumedAmountTotal
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[2]);
                            totalAdvanceRefundAmountTotal = totalAdvanceRefundAmountTotal
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[3]);
                        } else if (mode.equalsIgnoreCase("transfer")) {
                            balancedAmount.put("transfer", "" + advanceAmountSize.get(j)[1]);
                            consumedAmount.put("transfer", "" + advanceAmountSize.get(j)[2]);
                            refundAmount.put("transfer", "" + advanceAmountSize.get(j)[3]);
                            balancedAmountTotal = balancedAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[1]);
                            consumedAmountTotal = consumedAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[2]);
                            refundAmountTotal = refundAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[3]);
                            totalAdvanceBalancedAmount = totalAdvanceBalancedAmount
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[1]);
                            totalAdvanceConsumedAmountTotal = totalAdvanceConsumedAmountTotal
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[2]);
                            totalAdvanceRefundAmountTotal = totalAdvanceRefundAmountTotal
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[3]);
                        } else {
                            balancedAmount.put("other", "" + advanceAmountSize.get(j)[1]);
                            consumedAmount.put("other", "" + advanceAmountSize.get(j)[2]);
                            refundAmount.put("other", "" + advanceAmountSize.get(j)[3]);
                            balancedAmountTotal = balancedAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[1]);
                            consumedAmountTotal = consumedAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[2]);
                            refundAmountTotal = refundAmountTotal + Float.parseFloat("" + advanceAmountSize.get(j)[3]);
                            totalAdvanceBalancedAmount = totalAdvanceBalancedAmount
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[1]);
                            totalAdvanceConsumedAmountTotal = totalAdvanceConsumedAmountTotal
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[2]);
                            totalAdvanceRefundAmountTotal = totalAdvanceRefundAmountTotal
                                    + Float.parseFloat("" + advanceAmountSize.get(j)[3]);
                        }
                    }
                }
                balancedAmount.put("total", "" + balancedAmountTotal);
                consumedAmount.put("total", "" + consumedAmountTotal);
                refundAmount.put("total", "" + refundAmountTotal);
                JSONObject paymentAmountDetail = new JSONObject();
                JSONObject paymentRefundAmountDetail = new JSONObject();
                String paymentAmount = "";
                if (IPDFlag == 1) {
                    paymentAmount = "SELECT mp.pm_name,sum(tr.br_payment_amount) AS amount,sum(tr.refund_amount) as refund_amount FROM tbill_reciept tr INNER JOIN mst_payment_mode mp  INNER JOIN tbill_bill tb  WHERE tb.bill_id=tr.br_bill_id AND tb.ipd_bill=true and mp.pm_id=tr.br_pm_id and tr.br_patient_id="
                            + patient_id + " GROUP BY mp.pm_name;";

                } else {
                    paymentAmount = "SELECT mp.pm_name,sum(tr.br_payment_amount) AS amount,sum(tr.refund_amount) as refund_amount FROM tbill_reciept tr INNER JOIN mst_payment_mode mp  INNER JOIN tbill_bill tb  WHERE tb.bill_id=tr.br_bill_id AND tb.ipd_bill=FALSE and mp.pm_id=tr.br_pm_id and tr.br_patient_id="
                            + patient_id + " GROUP BY mp.pm_name;";

                }
//                String paymentAmount = "SELECT mp.pm_name,sum(ifnull(tr.br_payment_amount,0)) AS amount,sum(ifnull(tr.refund_amount,0)) as refund_amount FROM tbill_reciept tr INNER JOIN mst_payment_mode mp  WHERE mp.pm_id=tr.br_pm_id and tr.br_patient_id="
//                        + patient_id + " GROUP BY mp.pm_name;";
                List<Object[]> paymentAmountSize = (List<Object[]>) entityManager.createNativeQuery(paymentAmount)
                        .getResultList();
                float paymentAmountTotal = 0;
                float paymentRefunalAmountTotal = 0;
                for (int j = 0; j < paymentAmountSize.size(); j++) {
                    String mode = "" + paymentAmountSize.get(j)[0];
                    if (mode.equalsIgnoreCase("cash")) {
                        paymentAmountDetail.put("cash", paymentAmountSize.get(j)[1]);
                        paymentRefundAmountDetail.put("cash", paymentAmountSize.get(j)[2]);
                        paymentAmountTotal = paymentAmountTotal + Float.parseFloat("" + paymentAmountSize.get(j)[1]);
                        paymentRefunalAmountTotal = paymentRefunalAmountTotal
                                + Float.parseFloat("" + paymentAmountSize.get(j)[2]);
                        paymentTotalAmount = paymentTotalAmount + Float.parseFloat("" + paymentAmountSize.get(j)[1]);
                        paymentTotalRefunalAmount = paymentTotalRefunalAmount
                                + Float.parseFloat("" + paymentAmountSize.get(j)[2]);

                    } else if (mode.equalsIgnoreCase("cheque")) {
                        paymentAmountDetail.put("cheque", paymentAmountSize.get(j)[1]);
                        paymentRefundAmountDetail.put("cheque", paymentAmountSize.get(j)[2]);
                        paymentAmountTotal = paymentAmountTotal + Float.parseFloat("" + paymentAmountSize.get(j)[1]);
                        paymentRefunalAmountTotal = paymentRefunalAmountTotal
                                + Float.parseFloat("" + paymentAmountSize.get(j)[2]);
                        paymentTotalAmount = paymentTotalAmount + Float.parseFloat("" + paymentAmountSize.get(j)[1]);
                        paymentTotalRefunalAmount = paymentTotalRefunalAmount
                                + Float.parseFloat("" + paymentAmountSize.get(j)[2]);

                    } else if (mode.equalsIgnoreCase("card")) {
                        paymentAmountDetail.put("card", paymentAmountSize.get(j)[1]);
                        paymentRefundAmountDetail.put("card", paymentAmountSize.get(j)[2]);
                        paymentAmountTotal = paymentAmountTotal + Float.parseFloat("" + paymentAmountSize.get(j)[1]);
                        paymentRefunalAmountTotal = paymentRefunalAmountTotal
                                + Float.parseFloat("" + paymentAmountSize.get(j)[2]);
                        paymentTotalAmount = paymentTotalAmount + Float.parseFloat("" + paymentAmountSize.get(j)[1]);
                        paymentTotalRefunalAmount = paymentTotalRefunalAmount
                                + Float.parseFloat("" + paymentAmountSize.get(j)[2]);
                    } else if (mode.equalsIgnoreCase("transfer")) {
                        paymentAmountDetail.put("transfer", paymentAmountSize.get(j)[1]);
                        paymentRefundAmountDetail.put("transfer", paymentAmountSize.get(j)[2]);
                        paymentAmountTotal = paymentAmountTotal + Float.parseFloat("" + paymentAmountSize.get(j)[1]);
                        paymentRefunalAmountTotal = paymentRefunalAmountTotal
                                + Float.parseFloat("" + paymentAmountSize.get(j)[2]);
                        paymentTotalAmount = paymentTotalAmount + Float.parseFloat("" + paymentAmountSize.get(j)[1]);
                        paymentTotalRefunalAmount = paymentTotalRefunalAmount
                                + Float.parseFloat("" + paymentAmountSize.get(j)[2]);
                    } else {
                        paymentAmountDetail.put("other", paymentAmountSize.get(j)[1]);
                        paymentRefundAmountDetail.put("other", paymentAmountSize.get(j)[2]);
                        paymentAmountTotal = paymentAmountTotal + Float.parseFloat("" + paymentAmountSize.get(j)[1]);
                        paymentRefunalAmountTotal = paymentRefunalAmountTotal
                                + Float.parseFloat("" + paymentAmountSize.get(j)[2]);
                        paymentTotalAmount = paymentTotalAmount + Float.parseFloat("" + paymentAmountSize.get(j)[1]);
                        paymentTotalRefunalAmount = paymentTotalRefunalAmount
                                + Float.parseFloat("" + paymentAmountSize.get(j)[2]);
                    }
                }
                paymentAmountDetail.put("total", paymentAmountTotal);
                paymentRefundAmountDetail.put("total", paymentRefunalAmountTotal);
                content.put("balancedAmount", balancedAmount);
                content.put("consumedAmount", consumedAmount);
                content.put("refundAmount", refundAmount);
                content.put("paymentAmountDetail", paymentAmountDetail);
                content.put("paymentRefundAmountDetail", paymentRefundAmountDetail);
                responseArray.put(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject res = new JSONObject();
        try {
            res.put("data", responseArray);
            res.put("total_advance", totalAdvanceBalancedAmount);
            res.put("total_advance_refund", totalAdvanceRefundAmountTotal);
            res.put("total_advance_consumed", totalAdvanceConsumedAmountTotal);
            res.put("payment_total_amount", paymentTotalAmount);
            res.put("payment_total_refund_amount", paymentTotalRefunalAmount);
            res.put("count", count);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok(res.toString());
    }

    // OutStanding report Start 12.11.2019
    @RequestMapping("getoutstandingDetailsReport/{unitList}/{fromdate}/{todate}/{ctId}/{companyId}/{IPDFlag}")
    public ResponseEntity searchinvoicereport(@RequestHeader("X-tenantId") String tenantName, @RequestBody BillOutstandSearchDTO billoutstandSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate, @PathVariable String ctId, @PathVariable String companyId, @PathVariable int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String SummaryQuery = " ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query += "  SELECT IFNULL(mt.title_name, '')as title_name, ifnull(mu.user_firstname,'')as user_firstname ,ifnull(mu.user_lastname,'') AS user_lastname," +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') billNo, " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.ipd_bill = 0,'OPD','IPD') AS classType," +
                    " IF(mv.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName," +
                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount," +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount,IFNULL(tb.company_net_pay,0) AS credit, IFNULL(tb.bill_amount_paid,0) AS selfPaid, " +
                    " IFNULL(tb.bill_outstanding,0) AS balance," +
                    " IFNULL(tb.company_net_pay,0) AS companyCredit,IFNULL(tb.company_paid_amount,0) AS companyCollection," +
                    " IFNULL(tb.bill_company_out_standing,0) AS companyBalance," +
                    " IFNULL(mun.unit_name,'') AS unitName" +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id" +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id" +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id" +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0" +
                    " AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT SUM(tb.gross_amount) AS TotalGrossAmount," +
                    " SUM(tb.bill_discount_amount) AS TotalDiscount," +
                    " SUM(tb.bill_net_payable) AS TotalNetAmount," +
                    " SUM(tb.bill_amount_paid) AS TotalPaidAmount," +
                    " SUM(tb.bill_outstanding) AS TotalSelfBalanceAmt," +
                    " SUM(tb.company_net_pay) AS TotalCompanyCreditAmt," +
                    " SUM(tb.company_paid_amount) AS TotalCompanyCollection," +
                    " SUM(tb.bill_company_out_standing) AS TotalCompanyBalance" +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id" +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id" +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id" +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0" +
                    " AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += " SELECT IFNULL(mt.title_name, '')as title_name, ifnull(mu.user_firstname,'')as user_firstname ,ifnull(mu.user_lastname,'') AS user_lastname," +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') billNo, " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.ipd_bill = 0,'OPD','IPD') AS classType, " +
                    " IF(ta.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
                    " IFNULL(tb.bill_amount_paid,0) AS selfPaid, IFNULL(tb.bill_outstanding,0) AS balance, " +
                    " IFNULL(tb.company_net_pay,0) AS companyCredit,IFNULL(tb.company_paid_amount,0) AS companyCollection, " +
                    " IFNULL(tb.bill_company_out_standing,0) AS companyBalance,IFNULL(mun.unit_name,'') AS unitName " +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                    " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
                    "  LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0 AND tb.emrbill = 0  AND tb.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT SUM(tb.gross_amount) AS TotalGrossAmount," +
                    " SUM(tb.bill_discount_amount) AS TotalDiscount," +
                    " SUM(tb.bill_net_payable) AS TotalNetAmount," +
                    " SUM(tb.bill_amount_paid) AS TotalPaidAmount," +
                    " SUM(tb.bill_outstanding) AS TotalSelfBalanceAmt," +
                    " SUM(tb.company_net_pay) AS TotalCompanyCreditAmt," +
                    " SUM(tb.company_paid_amount) AS TotalCompanyCollection," +
                    " SUM(tb.bill_company_out_standing) AS TotalCompanyBalance" +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id" +
                    " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id" +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id" +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0 AND tb.emrbill = 0  AND tb.ipd_bill = " + IPDFlag;
        } else {
            Query += " SELECT IFNULL(mt.title_name, '')as title_name, ifnull(mu.user_firstname,'')as user_firstname ,ifnull(mu.user_lastname,'') AS user_lastname," +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') billNo, " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.emrbill = 1,'EMG','') AS classType," +
                    " IF(mv.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
                    " IFNULL(tb.bill_amount_paid,0) AS selfPaid, IFNULL(tb.bill_outstanding,0) AS balance, " +
                    " IFNULL(tb.company_net_pay,0) AS companyCredit,IFNULL(tb.company_paid_amount,0) AS companyCollection, " +
                    " IFNULL(tb.bill_company_out_standing,0) AS companyBalance, " +
                    " IFNULL(mun.unit_name,'') AS unitName" +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id" +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id" +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id" +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0" +
                    " AND tb.emrbill = 1 ";
            SummaryQuery += "  SELECT SUM(tb.gross_amount) AS TotalGrossAmount," +
                    " SUM(tb.bill_discount_amount) AS TotalDiscount," +
                    " SUM(tb.bill_net_payable) AS TotalNetAmount," +
                    " SUM(tb.bill_amount_paid) AS TotalPaidAmount," +
                    " SUM(tb.bill_outstanding) AS TotalSelfBalanceAmt," +
                    " SUM(tb.company_net_pay) AS TotalCompanyCreditAmt," +
                    " SUM(tb.company_paid_amount) AS TotalCompanyCollection," +
                    " SUM(tb.bill_company_out_standing) AS TotalCompanyBalance" +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id" +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id" +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id" +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0" +
                    " AND tb.emrbill = 1 ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
        }
        if (billoutstandSearchDTO.getTodaydate()) {
            Query += " and (date(tb.created_date)=curdate()) ";
            SummaryQuery += " and (date(tb.created_date)=curdate()) ";
        } else {
            Query += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            SummaryQuery += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and tb.tbill_unit_id in (" + values + ") ";
            SummaryQuery += " and tb.tbill_unit_id in (" + values + ") ";
        }
        if (!billoutstandSearchDTO.getMrNo().equals("")) {
            Query += " and  mp.patient_mr_no like  '%" + billoutstandSearchDTO.getMrNo() + "%' ";
            SummaryQuery += " and mp.patient_mr_no like  '%" + billoutstandSearchDTO.getMrNo() + "%' ";
        }
        if ((billoutstandSearchDTO.getUser_firstname() != null) && (!billoutstandSearchDTO.getUser_firstname().equals(""))) {
            Query += " and (mu.user_firstname like '%" + billoutstandSearchDTO.getUser_firstname() + "%') ";
            SummaryQuery += " and (mu.user_firstname like '%" + billoutstandSearchDTO.getUser_firstname() + "%') ";
        }
        if ((billoutstandSearchDTO.getUser_lastname() != null) && (!billoutstandSearchDTO.getUser_lastname().equals(""))) {
            Query += " and (mu.user_lastname like '%" + billoutstandSearchDTO.getUser_lastname() + "%') ";
            SummaryQuery += " and (mu.user_lastname like '%" + billoutstandSearchDTO.getUser_lastname() + "%') ";
        }
        // CashCounter Id
        if (!companyId.equals("null") && !companyId.equals("0")) {
            Query += " and tspcom.sc_company_id =  " + companyId + " ";
            SummaryQuery += " and tspcom.sc_company_id =  " + companyId + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "title_name,user_firstname,user_lastname,mrNo,billNo,billDate,classType,billType,companyName,grossAmt,discount,netAmount,credit,selfPaid,balance,companyCredit,companyCollection,companyBalance,unitName";
        Query += "  limit " + ((billoutstandSearchDTO.getOffset() - 1) * billoutstandSearchDTO.getLimit()) + "," + billoutstandSearchDTO.getLimit();
        System.out.println("OutStanding Report : " + Query);
        System.out.println("CountQuery : " + CountQuery);
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(SummaryQuery).getResultList();
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("TotalGrossAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("TotalDiscount", ob[1].toString());
                jsonArray.getJSONObject(0).put("TotalNetAmount", ob[2].toString());
                jsonArray.getJSONObject(0).put("TotalPaidAmount", ob[3].toString());
                jsonArray.getJSONObject(0).put("TotalSelfBalanceAmt", ob[4].toString());
                jsonArray.getJSONObject(0).put("TotalCompanyCreditAmt", ob[5].toString());
                jsonArray.getJSONObject(0).put("TotalCompanyCollection", ob[6].toString());
                jsonArray.getJSONObject(0).put("TotalCompanyBalance", ob[7].toString());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        return ResponseEntity.ok(jsonArray.toString());
    }
    // OutStanding report End 12.11.2019

    // OutStanding report Print Start 12.11.2019
    @RequestMapping("getoutstandingDetailsReportPrint/{unitList}/{fromdate}/{todate}/{ctId}/{companyId}/{IPDFlag}")
    public String searchinvoicereportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody BillOutstandSearchDTO billoutstandSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate, @PathVariable String ctId, @PathVariable String companyId, @PathVariable int IPDFlag) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String SummaryQuery = " ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query += "  SELECT IFNULL(mt.title_name, '')as title_name, ifnull(mu.user_firstname,'')as user_firstname ,ifnull(mu.user_lastname,'') AS user_lastname," +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') billNo, " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.ipd_bill = 0,'OPD','IPD') AS classType," +
                    " IF(mv.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName," +
                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount," +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount,IFNULL(tb.company_net_pay,0) AS credit, IFNULL(tb.bill_amount_paid,0) AS selfPaid, " +
                    " IFNULL(tb.bill_outstanding,0) AS balance," +
                    " IFNULL(tb.company_net_pay,0) AS companyCredit,IFNULL(tb.company_paid_amount,0) AS companyCollection," +
                    " IFNULL(tb.bill_company_out_standing,0) AS companyBalance," +
                    " IFNULL(mun.unit_name,'') AS unitName" +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id" +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id" +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id" +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0" +
                    " AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT SUM(tb.gross_amount) AS TotalGrossAmount," +
                    " SUM(tb.bill_discount_amount) AS TotalDiscount," +
                    " SUM(tb.bill_net_payable) AS TotalNetAmount," +
                    " SUM(tb.bill_amount_paid) AS TotalPaidAmount," +
                    " SUM(tb.bill_outstanding) AS TotalSelfBalanceAmt," +
                    " SUM(tb.company_net_pay) AS TotalCompanyCreditAmt," +
                    " SUM(tb.company_paid_amount) AS TotalCompanyCollection," +
                    " SUM(tb.bill_company_out_standing) AS TotalCompanyBalance" +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id" +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id" +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id" +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0" +
                    " AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += " SELECT IFNULL(mt.title_name, '')as title_name, ifnull(mu.user_firstname,'')as user_firstname ,ifnull(mu.user_lastname,'') AS user_lastname," +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') billNo, " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.ipd_bill = 0,'OPD','IPD') AS classType, " +
                    " IF(ta.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
                    " IFNULL(tb.bill_amount_paid,0) AS selfPaid, IFNULL(tb.bill_outstanding,0) AS balance, " +
                    " IFNULL(tb.company_net_pay,0) AS companyCredit,IFNULL(tb.company_paid_amount,0) AS companyCollection, " +
                    " IFNULL(tb.bill_company_out_standing,0) AS companyBalance,IFNULL(mun.unit_name,'') AS unitName " +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                    " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
                    "  LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0 AND tb.emrbill = 0  AND tb.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT SUM(tb.gross_amount) AS TotalGrossAmount," +
                    " SUM(tb.bill_discount_amount) AS TotalDiscount," +
                    " SUM(tb.bill_net_payable) AS TotalNetAmount," +
                    " SUM(tb.bill_amount_paid) AS TotalPaidAmount," +
                    " SUM(tb.bill_outstanding) AS TotalSelfBalanceAmt," +
                    " SUM(tb.company_net_pay) AS TotalCompanyCreditAmt," +
                    " SUM(tb.company_paid_amount) AS TotalCompanyCollection," +
                    " SUM(tb.bill_company_out_standing) AS TotalCompanyBalance" +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id" +
                    " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id" +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id" +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0 AND tb.emrbill = 0  AND tb.ipd_bill = " + IPDFlag;
        } else {
            Query += " SELECT IFNULL(mt.title_name, '')as title_name, ifnull(mu.user_firstname,'')as user_firstname ,ifnull(mu.user_lastname,'') AS user_lastname," +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') billNo, " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.emrbill = 1,'EMG','') AS classType," +
                    " IF(mv.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
                    " IFNULL(tb.bill_amount_paid,0) AS selfPaid, IFNULL(tb.bill_outstanding,0) AS balance, " +
                    " IFNULL(tb.company_net_pay,0) AS companyCredit,IFNULL(tb.company_paid_amount,0) AS companyCollection, " +
                    " IFNULL(tb.bill_company_out_standing,0) AS companyBalance, " +
                    " IFNULL(mun.unit_name,'') AS unitName" +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id" +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id" +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id" +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0" +
                    " AND tb.emrbill = " + IPDFlag;
            SummaryQuery += "  SELECT SUM(tb.gross_amount) AS TotalGrossAmount," +
                    " SUM(tb.bill_discount_amount) AS TotalDiscount," +
                    " SUM(tb.bill_net_payable) AS TotalNetAmount," +
                    " SUM(tb.bill_amount_paid) AS TotalPaidAmount," +
                    " SUM(tb.bill_outstanding) AS TotalSelfBalanceAmt," +
                    " SUM(tb.company_net_pay) AS TotalCompanyCreditAmt," +
                    " SUM(tb.company_paid_amount) AS TotalCompanyCollection," +
                    " SUM(tb.bill_company_out_standing) AS TotalCompanyBalance" +
                    " FROM tbill_bill tb" +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id" +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id" +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id" +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.bill_outstanding > 0" +
                    " AND tb.emrbill = " + IPDFlag;
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
        }
        if (billoutstandSearchDTO.getTodaydate()) {
            Query += " and (date(tb.created_date)=curdate()) ";
            SummaryQuery += " and (date(tb.created_date)=curdate()) ";
        } else {
            Query += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            SummaryQuery += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and tb.tbill_unit_id in (" + values + ") ";
            SummaryQuery += " and tb.tbill_unit_id in (" + values + ") ";
        }
        if (!billoutstandSearchDTO.getMrNo().equals("")) {
            Query += " and  mp.patient_mr_no like  '%" + billoutstandSearchDTO.getMrNo() + "%' ";
            SummaryQuery += " and mp.patient_mr_no like  '%" + billoutstandSearchDTO.getMrNo() + "%' ";
        }
        if ((billoutstandSearchDTO.getUser_firstname() != null) && (!billoutstandSearchDTO.getUser_firstname().equals(""))) {
            Query += " and (mu.user_firstname like '%" + billoutstandSearchDTO.getUser_firstname() + "%') ";
            SummaryQuery += " and (mu.user_firstname like '%" + billoutstandSearchDTO.getUser_firstname() + "%') ";
        }
        if ((billoutstandSearchDTO.getUser_lastname() != null) && (!billoutstandSearchDTO.getUser_lastname().equals(""))) {
            Query += " and (mu.user_lastname like '%" + billoutstandSearchDTO.getUser_lastname() + "%') ";
            SummaryQuery += " and (mu.user_lastname like '%" + billoutstandSearchDTO.getUser_lastname() + "%') ";
        }
        // CashCounter Id
        if (!companyId.equals("null") && !companyId.equals("0")) {
            Query += " and tspcom.sc_company_id =  " + companyId + " ";
            SummaryQuery += " and tspcom.sc_company_id =  " + companyId + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "title_name,user_firstname,user_lastname,mrNo,billNo,billDate,classType,billType,companyName,grossAmt,discount,netAmount,credit,selfPaid,balance,companyCredit,companyCollection,companyBalance,unitName";
        // Query += "  limit " + ((billoutstandSearchDTO.getOffset() - 1) * billoutstandSearchDTO.getLimit()) + "," + billoutstandSearchDTO.getLimit();
        System.out.println("MainQuery : " + Query);
        System.out.println("CountQuery : " + CountQuery);
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(SummaryQuery).getResultList();
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("TotalGrossAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("TotalDiscount", ob[1].toString());
                jsonArray.getJSONObject(0).put("TotalNetAmount", ob[2].toString());
                jsonArray.getJSONObject(0).put("TotalPaidAmount", ob[3].toString());
                jsonArray.getJSONObject(0).put("TotalSelfBalanceAmt", ob[4].toString());
                jsonArray.getJSONObject(0).put("TotalCompanyCreditAmt", ob[5].toString());
                jsonArray.getJSONObject(0).put("TotalCompanyCollection", ob[5].toString());
                jsonArray.getJSONObject(0).put("TotalCompanyBalance", ob[5].toString());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        // return ResponseEntity.ok(jsonArray.toString());
        //return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(billoutstandSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(billoutstandSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (billoutstandSearchDTO.getPrint()) {
            columnName = "title_name,user_firstname,user_lastname,mrNo,billNo,billDate,classType,billType,companyName,grossAmt,discount,netAmount,credit,selfPaid,balance,companyCredit,companyCollection,companyBalance,unitName";
            return createReport.generateExcel(columnName, "OutStandingReport", jsonArray);
        } else {
            // return ResponseEntity.ok(jsonArray.toString());
            return createReport.createJasperReportPDFByJson("OutStandingReport", jsonArray.toString());
        }
    }
    // OutStanding report Print End 12.11.2019

    // Sponsor Settlment report Start 12.11.2019
    @RequestMapping("getsponsorsettlementReport/{unitList}/{mstuserlist}/{fromdate}/{todate}/{ctId}/{companyId}/{IPDFlag}")
    public ResponseEntity searchSponsorsetlreport(@RequestHeader("X-tenantId") String tenantName, @RequestBody BillSponsorDetSearchDTO billsponsorDetSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String fromdate, @PathVariable String todate, @PathVariable String ctId, @PathVariable String companyId,
                                                  @PathVariable int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String SummaryQuery = " ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query += " SELECT " +
                    " b.bill_number,b.created_date,c.class_name," +
                    " r.br_reciept_number,r.created_date as ReceiptDate," +
                    " ifnull(p.patient_mr_no,'')as MrNO,ifnull(ps.user_firstname,'')as user_firstname,ifnull(ps.user_lastname,'')as user_lastname, " +
                    " mct.ct_name AS CompanyType, mcom.company_name AS CompanyName," +
                    " b.company_net_pay AS Credit, b.company_discount_amount AS CompanyDiscount, b.company_paid_amount as CompanyPaidAmt," +
                    " b.bill_company_out_standing AS CompanyBalance, " +
                    " concat(us.user_firstname,' ',us.user_lastname)as UserName, " +
                    " u.unit_name,u.unit_id,us.user_id " +
                    " FROM tbill_reciept r " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id " +
                    "  LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id " +
                    " left join mst_class c on b.bill_class_id = c.class_id " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id " +
                    " left join mst_user us on s.staff_user_id = us.user_id " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE b.is_cancelled = 0 AND b.emrbill = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT " +
                    " ifnull(SUM(b.company_net_pay),0)AS TotalCreditAmt," +
                    " ifnull(SUM(b.company_discount_amount),0) AS TotalDiscountAmt," +
                    " ifnull(SUM(b.company_paid_amount),0) AS TotalCompanyPaidAmt," +
                    " ifnull(SUM(b.bill_company_out_standing),0) AS TotalCompanyBalanceAmt" +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id" +
                    " left join mst_class c on b.bill_class_id = c.class_id  " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id  " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id  " +
                    " left join mst_user us on s.staff_user_id = us.user_id  " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id  " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id  " +
                    " WHERE b.is_cancelled = 0 AND b.emrbill = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.ipd_bill = " + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += " SELECT " +
                    " b.bill_number,b.created_date,c.class_name," +
                    " r.br_reciept_number,r.created_date as ReceiptDate," +
                    " ifnull(p.patient_mr_no,'')as MrNO,ifnull(ps.user_firstname,'')as user_firstname,ifnull(ps.user_lastname,'')as user_lastname,  " +
                    " mct.ct_name AS CompanyType, mcom.company_name AS CompanyName," +
                    " b.company_net_pay AS Credit, b.company_discount_amount AS CompanyDiscount, b.company_paid_amount as CompanyPaidAmt," +
                    " b.bill_company_out_standing AS CompanyBalance, " +
                    " concat(us.user_firstname,' ',us.user_lastname)as UserName, " +
                    " u.unit_name,u.unit_id,us.user_id " +
                    " FROM tbill_reciept r " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id " +
                    " left join mst_class c on b.bill_class_id = c.class_id  " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id " +
                    " left join mst_user us on s.staff_user_id = us.user_id " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE b.is_cancelled = 0 AND b.emrbill = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT " +
                    " ifnull(SUM(b.company_net_pay) ,0)AS TotalCreditAmt," +
                    " ifnull(SUM(b.company_discount_amount),0) AS TotalDiscountAmt," +
                    " ifnull(SUM(b.company_paid_amount),0) AS TotalCompanyPaidAmt," +
                    " ifnull(SUM(b.bill_company_out_standing),0) AS TotalCompanyBalanceAmt " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id " +
                    " left join mst_class c on b.bill_class_id = c.class_id " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id " +
                    " left join mst_user us on s.staff_user_id = us.user_id " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE b.is_cancelled = 0 AND b.emrbill = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.ipd_bill = " + IPDFlag;
        } else {
            Query += " SELECT " +
                    " b.bill_number,b.created_date,c.class_name," +
                    " r.br_reciept_number,r.created_date as ReceiptDate," +
                    " ifnull(p.patient_mr_no,'')as MrNO,ifnull(ps.user_firstname,'')as user_firstname,ifnull(ps.user_lastname,'')as user_lastname, " +
                    " mct.ct_name AS CompanyType, mcom.company_name AS CompanyName," +
                    " b.company_net_pay AS Credit, b.company_discount_amount AS CompanyDiscount, b.company_paid_amount as CompanyPaidAmt," +
                    " b.bill_company_out_standing AS CompanyBalance, " +
                    " concat(us.user_firstname,' ',us.user_lastname)as UserName, " +
                    " u.unit_name,u.unit_id,us.user_id" +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id " +
                    " left join mst_class c on b.bill_class_id = c.class_id " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id " +
                    " left join mst_user us on s.staff_user_id = us.user_id " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE b.is_cancelled = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.emrbill = 1";
            SummaryQuery += " SELECT " +
                    " ifnull(SUM(b.company_net_pay),0) AS TotalCreditAmt," +
                    " ifnull(SUM(b.company_discount_amount),0) AS TotalDiscountAmt," +
                    " ifnull(SUM(b.company_paid_amount),0) AS TotalCompanyPaidAmt," +
                    " ifnull(SUM(b.bill_company_out_standing),0) AS TotalCompanyBalanceAmt " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id " +
                    "  LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id " +
                    " left join mst_class c on b.bill_class_id = c.class_id " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id " +
                    " left join mst_user us on s.staff_user_id = us.user_id " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE b.is_cancelled = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.emrbill = 1";
        }

        /*if (billsponsorDetSearchDTO.getFromdate().equals("") || billsponsorDetSearchDTO.getFromdate().equals("null")) {
            billsponsorDetSearchDTO.setFromdate("1990-06-07");
        }

        if (billsponsorDetSearchDTO.getTodate().equals("") || billsponsorDetSearchDTO.getTodate().equals("null")) {
            billsponsorDetSearchDTO.setTodate(strDate);
        }

        if (billsponsorDetSearchDTO.getTodaydate()) {
            Query += " and (date(b.created_date)=curdate()) ";
            SummaryQuery += " and (date(b.created_date)=curdate()) ";
        } else {
            Query += " and (date(b.created_date) between '" + billsponsorDetSearchDTO.getFromdate() + "' and '" + billsponsorDetSearchDTO.getTodate() + "')  ";
            SummaryQuery += " and (date(b.created_date) between '" + billsponsorDetSearchDTO.getFromdate() + "' and '" + billsponsorDetSearchDTO.getTodate() + "')  ";
        }*/
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (billsponsorDetSearchDTO.getTodaydate()) {
            Query += " and (date(b.created_date)=curdate()) ";
            SummaryQuery += " and (date(b.created_date)=curdate()) ";
        } else {
            Query += " and (date(b.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            SummaryQuery += " and (date(b.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and b.tbill_unit_id in (" + values + ") ";
            SummaryQuery += " and b.tbill_unit_id in (" + values + ") ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query += " and b.bill_user_id in (" + values + ") ";
        }
        if (!billsponsorDetSearchDTO.getMrNo().equals("")) {
            Query += " and  p.patient_mr_no like  '%" + billsponsorDetSearchDTO.getMrNo() + "%' ";
            SummaryQuery += " and p.patient_mr_no like  '%" + billsponsorDetSearchDTO.getMrNo() + "%' ";
        }
        if ((billsponsorDetSearchDTO.getUser_firstname() != null) && (!billsponsorDetSearchDTO.getUser_firstname().equals(""))) {
            Query += " and (ps.user_firstname like '%" + billsponsorDetSearchDTO.getUser_firstname() + "%') ";
            SummaryQuery += " and (ps.user_firstname like '%" + billsponsorDetSearchDTO.getUser_firstname() + "%') ";
        }
        if ((billsponsorDetSearchDTO.getUser_lastname() != null) && (!billsponsorDetSearchDTO.getUser_lastname().equals(""))) {
            Query += " and (ps.user_lastname like '%" + billsponsorDetSearchDTO.getUser_lastname() + "%') ";
            SummaryQuery += " and (ps.user_lastname like '%" + billsponsorDetSearchDTO.getUser_lastname() + "%') ";

        }
        // Company Type Id
        if (!ctId.equals("null") && !ctId.equals("0")) {
            Query += " and b.bill_cash_counter_id =  " + ctId + " ";
            SummaryQuery += " and b.bill_cash_counter_id =  " + ctId + " ";
        }
        // CashCounter Id
        if (!companyId.equals("null") && !companyId.equals("0")) {
            Query += " and tspcom.sc_company_id =  " + companyId + " ";
            SummaryQuery += " and tspcom.sc_company_id =  " + companyId + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "bill_number,created_date,class_name,br_reciept_number,ReceiptDate,MRNO,user_firstname,user_lastname,CompanyType,CompanyName,Credit,CompanyDiscount,CompanyPaidAmt,CompanyBalance,UserName,unit_name,unit_id,user_id";
        Query += "  limit " + ((billsponsorDetSearchDTO.getOffset() - 1) * billsponsorDetSearchDTO.getLimit()) + "," + billsponsorDetSearchDTO.getLimit();
        System.out.println("MainQuery : " + Query);
        System.out.println("CountQuery : " + CountQuery);
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(SummaryQuery).getResultList();
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("TotalCreditAmt", ob[0].toString());
                jsonArray.getJSONObject(0).put("TotalDiscountAmt", ob[1].toString());
                jsonArray.getJSONObject(0).put("TotalCompanyPaidAmt", ob[2].toString());
                jsonArray.getJSONObject(0).put("TotalCompanyBalanceAmt", ob[3].toString());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        return ResponseEntity.ok(jsonArray.toString());
    }
    // Sponsor Settlment report End 12.11.2019

    // Sponsor Settlment report Start 12.11.2019
    @RequestMapping("getsponsorsettlementReportPrint/{unitList}/{mstuserlist}/{fromdate}/{todate}/{ctId}/{companyId}/{IPDFlag}")
    public String searchSponsorsetlreportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody BillSponsorDetSearchDTO billsponsorDetSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String fromdate, @PathVariable String todate, @PathVariable String ctId, @PathVariable String companyId,
                                               @PathVariable int IPDFlag) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String SummaryQuery = " ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query += " SELECT " +
                    " b.bill_number,b.created_date,c.class_name," +
                    " r.br_reciept_number,r.created_date as ReceiptDate," +
                    " ifnull(p.patient_mr_no,'')as MrNO,ifnull(ps.user_firstname,'')as user_firstname,ifnull(ps.user_lastname,'')as user_lastname, " +
                    " mct.ct_name AS CompanyType, mcom.company_name AS CompanyName," +
                    " b.company_net_pay AS Credit, b.company_discount_amount AS CompanyDiscount, b.company_paid_amount as CompanyPaidAmt," +
                    " b.bill_company_out_standing AS CompanyBalance, " +
                    " concat(us.user_firstname,' ',us.user_lastname)as UserName, " +
                    " u.unit_name,u.unit_id,us.user_id " +
                    " FROM tbill_reciept r " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id " +
                    "  LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id " +
                    " left join mst_class c on b.bill_class_id = c.class_id " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id " +
                    " left join mst_user us on s.staff_user_id = us.user_id " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE b.is_cancelled = 0 AND b.emrbill = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT " +
                    " ifnull(SUM(b.company_net_pay),0)AS TotalCreditAmt," +
                    " ifnull(SUM(b.company_discount_amount),0) AS TotalDiscountAmt," +
                    " ifnull(SUM(b.company_paid_amount),0) AS TotalCompanyPaidAmt," +
                    " ifnull(SUM(b.bill_company_out_standing),0) AS TotalCompanyBalanceAmt" +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id" +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id" +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id" +
                    " left join mst_class c on b.bill_class_id = c.class_id  " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id  " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id  " +
                    " left join mst_user us on s.staff_user_id = us.user_id  " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id  " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id  " +
                    " WHERE b.is_cancelled = 0 AND b.emrbill = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.ipd_bill = " + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += " SELECT " +
                    " b.bill_number,b.created_date,c.class_name," +
                    " r.br_reciept_number,r.created_date as ReceiptDate," +
                    " ifnull(p.patient_mr_no,'')as MrNO,ifnull(ps.user_firstname,'')as user_firstname,ifnull(ps.user_lastname,'')as user_lastname,  " +
                    " mct.ct_name AS CompanyType, mcom.company_name AS CompanyName," +
                    " b.company_net_pay AS Credit, b.company_discount_amount AS CompanyDiscount, b.company_paid_amount as CompanyPaidAmt," +
                    " b.bill_company_out_standing AS CompanyBalance, " +
                    " concat(us.user_firstname,' ',us.user_lastname)as UserName, " +
                    " u.unit_name,u.unit_id,us.user_id " +
                    " FROM tbill_reciept r " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id " +
                    " left join mst_class c on b.bill_class_id = c.class_id  " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id " +
                    " left join mst_user us on s.staff_user_id = us.user_id " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE b.is_cancelled = 0 AND b.emrbill = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT " +
                    " ifnull(SUM(b.company_net_pay) ,0)AS TotalCreditAmt," +
                    " ifnull(SUM(b.company_discount_amount),0) AS TotalDiscountAmt," +
                    " ifnull(SUM(b.company_paid_amount),0) AS TotalCompanyPaidAmt," +
                    " ifnull(SUM(b.bill_company_out_standing),0) AS TotalCompanyBalanceAmt " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id " +
                    " left join mst_class c on b.bill_class_id = c.class_id " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id " +
                    " left join mst_user us on s.staff_user_id = us.user_id " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE b.is_cancelled = 0 AND b.emrbill = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.ipd_bill = " + IPDFlag;
        } else {
            Query += " SELECT " +
                    " b.bill_number,b.created_date,c.class_name," +
                    " r.br_reciept_number,r.created_date as ReceiptDate," +
                    " ifnull(p.patient_mr_no,'')as MrNO,ifnull(ps.user_firstname,'')as user_firstname,ifnull(ps.user_lastname,'')as user_lastname, " +
                    " mct.ct_name AS CompanyType, mcom.company_name AS CompanyName," +
                    " b.company_net_pay AS Credit, b.company_discount_amount AS CompanyDiscount, b.company_paid_amount as CompanyPaidAmt," +
                    " b.bill_company_out_standing AS CompanyBalance, " +
                    " concat(us.user_firstname,' ',us.user_lastname)as UserName, " +
                    " u.unit_name,u.unit_id,us.user_id" +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id " +
                    " left join mst_class c on b.bill_class_id = c.class_id " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id " +
                    " left join mst_user us on s.staff_user_id = us.user_id " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE b.is_cancelled = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.emrbill = " + IPDFlag;
            SummaryQuery += " SELECT " +
                    " ifnull(SUM(b.company_net_pay),0) AS TotalCreditAmt," +
                    " ifnull(SUM(b.company_discount_amount),0) AS TotalDiscountAmt," +
                    " ifnull(SUM(b.company_paid_amount),0) AS TotalCompanyPaidAmt," +
                    " ifnull(SUM(b.bill_company_out_standing),0) AS TotalCompanyBalanceAmt " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id " +
                    "  LEFT JOIN trn_bill_bill_sponsor tbsp ON b.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " LEFT JOIN mst_company_type mct ON mcom.company_ct_id = mct.ct_id " +
                    " left join mst_class c on b.bill_class_id = c.class_id " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id " +
                    " left join mst_user us on s.staff_user_id = us.user_id " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE b.is_cancelled = 0 AND tspcom.sc_company_id IS NOT NULL  AND b.emrbill = " + IPDFlag;
        }

        /*if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");
        }

        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
        }

        if (billsponsorDetSearchDTO.getTodaydate()) {
            Query += " and (date(b.created_date)=curdate()) ";
            SummaryQuery += " and (date(b.created_date)=curdate()) ";
        } else {
            Query += " and (date(b.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            SummaryQuery += " and (date(b.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }*/
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (billsponsorDetSearchDTO.getTodaydate()) {
            Query += " and (date(b.created_date)=curdate()) ";
            SummaryQuery += " and (date(b.created_date)=curdate()) ";
        } else {
            Query += " and (date(b.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            SummaryQuery += " and (date(b.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and b.tbill_unit_id in (" + values + ") ";
            SummaryQuery += " and b.tbill_unit_id in (" + values + ") ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query += " and b.bill_user_id in (" + values + ") ";
        }
        if (!billsponsorDetSearchDTO.getMrNo().equals("")) {
            Query += " and  p.patient_mr_no like  '%" + billsponsorDetSearchDTO.getMrNo() + "%' ";
            SummaryQuery += " and p.patient_mr_no like  '%" + billsponsorDetSearchDTO.getMrNo() + "%' ";
        }
        if ((billsponsorDetSearchDTO.getUser_firstname() != null) && (!billsponsorDetSearchDTO.getUser_firstname().equals(""))) {
            Query += " and (ps.user_firstname like '%" + billsponsorDetSearchDTO.getUser_firstname() + "%') ";
            SummaryQuery += " and (ps.user_firstname like '%" + billsponsorDetSearchDTO.getUser_firstname() + "%') ";
        }
        if ((billsponsorDetSearchDTO.getUser_lastname() != null) && (!billsponsorDetSearchDTO.getUser_lastname().equals(""))) {
            Query += " and (ps.user_lastname like '%" + billsponsorDetSearchDTO.getUser_lastname() + "%') ";
            SummaryQuery += " and (ps.user_lastname like '%" + billsponsorDetSearchDTO.getUser_lastname() + "%') ";

        }
        // Company Type Id
        if (!ctId.equals("null") && !ctId.equals("0")) {
            Query += " and b.bill_cash_counter_id =  " + ctId + " ";
            SummaryQuery += " and b.bill_cash_counter_id =  " + ctId + " ";
        }
        // CashCounter Id
        if (!companyId.equals("null") && !companyId.equals("0")) {
            Query += " and tspcom.sc_company_id =  " + companyId + " ";
            SummaryQuery += " and tspcom.sc_company_id =  " + companyId + " ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "bill_number,created_date,class_name,br_reciept_number,ReceiptDate,MRNO,user_firstname,user_lastname,CompanyType,CompanyName,Credit,CompanyDiscount,CompanyPaidAmt,CompanyBalance,UserName,unit_name,unit_id,user_id";
        Query += "  limit " + ((billsponsorDetSearchDTO.getOffset() - 1) * billsponsorDetSearchDTO.getLimit()) + "," + billsponsorDetSearchDTO.getLimit();
        System.out.println("MainQuery : " + Query);
        System.out.println("CountQuery : " + CountQuery);
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(SummaryQuery).getResultList();
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("TotalCreditAmt", ob[0].toString());
                jsonArray.getJSONObject(0).put("TotalDiscountAmt", ob[1].toString());
                jsonArray.getJSONObject(0).put("TotalCompanyPaidAmt", ob[2].toString());
                jsonArray.getJSONObject(0).put("TotalCompanyBalanceAmt", ob[3].toString());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        //    return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(billsponsorDetSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(billsponsorDetSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (billsponsorDetSearchDTO.getPrint()) {
            columnName = "bill_number,created_date,class_name,br_reciept_number,ReceiptDate,MRNO,user_firstname,user_lastname,CompanyType,CompanyName,Credit,CompanyDiscount,CompanyPaidAmt,CompanyBalance,UserName,unit_name,unit_id,user_id";
            return createReport.generateExcel(columnName, "SponsorsettlementReport", jsonArray);
        } else {
            // return ResponseEntity.ok(jsonArray.toString());
            return createReport.createJasperReportPDFByJson("SponsorsettlementReport", jsonArray.toString());
        }
    }
    // Sponsor Settlment report End 12.11.2019

}


