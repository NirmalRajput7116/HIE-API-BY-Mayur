package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@RestController
@RequestMapping("/mis_opd_ipd_collection_summary")
public class OpdIpdCollectionSummaryController {

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
    @RequestMapping("getopdcollectionSummaryReport/{unitList}/{pmId}/{clclId}/{fromdate}/{todate}/{IPDFlag}")
    public ResponseEntity searchOpdCollectionSummary(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdCollectionSummarySearchDTO opdipdcollectionsummarySearchDTO, @PathVariable String[] unitList, @PathVariable String pmId, @PathVariable String clclId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable String IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " SELECT b.tbill_unit_id as unt,ifnull(un.unit_name,'')as unit_name , COUNT(b.bill_number) as InvoiceNO,COUNT(tr.br_reciept_number)as RecieptNo, " +
                " sum(b.bill_discount_amount)as ConcOnBill,sum(b.bill_net_payable)as bill_net_payable, " +
                " sum(tr.br_payment_amount)as PaidAmount,sum(b.bill_outstanding)as OSA,(sum(b.bill_discount_amount)+sum(b.bill_net_payable)) as grossPayable, " +
                " sum((SELECT sum(bs_discount_amount) " +
                " FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id)) as discountonservice ,(sum(b.bill_discount_amount)+sum(b.bill_net_payable)+ sum((SELECT sum(bs_discount_amount) " +
                " FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id)) ) as totalAmount " +
                " FROM tbill_reciept tr " +
                " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                " left join mst_visit v on b.bill_visit_id=v.visit_id " +
                " left join mst_patient p on v.visit_patient_id=p.patient_id " +
                " left join mst_user u on p.patient_user_id=u.user_id " +
                " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                " left join mst_staff s on b.bill_user_id=s.staff_id " +
                " left join mst_user mu on s.staff_user_id=mu.user_id " +
                " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                " left join mst_unit un on b.tbill_unit_id=un.unit_id ";
        String Query2 = " select b.tbill_unit_id as punt,case when tpr.br_pm_id = 1 then sum(tpr.br_payment_amount) end as Cash, " +
                "    case when tpr.br_pm_id = 2 then sum(tpr.br_payment_amount) end as Cheque, " +
                "    case when tpr.br_pm_id = 3 then sum(tpr.br_payment_amount) end as Card, " +
                "    case when tpr.br_pm_id = 4 then sum(tpr.br_payment_amount) end as Transfer, " +
                "    case when tpr.br_pm_id = 5 then sum(tpr.br_payment_amount) end as Other " +
                " from tbill_reciept tpr " +
                " left join tbill_bill b on tpr.br_bill_id=b.bill_id " +
                " left join mst_payment_mode m on tpr.br_pm_id=m.pm_id ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag.equals("0")) {
            Query1 += " where b.ipd_bill=false";
            Query2 += " where b.ipd_bill=false";
        } else {
            Query1 += "where b.ipd_bill=true";
            Query2 += "where b.ipd_bill=true";
        }
        if (opdipdcollectionsummarySearchDTO.getFromdate().equals("") || opdipdcollectionsummarySearchDTO.getFromdate().equals("null")) {
            opdipdcollectionsummarySearchDTO.setFromdate("1990-06-07");
        }
        if (opdipdcollectionsummarySearchDTO.getTodate().equals("") || opdipdcollectionsummarySearchDTO.getTodate().equals("null")) {
            opdipdcollectionsummarySearchDTO.setTodate(strDate);
        }
        if (opdipdcollectionsummarySearchDTO.getTodaydate()) {
            Query1 += " and (date(tr.created_date)=curdate()) ";
            Query2 += " and (date(tpr.created_date)=curdate()) ";
        } else {
            Query1 += " and (date(tr.created_date) between '" + opdipdcollectionsummarySearchDTO.getFromdate() + "' and '" + opdipdcollectionsummarySearchDTO.getTodate() + "')  ";
            Query2 += " and (date(tpr.created_date) between '" + opdipdcollectionsummarySearchDTO.getFromdate() + "' and '" + opdipdcollectionsummarySearchDTO.getTodate() + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and b.tbill_unit_id in (" + values + ") ";
            Query2 += " and b.tbill_unit_id in (" + values + ") ";
        }
        // Payment-mode list
        if (!pmId.equals("null") && !pmId.equals("0")) {
            Query1 += " and tr.br_pm_id =  " + pmId + " ";
            //   Query2 += " and tr.br_pm_id =  " + pmId + " ";
        }
        // Collection Mode
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query1 += " and (b.bill_discount_amount+b.bill_net_payable+ (SELECT sum(bs_discount_amount) " +
                        " FROM tbill_bill_service " +
                        " where bs_bill_id=tr.br_bill_id))!=0 and b.bill_outstanding=0 ";
            } else if (clclId.equals("2")) {
                Query1 += " and (b.bill_discount_amount+b.bill_net_payable+ (SELECT sum(bs_discount_amount) " +
                        " FROM tbill_bill_service " +
                        " where bs_bill_id=tr.br_bill_id)) and b.bill_outstanding!=0 ";
            } else {
                Query1 += " and (b.bill_discount_amount+b.bill_net_payable+ (SELECT sum(bs_discount_amount) " +
                        " FROM tbill_bill_service " +
                        " where bs_bill_id=tr.br_bill_id))=0 and b.bill_outstanding!=0 ";
            }
        }
        String MainQuery = " select HalfData.unit_name,HalfData.InvoiceNO,HalfData.RecieptNo,HalfData.ConcOnBill,HalfData.bill_net_payable, " +
                " HalfData.PaidAmount,HalfData.OSA,HalfData.grossPayable,HalfData.discountonservice,HalfData.totalAmount,ifnull(pmDisti.Cash,0) as Cash " +
                " ,ifnull(pmDisti.Cheque,0) as Cheque,ifnull(pmDisti.Card,0) as Card ,ifnull(pmDisti.Transfer,0) as Transfer ,ifnull(pmDisti.Other,0) as Other from " +
                " ( " + Query1 + " group by b.tbill_unit_id  ) as HalfData " +
                "  " +
                " left join " +
                " ( " + Query2 + " group by tpr.br_pm_id,b.tbill_unit_id  ) as pmDisti " +
                " on HalfData.unt=pmDisti.punt ";
        System.out.println("OPD-IPD Bill Collection Summary Report"+MainQuery);
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "unit_name,InvoiceNO,RecieptNo,ConcOnBill,bill_net_payable,PaidAmount,OSA,grossPayable,discountonservice,totalAmount,Cash,Cheque,Card,Transfer,Other";
        MainQuery += " limit " + ((opdipdcollectionsummarySearchDTO.getOffset() - 1) * opdipdcollectionsummarySearchDTO.getLimit()) + "," + opdipdcollectionsummarySearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getopdcollectionSummaryReportPrint/{unitList}/{pmId}/{clclId}/{fromdate}/{todate}/{IPDFlag}")
    public String searchOpdCollectionSummaryPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdCollectionSummarySearchDTO opdipdcollectionsummarySearchDTO, @PathVariable String[] unitList, @PathVariable String pmId, @PathVariable String clclId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable String IPDFlag) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        JSONArray jsonArray = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " SELECT b.tbill_unit_id as unt,ifnull(un.unit_name,'')as unit_name , COUNT(b.bill_number) as InvoiceNO,COUNT(tr.br_reciept_number)as RecieptNo, " +
                " sum(b.bill_discount_amount)as ConcOnBill,sum(b.bill_net_payable)as bill_net_payable, " +
                " sum(tr.br_payment_amount)as PaidAmount,sum(b.bill_outstanding)as OSA,(sum(b.bill_discount_amount)+sum(b.bill_net_payable)) as grossPayable, " +
                " sum((SELECT sum(bs_discount_amount) " +
                " FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id)) as discountonservice ,(sum(b.bill_discount_amount)+sum(b.bill_net_payable)+ sum((SELECT sum(bs_discount_amount) " +
                " FROM tbill_bill_service  where bs_bill_id=tr.br_bill_id)) ) as totalAmount " +
                " FROM tbill_reciept tr " +
                " left join tbill_bill b on tr.br_bill_id=b.bill_id " +
                " left join mst_visit v on b.bill_visit_id=v.visit_id " +
                " left join mst_patient p on v.visit_patient_id=p.patient_id " +
                " left join mst_user u on p.patient_user_id=u.user_id " +
                " left join mst_payment_mode m on tr.br_pm_id=m.pm_id " +
                " left join mst_staff s on b.bill_user_id=s.staff_id " +
                " left join mst_user mu on s.staff_user_id=mu.user_id " +
                " left join mst_cash_counter cu on b.bill_cash_counter_id=cu.cc_id " +
                " left join mst_unit un on b.tbill_unit_id=un.unit_id ";
        String Query2 = " select b.tbill_unit_id as punt,case when tpr.br_pm_id = 1 then sum(tpr.br_payment_amount) end as Cash, " +
                "    case when tpr.br_pm_id = 2 then sum(tpr.br_payment_amount) end as Cheque, " +
                "    case when tpr.br_pm_id = 3 then sum(tpr.br_payment_amount) end as Card, " +
                "    case when tpr.br_pm_id = 4 then sum(tpr.br_payment_amount) end as Transfer, " +
                "    case when tpr.br_pm_id = 5 then sum(tpr.br_payment_amount) end as Other " +
                " from tbill_reciept tpr " +
                " left join tbill_bill b on tpr.br_bill_id=b.bill_id " +
                " left join mst_payment_mode m on tpr.br_pm_id=m.pm_id ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag.equals("0")) {
            Query1 += " where b.ipd_bill=false";
            Query2 += " where b.ipd_bill=false";
        } else {
            Query1 += "where b.ipd_bill=true";
            Query2 += "where b.ipd_bill=true";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
//            fromdate = "1990-06-07";
            fromdate = strDate;
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (opdipdcollectionsummarySearchDTO.getTodaydate()) {
            Query1 += " and (date(tr.created_date)=curdate()) ";
            Query2 += " and (date(tpr.created_date)=curdate()) ";
        } else {
            Query1 += " and (date(tr.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query2 += " and (date(tpr.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (opdipdcollectionsummarySearchDTO.getFromdate().equals("") || opdipdcollectionsummarySearchDTO.getFromdate().equals("null")) {
            opdipdcollectionsummarySearchDTO.setFromdate("1990-06-07");
        }
        if (opdipdcollectionsummarySearchDTO.getTodate().equals("") || opdipdcollectionsummarySearchDTO.getTodate().equals("null")) {
            opdipdcollectionsummarySearchDTO.setTodate(strDate);
        }
        if (opdipdcollectionsummarySearchDTO.getTodaydate()) {
            Query1 += " and (date(tr.created_date)=curdate()) ";
            Query2 += " and (date(tpr.created_date)=curdate()) ";
        } else {
            Query1 += " and (date(tr.created_date) between '" + opdipdcollectionsummarySearchDTO.getFromdate() + "' and '" + opdipdcollectionsummarySearchDTO.getTodate() + "')  ";
            Query2 += " and (date(tpr.created_date) between '" + opdipdcollectionsummarySearchDTO.getFromdate() + "' and '" + opdipdcollectionsummarySearchDTO.getTodate() + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and b.tbill_unit_id in (" + values + ") ";
            Query2 += " and b.tbill_unit_id in (" + values + ") ";
        }
        // Payment-mode list
        if (!pmId.equals("null") && !pmId.equals("0")) {
            Query1 += " and tr.br_pm_id =  " + pmId + " ";
            //Query2 += " and tr.br_pm_id =  " + pmId + " ";
        }
        // Collection Mode
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query1 += " and (b.bill_discount_amount+b.bill_net_payable+ (SELECT sum(bs_discount_amount) " +
                        " FROM tbill_bill_service " +
                        " where bs_bill_id=tr.br_bill_id))!=0 and b.bill_outstanding=0 ";
            } else if (clclId.equals("2")) {
                Query1 += " and (b.bill_discount_amount+b.bill_net_payable+ (SELECT sum(bs_discount_amount) " +
                        " FROM tbill_bill_service " +
                        " where bs_bill_id=tr.br_bill_id)) and b.bill_outstanding!=0 ";
            } else {
                Query1 += " and (b.bill_discount_amount+b.bill_net_payable+ (SELECT sum(bs_discount_amount) " +
                        " FROM tbill_bill_service " +
                        " where bs_bill_id=tr.br_bill_id))=0 and b.bill_outstanding!=0 ";
            }
        }
        String MainQuery = " select HalfData.unit_name,HalfData.InvoiceNO,HalfData.RecieptNo,HalfData.ConcOnBill,HalfData.bill_net_payable, " +
                " HalfData.PaidAmount,HalfData.OSA,HalfData.grossPayable,HalfData.discountonservice,HalfData.totalAmount,ifnull(pmDisti.Cash,0) as Cash " +
                " ,ifnull(pmDisti.Cheque,0) as Cheque,ifnull(pmDisti.Card,0) as Card ,ifnull(pmDisti.Transfer,0) as Transfer ,ifnull(pmDisti.Other,0) as Other from " +
                " ( " + Query1 + " group by b.tbill_unit_id  ) as HalfData " +
                "  " +
                " left join " +
                " ( " + Query2 + " group by tpr.br_pm_id,b.tbill_unit_id  ) as pmDisti " +
                " on HalfData.unt=pmDisti.punt ";
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "unit_name,InvoiceNO,RecieptNo,ConcOnBill,bill_net_payable,PaidAmount,OSA,grossPayable,discountonservice,totalAmount,Cash,Cheque,Card,Transfer,Other";
        jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(opdipdcollectionsummarySearchDTO.getCurrentUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(opdipdcollectionsummarySearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        if (jsonArray != null) {
            jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
            jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        }
        if (opdipdcollectionsummarySearchDTO.isPrint()) {
            columnName = "unit_name,InvoiceNO,RecieptNo,ConcOnBill,bill_net_payable,PaidAmount,OSA,grossPayable,discountonservice,totalAmount,Cash,Cheque,Card,Transfer,Other";
            return createReport.generateExcel(columnName, "BillingOPDIPDBillCollectionSummaryReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("BillingOPDIPDBillCollectionSummaryReport", jsonArray.toString());
        }
    }

}
