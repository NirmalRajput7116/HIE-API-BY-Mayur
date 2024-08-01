package com.cellbeans.hspa.mbilladvanceagainst;

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
@RequestMapping("/mbill_advance_against")
public class MbillAdvanceAgainstController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillAdvanceAgainstRepository mbillAdvanceAgainstRepository;

    @Autowired
    private MbillAdvanceAgainstService mbillAdvanceAgainstService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillAdvanceAgainst mbillAdvanceAgainst) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillAdvanceAgainst.getAaName() != null) {
            mbillAdvanceAgainstRepository.save(mbillAdvanceAgainst);
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
        List<MbillAdvanceAgainst> records;
        records = mbillAdvanceAgainstRepository.findByAaNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{aaId}")
    public MbillAdvanceAgainst read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("aaId") Long aaId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillAdvanceAgainst mbillAdvanceAgainst = mbillAdvanceAgainstRepository.getById(aaId);
        return mbillAdvanceAgainst;
    }

    @RequestMapping("update")
    public MbillAdvanceAgainst update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillAdvanceAgainst mbillAdvanceAgainst) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillAdvanceAgainstRepository.save(mbillAdvanceAgainst);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillAdvanceAgainst> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "aaId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillAdvanceAgainstRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillAdvanceAgainstRepository.findByAaNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{aaId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("aaId") Long aaId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillAdvanceAgainst mbillAdvanceAgainst = mbillAdvanceAgainstRepository.getById(aaId);
        if (mbillAdvanceAgainst != null) {
            mbillAdvanceAgainst.setIsDeleted(true);
            mbillAdvanceAgainstRepository.save(mbillAdvanceAgainst);
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
        List<Tuple> items = mbillAdvanceAgainstService.getMbillAdvanceAgainstDropdown(page, size, globalFilter);
        return items;
    }

}
            
