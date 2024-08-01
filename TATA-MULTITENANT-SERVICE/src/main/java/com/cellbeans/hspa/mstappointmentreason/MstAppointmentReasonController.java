package com.cellbeans.hspa.mstappointmentreason;

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
@RequestMapping("/mst_appointment_reason")
public class MstAppointmentReasonController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstAppointmentReasonRepository mstAppointmentReasonRepository;

    @Autowired
    private MstAppointmentReasonService mstAppointmentReasonService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAppointmentReason mstAppointmentReason) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstAppointmentReason.getArName() != null) {
            mstAppointmentReason.setArName(mstAppointmentReason.getArName().trim());
            MstAppointmentReason mstAppointmentReasonObject = mstAppointmentReasonRepository.findByArNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstAppointmentReason.getArName());
            if (mstAppointmentReasonObject == null) {
                mstAppointmentReasonRepository.save(mstAppointmentReason);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Already Added");
                return respMap;
            }
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
        List<MstAppointmentReason> records;
        records = mstAppointmentReasonRepository.findByArNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{arId}")
    public MstAppointmentReason read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("arId") Long arId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAppointmentReason mstAppointmentReason = mstAppointmentReasonRepository.getById(arId);
        return mstAppointmentReason;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAppointmentReason mstAppointmentReason) {
        TenantContext.setCurrentTenant(tenantName);
        mstAppointmentReason.setArName(mstAppointmentReason.getArName().trim());
        MstAppointmentReason mstAppointmentReasonObject = mstAppointmentReasonRepository.findByArNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstAppointmentReason.getArName());
        if (mstAppointmentReasonObject == null) {
            mstAppointmentReasonRepository.save(mstAppointmentReason);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstAppointmentReasonObject.getArId() == mstAppointmentReason.getArId()) {
            mstAppointmentReasonRepository.save(mstAppointmentReason);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstAppointmentReason> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "arId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstAppointmentReasonRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstAppointmentReasonRepository.findByArNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{arId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("arId") Long arId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAppointmentReason mstAppointmentReason = mstAppointmentReasonRepository.getById(arId);
        if (mstAppointmentReason != null) {
            mstAppointmentReason.setIsDeleted(true);
            mstAppointmentReasonRepository.save(mstAppointmentReason);
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
        List<Tuple> items = mstAppointmentReasonService.getMstAppointmentReasonForDropdown(page, size, globalFilter);
        return items;
    }

}
            
