package com.cellbeans.hspa.nstnursingstationdefination;

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
@RequestMapping("/nst_nursing_station_defination")
public class NstNursingStationDefinationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    NstNursingStationDefinationRepository nstNursingStationDefinationRepository;
    @Autowired
    private NstNursingStationDefinationService nstNursingStationDefinationService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NstNursingStationDefination nstNursingStationDefination) {
        TenantContext.setCurrentTenant(tenantName);
        if (nstNursingStationDefination.getNsdName() != null) {
            nstNursingStationDefinationRepository.save(nstNursingStationDefination);
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
    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        Map<String, Object> automap = new HashMap<String, Object>();
        List<NstNursingStationDefination> records;
        records = nstNursingStationDefinationRepository.findByContains(key);
        automap.put("record", records);
        return automap;
    }
*/

    @RequestMapping("byid/{nsdId}")
    public NstNursingStationDefination read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("nsdId") Long nsdId) {
        TenantContext.setCurrentTenant(tenantName);
        NstNursingStationDefination nstNursingStationDefination = nstNursingStationDefinationRepository.getById(nsdId);
        return nstNursingStationDefination;
    }

    @RequestMapping("update")
    public NstNursingStationDefination update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NstNursingStationDefination nstNursingStationDefination) {
        TenantContext.setCurrentTenant(tenantName);
        return nstNursingStationDefinationRepository.save(nstNursingStationDefination);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<NstNursingStationDefination> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "nsdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return nstNursingStationDefinationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            //return nstNursingStationDefinationRepository.findByNsdNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            return nstNursingStationDefinationRepository.findAllByNsdNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{nsdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("nsdId") Long nsdId) {
        TenantContext.setCurrentTenant(tenantName);
        NstNursingStationDefination nstNursingStationDefination = nstNursingStationDefinationRepository.getById(nsdId);
        if (nstNursingStationDefination != null) {
            nstNursingStationDefination.setDeleted(true);
            nstNursingStationDefinationRepository.save(nstNursingStationDefination);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("GetAllRecordServiceByUnitId/{unitId}")
    public List<NstNursingStationDefination> GetAllRecordServiceByUnitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return nstNursingStationDefinationRepository.findAllByNsdUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(unitId);

    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = nstNursingStationDefinationService.getNstNursingStationDefinationForDropdown(page, size, globalFilter);
        return items;
    }

    @RequestMapping("getAllocatedBedToNursingStation/{unitId}")
    public List<NstNursingStationDefination> getAllocatedBedToNursingStation(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        List<NstNursingStationDefination> nstNursingStationDefination = nstNursingStationDefinationRepository.findAllByNsdUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(unitId);
        return nstNursingStationDefination;
    }

}
            
