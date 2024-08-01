package com.cellbeans.hspa.TrnVisitServiceService;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_visit_service_service_id")
public class TrnVisitServiceServiceController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnVisitServiceServiceRepository trnVisitServiceServiceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnVisitServiceService trnVisitServiceService) {
        TenantContext.setCurrentTenant(tenantName);
        if (true) {
            trnVisitServiceServiceRepository.save(trnVisitServiceService);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnVisitServiceService> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "trnVsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnVisitServiceServiceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.Direction.fromString(sort), "trnVsId"));

        } else {
            return trnVisitServiceServiceRepository.findByServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.Direction.fromString(sort), "trnVsId"));
            /*            return mbillServiceRepository.findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col))); removed old method as required filter by subGroupId also*/
        }
    }

   /* @RequestMapping("byid/{TrnVisitServiceService}")
    public List<TrnVisitServiceService> pTaxbyID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("TrnVisitServiceService") Long trnVisitServiceService) {
        List<TrnVisitServiceService> records;
        records = trnVisitServiceServiceRepository.findByVsIdEquals(trnVisitServiceService);
        return records;
    *//*public TinvTaxPrimaryTax read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("invTax") Long invTax) {
        TinvTaxPrimaryTax tinvTaxPrimaryTax = tinvTaxPrimaryTaxRepository.getById(invTax);
        return tinvTaxPrimaryTax;*//*
    }*/

    @PutMapping("delete/{trnvsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("trnvsId") Long trnvsId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnVisitServiceService trnVisitServiceService = trnVisitServiceServiceRepository.getById(trnvsId);
        if (trnVisitServiceService != null) {
            trnVisitServiceService.setDeleted(true);
            trnVisitServiceServiceRepository.save(trnVisitServiceService);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
//    @GetMapping
//    @RequestMapping("list")
//    public Iterable<TrnVisitServiceService> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "trnVsId") String col) {
//            return trnVisitServiceServiceRepository.findAllByIsActiveTrueAndIsDeletedFalse(new PageRequest(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.Direction.fromStringOrNull(sort), "trnVsId"));
//    }
//
//   /* @RequestMapping("byid/{TrnVisitServiceService}")
//    public List<TrnVisitServiceService> pTaxbyID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("TrnVisitServiceService") Long trnVisitServiceService) {
//        List<TrnVisitServiceService> records;
//        records = trnVisitServiceServiceRepository.findByVsIdEquals(trnVisitServiceService);
//        return records;
//    *//*public TinvTaxPrimaryTax read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("invTax") Long invTax) {
//        TinvTaxPrimaryTax tinvTaxPrimaryTax = tinvTaxPrimaryTaxRepository.getById(invTax);
//        return tinvTaxPrimaryTax;*//*
//    }*/
}

