package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.cellbeans.hspa.tally.TallyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/mis_opd_ipd_collection_details")
public class OpdIpdCollectionDetailController {

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

    @Value("${tallyUrl}")
    private String tallyUrl;

    @Value("${tallyCompanyName}")
    private String tallyCompanyName;

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getopdcollectionDetailsReport/{unitList}/{mstuserlist}/{ccId}/{clclId}/{bbsId}/{fromdate}/{todate}/{IPDFlag}")
    public ResponseEntity searchOpdCollectionDetail(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdCollectionDetailSearchDTO opdipdcollectionDetailSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String ccId, @PathVariable String clclId, @PathVariable String bbsId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) {
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
        String MixQuery = "";
        boolean all = false;
        if (IPDFlag == 0) {
            Query += " SELECT b.bill_id,date(tr.created_date)as rDate,b.bill_number as InvoiceNO,tr.br_reciept_number as recieptNo, " +
                    " ifnull(p.patient_mr_no,'')as patient_mr_no ,ifnull(b.bill_opd_number,'')as OpdNo, " +
                    " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname, " +
                    " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable, " +
                    " ifnull(m.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount, " +
                    " (SELECT SUM(trr.br_payment_amount) FROM tbill_reciept trr WHERE trr.br_bill_id=tr.br_bill_id) as PaidAmount,ifnull(b.bill_outstanding,'')as OSA, " +
                    " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,''))as CollectedBY, " +
                    " ifnull(cu.cc_name,'') as cc_name,ifnull(un.unit_name,'')as unit_name , " +
                    " (SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    " where bs_bill_id=tr.br_bill_id) as discountonservice " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    " left join mst_visit v on b.bill_visit_id=v.visit_id " +
                    " left join mst_patient p on v.visit_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id " +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
            Query1 += " SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    "  where bs_bill_id=tr.br_bill_id) as discountonservice FROM tbill_reciept tr " +
                    "  left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    "  left join mst_visit v on b.bill_visit_id=v.visit_id " +
                    "  left join mst_patient p on v.visit_patient_id=p.patient_id " +
                    "  left join mst_user u on p.patient_user_id=u.user_id " +
                    "  left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    "  left join mst_staff s on b.bill_user_id=s.staff_id " +
                    "  left join mst_user mu on s.staff_user_id=mu.user_id " +
                    "  left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    "  left join mst_unit un on b.tbill_unit_id=un.unit_id  where b.ipd_bill=" + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += "  SELECT b.bill_id,date(tr.created_date)as rDate,b.bill_number as InvoiceNO,tr.br_reciept_number as recieptNo, " +
                    " ifnull(p.patient_mr_no,'')as patient_mr_no ,ifnull(b.bill_ipd_number,'')as OpdNo, " +
                    " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname, " +
                    " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable, " +
                    " ifnull(m.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount, " +
                    " (SELECT SUM(trr.br_payment_amount) FROM tbill_reciept trr WHERE trr.br_bill_id=tr.br_bill_id) as PaidAmount,ifnull(b.bill_outstanding,'')as OSA, " +
                    " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,''))as CollectedBY, " +
                    " ifnull(cu.cc_name,'') as cc_name,ifnull(un.unit_name,'')as unit_name , " +
                    " (SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    " where bs_bill_id=tr.br_bill_id) as discountonservice " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    " left join trn_admission a on b.bill_admission_id=a.admission_id " +
                    " left join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id " +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
            Query1 += "  SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    " where bs_bill_id=tr.br_bill_id) as discountonservice FROM tbill_reciept tr " +
                    " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    " left join trn_admission a on b.bill_admission_id=a.admission_id " +
                    " left join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id " +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
        } else {
            all = true;
            Query += " SELECT b.bill_id,date(tr.created_date)as rDate,b.bill_number as InvoiceNO,tr.br_reciept_number as recieptNo,  " +
                    " ifnull(p.patient_mr_no,'')as patient_mr_no ,ifnull(b.bill_opd_number,'')as OpdNo,  CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname,  " +
                    " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable,  ifnull(m.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount,  " +
                    " (SELECT SUM(trr.br_payment_amount) " +
                    " FROM tbill_reciept trr " +
                    " WHERE trr.br_bill_id=tr.br_bill_id) as PaidAmount,ifnull(b.bill_outstanding,'')as OSA,  " +
                    " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,''))as CollectedBY,  ifnull(cu.cc_name,'') as cc_name,ifnull(un.unit_name,'')as unit_name ,  " +
                    " (SELECT sum(bs_discount_amount) " +
                    " FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id) as discountonservice  " +
                    " FROM tbill_reciept tr  left join tbill_bill b on tr.br_bill_id=b.bill_id  " +
                    " left join mst_visit v on b.bill_visit_id=v.visit_id  " +
                    " left join mst_patient p on v.visit_patient_id=p.patient_id  " +
                    " left join mst_user u on p.patient_user_id=u.user_id  " +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id  " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id  " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id  " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id  " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill = 0";
            MixQuery += " SELECT b.bill_id,date(tr.created_date)as rDate,b.bill_number as InvoiceNO,tr.br_reciept_number as recieptNo,  " +
                    " ifnull(p.patient_mr_no,'')as patient_mr_no ,ifnull(b.bill_ipd_number,'')as OpdNo,  CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname,  " +
                    " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable,  ifnull(m.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount,  " +
                    " (SELECT SUM(trr.br_payment_amount) " +
                    " FROM tbill_reciept trr " +
                    " WHERE trr.br_bill_id=tr.br_bill_id) as PaidAmount,ifnull(b.bill_outstanding,'')as OSA,  " +
                    " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,''))as CollectedBY,  ifnull(cu.cc_name,'') as cc_name,ifnull(un.unit_name,'')as unit_name ,  " +
                    " (SELECT sum(bs_discount_amount) " +
                    " FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id) as discountonservice  " +
                    " FROM tbill_reciept tr  left join tbill_bill b on tr.br_bill_id=b.bill_id  " +
                    " left join trn_admission a on b.bill_admission_id=a.admission_id " +
                    " left join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id " +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id  " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id  " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id  " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id  " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill = 1 ";
            Query1 += "   SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    " where bs_bill_id=tr.br_bill_id) as discountonservice FROM tbill_reciept tr " +
                    " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    " left join trn_admission a on b.bill_admission_id=a.admission_id" +
                    " left join mst_patient p on a.admission_patient_id=p.patient_id" +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where 1 ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (opdipdcollectionDetailSearchDTO.getTodaydate()) {
            Query += " and (date(tr.created_date)=curdate()) ";
            Query1 += " and (date(tr.created_date)=curdate()) ";
            MixQuery += "and (date(tr.created_date)=curdate()) ";

        } else {
            Query += " and (date(tr.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query1 += " and (date(tr.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            MixQuery += " and (date(tr.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and b.tbill_unit_id in (" + values + ") ";
            Query1 += " and b.tbill_unit_id in (" + values + ") ";
            MixQuery += " and b.tbill_unit_id in (" + values + ") ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query += " and b.bill_user_id in (" + values + ") ";
            Query1 += " and b.bill_user_id in (" + values + ") ";
            MixQuery += " and b.bill_user_id in (" + values + ") ";
        }
        // CashCounter Id
        if (!ccId.equals("null") && !ccId.equals("0")) {
            Query += " and b.bill_cash_counter_id =  " + ccId + " ";
            Query1 += " and b.bill_cash_counter_id =  " + ccId + " ";
            MixQuery += " and b.bill_cash_counter_id =  " + ccId + " ";
        }
        // Sponsor ID
//        if (!bbsId.equals("null") && !bbsId.equals("0")) {
//            Query += " and b.bill_cash_counter_id =  " + bbsId + " ";
//        }
        // Collection Mode
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query += " and tr.br_payment_amount!=0 and b.bill_outstanding=0 ";
                Query1 += " and tr.br_payment_amount!=0 and b.bill_outstanding=0 ";
                MixQuery += " and tr.br_payment_amount!=0 and b.bill_outstanding=0 ";
            } else if (clclId.equals("2")) {
                Query += " and tr.br_payment_amount!=0 and b.bill_outstanding!=0 ";
                Query1 += " and tr.br_payment_amount!=0 and b.bill_outstanding!=0 ";
                MixQuery += " and tr.br_payment_amount!=0 and b.bill_outstanding!=0 ";
            } else {
                Query += " and tr.br_payment_amount=0 and b.bill_outstanding!=0 ";
                Query1 += " and tr.br_payment_amount=0 and b.bill_outstanding!=0 ";
                MixQuery += " and tr.br_payment_amount=0 and b.bill_outstanding!=0 ";
            }
        }
        String MainQuery1 = " select " +
                " sum(test123.ConcOnBill+test123.bill_net_payable)as GrandGrossTotalAmount, " +
                " sum(test123.ConcOnBill)as GrandConcessionAmount, " +
                " sum(test123.bill_net_payable)as GrandBillNetPaybleAmount, " +
                " sum(test123.PaidAmount)as GrandPaidAmount, " +
                " sum(test123.OSA)as GrandOSA, " +
                " sum(test123.discountonservice) as GrandDiscountonService , " +
                " ifnull(sum(test123.BillRefund),0) as GrandBillRefund, " +
                " ( sum(test123.discountonservice)+sum(test123.ConcOnBill)+ sum(test123.bill_net_payable)) as GrandTotalAmount, " +
                " ifnull( (SELECT sum(tr.br_payment_amount) FROM tbill_reciept tr where tr.br_pm_id=6) ,0)as GrandCredit " +
                " " +
                " from    (select * from ( SELECT b.bill_id as bbId, " +
                " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable, " +
                " ifnull(tr.br_payment_amount,'')as PaidAmount,ifnull(b.bill_outstanding,'')as OSA, " +
                " ( " + Query1 + " ) as test " +
                " left join  (select tb.bill_id,sum(bs.bs_gross_rate)as BillRefund " +
                " from tbill_bill tb,tbill_bill_service bs where tb.bill_id=bs.bs_bill_id and bs.bs_cancel=1  " +
                " group by tb.bill_id) as billre   on test.bbId=billre.bill_id group by test.bbId ) as test123 ";
        String MainQuery = " select ifnull(test.bill_id,'')as bill_id,ifnull(test.rDate,'')as rDate," +
                "  ifnull(test.InvoiceNO,'')as InvoiceNO,ifnull(test.recieptNo,0)as recieptNo,ifnull(test.patient_mr_no,'')as patient_mr_no,ifnull(test.OpdNo,'')as OpdNo," +
                "   ifnull( test.patientname,'')as patientname,ifnull(test.ConcOnBill,0)as ConcOnBill,ifnull(test.bill_net_payable,0)as bill_net_payable," +
                " ifnull(test.PaymentMode,'')as PaymentMode, " +
                "  ifnull(test.PaymenModeAmount,0)as PaymenModeAmount,ifnull(test.PaidAmount,0)as PaidAmount,ifnull(test.OSA,0)as OSA," +
                "  ifnull(test.CollectedBY,'')as CollectedBY,  ifnull(test.cc_name,'')as cc_name," +
                "  ifnull(test.unit_name,'')as unit_name,ifnull(test.discountonservice,0)as discountonservice,ifnull(billre.BillRefund,0)as BillRefund " +
                "  " +
                " from " +
                " ( " + Query + "  ) as test " +
                " left join " +
                " (select tb.bill_id,sum(bs.bs_gross_rate)as BillRefund from tbill_bill tb,tbill_bill_service bs where tb.bill_id=bs.bs_bill_id and bs.bs_cancel=1 group by tb.bill_id) as billre " +
                " on test.bill_id=billre.bill_id group by test.bill_id  ";
        String MainMixQuery = " select ifnull(test.bill_id,'')as bill_id,ifnull(test.rDate,'')as rDate," +
                "  ifnull(test.InvoiceNO,'')as InvoiceNO,ifnull(test.recieptNo,0)as recieptNo,ifnull(test.patient_mr_no,'')as patient_mr_no,ifnull(test.OpdNo,'')as OpdNo," +
                "   ifnull( test.patientname,'')as patientname,ifnull(test.ConcOnBill,0)as ConcOnBill,ifnull(test.bill_net_payable,0)as bill_net_payable," +
                " ifnull(test.PaymentMode,'')as PaymentMode, " +
                "  ifnull(test.PaymenModeAmount,0)as PaymenModeAmount,ifnull(test.PaidAmount,0)as PaidAmount,ifnull(test.OSA,0)as OSA," +
                "  ifnull(test.CollectedBY,'')as CollectedBY,  ifnull(test.cc_name,'')as cc_name," +
                "  ifnull(test.unit_name,'')as unit_name,ifnull(test.discountonservice,0)as discountonservice,ifnull(billre.BillRefund,0)as BillRefund " +
                "  " +
                " from " +
                " ( " + MixQuery + "  ) as test " +
                " left join " +
                " (select tb.bill_id,sum(bs.bs_gross_rate)as BillRefund from tbill_bill tb,tbill_bill_service bs where tb.bill_id=bs.bs_bill_id and bs.bs_cancel=1 group by tb.bill_id) as billre " +
                " on test.bill_id=billre.bill_id group by test.bill_id  ";
        if (all) {
            MainQuery = MainQuery + " union All " + MainMixQuery;
        }

        System.out.println("OPD-IPD Collection Details Reports"+MainQuery);
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "bill_id,rDate,InvoiceNO,recieptNo,patient_mr_no,OpdNo,patientname,ConcOnBill,bill_net_payable,PaymentMode,PaymenModeAmount,PaidAmount,OSA,CollectedBY,cc_name,unit_name,discountonservice,BillRefund";
        MainQuery += "  limit " + ((opdipdcollectionDetailSearchDTO.getOffset() - 1) * opdipdcollectionDetailSearchDTO.getLimit()) + "," + opdipdcollectionDetailSearchDTO.getLimit();
        System.out.println("OPD-IPD Collection Details Reports : " + MainQuery);
        System.out.println("CountQuery : " + CountQuery);
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        for (int j = 0; j < jsonArray.length(); j++) {
            if (!jsonArray.getJSONObject(j).get("PaymentMode").toString().equals("")) {
                String Query5 = " SELECT ifnull(tr.br_pm_id,'') ,sum(tr.br_payment_amount) " +
                        " FROM tbill_reciept tr where br_bill_id =" + jsonArray.getJSONObject(j).get("bill_id") + " group by tr.br_pm_id ";
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
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery1).getResultList();
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("GrandGrossTotalAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("GrandConcessionAmount", ob[1].toString());
                jsonArray.getJSONObject(0).put("GrandBillNetPaybleAmount", ob[2].toString());
                jsonArray.getJSONObject(0).put("GrandPaidAmount", ob[3].toString());
                jsonArray.getJSONObject(0).put("GrandOSA", ob[4].toString());
                jsonArray.getJSONObject(0).put("GrandDiscountonService", ob[5].toString());
                jsonArray.getJSONObject(0).put("GrandBillRefund", ob[6].toString());
                jsonArray.getJSONObject(0).put("GrandTotalAmount", ob[7].toString());
                jsonArray.getJSONObject(0).put("GrandCredit", ob[8].toString());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        return ResponseEntity.ok(jsonArray.toString());

    }
    // Method For Collection Details Report Report End

    // Method For Collection Details Report Jasper Print Start
    @RequestMapping("getopdcollectionDetailsReportPrint/{unitList}/{mstuserlist}/{ccId}/{clclId}/{bbsId}/{fromdate}/{todate}/{IPDFlag}")
    public String searchOpdCollectionDetailPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdCollectionDetailSearchDTO opdipdcollectionDetailSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String ccId, @PathVariable String clclId, @PathVariable String bbsId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) throws JsonProcessingException {
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
        String MixQuery = "";
        boolean all = false;
        if (IPDFlag == 0) {
            Query += " SELECT b.bill_id,date(tr.created_date)as rDate,b.bill_number as InvoiceNO,tr.br_reciept_number as recieptNo, " +
                    " ifnull(p.patient_mr_no,'')as patient_mr_no ,ifnull(b.bill_opd_number,'')as OpdNo, " +
                    " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname, " +
                    " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable, " +
                    " ifnull(m.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount, " +
                    " (SELECT SUM(trr.br_payment_amount) FROM tbill_reciept trr WHERE trr.br_bill_id=tr.br_bill_id) as PaidAmount,ifnull(b.bill_outstanding,'')as OSA, " +
                    " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,''))as CollectedBY, " +
                    " ifnull(cu.cc_name,'') as cc_name,ifnull(un.unit_name,'')as unit_name , " +
                    " (SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    " where bs_bill_id=tr.br_bill_id) as discountonservice " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    " left join mst_visit v on b.bill_visit_id=v.visit_id " +
                    " left join mst_patient p on v.visit_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id " +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
            Query1 += " SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    "  where bs_bill_id=tr.br_bill_id) as discountonservice FROM tbill_reciept tr " +
                    "  left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    "  left join mst_visit v on b.bill_visit_id=v.visit_id " +
                    "  left join mst_patient p on v.visit_patient_id=p.patient_id " +
                    "  left join mst_user u on p.patient_user_id=u.user_id " +
                    "  left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    "  left join mst_staff s on b.bill_user_id=s.staff_id " +
                    "  left join mst_user mu on s.staff_user_id=mu.user_id " +
                    "  left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    "  left join mst_unit un on b.tbill_unit_id=un.unit_id  where b.ipd_bill=" + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += "  SELECT b.bill_id,date(tr.created_date)as rDate,b.bill_number as InvoiceNO,tr.br_reciept_number as recieptNo, " +
                    " ifnull(p.patient_mr_no,'')as patient_mr_no ,ifnull(b.bill_ipd_number,'')as OpdNo, " +
                    " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname, " +
                    " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable, " +
                    " ifnull(m.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount, " +
                    " (SELECT SUM(trr.br_payment_amount) FROM tbill_reciept trr WHERE trr.br_bill_id=tr.br_bill_id) as PaidAmount,ifnull(b.bill_outstanding,'')as OSA, " +
                    " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,''))as CollectedBY, " +
                    " ifnull(cu.cc_name,'') as cc_name,ifnull(un.unit_name,'')as unit_name , " +
                    " (SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    " where bs_bill_id=tr.br_bill_id) as discountonservice " +
                    " FROM tbill_reciept tr " +
                    " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    " left join trn_admission a on b.bill_admission_id=a.admission_id" +
                    " left join mst_patient p on a.admission_patient_id=p.patient_id" +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
            Query1 += "   SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    " where bs_bill_id=tr.br_bill_id) as discountonservice FROM tbill_reciept tr " +
                    " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    " left join trn_admission a on b.bill_admission_id=a.admission_id" +
                    " left join mst_patient p on a.admission_patient_id=p.patient_id" +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
        } else {
            all = true;
            Query += " SELECT b.bill_id,date(tr.created_date)as rDate,b.bill_number as InvoiceNO,tr.br_reciept_number as recieptNo,  " +
                    " ifnull(p.patient_mr_no,'')as patient_mr_no ,ifnull(b.bill_opd_number,'')as OpdNo,  CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname,  " +
                    " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable,  ifnull(m.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount,  " +
                    " (SELECT SUM(trr.br_payment_amount) " +
                    " FROM tbill_reciept trr " +
                    " WHERE trr.br_bill_id=tr.br_bill_id) as PaidAmount,ifnull(b.bill_outstanding,'')as OSA,  " +
                    " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,''))as CollectedBY,  ifnull(cu.cc_name,'') as cc_name,ifnull(un.unit_name,'')as unit_name ,  " +
                    " (SELECT sum(bs_discount_amount) " +
                    " FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id) as discountonservice  " +
                    " FROM tbill_reciept tr  left join tbill_bill b on tr.br_bill_id=b.bill_id  " +
                    " left join mst_visit v on b.bill_visit_id=v.visit_id  " +
                    " left join mst_patient p on v.visit_patient_id=p.patient_id  " +
                    " left join mst_user u on p.patient_user_id=u.user_id  " +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id  " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id  " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id  " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id  " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill = 0";
            MixQuery += " SELECT b.bill_id,date(tr.created_date)as rDate,b.bill_number as InvoiceNO,tr.br_reciept_number as recieptNo,  " +
                    " ifnull(p.patient_mr_no,'')as patient_mr_no ,ifnull(b.bill_ipd_number,'')as OpdNo,  CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname,  " +
                    " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable,  ifnull(m.pm_name,'')as PaymentMode, ifnull(tr.br_payment_amount,'')as PaymenModeAmount,  " +
                    " (SELECT SUM(trr.br_payment_amount) " +
                    " FROM tbill_reciept trr " +
                    " WHERE trr.br_bill_id=tr.br_bill_id) as PaidAmount,ifnull(b.bill_outstanding,'')as OSA,  " +
                    " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,''))as CollectedBY,  ifnull(cu.cc_name,'') as cc_name,ifnull(un.unit_name,'')as unit_name ,  " +
                    " (SELECT sum(bs_discount_amount) " +
                    " FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id) as discountonservice  " +
                    " FROM tbill_reciept tr  left join tbill_bill b on tr.br_bill_id=b.bill_id  " +
                    " left join trn_admission a on b.bill_admission_id=a.admission_id " +
                    " left join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id " +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id  " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id  " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id  " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id  " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill = 1 ";
            Query1 += "   SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    " where bs_bill_id=tr.br_bill_id) as discountonservice FROM tbill_reciept tr " +
                    " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    " left join trn_admission a on b.bill_admission_id=a.admission_id" +
                    " left join mst_patient p on a.admission_patient_id=p.patient_id" +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where 1 ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (opdipdcollectionDetailSearchDTO.getTodaydate()) {
            Query += " and (date(tr.created_date)=curdate()) ";
            Query1 += " and (date(tr.created_date)=curdate()) ";
            MixQuery += "and (date(tr.created_date)=curdate()) ";

        } else {
            Query += " and (date(tr.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query1 += " and (date(tr.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            MixQuery += " and (date(tr.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and b.tbill_unit_id in (" + values + ") ";
            Query1 += " and b.tbill_unit_id in (" + values + ") ";
            MixQuery += " and b.tbill_unit_id in (" + values + ") ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query += " and b.bill_user_id in (" + values + ") ";
            Query1 += " and b.bill_user_id in (" + values + ") ";
            MixQuery += " and b.bill_user_id in (" + values + ") ";
        }
        // CashCounter Id
        if (!ccId.equals("null") && !ccId.equals("0")) {
            Query += " and b.bill_cash_counter_id =  " + ccId + " ";
            Query1 += " and b.bill_cash_counter_id =  " + ccId + " ";
            MixQuery += " and b.bill_cash_counter_id =  " + ccId + " ";
        }
        // Sponsor ID
//        if (!bbsId.equals("null") && !bbsId.equals("0")) {
//            Query += " and b.bill_cash_counter_id =  " + bbsId + " ";
//        }
        // Collection Mode
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query += " and tr.br_payment_amount!=0 and b.bill_outstanding=0 ";
                Query1 += " and tr.br_payment_amount!=0 and b.bill_outstanding=0 ";
                MixQuery += " and tr.br_payment_amount!=0 and b.bill_outstanding=0 ";
            } else if (clclId.equals("2")) {
                Query += " and tr.br_payment_amount!=0 and b.bill_outstanding!=0 ";
                Query1 += " and tr.br_payment_amount!=0 and b.bill_outstanding!=0 ";
                MixQuery += " and tr.br_payment_amount!=0 and b.bill_outstanding!=0 ";
            } else {
                Query += " and tr.br_payment_amount=0 and b.bill_outstanding!=0 ";
                Query1 += " and tr.br_payment_amount=0 and b.bill_outstanding!=0 ";
                MixQuery += " and tr.br_payment_amount=0 and b.bill_outstanding!=0 ";
            }
        }
        String MainQuery1 = " select " +
                " sum(test123.ConcOnBill+test123.bill_net_payable)as GrandGrossTotalAmount, " +
                " sum(test123.ConcOnBill)as GrandConcessionAmount, " +
                " sum(test123.bill_net_payable)as GrandBillNetPaybleAmount, " +
                " sum(test123.PaidAmount)as GrandPaidAmount, " +
                " sum(test123.OSA)as GrandOSA, " +
                " sum(test123.discountonservice) as GrandDiscountonService , " +
                " ifnull(sum(test123.BillRefund),0) as GrandBillRefund, " +
                " ( sum(test123.discountonservice)+sum(test123.ConcOnBill)+ sum(test123.bill_net_payable)) as GrandTotalAmount, " +
                " ifnull( (SELECT sum(tr.br_payment_amount) FROM tbill_reciept tr where tr.br_pm_id=6) ,0)as GrandCredit " +
                " " +
                " from    (select * from ( SELECT b.bill_id as bbId, " +
                " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable, " +
                " ifnull(tr.br_payment_amount,'')as PaidAmount,ifnull(b.bill_outstanding,'')as OSA, " +
                " ( " + Query1 + " ) as test " +
                " left join  (select tb.bill_id,sum(bs.bs_gross_rate)as BillRefund " +
                " from tbill_bill tb,tbill_bill_service bs where tb.bill_id=bs.bs_bill_id and bs.bs_cancel=1  " +
                " group by tb.bill_id) as billre   on test.bbId=billre.bill_id group by test.bbId ) as test123 ";
        String MainQuery = " select ifnull(test.bill_id,'')as bill_id,ifnull(test.rDate,'')as rDate," +
                "  ifnull(test.InvoiceNO,'')as InvoiceNO,ifnull(test.recieptNo,0)as recieptNo,ifnull(test.patient_mr_no,'')as patient_mr_no,ifnull(test.OpdNo,'')as OpdNo," +
                "   ifnull( test.patientname,'')as patientname,ifnull(test.ConcOnBill,0)as ConcOnBill,ifnull(test.bill_net_payable,0)as bill_net_payable," +
                " ifnull(test.PaymentMode,'')as PaymentMode, " +
                "  ifnull(test.PaymenModeAmount,0)as PaymenModeAmount,ifnull(test.PaidAmount,0)as PaidAmount,ifnull(test.OSA,0)as OSA," +
                "  ifnull(test.CollectedBY,'')as CollectedBY,  ifnull(test.cc_name,'')as cc_name," +
                "  ifnull(test.unit_name,'')as unit_name,ifnull(test.discountonservice,0)as discountonservice,ifnull(billre.BillRefund,0)as BillRefund " +
                "  " +
                " from " +
                " ( " + Query + "  ) as test " +
                " left join " +
                " (select tb.bill_id,sum(bs.bs_gross_rate)as BillRefund from tbill_bill tb,tbill_bill_service bs where tb.bill_id=bs.bs_bill_id and bs.bs_cancel=1 group by tb.bill_id) as billre " +
                " on test.bill_id=billre.bill_id group by test.bill_id  ";
        String MainMixQuery = " select ifnull(test.bill_id,'')as bill_id,ifnull(test.rDate,'')as rDate," +
                "  ifnull(test.InvoiceNO,'')as InvoiceNO,ifnull(test.recieptNo,0)as recieptNo,ifnull(test.patient_mr_no,'')as patient_mr_no,ifnull(test.OpdNo,'')as OpdNo," +
                "   ifnull( test.patientname,'')as patientname,ifnull(test.ConcOnBill,0)as ConcOnBill,ifnull(test.bill_net_payable,0)as bill_net_payable," +
                " ifnull(test.PaymentMode,'')as PaymentMode, " +
                "  ifnull(test.PaymenModeAmount,0)as PaymenModeAmount,ifnull(test.PaidAmount,0)as PaidAmount,ifnull(test.OSA,0)as OSA," +
                "  ifnull(test.CollectedBY,'')as CollectedBY,  ifnull(test.cc_name,'')as cc_name," +
                "  ifnull(test.unit_name,'')as unit_name,ifnull(test.discountonservice,0)as discountonservice,ifnull(billre.BillRefund,0)as BillRefund " +
                "  " +
                " from " +
                " ( " + MixQuery + "  ) as test " +
                " left join " +
                " (select tb.bill_id,sum(bs.bs_gross_rate)as BillRefund from tbill_bill tb,tbill_bill_service bs where tb.bill_id=bs.bs_bill_id and bs.bs_cancel=1 group by tb.bill_id) as billre " +
                " on test.bill_id=billre.bill_id group by test.bill_id  ";
        if (all) {
            MainQuery = MainQuery + " union All " + MainMixQuery;
        }
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "bill_id,rDate,InvoiceNO,recieptNo,patient_mr_no,OpdNo,patientname,ConcOnBill,bill_net_payable,PaymentMode,PaymenModeAmount,PaidAmount,OSA,CollectedBY,cc_name,unit_name,discountonservice,BillRefund";
        //    MainQuery += "  limit " + ((opdipdcollectionDetailSearchDTO.getOffset() - 1) * opdipdcollectionDetailSearchDTO.getLimit()) + "," + opdipdcollectionDetailSearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        for (int j = 0; j < jsonArray.length(); j++) {
            if (!jsonArray.getJSONObject(j).get("PaymentMode").toString().equals("")) {
                if (jsonArray.getJSONObject(j).get("bill_id") != null) {
                    String Query5 = " SELECT tr.br_pm_id,sum(tr.br_payment_amount) " +
                            " FROM tbill_reciept tr where br_bill_id =" + jsonArray.getJSONObject(j).get("bill_id") + " group by tr.br_pm_id ";
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
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery1).getResultList();
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("GrandGrossTotalAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("GrandConcessionAmount", ob[1].toString());
                jsonArray.getJSONObject(0).put("GrandBillNetPaybleAmount", ob[2].toString());
                jsonArray.getJSONObject(0).put("GrandPaidAmount", ob[3].toString());
                jsonArray.getJSONObject(0).put("GrandOSA", ob[4].toString());
                jsonArray.getJSONObject(0).put("GrandDiscountonService", ob[5].toString());
                jsonArray.getJSONObject(0).put("GrandBillRefund", ob[6].toString());
                jsonArray.getJSONObject(0).put("GrandTotalAmount", ob[7].toString());
                jsonArray.getJSONObject(0).put("GrandCredit", ob[8].toString());
//                int totalamount = 0;
//                totalamount = discountonservice + ConcOnBill + bill_net_payable;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        // return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(opdipdcollectionDetailSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(opdipdcollectionDetailSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (opdipdcollectionDetailSearchDTO.getPrint()) {
            columnName = "rDate,InvoiceNO,patient_mr_no,OpdNo,patientname,discountonservice,ConcOnBill,bill_net_payable,Cash,Cheque,Card,Transfer,DebitCard,Credit,PaidAmount,OSA,BillRefund,CollectedBY,cc_name,unit_name";
            return createReport.generateExcel(columnName, "OpdIpdCollectionDetailsReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("OpdIpdCollectionDetailsReport", jsonArray.toString());
        }
    }
    // Method For Collection Details Report Jasper Print End

    // Method For Collection audit Report Start
    @RequestMapping("getopdcollectionAuditReport/{unitList}/{mstuserlist}/{ccId}/{clclId}/{bbsId}/{IPDFlag}/{LimitFlag}")
    public ResponseEntity searchCollectionAuditDetail(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdCollectionDetailSearchDTO opdipdcollectionDetailSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String ccId, @PathVariable String clclId, @PathVariable String bbsId, @PathVariable int IPDFlag, @PathVariable int LimitFlag) {
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
            Query += " SELECT b.bill_id,date(b.created_date)as rDate, " +
                    "  ifnull(p.patient_mr_no,'')as patient_mr_no , CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname, " +
                    "  ifnull(b.bill_net_payable,'')as bill_net_payable, ifnull(tr.br_payment_amount,0)as PaidAmount, " +
                    "  ifnull(un.unit_name,'')as unit_name , " +
                    "  ifnull(u.user_age,'')as user_age,ifnull(u.user_mobile,'')as user_mobile,ifnull(u.user_email,'')as user_email,ifnull(mt.tariff_name,'')as tariff_name, " +
                    "  date(p.created_date)as RegDate,ifnull(mn.nationality_name,'')as nationality_name,ifnull(u.user_uid,'')as user_uid,ifnull(gn.gender_name,'')as gender_name, " +
                    "  (SELECT sum(bs_discount_amount) FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id) as discountonservice " +
                    "  FROM tbill_bill b" +
                    "  left join tbill_reciept tr on b.bill_id=tr.br_bill_id " +
                    "  left join mbill_tariff mt on b.bill_tariff_id=mt.tariff_id " +
                    "  left join mst_visit v on b.bill_visit_id=v.visit_id " +
                    "  left join mst_patient p on v.visit_patient_id=p.patient_id " +
                    "  left join mst_user u on p.patient_user_id=u.user_id " +
                    "  left join mst_gender gn on u.user_gender_id=gn.gender_id " +
                    "  left join mst_nationality mn on u.user_nationality_id=mn.nationality_id " +
                    "  left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    "  left join mst_staff s on b.bill_user_id=s.staff_id " +
                    "  left join mst_user mu on s.staff_user_id=mu.user_id " +
                    "  left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    "  left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
            Query1 += " SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    "  where bs_bill_id=tr.br_bill_id) as discountonservice FROM tbill_reciept tr " +
                    "  left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    "  left join mst_visit v on b.bill_visit_id=v.visit_id " +
                    "  left join mst_patient p on v.visit_patient_id=p.patient_id " +
                    "  left join mst_user u on p.patient_user_id=u.user_id " +
                    "  left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    "  left join mst_staff s on b.bill_user_id=s.staff_id " +
                    "  left join mst_user mu on s.staff_user_id=mu.user_id " +
                    "  left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    "  left join mst_unit un on b.tbill_unit_id=un.unit_id  where b.ipd_bill=" + IPDFlag;

        } else {
            Query += " SELECT b.bill_id,date(b.created_date)as rDate, " +
                    "  ifnull(p.patient_mr_no,'')as patient_mr_no , CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname, " +
                    "  ifnull(b.bill_net_payable,'')as bill_net_payable, ifnull(tr.br_payment_amount,0)as PaidAmount, " +
                    "  ifnull(un.unit_name,'')as unit_name , " +
                    "  ifnull(u.user_age,'')as user_age,ifnull(u.user_mobile,'')as user_mobile,ifnull(u.user_email,'')as user_email,ifnull(mt.tariff_name,'')as tariff_name, " +
                    "  date(p.created_date)as RegDate,ifnull(mn.nationality_name,'')as nationality_name,ifnull(u.user_uid,'')as user_uid,ifnull(gn.gender_name,'')as gender_name, " +
                    "  (SELECT sum(bs_discount_amount) FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id) as discountonservice " +
                    "  FROM  tbill_bill b " +
                    "  left join tbill_reciept tr on b.bill_id=tr.br_bill_id " +
                    "  left join mbill_tariff mt on b.bill_tariff_id=mt.tariff_id " +
                    "  left join mst_visit v on b.bill_visit_id=v.visit_id " +
                    "  left join mst_patient p on v.visit_patient_id=p.patient_id " +
                    "  left join mst_user u on p.patient_user_id=u.user_id " +
                    "  left join mst_gender gn on u.user_gender_id=gn.gender_id " +
                    "  left join mst_nationality mn on u.user_nationality_id=mn.nationality_id " +
                    "  left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    "  left join mst_staff s on b.bill_user_id=s.staff_id " +
                    "  left join mst_user mu on s.staff_user_id=mu.user_id " +
                    "  left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    "  left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
            Query1 += "   SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    " where bs_bill_id=tr.br_bill_id) as discountonservice FROM tbill_reciept tr " +
                    " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    " left join trn_admission a on b.bill_admission_id=a.admission_id" +
                    " left join mst_patient p on a.admission_patient_id=p.patient_id" +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
        }
        if (opdipdcollectionDetailSearchDTO.getFromdate().equals("") || opdipdcollectionDetailSearchDTO.getFromdate().equals("null")) {
            opdipdcollectionDetailSearchDTO.setFromdate("1990-06-07");
        }
        if (opdipdcollectionDetailSearchDTO.getTodate().equals("") || opdipdcollectionDetailSearchDTO.getTodate().equals("null")) {
            opdipdcollectionDetailSearchDTO.setTodate(strDate);
        }
        if (opdipdcollectionDetailSearchDTO.getTodaydate()) {
            Query += " and (date(b.created_date)=curdate()) ";
            Query1 += " and (date(b.created_date)=curdate()) ";
        } else {
            Query += " and (date(b.created_date) between '" + opdipdcollectionDetailSearchDTO.getFromdate() + "' and '" + opdipdcollectionDetailSearchDTO.getTodate() + "')  ";
            Query1 += " and (date(b.created_date) between '" + opdipdcollectionDetailSearchDTO.getFromdate() + "' and '" + opdipdcollectionDetailSearchDTO.getTodate() + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and b.tbill_unit_id in (" + values + ") ";
            Query1 += " and b.tbill_unit_id in (" + values + ") ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query += " and s.staff_user_id in (" + values + ") ";
            Query1 += " and s.staff_user_id in (" + values + ") ";
        }
        // CashCounter Id
        if (!ccId.equals("null") && !ccId.equals("0")) {
            Query += " and b.bill_cash_counter_id =  " + ccId + " ";
            Query1 += " and b.bill_cash_counter_id =  " + ccId + " ";
        }
        // Sponsor ID
//        if (!bbsId.equals("null") && !bbsId.equals("0")) {
//            Query += " and b.bill_cash_counter_id =  " + bbsId + " ";
//        }
        // Collection Mode
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query += " and tr.br_payment_amount!=0 and b.bill_outstanding=0 ";
                Query1 += " and tr.br_payment_amount!=0 and b.bill_outstanding=0 ";
            } else if (clclId.equals("2")) {
                Query += " and tr.br_payment_amount!=0 and b.bill_outstanding!=0 ";
                Query1 += " and tr.br_payment_amount!=0 and b.bill_outstanding!=0 ";
            } else {
                Query += " and tr.br_payment_amount=0 and b.bill_outstanding!=0 ";
                Query1 += " and tr.br_payment_amount=0 and b.bill_outstanding!=0 ";
            }
        }
        String MainQuery1 = " select " +
                " sum(test123.bill_net_payable)as GrandBillNetPaybleAmount, " +
                " sum(test123.PaidAmount)as GrandPaidAmount, " +
                " ifnull( (SELECT sum(tr.br_payment_amount) FROM tbill_reciept tr where tr.br_pm_id=6) ,0)as GrandCredit " +
                " from    (select * from ( SELECT b.bill_id as bbId, " +
                " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable, " +
                " ifnull(tr.br_payment_amount,'')as PaidAmount,ifnull(b.bill_outstanding,'')as OSA, " +
                " ( " + Query1 + " ) as test " +
                " left join  (select tb.bill_id,sum(bs.bs_gross_rate)as BillRefund " +
                " from tbill_bill tb,tbill_bill_service bs where tb.bill_id=bs.bs_bill_id and bs.bs_cancel=1  " +
                " group by tb.bill_id) as billre   on test.bbId=billre.bill_id group by test.bbId ) as test123 ";
        String MainQuery = " select test.bill_id,test.rDate,test.patient_mr_no,test.patientname,test.bill_net_payable, " +
                "  test.PaidAmount,test.unit_name,test.user_age,test.user_mobile,test.user_email,test.tariff_name, " +
                "  test.RegDate,test.nationality_name,test.user_uid,test.gender_name" +
                " from " +
                " ( " + Query + "  ) as test " +
                " left join " +
                " (select tb.bill_id,sum(bs.bs_gross_rate)as BillRefund from tbill_bill tb,tbill_bill_service bs where tb.bill_id=bs.bs_bill_id and bs.bs_cancel=1 group by tb.bill_id) as billre " +
                " on test.bill_id=billre.bill_id group by test.bill_id  ";
        System.out.println("Collection Audit Reports(Unit-Wise):" + MainQuery);
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "bill_id,rDate,patient_mr_no,patientname,bill_net_payable,PaidAmount,unit_name,user_age,user_mobile,user_email,tariff_name,RegDate,nationality_name,user_uid,gender_name";
        if (LimitFlag == 1) {
            MainQuery += "  limit " + ((opdipdcollectionDetailSearchDTO.getOffset() - 1) * opdipdcollectionDetailSearchDTO.getLimit()) + "," + opdipdcollectionDetailSearchDTO.getLimit();
        }
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
//        for (int j = 0; j < jsonArray.length(); j++) {
//            String Query5 = " SELECT tr.br_pm_id,sum(tr.br_payment_amount) " +
//                    " FROM tbill_reciept tr where br_bill_id =" + jsonArray.getJSONObject(j).get("bill_id") + " group by tr.br_pm_id ";
//
//            List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query5).getResultList();
//            for (Object[] obj : list) {
//                if (obj[0].toString().equals("1")) {
//                    jsonArray.getJSONObject(j).put("Cash", obj[1].toString());
//                } else if (obj[0].toString().equals("2")) {
//                    jsonArray.getJSONObject(j).put("Cheque", obj[1].toString());
//                } else if (obj[0].toString().equals("3")) {
//                    jsonArray.getJSONObject(j).put("Card", obj[1].toString());
//                } else if (obj[0].toString().equals("4")) {
//                    jsonArray.getJSONObject(j).put("Transfer", obj[1].toString());
//                } else if (obj[0].toString().equals("5")) {
//                    jsonArray.getJSONObject(j).put("DebitCard", obj[1].toString());
//                } else if (obj[0].toString().equals("6")) {
//                    jsonArray.getJSONObject(j).put("Credit", obj[1].toString());
//                }
//            }
//        }
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery1).getResultList();
        HashMap<String, Integer> hm = new HashMap<>();
        JSONArray newArray = new JSONArray();
        HashMap<String, Integer> tariffWiseCount = new HashMap<>();
        HashMap<String, Integer> reasonWiseCount = new HashMap<>();
        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        HashMap<String, Integer> ageWiseCount = new HashMap<>();
        HashMap<String, Integer> patientWiseCount = new HashMap<>();
        patientWiseCount.put("newPatientCount", 0);
        patientWiseCount.put("followPatientCount", 0);
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
                if (tariffWiseCount.get(obj1.get("tariff_name").toString().replace(" ", "")) == null) {
                    tariffWiseCount.put(obj1.get("tariff_name").toString().replace(" ", ""), 1);
                } else {
                    int reason_count = tariffWiseCount.get(obj1.get("tariff_name").toString().replace(" ", ""));
                    tariffWiseCount.put(obj1.get("tariff_name").toString().replace(" ", ""), ++reason_count);
                }
            }
            if (obj1.get("rDate").toString().equals(obj1.get("RegDate").toString())) {
                int pCount = patientWiseCount.get("newPatientCount");
                patientWiseCount.put("newPatientCount", ++pCount);
            } else {
                int pCount = patientWiseCount.get("followPatientCount");
                patientWiseCount.put("followPatientCount", ++pCount);
            }
//            if (obj1.get("reason_visit") != null && !"".equals(obj1.get("reason_visit"))) {
//                if (reasonWiseCount.get(obj1.get("reason_visit")) == null) {
//                    reasonWiseCount.put(obj1.get("reason_visit").toString().replace(" ", "").toString(), 1);
//                } else {
//                    int reason_count = reasonWiseCount.get(obj1.get("reason_visit").toString().replace(" ", ""));
//                    reasonWiseCount.put(obj1.get("reason_visit").toString().replace(" ", "").toString(), ++reason_count);
//                }
//            }
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
            JSONObject currentObj = jsonArray.getJSONObject(i);
            String key = currentObj.get("rDate").toString() + currentObj.get("patient_mr_no").toString() + currentObj.get("unit_name").toString();
            if (hm.containsKey(key)) {
                int index = hm.get(key);
                JSONObject previousObj = jsonArray.getJSONObject(index);
                float pre_bill_net_payable = Float.parseFloat(previousObj.get("bill_net_payable").toString());
                float pre_PaidAmount = Float.parseFloat(previousObj.get("PaidAmount").toString());
                float curr_bill_net_payable = Float.parseFloat(currentObj.get("bill_net_payable").toString());
                float curr_PaidAmount = Float.parseFloat(currentObj.get("PaidAmount").toString());
                previousObj.put("bill_net_payable", (pre_bill_net_payable + curr_bill_net_payable));
                previousObj.put("PaidAmount", (pre_PaidAmount + curr_PaidAmount));
                jsonArray.put(index, previousObj);
                currentObj.put("rDate", "delete");
                jsonArray.put(i, currentObj);
            } else {
                hm.put(key, i);
            }

        }
        try {
            for (Object[] ob : list) {
//                jsonArray.getJSONObject(0).put("GrandGrossTotalAmount", ob[0].toString());
//                jsonArray.getJSONObject(0).put("GrandConcessionAmount", ob[1].toString());
                jsonArray.getJSONObject(0).put("GrandBillNetPaybleAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("GrandPaidAmount", ob[1].toString());
//                jsonArray.getJSONObject(0).put("GrandOSA", ob[4].toString());
//                jsonArray.getJSONObject(0).put("GrandDiscountonService", ob[5].toString());
//                jsonArray.getJSONObject(0).put("GrandBillRefund", ob[6].toString());
//                jsonArray.getJSONObject(0).put("GrandTotalAmount", ob[7].toString());
                jsonArray.getJSONObject(0).put("GrandCredit", ob[2].toString());
                jsonArray.getJSONObject(0).put("reasonWiseCount", new JSONObject(reasonWiseCount));
                jsonArray.getJSONObject(0).put("tariffWiseCount", new JSONObject(tariffWiseCount));
                jsonArray.getJSONObject(0).put("genderWiseCount", new JSONObject(genderWiseCount));
                jsonArray.getJSONObject(0).put("patientWiseCount", new JSONObject(patientWiseCount));
                jsonArray.getJSONObject(0).put("ageWiseCount", ageWiseCount);
            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject currentObj = jsonArray.getJSONObject(i);
//                String key = currentObj.get("rDate").toString() + currentObj.get("patient_mr_no").toString() + currentObj.get("unit_name").toString();
//                if (hm.containsKey(key)) {
//                    int index = hm.get(key);
//                    JSONObject previousObj = jsonArray.getJSONObject(index);
//                    float pre_bill_net_payable = Float.parseFloat(previousObj.get("bill_net_payable").toString());
//                    float pre_PaidAmount = Float.parseFloat(previousObj.get("PaidAmount").toString());
//                    float curr_bill_net_payable = Float.parseFloat(currentObj.get("bill_net_payable").toString());
//                    float curr_PaidAmount = Float.parseFloat(currentObj.get("PaidAmount").toString());
//                    previousObj.put("bill_net_payable", (pre_bill_net_payable + curr_bill_net_payable));
//                    previousObj.put("PaidAmount", (pre_PaidAmount + curr_PaidAmount));
//                    jsonArray.put(index, previousObj);
//                    currentObj.put("rDate", "delete");
//                    jsonArray.put(i, currentObj);
//                } else {
//                    hm.put(key, i);
//                }
//            }
            int count1 = 0;
            if (LimitFlag == 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObj = jsonArray.getJSONObject(i);
                    if (count1 == opdipdcollectionDetailSearchDTO.getLimit()) {
                        break;
                    }
                    if (!currentObj.get("rDate").toString().equalsIgnoreCase("delete")) {
                        newArray.put(jsonArray.get(i));
                        count1++;
                    }
                }
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObj = jsonArray.getJSONObject(i);
                    if (!currentObj.get("rDate").toString().equalsIgnoreCase("delete")) {
                        newArray.put(jsonArray.get(i));
                    }
                }
            }
//            JSONArray myJson = new JSONArray();
//            if (LimitFlag == 0) {
//                for (int i = 0; i < 20; i++) {
//                    myJson.put(jsonArray.get(i));
//                    System.out.print("Enter Here");
//                }
//                return ResponseEntity.ok(myJson.toString());
//            }
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        return ResponseEntity.ok(newArray.toString());

    }
    // Method For Collection audit Report End

    // Method For Collection Audit Report Jasper Print Start
    @RequestMapping("getopdcollectionAuditReportPrint/{unitList}/{mstuserlist}/{ccId}/{clclId}/{bbsId}/{fromdate}/{todate}/{IPDFlag}")
    public String searchCollectionAuditDetailPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdCollectionDetailSearchDTO opdipdcollectionDetailSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String ccId, @PathVariable String clclId, @PathVariable String bbsId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) {
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
            Query += " SELECT b.bill_id,date(b.created_date)as rDate, " +
                    "  ifnull(p.patient_mr_no,'')as patient_mr_no , CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname, " +
                    "  ifnull(b.bill_net_payable,'')as bill_net_payable, ifnull(tr.br_payment_amount,0)as PaidAmount, " +
                    "  ifnull(un.unit_name,'')as unit_name , " +
                    "  ifnull(u.user_age,'')as user_age,ifnull(u.user_mobile,'')as user_mobile,ifnull(u.user_email,'')as user_email,ifnull(mt.tariff_name,'')as tariff_name, " +
                    "  date(p.created_date)as RegDate,ifnull(mn.nationality_name,'')as nationality_name,ifnull(u.user_uid,'')as user_uid,ifnull(gn.gender_name,'')as gender_name, " +
                    "  (SELECT sum(bs_discount_amount) FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id) as discountonservice " +
                    "  FROM tbill_bill b" +
                    "  left join tbill_reciept tr on b.bill_id=tr.br_bill_id " +
                    "  left join mbill_tariff mt on b.bill_tariff_id=mt.tariff_id " +
                    "  left join mst_visit v on b.bill_visit_id=v.visit_id " +
                    "  left join mst_patient p on v.visit_patient_id=p.patient_id " +
                    "  left join mst_user u on p.patient_user_id=u.user_id " +
                    "  left join mst_gender gn on u.user_gender_id=gn.gender_id " +
                    "  left join mst_nationality mn on u.user_nationality_id=mn.nationality_id " +
                    "  left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    "  left join mst_staff s on b.bill_user_id=s.staff_id " +
                    "  left join mst_user mu on s.staff_user_id=mu.user_id " +
                    "  left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    "  left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
            Query1 += " SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    "  where bs_bill_id=tr.br_bill_id) as discountonservice FROM tbill_reciept tr " +
                    "  left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    "  left join mst_visit v on b.bill_visit_id=v.visit_id " +
                    "  left join mst_patient p on v.visit_patient_id=p.patient_id " +
                    "  left join mst_user u on p.patient_user_id=u.user_id " +
                    "  left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    "  left join mst_staff s on b.bill_user_id=s.staff_id " +
                    "  left join mst_user mu on s.staff_user_id=mu.user_id " +
                    "  left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    "  left join mst_unit un on b.tbill_unit_id=un.unit_id  where b.ipd_bill=" + IPDFlag;

        } else {
            Query += " SELECT b.bill_id,date(b.created_date)as rDate, " +
                    "  ifnull(p.patient_mr_no,'')as patient_mr_no , CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,''))as patientname, " +
                    "  ifnull(b.bill_net_payable,'')as bill_net_payable, ifnull(tr.br_payment_amount,0)as PaidAmount, " +
                    "  ifnull(un.unit_name,'')as unit_name , " +
                    "  ifnull(u.user_age,'')as user_age,ifnull(u.user_mobile,'')as user_mobile,ifnull(u.user_email,'')as user_email,ifnull(mt.tariff_name,'')as tariff_name, " +
                    "  date(p.created_date)as RegDate,ifnull(mn.nationality_name,'')as nationality_name,ifnull(u.user_uid,'')as user_uid,ifnull(gn.gender_name,'')as gender_name, " +
                    "  (SELECT sum(bs_discount_amount) FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id) as discountonservice " +
                    "  FROM  tbill_bill b " +
                    "  left join tbill_reciept tr on b.bill_id=tr.br_bill_id " +
                    "  left join mbill_tariff mt on b.bill_tariff_id=mt.tariff_id " +
                    "  left join mst_visit v on b.bill_visit_id=v.visit_id " +
                    "  left join mst_patient p on v.visit_patient_id=p.patient_id " +
                    "  left join mst_user u on p.patient_user_id=u.user_id " +
                    "  left join mst_gender gn on u.user_gender_id=gn.gender_id " +
                    "  left join mst_nationality mn on u.user_nationality_id=mn.nationality_id " +
                    "  left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    "  left join mst_staff s on b.bill_user_id=s.staff_id " +
                    "  left join mst_user mu on s.staff_user_id=mu.user_id " +
                    "  left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    "  left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
            Query1 += "   SELECT sum(bs_discount_amount) FROM tbill_bill_service " +
                    " where bs_bill_id=tr.br_bill_id) as discountonservice FROM tbill_reciept tr " +
                    " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                    " left join trn_admission a on b.bill_admission_id=a.admission_id" +
                    " left join mst_patient p on a.admission_patient_id=p.patient_id" +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                    " left join mst_staff s on b.bill_user_id=s.staff_id " +
                    " left join mst_user mu on s.staff_user_id=mu.user_id " +
                    " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                    " left join mst_unit un on b.tbill_unit_id=un.unit_id where b.ipd_bill=" + IPDFlag;
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (opdipdcollectionDetailSearchDTO.getTodaydate()) {
            Query += " and (date(b.created_date)=curdate()) ";
            Query1 += " and (date(b.created_date)=curdate()) ";
        } else {
            Query += " and (date(b.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query1 += " and (date(b.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and b.tbill_unit_id in (" + values + ") ";
            Query1 += " and b.tbill_unit_id in (" + values + ") ";
        }
        if (!mstuserlist[0].equals("null")) {
            String values = mstuserlist[0];
            for (int i = 1; i < mstuserlist.length; i++) {
                values += "," + mstuserlist[i];
            }
            Query += " and s.staff_user_id in (" + values + ") ";
            Query1 += " and s.staff_user_id in (" + values + ") ";
        }
        // CashCounter Id
        if (!ccId.equals("null") && !ccId.equals("0")) {
            Query += " and b.bill_cash_counter_id =  " + ccId + " ";
            Query1 += " and b.bill_cash_counter_id =  " + ccId + " ";
        }
        // Sponsor ID
//        if (!bbsId.equals("null") && !bbsId.equals("0")) {
//            Query += " and b.bill_cash_counter_id =  " + bbsId + " ";
//        }
        // Collection Mode
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query += " and tr.br_payment_amount!=0 and b.bill_outstanding=0 ";
                Query1 += " and tr.br_payment_amount!=0 and b.bill_outstanding=0 ";
            } else if (clclId.equals("2")) {
                Query += " and tr.br_payment_amount!=0 and b.bill_outstanding!=0 ";
                Query1 += " and tr.br_payment_amount!=0 and b.bill_outstanding!=0 ";
            } else {
                Query += " and tr.br_payment_amount=0 and b.bill_outstanding!=0 ";
                Query1 += " and tr.br_payment_amount=0 and b.bill_outstanding!=0 ";
            }
        }
        String MainQuery1 = " select " +
                " sum(test123.bill_net_payable)as GrandBillNetPaybleAmount, " +
                " sum(test123.PaidAmount)as GrandPaidAmount, " +
                " ifnull( (SELECT sum(tr.br_payment_amount) FROM tbill_reciept tr where tr.br_pm_id=6) ,0)as GrandCredit " +
                " from    (select * from ( SELECT b.bill_id as bbId, " +
                " ifnull(b.bill_discount_amount,'')as ConcOnBill,ifnull(b.bill_net_payable,'')as bill_net_payable, " +
                " ifnull(tr.br_payment_amount,'')as PaidAmount,ifnull(b.bill_outstanding,'')as OSA, " +
                " ( " + Query1 + " ) as test " +
                " left join  (select tb.bill_id,sum(bs.bs_gross_rate)as BillRefund " +
                " from tbill_bill tb,tbill_bill_service bs where tb.bill_id=bs.bs_bill_id and bs.bs_cancel=1  " +
                " group by tb.bill_id) as billre   on test.bbId=billre.bill_id group by test.bbId ) as test123 ";
        String MainQuery = " select test.bill_id,test.rDate,test.patient_mr_no,test.patientname,test.bill_net_payable, " +
                "  test.PaidAmount,test.unit_name,test.user_age,test.user_mobile,test.user_email,test.tariff_name, " +
                "  test.RegDate,test.nationality_name,test.user_uid,test.gender_name" +
                " from " +
                " ( " + Query + "  ) as test " +
                " left join " +
                " (select tb.bill_id,sum(bs.bs_gross_rate)as BillRefund from tbill_bill tb,tbill_bill_service bs where tb.bill_id=bs.bs_bill_id and bs.bs_cancel=1 group by tb.bill_id) as billre " +
                " on test.bill_id=billre.bill_id group by test.bill_id  ";
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "bill_id,rDate,patient_mr_no,patientname,bill_net_payable,PaidAmount,unit_name,user_age,user_mobile,user_email,tariff_name,RegDate,nationality_name,user_uid,gender_name";
//        if (LimitFlag == 1) {
//            MainQuery += "  limit " + ((opdipdcollectionDetailSearchDTO.getOffset() - 1) * opdipdcollectionDetailSearchDTO.getLimit()) + "," + opdipdcollectionDetailSearchDTO.getLimit();
//        }
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
//        for (int j = 0; j < jsonArray.length(); j++) {
//            String Query5 = " SELECT tr.br_pm_id,sum(tr.br_payment_amount) " +
//                    " FROM tbill_reciept tr where br_bill_id =" + jsonArray.getJSONObject(j).get("bill_id") + " group by tr.br_pm_id ";
//
//            List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query5).getResultList();
//            for (Object[] obj : list) {
//                if (obj[0].toString().equals("1")) {
//                    jsonArray.getJSONObject(j).put("Cash", obj[1].toString());
//                } else if (obj[0].toString().equals("2")) {
//                    jsonArray.getJSONObject(j).put("Cheque", obj[1].toString());
//                } else if (obj[0].toString().equals("3")) {
//                    jsonArray.getJSONObject(j).put("Card", obj[1].toString());
//                } else if (obj[0].toString().equals("4")) {
//                    jsonArray.getJSONObject(j).put("Transfer", obj[1].toString());
//                } else if (obj[0].toString().equals("5")) {
//                    jsonArray.getJSONObject(j).put("DebitCard", obj[1].toString());
//                } else if (obj[0].toString().equals("6")) {
//                    jsonArray.getJSONObject(j).put("Credit", obj[1].toString());
//                }
//            }
//        }
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery1).getResultList();
        HashMap<String, Integer> hm = new HashMap<>();
        JSONArray newArray = new JSONArray();
        HashMap<String, Integer> tariffWiseCount = new HashMap<>();
        HashMap<String, Integer> reasonWiseCount = new HashMap<>();
        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        HashMap<String, Integer> ageWiseCount = new HashMap<>();
        HashMap<String, Integer> patientWiseCount = new HashMap<>();
        patientWiseCount.put("newPatientCount", 0);
        patientWiseCount.put("followPatientCount", 0);
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
                if (tariffWiseCount.get(obj1.get("tariff_name").toString().replace(" ", "")) == null) {
                    tariffWiseCount.put(obj1.get("tariff_name").toString().replace(" ", ""), 1);
                } else {
                    int reason_count = tariffWiseCount.get(obj1.get("tariff_name").toString().replace(" ", ""));
                    tariffWiseCount.put(obj1.get("tariff_name").toString().replace(" ", ""), ++reason_count);
                }
            }
            if (obj1.get("rDate").toString().equals(obj1.get("RegDate").toString())) {
                int pCount = patientWiseCount.get("newPatientCount");
                patientWiseCount.put("newPatientCount", ++pCount);
            } else {
                int pCount = patientWiseCount.get("followPatientCount");
                patientWiseCount.put("followPatientCount", ++pCount);
            }
//            if (obj1.get("reason_visit") != null && !"".equals(obj1.get("reason_visit"))) {
//                if (reasonWiseCount.get(obj1.get("reason_visit")) == null) {
//                    reasonWiseCount.put(obj1.get("reason_visit").toString().replace(" ", "").toString(), 1);
//                } else {
//                    int reason_count = reasonWiseCount.get(obj1.get("reason_visit").toString().replace(" ", ""));
//                    reasonWiseCount.put(obj1.get("reason_visit").toString().replace(" ", "").toString(), ++reason_count);
//                }
//            }
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
            JSONObject currentObj = jsonArray.getJSONObject(i);
            String key = currentObj.get("rDate").toString() + currentObj.get("patient_mr_no").toString() + currentObj.get("unit_name").toString();
            if (hm.containsKey(key)) {
                int index = hm.get(key);
                JSONObject previousObj = jsonArray.getJSONObject(index);
                float pre_bill_net_payable = Float.parseFloat(previousObj.get("bill_net_payable").toString());
                float pre_PaidAmount = Float.parseFloat(previousObj.get("PaidAmount").toString());
                float curr_bill_net_payable = Float.parseFloat(currentObj.get("bill_net_payable").toString());
                float curr_PaidAmount = Float.parseFloat(currentObj.get("PaidAmount").toString());
                previousObj.put("bill_net_payable", (pre_bill_net_payable + curr_bill_net_payable));
                previousObj.put("PaidAmount", (pre_PaidAmount + curr_PaidAmount));
                jsonArray.put(index, previousObj);
                currentObj.put("rDate", "delete");
                jsonArray.put(i, currentObj);
            } else {
                hm.put(key, i);
            }

        }
        try {
            for (Object[] ob : list) {
//                jsonArray.getJSONObject(0).put("GrandGrossTotalAmount", ob[0].toString());
//                jsonArray.getJSONObject(0).put("GrandConcessionAmount", ob[1].toString());
                jsonArray.getJSONObject(0).put("GrandBillNetPaybleAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("GrandPaidAmount", ob[1].toString());
//                jsonArray.getJSONObject(0).put("GrandOSA", ob[4].toString());
//                jsonArray.getJSONObject(0).put("GrandDiscountonService", ob[5].toString());
//                jsonArray.getJSONObject(0).put("GrandBillRefund", ob[6].toString());
//                jsonArray.getJSONObject(0).put("GrandTotalAmount", ob[7].toString());
                jsonArray.getJSONObject(0).put("GrandCredit", ob[2].toString());
                jsonArray.getJSONObject(0).put("fromdate", fromdate);
                jsonArray.getJSONObject(0).put("todate", todate);
                jsonArray.getJSONObject(0).put("reasonWiseCount", new JSONObject(reasonWiseCount));
                jsonArray.getJSONObject(0).put("tariffWiseCount", new JSONObject(tariffWiseCount));
                jsonArray.getJSONObject(0).put("genderWiseCount", new JSONObject(genderWiseCount));
                jsonArray.getJSONObject(0).put("patientWiseCount", new JSONObject(patientWiseCount));
                jsonArray.getJSONObject(0).put("ageWiseCount", ageWiseCount);
            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject currentObj = jsonArray.getJSONObject(i);
//                String key = currentObj.get("rDate").toString() + currentObj.get("patient_mr_no").toString() + currentObj.get("unit_name").toString();
//                if (hm.containsKey(key)) {
//                    int index = hm.get(key);
//                    JSONObject previousObj = jsonArray.getJSONObject(index);
//                    float pre_bill_net_payable = Float.parseFloat(previousObj.get("bill_net_payable").toString());
//                    float pre_PaidAmount = Float.parseFloat(previousObj.get("PaidAmount").toString());
//                    float curr_bill_net_payable = Float.parseFloat(currentObj.get("bill_net_payable").toString());
//                    float curr_PaidAmount = Float.parseFloat(currentObj.get("PaidAmount").toString());
//                    previousObj.put("bill_net_payable", (pre_bill_net_payable + curr_bill_net_payable));
//                    previousObj.put("PaidAmount", (pre_PaidAmount + curr_PaidAmount));
//                    jsonArray.put(index, previousObj);
//                    currentObj.put("rDate", "delete");
//                    jsonArray.put(i, currentObj);
//                } else {
//                    hm.put(key, i);
//                }
//            }
//            int count1 = 0;
//            if (LimitFlag == 0) {
/*                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObj = jsonArray.getJSONObject(i);
                    if (count1 == 19) {
                        break;
                    }
                    if (!currentObj.get("rDate").toString().equalsIgnoreCase("delete")) {
                        newArray.put(jsonArray.get(i));
                        count1++;
                    }
                }
            }else{*/
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentObj = jsonArray.getJSONObject(i);
                if (!currentObj.get("rDate").toString().equalsIgnoreCase("delete")) {
                    newArray.put(jsonArray.get(i));
                }
            }
//            JSONArray myJson = new JSONArray();
//            if (LimitFlag == 0) {
//                for (int i = 0; i < 20; i++) {
//                    myJson.put(jsonArray.get(i));
//                    System.out.print("Enter Here");
//                }
//                return ResponseEntity.ok(myJson.toString());
//            }
            //   return ResponseEntity.ok(newArray.toString());
            // return ResponseEntity.ok(jsonArray.toString());
            MstUnit HeaderObject = mstUnitRepository.findByUnitId(opdipdcollectionDetailSearchDTO.getUnitId());
            MstUser HeaderObjectUser = mstUserRepository.findbyUserID(opdipdcollectionDetailSearchDTO.getUserId());
            JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
            JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
            jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
            jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        if (opdipdcollectionDetailSearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("OpdIpdCollectionAuditReport", jsonArray.toString());
        } else {
            return createReport.createJasperReportPDFByJson("OpdIpdCollectionAuditReport", jsonArray.toString());
        }

    }
    // Method For Collection Audit ReportJasper Print End
//    // Method For Service wise bill report start old by rohan
//    @RequestMapping("getServiceWiseBillReport/{unitList}/{serviceId}/{ctId}/{companyId}/{ptId}/{psId}/{fromdate}/{todate}")
//    public ResponseEntity searchServiceWiseBillReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdServiceWiseBillSearchDTO opdipdserviceWiseBillSearchDTO, @PathVariable String[] unitList, @PathVariable String serviceId, @PathVariable String ctId, @PathVariable String companyId, @PathVariable String ptId, @PathVariable String psId, @PathVariable String fromdate, @PathVariable String todate) {
//        long count = 0;
//
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = formatter.format(date);
//        String Query = " ";
//        String Query2 = " ";
//        String MainQuery = " ";
//
//        String CountQuery = "";
//        String columnName = "";
//        Query += " select s.bs_id,t.bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no," +
//                " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname," +
//                " ifnull(u.user_age,'') as user_age , ifnull(g.gender_name,'')as gender_name,ifnull(pt.pt_name,'')as pt_name,ifnull(ps.ps_name,'')as ps_name,ifnull(sc.sc_uhid_no,'')as NMMCuhid," +
//                " ifnull(sc.sc_policy_no,'')as sc_policy_no," +
//                " IF(v.sponsor_combination = 0,'Insurance','Self' ) AS TYPE1," +
//                "  ifnull(ct.ct_name,'') as ct_name,ifnull(com.company_name,'')as company_name,ifnull(s.bs_service_id,'')as bs_service_id,ifnull(ss.service_name, '')as service_name," +
//                " (s.bs_base_rate*s.bs_quantity) as serviceAmount," +
//                " ifnull(s.bs_gross_rate,'')as bs_gross_rate,ifnull(s.bs_discount_amount,'')as bs_discount_amount,ifnull(un.unit_name,'')as unit_name,ifnull(un.unit_id,'')as unit_id ," +
//                " ifnull(sc.sc_company_id,'')as sc_company_id," +
//                " ifnull(sc.sc_ct_id,'')as sc_ct_id,ifnull(v.patient_type,'')AS patient_type,IFNULL(v.visit_ps_id,'')AS visit_ps_id,IFNULL(v.refer_by,'')AS refer_by, " +
//                " ifnull(re.re_name,'')AS ReferEntityName" +
//                " from tbill_bill_service s " +
//                " left join mbill_service ss on s.bs_service_id=ss.service_id " +
//                " left join tbill_bill t on s.bs_bill_id=t.bill_id " +
//                " left join tbill_reciept tr on t.bill_id=tr.br_bill_id " +
//                " left join mst_unit un on un.unit_id=t.tbill_unit_id " +
//                " left join mst_visit v on t.bill_visit_id=v.visit_id " +
//                " left join mst_patient_type pt on v.patient_type=pt.pt_id" +
//                " left join mst_patient_source ps ON v.visit_ps_id=ps.ps_id" +
//                " left join mst_patient p on  v.visit_patient_id=p.patient_id " +
//                " left join mst_user u on p.patient_user_id=u.user_id" +
//                " left join trn_sponsor_combination sc ON u.user_id=sc.sc_user_id" +
//                " left join mst_company com ON sc.sc_company_id=com.company_id" +
//                " left join mst_company_type ct ON sc.sc_ct_id=ct.ct_id " +
//                " left join mst_referring_entity re ON v.refer_by=re.re_id " +
//                " left join mst_gender g on u.user_gender_id=g.gender_id ";
//
//        MainQuery = " SELECT billDetails.bs_id,billDetails.bill_date,billDetails.patient_mr_no,billDetails.patientname,billDetails.user_age,billDetails.gender_name,billDetails.pt_name, " +
//                " billDetails.ps_name,billDetails.NMMCuhid,billDetails.sc_policy_no,billDetails.TYPE1,billDetails.ct_name,billDetails.company_name," +
//                " billDetails.bs_service_id,billDetails.service_name,billDetails.serviceAmount,billDetails.bs_gross_rate ,billDetails.bs_discount_amount, billDetails.unit_name,billDetails.ReferEntityName,billDetails.unit_id ," +
//                " billDetails.sc_company_id, billDetails.sc_ct_id, billDetails.patient_type,billDetails.visit_ps_id,billDetails.refer_by," +
//                " userNameTB.user_name,userNameTB.bill_user_id,CONCAT(ifnull(userNameTB.user_firstname,''),' ',ifnull(userNameTB.user_lastname,''))as user_namefull,doctorStaff.doctorName " +
//                "  " +
//                " from " +
//                " ( " + Query + "  ) as billDetails " +
//                " left join " +
//                " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName " +
//                " from tbill_bill_service s1 " +
//                " left join mst_staff st1 on s1.bs_staff_id=st1.staff_id  " +
//                " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff  on billDetails.bs_id=doctorStaff.bs_id " +
//                " left join (select s.bs_id,st.staff_id,u.user_name,u.user_firstname,u.user_lastname,t.bill_user_id" +
//                " from tbill_bill_service s " +
//                " left join  tbill_bill t on s.bs_bill_id=t.bill_id " +
//                " left join mst_staff st on t.bill_user_id=st.staff_id  " +
//                " left join mst_user u on st.staff_user_id=u.user_id " +
//                " ) " +
//                " as userNameTB " +
//                "  " +
//                " on billDetails.bs_id=userNameTB.bs_id  ";
//        //" where  billDetails.patientName  like '%%'";
//
//        // From Date To Date
//        if (fromdate.equals("") || fromdate.equals("null")) {
//            fromdate = "1990-06-07";
//        }
//        if (todate.equals("") || todate.equals("null")) {
//            todate = strDate;
//        }
//
//        if (opdipdserviceWiseBillSearchDTO.getTodaydate()) {
//            MainQuery += " where (date(billDetails.bill_date)=curdate()) ";
//        } else {
//            MainQuery += " where (date(billDetails.bill_date) between '" + fromdate + "' and '" + todate + "')  ";
//        }
//
//        //Unit Name
//        if (!unitList[0].equals("null")) {
//            String values = String.valueOf(unitList[0]);
//            for (int i = 0; i < unitList.length; i++) {
//                values += "," + unitList[i];
//            }
//            MainQuery += " and billDetails.unit_id in (" + values + ") ";
//        }
//
//        //Mr Number
//        if (!opdipdserviceWiseBillSearchDTO.getMrNo().equals("")) {
//            MainQuery += " and  billDetails.patient_mr_no like '%" + opdipdserviceWiseBillSearchDTO.getMrNo() + "%' ";
//        }
//
//        // Service Name or Code
//        if (!serviceId.equals("null") && !serviceId.equals("0")) {
//            MainQuery += " AND billDetails.bs_service_id=  " + serviceId + " ";
//        }
//
//        // Patient type
//        if (!ptId.equals("null") && !ptId.equals("0")) {
//            MainQuery += " AND billDetails.patient_type=  " + ptId + " ";
//        }
//
//        // Company type
//        if (!ctId.equals("null") && !ctId.equals("0")) {
//            MainQuery += " AND billDetails.sc_ct_id=  " + ctId + " ";
//        }
//
//        // Patient Source
//        if (!psId.equals("null") && !psId.equals("0")) {
//            MainQuery += " and billDetails.visit_ps_id =  " + psId + " ";
//        }
//
//        // Company Name
//        if (!companyId.equals("null") && !companyId.equals("0")) {
//            MainQuery += " AND billDetails.sc_company_id=  " + companyId + " ";
//        }
//
////        if (!String.valueOf(opdipdserviceWiseBillSearchDTO.getRefentityId()).equals("0") && !String.valueOf(opdipdserviceWiseBillSearchDTO.getRefentityTypeId()).equals("null")) {
////            Query += " and rh.rh_referring_entity_type_id=" + opdipdserviceWiseBillSearchDTO.getRefentityTypeId() + " ";
////            CountQuery += " and rh.rh_referring_entity_type_id=" + opdipdserviceWiseBillSearchDTO.getRefentityTypeId() + " ";
////        }
//        if (!String.valueOf(opdipdserviceWiseBillSearchDTO.getRefentityId()).equals("0") && !String.valueOf(opdipdserviceWiseBillSearchDTO.getRefentityId()).equals("null")) {
//            MainQuery += "And billDetails.refer_by=" + opdipdserviceWiseBillSearchDTO.getRefentityId() + " ";
//        }
//
//        if (!String.valueOf(opdipdserviceWiseBillSearchDTO.getStaffId()).equals("0")) {
//            MainQuery += "  and userNameTB.bill_user_id=" + opdipdserviceWiseBillSearchDTO.getStaffId() + " ";
//        }
//
//        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
//        columnName = "bs_id,bill_date,patient_mr_no,patientName,user_age,gender_name,pt_name,ps_name,NMMCuhid,sc_policy_no,TYPE1,ct_name,company_name,bs_service_id,service_name,serviceAmount,bs_gross_rate,bs_discount_amount,unit_name,ReferEntityName,unit_id,sc_company_id, sc_ct_id, patient_type,visit_ps_id,refer_by,user_name,bill_user_id,user_namefull,doctorName";
//        MainQuery += "  limit " + ((opdipdserviceWiseBillSearchDTO.getOffset() - 1) * opdipdserviceWiseBillSearchDTO.getLimit()) + "," + opdipdserviceWiseBillSearchDTO.getLimit();
//
//        //      String MainQuerysub =" select Total.bs_id,Total.bill_date,Total.patient_mr_no,Total.patientname,Total.user_age," +
////                "Total.gender_name,Total.pt_name, Total.ps_name,Total.NMMCuhid,Total.sc_policy_no,Total.TYPE1,Total.ct_name," +
////                "Total.company_name,Total.bs_service_id,Total.service_name,Total.serviceAmount,Total.bs_gross_rate ," +
////                "Total.bs_discount_amount, Total.unit_name,Total.unit_id ,Total.user_name from (" + MainQuery + ") as Total" +
////               " limit " + ((opdipdserviceWiseBillSearchDTO.getOffset() - 1) * opdipdserviceWiseBillSearchDTO.getLimit()) + "," + opdipdserviceWiseBillSearchDTO.getLimit();
//        //        return ResponseEntity.ok(jsonArray.toString());
//        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
//
//    }

    @RequestMapping("getServiceWiseBillReport/{unitList}/{serviceId}/{ctId}/{companyId}/{ptId}/{psId}/{fromdate}/{todate}/{IPDFlag}")
    public ResponseEntity searchServiceWiseBillReport(@RequestHeader("X-tenantId") String tenantName,
                                                      @RequestBody OpdIpdServiceWiseBillSearchDTO opdipdserviceWiseBillSearchDTO, @PathVariable String[] unitList,
                                                      @PathVariable String serviceId, @PathVariable String ctId, @PathVariable String companyId,
                                                      @PathVariable String ptId, @PathVariable String psId, @PathVariable String fromdate,
                                                      @PathVariable String todate, @PathVariable int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String Query2 = " ";
        String MainQuery = " ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query += " select ifnull(t.gross_amount,0) as bill_gross, t.bill_amount_paid,t.company_net_pay,t.bill_net_payable,t.bill_discount_amount,t.bill_id,v.visit_date,p.created_date,s.bs_id,t.bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no,"
                    + " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname,"
                    + " ifnull(u.user_age,'') as user_age , ifnull(g.gender_name,'')as gender_name,ifnull(pt.pt_name,'')as pt_name,ifnull(ps.ps_name,'')as ps_name,ifnull(sc.sc_uhid_no,'')as NMMCuhid,ifnull(sc.sc_policy_no,'')as sc_policy_no,"
                    + " IF(v.sponsor_combination = 0,'Insurance','Self' ) AS TYPE1,"
                    + " ifnull(ct.ct_name,'') as ct_name,ifnull(com.company_name,'')as company_name,ifnull(s.bs_service_id,'')as bs_service_id,ifnull(ss.service_name, '')as service_name,"
                    + " (s.bs_base_rate*s.bs_quantity) as serviceAmount,"
                    + " ifnull(s.bs_gross_rate,'')as bs_gross_rate,ifnull(s.bs_discount_amount,'')as bs_discount_amount,ifnull(un.unit_name,'')as unit_name,ifnull(un.unit_id,'')as unit_id, " +
                    " ifnull(sc.sc_company_id,'')as sc_company_id," +
                    " ifnull(sc.sc_ct_id,'')as sc_ct_id,ifnull(v.patient_type,'')AS patient_type,IFNULL(v.visit_ps_id,'')AS visit_ps_id,IFNULL(v.refer_by,'')AS refer_by, " +
                    " ifnull(re.re_name,'')AS ReferEntityName, t.is_active, t.is_deleted, t.is_cancelled, t.ipd_bill, t.emrbill "
                    + " from tbill_bill_service s " + " " +
                    " left join mbill_service ss on s.bs_service_id=ss.service_id " +
                    " left join tbill_bill t on s.bs_bill_id=t.bill_id " +
                    " left join mst_unit un on un.unit_id=t.tbill_unit_id " +
                    " left join mst_visit v on t.bill_visit_id=v.visit_id " +
                    " left join mst_patient_type pt on v.patient_type=pt.pt_id" +
                    " left join mst_patient_source ps ON v.visit_ps_id=ps.ps_id" +
                    " left join mst_patient p on  v.visit_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join trn_sponsor_combination sc ON u.user_id=sc.sc_user_id" +
                    " left join mst_company com ON sc.sc_company_id=com.company_id" +
                    " left join mst_company_type ct ON sc.sc_ct_id=ct.ct_id " +
                    " left join mst_referring_entity re ON v.refer_by=re.re_id " +
                    " left join mst_gender g on u.user_gender_id=g.gender_id ";
            MainQuery = "SELECT billDetails.bs_id,billDetails.bill_date,billDetails.patient_mr_no,billDetails.patientname,billDetails.user_age,billDetails.gender_name,billDetails.pt_name, "
                    + "billDetails.ps_name,billDetails.NMMCuhid,billDetails.sc_policy_no,billDetails.TYPE1,billDetails.ct_name,billDetails.company_name,"
                    + "billDetails.bs_service_id,billDetails.service_name,billDetails.serviceAmount,billDetails.bs_gross_rate ,billDetails.bs_discount_amount, billDetails.unit_name,billDetails.ReferEntityName,billDetails.unit_id, "
                    + " billDetails.sc_company_id, billDetails.sc_ct_id, billDetails.patient_type,billDetails.visit_ps_id,billDetails.refer_by"
                    + ",userNameTB.user_name,userNameTB.bill_user_id,CONCAT(ifnull(userNameTB.user_firstname,''),' ',ifnull(userNameTB.user_lastname,''))as user_namefull,ifnull(billDetails.visit_date,0)as visit_date,ifnull(billDetails.created_date,0)as created_date,billDetails.bill_id,billDetails.bill_discount_amount," +
                    " billDetails.bill_net_payable,billDetails.company_net_pay,billDetails.bill_amount_paid, billDetails.bill_gross, " +
                    " billDetails.ipd_bill,billDetails.emrbill,billDetails.is_active, billDetails.is_deleted, billDetails.is_cancelled " +
                    " from " + " ( " + Query + "  ) as billDetails " + " left join "
                    + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName "
                    + " from tbill_bill_service s1 " + " left join mst_staff st1 on s1.bs_staff_id=st1.staff_id  "
                    + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff  on billDetails.bs_id=doctorStaff.bs_id "
                    + " left join (select s.bs_id,st.staff_id,u.user_name,u.user_firstname,u.user_lastname,t.bill_user_id " + " from tbill_bill_service s "
                    + " left join  tbill_bill t on s.bs_bill_id=t.bill_id "
                    + " left join mst_staff st on t.bill_user_id=st.staff_id  "
                    + " left join mst_user u on st.staff_user_id=u.user_id " + " ) " + " as userNameTB " + "  "
                    + " on billDetails.bs_id=userNameTB.bs_id  " +
                    " where billDetails.is_active = 1 AND billDetails.is_deleted = 0 AND billDetails.is_cancelled = 0 " +
                    " AND billDetails.emrbill = 0 AND billDetails.ipd_bill = " + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += " select ifnull(t.gross_amount,0) as bill_gross, t.bill_amount_paid,t.company_net_pay,t.bill_net_payable,t.bill_discount_amount,t.bill_id,v.visit_date,p.created_date,s.bs_id,t.bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no,"
                    + " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname,"
                    + " ifnull(u.user_age,'') as user_age , ifnull(g.gender_name,'')as gender_name,ifnull(pt.pt_name,'')as pt_name,ifnull(ps.ps_name,'')as ps_name,ifnull(sc.sc_uhid_no,'')as NMMCuhid,ifnull(sc.sc_policy_no,'')as sc_policy_no,"
                    + " IF(v.sponsor_combination = 0,'Insurance','Self' ) AS TYPE1,"
                    + " ifnull(ct.ct_name,'') as ct_name,ifnull(com.company_name,'')as company_name,ifnull(s.bs_service_id,'')as bs_service_id,ifnull(ss.service_name, '')as service_name,"
                    + " (s.bs_base_rate*s.bs_quantity) as serviceAmount,"
                    + " ifnull(s.bs_gross_rate,'')as bs_gross_rate,ifnull(s.bs_discount_amount,'')as bs_discount_amount,ifnull(un.unit_name,'')as unit_name,ifnull(un.unit_id,'')as unit_id, " +
                    " ifnull(sc.sc_company_id,'')as sc_company_id," +
                    " ifnull(sc.sc_ct_id,'')as sc_ct_id,ifnull(v.patient_type,'')AS patient_type,IFNULL(v.visit_ps_id,'')AS visit_ps_id,IFNULL(v.refer_by,'')AS refer_by, " +
                    " ifnull(re.re_name,'')AS ReferEntityName, t.is_active, t.is_deleted, t.is_cancelled, t.ipd_bill, t.emrbill "
                    + " from tbill_bill_service s " + " " +
                    " left join mbill_service ss on s.bs_service_id=ss.service_id " +
                    " left join tbill_bill t on s.bs_bill_id=t.bill_id " +
                    " left join mst_unit un on un.unit_id=t.tbill_unit_id " +
                    " left join mst_visit v on t.bill_visit_id=v.visit_id " +
                    " left join mst_patient_type pt on v.patient_type=pt.pt_id" +
                    " left join mst_patient_source ps ON v.visit_ps_id=ps.ps_id" +
                    " left join mst_patient p on  v.visit_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join trn_sponsor_combination sc ON u.user_id=sc.sc_user_id" +
                    " left join mst_company com ON sc.sc_company_id=com.company_id" +
                    " left join mst_company_type ct ON sc.sc_ct_id=ct.ct_id " +
                    " left join mst_referring_entity re ON v.refer_by=re.re_id " +
                    " left join mst_gender g on u.user_gender_id=g.gender_id ";
            MainQuery = "SELECT billDetails.bs_id,billDetails.bill_date,billDetails.patient_mr_no,billDetails.patientname,billDetails.user_age,billDetails.gender_name,billDetails.pt_name, "
                    + "billDetails.ps_name,billDetails.NMMCuhid,billDetails.sc_policy_no,billDetails.TYPE1,billDetails.ct_name,billDetails.company_name,"
                    + "billDetails.bs_service_id,billDetails.service_name,billDetails.serviceAmount,billDetails.bs_gross_rate ,billDetails.bs_discount_amount, billDetails.unit_name,billDetails.ReferEntityName,billDetails.unit_id, "
                    + " billDetails.sc_company_id, billDetails.sc_ct_id, billDetails.patient_type,billDetails.visit_ps_id,billDetails.refer_by"
                    + ",userNameTB.user_name,userNameTB.bill_user_id,CONCAT(ifnull(userNameTB.user_firstname,''),' ',ifnull(userNameTB.user_lastname,''))as user_namefull,ifnull(billDetails.visit_date,0)as visit_date,ifnull(billDetails.created_date,0)as created_date,billDetails.bill_id,billDetails.bill_discount_amount," +
                    " billDetails.bill_net_payable,billDetails.company_net_pay,billDetails.bill_amount_paid, billDetails.bill_gross, " +
                    " billDetails.ipd_bill,billDetails.emrbill,billDetails.is_active, billDetails.is_deleted, billDetails.is_cancelled " +
                    " from " + " ( " + Query + "  ) as billDetails " + " left join "
                    + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName "
                    + " from tbill_bill_service s1 " + " left join mst_staff st1 on s1.bs_staff_id=st1.staff_id  "
                    + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff  on billDetails.bs_id=doctorStaff.bs_id "
                    + " left join (select s.bs_id,st.staff_id,u.user_name,u.user_firstname,u.user_lastname,t.bill_user_id " + " from tbill_bill_service s "
                    + " left join  tbill_bill t on s.bs_bill_id=t.bill_id "
                    + " left join mst_staff st on t.bill_user_id=st.staff_id  "
                    + " left join mst_user u on st.staff_user_id=u.user_id " + " ) " + " as userNameTB " + "  "
                    + " on billDetails.bs_id=userNameTB.bs_id  " +
                    " where billDetails.is_active = 1 AND billDetails.is_deleted = 0 AND billDetails.is_cancelled = 0 " +
                    " AND billDetails.emrbill = 0 AND billDetails.ipd_bill = " + IPDFlag;
        } else {
            Query += " select ifnull(t.gross_amount,0) as bill_gross, t.bill_amount_paid,t.company_net_pay,t.bill_net_payable,t.bill_discount_amount,t.bill_id,v.visit_date,p.created_date,s.bs_id,t.bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no,"
                    + " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname,"
                    + " ifnull(u.user_age,'') as user_age , ifnull(g.gender_name,'')as gender_name,ifnull(pt.pt_name,'')as pt_name,ifnull(ps.ps_name,'')as ps_name,ifnull(sc.sc_uhid_no,'')as NMMCuhid,ifnull(sc.sc_policy_no,'')as sc_policy_no,"
                    + " IF(v.sponsor_combination = 0,'Insurance','Self' ) AS TYPE1,"
                    + " ifnull(ct.ct_name,'') as ct_name,ifnull(com.company_name,'')as company_name,ifnull(s.bs_service_id,'')as bs_service_id,ifnull(ss.service_name, '')as service_name,"
                    + " (s.bs_base_rate*s.bs_quantity) as serviceAmount,"
                    + " ifnull(s.bs_gross_rate,'')as bs_gross_rate,ifnull(s.bs_discount_amount,'')as bs_discount_amount,ifnull(un.unit_name,'')as unit_name,ifnull(un.unit_id,'')as unit_id, " +
                    " ifnull(sc.sc_company_id,'')as sc_company_id," +
                    " ifnull(sc.sc_ct_id,'')as sc_ct_id,ifnull(v.patient_type,'')AS patient_type,IFNULL(v.visit_ps_id,'')AS visit_ps_id,IFNULL(v.refer_by,'')AS refer_by, " +
                    " ifnull(re.re_name,'')AS ReferEntityName, t.is_active, t.is_deleted, t.is_cancelled, t.ipd_bill, t.emrbill "
                    + " from tbill_bill_service s " + " " +
                    " left join mbill_service ss on s.bs_service_id=ss.service_id " +
                    " left join tbill_bill t on s.bs_bill_id=t.bill_id " +
                    " left join mst_unit un on un.unit_id=t.tbill_unit_id " +
                    " left join mst_visit v on t.bill_visit_id=v.visit_id " +
                    " left join mst_patient_type pt on v.patient_type=pt.pt_id" +
                    " left join mst_patient_source ps ON v.visit_ps_id=ps.ps_id" +
                    " left join mst_patient p on  v.visit_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join trn_sponsor_combination sc ON u.user_id=sc.sc_user_id" +
                    " left join mst_company com ON sc.sc_company_id=com.company_id" +
                    " left join mst_company_type ct ON sc.sc_ct_id=ct.ct_id " +
                    " left join mst_referring_entity re ON v.refer_by=re.re_id " +
                    " left join mst_gender g on u.user_gender_id=g.gender_id ";
            MainQuery = "SELECT billDetails.bs_id,billDetails.bill_date,billDetails.patient_mr_no,billDetails.patientname,billDetails.user_age,billDetails.gender_name,billDetails.pt_name, "
                    + "billDetails.ps_name,billDetails.NMMCuhid,billDetails.sc_policy_no,billDetails.TYPE1,billDetails.ct_name,billDetails.company_name,"
                    + "billDetails.bs_service_id,billDetails.service_name,billDetails.serviceAmount,billDetails.bs_gross_rate ,billDetails.bs_discount_amount, billDetails.unit_name,billDetails.ReferEntityName,billDetails.unit_id, "
                    + " billDetails.sc_company_id, billDetails.sc_ct_id, billDetails.patient_type,billDetails.visit_ps_id,billDetails.refer_by"
                    + ",userNameTB.user_name,userNameTB.bill_user_id,CONCAT(ifnull(userNameTB.user_firstname,''),' ',ifnull(userNameTB.user_lastname,''))as user_namefull,ifnull(billDetails.visit_date,0)as visit_date,ifnull(billDetails.created_date,0)as created_date,billDetails.bill_id,billDetails.bill_discount_amount," +
                    " billDetails.bill_net_payable,billDetails.company_net_pay,billDetails.bill_amount_paid, billDetails.bill_gross, " +
                    " billDetails.ipd_bill,billDetails.emrbill,billDetails.is_active, billDetails.is_deleted, billDetails.is_cancelled " +
                    " from " + " ( " + Query + "  ) as billDetails " + " left join "
                    + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName "
                    + " from tbill_bill_service s1 " + " left join mst_staff st1 on s1.bs_staff_id=st1.staff_id  "
                    + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff  on billDetails.bs_id=doctorStaff.bs_id "
                    + " left join (select s.bs_id,st.staff_id,u.user_name,u.user_firstname,u.user_lastname,t.bill_user_id " + " from tbill_bill_service s "
                    + " left join  tbill_bill t on s.bs_bill_id=t.bill_id "
                    + " left join mst_staff st on t.bill_user_id=st.staff_id  "
                    + " left join mst_user u on st.staff_user_id=u.user_id " + " ) " + " as userNameTB " + "  "
                    + " on billDetails.bs_id=userNameTB.bs_id  " +
                    " where billDetails.is_active = 1 AND billDetails.is_deleted = 0 AND billDetails.is_cancelled = 0 " +
                    " AND billDetails.emrbill = 1";
        }
        if (opdipdserviceWiseBillSearchDTO.getFromdate().equals("") || opdipdserviceWiseBillSearchDTO.getFromdate().equals("null")) {
            opdipdserviceWiseBillSearchDTO.setFromdate("1990-06-07");
        }
        if (opdipdserviceWiseBillSearchDTO.getTodate().equals("") || opdipdserviceWiseBillSearchDTO.getTodate().equals("null")) {
            opdipdserviceWiseBillSearchDTO.setTodate(strDate);
        }
        if (opdipdserviceWiseBillSearchDTO.getTodaydate()) {
            MainQuery += " and (date(billDetails.bill_date)=curdate()) ";
        } else {
            MainQuery += " and (date(billDetails.bill_date) between '" + opdipdserviceWiseBillSearchDTO.getFromdate() + "' and '" + opdipdserviceWiseBillSearchDTO.getTodate() + "')  ";
        }
        // Unit Name
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 0; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and billDetails.unit_id in (" + values + ") ";
        }
        // Mr Number
        if (!opdipdserviceWiseBillSearchDTO.getMrNo().equals("")) {
            MainQuery += " and  billDetails.patient_mr_no like '%" + opdipdserviceWiseBillSearchDTO.getMrNo() + "%' ";
        }
        // Service Name or Code
        if (!serviceId.equals("null") && !serviceId.equals("0")) {
            MainQuery += " AND billDetails.bs_service_id=  " + serviceId + " ";
        }
        // Patient type
        if (!ptId.equals("null") && !ptId.equals("0")) {
            MainQuery += " AND billDetails.patient_type=  " + ptId + " ";
        }
        // Company type
        if (!ctId.equals("null") && !ctId.equals("0")) {
            MainQuery += " AND billDetails.sc_ct_id=  " + ctId + " ";
        }
        // Patient Source
        if (!psId.equals("null") && !psId.equals("0")) {
            MainQuery += " and billDetails.visit_ps_id =  " + psId + " ";
        }
        // Company Name
        if (!companyId.equals("null") && !companyId.equals("0")) {
            MainQuery += " AND billDetails.sc_company_id=  " + companyId + " ";
        }
        if (!String.valueOf(opdipdserviceWiseBillSearchDTO.getRefentityId()).equals("0") && !String.valueOf(opdipdserviceWiseBillSearchDTO.getRefentityId()).equals("null")) {
            MainQuery += "And billDetails.refer_by=" + opdipdserviceWiseBillSearchDTO.getRefentityId() + " ";
        }
        if (!String.valueOf(opdipdserviceWiseBillSearchDTO.getStaffId()).equals("0")) {
            MainQuery += "  and userNameTB.bill_user_id=" + opdipdserviceWiseBillSearchDTO.getStaffId() + " ";
        }

        System.out.println("Service Wise Report (Self/Company)"+MainQuery);
        CountQuery = MainQuery;
        columnName = "bs_id,bill_date,patient_mr_no,patientName,user_age,gender_name,pt_name,ps_name,NMMCuhid,sc_policy_no,TYPE1,ct_name,company_name,bs_service_id,service_name,serviceAmount,bs_gross_rate,bs_discount_amount,unit_name,ReferEntityName,unit_id,sc_company_id, sc_ct_id, patient_type,visit_ps_id,refer_by,user_name,bill_user_id,user_namefull,visit_date,created_date,bill_id,bill_discount_amount,bill_net_payable,company_net_pay,bill_amount_paid,bill_gross,ipd_bill,emrbill,is_active,is_deleted,is_cancelled";
        MainQuery += "  limit "
                + ((opdipdserviceWiseBillSearchDTO.getOffset() - 1) * opdipdserviceWiseBillSearchDTO.getLimit()) + ","
                + opdipdserviceWiseBillSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.serviceWiseBill(columnName, MainQuery, CountQuery, opdipdserviceWiseBillSearchDTO.getFromdate(), opdipdserviceWiseBillSearchDTO.getTodate()));

    }

    // Method For Service wise bill report start
    @RequestMapping("getServiceWiseBillReportPrint/{unitList}/{serviceId}/{ctId}/{companyId}/{ptId}/{psId}/{fromdate}/{todate}/{IPDFlag}")
    public String searchServiceWiseBillReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdServiceWiseBillSearchDTO opdipdserviceWiseBillSearchDTO, @PathVariable String[] unitList, @PathVariable String serviceId, @PathVariable String ctId, @PathVariable String companyId, @PathVariable String ptId, @PathVariable String psId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) throws JsonProcessingException {
//        long count = 0;
//
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = formatter.format(date);
//        String Query = " ";
//        String Query2 = " ";
//        String MainQuery = " ";
//
//        String CountQuery = "";
//        String columnName = "";
//        Query += " select s.bs_id,t.bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no," +
//                " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname," +
//                " ifnull(u.user_age,'') as user_age , ifnull(g.gender_name,'')as gender_name,ifnull(pt.pt_name,'')as pt_name,ifnull(ps.ps_name,'')as ps_name,ifnull(sc.sc_uhid_no,'')as NMMCuhid,ifnull(sc.sc_policy_no,'')as sc_policy_no," +
//                " IF(v.sponsor_combination = 0,'Insurance','Self' ) AS TYPE1," +
//                "  ifnull(ct.ct_name,'') as ct_name,ifnull(com.company_name,'')as company_name,ifnull(s.bs_service_id,'')as bs_service_id,ifnull(ss.service_name, '')as service_name," +
//                " (s.bs_base_rate*s.bs_quantity) as serviceAmount," +
//                " ifnull(s.bs_gross_rate,'')as bs_gross_rate,ifnull(s.bs_discount_amount,'')as bs_discount_amount,ifnull(un.unit_name,'')as unit_name,ifnull(un.unit_id,'')as unit_id ,ifnull(re.re_name,'')AS ReferEntityName" +
//                " from tbill_bill_service s " +
//                " left join mbill_service ss on s.bs_service_id=ss.service_id " +
//                " left join tbill_bill t on s.bs_bill_id=t.bill_id " +
//                " left join tbill_reciept tr on t.bill_id=tr.br_bill_id " +
//                " left join mst_unit un on un.unit_id=t.tbill_unit_id " +
//                " left join mst_visit v on t.bill_visit_id=v.visit_id " +
//                " left join mst_patient_type pt on v.patient_type=pt.pt_id" +
//                " left join mst_patient_source ps ON v.visit_ps_id=ps.ps_id" +
//                " left join mst_patient p on  v.visit_patient_id=p.patient_id " +
//                " left join mst_user u on p.patient_user_id=u.user_id" +
//                " left join trn_sponsor_combination sc ON u.user_id=sc.sc_user_id" +
//                " left join mst_company com ON sc.sc_company_id=com.company_id" +
//                " left join mst_company_type ct ON sc.sc_ct_id=ct.ct_id " +
//                " left join mst_referring_entity re ON v.refer_by=re.re_id " +
//                " left join mst_gender g on u.user_gender_id=g.gender_id ";
//
//        MainQuery = "SELECT billDetails.bs_id,billDetails.bill_date,billDetails.patient_mr_no,billDetails.patientname,billDetails.user_age,billDetails.gender_name,billDetails.pt_name, " +
//                "billDetails.ps_name,billDetails.NMMCuhid,billDetails.sc_policy_no,billDetails.TYPE1,billDetails.ct_name,billDetails.company_name," +
//                "billDetails.bs_service_id,billDetails.service_name,billDetails.serviceAmount,billDetails.bs_gross_rate ,billDetails.bs_discount_amount, billDetails.unit_name,billDetails.ReferEntityName,billDetails.unit_id " +
//                ",userNameTB.user_name,doctorStaff.doctorName " +
//                "  " +
//                " from " +
//                " ( " + Query + "  ) as billDetails " +
//                " left join " +
//                " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName " +
//                " from tbill_bill_service s1 " +
//                " left join mst_staff st1 on s1.bs_staff_id=st1.staff_id  " +
//                " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff  on billDetails.bs_id=doctorStaff.bs_id " +
//                " left join (select s.bs_id,st.staff_id,u.user_name " +
//                " from tbill_bill_service s " +
//                " left join  tbill_bill t on s.bs_bill_id=t.bill_id " +
//                " left join mst_staff st on t.bill_user_id=st.staff_id  " +
//                " left join mst_user u on st.staff_user_id=u.user_id " +
//                " ) " +
//                " as userNameTB " +
//                "  " +
//                " on billDetails.bs_id=userNameTB.bs_id  ";
//        //" where  billDetails.patientName  like '%%'";
//
//        // From Date To Date
//        if (fromdate.equals("") || fromdate.equals("null")) {
//            fromdate = "1990-06-07";
//        }
//        if (todate.equals("") || todate.equals("null")) {
//            todate = strDate;
//        }
//
//        if (opdipdserviceWiseBillSearchDTO.getTodaydate()) {
//            MainQuery += " where (date(billDetails.bill_date)=curdate()) ";
//        } else {
//            MainQuery += " where (date(billDetails.bill_date) between '" + fromdate + "' and '" + todate + "')  ";
//        }
//
//        //Unit Name
//        if (!unitList[0].equals("null")) {
//            String values = String.valueOf(unitList[0]);
//            for (int i = 0; i < unitList.length; i++) {
//                values += "," + unitList[i];
//            }
//            MainQuery += " and billDetails.unit_id in (" + values + ") ";
//        }
//
//        //Mr Number
//        if (!opdipdserviceWiseBillSearchDTO.getMrNo().equals("")) {
//            MainQuery += " and  billDetails.patient_mr_no like '%" + opdipdserviceWiseBillSearchDTO.getMrNo() + "%' ";
//        }
//
//        // Service Name or Code
//        if (!serviceId.equals("null") && !serviceId.equals("0")) {
//            MainQuery += " AND billDetails.bs_service_id=  " + serviceId + " ";
//        }
//
//        // Patient type
//        if (!ptId.equals("null") && !ptId.equals("0")) {
//            MainQuery += " AND billDetails.ppt_id=  " + ptId + " ";
//        }
//
//        // Company type
//        if (!ctId.equals("null") && !ctId.equals("0")) {
//            MainQuery += " AND billDetails.sct_id=  " + ctId + " ";
//        }
//
//        // Patient Source
//        if (!psId.equals("null") && !psId.equals("0")) {
//            MainQuery += " and billDetails.pps_id =  " + psId + " ";
//        }
//
//        // Company Name
//        if (!companyId.equals("null") && !companyId.equals("0")) {
//            MainQuery += " AND billDetails.scom_id=  " + companyId + " ";
//        }
//
//        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
//        columnName = "bs_id,bill_date,patient_mr_no,patientName,user_age,gender_name,pt_name,ps_name,NMMCuhid,sc_policy_no,TYPE1,ct_name,company_name,bs_service_id,service_name,serviceAmount,bs_gross_rate,bs_discount_amount,unit_name,ReferEntityName,unit_id,user_name,doctorName";
        // MainQuery += "  limit " + ((opdipdserviceWiseBillSearchDTO.getOffset() - 1) * opdipdserviceWiseBillSearchDTO.getLimit()) + "," + opdipdserviceWiseBillSearchDTO.getLimit();
//      String MainQuerysub =" select Total.bs_id,Total.bill_date,Total.patient_mr_no,Total.patientname,Total.user_age," +
//                "Total.gender_name,Total.pt_name, Total.ps_name,Total.NMMCuhid,Total.sc_policy_no,Total.TYPE1,Total.ct_name," +
//                "Total.company_name,Total.bs_service_id,Total.service_name,Total.serviceAmount,Total.bs_gross_rate ," +
//                "Total.bs_discount_amount, Total.unit_name,Total.unit_id ,Total.user_name from (" + MainQuery + ") as Total" +
//               " limit " + ((opdipdserviceWiseBillSearchDTO.getOffset() - 1) * opdipdserviceWiseBillSearchDTO.getLimit()) + "," + opdipdserviceWiseBillSearchDTO.getLimit();
        //        return ResponseEntity.ok(jsonArray.toString());
        // return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String Query2 = " ";
        String MainQuery = " ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query += " select ifnull(t.gross_amount,0) as bill_gross, t.bill_amount_paid,t.company_net_pay,t.bill_net_payable,t.bill_discount_amount,t.bill_id,v.visit_date,p.created_date,s.bs_id,t.bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no,"
                    + " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname,"
                    + " ifnull(u.user_age,'') as user_age , ifnull(g.gender_name,'')as gender_name,ifnull(pt.pt_name,'')as pt_name,ifnull(ps.ps_name,'')as ps_name,ifnull(sc.sc_uhid_no,'')as NMMCuhid,ifnull(sc.sc_policy_no,'')as sc_policy_no,"
                    + " IF(v.sponsor_combination = 0,'Insurance','Self' ) AS TYPE1,"
                    + " ifnull(ct.ct_name,'') as ct_name,ifnull(com.company_name,'')as company_name,ifnull(s.bs_service_id,'')as bs_service_id,ifnull(ss.service_name, '')as service_name,"
                    + " (s.bs_base_rate*s.bs_quantity) as serviceAmount,"
                    + " ifnull(s.bs_gross_rate,'')as bs_gross_rate,ifnull(s.bs_discount_amount,'')as bs_discount_amount,ifnull(un.unit_name,'')as unit_name,ifnull(un.unit_id,'')as unit_id, " +
                    " ifnull(sc.sc_company_id,'')as sc_company_id," +
                    " ifnull(sc.sc_ct_id,'')as sc_ct_id,ifnull(v.patient_type,'')AS patient_type,IFNULL(v.visit_ps_id,'')AS visit_ps_id,IFNULL(v.refer_by,'')AS refer_by, " +
                    " ifnull(re.re_name,'')AS ReferEntityName, t.is_active, t.is_deleted, t.is_cancelled, t.ipd_bill, t.emrbill "
                    + " from tbill_bill_service s " + " " +
                    " left join mbill_service ss on s.bs_service_id=ss.service_id " +
                    " left join tbill_bill t on s.bs_bill_id=t.bill_id " +
                    " left join mst_unit un on un.unit_id=t.tbill_unit_id " +
                    " left join mst_visit v on t.bill_visit_id=v.visit_id " +
                    " left join mst_patient_type pt on v.patient_type=pt.pt_id" +
                    " left join mst_patient_source ps ON v.visit_ps_id=ps.ps_id" +
                    " left join mst_patient p on  v.visit_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join trn_sponsor_combination sc ON u.user_id=sc.sc_user_id" +
                    " left join mst_company com ON sc.sc_company_id=com.company_id" +
                    " left join mst_company_type ct ON sc.sc_ct_id=ct.ct_id " +
                    " left join mst_referring_entity re ON v.refer_by=re.re_id " +
                    " left join mst_gender g on u.user_gender_id=g.gender_id ";
            MainQuery = "SELECT billDetails.bs_id,billDetails.bill_date,billDetails.patient_mr_no,billDetails.patientname,billDetails.user_age,billDetails.gender_name,billDetails.pt_name, "
                    + "billDetails.ps_name,billDetails.NMMCuhid,billDetails.sc_policy_no,billDetails.TYPE1,billDetails.ct_name,billDetails.company_name,"
                    + "billDetails.bs_service_id,billDetails.service_name,billDetails.serviceAmount,billDetails.bs_gross_rate ,billDetails.bs_discount_amount, billDetails.unit_name,billDetails.ReferEntityName,billDetails.unit_id, "
                    + " billDetails.sc_company_id, billDetails.sc_ct_id, billDetails.patient_type,billDetails.visit_ps_id,billDetails.refer_by"
                    + ",userNameTB.user_name,userNameTB.bill_user_id,CONCAT(ifnull(userNameTB.user_firstname,''),' ',ifnull(userNameTB.user_lastname,''))as user_namefull,ifnull(billDetails.visit_date,0)as visit_date,ifnull(billDetails.created_date,0)as created_date,billDetails.bill_id,billDetails.bill_discount_amount," +
                    " billDetails.bill_net_payable,billDetails.company_net_pay,billDetails.bill_amount_paid, billDetails.bill_gross, " +
                    " billDetails.ipd_bill,billDetails.emrbill,billDetails.is_active, billDetails.is_deleted, billDetails.is_cancelled " +
                    " from " + " ( " + Query + "  ) as billDetails " + " left join "
                    + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName "
                    + " from tbill_bill_service s1 " + " left join mst_staff st1 on s1.bs_staff_id=st1.staff_id  "
                    + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff  on billDetails.bs_id=doctorStaff.bs_id "
                    + " left join (select s.bs_id,st.staff_id,u.user_name,u.user_firstname,u.user_lastname,t.bill_user_id " + " from tbill_bill_service s "
                    + " left join  tbill_bill t on s.bs_bill_id=t.bill_id "
                    + " left join mst_staff st on t.bill_user_id=st.staff_id  "
                    + " left join mst_user u on st.staff_user_id=u.user_id " + " ) " + " as userNameTB " + "  "
                    + " on billDetails.bs_id=userNameTB.bs_id  " +
                    " where billDetails.is_active = 1 AND billDetails.is_deleted = 0 AND billDetails.is_cancelled = 0 " +
                    " AND billDetails.emrbill = 0 AND billDetails.ipd_bill = " + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += " select ifnull(t.gross_amount,0) as bill_gross, t.bill_amount_paid,t.company_net_pay,t.bill_net_payable,t.bill_discount_amount,t.bill_id,v.visit_date,p.created_date,s.bs_id,t.bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no,"
                    + " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname,"
                    + " ifnull(u.user_age,'') as user_age , ifnull(g.gender_name,'')as gender_name,ifnull(pt.pt_name,'')as pt_name,ifnull(ps.ps_name,'')as ps_name,ifnull(sc.sc_uhid_no,'')as NMMCuhid,ifnull(sc.sc_policy_no,'')as sc_policy_no,"
                    + " IF(v.sponsor_combination = 0,'Insurance','Self' ) AS TYPE1,"
                    + " ifnull(ct.ct_name,'') as ct_name,ifnull(com.company_name,'')as company_name,ifnull(s.bs_service_id,'')as bs_service_id,ifnull(ss.service_name, '')as service_name,"
                    + " (s.bs_base_rate*s.bs_quantity) as serviceAmount,"
                    + " ifnull(s.bs_gross_rate,'')as bs_gross_rate,ifnull(s.bs_discount_amount,'')as bs_discount_amount,ifnull(un.unit_name,'')as unit_name,ifnull(un.unit_id,'')as unit_id, " +
                    " ifnull(sc.sc_company_id,'')as sc_company_id," +
                    " ifnull(sc.sc_ct_id,'')as sc_ct_id,ifnull(v.patient_type,'')AS patient_type,IFNULL(v.visit_ps_id,'')AS visit_ps_id,IFNULL(v.refer_by,'')AS refer_by, " +
                    " ifnull(re.re_name,'')AS ReferEntityName, t.is_active, t.is_deleted, t.is_cancelled, t.ipd_bill, t.emrbill "
                    + " from tbill_bill_service s " + " " +
                    " left join mbill_service ss on s.bs_service_id=ss.service_id " +
                    " left join tbill_bill t on s.bs_bill_id=t.bill_id " +
                    " left join mst_unit un on un.unit_id=t.tbill_unit_id " +
                    " left join mst_visit v on t.bill_visit_id=v.visit_id " +
                    " left join mst_patient_type pt on v.patient_type=pt.pt_id" +
                    " left join mst_patient_source ps ON v.visit_ps_id=ps.ps_id" +
                    " left join mst_patient p on  v.visit_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join trn_sponsor_combination sc ON u.user_id=sc.sc_user_id" +
                    " left join mst_company com ON sc.sc_company_id=com.company_id" +
                    " left join mst_company_type ct ON sc.sc_ct_id=ct.ct_id " +
                    " left join mst_referring_entity re ON v.refer_by=re.re_id " +
                    " left join mst_gender g on u.user_gender_id=g.gender_id ";
            MainQuery = "SELECT billDetails.bs_id,billDetails.bill_date,billDetails.patient_mr_no,billDetails.patientname,billDetails.user_age,billDetails.gender_name,billDetails.pt_name, "
                    + "billDetails.ps_name,billDetails.NMMCuhid,billDetails.sc_policy_no,billDetails.TYPE1,billDetails.ct_name,billDetails.company_name,"
                    + "billDetails.bs_service_id,billDetails.service_name,billDetails.serviceAmount,billDetails.bs_gross_rate ,billDetails.bs_discount_amount, billDetails.unit_name,billDetails.ReferEntityName,billDetails.unit_id, "
                    + " billDetails.sc_company_id, billDetails.sc_ct_id, billDetails.patient_type,billDetails.visit_ps_id,billDetails.refer_by"
                    + ",userNameTB.user_name,userNameTB.bill_user_id,CONCAT(ifnull(userNameTB.user_firstname,''),' ',ifnull(userNameTB.user_lastname,''))as user_namefull,ifnull(billDetails.visit_date,0)as visit_date,ifnull(billDetails.created_date,0)as created_date,billDetails.bill_id,billDetails.bill_discount_amount," +
                    " billDetails.bill_net_payable,billDetails.company_net_pay,billDetails.bill_amount_paid, billDetails.bill_gross, " +
                    " billDetails.ipd_bill,billDetails.emrbill,billDetails.is_active, billDetails.is_deleted, billDetails.is_cancelled " +
                    " from " + " ( " + Query + "  ) as billDetails " + " left join "
                    + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName "
                    + " from tbill_bill_service s1 " + " left join mst_staff st1 on s1.bs_staff_id=st1.staff_id  "
                    + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff  on billDetails.bs_id=doctorStaff.bs_id "
                    + " left join (select s.bs_id,st.staff_id,u.user_name,u.user_firstname,u.user_lastname,t.bill_user_id " + " from tbill_bill_service s "
                    + " left join  tbill_bill t on s.bs_bill_id=t.bill_id "
                    + " left join mst_staff st on t.bill_user_id=st.staff_id  "
                    + " left join mst_user u on st.staff_user_id=u.user_id " + " ) " + " as userNameTB " + "  "
                    + " on billDetails.bs_id=userNameTB.bs_id  " +
                    " where billDetails.is_active = 1 AND billDetails.is_deleted = 0 AND billDetails.is_cancelled = 0 " +
                    " AND billDetails.emrbill = 0 AND billDetails.ipd_bill = " + IPDFlag;
        } else {
            Query += " select ifnull(t.gross_amount,0) as bill_gross, t.bill_amount_paid,t.company_net_pay,t.bill_net_payable,t.bill_discount_amount,t.bill_id,v.visit_date,p.created_date,s.bs_id,t.bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no,"
                    + " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname,"
                    + " ifnull(u.user_age,'') as user_age , ifnull(g.gender_name,'')as gender_name,ifnull(pt.pt_name,'')as pt_name,ifnull(ps.ps_name,'')as ps_name,ifnull(sc.sc_uhid_no,'')as NMMCuhid,ifnull(sc.sc_policy_no,'')as sc_policy_no,"
                    + " IF(v.sponsor_combination = 0,'Insurance','Self' ) AS TYPE1,"
                    + " ifnull(ct.ct_name,'') as ct_name,ifnull(com.company_name,'')as company_name,ifnull(s.bs_service_id,'')as bs_service_id,ifnull(ss.service_name, '')as service_name,"
                    + " (s.bs_base_rate*s.bs_quantity) as serviceAmount,"
                    + " ifnull(s.bs_gross_rate,'')as bs_gross_rate,ifnull(s.bs_discount_amount,'')as bs_discount_amount,ifnull(un.unit_name,'')as unit_name,ifnull(un.unit_id,'')as unit_id, " +
                    " ifnull(sc.sc_company_id,'')as sc_company_id," +
                    " ifnull(sc.sc_ct_id,'')as sc_ct_id,ifnull(v.patient_type,'')AS patient_type,IFNULL(v.visit_ps_id,'')AS visit_ps_id,IFNULL(v.refer_by,'')AS refer_by, " +
                    " ifnull(re.re_name,'')AS ReferEntityName, t.is_active, t.is_deleted, t.is_cancelled, t.ipd_bill, t.emrbill "
                    + " from tbill_bill_service s " + " " +
                    " left join mbill_service ss on s.bs_service_id=ss.service_id " +
                    " left join tbill_bill t on s.bs_bill_id=t.bill_id " +
                    " left join mst_unit un on un.unit_id=t.tbill_unit_id " +
                    " left join mst_visit v on t.bill_visit_id=v.visit_id " +
                    " left join mst_patient_type pt on v.patient_type=pt.pt_id" +
                    " left join mst_patient_source ps ON v.visit_ps_id=ps.ps_id" +
                    " left join mst_patient p on  v.visit_patient_id=p.patient_id " +
                    " left join mst_user u on p.patient_user_id=u.user_id" +
                    " left join trn_sponsor_combination sc ON u.user_id=sc.sc_user_id" +
                    " left join mst_company com ON sc.sc_company_id=com.company_id" +
                    " left join mst_company_type ct ON sc.sc_ct_id=ct.ct_id " +
                    " left join mst_referring_entity re ON v.refer_by=re.re_id " +
                    " left join mst_gender g on u.user_gender_id=g.gender_id ";
            MainQuery = "SELECT billDetails.bs_id,billDetails.bill_date,billDetails.patient_mr_no,billDetails.patientname,billDetails.user_age,billDetails.gender_name,billDetails.pt_name, "
                    + "billDetails.ps_name,billDetails.NMMCuhid,billDetails.sc_policy_no,billDetails.TYPE1,billDetails.ct_name,billDetails.company_name,"
                    + "billDetails.bs_service_id,billDetails.service_name,billDetails.serviceAmount,billDetails.bs_gross_rate ,billDetails.bs_discount_amount, billDetails.unit_name,billDetails.ReferEntityName,billDetails.unit_id, "
                    + " billDetails.sc_company_id, billDetails.sc_ct_id, billDetails.patient_type,billDetails.visit_ps_id,billDetails.refer_by"
                    + ",userNameTB.user_name,userNameTB.bill_user_id,CONCAT(ifnull(userNameTB.user_firstname,''),' ',ifnull(userNameTB.user_lastname,''))as user_namefull,ifnull(billDetails.visit_date,0)as visit_date,ifnull(billDetails.created_date,0)as created_date,billDetails.bill_id,billDetails.bill_discount_amount," +
                    " billDetails.bill_net_payable,billDetails.company_net_pay,billDetails.bill_amount_paid, billDetails.bill_gross, " +
                    " billDetails.ipd_bill,billDetails.emrbill,billDetails.is_active, billDetails.is_deleted, billDetails.is_cancelled " +
                    " from " + " ( " + Query + "  ) as billDetails " + " left join "
                    + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName "
                    + " from tbill_bill_service s1 " + " left join mst_staff st1 on s1.bs_staff_id=st1.staff_id  "
                    + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff  on billDetails.bs_id=doctorStaff.bs_id "
                    + " left join (select s.bs_id,st.staff_id,u.user_name,u.user_firstname,u.user_lastname,t.bill_user_id " + " from tbill_bill_service s "
                    + " left join  tbill_bill t on s.bs_bill_id=t.bill_id "
                    + " left join mst_staff st on t.bill_user_id=st.staff_id  "
                    + " left join mst_user u on st.staff_user_id=u.user_id " + " ) " + " as userNameTB " + "  "
                    + " on billDetails.bs_id=userNameTB.bs_id  " +
                    " where billDetails.is_active = 1 AND billDetails.is_deleted = 0 AND billDetails.is_cancelled = 0 " +
                    " AND billDetails.emrbill = 1";
        }
        if (fromdate.equals("") || fromdate.equals("null") || fromdate.equals("undefined")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null") || todate.equals("undefined")) {
            todate = strDate;
        }
        if (opdipdserviceWiseBillSearchDTO.getTodaydate()) {
            MainQuery += " and (date(billDetails.bill_date)=curdate()) ";
        } else {
            MainQuery += " and (date(billDetails.bill_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        // Unit Name
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 0; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and billDetails.unit_id in (" + values + ") ";
        }
        // Mr Number
        if (!opdipdserviceWiseBillSearchDTO.getMrNo().equals("")) {
            MainQuery += " and  billDetails.patient_mr_no like '%" + opdipdserviceWiseBillSearchDTO.getMrNo() + "%' ";
        }
        // Service Name or Code
        if (!serviceId.equals("null") && !serviceId.equals("0")) {
            MainQuery += " AND billDetails.bs_service_id=  " + serviceId + " ";
        }
        // Patient type
        if (!ptId.equals("null") && !ptId.equals("0")) {
            MainQuery += " AND billDetails.patient_type=  " + ptId + " ";
        }
        // Company type
        if (!ctId.equals("null") && !ctId.equals("0")) {
            MainQuery += " AND billDetails.sc_ct_id=  " + ctId + " ";
        }
        // Patient Source
        if (!psId.equals("null") && !psId.equals("0")) {
            MainQuery += " and billDetails.visit_ps_id =  " + psId + " ";
        }
        // Company Name
        if (!companyId.equals("null") && !companyId.equals("0")) {
            MainQuery += " AND billDetails.sc_company_id=  " + companyId + " ";
        }
        if (!String.valueOf(opdipdserviceWiseBillSearchDTO.getRefentityId()).equals("0") && !String.valueOf(opdipdserviceWiseBillSearchDTO.getRefentityId()).equals("null")) {
            MainQuery += "And billDetails.refer_by=" + opdipdserviceWiseBillSearchDTO.getRefentityId() + " ";
        }
        if (!String.valueOf(opdipdserviceWiseBillSearchDTO.getStaffId()).equals("0")) {
            MainQuery += "  and userNameTB.bill_user_id=" + opdipdserviceWiseBillSearchDTO.getStaffId() + " ";
        }
        CountQuery = MainQuery;
        columnName = "bs_id,bill_date,patient_mr_no,patientName,user_age,gender_name,pt_name,ps_name,NMMCuhid,sc_policy_no,TYPE1,ct_name,company_name,bs_service_id,service_name,serviceAmount,bs_gross_rate,bs_discount_amount,unit_name,ReferEntityName,unit_id,sc_company_id, sc_ct_id, patient_type,visit_ps_id,refer_by,user_name,bill_user_id,user_namefull,visit_date,created_date,bill_id,bill_discount_amount,bill_net_payable,company_net_pay,bill_amount_paid,bill_gross,ipd_bill,emrbill,is_active,is_deleted,is_cancelled";
        JSONArray jsonArray = new JSONArray(createJSONObject.serviceWiseBill(columnName, MainQuery, CountQuery, fromdate, todate));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(opdipdserviceWiseBillSearchDTO.getUnitId());
        String col1 = HeaderObject.getUnitName() + " " + HeaderObject.getUnitpostfix();
        String col2 = HeaderObject.getUnitcenter() + " " + HeaderObject.getUnitName();
        String col3 = HeaderObject.getUnitAddress() + " " + HeaderObject.getUnitCityId().getCityName() + " "
                + HeaderObject.getUnitCityId().getCityStateId().getStateName() + " "
                + HeaderObject.getUnitCityId().getCityStateId().getStateCountryId().getCountryName();
        String col4 = "Current Date : " + strDate;
        String[][] data = {{col1, col2, col3, col4}};
        if (opdipdserviceWiseBillSearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson1("service_report", jsonArray, data);
        } else {
            return createReport.createJasperReportPDFByJson1("service_report", jsonArray, data);
        }
    }

    // Method For Tally Start
    @RequestMapping("opdCollectionReportForTally/{report_date}")
    public String opdDailyCollectionReport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("report_date") String report_date) {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject object = new JSONObject();
        HashMap<Integer, Double> netPaybaleAmount = new HashMap<>();
        Double netPayAmount = 0.0;
        Double advance = 0.0;
        Double card = 0.0;
        Double credit = 0.0;
        Double cash = 0.0;
        Double cheque = 0.0;
        String query = "SELECT bill_id,bill_date,bill_advance,bill_number,bill_sub_total,br_payment_amount,pm_name FROM tbill_bill tb INNER JOIN tbill_reciept INNER JOIN mst_payment_mode WHERE br_bill_id=bill_id AND br_pm_id=pm_id AND bill_date='"
                + report_date + "' and ipd_bill=0 and tb.is_active=1 ORDER BY bill_id desc";
        List<Object[]> bill_list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        for (Object[] objects : bill_list) {
            if (!netPaybaleAmount.containsKey(Integer.parseInt(objects[0].toString()))) {
                netPaybaleAmount.put(Integer.parseInt(objects[0].toString()),
                        Double.parseDouble(objects[4].toString()));
                netPayAmount = netPayAmount + Double.parseDouble(objects[4].toString());
            }
            if (objects[6].toString().trim().equalsIgnoreCase("Advance")) {
                advance = advance + Double.parseDouble(objects[5].toString());
            }
            if (objects[6].toString().trim().equalsIgnoreCase("Card")) {
                card = card + Double.parseDouble(objects[5].toString());
            }
            if (objects[6].toString().trim().equalsIgnoreCase("Credit")) {
                credit = credit + Double.parseDouble(objects[5].toString());
            }
            if (objects[6].toString().trim().equalsIgnoreCase("Cash")) {
                cash = cash + Double.parseDouble(objects[5].toString());
            }
            if (objects[6].toString().trim().equalsIgnoreCase("Cheque")) {
                cheque = cheque + Double.parseDouble(objects[5].toString());
            }
        }
        String refund_query = "SELECT sum(tbr_total_amount) FROM tbill_bill_refund tbr inner join tbill_bill tbill_bill WHERE tbill_bill.bill_id=tbr.tbr_bill_id and tbill_bill.ipd_bill=0 and tbill_bill.is_active=1 and tbr.created_date between '"
                + report_date + " 00:00:00' AND '" + report_date + " 23:59:59';";
        Object refund_amount = entityManager.createNativeQuery(refund_query).getSingleResult();
        try {
            object.put("netpayamount", netPayAmount);
            object.put("advance", advance);
            object.put("card", card);
            object.put("credit", credit);
            object.put("cash", cash);
            object.put("cheque", cheque);
            if (refund_amount == null) {
                object.put("refund_amount", "0.0");
            } else {
                object.put("refund_amount", refund_amount.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
    // Method For Tally End

    // Method for generate xml Start
    @RequestMapping("/generate_xml")
    public String generate_xml(@RequestHeader("X-tenantId") String tenantName, @RequestBody String generate_xml) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            JSONObject object = new JSONObject(generate_xml);
            Double cashAmount = Double.parseDouble(object.get("advance").toString())
                    + Double.parseDouble(object.get("cheque").toString())
                    + Double.parseDouble(object.get("card").toString())
                    + Double.parseDouble(object.get("cash").toString())
                    - Double.parseDouble(object.get("refund_amount").toString());
            String date = object.get("date").toString().replaceAll("-", "");
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ENVELOPE>" + "  <HEADER>"
                    + "    <TALLYREQUEST>Import Data</TALLYREQUEST>" + "  </HEADER>" + "  <BODY>"
                    + "    <IMPORTDATA>" + "      <REQUESTDESC>" + "        <REPORTNAME>Vouchers</REPORTNAME>"
                    + "        <STATICVARIABLES>" + "          <SVCURRENTCOMPANY>" + tallyCompanyName
                    + "</SVCURRENTCOMPANY>" + "        </STATICVARIABLES>" + "      </REQUESTDESC>"
                    + "      <REQUESTDATA>" + "        <TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
                    + "          <VOUCHER REMOTEID=\"" + date + "\">" + "            <ISOPTIONAL>No</ISOPTIONAL>"
                    + "            <ISINVOICE>No</ISINVOICE>"
                    + "            <VOUCHERTYPENAME>Receipt</VOUCHERTYPENAME>" + "            <DATE>" + date
                    + "</DATE>" + "            <EFFECTIVEDATE>" + date + "</EFFECTIVEDATE>"
                    + "            <ISCANCELLED>No</ISCANCELLED>" + "            <VOUCHERNUMBER>5</VOUCHERNUMBER>"
                    + "            <PARTYLEDGERNAME>Cash</PARTYLEDGERNAME>" + "            <ALLLEDGERENTRIES.LIST>"
                    + "              <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
                    + "              <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
                    + "              <LEDGERFROMITEM>No</LEDGERFROMITEM>"
                    + "              <LEDGERNAME>Advanced</LEDGERNAME>" + "              <AMOUNT>"
                    + object.get("advance") + "</AMOUNT>" + "            </ALLLEDGERENTRIES.LIST>"
                    + "            <ALLLEDGERENTRIES.LIST>"
                    + "              <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
                    + "              <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
                    + "              <LEDGERFROMITEM>No</LEDGERFROMITEM>"
                    + "              <LEDGERNAME>Cheque</LEDGERNAME>" + "              <AMOUNT>"
                    + object.get("cheque") + "</AMOUNT>" + "            </ALLLEDGERENTRIES.LIST>"
                    + "            <ALLLEDGERENTRIES.LIST>"
                    + "              <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
                    + "              <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
                    + "              <LEDGERFROMITEM>No</LEDGERFROMITEM>"
                    + "              <LEDGERNAME>Card</LEDGERNAME>" + "              <AMOUNT>" + object.get("card")
                    + "</AMOUNT>" + "            </ALLLEDGERENTRIES.LIST>" + "            <ALLLEDGERENTRIES.LIST>"
                    + "              <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
                    + "              <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
                    + "              <LEDGERFROMITEM>No</LEDGERFROMITEM>"
                    + "              <LEDGERNAME>Cash Amount</LEDGERNAME>" + "              <AMOUNT>"
                    + object.get("cash") + "</AMOUNT>" + "            </ALLLEDGERENTRIES.LIST>"
                    + "            <ALLLEDGERENTRIES.LIST>"
                    + "              <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
                    + "              <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
                    + "              <LEDGERFROMITEM>No</LEDGERFROMITEM>"
                    + "              <LEDGERNAME>Refund</LEDGERNAME>" + "              <AMOUNT>-"
                    + object.get("refund_amount") + "</AMOUNT>" + "            </ALLLEDGERENTRIES.LIST>"
                    + "            <ALLLEDGERENTRIES.LIST>"
                    + "              <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
                    + "              <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
                    + "              <LEDGERFROMITEM>No</LEDGERFROMITEM>"
                    + "              <LEDGERNAME>Cash</LEDGERNAME>" + "              <AMOUNT>-" + cashAmount
                    + "</AMOUNT>" + "            </ALLLEDGERENTRIES.LIST>" + "          </VOUCHER>"
                    + "        </TALLYMESSAGE>" + "      </REQUESTDATA>" + "    </IMPORTDATA>" + "  </BODY>"
                    + "</ENVELOPE>";
            System.out.println("xml : " + xml);
            TallyRequest tallyRequest = new TallyRequest();
            tallyRequest.SendToTally(xml, tallyUrl, tallyCompanyName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Method for generate xml End

    // Method for Referal Summary Report Start
    @RequestMapping("getreferalsummaryreport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchReferalSummaryReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody ReferalSummarySearchDTO referalsummarysearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT re.re_id,ifnull(re.re_name,'')AS refer_name," +
                "SUM((SELECT COUNT(*) FROM mst_visit v WHERE t.bill_visit_id=v.visit_id)) AS visit_count," +
                "SUM((SELECT COUNT(*) FROM tbill_bill_service tbs WHERE tbs.bs_bill_id=t.bill_id)) AS service_count," +
                "COUNT(t.bill_id) AS invoice_count," +
                "ifnull(sum(t.gross_amount),0)as invoice_value," +
                "ifnull(sum(t.bill_net_payable),0)as cash_value," +
                "ifnull(sum(t.company_net_pay),0)as credit_value ,IFNULL(un.unit_name,'') AS unit_name " +
                " from tbill_bill t " +
                "inner join mst_unit un on un.unit_id=t.tbill_unit_id  " +
                "inner join mst_visit v on t.bill_visit_id=v.visit_id  " +
                "inner join mst_patient p on  v.visit_patient_id=p.patient_id " +
                "left join mst_referring_entity re ON v.refer_by=re.re_id " +
                "where re.re_name !='' ";
        if (referalsummarysearchDTO.getFromdate().equals("") || referalsummarysearchDTO.getFromdate().equals("null")) {
            referalsummarysearchDTO.setFromdate("1990-06-07");
        }
        if (referalsummarysearchDTO.getTodate().equals("") || referalsummarysearchDTO.getTodate().equals("null")) {
            referalsummarysearchDTO.setTodate(strDate);
        }
        if (referalsummarysearchDTO.getTodaydate()) {
            MainQuery += " and (date(t.bill_date)=curdate()) ";
        } else {
            MainQuery += " and (date(t.bill_date) between '" + referalsummarysearchDTO.getFromdate() + "' and '" + referalsummarysearchDTO.getTodate() + "')  ";
        }
        //Unit Name
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 0; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and t.tbill_unit_id in (" + values + ") ";
        }
        System.out.println("Referral Summary Report"+MainQuery);
        CountQuery = " select count(*) from ( " + MainQuery + " GROUP BY re.re_name) as combine ";
        columnName = "re_id,refer_name,visit_count,service_count,invoice_count,invoice_value,cash_value,credit_value,unit_name";
        MainQuery += "GROUP BY re.re_name  limit " + ((referalsummarysearchDTO.getOffset() - 1) * referalsummarysearchDTO.getLimit()) + "," + referalsummarysearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));

    } // Method for Referal Summary Report End

    // Method for Referal Summary Report Start
    @RequestMapping("getreferalsummaryreportPrint/{unitList}/{fromdate}/{todate}")
    public String searchReferalSummaryReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody ReferalSummarySearchDTO referalsummarysearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT re.re_id,ifnull(re.re_name,'')AS refer_name," +
                "SUM((SELECT COUNT(*) FROM mst_visit v WHERE t.bill_visit_id=v.visit_id)) AS visit_count," +
                "SUM((SELECT COUNT(*) FROM tbill_bill_service tbs WHERE tbs.bs_bill_id=t.bill_id)) AS service_count," +
                "COUNT(t.bill_id) AS invoice_count," +
                "ifnull(sum(t.gross_amount),0)as invoice_value," +
                "ifnull(sum(t.bill_net_payable),0)as cash_value," +
                "ifnull(sum(t.company_net_pay),0)as credit_value,IFNULL(un.unit_name,'') AS unit_name  " +
                " from tbill_bill t " +
                "inner join mst_unit un on un.unit_id=t.tbill_unit_id  " +
                "inner join mst_visit v on t.bill_visit_id=v.visit_id  " +
                "inner join mst_patient p on  v.visit_patient_id=p.patient_id " +
                "left join mst_referring_entity re ON v.refer_by=re.re_id " +
                "where re.re_name !='' ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (referalsummarysearchDTO.getTodaydate()) {
            MainQuery += " and (date(t.bill_date)=curdate()) ";
        } else {
            MainQuery += " and (date(t.bill_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        //Unit Name
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 0; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and t.tbill_unit_id in (" + values + ") ";
        }
        CountQuery = " select count(*) from ( " + MainQuery + " GROUP BY re.re_name) as combine ";
        columnName = "re_id,refer_name,visit_count,service_count,invoice_count,invoice_value,cash_value,credit_value,unit_name";
        MainQuery += "GROUP BY re.re_name";
//                "limit " + ((referalsummarysearchDTO.getOffset() - 1) * referalsummarysearchDTO.getLimit()) + "," + referalsummarysearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        // return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(referalsummarysearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(referalsummarysearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (referalsummarysearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("ReferralSummaryReport", jsonArray.toString());
        } else {
            return createReport.createJasperReportPDFByJson("ReferralSummaryReport", jsonArray.toString());
        }
    }
    // Method for Referal Summary Report End

    // Referal report Start by bhushan 15.10.2019
    @RequestMapping("getreferaldetailreport/{unitList}")
    public ResponseEntity searchReferalDetailReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody ReferalDetailSearchDTO referaldetailsearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String MainQuery = " ";
        String CountQuery = "";
        String CountQuery1 = "";
        String GrandCount = "";
        String columnName = "";
        String columnName1 = "";
        String columnName2 = "";
        Query += " ";
        GrandCount = " SELECT re.re_id,re.re_name," +
                " IFNULL(COUNT(tb.bill_id),0) AS bill_count," +
                " SUM((SELECT COUNT(*) FROM tbill_bill_service ts WHERE ts.bs_bill_id=tb.bill_id)) AS service_count," +
                " IFNULL(SUM(tb.bill_net_payable+company_net_pay+tb.bill_discount_amount),0) AS invoice," +
                " IFNULL(SUM(tb.bill_discount_amount),0) AS discount, " +
                " IFNULL(SUM(tb.bill_net_payable),0) AS cash," +
                " IFNULL(SUM(tb.company_net_pay),0) AS credit" +
                " from tbill_bill tb" +
                " inner join mst_unit un on un.unit_id=tb.tbill_unit_id" +
                " inner join mst_visit v on tb.bill_visit_id=v.visit_id" +
                " inner join mst_patient p on v.visit_patient_id=p.patient_id" +
                " inner join mst_user u on p.patient_user_id=u.user_id" +
                " left join mst_gender g on u.user_gender_id=g.gender_id" +
                " left join mst_referring_entity re ON v.refer_by=re.re_id" +
                " where re.re_name !=''";
        CountQuery1 = " SELECT re.re_id,re.re_name," +
                " IFNULL(COUNT(tb.bill_id),0) AS bill_count," +
                " SUM((SELECT COUNT(*) FROM tbill_bill_service ts WHERE ts.bs_bill_id=tb.bill_id)) AS service_count," +
                " IFNULL(SUM(tb.bill_net_payable+company_net_pay+tb.bill_discount_amount),0) AS invoice," +
                " IFNULL(SUM(tb.bill_discount_amount),0) AS discount, " +
                " IFNULL(SUM(tb.bill_net_payable),0) AS cash," +
                " IFNULL(SUM(tb.company_net_pay),0) AS credit" +
                " from tbill_bill tb" +
                " inner join mst_unit un on un.unit_id=tb.tbill_unit_id " +
                " inner join mst_visit v on tb.bill_visit_id=v.visit_id " +
                " inner join mst_patient p on v.visit_patient_id=p.patient_id " +
                " inner join mst_user u on p.patient_user_id=u.user_id " +
                " left join mst_gender g on u.user_gender_id=g.gender_id " +
                " left join mst_referring_entity re ON v.refer_by=re.re_id" +
                " where re.re_name !='' ";
        MainQuery = " SELECT billDetails.visit_date, billDetails.bill_number,billDetails.bill_date," +
                " billDetails.patient_mr_no,billDetails.patientname,billDetails.user_age,billDetails.user_gender," +
                " billDetails.bill_gross_rate,billDetails.bill_discount_amount," +
                " billDetails.bill_cash,billDetails.bill_credit,billDetails.ReferEntityName,billDetails.ReferId,billDetails.unit_name " +
                " from ( SELECT v.created_date as visit_date,t.created_date as bill_date, t.bill_number," +
                " ifnull(p.patient_mr_no,'')as patient_mr_no, CONCAT( ifnull(u.user_firstname,''),' '," +
                " ifnull(u.user_lastname,'') ) as patientname, IFNULL(u.user_age,'') AS user_age, " +
                " IFNULL(g.gender_name,'') AS user_gender,ifnull(t.bill_discount_amount,'')as bill_discount_amount," +
                " ifnull(t.bill_net_payable+company_net_pay+t.bill_discount_amount,'')as bill_gross_rate,ifnull(t.bill_net_payable,'')as bill_cash," +
                " ifnull(t.company_net_pay,'')as bill_credit, ifnull(re.re_name,'')AS ReferEntityName, ifnull(re.re_id,'')AS ReferId," +
                " ifnull(un.unit_name,'')as unit_name,ifnull(un.unit_id,'')as unit_id from tbill_bill t " +
                " left join mst_unit un on un.unit_id=t.tbill_unit_id  " +
                " left join mst_visit v on t.bill_visit_id=v.visit_id  " +
                " left join mst_patient p on  v.visit_patient_id=p.patient_id  " +
                " left join mst_user u on p.patient_user_id=u.user_id " +
                " left join mst_gender g on u.user_gender_id=g.gender_id" +
                " left join mst_referring_entity re ON v.refer_by=re.re_id ) as billDetails  " +
                " where billDetails.ReferEntityName !='' ";
        // From Date To Date
//        if (fromdate.equals("") || fromdate.equals("null")) {
//            fromdate = "1990-06-07";
//        }
//        if (todate.equals("") || todate.equals("null")) {
//            todate = strDate;
//        }
        if (referaldetailsearchDTO.getFromdate() == null || referaldetailsearchDTO.getFromdate().equals("")) {
            referaldetailsearchDTO.setFromdate(strDate);
        }
        if (referaldetailsearchDTO.getTodate() == null || referaldetailsearchDTO.getTodate().equals("")) {
            referaldetailsearchDTO.setTodate(strDate);
        }
        if (referaldetailsearchDTO.getFromdate() != null && referaldetailsearchDTO.getTodate() != null) {
            MainQuery += " and (date(billDetails.bill_date) between '" + referaldetailsearchDTO.getFromdate() + "' and '" + referaldetailsearchDTO.getTodate() + "')  ";
            CountQuery1 += " and (date(tb.bill_date) between '" + referaldetailsearchDTO.getFromdate() + "' and '" + referaldetailsearchDTO.getTodate() + "')  ";
            GrandCount += " and (date(tb.bill_date) between '" + referaldetailsearchDTO.getFromdate() + "' and '" + referaldetailsearchDTO.getTodate() + "')  ";
        } else {
            MainQuery += " and (date(billDetails.bill_date)=curdate()) ";
            CountQuery1 += " and (date(tb.bill_date)=curdate())";
            GrandCount += " and (date(tb.bill_date)=curdate())";
        }
        if (referaldetailsearchDTO.getPatientName() != null && !referaldetailsearchDTO.getPatientName().equals("")) {
            MainQuery += " and  billDetails.patientname LIKE'%" + referaldetailsearchDTO.getPatientName() + "%' ";
            CountQuery1 += " and concat(u.user_firstname,' ',u.user_lastname) LIKE '%" + referaldetailsearchDTO.getPatientName() + "%' ";
            GrandCount += " and concat(u.user_firstname,' ',u.user_lastname) LIKE '%" + referaldetailsearchDTO.getPatientName() + "%' ";
        }
        if (referaldetailsearchDTO.getMrNo() != null && !referaldetailsearchDTO.getMrNo().equals("")) {
            MainQuery += " and billDetails.patient_mr_no LIKE '%" + referaldetailsearchDTO.getMrNo() + "%' ";
            CountQuery1 += " and p.patient_mr_no LIKE '%" + referaldetailsearchDTO.getMrNo() + "%' ";
            GrandCount += " and p.patient_mr_no LIKE '%" + referaldetailsearchDTO.getMrNo() + "%' ";
        }
        if (referaldetailsearchDTO.getVisitdate() != null && !referaldetailsearchDTO.getVisitdate().equals("")) {
            MainQuery += " and billDetails.visit_date = '" + referaldetailsearchDTO.getVisitdate() + "' ";
            CountQuery1 += " and v.visit_date = '" + referaldetailsearchDTO.getVisitdate() + "' ";
            GrandCount += " and v.visit_date = '" + referaldetailsearchDTO.getVisitdate() + "' ";
        }
        if (referaldetailsearchDTO.getReferBy() != 0) {
            MainQuery += " and billDetails.ReferId = '" + referaldetailsearchDTO.getReferBy() + "' ";
            CountQuery1 += " and v.refer_by = '" + referaldetailsearchDTO.getReferBy() + "' ";
            GrandCount += " and v.refer_by = '" + referaldetailsearchDTO.getReferBy() + "' ";
        }
        //Unit Name
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 0; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and billDetails.unit_id in (" + values + ") ";
            CountQuery1 += " and tb.tbill_unit_id in (" + values + ") ";
            GrandCount += " and tb.tbill_unit_id in (" + values + ") ";
        }
        MainQuery += " ORDER BY billDetails.bill_date desc ";
        CountQuery1 += " GROUP BY re.re_name ORDER BY tb.bill_date DESC";
        GrandCount += " ORDER BY tb.bill_date DESC";
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "visit_date,bill_number,bill_date,patient_mr_no,patientname,user_age,user_gender,bill_gross_rate,bill_discount_amount,bill_cash,bill_credit,ReferEntityName,ReferId,unit_name";
        columnName1 = "re_id,re_name,bill_count,service_count,invoice,discount,cash,credit";
        columnName2 = "re_id,re_name,bill_count,service_count,invoice,discount,cash,credit";
        MainQuery += "  limit " + ((referaldetailsearchDTO.getOffset() - 1) * referaldetailsearchDTO.getLimit()) + "," + referaldetailsearchDTO.getLimit();
        System.out.println("Referral Detail Report : " + MainQuery);
        System.out.println("CountQuery1:" + CountQuery1);
        System.out.println("GrandCount:" + GrandCount);
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, columnName1, columnName2, MainQuery, CountQuery, CountQuery1, GrandCount));
    }

    public String sendSmsToNMC(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = " ";
        String MainQuery = " ";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        Query += " select ifnull(t.gross_amount,0) as bill_gross, t.bill_amount_paid,t.company_net_pay,t.bill_net_payable,t.bill_discount_amount,t.bill_id,v.visit_date,p.created_date,s.bs_id,t.bill_date,ifnull(p.patient_mr_no,'')as patient_mr_no,"
                + " CONCAT( ifnull(u.user_firstname,'') ,' ',ifnull(u.user_lastname,'') ) as patientname,"
                + " ifnull(u.user_age,'') as user_age , ifnull(g.gender_name,'')as gender_name,ifnull(pt.pt_name,'')as pt_name,ifnull(ps.ps_name,'')as ps_name,ifnull(sc.sc_uhid_no,'')as NMMCuhid,ifnull(sc.sc_policy_no,'')as sc_policy_no,"
                + " IF(v.sponsor_combination = 0,'Insurance','Self' ) AS TYPE1,"
                + " ifnull(ct.ct_name,'') as ct_name,ifnull(com.company_name,'')as company_name,ifnull(s.bs_service_id,'')as bs_service_id,ifnull(ss.service_name, '')as service_name,"
                + " (s.bs_base_rate*s.bs_quantity) as serviceAmount,"
                + " ifnull(s.bs_gross_rate,'')as bs_gross_rate,ifnull(s.bs_discount_amount,'')as bs_discount_amount,ifnull(un.unit_name,'')as unit_name,ifnull(un.unit_id,'')as unit_id, " +
                " ifnull(sc.sc_company_id,'')as sc_company_id," +
                " ifnull(sc.sc_ct_id,'')as sc_ct_id,ifnull(v.patient_type,'')AS patient_type,IFNULL(v.visit_ps_id,'')AS visit_ps_id,IFNULL(v.refer_by,'')AS refer_by, " +
                " ifnull(re.re_name,'')AS ReferEntityName"
                + " from tbill_bill_service s " + " " +
                " left join mbill_service ss on s.bs_service_id=ss.service_id " +
                " left join tbill_bill t on s.bs_bill_id=t.bill_id " +
                " left join mst_unit un on un.unit_id=t.tbill_unit_id " +
                " left join mst_visit v on t.bill_visit_id=v.visit_id " +
                " left join mst_patient_type pt on v.patient_type=pt.pt_id" +
                " left join mst_patient_source ps ON v.visit_ps_id=ps.ps_id" +
                " left join mst_patient p on  v.visit_patient_id=p.patient_id " +
                " left join mst_user u on p.patient_user_id=u.user_id" +
                " left join trn_sponsor_combination sc ON u.user_id=sc.sc_user_id" +
                " left join mst_company com ON sc.sc_company_id=com.company_id" +
                " left join mst_company_type ct ON sc.sc_ct_id=ct.ct_id " +
                " left join mst_referring_entity re ON v.refer_by=re.re_id " +
                " left join mst_gender g on u.user_gender_id=g.gender_id ";
        MainQuery = "SELECT billDetails.bs_id,billDetails.bill_date,billDetails.patient_mr_no,billDetails.patientname,billDetails.user_age,billDetails.gender_name,billDetails.pt_name, "
                + "billDetails.ps_name,billDetails.NMMCuhid,billDetails.sc_policy_no,billDetails.TYPE1,billDetails.ct_name,billDetails.company_name,"
                + "billDetails.bs_service_id,billDetails.service_name,billDetails.serviceAmount,billDetails.bs_gross_rate ,billDetails.bs_discount_amount, billDetails.unit_name,billDetails.ReferEntityName,billDetails.unit_id, "
                + " billDetails.sc_company_id, billDetails.sc_ct_id, billDetails.patient_type,billDetails.visit_ps_id,billDetails.refer_by"
                + ",userNameTB.user_name,userNameTB.bill_user_id,CONCAT(ifnull(userNameTB.user_firstname,''),' ',ifnull(userNameTB.user_lastname,''))as user_namefull,ifnull(billDetails.visit_date,0)as visit_date,ifnull(billDetails.created_date,0)as created_date,billDetails.bill_id,billDetails.bill_discount_amount," +
                " billDetails.bill_net_payable,billDetails.company_net_pay,billDetails.bill_amount_paid, billDetails.bill_gross"
                + "  " + " from " + " ( " + Query + "  ) as billDetails " + " left join "
                + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName "
                + " from tbill_bill_service s1 " + " left join mst_staff st1 on s1.bs_staff_id=st1.staff_id  "
                + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff  on billDetails.bs_id=doctorStaff.bs_id "
                + " left join (select s.bs_id,st.staff_id,u.user_name,u.user_firstname,u.user_lastname,t.bill_user_id " + " from tbill_bill_service s "
                + " left join  tbill_bill t on s.bs_bill_id=t.bill_id "
                + " left join mst_staff st on t.bill_user_id=st.staff_id  "
                + " left join mst_user u on st.staff_user_id=u.user_id " + " ) " + " as userNameTB " + "  "
                + " on billDetails.bs_id=userNameTB.bs_id  ";
        MainQuery += " where date(billDetails.bill_date)= '" + strDate + "'";
        System.out.println("MainQuery by r" + MainQuery);
        HashMap<String, String> patientRegisterCount = new HashMap<>();
        HashMap<String, String> pstientVisitCount = new HashMap<>();
        int newCount = 0, visitCount = 0, totalCount = 0, invoiceCount = 0;
        double totalCoPay = 0.0;
        double totalCompanyPay = 0.0;
        double totalInvoiceAmount = 0.0;
        double totalPaidAmount = 0.0;
        double totalDiscountAmount = 0.0;
        double totalServiceDiscountAmount = 0.0;
        HashMap<String, Integer> newPatientCount = new HashMap<>();
        HashMap<String, Integer> billCount = new HashMap<>();
        List<Object[]> list1 = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        for (Object[] temp : list1) {
            totalServiceDiscountAmount = totalServiceDiscountAmount + Double.parseDouble(temp[17].toString());
            StringBuilder visit_date = new StringBuilder("" + temp[29]);
            StringBuilder reg_date = new StringBuilder("" + temp[30]);
            StringBuilder billKey = new StringBuilder(temp[31].toString());
            String key = "";
            if (visit_date.toString().trim().equals("0") && reg_date.toString().trim().equals("0")) {
                key = "test";
            } else if (visit_date.toString().trim().equals("0")) {
                key = reg_date.toString().trim().substring(0, 19);
            } else if (reg_date.toString().trim().equals("0")) {
                key = reg_date.toString().trim().substring(0, 19);
            } else {
                key = "" + visit_date.toString().trim().substring(0, 19)
                        + reg_date.toString().trim().substring(0, 19);
            }
            if (billCount.containsKey(billKey.toString())) {
            } else {
                invoiceCount++;
                billCount.put(billKey.toString(), 0);
                totalCoPay = totalCoPay + Double.parseDouble(temp[33].toString());
                totalCompanyPay = totalCompanyPay + Double.parseDouble(temp[34].toString());
                totalPaidAmount = totalPaidAmount + Double.parseDouble(temp[35].toString());
                totalDiscountAmount = totalDiscountAmount + Double.parseDouble(temp[32].toString());
                totalInvoiceAmount = totalInvoiceAmount + Double.parseDouble(temp[36].toString());
            }
        }
        String myquery = "SELECT mv.visit_date ,mp.created_date,mp.patient_id FROM mst_visit mv INNER JOIN mst_patient mp WHERE mv.visit_patient_id=mp.patient_id  AND mv.visit_date >= '"
                + strDate + " 00:00:00' AND mv.visit_date <= '" + strDate + " 23:59:59' and mv.visit_tariff_id is not null";
        List<Object[]> patientFootfall = (List<Object[]>) entityManager.createNativeQuery(myquery).getResultList();
        if (patientFootfall.size() > 0) {
            for (int i = 0; i < patientFootfall.size(); i++) {
                if (pstientVisitCount.containsKey("" + patientFootfall.get(i)[0] + patientFootfall.get(i)[2])) {
                } else {
                    pstientVisitCount.put("" + patientFootfall.get(i)[0] + patientFootfall.get(i)[2], "");
                    visitCount++;
                }
            }
        }
        myquery = "SELECT mp.created_date,mp.patient_id FROM  mst_patient mp WHERE  mp.created_date >= '" + strDate
                + " 00:00:00' AND mp.created_date <= '" + strDate + " 23:59:59'";
        System.out.println("myquery : " + myquery);
        List<Object[]> registration = (List<Object[]>) entityManager.createNativeQuery(myquery).getResultList();
        if (registration.size() > 0) {
            for (int i = 0; i < registration.size(); i++) {
                if (patientRegisterCount.containsKey("" + registration.get(i)[0] + registration.get(i)[1])) {
                } else {
                    newCount++;
                    patientRegisterCount.put("" + registration.get(i)[0] + registration.get(i)[1], "");
                }
            }
        }
        String sms = "Unit Name:RUBY ALICARE NMMC VASHI REG: " + newCount + "FF: " + visitCount + "INV Count: " + invoiceCount + "SERV Count: "
                + list1.size() + "INV AMT: " + totalInvoiceAmount + "Disc: " + totalDiscountAmount + "CASH: "
                + totalCoPay + "Credit: " + totalCompanyPay + "INCOME: " + (totalPaidAmount)
                + "";
        return sms;

    }
}