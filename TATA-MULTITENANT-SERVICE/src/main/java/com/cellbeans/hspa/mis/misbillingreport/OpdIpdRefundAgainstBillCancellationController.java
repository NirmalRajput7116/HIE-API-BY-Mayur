package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/opd_ipd_refund_against_bill_cancelled")
public class OpdIpdRefundAgainstBillCancellationController {
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
    @RequestMapping("getOpdIpdRefundAgainstBillCancellationReport/{unitList}/{mstuserlist}/{IPDFlag}")
    public ResponseEntity searchOpdIpdRefundAgainstBillList(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdRefundAgainstBillCancellationSearchDTO opdipdrefundAgainstBillCancellationSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String Query2 = " ";
        String Query1 = " ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query += " SELECT " +
                    " tr.br_id,ifnull(un.unit_name,'')as unit_name, date(tr.created_date)as refDate, " +
                    " ifnull(mp.patient_mr_no,'')as patient_mr_no ,ifnull(tb.bill_number,0)as BillNumber, " +
                    " ifnull(tr.br_reciept_number,'')as RecieptNumber,ifnull(cc.cc_name,'')as CashCounter, " +
                    " CONCAT( ifnull(mu.user_firstname,'') ,' ',ifnull(mu.user_lastname,''))as patientname, " +
                    " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,''))as CollectedBy, " +
                    " ifnull(pm.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount, " +
                    " ifnull(tr.br_payment_amount,'')as RefundedAmount " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill tb on tr.br_bill_id=tb.bill_id " +
                    " left join mst_cash_counter cc on tb.bill_cash_counter_id=cc_id " +
                    " left join mst_payment_mode pm on tr.br_ret_pm_id=pm.pm_id " +
                    " left join mst_unit un on tb.tbill_unit_id=un.unit_id " +
                    " left join mst_visit mv on tb.bill_visit_id=mv.visit_id " +
                    " left join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                    " left join mst_user mu on mp.patient_user_id=mu.user_id " +
                    " left join mst_staff ms on tb.bill_user_id=ms.staff_id " +
                    " left join mst_user su on ms.staff_user_id=su.user_id where tr.is_cancelled= true and tb.ipd_bill= " + IPDFlag;
            Query1 += " SELECT " +
                    " ifnull(un.unit_name,'')as unit_name, " +
                    " count(tb.bill_number)as sumBillNo, " +
                    " count(tr.br_reciept_number)as sumRecieptNumber,ifnull(sum(tr.br_payment_amount),0)as sumRefundedAmount, " +
                    " ifnull(pm.pm_name,'')as PaymentMode, ifnull(sum(tr.br_payment_amount),0)as PaymenModeAmount " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill tb on tr.br_bill_id=tb.bill_id " +
                    " left join mst_cash_counter cc on tb.bill_cash_counter_id=cc_id " +
                    " left join mst_payment_mode pm on tr.br_ret_pm_id=pm.pm_id " +
                    " left join mst_unit un on tb.tbill_unit_id=un.unit_id " +
                    " left join mst_visit mv on tb.bill_visit_id=mv.visit_id " +
                    " left join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                    " left join mst_user mu on mp.patient_user_id=mu.user_id  " +
                    " left join mst_staff ms on tb.bill_user_id=ms.staff_id " +
                    " left join mst_user su on ms.staff_user_id=su.user_id  where tr.is_cancelled= true and tb.ipd_bill=  " + IPDFlag;
            Query2 += " SELECT " +
                    " ifnull(tr.br_ret_pm_id,'')as PaymentMode, sum(tr.br_payment_amount)as PaymenModeAmount " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill tb on tr.br_bill_id=tb.bill_id " +
                    " left join mst_cash_counter cc on tb.bill_cash_counter_id=cc_id " +
                    " left join mst_payment_mode pm on tr.br_ret_pm_id=pm.pm_id " +
                    " left join mst_unit un on tb.tbill_unit_id=un.unit_id " +
                    " left join mst_visit mv on tb.bill_visit_id=mv.visit_id " +
                    " left join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                    " left join mst_user mu on mp.patient_user_id=mu.user_id " +
                    " left join mst_staff ms on tb.bill_user_id=ms.staff_id " +
                    " left join mst_user su on ms.staff_user_id=su.user_id " +
                    " where tr.is_cancelled=1 and tb.ipd_bill= " + IPDFlag;

        } else {
            Query += " SELECT " +
                    " tr.br_id,ifnull(un.unit_name,'')as unit_name, date(tr.created_date)as refDate, " +
                    " ifnull(mp.patient_mr_no,'')as patient_mr_no ,ifnull(tb.bill_number,0)as BillNumber, " +
                    " ifnull(tr.br_reciept_number,'')as RecieptNumber,ifnull(cc.cc_name,'')as CashCounter, " +
                    " CONCAT( ifnull(mu.user_firstname,'') ,' ',ifnull(mu.user_lastname,''))as patientname, " +
                    " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,''))as CollectedBy, " +
                    " ifnull(pm.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount, " +
                    " ifnull(tr.br_payment_amount,'')as RefundedAmount " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill tb on tr.br_bill_id=tb.bill_id " +
                    " left join mst_cash_counter cc on tb.bill_cash_counter_id=cc_id " +
                    " left join mst_payment_mode pm on tr.br_ret_pm_id=pm.pm_id " +
                    " left join mst_unit un on tb.tbill_unit_id=un.unit_id " +
                    " left join trn_admission ad on tb.bill_admission_id=ad.admission_id " +
                    " left join mst_patient mp on ad.admission_patient_id=mp.patient_id " +
                    " left join mst_user mu on mp.patient_user_id=mu.user_id " +
                    " left join mst_staff ms on tb.bill_user_id=ms.staff_id " +
                    " left join mst_user su on ms.staff_user_id=su.user_id where tr.is_cancelled =true  and tb.ipd_bill= " + IPDFlag;
            Query1 += " SELECT " +
                    " ifnull(un.unit_name,'')as unit_name, " +
                    " count(tb.bill_number)as sumBillNo, " +
                    " count(tr.br_reciept_number)as sumRecieptNumber,ifnull(sum(tr.br_payment_amount),0)as sumRefundedAmount, " +
                    " ifnull(pm.pm_name,'')as PaymentMode, ifnull(sum(tr.br_payment_amount),0)as PaymenModeAmount " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill tb on tr.br_bill_id=tb.bill_id " +
                    " left join mst_cash_counter cc on tb.bill_cash_counter_id=cc_id " +
                    " left join mst_payment_mode pm on tr.br_ret_pm_id=pm.pm_id " +
                    " left join mst_unit un on tb.tbill_unit_id=un.unit_id " +
                    " left join trn_admission ad on tb.bill_admission_id=ad.admission_id " +
                    " left join mst_patient mp on ad.admission_patient_id=mp.patient_id " +
                    " left join mst_user mu on mp.patient_user_id=mu.user_id " +
                    " left join mst_staff ms on tb.bill_user_id=ms.staff_id " +
                    " left join mst_user su on ms.staff_user_id=su.user_id where tr.is_cancelled= true and tb.ipd_bill= " + IPDFlag;
            Query2 += " SELECT " +
                    " ifnull(tr.br_ret_pm_id,'')as PaymentMode, sum(tr.br_payment_amount)as PaymenModeAmount " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill tb on tr.br_bill_id=tb.bill_id " +
                    " left join mst_cash_counter cc on tb.bill_cash_counter_id=cc_id " +
                    " left join mst_payment_mode pm on tr.br_ret_pm_id=pm.pm_id " +
                    " left join mst_unit un on tb.tbill_unit_id=un.unit_id " +
                    " left join trn_admission ad on tb.bill_admission_id=ad.admission_id " +
                    " left join mst_patient mp on ad.admission_patient_id=mp.patient_id " +
                    " left join mst_user mu on mp.patient_user_id=mu.user_id " +
                    " left join mst_staff ms on tb.bill_user_id=ms.staff_id " +
                    " left join mst_user su on ms.staff_user_id=su.user_id " +
                    " where tr.is_cancelled = true  and tb.ipd_bill= " + IPDFlag;

        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and tb.tbill_unit_id in (" + values + ") ";
            Query1 += " and tb.tbill_unit_id in (" + values + ") ";
            Query2 += " and tb.tbill_unit_id in (" + values + ") ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query += " and ms.staff_user_id in (" + values + ") ";
            Query1 += " and ms.staff_user_id in (" + values + ") ";
            Query2 += " and ms.staff_user_id in (" + values + ") ";
        }
        if (opdipdrefundAgainstBillCancellationSearchDTO.getFromdate().equals("null") || opdipdrefundAgainstBillCancellationSearchDTO.getFromdate().equals("")) {
            opdipdrefundAgainstBillCancellationSearchDTO.setFromdate("1990-06-07");
        }
        if (opdipdrefundAgainstBillCancellationSearchDTO.getTodate().equals("null") || opdipdrefundAgainstBillCancellationSearchDTO.getTodate().equals("")) {
            opdipdrefundAgainstBillCancellationSearchDTO.setTodate(strDate);
        }

        /*if (!opdipdrefundAgainstBillCancellationSearchDTO.getPatientName().equals("")) {
            Query += " and (mu.user_firstname like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%') ";
            Query1 += " and (mu.user_firstname like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%') ";
            Query2 += " and (mu.user_firstname like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%') ";

        }*/
        if (!opdipdrefundAgainstBillCancellationSearchDTO.getPatientName().equals("")) {
            Query += " and (concat(mu.user_firstname,' ',mu.user_lastname) like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%' or concat(mu.user_lastname,' ',mu.user_firstname) like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%' ) ";
            Query1 += " and (concat(mu.user_firstname,' ',mu.user_lastname) like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%' or concat(mu.user_lastname,' ',mu.user_firstname) like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%' ) ";
            Query2 += " and (concat(mu.user_firstname,' ',mu.user_lastname) like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%' or concat(mu.user_lastname,' ',mu.user_firstname) like '%" + opdipdrefundAgainstBillCancellationSearchDTO.getPatientName() + "%' ) ";
        }
        if (opdipdrefundAgainstBillCancellationSearchDTO.getFromdate().equals("")) {
            opdipdrefundAgainstBillCancellationSearchDTO.setFromdate("1990-06-07");
        }
        if (opdipdrefundAgainstBillCancellationSearchDTO.getTodate().equals("")) {
            opdipdrefundAgainstBillCancellationSearchDTO.setTodate(strDate);
        }
        if (opdipdrefundAgainstBillCancellationSearchDTO.getTodaydate()) {
            Query += "  and date(tr.created_date)=curdate() ";
            Query1 += " and date(tr.created_date)=curdate() ";
            Query2 += " and date(tr.created_date)=curdate() ";
        } else {
            Query += " and  (date(tr.created_date) between '" + opdipdrefundAgainstBillCancellationSearchDTO.getFromdate() + "' and '" + opdipdrefundAgainstBillCancellationSearchDTO.getTodate() + "') ";
            Query1 += " and  (date(tr.created_date) between '" + opdipdrefundAgainstBillCancellationSearchDTO.getFromdate() + "' and '" + opdipdrefundAgainstBillCancellationSearchDTO.getTodate() + "') ";
            Query2 += " and  (date(tr.created_date) between '" + opdipdrefundAgainstBillCancellationSearchDTO.getFromdate() + "' and '" + opdipdrefundAgainstBillCancellationSearchDTO.getTodate() + "') ";

        }
        if (!String.valueOf(opdipdrefundAgainstBillCancellationSearchDTO.getCcId()).equals("0")) {
            Query += "  and tb.bill_cash_counter_id=" + opdipdrefundAgainstBillCancellationSearchDTO.getCcId() + " ";
            Query1 += "  and tb.bill_cash_counter_id=" + opdipdrefundAgainstBillCancellationSearchDTO.getCcId() + " ";
            Query2 += "  and tb.bill_cash_counter_id=" + opdipdrefundAgainstBillCancellationSearchDTO.getCcId() + " ";
        }
        if (!String.valueOf(opdipdrefundAgainstBillCancellationSearchDTO.getPmId()).equals("0")) {
            Query += "  and tr.br_ret_pm_id=" + opdipdrefundAgainstBillCancellationSearchDTO.getPmId() + " ";
            Query1 += "  and tr.br_ret_pm_id=" + opdipdrefundAgainstBillCancellationSearchDTO.getPmId() + " ";
            Query2 += "  and tr.br_ret_pm_id=" + opdipdrefundAgainstBillCancellationSearchDTO.getPmId() + " ";
        }
        Query2 += " group by tr.br_ret_pm_id  ";
        System.out.println("OPD-IPD (Refund-Against-Bill-Cancellation) Report"+Query);
        CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        columnName = "br_id,unit_name,refDate,patient_mr_no,BillNumber,RecieptNumber,CashCounter,patientname,CollectedBy,PaymentMode,PaymenModeAmount,RefundedAmount";
        Query += " limit " + ((opdipdrefundAgainstBillCancellationSearchDTO.getOffset() - 1) * opdipdrefundAgainstBillCancellationSearchDTO.getLimit()) + "," + opdipdrefundAgainstBillCancellationSearchDTO.getLimit();
        System.out.println("Query::" + Query);
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        for (int j = 0; j < jsonArray.length(); j++) {
            String Query5 = " select r.br_ret_pm_id,sum(r.br_payment_amount) from tbill_reciept r where r.br_id =" + jsonArray.getJSONObject(j).get("br_id") + " group by r.br_ret_pm_id ";
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
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query1).getResultList();
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("sumBillNo", ob[1].toString());
                jsonArray.getJSONObject(0).put("sumRecieptNumber", ob[2].toString());
                jsonArray.getJSONObject(0).put("sumRefundedAmount", ob[3].toString());
            }
        } catch (Exception e) {
        }
        List<Object[]> list1 = (List<Object[]>) entityManager.createNativeQuery(Query2).getResultList();
        for (Object[] obj : list1) {
            if (obj[0].toString().equals("1")) {
                jsonArray.getJSONObject(0).put("SumCash", obj[1].toString());
            } else if (obj[0].toString().equals("2")) {
                jsonArray.getJSONObject(0).put("SumCheque", obj[1].toString());
            } else if (obj[0].toString().equals("3")) {
                jsonArray.getJSONObject(0).put("SumCard", obj[1].toString());
            } else if (obj[0].toString().equals("4")) {
                jsonArray.getJSONObject(0).put("SumTransfer", obj[1].toString());
            } else if (obj[0].toString().equals("5")) {
                jsonArray.getJSONObject(0).put("SumDebitCard", obj[1].toString());
            } else if (obj[0].toString().equals("6")) {
                jsonArray.getJSONObject(0).put("SumCredit", obj[1].toString());
            }
        }
        return ResponseEntity.ok(jsonArray.toString());
    }

    //// @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getOpdIpdRefundAgainstServiceCancellationReport/{unitList}/{mstuserlist}/{IPDFlag}")
    public ResponseEntity searchOpdIpdRefundAgainstServiceList(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdRefundAgainstServiceCancellationSearchDTO opdipdrefundagainstserviceCancellationSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String Query2 = " ";
        String Query1 = " ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query += " SELECT " +
                    " tr.br_id,ifnull(un.unit_name,'')as unit_name, date(tr.created_date)as refDate, " +
                    " ifnull(mp.patient_mr_no,'')as patient_mr_no ,ifnull(tb.bill_number,0)as BillNumber, " +
                    " ifnull(tr.br_reciept_number,'')as RecieptNumber,ifnull(cc.cc_name,'')as CashCounter, " +
                    " CONCAT( ifnull(mu.user_firstname,'') ,' ',ifnull(mu.user_lastname,''))as patientname, " +
                    " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,''))as CollectedBy, " +
                    " ifnull(pm.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount, " +
                    " ifnull(tr.br_payment_amount,'')as RefundedAmount " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill tb on tr.br_bill_id=tb.bill_id " +
                    " left join mst_cash_counter cc on tb.bill_cash_counter_id=cc_id " +
                    " left join mst_payment_mode pm on tr.br_ret_pm_id=pm.pm_id " +
                    " left join mst_unit un on tb.tbill_unit_id=un.unit_id " +
                    " left join mst_visit mv on tb.bill_visit_id=mv.visit_id " +
                    " left join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                    " left join mst_user mu on mp.patient_user_id=mu.user_id " +
                    " left join mst_staff ms on tb.bill_user_id=ms.staff_id " +
                    " left join mst_user su on ms.staff_user_id=su.user_id where tr.is_cancelled= true and tb.ipd_bill= " + IPDFlag;
            Query1 += " SELECT " +
                    " ifnull(un.unit_name,'')as unit_name, " +
                    " count(tb.bill_number)as sumBillNo, " +
                    " count(tr.br_reciept_number)as sumRecieptNumber,ifnull(sum(tr.br_payment_amount),0)as sumRefundedAmount, " +
                    " ifnull(pm.pm_name,'')as PaymentMode, ifnull(sum(tr.br_payment_amount),0)as PaymenModeAmount " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill tb on tr.br_bill_id=tb.bill_id " +
                    " left join mst_cash_counter cc on tb.bill_cash_counter_id=cc_id " +
                    " left join mst_payment_mode pm on tr.br_ret_pm_id=pm.pm_id " +
                    " left join mst_unit un on tb.tbill_unit_id=un.unit_id " +
                    " left join mst_visit mv on tb.bill_visit_id=mv.visit_id " +
                    " left join mst_patient mp on mv.visit_patient_id=mp.patient_id " +
                    " left join mst_user mu on mp.patient_user_id=mu.user_id  " +
                    " left join mst_staff ms on tb.bill_user_id=ms.staff_id " +
                    " left join mst_user su on ms.staff_user_id=su.user_id  where tr.is_cancelled= true and tb.ipd_bill=  " + IPDFlag;

        } else {
            Query += " SELECT " +
                    " tr.br_id,ifnull(un.unit_name,'')as unit_name, date(tr.created_date)as refDate, " +
                    " ifnull(mp.patient_mr_no,'')as patient_mr_no ,ifnull(tb.bill_number,0)as BillNumber, " +
                    " ifnull(tr.br_reciept_number,'')as RecieptNumber,ifnull(cc.cc_name,'')as CashCounter, " +
                    " CONCAT( ifnull(mu.user_firstname,'') ,' ',ifnull(mu.user_lastname,''))as patientname, " +
                    " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,''))as CollectedBy, " +
                    " ifnull(pm.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount, " +
                    " ifnull(tr.br_payment_amount,'')as RefundedAmount " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill tb on tr.br_bill_id=tb.bill_id " +
                    " left join mst_cash_counter cc on tb.bill_cash_counter_id=cc_id " +
                    " left join mst_payment_mode pm on tr.br_ret_pm_id=pm.pm_id " +
                    " left join mst_unit un on tb.tbill_unit_id=un.unit_id " +
                    " left join trn_admission ad on tb.bill_admission_id=ad.admission_id " +
                    " left join mst_patient mp on ad.admission_patient_id=mp.patient_id " +
                    " left join mst_user mu on mp.patient_user_id=mu.user_id " +
                    " left join mst_staff ms on tb.bill_user_id=ms.staff_id " +
                    " left join mst_user su on ms.staff_user_id=su.user_id where tr.is_cancelled =true  and tb.ipd_bill= " + IPDFlag;
            Query1 += " SELECT " +
                    " ifnull(un.unit_name,'')as unit_name, " +
                    " count(tb.bill_number)as sumBillNo, " +
                    " count(tr.br_reciept_number)as sumRecieptNumber,ifnull(sum(tr.br_payment_amount),0)as sumRefundedAmount, " +
                    " ifnull(pm.pm_name,'')as PaymentMode, ifnull(sum(tr.br_payment_amount),0)as PaymenModeAmount " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill tb on tr.br_bill_id=tb.bill_id " +
                    " left join mst_cash_counter cc on tb.bill_cash_counter_id=cc_id " +
                    " left join mst_payment_mode pm on tr.br_ret_pm_id=pm.pm_id " +
                    " left join mst_unit un on tb.tbill_unit_id=un.unit_id " +
                    " left join trn_admission ad on tb.bill_admission_id=ad.admission_id " +
                    " left join mst_patient mp on ad.admission_patient_id=mp.patient_id " +
                    " left join mst_user mu on mp.patient_user_id=mu.user_id " +
                    " left join mst_staff ms on tb.bill_user_id=ms.staff_id " +
                    " left join mst_user su on ms.staff_user_id=su.user_id where tr.is_cancelled= true and tb.ipd_bill= " + IPDFlag;

        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and tb.tbill_unit_id in (" + values + ") ";
            Query1 += " and tb.tbill_unit_id in (" + values + ") ";

        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query += " and ms.staff_user_id in (" + values + ") ";
            Query1 += " and ms.staff_user_id in (" + values + ") ";

        }
        if (opdipdrefundagainstserviceCancellationSearchDTO.getFromdate().equals("null") || opdipdrefundagainstserviceCancellationSearchDTO.getFromdate().equals("")) {
            opdipdrefundagainstserviceCancellationSearchDTO.setFromdate("1990-06-07");
        }
        if (opdipdrefundagainstserviceCancellationSearchDTO.getTodate().equals("null") || opdipdrefundagainstserviceCancellationSearchDTO.getTodate().equals("")) {
            opdipdrefundagainstserviceCancellationSearchDTO.setTodate(strDate);
        }
//        if (!opdipdrefundagainstserviceCancellationSearchDTO.getPatientName().equals("")) {
//            Query += " and (mu.user_firstname like '%" + opdipdrefundagainstserviceCancellationSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + opdipdrefundagainstserviceCancellationSearchDTO.getPatientName() + "%') ";
//            Query1 += " and (mu.user_firstname like '%" + opdipdrefundagainstserviceCancellationSearchDTO.getPatientName() + "%' or mu.user_lastname like '%" + opdipdrefundagainstserviceCancellationSearchDTO.getPatientName() + "%') ";
//        }
        if (!opdipdrefundagainstserviceCancellationSearchDTO.getPatientName().equals("")) {
            Query += " and (concat(mu.user_firstname,' ',mu.user_lastname) like '%" + opdipdrefundagainstserviceCancellationSearchDTO.getPatientName() + "%' or concat(mu.user_lastname,' ',mu.user_firstname) like '%" + opdipdrefundagainstserviceCancellationSearchDTO.getPatientName() + "%' ) ";
            Query1 += " and (concat(mu.user_firstname,' ',mu.user_lastname) like '%" + opdipdrefundagainstserviceCancellationSearchDTO.getPatientName() + "%' or concat(mu.user_lastname,' ',mu.user_firstname) like '%" + opdipdrefundagainstserviceCancellationSearchDTO.getPatientName() + "%' ) ";
        }
        if (opdipdrefundagainstserviceCancellationSearchDTO.getFromdate().equals("")) {
            opdipdrefundagainstserviceCancellationSearchDTO.setFromdate("1990-06-07");
        }
        if (opdipdrefundagainstserviceCancellationSearchDTO.getTodate().equals("")) {
            opdipdrefundagainstserviceCancellationSearchDTO.setTodate(strDate);
        }
        if (opdipdrefundagainstserviceCancellationSearchDTO.getTodaydate()) {
            Query += "  and date(tr.created_date)=curdate() ";
            Query1 += " and date(tr.created_date)=curdate() ";
        } else {
            Query += " and  (date(tr.created_date) between '" + opdipdrefundagainstserviceCancellationSearchDTO.getFromdate() + "' and '" + opdipdrefundagainstserviceCancellationSearchDTO.getTodate() + "') ";
            Query1 += " and  (date(tr.created_date) between '" + opdipdrefundagainstserviceCancellationSearchDTO.getFromdate() + "' and '" + opdipdrefundagainstserviceCancellationSearchDTO.getTodate() + "') ";

        }
        if (!opdipdrefundagainstserviceCancellationSearchDTO.getMrNo().equals("")) {
            Query += " and  mp.patient_mr_no like  '%" + opdipdrefundagainstserviceCancellationSearchDTO.getMrNo() + "%' ";
            Query1 += " and mp.patient_mr_no like  '%" + opdipdrefundagainstserviceCancellationSearchDTO.getMrNo() + "%' ";
        }
        if (!String.valueOf(opdipdrefundagainstserviceCancellationSearchDTO.getCcId()).equals("0")) {
            Query += "  and tb.bill_cash_counter_id=" + opdipdrefundagainstserviceCancellationSearchDTO.getCcId() + " ";
            Query1 += "  and tb.bill_cash_counter_id=" + opdipdrefundagainstserviceCancellationSearchDTO.getCcId() + " ";
        }
        if (!String.valueOf(opdipdrefundagainstserviceCancellationSearchDTO.getPmId()).equals("0")) {
            Query += "  and tr.br_ret_pm_id=" + opdipdrefundagainstserviceCancellationSearchDTO.getPmId() + " ";
            Query1 += "  and tr.br_ret_pm_id=" + opdipdrefundagainstserviceCancellationSearchDTO.getPmId() + " ";
        }
        // Query2 += " group by tr.br_ret_pm_id  ";
        System.out.println("OPD-IPD (Refund-Against-Service-Cancellation) Report"+Query1);
        CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        columnName = "br_id,unit_name,refDate,patient_mr_no,BillNumber,RecieptNumber,CashCounter,patientname,CollectedBy,PaymentMode,PaymenModeAmount,RefundedAmount";
        Query += " limit " + ((opdipdrefundagainstserviceCancellationSearchDTO.getOffset() - 1) * opdipdrefundagainstserviceCancellationSearchDTO.getLimit()) + "," + opdipdrefundagainstserviceCancellationSearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query1).getResultList();
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("sumBillNo", ob[1].toString());
                jsonArray.getJSONObject(0).put("sumRecieptNumber", ob[2].toString());
                jsonArray.getJSONObject(0).put("sumRefundedAmount", ob[3].toString());
            }
        } catch (Exception e) {
        }
        return ResponseEntity.ok(jsonArray.toString());
    }

}
