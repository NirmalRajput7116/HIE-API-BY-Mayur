package com.cellbeans.hspa.rpathtestparameter;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rpath_test_parameter")
public class RpathTestParameterController {

    Map<String, String> respMap = new HashMap<String, String>();
    boolean flag = false;
    @Autowired
    RpathTestParameterRepository rpathTestParameterRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<RpathTestParameter> rpathTestParameter) {
        TenantContext.setCurrentTenant(tenantName);
        if (rpathTestParameter != null) {
            rpathTestParameterRepository.saveAll(rpathTestParameter);
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
        List<RpathTestParameter> records;
        records = rpathTestParameterRepository.findByTpTestIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{tpId}")
    public RpathTestParameter read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tpId") Long tpId) {
        TenantContext.setCurrentTenant(tenantName);
        RpathTestParameter rpathTestParameter = rpathTestParameterRepository.getById(tpId);
        return rpathTestParameter;
    }

    @RequestMapping("bytestid")
    public Iterable<RpathTestParameter> paramByTestId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "testId", required = false) Long testId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return rpathTestParameterRepository.findByTpTestIdTestIdLikeAndIsActiveTrueAndIsDeletedFalseOrderByTpParameterSequenceNumberAsc(testId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @RequestMapping("update")
    public RpathTestParameter update(@RequestHeader("X-tenantId") String tenantName, @RequestBody RpathTestParameter rpathTestParameter) {
        TenantContext.setCurrentTenant(tenantName);
        return rpathTestParameterRepository.save(rpathTestParameter);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<RpathTestParameter> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        // if ((qString == null || qString.equals(""))) {
        return rpathTestParameterRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        //       }
//        if ((qString == null || qString.equals(""))) {
//            return rpathTestParameterRepository.findByTpTestIdTestIdLikeAndIsActiveTrueAndIsDeletedFalse(testId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        } else {
//
//            return rpathTestParameterRepository.findByTpTestIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
        //      }
    }

    @PutMapping("delete/{tpId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tpId") Long tpId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("hello :" + tpId);
        RpathTestParameter rpathTestParameter = rpathTestParameterRepository.getById(tpId);
        if (tpId != null) {
            rpathTestParameter.setIsDeleted(true);
            rpathTestParameterRepository.save(rpathTestParameter);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}

