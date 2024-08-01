package com.cellbeans.hspa.mis.payoutreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
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
@RequestMapping("/payout")
public class PayoutReportController {

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

    // Payout test Report start 13.11.2019
    @RequestMapping("report/{unitList}")
    public List<PayoutReportListDTO> searchListofgetPayoutReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody PayoutReportSearchDTO payoutReportSearchDTO, @PathVariable String[] unitList) {
        long count = 0;
        String MainQuery = "";
        String fromdate = payoutReportSearchDTO.getFromdate(), todate = payoutReportSearchDTO.getTodate();
        List<PayoutReportListDTO> listOfPayoutReportListDTO = new ArrayList<PayoutReportListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " Select  p.created_date as dateAndTime, p.payout_service_type as TransactionType, IFFNULL(mcc.cc_name,'') as fromCashCounter, " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS fromStaff, " +
                " IFNULL(mcc1.cc_name,'') as toCashCounter, " +
                " IFNULL(CONCAT(mt1.title_name,' ',mu1.user_firstname,' ',mu1.user_lastname),'') AS toStaff, " +
                " p.payout_amount as Amount, " +
                " IFNULL(p.payout_remarks,'') as remarks " +
                " from trn_payout p " +
                " Left join mst_staff ms1 on p.payout_from_staff_id = ms1.staff_id " +
                " Left join mst_user mu on ms1.staff_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " Left join mst_staff ms2 on p.payout_to_staff_id = ms2.staff_id  " +
                " Left join mst_user mu1 on ms2.staff_id = mu1.user_id  " +
                " LEFT JOIN mst_title mt1 ON mu1.user_title_id= mt1.title_id " +
                " Left join mst_cash_counter mcc on p.payoutfrom_cc_id = mcc.cc_id " +
                " Left join mst_cash_counter mcc1 on p.payout_to_cc_id = mcc1.cc_id " +
                " where p.is_active = 1 and p.is_deleted = 0 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (payoutReportSearchDTO.getTodaydate()) {
            Query += " and (date(p.created_date)=curdate()) ";

        } else {
            Query += " and (date(p.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and  p.payout_unit_id in   (" + values + ") ";
        }
        if (payoutReportSearchDTO.getFromccId() != null && !payoutReportSearchDTO.getFromccId().equals("")) {
            Query += " and  mcc.cc_id = " + payoutReportSearchDTO.getFromccId();
        }
        if (payoutReportSearchDTO.getToccId() != null && !payoutReportSearchDTO.getToccId().equals("")) {
            Query += " and mcc1.cc_id = " + payoutReportSearchDTO.getToccId();
        }
        if (payoutReportSearchDTO.getFromstaffId() != null && !payoutReportSearchDTO.getFromstaffId().equals("")) {
            Query += " and ms1.staff_id = " + payoutReportSearchDTO.getFromstaffId();
        }
        if (payoutReportSearchDTO.getTostaffId() != null && !payoutReportSearchDTO.getTostaffId().equals("")) {
            Query += "  and  ms2.staff_id = " + payoutReportSearchDTO.getTostaffId();
        }
        if (payoutReportSearchDTO.getUserId() != null && !payoutReportSearchDTO.getUserId().equals("")) {
            Query += " p.payout_to_staff_id =" + payoutReportSearchDTO.getUserId();
        }
        if (payoutReportSearchDTO.getClclId() == "0") {
            Query += " and p.payout_service_type = 'expense'";
        } else if (payoutReportSearchDTO.getClclId() == "1") {
            Query += " and p.payout_service_type = 'bankdesposit'";
        } else if (payoutReportSearchDTO.getClclId() == "2") {
            Query = " and p.payout_service_type = 'cashHandover'";
        } else if (payoutReportSearchDTO.getClclId() == "2") {
            Query += " and p.payout_service_type = 'income'";
        } else {
        }
        System.out.println("MainQuery1:" + Query);
        String CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        System.out.println("MainQuery:" + Query);
        try {
            MainQuery += " limit " + ((payoutReportSearchDTO.getOffset() - 1) * payoutReportSearchDTO.getLimit()) + "," + payoutReportSearchDTO.getLimit();
            List<Object[]> payoutReportList = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : payoutReportList) {
                PayoutReportListDTO objPayoutReportListDTO = new PayoutReportListDTO();
                objPayoutReportListDTO.setDateAndTime("" + obj[0]);
                objPayoutReportListDTO.setTransactionType("" + obj[1]);
                objPayoutReportListDTO.setFromCashCounter("" + obj[2]);
                objPayoutReportListDTO.setFromStaff("" + obj[3]);
                objPayoutReportListDTO.setToCashCounter("" + obj[4]);
                objPayoutReportListDTO.setToStaff("" + obj[5]);
                objPayoutReportListDTO.setAmount("" + obj[6]);
                objPayoutReportListDTO.setRemarks("" + obj[7]);
                objPayoutReportListDTO.setCount(count);        // total count
                listOfPayoutReportListDTO.add(objPayoutReportListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return listOfPayoutReportListDTO;
    } // END Service

}