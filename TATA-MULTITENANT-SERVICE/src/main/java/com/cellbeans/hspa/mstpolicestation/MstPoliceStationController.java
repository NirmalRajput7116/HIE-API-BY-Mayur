package com.cellbeans.hspa.mstpolicestation;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mst_police_station")
public class MstPoliceStationController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstPoliceStationRepository mstPoliceStationRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPoliceStation mstPoliceStation) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstPoliceStation.getPoliceStationName() != null) {
            mstPoliceStationRepository.save(mstPoliceStation);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{policeStationId}")
    public MstPoliceStation read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("policeStationId") Long policeStationId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPoliceStation mstPoliceStation = mstPoliceStationRepository.getById(policeStationId);
        return mstPoliceStation;
    }

    @RequestMapping("update")
    public MstPoliceStation update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPoliceStation mstPoliceStation) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPoliceStationRepository.save(mstPoliceStation);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstPoliceStation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "policeStationId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstPoliceStationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstPoliceStationRepository.findByPoliceStationNameContainsOrPoliceStationCodeContainsOrPoliceStationContactContainsOrPoliceStationCityIdCityNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{policeStationId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("policeStationId") Long policeStationId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPoliceStation mstPoliceStation = mstPoliceStationRepository.getById(policeStationId);
        if (mstPoliceStation != null) {
            mstPoliceStation.setIsDeleted(true);
            mstPoliceStationRepository.save(mstPoliceStation);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
