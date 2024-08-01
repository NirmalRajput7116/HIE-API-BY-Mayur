package com.cellbeans.hspa.tinvopeningbalance;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_opening_balance")
public class TinvOpeningBalanceController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvOpeningBalanceRepository tinvOpeningBalanceRepository;
    @Autowired
    TinvOpeningBalanceItemRepository tinvOpeningBalanceItemRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvOpeningBalance tinvOpeningBalance) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvOpeningBalance.getObTotalAmount() >= 0) {
            TinvOpeningBalance openingBalance = new TinvOpeningBalance();
            openingBalance = tinvOpeningBalanceRepository.save(tinvOpeningBalance);
            respMap.put("obiobId", Long.toString(openingBalance.getObId()));
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
        List<TinvOpeningBalance> records;
        records = tinvOpeningBalanceRepository.findByObTotalAmountContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{obId}")
    public TinvOpeningBalance read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("obId") Long obId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvOpeningBalance tinvOpeningBalance = tinvOpeningBalanceRepository.getById(obId);
        return tinvOpeningBalance;
    }

    @RequestMapping("update")
    public TinvOpeningBalance update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvOpeningBalance tinvOpeningBalance) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceRepository.save(tinvOpeningBalance);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvOpeningBalance> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "obId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvOpeningBalanceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvOpeningBalanceRepository.findByObTotalAmountContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{obId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("obId") Long obId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvOpeningBalance tinvOpeningBalance = tinvOpeningBalanceRepository.getById(obId);
        if (tinvOpeningBalance != null) {
            tinvOpeningBalance.setIsDeleted(true);
            tinvOpeningBalanceRepository.save(tinvOpeningBalance);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("search")
    public List<TinvOpeningBalance> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchOpeningBalance searchOpeningBalance) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchOpeningBalance == null) {
            SearchOpeningBalance sb = new SearchOpeningBalance();
            searchOpeningBalance = sb;
        }
        String queryInya = "";
        if (searchOpeningBalance.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchOpeningBalance.setSearchFromDate(sDate1);
        }
        if (searchOpeningBalance.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchOpeningBalance.setSearchToDate(date);
        }
//        if(searchOpeningBalance.getSearchUnitId()){
//
//        }if(searchOpeningBalance.getSearchUnitId()){
//
//        }
        queryInya = "Select i from TinvOpeningBalance i Where i.isActive=1 AND i.isDeleted=0 AND i.openingBalanceUnitId.unitId=" + searchOpeningBalance.getUnitId() + " And date(i.createdDate) between " + " ' " + searchOpeningBalance.getSearchFromDate() + " ' AND  " + "'" + searchOpeningBalance.getSearchToDate() + "'" + " order by i.obId desc";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.obId) from TinvOpeningBalance i Where i.isActive=1 AND i.isDeleted=0 AND i.openingBalanceUnitId.unitId=" + searchOpeningBalance.getUnitId() + " And date(i.createdDate) between   " + " ' " + searchOpeningBalance.getSearchFromDate() + " ' AND  " + "'" + searchOpeningBalance.getSearchToDate() + "'" + " order by i.obId desc").getSingleResult();
        List<TinvOpeningBalance> ItemList = entityManager.createQuery(queryInya, TinvOpeningBalance.class).setFirstResult(((searchOpeningBalance.getOffset() - 1) * searchOpeningBalance.getLimit())).setMaxResults(searchOpeningBalance.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @GetMapping("freezById/{id}")
    public Map<String, String> getListOfItemById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select p from TinvOpeningBalanceItem p where p.obiobId.obId=" + id;
        TinvOpeningBalance updateObj = tinvOpeningBalanceRepository.getById(id);
        List<TinvOpeningBalanceItem> obiList = entityManager.createQuery(query, TinvOpeningBalanceItem.class).getResultList();
        if (obiList.size() > 0) {
            updateObj.setIsApproved(true);
            for (TinvOpeningBalanceItem ob : obiList) {
                ob.setIsApproved(true);
            }
            tinvOpeningBalanceRepository.save(updateObj);
            tinvOpeningBalanceItemRepository.saveAll(obiList);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("deleteRecordById")
    public Map<String, String> deleteRecordById(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvOpeningBalance tinvOpeningBalance) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvOpeningBalanceItem> openingBalanceItemsList = tinvOpeningBalanceItemRepository.findAllByObiobIdObIdAndIsActiveTrueAndIsDeletedFalse(tinvOpeningBalance.getObId());
        try {
            if (openingBalanceItemsList.size() > 0) {
                tinvOpeningBalanceItemRepository.deleteAllInBatch(openingBalanceItemsList);
                tinvOpeningBalanceRepository.deleteById(tinvOpeningBalance.getObId());
                respMap.put("msg", "Operation Successful");
                respMap.put("success", "1");
            } else {
                respMap.put("msg", "Operation Failed");
                respMap.put("success", "0");
            }
        } catch (Exception e) {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
