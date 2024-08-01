package com.cellbeans.hspa.mstoperationstatus;

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
@RequestMapping("/mst_operation_status")
public class MstOperationStatusController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstOperationStatusRepository mstOperationStatusRepository;

    @Autowired
    private MstOperationStatusService mstOperationStatusService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstOperationStatus mstOperationStatus) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstOperationStatus.getOsName() != null) {
            mstOperationStatusRepository.save(mstOperationStatus);
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
        List<MstOperationStatus> records;
        records = mstOperationStatusRepository.findByOsNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{osId}")
    public MstOperationStatus read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("osId") Long osId) {
        TenantContext.setCurrentTenant(tenantName);
        MstOperationStatus mstOperationStatus = mstOperationStatusRepository.getById(osId);
        return mstOperationStatus;
    }

    @RequestMapping("update")
    public MstOperationStatus update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstOperationStatus mstOperationStatus) {
        TenantContext.setCurrentTenant(tenantName);
        return mstOperationStatusRepository.save(mstOperationStatus);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstOperationStatus> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "osId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstOperationStatusRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstOperationStatusRepository.findByOsNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{osId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("osId") Long osId) {
        TenantContext.setCurrentTenant(tenantName);
        MstOperationStatus mstOperationStatus = mstOperationStatusRepository.getById(osId);
        if (mstOperationStatus != null) {
            mstOperationStatus.setIsDeleted(true);
            mstOperationStatusRepository.save(mstOperationStatus);
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
        List<Tuple> items = mstOperationStatusService.getMstOperationStatusForDropdown(page, size, globalFilter);
        return items;
    }

}
            
