package com.cellbeans.hspa.tinvpurchasequotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.cellbeans.hspa.TenantContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_purchase_quotation")
public class TinvPurchaseQuotationController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvPurchaseQuotationRepository tinvPurchaseQuotationRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseQuotation tinvPurchaseQuotation) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPurchaseQuotation.getPqTotalNetAmount() != null) {
            tinvPurchaseQuotationRepository.save(tinvPurchaseQuotation);
            respMap.put("pqId", String.valueOf(tinvPurchaseQuotation.getPqId()));
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
        List<TinvPurchaseQuotation> records;
        records = tinvPurchaseQuotationRepository.findByPqNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{pqId}")
    public TinvPurchaseQuotation read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pqId") Long pqId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseQuotation tinvPurchaseQuotation = tinvPurchaseQuotationRepository.getById(pqId);
        return tinvPurchaseQuotation;
    }

    @RequestMapping("update")
    public TinvPurchaseQuotation update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseQuotation tinvPurchaseQuotation) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseQuotationRepository.save(tinvPurchaseQuotation);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvPurchaseQuotation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pqId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPurchaseQuotationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvPurchaseQuotationRepository.findByPqIdAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{pqId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pqId") Long pqId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseQuotation tinvPurchaseQuotation = tinvPurchaseQuotationRepository.getById(pqId);
        if (tinvPurchaseQuotation != null) {
            tinvPurchaseQuotation.setIsDeleted(true);
            tinvPurchaseQuotationRepository.save(tinvPurchaseQuotation);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("bypqpieId")
    public List<TinvPurchaseQuotation> getQuatationdependsOnEnquery(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseQuotation tinvPurchaseQuotation) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseQuotationRepository.findByPqPieIdPieIdAndPqIsApproveTrueAndIsActiveTrueAndIsDeletedFalse(tinvPurchaseQuotation.getPqPieId().getPieId());
    }

    @RequestMapping("search")
    public List<TinvPurchaseQuotation> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchQuotationItem searchQuotationItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchQuotationItem == null) {
            SearchQuotationItem sb = new SearchQuotationItem();
            searchQuotationItem = sb;
        }
        String queryInya = "";
        String queryInyaCount = "";
        String itemStore = "";
        String itemSupplier = "";
        queryInya = "Select i from TinvPurchaseQuotation i left join i.pqPieId tpe Where i.pqUnitId.unitId=" + searchQuotationItem.getUnitId();
        queryInyaCount = "Select count(i.pqId) from TinvPurchaseQuotation i left join i.pqPieId tpe Where i.pqUnitId.unitId=" + searchQuotationItem.getUnitId();
        // System.out.println(queryInya);
        if (searchQuotationItem.getSearchQuotationRefNo() == null)
            searchQuotationItem.setSearchQuotationRefNo("");
        if (searchQuotationItem.getSearchPieEnquiryNo() == null)
            searchQuotationItem.setSearchPieEnquiryNo("");
        if (!searchQuotationItem.getSearchQuotationRefNo().equals("")) {
            queryInya += " AND  i.pqQuatationRefNo like '%" + searchQuotationItem.getSearchQuotationRefNo() + "%' ";
            queryInyaCount += " AND  i.pqQuatationRefNo like '%" + searchQuotationItem.getSearchQuotationRefNo() + "%' ";
        }
        if (!searchQuotationItem.getSearchPieEnquiryNo().equals("")) {
            queryInya += " And tpe.pieEnquiryNo like '%" + searchQuotationItem.getSearchPieEnquiryNo() + "%' ";
            queryInyaCount += " And tpe.pieEnquiryNo like '%" + searchQuotationItem.getSearchPieEnquiryNo() + "%' ";
        }
        if (searchQuotationItem.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchQuotationItem.setSearchFromDate(sDate1);
        }
        if (searchQuotationItem.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchQuotationItem.setSearchToDate(date);
        }
        if (searchQuotationItem.getSearchStoreId().getStoreId() != 0)
            itemStore = " AND i.pqStoreId.storeId=" + searchQuotationItem.getSearchStoreId().getStoreId() + " ";
        if (searchQuotationItem.getSearchSupplierId().getSupplierId() != 0)
            itemSupplier = " AND i.pqSupplierId.supplierId=" + searchQuotationItem.getSearchSupplierId().getSupplierId() + " ";
        queryInya += " AND date(i.createdDate) between   " + " '" + searchQuotationItem.getSearchFromDate() + "' AND  " + "'" + searchQuotationItem.getSearchToDate() + "' " + itemStore + "  " + itemSupplier + " order by i.pqId desc ";
        queryInyaCount += " AND date(i.createdDate) between   " + " '" + searchQuotationItem.getSearchFromDate() + "' AND  " + "'" + searchQuotationItem.getSearchToDate() + "' " + itemStore + "  " + itemSupplier + " ";
//        queryInya = "Select i from TinvPurchaseQuotation i left join i.pqPieId tpe Where i.pqUnitId.unitId="+searchQuotationItem.getUnitId()+" And tpe.pieEnquiryNo like '%" + searchQuotationItem.getSearchPieEnquiryNo() + "%'" +
//                " AND  i.pqQuatationRefNo like '%" + searchQuotationItem.getSearchQuotationRefNo() + "%'  AND date(i.createdDate) between   " + " ' " + searchQuotationItem.getSearchFromDate() + " ' AND  " + "'" + searchQuotationItem.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.pqId desc";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery(queryInyaCount).getSingleResult();
        List<TinvPurchaseQuotation> ItemList = entityManager.createQuery(queryInya, TinvPurchaseQuotation.class).setFirstResult(((searchQuotationItem.getOffset() - 1) * searchQuotationItem.getLimit())).setMaxResults(searchQuotationItem.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @PutMapping("aprovepo/{pqId}")
    public Map<String, String> ApprovePo(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pqId") Long pqId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseQuotation tinvPurchaseQuotation = tinvPurchaseQuotationRepository.getById(pqId);
        if (tinvPurchaseQuotation != null) {
            if (tinvPurchaseQuotation.getPqIsApprove())
                tinvPurchaseQuotation.setPqIsApprove(false);
            else
                tinvPurchaseQuotation.setPqIsApprove(true);
            tinvPurchaseQuotationRepository.save(tinvPurchaseQuotation);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
