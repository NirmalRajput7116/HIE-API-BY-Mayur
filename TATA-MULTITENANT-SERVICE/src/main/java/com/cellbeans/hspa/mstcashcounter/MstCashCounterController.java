package com.cellbeans.hspa.mstcashcounter;

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
@RequestMapping("/mst_cash_counter")
public class MstCashCounterController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstCashCounterRepository mstCashCounterRepository;

    @Autowired
    private MstCashCounterService mstCashCounterService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCashCounter mstCashCounter) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstCashCounter.getCcName() != null) {
            if (mstCashCounterRepository.findByAllOrderByCashCounter(mstCashCounter.getCcName(), mstCashCounter.getCcUnitId().getUnitId()) == 0) {
                mstCashCounterRepository.save(mstCashCounter);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
            }
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
        List<MstCashCounter> records;
        records = mstCashCounterRepository.findByCcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ccId}")
    public MstCashCounter read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ccId") Long ccId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCashCounter mstCashCounter = mstCashCounterRepository.getById(ccId);
        return mstCashCounter;
    }

    @RequestMapping("update")
    public MstCashCounter update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCashCounter mstCashCounter) {
        TenantContext.setCurrentTenant(tenantName);
        return mstCashCounterRepository.save(mstCashCounter);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstCashCounter> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ccId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstCashCounterRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstCashCounterRepository.findByCcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("cclistbyunitid")
    public List<MstCashCounter> ccListByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("unitId :" + unitId);
        return mstCashCounterRepository.findByCcUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(unitId);
    }

    @PutMapping("delete/{ccId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ccId") Long ccId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCashCounter mstCashCounter = mstCashCounterRepository.getById(ccId);
        if (mstCashCounter != null) {
            mstCashCounter.setIsDeleted(true);
            mstCashCounterRepository.save(mstCashCounter);
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
        List<Tuple> items = mstCashCounterService.getMstCashCounterForDropdown(page, size, globalFilter);
        return items;
    }

}
            
