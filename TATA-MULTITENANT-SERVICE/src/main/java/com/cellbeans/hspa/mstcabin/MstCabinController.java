package com.cellbeans.hspa.mstcabin;

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
@RequestMapping("/mst_cabin")
public class MstCabinController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstCabinRepository mstCabinRepository;

    @Autowired
    private MstCabinService mstCabinService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCabin mstCabin) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstCabin.getCabinName() != null) {
            if (mstCabinRepository.findByAllOrderByCabinName(mstCabin.getCabinName()) == 0) {
                mstCabinRepository.save(mstCabin);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "");
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
        List<MstCabin> records;
        records = mstCabinRepository.findByCabinNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{cabinId}")
    public MstCabin read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cabinId") Long cabinId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCabin mstCabin = mstCabinRepository.getById(cabinId);
        return mstCabin;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCabin mstCabin) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstCabinRepository.findByAllOrderByCabinName(mstCabin.getCabinName()) == 0) {
            mstCabinRepository.save(mstCabin);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
        } else {
            respMap.put("success", "");
            respMap.put("msg", "Already Added");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstCabin> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "cabinId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstCabinRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstCabinRepository.findByCabinNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{cabinId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cabinId") Long cabinId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCabin mstCabin = mstCabinRepository.getById(cabinId);
        if (mstCabin != null) {
            mstCabin.setIsDeleted(true);
            mstCabinRepository.save(mstCabin);
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
        List<Tuple> items = mstCabinService.getMstCabinForDropdown(page, size, globalFilter);
        return items;
    }

}
            
