package com.cellbeans.hspa.mstspeciality;

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
@RequestMapping("/mst_speciality")
public class MstSpecialityController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstSpecialityRepository mstSpecialityRepository;

    @Autowired
    private MstSpecialityService mstSpecialityService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSpeciality mstSpeciality) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstSpeciality.getSpecialityName() != null) {
            mstSpeciality.setSpecialityName(mstSpeciality.getSpecialityName().trim());
            MstSpeciality mstSpecialityObject = mstSpecialityRepository.findBySpecialityNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstSpeciality.getSpecialityName());
            if (mstSpecialityObject == null) {
                mstSpecialityRepository.save(mstSpeciality);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Duplicate Speciality Name");
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
        List<MstSpeciality> records;
        records = mstSpecialityRepository.findBySpecialityNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{specialityId}")
    public MstSpeciality read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("specialityId") Long specialityId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSpeciality mstSpeciality = mstSpecialityRepository.getById(specialityId);
        return mstSpeciality;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSpeciality mstSpeciality) {
        TenantContext.setCurrentTenant(tenantName);
//        return mstSpecialityRepository.save(mstSpeciality);
        mstSpeciality.setSpecialityName(mstSpeciality.getSpecialityName().trim());
        MstSpeciality mstSpecialityObject = mstSpecialityRepository.findBySpecialityNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstSpeciality.getSpecialityName());
        if (mstSpecialityObject == null) {
            mstSpecialityRepository.save(mstSpeciality);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstSpecialityObject.getSpecialityId() == mstSpeciality.getSpecialityId()) {
            mstSpecialityRepository.save(mstSpeciality);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Duplicate Speciality Found");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstSpeciality> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10000") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "specialityId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstSpecialityRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderBySpecialityName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstSpecialityRepository.findBySpecialityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderBySpecialityName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{specialityId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("specialityId") Long specialityId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSpeciality mstSpeciality = mstSpecialityRepository.getById(specialityId);
        if (mstSpeciality != null) {
            mstSpeciality.setIsDeleted(true);
            mstSpecialityRepository.save(mstSpeciality);
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
        List<Tuple> items = mstSpecialityService.getMstSpecialityForDropdown(page, size, globalFilter);
        return items;
    }

    @RequestMapping(value = "/getAllDropDown", method = RequestMethod.GET)
    public List getAllDropDown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstSpecialityService.getAllMstSpecialityForDropdown(page, size, globalFilter);
        return items;
    }

}
            
