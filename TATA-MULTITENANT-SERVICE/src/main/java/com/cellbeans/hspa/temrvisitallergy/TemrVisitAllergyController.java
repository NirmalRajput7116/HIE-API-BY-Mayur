package com.cellbeans.hspa.temrvisitallergy;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_visit_allergy")
public class TemrVisitAllergyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrVisitAllergyRepository temrVisitAllergyRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitAllergy temrVisitAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitAllergy.getVaCompoundId() != null) {
            temrVisitAllergyRepository.save(temrVisitAllergy);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("createNew")
    public Map<String, String> createNew(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrVisitAllergy> temrVisitAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        temrVisitAllergyRepository.saveAll(temrVisitAllergy);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }
//        @RequestMapping("/autocomplete/{key}")
//	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//		Map<String, Object> automap  = new HashMap<String, Object>();
//		List<TemrVisitAllergy> records;
//		records = temrVisitAllergyRepository.findByAllergyNameContains(key);
//		automap.put("record", records);
//		return automap;
//	}

    @RequestMapping("byid/{vaId}")
    public TemrVisitAllergy read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vaId") Long vaId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitAllergy temrVisitAllergy = temrVisitAllergyRepository.getById(vaId);
        return temrVisitAllergy;
    }

    @RequestMapping("update")
    public TemrVisitAllergy update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitAllergy temrVisitAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitAllergyRepository.save(temrVisitAllergy);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrVisitAllergy> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vaVisitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitAllergyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVisitAllergyRepository.findByVaIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listbyid")
    public Iterable<TemrVisitAllergy> listById(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "patientId", required = false) Long patientId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vaId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitAllergyRepository.findByVaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(patientId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @PutMapping("delete/{vaId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vaId") Long vaId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitAllergy temrVisitAllergy = temrVisitAllergyRepository.getById(vaId);
        if (temrVisitAllergy != null) {
            temrVisitAllergy.setDeleted(true);
            temrVisitAllergyRepository.save(temrVisitAllergy);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("getAllRecordByPatientId/{patientId}")
    public List<TemrVisitAllergy> getAllRecordByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitAllergyRepository.findByVaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(patientId);

    }

    @GetMapping
    @RequestMapping("contarindicatedlistbyid")
    public List<TemrVisitAllergy> contarindicatedlistbyid(@RequestHeader("X-tenantId") String tenantName,
                                                          @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                          @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                          @RequestParam(value = "patientId", required = false) Long patientId,
                                                          @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                          @RequestParam(value = "col", required = false, defaultValue = "vaId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitAllergyRepository.findByVaPatientIdPatientIdEqualsAndVaAsIdAsIdEqualsAndIsDeletedFalse(patientId);

    }
}
            
