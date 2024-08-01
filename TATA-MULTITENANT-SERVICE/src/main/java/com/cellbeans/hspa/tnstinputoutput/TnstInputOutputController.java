package com.cellbeans.hspa.tnstinputoutput;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tnst_input_output")
public class TnstInputOutputController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TnstInputOutputRepository tnstInputOutputRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstInputOutput tnstInputOutput) {
        TenantContext.setCurrentTenant(tenantName);
        if (tnstInputOutput.getIoNotes() != null) {
            tnstInputOutputRepository.save(tnstInputOutput);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    /*@RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TnstDrugAdmin> records;
        records = tnstInputOutputRepository.findByContains(key);
        automap.put("record", records);
        return automap;
    }*/

    @RequestMapping("byid/{ioId}")
    public TnstInputOutput read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ioId") Long ioId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstInputOutput tnstInputOutput = tnstInputOutputRepository.getById(ioId);
        return tnstInputOutput;
    }

    @RequestMapping("update")
    public TnstInputOutput update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstInputOutput tnstInputOutput) {
        TenantContext.setCurrentTenant(tenantName);
        return tnstInputOutputRepository.save(tnstInputOutput);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TnstInputOutput> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ioId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tnstInputOutputRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tnstInputOutputRepository.findByAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listByAdmission")
    public Iterable<TnstInputOutput> listByAdmissionId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ioId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tnstInputOutputRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tnstInputOutputRepository.findByIoAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ioId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ioId") Long ioId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstInputOutput tnstInputOutput = tnstInputOutputRepository.getById(ioId);
        if (tnstInputOutput != null) {
            tnstInputOutput.setDeleted(true);
            tnstInputOutputRepository.save(tnstInputOutput);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
