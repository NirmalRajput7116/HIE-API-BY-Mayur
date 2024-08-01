package com.cellbeans.hspa.tinvpurchasepurchaseorder;

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
@RequestMapping("/tinv_purchase_purchase_order")
public class TinvPurchasePurchaseOrderController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvPurchasePurchaseOrderRepository tinvPurchasePurchaseOrderRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchasePurchaseOrder tinvPurchasePurchaseOrder) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPurchasePurchaseOrder.getPpoNetAmount() != null) {
//            tinvPurchasePurchaseOrder.setPpoNo(tinvPurchasePurchaseOrderRepository.makePONumber(tinvPurchasePurchaseOrder.getPpoStoreId().getStorePrefix()));
            tinvPurchasePurchaseOrderRepository.save(tinvPurchasePurchaseOrder);
            respMap.put("ppoId", String.valueOf(tinvPurchasePurchaseOrder.getPpoId()));
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
        List<TinvPurchasePurchaseOrder> records;
        records = tinvPurchasePurchaseOrderRepository.findByPpoPqIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ppoId}")
    public TinvPurchasePurchaseOrder read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ppoId") Long ppoId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchasePurchaseOrder tinvPurchasePurchaseOrder = tinvPurchasePurchaseOrderRepository.getById(ppoId);
        return tinvPurchasePurchaseOrder;
    }

    @RequestMapping("update")
    public TinvPurchasePurchaseOrder update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchasePurchaseOrder tinvPurchasePurchaseOrder) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchasePurchaseOrderRepository.save(tinvPurchasePurchaseOrder);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvPurchasePurchaseOrder> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ppoId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPurchasePurchaseOrderRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvPurchasePurchaseOrderRepository.findByPpoNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }
//    @GetMapping
//    @RequestMapping("listByUnitId")
//    public Iterable<TinvPurchasePurchaseOrder> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                                    @RequestParam(value = "size", required = false, defaultValue = "20") String size,
//                                                    @RequestParam(value = "qString", required = false) String qString,
//                                                    @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                                    @RequestParam(value = "col", required = false, defaultValue = "ppoId") String col,
//                                                    @RequestParam(value = "unitId", required = false) long unitId) {
//
//        if (qString == null || qString.equals("")) {
//            return tinvPurchasePurchaseOrderRepository.findAllByPpoUnitIdUnitIdIsActiveTrueAndIsDeletedFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        } else {
//
//            return tinvPurchasePurchaseOrderRepository.findByPpoNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        }
//
//    }

    @PutMapping("delete/{ppoId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ppoId") Long ppoId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchasePurchaseOrder tinvPurchasePurchaseOrder = tinvPurchasePurchaseOrderRepository.getById(ppoId);
        if (tinvPurchasePurchaseOrder != null) {
            tinvPurchasePurchaseOrder.setIsDeleted(true);
            tinvPurchasePurchaseOrderRepository.save(tinvPurchasePurchaseOrder);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("search")
    public List<TinvPurchasePurchaseOrder> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPurchaseOrder searchPurchaseOrder) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchPurchaseOrder == null) {
            SearchPurchaseOrder sb = new SearchPurchaseOrder();
            searchPurchaseOrder = sb;
        }
        String queryInya = "";
        String queryCountInya = "";
        String itemStore = "";
        String itemSupplier = "";
        queryInya = "Select i from TinvPurchasePurchaseOrder i left join i.ppoPqId tpq left join tpq.pqPieId tpe Where  i.ppoUnitId.unitId=" + searchPurchaseOrder.getUnitId();
        queryCountInya = "Select count(i.ppoId) from TinvPurchasePurchaseOrder i left join i.ppoPqId tpq left join tpq.pqPieId tpe Where  i.ppoUnitId.unitId=" + searchPurchaseOrder.getUnitId();
        if (searchPurchaseOrder.getSearchQuotationRefNo() != null) {
            queryInya += "  AND  tpq.pqQuatationRefNo like '%" + searchPurchaseOrder.getSearchQuotationRefNo() + "%' ";
            queryCountInya += "  AND  tpq.pqQuatationRefNo like '%" + searchPurchaseOrder.getSearchQuotationRefNo() + "%' ";
        }
        if (searchPurchaseOrder.getSearchPieEnquiryNo() != null) {
            queryInya += " And tpe.pieEnquiryNo like '%" + searchPurchaseOrder.getSearchPieEnquiryNo() + "%'";
            queryCountInya += " And tpe.pieEnquiryNo like '%" + searchPurchaseOrder.getSearchPieEnquiryNo() + "%'";
        }
        if (searchPurchaseOrder.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchPurchaseOrder.setSearchFromDate(sDate1);
        }
        if (searchPurchaseOrder.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchPurchaseOrder.setSearchToDate(date);
        }
        if (searchPurchaseOrder.getSearchStoreId().getStoreId() != 0)
            itemStore = " and i.ppoStoreId.storeId=" + searchPurchaseOrder.getSearchStoreId().getStoreId() + " ";
        if (searchPurchaseOrder.getSearchSupplierId().getSupplierId() != 0)
            itemSupplier = "AND i.ppoSupplierId.supplierId=" + searchPurchaseOrder.getSearchSupplierId().getSupplierId() + " ";
        queryInya += itemStore + itemSupplier + " and date(i.createdDate) between   " + " '" + searchPurchaseOrder.getSearchFromDate() + "' AND  " + "'" + searchPurchaseOrder.getSearchToDate() + "' " + itemStore + "  " + itemSupplier + " AND i.isRejected = 0 order by i.ppoId desc";
        queryCountInya += itemStore + itemSupplier + " and date(i.createdDate) between   " + " '" + searchPurchaseOrder.getSearchFromDate() + "' AND  " + "'" + searchPurchaseOrder.getSearchToDate() + "' " + itemStore + "  " + itemSupplier + "";
        //System.out.println(queryInya);
        long count = (long) entityManager.createQuery(queryCountInya).getSingleResult();
        List<TinvPurchasePurchaseOrder> ItemList = entityManager.createQuery(queryInya, TinvPurchasePurchaseOrder.class).setFirstResult(((searchPurchaseOrder.getOffset() - 1) * searchPurchaseOrder.getLimit())).setMaxResults(searchPurchaseOrder.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping("searchforpo/{data}")
    public List<SearchPurchaseOrder> getDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @PathVariable("data") String data) {
        TenantContext.setCurrentTenant(tenantName);
        String queryInya;
        List<SearchPurchaseOrder> ObjArray = new ArrayList<>();
        queryInya = "Select i from TinvPurchasePurchaseOrder i left join i.ppoPqId tpq left join tpq.pqPieId tpe Where (tpe.pieEnquiryNo like '%" + data + "%' OR  tpq.pqQuatationRefNo like '%" + data + "%'   OR  i.ppoId like '%" + data + "%') AND i.ppoIsPoApprove=true order by i.ppoId desc";
        // System.out.println(queryInya);
        List<TinvPurchasePurchaseOrder> ItemList = entityManager.createQuery(queryInya, TinvPurchasePurchaseOrder.class).getResultList();
        for (TinvPurchasePurchaseOrder ob : ItemList) {
            SearchPurchaseOrder searchPurchaseOrder = new SearchPurchaseOrder();
            searchPurchaseOrder.setPoId(ob.getPpoId());
            if (ob.getPpoPqId() != null) {
                searchPurchaseOrder.setSearchQuotationRefNo(ob.getPpoPqId().getPqQuotationRefNo());
                if (ob.getPpoPqId().getPqPieId() != null) {
                    searchPurchaseOrder.setSearchPieEnquiryNo(ob.getPpoPqId().getPqPieId().getPieEnquiryNo());
                } else {
                    searchPurchaseOrder.setSearchPieEnquiryNo("");
                }
            } else {
                searchPurchaseOrder.setSearchQuotationRefNo("");
                searchPurchaseOrder.setSearchPieEnquiryNo("");
            }
            searchPurchaseOrder.setSearchStoreId(ob.getPpoStoreId());
            searchPurchaseOrder.setSearchSupplierId(ob.getPpoSupplierId());
            ObjArray.add(searchPurchaseOrder);
        }
        return ObjArray;
    }

    @RequestMapping("approvePO/{ppoId}")
    public Map<String, String> onApprovePO(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ppoId") Long ppoId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchasePurchaseOrder tinvPurchasePurchaseOrder = tinvPurchasePurchaseOrderRepository.getById(ppoId);
        if (tinvPurchasePurchaseOrder != null) {
            if (tinvPurchasePurchaseOrder.getPpoIsPoApprove())
                tinvPurchasePurchaseOrder.setPpoIsPoApprove(false);
            else
                tinvPurchasePurchaseOrder.setPpoIsPoApprove(true);
            tinvPurchasePurchaseOrderRepository.save(tinvPurchasePurchaseOrder);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("rejectPO")
    public Map<String, String> onRejectGRN(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "poId") Long poId, @RequestParam(value = "poRejectReason") String pgrnRejectReason) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchasePurchaseOrder tinvPurchasePurchaseOrder = tinvPurchasePurchaseOrderRepository.getById(poId);
        if (tinvPurchasePurchaseOrder != null) {
            if (tinvPurchasePurchaseOrder.getRejected())
                tinvPurchasePurchaseOrder.setRejected(false);
            else
                tinvPurchasePurchaseOrder.setRejected(true);
            tinvPurchasePurchaseOrder.setPoRejectReason(pgrnRejectReason);
            tinvPurchasePurchaseOrderRepository.save(tinvPurchasePurchaseOrder);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("createAutoGrnnumber")
    public String createAutoGrnnumber(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        int value;
        try {
            String Query = "SELECT ifnull(max(r.ppo_id), 0) FROM tinv_purchase_purchase_order r";
            System.out.println("max number" + Query);
            BigInteger temp = (BigInteger) entityManager.createNativeQuery(Query).getSingleResult();
            value = temp.intValue() + 1;
            return Integer.toString(value);
        } catch (Exception e) {
            return "1";
        }
    }

}
            
