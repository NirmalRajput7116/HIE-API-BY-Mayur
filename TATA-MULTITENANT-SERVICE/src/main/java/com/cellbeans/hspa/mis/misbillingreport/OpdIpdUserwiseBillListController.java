package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_opd_ipd_userwise_billlist")
public class OpdIpdUserwiseBillListController {

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
    @RequestMapping("getOpdIpdUserwiseBillListReport/{unitList}/{IPDFlag}")
    public ResponseEntity searchOpdIpdUserwiseBillList(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdUserwiseBillListSearchDTO opdipduserwisebilllistsearchDTO, @PathVariable String[] unitList, @PathVariable int IPDFlag) {
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " ";
        String Query2 = " ";
        String CountQuery = " ";
        String columnName = " ";
        if (IPDFlag == 0) {
            Query1 = " SELECT ifnull(u.unit_name,'')as unit_name," +
                    "  ifnull(tb.bill_number,'')as bill_number," +
                    "  ifnull(tb.bill_date,'')as bill_date," +
                    "  ifnull(p.patient_mr_no,'')as patient_mr_no," +
                    "  ifnull(tr.tariff_name,'')as tariff_name, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as ConsultDoctor, " +
                    " CONCAT( ifnull(uf.user_firstname,'') ,' ',ifnull(uf.user_lastname,'') ) as BillMadeBystaff, " +
                    " ifnull(tb.bill_sub_total,0) as totalBillAmount," +
                    " ifnull(tb.bill_discount_amount,0) as ConsessionAmount, " +
                    " ifnull(tb.bill_net_payable,0) as selfAmount," +
                    " ifnull((tb.company_net_pay),0)as NonSelfAmount, " +
                    " ifnull(tb.bill_outstanding,0) as selfBalancedAmount," +
                    " ifnull(tb.bill_amount_paid,0) as PaidAmount " +
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
            Query2 = " SELECT ifnull(sum(tb.bill_sub_total),0)as GrandTotalBillAmount ," +
                    " ifnull(sum(tb.bill_discount_amount),0)as GrandConcessionAmount, " +
                    " ifnull(sum(tb.bill_net_payable),0)as GrandSelfAmount, " +
                    " ifnull(sum(tb.company_net_pay),0)as NonSelfAmount, " +
                    " ifnull(sum(tb.bill_outstanding),0)as GrandSelfBalancedAmount," +
                    " ifnull(sum(tb.bill_amount_paid),0)GrandPaidAmount " +
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

        } else {
            Query1 = " SELECT ifnull(u.unit_name,'')as unit_name," +
                    "  ifnull(tb.bill_number,'')as bill_number," +
                    "  ifnull(tb.bill_date,'')as bill_date," +
                    "  ifnull(p.patient_mr_no,'')as patient_mr_no," +
                    "  ifnull(tr.tariff_name,'')as tariff_name, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as ConsultDoctor, " +
                    " CONCAT( ifnull(uf.user_firstname,'') ,' ',ifnull(uf.user_lastname,'') ) as BillMadeBystaff, " +
                    " ifnull(tb.bill_sub_total,0) as totalBillAmount," +
                    " ifnull(tb.bill_discount_amount,0) as ConsessionAmount, " +
                    " ifnull(tb.bill_net_payable,0) as selfAmount," +
                    " ifnull((tb.company_net_pay),0)as NonSelfAmount, " +
                    " ifnull(tb.bill_outstanding,0) as selfBalancedAmount," +
                    " ifnull(tb.bill_amount_paid,0) as PaidAmount " +
                    " FROM tbill_bill tb " +
                    " inner join mbill_tariff tr on tb.bill_tariff_id=tr.tariff_id " +
                    " inner join mst_unit u on tb.tbill_unit_id=u.unit_id " +
                    " inner join trn_admission a on tb.bill_admission_id=a.admission_id " +
                    " inner join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id " +
                    " inner join mst_staff sf on a.admission_staff_id=sf.staff_id " +
                    " inner join mst_user su on sf.staff_user_id=su.user_id " +
                    " inner join mst_staff ff on tb.bill_user_id=ff.staff_id " +
                    " inner join mst_user uf on ff.staff_user_id=uf.user_id where tb.ipd_bill=" + IPDFlag;
            Query2 = " SELECT ifnull(sum(tb.bill_sub_total),0)as GrandTotalBillAmount ," +
                    " ifnull(sum(tb.bill_discount_amount),0)as GrandConcessionAmount, " +
                    " ifnull(sum(tb.bill_net_payable),0)as GrandSelfAmount," +
                    " ifnull(sum(tb.company_net_pay),0)as NonSelfAmount," +
                    " ifnull(sum(tb.bill_outstanding),0)as GrandSelfBalancedAmount," +
                    " ifnull(sum(tb.bill_amount_paid),0)GrandPaidAmount " +
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
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and tb.tbill_unit_id in (" + values + ") ";
            Query2 += " and tb.tbill_unit_id in (" + values + ") ";
        }
        if (opdipduserwisebilllistsearchDTO.getFromdate().equals("null") || opdipduserwisebilllistsearchDTO.getFromdate().equals("")) {
            opdipduserwisebilllistsearchDTO.setFromdate("1990-06-07");
        }
        if (opdipduserwisebilllistsearchDTO.getTodate().equals("null") || opdipduserwisebilllistsearchDTO.getTodate().equals("")) {
            opdipduserwisebilllistsearchDTO.setTodate(strDate);
        }
        if (!opdipduserwisebilllistsearchDTO.getMrNo().equals("")) {
            Query1 += " and  p.patient_mr_no like  '%" + opdipduserwisebilllistsearchDTO.getMrNo() + "%' ";
            Query2 += " and p.patient_mr_no like  '%" + opdipduserwisebilllistsearchDTO.getMrNo() + "%' ";
        }
        if (!String.valueOf(opdipduserwisebilllistsearchDTO.getStaffId()).equals("0")) {
            Query1 += "  and tb.bill_user_id=" + opdipduserwisebilllistsearchDTO.getStaffId() + " ";
            Query2 += "  and tb.bill_user_id=" + opdipduserwisebilllistsearchDTO.getStaffId() + " ";
        }
        if (!String.valueOf(opdipduserwisebilllistsearchDTO.getStaffId1()).equals("0")) {
            Query1 += "  and sf.staff_id=" + opdipduserwisebilllistsearchDTO.getStaffId1() + " ";
            Query2 += "   and sf.staff_id=" + opdipduserwisebilllistsearchDTO.getStaffId1() + " ";
        }
        if (!String.valueOf(opdipduserwisebilllistsearchDTO.getTariffId()).equals("0")) {
            Query1 += "  and tb.bill_tariff_id=" + opdipduserwisebilllistsearchDTO.getTariffId() + " ";
            Query2 += "  and tb.bill_tariff_id=" + opdipduserwisebilllistsearchDTO.getTariffId() + " ";
        }
        if (opdipduserwisebilllistsearchDTO.getFromdate().equals("")) {
            opdipduserwisebilllistsearchDTO.setFromdate("1990-06-07");
        }
        if (opdipduserwisebilllistsearchDTO.getTodate().equals("")) {
            opdipduserwisebilllistsearchDTO.setTodate(strDate);
        }
        if (opdipduserwisebilllistsearchDTO.getTodaydate()) {
            Query1 += "  and date(tb.created_date)=curdate() ";
            Query2 += "  and date(tb.created_date)=curdate() ";
        } else {
            Query1 += " and  (date(tb.created_date) between '" + opdipduserwisebilllistsearchDTO.getFromdate() + "' and '" + opdipduserwisebilllistsearchDTO.getTodate() + "') ";
            Query2 += " and  (date(tb.created_date) between '" + opdipduserwisebilllistsearchDTO.getFromdate() + "' and '" + opdipduserwisebilllistsearchDTO.getTodate() + "') ";
        }
        if (!opdipduserwisebilllistsearchDTO.getPatientName().equals("")) {
            Query1 += " and (concat(us.user_firstname,' ',us.user_lastname) like '%" + opdipduserwisebilllistsearchDTO.getPatientName() + "%' or concat(us.user_lastname,' ',us.user_firstname) like '%" + opdipduserwisebilllistsearchDTO.getPatientName() + "%' ) ";
            Query2 += " and (concat(us.user_firstname,' ',us.user_lastname) like '%" + opdipduserwisebilllistsearchDTO.getPatientName() + "%' or concat(us.user_lastname,' ',us.user_firstname) like '%" + opdipduserwisebilllistsearchDTO.getPatientName() + "%' ) ";
        }

        System.out.println("User-wise Bill List Reports"+Query1);
        CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        columnName = "unit_name,bill_number,bill_date,patient_mr_no,tariff_name,patientname,ConsultDoctor,BillMadeBystaff,totalBillAmount,ConsessionAmount,selfAmount,NonSelfAmount,selfBalancedAmount,PaidAmount";
        Query1 += " ORDER BY tb.created_date DESC limit " + ((opdipduserwisebilllistsearchDTO.getOffset() - 1) * opdipduserwisebilllistsearchDTO.getLimit()) + "," + opdipduserwisebilllistsearchDTO.getLimit();
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query2).getResultList();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query1, CountQuery));
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("GrandTotalBillAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("GrandConcessionAmount", ob[1].toString());
                jsonArray.getJSONObject(0).put("GrandSelfAmount", ob[2].toString());
                jsonArray.getJSONObject(0).put("GrandNonSelfAmount", ob[3].toString());
                jsonArray.getJSONObject(0).put("GrandSelfBalancedAmount", ob[4].toString());
                jsonArray.getJSONObject(0).put("GrandPaidAmount", ob[5].toString());
            }
            //System.out.println(" "+jsonArray.toString());
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        return ResponseEntity.ok(jsonArray.toString());
    }
    // Jasper Print Code Start Userwise Bill List Reports.

    @RequestMapping("getOpdIpdUserwiseBillListReportPrint/{unitList}/{IPDFlag}")
    public String searchOpdIpdUserwiseBillListPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody OpdIpdUserwiseBillListSearchDTO opdipduserwisebilllistsearchDTO, @PathVariable String[] unitList, @PathVariable int IPDFlag) throws JsonProcessingException {
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " ";
        String Query2 = " ";
        String CountQuery = " ";
        String columnName = " ";
        if (IPDFlag == 0) {
            Query1 = " SELECT ifnull(u.unit_name,'')as unit_name," +
                    "  ifnull(tb.bill_number,'')as bill_number," +
                    "  ifnull(tb.bill_date,'')as bill_date," +
                    "  ifnull(p.patient_mr_no,'')as patient_mr_no," +
                    "  ifnull(tr.tariff_name,'')as tariff_name, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as ConsultDoctor, " +
                    " CONCAT( ifnull(uf.user_firstname,'') ,' ',ifnull(uf.user_lastname,'') ) as BillMadeBystaff, " +
                    " ifnull(tb.bill_sub_total,0) as totalBillAmount," +
                    " ifnull(tb.bill_discount_amount,0) as ConsessionAmount, " +
                    " ifnull(tb.bill_net_payable,0) as selfAmount," +
                    " ifnull((tb.company_net_pay),0)as NonSelfAmount, " +
                    " ifnull(tb.bill_outstanding,0) as selfBalancedAmount," +
                    " ifnull(tb.bill_amount_paid,0) as PaidAmount " +
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
            Query2 = " SELECT ifnull(sum(tb.bill_sub_total),0)as GrandTotalBillAmount ," +
                    " ifnull(sum(tb.bill_discount_amount),0)as GrandConcessionAmount, " +
                    " ifnull(sum(tb.bill_net_payable),0)as GrandSelfAmount, " +
                    " ifnull(sum(tb.company_net_pay),0)as NonSelfAmount, " +
                    " ifnull(sum(tb.bill_outstanding),0)as GrandSelfBalancedAmount, " +
                    " ifnull(sum(tb.bill_amount_paid),0)GrandPaidAmount " +
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

        } else {
            Query1 = " SELECT ifnull(u.unit_name,'')as unit_name," +
                    "  ifnull(tb.bill_number,'')as bill_number," +
                    "  ifnull(tb.bill_date,'')as bill_date," +
                    "  ifnull(p.patient_mr_no,'')as patient_mr_no," +
                    "  ifnull(tr.tariff_name,'')as tariff_name, " +
                    " CONCAT( ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'') ) as patientname, " +
                    " CONCAT( ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,'') ) as ConsultDoctor, " +
                    " CONCAT( ifnull(uf.user_firstname,'') ,' ',ifnull(uf.user_lastname,'') ) as BillMadeBystaff, " +
                    " ifnull(tb.bill_sub_total,0) as totalBillAmount," +
                    " ifnull(tb.bill_discount_amount,0) as ConsessionAmount, " +
                    " ifnull(tb.bill_net_payable,0) as selfAmount," +
                    " ifnull((tb.company_net_pay),0)as NonSelfAmount, " +
                    " ifnull(tb.bill_outstanding,0) as selfBalancedAmount," +
                    " ifnull(tb.bill_amount_paid,0) as PaidAmount " +
                    " FROM tbill_bill tb " +
                    " inner join mbill_tariff tr on tb.bill_tariff_id=tr.tariff_id " +
                    " inner join mst_unit u on tb.tbill_unit_id=u.unit_id " +
                    " inner join trn_admission a on tb.bill_admission_id=a.admission_id " +
                    " inner join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id " +
                    " inner join mst_staff sf on a.admission_staff_id=sf.staff_id " +
                    " inner join mst_user su on sf.staff_user_id=su.user_id " +
                    " inner join mst_staff ff on tb.bill_user_id=ff.staff_id " +
                    " inner join mst_user uf on ff.staff_user_id=uf.user_id where tb.ipd_bill=" + IPDFlag;
            Query2 = " SELECT ifnull(sum(tb.bill_sub_total),0)as GrandTotalBillAmount ," +
                    " ifnull(sum(tb.bill_discount_amount),0)as GrandConcessionAmount, " +
                    " ifnull(sum(tb.bill_net_payable),0)as GrandSelfAmount, " +
                    " ifnull(sum(tb.company_net_pay),0)as NonSelfAmount," +
                    " ifnull(sum(tb.bill_outstanding),0)as GrandSelfBalancedAmount," +
                    " ifnull(sum(tb.bill_amount_paid),0)GrandPaidAmount " +
                    " FROM tbill_bill tb " +
                    " inner join mbill_tariff tr on tb.bill_tariff_id=tr.tariff_id " +
                    " inner join mst_unit u on tb.tbill_unit_id=u.unit_id " +
                    " inner join trn_admission a on tb.bill_admission_id=a.admission_id " +
                    " inner join mst_patient p on a.admission_patient_id=p.patient_id " +
                    " inner join mst_user us on p.patient_user_id=us.user_id " +
                    " inner join mst_staff sf on a.admission_staff_id=sf.staff_id " +
                    " inner join mst_user su on sf.staff_user_id=su.user_id " +
                    " inner join mst_staff ff on tb.bill_user_id=ff.staff_id " +
                    " inner join mst_user uf on ff.staff_user_id=uf.user_id where tb.ipd_bill=" + IPDFlag;
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and tb.tbill_unit_id in (" + values + ") ";
            Query2 += " and tb.tbill_unit_id in (" + values + ") ";
        }
        if (opdipduserwisebilllistsearchDTO.getFromdate().equals("null") || opdipduserwisebilllistsearchDTO.getFromdate().equals("")) {
            opdipduserwisebilllistsearchDTO.setFromdate("1990-06-07");
        }
        if (opdipduserwisebilllistsearchDTO.getTodate().equals("null") || opdipduserwisebilllistsearchDTO.getTodate().equals("")) {
            opdipduserwisebilllistsearchDTO.setTodate(strDate);
        }
        if (!opdipduserwisebilllistsearchDTO.getMrNo().equals("")) {
            Query1 += " and  p.patient_mr_no like  '%" + opdipduserwisebilllistsearchDTO.getMrNo() + "%' ";
            Query2 += " and p.patient_mr_no like  '%" + opdipduserwisebilllistsearchDTO.getMrNo() + "%' ";
        }
        if (!String.valueOf(opdipduserwisebilllistsearchDTO.getStaffId()).equals("0")) {
            Query1 += "  and ff.staff_user_id=" + opdipduserwisebilllistsearchDTO.getStaffId() + " ";
            Query2 += "  and ff.staff_user_id=" + opdipduserwisebilllistsearchDTO.getStaffId() + " ";
        }
        if (!String.valueOf(opdipduserwisebilllistsearchDTO.getStaffId1()).equals("0")) {
            Query1 += "  and sf.staff_id=" + opdipduserwisebilllistsearchDTO.getStaffId1() + " ";
            Query2 += "  and sf.staff_id=" + opdipduserwisebilllistsearchDTO.getStaffId1() + " ";
        }
        if (!String.valueOf(opdipduserwisebilllistsearchDTO.getTariffId()).equals("0")) {
            Query1 += "  and tb.bill_tariff_id=" + opdipduserwisebilllistsearchDTO.getTariffId() + " ";
            Query2 += "  and tb.bill_tariff_id=" + opdipduserwisebilllistsearchDTO.getTariffId() + " ";
        }
        if (opdipduserwisebilllistsearchDTO.getFromdate().equals("")) {
            opdipduserwisebilllistsearchDTO.setFromdate("1990-06-07");
        }
        if (opdipduserwisebilllistsearchDTO.getTodate().equals("")) {
            opdipduserwisebilllistsearchDTO.setTodate(strDate);
        }
        if (opdipduserwisebilllistsearchDTO.getTodaydate()) {
            Query1 += "  and date(tb.created_date)=curdate() ";
            Query2 += "  and date(tb.created_date)=curdate() ";
        } else {
            Query1 += " and  (date(tb.created_date) between '" + opdipduserwisebilllistsearchDTO.getFromdate() + "' and '" + opdipduserwisebilllistsearchDTO.getTodate() + "') ";
            Query2 += " and  (date(tb.created_date) between '" + opdipduserwisebilllistsearchDTO.getFromdate() + "' and '" + opdipduserwisebilllistsearchDTO.getTodate() + "') ";
        }
        if (!opdipduserwisebilllistsearchDTO.getPatientName().equals("")) {
            Query1 += " and (concat(us.user_firstname,' ',us.user_lastname) like '%" + opdipduserwisebilllistsearchDTO.getPatientName() + "%' or concat(us.user_lastname,' ',us.user_firstname) like '%" + opdipduserwisebilllistsearchDTO.getPatientName() + "%' ) ";
            Query2 += " and (concat(us.user_firstname,' ',us.user_lastname) like '%" + opdipduserwisebilllistsearchDTO.getPatientName() + "%' or concat(us.user_lastname,' ',us.user_firstname) like '%" + opdipduserwisebilllistsearchDTO.getPatientName() + "%' ) ";
        }
        CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        columnName = "unit_name,bill_number,bill_date,patient_mr_no,tariff_name,patientname,ConsultDoctor,BillMadeBystaff,totalBillAmount,ConsessionAmount,selfAmount,NonSelfAmount,selfBalancedAmount,PaidAmount";
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query2).getResultList();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query1, CountQuery));
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("GrandTotalBillAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("GrandConcessionAmount", ob[1].toString());
                jsonArray.getJSONObject(0).put("GrandSelfAmount", ob[2].toString());
                jsonArray.getJSONObject(0).put("GrandNonSelfAmount", ob[3].toString());
                jsonArray.getJSONObject(0).put("GrandSelfBalancedAmount", ob[4].toString());
                jsonArray.getJSONObject(0).put("GrandPaidAmount", ob[5].toString());
            }
        } catch (Exception e) {
            //  e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(opdipduserwisebilllistsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(opdipduserwisebilllistsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (opdipduserwisebilllistsearchDTO.getPrint()) {
            columnName = "unit_name,bill_number,bill_date,patient_mr_no,patientname,ConsultDoctor,BillMadeBystaff,tariff_name,totalBillAmount,ConsessionAmount,selfAmount,NonSelfAmount,selfBalancedAmount,PaidAmount";
            return createReport.generateExcel(columnName, "OpdIpdUserWiseBillListReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("OpdIpdUserWiseBillListReport", jsonArray.toString());
        }
    }

    //OPD Service Detail Bill Reports PrintAll generated excel and then excel attach to mail body send it mail automatically.
    @RequestMapping("getOpdServiceBillListExcel/{fromdate}/{todate}")
    public String getOpdServiceBillListExcel(@RequestHeader("X-tenantId") String tenantName, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " ";
        String CountQuery = " ";
        String columnName = " ";
        Query1 = " SELECT ifnull(u.unit_name,'')as unit_name, ifnull(p.patient_mr_no,'')as patient_mr_no, " +
                " CONCAT(ifnull(us.user_firstname,'') ,' ',ifnull(us.user_lastname,'')) as patientname, " +
                " IFNULL(tb.bill_number,'')as bill_number,IFNULL(tb.bill_date,'')as bill_date, " +
                " IFNULL(ms.service_name,'')as service_name, IFNULL(tbs.bs_gross_rate,0) as serviceAmount, " +
                " IFNULL(tr.tariff_name,'')as tariff_name, " +
                " CONCAT(ifnull(su.user_firstname,'') ,' ',ifnull(su.user_lastname,'')) as ConsultDoctor, " +
                " CONCAT(ifnull(uf.user_firstname,'') ,' ',ifnull(uf.user_lastname,'')) as BillMadeBystaff " +
                " FROM tbill_bill_service tbs " +
                " INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id " +
                " INNER JOIN mbill_service ms ON ms.service_id = tbs.bs_service_id " +
                " inner join mbill_tariff tr on tb.bill_tariff_id=tr.tariff_id " +
                " inner join mst_unit u on tb.tbill_unit_id=u.unit_id " +
                " inner join mst_visit v on tb.bill_visit_id=v.visit_id " +
                " inner join mst_patient p on v.visit_patient_id=p.patient_id " +
                " inner join mst_user us on p.patient_user_id=us.user_id " +
                " inner join mst_staff sf on v.visit_staff_id=sf.staff_id " +
                " inner join mst_user su on sf.staff_user_id=su.user_id " +
                " inner join mst_staff ff on tb.bill_user_id=ff.staff_id " +
                " inner join mst_user uf on ff.staff_user_id=uf.user_id where (date(tbs.created_date) between '" + fromdate + "' and '" + todate + "'  ) ";
        System.out.println("Query1 : " + Query1);
        CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        columnName = "unit_name,patient_mr_no,patientname,bill_number,bill_date,service_name,serviceAmount,tariff_name,ConsultDoctor,BillMadeBystaff";
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query1).getResultList();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query1, CountQuery));
        return createReport.generateExcel(columnName, "OpdServiceBillListDailyReport", jsonArray);
    }

}
