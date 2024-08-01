package com.cellbeans.hspa.motpostoperativeinstruction;

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
@RequestMapping("/mot_post_operative_instruction")
public class MotPostOperativeInstructionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MotPostOperativeInstructionRepository motPostOperativeInstructionRepository;

    @Autowired
    private MotPostOperativeInstructionService motPostOperativeInstructionService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotPostOperativeInstruction motPostOperativeInstruction) {
        TenantContext.setCurrentTenant(tenantName);
        if (motPostOperativeInstruction.getPoiName() != null) {
            motPostOperativeInstructionRepository.save(motPostOperativeInstruction);
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
        List<MotPostOperativeInstruction> records;
        records = motPostOperativeInstructionRepository.findByPoiNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{poiId}")
    public MotPostOperativeInstruction read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("poiId") Long poiId) {
        TenantContext.setCurrentTenant(tenantName);
        MotPostOperativeInstruction motPostOperativeInstruction = motPostOperativeInstructionRepository.getById(poiId);
        return motPostOperativeInstruction;
    }

    @RequestMapping("update")
    public MotPostOperativeInstruction update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotPostOperativeInstruction motPostOperativeInstruction) {
        TenantContext.setCurrentTenant(tenantName);
        return motPostOperativeInstructionRepository.save(motPostOperativeInstruction);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MotPostOperativeInstruction> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "poiId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return motPostOperativeInstructionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return motPostOperativeInstructionRepository.findByPoiNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{poiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("poiId") Long poiId) {
        TenantContext.setCurrentTenant(tenantName);
        MotPostOperativeInstruction motPostOperativeInstruction = motPostOperativeInstructionRepository.getById(poiId);
        if (motPostOperativeInstruction != null) {
            motPostOperativeInstruction.setIsDeleted(true);
            motPostOperativeInstructionRepository.save(motPostOperativeInstruction);
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
        List<Tuple> items = motPostOperativeInstructionService.getMotPostOperativeInstructionForDropdown(page, size, globalFilter);
        return items;
    }

}
            
