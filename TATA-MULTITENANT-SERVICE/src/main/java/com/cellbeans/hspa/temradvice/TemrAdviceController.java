package com.cellbeans.hspa.temradvice;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/temr_advice")
public class TemrAdviceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrAdviceRepository temrAdviceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrAdvice temrAdvice) {
        TenantContext.setCurrentTenant(tenantName);
        // if (temrAdvice.getAdviceName() != null)
        {
            temrAdviceRepository.save(temrAdvice);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        }/* else {
            respMap.put("success", "0");
			respMap.put("msg", "Failed To Add Null Field");
			return respMap;
		}*/
    }

/*    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TemrAdvice> records;
        records = temrAdviceRepository.findByAdviceAdviceIdAdviseNameContains(key);
        automap.put("record", records);
        return automap;
    }*/

    @RequestMapping("byid/{adviceId}")
    public TemrAdvice read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("adviceId") Long adviceId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrAdvice temrAdvice = temrAdviceRepository.getById(adviceId);
        return temrAdvice;
    }

    @RequestMapping("update")
    public TemrAdvice update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrAdvice temrAdvice) {
        TenantContext.setCurrentTenant(tenantName);
        return temrAdviceRepository.save(temrAdvice);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrAdvice> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "adviceId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrAdviceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return null;

          /*  return temrAdviceRepository.findByAdviceAdviceIdAdviseNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, new PageRequest(
                    Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.Direction.fromStringOrNull(sort), col));*/
        }

    }

    @PutMapping("delete/{adviceId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("adviceId") Long adviceId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrAdvice temrAdvice = temrAdviceRepository.getById(adviceId);
        if (temrAdvice != null) {
            temrAdvice.setDeleted(true);
            temrAdviceRepository.save(temrAdvice);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
