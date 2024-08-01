package com.cellbeans.hspa.mis.misinventoryexpiryreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_inventory_expiry_report")
public class MisinventoryExpiryController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    MstUserRepository mstUserRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CreateReport createReport;

    /*@RequestMapping("search/{unitList}")
    public List<TinvOpeningBalanceItem> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchMisinventoryExpiry searchMisinventoryExpiry, @PathVariable Long[] unitList) {

        long count = 0;
        List<TinvOpeningBalanceItem> ItemList = new ArrayList<TinvOpeningBalanceItem>();

        if (searchMisinventoryExpiry == null) {
            SearchMisinventoryExpiry sb = new SearchMisinventoryExpiry();
            searchMisinventoryExpiry = sb;
        }

        String queryInya = "";


        if (searchMisinventoryExpiry.getSearchFromExpdate() == null) {
            String sDate1 = "1998/12/31";
            searchMisinventoryExpiry.setSearchFromExpdate(sDate1);
        }
        if (searchMisinventoryExpiry.getSearchToExpdate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchToExpdate(date);
        }

        queryInya = "Select i from TinvOpeningBalanceItem i Where  date(i.obiItemExpiryDate) between   " + " ' " + searchMisinventoryExpiry.getSearchFromExpdate() + " ' AND  " + "'" + searchMisinventoryExpiry.getSearchToExpdate() + "' ";


//        if (searchMisinventoryExpiry.getUnitId() != 0) {
//            queryInya += " And i.openingBalanceUnitId.unitId=" + searchMisinventoryExpiry.getUnitId();
//        }

        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryInya += " And i.openingBalanceUnitId.unitId in (" + values + ") ";
        }


        if (searchMisinventoryExpiry.getStoreId() != 0) {
            queryInya += " And i.obiStoreId.storeId=" + searchMisinventoryExpiry.getStoreId();
        }

        queryInya += " order by i.obiId desc";


        System.out.println(queryInya);
        //List<TinvOpeningBalanceItem> ItemListfinal = new ArrayList<TinvOpeningBalanceItem>();
        String CountQuery = StringUtils.replace(queryInya, " i fr", " count(i.obiId) fr");
        System.out.println("Count:" + CountQuery);
        count = (Long) entityManager.createQuery(CountQuery).getSingleResult();
        ItemList = entityManager.createQuery(queryInya, TinvOpeningBalanceItem.class).setFirstResult(searchMisinventoryExpiry.getOffset() - 1).setMaxResults(searchMisinventoryExpiry.getLimit()).getResultList();


        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }*/

    // 24-02-2020
    @RequestMapping("search/{unitList}")
    public List<TinvOpeningBalanceItem> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName,
                                                                     @RequestBody SearchMisinventoryExpiry searchMisinventoryExpiry, @PathVariable Long[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = searchMisinventoryExpiry.getLimit() * ((searchMisinventoryExpiry.getOffset()) - 1);
        int limit = searchMisinventoryExpiry.getLimit();
        long count = 0;
        List<TinvOpeningBalanceItem> ItemList = new ArrayList<TinvOpeningBalanceItem>();
        if (searchMisinventoryExpiry == null) {
            SearchMisinventoryExpiry sb = new SearchMisinventoryExpiry();
            searchMisinventoryExpiry = sb;
        }
        String queryInya = "";
        if (searchMisinventoryExpiry.getSearchFromExpdate() == null) {
//			String sDate1 = "1998/12/31";
            String sDate1 = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchFromExpdate(sDate1);
        }
        if (searchMisinventoryExpiry.getSearchToExpdate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchToExpdate(date);
        }
        queryInya = "Select i from TinvOpeningBalanceItem i Where  date(i.obiItemExpiryDate) between   " + " ' "
                + searchMisinventoryExpiry.getSearchFromExpdate() + " ' AND  " + "'"
                + searchMisinventoryExpiry.getSearchToExpdate() + "' ";
        // if (searchMisinventoryExpiry.getUnitId() != 0) {
        // queryInya += " And i.openingBalanceUnitId.unitId=" +
        // searchMisinventoryExpiry.getUnitId();
        // }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryInya += " And i.openingBalanceUnitId.unitId in (" + values + ") ";
        }
        if (searchMisinventoryExpiry.getStoreId() != 0) {
            queryInya += " And i.obiStoreId.storeId=" + searchMisinventoryExpiry.getStoreId();
        }
        queryInya += " order by i.obiId desc";
        System.out.println("Expired Item List Report "+ queryInya);
        // List<TinvOpeningBalanceItem> ItemListfinal = new
        // ArrayList<TinvOpeningBalanceItem>();
        String CountQuery = StringUtils.replace(queryInya, " i fr", " count(i.obiId) fr");
        System.out.println("Count:" + CountQuery);
        count = (Long) entityManager.createQuery(CountQuery).getSingleResult();
        ItemList = entityManager.createQuery(queryInya, TinvOpeningBalanceItem.class)
                .setFirstResult(offset)
                .setMaxResults(limit).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping("searchPrint/{unitList}")
    public String getListOfItemsDependsOnSerchPrint(@RequestHeader("X-tenantId") String tenantName,
                                                    @RequestBody SearchMisinventoryExpiry searchMisinventoryExpiry, @PathVariable Long[] unitList) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        JSONArray jsonArray = null;
        List<TinvOpeningBalanceItem> ItemList = new ArrayList<TinvOpeningBalanceItem>();
        if (searchMisinventoryExpiry == null) {
            SearchMisinventoryExpiry sb = new SearchMisinventoryExpiry();
            searchMisinventoryExpiry = sb;
        }
        String queryInya = "";
        if (searchMisinventoryExpiry.getSearchFromExpdate() == null) {
//			String sDate1 = "1998/12/31";
            String sDate1 = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchFromExpdate(sDate1);
        }
        if (searchMisinventoryExpiry.getSearchToExpdate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchToExpdate(date);
        }
        queryInya = "Select ifnull(mu.unit_name,'') as unitName, ifnull(i.item_name,'') as itemName,ifnull(s.obi_item_batch_code,'') as itemBatchcode, ifnull(date(s.obi_item_expiry_date),'') as expiryDate, ifnull(s.obi_item_quantity,'') as itemQty, ifnull(idt.idt_name,'') as idtName from tinv_opening_balance_item s LEFT JOIN inv_item i on s.obi_item_id=i.item_id LEFT JOIN mst_unit mu on mu.unit_id = s.opening_balance_unit_id LEFT JOIN inv_store ins on ins.store_id = s.obi_store_id LEFT JOIN inv_item_dispensing_type idt on  idt.idt_id = s.obi_item_sale_idt_id" +
                " Where date(s.obi_item_expiry_date) between  " + "'"
                + searchMisinventoryExpiry.getSearchFromExpdate() + "' AND  " + "'"
                + searchMisinventoryExpiry.getSearchToExpdate() + "' ";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryInya += " And s.opening_balance_unit_id in (" + values + ") ";
        }
        if (searchMisinventoryExpiry.getStoreId() != 0) {
            queryInya += " And s.obi_store_id=" + searchMisinventoryExpiry.getStoreId();
        }
        queryInya += " order by s.obi_id desc";
        String CountQuery = " select count(*) from ( " + queryInya + " ) as combine ";
        String columnName = "unitName,itemName,itemBatchcode,expiryDate,itemQty,idtName";
        jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, queryInya, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(searchMisinventoryExpiry.getCurrentUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(searchMisinventoryExpiry.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        if (jsonArray != null) {
            jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
            jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonArray.getJSONObject(i).put("showType", searchMisinventoryExpiry.getShowType());
        }
        if (searchMisinventoryExpiry.isPrint()) {
            columnName = "unitName,itemName,itemBatchcode,expiryDate,itemQty,idtName";
            return createReport.generateExcel(columnName, "ExpiredItemListReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("ExpiredItemListReport", jsonArray.toString());
        }
    }


    /*@RequestMapping("searchMin/{unitList}")
    public List<TinvOpeningBalanceItem> getListOfItemsDependsOnSerchMin(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchMisinventoryExpiry searchMisinventoryExpiry, @PathVariable Long[] unitList) {

        long count = 0;
        BigInteger bi1;
        List<TinvOpeningBalanceItem> ItemList = new ArrayList<TinvOpeningBalanceItem>();

        if (searchMisinventoryExpiry == null) {
            SearchMisinventoryExpiry sb = new SearchMisinventoryExpiry();
            searchMisinventoryExpiry = sb;
        }

        String queryInya = "";


        if (searchMisinventoryExpiry.getSearchFromExpdate() == null) {
            String sDate1 = "1998/12/31";
            searchMisinventoryExpiry.setSearchFromExpdate(sDate1);
        }
        if (searchMisinventoryExpiry.getSearchToExpdate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchToExpdate(date);
        }
//        SELECT s.* FROM hspa.tinv_opening_balance_item s,hspa.inv_item i where s.obi_item_id=i.item_id
//        and i.item_minimum_quantity>s.obi_item_quantity and (s.created_date between '1998-12-21' and '2018-06-12')


        queryInya = "SELECT * FROM tinv_opening_balance_item s,inv_item i where s.obi_item_id=i.item_id and i.item_minimum_quantity is not null " + " and (s.created_date between '" + searchMisinventoryExpiry.getSearchFromExpdate() + "' and '" + searchMisinventoryExpiry.getSearchToExpdate() + "') ";

        String CuntQuery = "SELECT count(s.obi_id) FROM tinv_opening_balance_item s,inv_item i where s.obi_item_id=i.item_id and i.item_minimum_quantity is not null " + " and (s.created_date between '" + searchMisinventoryExpiry.getSearchFromExpdate() + "' and '" + searchMisinventoryExpiry.getSearchToExpdate() + "') ";

//        if (searchMisinventoryExpiry.getUnitId() != 0) {
//            queryInya += " and s.opening_balance_unit_id=" + searchMisinventoryExpiry.getUnitId();
//            CuntQuery += " and s.opening_balance_unit_id=" + searchMisinventoryExpiry.getUnitId();
//        }

        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryInya += " and s.opening_balance_unit_id in (" + values + ") ";
            CuntQuery += " and s.opening_balance_unit_id in  (" + values + ") ";
        }

        if (searchMisinventoryExpiry.getStoreId() != 0) {
            queryInya += " and s.obi_store_id=" + searchMisinventoryExpiry.getStoreId();
            CuntQuery += " and s.obi_store_id=" + searchMisinventoryExpiry.getStoreId();
        }

        queryInya += " order by s.obi_id desc limit " + ((searchMisinventoryExpiry.getOffset() - 1) * searchMisinventoryExpiry.getLimit()) + " , " + searchMisinventoryExpiry.getLimit();


        System.out.println(queryInya);
        //List<TinvOpeningBalanceItem> ItemListfinal = new ArrayList<TinvOpeningBalanceItem>();
        //String CountQuery=StringUtils.replace(queryInya," * FR"," count(s.obiId) FR");
        System.out.println("Count:" + CuntQuery);
        bi1 = (BigInteger) entityManager.createNativeQuery(CuntQuery).getSingleResult();
        count = bi1.longValue();
        ItemList = entityManager.createNativeQuery(queryInya, TinvOpeningBalanceItem.class).getResultList();


        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }
*/

    // 24-02-2020
    @RequestMapping("searchMin/{unitList}")
    public List<TinvOpeningBalanceItem> getListOfItemsDependsOnSerchMin(@RequestHeader("X-tenantId") String tenantName,
                                                                        @RequestBody SearchMisinventoryExpiry searchMisinventoryExpiry, @PathVariable Long[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        BigInteger bi1;
        List<TinvOpeningBalanceItem> ItemList = new ArrayList<TinvOpeningBalanceItem>();
        if (searchMisinventoryExpiry == null) {
            SearchMisinventoryExpiry sb = new SearchMisinventoryExpiry();
            searchMisinventoryExpiry = sb;
        }
        String queryInya = "";
        if (searchMisinventoryExpiry.getSearchFromExpdate() == null) {
            String sDate1 = "1998/12/31";
            searchMisinventoryExpiry.setSearchFromExpdate(sDate1);
        }
        if (searchMisinventoryExpiry.getSearchToExpdate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchToExpdate(date);
        }
        // SELECT s.* FROM hspa.tinv_opening_balance_item s,hspa.inv_item i
        // where s.obi_item_id=i.item_id
        // and i.item_minimum_quantity>s.obi_item_quantity and (s.created_date
        // between '1998-12-21' and '2018-06-12')
        queryInya = "SELECT * FROM tinv_opening_balance_item s,inv_item i where s.obi_item_id=i.item_id and i.item_minimum_quantity is not null "
                + " and (date(s.created_date) between '" + searchMisinventoryExpiry.getSearchFromExpdate() + "' and '"
                + searchMisinventoryExpiry.getSearchToExpdate() + "') ";
        String CuntQuery = "SELECT count(s.obi_id) FROM tinv_opening_balance_item s,inv_item i where s.obi_item_id=i.item_id and i.item_minimum_quantity is not null "
                + " and (date(s.created_date) between '" + searchMisinventoryExpiry.getSearchFromExpdate() + "' and '"
                + searchMisinventoryExpiry.getSearchToExpdate() + "') ";
        // if (searchMisinventoryExpiry.getUnitId() != 0) {
        // queryInya += " and s.opening_balance_unit_id=" +
        // searchMisinventoryExpiry.getUnitId();
        // CuntQuery += " and s.opening_balance_unit_id=" +
        // searchMisinventoryExpiry.getUnitId();
        // }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryInya += " and s.opening_balance_unit_id in (" + values + ") ";
            CuntQuery += " and s.opening_balance_unit_id in  (" + values + ") ";
        }
        if (searchMisinventoryExpiry.getStoreId() != 0) {
            queryInya += " and s.obi_store_id=" + searchMisinventoryExpiry.getStoreId();
            CuntQuery += " and s.obi_store_id=" + searchMisinventoryExpiry.getStoreId();
        }
        queryInya += " order by s.obi_id desc limit "
                + ((searchMisinventoryExpiry.getOffset() - 1) * searchMisinventoryExpiry.getLimit()) + " , "
                + searchMisinventoryExpiry.getLimit();
        System.out.println("Item Minimum Qty Report"+queryInya);
        // List<TinvOpeningBalanceItem> ItemListfinal = new
        // ArrayList<TinvOpeningBalanceItem>();
        // String CountQuery=StringUtils.replace(queryInya," * FR","
        // count(s.obiId) FR");
        System.out.println("Count:" + CuntQuery);
        bi1 = (BigInteger) entityManager.createNativeQuery(CuntQuery).getSingleResult();
        count = bi1.longValue();
        ItemList = entityManager.createNativeQuery(queryInya, TinvOpeningBalanceItem.class).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    //24-02-2020
    @RequestMapping("searchMinPrint/{unitList}")
    public String getListOfItemsDependsOnSerchMinPrint(@RequestHeader("X-tenantId") String tenantName,
                                                       @RequestBody SearchMisinventoryExpiry searchMisinventoryExpiry, @PathVariable Long[] unitList) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        BigInteger bi1;
        JSONArray jsonArray = null;
        List<TinvOpeningBalanceItem> ItemList = new ArrayList<TinvOpeningBalanceItem>();
        if (searchMisinventoryExpiry == null) {
            SearchMisinventoryExpiry sb = new SearchMisinventoryExpiry();
            searchMisinventoryExpiry = sb;
        }
        String queryInya = "";
        if (searchMisinventoryExpiry.getSearchFromExpdate() == null) {
            String sDate1 = "1998/12/31";
            searchMisinventoryExpiry.setSearchFromExpdate(sDate1);
        }
        if (searchMisinventoryExpiry.getSearchToExpdate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchToExpdate(date);
        }
        // SELECT s.* FROM hspa.tinv_opening_balance_item s,hspa.inv_item i
        // where s.obi_item_id=i.item_id
        // and i.item_minimum_quantity>s.obi_item_quantity and (s.created_date
        // between '1998-12-21' and '2018-06-12')
        queryInya = "SELECT mu.unit_name as unitName,i.item_name as itemName,ins.store_name as storeName, s.obi_item_quantity as availableQty, i.item_minimum_quantity as minimumQty FROM tinv_opening_balance_item s,inv_item i,mst_unit mu,inv_store ins where s.obi_item_id=i.item_id and  mu.unit_id = s.opening_balance_unit_id and ins.store_id = s.obi_store_id and i.item_minimum_quantity is not null "
                + " and (date(s.created_date) between '" + searchMisinventoryExpiry.getSearchFromExpdate() + "' and '"
                + searchMisinventoryExpiry.getSearchToExpdate() + "') ";
        String CuntQuery = "SELECT count(s.obi_id) FROM tinv_opening_balance_item s,inv_item i where s.obi_item_id=i.item_id and i.item_minimum_quantity is not null "
                + " and (date(s.created_date) between '" + searchMisinventoryExpiry.getSearchFromExpdate() + "' and '"
                + searchMisinventoryExpiry.getSearchToExpdate() + "') ";
        // if (searchMisinventoryExpiry.getUnitId() != 0) {
        // queryInya += " and s.opening_balance_unit_id=" +
        // searchMisinventoryExpiry.getUnitId();
        // CuntQuery += " and s.opening_balance_unit_id=" +
        // searchMisinventoryExpiry.getUnitId();
        // }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryInya += " and s.opening_balance_unit_id in (" + values + ") ";
            CuntQuery += " and s.opening_balance_unit_id in  (" + values + ") ";
        }
        if (searchMisinventoryExpiry.getStoreId() != 0) {
            queryInya += " and s.obi_store_id=" + searchMisinventoryExpiry.getStoreId();
            CuntQuery += " and s.obi_store_id=" + searchMisinventoryExpiry.getStoreId();
        }
        queryInya += " order by s.obi_id desc  ";
        System.out.println(queryInya);
        // List<TinvOpeningBalanceItem> ItemListfinal = new
        // ArrayList<TinvOpeningBalanceItem>();
        // String CountQuery=StringUtils.replace(queryInya," * FR","
        // count(s.obiId) FR");
        System.out.println("Count:" + CuntQuery);
       /* bi1 = (BigInteger) entityManager.createNativeQuery(CuntQuery).getSingleResult();
        count = bi1.longValue();
        ItemList = entityManager.createNativeQuery(queryInya, TinvOpeningBalanceItem.class).getResultList();

        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;*/
        String columnName = "unitName,itemName,storeName,availableQty,minimumQty";
        jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, queryInya, CuntQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(searchMisinventoryExpiry.getCurrentUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(searchMisinventoryExpiry.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        if (jsonArray != null) {
            jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
            jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        }
        if (searchMisinventoryExpiry.isPrint()) {
            columnName = "unitName,itemName,storeName,availableQty,minimumQty";
            return createReport.generateExcel(columnName, "ItemMinimumQtyReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("ItemMinimumQtyReport", jsonArray.toString());
        }

    }

    @RequestMapping("getPharmacySalesReport/{today}/{fromdate}/{todate}/{unitList}/{storeList}/{limit}/{offset}")
    public List<pharmacysalesDTO> getPharmacySalesReport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("today") Boolean today, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable String[] unitList, @PathVariable String[] storeList, @PathVariable("limit") String limit, @PathVariable("offset") String offset) {
        TenantContext.setCurrentTenant(tenantName);
        List<pharmacysalesDTO> pharmacysalesDTOList = new ArrayList<pharmacysalesDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromdate == "" || fromdate.equals("null")) {
            fromdate = "1990-06-12";
        }
        if (todate == "" || todate.equals("null")) {
            todate = strDate;
        }
        List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
        long count = 0;
        try {
//                String query2="SELECT it.item_code,it.item_name,sum(i.psi_item_quantity) as consumeqty " +
//                        " FROM tinv_pharmacy_sale_item i left join inv_item it on " +
//                        " i.psi_item_id=it.item_id  " +
//                        " where (date(i.created_date)  between '"+fromdate+"' and '"+todate+"') " ;
            String query2 = " SELECT ifnull(it.item_code,'')as item_code,ifnull(it.item_name,'')as item_name," + "sum(i.psi_item_quantity) as consumeqty, " + " ifnull(u.unit_name,'')as unit_name,ifnull(st.store_name,'')as store_name " + " FROM tinv_pharmacy_sale_item i " + " left join inv_item it on  i.psi_item_id=it.item_id  " + " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " + " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " + " left join inv_store st on bi.obi_store_id=st.store_id ";
            String countQuery = "SELECT count(distinct(i.psi_item_id)) " + " FROM tinv_pharmacy_sale_item i " + " left join inv_item it on i.psi_item_id=it.item_id  " + " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " + " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " + " left join inv_store st on bi.obi_store_id=st.store_id ";
            // " where (date(i.created_date)  between '"+fromdate+"' and '"+todate+"') ";
            if (today) {
                query2 += " where date(i.created_date)=curdate()";
                countQuery += " where date(i.created_date)=curdate()";
            } else {
                query2 += " where (date(i.created_date)  between '" + fromdate + "' and '" + todate + "') ";
                countQuery += " where (date(i.created_date)  between '" + fromdate + "' and '" + todate + "') ";
            }
            if (!unitList[0].equals("null")) {
                String values = unitList[0];
                for (int i = 1; i < unitList.length; i++) {
                    values += "," + unitList[i];
                }
                query2 += " and i.pharmacy_unit_id in (" + values + ")";
                countQuery += " and i.pharmacy_unit_id in (" + values + ")";
            }
            if (!storeList[0].equals("null")) {
                String values = storeList[0];
                for (int i = 1; i < storeList.length; i++) {
                    values += "," + storeList[i];
                }
                query2 += " and it.item_id in (select distinct(op.obi_item_id) from  tinv_opening_balance_item op,inv_store s " + " where op.obi_store_id=s.store_id and s.store_id in (" + values + ") )";
                countQuery += " and it.item_id in (select distinct(op.obi_item_id) from  tinv_opening_balance_item op,inv_store s " + " where op.obi_store_id=s.store_id and s.store_id in (" + values + ") ) ";
            }
            query2 += " group by i.psi_item_id limit " + limit + " offset " + (Integer.parseInt(offset) - 1);
            System.out.println("Drug Consumption Report:" + query2);
            System.out.println("Count QUery:" + countQuery);
            finalDignosisCount = entityManager.createNativeQuery(query2).getResultList();
            BigInteger temp = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = temp.longValue();
            for (Object[] obj : finalDignosisCount) {
                pharmacysalesDTO objpharmacysalesDTO = new pharmacysalesDTO();
                objpharmacysalesDTO.setItem_name(obj[1].toString());
                objpharmacysalesDTO.setItem_code(obj[0].toString());
                objpharmacysalesDTO.setConsumeQty(obj[2].toString());
                objpharmacysalesDTO.setUnitName(obj[3].toString());
                objpharmacysalesDTO.setStoreName(obj[4].toString());
                objpharmacysalesDTO.setCount(count);
                pharmacysalesDTOList.add(objpharmacysalesDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pharmacysalesDTOList;
    }

    @RequestMapping("getPharmacySalesReportPrint/{today}/{fromdate}/{todate}/{unitList}/{storeList}/{limit}/{offset}/{print}/{unitId}")
    public String getPharmacySalesReportPrint(@RequestHeader("X-tenantId") String tenantName, @PathVariable("today") Boolean today, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable String[] unitList, @PathVariable String[] storeList, @PathVariable("limit") String limit, @PathVariable("offset") String offset, @PathVariable("print") boolean print, @PathVariable("unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        List<pharmacysalesDTO> pharmacysalesDTOList = new ArrayList<pharmacysalesDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromdate == "" || fromdate.equals("null")) {
            fromdate = "1990-06-12";
        }
        if (todate == "" || todate.equals("null")) {
            todate = strDate;
        }
        List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
        long count = 0;
        try {
//                String query2="SELECT it.item_code,it.item_name,sum(i.psi_item_quantity) as consumeqty " +
//                        " FROM tinv_pharmacy_sale_item i left join inv_item it on " +
//                        " i.psi_item_id=it.item_id  " +
//                        " where (date(i.created_date)  between '"+fromdate+"' and '"+todate+"') " ;
            String query2 = " SELECT ifnull(it.item_code,'')as item_code,ifnull(it.item_name,'')as item_name," + "sum(i.psi_item_quantity) as consumeqty, " + " ifnull(u.unit_name,'')as unit_name,ifnull(st.store_name,'')as store_name " + " FROM tinv_pharmacy_sale_item i " + " left join inv_item it on  i.psi_item_id=it.item_id  " + " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " + " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " + " left join inv_store st on bi.obi_store_id=st.store_id ";
            String countQuery = "SELECT count(distinct(i.psi_item_id)) " + " FROM tinv_pharmacy_sale_item i " + " left join inv_item it on i.psi_item_id=it.item_id  " + " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " + " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " + " left join inv_store st on bi.obi_store_id=st.store_id ";
            // " where (date(i.created_date)  between '"+fromdate+"' and '"+todate+"') ";
            if (today) {
                query2 += " where date(i.created_date)=curdate()";
                countQuery += " where date(i.created_date)=curdate()";
            } else {
                query2 += " where (date(i.created_date)  between '" + fromdate + "' and '" + todate + "') ";
                countQuery += " where (date(i.created_date)  between '" + fromdate + "' and '" + todate + "') ";
            }
            if (!unitList[0].equals("null")) {
                String values = unitList[0];
                for (int i = 1; i < unitList.length; i++) {
                    values += "," + unitList[i];
                }
                query2 += " and i.pharmacy_unit_id in (" + values + ")";
                countQuery += " and i.pharmacy_unit_id in (" + values + ")";
            }
            if (!storeList[0].equals("null")) {
                String values = storeList[0];
                for (int i = 1; i < storeList.length; i++) {
                    values += "," + storeList[i];
                }
                query2 += " and it.item_id in (select distinct(op.obi_item_id) from  tinv_opening_balance_item op,inv_store s " + " where op.obi_store_id=s.store_id and s.store_id in (" + values + ") )";
                countQuery += " and it.item_id in (select distinct(op.obi_item_id) from  tinv_opening_balance_item op,inv_store s " + " where op.obi_store_id=s.store_id and s.store_id in (" + values + ") ) ";
            }
            query2 += " group by i.psi_item_id ";
            System.out.println("Query sub:" + query2);
            System.out.println("Count QUery:" + countQuery);
            finalDignosisCount = entityManager.createNativeQuery(query2).getResultList();
            BigInteger temp = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = temp.longValue();
            for (Object[] obj : finalDignosisCount) {
                pharmacysalesDTO objpharmacysalesDTO = new pharmacysalesDTO();
                objpharmacysalesDTO.setItem_name(obj[1].toString());
                objpharmacysalesDTO.setItem_code(obj[0].toString());
                objpharmacysalesDTO.setConsumeQty(obj[2].toString());
                objpharmacysalesDTO.setUnitName(obj[3].toString());
                objpharmacysalesDTO.setStoreName(obj[4].toString());
                objpharmacysalesDTO.setCount(count);
                pharmacysalesDTOList.add(objpharmacysalesDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // return null;
        //  return pharmacysalesDTOList;
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(unitId);
        pharmacysalesDTOList.get(0).setHeaderObject(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(pharmacysalesDTOList);
        // return createReport.createJasperReportXLS("OutwardReferralReport", ds);
        if (print) {
            return createReport.createJasperReportXLS("DrugConsumptionReport", ds);
        } else {
            return createReport.createJasperReportPDF("DrugConsumptionReport", ds);
        }
    }

    //By Neha
    @RequestMapping("getPatientwisePharmacySalesReport/{today}/{fromdate}/{todate}/{unitList}/{storeList}/{IPDFlag}/{limit}/{offset}")
    public List<pharmacysalesDTO> getPharmacySalesReport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("today") Boolean today, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable String[] unitList, @PathVariable String[] storeList, @PathVariable("IPDFlag") int IPDFlag, @PathVariable("limit") String limit, @PathVariable("offset") String offset) {
        TenantContext.setCurrentTenant(tenantName);
        List<pharmacysalesDTO> pharmacysalesDTOList = new ArrayList<pharmacysalesDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromdate == "" || fromdate.equals("null")) {
            fromdate = "1990-06-12";
        }
        if (todate == "" || todate.equals("null")) {
            todate = strDate;
        }
        List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
        long count = 0;
        String query2 = " ";
        String countQuery = " ";
        try {
//                String query2="SELECT it.item_code,it.item_name,sum(i.psi_item_quantity) as consumeqty " +
//                        " FROM tinv_pharmacy_sale_item i left join inv_item it on " +
//                        " i.psi_item_id=it.item_id  " +
//                        " where (date(i.created_date)  between '"+fromdate+"' and '"+todate+"') " ;
            if (IPDFlag == 0) {
                query2 = " select drugconsmp.item_code,drugconsmp.item_name, " + " drugconsmp.consumeqty, drugconsmp.unit_name, " +
                        " drugconsmp.store_name, drugconsmp.first_name,drugconsmp.last_name" +
                        " from" +
                        " (SELECT us.user_firstname as first_name,us.user_lastname as last_name,ifnull(it.item_code,'')as item_code,ifnull(it.item_name,'')as item_name, " +
                        " sum(i.psi_item_quantity) as consumeqty, ifnull(u.unit_name,'')as unit_name, " +
                        " ifnull(st.store_name,'')as store_name " +
                        " FROM tinv_pharmacy_sale_item i " +
                        " inner join tinv_pharmacy_sale psl on  i.psi_ps_id=psl.ps_id " +
                        " inner join mst_visit v on psl.ps_visit_id=v.visit_id " +
                        " inner join mst_patient p on p.patient_id=v.visit_patient_id " +
                        " inner join mst_user us on p.patient_user_id=us.user_id " +
                        " left join inv_item it on i.psi_item_id=it.item_id " +
                        " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " +
                        " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " +
                        " left join inv_store st on bi.obi_store_id=st.store_id where psl.pharmacy_type=0 ";
                countQuery = " select COUNT(*)" +
                        " from" +
                        " (SELECT us.user_firstname as first_name,us.user_lastname as last_name,ifnull(it.item_code,'')as item_code,ifnull(it.item_name,'')as item_name, " +
                        " sum(i.psi_item_quantity) as consumeqty, ifnull(u.unit_name,'')as unit_name, " +
                        " ifnull(st.store_name,'')as store_name " +
                        " FROM tinv_pharmacy_sale_item i " +
                        " inner join tinv_pharmacy_sale psl on  i.psi_ps_id=psl.ps_id " +
                        " inner join mst_visit v on psl.ps_visit_id=v.visit_id " +
                        " inner join mst_patient p on p.patient_id=v.visit_patient_id " +
                        " inner join mst_user us on p.patient_user_id=us.user_id " +
                        " left join inv_item it on  i.psi_item_id=it.item_id " +
                        " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " +
                        " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " +
                        " left join inv_store st on bi.obi_store_id=st.store_id where psl.pharmacy_type=0 ";

            } else {
                query2 = " select drugconsmp.item_code,drugconsmp.item_name, " +
                        " drugconsmp.consumeqty,drugconsmp.unit_name,drugconsmp.store_name,drugconsmp.firstname,drugconsmp.lastname " +
                        " from " +
                        " (SELECT us.user_firstname as firstname,us.user_lastname as lastname,ifnull(it.item_code,'')as item_code,ifnull(it.item_name,'')as item_name, " +
                        " sum(i.psi_item_quantity) as consumeqty, ifnull(u.unit_name,'')as unit_name, " +
                        " ifnull(st.store_name,'')as store_name " +
                        " FROM tinv_pharmacy_sale_item i " +
                        " inner join tinv_pharmacy_sale psl on i.psi_ps_id=psl.ps_id " +
                        " inner join trn_admission ad on psl.ps_admission_id=ad.admission_id " +
                        " inner join mst_patient p on p.patient_id=ad.admission_patient_id " +
                        " inner join mst_user us on p.patient_user_id=us.user_id " +
                        " left join inv_item it on i.psi_item_id=it.item_id " +
                        " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " +
                        " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " +
                        " left join inv_store st on bi.obi_store_id=st.store_id where psl.pharmacy_type=1 ";
                countQuery = " select count(*) " +
                        " from " +
                        " (SELECT us.user_firstname as firstname,us.user_lastname as lastname,ifnull(it.item_code,'')as item_code,ifnull(it.item_name,'')as item_name, " +
                        " sum(i.psi_item_quantity) as consumeqty, ifnull(u.unit_name,'')as unit_name, " +
                        " ifnull(st.store_name,'')as store_name " +
                        " FROM tinv_pharmacy_sale_item i " +
                        " inner join tinv_pharmacy_sale psl on i.psi_ps_id=psl.ps_id " +
                        " inner join trn_admission ad on psl.ps_admission_id=ad.admission_id " +
                        " inner join mst_patient p on p.patient_id=ad.admission_patient_id " +
                        " inner join mst_user us on p.patient_user_id=us.user_id " +
                        " left join inv_item it on i.psi_item_id=it.item_id " +
                        " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " +
                        " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " +
                        " left join inv_store st on bi.obi_store_id=st.store_id where psl.pharmacy_type=1 ";
            }
            // " where (date(i.created_date)  between '"+fromdate+"' and '"+todate+"') ";
            if (today) {
                query2 += " and date(i.created_date)=curdate()";
                countQuery += " and date(i.created_date)=curdate()";
            } else {
                query2 += " and (date(i.created_date)  between '" + fromdate + "' and '" + todate + "') ";
                countQuery += " and (date(i.created_date)  between '" + fromdate + "' and '" + todate + "') ";
            }
            if (!unitList[0].equals("null")) {
                String values = unitList[0];
                for (int i = 1; i < unitList.length; i++) {
                    values += "," + unitList[i];
                }
                query2 += " and i.pharmacy_unit_id in (" + values + ")";
                countQuery += " and i.pharmacy_unit_id in (" + values + ")";
            }
            if (!storeList[0].equals("null")) {
                String values = storeList[0];
                for (int i = 1; i < storeList.length; i++) {
                    values += "," + storeList[i];
                }
                query2 += " and it.item_id in (select distinct(op.obi_item_id) from  tinv_opening_balance_item op,inv_store s " + " where op.obi_store_id=s.store_id and s.store_id in (" + values + ") ) and  bi.obi_store_id in (" + values + ") ";
                countQuery += " and it.item_id in (select distinct(op.obi_item_id) from  tinv_opening_balance_item op,inv_store s " + " where op.obi_store_id=s.store_id and s.store_id in (" + values + ") ) and  bi.obi_store_id in (" + values + ") ";
            }
            query2 += " group by i.psi_item_id,p.patient_id) as drugconsmp where drugconsmp.consumeqty!=0 limit " + (Integer.parseInt(limit) * (Integer.parseInt(offset) - 1)) + " , " + limit + " ";
            //   query2 += " group by i.psi_item_id,p.patient_id) as drugconsmp where drugconsmp.consumeqty!=0 limit " + limit + " offset " + (Integer.parseInt(offset) - 1) + " ";
            System.out.println("Patient-Wise Drug Consumption Report" + query2);
            countQuery += " group by i.psi_item_id,p.patient_id) as drugconsmp where drugconsmp.consumeqty!=0 ";
            System.out.println("Query sub:" + query2);
            System.out.println("Count QUery:" + countQuery);
            finalDignosisCount = entityManager.createNativeQuery(query2).getResultList();
            BigInteger temp = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = temp.longValue();
            for (Object[] obj : finalDignosisCount) {
                pharmacysalesDTO objpharmacysalesDTO = new pharmacysalesDTO();
                objpharmacysalesDTO.setItem_name(obj[1].toString());
                objpharmacysalesDTO.setItem_code(obj[0].toString());
                objpharmacysalesDTO.setConsumeQty(obj[2].toString());
                objpharmacysalesDTO.setUnitName(obj[3].toString());
                objpharmacysalesDTO.setStoreName(obj[4].toString());
                objpharmacysalesDTO.setFirstname(obj[5].toString());
                objpharmacysalesDTO.setLastname(obj[6].toString());
                objpharmacysalesDTO.setCount(count);
                pharmacysalesDTOList.add(objpharmacysalesDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pharmacysalesDTOList;
    }

    @RequestMapping("getPatientwisePharmacySalesReportPrint/{today}/{fromdate}/{todate}/{unitList}/{storeList}/{IPDFlag}/{limit}/{offset}/{print}/{unitId}")
    public String getPharmacySalesReportPrint(@RequestHeader("X-tenantId") String tenantName, @PathVariable("today") Boolean today, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable String[] unitList, @PathVariable String[] storeList, @PathVariable("IPDFlag") int IPDFlag, @PathVariable("limit") String limit, @PathVariable("offset") String offset, @PathVariable("print") boolean print, @PathVariable("unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        List<pharmacysalesDTO> pharmacysalesDTOList = new ArrayList<pharmacysalesDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromdate == "" || fromdate.equals("null")) {
            fromdate = "1990-06-12";
        }
        if (todate == "" || todate.equals("null")) {
            todate = strDate;
        }
        List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
        long count = 0;
        String query2 = " ";
        String countQuery = " ";
        try {
//                String query2="SELECT it.item_code,it.item_name,sum(i.psi_item_quantity) as consumeqty " +
//                        " FROM tinv_pharmacy_sale_item i left join inv_item it on " +
//                        " i.psi_item_id=it.item_id  " +
//                        " where (date(i.created_date)  between '"+fromdate+"' and '"+todate+"') " ;
            if (IPDFlag == 0) {
                query2 = " select drugconsmp.item_code,drugconsmp.item_name, " + " drugconsmp.consumeqty, drugconsmp.unit_name, " +
                        " drugconsmp.store_name, drugconsmp.first_name,drugconsmp.last_name" +
                        " from" +
                        " (SELECT us.user_firstname as first_name,us.user_lastname as last_name,ifnull(it.item_code,'')as item_code,ifnull(it.item_name,'')as item_name, " +
                        " sum(i.psi_item_quantity) as consumeqty, ifnull(u.unit_name,'')as unit_name, " +
                        " ifnull(st.store_name,'')as store_name " +
                        " FROM tinv_pharmacy_sale_item i " +
                        " inner join tinv_pharmacy_sale psl on  i.psi_ps_id=psl.ps_id " +
                        " inner join mst_visit v on psl.ps_visit_id=v.visit_id " +
                        " inner join mst_patient p on p.patient_id=v.visit_patient_id " +
                        " inner join mst_user us on p.patient_user_id=us.user_id " +
                        " left join inv_item it on i.psi_item_id=it.item_id " +
                        " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " +
                        " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " +
                        " left join inv_store st on bi.obi_store_id=st.store_id where psl.pharmacy_type=0 ";
                countQuery = " select COUNT(*)" +
                        " from" +
                        " (SELECT us.user_firstname as first_name,us.user_lastname as last_name,ifnull(it.item_code,'')as item_code,ifnull(it.item_name,'')as item_name, " +
                        " sum(i.psi_item_quantity) as consumeqty, ifnull(u.unit_name,'')as unit_name, " +
                        " ifnull(st.store_name,'')as store_name " +
                        " FROM tinv_pharmacy_sale_item i " +
                        " inner join tinv_pharmacy_sale psl on  i.psi_ps_id=psl.ps_id " +
                        " inner join mst_visit v on psl.ps_visit_id=v.visit_id " +
                        " inner join mst_patient p on p.patient_id=v.visit_patient_id " +
                        " inner join mst_user us on p.patient_user_id=us.user_id " +
                        " left join inv_item it on  i.psi_item_id=it.item_id " +
                        " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " +
                        " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " +
                        " left join inv_store st on bi.obi_store_id=st.store_id where psl.pharmacy_type=0 ";

            } else {
                query2 = " select drugconsmp.item_code,drugconsmp.item_name, " +
                        " drugconsmp.consumeqty,drugconsmp.unit_name,drugconsmp.store_name,drugconsmp.firstname,drugconsmp.lastname " +
                        " from " +
                        " (SELECT us.user_firstname as firstname,us.user_lastname as lastname,ifnull(it.item_code,'')as item_code,ifnull(it.item_name,'')as item_name, " +
                        " sum(i.psi_item_quantity) as consumeqty, ifnull(u.unit_name,'')as unit_name, " +
                        " ifnull(st.store_name,'')as store_name " +
                        " FROM tinv_pharmacy_sale_item i " +
                        " inner join tinv_pharmacy_sale psl on i.psi_ps_id=psl.ps_id " +
                        " inner join trn_admission ad on psl.ps_admission_id=ad.admission_id " +
                        " inner join mst_patient p on p.patient_id=ad.admission_patient_id " +
                        " inner join mst_user us on p.patient_user_id=us.user_id " +
                        " left join inv_item it on i.psi_item_id=it.item_id " +
                        " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " +
                        " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " +
                        " left join inv_store st on bi.obi_store_id=st.store_id where psl.pharmacy_type=1 ";
                countQuery = " select count(*) " +
                        " from " +
                        " (SELECT us.user_firstname as firstname,us.user_lastname as lastname,ifnull(it.item_code,'')as item_code,ifnull(it.item_name,'')as item_name, " +
                        " sum(i.psi_item_quantity) as consumeqty, ifnull(u.unit_name,'')as unit_name, " +
                        " ifnull(st.store_name,'')as store_name " +
                        " FROM tinv_pharmacy_sale_item i " +
                        " inner join tinv_pharmacy_sale psl on i.psi_ps_id=psl.ps_id " +
                        " inner join trn_admission ad on psl.ps_admission_id=ad.admission_id " +
                        " inner join mst_patient p on p.patient_id=ad.admission_patient_id " +
                        " inner join mst_user us on p.patient_user_id=us.user_id " +
                        " left join inv_item it on i.psi_item_id=it.item_id " +
                        " left join mst_unit u on i.pharmacy_unit_id=u.unit_id " +
                        " left join tinv_opening_balance_item bi on i.psi_obi_id=bi.obi_id " +
                        " left join inv_store st on bi.obi_store_id=st.store_id where psl.pharmacy_type=1 ";
            }
            // " where (date(i.created_date)  between '"+fromdate+"' and '"+todate+"') ";
            if (today) {
                query2 += " and date(i.created_date)=curdate()";
                countQuery += " and date(i.created_date)=curdate()";
            } else {
                query2 += " and (date(i.created_date)  between '" + fromdate + "' and '" + todate + "') ";
                countQuery += " and (date(i.created_date)  between '" + fromdate + "' and '" + todate + "') ";
            }
            if (!unitList[0].equals("null")) {
                String values = unitList[0];
                for (int i = 1; i < unitList.length; i++) {
                    values += "," + unitList[i];
                }
                query2 += " and i.pharmacy_unit_id in (" + values + ")";
                countQuery += " and i.pharmacy_unit_id in (" + values + ")";
            }
            if (!storeList[0].equals("null")) {
                String values = storeList[0];
                for (int i = 1; i < storeList.length; i++) {
                    values += "," + storeList[i];
                }
                query2 += " and it.item_id in (select distinct(op.obi_item_id) from  tinv_opening_balance_item op,inv_store s " + " where op.obi_store_id=s.store_id and s.store_id in (" + values + ") ) and  bi.obi_store_id in (" + values + ") ";
                countQuery += " and it.item_id in (select distinct(op.obi_item_id) from  tinv_opening_balance_item op,inv_store s " + " where op.obi_store_id=s.store_id and s.store_id in (" + values + ") ) and  bi.obi_store_id in (" + values + ") ";
            }
            //   query2 += " group by i.psi_item_id,p.patient_id) as drugconsmp where drugconsmp.consumeqty!=0 limit " + (Integer.parseInt(limit) * (Integer.parseInt(offset) - 1)) + " , " + limit + " ";
            query2 += " group by i.psi_item_id,p.patient_id) as drugconsmp where drugconsmp.consumeqty!=0";
            //   query2 += " group by i.psi_item_id,p.patient_id) as drugconsmp where drugconsmp.consumeqty!=0 limit " + limit + " offset " + (Integer.parseInt(offset) - 1) + " ";
            countQuery += " group by i.psi_item_id,p.patient_id) as drugconsmp where drugconsmp.consumeqty!=0 ";
            System.out.println("Query sub:" + query2);
            System.out.println("Count QUery:" + countQuery);
            finalDignosisCount = entityManager.createNativeQuery(query2).getResultList();
            BigInteger temp = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = temp.longValue();
            for (Object[] obj : finalDignosisCount) {
                pharmacysalesDTO objpharmacysalesDTO = new pharmacysalesDTO();
                objpharmacysalesDTO.setItem_name(obj[1].toString());
                objpharmacysalesDTO.setItem_code(obj[0].toString());
                objpharmacysalesDTO.setConsumeQty(obj[2].toString());
                objpharmacysalesDTO.setUnitName(obj[3].toString());
                objpharmacysalesDTO.setStoreName(obj[4].toString());
                objpharmacysalesDTO.setFirstname(obj[5].toString());
                objpharmacysalesDTO.setLastname(obj[6].toString());
                objpharmacysalesDTO.setCount(count);
                pharmacysalesDTOList.add(objpharmacysalesDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(unitId);
        pharmacysalesDTOList.get(0).setHeaderObject(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(pharmacysalesDTOList);
        // return createReport.createJasperReportXLS("OutwardReferralReport", ds);
        if (print) {
            return createReport.createJasperReportXLS("PatientwiseDrugConsumptionReport", ds);
        } else {
            return createReport.createJasperReportPDF("PatientwiseDrugConsumptionReport", ds);
        }
        //  return pharmacysalesDTOList;
    }

    @RequestMapping("getMisServiceCountReport/{fromdate}/{todate}/{todaydate}/{staffId}/{unitList}/{serviceType}/{limit}/{offset}")
    public List<MisservicecountDTO> getMisServiceCountReport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("todaydate") boolean todaydate, @PathVariable("staffId") long staffId, @PathVariable String[] unitList, @PathVariable("serviceType") int serviceType, @PathVariable("limit") String limit, @PathVariable("offset") String offset) {
        TenantContext.setCurrentTenant(tenantName);
//        String query2 = "SELECT s.service_code,s.service_name,count(p.bs_service_id) as countm FROM mbill_service s " +
//                "left join tbill_bill_service p on p.bs_service_id=s.service_id left join tbill_bill tb on p.bs_bill_id=tb.bill_id " +
//                " where  s.service_id " +
//                " in (SELECT distinct(r.bs_service_id)  " +
//                " FROM tbill_bill_service r) ";
        String query2 = " SELECT u.unit_name,s.service_code,s.service_name, " + " count(p.bs_service_id) as countm ,concat(us.user_firstname,' ',us.user_lastname)as StaffName " + " FROM mbill_service s " + " left join tbill_bill_service p on p.bs_service_id=s.service_id " + " left join mst_staff sf on p.bs_staff_id=sf.staff_id " + " left join mst_user us on sf.staff_user_id=us.user_id " + " left join tbill_bill tb on p.bs_bill_id=tb.bill_id " + " left join mst_unit u on tb.tbill_unit_id=u.unit_id " + " where  s.service_id  in (SELECT distinct(r.bs_service_id) " + " FROM tbill_bill_service r) ";
        String countquery = "SELECT count(distinct(p.bs_service_id)) " + " FROM tbill_bill_service p " + " left join mst_staff sf on p.bs_staff_id=sf.staff_id " + " left join mst_user us on sf.staff_user_id=us.user_id " + " left join tbill_bill tb on p.bs_bill_id=tb.bill_id " + " left join mst_unit u on tb.tbill_unit_id=u.unit_id " + " where ";
//        String countquery = "SELECT count(distinct(p.bs_service_id)) " +
//                " FROM tbill_bill_service p where ";
        List<MisservicecountDTO> misservicecountDTOList = new ArrayList<MisservicecountDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (serviceType == 1) {
            query2 += "  and tb.ipd_bill=1 ";
            countquery += " tb.ipd_bill=1 and ";
        } else {
            query2 += "and  tb.ipd_bill=0  ";
            countquery += " tb.ipd_bill=0 and ";
        }
        if (fromdate == "" || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate == "" || todate.equals("null")) {
            todate = strDate;
        }
        if (todaydate == true) {
            query2 += " and date(p.created_date)='" + strDate + "' ";
            countquery += " date(p.created_date)='" + strDate + "' ";

        } else {
            query2 += " and (date(p.created_date) between '" + fromdate + "' and '" + todate + "') ";
            countquery += " (date(p.created_date) between '" + fromdate + "' and '" + todate + "') ";
        }
        if (staffId != 0) {
            query2 += "and p.bs_staff_id=" + staffId + " ";
            countquery += " and p.bs_staff_id=" + staffId + " ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and tb.tbill_unit_id in (" + values + ")";
            countquery += " and tb.tbill_unit_id in (" + values + ")";
        }
//        if (unitId != 0) {
//            query2 += "and tb.tbill_unit_id=" + unitId + " ";
//            countquery += " and tb.tbill_unit_id=" + unitId + " ";
//        }
        List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
        query2 += " group by p.bs_service_id limit " + limit + " offset " + (Integer.parseInt(offset) - 1) + " ";
        System.out.println("Service Count Report:" + query2);
        finalDignosisCount = entityManager.createNativeQuery(query2).getResultList();
        System.out.println("count query:" + countquery);
        BigInteger temp = (BigInteger) entityManager.createNativeQuery(countquery).getSingleResult();
        long count = temp.longValue();
        for (Object[] obj : finalDignosisCount) {
            MisservicecountDTO objmisservicecountDTO = new MisservicecountDTO();
            objmisservicecountDTO.setUnit_name(obj[0].toString());
            objmisservicecountDTO.setService_code(obj[1].toString());
            objmisservicecountDTO.setService_name(obj[2].toString());
            objmisservicecountDTO.setCountData(obj[3].toString());
            objmisservicecountDTO.setStaff_name(obj[4].toString());
            objmisservicecountDTO.setCount(count);
            misservicecountDTOList.add(objmisservicecountDTO);
        }
        return misservicecountDTOList;
    }

    @RequestMapping("getMisServiceCountReportPrint/{fromdate}/{todate}/{todaydate}/{staffId}/{unitList}/{serviceType}/{limit}/{offset}/{print}/{unitId}/{userId}")
    public String getMisServiceCountReportPrint(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("todaydate") boolean todaydate, @PathVariable("staffId") long staffId, @PathVariable String[] unitList, @PathVariable("serviceType") int serviceType, @PathVariable("limit") String limit, @PathVariable("offset") String offset, @PathVariable("print") boolean print, @PathVariable("unitId") long unitId, @PathVariable("userId") long userId) {
        TenantContext.setCurrentTenant(tenantName);
//        String query2 = "SELECT s.service_code,s.service_name,count(p.bs_service_id) as countm FROM mbill_service s " +
//                "left join tbill_bill_service p on p.bs_service_id=s.service_id left join tbill_bill tb on p.bs_bill_id=tb.bill_id " +
//                " where  s.service_id " +
//                " in (SELECT distinct(r.bs_service_id)  " +
//                " FROM tbill_bill_service r) ";
        String query2 = " SELECT u.unit_name,s.service_code,s.service_name, " + " count(p.bs_service_id) as countm ,concat(ifnull(us.user_firstname,''),' ',ifnull(us.user_lastname,''))as StaffName " + " FROM mbill_service s " + " left join tbill_bill_service p on p.bs_service_id=s.service_id " + " left join mst_staff sf on p.bs_staff_id=sf.staff_id " + " left join mst_user us on sf.staff_user_id=us.user_id " + " left join tbill_bill tb on p.bs_bill_id=tb.bill_id " + " left join mst_unit u on tb.tbill_unit_id=u.unit_id " + " where  s.service_id  in (SELECT distinct(r.bs_service_id) " + " FROM tbill_bill_service r) ";
        String countquery = "SELECT count(distinct(p.bs_service_id)) " + " FROM tbill_bill_service p " + " left join mst_staff sf on p.bs_staff_id=sf.staff_id " + " left join mst_user us on sf.staff_user_id=us.user_id " + " left join tbill_bill tb on p.bs_bill_id=tb.bill_id " + " left join mst_unit u on tb.tbill_unit_id=u.unit_id " + " where ";
//        String countquery = "SELECT count(distinct(p.bs_service_id)) " +
//                " FROM tbill_bill_service p where ";
        List<MisservicecountDTO> misservicecountDTOList = new ArrayList<MisservicecountDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (serviceType == 1) {
            query2 += "  and tb.ipd_bill=1 ";
            countquery += " tb.ipd_bill=1 and ";
        } else {
            query2 += "and  tb.ipd_bill=0  ";
            countquery += " tb.ipd_bill=0  ";
        }
        if (fromdate == "" || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate == "" || todate.equals("null")) {
            todate = strDate;
        }
        if (todaydate == true) {
            query2 += " and (date(p.created_date)=curdate()) ";
            countquery += " and (date(p.created_date)= curdate())";

        } else {
            query2 += " and (date(p.created_date) between '" + fromdate + "' and '" + todate + "') ";
            countquery += " and (date(p.created_date) between '" + fromdate + "' and '" + todate + "') ";
        }
        if (staffId != 0) {
            query2 += "and p.bs_staff_id=" + staffId + " ";
            countquery += " and p.bs_staff_id=" + staffId + " ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and tb.tbill_unit_id in (" + values + ")";
            countquery += " and tb.tbill_unit_id in (" + values + ")";
        }
//        if (unitId != 0) {
//            query2 += "and tb.tbill_unit_id=" + unitId + " ";
//            countquery += " and tb.tbill_unit_id=" + unitId + " ";
//        }
        List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
        query2 += " group by p.bs_service_id ";
        System.out.println("Query:" + query2);
        finalDignosisCount = entityManager.createNativeQuery(query2).getResultList();
        System.out.println("count query:" + countquery);
        BigInteger temp = (BigInteger) entityManager.createNativeQuery(countquery).getSingleResult();
        long count = temp.longValue();
        for (Object[] obj : finalDignosisCount) {
            MisservicecountDTO objmisservicecountDTO = new MisservicecountDTO();
            objmisservicecountDTO.setUnit_name(obj[0].toString());
            objmisservicecountDTO.setService_code(obj[1].toString());
            objmisservicecountDTO.setService_name(obj[2].toString());
            objmisservicecountDTO.setCountData(obj[3].toString());
            objmisservicecountDTO.setStaff_name(obj[4].toString());
            objmisservicecountDTO.setCount(count);
            misservicecountDTOList.add(objmisservicecountDTO);
        }
        //  return misservicecountDTOList;
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(unitId);
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(userId);
        misservicecountDTOList.get(0).setObjHeaderData(HeaderObject);
        misservicecountDTOList.get(0).setHeaderObjectUser(HeaderObjectUser);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(misservicecountDTOList);
        // return createReport.createJasperReportXLS("OutwardReferralReport", ds);
        if (print) {
            return createReport.createJasperReportXLS("ServiceCountReport", ds);
        } else {
            return createReport.createJasperReportPDF("ServiceCountReport", ds);
        }
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getitemmovementReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchItemMovement(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisItemMovementSearchDTO misitemMovementSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<MisItemMovementListDTO> misitemmovementlistDTOList = new ArrayList<MisItemMovementListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT 'Issue' as TrasType,ifnull(un.unit_name,'')as unit_name,date(ic.ic_Date)as IssueDate ,ifnull(i.item_name,'')as itemname, " + " ifnull(ct.ic_name,'')as Itemcategory ,ifnull(fs.store_name,'')as Fromstore , " + " ifnull(ts.store_name,'')as ToStore ,ifnull(ti.ici_issue_quantity,0) as issueQuantity,ifnull(ic.ic_indent_no,0) as indentno, " + " ifnull(us.user_fullname,'')as username " + " FROM tinv_issue_clinic_item ti " + " left join tinv_opening_balance_item ob on ti.ici_item_id=ob.obi_id " + " left join inv_item i on ob.obi_item_id=i.item_id " + " left join tinv_issue_clinic ic on ti.ici_ic_id=ic.ic_id " + " left join inv_store fs on ic.ic_from_store_id=fs.store_id " + " left join inv_store ts on ic.ic_to_store_id=ts.store_id " + " left join inv_item_category ct on i.item_tc_id=ct.ic_id " + " left join mst_unit un on ic.issue_clinic_unit_id=un.unit_id " + " left join mst_staff st on ic.ic_staffd=st.staff_id " + " left join mst_user us on st.staff_user_id=us.user_id ";
        String Query1 = " SELECT  'Return' as TrasType,ifnull(un.unit_name,'')as unit_name,date(ri.ri_issue_date)as IssueDate ,ifnull(i.item_name,'')as itemname, " + " ifnull(ct.ic_name,'')as Itemcategory ,ifnull(fs.store_name,'')as Fromstore , " + " ifnull(ts.store_name,'')as ToStore ,ifnull(ti.ici_return_quantity,0)as returnquantity,ifnull(ic.ic_indent_no,0)as indentno , " + " ifnull(us.user_fullname,'')as username " + " FROM tinv_issue_clinic_item ti " + " left join tinv_opening_balance_item ob on ti.ici_item_id=ob.obi_id " + " left join inv_item i on ob.obi_item_id=i.item_id " + " left join tinv_issue_clinic ic on ti.ici_ic_id=ic.ic_id " + " left join tinv_receive_issue ri on ti.ici_ri_id=ri.ri_id " + " left join inv_store fs on ri.ri_from_store_id=fs.store_id " + " left join inv_store ts on ri.ri_reciving_store_id=ts.store_id " + " left join inv_item_category ct on i.item_tc_id=ct.ic_id " + " left join mst_unit un on ri.ri_unit_id=un.unit_id " + " left join mst_staff st on ic.ic_staffd=st.staff_id " + " left join mst_user us on st.staff_user_id=us.user_id " + " where ti.ici_return_quantity!=0 ";
        String CountQuery = "";
        String columnName = "";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misitemMovementSearchDTO.getTodaydate()) {
            Query += " where (date(ti.created_date)=curdate()) ";
            Query1 += " AND (date(ti.created_date)=curdate()) ";
        } else {
            Query += " where (date(ti.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query1 += " AND (date(ti.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and un.unit_id in (" + values + ") ";
            Query1 += " and un.unit_id in (" + values + ") ";
        }
        if (misitemMovementSearchDTO.getItemName() != null && !misitemMovementSearchDTO.getItemName().equals("")) {
            Query += " and  i.item_name like '%" + misitemMovementSearchDTO.getItemName() + "%' ";
            Query1 += " and  i.item_name like '%" + misitemMovementSearchDTO.getItemName() + "%' ";
        }
        if (!String.valueOf(misitemMovementSearchDTO.getIcId()).equals("0") && !String.valueOf(misitemMovementSearchDTO.getIcId()).equals("null")) {
            Query += " and i.item_tc_id = " + misitemMovementSearchDTO.getIcId() + " ";
            Query1 += " and i.item_tc_id = " + misitemMovementSearchDTO.getIcId() + " ";
        }
        if (!String.valueOf(misitemMovementSearchDTO.getFromstoreId()).equals("0") && !String.valueOf(misitemMovementSearchDTO.getFromstoreId()).equals("null")) {
            Query += " and ic.ic_from_store_id= " + misitemMovementSearchDTO.getFromstoreId() + " ";
            Query1 += " and ri.ri_from_store_id= " + misitemMovementSearchDTO.getTostoreId() + " ";
        }
        if (!String.valueOf(misitemMovementSearchDTO.getTostoreId()).equals("0") && !String.valueOf(misitemMovementSearchDTO.getTostoreId()).equals("null")) {
            Query += " and ic.ic_to_store_id = " + misitemMovementSearchDTO.getTostoreId() + " ";
            Query1 += " and ri.ri_reciving_store_id =" + misitemMovementSearchDTO.getFromstoreId() + " ";
        }
        String MainQuery = " select combine.TrasType ,combine.unit_name ,combine.IssueDate , " + " combine.itemname ,combine.Itemcategory ,combine.Fromstore,combine.ToStore, " + " combine.issueQuantity,combine.indentno ,combine.username from " + "( " + Query + " UNION " + Query1 + " ) as combine ";
        System.out.println("Item Movement Report " + MainQuery );
        CountQuery = " select count(*) from ( " + Query + " UNION " + Query1 + " ) as combine ";
        columnName = "TrasType,unit_name,IssueDate,itemname,Itemcategory,Fromstore,ToStore,issueQuantity,indentno,username";
        MainQuery += " limit " + ((misitemMovementSearchDTO.getOffset() - 1) * misitemMovementSearchDTO.getLimit()) + "," + misitemMovementSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));

    }

    @RequestMapping("getitemmovementReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchItemMovementPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisItemMovementSearchDTO misitemMovementSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<MisItemMovementListDTO> misitemmovementlistDTOList = new ArrayList<MisItemMovementListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT 'Issue' as TrasType,ifnull(un.unit_name,'')as unit_name,date(ic.ic_Date)as IssueDate ,ifnull(i.item_name,'')as itemname, " + " ifnull(ct.ic_name,'')as Itemcategory ,ifnull(fs.store_name,'')as Fromstore , " + " ifnull(ts.store_name,'')as ToStore ,ifnull(ti.ici_issue_quantity,0) as issueQuantity,ifnull(ic.ic_indent_no,0) as indentno, " + " ifnull(us.user_fullname,'')as username " + " FROM tinv_issue_clinic_item ti " + " left join tinv_opening_balance_item ob on ti.ici_item_id=ob.obi_id " + " left join inv_item i on ob.obi_item_id=i.item_id " + " left join tinv_issue_clinic ic on ti.ici_ic_id=ic.ic_id " + " left join inv_store fs on ic.ic_from_store_id=fs.store_id " + " left join inv_store ts on ic.ic_to_store_id=ts.store_id " + " left join inv_item_category ct on i.item_tc_id=ct.ic_id " + " left join mst_unit un on ic.issue_clinic_unit_id=un.unit_id " + " left join mst_staff st on ic.ic_staffd=st.staff_id " + " left join mst_user us on st.staff_user_id=us.user_id ";
        String Query1 = " SELECT  'Return' as TrasType,ifnull(un.unit_name,'')as unit_name,date(ri.ri_issue_date)as IssueDate ,ifnull(i.item_name,'')as itemname, " + " ifnull(ct.ic_name,'')as Itemcategory ,ifnull(fs.store_name,'')as Fromstore , " + " ifnull(ts.store_name,'')as ToStore ,ifnull(ti.ici_return_quantity,0)as returnquantity,ifnull(ic.ic_indent_no,0)as indentno , " + " ifnull(us.user_fullname,'')as username " + " FROM tinv_issue_clinic_item ti " + " left join tinv_opening_balance_item ob on ti.ici_item_id=ob.obi_id " + " left join inv_item i on ob.obi_item_id=i.item_id " + " left join tinv_issue_clinic ic on ti.ici_ic_id=ic.ic_id " + " left join tinv_receive_issue ri on ti.ici_ri_id=ri.ri_id " + " left join inv_store fs on ri.ri_from_store_id=fs.store_id " + " left join inv_store ts on ri.ri_reciving_store_id=ts.store_id " + " left join inv_item_category ct on i.item_tc_id=ct.ic_id " + " left join mst_unit un on ri.ri_unit_id=un.unit_id " + " left join mst_staff st on ic.ic_staffd=st.staff_id " + " left join mst_user us on st.staff_user_id=us.user_id " + " where ti.ici_return_quantity!=0 ";
        String CountQuery = "";
        String columnName = "";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (misitemMovementSearchDTO.getTodaydate()) {
            Query += " where (date(ti.created_date)=curdate()) ";
            Query1 += " AND (date(ti.created_date)=curdate()) ";
        } else {
            Query += " where (date(ti.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query1 += " AND (date(ti.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and un.unit_id in (" + values + ") ";
            Query1 += " and un.unit_id in (" + values + ") ";
        }
        if (misitemMovementSearchDTO.getItemName() != null && !misitemMovementSearchDTO.getItemName().equals("")) {
            Query += " and  i.item_name like '%" + misitemMovementSearchDTO.getItemName() + "%' ";
            Query1 += " and  i.item_name like '%" + misitemMovementSearchDTO.getItemName() + "%' ";
        }
        if (!String.valueOf(misitemMovementSearchDTO.getIcId()).equals("0") && !String.valueOf(misitemMovementSearchDTO.getIcId()).equals("null")) {
            Query += " and i.item_tc_id = " + misitemMovementSearchDTO.getIcId() + " ";
            Query1 += " and i.item_tc_id = " + misitemMovementSearchDTO.getIcId() + " ";
        }
        if (!String.valueOf(misitemMovementSearchDTO.getFromstoreId()).equals("0") && !String.valueOf(misitemMovementSearchDTO.getFromstoreId()).equals("null")) {
            Query += " and ic.ic_from_store_id= " + misitemMovementSearchDTO.getFromstoreId() + " ";
            Query1 += " and ri.ri_from_store_id= " + misitemMovementSearchDTO.getTostoreId() + " ";
        }
        if (!String.valueOf(misitemMovementSearchDTO.getTostoreId()).equals("0") && !String.valueOf(misitemMovementSearchDTO.getTostoreId()).equals("null")) {
            Query += " and ic.ic_to_store_id = " + misitemMovementSearchDTO.getTostoreId() + " ";
            Query1 += " and ri.ri_reciving_store_id =" + misitemMovementSearchDTO.getFromstoreId() + " ";
        }
        String MainQuery = " select combine.TrasType ,combine.unit_name ,combine.IssueDate , " + " combine.itemname ,combine.Itemcategory ,combine.Fromstore,combine.ToStore, " + " combine.issueQuantity,combine.indentno ,combine.username from " + "( " + Query + " UNION " + Query1 + " ) as combine ";
        CountQuery = " select count(*) from ( " + Query + " UNION " + Query1 + " ) as combine ";
        columnName = "TrasType,unit_name,IssueDate,itemname,Itemcategory,Fromstore,ToStore,issueQuantity,indentno,username";
        MainQuery += " limit " + ((misitemMovementSearchDTO.getOffset() - 1) * misitemMovementSearchDTO.getLimit()) + "," + misitemMovementSearchDTO.getLimit();
        //return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misitemMovementSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(misitemMovementSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (misitemMovementSearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("ItemMovementReport", jsonArray.toString());
        } else {
            // return ResponseEntity.ok(jsonArray.toString());
            return createReport.createJasperReportPDFByJson("ItemMovementReport", jsonArray.toString());
        }

    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getitemwisepurchaseReport/{unitList}/{supplierId}/{fromdate}/{todate}/{itemName}")
    public ResponseEntity searchItemWisePurchase(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisItemwisePurchaseSearchDTO misitemwisepurchaseSearchDTO, @PathVariable String[] unitList, @PathVariable String supplierId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable String itemName) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
//        List<MisitemwisePurchaseListDTO> misitemwisepurchaseListDTOList = new ArrayList<MisitemwisePurchaseListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name,ii.item_name,po.ppo_id as PO_NO,date(po.created_date) as " + " PO_Date,pi.ppot_item_quantity as Po_Qty, " + " pi.ppot_item_rate ,pi.ppot_item_rate_per_qty as Mrp,ss.supplier_name " + " FROM  tinv_purchase_purchase_order_item pi , " + " tinv_purchase_purchase_order po,inv_supplier ss,mst_unit un,inv_item ii where pi.ppot_ppo_id=po.ppo_id " + " and po.ppo_supplier_id=ss.supplier_id and pi.ppot_unit_id=un.unit_id and pi.ppot_item_id=ii.item_id ";
        String CountQuery = "";
        String columnName = "";
//        if (misitemwisepurchaseSearchDTO.getFromdate().equals("") || misitemwisepurchaseSearchDTO.getFromdate().equals("null")) {
//            misitemwisepurchaseSearchDTO.setFromdate("1990-06-07");
//        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
//        if (misitemwisepurchaseSearchDTO.getTodate().equals("") || misitemwisepurchaseSearchDTO.getTodate().equals("null")) {
//            misitemwisepurchaseSearchDTO.setTodate(strDate);
//        }
        if (misitemwisepurchaseSearchDTO.getTodaydate()) {
            Query += " and (date(po.created_date)=curdate()) ";
        } else {
            Query += " and (date(po.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
//        if (misitemwisepurchaseSearchDTO.getTodaydate()) {
//            Query += " and (date(po.created_date)=curdate()) ";
//        } else {
//            Query += " and (date(po.created_date) between '" + misitemwisepurchaseSearchDTO.getFromdate() + "' and '" + misitemwisepurchaseSearchDTO.getTodate() + "')  ";
//        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and pi.ppot_unit_id in (" + values + ") ";
        }
        if (!supplierId.equals("null") && !supplierId.equals("0")) {
            Query += " and po.ppo_supplier_id =  " + supplierId + " ";
        }
        if (!itemName.equals("null") && !itemName.equals(" ")) {
            Query += " and  ii.item_name like '%" + itemName + "%' ";
        }

        System.out.println("Item Wise Purchase Report:" + Query);
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,itemname,PoNo,PoDate,PoQty,ItemRate,MRP,suppliername";
        Query += " limit " + ((misitemwisepurchaseSearchDTO.getOffset() - 1) * misitemwisepurchaseSearchDTO.getLimit()) + "," + misitemwisepurchaseSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    @RequestMapping("getitemwisepurchaseReportPrint/{unitList}/{supplierId}/{fromdate}/{todate}/{itemName}")
    public String searchItemWisePurchasePrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisItemwisePurchaseSearchDTO misitemwisepurchaseSearchDTO, @PathVariable String[] unitList, @PathVariable String supplierId, @PathVariable String fromdate, @PathVariable String todate, @PathVariable String itemName) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
//        List<MisitemwisePurchaseListDTO> misitemwisepurchaseListDTOList = new ArrayList<MisitemwisePurchaseListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT un.unit_name,ii.item_name,po.ppo_id as PO_NO,date(po.created_date) as " + " PO_Date,pi.ppot_item_quantity as Po_Qty, " + " pi.ppot_item_rate ,pi.ppot_item_rate_per_qty as Mrp,ss.supplier_name " + " FROM  tinv_purchase_purchase_order_item pi , " + " tinv_purchase_purchase_order po,inv_supplier ss,mst_unit un,inv_item ii where pi.ppot_ppo_id=po.ppo_id " + " and po.ppo_supplier_id=ss.supplier_id and pi.ppot_unit_id=un.unit_id and pi.ppot_item_id=ii.item_id ";
        String CountQuery = "";
        String columnName = "";
//        if (misitemwisepurchaseSearchDTO.getFromdate().equals("") || misitemwisepurchaseSearchDTO.getFromdate().equals("null")) {
//            misitemwisepurchaseSearchDTO.setFromdate("1990-06-07");
//        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
//        if (misitemwisepurchaseSearchDTO.getTodate().equals("") || misitemwisepurchaseSearchDTO.getTodate().equals("null")) {
//            misitemwisepurchaseSearchDTO.setTodate(strDate);
//        }
        if (misitemwisepurchaseSearchDTO.getTodaydate()) {
            Query += " and (date(po.created_date)=curdate()) ";
        } else {
            Query += " and (date(po.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
//        if (misitemwisepurchaseSearchDTO.getTodaydate()) {
//            Query += " and (date(po.created_date)=curdate()) ";
//        } else {
//            Query += " and (date(po.created_date) between '" + misitemwisepurchaseSearchDTO.getFromdate() + "' and '" + misitemwisepurchaseSearchDTO.getTodate() + "')  ";
//        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and pi.ppot_unit_id in (" + values + ") ";
        }
        if (!supplierId.equals("null") && !supplierId.equals("0")) {
            Query += " and po.ppo_supplier_id =  " + supplierId + " ";
        }
        if (!itemName.equals("null") && !itemName.equals(" ")) {
            Query += " and  ii.item_name like '%" + itemName + "%' ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,itemname,PoNo,PoDate,PoQty,ItemRate,MRP,suppliername";
        Query += " limit " + ((misitemwisepurchaseSearchDTO.getOffset() - 1) * misitemwisepurchaseSearchDTO.getLimit()) + "," + misitemwisepurchaseSearchDTO.getLimit();
        //   return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misitemwisepurchaseSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(misitemwisepurchaseSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (misitemwisepurchaseSearchDTO.getPrint()) {
            return createReport.createJasperReportXLSByJson("ItemWisePurchaseReport", jsonArray.toString());
        } else {
            // return ResponseEntity.ok(jsonArray.toString());
            return createReport.createJasperReportPDFByJson("ItemWisePurchaseReport", jsonArray.toString());
        }
    }

    @RequestMapping("searchNearbyExpiryItems/{unitList}")
    public List<TinvOpeningBalanceItem> getListOfItemsForNearbyExpiry(@RequestHeader("X-tenantId") String tenantName,
                                                                      @RequestBody SearchMisinventoryExpiry searchMisinventoryExpiry, @PathVariable Long[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = searchMisinventoryExpiry.getLimit() * ((searchMisinventoryExpiry.getOffset()) - 1);
        int limit = searchMisinventoryExpiry.getLimit();
        long count = 0;
        List<TinvOpeningBalanceItem> ItemList = new ArrayList<TinvOpeningBalanceItem>();
        if (searchMisinventoryExpiry == null) {
            SearchMisinventoryExpiry sb = new SearchMisinventoryExpiry();
            searchMisinventoryExpiry = sb;
        }
        String queryInya = "";
        if (searchMisinventoryExpiry.getSearchFromExpdate() == null) {
//			String sDate1 = "1998/12/31";
            String sDate1 = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchFromExpdate(sDate1);
        }
        if (searchMisinventoryExpiry.getSearchToExpdate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchToExpdate(date);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date newDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDate);
        cal.add(Calendar.MONTH, 3);
        String new1 = sdf.format(cal.getTime());
        queryInya = "Select i from TinvOpeningBalanceItem i Where  DATE(i.obiItemExpiryDate) > CURDATE() AND NOT(DATE(i.obiItemExpiryDate) >= '" + new1 + "') ";
        // if (searchMisinventoryExpiry.getUnitId() != 0) {
        // queryInya += " And i.openingBalanceUnitId.unitId=" +
        // searchMisinventoryExpiry.getUnitId();
        // }
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryInya += " And i.openingBalanceUnitId.unitId in (" + values + ") ";
        }
        if (searchMisinventoryExpiry.getStoreId() != 0) {
            queryInya += " And i.obiStoreId.storeId=" + searchMisinventoryExpiry.getStoreId();
        }
        queryInya += " order by i.obiId desc";
        System.out.println("Nearby Expiry Item List Report "+queryInya);
        // List<TinvOpeningBalanceItem> ItemListfinal = new
        // ArrayList<TinvOpeningBalanceItem>();
        String CountQuery = StringUtils.replace(queryInya, " i fr", " count(i.obiId) fr");
        System.out.println("Count:" + CountQuery);
        count = (Long) entityManager.createQuery(CountQuery).getSingleResult();
        ItemList = entityManager.createQuery(queryInya, TinvOpeningBalanceItem.class)
                .setFirstResult(offset)
                .setMaxResults(limit).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping("searchNearbyExpiryItemsPrint/{unitList}")
    public String getNearbyExpiryItemsPrint(@RequestHeader("X-tenantId") String tenantName,
                                            @RequestBody SearchMisinventoryExpiry searchMisinventoryExpiry, @PathVariable Long[] unitList) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        JSONArray jsonArray = null;
        List<TinvOpeningBalanceItem> ItemList = new ArrayList<TinvOpeningBalanceItem>();
        if (searchMisinventoryExpiry == null) {
            SearchMisinventoryExpiry sb = new SearchMisinventoryExpiry();
            searchMisinventoryExpiry = sb;
        }
        String queryInya = "";
        if (searchMisinventoryExpiry.getSearchFromExpdate() == null) {
//			String sDate1 = "1998/12/31";
            String sDate1 = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchFromExpdate(sDate1);
        }
        if (searchMisinventoryExpiry.getSearchToExpdate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchMisinventoryExpiry.setSearchToExpdate(date);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date newDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDate);
        cal.add(Calendar.MONTH, 3);
        String new1 = sdf.format(cal.getTime());
//        queryInya = "Select i from TinvOpeningBalanceItem i Where  DATE(i.obiItemExpiryDate) > CURDATE() AND NOT(DATE(i.obiItemExpiryDate) >= '" + new1 + "') ";
        queryInya = "SELECT mu.unit_name AS unitName,tobi.obi_item_name AS itemName,tobi.obi_item_batch_code AS itemBatchcode, " +
                " tobi.obi_item_expiry_date as expiryDate,tobi.obi_item_quantity AS itemQty,idt.idt_name AS idtName " +
                " FROM tinv_opening_balance_item tobi " +
                " INNER JOIN inv_item ii ON ii.item_id = tobi.obi_item_id " +
                " INNER JOIN inv_store ist ON ist.store_id = tobi.obi_store_id " +
                " INNER JOIN mst_unit mu ON mu.unit_id = tobi.opening_balance_unit_id " +
                " INNER JOIN inv_item_dispensing_type idt ON idt.idt_id = tobi.obi_item_despensing_idt_id " +
                " WHERE DATE(tobi.obi_item_expiry_date) > CURDATE() AND NOT(DATE(tobi.obi_item_expiry_date) >= '" + new1 + "') ";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryInya += " And tobi.opening_balance_unit_id in (" + values + ") ";
        }
        if (searchMisinventoryExpiry.getStoreId() != 0) {
            queryInya += " And tobi.obi_store_id=" + searchMisinventoryExpiry.getStoreId();
        }
        queryInya += " order by tobi.obi_id desc";
        String CountQuery = " select count(*) from ( " + queryInya + " ) as combine ";
        String columnName = "unitName,itemName,itemBatchcode,expiryDate,itemQty,idtName";
        jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, queryInya, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(searchMisinventoryExpiry.getCurrentUnitId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        if (jsonArray != null) {
            jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        }
        if (searchMisinventoryExpiry.isPrint()) {
            columnName = "unitName,itemName,itemBatchcode,expiryDate,itemQty,idtName";
            return createReport.generateExcel(columnName, "NearByExpiryReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("NearByExpiryReport", jsonArray.toString());
        }
    }

    @GetMapping("searchAutoMailNearbyExpiryItemsPrint")
    public String getAutoMailNearbyExpiryItemsPrint(@RequestHeader("X-tenantId") String tenantName) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("Hello");
        JSONArray jsonArray = null;
        List<TinvOpeningBalanceItem> ItemList = new ArrayList<TinvOpeningBalanceItem>();
        String queryInya = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date newDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDate);
        cal.add(Calendar.MONTH, 3);
        String new1 = sdf.format(cal.getTime());
//  queryInya = "Select i from TinvOpeningBalanceItem i Where  DATE(i.obiItemExpiryDate) > CURDATE() AND NOT(DATE(i.obiItemExpiryDate) >= '" + new1 + "') ";
        queryInya = "SELECT mu.unit_name AS unitName,tobi.obi_item_name AS itemName,tobi.obi_item_batch_code AS itemBatchcode, " +
                " tobi.obi_item_expiry_date as expiryDate,tobi.obi_item_quantity AS itemQty,idt.idt_name AS idtName " +
                " FROM tinv_opening_balance_item tobi " +
                " INNER JOIN inv_item ii ON ii.item_id = tobi.obi_item_id " +
                " INNER JOIN inv_store ist ON ist.store_id = tobi.obi_store_id " +
                " INNER JOIN mst_unit mu ON mu.unit_id = tobi.opening_balance_unit_id " +
                " INNER JOIN inv_item_dispensing_type idt ON idt.idt_id = tobi.obi_item_despensing_idt_id " +
                " WHERE DATE(tobi.obi_item_expiry_date) > CURDATE() AND NOT(DATE(tobi.obi_item_expiry_date) >= '" + new1 + "') ";
        queryInya += " order by tobi.obi_id desc";
        String CountQuery = " select count(*) from ( " + queryInya + " ) as combine ";
        System.out.println("searchAutoMailNearbyExpiryItemsPrint : " + queryInya);
        String columnName = "unitName,itemName,itemBatchcode,expiryDate,itemQty,idtName";
        jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, queryInya, CountQuery));
        columnName = "unitName,itemName,itemBatchcode,expiryDate,itemQty,idtName";
        return createReport.generateExcel(columnName, "NearByExpiryReport", jsonArray);
    }
}
