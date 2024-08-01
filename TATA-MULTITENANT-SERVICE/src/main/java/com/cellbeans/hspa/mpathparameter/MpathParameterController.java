package com.cellbeans.hspa.mpathparameter;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mpath_parameter")
public class MpathParameterController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathParameterRepository mpathParameterRepository;

    @Autowired
    private MpathParameterService mpathParameterService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameter mpathParameter) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("add record "+ mpathParameter);
        if (mpathParameter.getParameterName() != null) {
            mpathParameterRepository.save(mpathParameter);
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
        List<MpathParameter> records;
        records = mpathParameterRepository.findByParameterNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{parameterId}")
    public MpathParameter read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("parameterId") Long parameterId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathParameter mpathParameter = mpathParameterRepository.getById(parameterId);
        return mpathParameter;
    }

//    @RequestMapping("byid/{parameterId}")
//    public ResponseEntity<?> read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("parameterId") Long parameterId) {
//        TenantContext.setCurrentTenant(tenantName);
//        Optional<MpathParameter> mpathParameterOptional = mpathParameterRepository.findById(parameterId);
//        if (mpathParameterOptional.isPresent()) {
//            MpathParameter mpathParameter = mpathParameterOptional.get();
//            return ResponseEntity.ok(mpathParameter);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


    @RequestMapping("update")
    public MpathParameter update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameter mpathParameter) {
        System.out.print(tenantName);
        System.out.print(mpathParameter.toString());
        TenantContext.setCurrentTenant(tenantName);
        return mpathParameterRepository.save(mpathParameter);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathParameter> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "parameterId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mpathParameterRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mpathParameterRepository.findByParameterNameContainsAndIsActiveTrueAndIsDeletedFalseOrParameterCodeContainsAndIsActiveTrueAndIsDeletedFalseOrParameterPrintNameContainsAndIsActiveTrueAndIsDeletedFalseOrParameterParameterUnitIdPuNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("alllist")
    public Iterable<MpathParameter> listAll(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathParameterRepository.findAllByIsActiveTrueAndIsDeletedFalse();
    }

    @PutMapping("delete/{parameterId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("parameterId") Long parameterId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathParameter mpathParameter = mpathParameterRepository.getById(parameterId);
        if (mpathParameter != null) {
            mpathParameter.setIsDeleted(true);
            mpathParameterRepository.save(mpathParameter);
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
        List<Tuple> items = mpathParameterService.getMpathParameterForDropdown(page, size, globalFilter);
        return items;
    }

}
            
