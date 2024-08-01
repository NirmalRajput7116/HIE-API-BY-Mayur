package com.cellbeans.hspa.mbilltermspayment;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mbill_terms_payment")
public class MbillTermsPaymentController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillTermsPaymentRepository mbillTermsPaymentRepository;

    @Autowired
    private MbillTermsPaymentService mbillTermsPaymentService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillTermsPayment mbillTermsPayment) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillTermsPayment.getTpName() != null) {
            mbillTermsPaymentRepository.save(mbillTermsPayment);
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
        List<MbillTermsPayment> records;
        records = mbillTermsPaymentRepository.findByTpNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{tpId}")
    public MbillTermsPayment read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tpId") Long tpId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillTermsPayment mbillTermsPayment = mbillTermsPaymentRepository.getById(tpId);
        return mbillTermsPayment;
    }

    @RequestMapping("update")
    public MbillTermsPayment update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillTermsPayment mbillTermsPayment) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillTermsPaymentRepository.save(mbillTermsPayment);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillTermsPayment> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillTermsPaymentRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillTermsPaymentRepository.findByTpNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{tpId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tpId") Long tpId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillTermsPayment mbillTermsPayment = mbillTermsPaymentRepository.getById(tpId);
        if (mbillTermsPayment != null) {
            mbillTermsPayment.setIsDeleted(true);
            mbillTermsPaymentRepository.save(mbillTermsPayment);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mbillTermsPaymentService.getMbillTermsPaymentForDropdown(page, size, globalFilter);
        return items;
    }

}
            
