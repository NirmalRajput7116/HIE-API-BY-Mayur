package com.cellbeans.hspa.mpathtest;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mpath_test")
public class MpathTestController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathTestRepository mpathTestRepository;

    @Autowired
    private MpathTestService mpathTestService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTest mpathTest) {
        TenantContext.setCurrentTenant(tenantName);
        List<MpathTest> list = mpathTestRepository.findByMbillServiceIdServiceIdAndIsActiveTrueAndIsDeletedFalse(mpathTest.getMbillServiceId().getServiceId());
        if (list.size() <= 0) {
            if (mpathTest.getTestName() != null) {
                try {
                    mpathTest.setCreatedDatetime(new Date());
                    mpathTestRepository.save(mpathTest);
                    respMap.put("testId", String.valueOf(mpathTest.getTestId()));
                    respMap.put("success", "1");
                    respMap.put("msg", "Test Add Successfully");
                } catch (Exception e) {
                    respMap.put("fail", "0");
                    respMap.put("msg", "Service Already Linked");
                    return respMap;
                }
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
                return respMap;
            }
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Service already allocation to another Test ");
            return respMap;

        }

    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MpathTest> records;
        records = mpathTestRepository.findByTestNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("findtestid/{mBillServiceId}")
    public List<MpathTest> findTestId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mBillServiceId") Long mBillServiceId) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("hello findtestid " + mBillServiceId);
        List<MpathTest> records;
        records = mpathTestRepository.findByMbillServiceIdServiceIdAndIsActiveTrueAndIsDeletedFalse(mBillServiceId);
        return records;
    }

    @RequestMapping("byid/{testId}")
    public MpathTest read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("testId") Long testId) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("TestID :" + testId);
        MpathTest mpathTest = mpathTestRepository.getById(testId);
        return mpathTest;
    }

    @RequestMapping("update")
    public MpathTest update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTest mpathTest) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("Update MpathTest : " + mpathTest);
        mpathTest.setUpdatedDatetime(new Date());
        return mpathTestRepository.save(mpathTest);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathTest> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "testId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mpathTestRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mpathTestRepository.findByTestNameContainsAndIsActiveTrueAndIsDeletedFalseOrTestCodeContainsAndIsActiveTrueAndIsDeletedFalseOrTestPrintNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{testId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("testId") Long testId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathTest mpathTest = mpathTestRepository.getById(testId);
        if (mpathTest != null) {
            mpathTest.setIsDeleted(true);
            mpathTestRepository.save(mpathTest);
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
        List<Tuple> items = mpathTestService.getMpathTestForDropdown(page, size, globalFilter);
        return items;
    }

}
            
