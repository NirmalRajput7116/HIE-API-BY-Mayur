package com.cellbeans.hspa.mstsuperspeciality;

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
@RequestMapping("/mst_super_speciality")
public class MstSuperSpecialityController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstSuperSpecialityRepository mstSuperSpecialityRepository;

    @Autowired
    private MstSuperSpecialityService mstSuperSpecialityService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSuperSpeciality mstSuperSpeciality) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstSuperSpeciality.getSsName() != null) {
            mstSuperSpeciality.setSsName(mstSuperSpeciality.getSsName().trim());
            MstSuperSpeciality mstSuperSpecialityObject = mstSuperSpecialityRepository.findBySsNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstSuperSpeciality.getSsName());
            if (mstSuperSpecialityObject == null) {
                mstSuperSpecialityRepository.save(mstSuperSpeciality);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Duplicate SuperSpeciality Name");
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
        List<MstSuperSpeciality> records;
        records = mstSuperSpecialityRepository.findBySsNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ssId}")
    public MstSuperSpeciality read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ssId") Long ssId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSuperSpeciality mstSuperSpeciality = mstSuperSpecialityRepository.getById(ssId);
        return mstSuperSpeciality;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSuperSpeciality mstSuperSpeciality) {
        TenantContext.setCurrentTenant(tenantName);
//        return mstSuperSpecialityRepository.save(mstSuperSpeciality);
        mstSuperSpeciality.setSsName(mstSuperSpeciality.getSsName().trim());
        MstSuperSpeciality mstSuperSpecialityObject = mstSuperSpecialityRepository.findBySsNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstSuperSpeciality.getSsName());
        if (mstSuperSpecialityObject == null) {
            mstSuperSpecialityRepository.save(mstSuperSpeciality);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstSuperSpecialityObject.getSsId() == mstSuperSpeciality.getSsId()) {
            mstSuperSpecialityRepository.save(mstSuperSpeciality);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Duplicate Super Speciality Found");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstSuperSpeciality> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ssId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstSuperSpecialityRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderBySsName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstSuperSpecialityRepository.findBySsNameContainsAndIsActiveTrueAndIsDeletedFalseOrSsSpecialityIdSpecialityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderBySsName(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ssId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ssId") Long ssId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSuperSpeciality mstSuperSpeciality = mstSuperSpecialityRepository.getById(ssId);
        if (mstSuperSpeciality != null) {
            mstSuperSpeciality.setIsDeleted(true);
            mstSuperSpecialityRepository.save(mstSuperSpeciality);
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
        List<Tuple> items = mstSuperSpecialityService.getMstSuperSpecialityForDropdown(page, size, globalFilter);
        return items;
    }

    @RequestMapping(value = "/dropdownbyspecilityId", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter, @RequestParam(value = "specialityId", required = false) long specialityId) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstSuperSpecialityService.getMstSuperSpecialityForDropdownBySpecilityId(page, size, globalFilter, specialityId);
        return items;

    }

    @RequestMapping(value = "/dropdownspecilityId", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "specialityId", required = false) long specialityId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstSuperSpecialityRepository.findByssSpecialityIdSpecialityId(specialityId);

    }

}
            
