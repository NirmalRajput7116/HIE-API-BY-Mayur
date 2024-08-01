package com.cellbeans.hspa.mis.misinventorycurrentstockstatement;

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
@RequestMapping("/invstockadjustmentReport")
public class InvStockAdjustmentController {

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

    @RequestMapping("getstockadjustmentReport/{unitList}")
    public List<InvStockAdjustmentListDTO> searchListofStockAdjustreport(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvStockAdjustmentSearchDTO invStockAdjustmentSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String fromdate = invStockAdjustmentSearchDTO.getFromdate(), todate = invStockAdjustmentSearchDTO.getTodate();
        List<InvStockAdjustmentListDTO> listOfStockAdjustDTOList = new ArrayList<InvStockAdjustmentListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String columnName = "";
        String Query = "select tisa.created_date, mu.unit_name, ifnull(isa.store_name,'') as StoreName, ifnull(tisa.isa_item_name,'') as ItemName, ifnull(tisa.isa_item_batch_code,'') as BatchCode, " +
                " ifnull(tisa.isa_item_expiry_date,'') as ExpiryDate, ifnull(tisa.isa_avilable_qty,'') as AvailableQTY, ifnull(tisa.isa_adjust_qty,'') as AdjustQTY, " +
                " ifnull(tisa.isa_operation_type,'') as Operation, ifnull(tisa.isa_remark,'') as Remark, ifnull(tisa.created_by,'') as UserName from tinv_item_stock_adjustment tisa " +
                " inner join mst_unit mu on tisa.isa_unit_id = mu.unit_id " +
                " inner join tinv_opening_balance_item tobi on tisa.isa_id = tobi.obi_id " +
                " left join inv_store isa on tobi.obi_store_id = isa.store_id " +
                " where tisa.is_active = 1 and tisa.is_deleted=0 ";
        // Total Count
        String CountQuery = " select count(*) " +
                " from tinv_item_stock_adjustment tisa  " +
                " inner join mst_unit mu on tisa.isa_unit_id = mu.unit_id  " +
                " inner join tinv_opening_balance_item tobi on tisa.isa_id = tobi.obi_id  " +
                " left join inv_store isa on tobi.obi_store_id = isa.store_id  " +
                " where tisa.is_active = 1 and tisa.is_deleted=0 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (invStockAdjustmentSearchDTO.getTodaydate()) {
            Query += " and (date(tisa.created_date)=curdate()) ";
            CountQuery += " and (date(tisa.created_date)=curdate()) ";

        } else {
            Query += " and (date(tisa.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(tisa.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and mu.unit_id in   (" + values + ") ";
            CountQuery += " and  mu.unit_id in  (" + values + ") ";
        }
        // sotre Id
        if (invStockAdjustmentSearchDTO.getStoreId() != null && !invStockAdjustmentSearchDTO.getStoreId().equals("0")) {
            Query += " and tobi.obi_store_id =  " + invStockAdjustmentSearchDTO.getStoreId() + " ";
            CountQuery += " and tobi.obi_store_id =  " + invStockAdjustmentSearchDTO.getStoreId() + " ";
        }
        if (invStockAdjustmentSearchDTO.getItemName() != null && !invStockAdjustmentSearchDTO.getItemName().equals("0")) {
            Query += " and tisa.isa_item_name like '%" + invStockAdjustmentSearchDTO.getItemName() + "%'";
            CountQuery += " and tisa.isa_item_name like '%" + invStockAdjustmentSearchDTO.getItemName() + "%'";
        }
        System.out.println("Stock Adjustment Report:" + Query);
        try {
            Query += " limit " + ((invStockAdjustmentSearchDTO.getOffset() - 1) * invStockAdjustmentSearchDTO.getLimit()) + "," + invStockAdjustmentSearchDTO.getLimit();
            List<Object[]> stockadjustreport = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : stockadjustreport) {
                InvStockAdjustmentListDTO objStockAdjustListDTO = new InvStockAdjustmentListDTO();
                objStockAdjustListDTO.setDateOfAdjust("" + obj[0]);
                objStockAdjustListDTO.setUnitName("" + obj[1]);
                objStockAdjustListDTO.setStoreName("" + obj[2]);
                objStockAdjustListDTO.setItemName("" + obj[3]);
                objStockAdjustListDTO.setItemBatchName("" + obj[4]);
                objStockAdjustListDTO.setExpDate("" + obj[5]);
                objStockAdjustListDTO.setAvbCurrentQty("" + obj[6]);
                objStockAdjustListDTO.setAdjQty("" + obj[7]);
                objStockAdjustListDTO.setAdjType("" + obj[8]);
//                objStockAdjustListDTO.setNewAvbQty("" + obj[9]);
//                objStockAdjustListDTO.setStockAdjNo("" + obj[10]);
                objStockAdjustListDTO.setRemark("" + obj[9]);
                objStockAdjustListDTO.setUserName("" + obj[10]);
                objStockAdjustListDTO.setCount(count);        // total count
                listOfStockAdjustDTOList.add(objStockAdjustListDTO);  //
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error:" + e);
            //return null;
        }
        return listOfStockAdjustDTOList;
    } // END Service

    @RequestMapping("getstockadjustmentReportPrint/{unitList}")
    public String searchListofStockAdjustreportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvStockAdjustmentSearchDTO invStockAdjustmentSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String fromdate = invStockAdjustmentSearchDTO.getFromdate(), todate = invStockAdjustmentSearchDTO.getTodate();
        List<InvStockAdjustmentListDTO> listOfStockAdjustDTOList = new ArrayList<InvStockAdjustmentListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String columnName = "";
        String Query = "select ifnull(tisa.created_date,'')AS created_date, ifnull(mu.unit_name,'')AS unit_name, " +
                "ifnull(isa.store_name,'') as StoreName, " +
                "ifnull(tisa.isa_item_name,'') as ItemName, " +
                "ifnull(tisa.isa_item_batch_code,'') as BatchCode, " +
                "ifnull(tisa.isa_item_expiry_date,'') as ExpiryDate, " +
                "ifnull(tisa.isa_avilable_qty,0) as AvailableQTY, " +
                "ifnull(tisa.isa_adjust_qty,0) as AdjustQTY, " +
                "ifnull(tisa.isa_operation_type,'') as Operation, " +
                "ifnull(tisa.isa_remark,'') as Remark, " +
                "ifnull(tisa.created_by,'') as UserName from tinv_item_stock_adjustment tisa " +
                " inner join mst_unit mu on tisa.isa_unit_id = mu.unit_id " +
                " inner join tinv_opening_balance_item tobi on tisa.isa_id = tobi.obi_id " +
                " left join inv_store isa on tobi.obi_store_id = isa.store_id " +
                " where tisa.is_active = 1 and tisa.is_deleted=0 ";
        // Total Count
        String CountQuery = " select count(*) " +
                " from tinv_item_stock_adjustment tisa  " +
                " inner join mst_unit mu on tisa.isa_unit_id = mu.unit_id  " +
                " inner join tinv_opening_balance_item tobi on tisa.isa_id = tobi.obi_id  " +
                " left join inv_store isa on tobi.obi_store_id = isa.store_id  " +
                " where tisa.is_active = 1 and tisa.is_deleted=0 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (invStockAdjustmentSearchDTO.getTodaydate()) {
            Query += " and (date(tisa.created_date)=curdate()) ";
            CountQuery += " and (date(tisa.created_date)=curdate()) ";

        } else {
            Query += " and (date(tisa.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(tisa.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and mu.unit_id in   (" + values + ") ";
            CountQuery += " and  mu.unit_id in  (" + values + ") ";
        }
        // sotre Id
        if (invStockAdjustmentSearchDTO.getStoreId() != null && !invStockAdjustmentSearchDTO.getStoreId().equals("0")) {
            Query += " and tobi.obi_store_id =  " + invStockAdjustmentSearchDTO.getStoreId() + " ";
            CountQuery += " and tobi.obi_store_id =  " + invStockAdjustmentSearchDTO.getStoreId() + " ";
        }
        if (invStockAdjustmentSearchDTO.getItemName() != null && !invStockAdjustmentSearchDTO.getItemName().equals("0")) {
            Query += " and tisa.isa_item_name like '%" + invStockAdjustmentSearchDTO.getItemName() + "%'";
            CountQuery += " and tisa.isa_item_name like '%" + invStockAdjustmentSearchDTO.getItemName() + "%'";
        }
        System.out.println("Query:" + Query);
        try {
            // Query += " limit " + ((invStockAdjustmentSearchDTO.getOffset() - 1) * invStockAdjustmentSearchDTO.getLimit()) + "," + invStockAdjustmentSearchDTO.getLimit();
            List<Object[]> stockadjustreport = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : stockadjustreport) {
                InvStockAdjustmentListDTO objStockAdjustListDTO = new InvStockAdjustmentListDTO();
                objStockAdjustListDTO.setDateOfAdjust("" + obj[0]);
                objStockAdjustListDTO.setUnitName("" + obj[1]);
                objStockAdjustListDTO.setStoreName("" + obj[2]);
                objStockAdjustListDTO.setItemName("" + obj[3]);
                objStockAdjustListDTO.setItemBatchName("" + obj[4]);
                objStockAdjustListDTO.setExpDate("" + obj[5]);
                objStockAdjustListDTO.setAvbCurrentQty("" + obj[6]);
                objStockAdjustListDTO.setAdjQty("" + obj[7]);
                objStockAdjustListDTO.setAdjType("" + obj[8]);
//                objStockAdjustListDTO.setNewAvbQty("" + obj[9]);
//                objStockAdjustListDTO.setStockAdjNo("" + obj[10]);
                objStockAdjustListDTO.setRemark("" + obj[9]);
                objStockAdjustListDTO.setUserName("" + obj[10]);
                objStockAdjustListDTO.setCount(count);        // total count
                listOfStockAdjustDTOList.add(objStockAdjustListDTO);  //
            }
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("Error:" + e);
            //return null;
        }
        //   return listOfStockAdjustDTOList;
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(invStockAdjustmentSearchDTO.getUnitId());
        listOfStockAdjustDTOList.get(0).setObjHeaderData(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listOfStockAdjustDTOList);
        // return createReport.createJasperReportXLS("OutwardReferralReport", ds);
        if (invStockAdjustmentSearchDTO.getPrint()) {
            return createReport.createJasperReportXLS("StockAdjustmentReport", ds);
        } else {
            return createReport.createJasperReportPDF("StockAdjustmentReport", ds);
        }
    } // END Service

    //
// Stock Consumption Report start 15.10.2019
    @RequestMapping("getstockaconsumptionReport/{unitList}")
    public List<InvStockConsumptionListDTO> searchListofgetstockaconsumptionreport(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvStockConsumptionSearchDTO invStockConsumptionSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String fromdate = invStockConsumptionSearchDTO.getFromdate(), todate = invStockConsumptionSearchDTO.getTodate();
        List<InvStockConsumptionListDTO> listOfConsumptionDTOList = new ArrayList<InvStockConsumptionListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String columnName = "";
        String Query = " SELECT isc.created_date AS Consumption_Date, isc.sc_id AS Consumption_No, isc.sc_item_name AS Item_Name, " +
                " ifnull(tobi.obi_item_batch_code,'') AS Batch_No, ifnull(tobi.obi_item_expiry_date,'') AS Expiry_Date, " +
                " ifnull(iidt.idt_name,'') AS UOM, ifnull(isc.sc_qty,0) AS QTY, ifnull(tobi.obi_item_mrp,0) AS MRP, " +
                " ifnull(tobi.obi_item_net_amount,0) AS Total_Amount, ifnull(isc.sc_remark,'') AS Remark, " +
                " ifnull(ist.store_name,'') AS Store_Name,ifnull(mu.unit_name,'')AS unit_name, " +
                " ifnull(concat(u.user_firstname,' ', u.user_lastname),'') AS user_name " +
                " FROM inv_store_consumption isc " +
                " INNER JOIN tinv_opening_balance_item tobi on isc.sc_obi_item_id = tobi.obi_id " +
                " left JOIN inv_item_dispensing_type iidt on tobi.obi_item_despensing_idt_id = iidt.idt_id " +
                " INNER JOIN inv_store ist on isc.sc_store_id = ist.store_id " +
                " left join mst_staff s on isc.sc_staff_id = s.staff_id " +
                " left join mst_user u on s.staff_user_id = u.user_id " +
                " inner join mst_unit mu on isc.sc_unit_id = mu.unit_id " +
                " WHERE isc.is_active = 1 AND isc.is_deleted = 0 ";
        // Total Count
        String CountQuery = " select count(*) " +
                " FROM inv_store_consumption isc " +
                " INNER JOIN tinv_opening_balance_item tobi on isc.sc_obi_item_id = tobi.obi_id " +
                " left JOIN inv_item_dispensing_type iidt on tobi.obi_item_despensing_idt_id = iidt.idt_id " +
                " INNER JOIN inv_store ist on isc.sc_store_id = ist.store_id " +
                " left join mst_staff s on isc.sc_staff_id = s.staff_id " +
                " left join mst_user u on s.staff_user_id = u.user_id " +
                " inner join mst_unit mu on isc.sc_unit_id = mu.unit_id " +
                " WHERE isc.is_active = 1 AND isc.is_deleted = 0 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (invStockConsumptionSearchDTO.getTodaydate()) {
            Query += " and (date(isc.created_date)=curdate()) ";
            CountQuery += " and (date(isc.created_date)=curdate()) ";

        } else {
            Query += " and (date(isc.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(isc.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and mu.unit_id in   (" + values + ") ";
            CountQuery += " and  mu.unit_id in  (" + values + ") ";
        }
        // sotre Id
        if (invStockConsumptionSearchDTO.getStoreId() != null && !invStockConsumptionSearchDTO.getStoreId().equals("0")) {
            Query += " and isc.sc_store_id =  " + invStockConsumptionSearchDTO.getStoreId() + " ";
            CountQuery += " and isc.sc_store_id =  " + invStockConsumptionSearchDTO.getStoreId() + " ";
        }
        if (invStockConsumptionSearchDTO.getItemName() != null && !invStockConsumptionSearchDTO.getItemName().equals("0")) {
            Query += " and isc.sc_item_name like '%" + invStockConsumptionSearchDTO.getItemName() + "%'";
            CountQuery += " and isc.sc_item_name like '%" + invStockConsumptionSearchDTO.getItemName() + "%'";
        }
        if (invStockConsumptionSearchDTO.getStaffId() != null && !invStockConsumptionSearchDTO.getStaffId().equals("0")) {
            Query += " and isc.sc_staff_id = " + invStockConsumptionSearchDTO.getStaffId() + " ";
            CountQuery += " and isc.sc_staff_id =  " + invStockConsumptionSearchDTO.getStaffId() + " ";
        }
        Query += "ORDER BY isc.created_date DESC";
        System.out.println("Item-Wise Consumption Report:" + Query);
        try {
            Query += " limit " + ((invStockConsumptionSearchDTO.getOffset() - 1) * invStockConsumptionSearchDTO.getLimit()) + "," + invStockConsumptionSearchDTO.getLimit();
            List<Object[]> stockadjustreport = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : stockadjustreport) {
                InvStockConsumptionListDTO objStockConsumptionListDTO = new InvStockConsumptionListDTO();
                objStockConsumptionListDTO.setScDate("" + obj[0]);
                objStockConsumptionListDTO.setScNo("" + obj[1]);
                objStockConsumptionListDTO.setItemName("" + obj[2]);
                objStockConsumptionListDTO.setScBatchNo("" + obj[3]);
                objStockConsumptionListDTO.setExpDate("" + obj[4]);
                objStockConsumptionListDTO.setuOM("" + obj[5]);
                objStockConsumptionListDTO.setScqty("" + obj[6]);
                objStockConsumptionListDTO.setMrp("" + obj[7]);
                objStockConsumptionListDTO.setTotalAmount("" + obj[8]);
                objStockConsumptionListDTO.setRemark("" + obj[9]);
                objStockConsumptionListDTO.setStoreName("" + obj[10]);
                objStockConsumptionListDTO.setUnitName("" + obj[11]);
                objStockConsumptionListDTO.setUserName("" + obj[12]);
//            	objStockConsumptionListDTO.setGrandTotal("" + obj[13]);
                objStockConsumptionListDTO.setCount(count);                         // total count
                listOfConsumptionDTOList.add(objStockConsumptionListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return listOfConsumptionDTOList;
    } // END Service
// Stock Consumption Report End 15.10.2019

    // Stock Consumption Report start 15.10.2019
    @RequestMapping("getstockaconsumptionReportPrint/{unitList}")
    public String searchListofgetstockaconsumptionreportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvStockConsumptionSearchDTO invStockConsumptionSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String fromdate = invStockConsumptionSearchDTO.getFromdate(), todate = invStockConsumptionSearchDTO.getTodate();
        List<InvStockConsumptionListDTO> listOfConsumptionDTOList = new ArrayList<InvStockConsumptionListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String columnName = "";
        String Query = " SELECT isc.created_date AS Consumption_Date, isc.sc_id AS Consumption_No, isc.sc_item_name AS Item_Name, " +
                " ifnull(tobi.obi_item_batch_code,'') AS Batch_No, ifnull(tobi.obi_item_expiry_date,'') AS Expiry_Date, " +
                " ifnull(iidt.idt_name,'') AS UOM, ifnull(isc.sc_qty,0) AS QTY, ifnull(tobi.obi_item_mrp,0) AS MRP, " +
                " ifnull(tobi.obi_item_net_amount,0) AS Total_Amount, ifnull(isc.sc_remark,'') AS Remark, " +
                " ifnull(ist.store_name,'') AS Store_Name,ifnull(mu.unit_name,'')AS unit_name, " +
                " ifnull(concat(u.user_firstname,' ', u.user_lastname),'') AS user_name " +
                " FROM inv_store_consumption isc " +
                " INNER JOIN tinv_opening_balance_item tobi on isc.sc_obi_item_id = tobi.obi_id " +
                " left JOIN inv_item_dispensing_type iidt on tobi.obi_item_despensing_idt_id = iidt.idt_id " +
                " INNER JOIN inv_store ist on isc.sc_store_id = ist.store_id " +
                " left join mst_staff s on isc.sc_staff_id = s.staff_id " +
                " left join mst_user u on s.staff_user_id = u.user_id " +
                " inner join mst_unit mu on isc.sc_unit_id = mu.unit_id " +
                " WHERE isc.is_active = 1 AND isc.is_deleted = 0 ";
        // Total Count
        String CountQuery = " select count(*) " +
                " FROM inv_store_consumption isc " +
                " INNER JOIN tinv_opening_balance_item tobi on isc.sc_obi_item_id = tobi.obi_id " +
                " left JOIN inv_item_dispensing_type iidt on tobi.obi_item_despensing_idt_id = iidt.idt_id " +
                " INNER JOIN inv_store ist on isc.sc_store_id = ist.store_id " +
                " left join mst_staff s on isc.sc_staff_id = s.staff_id " +
                " left join mst_user u on s.staff_user_id = u.user_id " +
                " inner join mst_unit mu on isc.sc_unit_id = mu.unit_id " +
                " WHERE isc.is_active = 1 AND isc.is_deleted = 0 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (invStockConsumptionSearchDTO.getTodaydate()) {
            Query += " and (date(isc.created_date)=curdate()) ";
            CountQuery += " and (date(isc.created_date)=curdate()) ";

        } else {
            Query += " and (date(isc.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(isc.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and mu.unit_id in   (" + values + ") ";
            CountQuery += " and  mu.unit_id in  (" + values + ") ";
        }
        // sotre Id
        if (invStockConsumptionSearchDTO.getStoreId() != null && !invStockConsumptionSearchDTO.getStoreId().equals("0")) {
            Query += " and isc.sc_store_id =  " + invStockConsumptionSearchDTO.getStoreId() + " ";
            CountQuery += " and isc.sc_store_id =  " + invStockConsumptionSearchDTO.getStoreId() + " ";
        }
        if (invStockConsumptionSearchDTO.getItemName() != null && !invStockConsumptionSearchDTO.getItemName().equals("0")) {
            Query += " and isc.sc_item_name like '%" + invStockConsumptionSearchDTO.getItemName() + "%'";
            CountQuery += " and isc.sc_item_name like '%" + invStockConsumptionSearchDTO.getItemName() + "%'";
        }
        if (invStockConsumptionSearchDTO.getStaffId() != null && !invStockConsumptionSearchDTO.getStaffId().equals("0")) {
            Query += " and isc.sc_staff_id = " + invStockConsumptionSearchDTO.getStaffId() + " ";
            CountQuery += " and isc.sc_staff_id =  " + invStockConsumptionSearchDTO.getStaffId() + " ";
        }
        Query += "ORDER BY isc.created_date DESC";
        System.out.println("Query:" + Query);
        try {
//            Query += " limit " + ((invStockConsumptionSearchDTO.getOffset() - 1) * invStockConsumptionSearchDTO.getLimit()) + "," + invStockConsumptionSearchDTO.getLimit();
            List<Object[]> stockadjustreport = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : stockadjustreport) {
                InvStockConsumptionListDTO objStockConsumptionListDTO = new InvStockConsumptionListDTO();
                objStockConsumptionListDTO.setScDate("" + obj[0]);
                objStockConsumptionListDTO.setScNo("" + obj[1]);
                objStockConsumptionListDTO.setItemName("" + obj[2]);
                objStockConsumptionListDTO.setScBatchNo("" + obj[3]);
                objStockConsumptionListDTO.setExpDate("" + obj[4]);
                objStockConsumptionListDTO.setuOM("" + obj[5]);
                objStockConsumptionListDTO.setScqty("" + obj[6]);
                objStockConsumptionListDTO.setMrp("" + obj[7]);
                objStockConsumptionListDTO.setTotalAmount("" + obj[8]);
                objStockConsumptionListDTO.setRemark("" + obj[9]);
                objStockConsumptionListDTO.setStoreName("" + obj[10]);
                objStockConsumptionListDTO.setUnitName("" + obj[11]);
                objStockConsumptionListDTO.setUserName("" + obj[12]);
//            	objStockConsumptionListDTO.setGrandTotal("" + obj[13]);
                objStockConsumptionListDTO.setCount(count);                         // total count
                listOfConsumptionDTOList.add(objStockConsumptionListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error:" + e);
            //return null;
        }
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(invStockConsumptionSearchDTO.getUnitId());
        listOfConsumptionDTOList.get(0).setObjHeaderData(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listOfConsumptionDTOList);
        if (invStockConsumptionSearchDTO.getPrint()) {
            return createReport.createJasperReportXLS("StockConsumptionReport", ds);
        } else {
            return createReport.createJasperReportPDF("StockConsumptionReport", ds);
        }
    } // END Service
// Stock Consumption Report End 15.10.2019

}