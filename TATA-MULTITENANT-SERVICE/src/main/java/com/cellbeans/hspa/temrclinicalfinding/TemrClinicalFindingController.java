package com.cellbeans.hspa.temrclinicalfinding;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstspeciality.MstSpecialityRepository;
import com.cellbeans.hspa.mstsuperspeciality.MstSuperSpeciality;
import com.cellbeans.hspa.mstsuperspeciality.MstSuperSpecialityRepository;
import com.cellbeans.hspa.temrtimeline.TemrTimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_clinical_finding")
public class TemrClinicalFindingController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrClinicalFindingRepository temrClinicalFindingRepository;

    @Autowired
    MstSpecialityRepository mstSpecialityRepository;

    @Autowired
    MstSuperSpecialityRepository mstSuperSpecialityRepository;

    @Autowired
    TemrTimelineRepository temrTimelineRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MstSuperSpeciality> temrClinicalFinding) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrClinicalFinding != null) {
            for (MstSuperSpeciality obj : temrClinicalFinding) {
                TemrClinicalFinding objTemrClinicalFinding = new TemrClinicalFinding();
                objTemrClinicalFinding.setCfName(obj.getClinicalFinding());
                objTemrClinicalFinding.setCfSpecialityId(mstSpecialityRepository.getById(obj.getSsSpecialityId().getSpecialityId()));
                objTemrClinicalFinding.setSsSpecialityId(mstSuperSpecialityRepository.getById(obj.getSsId()));
                objTemrClinicalFinding.setCfTimelineId(temrTimelineRepository.getById(obj.getCfTimelineId().getTimelineId()));
                temrClinicalFindingRepository.save(objTemrClinicalFinding);

            }
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
        List<TemrClinicalFinding> records;
        records = temrClinicalFindingRepository.findByCfNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{cfId}")
    public TemrClinicalFinding read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cfId") Long cfId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrClinicalFinding temrClinicalFinding = temrClinicalFindingRepository.getById(cfId);
        return temrClinicalFinding;
    }

    @RequestMapping("update")
    public TemrClinicalFinding update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrClinicalFinding temrClinicalFinding) {
        TenantContext.setCurrentTenant(tenantName);
        return temrClinicalFindingRepository.save(temrClinicalFinding);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrClinicalFinding> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "cpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrClinicalFindingRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrClinicalFindingRepository.findByCfNameContainsOrCfSpecialityIdSpecialityNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{cfId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cfId") Long cfId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrClinicalFinding temrClinicalFinding = temrClinicalFindingRepository.getById(cfId);
        if (temrClinicalFinding != null) {
            temrClinicalFinding.setIsDeleted(true);
            temrClinicalFindingRepository.save(temrClinicalFinding);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("byspeciality/{specialityId}")
    public List<TemrClinicalFinding> findWardByFloorId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("specialityId") Long specialityId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrClinicalFindingRepository.findByCfSpecialityIdSpecialityIdAndIsActiveTrueAndIsDeletedFalse(specialityId, PageRequest.of(0, 10));
    }

    @RequestMapping("listByTimelineId/{tiemlineId}")
    public List<TemrClinicalFinding> listByTimelineId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tiemlineId") Long tiemlineId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrClinicalFindingRepository.findByCfTimelineIdTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(tiemlineId);
    }

    @RequestMapping("listByPatientId/{patientId}")
    public List<TemrClinicalFinding> listByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrClinicalFindingRepository.findByCfTimelineIdTimelinePatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(patientId);
    }
//
//    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
//    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "specialityId", required = false) String specialityId, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
//        List<Tuple> items = temrClinicalFindingRepository.getMemrClinicalProcedureForDropdown(page, size, Long.parseLong(specialityId), globalFilter);
//        return items;
//    }

}
