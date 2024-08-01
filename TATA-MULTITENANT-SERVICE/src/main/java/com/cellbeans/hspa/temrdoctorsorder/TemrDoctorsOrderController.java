package com.cellbeans.hspa.temrdoctorsorder;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/temr_doctors_order")
public class TemrDoctorsOrderController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrDoctorsOrderRepository temrDoctorsOrderRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrDoctorsOrder temrDoctorsOrder) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrDoctorsOrder.getDoVisitId() != null) {
            temrDoctorsOrderRepository.save(temrDoctorsOrder);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
//        @RequestMapping("/autocomplete/{key}")
//	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//		Map<String, Object> automap  = new HashMap<String, Object>();
//		List<TemrDoctorsOrder> records;
//		records = temrDoctorsOrderRepository.findByServiceNameContains(key);
//		automap.put("record", records);
//		return automap;
//	}

    @RequestMapping("byid/{doId}")
    public TemrDoctorsOrder read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("doId") Long doId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrDoctorsOrder temrDoctorsOrder = temrDoctorsOrderRepository.getById(doId);
        return temrDoctorsOrder;
    }

    @RequestMapping("update")
    public TemrDoctorsOrder update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrDoctorsOrder temrDoctorsOrder) {
        TenantContext.setCurrentTenant(tenantName);
        return temrDoctorsOrderRepository.save(temrDoctorsOrder);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrDoctorsOrder> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "doId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrDoctorsOrderRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrDoctorsOrderRepository.findByDoServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{doId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("doId") Long doId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrDoctorsOrder temrDoctorsOrder = temrDoctorsOrderRepository.getById(doId);
        if (temrDoctorsOrder != null) {
            temrDoctorsOrder.setIsDeleted(true);
            temrDoctorsOrderRepository.save(temrDoctorsOrder);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
