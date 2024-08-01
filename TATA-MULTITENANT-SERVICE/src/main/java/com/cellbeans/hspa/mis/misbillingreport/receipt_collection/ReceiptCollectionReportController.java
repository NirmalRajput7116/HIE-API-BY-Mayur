package com.cellbeans.hspa.mis.misbillingreport.receipt_collection;

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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/receipt_collection_report")
public class ReceiptCollectionReportController {

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

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("report/{unitList}/{userList}/{fromdate}/{todate}/{limit}/{offset}/{IPDFlag}")
    public String create(@RequestHeader("X-tenantId") String tenantName, @PathVariable String[] unitList, @PathVariable String[] userList, @PathVariable String fromdate, @PathVariable String todate, @PathVariable Integer limit, @PathVariable Integer offset, @PathVariable int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        JSONArray resultArray = new JSONArray();
        HashSet refund = new HashSet();
        String countQuery = "";
        if (IPDFlag == 0) {
            countQuery = "SELECT COUNT(*) FROM tbill_reciept tr INNER JOIN tbill_bill tb WHERE tr.br_bill_id=tb.bill_id and tr.is_deleted=FALSE AND tr.is_cancelled=0 AND tb.ipd_bill=false AND tb.emrbill=0";
        } else if (IPDFlag == 1) {
            countQuery = "SELECT COUNT(*) FROM tbill_reciept tr INNER JOIN tbill_bill tb WHERE tr.br_bill_id=tb.bill_id and tr.is_deleted=FALSE AND tr.is_cancelled=0 AND tb.ipd_bill=true AND tb.emrbill=0";
        } else {
            countQuery = "SELECT COUNT(*) FROM tbill_reciept tr INNER JOIN tbill_bill tb WHERE tr.br_bill_id=tb.bill_id and tr.is_deleted=FALSE AND tr.is_cancelled=0 AND tb.emrbill=1";
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "";
        if (IPDFlag == 0) {
            Query = "SELECT tr.br_reciept_number AS recp_no,tr.created_date,tr.br_payment_amount AS paid_amount,tr.br_bill_id AS bill_id," +
                    "(SELECT CONCAT(mst_user.user_firstname,' ',mst_user.user_lastname) FROM mst_patient mp INNER JOIN mst_user " +
                    " WHERE mp.patient_user_id=mst_user.user_id AND mp.patient_id=tr.br_patient_id) AS patient_id,mp.pm_name AS payment_mode," +
                    " mu.user_fullname AS patient_name,mc.cc_name AS cash_counter,(SELECT mst_unit.unit_name FROM mst_unit " +
                    " where mst_unit.unit_id=tb.tbill_unit_id) as unit_name,tb.refund_amount,tr.br_id,tb.company_net_pay AS company_pay, tb.company_paid_amount AS company_paid" +
                    " FROM tbill_reciept tr INNER JOIN tbill_bill tb INNER JOIN mst_payment_mode mp INNER JOIN mst_staff ms " +
                    " INNER JOIN mst_user mu INNER JOIN mst_cash_counter mc where tr.br_bill_id=tb.bill_id" +
                    " AND mp.pm_id=tr.br_pm_id AND tb.bill_user_id=ms.staff_id AND mu.user_id=ms.staff_user_id " +
                    " AND tb.bill_cash_counter_id=mc.cc_id AND tr.is_active=TRUE AND tr.is_cancelled=0 AND tb.ipd_bill=false AND tb.emrbill=0 and tr.is_deleted=FALSE";
        } else if (IPDFlag == 1) {
            Query = "SELECT tr.br_reciept_number AS recp_no,tr.created_date,tr.br_payment_amount AS paid_amount,tr.br_bill_id AS bill_id," +
                    "(SELECT CONCAT(mst_user.user_firstname,' ',mst_user.user_lastname) FROM mst_patient mp INNER JOIN mst_user " +
                    " WHERE mp.patient_user_id=mst_user.user_id AND mp.patient_id=tr.br_patient_id) AS patient_id,mp.pm_name AS payment_mode," +
                    " mu.user_fullname AS patient_name,mc.cc_name AS cash_counter,(SELECT mst_unit.unit_name FROM mst_unit " +
                    " where mst_unit.unit_id=tb.tbill_unit_id) as unit_name,tb.refund_amount,tr.br_id,tb.company_net_pay AS company_pay, tb.company_paid_amount AS company_paid" +
                    " FROM tbill_reciept tr INNER JOIN tbill_bill tb INNER JOIN mst_payment_mode mp INNER JOIN mst_staff ms " +
                    " INNER JOIN mst_user mu INNER JOIN mst_cash_counter mc where tr.br_bill_id=tb.bill_id" +
                    " AND mp.pm_id=tr.br_pm_id AND tb.bill_user_id=ms.staff_id AND mu.user_id=ms.staff_user_id " +
                    " AND tb.bill_cash_counter_id=mc.cc_id AND tr.is_active=TRUE AND tr.is_cancelled=0 AND tb.ipd_bill=true AND tb.emrbill=0 and tr.is_deleted=FALSE";

        } else {
            Query = "SELECT tr.br_reciept_number AS recp_no,tr.created_date,tr.br_payment_amount AS paid_amount,tr.br_bill_id AS bill_id," +
                    "(SELECT CONCAT(mst_user.user_firstname,' ',mst_user.user_lastname) FROM mst_patient mp INNER JOIN mst_user " +
                    " WHERE mp.patient_user_id=mst_user.user_id AND mp.patient_id=tr.br_patient_id) AS patient_id,mp.pm_name AS payment_mode," +
                    " mu.user_fullname AS patient_name,mc.cc_name AS cash_counter,(SELECT mst_unit.unit_name FROM mst_unit " +
                    " where mst_unit.unit_id=tb.tbill_unit_id) as unit_name,tb.refund_amount,tr.br_id,tb.company_net_pay AS company_pay, tb.company_paid_amount AS company_paid" +
                    " FROM tbill_reciept tr INNER JOIN tbill_bill tb INNER JOIN mst_payment_mode mp INNER JOIN mst_staff ms " +
                    " INNER JOIN mst_user mu INNER JOIN mst_cash_counter mc where tr.br_bill_id=tb.bill_id" +
                    " AND mp.pm_id=tr.br_pm_id AND tb.bill_user_id=ms.staff_id AND mu.user_id=ms.staff_user_id " +
                    " AND tb.bill_cash_counter_id=mc.cc_id AND tr.is_active=TRUE AND tr.is_cancelled=0 AND tb.emrbill=1 and tr.is_deleted=FALSE";
        }
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
            Query += " and tb.tbill_unit_id in (" + values + ") ";
            countQuery += " and tb.tbill_unit_id in (" + values + ") ";

        }
        if (!userList[0].equals("null")) {
            String values = userList[0];
            for (int i = 1; i < userList.length; i++) {
                values += "," + userList[i];
            }
            Query += " and tb.bill_user_id in (" + values + ") ";
            countQuery += " and tb.bill_user_id in (" + values + ") ";
        }
        if (fromdate != null && !fromdate.equals("null") && !fromdate.isEmpty()) {
            Query += "  AND tr.created_date > '" + fromdate + " 00:00:00' AND tr.created_date < '" + todate + " 23:59:59'";
            countQuery += "  AND tr.created_date > '" + fromdate + " 00:00:00' AND tr.created_date < '" + todate + " 23:59:59'";
        }
        Query += "  ORDER BY tr.created_date desc ";
        Query += "  limit " + ((offset - 1) * limit) + "," + limit;
        System.out.println("Collection Reports : " + Query);
        BigInteger count = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
        List<Object[]> result = entityManager.createNativeQuery(Query).getResultList();
        String unitName = "";
        for (int i = 0; i < result.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("receipt_no", "" + result.get(i)[0]);
            object.put("count", count);
            object.put("date", "" + result.get(i)[1]);
            object.put("paid_amount", "" + result.get(i)[2]);
            object.put("bill_id", "" + result.get(i)[3]);
            object.put("patient_id", "" + result.get(i)[4]);
            object.put("recipt_type", "Payment Collection");
            StringBuilder sb = new StringBuilder("" + result.get(i)[5]);
            if (sb.toString().equalsIgnoreCase("cash")) {
                object.put("cash", "" + result.get(i)[2]);
                object.put("payment_mode", "cash");
            } else if (sb.toString().toLowerCase().contains("card")) {
                object.put("card", "" + result.get(i)[2]);
                object.put("payment_mode", "card");
            } else if (sb.toString().equalsIgnoreCase("cheque")) {
                object.put("cheque", "" + result.get(i)[2]);
                object.put("payment_mode", "cheque");
            } else {
                object.put("other", "" + result.get(i)[2]);
                object.put("payment_mode", "other");
            }
            object.put("patient_name", "" + result.get(i)[6]);
            object.put("cash_counter", "" + result.get(i)[7]);
            object.put("unit_name", "" + result.get(i)[8]);
            unitName = "" + result.get(i)[8];
            object.put("company_net_pay", "" + result.get(i)[11]);
            object.put("company_paid_amount", "" + result.get(i)[12]);
            resultArray.put(object);
            Double refund_amount = Double.parseDouble("" + result.get(i)[9]);
            if (refund_amount > 0) {
                if (!refund.contains("" + result.get(i)[3])) {
                    JSONObject dum = new JSONObject();
                    dum.put("receipt_no", "PAYREF/" + result.get(i)[10]);
                    dum.put("count", count);
                    dum.put("date", object.get("date"));
                    dum.put("bill_id", object.get("bill_id"));
                    dum.put("patient_id", object.get("patient_id"));
                    dum.put("recipt_type", "Payment Refund");
                    if (sb.toString().equalsIgnoreCase("cash")) {
                        dum.put("cash", "" + refund_amount);
                        dum.put("payment_mode", "cash");
                    } else if (sb.toString().toLowerCase().contains("card")) {
                        dum.put("card", "" + refund_amount);
                        dum.put("payment_mode", "card");
                    } else if (sb.toString().equalsIgnoreCase("cheque")) {
                        dum.put("cheque", "" + refund_amount);
                        dum.put("payment_mode", "cheque");
                    } else {
                        dum.put("other", "" + refund_amount);
                        dum.put("payment_mode", "other");
                    }
                    dum.put("patient_name", object.get("patient_name"));
                    dum.put("cash_counter", object.get("cash_counter"));
                    dum.put("unit_name", object.get("unit_name"));
                    dum.put("paid_amount", refund_amount);
                    resultArray.put(dum);
                }
            }
            refund.add("" + result.get(i)[3]);
        }
        if (IPDFlag == 1) {
            String advanceQuery = "SELECT  (SELECT CONCAT(mu.user_firstname,' ' ,mu.user_lastname)  FROM mst_patient mp INNER JOIN mst_user mu WHERE  mu.user_id=mp.patient_user_id and mp.patient_id=trn_ad.aa_patient_id ) as name,trn_ad.advanced_amount,trn_ad.ipdadvanced_id as rec_no,mp.pm_name,trn_ad.aa_patient_id,trn_ad.created_date,(SELECT mu.user_firstname FROM mst_patient mp INNER JOIN mst_user mu WHERE mu.user_id=mp.patient_user_id and mp.patient_id=trn_ad.aa_patient_id) AS patient_name FROM trn_ipd_advanced trn_ad INNER JOIN mst_payment_mode mp inner JOIN mst_staff ms INNER JOIN mst_user mu WHERE trn_ad.created_date > '" + fromdate + " 00:00:00' AND trn_ad.created_date < '" + todate + " 23:59:59' AND mp.pm_id=trn_ad.aa_pm_id AND ms.staff_user_id=mu.user_id AND ms.staff_id=trn_ad.aa_user_id";
            List<Object[]> advanceResult = entityManager.createNativeQuery(advanceQuery).getResultList();
            for (int j = 0; j < advanceResult.size(); j++) {
                JSONObject dum = new JSONObject();
                dum.put("receipt_no", "ADVCOLL" + advanceResult.get(j)[2]);
                dum.put("count", count);
                dum.put("date", "" + advanceResult.get(j)[5]);
                dum.put("bill_id", "" + advanceResult.get(j)[2]);
                dum.put("patient_id", "" + advanceResult.get(j)[6]);
                dum.put("recipt_type", "Advance Collection");
                if (("" + advanceResult.get(j)[3]).toLowerCase().contains("cash")) {
                    dum.put("cash", "" + advanceResult.get(j)[1]);
                    dum.put("payment_mode", "cash");
                } else if (("" + advanceResult.get(j)[3]).toLowerCase().contains("card")) {
                    dum.put("card", "" + advanceResult.get(j)[1]);
                    dum.put("payment_mode", "card");
                } else if (("" + advanceResult.get(j)[3]).toLowerCase().contains("cheque")) {
                    dum.put("cheque", "" + advanceResult.get(j)[1]);
                    dum.put("payment_mode", "cheque");
                } else {
                    dum.put("other", "" + advanceResult.get(j)[1]);
                    dum.put("payment_mode", "other");
                }
                dum.put("patient_name", advanceResult.get(j)[0]);
                dum.put("cash_counter", "");
                dum.put("unit_name", unitName);
                dum.put("paid_amount", advanceResult.get(j)[1]);
                resultArray.put(dum);
            }
            String advanceConsumptioQuery = "SELECT  (SELECT CONCAT(mu.user_firstname,' ' ,mu.user_lastname)  FROM mst_patient mp INNER JOIN mst_user mu WHERE  mu.user_id=mp.patient_user_id and mp.patient_id=trn_ad.aa_patient_id ) as name,trn_ad.advanced_amount,ah.id as history_id,mp.pm_name,trn_ad.aa_patient_id,trn_ad.created_date,(SELECT mu.user_firstname FROM mst_patient mp INNER JOIN mst_user mu WHERE mu.user_id=mp.patient_user_id and mp.patient_id=trn_ad.aa_patient_id) AS patient_name,ah.consumed_amount,ah.id FROM advanced_history ah INNER join trn_ipd_advanced trn_ad INNER JOIN mst_payment_mode mp  WHERE  ah.advanced_amount_id=trn_ad.ipdadvanced_id and ah.consumed_date > '" + fromdate + " 00:00:00' AND ah.consumed_date < '" + todate + " 23:59:59' AND mp.pm_id=trn_ad.aa_pm_id";
            List<Object[]> advanceConsumptioResult = entityManager.createNativeQuery(advanceConsumptioQuery).getResultList();
            for (int j = 0; j < advanceConsumptioResult.size(); j++) {
                JSONObject dum = new JSONObject();
                dum.put("receipt_no", "ADVCON" + advanceConsumptioResult.get(j)[8]);
                dum.put("count", count);
                dum.put("date", "" + advanceConsumptioResult.get(j)[5]);
                dum.put("bill_id", "" + advanceConsumptioResult.get(j)[2]);
                dum.put("patient_id", "" + advanceConsumptioResult.get(j)[6]);
                dum.put("recipt_type", "Advance Consumption");
                if (("" + advanceConsumptioResult.get(j)[3]).toLowerCase().contains("cash")) {
                    dum.put("cash", "" + advanceConsumptioResult.get(j)[7]);
                    dum.put("payment_mode", "cash");
                } else if (("" + advanceConsumptioResult.get(j)[3]).toLowerCase().contains("card")) {
                    dum.put("card", "" + advanceConsumptioResult.get(j)[7]);
                    dum.put("payment_mode", "card");
                } else if (("" + advanceConsumptioResult.get(j)[3]).toLowerCase().contains("cheque")) {
                    dum.put("cheque", "" + advanceConsumptioResult.get(j)[7]);
                    dum.put("payment_mode", "cheque");
                } else {
                    dum.put("other", "" + advanceConsumptioResult.get(j)[7]);
                    dum.put("payment_mode", "other");
                }
                dum.put("patient_name", advanceConsumptioResult.get(j)[0]);
                dum.put("cash_counter", "");
                dum.put("unit_name", unitName);
                dum.put("paid_amount", advanceConsumptioResult.get(j)[7]);
                resultArray.put(dum);
            }
            String advanceRefund = "SELECT  mu.user_name,trn_ad.advanced_amount,ah.id as refund_id,mp.pm_name,trn_ad.aa_patient_id,trn_ad.created_date,(SELECT mu.user_firstname FROM mst_patient mp INNER JOIN mst_user mu WHERE mu.user_id=mp.patient_user_id and mp.patient_id=trn_ad.aa_patient_id) AS patient_name,ah.advance_refund_amount,ah.id FROM advance_refund_history ah INNER join trn_ipd_advanced trn_ad INNER JOIN mst_payment_mode mp inner JOIN mst_staff ms INNER JOIN mst_user mu WHERE  ah.advance_id=trn_ad.ipdadvanced_id and ah.advance_refund_date > '" + fromdate + "' AND ah.advance_refund_date < '" + todate + "' AND mp.pm_id=trn_ad.aa_pm_id AND ms.staff_user_id=mu.user_id AND ms.staff_id=trn_ad.aa_user_id;";
            List<Object[]> advanceRefundList = entityManager.createNativeQuery(advanceRefund).getResultList();
            for (int j = 0; j < advanceRefundList.size(); j++) {
                JSONObject dum = new JSONObject();
                dum.put("receipt_no", "ADVREFUND" + advanceRefundList.get(j)[8]);
                dum.put("count", count);
                dum.put("date", "" + advanceRefundList.get(j)[5]);
                dum.put("bill_id", "" + advanceRefundList.get(j)[2]);
                dum.put("patient_id", "" + advanceRefundList.get(j)[6]);
                dum.put("recipt_type", "Advance Refund");
                if (("" + advanceRefundList.get(j)[3]).toLowerCase().contains("cash")) {
                    dum.put("cash", "" + advanceRefundList.get(j)[7]);
                    dum.put("payment_mode", "cash");
                } else if (("" + advanceRefundList.get(j)[3]).toLowerCase().contains("card")) {
                    dum.put("card", "" + advanceRefundList.get(j)[7]);
                    dum.put("payment_mode", "card");
                } else if (("" + advanceRefundList.get(j)[3]).toLowerCase().contains("cheque")) {
                    dum.put("cheque", "" + advanceRefundList.get(j)[7]);
                    dum.put("payment_mode", "cheque");
                } else {
                    dum.put("other", "" + advanceRefundList.get(j)[7]);
                    dum.put("payment_mode", "other");
                }
                dum.put("patient_name", advanceRefundList.get(j)[0]);
                dum.put("cash_counter", "");
                dum.put("unit_name", unitName);
                dum.put("paid_amount", advanceRefundList.get(j)[7]);
                resultArray.put(dum);
            }
        }
        LinkedHashMap<String, JSONObject> resultMap = new LinkedHashMap<String, JSONObject>();
        for (int i = 0; i < resultArray.length(); i++) {
            if (resultMap.containsKey(resultArray.getJSONObject(i).get("receipt_no").toString())) {
                JSONObject old_obj = resultArray.getJSONObject(i);
                JSONObject new_obj = resultMap.get(resultArray.getJSONObject(i).get("receipt_no").toString());
                String payment_mode = old_obj.getString("payment_mode");
                if (payment_mode.equals("cash")) {
                    String cash = null;
                    try {
                        cash = new_obj.get("cash").toString();
                    } catch (Exception e) {
                    }
                    if (cash == null) {
                        new_obj.put("cash", old_obj.get("cash"));
                    } else {
                        Double new_val = new_obj.getDouble("cash");
                        Double old_val = old_obj.getDouble("cash");
                        Double final_val = new_val + old_val;
                        new_obj.put("cash", final_val);
                    }
                }
                if (payment_mode.equals("card")) {
                    String card = null;
                    try {
                        card = new_obj.get("card").toString();
                    } catch (Exception e) {
                    }
                    if (card == null) {
                        new_obj.put("card", old_obj.get("card"));
                    } else {
                        Double new_val = new_obj.getDouble("card");
                        Double old_val = old_obj.getDouble("card");
                        Double final_val = new_val + old_val;
                        new_obj.put("card", final_val);
                    }
                }
                if (payment_mode.equals("cheque")) {
                    String cheque = null;
                    try {
                        cheque = new_obj.get("cheque").toString();
                    } catch (Exception e) {
                    }
                    if (cheque == null) {
                        new_obj.put("cheque", old_obj.get("cheque"));
                    } else {
                        Double new_val = new_obj.getDouble("cheque");
                        Double old_val = old_obj.getDouble("cheque");
                        Double final_val = new_val + old_val;
                        new_obj.put("cheque", final_val);

                    }
                }
                if (payment_mode.equals("other")) {
                    String other = null;
                    try {
                        other = new_obj.get("other").toString();
                    } catch (Exception e) {
                    }
                    if (other == null) {
                        new_obj.put("other", old_obj.get("other"));
                    } else {
                        Double new_val = new_obj.getDouble("other");
                        Double old_val = old_obj.getDouble("other");
                        Double final_val = new_val + old_val;
                        new_obj.put("other", final_val);

                    }
                }
                Double finla_total = 0.0;
                String cash = null;
                try {
                    cash = new_obj.get("cash").toString();
                    finla_total = finla_total + new_obj.getDouble("cash");
                } catch (Exception e) {
                }
                try {
                    cash = new_obj.get("card").toString();
                    finla_total = finla_total + new_obj.getDouble("card");
                } catch (Exception e) {
                }
                try {
                    cash = new_obj.get("cheque").toString();
                    finla_total = finla_total + new_obj.getDouble("cheque");
                } catch (Exception e) {
                }
                try {
                    cash = new_obj.get("other").toString();
                    finla_total = finla_total + new_obj.getDouble("other");
                } catch (Exception e) {
                }
                new_obj.put("paid_amount", finla_total);
                resultMap.put(resultArray.getJSONObject(i).get("receipt_no").toString(), new_obj);
            } else {
                resultMap.put(resultArray.getJSONObject(i).get("receipt_no").toString(), resultArray.getJSONObject(i));
            }
        }
        JSONArray finalResutlt = new JSONArray();
        for (Map.Entry<String, JSONObject> entry : resultMap.entrySet()) {
            finalResutlt.put(entry.getValue());
        }
        finalResutlt = calculateFinalSummary(tenantName, fromdate, todate, unitList, userList, finalResutlt, IPDFlag);
        return finalResutlt.toString();
    }

    @RequestMapping("reportPrint/{unitList}/{userList}/{fromdate}/{todate}/{IPDFlag}")
    public String createPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody RecieptCollectionsearchDTO recieptcollectionsearchDTO, @PathVariable String[] unitList, @PathVariable String[] userList, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        JSONArray resultArray = new JSONArray();
        HashSet refund = new HashSet();
        String countQuery = "";
        if (IPDFlag == 0) {
            countQuery = "SELECT COUNT(*) FROM tbill_reciept tr INNER JOIN tbill_bill tb WHERE tr.br_bill_id=tb.bill_id and tr.is_deleted=FALSE AND tr.is_cancelled=0 AND tb.ipd_bill=false AND tb.emrbill=0";
        } else if (IPDFlag == 1) {
            countQuery = "SELECT COUNT(*) FROM tbill_reciept tr INNER JOIN tbill_bill tb WHERE tr.br_bill_id=tb.bill_id and tr.is_deleted=FALSE AND tr.is_cancelled=0 AND tb.ipd_bill=true AND tb.emrbill=0";
        } else {
            countQuery = "SELECT COUNT(*) FROM tbill_reciept tr INNER JOIN tbill_bill tb WHERE tr.br_bill_id=tb.bill_id and tr.is_deleted=FALSE AND tr.is_cancelled=0 AND tb.emrbill=1";
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "";
        if (IPDFlag == 0) {
            Query = "SELECT tr.br_reciept_number AS recp_no,tr.created_date,tr.br_payment_amount AS paid_amount,tr.br_bill_id AS bill_id," +
                    "(SELECT CONCAT(mst_user.user_firstname,' ',mst_user.user_lastname) FROM mst_patient mp INNER JOIN mst_user " +
                    " WHERE mp.patient_user_id=mst_user.user_id AND mp.patient_id=tr.br_patient_id) AS patient_id,mp.pm_name AS payment_mode," +
                    " mu.user_fullname AS patient_name,mc.cc_name AS cash_counter,(SELECT mst_unit.unit_name FROM mst_unit " +
                    " where mst_unit.unit_id=tb.tbill_unit_id) as unit_name,tb.refund_amount,tr.br_id " +
                    " FROM tbill_reciept tr INNER JOIN tbill_bill tb INNER JOIN mst_payment_mode mp INNER JOIN mst_staff ms " +
                    " INNER JOIN mst_user mu INNER JOIN mst_cash_counter mc where tr.br_bill_id=tb.bill_id" +
                    " AND mp.pm_id=tr.br_pm_id AND tb.bill_user_id=ms.staff_id AND mu.user_id=ms.staff_user_id " +
                    " AND tb.bill_cash_counter_id=mc.cc_id AND tr.is_active=TRUE AND tr.is_cancelled=0 AND tb.ipd_bill=false AND tb.emrbill=0 and tr.is_deleted=FALSE";
        } else if (IPDFlag == 1) {
            Query = "SELECT tr.br_reciept_number AS recp_no,tr.created_date,tr.br_payment_amount AS paid_amount,tr.br_bill_id AS bill_id," +
                    "(SELECT CONCAT(mst_user.user_firstname,' ',mst_user.user_lastname) FROM mst_patient mp INNER JOIN mst_user " +
                    " WHERE mp.patient_user_id=mst_user.user_id AND mp.patient_id=tr.br_patient_id) AS patient_id,mp.pm_name AS payment_mode," +
                    " mu.user_fullname AS patient_name,mc.cc_name AS cash_counter,(SELECT mst_unit.unit_name FROM mst_unit " +
                    " where mst_unit.unit_id=tb.tbill_unit_id) as unit_name,tb.refund_amount,tr.br_id " +
                    " FROM tbill_reciept tr INNER JOIN tbill_bill tb INNER JOIN mst_payment_mode mp INNER JOIN mst_staff ms " +
                    " INNER JOIN mst_user mu INNER JOIN mst_cash_counter mc where tr.br_bill_id=tb.bill_id" +
                    " AND mp.pm_id=tr.br_pm_id AND tb.bill_user_id=ms.staff_id AND mu.user_id=ms.staff_user_id " +
                    " AND tb.bill_cash_counter_id=mc.cc_id AND tr.is_active=TRUE AND tr.is_cancelled=0 AND tb.ipd_bill=true AND tb.emrbill=0 and tr.is_deleted=FALSE";

        } else {
            Query = "SELECT tr.br_reciept_number AS recp_no,tr.created_date,tr.br_payment_amount AS paid_amount,tr.br_bill_id AS bill_id," +
                    "(SELECT CONCAT(mst_user.user_firstname,' ',mst_user.user_lastname) FROM mst_patient mp INNER JOIN mst_user " +
                    " WHERE mp.patient_user_id=mst_user.user_id AND mp.patient_id=tr.br_patient_id) AS patient_id,mp.pm_name AS payment_mode," +
                    " mu.user_fullname AS patient_name,mc.cc_name AS cash_counter,(SELECT mst_unit.unit_name FROM mst_unit " +
                    " where mst_unit.unit_id=tb.tbill_unit_id) as unit_name,tb.refund_amount,tr.br_id " +
                    " FROM tbill_reciept tr INNER JOIN tbill_bill tb INNER JOIN mst_payment_mode mp INNER JOIN mst_staff ms " +
                    " INNER JOIN mst_user mu INNER JOIN mst_cash_counter mc where tr.br_bill_id=tb.bill_id" +
                    " AND mp.pm_id=tr.br_pm_id AND tb.bill_user_id=ms.staff_id AND mu.user_id=ms.staff_user_id " +
                    " AND tb.bill_cash_counter_id=mc.cc_id AND tr.is_active=TRUE AND tr.is_cancelled=0 AND tb.emrbill=1 and tr.is_deleted=FALSE";
        }
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
            Query += " and tb.tbill_unit_id in (" + values + ") ";
            countQuery += " and tb.tbill_unit_id in (" + values + ") ";

        }
        if (!userList[0].equals("null")) {
            String values = userList[0];
            for (int i = 1; i < userList.length; i++) {
                values += "," + userList[i];
            }
            Query += " and tb.bill_user_id in (" + values + ") ";
            countQuery += " and tb.bill_user_id in (" + values + ") ";
        }
        if (fromdate != null && !fromdate.equals("null") && !fromdate.isEmpty()) {
            Query += "  AND tr.created_date > '" + fromdate + " 00:00:00' AND tr.created_date < '" + todate + " 23:59:59'";
            countQuery += "  AND tr.created_date > '" + fromdate + " 00:00:00' AND tr.created_date < '" + todate + " 23:59:59'";
        }
        Query += "  ORDER BY tr.created_date desc ";
        //   Query += "  limit " + ((offset - 1) * limit) + "," + limit;
        System.out.println("Query : " + Query);
        BigInteger count = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
        List<Object[]> result = entityManager.createNativeQuery(Query).getResultList();
        for (int i = 0; i < result.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("receipt_no", "" + result.get(i)[0]);
            object.put("count", count);
            object.put("date", "" + result.get(i)[1]);
            object.put("paid_amount", "" + result.get(i)[2]);
            object.put("bill_id", "" + result.get(i)[3]);
            object.put("patient_id", "" + result.get(i)[4]);
            object.put("recipt_type", "Payment Collection");
            StringBuilder sb = new StringBuilder("" + result.get(i)[5]);
            if (sb.toString().equalsIgnoreCase("cash")) {
                object.put("cash", "" + result.get(i)[2]);
                object.put("payment_mode", "cash");
            } else if (sb.toString().toLowerCase().contains("card")) {
                object.put("card", "" + result.get(i)[2]);
                object.put("payment_mode", "card");
            } else if (sb.toString().equalsIgnoreCase("cheque")) {
                object.put("cheque", "" + result.get(i)[2]);
                object.put("payment_mode", "cheque");
            } else {
                object.put("other", "" + result.get(i)[2]);
                object.put("payment_mode", "other");
            }
            object.put("patient_name", "" + result.get(i)[6]);
            object.put("cash_counter", "" + result.get(i)[7]);
            object.put("unit_name", "" + result.get(i)[8]);
            resultArray.put(object);
            Double refund_amount = Double.parseDouble("" + result.get(i)[9]);
            if (refund_amount > 0) {
                if (!refund.contains("" + result.get(i)[3])) {
                    JSONObject dum = new JSONObject();
                    dum.put("receipt_no", "PAYREF/" + result.get(i)[10]);
                    dum.put("count", count);
                    dum.put("date", object.get("date"));
                    dum.put("bill_id", object.get("bill_id"));
                    dum.put("patient_id", object.get("patient_id"));
                    dum.put("recipt_type", "Payment Refund");
                    if (sb.toString().equalsIgnoreCase("cash")) {
                        dum.put("cash", "" + refund_amount);
                        dum.put("payment_mode", "cash");
                    } else if (sb.toString().toLowerCase().contains("card")) {
                        dum.put("card", "" + refund_amount);
                        dum.put("payment_mode", "card");
                    } else if (sb.toString().equalsIgnoreCase("cheque")) {
                        dum.put("cheque", "" + refund_amount);
                        dum.put("payment_mode", "cheque");
                    } else {
                        dum.put("other", "" + refund_amount);
                        dum.put("payment_mode", "other");
                    }
                    dum.put("patient_name", object.get("patient_name"));
                    dum.put("cash_counter", object.get("cash_counter"));
                    dum.put("unit_name", object.get("unit_name"));
                    dum.put("paid_amount", refund_amount);
                    resultArray.put(dum);
                }
            }
            if (!refund.contains("" + result.get(i)[3])) {
                String advance_query = "SELECT  ifnull(advanced_amount,0) AS advanced_amount,ifnull(ah.consumed_amount,0) AS advanced_consumed," +
                        "ifnull(refund_amount,0) AS advance_refund,ah.id,ipd_adv.ipdadvanced_id FROM trn_ipd_advanced ipd_adv INNER JOIN advanced_history ah " +
                        "WHERE ipd_adv.ipdadvanced_id=ah.advanced_amount_id AND ah.bill_id=" + object.get("bill_id");
                System.out.println("advance_query : " + advance_query);
                List<Object[]> advanceDetail = entityManager.createNativeQuery(advance_query).getResultList();
                for (int j = 0; j < advanceDetail.size(); j++) {
                    Double advanceCollection = Double.parseDouble("" + advanceDetail.get(j)[0]);
                    Double advanceConsumed = Double.parseDouble("" + advanceDetail.get(j)[1]);
                    Double advanceRefund = Double.parseDouble("" + advanceDetail.get(j)[2]);
                    if (advanceCollection > 0) {
                        if (!refund.contains("" + result.get(i)[3])) {
                            JSONObject dum = new JSONObject();
                            dum.put("receipt_no", "ADVCOLL/" + advanceDetail.get(j)[4]);
                            dum.put("count", count);
                            dum.put("date", object.get("date"));
                            dum.put("bill_id", object.get("bill_id"));
                            dum.put("patient_id", object.get("patient_id"));
                            dum.put("recipt_type", "Advance Collection");
                            if (sb.toString().equalsIgnoreCase("cash")) {
                                dum.put("cash", "" + advanceCollection);
                                dum.put("payment_mode", "cash");
                            } else if (sb.toString().toLowerCase().contains("card")) {
                                dum.put("card", "" + advanceCollection);
                                dum.put("payment_mode", "card");
                            } else if (sb.toString().equalsIgnoreCase("cheque")) {
                                dum.put("cheque", "" + advanceCollection);
                                dum.put("payment_mode", "cheque");
                            } else {
                                dum.put("other", "" + advanceCollection);
                                dum.put("payment_mode", "other");
                            }
                            dum.put("patient_name", object.get("patient_name"));
                            dum.put("cash_counter", object.get("cash_counter"));
                            dum.put("unit_name", object.get("unit_name"));
                            dum.put("paid_amount", advanceCollection);
                            resultArray.put(dum);
                        }
                    }
                    if (advanceRefund > 0) {
                        JSONObject dum = new JSONObject();
                        dum.put("receipt_no", "ADVREF/" + advanceDetail.get(j)[4]);
                        dum.put("count", count);
                        dum.put("date", object.get("date"));
                        dum.put("bill_id", object.get("bill_id"));
                        dum.put("patient_id", object.get("patient_id"));
                        dum.put("recipt_type", "Advance Refund");
                        if (sb.toString().equalsIgnoreCase("cash")) {
                            dum.put("cash", "" + advanceRefund);
                            dum.put("payment_mode", "cash");
                        } else if (sb.toString().toLowerCase().contains("card")) {
                            dum.put("card", "" + advanceRefund);
                            dum.put("payment_mode", "card");
                        } else if (sb.toString().equalsIgnoreCase("cheque")) {
                            dum.put("cheque", "" + advanceRefund);
                            dum.put("payment_mode", "cheque");
                        } else {
                            dum.put("other", "" + advanceRefund);
                            dum.put("payment_mode", "other");
                        }
                        dum.put("patient_name", object.get("patient_name"));
                        dum.put("cash_counter", object.get("cash_counter"));
                        dum.put("unit_name", object.get("unit_name"));
                        dum.put("paid_amount", advanceRefund);
                        resultArray.put(dum);

                    }
                    if (advanceConsumed > 0) {
                        JSONObject dum = new JSONObject();
                        dum.put("receipt_no", "ADVCONS/" + advanceDetail.get(j)[3]);
                        dum.put("count", count);
                        dum.put("date", object.get("date"));
                        dum.put("bill_id", object.get("bill_id"));
                        dum.put("patient_id", object.get("patient_id"));
                        dum.put("recipt_type", "Advance Consumption");
                        if (sb.toString().equalsIgnoreCase("cash")) {
                            dum.put("cash", "" + advanceConsumed);
                            dum.put("payment_mode", "cash");
                        } else if (sb.toString().toLowerCase().contains("card")) {
                            dum.put("card", "" + advanceConsumed);
                            dum.put("payment_mode", "card");
                        } else if (sb.toString().equalsIgnoreCase("cheque")) {
                            dum.put("cheque", "" + advanceConsumed);
                            dum.put("payment_mode", "cheque");
                        } else {
                            dum.put("other", "" + advanceConsumed);
                            dum.put("payment_mode", "other");
                        }
                        dum.put("patient_name", object.get("patient_name"));
                        dum.put("cash_counter", object.get("cash_counter"));
                        dum.put("unit_name", object.get("unit_name"));
                        dum.put("paid_amount", advanceConsumed);
                        resultArray.put(dum);

                    }

                }
            }
            refund.add("" + result.get(i)[3]);
        }
        LinkedHashMap<String, JSONObject> resultMap = new LinkedHashMap<String, JSONObject>();
        for (int i = 0; i < resultArray.length(); i++) {
            if (resultMap.containsKey(resultArray.getJSONObject(i).get("receipt_no").toString())) {
                JSONObject old_obj = resultArray.getJSONObject(i);
                JSONObject new_obj = resultMap.get(resultArray.getJSONObject(i).get("receipt_no").toString());
                String payment_mode = old_obj.getString("payment_mode");
                if (payment_mode.equals("cash")) {
                    String cash = null;
                    try {
                        cash = new_obj.get("cash").toString();
                    } catch (Exception e) {
                    }
                    if (cash == null) {
                        new_obj.put("cash", old_obj.get("cash"));
                    } else {
                        Double new_val = new_obj.getDouble("cash");
                        Double old_val = old_obj.getDouble("cash");
                        Double final_val = new_val + old_val;
                        new_obj.put("cash", final_val);

                    }
                }
                if (payment_mode.equals("card")) {
                    String card = null;
                    try {
                        card = new_obj.get("card").toString();
                    } catch (Exception e) {
                    }
                    if (card == null) {
                        new_obj.put("card", old_obj.get("card"));

                    } else {
                        Double new_val = new_obj.getDouble("card");
                        Double old_val = old_obj.getDouble("card");
                        Double final_val = new_val + old_val;
                        new_obj.put("card", final_val);

                    }
                }
                if (payment_mode.equals("cheque")) {
                    String cheque = null;
                    try {
                        cheque = new_obj.get("cheque").toString();
                    } catch (Exception e) {
                    }
                    if (cheque == null) {
                        new_obj.put("cheque", old_obj.get("cheque"));
                    } else {
                        Double new_val = new_obj.getDouble("cheque");
                        Double old_val = old_obj.getDouble("cheque");
                        Double final_val = new_val + old_val;
                        new_obj.put("cheque", final_val);

                    }
                }
                if (payment_mode.equals("other")) {
                    String other = null;
                    try {
                        other = new_obj.get("other").toString();
                    } catch (Exception e) {
                    }
                    if (other == null) {
                        new_obj.put("other", old_obj.get("other"));
                    } else {
                        Double new_val = new_obj.getDouble("other");
                        Double old_val = old_obj.getDouble("other");
                        Double final_val = new_val + old_val;
                        new_obj.put("other", final_val);

                    }
                }
                Double finla_total = 0.0;
                String cash = null;
                try {
                    cash = new_obj.get("cash").toString();
                    finla_total = finla_total + new_obj.getDouble("cash");
                } catch (Exception e) {
                }
                try {
                    cash = new_obj.get("card").toString();
                    finla_total = finla_total + new_obj.getDouble("card");
                } catch (Exception e) {
                }
                try {
                    cash = new_obj.get("cheque").toString();
                    finla_total = finla_total + new_obj.getDouble("cheque");
                } catch (Exception e) {
                }
                try {
                    cash = new_obj.get("other").toString();
                    finla_total = finla_total + new_obj.getDouble("other");
                } catch (Exception e) {
                }
                new_obj.put("paid_amount", finla_total);
                resultMap.put(resultArray.getJSONObject(i).get("receipt_no").toString(), new_obj);
            } else {
                resultMap.put(resultArray.getJSONObject(i).get("receipt_no").toString(), resultArray.getJSONObject(i));
            }
        }
        JSONArray finalResutlt = new JSONArray();
        for (Map.Entry<String, JSONObject> entry : resultMap.entrySet()) {
            finalResutlt.put(entry.getValue());
        }
        finalResutlt = calculateFinalSummary(tenantName, fromdate, todate, unitList, userList, finalResutlt, IPDFlag);
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(recieptcollectionsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(recieptcollectionsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        finalResutlt.getJSONObject(0).put("objHeaderData", jsonObject);
        finalResutlt.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (recieptcollectionsearchDTO.getPrint()) {
            String columnName = "receipt_no,count,date,bill_id,patient_id,recipt_type,cash,card,cheque,other,patient_name,cash_counter,unit_name,paid_amount";
            return createReport.generateExcel(columnName, "CollectionReport", finalResutlt);
        } else {
            return createReport.createJasperReportPDFByJson("CollectionReport", finalResutlt.toString());
        }
    }

    // Code for summary calculations Start
    public JSONArray calculateFinalSummary(@RequestHeader("X-tenantId") String tenantName, String fromdate, String todate, String[] unitList, @PathVariable String[] userList, JSONArray changeableArray, int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        JSONArray resultArray = new JSONArray();
        HashSet refund = new HashSet();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = "";
        if (IPDFlag == 0) {
            Query = "SELECT tr.br_reciept_number AS recp_no,tr.created_date,tr.br_payment_amount AS paid_amount,tr.br_bill_id AS bill_id," +
                    "(SELECT CONCAT(mst_user.user_firstname,' ',mst_user.user_lastname) FROM mst_patient mp INNER JOIN mst_user WHERE mp.patient_user_id=mst_user.user_id " +
                    "AND mp.patient_id=tr.br_patient_id) AS patient_id,mp.pm_name AS payment_mode,mu.user_fullname AS patient_name,mc.cc_name AS cash_counter," +
                    "(SELECT mst_unit.unit_name FROM mst_unit where mst_unit.unit_id=tb.tbill_unit_id) as unit_name,tb.refund_amount,tr.br_id FROM tbill_reciept tr " +
                    "INNER JOIN tbill_bill tb INNER JOIN mst_payment_mode mp INNER JOIN mst_staff ms INNER JOIN mst_user mu INNER JOIN mst_cash_counter mc " +
                    "where tr.br_bill_id=tb.bill_id AND mp.pm_id=tr.br_pm_id AND tb.bill_user_id=ms.staff_id AND mu.user_id=ms.staff_user_id AND tb.bill_cash_counter_id=mc.cc_id" +
                    " AND tr.is_active=TRUE AND tr.is_cancelled=0 AND tb.ipd_bill=false AND tb.emrbill=0 and tr.is_deleted=FALSE";
        } else if (IPDFlag == 1) {
            Query = "SELECT tr.br_reciept_number AS recp_no,tr.created_date,tr.br_payment_amount AS paid_amount,tr.br_bill_id AS bill_id," +
                    "(SELECT CONCAT(mst_user.user_firstname,' ',mst_user.user_lastname) FROM mst_patient mp INNER JOIN mst_user WHERE mp.patient_user_id=mst_user.user_id" +
                    " AND mp.patient_id=tr.br_patient_id) AS patient_id,mp.pm_name AS payment_mode,mu.user_fullname AS patient_name,mc.cc_name AS cash_counter," +
                    "(SELECT mst_unit.unit_name FROM mst_unit where mst_unit.unit_id=tb.tbill_unit_id) as unit_name,tb.refund_amount,tr.br_id FROM tbill_reciept tr " +
                    "INNER JOIN tbill_bill tb INNER JOIN mst_payment_mode mp INNER JOIN mst_staff ms INNER JOIN mst_user mu INNER JOIN mst_cash_counter mc " +
                    "where tr.br_bill_id=tb.bill_id AND mp.pm_id=tr.br_pm_id AND tb.bill_user_id=ms.staff_id AND mu.user_id=ms.staff_user_id AND tb.bill_cash_counter_id=mc.cc_id" +
                    " AND tr.is_active=TRUE AND tr.is_cancelled=0 AND tb.ipd_bill=true AND tb.emrbill=0 and tr.is_deleted=FALSE";
        } else {
            Query = "SELECT tr.br_reciept_number AS recp_no,tr.created_date,tr.br_payment_amount AS paid_amount,tr.br_bill_id AS bill_id," +
                    "(SELECT CONCAT(mst_user.user_firstname,' ',mst_user.user_lastname) FROM mst_patient mp INNER JOIN mst_user WHERE mp.patient_user_id=mst_user.user_id " +
                    "AND mp.patient_id=tr.br_patient_id) AS patient_id,mp.pm_name AS payment_mode,mu.user_fullname AS patient_name,mc.cc_name AS cash_counter," +
                    "(SELECT mst_unit.unit_name FROM mst_unit where mst_unit.unit_id=tb.tbill_unit_id) as unit_name,tb.refund_amount,tr.br_id FROM tbill_reciept tr " +
                    "INNER JOIN tbill_bill tb INNER JOIN mst_payment_mode mp INNER JOIN mst_staff ms INNER JOIN mst_user mu INNER JOIN mst_cash_counter mc " +
                    "where tr.br_bill_id=tb.bill_id AND mp.pm_id=tr.br_pm_id AND tb.bill_user_id=ms.staff_id AND mu.user_id=ms.staff_user_id AND tb.bill_cash_counter_id=mc.cc_id" +
                    " AND tr.is_active=TRUE AND tr.is_cancelled=0 AND tb.emrbill=1 and tr.is_deleted=FALSE";
        }
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
            Query += " and tb.tbill_unit_id in (" + values + ") ";

        }
        if (!userList[0].equals("null")) {
            String values = userList[0];
            for (int i = 1; i < userList.length; i++) {
                values += "," + userList[i];
            }
            Query += " and tb.bill_user_id in (" + values + ") ";
        }
        if (fromdate != null && !fromdate.equals("null") && !fromdate.isEmpty()) {
            Query += "  AND tr.created_date > '" + fromdate + " 00:00:00' AND tr.created_date < '" + todate + " 23:59:59'";
        }
        Query += "  ORDER BY tr.created_date desc ";
        String unitName = "";
        List<Object[]> result = entityManager.createNativeQuery(Query).getResultList();
        for (int i = 0; i < result.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("receipt_no", "" + result.get(i)[0]);
            object.put("date", "" + result.get(i)[1]);
            object.put("paid_amount", "" + result.get(i)[2]);
            object.put("bill_id", "" + result.get(i)[3]);
            object.put("patient_id", "" + result.get(i)[4]);
            object.put("recipt_type", "Payment Collection");
            StringBuilder sb = new StringBuilder("" + result.get(i)[5]);
            if (sb.toString().equalsIgnoreCase("cash")) {
                object.put("cash", "" + result.get(i)[2]);
                object.put("payment_mode", "cash");
            } else if (sb.toString().toLowerCase().contains("card")) {
                object.put("card", "" + result.get(i)[2]);
                object.put("payment_mode", "card");
            } else if (sb.toString().equalsIgnoreCase("cheque")) {
                object.put("cheque", "" + result.get(i)[2]);
                object.put("payment_mode", "cheque");
            } else {
                object.put("other", "" + result.get(i)[2]);
                object.put("payment_mode", "other");
            }
            object.put("patient_name", "" + result.get(i)[6]);
            object.put("cash_counter", "" + result.get(i)[7]);
            object.put("unit_name", "" + result.get(i)[8]);
            resultArray.put(object);
            Double refund_amount = Double.parseDouble("" + result.get(i)[9]);
            if (refund_amount > 0) {
                if (!refund.contains("" + result.get(i)[3])) {
                    JSONObject dum = new JSONObject();
                    dum.put("receipt_no", "PAYREF/" + result.get(i)[10]);
                    dum.put("date", object.get("date"));
                    dum.put("bill_id", object.get("bill_id"));
                    dum.put("patient_id", object.get("patient_id"));
                    dum.put("recipt_type", "Payment Refund");
                    if (sb.toString().equalsIgnoreCase("cash")) {
                        dum.put("cash", "" + refund_amount);
                        dum.put("payment_mode", "cash");
                    } else if (sb.toString().toLowerCase().contains("card")) {
                        dum.put("card", "" + refund_amount);
                        dum.put("payment_mode", "card");
                    } else if (sb.toString().equalsIgnoreCase("cheque")) {
                        dum.put("cheque", "" + refund_amount);
                        dum.put("payment_mode", "cheque");
                    } else {
                        dum.put("other", "" + refund_amount);
                        dum.put("payment_mode", "other");
                    }
                    dum.put("patient_name", object.get("patient_name"));
                    dum.put("cash_counter", object.get("cash_counter"));
                    dum.put("unit_name", object.get("unit_name"));
                    unitName = "" + object.get("unit_name");
                    dum.put("paid_amount", refund_amount);
                    resultArray.put(dum);
                }
            }
            refund.add("" + result.get(i)[3]);
        }
        if (IPDFlag == 1) {
            String advanceQuery = "SELECT  (SELECT CONCAT(mu.user_firstname,' ' ,mu.user_lastname)  FROM mst_patient mp INNER JOIN mst_user mu WHERE  mu.user_id=mp.patient_user_id and mp.patient_id=trn_ad.aa_patient_id ) as name,trn_ad.advanced_amount,trn_ad.ipdadvanced_id as rec_no,mp.pm_name,trn_ad.aa_patient_id,trn_ad.created_date,(SELECT mu.user_firstname FROM mst_patient mp INNER JOIN mst_user mu WHERE mu.user_id=mp.patient_user_id and mp.patient_id=trn_ad.aa_patient_id) AS patient_name FROM trn_ipd_advanced trn_ad INNER JOIN mst_payment_mode mp inner JOIN mst_staff ms INNER JOIN mst_user mu WHERE trn_ad.created_date > '" + fromdate + " 00:00:00' AND trn_ad.created_date < '" + todate + " 23:59:59' AND mp.pm_id=trn_ad.aa_pm_id AND ms.staff_user_id=mu.user_id AND ms.staff_id=trn_ad.aa_user_id";
            List<Object[]> advanceResult = entityManager.createNativeQuery(advanceQuery).getResultList();
            for (int j = 0; j < advanceResult.size(); j++) {
                JSONObject dum = new JSONObject();
                dum.put("receipt_no", "ADVCOLL" + advanceResult.get(j)[2]);
                dum.put("date", "" + advanceResult.get(j)[5]);
                dum.put("bill_id", "" + advanceResult.get(j)[2]);
                dum.put("patient_id", "" + advanceResult.get(j)[6]);
                dum.put("recipt_type", "Advance Collection");
                if (("" + advanceResult.get(j)[3]).toLowerCase().contains("cash")) {
                    dum.put("cash", "" + advanceResult.get(j)[1]);
                    dum.put("payment_mode", "cash");
                } else if (("" + advanceResult.get(j)[3]).toLowerCase().contains("card")) {
                    dum.put("card", "" + advanceResult.get(j)[1]);
                    dum.put("payment_mode", "card");
                } else if (("" + advanceResult.get(j)[3]).toLowerCase().contains("cheque")) {
                    dum.put("cheque", "" + advanceResult.get(j)[1]);
                    dum.put("payment_mode", "cheque");
                } else {
                    dum.put("other", "" + advanceResult.get(j)[1]);
                    dum.put("payment_mode", "other");
                }
                dum.put("patient_name", advanceResult.get(j)[2]);
                dum.put("cash_counter", "");
                dum.put("unit_name", unitName);
                dum.put("paid_amount", advanceResult.get(j)[1]);
                resultArray.put(dum);
            }
            String advanceConsumptioQuery = "SELECT  (SELECT CONCAT(mu.user_firstname,' ' ,mu.user_lastname)  FROM mst_patient mp INNER JOIN mst_user mu WHERE  mu.user_id=mp.patient_user_id and mp.patient_id=trn_ad.aa_patient_id ) as name,trn_ad.advanced_amount,ah.id as history_id,mp.pm_name,trn_ad.aa_patient_id,trn_ad.created_date,(SELECT mu.user_firstname FROM mst_patient mp INNER JOIN mst_user mu WHERE mu.user_id=mp.patient_user_id and mp.patient_id=trn_ad.aa_patient_id) AS patient_name,ah.consumed_amount,ah.id FROM advanced_history ah INNER join trn_ipd_advanced trn_ad INNER JOIN mst_payment_mode mp  WHERE  ah.advanced_amount_id=trn_ad.ipdadvanced_id and ah.consumed_date > '" + fromdate + " 00:00:00' AND ah.consumed_date < '" + todate + " 23:59:59' AND mp.pm_id=trn_ad.aa_pm_id";
            List<Object[]> advanceConsumptioResult = entityManager.createNativeQuery(advanceConsumptioQuery).getResultList();
            for (int j = 0; j < advanceConsumptioResult.size(); j++) {
                JSONObject dum = new JSONObject();
                dum.put("receipt_no", "ADVCON" + advanceConsumptioResult.get(j)[8]);
                dum.put("date", "" + advanceConsumptioResult.get(j)[5]);
                dum.put("bill_id", "" + advanceConsumptioResult.get(j)[2]);
                dum.put("patient_id", "" + advanceConsumptioResult.get(j)[6]);
                dum.put("recipt_type", "Advance Consumption");
                if (("" + advanceConsumptioResult.get(j)[3]).toLowerCase().contains("cash")) {
                    dum.put("cash", "" + advanceConsumptioResult.get(j)[7]);
                    dum.put("payment_mode", "cash");
                } else if (("" + advanceConsumptioResult.get(j)[3]).toLowerCase().contains("card")) {
                    dum.put("card", "" + advanceConsumptioResult.get(j)[7]);
                    dum.put("payment_mode", "card");
                } else if (("" + advanceConsumptioResult.get(j)[3]).toLowerCase().contains("cheque")) {
                    dum.put("cheque", "" + advanceConsumptioResult.get(j)[7]);
                    dum.put("payment_mode", "cheque");
                } else {
                    dum.put("other", "" + advanceConsumptioResult.get(j)[7]);
                    dum.put("payment_mode", "other");
                }
                dum.put("patient_name", advanceConsumptioResult.get(j)[2]);
                dum.put("cash_counter", "");
                dum.put("unit_name", unitName);
                dum.put("paid_amount", advanceConsumptioResult.get(j)[7]);
                resultArray.put(dum);
            }
            String advanceRefund = "SELECT  mu.user_name,trn_ad.advanced_amount,ah.id as refund_id,mp.pm_name,trn_ad.aa_patient_id,trn_ad.created_date,(SELECT mu.user_firstname FROM mst_patient mp INNER JOIN mst_user mu WHERE mu.user_id=mp.patient_user_id and mp.patient_id=trn_ad.aa_patient_id) AS patient_name,ah.advance_refund_amount,ah.id FROM advance_refund_history ah INNER join trn_ipd_advanced trn_ad INNER JOIN mst_payment_mode mp inner JOIN mst_staff ms INNER JOIN mst_user mu WHERE  ah.advance_id=trn_ad.ipdadvanced_id and ah.advance_refund_date > '" + fromdate + "' AND ah.advance_refund_date < '" + todate + "' AND mp.pm_id=trn_ad.aa_pm_id AND ms.staff_user_id=mu.user_id AND ms.staff_id=trn_ad.aa_user_id;";
            List<Object[]> advanceRefundList = entityManager.createNativeQuery(advanceRefund).getResultList();
            for (int j = 0; j < advanceRefundList.size(); j++) {
                JSONObject dum = new JSONObject();
                dum.put("receipt_no", "ADVREFUND" + advanceRefundList.get(j)[8]);
                dum.put("date", "" + advanceRefundList.get(j)[5]);
                dum.put("bill_id", "" + advanceRefundList.get(j)[2]);
                dum.put("patient_id", "" + advanceRefundList.get(j)[6]);
                dum.put("recipt_type", "Advance Refund");
                if (("" + advanceRefundList.get(j)[3]).toLowerCase().contains("cash")) {
                    dum.put("cash", "" + advanceRefundList.get(j)[7]);
                    dum.put("payment_mode", "cash");
                } else if (("" + advanceRefundList.get(j)[3]).toLowerCase().contains("card")) {
                    dum.put("card", "" + advanceRefundList.get(j)[7]);
                    dum.put("payment_mode", "card");
                } else if (("" + advanceRefundList.get(j)[3]).toLowerCase().contains("cheque")) {
                    dum.put("cheque", "" + advanceRefundList.get(j)[7]);
                    dum.put("payment_mode", "cheque");
                } else {
                    dum.put("other", "" + advanceRefundList.get(j)[7]);
                    dum.put("payment_mode", "other");
                }
                dum.put("patient_name", advanceRefundList.get(j)[2]);
                dum.put("cash_counter", "");
                dum.put("unit_name", unitName);
                dum.put("paid_amount", advanceRefundList.get(j)[7]);
                resultArray.put(dum);
            }
        }
        LinkedHashMap<String, JSONObject> resultMap = new LinkedHashMap<String, JSONObject>();
        for (int i = 0; i < resultArray.length(); i++) {
            if (resultMap.containsKey(resultArray.getJSONObject(i).get("receipt_no").toString())) {
                JSONObject old_obj = resultArray.getJSONObject(i);
                JSONObject new_obj = resultMap.get(resultArray.getJSONObject(i).get("receipt_no").toString());
                String payment_mode = old_obj.getString("payment_mode");
                if (payment_mode.equals("cash")) {
                    String cash = null;
                    try {
                        cash = new_obj.get("cash").toString();
                    } catch (Exception e) {
                    }
                    if (cash == null) {
                        new_obj.put("cash", old_obj.get("cash"));
                    } else {
                        Double new_val = new_obj.getDouble("cash");
                        Double old_val = old_obj.getDouble("cash");
                        Double final_val = new_val + old_val;
                        new_obj.put("cash", final_val);

                    }
                }
                if (payment_mode.toLowerCase().contains("card")) {
                    String card = null;
                    try {
                        card = new_obj.get("card").toString();
                    } catch (Exception e) {
                    }
                    if (card == null) {
                        new_obj.put("card", old_obj.get("card"));

                    } else {
                        Double new_val = new_obj.getDouble("card");
                        Double old_val = old_obj.getDouble("card");
                        Double final_val = new_val + old_val;
                        new_obj.put("card", final_val);

                    }
                }
                if (payment_mode.equals("cheque")) {
                    String cheque = null;
                    try {
                        cheque = new_obj.get("cheque").toString();
                    } catch (Exception e) {
                    }
                    if (cheque == null) {
                        new_obj.put("cheque", old_obj.get("cheque"));
                    } else {
                        Double new_val = new_obj.getDouble("cheque");
                        Double old_val = old_obj.getDouble("cheque");
                        Double final_val = new_val + old_val;
                        new_obj.put("cheque", final_val);

                    }
                }
                if (payment_mode.equals("other")) {
                    String other = null;
                    try {
                        other = new_obj.get("other").toString();
                    } catch (Exception e) {
                    }
                    if (other == null) {
                        new_obj.put("other", old_obj.get("other"));
                    } else {
                        Double new_val = new_obj.getDouble("other");
                        Double old_val = old_obj.getDouble("other");
                        Double final_val = new_val + old_val;
                        new_obj.put("other", final_val);

                    }
                }
                Double finla_total = 0.0;
                String cash = null;
                try {
                    cash = new_obj.get("cash").toString();
                    finla_total = finla_total + new_obj.getDouble("cash");
                } catch (Exception e) {
                }
                try {
                    cash = new_obj.get("card").toString();
                    finla_total = finla_total + new_obj.getDouble("card");
                } catch (Exception e) {
                }
                try {
                    cash = new_obj.get("cheque").toString();
                    finla_total = finla_total + new_obj.getDouble("cheque");
                } catch (Exception e) {
                }
                try {
                    cash = new_obj.get("other").toString();
                    finla_total = finla_total + new_obj.getDouble("other");
                } catch (Exception e) {
                }
                new_obj.put("paid_amount", finla_total);
                resultMap.put(resultArray.getJSONObject(i).get("receipt_no").toString(), new_obj);
            } else {
                resultMap.put(resultArray.getJSONObject(i).get("receipt_no").toString(), resultArray.getJSONObject(i));
            }
        }
        JSONArray finalResutlt = new JSONArray();
        for (Map.Entry<String, JSONObject> entry : resultMap.entrySet()) {
            finalResutlt.put(entry.getValue());
        }
        Double totalAmount = 0.0;
        Double totalCashAmount = 0.0;
        Double totalCardAmount = 0.0;
        Double totalChequeAmount = 0.0;
        Double totalOtherAmount = 0.0;
        for (int i = 0; i < finalResutlt.length(); i++) {
            if (finalResutlt.getJSONObject(i).getString("recipt_type").equalsIgnoreCase("Payment Collection")) {
                totalAmount = totalAmount + finalResutlt.getJSONObject(i).getDouble("paid_amount");
                try {
                    totalCashAmount = totalCashAmount + finalResutlt.getJSONObject(i).getDouble("cash");
                } catch (Exception e) {
                }
                try {
                    totalCardAmount = totalCardAmount + finalResutlt.getJSONObject(i).getDouble("card");
                } catch (Exception e) {
                }
                try {
                    totalChequeAmount = totalChequeAmount + finalResutlt.getJSONObject(i).getDouble("cheque");
                } catch (Exception e) {
                }
                try {
                    totalOtherAmount = totalOtherAmount + finalResutlt.getJSONObject(i).getDouble("other");
                } catch (Exception e) {
                }

            }
            if (finalResutlt.getJSONObject(i).getString("recipt_type").equalsIgnoreCase("Payment Refund")) {
                totalAmount = totalAmount - finalResutlt.getJSONObject(i).getDouble("paid_amount");
                try {
                    totalCashAmount = totalCashAmount - finalResutlt.getJSONObject(i).getDouble("cash");
                } catch (Exception e) {
                }
                try {
                    totalCardAmount = totalCardAmount - finalResutlt.getJSONObject(i).getDouble("card");
                } catch (Exception e) {
                }
                try {
                    totalChequeAmount = totalChequeAmount - finalResutlt.getJSONObject(i).getDouble("cheque");
                } catch (Exception e) {
                }
                try {
                    totalOtherAmount = totalOtherAmount - finalResutlt.getJSONObject(i).getDouble("other");
                } catch (Exception e) {
                }
            }
            if (finalResutlt.getJSONObject(i).getString("recipt_type").equalsIgnoreCase("Advance Collection")) {
                totalAmount = totalAmount + finalResutlt.getJSONObject(i).getDouble("paid_amount");
                try {
                    totalCashAmount = totalCashAmount + finalResutlt.getJSONObject(i).getDouble("cash");
                } catch (Exception e) {
                }
                try {
                    totalCardAmount = totalCardAmount + finalResutlt.getJSONObject(i).getDouble("card");
                } catch (Exception e) {
                }
                try {
                    totalChequeAmount = totalChequeAmount + finalResutlt.getJSONObject(i).getDouble("cheque");
                } catch (Exception e) {
                }
                try {
                    totalOtherAmount = totalOtherAmount + finalResutlt.getJSONObject(i).getDouble("other");
                } catch (Exception e) {
                }
            }
            if (finalResutlt.getJSONObject(i).getString("recipt_type").equalsIgnoreCase("Advance Refund")) {
                totalAmount = totalAmount - finalResutlt.getJSONObject(i).getDouble("paid_amount");
                try {
                    totalCashAmount = totalCashAmount - finalResutlt.getJSONObject(i).getDouble("cash");
                } catch (Exception e) {
                }
                try {
                    totalCardAmount = totalCardAmount - finalResutlt.getJSONObject(i).getDouble("card");
                } catch (Exception e) {
                }
                try {
                    totalChequeAmount = totalChequeAmount - finalResutlt.getJSONObject(i).getDouble("cheque");
                } catch (Exception e) {
                }
                try {
                    totalOtherAmount = totalOtherAmount - finalResutlt.getJSONObject(i).getDouble("other");
                } catch (Exception e) {
                }
            }
            if (finalResutlt.getJSONObject(i).getString("recipt_type").equalsIgnoreCase("Advance Consumption")) {
                totalAmount = totalAmount - finalResutlt.getJSONObject(i).getDouble("paid_amount");
                try {
                    totalCashAmount = totalCashAmount - finalResutlt.getJSONObject(i).getDouble("cash");
                } catch (Exception e) {
                }
                try {
                    totalCardAmount = totalCardAmount - finalResutlt.getJSONObject(i).getDouble("card");
                } catch (Exception e) {
                }
                try {
                    totalChequeAmount = totalChequeAmount - finalResutlt.getJSONObject(i).getDouble("cheque");
                } catch (Exception e) {
                }
                try {
                    totalOtherAmount = totalOtherAmount - finalResutlt.getJSONObject(i).getDouble("other");
                } catch (Exception e) {
                }
            }
        }
        for (int j = 0; j < changeableArray.length(); j++) {
            changeableArray.getJSONObject(j).put("total_amount", totalAmount);
            changeableArray.getJSONObject(j).put("total_cash", totalCashAmount);
            changeableArray.getJSONObject(j).put("total_card", totalCardAmount);
            changeableArray.getJSONObject(j).put("total_cheque", totalChequeAmount);
            changeableArray.getJSONObject(j).put("total_other", totalOtherAmount);
        }
        return changeableArray;
    }
// Code for summary calculations End

}
