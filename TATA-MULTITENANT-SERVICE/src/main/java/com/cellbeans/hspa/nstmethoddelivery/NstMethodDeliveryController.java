package com.cellbeans.hspa.nstmethoddelivery;

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
@RequestMapping("/nst_method_delivery")
public class NstMethodDeliveryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    NstMethodDeliveryRepository nstMethodDeliveryRepository;
    @Autowired
    private NstMethodDeliveryService nstMethodDeliveryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NstMethodDelivery nstMethodDelivery) {
        TenantContext.setCurrentTenant(tenantName);
        if (nstMethodDelivery.getMdName() != null) {
            nstMethodDeliveryRepository.save(nstMethodDelivery);
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
        List<NstMethodDelivery> records;
        records = nstMethodDeliveryRepository.findByMdNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{mdId}")
    public NstMethodDelivery read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mdId") Long mdId) {
        TenantContext.setCurrentTenant(tenantName);
        NstMethodDelivery nstMethodDelivery = nstMethodDeliveryRepository.getById(mdId);
        return nstMethodDelivery;
    }

    @RequestMapping("update")
    public NstMethodDelivery update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NstMethodDelivery nstMethodDelivery) {
        TenantContext.setCurrentTenant(tenantName);
        return nstMethodDeliveryRepository.save(nstMethodDelivery);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<NstMethodDelivery> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "mdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return nstMethodDeliveryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return nstMethodDeliveryRepository.findByMdNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{mdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mdId") Long mdId) {
        TenantContext.setCurrentTenant(tenantName);
        NstMethodDelivery nstMethodDelivery = nstMethodDeliveryRepository.getById(mdId);
        if (nstMethodDelivery != null) {
            nstMethodDelivery.setIsDeleted(true);
            nstMethodDeliveryRepository.save(nstMethodDelivery);
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
        List<Tuple> items = nstMethodDeliveryService.getNstMethodDeliveryForDropdown(page, size, globalFilter);
        return items;
    }

}
            
