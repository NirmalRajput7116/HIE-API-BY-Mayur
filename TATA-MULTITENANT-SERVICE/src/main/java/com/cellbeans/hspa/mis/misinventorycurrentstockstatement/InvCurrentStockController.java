package com.cellbeans.hspa.mis.misinventorycurrentstockstatement;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/invcurrentstockReport")
public class InvCurrentStockController {

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

    @RequestMapping("getcurrentstockReport/{unitList}")
    public List<InvCurrentStockListDTO> searchCurrentStockReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvCurrentStockSearchDTO invCurrentStockSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String fromdate = invCurrentStockSearchDTO.getStartdate(), todate = invCurrentStockSearchDTO.getEnddate();
        // fromQty toQty
        long fromQty = invCurrentStockSearchDTO.getFromQuantity(), toQty = invCurrentStockSearchDTO.getToQuantity();
        List<InvCurrentStockListDTO> listOfCurrentStockDTOList = new ArrayList<InvCurrentStockListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String columnName = "";
        String Query = " SELECT ifnull(un.unit_name,'')AS unitname,ifnull(pb.obi_item_name,'')AS itemName, " +
                " ifnull(ns.store_name,'')AS storeName,ifnull(pb.obi_item_batch_code,'')AS BatchCode, " +
                " CONCAT( ifnull(pb.obi_item_quantity,'') ,' - ',ifnull(dy.idt_name,''))as AvailableStock, " +
                " ifnull(it.item_minimum_quantity,'')AS minimumQuantity,ifnull(pb.obi_item_expiry_date,'')AS expiryDate , " +
                " ifnull(pb.obi_item_mrp,'')AS MRP, " +
                " ifnull(pb.obi_item_cost,'')AS PurchaseRate, " +
                " ifnull(pb.created_date,'')AS StockDate " +
                " FROM tinv_opening_balance_item pb " +
                " LEFT JOIN inv_store ns ON pb.obi_store_id=ns.store_id " +
                " LEFT JOIN tinv_opening_balance ob ON pb.obiob_id=ob.ob_id " +
                " LEFT JOIN mst_unit un ON pb.opening_balance_unit_id=un.unit_id " +
                " LEFT JOIN inv_item it ON pb.obi_item_id=it.item_id " +
                " LEFT JOIN inv_item_dispensing_type dy ON pb.obi_item_despensing_idt_id  = dy.idt_id" +
                " WHERE pb.is_active=1 AND pb.is_deleted=0 AND pb.is_approved=1 AND pb.obi_item_quantity > 0 AND un.unit_id in (1)";
        // Total Count
        String CountQuery = "SELECT count(*) " +
                " FROM tinv_opening_balance_item pb " +
                " LEFT JOIN inv_store ns ON pb.obi_store_id=ns.store_id " +
                " LEFT JOIN tinv_opening_balance ob ON pb.obiob_id=ob.ob_id " +
                " LEFT JOIN mst_unit un ON pb.opening_balance_unit_id=un.unit_id " +
                " LEFT JOIN inv_item it ON pb.obi_item_id=it.item_id " +
                " LEFT JOIN inv_item_dispensing_type dy ON pb.obi_item_despensing_idt_id = dy.idt_id" +
                " WHERE pb.is_active=1 AND pb.is_deleted=0 AND pb.is_approved=1 AND pb.obi_item_quantity > 0 AND un.unit_id in (1)";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (invCurrentStockSearchDTO.getTodaydate()) {
            Query += " and (date(pb.created_date)=curdate()) ";
            CountQuery += " and (date(pb.created_date)=curdate()) ";
        } else {
            Query += " and (date(pb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(pb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        // Unit List
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and un.unit_id in  (" + values + ") ";
            CountQuery += " and un.unit_id in  (" + values + ") ";
        }
        // Item Name
        if (invCurrentStockSearchDTO.getItemName() != null && !invCurrentStockSearchDTO.getItemName().equals("")) {
            Query += " and pb.obi_item_name like '%" + invCurrentStockSearchDTO.getItemName() + "%'";
            CountQuery += " and pb.obi_item_name like '%" + invCurrentStockSearchDTO.getItemName() + "%'";
        }
        // sotre Id
        if (invCurrentStockSearchDTO.getStoreId() != 0) {
            Query += " and ns.store_id =  " + invCurrentStockSearchDTO.getStoreId() + " ";
            CountQuery += " and ns.store_id =  " + invCurrentStockSearchDTO.getStoreId() + " ";
        }
        // Batch Code
        if (invCurrentStockSearchDTO.getItemBatchCode() != 0) {
            Query += " and pb.obi_item_batch_code like '%" + invCurrentStockSearchDTO.getItemBatchCode() + "%'";
            CountQuery += " and pb.obi_item_batch_code like '%" + invCurrentStockSearchDTO.getItemBatchCode() + "%'";
        }
        if (invCurrentStockSearchDTO.getItemExpUltimatum() != 0) {
            LocalDate ultimateDate = LocalDate.now().plusMonths(invCurrentStockSearchDTO.getItemExpUltimatum());
            System.out.println(ultimateDate);
            // SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");
            Query += " And date(pb.obi_item_expiry_date) between date(NOW()) And '" + ultimateDate + "'";

        } else if (invCurrentStockSearchDTO.getItemExpUltimatum() != 0) {
            String sDate1 = "1998/12/31";
            invCurrentStockSearchDTO.setStartdate(sDate1);
            if (invCurrentStockSearchDTO.getEnddate() != null) {
                Query += "AND date(pb.obi_item_expiry_date) between '" + invCurrentStockSearchDTO.getStartdate()
                        + "' AND  '" + invCurrentStockSearchDTO.getEnddate() + "' ";
            }
        }
        // fromExpiry Date  toExpiry Date
        if (invCurrentStockSearchDTO.getStartdate() != "" || invCurrentStockSearchDTO.getEnddate() != "") {
            Query += " and (date(pb.obi_item_expiry_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(pb.obi_item_expiry_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        // from quantity  To Quantity
        if (invCurrentStockSearchDTO.getFromQuantity() != 0 && invCurrentStockSearchDTO.getToQuantity() != 0) {
            Query += " and it.item_minimum_quantity between " + fromQty + " and " + toQty + " ";
            CountQuery += " and it.item_minimum_quantity between " + fromQty + " and " + toQty + " ";
        }
        System.out.println("Current Stock Statement Report:" + Query);
        try {
            Query += " order by pb.obi_id desc limit " + ((invCurrentStockSearchDTO.getOffset() - 1) * invCurrentStockSearchDTO.getLimit()) + "," + invCurrentStockSearchDTO.getLimit();
            List<Object[]> currentstockreport = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : currentstockreport) {
                InvCurrentStockListDTO objcurrentStockListDTO = new InvCurrentStockListDTO();
                objcurrentStockListDTO.setUnitName("" + obj[0]);
                objcurrentStockListDTO.setItemName("" + obj[1]);
                objcurrentStockListDTO.setStoreName("" + obj[2]);
                objcurrentStockListDTO.setBatchCode("" + obj[3]);
                objcurrentStockListDTO.setAvailableStock("" + obj[4]);
                objcurrentStockListDTO.setMinimumQuantity("" + obj[5]);
                objcurrentStockListDTO.setExpiryDate("" + obj[6]);
                objcurrentStockListDTO.setMrp("" + obj[7]);
                objcurrentStockListDTO.setPurchaseRate("" + obj[8]);
                objcurrentStockListDTO.setStockdate("" + obj[9]);
                objcurrentStockListDTO.setCount(count);        // total count
                listOfCurrentStockDTOList.add(objcurrentStockListDTO);  //
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error:" + e);
            //return null;
        }
        return listOfCurrentStockDTOList;

    } // end of service

    @RequestMapping("getcurrentstockitemvaluationReport/{unitList}")
    public List<InvCurrenSstockitemValuationListDTO> searchCurrentStockItemValuationReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvCurrenSstockitemValuationSearchDTO invCurrensstockitemvaluationSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String fromdate = invCurrensstockitemvaluationSearchDTO.getStartdate(), todate = invCurrensstockitemvaluationSearchDTO.getEnddate();
//        // fromQty toQty
//        long fromQty = invCurrensstockitemvaluationSearchDTO.getFromQuantity(), toQty = invCurrensstockitemvaluationSearchDTO.getToQuantity();
        List<InvCurrenSstockitemValuationListDTO> InvCurrenSstockitemValuationDTODTOList = new ArrayList<InvCurrenSstockitemValuationListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String columnName = "";
        String Query = " SELECT IFNULL(pb.obi_item_name,'') AS itemName, IFNULL(un.unit_name,'') AS unitname,  " +
                " IFNULL(pb.obi_item_batch_code,'') AS BatchCode,  " +
                " iFNULL(pb.obi_item_quantity,'') AS Qty,  " +
                " iFNULL(pb.obi_item_mrp,'') AS TotalMRP,  " +
                " IFNULL(pb.obi_item_cost,'') AS UnitPurchasecost,tx.tax_name AS TaxPercent,pb.obi_item_tax_amount AS TaxAmount " +
                " ,pb.obi_item_unit_cost_mrp AS UnitMrp " +
                " FROM tinv_opening_balance_item pb " +
                " LEFT JOIN inv_store ns ON pb.obi_store_id=ns.store_id " +
                " LEFT JOIN tinv_opening_balance ob ON pb.obiob_id=ob.ob_id " +
                " LEFT JOIN mst_unit un ON ob.opening_balance_unit_id=un.unit_id " +
                " LEFT JOIN inv_item it ON pb.obi_item_id=it.item_id " +
                " LEFT JOIN inv_item_dispensing_type dy ON pb.obi_item_despensing_idt_id " +
                " LEFT JOIN inv_tax tx ON tx.tax_id=pb.obi_item_tax_id " +
                " WHERE pb.is_active=1 ";
        // Total Count
        String CountQuery = " SELECT count(*) " +
                " FROM tinv_opening_balance_item pb " +
                " LEFT JOIN inv_store ns ON pb.obi_store_id=ns.store_id " +
                " LEFT JOIN tinv_opening_balance ob ON pb.obiob_id=ob.ob_id " +
                " LEFT JOIN mst_unit un ON ob.opening_balance_unit_id=un.unit_id " +
                " LEFT JOIN inv_item it ON pb.obi_item_id=it.item_id " +
                " LEFT JOIN inv_item_dispensing_type dy ON pb.obi_item_despensing_idt_id " +
                " LEFT JOIN inv_tax tx ON tx.tax_id=pb.obi_item_tax_id " +
                " WHERE pb.is_active=1 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (invCurrensstockitemvaluationSearchDTO.getTodaydate()) {
            Query += " and (date(pb.created_date)=curdate()) ";
            CountQuery += " and (date(pb.created_date)=curdate()) ";
        } else {
            Query += " and (date(pb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(pb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
//        // Unit List
//        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
//            String values = String.valueOf(unitList[0]);
//            for (int i = 1; i < unitList.length; i++) {
//                values += "," + unitList[i];
//            }
//            Query += " and un.unit_id in  (" + values + ") ";
//            CountQuery += " and un.unit_id in  (" + values + ") ";
//        }
        // Item Name
        if (invCurrensstockitemvaluationSearchDTO.getItemName() != null && !invCurrensstockitemvaluationSearchDTO.getItemName().equals("")) {
            Query += " and pb.obi_item_name like '%" + invCurrensstockitemvaluationSearchDTO.getItemName() + "%'";
            CountQuery += " and pb.obi_item_name like '%" + invCurrensstockitemvaluationSearchDTO.getItemName() + "%'";
        }
        // sotre Id
        if (invCurrensstockitemvaluationSearchDTO.getStoreId() != 0) {
            Query += " and ns.store_id =  " + invCurrensstockitemvaluationSearchDTO.getStoreId() + " ";
            CountQuery += " and ns.store_id =  " + invCurrensstockitemvaluationSearchDTO.getStoreId() + " ";
        }
        // Batch Code
        if (invCurrensstockitemvaluationSearchDTO.getItemBatchCode() != 0) {
            Query += " and pb.obi_item_batch_code like '%" + invCurrensstockitemvaluationSearchDTO.getItemBatchCode() + "%'";
            CountQuery += " and pb.obi_item_batch_code like '%" + invCurrensstockitemvaluationSearchDTO.getItemBatchCode() + "%'";
        }
//        if ( invCurrensstockitemvaluationSearchDTO.getItemExpUltimatum() != 0) {
//            LocalDate ultimateDate = LocalDate.now().plusMonths( invCurrensstockitemvaluationSearchDTO.getItemExpUltimatum());
//            System.out.println(ultimateDate);
//            // SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");
//            Query += " And date(pb.obi_item_expiry_date) between date(NOW()) And '" + ultimateDate + "'";
//
//        }else if( invCurrensstockitemvaluationSearchDTO.getItemExpUltimatum() != 0) {
//            String sDate1 = "1998/12/31";
//             invCurrensstockitemvaluationSearchDTO.setStartdate(sDate1);
//            if ( invCurrensstockitemvaluationSearchDTO.getEnddate() != null) {
//                Query += "AND date(pb.obi_item_expiry_date) between '" +  invCurrensstockitemvaluationSearchDTO.getStartdate()
//                        + "' AND  '" +  invCurrensstockitemvaluationSearchDTO.getEnddate() + "' ";
//            }
//        }
        // fromExpiry Date  toExpiry Date
        if (invCurrensstockitemvaluationSearchDTO.getStartdate() != "" || invCurrensstockitemvaluationSearchDTO.getEnddate() != "") {
            Query += " and (date(pb.obi_item_expiry_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(pb.obi_item_expiry_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
//        // from quantity  To Quantity
//        if ( invCurrensstockitemvaluationSearchDTO.getFromQuantity() != 0  &&  invCurrensstockitemvaluationSearchDTO.getToQuantity() != 0) {
//            Query += " and it.item_minimum_quantity between " + fromQty + " and " + toQty + " ";
//            CountQuery += " and it.item_minimum_quantity between " + fromQty + " and " + toQty + " ";
//        }
        System.out.println("Query:" + Query);
        try {
            Query += " limit " + ((invCurrensstockitemvaluationSearchDTO.getOffset() - 1) * invCurrensstockitemvaluationSearchDTO.getLimit()) + "," + invCurrensstockitemvaluationSearchDTO.getLimit();
            List<Object[]> currentstockreport = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : currentstockreport) {
                InvCurrenSstockitemValuationListDTO objInvCurrenSstockitemValuationListDTO = new InvCurrenSstockitemValuationListDTO();
                objInvCurrenSstockitemValuationListDTO.setItemName("" + obj[0]);
                objInvCurrenSstockitemValuationListDTO.setUnitName("" + obj[1]);
                objInvCurrenSstockitemValuationListDTO.setBatch("" + obj[2]);
                objInvCurrenSstockitemValuationListDTO.setQty("" + obj[3]);
                objInvCurrenSstockitemValuationListDTO.setTotalMrp("" + obj[4]);
                objInvCurrenSstockitemValuationListDTO.setUnitPurchaceCost("" + obj[5]);
                objInvCurrenSstockitemValuationListDTO.setTaxPerUnit("" + obj[6]);
                objInvCurrenSstockitemValuationListDTO.setTaxAmount("" + obj[7]);
                objInvCurrenSstockitemValuationListDTO.setUnitMrp("" + obj[8]);
                double c = Double.parseDouble("" + obj[5]) + Double.parseDouble("" + obj[7]);
                double d = Double.parseDouble("" + obj[5]) * Double.parseDouble("" + obj[3]);
                double e = Double.parseDouble("" + obj[5]) * Double.parseDouble("" + obj[7]);
                objInvCurrenSstockitemValuationListDTO.setPurchaseAmountPerUnit("" + c);
                objInvCurrenSstockitemValuationListDTO.setPurchaseAmount("" + d);
                objInvCurrenSstockitemValuationListDTO.setTaxAmountTt("" + e);
                double f = (Double.parseDouble("" + obj[5]) * Double.parseDouble("" + obj[3])) + (Double.parseDouble("" + obj[5]) * Double.parseDouble("" + obj[7]));
                objInvCurrenSstockitemValuationListDTO.setTotalPurchaseAmountTax("" + f);
                double g = Double.parseDouble("" + obj[8]) * Double.parseDouble("" + obj[3]);
                objInvCurrenSstockitemValuationListDTO.setTotalMrp("" + g);
                objInvCurrenSstockitemValuationListDTO.setCount(count);        // total count
                InvCurrenSstockitemValuationDTODTOList.add(objInvCurrenSstockitemValuationListDTO);  //
            }

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error:" + e);
            //return null;
        }
        return InvCurrenSstockitemValuationDTODTOList;

    } // end of service

}