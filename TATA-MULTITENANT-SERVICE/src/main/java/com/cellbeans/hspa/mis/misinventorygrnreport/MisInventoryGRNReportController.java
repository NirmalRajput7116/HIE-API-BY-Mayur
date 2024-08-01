package com.cellbeans.hspa.mis.misinventorygrnreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.cellbeans.hspa.tbillbillSponsor.TrnBillBillSponsor;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvpurchasegoodsreceivednote.TinvPurchaseGoodsReceivedNote;
//import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/mis_inventory_grn_report")
public class MisInventoryGRNReportController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateReport createReport;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    MstUserRepository mstUserRepository;

    @RequestMapping("search/{unitList}/{searchFromDate}/{searchToDate}")
    public List<TinvPurchaseGoodsReceivedNote> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName,
                                                                            @RequestBody SearchInventoryGRNReport searchInventoryGRNReport, @PathVariable Long[] unitList, @PathVariable String searchFromDate, @PathVariable String searchToDate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<TinvPurchaseGoodsReceivedNote> ItemList = new ArrayList<TinvPurchaseGoodsReceivedNote>();
        if (searchInventoryGRNReport == null) {
            SearchInventoryGRNReport sb = new SearchInventoryGRNReport();
            searchInventoryGRNReport = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        String unitSerch = "";
        String ifPo = "";
        if (searchInventoryGRNReport.getSearchGrnNo() == null)
            searchInventoryGRNReport.setSearchGrnNo("");
        if (searchInventoryGRNReport.getSearchPoNo() == null)
            searchInventoryGRNReport.setSearchPoNo("");
        else {
            ifPo = "AND i.pgrnPpoId.ppoPqId.pqPieId.pieEnquiryNo like '%"
                    + searchInventoryGRNReport.getSearchPieEnquiryNo() + "%' AND i.pgrnPpoId.ppoPqId like '%"
                    + searchInventoryGRNReport.getSearchPoNo() + "%'"
                    + "  AND  i.pgrnPpoId.ppoPqId.pqQuatationRefNo like '%"
                    + searchInventoryGRNReport.getSearchQuotationRefNo() + "%' ";
        }
        if (searchInventoryGRNReport.getSearchQuotationRefNo() == null)
            searchInventoryGRNReport.setSearchQuotationRefNo("");
        else {
            ifPo = "AND i.pgrnPpoId.ppoPqId.pqPieId.pieEnquiryNo like '%"
                    + searchInventoryGRNReport.getSearchPieEnquiryNo() + "%' AND i.pgrnPpoId.ppoPqId like '%"
                    + searchInventoryGRNReport.getSearchPoNo() + "%'"
                    + "  AND  i.pgrnPpoId.ppoPqId.pqQuatationRefNo like '%"
                    + searchInventoryGRNReport.getSearchQuotationRefNo() + "%' ";
        }
        if (searchInventoryGRNReport.getSearchPieEnquiryNo() == null)
            searchInventoryGRNReport.setSearchPieEnquiryNo("");
        else {
            ifPo = "AND i.pgrnPpoId.ppoPqId.pqPieId.pieEnquiryNo like '%"
                    + searchInventoryGRNReport.getSearchPieEnquiryNo() + "%' AND i.pgrnPpoId.ppoPqId like '%"
                    + searchInventoryGRNReport.getSearchPoNo() + "%'"
                    + "  AND  i.pgrnPpoId.ppoPqId.pqQuatationRefNo like '%"
                    + searchInventoryGRNReport.getSearchQuotationRefNo() + "%' ";
        }
        if (searchInventoryGRNReport.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchInventoryGRNReport.setSearchFromDate(sDate1);
        }
        if (searchInventoryGRNReport.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchInventoryGRNReport.setSearchToDate(date);
        }
        if (searchInventoryGRNReport.getSearchStoreId().getStoreId() != 0)
            itemStore = "AND i.pgrnStoreId.storeId=" + searchInventoryGRNReport.getSearchStoreId().getStoreId() + "";
        if (searchInventoryGRNReport.getSearchSupplierId().getSupplierId() != 0)
            itemSupplier = "AND i.pgrnSupplierId.supplierId="
                    + searchInventoryGRNReport.getSearchSupplierId().getSupplierId() + "";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            unitSerch += " i.grnUnitId.unitId in (" + values + ") ";
        }
        queryInya = "Select i from TinvPurchaseGoodsReceivedNote i Where " + unitSerch + " And i.pgrnGrnNo like '%"
                + searchInventoryGRNReport.getSearchGrnNo() + "%'  AND date(i.createdDate) between   " + " ' "
                + searchInventoryGRNReport.getSearchFromDate() + " ' AND  " + "'"
                + searchInventoryGRNReport.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " " + ifPo
                + " order by i.pgrnId desc";
        List<TinvPurchaseGoodsReceivedNote> ItemListfinal = new ArrayList<TinvPurchaseGoodsReceivedNote>();
        if (searchInventoryGRNReport.isPrint()) {
            List<TinvOpeningBalanceItem> tinvOpeningBalanceItemsListtemp = new ArrayList<TinvOpeningBalanceItem>();
            count = (Long) entityManager
                    .createQuery("Select count(i.pgrnId) from TinvPurchaseGoodsReceivedNote i Where " + unitSerch
                            + " And i.pgrnGrnNo like '%" + searchInventoryGRNReport.getSearchGrnNo()
                            + "%'  AND date(i.createdDate) between   " + " ' "
                            + searchInventoryGRNReport.getSearchFromDate() + " ' AND  " + "'"
                            + searchInventoryGRNReport.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " "
                            + ifPo + " order by i.pgrnId desc")
                    .getSingleResult();
            ItemList = entityManager.createQuery(queryInya, TinvPurchaseGoodsReceivedNote.class).getResultList();
            for (TinvPurchaseGoodsReceivedNote objItemList : ItemList) {
                List<TinvOpeningBalanceItem> tinvOpeningBalanceItemsList = new ArrayList<TinvOpeningBalanceItem>();
                String openQuery = "select * from tinv_opening_balance_item p where p.obi_pgrn_id="
                        + objItemList.getPgrnId();
                tinvOpeningBalanceItemsListtemp = entityManager
                        .createNativeQuery(openQuery, TinvOpeningBalanceItem.class).getResultList();
                for (TinvOpeningBalanceItem openlist : tinvOpeningBalanceItemsListtemp) {
                    TinvOpeningBalanceItem objOpeningBal = new TinvOpeningBalanceItem();
                    objOpeningBal.setObiId((int) openlist.getObiId());
                    objOpeningBal.setObiItemId(openlist.getObiItemId());
                    objOpeningBal.setObiStoreId(openlist.getObiStoreId());
                    objOpeningBal.setObiItemBatchCode(openlist.getObiItemBatchCode());
                    objOpeningBal.setObiItemName(openlist.getObiItemName());
                    objOpeningBal.setObiItemExpiryDate(openlist.getObiItemExpiryDate());
                    objOpeningBal.setObiItemPurchaseIdtId(openlist.getObiItemPurchaseIdtId());
                    objOpeningBal.setObiItemCategoryId(openlist.getObiItemCategoryId());
                    objOpeningBal.setObiobId(openlist.getObiobId());
                    objOpeningBal.setObiItemDespensingIdtId(openlist.getObiItemDespensingIdtId());
                    objOpeningBal.setObiItemSaleIdtId(openlist.getObiItemSaleIdtId());
                    objOpeningBal.setObiItemConversionFactor(openlist.getObiItemConversionFactor());
                    objOpeningBal.setObiItemQuantity(openlist.getObiItemQuantity());
                    objOpeningBal.setObiItemFreeQuantity(openlist.getObiItemFreeQuantity());
                    objOpeningBal.setObiItemCode(openlist.getObiItemCode());
                    objOpeningBal.setObiItemCost(openlist.getObiItemCost());
                    objOpeningBal.setObiItemMrp(openlist.getObiItemMrp());
                    objOpeningBal.setObiItemTaxId(openlist.getObiItemTaxId());
                    objOpeningBal.setObiItemAmount(openlist.getObiItemAmount());
                    objOpeningBal.setObiItemDiscountOnSalePercentage(openlist.getObiItemDiscountOnSalePercentage());
                    objOpeningBal.setObiItemTaxAmount(openlist.getObiItemTaxAmount());
                    objOpeningBal.setObiItemNetAmount(openlist.getObiItemNetAmount());
                    objOpeningBal.setIsDeleted(openlist.getIsDeleted());
                    objOpeningBal.setIsActive(openlist.getIsActive());
                    objOpeningBal.setObiItemDiscountOnSaleAmount(openlist.getObiItemDiscountOnSaleAmount());
                    objOpeningBal.setCreatedBy(openlist.getCreatedBy());
                    objOpeningBal.setLastModifiedBy(openlist.getLastModifiedBy());
                    objOpeningBal.setCreatedDate(openlist.getCreatedDate());
                    objOpeningBal.setLastModifiedDate(openlist.getLastModifiedDate());
                    objOpeningBal.setOpeningBalanceUnitId(openlist.getOpeningBalanceUnitId());
                    tinvOpeningBalanceItemsList.add(objOpeningBal);
                }
                objItemList.setTinvOpeningBalanceItems(tinvOpeningBalanceItemsList);
                ItemListfinal.add(objItemList);
            }
        } else {
            count = (Long) entityManager
                    .createQuery("Select count(i.pgrnId) from TinvPurchaseGoodsReceivedNote i Where " + unitSerch
                            + " And i.pgrnGrnNo like '%" + searchInventoryGRNReport.getSearchGrnNo()
                            + "%'  AND date(i.createdDate) between   " + " ' "
                            + searchInventoryGRNReport.getSearchFromDate() + " ' AND  " + "'"
                            + searchInventoryGRNReport.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " "
                            + ifPo + " order by i.pgrnId desc")
                    .getSingleResult();
            ItemListfinal = entityManager.createQuery(queryInya, TinvPurchaseGoodsReceivedNote.class)
                    .setFirstResult(searchInventoryGRNReport.getOffset())
                    .setMaxResults(searchInventoryGRNReport.getLimit()).getResultList();
        }
        if (ItemListfinal.size() > 0)
            ItemListfinal.get(0).setCount(count);
        return ItemListfinal;
    }

    @RequestMapping("getFinalizedEmrSummaryCountReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchFinalisedSummarycount(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT ifnull(mun.unit_name,'')as unit_name, IFNULL(mu.user_fullname,'')as doctorname , COUNT(tm.timelineemrfinal_staff_id) AS finalisedcount " +
                "FROM temr_timeline tm " +
                "LEFT JOIN mst_visit mv ON tm.timeline_visit_id = mv.visit_id " +
                "LEFT JOIN mst_staff ms ON tm.timelineemrfinal_staff_id = ms.staff_id " +
                "LEFT JOIN mst_user mu ON ms.staff_user_id=mu.user_id " +
                "INNER JOIN staff_configuration sf ON sf.sc_staff_id=tm.timelineemrfinal_staff_id " +
                "LEFT JOIN mst_unit mun ON sf.sc_unit_id = mun.unit_id WHERE tm.isemrfinal=1 ";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and sf.sc_unit_id in (" + values + ") ";

        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }

        if (finalizecountemrsearchDTO.getStaffId1() != null && !String.valueOf(finalizecountemrsearchDTO.getStaffId1()).equals("0")) {
            MainQuery += "  and ms.staff_id=" + finalizecountemrsearchDTO.getStaffId1() + " ";

        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "unit_name,doctorname,finalisedcount";
//        MainQuery += "group by tm.timelineemrfinal_staff_id,mun.unit_name,mu.user_fullname  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        MainQuery += "group by tm.timelineemrfinal_staff_id,mun.unit_name,mu.user_fullname ";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        return ResponseEntity.ok(jsonArray.toString());
    }

    @RequestMapping("getFinalizedEmrSummaryCountReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchFinalisedSummarycountPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT ifnull(mun.unit_name,'')as unit_name, IFNULL(mu.user_fullname,'')as doctorname , COUNT(tm.timelineemrfinal_staff_id) AS finalisedcount " +
                "FROM temr_timeline tm " +
                "LEFT JOIN mst_visit mv ON tm.timeline_visit_id = mv.visit_id " +
                "LEFT JOIN mst_staff ms ON tm.timelineemrfinal_staff_id = ms.staff_id " +
                "LEFT JOIN mst_user mu ON ms.staff_user_id=mu.user_id " +
                "INNER JOIN staff_configuration sf ON sf.sc_staff_id=tm.timelineemrfinal_staff_id " +
                "LEFT JOIN mst_unit mun ON sf.sc_unit_id = mun.unit_id WHERE tm.isemrfinal=1 ";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and sf.sc_unit_id in (" + values + ") ";

        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }

        if (finalizecountemrsearchDTO.getStaffId1() != null && !String.valueOf(finalizecountemrsearchDTO.getStaffId1()).equals("0")) {
            MainQuery += "  and ms.staff_id=" + finalizecountemrsearchDTO.getStaffId1() + " ";

        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        Integer myCount = 0;
        for (int i = 0; i < list.size(); i++) {
            myCount = myCount + Integer.parseInt("" + list.get(i)[2]);
        }
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "unit_name,doctorname,finalisedcount";
//        MainQuery += "group by tm.timelineemrfinal_staff_id,mun.unit_name,mu.user_fullname limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        MainQuery += "group by tm.timelineemrfinal_staff_id,mun.unit_name,mu.user_fullname ";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(finalizecountemrsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(finalizecountemrsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        jsonObject.put("finalizedCount", myCount);
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (finalizecountemrsearchDTO.getPrint()) {
            columnName = "unit_name,doctorname,finalisedcount";
            return createReport.generateExcel(columnName, "FinalisedCountSummaryReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("FinalisedCountSummaryReport", jsonArray.toString());
        }
    }



    @RequestMapping("searchFinalizedEmrCountReport/{fromdate}/{todate}")
    public ResponseEntity searchOpdCollectionDetail(@RequestHeader("X-tenantId") String tenantName,
                                                    @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO,
                                                    @PathVariable String fromdate,
                                                    @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.print(finalizecountemrsearchDTO.unitList.toString());
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT tt.timeline_patient_id, mun.unit_name, ifnull(mv.visit_date,'')as visit_date, " +
                "ifnull(mp.patient_mr_no, '') as patient_mr_no, "
                + " ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as Patient_Name,"
                + " ifnull(mu.user_age,'')as user_age, ifnull(mg.gender_name, '') as gender_name, "
                + " CONCAT(mt1.title_name,' ', mu1.user_firstname,' ', mu1.user_lastname) AS Finalized_By, ifnull(md.district_name, '') AS  District_name"
                + " from temr_timeline tt " + " INNER JOIN mst_patient mp on tt.timeline_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                " LEFT JOIN mst_visit mv ON tt.timeline_visit_id = mv.visit_id " +
                " LEFT JOIN mbill_tariff mta ON mv.visit_tariff_id = mta.tariff_id " +
                " INNER JOIN mst_staff ms ON tt.timelineemrfinal_staff_id = ms.staff_id " +
                " INNER JOIN staff_configuration sf ON sf.sc_staff_id=tt.timelineemrfinal_staff_id " +
                " LEFT JOIN mst_unit mun ON sf.sc_unit_id = mun.unit_id " +
                " LEFT JOIN mst_user mu1 ON ms.staff_user_id = mu1.user_id " +
                " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                " LEFT JOIN mst_unit mul ON mul.unit_id = sf.sc_unit_id " +
                " LEFT JOIN mst_city mct ON mct.city_id = mul.unit_city_id " +
                " LEFT JOIN mst_district md ON md.district_id = mct.city_district_id " +
                " LEFT JOIN trn_appointment tra ON tra.appointment_timeline_id=tt.timeline_id " +
                " WHERE tt.isemrfinal = 1 AND tt.is_active=1 AND tt.is_deleted=0 ";
//
        if (!finalizecountemrsearchDTO.unitList[0].equals("null")) {
            String values = String.valueOf(finalizecountemrsearchDTO.unitList[0]);
            for (int i = 1; i < finalizecountemrsearchDTO.unitList.length; i++) {
                values += "," + finalizecountemrsearchDTO.unitList[i];
            }
//            MainQuery += " and sf.sc_unit_id in (" + values + ") ";
            MainQuery += " and mv.visit_unit_id in (" + values + ") ";

        }
//
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }


        if (finalizecountemrsearchDTO.getMrNo() != null && !finalizecountemrsearchDTO.getMrNo().equals("")) {
            MainQuery += " and mp.patient_mr_no like  '%" + finalizecountemrsearchDTO.getMrNo() + "%' ";

        }
        if (finalizecountemrsearchDTO.getStaffId1() != null && !String.valueOf(finalizecountemrsearchDTO.getStaffId1()).equals("0")) {
            MainQuery += "  and ms.staff_id=" + finalizecountemrsearchDTO.getStaffId1() + " ";

        }
        if (finalizecountemrsearchDTO.getGenderId() != null && !finalizecountemrsearchDTO.getGenderId().equals("0")) {
            MainQuery += " and mu.user_gender_id  =  " + finalizecountemrsearchDTO.getGenderId() + " ";

        }
        if (finalizecountemrsearchDTO.getDistrictId() != null && !finalizecountemrsearchDTO.getDistrictId().equals("0")) {
            MainQuery += " and md.district_id  =  " + finalizecountemrsearchDTO.getDistrictId() + " ";
        }


        if (finalizecountemrsearchDTO.getPatientName() != null && !finalizecountemrsearchDTO.getPatientName().equals("")) {
            MainQuery += " and concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname) LIKE '%" + finalizecountemrsearchDTO.getPatientName() + "%'";

        }
        System.out.println("Finalized EMR Count Report:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        for (Object[] obj : list) {
            if (obj[6].toString() != null && !"".equals(obj[6].toString())) {
                if (genderWiseCount.get(obj[6].toString()) == null) {
                    genderWiseCount.put(obj[6].toString(), 1);
                } else {
                    int gender_count = genderWiseCount.get(obj[6].toString());
                    genderWiseCount.put(obj[6].toString(), ++gender_count);
                }
            }
        }
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "timeline_patient_id,unit_name,visit_date,patient_mr_no,Patient_Name,user_age,gender_name,Finalized_By,District_name";
//        MainQuery += " GROUP BY mv.visit_staff_id " ;
        MainQuery += "  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonArray.getJSONObject(i).put("genderCount", new JSONObject(genderWiseCount));
            }
        } catch (Exception e) {
        }
        return ResponseEntity.ok(jsonArray.toString());
    }

    @RequestMapping("searchNewFinalizedEmrCountReport/{fromdate}/{todate}")
//    @Timed
//    @Transactional(timeout = 18000)
    public ResponseEntity searchNewOpdCollectionDetail(@RequestHeader("X-tenantId") String tenantName,
                                                       @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO,
                                                       @PathVariable String fromdate,
                                                       @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        //Nayan
        MainQuery =    " SELECT DATE(tra.`created_date`) AS 'AppointmentDate',DATE(tra.`appointment_date`) AS 'VisitDate', IFNULL(tra.`appointment_slot`, '') AS 'AppointmentSlot', IFNULL(mp.`patient_mr_no`, '') AS 'patient_mr_no', IFNULL(CONCAT(mt.`title_name`, ' ', mu.`user_firstname`, ' ', mu.`user_lastname`), '') AS 'Patient_Name', " +
                " mu.`user_mobile`, IFNULL(mun2.`unit_name`, ' ') AS 'SpokeUnit', IFNULL(mut.`unittype_name`, ' ') AS 'SpokeUnitType', " +
                " tra.`created_by` AS 'Spokedoctor', IFNULL(mdp.`department_name`, ' '), " +
                " IF(tra.`appointment_is_confirm` = 1, 'Yes', 'NO') AS 'AppointmentConfirm', " +
                " IF(tra.`appointment_is_closed` = 1, 'Yes', 'No') AS 'AppointmentClosed', " +
                " IF(tra.`appointment_is_cancelled` = 1, 'Yes', 'No') AS 'AppointmentCancelled', IFNULL(md2.`district_name`, '') AS 'District_name', " +
                " tra.`appointmentreason`, " +
//                "mr.role_name AS 'RoleName'," +
                " tra.`created_by` AS 'AppointmentBookedBy', " +
                " mbs.`service_abbreviations` AS 'ConsultationService', CONCAT(mt1.`title_name` , mu1.`user_firstname`, ' ', mu1.`user_lastname`) AS 'Finalized_by', IFNULL(mc.`cluster_name`, ' ') AS 'hubUnitName', IFNULL(md.`district_name`, '') AS 'District', " +
                " mun.`unit_name` AS 'UserUnitName', IFNULL(mut2.`unittype_name`, '') AS 'ConsultationUnitType', IFNULL(msp.`speciality_name`, ' '), IFNULL(tt.`timeline_doctor_advice`, '') AS 'DoctorAdvise'," +
                " IF(tt.`isemrfinal` = 1, 'Yes', 'No') AS 'emr_status' " +
                " FROM `temr_timeline` tt " +
                " INNER JOIN `mst_patient` mp ON tt.`timeline_patient_id` = mp.`patient_id` " +
                " INNER JOIN `mst_user` mu ON mp.`patient_user_id` = mu.`user_id` " +
                " LEFT JOIN `mst_title` mt ON mu.`user_title_id` = mt.`title_id` " +
                " LEFT JOIN `mst_gender` mg ON mu.`user_gender_id` = mg.`gender_id` " +
                " LEFT JOIN `mst_visit` mv ON tt.`timeline_visit_id` = mv.`visit_id` " +
                " LEFT JOIN `mbill_tariff` mta ON mv.`visit_tariff_id` = mta.`tariff_id` " +
                " INNER JOIN `mst_staff` ms ON tt.`timelineemrfinal_staff_id` = ms.`staff_id` " +
                " INNER JOIN `staff_configuration` sf ON sf.`sc_staff_id` = tt.`timelineemrfinal_staff_id` " +
                " LEFT JOIN `mst_unit` mun ON sf.`sc_unit_id` = mun.`unit_id` " +
                " LEFT JOIN `mst_unit_type` mut2 ON mut2.`unittype_id` = mun.unit_unittype_id " +
                " LEFT JOIN `mst_user` mu1 ON ms.`staff_user_id` = mu1.`user_id` " +
                " LEFT JOIN `mst_title` mt1 ON mu1.`user_title_id` = mt1.`title_id` " +
                " LEFT JOIN `mst_unit` mul ON mul.`unit_id` = sf.`sc_unit_id` " +
                " LEFT JOIN `mst_city` mct ON mct.`city_id` = mul.`unit_city_id` " +
                " LEFT JOIN `mst_district` md ON md.`district_id` = mct.`city_district_id` " +
                " INNER JOIN `trn_appointment` tra ON tra.`appointment_timeline_id` = tt.`timeline_id` " +
                " LEFT JOIN `mst_unit` mun2 ON mun2.`unit_id` = tra.`appointment_unit_id` " +
                " LEFT JOIN `mst_unit_type` mut ON mut.`unittype_id` = mun2.unit_unittype_id " +
                " LEFT JOIN `mst_staff` ms1 ON ms1.`staff_id` = tra.`appointment_staff_id` " +
                " LEFT JOIN `mst_speciality` msp ON msp.`speciality_id` = ms.`staff_speciality_id` " +
                " LEFT JOIN `mst_user` mu3 ON mu3.`user_id` = ms1.`staff_user_id` " +
                " INNER JOIN `mst_department` mdp ON mdp.`department_id` = mv.`visit_department_id` " +
                " LEFT JOIN `staff_configuration` sf1 ON sf1.`sc_department_id` = mdp.`department_id` " +
                " LEFT JOIN `mst_staff_staff_cluster_id_list` staff_cluster ON staff_cluster.`mst_staff_staff_id` = ms.`staff_id` " +
                " LEFT JOIN `mst_cluster` mc ON mc.`cluster_id` = staff_cluster.`staff_cluster_id_list_cluster_id` " +
                " LEFT JOIN `mbill_service` mbs ON mbs.`service_id` = tra.`appointment_service_id` " +
                 " LEFT JOIN `mst_city` mct2 ON mct2.`city_id` = mun2.`unit_city_id` " +
                " LEFT JOIN `mst_district` md2 ON md2.`district_id` = mct2.`city_district_id` " +
//                " LEFT JOIN mst_user  mu4 ON mu4.user_fullname = tra.created_by " +
//                " LEFT JOIN mst_staff ms4 ON ms4.staff_user_id = mu4.user_id " +
//                " LEFT JOIN mst_role mr ON mr.role_id = ms4.staff_role " +
                " WHERE tt.`isemrfinal` = 1 AND tt.`is_active` = 1 AND tt.`is_deleted` = 0";
//
        if (!finalizecountemrsearchDTO.unitList[0].equals("null")) {
            String values = String.valueOf(finalizecountemrsearchDTO.unitList[0]);
            for (int i = 1; i < finalizecountemrsearchDTO.unitList.length; i++) {
                values += "," + finalizecountemrsearchDTO.unitList[i];
            }
//            MainQuery += " and sf.sc_unit_id in (" + values + ") ";
            MainQuery += " and mv.visit_unit_id in (" + values + ") ";

        }

        if(finalizecountemrsearchDTO.getSpecialityId() != null) {
            MainQuery += " and msp.speciality_id  =  " + finalizecountemrsearchDTO.getSpecialityId() + " ";
        }

        if (!finalizecountemrsearchDTO.unitConsultationList[0].equals("null")) {
            String values = String.valueOf(finalizecountemrsearchDTO.unitConsultationList[0]);
            for (int i = 1; i < finalizecountemrsearchDTO.unitConsultationList.length; i++) {
                values += "," + finalizecountemrsearchDTO.unitConsultationList[i];
            }
            MainQuery += " and mun.unit_id in (" + values + ") ";
        }

//
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }


        if (finalizecountemrsearchDTO.getStaffId1() != null && !String.valueOf(finalizecountemrsearchDTO.getStaffId1()).equals("0")) {
            MainQuery += "  and tra.created_by like '%" + finalizecountemrsearchDTO.getStaffId1() +"%'";

        }
        if (finalizecountemrsearchDTO.getStaffId2() != null && !String.valueOf(finalizecountemrsearchDTO.getStaffId2()).equals("0")) {
            MainQuery += "  and tt.timelineemrfinal_staff_id=" + finalizecountemrsearchDTO.getStaffId2() + " ";

        }

        if (finalizecountemrsearchDTO.getAppointmentDistrictId() != null && !finalizecountemrsearchDTO.getAppointmentDistrictId().equals("0")) {
            MainQuery += " and md2.district_id  =  " + finalizecountemrsearchDTO.getAppointmentDistrictId() + " ";
        }

        if (finalizecountemrsearchDTO.getConsultationDistrictId() != null && !finalizecountemrsearchDTO.getConsultationDistrictId().equals("0")) {
            MainQuery += " and md.district_id  =  " + finalizecountemrsearchDTO.getConsultationDistrictId() + " ";
        }

        if (finalizecountemrsearchDTO.getConsulationUnitType() != null && !String.valueOf(finalizecountemrsearchDTO.getConsulationUnitType()).equals("0")) {
            MainQuery += " and mut2.unittype_id  =  " + finalizecountemrsearchDTO.getConsulationUnitType() + " ";
        }

        if (finalizecountemrsearchDTO.getSpokeUnitType() != null && !String.valueOf(finalizecountemrsearchDTO.getSpokeUnitType()).equals("0")) {
             MainQuery += " and mut.unittype_id =  " + finalizecountemrsearchDTO.getSpokeUnitType() + " ";
        }
        if (finalizecountemrsearchDTO.getAppointmentConfirm() != null && !finalizecountemrsearchDTO.getAppointmentConfirm().equals("0")) {
            MainQuery += " and tra.appointment_is_confirm = " + finalizecountemrsearchDTO.getAppointmentConfirm() + " ";
        }

        if (finalizecountemrsearchDTO.getAppointmentClosed() != null && !finalizecountemrsearchDTO.getAppointmentClosed().equals("0")) {
            MainQuery += " and tra.appointment_is_closed = " + finalizecountemrsearchDTO.getAppointmentClosed() + " ";
        }

        if (finalizecountemrsearchDTO.getAppointmentCancelled() != null && !finalizecountemrsearchDTO.getAppointmentCancelled().equals("0")) {
            MainQuery += " and tra.appointment_is_cancelled = " + finalizecountemrsearchDTO.getAppointmentCancelled() + " ";
        }

//
        MainQuery += " GROUP BY mv.visit_id ";
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        for (Object[] obj : list) {
            if (obj[6].toString() != null && !"".equals(obj[6].toString())) {
                if (genderWiseCount.get(obj[6].toString()) == null) {
                    genderWiseCount.put(obj[6].toString(), 1);
                } else {
                    int gender_count = genderWiseCount.get(obj[6].toString());
                    genderWiseCount.put(obj[6].toString(), ++gender_count);
                }
            }
        }
        System.out.println("Finallize EMR Count with Appointment Details : "+ MainQuery);
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "AppointmentDate,VisitDate,AppointmentSlot,patient_mr_no,Patient_Name,user_mobile,SpokeUnit,SpokeUnitType,Spokedoctor,department_name,AppointmentConfirm,AppointmentClosed,AppointmentCancelled,District_name,appointmentreason,AppointmentBookedBy,ConsultationService,Finalized_by,hubUnitName,District,unit_name,ConsultationUnitType,speciality_name,DoctorAdvise,emr_status";
//        MainQuery += " GROUP BY mv.visit_staff_id " ;
        MainQuery += "  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonArray.getJSONObject(i).put("genderCount", new JSONObject(genderWiseCount));
            }
        } catch (Exception e) {
        }
        return ResponseEntity.ok(jsonArray.toString());
    }


    @RequestMapping("searchClinicalComplainceReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchClinicalComplainceDetail(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT tetl.timeline_id, mp.patient_mr_no " +
                ", CONCAT(mu.user_firstname, ' ', mu.user_lastname) AS patient_name , " +
                " mu.user_age, mg.gender_name, " +
                " md.district_name, mu2.unit_name, tetl.timeline_staff_id, " +
                " CONCAT(mu1.user_firstname, ' ', mu1.user_lastname) AS timeline_doctor_name, " +
                " CONCAT(mu3.user_firstname, ' ', mu3.user_lastname) AS Finalized_By," +
                " tetl.timelineemrfinal_staff_id, tetl.timeline_date AS Timeline_Date," +
                " if(tetl.timeline_is_allergies=1, 'yes', 'no') AS Allergies, " +
                " if(tetl.timeline_is_chief_complaint = 1, 'yes', 'no') AS Chief_Complaint, " +
                " if(tetl.timeline_is_diagnosis = 1, 'yes', 'no') AS Diagnosis," +
                " if(tetl.timeline_is_doctors_advice = 1, 'yes', 'no') AS Doctors_Advice, " +
                " if(tetl.timeline_is_history = 1, 'yes', 'no') AS History, " +
                " if(tetl.timeline_is_investigation = 1, 'yes', 'no') AS Investigation, " +
                " if(tetl.timeline_is_prescription = 1, 'yes', 'no') AS Prescription, " +
                " if(tetl.timeline_is_symptoms = 1, 'yes', 'no') AS Symptoms, " +
                " if(tetl.timeline_is_vitals = 1, 'yes', 'no') AS Vitals " +
                " FROM temr_timeline tetl " +
                " INNER JOIN mst_patient mp ON mp.patient_id = tetl.timeline_patient_id " +
                " LEFT JOIN mst_user mu ON mu.user_id = mp.patient_user_id " +
                " INNER JOIN mst_gender mg ON mg.gender_id=mu.user_gender_id " +
                " LEFT JOIN mst_city mc ON mc.city_id=mu.user_city_id " +
                " LEFT JOIN mst_district md ON md.district_id=mc.city_district_id " +
                " LEFT JOIN mst_staff ms ON ms.staff_id = tetl.timeline_staff_id " +
                " LEFT JOIN mst_user mu1 ON mu1.user_id = ms.staff_user_id " +
                " INNER JOIN mst_unit mu2 ON mu2.unit_id=mu.user_unit_id " +
                " LEFT JOIN mst_staff ms1 ON ms1.staff_id=tetl.timelineemrfinal_staff_id " +
                " LEFT JOIN mst_user mu3 ON mu3.user_id=ms1.staff_user_id ";

        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
//            MainQuery += " and sf.sc_unit_id in (" + values + ") ";
            MainQuery += " where mu.user_unit_id in (" + values + ") ";

        }
//
//
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " and (date(tetl.timeline_date)=curdate()) ";
        } else {
            MainQuery += " and (Date(tetl.timeline_date) between '" + fromdate + "' and '" + todate + "') ";
        }

        if (finalizecountemrsearchDTO.getMrNo() != null && !finalizecountemrsearchDTO.getMrNo().equals("")) {
            MainQuery += " and mp.patient_mr_no like  '%" + finalizecountemrsearchDTO.getMrNo() + "%' ";

        }
        if (finalizecountemrsearchDTO.getStaffId1() != null && !String.valueOf(finalizecountemrsearchDTO.getStaffId1()).equals("0")) {
            MainQuery += "  and ms.staff_id=" + finalizecountemrsearchDTO.getStaffId1() + " ";

        }
//        if (finalizecountemrsearchDTO.getDistrictId() != null && !finalizecountemrsearchDTO.getDistrictId().equals("0")) {
//            MainQuery += " and md.district_id  =  " + finalizecountemrsearchDTO.getDistrictId() + " ";
//        }
        if (finalizecountemrsearchDTO.getPatientName() != null && !finalizecountemrsearchDTO.getPatientName().equals("")) {
            MainQuery += " and concat(mu.user_firstname,' ', mu.user_lastname) LIKE '%" + finalizecountemrsearchDTO.getPatientName() + "%'";

        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "timeline_id,patient_mr_no,patient_name,user_age,gender_name,district_name,unit_name,timeline_staff_id,timeline_doctor_name,Finalized_By,timelineemrfinal_staff_id,Timeline_Date_time,Allergies,Chief_Complaint,Diagnosis,Doctors_Advice,History,Investigation,Prescription,Symptoms,Vitals";
        MainQuery += "  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        return ResponseEntity.ok(jsonArray.toString());
    }


    @RequestMapping("searchcaredeskinboudReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchcaredeskinboudDetail(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT mu.unit_id,tic.inboundcc_id,mu.unit_name,mp.patient_mr_no AS MrNo,mss.user_age AS Age,mg.gender_name AS Gender,mss.user_dob AS Dob,mss.user_mobile AS Mobile_no, " +
                " CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(mss.user_firstname,''),' ', IFNULL(mss.user_lastname,'')) AS Patient_name, " +
                " CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(msu.user_firstname,''),' ', IFNULL(msu.user_lastname,'')) AS User_name," +
                " tic.created_date AS InDate, IFNULL(tic.inbound_responce,'') AS Responce, IFNULL(tic.inbound_query,'') AS InQuery, " +
                " IFNULL(tic.inbounddescription,'') AS Description, IFNULL(co.call_outcome_name,'') AS Calloutcome, IFNULL(cr.care_activity_name,'') AS Careactivity," +
                " IFNULL(cs.channel_source_name,'') AS Chanelsource, IFNULL(ity.issue_type_name,'') AS Issuetype ," +
                " if(tic.inbound_clinical_form_id != '', 'Yes', 'No') AS ClinicalForm " +
                " FROM trn_inbound_cc tic" +
                " LEFT JOIN memr_calloutcome co ON co.call_outcome_id = tic.inboundcall_outcome_id" +
                " LEFT JOIN memr_careactivity cr ON cr.care_activity_id = tic.inboundcare_activity_id" +
                " LEFT JOIN memr_channelsource cs ON cs.channel_source_id = tic.inboundchannel_source_id" +
                " LEFT JOIN memr_issuetype ity ON ity.issue_type_id = tic.inboundissue_type_id" +
                " LEFT JOIN mst_unit mu ON mu.unit_id = tic.inboundcc_unit_id" +
                " LEFT JOIN mst_staff ms ON ms.staff_id = tic.inboundcc_staff_id" +
                " LEFT JOIN mst_user msu ON msu.user_id = ms.staff_user_id" +
                " LEFT JOIN mst_title mt ON msu.user_title_id=msu.user_id" +
                " LEFT JOIN mst_patient mp ON mp.patient_id = tic.inboundcc_patient_id" +
                " LEFT JOIN mst_user mss ON mss.user_id=mp.patient_user_id" +
                " LEFT JOIN mst_gender mg ON mg.gender_id=mss.user_gender_id ";

        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " where (date(tic.created_date)=curdate()) ";
        } else {
            MainQuery += " where (Date(tic.created_date) between '" + fromdate + "' and '" + todate + "') ";
        }

        if (finalizecountemrsearchDTO.getMrNo() != null && !finalizecountemrsearchDTO.getMrNo().equals("")) {
            MainQuery += " and mp.patient_mr_no like  '%" + finalizecountemrsearchDTO.getMrNo() + "%' ";

        }

        if (finalizecountemrsearchDTO.getPatientName() != null && !finalizecountemrsearchDTO.getPatientName().equals("")) {
            MainQuery += " and concat(mss.user_firstname,' ', mss.user_lastname) LIKE '%" + finalizecountemrsearchDTO.getPatientName() + "%'";

        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "unit_id,inboundcc_id,unit_name,MrNo,Age,Gender,Dob,Mobile_no,Patient_name,User_name,InDate,Responce,InQuery,Description,Calloutcome,Careactivity,Chanelsource,Issuetype,ClinicalForm";
        MainQuery += "  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        return ResponseEntity.ok(jsonArray.toString());
    }


    @RequestMapping("searchcaredeskinboudReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchcaredeskinboudDetailPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate)throws JsonProcessingException  {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT mu.unit_id,tic.inboundcc_id,mu.unit_name,mp.patient_mr_no AS MrNo,mss.user_age AS Age,mg.gender_name AS Gender,mss.user_dob AS Dob,mss.user_mobile AS Mobile_no, " +
                " CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(mss.user_firstname,''),' ', IFNULL(mss.user_lastname,'')) AS Patient_name, " +
                " CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(msu.user_firstname,''),' ', IFNULL(msu.user_lastname,'')) AS User_name," +
                " tic.created_date AS InDate, IFNULL(tic.inbound_responce,'') AS Responce, IFNULL(tic.inbound_query,'') AS InQuery, " +
                " IFNULL(tic.inbounddescription,'') AS Description, IFNULL(co.call_outcome_name,'') AS Calloutcome, IFNULL(cr.care_activity_name,'') AS Careactivity," +
                " IFNULL(cs.channel_source_name,'') AS Chanelsource, IFNULL(ity.issue_type_name,'') AS Issuetype ," +
                " if(tic.inbound_clinical_form_id != '', 'Yes', 'No') AS ClinicalForm " +
                " FROM trn_inbound_cc tic" +
                " LEFT JOIN memr_calloutcome co ON co.call_outcome_id = tic.inboundcall_outcome_id" +
                " LEFT JOIN memr_careactivity cr ON cr.care_activity_id = tic.inboundcare_activity_id" +
                " LEFT JOIN memr_channelsource cs ON cs.channel_source_id = tic.inboundchannel_source_id" +
                " LEFT JOIN memr_issuetype ity ON ity.issue_type_id = tic.inboundissue_type_id" +
                " LEFT JOIN mst_unit mu ON mu.unit_id = tic.inboundcc_unit_id" +
                " LEFT JOIN mst_staff ms ON ms.staff_id = tic.inboundcc_staff_id" +
                " LEFT JOIN mst_user msu ON msu.user_id = ms.staff_user_id" +
                " LEFT JOIN mst_title mt ON msu.user_title_id=msu.user_id" +
                " LEFT JOIN mst_patient mp ON mp.patient_id = tic.inboundcc_patient_id" +
                " LEFT JOIN mst_user mss ON mss.user_id=mp.patient_user_id" +
                " LEFT JOIN mst_gender mg ON mg.gender_id=mss.user_gender_id ";


        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " where (date(tic.created_date)=curdate()) ";
        } else {
            MainQuery += " where (Date(tic.created_date) between '" + fromdate + "' and '" + todate + "') ";
        }

        if (finalizecountemrsearchDTO.getMrNo() != null && !finalizecountemrsearchDTO.getMrNo().equals("")) {
            MainQuery += " and mp.patient_mr_no like  '%" + finalizecountemrsearchDTO.getMrNo() + "%' ";

        }

        if (finalizecountemrsearchDTO.getPatientName() != null && !finalizecountemrsearchDTO.getPatientName().equals("")) {
            MainQuery += " and concat(mss.user_firstname,' ', mss.user_lastname) LIKE '%" + finalizecountemrsearchDTO.getPatientName() + "%'";

        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "unit_id,inboundcc_id,unit_name,MrNo,Age,Gender,Dob,Mobile_no,Patient_name,User_name,InDate,Responce,InQuery,Description,Calloutcome,Careactivity,Chanelsource,Issuetype,ClinicalForm";
        MainQuery += "  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        // return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(finalizecountemrsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(finalizecountemrsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (finalizecountemrsearchDTO.getPrint()) {
            columnName = "unit_name,MrNo,Age,Gender,Dob,Mobile_no,Patient_name,User_name,InDate,Responce,InQuery,Description,Calloutcome,Careactivity,Chanelsource,Issuetype,ClinicalForm";
            return createReport.generateExcel(columnName, "CareDeskIncommingReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("CareDeskIncommingReport", jsonArray.toString());
        }
    }



    @RequestMapping("searchClinicalComplainceReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchClinicalComplainceDetailPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO,
                                                      @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT tetl.timeline_id, mp.patient_mr_no " +
                ", CONCAT(mu.user_firstname, ' ', mu.user_lastname) AS patient_name , " +
                " mu.user_age, mg.gender_name, " +
                " md.district_name, mu2.unit_name, tetl.timeline_staff_id, " +
                " CONCAT(mu1.user_firstname, ' ', mu1.user_lastname) AS timeline_doctor_name, " +
                " CONCAT(mu3.user_firstname, ' ', mu3.user_lastname) AS Finalized_By," +
                " tetl.timelineemrfinal_staff_id, tetl.timeline_date AS Timeline_Date," +
                " if(tetl.timeline_is_allergies=1, 'yes', 'no') AS Allergies, " +
                " if(tetl.timeline_is_chief_complaint = 1, 'yes', 'no') AS Chief_Complaint, " +
                " if(tetl.timeline_is_diagnosis = 1, 'yes', 'no') AS Diagnosis," +
                " if(tetl.timeline_is_doctors_advice = 1, 'yes', 'no') AS Doctors_Advice, " +
                " if(tetl.timeline_is_history = 1, 'yes', 'no') AS History, " +
                " if(tetl.timeline_is_investigation = 1, 'yes', 'no') AS Investigation, " +
                " if(tetl.timeline_is_prescription = 1, 'yes', 'no') AS Prescription, " +
                " if(tetl.timeline_is_symptoms = 1, 'yes', 'no') AS Symptoms, " +
                " if(tetl.timeline_is_vitals = 1, 'yes', 'no') AS Vitals " +
                " FROM temr_timeline tetl " +
                " INNER JOIN mst_patient mp ON mp.patient_id = tetl.timeline_patient_id " +
                " LEFT JOIN mst_user mu ON mu.user_id = mp.patient_user_id " +
                " INNER JOIN mst_gender mg ON mg.gender_id=mu.user_gender_id " +
                " LEFT JOIN mst_city mc ON mc.city_id=mu.user_city_id " +
                " LEFT JOIN mst_district md ON md.district_id=mc.city_district_id " +
                " LEFT JOIN mst_staff ms ON ms.staff_id = tetl.timeline_staff_id " +
                " LEFT JOIN mst_user mu1 ON mu1.user_id = ms.staff_user_id " +
                " INNER JOIN mst_unit mu2 ON mu2.unit_id=mu.user_unit_id " +
                " LEFT JOIN mst_staff ms1 ON ms1.staff_id=tetl.timelineemrfinal_staff_id " +
                " LEFT JOIN mst_user mu3 ON mu3.user_id=ms1.staff_user_id ";

        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }

            MainQuery += " where mu.user_unit_id in (" + values + ") ";

        }

        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " and (date(tetl.timeline_date)=curdate()) ";
        } else {
            MainQuery += " and (Date(tetl.timeline_date) between '" + fromdate + "' and '" + todate + "') ";
        }

        if (finalizecountemrsearchDTO.getMrNo() != null && !finalizecountemrsearchDTO.getMrNo().equals("")) {
            MainQuery += " and mp.patient_mr_no like  '%" + finalizecountemrsearchDTO.getMrNo() + "%' ";

        }
        if (finalizecountemrsearchDTO.getStaffId1() != null && !String.valueOf(finalizecountemrsearchDTO.getStaffId1()).equals("0")) {
            MainQuery += "  and ms.staff_id=" + finalizecountemrsearchDTO.getStaffId1() + " ";

        }

        if (finalizecountemrsearchDTO.getPatientName() != null && !finalizecountemrsearchDTO.getPatientName().equals("")) {
            MainQuery += " and concat(mu.user_firstname,' ', mu.user_lastname) LIKE '%" + finalizecountemrsearchDTO.getPatientName() + "%'";

        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "timeline_id,patient_mr_no,patient_name,user_age,gender_name,district_name,unit_name,timeline_staff_id,timeline_doctor_name,Finalized_By,timelineemrfinal_staff_id,Timeline_Date_time,Allergies,Chief_Complaint,Diagnosis,Doctors_Advice,History,Investigation,Prescription,Symptoms,Vitals";
        MainQuery += "  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        //  return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(finalizecountemrsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(finalizecountemrsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (finalizecountemrsearchDTO.getPrint()) {
            columnName = "patient_mr_no,patient_name,user_age,gender_name,district_name,unit_name,Finalized_By,Timeline_Date_time,Allergies,Chief_Complaint,Diagnosis,Doctors_Advice,History,Investigation,Prescription,Symptoms,Vitals";
            return createReport.generateExcel(columnName, "ClinicalComplaintsReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("ClinicalComplaintsReport", jsonArray.toString());
        }
    }


    @RequestMapping("searchcaredeskoutgoingReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchcaredeskoutgoingDetail(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT toc.outboundcc_id,mp.patient_mr_no,mss.user_age AS Age,mss.user_dob AS Dob,msu.user_mobile AS Mobile, " +
                " CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(mss.user_firstname,''),' ', IFNULL(mss.user_lastname,'')) AS Patient_name," +
                " CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(msu.user_firstname,''),' ', IFNULL(msu.user_lastname,'')) AS User_name," +
                " toc.outbound_query AS outQuery,toc.outbound_responce AS Responce,toc.outbounddescription AS Description,mst.service_type_name AS Service_type," +
                " mct.call_category_name AS Category," +
                " msb.ccs_name AS subCategory,rsb.resolution_category_name AS Resolution_caregory,mg.gender_name AS Gender,toc.created_date AS OutboundDate," +
                " if(toc.outbound_clinical_form_id != ' ', 'Yes', 'No') AS ClinicalForm " +
                " FROM trn_outbound_cc toc" +
                " LEFT JOIN memr_servicetype mst ON mst.service_type_id = toc.outboundservice_type_id" +
                " LEFT JOIN memr_callcategory mct ON mct.call_category_id = toc.outboundcall_category_id" +
                " LEFT JOIN memr_call_sub_category msb ON msb.csc_id = toc.outboundcallsubcategory" +
                " LEFT JOIN memr_resolutioncategory rsb ON rsb.resolution_category_id = toc.outboundresolution_category_id" +
                " LEFT JOIN mst_unit mu ON mu.unit_id = toc.outboundcc_unit_id" +
                " LEFT JOIN mst_staff ms ON ms.staff_id = toc.outboundcc_staff_id" +
                " LEFT JOIN mst_user msu ON msu.user_id = ms.staff_user_id" +
                " LEFT JOIN mst_title mt ON msu.user_title_id=msu.user_id" +
                " LEFT JOIN mst_patient mp ON mp.patient_id = toc.outboundcc_patient_id" +
                " LEFT JOIN mst_user mss ON mss.user_id=mp.patient_user_id" +
                " LEFT JOIN mst_gender mg ON mg.gender_id=mss.user_gender_id ";



        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " where (date(toc.created_date)=curdate()) ";
        } else {
            MainQuery += " where (Date(toc.created_date) between '" + fromdate + "' and '" + todate + "') ";
        }

        if (finalizecountemrsearchDTO.getMrNo() != null && !finalizecountemrsearchDTO.getMrNo().equals("")) {
            MainQuery += " and mp.patient_mr_no like  '%" + finalizecountemrsearchDTO.getMrNo() + "%' ";

        }

        if (finalizecountemrsearchDTO.getPatientName() != null && !finalizecountemrsearchDTO.getPatientName().equals("")) {
            MainQuery += " and concat(mss.user_firstname,' ', mss.user_lastname) LIKE '%" + finalizecountemrsearchDTO.getPatientName() + "%'";

        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "outboundcc_id,patient_mr_no,Age,Dob,Mobile,Patient_name,User_name,outQuery,Responce,Description,Service_type,Category,subCategory,Resolution_caregory,Gender,OutboundDate,ClinicalForm";
        MainQuery += "  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        return ResponseEntity.ok(jsonArray.toString());
    }


    @RequestMapping("searchcaredeskoutgoingReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchcaredeskoutgoingDetailPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate)throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT toc.outboundcc_id,mp.patient_mr_no,mss.user_age AS Age,mss.user_dob AS Dob,msu.user_mobile AS Mobile, " +
                " CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(mss.user_firstname,''),' ', IFNULL(mss.user_lastname,'')) AS Patient_name," +
                " CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(msu.user_firstname,''),' ', IFNULL(msu.user_lastname,'')) AS User_name," +
                " toc.outbound_query AS outQuery,toc.outbound_responce AS Responce,toc.outbounddescription AS Description,mst.service_type_name AS Service_type," +
                " mct.call_category_name AS Category," +
                " msb.ccs_name AS subCategory,rsb.resolution_category_name AS Resolution_caregory,mg.gender_name AS Gender,toc.created_date AS OutboundDate," +
                " if(toc.outbound_clinical_form_id != ' ', 'Yes', 'No') AS ClinicalForm " +
                " FROM trn_outbound_cc toc" +
                " LEFT JOIN memr_servicetype mst ON mst.service_type_id = toc.outboundservice_type_id" +
                " LEFT JOIN memr_callcategory mct ON mct.call_category_id = toc.outboundcall_category_id" +
                " LEFT JOIN memr_call_sub_category msb ON msb.csc_id = toc.outboundcallsubcategory" +
                " LEFT JOIN memr_resolutioncategory rsb ON rsb.resolution_category_id = toc.outboundresolution_category_id" +
                " LEFT JOIN mst_unit mu ON mu.unit_id = toc.outboundcc_unit_id" +
                " LEFT JOIN mst_staff ms ON ms.staff_id = toc.outboundcc_staff_id" +
                " LEFT JOIN mst_user msu ON msu.user_id = ms.staff_user_id" +
                " LEFT JOIN mst_title mt ON msu.user_title_id=msu.user_id" +
                " LEFT JOIN mst_patient mp ON mp.patient_id = toc.outboundcc_patient_id" +
                " LEFT JOIN mst_user mss ON mss.user_id=mp.patient_user_id" +
                " LEFT JOIN mst_gender mg ON mg.gender_id=mss.user_gender_id ";



        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " where (date(toc.created_date)=curdate()) ";
        } else {
            MainQuery += " where (Date(toc.created_date) between '" + fromdate + "' and '" + todate + "') ";
        }

        if (finalizecountemrsearchDTO.getMrNo() != null && !finalizecountemrsearchDTO.getMrNo().equals("")) {
            MainQuery += " and mp.patient_mr_no like  '%" + finalizecountemrsearchDTO.getMrNo() + "%' ";

        }

        if (finalizecountemrsearchDTO.getPatientName() != null && !finalizecountemrsearchDTO.getPatientName().equals("")) {
            MainQuery += " and concat(mss.user_firstname,' ', mss.user_lastname) LIKE '%" + finalizecountemrsearchDTO.getPatientName() + "%'";
        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "outboundcc_id,patient_mr_no,Age,Dob,Mobile,Patient_name,User_name,outQuery,Responce,Description,Service_type,Category,subCategory,Resolution_caregory,Gender,OutboundDate,ClinicalForm";
        MainQuery += "  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
//        return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(finalizecountemrsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(finalizecountemrsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (finalizecountemrsearchDTO.getPrint()) {
            columnName = "patient_mr_no,Age,Dob,Mobile,Patient_name,User_name,outQuery,Responce,Description,Service_type,Category,subCategory,Resolution_caregory,Gender,OutboundDate,ClinicalForm";
            return createReport.generateExcel(columnName, "CareDeskOutgoingReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("CareDeskOutgoingReport", jsonArray.toString());
        }
    }



    @RequestMapping("searchFinalizedEmrCountReportPrint/{fromdate}/{todate}")
    public String searchOpdCollectionDetailPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        //Main EMR Finalized Query
        MainQuery = " SELECT tt.timeline_patient_id, mun.unit_name, ifnull(mv.visit_date,'')as visit_date, " +
                "ifnull(mp.patient_mr_no, '') as patient_mr_no, "
                + " ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as Patient_Name,"
                + " ifnull(mu.user_age,'')as user_age, ifnull(mg.gender_name, '') as gender_name,  "
                + " CONCAT(mt1.title_name,' ', mu1.user_firstname,' ', mu1.user_lastname) AS Finalized_By, ifnull(md.district_name, '') AS  District_name"
                + " from temr_timeline tt " + " INNER JOIN mst_patient mp on tt.timeline_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                " LEFT JOIN mst_visit mv ON tt.timeline_visit_id = mv.visit_id " +
                " LEFT JOIN mbill_tariff mta ON mv.visit_tariff_id = mta.tariff_id " +
                " INNER JOIN mst_staff ms ON tt.timelineemrfinal_staff_id = ms.staff_id " +
                " INNER JOIN staff_configuration sf ON sf.sc_staff_id=tt.timelineemrfinal_staff_id " +
                " LEFT JOIN mst_unit mun ON sf.sc_unit_id = mun.unit_id " +
                " LEFT JOIN mst_user mu1 ON ms.staff_user_id = mu1.user_id " +
                " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                " LEFT JOIN mst_unit mul ON mul.unit_id = sf.sc_unit_id " +
                " LEFT JOIN mst_city mct ON mct.city_id = mul.unit_city_id " +
                " LEFT JOIN mst_district md ON md.district_id = mct.city_district_id " +
                " WHERE tt.isemrfinal = 1 AND tt.is_active=1 AND tt.is_deleted=0 ";

        if (!finalizecountemrsearchDTO.unitList[0].equals("null")) {
            String values = String.valueOf(finalizecountemrsearchDTO.unitList[0]);
            for (int i = 1; i < finalizecountemrsearchDTO.unitList.length; i++) {
                values += "," + finalizecountemrsearchDTO.unitList[i];
            }

            MainQuery += " and mv.visit_unit_id in (" + values + ") ";

        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }

        if (finalizecountemrsearchDTO.getMrNo() != null && !finalizecountemrsearchDTO.getMrNo().equals("")) {
            MainQuery += " and mp.patient_mr_no like  '%" + finalizecountemrsearchDTO.getMrNo() + "%' ";

        }
        if (finalizecountemrsearchDTO.getStaffId1() != null && !String.valueOf(finalizecountemrsearchDTO.getStaffId1()).equals("0")) {
            MainQuery += "  and ms.staff_id=" + finalizecountemrsearchDTO.getStaffId1() + " ";

        }
        if (finalizecountemrsearchDTO.getGenderId() != null && !finalizecountemrsearchDTO.getGenderId().equals("0")) {
            MainQuery += " and mu.user_gender_id  =  " + finalizecountemrsearchDTO.getGenderId() + " ";

        }
        if (finalizecountemrsearchDTO.getDistrictId() != null && !finalizecountemrsearchDTO.getDistrictId().equals("0")) {
            MainQuery += " and md.district_id  =  " + finalizecountemrsearchDTO.getDistrictId() + " ";
        }
        if (finalizecountemrsearchDTO.getPatientName() != null && !finalizecountemrsearchDTO.getPatientName().equals("")) {
            MainQuery += " and concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname) LIKE '%" + finalizecountemrsearchDTO.getPatientName() + "%'";

        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        for (Object[] obj : list) {
            if (obj[6].toString() != null && !"".equals(obj[6].toString())) {
                if (genderWiseCount.get(obj[6].toString()) == null) {
                    genderWiseCount.put(obj[6].toString(), 1);
                } else {
                    int gender_count = genderWiseCount.get(obj[6].toString());
                    genderWiseCount.put(obj[6].toString(), ++gender_count);
                }
            }
        }
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "timeline_patient_id,unit_name,visit_date,patient_mr_no,Patient_Name,user_age,gender_name,Finalized_By,District_name";
        System.out.print(MainQuery);
        //MainQuery += "  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
//		try {
////			for (int i = 0; i < jsonArray.length(); i++) {
////				jsonArray.getJSONObject(i).put("genderCount", new JSONObject(genderWiseCount));
////			}
////		} catch (Exception e) {
////		}
////		return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(finalizecountemrsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(finalizecountemrsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (finalizecountemrsearchDTO.getPrint()) {
            columnName = "timeline_patient_id,unit_name,visit_date,patient_mr_no,Patient_Name,user_age,gender_name,Finalized_By,District_name";
            return createReport.generateExcel(columnName, "FinalizedEmrCountReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("FinalizedEmrCountReport", jsonArray.toString());
        }

    }

    @RequestMapping("searchNewFinalizedEmrCountReportPrint/{fromdate}/{todate}")
//    @Timed
//    @Transactional(timeout = 180)
    public String searchNewOpdCollectionDetailPrint(@RequestHeader("X-tenantId") String tenantName,
                                                    @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO,
                                                    @PathVariable String fromdate,
                                                    @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";

         //Nayan
        MainQuery =  " SELECT DATE(tra.`created_date`) AS 'AppointmentDate',DATE(tra.`appointment_date`) AS 'VisitDate', IFNULL(tra.`appointment_slot`, '') AS 'AppointmentSlot', IFNULL(mp.`patient_mr_no`, '') AS 'patient_mr_no', IFNULL(CONCAT(mt.`title_name`, ' ', mu.`user_firstname`, ' ', mu.`user_lastname`), '') AS 'Patient_Name', " +
                " mu.`user_mobile`, IFNULL(mun2.`unit_name`, ' ') AS 'SpokeUnit', IFNULL(mut.`unittype_name`, ' ') AS 'SpokeUnitType', " +
                " tra.`created_by` AS 'Spokedoctor', IFNULL(mdp.`department_name`, ' '), " +
                " IF(tra.`appointment_is_confirm` = 1, 'Yes', 'NO') AS 'AppointmentConfirm', " +
                " IF(tra.`appointment_is_closed` = 1, 'Yes', 'No') AS 'AppointmentClosed', " +
                " IF(tra.`appointment_is_cancelled` = 1, 'Yes', 'No') AS 'AppointmentCancelled', IFNULL(md2.`district_name`, '') AS 'District_name', " +
                " tra.`appointmentreason`, " +
                " tra.`created_by` AS 'AppointmentBookedBy'," +
//                " mr.role_name AS 'RoleName', " +
                " mbs.`service_abbreviations` AS 'ConsultationService', CONCAT(mt1.`title_name` , mu1.`user_firstname`, ' ', mu1.`user_lastname`) AS 'Finalized_by', IFNULL(mc.`cluster_name`, ' ') AS 'hubUnitName', IFNULL(md.`district_name`, '') AS 'District', " +
                " mun.`unit_name` AS 'UserUnitName', IFNULL(mut2.`unittype_name`, '') AS 'ConsultationUnitType', IFNULL(msp.`speciality_name`, ' '), IFNULL(tt.`timeline_doctor_advice`, '') AS 'DoctorAdvise'," +
                " IF(tt.`isemrfinal` = 1, 'Yes', 'No') AS 'emr_status' " +
                " FROM `temr_timeline` tt " +
                " INNER JOIN `mst_patient` mp ON tt.`timeline_patient_id` = mp.`patient_id` " +
                " INNER JOIN `mst_user` mu ON mp.`patient_user_id` = mu.`user_id` " +
                " LEFT JOIN `mst_title` mt ON mu.`user_title_id` = mt.`title_id` " +
                " LEFT JOIN `mst_gender` mg ON mu.`user_gender_id` = mg.`gender_id` " +
                " LEFT JOIN `mst_visit` mv ON tt.`timeline_visit_id` = mv.`visit_id` " +
                " LEFT JOIN `mbill_tariff` mta ON mv.`visit_tariff_id` = mta.`tariff_id` " +
                " INNER JOIN `mst_staff` ms ON tt.`timelineemrfinal_staff_id` = ms.`staff_id` " +
                " INNER JOIN `staff_configuration` sf ON sf.`sc_staff_id` = tt.`timelineemrfinal_staff_id` " +
                " LEFT JOIN `mst_unit` mun ON sf.`sc_unit_id` = mun.`unit_id` " +
                " LEFT JOIN `mst_unit_type` mut2 ON mut2.`unittype_id` = mun.unit_unittype_id " +
                " LEFT JOIN `mst_user` mu1 ON ms.`staff_user_id` = mu1.`user_id` " +
                " LEFT JOIN `mst_title` mt1 ON mu1.`user_title_id` = mt1.`title_id` " +
                " LEFT JOIN `mst_unit` mul ON mul.`unit_id` = sf.`sc_unit_id` " +
                " LEFT JOIN `mst_city` mct ON mct.`city_id` = mul.`unit_city_id` " +
                " LEFT JOIN `mst_district` md ON md.`district_id` = mct.`city_district_id` " +
                " INNER JOIN `trn_appointment` tra ON tra.`appointment_timeline_id` = tt.`timeline_id` " +
                " LEFT JOIN `mst_unit` mun2 ON mun2.`unit_id` = tra.`appointment_unit_id` " +
                " LEFT JOIN `mst_unit_type` mut ON mut.`unittype_id` = mun2.unit_unittype_id " +
                " LEFT JOIN `mst_staff` ms1 ON ms1.`staff_id` = tra.`appointment_staff_id` " +
                " LEFT JOIN `mst_speciality` msp ON msp.`speciality_id` = ms.`staff_speciality_id` " +
                " LEFT JOIN `mst_user` mu3 ON mu3.`user_id` = ms1.`staff_user_id` " +
                " INNER JOIN `mst_department` mdp ON mdp.`department_id` = mv.`visit_department_id` " +
                " LEFT JOIN `staff_configuration` sf1 ON sf1.`sc_department_id` = mdp.`department_id` " +
                " LEFT JOIN `mst_staff_staff_cluster_id_list` staff_cluster ON staff_cluster.`mst_staff_staff_id` = ms.`staff_id` " +
                " LEFT JOIN `mst_cluster` mc ON mc.`cluster_id` = staff_cluster.`staff_cluster_id_list_cluster_id` " +
                " LEFT JOIN `mbill_service` mbs ON mbs.`service_id` = tra.`appointment_service_id` " +
                " LEFT JOIN `mst_city` mct2 ON mct2.`city_id` = mun2.`unit_city_id` " +
                " LEFT JOIN `mst_district` md2 ON md2.`district_id` = mct2.`city_district_id` " +
//                " LEFT JOIN mst_user  mu4 ON mu4.user_fullname = tra.created_by " +
//                " LEFT JOIN mst_staff ms4 ON ms4.staff_user_id = mu4.user_id " +
//                " LEFT JOIN mst_role mr ON mr.role_id = ms4.staff_role " +
                " WHERE tt.`isemrfinal` = 1 AND tt.`is_active` = 1 AND tt.`is_deleted` = 0";
        if (!finalizecountemrsearchDTO.unitList[0].equals("null")) {
            String values = String.valueOf(finalizecountemrsearchDTO.unitList[0]);
            for (int i = 1; i < finalizecountemrsearchDTO.unitList.length; i++) {
                values += "," + finalizecountemrsearchDTO.unitList[i];
            }
            MainQuery += " and sf.sc_unit_id in (" + values + ") ";

        }
        if(finalizecountemrsearchDTO.getSpecialityId() != null) {
            MainQuery += " and msp.speciality_id  =  " + finalizecountemrsearchDTO.getSpecialityId() + " ";
        }

        if (!finalizecountemrsearchDTO.unitConsultationList[0].equals("null")) {
            String values = String.valueOf(finalizecountemrsearchDTO.unitConsultationList[0]);
            for (int i = 1; i < finalizecountemrsearchDTO.unitConsultationList.length; i++) {
                values += "," + finalizecountemrsearchDTO.unitConsultationList[i];
            }
            MainQuery += " and mun.unit_id in (" + values + ") ";
        }

        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }

        if (finalizecountemrsearchDTO.getMrNo() != null && !finalizecountemrsearchDTO.getMrNo().equals("")) {
            MainQuery += " and mp.patient_mr_no like  '%" + finalizecountemrsearchDTO.getMrNo() + "%' ";

        }
        if (finalizecountemrsearchDTO.getStaffId1() != null && !String.valueOf(finalizecountemrsearchDTO.getStaffId1()).equals("0")) {
            MainQuery += "  and tra.createdby_id=" + finalizecountemrsearchDTO.getStaffId1() + " ";

        }

        if (finalizecountemrsearchDTO.getStaffId2() != null && !String.valueOf(finalizecountemrsearchDTO.getStaffId2()).equals("0")) {
            MainQuery += "  and tt.timelineemrfinal_staff_id=" + finalizecountemrsearchDTO.getStaffId2() + " ";

        }

        if (finalizecountemrsearchDTO.getAppointmentDistrictId() != null && !finalizecountemrsearchDTO.getAppointmentDistrictId().equals("0")) {
            MainQuery += " and md2.district_id  =  " + finalizecountemrsearchDTO.getAppointmentDistrictId() + " ";
        }

        if (finalizecountemrsearchDTO.getConsultationDistrictId() != null && !finalizecountemrsearchDTO.getConsultationDistrictId().equals("0")) {
            MainQuery += " and md.district_id  =  " + finalizecountemrsearchDTO.getConsultationDistrictId() + " ";
        }

        if (finalizecountemrsearchDTO.getConsulationUnitType() != null && !String.valueOf(finalizecountemrsearchDTO.getConsulationUnitType()).equals("0")) {
            MainQuery += " and mut2.unittype_id  =  " + finalizecountemrsearchDTO.getConsulationUnitType() + " ";
        }

        if (finalizecountemrsearchDTO.getSpokeUnitType() != null && !String.valueOf(finalizecountemrsearchDTO.getSpokeUnitType()).equals("0")) {
            MainQuery += " and mut.unittype_id =  " + finalizecountemrsearchDTO.getSpokeUnitType() + " ";
        }

        if (finalizecountemrsearchDTO.getAppointmentConfirm() != null && !finalizecountemrsearchDTO.getAppointmentConfirm().equals("0")) {
            MainQuery += " and tra.appointment_is_confirm = " + finalizecountemrsearchDTO.getAppointmentConfirm() + " ";
        }

        if (finalizecountemrsearchDTO.getAppointmentClosed() != null && !finalizecountemrsearchDTO.getAppointmentClosed().equals("0")) {
            MainQuery += " and tra.appointment_is_closed = " + finalizecountemrsearchDTO.getAppointmentClosed() + " ";
        }

        if (finalizecountemrsearchDTO.getAppointmentCancelled() != null && !finalizecountemrsearchDTO.getAppointmentCancelled().equals("0")) {
            MainQuery += " and tra.appointment_is_cancelled = " + finalizecountemrsearchDTO.getAppointmentCancelled() + " ";
        }

        if (finalizecountemrsearchDTO.getPatientName() != null && !finalizecountemrsearchDTO.getPatientName().equals("")) {
            MainQuery += " and concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname) LIKE '%" + finalizecountemrsearchDTO.getPatientName() + "%'";
        }
        MainQuery += " GROUP BY mv.visit_id ";
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        for (Object[] obj : list) {
            if (obj[6].toString() != null && !"".equals(obj[6].toString())) {
                if (genderWiseCount.get(obj[6].toString()) == null) {
                    genderWiseCount.put(obj[6].toString(), 1);
                } else {
                    int gender_count = genderWiseCount.get(obj[6].toString());
                    genderWiseCount.put(obj[6].toString(), ++gender_count);
                }
            }
        }
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "AppointmentDate,VisitDate,AppointmentSlot,patient_mr_no,Patient_Name,user_mobile,SpokeUnit,SpokeUnitType,Spokedoctor,department_name,AppointmentConfirm,AppointmentClosed,AppointmentCancelled,District_name,appointmentreason,AppointmentBookedBy,ConsultationService,Finalized_by,hubUnitName,District,unit_name,ConsultationUnitType,speciality_name,DoctorAdvise,emr_status";
        System.out.print(MainQuery);
        //MainQuery += "  limit " + ((finalizecountemrsearchDTO.getOffset() - 1) * finalizecountemrsearchDTO.getLimit()) + "," + finalizecountemrsearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));

        MstUnit HeaderObject = mstUnitRepository.findByUnitId(finalizecountemrsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(finalizecountemrsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (finalizecountemrsearchDTO.getPrint()) {
            columnName = "AppointmentDate,VisitDate,AppointmentSlot,patient_mr_no,Patient_Name,user_mobile,SpokeUnit,SpokeUnitType,Spokedoctor,department_name,AppointmentConfirm,AppointmentClosed,AppointmentCancelled,District_name,appointmentreason,AppointmentBookedBy,ConsultationService,Finalized_by,hubUnitName,District,unit_name,ConsultationUnitType,speciality_name,DoctorAdvise,emr_status";
            return createReport.generateExcel(columnName, "NewFinalizedEmrCountReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("NewFinalizedEmrCountReport", jsonArray.toString());
        }

    }


    @RequestMapping("searchPatientwaitDurationReport/{staffList}/{fromdate}/{todate}")
    public ResponseEntity searchPatientwaitDurationReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO, @PathVariable String[] staffList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery = " SELECT tt.timeline_patient_id, muunit.unit_name, ifnull(mv.visit_date,'')as visit_date, " +
                "ifnull(mp.patient_mr_no, '') as patient_mr_no, "
                + " ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as Patient_Name,"
                + " ifnull(mu.user_age,'')as user_age, ifnull(mg.gender_name, '') as gender_name,  "
                + " CONCAT(mt1.title_name,' ', mu1.user_firstname,' ', mu1.user_lastname) AS Finalized_By, ifnull(md.district_name, '') AS  District_name, "
                + " mv.visit_waiting_duration as waitDurat, count(mv.visit_id), IFNULL(MIN(mv.visit_waiting_duration),'') AS MINdurat, IFNULL(MAX(mv.visit_waiting_duration),'') AS maxDurat, AVG(mv.visit_waiting_duration) AS avgDurat, concat(MIN(date(mv.created_date)),' to ', MAX(date(mv.created_date))) AS toDate, mv.opd_visit_type AS visitType "
                + " from temr_timeline tt " + " INNER JOIN mst_patient mp on tt.timeline_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                " LEFT JOIN mst_visit mv ON tt.timeline_visit_id = mv.visit_id " +
                " LEFT JOIN mbill_tariff mta ON mv.visit_tariff_id = mta.tariff_id " +
                " INNER JOIN mst_staff ms ON tt.timelineemrfinal_staff_id = ms.staff_id " +
                " INNER JOIN staff_configuration sf ON sf.sc_staff_id=tt.timelineemrfinal_staff_id " +
                " LEFT JOIN mst_unit mun ON sf.sc_unit_id = mun.unit_id " +
                " LEFT JOIN mst_user mu1 ON ms.staff_user_id = mu1.user_id " +
                " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                " LEFT JOIN mst_unit mul ON mul.unit_id = sf.sc_unit_id " +
                " LEFT JOIN mst_city mct ON mct.city_id = mul.unit_city_id " +
                " LEFT JOIN mst_district md ON md.district_id = mct.city_district_id " +
                " LEFT JOIN mst_unit muunit ON muunit.unit_id = mv.visit_unit_id" +
                " WHERE tt.isemrfinal = 1 AND tt.is_active=1 AND tt.is_deleted=0 and mv.visit_unit_id =" + finalizecountemrsearchDTO.getUnitId();



        if (finalizecountemrsearchDTO.getVisitType() != null && !finalizecountemrsearchDTO.getVisitType().equals("null")) {
            MainQuery += " and mv.opd_visit_type =" + finalizecountemrsearchDTO.getVisitType();
        } else {
            MainQuery += " and mv.opd_visit_type != 0 ";

        }
        if (!staffList[0].equals("null")) {
            String values = String.valueOf(staffList[0]);
            for (int i = 1; i < staffList.length; i++) {
                values += "," + staffList[i];
            }
//            MainQuery += " and sf.sc_unit_id in (" + values + ") ";
            MainQuery += " and mv.visit_staff_id in (" + values + ") ";

        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }

        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        for (Object[] obj : list) {
            if (obj[6].toString() != null && !"".equals(obj[6].toString())) {
                if (genderWiseCount.get(obj[6].toString()) == null) {
                    genderWiseCount.put(obj[6].toString(), 1);
                } else {
                    int gender_count = genderWiseCount.get(obj[6].toString());
                    genderWiseCount.put(obj[6].toString(), ++gender_count);
                }
            }
        }

        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "timeline_patient_id,unit_name,visit_date,patient_mr_no,Patient_Name,user_age,gender_name,Finalized_By,District_name,waitDurat,noofConsult,minDurat,maxDurat,avgDurat,toDate,visitType";
        MainQuery += " GROUP BY mv.visit_staff_id ";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonArray.getJSONObject(i).put("genderCount", new JSONObject(genderWiseCount));
            }
        } catch (Exception e) {
        }
        return ResponseEntity.ok(jsonArray.toString());
    }

    @RequestMapping("searchPatientwaitDurationReportPrint/{staffList}/{fromdate}/{todate}")
    public String searchPatientwaitDurationReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody finalizecountemrSearchDTO finalizecountemrsearchDTO, @PathVariable String[] staffList, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";

        MainQuery = " SELECT tt.timeline_patient_id, muunit.unit_name, ifnull(mv.visit_date,'')as visit_date, " +
                "ifnull(mp.patient_mr_no, '') as patient_mr_no, "
                + " ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as Patient_Name,"
                + " ifnull(mu.user_age,'')as user_age, ifnull(mg.gender_name, '') as gender_name,  "
                + " CONCAT(mt1.title_name,' ', mu1.user_firstname,' ', mu1.user_lastname) AS Finalized_By, ifnull(md.district_name, '') AS  District_name, "
                + " mv.visit_waiting_duration as waitDurat, count(mv.visit_id), IFNULL(MIN(mv.visit_waiting_duration),'') AS MINdurat, IFNULL(MAX(mv.visit_waiting_duration),'') AS maxDurat, AVG(mv.visit_waiting_duration) AS avgDurat, concat(MIN(date(mv.created_date)),' to ', MAX(date(mv.created_date))) AS toDate, mv.opd_visit_type AS visitType "
                + " from temr_timeline tt " + " INNER JOIN mst_patient mp on tt.timeline_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                " LEFT JOIN mst_visit mv ON tt.timeline_visit_id = mv.visit_id " +
                " LEFT JOIN mbill_tariff mta ON mv.visit_tariff_id = mta.tariff_id " +
                " INNER JOIN mst_staff ms ON tt.timelineemrfinal_staff_id = ms.staff_id " +
                " INNER JOIN staff_configuration sf ON sf.sc_staff_id=tt.timelineemrfinal_staff_id " +
                " LEFT JOIN mst_unit mun ON sf.sc_unit_id = mun.unit_id " +
                " LEFT JOIN mst_user mu1 ON ms.staff_user_id = mu1.user_id " +
                " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                " LEFT JOIN mst_unit mul ON mul.unit_id = sf.sc_unit_id " +
                " LEFT JOIN mst_city mct ON mct.city_id = mul.unit_city_id " +
                " LEFT JOIN mst_district md ON md.district_id = mct.city_district_id " +
                " LEFT JOIN mst_unit muunit ON muunit.unit_id = mv.visit_unit_id" +
                " WHERE tt.isemrfinal = 1 AND tt.is_active=1 AND tt.is_deleted=0 and mv.visit_unit_id =" + finalizecountemrsearchDTO.getUnitId();



        if (!finalizecountemrsearchDTO.getVisitType().equals("null")) {
            MainQuery += " and mv.opd_visit_type =" + finalizecountemrsearchDTO.getVisitType();
        } else {
            MainQuery += " and mv.opd_visit_type != 0 ";

        }
        if (!staffList[0].equals("null")) {
            String values = String.valueOf(staffList[0]);
            for (int i = 1; i < staffList.length; i++) {
                values += "," + staffList[i];
            }
//            MainQuery += " and sf.sc_unit_id in (" + values + ") ";
            MainQuery += " and mv.visit_staff_id in (" + values + ") ";

        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (finalizecountemrsearchDTO.getTodaydate()) {
            MainQuery += " and (date(mv.visit_date)=curdate()) ";
        } else {
            MainQuery += " and (date(mv.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }


        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        HashMap<String, Integer> genderWiseCount = new HashMap<>();
        for (Object[] obj : list) {
            if (obj[6].toString() != null && !"".equals(obj[6].toString())) {
                if (genderWiseCount.get(obj[6].toString()) == null) {
                    genderWiseCount.put(obj[6].toString(), 1);
                } else {
                    int gender_count = genderWiseCount.get(obj[6].toString());
                    genderWiseCount.put(obj[6].toString(), ++gender_count);
                }
            }
        }
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "timeline_patient_id,unit_name,visit_date,patient_mr_no,Patient_Name,user_age,gender_name,Finalized_By,District_name,waitDurat,noofConsult,minDurat,maxDurat,avgDurat,toDate,visitType";
        MainQuery += " GROUP BY mv.visit_staff_id ";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonArray.getJSONObject(i).put("genderCount", new JSONObject(genderWiseCount));
            }
        } catch (Exception e) {
        }
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(finalizecountemrsearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(finalizecountemrsearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (finalizecountemrsearchDTO.getPrint()) {
            columnName = "timeline_patient_id,unit_name,visit_date,patient_mr_no,Patient_Name,user_age,gender_name,Finalized_By,District_name";
            return createReport.generateExcel(columnName, "PatientWaitDurationReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("PatientWaitDurationReport", jsonArray.toString());
        }
    }

    @RequestMapping("searchHubSpokesummaryReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchHubSpokeDetail(@RequestHeader("X-tenantId") String tenantName, @RequestBody hubspokesummarySearchDTO hubspokesummarysearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        //Main EMR Finalized Query
        MainQuery = " SELECT IFNULL(t.appointment_date,'') AS appointment_date, un.unit_name, " +
                "CASE WHEN t.is_virtual=1 THEN 'Virtual' ELSE 'Physical' END AS isVirtual,ifnull(cl.cluster_name,'')as cluster_name,t.hub_id, " +
                "SUM(CASE WHEN (t.appointment_is_confirm=0 AND t.appointment_is_block= FALSE AND t.appointment_is_cancelled= FALSE) THEN 1 ELSE 0 END) AS booked_app, " +
                "SUM(CASE WHEN (t.appointment_is_confirm= TRUE AND t.appointment_is_cancelled= FALSE) THEN 1 ELSE 0 END) AS arrived_app, " +
                "CONCAT(IFNULL(ust.user_firstname,''),' ', IFNULL(ust.user_lastname,'')) AS Doctor_name,";
        MainQuery = MainQuery + " (" +
                "SELECT COUNT(*)" +
                "FROM temr_timeline tt" +
                " WHERE tt.isemrfinal= TRUE AND tt.timeline_staff_id=t.appointment_staff_id ";
        if (hubspokesummarysearchDTO.getTodaydate()) {
            MainQuery += " and (date(tt.timeline_finalized_date)=curdate()) ";
        } else {
            MainQuery += " and (date(tt.timeline_finalized_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        MainQuery = MainQuery + " ) AS finalized_count, IFNULL(t.appointment_slot,'') AS appointment_time FROM trn_appointment t " +
                "LEFT JOIN mst_staff st ON t.appointment_staff_id=st.staff_id " +
                "LEFT JOIN mst_user ust ON st.staff_user_id=ust.user_id " +
                "LEFT JOIN mst_unit un ON t.appointment_unit_id=un.unit_id " +
                "LEFT JOIN mst_cluster cl ON t.hub_id=cl.cluster_id " +
                " WHERE t.is_active = 1 AND t.is_deleted = 0";
        if (hubspokesummarysearchDTO.getTodaydate()) {
            MainQuery += " and (date(t.appointment_date)=curdate()) ";
        } else {
            MainQuery += " and (date(t.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += "AND t.appointment_unit_id in (" + values + ") ";

        }

        if (hubspokesummarysearchDTO.getClusterId() != null && !String.valueOf(hubspokesummarysearchDTO.getClusterId()).equals("0")) {
            MainQuery += " AND t.hub_id=" + hubspokesummarysearchDTO.getClusterId() + " ";

        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "appointment_date,unit_name,isVirtual,cluster_name,hub_id,booked_app,arrived_app,Doctor_name,finalized_count,appointment_time";
        MainQuery += " GROUP BY t.appointment_staff_id  limit " + ((hubspokesummarysearchDTO.getOffset() - 1) * hubspokesummarysearchDTO.getLimit()) + "," + hubspokesummarysearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        return ResponseEntity.ok(jsonArray.toString());
    }

    @RequestMapping("searchHubSpokesummaryReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchHubSpokeDetailPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody hubspokesummarySearchDTO hubspokesummarysearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String genderCount = " ";
        String CountQuery = "";
        String columnName = "";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        //Main EMR Finalized Query
        MainQuery = " SELECT IFNULL(t.appointment_date,'') AS appointment_date, un.unit_name, " +
                "CASE WHEN t.is_virtual=1 THEN 'Virtual' ELSE 'Physical' END AS isVirtual,ifnull(cl.cluster_name,'')as cluster_name,t.hub_id, " +
                "SUM(CASE WHEN (t.appointment_is_confirm=0 AND t.appointment_is_block= FALSE AND t.appointment_is_cancelled= FALSE) THEN 1 ELSE 0 END) AS booked_app, " +
                "SUM(CASE WHEN (t.appointment_is_confirm= TRUE AND t.appointment_is_cancelled= FALSE) THEN 1 ELSE 0 END) AS arrived_app, " +
                "CONCAT(IFNULL(ust.user_firstname,''),' ', IFNULL(ust.user_lastname,'')) AS Doctor_name,";
        MainQuery = MainQuery + " (" +
                "SELECT COUNT(*)" +
                "FROM temr_timeline tt" +
                " WHERE tt.isemrfinal= TRUE AND tt.timeline_staff_id=t.appointment_staff_id ";
        if (hubspokesummarysearchDTO.getTodaydate()) {
            MainQuery += " and (date(tt.timeline_finalized_date)=curdate()) ";
        } else {
            MainQuery += " and (date(tt.timeline_finalized_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        MainQuery = MainQuery + " ) AS finalized_count FROM trn_appointment t " +
                "LEFT JOIN mst_staff st ON t.appointment_staff_id=st.staff_id " +
                "LEFT JOIN mst_user ust ON st.staff_user_id=ust.user_id " +
                "LEFT JOIN mst_unit un ON t.appointment_unit_id=un.unit_id " +
                "LEFT JOIN mst_cluster cl ON t.hub_id=cl.cluster_id " +
                " WHERE t.is_active = 1 AND t.is_deleted = 0";
        if (hubspokesummarysearchDTO.getTodaydate()) {
            MainQuery += " and (date(t.appointment_date)=curdate()) ";
        } else {
            MainQuery += " and (date(t.appointment_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += "AND t.appointment_unit_id in (" + values + ") ";

        }

        if (hubspokesummarysearchDTO.getClusterId() != null && !String.valueOf(hubspokesummarysearchDTO.getClusterId()).equals("0")) {
            MainQuery += " AND t.hub_id=" + hubspokesummarysearchDTO.getClusterId() + " ";

        }
        System.out.println("MainQuery:" + MainQuery);
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainQuery).getResultList();
        CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "appointment_date,unit_name,isVirtual,cluster_name,hub_id,booked_app,arrived_app,Doctor_name,finalized_count";
        MainQuery += " GROUP BY t.appointment_staff_id  limit " + ((hubspokesummarysearchDTO.getOffset() - 1) * hubspokesummarysearchDTO.getLimit()) + "," + hubspokesummarysearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        //return ResponseEntity.ok(jsonArray.toString());
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(hubspokesummarysearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(hubspokesummarysearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
//        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
//        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (hubspokesummarysearchDTO.getPrint()) {
            columnName = "appointment_date,unit_name,isVirtual,cluster_name,hub_id,booked_app,arrived_app,Doctor_name,finalized_count";
            return createReport.generateExcel(columnName, "HubSpokeAppointmentSummaryReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("HubSpokeAppointmentSummaryReport", jsonArray.toString());
        }
    }

    // Rohan Code For Sponsor Settlement List start
    @GetMapping
    @RequestMapping("getSponcerSettaleReport")
    public List<TrnBillBillSponsor> getSponcerSettaleReport(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String query2 = "SELECT p from TrnBillBillSponsor p where  p.isActive=1 and p.isDeleted=0 ";
        String countQuery = "SELECT count(p.bbsId) from TrnBillBillSponsor p where  p.isActive=1 and p.isDeleted=0 ";
        List<TrnBillBillSponsor> listTrnBillBillSponsor = entityManager.createQuery(query2, TrnBillBillSponsor.class)
                .getResultList();
        count = (long) entityManager.createQuery(countQuery).getSingleResult();
        if (listTrnBillBillSponsor.size() > 0) {
            listTrnBillBillSponsor.get(0).setCount(count);
        }
        return listTrnBillBillSponsor;
    }

    @GetMapping
    @RequestMapping("getSerachSponcerSettleReport")
    public List<TrnBillBillSponsor> getSerachSponcerSettleReport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate,
                                                                 @PathVariable("todate") String todate, @PathVariable("todaydate") String todaydate,
                                                                 @PathVariable("bbsId") String bbsId, @PathVariable("limit") String limit,
                                                                 @PathVariable("offset") String offset) {
        TenantContext.setCurrentTenant(tenantName);

        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query2 = "SELECT p from TrnBillBillSponsor p where  p.isActive=1 and p.isDeleted=0 ";
        String countQuery = "SELECT count(p.bbsId) from TrnBillBillSponsor p where  p.isActive=1 and p.isDeleted=0 ";
        // int foo = Integer.parseInt(bbsId);
        if (!bbsId.equals("0")) {
            query2 += " and p.bbsSCId.scCompanyId.companyId=" + bbsId + " ";
            countQuery += " and p.bbsSCId.scCompanyId.companyId=" + bbsId + " ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (todaydate.equals("T")) {
            query2 += "  and date(p.bbsBillId.billDate)='" + strDate + "' ";
            countQuery += "  and date(p.bbsBillId.billDate)='" + strDate + "' ";
        } else {
            query2 += " and (date(p.bbsBillId.billDate) between '" + fromdate + "' and '" + todate + "') ";
            countQuery += " and (date(p.bbsBillId.billDate) between '" + fromdate + "' and '" + todate + "') ";
        }
        List<TrnBillBillSponsor> listTrnBillBillSponsor = entityManager.createQuery(query2, TrnBillBillSponsor.class)
                .setFirstResult((Integer.parseInt(offset) - 1)).setMaxResults(Integer.parseInt(limit)).getResultList();
        count = (long) entityManager.createQuery(countQuery).getSingleResult();
        if (listTrnBillBillSponsor.size() > 0) {
            listTrnBillBillSponsor.get(0).setCount(count);
        }
        return listTrnBillBillSponsor;
    }


}
