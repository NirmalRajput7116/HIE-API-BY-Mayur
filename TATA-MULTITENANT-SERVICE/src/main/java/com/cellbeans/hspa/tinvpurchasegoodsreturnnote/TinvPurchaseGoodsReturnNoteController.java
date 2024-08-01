package com.cellbeans.hspa.tinvpurchasegoodsreturnnote;

import com.cellbeans.hspa.TenantContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_purchase_goods_return_note")
public class TinvPurchaseGoodsReturnNoteController {

    Map<String, String> respMap = new HashMap<String, String>();
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    TinvPurchaseGoodsReturnNoteRepository tinvPurchaseGoodsReturnNoteRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseGoodsReturnNote tinvPurchaseGoodsReturnNote) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPurchaseGoodsReturnNote.getPgrnNetAmount() >= 0) {
            tinvPurchaseGoodsReturnNoteRepository.save(tinvPurchaseGoodsReturnNote);
            respMap.put("pgrnId", String.valueOf(tinvPurchaseGoodsReturnNote.getPgrnId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TinvPurchaseGoodsReturnNote> records;
        records = tinvPurchaseGoodsReturnNoteRepository.findByPgrnGoodsReturnNoContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{pgrnId}")
    public TinvPurchaseGoodsReturnNote read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrnId") Long pgrnId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseGoodsReturnNote tinvPurchaseGoodsReturnNote = tinvPurchaseGoodsReturnNoteRepository.getById(pgrnId);
        return tinvPurchaseGoodsReturnNote;
    }

    @RequestMapping("update")
    public TinvPurchaseGoodsReturnNote update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseGoodsReturnNote tinvPurchaseGoodsReturnNote) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseGoodsReturnNoteRepository.save(tinvPurchaseGoodsReturnNote);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvPurchaseGoodsReturnNote> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pgrnId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPurchaseGoodsReturnNoteRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            long id = 0;
            return tinvPurchaseGoodsReturnNoteRepository.findByPgrnIdContainsAndIsActiveTrueAndIsDeletedFalse(id, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{pgrnId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrnId") Long pgrnId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseGoodsReturnNote tinvPurchaseGoodsReturnNote = tinvPurchaseGoodsReturnNoteRepository.getById(pgrnId);
        if (tinvPurchaseGoodsReturnNote != null) {
            tinvPurchaseGoodsReturnNote.setIsDeleted(true);
            tinvPurchaseGoodsReturnNoteRepository.save(tinvPurchaseGoodsReturnNote);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("search")
    public List<TinvPurchaseGoodsReturnNote> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchGRNReturnItem searchGRNReturnItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchGRNReturnItem == null) {
            SearchGRNReturnItem sb = new SearchGRNReturnItem();
            searchGRNReturnItem = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        String ifPo = "";
        if (searchGRNReturnItem.getSearchGrnReturnNo() == null)
            searchGRNReturnItem.setSearchGrnReturnNo("");
        if (searchGRNReturnItem.getSearchGrnNo() == null)
            searchGRNReturnItem.setSearchGrnNo("");
        else {
            ifPo = " AND i.pgrnPgrnId.pgrnPpoId.ppoPqId.pqPieId.pieEnquiryNo like '%" + searchGRNReturnItem.getSearchPieEnquiryNo() + "%'AND i.pgrnPgrnId.pgrnGrnNo like '%" + searchGRNReturnItem.getSearchGrnNo() + "%' AND i.pgrnPgrnId.pgrnPpoId.ppoId like '%" + searchGRNReturnItem.getSearchPoNo() + "%'" + "  AND  i.pgrnPgrnId.pgrnPpoId.ppoPqId.pqQuatationRefNo like '%" + searchGRNReturnItem.getSearchQuotationRefNo() + "%' ";
        }
        if (searchGRNReturnItem.getSearchPoNo() == null)
            searchGRNReturnItem.setSearchPoNo("");
        else {
            ifPo = " AND i.pgrnPgrnId.pgrnPpoId.ppoPqId.pqPieId.pieEnquiryNo like '%" + searchGRNReturnItem.getSearchPieEnquiryNo() + "%'AND i.pgrnPgrnId.pgrnGrnNo like '%" + searchGRNReturnItem.getSearchGrnNo() + "%' AND i.pgrnPgrnId.pgrnPpoId.ppoId like '%" + searchGRNReturnItem.getSearchPoNo() + "%'" + "  AND  i.pgrnPgrnId.pgrnPpoId.ppoPqId.pqQuatationRefNo like '%" + searchGRNReturnItem.getSearchQuotationRefNo() + "%' ";
        }
        if (searchGRNReturnItem.getSearchQuotationRefNo() == null)
            searchGRNReturnItem.setSearchQuotationRefNo("");
        else {
            ifPo = " AND i.pgrnPgrnId.pgrnPpoId.ppoPqId.pqPieId.pieEnquiryNo like '%" + searchGRNReturnItem.getSearchPieEnquiryNo() + "%'AND i.pgrnPgrnId.pgrnGrnNo like '%" + searchGRNReturnItem.getSearchGrnNo() + "%' AND i.pgrnPgrnId.pgrnPpoId.ppoId like '%" + searchGRNReturnItem.getSearchPoNo() + "%'" + "  AND  i.pgrnPgrnId.pgrnPpoId.ppoPqId.pqQuatationRefNo like '%" + searchGRNReturnItem.getSearchQuotationRefNo() + "%' ";
        }
        if (searchGRNReturnItem.getSearchPieEnquiryNo() == null)
            searchGRNReturnItem.setSearchPieEnquiryNo("");
        else {
            ifPo = " AND i.pgrnPgrnId.pgrnPpoId.ppoPqId.pqPieId.pieEnquiryNo like '%" + searchGRNReturnItem.getSearchPieEnquiryNo() + "%'AND i.pgrnPgrnId.pgrnGrnNo like '%" + searchGRNReturnItem.getSearchGrnNo() + "%' AND i.pgrnPgrnId.pgrnPpoId.ppoId like '%" + searchGRNReturnItem.getSearchPoNo() + "%'" + "  AND  i.pgrnPgrnId.pgrnPpoId.ppoPqId.pqQuatationRefNo like '%" + searchGRNReturnItem.getSearchQuotationRefNo() + "%' ";
        }
        if (searchGRNReturnItem.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchGRNReturnItem.setSearchFromDate(sDate1);
        }
        if (searchGRNReturnItem.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchGRNReturnItem.setSearchToDate(date);
        }
        if (searchGRNReturnItem.getSearchStoreId().getStoreId() != 0)
            itemStore = "AND i.pgrnStoreId.storeId=" + searchGRNReturnItem.getSearchStoreId().getStoreId() + "";
        if (searchGRNReturnItem.getSearchSupplierId().getSupplierId() != 0)
            itemSupplier = "AND i.pgrnSupplierId.supplierId=" + searchGRNReturnItem.getSearchSupplierId().getSupplierId() + "";
        queryInya = "Select i from TinvPurchaseGoodsReturnNote i Where i.pgrnUnitId.unitId=" + searchGRNReturnItem.getUnitId() + " And i.pgrnGoodsReturnNo like '%" + searchGRNReturnItem.getSearchGrnNo() + "%'  AND date(i.createdDate) between   " + " ' " + searchGRNReturnItem.getSearchFromDate() + " ' AND  " + "'" + searchGRNReturnItem.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " " + ifPo + " order by i.pgrnId desc";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.pgrnId) from TinvPurchaseGoodsReturnNote i Where i.pgrnUnitId.unitId=" + searchGRNReturnItem.getUnitId() + " And i.pgrnGoodsReturnNo like '%" + searchGRNReturnItem.getSearchGrnNo() + "%'  AND date(i.createdDate) between   " + " ' " + searchGRNReturnItem.getSearchFromDate() + " ' AND  " + "'" + searchGRNReturnItem.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " " + ifPo + " order by i.pgrnId desc").getSingleResult();
        List<TinvPurchaseGoodsReturnNote> ItemList = entityManager.createQuery(queryInya, TinvPurchaseGoodsReturnNote.class).setFirstResult(((searchGRNReturnItem.getOffset() - 1) * searchGRNReturnItem.getLimit())).setMaxResults(searchGRNReturnItem.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping("createAutoGrnReturnnumber")
    public String createAutoGrnnumber(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        int value;
        try {
            String Query = "SELECT ifnull(max(r.pgrn_id), 0) FROM tinv_purchase_goods_return_note r";
            System.out.println("max number" + Query);
            BigInteger temp = (BigInteger) entityManager.createNativeQuery(Query).getSingleResult();
            value = temp.intValue() + 1;
            return Integer.toString(value);
        } catch (Exception e) {
            return "1";
        }
    }

}
            
