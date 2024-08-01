package com.cellbeans.hspa.maccurrency;

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
@RequestMapping("/mac_currency")
public class MacCurrencyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MacCurrencyRepository macCurrencyRepository;

    @Autowired
    private MacCurrencyService macCurrencyService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MacCurrency macCurrency) {
        TenantContext.setCurrentTenant(tenantName);
        if (macCurrency.getCurrencyName() != null) {
            macCurrencyRepository.save(macCurrency);
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
        List<MacCurrency> records;
        records = macCurrencyRepository.findByCurrencyNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{currencyId}")
    public MacCurrency read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("currencyId") Long currencyId) {
        TenantContext.setCurrentTenant(tenantName);
        MacCurrency macCurrency = macCurrencyRepository.getById(currencyId);
        return macCurrency;
    }

    @RequestMapping("update")
    public MacCurrency update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MacCurrency macCurrency) {
        TenantContext.setCurrentTenant(tenantName);
        return macCurrencyRepository.save(macCurrency);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MacCurrency> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "currencyId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return macCurrencyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return macCurrencyRepository.findByCurrencyNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{currencyId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("currencyId") Long currencyId) {
        TenantContext.setCurrentTenant(tenantName);
        MacCurrency macCurrency = macCurrencyRepository.getById(currencyId);
        if (macCurrency != null) {
            macCurrency.setIsDeleted(true);
            macCurrencyRepository.save(macCurrency);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping("/default")
    public MacCurrency readDefault(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return macCurrencyRepository.findByIsCurrencyDefaultTrueAndIsActiveTrueAndIsDeletedFalse();
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = macCurrencyService.getMacCurrencyForDropdown(page, size, globalFilter);
        return items;
    }

}
            