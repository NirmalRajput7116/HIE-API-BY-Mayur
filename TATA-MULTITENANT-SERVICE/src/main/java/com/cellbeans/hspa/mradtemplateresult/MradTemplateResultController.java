package com.cellbeans.hspa.mradtemplateresult;

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
@RequestMapping("/mrad_template_result")
public class MradTemplateResultController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MradTemplateResultRepository mradTemplateResultRepository;

    @Autowired
    private MradTemplateResultService mradTemplateResultService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MradTemplateResult mradTemplateResult) {
        TenantContext.setCurrentTenant(tenantName);
        if (mradTemplateResult.getTrName() != null) {
            mradTemplateResultRepository.save(mradTemplateResult);
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
        List<MradTemplateResult> records;
        records = mradTemplateResultRepository.findByTrNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{trId}")
    public MradTemplateResult read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("trId") Long trId) {
        TenantContext.setCurrentTenant(tenantName);
        MradTemplateResult mradTemplateResult = mradTemplateResultRepository.getById(trId);
        return mradTemplateResult;
    }

    @RequestMapping("update")
    public MradTemplateResult update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MradTemplateResult mradTemplateResult) {
        TenantContext.setCurrentTenant(tenantName);
        return mradTemplateResultRepository.save(mradTemplateResult);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MradTemplateResult> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "trId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mradTemplateResultRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mradTemplateResultRepository.findByTrNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{trId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("trId") Long trId) {
        TenantContext.setCurrentTenant(tenantName);
        MradTemplateResult mradTemplateResult = mradTemplateResultRepository.getById(trId);
        if (mradTemplateResult != null) {
            mradTemplateResult.setIsDeleted(true);
            mradTemplateResultRepository.save(mradTemplateResult);
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
        List<Tuple> items = mradTemplateResultService.getMradTemplateResultForDropdown(page, size, globalFilter);
        return items;
    }

}
            
