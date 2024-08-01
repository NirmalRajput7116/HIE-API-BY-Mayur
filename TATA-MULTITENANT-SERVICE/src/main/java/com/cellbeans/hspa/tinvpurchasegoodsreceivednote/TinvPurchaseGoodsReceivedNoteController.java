package com.cellbeans.hspa.tinvpurchasegoodsreceivednote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import com.cellbeans.hspa.TenantContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/tinv_purchase_goods_received_note")
public class TinvPurchaseGoodsReceivedNoteController {

    Map<String, String> respMap = new HashMap<String, String>();
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    TinvPurchaseGoodsReceivedNoteRepository tinvPurchaseGoodsReceivedNoteRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseGoodsReceivedNote tinvPurchaseGoodsReceivedNote) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPurchaseGoodsReceivedNote.getPgrnNetAmount() != null) {
            tinvPurchaseGoodsReceivedNoteRepository.save(tinvPurchaseGoodsReceivedNote);
            respMap.put("pgrnId", String.valueOf(tinvPurchaseGoodsReceivedNote.getPgrnId()));
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
        List<TinvPurchaseGoodsReceivedNote> records;
        records = tinvPurchaseGoodsReceivedNoteRepository.findByPgrnGrnNoContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{pgrnId}")
    public TinvPurchaseGoodsReceivedNote read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrnId") Long pgrnId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseGoodsReceivedNote tinvPurchaseGoodsReceivedNote = tinvPurchaseGoodsReceivedNoteRepository.getById(pgrnId);
        return tinvPurchaseGoodsReceivedNote;
    }

    @RequestMapping("update")
    public TinvPurchaseGoodsReceivedNote update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseGoodsReceivedNote tinvPurchaseGoodsReceivedNote) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseGoodsReceivedNoteRepository.save(tinvPurchaseGoodsReceivedNote);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvPurchaseGoodsReceivedNote> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pgrnId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPurchaseGoodsReceivedNoteRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvPurchaseGoodsReceivedNoteRepository.findByPgrnNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{pgrnId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrnId") Long pgrnId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseGoodsReceivedNote tinvPurchaseGoodsReceivedNote = tinvPurchaseGoodsReceivedNoteRepository.getById(pgrnId);
        if (tinvPurchaseGoodsReceivedNote != null) {
            tinvPurchaseGoodsReceivedNote.setIsDeleted(true);
            tinvPurchaseGoodsReceivedNoteRepository.save(tinvPurchaseGoodsReceivedNote);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("search")
    public List<TinvPurchaseGoodsReceivedNote> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchGRNItem searchGRNItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchGRNItem == null) {
            SearchGRNItem sb = new SearchGRNItem();
            searchGRNItem = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        String ifPo = "";
        if (searchGRNItem.getSearchGrnNo() == null)
            searchGRNItem.setSearchGrnNo("");
        if (searchGRNItem.getSearchPoNo() == null)
            searchGRNItem.setSearchPoNo("");
        else {
            ifPo = "AND i.pgrnPpoId.ppoPqId.pqPieId.pieEnquiryNo like '%" + searchGRNItem.getSearchPieEnquiryNo() + "%' AND i.pgrnPpoId.ppoPqId like '%" + searchGRNItem.getSearchPoNo() + "%'" + "  AND  i.pgrnPpoId.ppoPqId.pqQuatationRefNo like '%" + searchGRNItem.getSearchQuotationRefNo() + "%' ";
        }
        if (searchGRNItem.getSearchQuotationRefNo() == null)
            searchGRNItem.setSearchQuotationRefNo("");
        else {
            ifPo = "AND i.pgrnPpoId.ppoPqId.pqPieId.pieEnquiryNo like '%" + searchGRNItem.getSearchPieEnquiryNo() + "%' AND i.pgrnPpoId.ppoPqId like '%" + searchGRNItem.getSearchPoNo() + "%'" + "  AND  i.pgrnPpoId.ppoPqId.pqQuatationRefNo like '%" + searchGRNItem.getSearchQuotationRefNo() + "%' ";
        }
        if (searchGRNItem.getSearchPieEnquiryNo() == null)
            searchGRNItem.setSearchPieEnquiryNo("");
        else {
            ifPo = "AND i.pgrnPpoId.ppoPqId.pqPieId.pieEnquiryNo like '%" + searchGRNItem.getSearchPieEnquiryNo() + "%' AND i.pgrnPpoId.ppoPqId like '%" + searchGRNItem.getSearchPoNo() + "%'" + "  AND  i.pgrnPpoId.ppoPqId.pqQuatationRefNo like '%" + searchGRNItem.getSearchQuotationRefNo() + "%' ";
        }
        if (searchGRNItem.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchGRNItem.setSearchFromDate(sDate1);
        }
        if (searchGRNItem.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchGRNItem.setSearchToDate(date);
        }
        if (searchGRNItem.getSearchStoreId().getStoreId() != 0)
            itemStore = "AND i.pgrnStoreId.storeId=" + searchGRNItem.getSearchStoreId().getStoreId() + "";
        if (searchGRNItem.getSearchSupplierId().getSupplierId() != 0)
            itemSupplier = "AND i.pgrnSupplierId.supplierId=" + searchGRNItem.getSearchSupplierId().getSupplierId() + "";
        queryInya = "Select i from TinvPurchaseGoodsReceivedNote i Where i.grnUnitId.unitId=" + searchGRNItem.getUnitId() + " And i.pgrnGrnNo like '%" + searchGRNItem.getSearchGrnNo() + "%'  AND date(i.createdDate) between   " + " ' " + searchGRNItem.getSearchFromDate() + " ' AND  " + "'" + searchGRNItem.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " " + ifPo + " and i.isRejected = 0 order by i.pgrnId desc";
        System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.pgrnId) from TinvPurchaseGoodsReceivedNote i Where i.grnUnitId.unitId=" + searchGRNItem.getUnitId() + " And i.pgrnGrnNo like '%" + searchGRNItem.getSearchGrnNo() + "%'  AND date(i.createdDate) between   " + " ' " + searchGRNItem.getSearchFromDate() + " ' AND  " + "'" + searchGRNItem.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " " + ifPo + " order by i.pgrnId desc").getSingleResult();
        List<TinvPurchaseGoodsReceivedNote> ItemList = entityManager.createQuery(queryInya, TinvPurchaseGoodsReceivedNote.class).setFirstResult(((searchGRNItem.getOffset() - 1) * searchGRNItem.getLimit())).setMaxResults(searchGRNItem.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping("searchautocomplate/{data}")
    public List<TinvPurchaseGoodsReceivedNote> getListBySearch(@RequestHeader("X-tenantId") String tenantName, @PathVariable("data") String data) {
        TenantContext.setCurrentTenant(tenantName);
        String queryInya = "Select i from TinvPurchaseGoodsReceivedNote i Where  i.pgrnGrnNo like '%" + data + "%' OR i.pgrnStoreId.storeName like '%" + data + "%' OR i.pgrnSupplierId.supplierName like '%" + data + "%' ";
        List<TinvPurchaseGoodsReceivedNote> ItemList = entityManager.createQuery(queryInya, TinvPurchaseGoodsReceivedNote.class).getResultList();
        return ItemList;
    }

    @RequestMapping("searchautocomplateByUnitId/{data}/{unitId}")
    public List<TinvPurchaseGoodsReceivedNote> searchautocomplateByUnitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("data") String data, @PathVariable("unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvPurchaseGoodsReceivedNote> ItemList = new ArrayList<TinvPurchaseGoodsReceivedNote>();
        try {
            String queryInya = "Select i from TinvPurchaseGoodsReceivedNote i Where  (i.pgrnGrnNo like '%" + data + "%' OR i.pgrnStoreId.storeName like '%" + data + "%' OR i.pgrnSupplierId.supplierName like '%" + data + "%') And i.grnUnitId.unitId=" + unitId + " ";
            System.out.println(queryInya);
            ItemList = entityManager.createQuery(queryInya, TinvPurchaseGoodsReceivedNote.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ItemList;
    }

    @RequestMapping("createAutoGrnnumber")
    public String createAutoGrnnumber(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        int value;
        try {
            String Query = "SELECT ifnull(max(r.pgrn_id), 0) FROM tinv_purchase_goods_received_note r";
            System.out.println("max number" + Query);
            BigInteger temp = (BigInteger) entityManager.createNativeQuery(Query).getSingleResult();
            value = temp.intValue() + 1;
            return Integer.toString(value);
        } catch (Exception e) {
            return "1";
        }
    }

    @RequestMapping("approveGRN/{pgrnId}")
    public Map<String, String> onApproveGRN(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrnId") Long pgrnId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseGoodsReceivedNote tinvPurchaseGoodsReceivedNote = tinvPurchaseGoodsReceivedNoteRepository.getById(pgrnId);
        if (tinvPurchaseGoodsReceivedNote != null) {
            if (tinvPurchaseGoodsReceivedNote.getGrnIsApprove())
                tinvPurchaseGoodsReceivedNote.setGrnIsApprove(false);
            else
                tinvPurchaseGoodsReceivedNote.setGrnIsApprove(true);
            tinvPurchaseGoodsReceivedNoteRepository.save(tinvPurchaseGoodsReceivedNote);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    /* @RequestMapping("cancleadmissionbyid")
     public Map<String, Object> cancleadmissionbyid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "admissionId") Long admissionId, @RequestParam(value = "admissionCancelReason") String admissionCancelReason) {
         TenantContext.setCurrentTenant(tenantName);
         String currentdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
         trnAdmissionRepository.markascancel(currentdate, admissionCancelReason, admissionId);
         respMapcreate.put("success", "1");
         respMapcreate.put("msg", "Cancelled Successfully");
         return respMapcreate;
     }*/
    @RequestMapping("rejectGRN")
    public Map<String, String> onRejectGRN(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "pgrnId") Long pgrnId, @RequestParam(value = "pgrnRejectReason") String pgrnRejectReason) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseGoodsReceivedNote tinvPurchaseGoodsReceivedNote = tinvPurchaseGoodsReceivedNoteRepository.getById(pgrnId);
        if (tinvPurchaseGoodsReceivedNote != null) {
            if (tinvPurchaseGoodsReceivedNote.getRejected())
                tinvPurchaseGoodsReceivedNote.setRejected(false);
            else
                tinvPurchaseGoodsReceivedNote.setRejected(true);
            tinvPurchaseGoodsReceivedNote.setPgrnRejectReason(pgrnRejectReason);
            tinvPurchaseGoodsReceivedNoteRepository.save(tinvPurchaseGoodsReceivedNote);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
