package com.cellbeans.hspa.mpathmachine;

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
@RequestMapping("/mpath_machine")
public class MpathMachineController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathMachineRepository mpathMachineRepository;

    @Autowired
    private MpathMachineService mpathMachineService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathMachine mpathMachine) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathMachine.getMachineName() != null) {
            mpathMachineRepository.save(mpathMachine);
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
        List<MpathMachine> records;
        records = mpathMachineRepository.findByMachineNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{machineId}")
    public MpathMachine read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("machineId") Long machineId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathMachine mpathMachine = mpathMachineRepository.getById(machineId);
        return mpathMachine;
    }

    @RequestMapping("update")
    public MpathMachine update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathMachine mpathMachine) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathMachineRepository.save(mpathMachine);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathMachine> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "machineId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mpathMachineRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mpathMachineRepository.findByMachineNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{machineId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("machineId") Long machineId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathMachine mpathMachine = mpathMachineRepository.getById(machineId);
        if (mpathMachine != null) {
            mpathMachine.setIsDeleted(true);
            mpathMachineRepository.save(mpathMachine);
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
        List<Tuple> items = mpathMachineService.getMpathMachineForDropdown(page, size, globalFilter);
        return items;
    }

}
            
