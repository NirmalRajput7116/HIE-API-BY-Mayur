package com.cellbeans.hspa.trnbloodstock;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_blood_stock")
public class TrnBloodStockController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnBloodStockRepository trnBloodStockRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnBloodStock trnBloodStock) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnBloodStock.getBsNoOf() != 0) {
            TrnBloodStock currentstockdetail = new TrnBloodStock();
            try {
                currentstockdetail = trnBloodStockRepository.findByBsBloodgroupIdBloodgroupIdEqualsAndBsUnitEqualsAndIsActiveTrueAndIsDeletedFalse(trnBloodStock.getBsBloodgroupId().getBloodgroupId(), trnBloodStock.getBsUnit());
                currentstockdetail.setBsNoOf(currentstockdetail.getBsNoOf() + trnBloodStock.getBsNoOf());
                trnBloodStockRepository.save(currentstockdetail);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } catch (Exception e) {
                trnBloodStockRepository.save(trnBloodStock);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            }

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
        List<TrnBloodStock> records;
        records = trnBloodStockRepository.findByBsNoOfContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bsId}")
    public TrnBloodStock read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsId") Long bsId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnBloodStock trnBloodStock = trnBloodStockRepository.getById(bsId);
        return trnBloodStock;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnBloodStock trnBloodStock) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnBloodStock.getBsNoOf() != 0) {
            TrnBloodStock currentstockdetail = new TrnBloodStock();
            try {
                currentstockdetail = trnBloodStockRepository.findByBsBloodgroupIdBloodgroupIdEqualsAndBsUnitEqualsAndIsActiveTrueAndIsDeletedFalse(trnBloodStock.getBsBloodgroupId().getBloodgroupId(), trnBloodStock.getBsUnit());
                currentstockdetail.setBsNoOf(currentstockdetail.getBsNoOf() - trnBloodStock.getBsNoOf());
                trnBloodStockRepository.save(currentstockdetail);
                respMap.put("success", "1");
                respMap.put("msg", "Deduct Successfully");
                return respMap;
            } catch (Exception e) {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
                return respMap;
            }
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnBloodStock> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnBloodStockRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnBloodStockRepository.findByAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{bsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsId") Long bsId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnBloodStock trnBloodStock = trnBloodStockRepository.getById(bsId);
        if (trnBloodStock != null) {
            //trnBloodStock.setIsDeleted(true);
            trnBloodStockRepository.save(trnBloodStock);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}

