package com.cellbeans.hspa.tinvpurchaseitemenquiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/tinv_purchase_item_enquiry")
public class TinvPurchaseItemEnquiryController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvPurchaseItemEnquiryRepository tinvPurchaseItemEnquiryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseItemEnquiry tinvPurchaseItemEnquiry) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPurchaseItemEnquiry.getPieStoreId() != null) {
//            tinvPurchaseItemEnquiry.setPieEnquiryNo(tinvPurchaseItemEnquiryRepository.makeEQNumber(tinvPurchaseItemEnquiry.getPieStoreId().getStoreName()));
            tinvPurchaseItemEnquiry.setPieEnquiryNo(tinvPurchaseItemEnquiryRepository.makeEQNumberNew(tinvPurchaseItemEnquiry.getPieStoreId().getStorePrefix()));
            tinvPurchaseItemEnquiry = tinvPurchaseItemEnquiryRepository.save(tinvPurchaseItemEnquiry);
            respMap.put("PieId", String.valueOf(tinvPurchaseItemEnquiry.getPieId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("eqNumber", tinvPurchaseItemEnquiry.getPieEnquiryNo());
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            respMap.put("eqNumber", "");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TinvPurchaseItemEnquiry> records;
        records = tinvPurchaseItemEnquiryRepository.findByPieEnquiryNoContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{pieId}")
    public TinvPurchaseItemEnquiry read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pieId") Long pieId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseItemEnquiry tinvPurchaseItemEnquiry = tinvPurchaseItemEnquiryRepository.getById(pieId);
        return tinvPurchaseItemEnquiry;
    }

    @RequestMapping("update")
    public TinvPurchaseItemEnquiry update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseItemEnquiry tinvPurchaseItemEnquiry) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseItemEnquiryRepository.save(tinvPurchaseItemEnquiry);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvPurchaseItemEnquiry> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pieId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPurchaseItemEnquiryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvPurchaseItemEnquiryRepository.findByPicNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{pieId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pieId") Long pieId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseItemEnquiry tinvPurchaseItemEnquiry = tinvPurchaseItemEnquiryRepository.getById(pieId);
        if (tinvPurchaseItemEnquiry != null) {
            tinvPurchaseItemEnquiry.setIsDeleted(true);
            tinvPurchaseItemEnquiryRepository.save(tinvPurchaseItemEnquiry);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("allenquiry/{data}")
    public List<TinvPurchaseItemEnquiry> getEnquiryByAll(@RequestHeader("X-tenantId") String tenantName, @PathVariable("data") String data) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseItemEnquiryRepository.findByPieEnquiryNoContainsOrPieStoreIdStoreNameContainsAndIsActiveTrueAndIsDeletedFalse(data, data);

    }

    @RequestMapping("allenquiryByUnitId/{data}/{unitId}")
    public List<TinvPurchaseItemEnquiry> allenquiryByUnitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("data") String data, @PathVariable("unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseItemEnquiryRepository.findByPieEnquiryNoContainsOrPieStoreIdStoreNameContainsAndPieUnitIdUnitId(data, unitId);

    }

    @RequestMapping("search")
    public List<TinvPurchaseItemEnquiry> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchEnquiryItem searchEnquiryItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchEnquiryItem == null) {
            SearchEnquiryItem sb = new SearchEnquiryItem();
            searchEnquiryItem = sb;
        }
        String queryInya = "";
        String itemStore = "";
        if (searchEnquiryItem.getSearchPieEnquiryNo() == null)
            searchEnquiryItem.setSearchPieEnquiryNo("");
        if (searchEnquiryItem.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchEnquiryItem.setSearchFromDate(sDate1);
        }
        if (searchEnquiryItem.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchEnquiryItem.setSearchToDate(date);
        }
        if (searchEnquiryItem.getSearchStoreId().getStoreId() != 0)
            itemStore = "AND i.pieStoreId.storeId=" + searchEnquiryItem.getSearchStoreId().getStoreId() + "";
        queryInya = "Select i from TinvPurchaseItemEnquiry i Where i.pieUnitId.unitId=" + searchEnquiryItem.getUnitId() + " And i.pieEnquiryNo like '%" + searchEnquiryItem.getSearchPieEnquiryNo() + "%' AND date(i.createdDate) between   " + " ' " + searchEnquiryItem.getSearchFromDate() + " ' AND  " + "'" + searchEnquiryItem.getSearchToDate() + " ' " + itemStore + "  order by i.pieId desc  ";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.pieId) from TinvPurchaseItemEnquiry i Where i.pieUnitId.unitId=" + searchEnquiryItem.getUnitId() + " And i.pieEnquiryNo like '%" + searchEnquiryItem.getSearchPieEnquiryNo() + "%' AND date(i.createdDate) between   " + " ' " + searchEnquiryItem.getSearchFromDate() + " ' AND  " + "'" + searchEnquiryItem.getSearchToDate() + " ' " + itemStore + "  order by i.pieId desc  ").getSingleResult();
        List<TinvPurchaseItemEnquiry> ItemList = entityManager.createQuery(queryInya, TinvPurchaseItemEnquiry.class).setFirstResult((searchEnquiryItem.getOffset() - 1) * 10).setMaxResults(searchEnquiryItem.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @Transactional
    @RequestMapping("createTemplate")
    public Map<String, String> createTemplate(@RequestHeader("X-tenantId") String tenantName, @RequestBody EnquiryTemplate enquiryTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        if (enquiryTemplate.getTemplateName() != null) {
            entityManager.persist(enquiryTemplate);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            respMap.put("eqNumber", "");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("listTemplate")
    public List<EnquiryTemplate> listTemplate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId", required = false) long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select p from EnquiryTemplate p where p.templateUnitId.unitId=" + unitId + " order by p.etId desc";
        return entityManager.createQuery(query, EnquiryTemplate.class).getResultList();
    }

    @RequestMapping("approveENQ/{pieId}")
    public Map<String, String> onApproveItemEnquiry(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pieId") Long pieId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseItemEnquiry tinvPurchaseItemEnquiry = tinvPurchaseItemEnquiryRepository.getById(pieId);
        if (tinvPurchaseItemEnquiry != null) {
            if (tinvPurchaseItemEnquiry.getPieIsApproved())
                tinvPurchaseItemEnquiry.setPieIsApproved(false);
            else
                tinvPurchaseItemEnquiry.setPieIsApproved(true);
            tinvPurchaseItemEnquiryRepository.save(tinvPurchaseItemEnquiry);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("rejectENQ")
    public Map<String, String> onRejectItemEnquiry(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "pieId") Long pieId, @RequestParam(value = "peiRejectReason") String peiRejectReason) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseItemEnquiry tinvPurchaseItemEnquiry = tinvPurchaseItemEnquiryRepository.getById(pieId);
        if (tinvPurchaseItemEnquiry != null) {
            if (tinvPurchaseItemEnquiry.getPieIsRejected()) {
                tinvPurchaseItemEnquiry.setPieIsRejected(false);
            } else {
                tinvPurchaseItemEnquiry.setPieIsRejected(true);
                tinvPurchaseItemEnquiry.setPeiRejectReason(peiRejectReason);
                tinvPurchaseItemEnquiryRepository.save(tinvPurchaseItemEnquiry);
                respMap.put("msg", "Operation Successful");
                respMap.put("success", "1");
            }
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}
            
