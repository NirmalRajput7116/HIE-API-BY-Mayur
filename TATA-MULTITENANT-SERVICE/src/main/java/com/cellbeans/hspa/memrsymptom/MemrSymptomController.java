package com.cellbeans.hspa.memrsymptom;

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
@RequestMapping("/memr_symptom")
public class MemrSymptomController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrSymptomRepository memrSymptomRepository;

    @Autowired
    private MemrSymptomService memrSymptomService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrSymptom memrSymptom) {
        TenantContext.setCurrentTenant(tenantName);
        MemrSymptom memrSymp = memrSymptomRepository.findBySymptomNameAndIsActiveTrueAndIsDeletedFalse(memrSymptom.getSymptomName());
        if (memrSymp != null) {
            respMap.put("success", "3");
            respMap.put("msg", "Symptom - '" + memrSymptom.getSymptomName() + "' already Present");
            return respMap;
        } else {
            if (memrSymptom.getSymptomName() != null) {
                memrSymptomRepository.save(memrSymptom);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
                return respMap;
            }

        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MemrSymptom> records;
        records = memrSymptomRepository.findBySymptomNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{symptomId}")
    public MemrSymptom read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("symptomId") Long symptomId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrSymptom memrSymptom = memrSymptomRepository.getById(symptomId);
        return memrSymptom;
    }

    @RequestMapping("update")
    public MemrSymptom update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrSymptom memrSymptom) {
        TenantContext.setCurrentTenant(tenantName);
        return memrSymptomRepository.save(memrSymptom);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrSymptom> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "symptomId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrSymptomRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrSymptomRepository.findBySymptomNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("search")
    public Map<String, Object> search(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> searchmap = new HashMap<String, Object>();
        List<MemrSymptom> records;
        /*if (qString == null || qString.equals("")) {
            return memrSymptomRepository.findAllByIsActiveTrueAndIsDeletedFalse(qString);

        } else {

*/
        records = memrSymptomRepository.findBySymptomNameContains(qString);
        searchmap.put("items", records);
        return searchmap;

    }

    @PutMapping("delete/{symptomId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("symptomId") Long symptomId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrSymptom memrSymptom = memrSymptomRepository.getById(symptomId);
        if (memrSymptom != null) {
            memrSymptom.setIsDeleted(true);
            memrSymptomRepository.save(memrSymptom);
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
        List<Tuple> items = memrSymptomService.getMemrSymptomForDropdown(page, size, globalFilter);
        return items;
    }

}
            
