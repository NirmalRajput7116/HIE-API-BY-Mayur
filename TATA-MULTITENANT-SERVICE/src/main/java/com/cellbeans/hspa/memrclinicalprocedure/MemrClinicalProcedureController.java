package com.cellbeans.hspa.memrclinicalprocedure;

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
@RequestMapping("/memr_clinical_procedure")
public class MemrClinicalProcedureController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrClinicalProcedureRepository memrClinicalProcedureRepository;

    @Autowired
    private MemrClinicalProcedureService memrClinicalProcedureService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrClinicalProcedure memrClinicalProcedure) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrClinicalProcedure.getCpName() != null) {
            memrClinicalProcedure.setCpName(memrClinicalProcedure.getCpName().trim());
            if (memrClinicalProcedureRepository.findByAllOrderByCpName(memrClinicalProcedure.getCpName()) == 0) {
                memrClinicalProcedureRepository.save(memrClinicalProcedure);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
            }
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
        List<MemrClinicalProcedure> records;
        records = memrClinicalProcedureRepository.findByCpNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{cpId}")
    public MemrClinicalProcedure read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cpId") Long cpId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrClinicalProcedure memrClinicalProcedure = memrClinicalProcedureRepository.getById(cpId);
        return memrClinicalProcedure;
    }

    @RequestMapping("update")
    public MemrClinicalProcedure update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrClinicalProcedure memrClinicalProcedure) {
        TenantContext.setCurrentTenant(tenantName);
        return memrClinicalProcedureRepository.save(memrClinicalProcedure);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrClinicalProcedure> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "cpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrClinicalProcedureRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByCpName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrClinicalProcedureRepository.findByCpNameContainsOrCpSpeiclityIdSpecialityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCpName(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{cpId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cpId") Long cpId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrClinicalProcedure memrClinicalProcedure = memrClinicalProcedureRepository.getById(cpId);
        if (memrClinicalProcedure != null) {
            memrClinicalProcedure.setIsDeleted(true);
            memrClinicalProcedureRepository.save(memrClinicalProcedure);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("byspeciality/{specialityId}")
    public List<MemrClinicalProcedure> findWardByFloorId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("specialityId") Long specialityId) {
        TenantContext.setCurrentTenant(tenantName);
        return memrClinicalProcedureRepository.findByCpSpeiclityIdSpecialityIdAndIsActiveTrueAndIsDeletedFalse(specialityId, PageRequest.of(0, 10));
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "specialityId", required = false) String specialityId, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = memrClinicalProcedureService.getMemrClinicalProcedureForDropdown(page, size, Long.parseLong(specialityId), globalFilter);
        return items;
    }

}
            
