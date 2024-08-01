package com.cellbeans.hspa.trnbloodbank;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.trnbloodstock.TrnBloodStock;
import com.cellbeans.hspa.trnbloodstock.TrnBloodStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_blood_bank")
public class TrnBloodBankController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnBloodBankRepository trnBloodBankRepository;

    @Autowired
    TrnBloodStockRepository trnBloodStockRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnBloodBank trnBloodBank) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnBloodBank.getBbQuantity() != 0) {
            TrnBloodStock currentstockdetail = new TrnBloodStock();
            try {
                currentstockdetail = trnBloodStockRepository.findByBsBloodgroupIdBloodgroupIdEqualsAndBsUnitEqualsAndIsActiveTrueAndIsDeletedFalse(trnBloodBank.getBbBloodgroupId().getBloodgroupId(), trnBloodBank.getBbUnit());
                currentstockdetail.setBsNoOf(currentstockdetail.getBsNoOf() - trnBloodBank.getBbQuantity());
                trnBloodStockRepository.save(currentstockdetail);
                trnBloodBankRepository.save(trnBloodBank);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } catch (Exception e) {
                respMap.put("success", "0");
                respMap.put("msg", "Stock Not Avalibal");
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
        List<TrnBloodBank> records;
        records = trnBloodBankRepository.findByBbQuantityContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bbId}")
    public TrnBloodBank read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bbId") Long bbId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnBloodBank trnBloodBank = trnBloodBankRepository.getById(bbId);
        return trnBloodBank;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnBloodBank trnBloodBank) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnBloodBank.getBbQuantity() != 0) {
            TrnBloodStock currentstockdetail = null;
            TrnBloodStock currentstockdetailnew = null;
            TrnBloodBank currentdetail = new TrnBloodBank();
            try {
                currentdetail = trnBloodBankRepository.getById(trnBloodBank.getBbId());
                currentstockdetail = new TrnBloodStock();
                currentstockdetail = trnBloodStockRepository.findByBsBloodgroupIdBloodgroupIdEqualsAndBsUnitEqualsAndIsActiveTrueAndIsDeletedFalse(trnBloodBank.getBbBloodgroupId().getBloodgroupId(), trnBloodBank.getBbUnit());
                currentstockdetail.setBsNoOf(currentstockdetail.getBsNoOf() + currentdetail.getBbQuantity());
                trnBloodStockRepository.save(currentstockdetail);
                currentstockdetailnew = new TrnBloodStock();
                currentstockdetailnew = trnBloodStockRepository.findByBsBloodgroupIdBloodgroupIdEqualsAndBsUnitEqualsAndIsActiveTrueAndIsDeletedFalse(trnBloodBank.getBbBloodgroupId().getBloodgroupId(), trnBloodBank.getBbUnit());
                currentstockdetailnew.setBsNoOf(currentstockdetailnew.getBsNoOf() - trnBloodBank.getBbQuantity());
                trnBloodStockRepository.save(currentstockdetailnew);
                trnBloodBankRepository.save(trnBloodBank);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } catch (Exception e) {
                respMap.put("success", "0");
                respMap.put("msg", "Stock Not Avalibal");
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
    public Iterable<TrnBloodBank> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bbId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnBloodBankRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnBloodBankRepository.findByAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{bbId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bbId") Long bbId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnBloodBank trnBloodBank = trnBloodBankRepository.getById(bbId);
        if (trnBloodBank != null) {
//			trnBloodBank.setIsDeleted(true);
            trnBloodBankRepository.save(trnBloodBank);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}

