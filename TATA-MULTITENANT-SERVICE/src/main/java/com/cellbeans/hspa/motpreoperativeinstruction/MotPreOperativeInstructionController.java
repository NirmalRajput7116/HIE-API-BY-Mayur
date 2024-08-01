package com.cellbeans.hspa.motpreoperativeinstruction;

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
@RequestMapping("/mot_pre_operative_instruction")
public class MotPreOperativeInstructionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MotPreOperativeInstructionRepository motPreOperativeInstructionRepository;

    @Autowired
    private MotPreOperativeInstructionService motPreOperativeInstructionService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotPreOperativeInstruction motPreOperativeInstruction) {
        TenantContext.setCurrentTenant(tenantName);
        if (motPreOperativeInstruction.getPoiName() != null) {
            motPreOperativeInstructionRepository.save(motPreOperativeInstruction);
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
        List<MotPreOperativeInstruction> records;
        records = motPreOperativeInstructionRepository.findByPoiNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{poiId}")
    public MotPreOperativeInstruction read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("poiId") Long poiId) {
        TenantContext.setCurrentTenant(tenantName);
        MotPreOperativeInstruction motPreOperativeInstruction = motPreOperativeInstructionRepository.getById(poiId);
        return motPreOperativeInstruction;
    }

    @RequestMapping("update")
    public MotPreOperativeInstruction update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotPreOperativeInstruction motPreOperativeInstruction) {
        TenantContext.setCurrentTenant(tenantName);
        return motPreOperativeInstructionRepository.save(motPreOperativeInstruction);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MotPreOperativeInstruction> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "poiId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return motPreOperativeInstructionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return motPreOperativeInstructionRepository.findByPoiNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{poiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("poiId") Long poiId) {
        TenantContext.setCurrentTenant(tenantName);
        MotPreOperativeInstruction motPreOperativeInstruction = motPreOperativeInstructionRepository.getById(poiId);
        if (motPreOperativeInstruction != null) {
            motPreOperativeInstruction.setIsDeleted(true);
            motPreOperativeInstructionRepository.save(motPreOperativeInstruction);
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
        List<Tuple> items = motPreOperativeInstructionService.getMotPreOperativeInstructionForDropdown(page, size, globalFilter);
        return items;
    }

}
            
