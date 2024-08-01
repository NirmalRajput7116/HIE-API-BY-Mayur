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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_patient_wise_bill_report")
public class PatientWiseBillReportController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateReport createReport;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    MstUserRepository mstUserRepository;

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getPatientWiseBillReport/{unitList}/{mstuserlist}/{fromdate}/{todate}/{IPDFlag}")
    public ResponseEntity searchPatientWiseBill(@RequestHeader("X-tenantId") String tenantName, @RequestBody PatientWiseSearchDTO patientWiseSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<PatientWiseBillListDTO> patientWiseBillListDTOArr = new ArrayList<PatientWiseBillListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String Query2 = " ";
        String Query1 = " ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query += " SELECT IFNULL(CONCAT(mt.title_name, ' ', mu.user_firstname,' ',mu.user_lastname),'') AS  patientName, " +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') AS billNo, " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.ipd_bill = 0,'OPD','IPD') AS classType," +
                    " IF(mv.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
                    " IFNULL(tb.bill_amount_paid,0) AS paid, IFNULL(tb.bill_outstanding,0) AS balance, " +
                    " IFNULL(CONCAT(mt1.title_name, ' ', mu1.user_firstname,' ',mu1.user_lastname),'') AS userName, " +
                    " IFNULL(mun.unit_name,'') AS unitName " +
                    " FROM tbill_bill tb " +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id " +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " LEFT JOIN mst_staff ms1 ON tb.bill_user_id = ms1.staff_id " +
                    " LEFT JOIN mst_user mu1 ON ms1.staff_user_id = mu1.user_id " +
                    " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                    " LEFT JOIN mst_gender mg1 ON mu1.user_gender_id = mg1.gender_id " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 " +
                    " AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
            Query1 += " SELECT SUM(tb.gross_amount) AS GrossTotal," +
                    " SUM(tb.bill_discount_amount) AS DiscountTotal," +
                    " SUM(tb.bill_net_payable) AS NetTotal," +
                    " SUM(tb.company_net_pay) AS CreditTotal," +
                    " SUM(tb.bill_amount_paid) AS PaidTotal," +
                    " SUM(tb.bill_outstanding) AS BalanceTotal " +
                    " FROM tbill_bill tb " +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 " +
                    " AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += " SELECT IFNULL(CONCAT(mt.title_name, ' ', mu.user_firstname,' ',mu.user_lastname),'') AS  patientName, " +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') AS billNo,  " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.ipd_bill = 0,'OPD','IPD') AS classType, " +
                    " IF(ta.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
                    " IFNULL(tb.bill_amount_paid,0) AS paid, IFNULL(tb.bill_outstanding,0) AS balance, " +
                    " IFNULL(CONCAT(mt1.title_name, ' ', mu1.user_firstname,' ',mu1.user_lastname),'') AS userName, " +
                    " IFNULL(mun.unit_name,'') AS unitName " +
                    " FROM tbill_bill tb " +
                    " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                    " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " LEFT JOIN mst_staff ms1 ON tb.bill_user_id = ms1.staff_id " +
                    " LEFT JOIN mst_user mu1 ON ms1.staff_user_id = mu1.user_id " +
                    " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                    " LEFT JOIN mst_gender mg1 ON mu1.user_gender_id = mg1.gender_id  " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id  " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
            Query1 += " SELECT SUM(tb.gross_amount) AS GrossTotal,  " +
                    "SUM(tb.bill_discount_amount) AS DiscountTotal,  " +
                    "SUM(tb.bill_net_payable) AS NetTotal,  " +
                    "SUM(tb.company_net_pay) AS CreditTotal,  " +
                    "SUM(tb.bill_amount_paid) AS PaidTotal,  " +
                    "SUM(tb.bill_outstanding) AS BalanceTotal  " +
                    "FROM tbill_bill tb  " +
                    "WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0  AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
        } else {
            Query += " SELECT IFNULL(CONCAT(mt.title_name, ' ', mu.user_firstname,' ',mu.user_lastname),'') AS  patientName, " +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') AS billNo,  " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.emrbill = 1,'EMG','') AS classType, " +
                    " IF(mv.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
                    "  IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
                    " IFNULL(tb.bill_amount_paid,0) AS paid, IFNULL(tb.bill_outstanding,0) AS balance, " +
                    " IFNULL(CONCAT(mt1.title_name, ' ', mu1.user_firstname,' ',mu1.user_lastname),'') AS userName, " +
                    " IFNULL(mun.unit_name,'') AS unitName " +
                    " FROM tbill_bill tb " +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id " +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " LEFT JOIN mst_staff ms1 ON tb.bill_user_id = ms1.staff_id " +
                    " LEFT JOIN mst_user mu1 ON ms1.staff_user_id = mu1.user_id " +
                    " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                    " LEFT JOIN mst_gender mg1 ON mu1.user_gender_id = mg1.gender_id  " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id  " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 " +
                    " AND tb.emrbill = 1 ";
            Query1 += " SELECT SUM(tb.gross_amount) AS GrossTotal,  " +
                    "SUM(tb.bill_discount_amount) AS DiscountTotal,  " +
                    "SUM(tb.bill_net_payable) AS NetTotal,  " +
                    "SUM(tb.company_net_pay) AS CreditTotal,  " +
                    "SUM(tb.bill_amount_paid) AS PaidTotal,  " +
                    "SUM(tb.bill_outstanding) AS BalanceTotal  " +
                    "FROM tbill_bill tb  " +
                    "WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0  " +
                    "AND tb.emrbill = 1 ";
        }

       /* if (patientWiseSearchDTO.getFromdate().equals("") || patientWiseSearchDTO.getFromdate().equals("null")) {
            patientWiseSearchDTO.setFromdate( "1990-06-07");
        }
        if (patientWiseSearchDTO.getTodate().equals("") || patientWiseSearchDTO.getTodate().equals("null")) {
            patientWiseSearchDTO.setTodate(strDate);
        }

        Query += " and (date(tb.created_date) between '" + patientWiseSearchDTO.getFromdate() + "' and '" + patientWiseSearchDTO.getTodate() + "')  ";
        Query1 += " and (date(tb.created_date) between '" + patientWiseSearchDTO.getFromdate() + "' and '" + patientWiseSearchDTO.getTodate() + "')  ";*/
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (patientWiseSearchDTO.getTodaydate()) {
            Query += " and (date(tb.created_date)=curdate()) ";
            Query1 += " and (date(tb.created_date)=curdate()) ";

        } else {
            Query += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query1 += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
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
            Query += " and tb.bill_user_id in (" + values + ") ";
            Query1 += " and tb.bill_user_id in (" + values + ") ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "patientName,mrNo,billNo,billDate,classType,billType,companyName,grossAmt,discount,netAmount,credit,paid,balance,userName,unitName";
        Query += "  limit " + ((patientWiseSearchDTO.getOffset() - 1) * patientWiseSearchDTO.getLimit()) + "," + patientWiseSearchDTO.getLimit();
        System.out.println("MainQuery : " + Query);
        System.out.println("CountQuery : " + CountQuery);
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        count = cc.longValue();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        List<Object[]> summList = (List<Object[]>) entityManager.createNativeQuery(Query1).getResultList();
        try {
            for (Object[] ob : summList) {
                jsonArray.getJSONObject(0).put("GrossTotal", ob[0].toString());
                jsonArray.getJSONObject(0).put("DiscountTotal", ob[1].toString());
                jsonArray.getJSONObject(0).put("NetTotal", ob[2].toString());
                jsonArray.getJSONObject(0).put("CreditTotal", ob[3].toString());
                jsonArray.getJSONObject(0).put("PaidTotal", ob[4].toString());
                jsonArray.getJSONObject(0).put("BalanceTotal", ob[5].toString());

            }
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        return ResponseEntity.ok(jsonArray.toString());
//        return patientWiseBillListDTOArr;
    }
    // Method For Patient Wise Bill Report End

    @RequestMapping("getPatientWiseBillReportPrint/{unitList}/{mstuserlist}/{fromdate}/{todate}/{IPDFlag}")
    public String searchPatientWiseBillPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody PatientWiseSearchDTO patientWiseSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<PatientWiseBillListDTO> patientWiseBillListDTOArr = new ArrayList<PatientWiseBillListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " ";
        String Query2 = " ";
        String Query1 = " ";
        String CountQuery = "";
        String columnName = "";
        if (IPDFlag == 0) {
            Query += " SELECT IFNULL(CONCAT(mt.title_name, ' ', mu.user_firstname,' ',mu.user_lastname),'') AS  patientName, " +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') AS billNo, " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.ipd_bill = 0,'OPD','IPD') AS classType," +
                    " IF(mv.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
                    " IFNULL(tb.bill_amount_paid,0) AS paid, IFNULL(tb.bill_outstanding,0) AS balance, " +
                    " IFNULL(CONCAT(mt1.title_name, ' ', mu1.user_firstname,' ',mu1.user_lastname),'') AS userName, " +
                    " IFNULL(mun.unit_name,'') AS unitName " +
                    " FROM tbill_bill tb " +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id " +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " LEFT JOIN mst_staff ms1 ON tb.bill_user_id = ms1.staff_id " +
                    " LEFT JOIN mst_user mu1 ON ms1.staff_user_id = mu1.user_id " +
                    " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                    " LEFT JOIN mst_gender mg1 ON mu1.user_gender_id = mg1.gender_id " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 " +
                    " AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
            Query1 += " SELECT SUM(tb.gross_amount) AS GrossTotal," +
                    " SUM(tb.bill_discount_amount) AS DiscountTotal," +
                    " SUM(tb.bill_net_payable) AS NetTotal," +
                    " SUM(tb.company_net_pay) AS CreditTotal," +
                    " SUM(tb.bill_amount_paid) AS PaidTotal," +
                    " SUM(tb.bill_outstanding) AS BalanceTotal " +
                    " FROM tbill_bill tb " +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 " +
                    " AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += " SELECT IFNULL(CONCAT(mt.title_name, ' ', mu.user_firstname,' ',mu.user_lastname),'') AS  patientName, " +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') AS billNo,  " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.ipd_bill = 0,'OPD','IPD') AS classType, " +
                    " IF(ta.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
                    " IFNULL(tb.bill_amount_paid,0) AS paid, IFNULL(tb.bill_outstanding,0) AS balance, " +
                    " IFNULL(CONCAT(mt1.title_name, ' ', mu1.user_firstname,' ',mu1.user_lastname),'') AS userName, " +
                    " IFNULL(mun.unit_name,'') AS unitName " +
                    " FROM tbill_bill tb " +
                    " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                    " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " LEFT JOIN mst_staff ms1 ON tb.bill_user_id = ms1.staff_id " +
                    " LEFT JOIN mst_user mu1 ON ms1.staff_user_id = mu1.user_id " +
                    " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                    " LEFT JOIN mst_gender mg1 ON mu1.user_gender_id = mg1.gender_id  " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id  " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
            Query1 += " SELECT SUM(tb.gross_amount) AS GrossTotal,  " +
                    "SUM(tb.bill_discount_amount) AS DiscountTotal,  " +
                    "SUM(tb.bill_net_payable) AS NetTotal,  " +
                    "SUM(tb.company_net_pay) AS CreditTotal,  " +
                    "SUM(tb.bill_amount_paid) AS PaidTotal,  " +
                    "SUM(tb.bill_outstanding) AS BalanceTotal  " +
                    "FROM tbill_bill tb  " +
                    "WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0  AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
        } else {
            Query += " SELECT IFNULL(CONCAT(mt.title_name, ' ', mu.user_firstname,' ',mu.user_lastname),'') AS  patientName, " +
                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') AS billNo,  " +
                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.emrbill = 1,'EMG','') AS classType, " +
                    " IF(mv.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
                    "  IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
                    " IFNULL(tb.bill_amount_paid,0) AS paid, IFNULL(tb.bill_outstanding,0) AS balance, " +
                    " IFNULL(CONCAT(mt1.title_name, ' ', mu1.user_firstname,' ',mu1.user_lastname),'') AS userName, " +
                    " IFNULL(mun.unit_name,'') AS unitName " +
                    " FROM tbill_bill tb " +
                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id " +
                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                    " LEFT JOIN mst_staff ms1 ON tb.bill_user_id = ms1.staff_id " +
                    " LEFT JOIN mst_user mu1 ON ms1.staff_user_id = mu1.user_id " +
                    " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                    " LEFT JOIN mst_gender mg1 ON mu1.user_gender_id = mg1.gender_id  " +
                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id  " +
                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 " +
                    " AND tb.emrbill = 1 ";
            Query1 += " SELECT SUM(tb.gross_amount) AS GrossTotal,  " +
                    "SUM(tb.bill_discount_amount) AS DiscountTotal,  " +
                    "SUM(tb.bill_net_payable) AS NetTotal,  " +
                    "SUM(tb.company_net_pay) AS CreditTotal,  " +
                    "SUM(tb.bill_amount_paid) AS PaidTotal,  " +
                    "SUM(tb.bill_outstanding) AS BalanceTotal  " +
                    "FROM tbill_bill tb  " +
                    "WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0  " +
                    "AND tb.emrbill = 1 ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (patientWiseSearchDTO.getTodaydate()) {
            Query += " and (date(tb.created_date)=curdate()) ";
            Query1 += " and (date(tb.created_date)=curdate()) ";

        } else {
            Query += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query1 += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
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
            Query += " and tb.bill_user_id in (" + values + ") ";
            Query1 += " and tb.bill_user_id in (" + values + ") ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "patientName,mrNo,billNo,billDate,classType,billType,companyName,grossAmt,discount,netAmount,credit,paid,balance,userName,unitName";
        // Query += "  limit " + ((patientWiseSearchDTO.getOffset() - 1) * patientWiseSearchDTO.getLimit()) + "," + patientWiseSearchDTO.getLimit();
        System.out.println("MainQuery : " + Query);
        System.out.println("CountQuery : " + CountQuery);
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        count = cc.longValue();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        List<Object[]> summList = (List<Object[]>) entityManager.createNativeQuery(Query1).getResultList();
        try {
            for (Object[] ob : summList) {
                jsonArray.getJSONObject(0).put("GrossTotal", ob[0].toString());
                jsonArray.getJSONObject(0).put("DiscountTotal", ob[1].toString());
                jsonArray.getJSONObject(0).put("NetTotal", ob[2].toString());
                jsonArray.getJSONObject(0).put("CreditTotal", ob[3].toString());
                jsonArray.getJSONObject(0).put("PaidTotal", ob[4].toString());
                jsonArray.getJSONObject(0).put("BalanceTotal", ob[5].toString());

            }
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        //   return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(patientWiseSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(patientWiseSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (patientWiseSearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("PatientWiseBillReport", jsonArray.toString());
        } else {
            return createReport.createJasperReportPDFByJson("PatientWiseBillReport", jsonArray.toString());
        }
//        return patientWiseBillListDTOArr;
    }
    // Method For Patient Wise Bill Report End
//    @RequestMapping("getPatientWiseBillReportPrint/{unitList}/{mstuserlist}/{fromdate}/{todate}/{IPDFlag}")
//    public ResponseEntity searchPatientWiseBillPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody PatientWiseSearchDTO patientWiseSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String fromdate, @PathVariable String todate, @PathVariable int IPDFlag) {
//        long count = 0;
//        List<PatientWiseBillListDTO> patientWiseBillListDTOArr = new ArrayList<PatientWiseBillListDTO>();
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = formatter.format(date);
//        String Query = " ";
//
//        String Query2 = " ";
//        String Query1 = " ";
//
//        String CountQuery = "";
//        String columnName = "";
//
//        if (IPDFlag == 0) {
//            Query += " SELECT IFNULL(CONCAT(mt.title_name, ' ', mu.user_firstname,' ',mu.user_lastname),'') AS  patientName, " +
//                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') AS billNo, " +
//                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.ipd_bill = 0,'OPD','IPD') AS classType," +
//                    " IF(mv.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
//                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
//                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
//                    " IFNULL(tb.bill_amount_paid,0) AS paid, IFNULL(tb.bill_outstanding,0) AS balance, " +
//                    " IFNULL(CONCAT(mt1.title_name, ' ', mu1.user_firstname,' ',mu1.user_lastname),'') AS userName, " +
//                    " IFNULL(mun.unit_name,'') AS unitName " +
//                    " FROM tbill_bill tb " +
//                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id " +
//                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
//                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
//                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
//                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
//                    " LEFT JOIN mst_staff ms1 ON tb.bill_user_id = ms1.staff_id " +
//                    " LEFT JOIN mst_user mu1 ON ms1.staff_user_id = mu1.user_id " +
//                    " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
//                    " LEFT JOIN mst_gender mg1 ON mu1.user_gender_id = mg1.gender_id " +
//                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
//                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id " +
//                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
//                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
//                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 " +
//                    " AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
//
//            Query1 += " SELECT SUM(tb.gross_amount) AS GrossTotal," +
//                    " SUM(tb.bill_discount_amount) AS DiscountTotal," +
//                    " SUM(tb.bill_net_payable) AS NetTotal," +
//                    " SUM(tb.company_net_pay) AS CreditTotal," +
//                    " SUM(tb.bill_amount_paid) AS PaidTotal," +
//                    " SUM(tb.bill_outstanding) AS BalanceTotal " +
//                    " FROM tbill_bill tb " +
//                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 " +
//                    " AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
//
//        } else if (IPDFlag == 1){
//            Query += " SELECT IFNULL(CONCAT(mt.title_name, ' ', mu.user_firstname,' ',mu.user_lastname),'') AS  patientName, " +
//                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') AS billNo,  " +
//                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.ipd_bill = 0,'OPD','IPD') AS classType, " +
//                    " IF(ta.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
//                    " IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
//                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
//                    " IFNULL(tb.bill_amount_paid,0) AS paid, IFNULL(tb.bill_outstanding,0) AS balance, " +
//                    " IFNULL(CONCAT(mt1.title_name, ' ', mu1.user_firstname,' ',mu1.user_lastname),'') AS userName, " +
//                    " IFNULL(mun.unit_name,'') AS unitName " +
//                    " FROM tbill_bill tb " +
//                    " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
//                    " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
//                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
//                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
//                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
//                    " LEFT JOIN mst_staff ms1 ON tb.bill_user_id = ms1.staff_id " +
//                    " LEFT JOIN mst_user mu1 ON ms1.staff_user_id = mu1.user_id " +
//                    " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
//                    " LEFT JOIN mst_gender mg1 ON mu1.user_gender_id = mg1.gender_id  " +
//                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
//                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id  " +
//                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
//                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
//                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
//
//            Query1 += " SELECT SUM(tb.gross_amount) AS GrossTotal,  " +
//                    "SUM(tb.bill_discount_amount) AS DiscountTotal,  " +
//                    "SUM(tb.bill_net_payable) AS NetTotal,  " +
//                    "SUM(tb.company_net_pay) AS CreditTotal,  " +
//                    "SUM(tb.bill_amount_paid) AS PaidTotal,  " +
//                    "SUM(tb.bill_outstanding) AS BalanceTotal  " +
//                    "FROM tbill_bill tb  " +
//                    "WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0  AND tb.emrbill = 0 AND tb.ipd_bill = " + IPDFlag;
//        }
//        else {
//            Query += " SELECT IFNULL(CONCAT(mt.title_name, ' ', mu.user_firstname,' ',mu.user_lastname),'') AS  patientName, " +
//                    " IFNULL(mp.patient_mr_no,'') AS mrNo, IFNULL(tb.bill_number,'') AS billNo,  " +
//                    " IFNULL(tb.created_date,'') AS billDate, IF(tb.emrbill = 1,'EMG','') AS classType, " +
//                    " IF(mv.sponsor_combination = 1,'Self','Insurance') AS billType, IFNULL(mcom.company_name,'') AS companyName, " +
//                    "  IFNULL(tb.gross_amount,0) AS grossAmt, IFNULL(tb.bill_discount_amount,0) AS discount, " +
//                    " IFNULL(tb.bill_net_payable,0) AS netAmount, IFNULL(tb.company_net_pay,0) AS credit, " +
//                    " IFNULL(tb.bill_amount_paid,0) AS paid, IFNULL(tb.bill_outstanding,0) AS balance, " +
//                    " IFNULL(CONCAT(mt1.title_name, ' ', mu1.user_firstname,' ',mu1.user_lastname),'') AS userName, " +
//                    " IFNULL(mun.unit_name,'') AS unitName " +
//                    " FROM tbill_bill tb " +
//                    " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id " +
//                    " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
//                    " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
//                    " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
//                    " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
//                    " LEFT JOIN mst_staff ms1 ON tb.bill_user_id = ms1.staff_id " +
//                    " LEFT JOIN mst_user mu1 ON ms1.staff_user_id = mu1.user_id " +
//                    " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
//                    " LEFT JOIN mst_gender mg1 ON mu1.user_gender_id = mg1.gender_id  " +
//                    " INNER JOIN mst_unit mun ON tb.tbill_unit_id = mun.unit_id " +
//                    " LEFT JOIN trn_bill_bill_sponsor tbsp ON tb.bill_id = tbsp.bbs_bill_id  " +
//                    " LEFT JOIN trn_sponsor_combination tspcom ON tbsp.bbsscid = tspcom.sc_id " +
//                    " LEFT JOIN mst_company mcom ON tspcom.sc_company_id = mcom.company_id " +
//                    " WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0 " +
//                    " AND tb.emrbill = 1 " ;
//
//            Query1 += " SELECT SUM(tb.gross_amount) AS GrossTotal,  " +
//                    "SUM(tb.bill_discount_amount) AS DiscountTotal,  " +
//                    "SUM(tb.bill_net_payable) AS NetTotal,  " +
//                    "SUM(tb.company_net_pay) AS CreditTotal,  " +
//                    "SUM(tb.bill_amount_paid) AS PaidTotal,  " +
//                    "SUM(tb.bill_outstanding) AS BalanceTotal  " +
//                    "FROM tbill_bill tb  " +
//                    "WHERE tb.is_active = 1 AND tb.is_deleted = 0 AND tb.is_cancelled = 0  " +
//                    "AND tb.emrbill = 1 ";
//        }
//
//        if (fromdate.equals("") || fromdate.equals("null")) {
//            fromdate = "1990-06-07";
//        }
//        if (todate.equals("") || todate.equals("null")) {
//            todate = strDate;
//        }
//
//        Query += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
//        Query1 += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
//
//        if (!unitList[0].equals("null")) {
//            String values = String.valueOf(unitList[0]);
//            for (int i = 1; i < unitList.length; i++) {
//                values += "," + unitList[i];
//            }
//            Query += " and tb.tbill_unit_id in (" + values + ") ";
//            Query1 += " and tb.tbill_unit_id in (" + values + ") ";
//        }
//
//        if (!mstuserlist[0].equals("null")) {
//            String values = String.valueOf(mstuserlist[0]);
//            for (int i = 1; i < mstuserlist.length; i++) {
//                values += "," + mstuserlist[i];
//            }
//            Query += " and tb.bill_user_id in (" + values + ") ";
//            Query1 += " and tb.bill_user_id in (" + values + ") ";
//        }
//
//
//        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
//        columnName = "patientName,mrNo,billNo,billDate,classType,billType,companyName,grossAmt,discount,netAmount,credit,paid,balance,userName,unitName";
//        Query += "  limit " + ((patientWiseSearchDTO.getOffset() - 1) * patientWiseSearchDTO.getLimit()) + "," + patientWiseSearchDTO.getLimit();
//
//
//
//        System.out.println("MainQuery : " + Query);
//        System.out.println("CountQuery : " + CountQuery);
//        BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
//        count = cc.longValue();
//
//
//        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
//
////        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(Query).getResultList();
////
////        System.out.println("jsonArray : " + jsonArray.toString());
////
////        for (Object[] obj : list) {
////            PatientWiseBillListDTO patientWiseBillListDTO = new PatientWiseBillListDTO();
////            patientWiseBillListDTO.setPatientName("" + obj[0]);
////            patientWiseBillListDTO.setMrNo("" + obj[1]);
////            patientWiseBillListDTO.setBillNo("" + obj[2]);
////            patientWiseBillListDTO.setBillDate("" + obj[3]);
////            patientWiseBillListDTO.setClassType("" + obj[4]);
////            patientWiseBillListDTO.setBillType("" + obj[5]);
////            patientWiseBillListDTO.setCompanyName("" + obj[6]);
////            patientWiseBillListDTO.setGrossAmt("" + obj[7]);
////            patientWiseBillListDTO.setDiscount("" + obj[8]);
////            patientWiseBillListDTO.setNetAmount("" + obj[9]);
////            patientWiseBillListDTO.setCredit("" + obj[10]);
////            patientWiseBillListDTO.setPaid("" + obj[11]);
////            patientWiseBillListDTO.setBalance("" + obj[12]);
////            patientWiseBillListDTO.setUserName("" + obj[13]);
////            patientWiseBillListDTO.setUnitName("" + obj[14]);
////            patientWiseBillListDTO.setCount(count);
////            patientWiseBillListDTOArr.add(patientWiseBillListDTO);
////        }
////
//        List<Object[]> summList = (List<Object[]>) entityManager.createNativeQuery(Query1).getResultList();
//
//        try {
//            for (Object[] ob : summList) {
//                jsonArray.getJSONObject(0).put("GrossTotal", ob[0].toString());
//                jsonArray.getJSONObject(0).put("DiscountTotal", ob[1].toString());
//                jsonArray.getJSONObject(0).put("NetTotal", ob[2].toString());
//                jsonArray.getJSONObject(0).put("CreditTotal", ob[3].toString());
//                jsonArray.getJSONObject(0).put("PaidTotal", ob[4].toString());
//                jsonArray.getJSONObject(0).put("BalanceTotal", ob[5].toString());
//
//            }
//        } catch (Exception e) {
//            // e.printStackTrace();
//            // System.out.println("Error:" + e);
//        }
//
//        return ResponseEntity.ok(jsonArray.toString());
////        return patientWiseBillListDTOArr;
//
//    }
//    // Method For Patient Wise Bill Report End
}
