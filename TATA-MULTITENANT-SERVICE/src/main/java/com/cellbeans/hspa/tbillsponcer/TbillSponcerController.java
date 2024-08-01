package com.cellbeans.hspa.tbillsponcer;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tbill_sponcer")
public class TbillSponcerController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillSponcerRepository tbillSponcerRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillSponcer tbillSponcer) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillSponcer.getBsBillId() != null) {
            tbillSponcerRepository.save(tbillSponcer);
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
        List<TbillSponcer> records;
        records = tbillSponcerRepository.findByBsBillIdBillNumberContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bsId}")
    public TbillSponcer read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsId") Long bsId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillSponcer tbillSponcer = tbillSponcerRepository.getById(bsId);
        return tbillSponcer;
    }

    @RequestMapping("update")
    public TbillSponcer update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillSponcer tbillSponcer) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillSponcerRepository.save(tbillSponcer);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillSponcer> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillSponcerRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillSponcerRepository.findByBsBillIdBillNumberContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{bsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsId") Long bsId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillSponcer tbillSponcer = tbillSponcerRepository.getById(bsId);
        if (tbillSponcer != null) {
            tbillSponcer.setIsDeleted(true);
            tbillSponcerRepository.save(tbillSponcer);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
