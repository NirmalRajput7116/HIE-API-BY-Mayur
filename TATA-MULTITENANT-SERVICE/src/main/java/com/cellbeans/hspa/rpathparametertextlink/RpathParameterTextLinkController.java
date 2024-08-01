package com.cellbeans.hspa.rpathparametertextlink;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rpath_parameter_text_link")
public class RpathParameterTextLinkController {

    Map<String, String> respMap = new HashMap<String, String>();
    boolean flag = false;
    @Autowired
    RpathParameterTextLinkRepository rpathParameterTextLinkRepository;
//    @RequestMapping("create")
//    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody RpathTestParameter rpathTestParameter) {
//        if (rpathTestParameter.getTpTestId() != null) {
//            rpathTestParameterRepository.save(rpathTestParameter);
//            respMap.put("success", "1");
//            respMap.put("msg", "Added Successfully");
//            return respMap;
//        } else {
//            respMap.put("success", "0");
//            respMap.put("msg", "Failed To Add Null Field");
//            return respMap;
//        }
//    }

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<RpathParameterTextLink> rpathParameterTextLinkList) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("RpathParameterTextLink :" + rpathParameterTextLinkList);
        rpathParameterTextLinkRepository.saveAll(rpathParameterTextLinkList);
        if (flag) {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Existing Field");
            return respMap;
        } else {
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<RpathParameterTextLink> records;
        records = rpathParameterTextLinkRepository.findByPtlPtIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{tpId}")
    public RpathParameterTextLink read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tpId") Long tpId) {
        TenantContext.setCurrentTenant(tenantName);
        RpathParameterTextLink rpathTestParameter = rpathParameterTextLinkRepository.getById(tpId);
        return rpathTestParameter;
    }

    @RequestMapping("bytestid")
    public Iterable<RpathParameterTextLink> paramByTestId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "parameterId", required = false) Long parameterId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ptlId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return rpathParameterTextLinkRepository.findByPtlPtIdPtIdLikeAndIsActiveTrueAndIsDeletedFalse(parameterId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @RequestMapping("update")
    public RpathParameterTextLink update(@RequestHeader("X-tenantId") String tenantName, @RequestBody RpathParameterTextLink rpathTestParameter) {
        TenantContext.setCurrentTenant(tenantName);
        return rpathParameterTextLinkRepository.save(rpathTestParameter);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<RpathParameterTextLink> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ptlId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if ((qString == null || qString.equals(""))) {
            return rpathParameterTextLinkRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return rpathParameterTextLinkRepository.findByPtlParameterIdParameterIdEqualsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{ptlId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ptlId") Long ptlId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("hello111 :" + ptlId);
        RpathParameterTextLink rpathTestParameter = rpathParameterTextLinkRepository.getById(ptlId);
        if (ptlId != null) {
            rpathTestParameter.setDeleted(true);
            rpathParameterTextLinkRepository.save(rpathTestParameter);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
