package com.cellbeans.hspa.mbillservicecodetype;

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
@RequestMapping("/mbill_service_code_type")
public class MbillServiceCodeTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillServiceCodeTypeRepository mbillServiceCodeTypeRepository;

    @Autowired
    private MbillServiceCodeTypeService mbillServiceCodeTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillServiceCodeType mbillServiceCodeType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillServiceCodeType.getSctName() != null) {
            if (mbillServiceCodeTypeRepository.findByAllOrderByServiceCodeType(mbillServiceCodeType.getSctName()) == 0) {
                mbillServiceCodeTypeRepository.save(mbillServiceCodeType);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
            }
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
        List<MbillServiceCodeType> records;
        records = mbillServiceCodeTypeRepository.findBySctNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{sctId}")
    public MbillServiceCodeType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sctId") Long sctId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillServiceCodeType mbillServiceCodeType = mbillServiceCodeTypeRepository.getById(sctId);
        return mbillServiceCodeType;
    }

    @RequestMapping("update")
    public MbillServiceCodeType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillServiceCodeType mbillServiceCodeType) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillServiceCodeTypeRepository.save(mbillServiceCodeType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillServiceCodeType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "sctId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillServiceCodeTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillServiceCodeTypeRepository.findBySctNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{sctId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sctId") Long sctId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillServiceCodeType mbillServiceCodeType = mbillServiceCodeTypeRepository.getById(sctId);
        if (mbillServiceCodeType != null) {
            mbillServiceCodeType.setIsDeleted(true);
            mbillServiceCodeTypeRepository.save(mbillServiceCodeType);
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
        List<Tuple> items = mbillServiceCodeTypeService.getMbillServiceCodeTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
