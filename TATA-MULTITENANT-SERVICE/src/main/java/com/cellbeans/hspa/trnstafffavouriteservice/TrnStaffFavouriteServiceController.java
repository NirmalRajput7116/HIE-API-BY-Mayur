package com.cellbeans.hspa.trnstafffavouriteservice;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_staff_favourite_service")
public class TrnStaffFavouriteServiceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnStaffFavouriteServiceRepository trnStaffFavouriteServiceRepository;

    @Autowired
    CreateJSONObject createJSONObject;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnStaffFavouriteService trnStaffFavouriteService) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnStaffFavouriteService.getTsfsServiceId() != null) {
            trnStaffFavouriteServiceRepository.save(trnStaffFavouriteService);
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
    public TrnStaffFavouriteService update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnStaffFavouriteService trnStaffFavouriteService) {
        TenantContext.setCurrentTenant(tenantName);
        return trnStaffFavouriteServiceRepository.save(trnStaffFavouriteService);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnStaffFavouriteService> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tsfsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return trnStaffFavouriteServiceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @PutMapping("delete/{id}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        TrnStaffFavouriteService trnStaffFavouriteService = trnStaffFavouriteServiceRepository.getById(id);
        if (trnStaffFavouriteService != null) {
            trnStaffFavouriteService.setDeleted(true);
            trnStaffFavouriteServiceRepository.save(trnStaffFavouriteService);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("get_all_record/{staffId}/{unitId}")
    public ResponseEntity get_all_record(@RequestHeader("X-tenantId") String tenantName, @PathVariable long staffId, @PathVariable long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        String columnName = "";
        String Query = "select s.service_id,s.service_name,sfs.tsfs_id from trn_staff_favourite_service  sfs " + "inner join mbill_service s on s.service_id = sfs.tsfs_service_id " + "where  sfs.tsfs_staff_id = " + staffId + " and  sfs.tsfs_unit_id =" + unitId + " and sfs.is_active = 1 and sfs.is_deleted = 0";
        columnName = "service_id,service_name,tsfs_id";
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, null));
    }

}
            
