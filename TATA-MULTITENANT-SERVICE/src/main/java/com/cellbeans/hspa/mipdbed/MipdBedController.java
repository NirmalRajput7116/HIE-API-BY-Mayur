package com.cellbeans.hspa.mipdbed;

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
@RequestMapping("/mipd_bed")
public class MipdBedController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdBedRepository mipdBedRepository;

    @Autowired
    private MipdBedService mipdBedService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdBed mipdBed) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdBed.getBedName() != null) {
            System.out.println("------------bed--------------------------------");
            System.out.println(mipdBed.getEmegency());
            MipdBed mipdBedCreated = new MipdBed();
            mipdBedCreated = mipdBedRepository.save(mipdBed);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("bedid", Long.toString(mipdBedCreated.getBedId()));
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
        List<MipdBed> records;
        records = mipdBedRepository.findByBedNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("bywordid/{wordId}")
    public List<MipdBed> bywordid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("wordId") Long wordId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("-----------ward--------------------------");
        System.out.println(wordId);
        return mipdBedRepository.findAllByBedWardIdWardIdEquals(wordId);
    }

    @RequestMapping("byid/{bedId}")
    public MipdBed read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bedId") Long bedId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdBed mipdBed = mipdBedRepository.getById(bedId);
        return mipdBed;
    }

    @RequestMapping("byroomid/{roomId}")
    public List<MipdBed> findBedbyRoomId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("roomId") Long roomId) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdBedRepository.findByBedRoomIdRoomIdAndIsActiveTrueAndIsDeletedFalse(roomId);

    }

    @RequestMapping("update")
    public MipdBed update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdBed mipdBed) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdBedRepository.save(mipdBed);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdBed> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bedId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdBedRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdBedRepository.findByBedNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            //return mipdBedRepository.findByBedNameContainsAndIsActiveTrueAndIsDeletedFalseOrBedCodeContainsAndIsActiveTrueAndIsDeletedFalseOrBedBcIdBcNameContainsAndIsActiveTrueAndIsDeletedFalseOrBedRoomIdRoomNameContainsAndIsActiveTrueAndIsDeletedFalseOrBedWardIdWardNameContainsAndIsActiveTrueAndIsDeletedFalseOrBedUnitIdUnitNameAndIsActiveTrueAndIsDeletedFalse(qString,qString,qString,qString,qString,qString, new PageRequest(Integer.parseInt(page) - 1,Integer.parseInt(size), Sort.Direction.fromStringOrNull(sort), col));
        }

    }

    @PutMapping("delete/{bedId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bedId") Long bedId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdBed mipdBed = mipdBedRepository.getById(bedId);
        if (mipdBed != null) {
            mipdBed.setIsDeleted(true);
            mipdBedRepository.save(mipdBed);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mipdBedService.getMipdBedForDropdown(page, size, globalFilter);
        return items;
    }

}
            
