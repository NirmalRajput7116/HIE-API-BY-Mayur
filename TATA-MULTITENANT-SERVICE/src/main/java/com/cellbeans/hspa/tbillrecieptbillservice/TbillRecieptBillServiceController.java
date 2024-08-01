package com.cellbeans.hspa.tbillrecieptbillservice;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tbill_reciept_bill_service")
public class TbillRecieptBillServiceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillRecieptBillServiceRepository tbillRecieptBillServiceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TbillRecieptBillService> tbillRecieptBillService) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillRecieptBillService.get(0).getRbsServiceId() != null) {
            tbillRecieptBillServiceRepository.saveAll(tbillRecieptBillService);
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
        List<TbillRecieptBillService> records;
        records = tbillRecieptBillServiceRepository.findByRbsServiceIdServiceNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{rbsId}")
    public TbillRecieptBillService read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rbsId") Long rbsId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillRecieptBillService tbillRecieptBillService = tbillRecieptBillServiceRepository.getById(rbsId);
        return tbillRecieptBillService;
    }

    @RequestMapping("update")
    public TbillRecieptBillService update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillRecieptBillService tbillRecieptBillService) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillRecieptBillServiceRepository.save(tbillRecieptBillService);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillRecieptBillService> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "rbsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillRecieptBillServiceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillRecieptBillServiceRepository.findByRbsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{rbsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rbsId") Long rbsId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillRecieptBillService tbillRecieptBillService = tbillRecieptBillServiceRepository.getById(rbsId);
        if (tbillRecieptBillService != null) {
            tbillRecieptBillService.setIsDeleted(true);
            tbillRecieptBillServiceRepository.save(tbillRecieptBillService);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
