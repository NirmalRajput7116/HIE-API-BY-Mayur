package com.cellbeans.hspa.trnemergencybedallocation;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.trnemergencybedhistory.TrnEmergencyBedHistory;
import com.cellbeans.hspa.trnemergencybedhistory.TrnEmergencyBedHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_emergency_bed_allocation")
public class TrnEmergencyBedAllocationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnEmergencyBedAllocationRepository trnemergencybedallocationRepository;

    @Autowired
    TrnEmergencyBedHistoryRepository trnEmergencyBedHistoryRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnEmergencyBedAllocation trnemergencybedallocation) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnemergencybedallocation.getEbaBedId() != null) {
            TrnEmergencyBedHistory trnEmergencyBedHistory = new TrnEmergencyBedHistory();
            List<MstVisit> mvlist = new ArrayList<MstVisit>();
            mvlist.add(trnemergencybedallocation.getEbaVisitId());
            trnEmergencyBedHistory.setEbhVisitId(mvlist);
            List<MipdBed> mblist = new ArrayList<MipdBed>();
            mblist.add(trnemergencybedallocation.getEbaBedId());
            trnEmergencyBedHistory.setEbhBedId(mblist);
            trnemergencybedallocationRepository.save(trnemergencybedallocation);
            trnEmergencyBedHistoryRepository.save(trnEmergencyBedHistory);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
//
//    @RequestMapping("/autocomplete/{key}")
//    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//        Map<String, Object> automap = new HashMap<String, Object>();
//        List<TrnEmergencyBedAllocation> records;
//        records = trnemergencybedallocation.findByRouteNameContains(key);
//        automap.put("record", records);
//        return automap;
//    }

    @RequestMapping("byid/{ebaId}")
    public TrnEmergencyBedAllocation read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ebaId") Long ebaId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnEmergencyBedAllocation trnemergencybedallocation = trnemergencybedallocationRepository.getById(ebaId);
        return trnemergencybedallocation;
    }

    @RequestMapping("editbed")
    public TrnEmergencyBedAllocation editbed(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnEmergencyBedAllocation trnemergencybedallocation) {
        TenantContext.setCurrentTenant(tenantName);
        TrnEmergencyBedHistory trnEmergencyBedHistory = new TrnEmergencyBedHistory();
        List<MstVisit> mvlist = new ArrayList<MstVisit>();
        mvlist.add(trnemergencybedallocation.getEbaVisitId());
        trnEmergencyBedHistory.setEbhVisitId(mvlist);
        List<MipdBed> mblist = new ArrayList<MipdBed>();
        mblist.add(trnemergencybedallocation.getEbaBedId());
        trnEmergencyBedHistory.setEbhBedId(mblist);
        trnEmergencyBedHistoryRepository.save(trnEmergencyBedHistory);
        return trnemergencybedallocationRepository.save(trnemergencybedallocation);
    }

    @RequestMapping("update")
    public TrnEmergencyBedAllocation update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnEmergencyBedAllocation trnemergencybedallocation) {
        TenantContext.setCurrentTenant(tenantName);
        return trnemergencybedallocationRepository.save(trnemergencybedallocation);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnEmergencyBedAllocation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false, defaultValue = "0") long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ebaId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString != 0) {
            return trnemergencybedallocationRepository.findAllByEbaBedIdBedUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnemergencybedallocationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ebaId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ebaId") Long ebaId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnEmergencyBedAllocation trnemergencybedallocation = trnemergencybedallocationRepository.getById(ebaId);
        if (trnemergencybedallocation != null) {
            trnemergencybedallocation.setDeleted(true);
            trnemergencybedallocationRepository.save(trnemergencybedallocation);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("advoicedischargedlist")
    public Iterable<TrnEmergencyBedAllocation> advoicedischargedlist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ebaId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == 0) {
            return trnemergencybedallocationRepository.findAllByEbaPatientStatusEqualsOrEbaPatientStatusEqualsAndIsActiveTrueAndIsDeletedFalse(1, 4, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return trnemergencybedallocationRepository.findAllByEbaVisitIdVisitUnitIdUnitIdEqualsAndEbaPatientStatusEqualsOrEbaPatientStatusEqualsAndIsActiveTrueAndIsDeletedFalse(qString, 1, 4, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @RequestMapping("updatedEmergencyPatientStatusByVisitId/{visitId}/{status}")
    public Map<String, String> updatedEmergencyPatientStatusByVisitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId, @PathVariable("status") int status) {
        TenantContext.setCurrentTenant(tenantName);
        TrnEmergencyBedAllocation trnemergencybedallocation = trnemergencybedallocationRepository.findAllByEbaVisitIdVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(visitId);
        if (trnemergencybedallocation != null) {
            trnemergencybedallocation.setEbaPatientStatus(status);
            trnemergencybedallocationRepository.save(trnemergencybedallocation);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}

            
