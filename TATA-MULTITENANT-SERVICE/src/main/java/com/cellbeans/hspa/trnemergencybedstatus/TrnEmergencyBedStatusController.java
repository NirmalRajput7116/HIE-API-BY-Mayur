package com.cellbeans.hspa.trnemergencybedstatus;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_emergency_bed_status")
public class TrnEmergencyBedStatusController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnEmergencyBedStatusRepository trnEmergencyBedStatusRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnEmergencyBedStatus trnEmergencyBedStatus) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnEmergencyBedStatus.getEbsBedId() != null) {
            trnEmergencyBedStatus.setEbsStatus(0);
            trnEmergencyBedStatusRepository.save(trnEmergencyBedStatus);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;

        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("update")
    public TrnEmergencyBedStatus update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnEmergencyBedStatus trnEmergencyBedStatus) {
        TenantContext.setCurrentTenant(tenantName);
        return trnEmergencyBedStatusRepository.save(trnEmergencyBedStatus);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnEmergencyBedStatus> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ebsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return trnEmergencyBedStatusRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @PutMapping("delete/{ebsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ebsId") Long ebsId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnEmergencyBedStatus trnEmergencyBedStatus = trnEmergencyBedStatusRepository.getById(ebsId);
        if (trnEmergencyBedStatus != null) {
            trnEmergencyBedStatus.setDeleted(true);
            trnEmergencyBedStatusRepository.save(trnEmergencyBedStatus);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
//
//    @RequestMapping("updatebadestatus/{bedid}/{status}")
//    public int updatebadestatus(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bedid") Long bedid , @PathVariable("status") int status ) {
//        return trnEmergencyBedStatusRepository.bedstatusupdate(status,bedid);
//    }
//    @GetMapping
//    @RequestMapping("getavailablebedforemegency")
//    public Iterable<TrnBedStatus> getavailablebedforemegency(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                                        @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                                        @RequestParam(value = "qString", required = false) String qString,
//                                                        @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                                        @RequestParam(value = "col", required = false, defaultValue = "bed_id") String col) {
//
//
//        return trnEmergencyBedStatusRepository.findAllByTbsStatusAndTbsBedIdEmegencyAndIsActiveTrueAndIsDeletedFalse(0,true,PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//    }

    @GetMapping
    @RequestMapping("getAllAvailableBedByWard")
    public Iterable<TrnEmergencyBedStatus> getAllAvailableBedByWard(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ebsId") String col, @RequestParam(value = "wardid", required = false) long wardid) {
        TenantContext.setCurrentTenant(tenantName);
        return trnEmergencyBedStatusRepository.findAllByEbsBedIdBedWardIdWardIdAndEbsStatusAndIsActiveTrueAndIsDeletedFalse(wardid, 0, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @RequestMapping("change_bed_status/{bedId}")
    public Map<String, String> change_bed_status(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bedId") Long bedId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnEmergencyBedStatus trnEmergencyBedStatus = trnEmergencyBedStatusRepository.findTopByEbsBedIdBedIdAndIsActiveTrueAndIsDeletedFalse(bedId);
        if (trnEmergencyBedStatus != null) {
            trnEmergencyBedStatus.setEbsStatus(0);
            trnEmergencyBedStatusRepository.save(trnEmergencyBedStatus);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            

