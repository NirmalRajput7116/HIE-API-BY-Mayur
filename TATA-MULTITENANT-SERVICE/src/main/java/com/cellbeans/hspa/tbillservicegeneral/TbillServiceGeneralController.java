package com.cellbeans.hspa.tbillservicegeneral;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tbill_service_general")
public class TbillServiceGeneralController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillServiceGeneralRepository tbillServiceGeneralRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillServiceGeneral tbillServiceGeneral) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillServiceGeneral.getBsgBsId() != null) {
            tbillServiceGeneralRepository.save(tbillServiceGeneral);
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
        List<TbillServiceGeneral> records;
        records = tbillServiceGeneralRepository.findByBsgBsIdBsBillIdBillNumberContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bsgId}")
    public TbillServiceGeneral read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsgId") Long bsgId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillServiceGeneral tbillServiceGeneral = tbillServiceGeneralRepository.getById(bsgId);
        return tbillServiceGeneral;
    }

    @RequestMapping("update")
    public TbillServiceGeneral update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillServiceGeneral tbillServiceGeneral) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillServiceGeneralRepository.save(tbillServiceGeneral);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillServiceGeneral> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsgId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillServiceGeneralRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillServiceGeneralRepository.findByBsgBsIdBsBillIdBillNumberContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{bsgId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsgId") Long bsgId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillServiceGeneral tbillServiceGeneral = tbillServiceGeneralRepository.getById(bsgId);
        if (tbillServiceGeneral != null) {
            tbillServiceGeneral.setIsDeleted(true);
            tbillServiceGeneralRepository.save(tbillServiceGeneral);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}