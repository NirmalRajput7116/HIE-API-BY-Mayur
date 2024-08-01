package com.cellbeans.hspa.tnstpostmortem;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tnst_post_mortem")
public class TnstPostMortemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TnstPostMortemRepository tnstPostMortemRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstPostMortem tnstPostMortem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tnstPostMortem.getPcNumber() != 0) {
            tnstPostMortemRepository.save(tnstPostMortem);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
        
  /*      @RequestMapping("/autocomplete/{key}")
	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
		Map<String, Object> automap  = new HashMap<String, Object>();
		List<TnstPostMortem> records;
		records = tnstPostMortemRepository.findByContains(key);
		automap.put("record", records);
		return automap;
	}*/

    @RequestMapping("byid/{pcId}")
    public TnstPostMortem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pcId") Long pcId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstPostMortem tnstPostMortem = tnstPostMortemRepository.getById(pcId);
        return tnstPostMortem;
    }

    @RequestMapping("update")
    public TnstPostMortem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstPostMortem tnstPostMortem) {
        TenantContext.setCurrentTenant(tenantName);
        return tnstPostMortemRepository.save(tnstPostMortem);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TnstPostMortem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                         @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                         @RequestParam(value = "qString", required = false) Long qString,
                                         @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                         @RequestParam(value = "col", required = false, defaultValue = "pcId") String col,
                                         @RequestParam(value = "search", required = false) String search) {
        TenantContext.setCurrentTenant(tenantName);
        if (search == null || search.equals("")) {
            return tnstPostMortemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tnstPostMortemRepository.findAllByPcAuthorizedStaffIdStaffUserIdUserFullnameContainsOrPcPatientNameContainsAndPcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(search, search, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{pcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pcId") Long pcId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstPostMortem tnstPostMortem = tnstPostMortemRepository.getById(pcId);
        if (tnstPostMortem != null) {
            tnstPostMortem.setDeleted(true);
            tnstPostMortemRepository.save(tnstPostMortem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
