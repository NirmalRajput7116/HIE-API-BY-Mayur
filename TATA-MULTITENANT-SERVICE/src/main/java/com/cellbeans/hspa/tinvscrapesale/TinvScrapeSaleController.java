package com.cellbeans.hspa.tinvscrapesale;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_scrape_sale")
public class TinvScrapeSaleController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvScrapeSaleRepository tinvScrapeSaleRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvScrapeSale tinvScrapeSale) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvScrapeSale.getSsStaffId() != null) {
            tinvScrapeSaleRepository.save(tinvScrapeSale);
            respMap.put("ssId", String.valueOf(tinvScrapeSale.getSsId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{ssId}")
    public TinvScrapeSale read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ssId") Long ssId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvScrapeSale tinvScrapeSale = tinvScrapeSaleRepository.getById(ssId);
        return tinvScrapeSale;
    }

    @RequestMapping("update")
    public TinvScrapeSale update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvScrapeSale tinvScrapeSale) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvScrapeSaleRepository.save(tinvScrapeSale);
    }

    @PutMapping("delete/{ssId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ssId") Long ssId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvScrapeSale tinvScrapeSale = tinvScrapeSaleRepository.getById(ssId);
        if (tinvScrapeSale != null) {
            tinvScrapeSale.setIsDeleted(true);
            tinvScrapeSaleRepository.save(tinvScrapeSale);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("search")
    public List<TinvScrapeSale> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchScrapeSale searchScrapeSale) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchScrapeSale == null) {
            SearchScrapeSale sb = new SearchScrapeSale();
            searchScrapeSale = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        if (searchScrapeSale.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchScrapeSale.setSearchFromDate(sDate1);
        }
        if (searchScrapeSale.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchScrapeSale.setSearchToDate(date);
        }
        if (searchScrapeSale.getSearchStoreId().getStoreId() != 0)
            itemStore = "AND i.ssStoreId.storeId=" + searchScrapeSale.getSearchStoreId().getStoreId() + "";
        if (searchScrapeSale.getSearchSupplierId().getSupplierId() != 0)
            itemSupplier = "AND i.ssSupplierId.supplierId=" + searchScrapeSale.getSearchSupplierId().getSupplierId() + "";
        queryInya = "Select i from TinvScrapeSale i Where  i.ssUnitId.unitId=" + searchScrapeSale.getUnitId() + " And date(i.createdDate) between   " + " ' " + searchScrapeSale.getSearchFromDate() + " ' AND  " + "'" + searchScrapeSale.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.ssId desc";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.ssId) from TinvScrapeSale i Where i.ssUnitId.unitId=" + searchScrapeSale.getUnitId() + " And date(i.createdDate) between   " + " ' " + searchScrapeSale.getSearchFromDate() + " ' AND  " + "'" + searchScrapeSale.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.ssId desc").getSingleResult();
        List<TinvScrapeSale> ItemList = entityManager.createQuery(queryInya, TinvScrapeSale.class).setFirstResult(searchScrapeSale.getOffset()).setMaxResults(searchScrapeSale.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

}
            
