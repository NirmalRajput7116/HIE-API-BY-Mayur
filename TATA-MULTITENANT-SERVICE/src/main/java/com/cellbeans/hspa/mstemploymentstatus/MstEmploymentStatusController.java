package com.cellbeans.hspa.mstemploymentstatus;

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
@RequestMapping("/mst_employment_status")
public class MstEmploymentStatusController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstEmploymentStatusRepository mstEmploymentStatusRepository;

    @Autowired
    private MstEmploymentStatusService mstEmploymentStatusService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstEmploymentStatus mstEmploymentStatus) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstEmploymentStatus.getEsName() != null) {
            mstEmploymentStatusRepository.save(mstEmploymentStatus);
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
        List<MstEmploymentStatus> records;
        records = mstEmploymentStatusRepository.findByEsNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{esId}")
    public MstEmploymentStatus read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("esId") Long esId) {
        TenantContext.setCurrentTenant(tenantName);
        MstEmploymentStatus mstEmploymentStatus = mstEmploymentStatusRepository.getById(esId);
        return mstEmploymentStatus;
    }

    @RequestMapping("update")
    public MstEmploymentStatus update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstEmploymentStatus mstEmploymentStatus) {
        TenantContext.setCurrentTenant(tenantName);
        return mstEmploymentStatusRepository.save(mstEmploymentStatus);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstEmploymentStatus> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "esId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstEmploymentStatusRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByEsName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstEmploymentStatusRepository.findByEsNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByEsName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{esId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("esId") Long esId) {
        TenantContext.setCurrentTenant(tenantName);
        MstEmploymentStatus mstEmploymentStatus = mstEmploymentStatusRepository.getById(esId);
        if (mstEmploymentStatus != null) {
            mstEmploymentStatus.setIsDeleted(true);
            mstEmploymentStatusRepository.save(mstEmploymentStatus);
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
        List<Tuple> items = mstEmploymentStatusService.getMstEmploymentStatusForDropdown(page, size, globalFilter);
        return items;
    }

}
            
