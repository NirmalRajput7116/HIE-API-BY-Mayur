package com.cellbeans.hspa.mradtest;

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
@RequestMapping("/mrad_test")
public class MradTestController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MradTestRepository mradTestRepository;

    @Autowired
    private MradTestService mradTestService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MradTest mradTest) {
        TenantContext.setCurrentTenant(tenantName);
        if (mradTest.getTestName() != null) {
            mradTestRepository.save(mradTest);
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
        List<MradTest> records;
        records = mradTestRepository.findByTestNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{testId}")
    public MradTest read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("testId") Long testId) {
        TenantContext.setCurrentTenant(tenantName);
        MradTest mradTest = mradTestRepository.getById(testId);
        return mradTest;
    }

    @RequestMapping("update")
    public MradTest update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MradTest mradTest) {
        TenantContext.setCurrentTenant(tenantName);
        return mradTestRepository.save(mradTest);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MradTest> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "testId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mradTestRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mradTestRepository.findByTestNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{testId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("testId") Long testId) {
        TenantContext.setCurrentTenant(tenantName);
        MradTest mradTest = mradTestRepository.getById(testId);
        if (mradTest != null) {
            mradTest.setIsDeleted(true);
            mradTestRepository.save(mradTest);
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
        List<Tuple> items = mradTestService.getMradTestForDropdown(page, size, globalFilter);
        return items;
    }

}
            
