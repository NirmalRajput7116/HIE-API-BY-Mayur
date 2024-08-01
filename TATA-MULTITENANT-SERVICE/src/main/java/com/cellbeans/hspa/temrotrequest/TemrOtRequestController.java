package com.cellbeans.hspa.temrotrequest;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_ot_request")
public class TemrOtRequestController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrOtRequestRepository temrOtRequestRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrOtRequest> temrOtRequest) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrOtRequest.size() != 0) {
            temrOtRequestRepository.saveAll(temrOtRequest);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{otrId}")
    public TemrOtRequest read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("otrId") Long otrId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrOtRequest temrOtRequest = temrOtRequestRepository.getById(otrId);
        return temrOtRequest;
    }

    @RequestMapping("update")
    public TemrOtRequest update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrOtRequest temrOtRequest) {
        TenantContext.setCurrentTenant(tenantName);
        return temrOtRequestRepository.save(temrOtRequest);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrOtRequest> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "otrId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return temrOtRequestRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @PutMapping("delete/{otrId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("otrId") Long otrId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrOtRequest temrOtRequest = temrOtRequestRepository.getById(otrId);
        if (temrOtRequest != null) {
            temrOtRequest.setDeleted(true);
            temrOtRequestRepository.save(temrOtRequest);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("listByTimelineId")
    public Iterable<TemrOtRequest> listByTimelineId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "otrId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrOtRequestRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrOtRequestRepository.findByOtrTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("otrequestlistbyunitid/{unitId}")
    public Iterable<TemrOtRequest> getOtRequestListByUnitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrOtRequestRepository.findByUntitIdAndIsActiveTrueAndIsDeletedFalseAndIsScheduledFalse("" + unitId);
    }

    @GetMapping
    @RequestMapping("updateisschedulebyid/{otrId}")
    public Map<String, String> updateIsScheduleByOtrId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("otrId") Long otrId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrOtRequest temrOtRequest = temrOtRequestRepository.getById(otrId);
        if (temrOtRequest != null) {
            temrOtRequest.setScheduled(true);
            temrOtRequestRepository.save(temrOtRequest);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("listByPatienteId/{patientId}")
    public List<TemrOtRequest> listByPatienteId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrOtRequestRepository.findByOtrTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(patientId);

    }
}
