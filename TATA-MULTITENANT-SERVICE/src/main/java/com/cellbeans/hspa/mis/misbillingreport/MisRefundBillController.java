package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.TenantContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_bill_refund_report")
public class MisRefundBillController {

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("searchConsoltantOpd")
    public List<RefundBillListDTO> searchBillRefund(@RequestHeader("X-tenantId") String tenantName, @RequestBody RefundBillSerachDTO refundBillSerachDTO) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<RefundBillListDTO> RefundBillListDTOList = new ArrayList<RefundBillListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String refundQuery = "";
        String CountQuery = "";
        if (!refundBillSerachDTO.getPatientName().equals("")) {
            refundQuery += " where  billDetails.patientName like '%" + refundBillSerachDTO.getPatientName() + "%' ";
            CountQuery += " where  billDetails.patientName like '%" + refundBillSerachDTO.getPatientName() + "%' ";
        } else {
            refundQuery += " where  billDetails.patientName  like '%%' ";
            CountQuery += " where billDetails.patientName  like '%%' ";
        }
        if (refundBillSerachDTO.getFromdate().equals("") || refundBillSerachDTO.getFromdate().equals("null")) {
            refundBillSerachDTO.setFromdate("1990-06-07");
        }
        if (refundBillSerachDTO.getTodate().equals("") || refundBillSerachDTO.getTodate().equals("null")) {
            refundBillSerachDTO.setTodate(strDate);
        }
        if (!String.valueOf(refundBillSerachDTO.getUserId()).equals("0")) {
            refundQuery += "  and userNameTB.staff_id=" + refundBillSerachDTO.getUserId() + " ";
            CountQuery += "  and userNameTB.staff_id=" + refundBillSerachDTO.getUserId() + " ";
        }
        if (!String.valueOf(refundBillSerachDTO.getUnitId()).equals("0")) {
            refundQuery += " and billDetails.unit_id=" + refundBillSerachDTO.getUnitId() + " ";
            CountQuery += " and billDetails.unit_id=" + refundBillSerachDTO.getUnitId() + " ";
        }
        if (!refundBillSerachDTO.getMrNo().equals("")) {
            refundQuery += " and  billDetails.patient_mr_no like  '%" + refundBillSerachDTO.getMrNo() + "%' ";
            CountQuery += " and  billDetails.patient_mr_no like '%" + refundBillSerachDTO.getMrNo() + "%' ";
        }
        if (!refundBillSerachDTO.getRefundreciptNo().equals("")) {
            refundQuery += " and  billDetails.patient_reci like  '%" + refundBillSerachDTO.getRefundreciptNo() + "%' ";
            CountQuery += " and  billDetails.patient_reci like '%" + refundBillSerachDTO.getRefundreciptNo() + "%' ";
        }
        if (!refundBillSerachDTO.getBillNo().equals("")) {
            refundQuery += " and  billDetails.patient_bill no like  '%" + refundBillSerachDTO.getBillNo() + "%' ";
            CountQuery += " and  billDetails.patient_bill_no like '%" + refundBillSerachDTO.getBillNo() + "%' ";
        }
        if (refundBillSerachDTO.getFromdate().equals("")) {
            refundBillSerachDTO.setFromdate("1990-06-07");
        }
        if (refundBillSerachDTO.getTodate().equals("")) {
            refundBillSerachDTO.setTodate(strDate);
        }
        if (refundBillSerachDTO.isTodaydate()) {
            refundQuery += "  and date(billDetails.bill_date)=curdate()='" + strDate + "' ";
            CountQuery += "  and date(billDetails.bill_date)=curdate()='" + strDate + "' ";
        } else {
            refundQuery += " and  (date(billDetails.bill_date) between '" + refundBillSerachDTO.getFromdate() + "' and '" + refundBillSerachDTO.getTodate() + "') ";
            CountQuery += " and  (date(billDetails.bill_date) between '" + refundBillSerachDTO.getFromdate() + "' and '" + refundBillSerachDTO.getTodate() + "') ";
        }
        if (refundBillSerachDTO.getIPDFlag()) {
            refundQuery += "and tb.bill_ipd_number is not null";
            CountQuery += "and tb.bill_ipd_number is not null";
            // CountQuery += " where tb.bill_ipd_number is not null" + refundBillSerachDTO.getPatientName() + "%' ";
        } else {
            refundQuery += " and tb.bill_opd_number is not null ";
            CountQuery += " and tb.bill_opd_number is not null ";
            //CountQuery += " where tb.bill_opd_number is not null ";
        }
        try {
            refundQuery += " limit " + ((refundBillSerachDTO.getOffset() - 1) * refundBillSerachDTO.getLimit()) + "," + refundBillSerachDTO.getLimit();
            // System.out.println("CalSumQuery1" + CalSumQuery1);
            //  System.out.println("CalSumQuery2" + CalSumQuery2);
//            System.out.println("Main Query" + query2);
            List<Object[]> listTBillBillRefund = entityManager.createNativeQuery(refundQuery).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listTBillBillRefund) {
                RefundBillListDTO objRefundBillListDTO = new RefundBillListDTO();
                objRefundBillListDTO.setRefundReciptNo("" + obj[1]);
                objRefundBillListDTO.setRefundReciptDate("" + obj[2]);
                objRefundBillListDTO.setMrNo("" + obj[3]);
                objRefundBillListDTO.setBillno("" + obj[4]);
                objRefundBillListDTO.setOpIpNo("" + obj[5]);
                objRefundBillListDTO.setPatientName("" + obj[6]);
                objRefundBillListDTO.setUserName("" + obj[7]);
                objRefundBillListDTO.setUnitName("" + obj[8]);
                objRefundBillListDTO.setRefundAmount("" + obj[9]);
                objRefundBillListDTO.setUnitId(Long.parseLong("" + obj[10]));
                objRefundBillListDTO.setServiceId(Long.parseLong("" + obj[11]));
                objRefundBillListDTO.setStaffId(Long.parseLong("" + obj[12]));
                objRefundBillListDTO.setCount(count);
                RefundBillListDTOList.add(objRefundBillListDTO);
            }

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        return RefundBillListDTOList;
    }

}
