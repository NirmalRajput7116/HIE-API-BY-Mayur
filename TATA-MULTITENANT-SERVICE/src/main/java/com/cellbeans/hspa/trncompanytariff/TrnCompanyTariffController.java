package com.cellbeans.hspa.trncompanytariff;

import com.cellbeans.hspa.TenantContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_company_tariff")
public class TrnCompanyTariffController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnCompanyTariffRepository trnCompanyTariffRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnCompanyTariff trnCompanyTariff) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnCompanyTariff.getCtCompanyId().getCompanyId() > 0) {
            trnCompanyTariffRepository.save(trnCompanyTariff);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{ctId}")
    public TrnCompanyTariff read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ctId") Long ctId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnCompanyTariff trnCompanyTariff = trnCompanyTariffRepository.getById(ctId);
        return trnCompanyTariff;
    }

    @RequestMapping("update")
    public TrnCompanyTariff update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnCompanyTariff trnCompanyTariff) {
        TenantContext.setCurrentTenant(tenantName);
        TrnCompanyTariff alreadyExistTrnCompanyTariff = trnCompanyTariffRepository.findByCtCompanyIdCompanyIdAndCtTariffIdTariffId(trnCompanyTariff.getCtCompanyId().getCompanyId(), trnCompanyTariff.getCtTariffId().getTariffId());
        if (alreadyExistTrnCompanyTariff != null) {
            alreadyExistTrnCompanyTariff.setIsActive(true);
            alreadyExistTrnCompanyTariff.setIsDeleted(false);
            alreadyExistTrnCompanyTariff.setCtPriority(trnCompanyTariff.getCtPriority());
            return trnCompanyTariffRepository.save(alreadyExistTrnCompanyTariff);
        } else {
            trnCompanyTariff.setCtPriority(trnCompanyTariff.getCtPriority());
            return trnCompanyTariffRepository.save(trnCompanyTariff);
        }

    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnCompanyTariff> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ctId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnCompanyTariffRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return null; // trnCompanyTariffRepository.findByCtTariffIdTariffNameContainsOrCtCompanyIdCompanyIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @RequestMapping("bycompanyId/{companyId}")
    public List<TrnCompanyTariff> getBycompanyId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("companyId") Long companyId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnCompanyTariff> records;
        records = trnCompanyTariffRepository.findByCtCompanyIdCompanyIdEquals(companyId);
        return records;
    }

    @PutMapping("delete/{ctId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ctId") Long ctId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnCompanyTariff trnCompanyTariff = trnCompanyTariffRepository.getById(ctId);
        if (trnCompanyTariff != null) {
            trnCompanyTariff.setIsDeleted(true);
            trnCompanyTariffRepository.save(trnCompanyTariff);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("deactivate/{companyId}/{tariffId}")
    public Map<String, String> deactivateByTariffId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("companyId") Long companyId, @PathVariable("tariffId") Long tariffId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnCompanyTariff trnCompanyTariff = trnCompanyTariffRepository.findByCtCompanyIdCompanyIdAndCtTariffIdTariffId(companyId, tariffId);
        if (trnCompanyTariff != null) {
            trnCompanyTariff.setIsActive(false);
            trnCompanyTariff.setIsDeleted(true);
            trnCompanyTariffRepository.save(trnCompanyTariff);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
