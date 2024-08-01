
package com.cellbeans.hspa.trnpatientimmunization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import com.cellbeans.hspa.trnpatientimmunization.TrnPatientImmunization;
import com.cellbeans.hspa.trnpatientimmunization.TrnPatientImmunizationRepository;

@RestController
@RequestMapping("/trn_patient_immunization")
public class TrnPatientImmunizationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnPatientImmunizationRepository trnPatientImmunizationRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnPatientImmunization trnPatientImmunization) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnPatientImmunization.getPiIcId() != null) {
            TrnPatientImmunization trnPatientImmunization1 = trnPatientImmunizationRepository.save(trnPatientImmunization);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("piId", ""+ trnPatientImmunization1.getPiId());
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

//    @RequestMapping("/autocomplete/{key}")
//    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//        TenantContext.setCurrentTenant(tenantName);
//        Map<String, Object> automap = new HashMap<String, Object>();
//        List<TrnPatientImmunization> records;
//        records = trnPatientImmunizationRepository.findByPiTestContains(key);
//        automap.put("record", records);
//        return automap;
//    }

    @RequestMapping("byid/{piId}")
    public TrnPatientImmunization read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("piId") Long piId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnPatientImmunization trnPatientImmunization = trnPatientImmunizationRepository.getById(piId);
        return trnPatientImmunization;
    }

    @RequestMapping("update")
    public TrnPatientImmunization update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnPatientImmunization trnPatientImmunization) {
        TenantContext.setCurrentTenant(tenantName);
        return trnPatientImmunizationRepository.save(trnPatientImmunization);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnPatientImmunization> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                                 @RequestParam(value = "qString", required = false) String qString,
                                                 @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                 @RequestParam(value = "col", required = false, defaultValue = "piId") String col) {
        TenantContext.setCurrentTenant(tenantName);

//        if (qString == null || qString.equals("")) {
        return trnPatientImmunizationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

//        } else {
//
//            return trnPatientImmunizationRepository.findByPiTestAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        }

    }

    @GetMapping
    @RequestMapping("listByPatientId")
    public Iterable<TrnPatientImmunization> listByPatientId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "patientId", required = false, defaultValue = "0") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);

//        if (qString == null || qString.equals("")) {
        return trnPatientImmunizationRepository.findAllByPiPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(patientId);

//        } else {
//
//            return trnPatientImmunizationRepository.findByPiTestAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        }

    }

    @PutMapping("delete/{piId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("piId") Long piId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnPatientImmunization trnPatientImmunization = trnPatientImmunizationRepository.getById(piId);
        if (trnPatientImmunization != null) {
            trnPatientImmunization.setIsDeleted(true);
            trnPatientImmunizationRepository.save(trnPatientImmunization);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}

