package com.cellbeans.hspa.misbillcumtestreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/misbillcumtestreport")
public class BillCumTestReportController {

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

    // Bill cum test Report start 16.10.2019
    @RequestMapping("billcumtestreport/{unitList}/{fromdate}/{todate}")
    public List<BillCumTestReportListDTO> searchListofgetBillCumTestReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody BillCumTestReportSearchDTO billCumTestReportSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        double totalGross = 0.0;
        double totalDiscount = 0.0;
        double totalNet = 0.0;
        double totalPaid = 0.0;
        double totalOutstanding = 0.0;
        double testAmount = 0.0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        List<BillCumTestReportListDTO> listOfBillCumTestDTOList = new ArrayList<BillCumTestReportListDTO>();
        String Query1 = " SELECT DISTINCT IFNULL(mp.patient_mr_no,'') AS PatientId, IFNULL(t.ps_performed_by_date,'') AS PerformedDate, " +
                " tb.bill_number AS BillNo, IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS PatientName, " +
                " IFNULL(mu.user_age,'') AS PatientAge, IFNULL(mgen.gender_name,'') AS PatientGender, IFNULL(mu.user_mobile,'') AS Contact, " +
                " IFNULL(mtest.test_name,'') AS TestName, IFNULL(re.re_name,'') AS Referral, IF(t.isipd=0,\"OPD\",\"IPD\") AS Services, " +
                " IF(mv.sponsor_combination='1',\"CASH\",\"CREDIT\") AS PaymentType, IFNULL(t.is_performed_by_unit_name,'') AS Organization, " +
                " tb.bill_sub_total AS Gross, tb.bill_discount_amount AS Discount, tb.bill_net_payable AS Net, tb.bill_amount_paid AS Paid, " +
                " tb.bill_outstanding AS Outstanding, IF(t.is_sample_outsource=1,\"Yes\",\"No\") AS Outsourced, IFNULL(tb.created_date,'') AS AccessionDate, " +
                " IFNULL(t.ps_sample_collection_date,'') AS SampleCollectionDate, IFNULL(t.ps_sample_accepted_date,'') AS SampleAcceptanceDate, " +
                " IFNULL(t.ps_report_delivery_date,'') AS SubmissionDate, IFNULL(t.ps_finalized_date,'') AS FinalizedDate, " +
                " CASE WHEN t.is_report_delivered = 1 THEN 'Submitted' WHEN t.is_finalized = 1 THEN 'Finalized' WHEN t.is_result_entry = 1 THEN 'Saved' " +
                " WHEN t.is_sample_accepted = 1 THEN 'Sample Accepted' WHEN t.is_sample_rejected = 1 THEN 'Sample Rejected' WHEN t.is_sample_collected = 1 " +
                " THEN 'Sample Collected' WHEN t.is_lab_order_acceptance = 1 THEN 'LO Accepted' ELSE \"LO Not Accepted\" END testStatus, " +
                " IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectionName, IFNULL(t.is_sample_accepted_by_name,'') AS SampleAcceptanceName, " +
                " IFNULL(t.is_report_delivered_by_name,'') AS SubmissionName, IFNULL(t.is_finalized_by_name,'') AS FinalizedName, " +
                " IFNULL(tbs.bs_gross_rate,'') AS TestAmount " +
                " FROM tpath_bs t " +
                " INNER JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " INNER JOIN tbill_bill_service tbs ON tbs.bs_bill_id = tb.bill_id " +
                " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id " +
                " LEFT JOIN mst_referring_entity re ON mv.refer_by = re.re_id " +
                " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_gender mgen ON mu.user_gender_id = mgen.gender_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.is_active = 1 AND t.is_deleted = 0  and t.isipd = 0 and t.ps_bill_id is not null ";
        String Query2 = " SELECT DISTINCT IFNULL(mp.patient_mr_no,'') AS PatientId, IFNULL(t.ps_performed_by_date,'') AS PerformedDate, " +
                " tb.bill_number AS BillNo, IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS PatientName, " +
                " IFNULL(mu.user_age,'') AS PatientAge, IFNULL(mgen.gender_name,'') AS PatientGender, IFNULL(mu.user_mobile,'') AS Contact, " +
                " IFNULL(mtest.test_name,'') AS TestName, IFNULL(re.re_name,'') AS Referral, IF(t.isipd=0,\"OPD\",\"IPD\") AS Services, " +
                " IF(ta.sponsor_combination='1',\"CASH\",\"CREDIT\") AS PaymentType, IFNULL(t.is_performed_by_unit_name,'') AS Organization, " +
                " tb.bill_sub_total AS Gross, tb.bill_discount_amount AS Discount, tb.bill_net_payable AS Net, tb.bill_amount_paid AS Paid, " +
                " tb.bill_outstanding AS Outstanding, IF(t.is_sample_outsource=1,\"Yes\",\"No\") AS Outsourced, IFNULL(tb.created_date,'') AS AccessionDate, " +
                " IFNULL(t.ps_sample_collection_date,'') AS SampleCollectionDate, IFNULL(t.ps_sample_accepted_date,'') AS SampleAcceptanceDate, " +
                " IFNULL(t.ps_report_delivery_date,'') AS SubmissionDate, IFNULL(t.ps_finalized_date,'') AS FinalizedDate, CASE WHEN t.is_report_delivered = 1 " +
                " THEN 'Submitted' WHEN t.is_finalized = 1 THEN 'Finalized' WHEN t.is_result_entry = 1 THEN 'Saved' WHEN t.is_sample_accepted = 1 " +
                " THEN 'Sample Accepted' WHEN t.is_sample_rejected = 1 THEN 'Sample Rejected' WHEN t.is_sample_collected = 1 THEN 'Sample Collected' " +
                " WHEN t.is_lab_order_acceptance = 1 THEN 'LO Accepted' ELSE \"LO Not Accepted\" END testStatus, " +
                " IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectionName, IFNULL(t.is_sample_accepted_by_name,'') AS SampleAcceptanceName, " +
                " IFNULL(t.is_report_delivered_by_name,'') AS SubmissionName, IFNULL(t.is_finalized_by_name,'') AS FinalizedName, " +
                " IFNULL(tbs.bs_gross_rate,'') AS TestAmount " +
                " FROM tpath_bs t " +
                " INNER JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " INNER JOIN tbill_bill_service tbs ON tbs.bs_bill_id = tb.bill_id " +
                " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                " LEFT JOIN mst_referring_entity re ON ta.admission_re_id = re.re_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_gender mgen ON mu.user_gender_id = mgen.gender_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.is_active = 1 AND t.is_deleted = 0 and t.isipd = 1 and t.ps_bill_id is not null ";
        String Query3 = " SELECT DISTINCT IFNULL(mp.patient_mr_no,'') AS PatientId, IFNULL(t.ps_performed_by_date,'') AS PerformedDate, " +
                " mic.charge_number AS BillNo, IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS PatientName, " +
                " IFNULL(mu.user_age,'') AS PatientAge, IFNULL(mgen.gender_name,'') AS PatientGender, IFNULL(mu.user_mobile,'') AS Contact, " +
                " IFNULL(mtest.test_name,'') AS TestName, IFNULL(re.re_name,'') AS Referral, IF(t.isipd=0,\"OPD\",\"IPD\") AS Services, " +
                " IF(ta.sponsor_combination='1',\"CASH\",\"CREDIT\") AS PaymentType, IFNULL(t.is_performed_by_unit_name,'') AS Organization, " +
                " mic.charge_sub_total AS Gross, mic.charge_discount_amount AS Discount, mic.charge_net_payable AS Net, " +
                " mic.charge_amount_tobe_paid AS Paid, mic.charge_outstanding AS Outstanding, IF(t.is_sample_outsource=1,\"Yes\",\"No\") AS Outsourced, " +
                " IFNULL(mic.created_date,'') AS AccessionDate, IFNULL(t.ps_sample_collection_date,'') AS SampleCollectionDate, " +
                " IFNULL(t.ps_sample_accepted_date,'') AS SampleAcceptanceDate, IFNULL(t.ps_report_delivery_date,'') AS SubmissionDate, " +
                " IFNULL(t.ps_finalized_date,'') AS FinalizedDate, CASE WHEN t.is_report_delivered = 1 THEN 'Submitted' WHEN t.is_finalized = 1 " +
                " THEN 'Finalized' WHEN t.is_result_entry = 1 THEN 'Saved' WHEN t.is_sample_accepted = 1 THEN 'Sample Accepted' WHEN t.is_sample_rejected = 1 " +
                " THEN 'Sample Rejected' WHEN t.is_sample_collected = 1 THEN 'Sample Collected' WHEN t.is_lab_order_acceptance = 1 " +
                " THEN 'LO Accepted' ELSE \"LO Not Accepted\" END testStatus, IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectionName, " +
                " IFNULL(t.is_sample_accepted_by_name,'') AS SampleAcceptanceName, IFNULL(t.is_report_delivered_by_name,'') AS SubmissionName, " +
                " IFNULL(t.is_finalized_by_name,'') AS FinalizedName, IFNULL(ics.cs_gross_rate,'') AS TestAmount " +
                " FROM tpath_bs t " +
                " INNER JOIN mbill_ipd_charge mic ON t.mbillipdcharge = mic.ipdcharge_id " +
                " INNER JOIN ipd_charges_service ics ON mic.ipdcharge_id = ics.cs_charge_id " +
                " LEFT JOIN trn_admission ta ON mic.charge_admission_id = ta.admission_id " +
                " LEFT JOIN mst_referring_entity re ON ta.admission_re_id = re.re_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_gender mgen ON mu.user_gender_id = mgen.gender_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.is_active = 1 AND t.is_deleted = 0 and t.isipd = 1 and t.ps_bill_id is null ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (billCumTestReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(t.ps_performed_by_date)=curdate()) ";
            Query2 += " and (date(t.ps_performed_by_date)=curdate()) ";
            Query3 += " and (date(t.ps_performed_by_date)=curdate()) ";
        } else {
            Query1 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query2 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query3 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and t.is_performed_by_unit_id in (" + values + ") ";
            Query2 += " and t.is_performed_by_unit_id in (" + values + ") ";
            Query3 += " and t.is_performed_by_unit_id in (" + values + ") ";
        }
        if (billCumTestReportSearchDTO.getPatientId() != null && !billCumTestReportSearchDTO.getPatientId().equals("0")) {
            Query1 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
            Query2 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
            Query3 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
        }
        if (billCumTestReportSearchDTO.getPatientName() != null && !billCumTestReportSearchDTO.getPatientName().equals("")) {
            Query1 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + billCumTestReportSearchDTO.getPatientName() + "%'";
            Query2 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + billCumTestReportSearchDTO.getPatientName() + "%'";
            Query3 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + billCumTestReportSearchDTO.getPatientName() + "%'";
        }
        if (billCumTestReportSearchDTO.getGenderId() != null && !billCumTestReportSearchDTO.getGenderId().equals("0")) {
            Query1 += " and mu.user_gender_id = " + billCumTestReportSearchDTO.getGenderId() + " ";
            Query2 += " and mu.user_gender_id = " + billCumTestReportSearchDTO.getGenderId() + " ";
            Query3 += " and mu.user_gender_id = " + billCumTestReportSearchDTO.getGenderId() + " ";
        }
        if (billCumTestReportSearchDTO.getReferral() != null && !billCumTestReportSearchDTO.getReferral().equals("")) {
            Query1 += " and re.re_name LIKE '%" + billCumTestReportSearchDTO.getReferral() + "%'";
            Query2 += " and re.re_name LIKE '%" + billCumTestReportSearchDTO.getReferral() + "%'";
            Query3 += " and re.re_name LIKE '%" + billCumTestReportSearchDTO.getReferral() + "%'";
        }
        if (billCumTestReportSearchDTO.getBillNo() != null && !billCumTestReportSearchDTO.getBillNo().equals("")) {
            Query1 += " and tb.bill_number LIKE '%" + billCumTestReportSearchDTO.getBillNo() + "%'";
            Query2 += " and tb.bill_number LIKE '%" + billCumTestReportSearchDTO.getBillNo() + "%'";
            Query3 += " and mic.charge_number LIKE '%" + billCumTestReportSearchDTO.getBillNo() + "%'";
        }
        if (billCumTestReportSearchDTO.getTestName() != null && !billCumTestReportSearchDTO.getTestName().equals("")) {
            Query1 += " and mtest.test_name LIKE '%" + billCumTestReportSearchDTO.getTestName() + "%'";
            Query2 += " and mtest.test_name LIKE '%" + billCumTestReportSearchDTO.getTestName() + "%'";
            Query3 += " and mtest.test_name LIKE '%" + billCumTestReportSearchDTO.getTestName() + "%'";
        }
        if (billCumTestReportSearchDTO.getServiceType() == 0) {
            MainQuery = Query1 + " ORDER BY SubmissionDate DESC";
        } else if (billCumTestReportSearchDTO.getServiceType() == 1) {
            MainQuery = Query2 + " UNION ALL " + Query3 + " GROUP BY t.ps_id ORDER BY SubmissionDate DESC";
        } else {
            MainQuery = Query1 + " UNION ALL " + Query2 + " UNION ALL " + Query3 + " GROUP BY t.ps_id ORDER BY SubmissionDate DESC";
        }
        String CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        String SummaryQuery = " select IFNULL(sum(Gross),0.0) AS totalgross, IFNULL(sum(Discount),0.0) AS totalDiscount, IFNULL(sum(Net),0.0) AS totalNet, IFNULL(sum(Paid),0.0) AS totalPaid,  IFNULL(sum(Outstanding),0.0) AS totalOutstanding from (" + MainQuery + " ) AS x";
        System.out.println("CountQuery:" + CountQuery);
        System.out.println("SummaryQuery:" + SummaryQuery);
        try {
            MainQuery += " limit " + ((billCumTestReportSearchDTO.getOffset() - 1) * billCumTestReportSearchDTO.getLimit()) + "," + billCumTestReportSearchDTO.getLimit();
            System.out.println("All In One Lab Report:" + MainQuery);
            List<Object[]> billCumTestReport = entityManager.createNativeQuery(MainQuery).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            List<Object[]> billCumSummaryReport = entityManager.createNativeQuery(SummaryQuery).getResultList();
            for (Object[] tempObj : billCumSummaryReport) {
                totalGross = Double.parseDouble("" + tempObj[0]);
                totalDiscount = Double.parseDouble("" + tempObj[1]);
                totalNet = Double.parseDouble("" + tempObj[2]);
                totalPaid = Double.parseDouble("" + tempObj[3]);
                totalOutstanding = Double.parseDouble("" + tempObj[4]);
            }
            for (Object[] obj : billCumTestReport) {
                BillCumTestReportListDTO objBillCumTestListDTO = new BillCumTestReportListDTO();
                objBillCumTestListDTO.setPatientId("" + obj[0]);
                objBillCumTestListDTO.setPerformedDate("" + obj[1]);
                objBillCumTestListDTO.setBillNo("" + obj[2]);
                objBillCumTestListDTO.setPatientName("" + obj[3]);
                objBillCumTestListDTO.setPatientAge("" + obj[4]);
                objBillCumTestListDTO.setPatientGender("" + obj[5]);
                objBillCumTestListDTO.setContact("" + obj[6]);
                objBillCumTestListDTO.setTestName("" + obj[7]);
                objBillCumTestListDTO.setReferral("" + obj[8]);
                objBillCumTestListDTO.setServices("" + obj[9]);
                objBillCumTestListDTO.setPaymentType("" + obj[10]);
                objBillCumTestListDTO.setOrganization("" + obj[11]);
                objBillCumTestListDTO.setGross("" + obj[12]);
                objBillCumTestListDTO.setDiscount("" + obj[13]);
                objBillCumTestListDTO.setNet("" + obj[14]);
                objBillCumTestListDTO.setPaid("" + obj[15]);
                objBillCumTestListDTO.setBillOutstanding("" + obj[16]);
                objBillCumTestListDTO.setOutsourced("" + obj[17]);
                objBillCumTestListDTO.setAccessionDate("" + obj[18]);
                objBillCumTestListDTO.setSampleCollectionDate("" + obj[19]);
                objBillCumTestListDTO.setSampleAcceptanceDate("" + obj[20]);
                //objBillCumTestListDTO.setModifiedDate("" + obj[21]);
                objBillCumTestListDTO.setSubmissionDate("" + obj[21]);
                objBillCumTestListDTO.setFinalizedDate("" + obj[22]);
                objBillCumTestListDTO.setTestStatus("" + obj[23]);
                objBillCumTestListDTO.setSampleCollectionName("" + obj[24]);
                objBillCumTestListDTO.setSampleAcceptanceName("" + obj[25]);
                objBillCumTestListDTO.setSubmissionName("" + obj[26]);
                objBillCumTestListDTO.setFinalizedName("" + obj[27]);
                objBillCumTestListDTO.setTestAmount(Double.parseDouble("" + obj[28]));
                objBillCumTestListDTO.setCount(count);        // total count
                objBillCumTestListDTO.setTotalGross(totalGross);
                objBillCumTestListDTO.setTotalDiscount(totalDiscount);
                objBillCumTestListDTO.setTotalNet(totalNet);
                objBillCumTestListDTO.setTotalPaid(totalPaid);
                objBillCumTestListDTO.setTotalOutstanding(totalOutstanding);
                listOfBillCumTestDTOList.add(objBillCumTestListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return listOfBillCumTestDTOList;
    } // END Service
// Bill Cum Test Report End 16.10.2019

    // Bill cum test Report start 16.10.2019
    @RequestMapping("billcumtestreportPrint/{unitList}/{fromdate}/{todate}")
    public String searchListofgetBillCumTestReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody BillCumTestReportSearchDTO billCumTestReportSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        double totalGross = 0.0;
        double totalDiscount = 0.0;
        double totalNet = 0.0;
        double totalPaid = 0.0;
        double totalOutstanding = 0.0;
        double testAmount = 0.0;
        String MainQuery = "";
        //String fromdate = billCumTestReportSearchDTO.getFromdate(),
        //       todate = billCumTestReportSearchDTO.getTodate();
        List<BillCumTestReportListDTO> listOfBillCumTestDTOList = new ArrayList<BillCumTestReportListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " SELECT DISTINCT IFNULL(mp.patient_mr_no,'') AS PatientId, IFNULL(t.ps_performed_by_date,'') AS PerformedDate, " +
                " tb.bill_number AS BillNo, IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS PatientName, " +
                " IFNULL(mu.user_age,'') AS PatientAge, IFNULL(mgen.gender_name,'') AS PatientGender, IFNULL(mu.user_mobile,'') AS Contact, " +
                " IFNULL(mtest.test_name,'') AS TestName, IFNULL(re.re_name,'') AS Referral, IF(t.isipd=0,\"OPD\",\"IPD\") AS Services, " +
                " IF(mv.sponsor_combination='1',\"CASH\",\"CREDIT\") AS PaymentType, IFNULL(t.is_performed_by_unit_name,'') AS Organization, " +
                " tb.bill_sub_total AS Gross, tb.bill_discount_amount AS Discount, tb.bill_net_payable AS Net, tb.bill_amount_paid AS Paid, " +
                " tb.bill_outstanding AS Outstanding, IF(t.is_sample_outsource=1,\"Yes\",\"No\") AS Outsourced, IFNULL(tb.created_date,'') AS AccessionDate, " +
                " IFNULL(t.ps_sample_collection_date,'') AS SampleCollectionDate, IFNULL(t.ps_sample_accepted_date,'') AS SampleAcceptanceDate, " +
                " t.ps_report_delivery_date AS SubmissionDate, IFNULL(t.ps_finalized_date,'') AS FinalizedDate, " +
                " CASE WHEN t.is_report_delivered = 1 THEN 'Submitted' WHEN t.is_finalized = 1 THEN 'Finalized' WHEN t.is_result_entry = 1 THEN 'Saved' " +
                " WHEN t.is_sample_accepted = 1 THEN 'Sample Accepted' WHEN t.is_sample_rejected = 1 THEN 'Sample Rejected' WHEN t.is_sample_collected = 1 " +
                " THEN 'Sample Collected' WHEN t.is_lab_order_acceptance = 1 THEN 'LO Accepted' ELSE \"LO Not Accepted\" END testStatus, " +
                " IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectionName, IFNULL(t.is_sample_accepted_by_name,'') AS SampleAcceptanceName, " +
                " IFNULL(t.is_report_delivered_by_name,'') AS SubmissionName, IFNULL(t.is_finalized_by_name,'') AS FinalizedName, " +
                " IFNULL(tbs.bs_gross_rate,'') AS TestAmount " +
                " FROM tpath_bs t " +
                " INNER JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " INNER JOIN tbill_bill_service tbs ON tbs.bs_bill_id = tb.bill_id " +
                " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id " +
                " LEFT JOIN mst_referring_entity re ON mv.refer_by = re.re_id " +
                " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_gender mgen ON mu.user_gender_id = mgen.gender_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.is_active = 1 AND t.is_deleted = 0  and t.isipd = 0 and t.ps_bill_id is not null ";
        String Query2 = " SELECT DISTINCT IFNULL(mp.patient_mr_no,'') AS PatientId, IFNULL(t.ps_performed_by_date,'') AS PerformedDate, " +
                " tb.bill_number AS BillNo, IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS PatientName, " +
                " IFNULL(mu.user_age,'') AS PatientAge, IFNULL(mgen.gender_name,'') AS PatientGender, IFNULL(mu.user_mobile,'') AS Contact, " +
                " IFNULL(mtest.test_name,'') AS TestName, IFNULL(re.re_name,'') AS Referral, IF(t.isipd=0,\"OPD\",\"IPD\") AS Services, " +
                " IF(ta.sponsor_combination='1',\"CASH\",\"CREDIT\") AS PaymentType, IFNULL(t.is_performed_by_unit_name,'') AS Organization, " +
                " tb.bill_sub_total AS Gross, tb.bill_discount_amount AS Discount, tb.bill_net_payable AS Net, tb.bill_amount_paid AS Paid, " +
                " tb.bill_outstanding AS Outstanding, IF(t.is_sample_outsource=1,\"Yes\",\"No\") AS Outsourced, IFNULL(tb.created_date,'') AS AccessionDate, " +
                " IFNULL(t.ps_sample_collection_date,'') AS SampleCollectionDate, IFNULL(t.ps_sample_accepted_date,'') AS SampleAcceptanceDate, " +
                " t.ps_report_delivery_date AS SubmissionDate, IFNULL(t.ps_finalized_date,'') AS FinalizedDate, CASE WHEN t.is_report_delivered = 1 " +
                " THEN 'Submitted' WHEN t.is_finalized = 1 THEN 'Finalized' WHEN t.is_result_entry = 1 THEN 'Saved' WHEN t.is_sample_accepted = 1 " +
                " THEN 'Sample Accepted' WHEN t.is_sample_rejected = 1 THEN 'Sample Rejected' WHEN t.is_sample_collected = 1 THEN 'Sample Collected' " +
                " WHEN t.is_lab_order_acceptance = 1 THEN 'LO Accepted' ELSE \"LO Not Accepted\" END testStatus, " +
                " IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectionName, IFNULL(t.is_sample_accepted_by_name,'') AS SampleAcceptanceName, " +
                " IFNULL(t.is_report_delivered_by_name,'') AS SubmissionName, IFNULL(t.is_finalized_by_name,'') AS FinalizedName, " +
                " IFNULL(tbs.bs_gross_rate,'') AS TestAmount " +
                " FROM tpath_bs t " +
                " INNER JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " INNER JOIN tbill_bill_service tbs ON tbs.bs_bill_id = tb.bill_id " +
                " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                " LEFT JOIN mst_referring_entity re ON ta.admission_re_id = re.re_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_gender mgen ON mu.user_gender_id = mgen.gender_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.is_active = 1 AND t.is_deleted = 0 and t.isipd = 1 and t.ps_bill_id is not null ";
        String Query3 = " SELECT DISTINCT IFNULL(mp.patient_mr_no,'') AS PatientId, IFNULL(t.ps_performed_by_date,'') AS PerformedDate, " +
                " mic.charge_number AS BillNo, IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS PatientName, " +
                " IFNULL(mu.user_age,'') AS PatientAge, IFNULL(mgen.gender_name,'') AS PatientGender, IFNULL(mu.user_mobile,'') AS Contact, " +
                " IFNULL(mtest.test_name,'') AS TestName, IFNULL(re.re_name,'') AS Referral, IF(t.isipd=0,\"OPD\",\"IPD\") AS Services, " +
                " IF(ta.sponsor_combination='1',\"CASH\",\"CREDIT\") AS PaymentType, IFNULL(t.is_performed_by_unit_name,'') AS Organization, " +
                " mic.charge_sub_total AS Gross, mic.charge_discount_amount AS Discount, mic.charge_net_payable AS Net, " +
                " mic.charge_amount_tobe_paid AS Paid, mic.charge_outstanding AS Outstanding, IF(t.is_sample_outsource=1,\"Yes\",\"No\") AS Outsourced, " +
                " IFNULL(mic.created_date,'') AS AccessionDate, IFNULL(t.ps_sample_collection_date,'') AS SampleCollectionDate, " +
                " IFNULL(t.ps_sample_accepted_date,'') AS SampleAcceptanceDate, t.ps_report_delivery_date AS SubmissionDate, " +
                " IFNULL(t.ps_finalized_date,'') AS FinalizedDate, CASE WHEN t.is_report_delivered = 1 THEN 'Submitted' WHEN t.is_finalized = 1 " +
                " THEN 'Finalized' WHEN t.is_result_entry = 1 THEN 'Saved' WHEN t.is_sample_accepted = 1 THEN 'Sample Accepted' WHEN t.is_sample_rejected = 1 " +
                " THEN 'Sample Rejected' WHEN t.is_sample_collected = 1 THEN 'Sample Collected' WHEN t.is_lab_order_acceptance = 1 " +
                " THEN 'LO Accepted' ELSE \"LO Not Accepted\" END testStatus, IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectionName, " +
                " IFNULL(t.is_sample_accepted_by_name,'') AS SampleAcceptanceName, IFNULL(t.is_report_delivered_by_name,'') AS SubmissionName, " +
                " IFNULL(t.is_finalized_by_name,'') AS FinalizedName, IFNULL(ics.cs_gross_rate,'') AS TestAmount " +
                " FROM tpath_bs t " +
                " INNER JOIN mbill_ipd_charge mic ON t.mbillipdcharge = mic.ipdcharge_id " +
                " INNER JOIN ipd_charges_service ics ON mic.ipdcharge_id = ics.cs_charge_id " +
                " LEFT JOIN trn_admission ta ON mic.charge_admission_id = ta.admission_id " +
                " LEFT JOIN mst_referring_entity re ON ta.admission_re_id = re.re_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_gender mgen ON mu.user_gender_id = mgen.gender_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.is_active = 1 AND t.is_deleted = 0 and t.isipd = 1 and t.ps_bill_id is null ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (billCumTestReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(t.ps_performed_by_date)=curdate()) ";
            Query2 += " and (date(t.ps_performed_by_date)=curdate()) ";
            Query3 += " and (date(t.ps_performed_by_date)=curdate()) ";

        } else {
            Query1 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query2 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query3 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
            Query2 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
            Query3 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
        }
        if (billCumTestReportSearchDTO.getPatientId() != null && !billCumTestReportSearchDTO.getPatientId().equals("0")) {
            Query1 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
            Query2 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
            Query3 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
        }
        if (billCumTestReportSearchDTO.getPatientName() != null && !billCumTestReportSearchDTO.getPatientName().equals("")) {
            Query1 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + billCumTestReportSearchDTO.getPatientName() + "%'";
            Query2 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + billCumTestReportSearchDTO.getPatientName() + "%'";
            Query3 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + billCumTestReportSearchDTO.getPatientName() + "%'";
        }
        if (billCumTestReportSearchDTO.getGenderId() != null && !billCumTestReportSearchDTO.getGenderId().equals("0")) {
            Query1 += " and mu.user_gender_id = " + billCumTestReportSearchDTO.getGenderId() + " ";
            Query2 += " and mu.user_gender_id = " + billCumTestReportSearchDTO.getGenderId() + " ";
            Query3 += " and mu.user_gender_id = " + billCumTestReportSearchDTO.getGenderId() + " ";
        }
        if (billCumTestReportSearchDTO.getReferral() != null && !billCumTestReportSearchDTO.getReferral().equals("")) {
            Query1 += " and re.re_name LIKE '%" + billCumTestReportSearchDTO.getReferral() + "%'";
            Query2 += " and re.re_name LIKE '%" + billCumTestReportSearchDTO.getReferral() + "%'";
            Query3 += " and re.re_name LIKE '%" + billCumTestReportSearchDTO.getReferral() + "%'";
        }
        if (billCumTestReportSearchDTO.getBillNo() != null && !billCumTestReportSearchDTO.getBillNo().equals("")) {
            Query1 += " and tb.bill_number LIKE '%" + billCumTestReportSearchDTO.getBillNo() + "%'";
            Query2 += " and tb.bill_number LIKE '%" + billCumTestReportSearchDTO.getBillNo() + "%'";
            Query3 += " and mic.charge_number LIKE '%" + billCumTestReportSearchDTO.getBillNo() + "%'";
        }
        if (billCumTestReportSearchDTO.getTestName() != null && !billCumTestReportSearchDTO.getTestName().equals("")) {
            Query1 += " and mtest.test_name LIKE '%" + billCumTestReportSearchDTO.getTestName() + "%'";
            Query2 += " and mtest.test_name LIKE '%" + billCumTestReportSearchDTO.getTestName() + "%'";
            Query3 += " and mtest.test_name LIKE '%" + billCumTestReportSearchDTO.getTestName() + "%'";
        }
        if (billCumTestReportSearchDTO.getServiceType() == 0) {
            MainQuery = Query1 + " GROUP BY t.ps_id ORDER BY SubmissionDate DESC";
        } else if (billCumTestReportSearchDTO.getServiceType() == 1) {
            MainQuery = Query2 + " UNION ALL " + Query3 + " GROUP BY t.ps_id ORDER BY SubmissionDate DESC";
        } else {
            MainQuery = Query1 + " UNION ALL " + Query2 + " UNION ALL " + Query3 + " GROUP BY t.ps_id ORDER BY SubmissionDate DESC";
        }
        String CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        String SummaryQuery = " select IFNULL(sum(Gross),0.0) AS totalgross, IFNULL(sum(Discount),0.0) AS totalDiscount, IFNULL(sum(Net),0.0) AS totalNet, IFNULL(sum(Paid),0.0) AS totalPaid,  IFNULL(sum(Outstanding),0.0) AS totalOutstanding from (" + MainQuery + " ) AS x";
        System.out.println("MainQuery:" + MainQuery);
        System.out.println("CountQuery:" + CountQuery);
        System.out.println("SummaryQuery:" + SummaryQuery);
        try {
            // MainQuery += " limit " + ((billCumTestReportSearchDTO.getOffset() - 1) * billCumTestReportSearchDTO.getLimit()) + "," + billCumTestReportSearchDTO.getLimit();
            List<Object[]> billCumTestReport = entityManager.createNativeQuery(MainQuery).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : billCumTestReport) {
                BillCumTestReportListDTO objBillCumTestListDTO = new BillCumTestReportListDTO();
                objBillCumTestListDTO.setPatientId("" + obj[0]);
                objBillCumTestListDTO.setPerformedDate("" + obj[1]);
                objBillCumTestListDTO.setBillNo("" + obj[2]);
                objBillCumTestListDTO.setPatientName("" + obj[3]);
                objBillCumTestListDTO.setPatientAge("" + obj[4]);
                objBillCumTestListDTO.setPatientGender("" + obj[5]);
                objBillCumTestListDTO.setContact("" + obj[6]);
                objBillCumTestListDTO.setTestName("" + obj[7]);
                objBillCumTestListDTO.setReferral("" + obj[8]);
                objBillCumTestListDTO.setServices("" + obj[9]);
                objBillCumTestListDTO.setPaymentType("" + obj[10]);
                objBillCumTestListDTO.setOrganization("" + obj[11]);
                objBillCumTestListDTO.setGross("" + obj[12]);
                objBillCumTestListDTO.setDiscount("" + obj[13]);
                objBillCumTestListDTO.setNet("" + obj[14]);
                objBillCumTestListDTO.setPaid("" + obj[15]);
                objBillCumTestListDTO.setBillOutstanding("" + obj[16]);
                objBillCumTestListDTO.setOutsourced("" + obj[17]);
                objBillCumTestListDTO.setAccessionDate("" + obj[18]);
                objBillCumTestListDTO.setSampleCollectionDate("" + obj[19]);
                objBillCumTestListDTO.setSampleAcceptanceDate("" + obj[20]);
                // objBillCumTestListDTO.setModifiedDate("" + obj[21]);
                objBillCumTestListDTO.setSubmissionDate("" + obj[21]);
                objBillCumTestListDTO.setFinalizedDate("" + obj[22]);
                objBillCumTestListDTO.setTestStatus("" + obj[23]);
                objBillCumTestListDTO.setSampleCollectionName("" + obj[24]);
                objBillCumTestListDTO.setSampleAcceptanceName("" + obj[25]);
                objBillCumTestListDTO.setSubmissionName("" + obj[26]);
                objBillCumTestListDTO.setFinalizedName("" + obj[27]);
                objBillCumTestListDTO.setTestAmount(Double.parseDouble("" + obj[28]));
                objBillCumTestListDTO.setCount(count);        // total count
                objBillCumTestListDTO.setTotalGross(totalGross);
                objBillCumTestListDTO.setTotalDiscount(totalDiscount);
                objBillCumTestListDTO.setTotalNet(totalNet);
                objBillCumTestListDTO.setTotalPaid(totalPaid);
                objBillCumTestListDTO.setTotalOutstanding(totalOutstanding);
                listOfBillCumTestDTOList.add(objBillCumTestListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        // return listOfBillCumTestDTOList;
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(billCumTestReportSearchDTO.getUnitId());
        listOfBillCumTestDTOList.get(0).setObjHeaderData(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listOfBillCumTestDTOList);
        if (billCumTestReportSearchDTO.getPrint()) {
            return createReport.createJasperReportXLS("LabBillTestReport", ds);
        } else {
            return createReport.createJasperReportPDF("LabBillTestReport", ds);
        }
    } // END Service
// Bill Cum Test Report End 16.10.2019
//    // Bill cum test Report Print Start
//    @RequestMapping("billcumtestreportPrint1/{unitList}")
//    public String searchListofgetBillCumTestReportPrint1(@RequestHeader("X-tenantId") String tenantName, @RequestBody BillCumTestReportSearchDTO billCumTestReportSearchDTO, @PathVariable String[] unitList) {
//
//        long count = 0;
//        String fromdate = billCumTestReportSearchDTO.getFromdate(), todate = billCumTestReportSearchDTO.getTodate();
//
//
//        List<BillCumTestReportListDTO> listOfBillCumTestDTOList = new ArrayList<BillCumTestReportListDTO>();
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = formatter.format(date);
//
//
//        String Query1 = " SELECT IFNULL(mp.patient_mr_no,'') AS PatientId, IFNULL(t.ps_performed_by_date,'') AS PerformedDate, tb.bill_number AS BillNo, " +
//                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS PatientName, " +
//                " IFNULL(mu.user_age,'') AS PatientAge, IFNULL(mgen.gender_name,'') AS PatientGender,  IFNULL(mu.user_mobile,'') AS Contact, " +
//                " IFNULL(mtest.test_name,'') AS TestName, IFNULL(re.re_name,'') AS Referral,  IF(t.isipd=0,\"OPD\",\"IPD\") AS Services, " +
//                " IF(mv.sponsor_combination='1',\"CASH\",\"CREDIT\") AS PaymentType, " +
//                " IFNULL(t.is_performed_by_unit_name,'') AS Organization, tb.bill_sub_total AS Gross, tb.bill_discount_amount AS Discount, " +
//                " tb.bill_net_payable AS Net, tb.bill_amount_paid AS Paid, tb.bill_outstanding AS Outstanding,  " +
//                " IF(t.is_sample_outsource=1,\"Yes\",\"No\") AS Outsourced,  IFNULL(tb.created_date,'') AS AccessionDate, " +
//                " IFNULL(t.ps_sample_collection_date,'') AS SampleCollectionDate,IFNULL(t.ps_sample_accepted_date,'') AS SampleAcceptanceDate, " +
//                " t.last_modified_date AS ModifiedDate,IFNULL(t.ps_finalized_date,'') AS FinalizedDate," +
//                " case " +
//                " when t.is_report_delivered = 1 then 'Submitted' " +
//                " when t.is_finalized = 1 then 'Finalized' " +
//                " when t.is_result_entry = 1 then 'Saved' " +
//                " when t.is_sample_accepted = 1 then 'Sample Accepted' " +
//                " when t.is_sample_rejected = 1 then 'Sample Rejected' " +
//                " when t.is_sample_collected = 1 then 'Sample Collected' " +
//                " when t.is_lab_order_acceptance = 1 then 'LO Accepted' " +
//                " ELSE \"LO Not Accepted\" " +
//                " end testStatus" +
//                " FROM tpath_bs t " +
//                " INNER JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
//                " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id " +
//                " LEFT JOIN mst_referring_entity re ON mv.refer_by = re.re_id " +
//                " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
//                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
//                " LEFT JOIN mst_gender mgen ON mu.user_gender_id = mgen.gender_id " +
//                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
//                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
//                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
//                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
//                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
//                " WHERE t.is_active = 1 AND t.is_deleted = 0  and t.isipd = 0  ";
//
//        String Query2 = " UNION ALL SELECT IFNULL(mp.patient_mr_no,'') AS PatientId, IFNULL(t.ps_performed_by_date,'') AS PerformedDate, tb.bill_number AS BillNo, " +
//                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS PatientName, " +
//                " IFNULL(mu.user_age,'') AS PatientAge, IFNULL(mgen.gender_name,'') AS PatientGender,  IFNULL(mu.user_mobile,'') AS Contact, " +
//                " IFNULL(mtest.test_name,'') AS TestName, IFNULL(re.re_name,'') AS Referral,  IF(t.isipd=0,\"OPD\",\"IPD\") AS Services, " +
//                " IF(ta.sponsor_combination='1',\"CASH\",\"CREDIT\") AS PaymentType, " +
//                " IFNULL(t.is_performed_by_unit_name,'') AS Organization, tb.bill_sub_total AS Gross, tb.bill_discount_amount AS Discount, " +
//                " tb.bill_net_payable AS Net, tb.bill_amount_paid AS BillAmountPaid, tb.bill_outstanding AS Outstanding, " +
//                " IF(t.is_sample_outsource=1,\"Yes\",\"No\") AS Outsourced,  IFNULL(tb.created_date,'') AS AccessionDate," +
//                " IFNULL(t.ps_sample_collection_date,'') AS SampleCollectionDate,IFNULL(t.ps_sample_accepted_date,'') AS SampleAcceptanceDate," +
//                " t.last_modified_date AS ModifiedDate,IFNULL(t.ps_finalized_date,'') AS FinalizedDate," +
//                " case" +
//                " when t.is_report_delivered = 1 then 'Submitted'" +
//                " when t.is_finalized = 1 then 'Finalized'" +
//                " when t.is_result_entry = 1 then 'Saved'" +
//                " when t.is_sample_accepted = 1 then 'Sample Accepted'" +
//                " when t.is_sample_rejected = 1 then 'Sample Rejected'" +
//                " when t.is_sample_collected = 1 then 'Sample Collected'" +
//                " when t.is_lab_order_acceptance = 1 then 'LO Accepted'" +
//                " ELSE \"LO Not Accepted\"" +
//                " end testStatus" +
//                " FROM tpath_bs t " +
//                " INNER JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
//                " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
//                " LEFT JOIN mst_referring_entity re ON ta.admission_re_id = re.re_id " +
//                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
//                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
//                " LEFT JOIN mst_gender mgen ON mu.user_gender_id = mgen.gender_id " +
//                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
//                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
//                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
//                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
//                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
//                " WHERE t.is_active = 1 AND t.is_deleted = 0 and t.isipd = 1 and t.ps_bill_id is not null";
//
//        String Query3 = " UNION ALL SELECT IFNULL(mp.patient_mr_no,'') AS PatientId, IFNULL(t.ps_performed_by_date,'') AS PerformedDate, mic.charge_number AS BillNo, " +
//                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS PatientName, " +
//                " IFNULL(mu.user_age,'') AS PatientAge, IFNULL(mgen.gender_name,'') AS PatientGender,  IFNULL(mu.user_mobile,'') AS Contact, " +
//                " IFNULL(mtest.test_name,'') AS TestName, IFNULL(re.re_name,'') AS Referral,  IF(t.isipd=0,\"OPD\",\"IPD\") AS Services, " +
//                " IF(ta.sponsor_combination='1',\"CASH\",\"CREDIT\") AS PaymentType, " +
//                " IFNULL(t.is_performed_by_unit_name,'') AS Organization, mic.charge_sub_total AS Gross, mic.charge_discount_amount AS Discount, " +
//                " mic.charge_net_payable AS Net, mic.charge_amount_tobe_paid AS Paid, mic.charge_outstanding AS Outstanding, " +
//                " IF(t.is_sample_outsource=1,\"Yes\",\"No\") AS Outsourced,  IFNULL(mic.created_date,'') AS AccessionDate, " +
//                " IFNULL(t.ps_sample_collection_date,'') AS SampleCollectionDate,IFNULL(t.ps_sample_accepted_date,'') AS SampleAcceptanceDate, " +
//                " t.last_modified_date AS ModifiedDate,IFNULL(t.ps_finalized_date,'') AS FinalizedDate, " +
//                " case " +
//                " when t.is_report_delivered = 1 then 'Submitted' " +
//                " when t.is_finalized = 1 then 'Finalized' " +
//                " when t.is_result_entry = 1 then 'Saved' " +
//                " when t.is_sample_accepted = 1 then 'Sample Accepted' " +
//                " when t.is_sample_rejected = 1 then 'Sample Rejected' " +
//                " when t.is_sample_collected = 1 then 'Sample Collected' " +
//                " when t.is_lab_order_acceptance = 1 then 'LO Accepted' " +
//                " ELSE \"LO Not Accepted\" " +
//                " end testStatus" +
//                " FROM tpath_bs t " +
//                " INNER JOIN mbill_ipd_charge mic ON t.mbillipdcharge = mic.ipdcharge_id " +
//                " LEFT JOIN trn_admission ta ON mic.charge_admission_id = ta.admission_id " +
//                " LEFT JOIN mst_referring_entity re ON ta.admission_re_id = re.re_id " +
//                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
//                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
//                " LEFT JOIN mst_gender mgen ON mu.user_gender_id = mgen.gender_id " +
//                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
//                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
//                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
//                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
//                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
//                " WHERE t.is_active = 1 AND t.is_deleted = 0 and t.isipd = 1 and t.ps_bill_id is null";
//
//
//        if (fromdate.equals("") || fromdate.equals("null")) {
//            fromdate = "1990-06-07";
//        }
//
//        if (todate.equals("") || todate.equals("null")) {
//            todate = strDate;
//        }
//
//        if (billCumTestReportSearchDTO.getTodaydate()) {
//            Query1 += " and (date(t.ps_performed_by_date)=curdate()) ";
//            Query2 += " and (date(t.ps_performed_by_date)=curdate()) ";
//            Query3 += " and (date(t.ps_performed_by_date)=curdate()) ";
//
//        } else {
//            Query1 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
//            Query2 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
//            Query3 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
//        }
//
//        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
//            String values = String.valueOf(unitList[0]);
//            for (int i = 1; i < unitList.length; i++) {
//                values += "," + unitList[i];
//            }
//            Query1 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
//            Query2 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
//            Query3 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
//        }
//
//        if (billCumTestReportSearchDTO.getPatientId() != null && !billCumTestReportSearchDTO.getPatientId().equals("0")) {
//            Query1 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
//            Query2 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
//            Query3 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
//        }
//
//        if (billCumTestReportSearchDTO.getPatientName() != null && !billCumTestReportSearchDTO.getPatientName().equals("")) {
//            Query1 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + billCumTestReportSearchDTO.getPatientName() + "%'";
//            Query2 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + billCumTestReportSearchDTO.getPatientName() + "%'";
//            Query3 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + billCumTestReportSearchDTO.getPatientName() + "%'";
//        }
//
//        if (billCumTestReportSearchDTO.getGenderId() != null && !billCumTestReportSearchDTO.getGenderId().equals("0")) {
//            Query1 += " and mu.user_gender_id = " + billCumTestReportSearchDTO.getGenderId() + " ";
//            Query2 += " and mu.user_gender_id = " + billCumTestReportSearchDTO.getGenderId() + " ";
//            Query3 += " and mu.user_gender_id = " + billCumTestReportSearchDTO.getGenderId() + " ";
//        }
//
//        if (billCumTestReportSearchDTO.getReferral() != null && !billCumTestReportSearchDTO.getReferral().equals("")) {
//            Query1 += " and re.re_name LIKE '%" + billCumTestReportSearchDTO.getReferral() + "%'";
//            Query2 += " and re.re_name LIKE '%" + billCumTestReportSearchDTO.getReferral() + "%'";
//            Query3 += " and re.re_name LIKE '%" + billCumTestReportSearchDTO.getReferral() + "%'";
//        }
//
//        if (billCumTestReportSearchDTO.getBillNo() != null && !billCumTestReportSearchDTO.getBillNo().equals("")) {
//            Query1 += " and tb.bill_number LIKE '%" + billCumTestReportSearchDTO.getBillNo() + "%'";
//            Query2 += " and tb.bill_number LIKE '%" + billCumTestReportSearchDTO.getBillNo() + "%'";
//            Query3 += " and mic.charge_number LIKE '%" + billCumTestReportSearchDTO.getBillNo() + "%'";
//        }
//
//        if (billCumTestReportSearchDTO.getTestName() != null && !billCumTestReportSearchDTO.getTestName().equals("")) {
//            Query1 += " and mtest.test_name LIKE '%" + billCumTestReportSearchDTO.getTestName() + "%'";
//            Query2 += " and mtest.test_name LIKE '%" + billCumTestReportSearchDTO.getTestName() + "%'";
//            Query3 += " and mtest.test_name LIKE '%" + billCumTestReportSearchDTO.getTestName() + "%'";
//        }
//
//        String MainQuery = Query1 + Query2 + Query3;
//        // Count query
//        String CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
//        System.out.println("Query:" + MainQuery);
//
//        try {
////            MainQuery += " limit " + ((billCumTestReportSearchDTO.getOffset() - 1) * billCumTestReportSearchDTO.getLimit()) + "," + billCumTestReportSearchDTO.getLimit();
//            List<Object[]> billCumTestReport = entityManager.createNativeQuery(MainQuery).getResultList();
//            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
//
//            count = cc.longValue();
//
//            for (Object[] obj : billCumTestReport) {
//                BillCumTestReportListDTO objBillCumTestListDTO = new BillCumTestReportListDTO();
//                objBillCumTestListDTO.setPatientId("" + obj[0]);
//                objBillCumTestListDTO.setPerformedDate("" + obj[1]);
//                objBillCumTestListDTO.setBillNo("" + obj[2]);
//                objBillCumTestListDTO.setPatientName("" + obj[3]);
//                objBillCumTestListDTO.setPatientAge("" + obj[4]);
//                objBillCumTestListDTO.setPatientGender("" + obj[5]);
//                objBillCumTestListDTO.setContact("" + obj[6]);
//                objBillCumTestListDTO.setTestName("" + obj[7]);
//                objBillCumTestListDTO.setReferral("" + obj[8]);
//                objBillCumTestListDTO.setServices("" + obj[9]);
//                objBillCumTestListDTO.setPaymentType("" + obj[10]);
//                objBillCumTestListDTO.setOrganization("" + obj[11]);
//                objBillCumTestListDTO.setGross("" + obj[12]);
//                objBillCumTestListDTO.setDiscount("" + obj[13]);
//                objBillCumTestListDTO.setNet("" + obj[14]);
//                objBillCumTestListDTO.setPaid("" + obj[15]);
//                objBillCumTestListDTO.setBillOutstanding("" + obj[16]);
//                objBillCumTestListDTO.setOutsourced("" + obj[17]);
//                objBillCumTestListDTO.setAccessionDate("" + obj[18]);
//                objBillCumTestListDTO.setSampleCollectionDate("" + obj[19]);
//                objBillCumTestListDTO.setSampleAcceptanceDate("" + obj[20]);
//                objBillCumTestListDTO.setModifiedDate("" + obj[21]);
//                objBillCumTestListDTO.setFinalizedDate("" + obj[22]);
//                objBillCumTestListDTO.setTestStatus("" + obj[23]);
//                objBillCumTestListDTO.setCount(count);        // total count
//
//                listOfBillCumTestDTOList.add(objBillCumTestListDTO);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//     //   return listOfBillCumTestDTOList;
//        MstUnit HeaderObject = mstUnitRepository.findByUnitId(billCumTestReportSearchDTO.getUnitId());
//        listOfBillCumTestDTOList.get(0).setObjHeaderData(HeaderObject);
//        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listOfBillCumTestDTOList);
//        if(billCumTestReportSearchDTO.getPrint()){
//            return createReport.createJasperReportXLS("LabBillTestReport", ds);
//        }else {
//            return createReport.createJasperReportPDF("LabBillTestReport", ds);
//        }
//    } // END Service
//// Bill Cum Test Report Print End
}