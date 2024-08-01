package com.cellbeans.hspa.tbillrecieptpaymentmode;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tbill_reciept_payment_mode")
public class TbillRecieptPaymentModeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillRecieptPaymentModeRepository tbillRecieptPaymentModeRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TbillRecieptPaymentMode> tbillRecieptPaymentMode) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillRecieptPaymentMode.get(0).getRpmRecieptId() != null) {
            tbillRecieptPaymentModeRepository.saveAll(tbillRecieptPaymentMode);
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
        List<TbillRecieptPaymentMode> records;
        records = tbillRecieptPaymentModeRepository.findByRpmRecieptIdBrRecieptNumberContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{rpmId}")
    public TbillRecieptPaymentMode read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rpmId") Long rpmId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillRecieptPaymentMode tbillRecieptPaymentMode = tbillRecieptPaymentModeRepository.getById(rpmId);
        return tbillRecieptPaymentMode;
    }

    @RequestMapping("update")
    public TbillRecieptPaymentMode update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillRecieptPaymentMode tbillRecieptPaymentMode) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillRecieptPaymentModeRepository.save(tbillRecieptPaymentMode);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillRecieptPaymentMode> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "rpmId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillRecieptPaymentModeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillRecieptPaymentModeRepository.findByRpmPmIdPmNameContainsOrRpmRecieptIdBrRecieptNumberContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{rpmId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rpmId") Long rpmId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillRecieptPaymentMode tbillRecieptPaymentMode = tbillRecieptPaymentModeRepository.getById(rpmId);
        if (tbillRecieptPaymentMode != null) {
            tbillRecieptPaymentMode.setIsDeleted(true);
            tbillRecieptPaymentModeRepository.save(tbillRecieptPaymentMode);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
