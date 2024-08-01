package com.cellbeans.hspa.trnbedstatus;

import com.cellbeans.hspa.TenantContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_bed_status")
public class TrnBedStatusController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnBedStatusRepository trnBedStatusRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnBedStatus trnBedStatus) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnBedStatus.getTbsBedId() != null) {
            trnBedStatus.setTbsStatus(0);
            trnBedStatusRepository.save(trnBedStatus);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{tbsId}")
    public TrnBedStatus read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tbsId") Long tbsId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnBedStatus trnBedStatus = trnBedStatusRepository.getById(tbsId);
        return trnBedStatus;
    }

    @RequestMapping("getfreebedlistbyword/{wordId}")
    public List<TrnBedStatus> getfreebedlistbyword(@RequestHeader("X-tenantId") String tenantName, @PathVariable("wordId") Long wordId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsBedIdBedWardIdWardIdEqualsAndTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(wordId, 0);
    }

    @RequestMapping("getfreebedlistbyroom/{roomId}")
    public List<TrnBedStatus> getfreebedlistbyroom(@RequestHeader("X-tenantId") String tenantName, @PathVariable("roomId") Long roomId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsBedIdBedRoomIdRoomIdEqualsAndTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(roomId, 0);
    }

    @RequestMapping("getoccupiedlist")
    public List<TrnBedStatus> getoccupiedlist(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(1);
    }

    @RequestMapping("getbedsummary")
    public Map<String, Object> getbedsummary(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respsum = new HashMap<String, Object>();
        int occupied = trnBedStatusRepository.countByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(1);
        int nonOccupied = trnBedStatusRepository.countByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(0);
//        int maintaince =trnBedStatusRepository.countByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(3);
        int undermain = trnBedStatusRepository.countByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(4);
        int houseKeeping = trnBedStatusRepository.countByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(2);
        respsum.put("occupiedBedCount", occupied);
        respsum.put("nonOccupiedBedCount", nonOccupied);
//        respsum.put("maintainceBedCount",maintaince);
        respsum.put("underMainBedCount", undermain);
        respsum.put("houseKeepingBedCount", houseKeeping);
        return respsum;
    }

    @RequestMapping("getmaintanansbedlist")
    public List<TrnBedStatus> getmaintanansbedlist(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(3);
    }

    @RequestMapping("getreservebedlist")
    public List<TrnBedStatus> getreservebedlist(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(3);
    }

    @RequestMapping("update")
    public TrnBedStatus update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnBedStatus trnBedStatus) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.save(trnBedStatus);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnBedStatus> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tbsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @PutMapping("delete/{tbsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tbsId") Long tbsId) {
        TrnBedStatus trnBedStatus = trnBedStatusRepository.getById(tbsId);
        if (trnBedStatus != null) {
            trnBedStatus.setDeleted(true);
            trnBedStatusRepository.save(trnBedStatus);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("deleteByBedId/{bedId}")
    public Map<String, String> deleteByBedId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bedId") Long bedId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnBedStatus trnBedStatus = trnBedStatusRepository.findByTbsBedIdBedIdAndIsDeletedFalseAndIsActiveTrue(bedId);
        if (trnBedStatus != null) {
            trnBedStatus.setDeleted(true);
            trnBedStatusRepository.save(trnBedStatus);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("getDetailsByBedidAndStatus/{bedId}/{bedStatus}")
    public TrnBedStatus getDetailsByBedidAndStatus(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bedId") Long bedId, @PathVariable("bedStatus") int bedStatus) {
        TenantContext.setCurrentTenant(tenantName);
        TrnBedStatus trnBedStatus = trnBedStatusRepository.findByTbsBedIdBedIdAndTbsStatusAndIsDeletedFalseAndIsActiveTrue(bedId, bedStatus);
        return trnBedStatus;
    }

    @RequestMapping("updatebadestatus/{bedid}/{status}")
    public int updatebadestatus(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bedid") Long bedid, @PathVariable("status") int status) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.bedstatusupdate(status, bedid);
    }

    @GetMapping
    @RequestMapping("getavailablebedforemegency")
    public Iterable<TrnBedStatus> getavailablebedforemegency(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bed_id") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsStatusAndTbsBedIdEmegencyAndIsActiveTrueAndIsDeletedFalse(0, true, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    //get occupied bed by unit id
    @RequestMapping("get_occupied_list_by_unit/{unitId}")
    public List<TrnBedStatus> get_occupied_listbyUnit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsStatusEqualsAndTbsBedIdBedUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(1, unitId);
    }

    //get non occupied bed by unit id
    @RequestMapping("get_non_occupied_list_by_unit/{unitId}")
    public List<TrnBedStatus> get_non_occupied_list_by_unit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsStatusEqualsAndTbsBedIdBedUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(0, unitId);
    }

    //get under_maintenance bed by unit id
    @RequestMapping("get_under_maintenance_list_by_unit/{unitId}")
    public List<TrnBedStatus> get_under_maintenance_list_by_unit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsStatusEqualsAndTbsBedIdBedUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(2, unitId);
    }

    @RequestMapping("get_maintainance_list_by_unit/{unitId}")
    public List<TrnBedStatus> get_maintainance_list_by_unit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsStatusEqualsAndTbsBedIdBedUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(4, unitId);
    }

    @RequestMapping("getAllfreebedlistbyword/{wordId}")
    public List<TrnBedStatus> getAllfreebedlistbyword(@RequestHeader("X-tenantId") String tenantName, @PathVariable("wordId") Long wordId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsBedIdBedWardIdWardIdEqualsAndIsActiveTrueAndIsDeletedFalse(wordId);
    }

    @RequestMapping("getAllbedlistbyroom/{roomId}")
    public List<TrnBedStatus> getAllbedlistbyroom(@RequestHeader("X-tenantId") String tenantName, @PathVariable("roomId") Long roomId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnBedStatusRepository.findAllByTbsBedIdBedRoomIdRoomIdEqualsAndIsActiveTrueAndIsDeletedFalse(roomId);
    }

}
            
