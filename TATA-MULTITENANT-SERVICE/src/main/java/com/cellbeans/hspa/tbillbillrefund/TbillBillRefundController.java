package com.cellbeans.hspa.tbillbillrefund;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tbill_bill_refund")
public class TbillBillRefundController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillBillRefundRepository tbillBillRefundRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillBillRefund tbillBillRefund) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillBillRefund.getTbrBillId() != null) {
            tbillBillRefundRepository.save(tbillBillRefund);
            respMap.put("tbrId", String.valueOf(tbillBillRefund.getTbrId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{tbrId}")
    public TbillBillRefund read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tbrId") Long tbrId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillBillRefund tbillBillRefund = tbillBillRefundRepository.getById(tbrId);
        return tbillBillRefund;
    }

    @RequestMapping("update")
    public TbillBillRefund update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillBillRefund tbillBillRefund) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillRefundRepository.save(tbillBillRefund);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillBillRefund> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tbrId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillBillRefundRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillBillRefundRepository.findByIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{tbrId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tbrId") Long tbrId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillBillRefund tbillBillRefund = tbillBillRefundRepository.getById(tbrId);
        if (tbillBillRefund != null) {
            tbillBillRefund.setIsDeleted(true);
            tbillBillRefundRepository.save(tbillBillRefund);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
