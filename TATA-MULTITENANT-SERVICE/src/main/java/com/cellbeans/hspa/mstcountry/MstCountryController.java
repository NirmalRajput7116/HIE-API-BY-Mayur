package com.cellbeans.hspa.mstcountry;

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
@RequestMapping("/mst_country")
public class MstCountryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstCountryRepository mstCountryRepository;

    @Autowired
    private MstCountryService mstCountryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCountry mstCountry) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstCountry.getCountryName() != null) {
            mstCountry.setCountryName(mstCountry.getCountryName().trim());
            MstCountry mstCountryObject = mstCountryRepository.findByCountryNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstCountry.getCountryName());
            if (mstCountryObject == null) {
                mstCountryRepository.save(mstCountry);
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
        List<MstCountry> records;
        records = mstCountryRepository.findByCountryNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{countryId}")
    public MstCountry read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("countryId") Long countryId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCountry mstCountry = mstCountryRepository.getById(countryId);
        return mstCountry;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCountry mstCountry) {
        TenantContext.setCurrentTenant(tenantName);
        mstCountry.setCountryName(mstCountry.getCountryName().trim());
        MstCountry mstCountryObject = mstCountryRepository.findByCountryNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstCountry.getCountryName());
        if (mstCountryObject == null) {
            mstCountryRepository.save(mstCountry);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstCountryObject.getCountryId() == mstCountry.getCountryId()) {
            mstCountryRepository.save(mstCountry);
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
    public Iterable<MstCountry> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort, @RequestParam(value = "col", required = false, defaultValue = "countryId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstCountryRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByCountryNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstCountryRepository.findByCountryNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{countryId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("countryId") Long countryId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCountry mstCountry = mstCountryRepository.getById(countryId);
        if (mstCountry != null) {
            mstCountry.setIsDeleted(true);
            mstCountryRepository.save(mstCountry);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/orgcountry/dropdown", method = RequestMethod.GET)
    public List getorgCountry(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstCountryService.getMstCountryForDropdown(page, size, globalFilter);
        return items;
    }

}
            
