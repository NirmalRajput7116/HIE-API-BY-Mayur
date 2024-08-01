package com.cellbeans.hspa.trnopdipd;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_opd_ipd")
public class TrnOpdIpdController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOpdIpdRepository trnOpdIpdRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOpdIpd trnOpdIpd) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOpdIpd.getOiStatus() != null) {
            trnOpdIpdRepository.save(trnOpdIpd);
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
        List<TrnOpdIpd> records;
        records = trnOpdIpdRepository.findByOiStatusContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{oiId}")
    public TrnOpdIpd read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("oiId") Long oiId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOpdIpd trnOpdIpd = trnOpdIpdRepository.getById(oiId);
        return trnOpdIpd;
    }

    @RequestMapping("update")
    public TrnOpdIpd update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOpdIpd trnOpdIpd) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOpdIpdRepository.save(trnOpdIpd);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnOpdIpd> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "oiId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnOpdIpdRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnOpdIpdRepository.findByOiStatusAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @PutMapping("delete/{oiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("oiId") Long oiId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOpdIpd trnOpdIpd = trnOpdIpdRepository.getById(oiId);
        if (trnOpdIpd != null) {
            trnOpdIpd.setIsDeleted(true);
            trnOpdIpdRepository.save(trnOpdIpd);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
