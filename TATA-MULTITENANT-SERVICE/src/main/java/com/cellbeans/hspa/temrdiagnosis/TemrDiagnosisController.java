package com.cellbeans.hspa.temrdiagnosis;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/temr_diagnosis")
public class TemrDiagnosisController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrDiagnosisRepository temrDiagnosisRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrDiagnosis temrDiagnosis) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrDiagnosis.getDiagnosisVisitId().getVisitId() > 0) {
            temrDiagnosisRepository.save(temrDiagnosis);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
//        @RequestMapping("/autocomplete/{key}")
//	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//		Map<String, Object> automap  = new HashMap<String, Object>();
//		List<TemrDiagnosis> records;
//		records = temrDiagnosisRepository.findByDCodeContains(key);
//		automap.put("record", records);
//		return automap;
//	}

    @RequestMapping("byid/{diagnosisId}")
    public TemrDiagnosis read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("diagnosisId") Long diagnosisId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrDiagnosis temrDiagnosis = temrDiagnosisRepository.getById(diagnosisId);
        return temrDiagnosis;
    }

    @RequestMapping("update")
    public TemrDiagnosis update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrDiagnosis temrDiagnosis) {
        TenantContext.setCurrentTenant(tenantName);
        return temrDiagnosisRepository.save(temrDiagnosis);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrDiagnosis> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "diagnosisId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrDiagnosisRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrDiagnosisRepository.findByDiagnosisDescriptionContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{diagnosisId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("diagnosisId") Long diagnosisId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrDiagnosis temrDiagnosis = temrDiagnosisRepository.getById(diagnosisId);
        if (temrDiagnosis != null) {
            temrDiagnosis.setIsDeleted(true);
            temrDiagnosisRepository.save(temrDiagnosis);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
