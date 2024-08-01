package com.cellbeans.hspa.mstdoctorwaiver;

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
@RequestMapping("/mst_doctor_waiver")
public class MstDoctorWaiverController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstDoctorWaiverRepository mstDoctorWaiverRepository;

    @Autowired
    private MstDoctorWaiverService mstDoctorWaiverService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoctorWaiver mstDoctorWaiver) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDoctorWaiver.getDwName() != null) {
            mstDoctorWaiverRepository.save(mstDoctorWaiver);
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
        List<MstDoctorWaiver> records;
        records = mstDoctorWaiverRepository.findByDwNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dwId}")
    public MstDoctorWaiver read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dwId") Long dwId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoctorWaiver mstDoctorWaiver = mstDoctorWaiverRepository.getById(dwId);
        return mstDoctorWaiver;
    }

    @RequestMapping("update")
    public MstDoctorWaiver update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoctorWaiver mstDoctorWaiver) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDoctorWaiverRepository.save(mstDoctorWaiver);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDoctorWaiver> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dwId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDoctorWaiverRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDoctorWaiverRepository.findByDwNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dwId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dwId") Long dwId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoctorWaiver mstDoctorWaiver = mstDoctorWaiverRepository.getById(dwId);
        if (mstDoctorWaiver != null) {
            mstDoctorWaiver.setIsDeleted(true);
            mstDoctorWaiverRepository.save(mstDoctorWaiver);
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
        List<Tuple> items = mstDoctorWaiverService.getMstDoctorWaiverForDropdown(page, size, globalFilter);
        return items;
    }

}
            
