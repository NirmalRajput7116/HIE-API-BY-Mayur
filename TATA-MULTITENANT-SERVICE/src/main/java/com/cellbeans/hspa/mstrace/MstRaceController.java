package com.cellbeans.hspa.mstrace;

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
@RequestMapping("/mst_race")
public class MstRaceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstRaceRepository mstRaceRepository;

    @Autowired
    private MstRaceService mstRaceService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstRace mstRace) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstRace.getRaceName() != null) {
            mstRaceRepository.save(mstRace);
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
        List<MstRace> records;
        records = mstRaceRepository.findByRaceNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{raceId}")
    public MstRace read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("raceId") Long raceId) {
        TenantContext.setCurrentTenant(tenantName);
        MstRace mstRace = mstRaceRepository.getById(raceId);
        return mstRace;
    }

    @RequestMapping("update")
    public MstRace update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstRace mstRace) {
        TenantContext.setCurrentTenant(tenantName);
        return mstRaceRepository.save(mstRace);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstRace> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "raceId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstRaceRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByRaceName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstRaceRepository.findByRaceNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByRaceName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{raceId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("raceId") Long raceId) {
        TenantContext.setCurrentTenant(tenantName);
        MstRace mstRace = mstRaceRepository.getById(raceId);
        if (mstRace != null) {
            mstRace.setIsDeleted(true);
            mstRaceRepository.save(mstRace);
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
        List<Tuple> items = mstRaceService.getMstRaceForDropdown(page, size, globalFilter);
        return items;
    }

}
            
