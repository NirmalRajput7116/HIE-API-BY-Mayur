package com.cellbeans.hspa.mstdistrict;

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
@RequestMapping("/mst_district")
public class MstDistrictController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstDistrictRepository mstDistrictRepository;

    @Autowired
    private MstDistrictService mstDistrictService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDistrict mstDistrict) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDistrict.getDistrictName() != null) {
            mstDistrict.setDistrictName(mstDistrict.getDistrictName().trim());
            MstDistrict mstDistrictObject = mstDistrictRepository.findByDistrictName(mstDistrict.getDistrictName(), mstDistrict.getDistrictStateId().getStateId());
            if (mstDistrictObject == null) {
                mstDistrictRepository.save(mstDistrict);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Already Added");
                return respMap;
            }
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
        List<MstDistrict> records;
        records = mstDistrictRepository.findByDistrictNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{districtId}")
    public MstDistrict read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("districtId") Long districtId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDistrict mstDistrict = mstDistrictRepository.getById(districtId);
        return mstDistrict;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDistrict mstDistrict) {
        TenantContext.setCurrentTenant(tenantName);
        mstDistrict.setDistrictName(mstDistrict.getDistrictName().trim());
        MstDistrict mstDistrictObject = mstDistrictRepository.findByDistrictName(mstDistrict.getDistrictName(), mstDistrict.getDistrictStateId().getStateId());
        if (mstDistrictObject == null) {
            mstDistrictRepository.save(mstDistrict);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstDistrictObject.getDistrictId() == mstDistrict.getDistrictId()) {
            mstDistrictRepository.save(mstDistrict);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDistrict> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "districtId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDistrictRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByDistrictNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDistrictRepository.findByDistrictNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{districtId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("districtId") Long districtId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDistrict mstDistrict = mstDistrictRepository.getById(districtId);
        if (mstDistrict != null) {
            mstDistrict.setIsDeleted(true);
            mstDistrictRepository.save(mstDistrict);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("bystateid/{stateid}")
    public List<MstDistrict> statebyID(@PathVariable("stateid") Long stateid) {
//        TenantContext.setCurrentTenant(tenantName);
        return mstDistrictRepository.findByDistrictStateIdStateIdEqualsAndIsActiveTrueAndIsDeletedFalse(stateid);

    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstDistrictService.getMstDistrictForDropdown(page, size, globalFilter);
        return items;
    }

}
            