package com.cellbeans.hspa.motintraoperativeinstruction;

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
@RequestMapping("/mot_intra_operative_instruction")
public class MotIntraOperativeInstructionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MotIntraOperativeInstructionRepository motIntraOperativeInstructionRepository;

    @Autowired
    private MotIntraOperativeInstructionService motIntraOperativeInstructionService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotIntraOperativeInstruction motIntraOperativeInstruction) {
        TenantContext.setCurrentTenant(tenantName);
        if (motIntraOperativeInstruction.getIoiName() != null) {
            motIntraOperativeInstructionRepository.save(motIntraOperativeInstruction);
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
        List<MotIntraOperativeInstruction> records;
        records = motIntraOperativeInstructionRepository.findByIoiNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ioiId}")
    public MotIntraOperativeInstruction read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ioiId") Long ioiId) {
        TenantContext.setCurrentTenant(tenantName);
        MotIntraOperativeInstruction motIntraOperativeInstruction = motIntraOperativeInstructionRepository.getById(ioiId);
        return motIntraOperativeInstruction;
    }

    @RequestMapping("update")
    public MotIntraOperativeInstruction update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotIntraOperativeInstruction motIntraOperativeInstruction) {
        TenantContext.setCurrentTenant(tenantName);
        return motIntraOperativeInstructionRepository.save(motIntraOperativeInstruction);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MotIntraOperativeInstruction> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ioiId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return motIntraOperativeInstructionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return motIntraOperativeInstructionRepository.findByIoiNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ioiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ioiId") Long ioiId) {
        TenantContext.setCurrentTenant(tenantName);
        MotIntraOperativeInstruction motIntraOperativeInstruction = motIntraOperativeInstructionRepository.getById(ioiId);
        if (motIntraOperativeInstruction != null) {
            motIntraOperativeInstruction.setIsDeleted(true);
            motIntraOperativeInstructionRepository.save(motIntraOperativeInstruction);
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
        List<Tuple> items = motIntraOperativeInstructionService.getMotIntraOperativeInstructionForDropdown(page, size, globalFilter);
        return items;
    }

}
            
