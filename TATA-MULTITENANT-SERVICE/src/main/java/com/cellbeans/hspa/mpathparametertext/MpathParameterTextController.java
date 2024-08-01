package com.cellbeans.hspa.mpathparametertext;

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
@RequestMapping("/mpath_parameter_text")
public class MpathParameterTextController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathParameterTextRepository mpathParameterTextRepository;

    @Autowired
    private MpathParameterTextService mpathParameterTextService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameterText mpathParameterText) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathParameterText.getPtName() != null) {
            mpathParameterTextRepository.save(mpathParameterText);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{ptId}")
    public MpathParameterText read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ptId") Long ptId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathParameterText mpathParameterText = mpathParameterTextRepository.getById(ptId);
        return mpathParameterText;
    }

    @RequestMapping("update")
    public MpathParameterText update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameterText mpathParameterText) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathParameterTextRepository.save(mpathParameterText);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathParameterText> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ptId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mpathParameterTextRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mpathParameterTextRepository.findByPtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ptId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ptId") Long ptId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathParameterText mpathParameterText = mpathParameterTextRepository.getById(ptId);
        if (mpathParameterText != null) {
            mpathParameterText.setDeleted(true);
            mpathParameterTextRepository.save(mpathParameterText);
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
        List<Tuple> items = mpathParameterTextService.getMpathParameterTextForDropdown(page, size, globalFilter);
        return items;
    }

}
            
