package com.cellbeans.hspa.temrlocalexamination;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/temr_local_examination")
public class TemrLocalExaminationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrLocalExaminationRepository temrLocalExaminationRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrLocalExamination temrLocalExamination) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrLocalExamination.getLeVisitId().getVisitId() > 0) {
            temrLocalExaminationRepository.save(temrLocalExamination);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
    /*
     * @RequestMapping("/autocomplete/{key}") public Map<String, Object>
     * listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) { Map<String, Object> automap =
     * new HashMap<String, Object>(); List<TemrLocalExamination> records;
     * records =
     * temrLocalExaminationRepository.findByDepartmentNameContains(key);
     * automap.put("record", records); return automap; }
     */

    @RequestMapping("byid/{leId}")
    public TemrLocalExamination read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("leId") Long leId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrLocalExamination temrLocalExamination = temrLocalExaminationRepository.getById(leId);
        return temrLocalExamination;
    }

    @RequestMapping("update")
    public TemrLocalExamination update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrLocalExamination temrLocalExamination) {
        TenantContext.setCurrentTenant(tenantName);
        return temrLocalExaminationRepository.save(temrLocalExamination);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrLocalExamination> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "leId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrLocalExaminationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrLocalExaminationRepository.findByLeIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{leId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("leId") Long leId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrLocalExamination temrLocalExamination = temrLocalExaminationRepository.getById(leId);
        if (temrLocalExamination != null) {
            temrLocalExamination.setIsDeleted(true);
            temrLocalExaminationRepository.save(temrLocalExamination);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
