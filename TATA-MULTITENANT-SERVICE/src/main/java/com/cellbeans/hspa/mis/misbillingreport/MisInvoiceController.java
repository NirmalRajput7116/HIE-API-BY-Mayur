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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis-Invoice-Report")
public class MisInvoiceController {

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
//    @RequestMapping("getinvoiceReport/{unitList}/{mstuserlist}/{fromdate}/{todate}")
//    public Map<String, List<?>> searinvoicereport(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvoiceSearchDTO invoiceSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstuserlist, @PathVariable String fromdate, @PathVariable String todate) {
//
//        long count = 0;
//
//        Map<String, List<?>> respMap = new HashMap<String, List<?>>();
//
//        List<InvoiceListDTO> invoiceListDTOList = new ArrayList<InvoiceListDTO>();
//        List<InvoiceListDTO> invoiceListDTOList1 = new ArrayList<InvoiceListDTO>();
//
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = formatter.format(date);
//
//        String Query = " SELECT u.unit_name,c.class_name,b.bill_number,b.bill_date,time(b.created_date)as btime, " +
//                " ifnull(p.patient_mr_no,'')as MrNO,concat(ps.user_firstname,' ',ps.user_lastname)as PatientName, " +
//                " b.bill_sub_total as GrossAmount,b.bill_discount_amount as concAmt,b.bill_net_payable as NetBillAmount, " +
//                " b.bill_amount_paid as Paymentrecieved,r.br_reciept_number,date(r.created_date)as ReceiptDate,concat(us.user_firstname,' ',us.user_lastname)as UserName, " +
//                " ifnull(cc.cc_name,'')as Cashcounter,u.unit_id,us.user_id,r.br_bill_id FROM tbill_reciept r " +
//                " left join tbill_bill b on r.br_bill_id=b.bill_id " +
//                " left join mst_class c on b.bill_class_id=c.class_id " +
//                " left join mst_unit u on b.tbill_unit_id=u.unit_id " +
//                " left join mst_staff s on b.bill_user_id=s.staff_id " +
//                " left join mst_user us on s.staff_user_id=us.user_id " +
//                " left join mst_visit v on b.bill_visit_id=v.visit_id " +
//                " left join mst_patient p on v.visit_patient_id=p.patient_id " +
//                " left join mst_user ps on p.patient_user_id=ps.user_id " +
//                " left join mst_cash_counter cc on b.bill_cash_counter_id=cc.cc_id ";
//
//        String CountQuery = " SELECT count(r.br_bill_id) FROM tbill_reciept r " +
//                " left join tbill_bill b on r.br_bill_id=b.bill_id " +
//                " left join mst_class c on b.bill_class_id=c.class_id " +
//                " left join mst_unit u on b.tbill_unit_id=u.unit_id " +
//                " left join mst_staff s on b.bill_user_id=s.staff_id " +
//                " left join mst_user us on s.staff_user_id=us.user_id " +
//                " left join mst_visit v on b.bill_visit_id=v.visit_id " +
//                " left join mst_patient p on v.visit_patient_id=p.patient_id " +
//                " left join mst_user ps on p.patient_user_id=ps.user_id " +
//                " left join mst_cash_counter cc on b.bill_cash_counter_id=cc.cc_id ";
//
//        String SummaryQuery = " SELECT u.unit_name,count(tb.bill_id) as billc,sum(tb.bill_sub_total) as grossAmount, " +
//                " sum(tb.bill_discount_amount) as concAmt,sum(tb.bill_net_payable) as NetBillAmount, " +
//                " sum(tb.bill_amount_paid) as Paymentrecieved " +
//                " FROM tbill_bill tb,mst_unit u where " +
//                " tb.tbill_unit_id=u.unit_id ";
//
//        String SummaryQuery1 = " select u.unit_name,sum(bs_discount_amount) as dicountService " +
//                " from tbill_bill_service ts,tbill_bill tb,mst_unit u " +
//                " where ts.bs_bill_id=tb.bill_id  and tb.tbill_unit_id=u.unit_id ";
//
//        if (fromdate.equals("") || fromdate.equals("null")) {
//            fromdate = "1990-06-07";
//            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");
//        }
//
//        if (todate.equals("") || todate.equals("null")) {
//            todate = strDate;
//            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
//        }
//
//        if (invoiceSearchDTO.getTodaydate()) {
//            Query += " where (date(r.created_date)=curdate()) ";
//            CountQuery += " where (date(r.created_date)=curdate()) ";
//            SummaryQuery += " and (date(tb.bill_Date)=curdate()) ";
//            SummaryQuery1 += " and (date(tb.bill_Date)=curdate()) ";
//        } else {
//            Query += " where (date(r.created_date) between '" + fromdate + "' and '" + todate + "')  ";
//            CountQuery += " where (date(r.created_date) between '" + fromdate + "' and '" + todate + "')  ";
//            SummaryQuery += " and (date(tb.bill_Date) between '" + fromdate + "' and '" + todate + "')  ";
//            SummaryQuery1 += " and (date(tb.bill_Date) between '" + fromdate + "' and '" + todate + "')  ";
//        }
//
//        if (!unitList[0].equals("null")) {
//            String values = String.valueOf(unitList[0]);
//            for (int i = 1; i < unitList.length; i++) {
//                values += "," + unitList[i];
//            }
//            Query += " and b.tbill_unit_id in (" + values + ") ";
//            CountQuery += " and b.tbill_unit_id in (" + values + ") ";
//            SummaryQuery += " and tb.tbill_unit_id in (" + values + ") ";
//            SummaryQuery1 += " and tb.tbill_unit_id in (" + values + ") ";
//        }
//
//        if (!mstuserlist[0].equals("null")) {
//            String values = String.valueOf(mstuserlist[0]);
//            for (int i = 1; i < mstuserlist.length; i++) {
//                values += "," + mstuserlist[i];
//            }
//            Query += " and s.staff_user_id in (" + values + ") ";
//            CountQuery += " and s.staff_user_id in (" + values + ") ";
//            SummaryQuery += " and tb.bill_user_id in (" + values + ") ";
//            SummaryQuery1 += " and tb.bill_user_id in (" + values + ") ";
//        }
//
//        if (!String.valueOf(invoiceSearchDTO.getCashcounterId()).equals("0") && !String.valueOf(invoiceSearchDTO.getCashcounterId()).equals("null")) {
//            Query += " and b.bill_cash_counter_id=" + invoiceSearchDTO.getCashcounterId() + " ";
//            CountQuery += " and b.bill_cash_counter_id=" + invoiceSearchDTO.getCashcounterId() + " ";
//            SummaryQuery += " and tb.bill_cash_counter_id=" + invoiceSearchDTO.getCashcounterId() + " ";
//            SummaryQuery1 += " and tb.bill_cash_counter_id=" + invoiceSearchDTO.getCashcounterId() + " ";
//        }
//
//        if (!String.valueOf(invoiceSearchDTO.getClassId()).equals("0") && !String.valueOf(invoiceSearchDTO.getClassId()).equals("null")) {
//            Query += " and b.bill_class_id=" + invoiceSearchDTO.getClassId() + " ";
//            CountQuery += " and b.bill_class_id=" + invoiceSearchDTO.getClassId() + " ";
//            SummaryQuery += " and tb.bill_class_id=" + invoiceSearchDTO.getClassId() + " ";
//            SummaryQuery1 += " and tb.bill_class_id=" + invoiceSearchDTO.getClassId() + " ";
//        }
//
//        String DisMainQuery = " select mainTab.unit_name,mainTab.billc as billCount,ifnull(mainTab.grossAmount,0) as grossAmount, " + " ifnull(mainTab.concAmt,0) as concAmt,ifnull(mainTab.NetBillAmount,0) as NetBillAmount, " + " ifnull(mainTab.Paymentrecieved,0) as Paymentrecieved,ifnull(dissTab.dicountService,0) as dicountService from " + " ( " + SummaryQuery + " group by tb.tbill_unit_id) as mainTab left join " + " ( " + SummaryQuery1 + " group by tb.tbill_unit_id) as dissTab " + " on mainTab.unit_name=dissTab.unit_name ";
//
//        try {
//            Query += " limit " + ((invoiceSearchDTO.getOffset() - 1) * invoiceSearchDTO.getLimit()) + "," + invoiceSearchDTO.getLimit();
//            System.out.println("Mainquery" + Query);
//
//            List<Object[]> listInvoice = entityManager.createNativeQuery(Query).getResultList();
//
//            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
//            count = cc.longValue();
//
//            for (Object[] obj : listInvoice) {
//
//                InvoiceListDTO objinvoiceListDTO = new InvoiceListDTO();
//
//                objinvoiceListDTO.setUnitName("" + obj[0]);
//                objinvoiceListDTO.setClassType("" + obj[1]);
//                objinvoiceListDTO.setBillNo("" + obj[2]);
//                objinvoiceListDTO.setBillDate("" + obj[3]);
//                objinvoiceListDTO.setBillTime("" + obj[4]);
//                objinvoiceListDTO.setMrNO("" + obj[5]);
//                objinvoiceListDTO.setPatientName("" + obj[6]);
////                objinvoiceListDTO.setGrossBillAmount("" + obj[7]);
//                objinvoiceListDTO.setGrossBillAmount("" + obj[7]);
//                objinvoiceListDTO.setConcOnBill("" + obj[8]);
//                objinvoiceListDTO.setNetBillAmount("" + obj[9]);
//                objinvoiceListDTO.setPaymentReceived("" + obj[10]);
//                objinvoiceListDTO.setReceiptNo("" + obj[11]);
//                objinvoiceListDTO.setReceiptDate("" + obj[12]);
//                objinvoiceListDTO.setUserName("" + obj[13]);
//                objinvoiceListDTO.setCashCounter("" + obj[14]);
//                objinvoiceListDTO.setUnitId(Long.parseLong("" + obj[15]));
//                objinvoiceListDTO.setUserId(Long.parseLong("" + obj[16]));
//                objinvoiceListDTO.setBr_bill_id(Long.parseLong(obj[17].toString()));
//                objinvoiceListDTO.setCount(count);
//                invoiceListDTOList.add(objinvoiceListDTO);
//            }
//
//            for (InvoiceListDTO temp : invoiceListDTOList) {
//                String querDiss = "select sum(bs_discount_amount) as dicountService from tbill_bill_service bs where bs.bs_bill_id=" + temp.getBr_bill_id();
//                BigDecimal disServiceAmount = (BigDecimal) entityManager.createNativeQuery(querDiss).getSingleResult();
//                temp.setDiscOnServices(disServiceAmount.doubleValue());
//            }
//
//            respMap.put("DetailsList", invoiceListDTOList);
//
//
//            System.out.println("Summary Query" + DisMainQuery);
//
//            List<Object[]> invoiceListDTOList2 = entityManager.createNativeQuery(DisMainQuery).getResultList();
//
//            for (Object[] obj : invoiceListDTOList2) {
//
//                InvoiceListDTO objinvoiceListDTO = new InvoiceListDTO();
//
//                objinvoiceListDTO.setUnitName("" + obj[0]);
//                objinvoiceListDTO.setNoofBillGenerated("" + obj[1]);
//                objinvoiceListDTO.setTotalGrossBillAmount("" + obj[2]);
//                objinvoiceListDTO.setToatalDiscountOnService("" + obj[6]);
//                objinvoiceListDTO.setTotalConcessionsonBill("" + obj[3]);
//                objinvoiceListDTO.setTotalNetBillAmount("" + obj[4]);
//                objinvoiceListDTO.setTotalPaymentReceived("" + obj[5]);
//                invoiceListDTOList1.add(objinvoiceListDTO);
//            }
//            respMap.put("SummaryDiscount", invoiceListDTOList1);
//
//        } catch (Exception e) {
//            // e.printStackTrace();
//            System.out.println("Error:" + e);
//            //return null;
//        }
//        return respMap;
//        //return invoiceListDTOList;
//    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getinvoiceDetailsReport/{unitList}/{mstuserlist}/{fromdate}/{todate}/{clclId}/{ccId}/{IPDFlag}")
    public ResponseEntity searchinvoicereport(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvoiceSearchDTO invoiceSearchDTO, @PathVariable String[] unitList,
                                              @PathVariable String[] mstuserlist, @PathVariable String fromdate, @PathVariable String todate,
                                              @PathVariable String clclId, @PathVariable String ccId,
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
            Query += " SELECT u.unit_name,c.class_name,b.bill_number,b.bill_date,time(b.created_date)as btime, " +
                    " r.br_reciept_number,r.created_date as receiptDate," +
                    " ifnull(p.patient_mr_no,'')AS mrNo,ifnull(ps.user_firstname,'')as user_firstname, ifnull(ps.user_lastname,'')as user_lastname,  " +
                    " b.gross_amount as grossAmount,b.bill_discount_amount as concAmt,b.bill_net_payable as netBillAmount,  " +
                    " b.company_net_pay AS credit, b.bill_amount_paid as paymentrecieved,b.bill_outstanding AS balance,  " +
                    " concat(us.user_firstname,' ',us.user_lastname)as userName,  ifnull(cc.cc_name,'')as cashcounter," +
                    " u.unit_id,us.user_id,r.br_bill_id " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " left join mst_class c on b.bill_class_id = c.class_id  " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id  " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id  " +
                    " left join mst_user us on s.staff_user_id = us.user_id  " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id  " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id  " +
                    " left join mst_cash_counter cc on b.bill_cash_counter_id=cc.cc_id  " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0  AND b.emrbill = 0 AND b.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT SUM(b.gross_amount) as TotalGrossAmount, " +
                    " SUM(b.bill_discount_amount) as TotalconcAmt, " +
                    " SUM(b.bill_net_payable) as TotalNetBillAmount,  " +
                    " SUM(b.company_net_pay) AS TotalCredit, " +
                    " SUM(b.bill_amount_paid) as TotalPaymentrecieved, " +
                    " SUM(b.bill_outstanding) AS TotalBalance " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    "left join mst_patient p on r.br_patient_id = p.patient_id " +
                    "inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0 AND b.emrbill = 0 AND b.ipd_bill = " + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += " SELECT u.unit_name,c.class_name,b.bill_number,b.bill_date,time(b.created_date)as btime, " +
                    " r.br_reciept_number,r.created_date as receiptDate," +
                    " ifnull(p.patient_mr_no,'')AS mrNo,ifnull(ps.user_firstname,'')as user_firstname, ifnull(ps.user_lastname,'')as user_lastname,  " +
                    " b.gross_amount as grossAmount,b.bill_discount_amount as concAmt,b.bill_net_payable as netBillAmount,  " +
                    " b.company_net_pay AS credit, b.bill_amount_paid as paymentrecieved,b.bill_outstanding AS balance,  " +
                    " concat(us.user_firstname,' ',us.user_lastname)as userName,  ifnull(cc.cc_name,'')as cashcounter, " +
                    " u.unit_id,us.user_id,r.br_bill_id  " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " left join mst_class c on b.bill_class_id = c.class_id  " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id  " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id  " +
                    " left join mst_user us on s.staff_user_id = us.user_id  " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id  " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id  " +
                    " left join mst_cash_counter cc on b.bill_cash_counter_id=cc.cc_id  " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0 AND b.emrbill = 0 AND b.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT SUM(b.gross_amount) as TotalGrossAmount, " +
                    " SUM(b.bill_discount_amount) as TotalconcAmt," +
                    " SUM(b.bill_net_payable) as TotalNetBillAmount,  " +
                    " SUM(b.company_net_pay) AS TotalCredit, " +
                    " SUM(b.bill_amount_paid) as TotalPaymentrecieved, " +
                    " SUM(b.bill_outstanding) AS TotalBalance " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0 AND b.emrbill = 0 AND b.ipd_bill = " + IPDFlag;
        } else {
            Query += " SELECT u.unit_name,c.class_name,b.bill_number,b.bill_date,time(b.created_date)as btime, " +
                    " r.br_reciept_number,r.created_date as receiptDate," +
                    " ifnull(p.patient_mr_no,'')AS mrNo,ifnull(ps.user_firstname,'')as user_firstname, ifnull(ps.user_lastname,'')as user_lastname,  " +
                    " b.gross_amount as grossAmount,b.bill_discount_amount as concAmt,b.bill_net_payable as netBillAmount,  " +
                    " b.company_net_pay AS credit, b.bill_amount_paid as paymentrecieved,b.bill_outstanding AS balance,  " +
                    " concat(us.user_firstname,' ',us.user_lastname)as userName,  ifnull(cc.cc_name,'')as cashcounter," +
                    " u.unit_id,us.user_id,r.br_bill_id " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " left join mst_class c on b.bill_class_id = c.class_id  " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id  " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id  " +
                    " left join mst_user us on s.staff_user_id = us.user_id  " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id  " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id  " +
                    " left join mst_cash_counter cc on b.bill_cash_counter_id=cc.cc_id  " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0 AND b.emrbill = 1";
            SummaryQuery += " SELECT SUM(b.gross_amount) as TotalGrossAmount," +
                    " SUM(b.bill_discount_amount) as TotalconcAmt," +
                    " SUM(b.bill_net_payable) as TotalNetBillAmount,  " +
                    " SUM(b.company_net_pay) AS TotalCredit, " +
                    " SUM(b.bill_amount_paid) as TotalPaymentrecieved, " +
                    " SUM(b.bill_outstanding) AS TotalBalance " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0 AND b.emrbill = 1";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (invoiceSearchDTO.getTodaydate()) {
            Query += " and (date(r.created_date)=curdate()) ";
            SummaryQuery += " and (date(b.bill_Date)=curdate()) ";

        } else {
            Query += " and (date(r.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            SummaryQuery += " and (date(b.bill_Date) between '" + fromdate + "' and '" + todate + "')  ";

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
            SummaryQuery += " and b.bill_user_id in (" + values + ") ";
        }
        if (!invoiceSearchDTO.getMrNo().equals("")) {
            Query += " and  p.patient_mr_no like  '%" + invoiceSearchDTO.getMrNo() + "%' ";
            SummaryQuery += " and p.patient_mr_no like  '%" + invoiceSearchDTO.getMrNo() + "%' ";
        }
        if ((invoiceSearchDTO.getUser_firstname() != null) && (!invoiceSearchDTO.getUser_firstname().equals(""))) {
            Query += " and (ps.user_firstname like '%" + invoiceSearchDTO.getUser_firstname() + "%') ";
            SummaryQuery += " and (ps.user_firstname like '%" + invoiceSearchDTO.getUser_firstname() + "%') ";
        }
        if ((invoiceSearchDTO.getUser_lastname() != null) && (!invoiceSearchDTO.getUser_lastname().equals(""))) {
            Query += " and (ps.user_lastname like '%" + invoiceSearchDTO.getUser_lastname() + "%') ";
            SummaryQuery += " and (ps.user_lastname like '%" + invoiceSearchDTO.getUser_lastname() + "%') ";

        }

      /*  // CashCounter Id
        if (!ccId.equals("null") && !ccId.equals("0")) {
            Query += " and b.bill_cash_counter_id =  " + ccId + " ";
            SummaryQuery += " and b.bill_cash_counter_id =  " + ccId + " ";
        }*/
        // CashCounter Id
        if (!invoiceSearchDTO.getCcId().equals("null") && !invoiceSearchDTO.getCcId().equals("0")) {
            Query += " and b.bill_cash_counter_id = " + invoiceSearchDTO.getCcId() + " ";
            SummaryQuery += " and b.bill_cash_counter_id = " + invoiceSearchDTO.getCcId() + " ";
        }
        //    Collection Mode
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query += " and r.br_payment_amount!=0 and b.bill_outstanding=0 ";
                SummaryQuery += " and r.br_payment_amount!=0 and b.bill_outstanding=0 ";
            } else if (clclId.equals("2")) {
                Query += " and r.br_payment_amount!=0 and b.bill_outstanding!=0 ";
                SummaryQuery += " and r.br_payment_amount!=0 and b.bill_outstanding!=0 ";
            } else {
                Query += " and r.br_payment_amount=0 and b.bill_outstanding!=0 ";
                SummaryQuery += " and r.br_payment_amount=0 and b.bill_outstanding!=0 ";
            }
        }

        System.out.println("Invoice Details"+Query);
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,class_name,bill_number,bill_date,btime,br_reciept_number,receiptDate,mrNo,user_firstname,user_lastname,grossAmount,concAmt,netBillAmount,credit,paymentrecieved,balance,userName,cashcounter,unit_id,user_id,br_bill_id";
        Query += "  limit " + ((invoiceSearchDTO.getOffset() - 1) * invoiceSearchDTO.getLimit()) + "," + invoiceSearchDTO.getLimit();
        System.out.println("MainQuery : " + Query);
        System.out.println("CountQuery : " + CountQuery);
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(SummaryQuery).getResultList();
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("TotalGrossAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("TotalconcAmt", ob[1].toString());
                jsonArray.getJSONObject(0).put("TotalNetBillAmount", ob[2].toString());
                jsonArray.getJSONObject(0).put("TotalCredit", ob[3].toString());
                jsonArray.getJSONObject(0).put("TotalPaymentrecieved", ob[4].toString());
                jsonArray.getJSONObject(0).put("TotalBalance", ob[5].toString());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        return ResponseEntity.ok(jsonArray.toString());
    }

    @RequestMapping("getinvoiceDetailsReportPrint/{unitList}/{mstuserlist}/{fromdate}/{todate}/{clclId}/{ccId}/{IPDFlag}")
    public String searchinvoicereportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvoiceSearchDTO invoiceSearchDTO, @PathVariable String[] unitList,
                                           @PathVariable String[] mstuserlist, @PathVariable String fromdate, @PathVariable String todate,
                                           @PathVariable String clclId, @PathVariable String ccId,
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
            Query += " SELECT u.unit_name,c.class_name,b.bill_number,b.bill_date,time(b.created_date)as btime, " +
                    " r.br_reciept_number,r.created_date as receiptDate," +
                    " ifnull(p.patient_mr_no,'')AS mrNo,ifnull(ps.user_firstname,'')as user_firstname, ifnull(ps.user_lastname,'')as user_lastname,  " +
                    " b.gross_amount as grossAmount,b.bill_discount_amount as concAmt,b.bill_net_payable as netBillAmount,  " +
                    " b.company_net_pay AS credit, b.bill_amount_paid as paymentrecieved,b.bill_outstanding AS balance,  " +
                    " concat(us.user_firstname,' ',us.user_lastname)as userName,  ifnull(cc.cc_name,'')as cashcounter," +
                    " u.unit_id,us.user_id,r.br_bill_id " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " left join mst_class c on b.bill_class_id = c.class_id  " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id  " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id  " +
                    " left join mst_user us on s.staff_user_id = us.user_id  " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id  " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id  " +
                    " left join mst_cash_counter cc on b.bill_cash_counter_id=cc.cc_id  " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0  AND b.emrbill = 0 AND b.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT SUM(b.gross_amount) as TotalGrossAmount, " +
                    " SUM(b.bill_discount_amount) as TotalconcAmt, " +
                    " SUM(b.bill_net_payable) as TotalNetBillAmount,  " +
                    " SUM(b.company_net_pay) AS TotalCredit, " +
                    " SUM(b.bill_amount_paid) as TotalPaymentrecieved, " +
                    " SUM(b.bill_outstanding) AS TotalBalance " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    "left join mst_patient p on r.br_patient_id = p.patient_id " +
                    "inner join mst_user ps on p.patient_user_id = ps.user_id " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0 AND b.emrbill = 0 AND b.ipd_bill = " + IPDFlag;

        } else if (IPDFlag == 1) {
            Query += " SELECT u.unit_name,c.class_name,b.bill_number,b.bill_date,time(b.created_date)as btime, " +
                    " r.br_reciept_number,r.created_date as receiptDate," +
                    " ifnull(p.patient_mr_no,'')AS mrNo,ifnull(ps.user_firstname,'')as user_firstname, ifnull(ps.user_lastname,'')as user_lastname,  " +
                    " b.gross_amount as grossAmount,b.bill_discount_amount as concAmt,b.bill_net_payable as netBillAmount,  " +
                    " b.company_net_pay AS credit, b.bill_amount_paid as paymentrecieved,b.bill_outstanding AS balance,  " +
                    " concat(us.user_firstname,' ',us.user_lastname)as userName,  ifnull(cc.cc_name,'')as cashcounter, " +
                    " u.unit_id,us.user_id,r.br_bill_id  " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " left join mst_class c on b.bill_class_id = c.class_id  " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id  " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id  " +
                    " left join mst_user us on s.staff_user_id = us.user_id  " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id  " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id  " +
                    " left join mst_cash_counter cc on b.bill_cash_counter_id=cc.cc_id  " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0 AND b.emrbill = 0 AND b.ipd_bill = " + IPDFlag;
            SummaryQuery += " SELECT SUM(b.gross_amount) as TotalGrossAmount, " +
                    " SUM(b.bill_discount_amount) as TotalconcAmt," +
                    " SUM(b.bill_net_payable) as TotalNetBillAmount,  " +
                    " SUM(b.company_net_pay) AS TotalCredit, " +
                    " SUM(b.bill_amount_paid) as TotalPaymentrecieved, " +
                    " SUM(b.bill_outstanding) AS TotalBalance " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0 AND b.emrbill = 0 AND b.ipd_bill = " + IPDFlag;
        } else {
            Query += " SELECT u.unit_name,c.class_name,b.bill_number,b.bill_date,time(b.created_date)as btime, " +
                    " r.br_reciept_number,r.created_date as receiptDate," +
                    " ifnull(p.patient_mr_no,'')AS mrNo,ifnull(ps.user_firstname,'')as user_firstname, ifnull(ps.user_lastname,'')as user_lastname,  " +
                    " b.gross_amount as grossAmount,b.bill_discount_amount as concAmt,b.bill_net_payable as netBillAmount,  " +
                    " b.company_net_pay AS credit, b.bill_amount_paid as paymentrecieved,b.bill_outstanding AS balance,  " +
                    " concat(us.user_firstname,' ',us.user_lastname)as userName,  ifnull(cc.cc_name,'')as cashcounter," +
                    " u.unit_id,us.user_id,r.br_bill_id " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " left join mst_class c on b.bill_class_id = c.class_id  " +
                    " left join mst_unit u on b.tbill_unit_id = u.unit_id  " +
                    " left join mst_staff s ON b.bill_user_id = s.staff_id  " +
                    " left join mst_user us on s.staff_user_id = us.user_id  " +
                    " left join mst_patient p on r.br_patient_id = p.patient_id  " +
                    " inner join mst_user ps on p.patient_user_id = ps.user_id  " +
                    " left join mst_cash_counter cc on b.bill_cash_counter_id=cc.cc_id  " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0 AND b.emrbill = 1";
            SummaryQuery += " SELECT SUM(b.gross_amount) as TotalGrossAmount," +
                    " SUM(b.bill_discount_amount) as TotalconcAmt," +
                    " SUM(b.bill_net_payable) as TotalNetBillAmount,  " +
                    " SUM(b.company_net_pay) AS TotalCredit, " +
                    " SUM(b.bill_amount_paid) as TotalPaymentrecieved, " +
                    " SUM(b.bill_outstanding) AS TotalBalance " +
                    " FROM tbill_reciept r  " +
                    " left join tbill_bill b on r.br_bill_id = b.bill_id  " +
                    " WHERE r.is_active = 1 AND r.is_cancelled = 0 AND b.emrbill = 1";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (invoiceSearchDTO.getTodaydate()) {
            Query += " and (date(r.created_date)=curdate()) ";
            SummaryQuery += " and (date(b.bill_Date)=curdate()) ";
        } else {
            Query += " and (date(r.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            SummaryQuery += " and (date(b.bill_Date) between '" + fromdate + "' and '" + todate + "')  ";
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
            SummaryQuery += " and b.bill_user_id in (" + values + ") ";
        }
        if (!invoiceSearchDTO.getMrNo().equals("")) {
            Query += " and  p.patient_mr_no like  '%" + invoiceSearchDTO.getMrNo() + "%' ";
            SummaryQuery += " and p.patient_mr_no like  '%" + invoiceSearchDTO.getMrNo() + "%' ";
        }
        if ((invoiceSearchDTO.getUser_firstname() != null) && (!invoiceSearchDTO.getUser_firstname().equals(""))) {
            Query += " and (ps.user_firstname like '%" + invoiceSearchDTO.getUser_firstname() + "%') ";
            SummaryQuery += " and (ps.user_firstname like '%" + invoiceSearchDTO.getUser_firstname() + "%') ";
        }
        if ((invoiceSearchDTO.getUser_lastname() != null) && (!invoiceSearchDTO.getUser_lastname().equals(""))) {
            Query += " and (ps.user_lastname like '%" + invoiceSearchDTO.getUser_lastname() + "%') ";
            SummaryQuery += " and (ps.user_lastname like '%" + invoiceSearchDTO.getUser_lastname() + "%') ";

        }
        // CashCounter Id
        if (!ccId.equals("null") && !ccId.equals("0")) {
            Query += " and b.bill_cash_counter_id =  " + ccId + " ";
            SummaryQuery += " and b.bill_cash_counter_id =  " + ccId + " ";
        }
        //    Collection Mode
        if (!clclId.equals("null") && !clclId.equals("0")) {
            if (clclId.equals("1")) {
                Query += " and r.br_payment_amount!=0 and b.bill_outstanding=0 ";
                SummaryQuery += " and r.br_payment_amount!=0 and b.bill_outstanding=0 ";
            } else if (clclId.equals("2")) {
                Query += " and r.br_payment_amount!=0 and b.bill_outstanding!=0 ";
                SummaryQuery += " and r.br_payment_amount!=0 and b.bill_outstanding!=0 ";
            } else {
                Query += " and r.br_payment_amount=0 and b.bill_outstanding!=0 ";
                SummaryQuery += " and r.br_payment_amount=0 and b.bill_outstanding!=0 ";
            }
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,class_name,bill_number,bill_date,btime,br_reciept_number,receiptDate,mrNo,user_firstname,user_lastname,grossAmount,concAmt,netBillAmount,credit,paymentrecieved,balance,userName,cashcounter,unit_id,user_id,br_bill_id";
        // Query += "  limit " + ((invoiceSearchDTO.getOffset() - 1) * invoiceSearchDTO.getLimit()) + "," + invoiceSearchDTO.getLimit();
        System.out.println("MainQuery : " + Query);
        System.out.println("CountQuery : " + CountQuery);
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(SummaryQuery).getResultList();
        try {
            for (Object[] ob : list) {
                jsonArray.getJSONObject(0).put("TotalGrossAmount", ob[0].toString());
                jsonArray.getJSONObject(0).put("TotalconcAmt", ob[1].toString());
                jsonArray.getJSONObject(0).put("TotalNetBillAmount", ob[2].toString());
                jsonArray.getJSONObject(0).put("TotalCredit", ob[3].toString());
                jsonArray.getJSONObject(0).put("TotalPaymentrecieved", ob[4].toString());
                jsonArray.getJSONObject(0).put("TotalBalance", ob[5].toString());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Error:" + e);
        }
        // return ResponseEntity.ok(jsonArray.toString());
        // return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(invoiceSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(invoiceSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (invoiceSearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("InvoiceReport", jsonArray.toString());
        } else {
            return createReport.createJasperReportPDFByJson("InvoiceReport", jsonArray.toString());
        }
    }

}