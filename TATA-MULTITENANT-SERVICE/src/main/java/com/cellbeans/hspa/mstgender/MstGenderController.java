package com.cellbeans.hspa.mstgender;

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
@RequestMapping("/mst_gender")
public class MstGenderController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstGenderRepository mstGenderRepository;

    @Autowired
    private MstGenderService mstGenderService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstGender mstGender) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("Gender :"+mstGender);
        if (mstGender.getGenderName() != null) {
            mstGender.setGenderName(mstGender.getGenderName().trim());
            MstGender mstGenderObject = mstGenderRepository.findByGenderNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstGender.getGenderName());
            if (mstGenderObject == null) {
                mstGenderRepository.save(mstGender);
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
        List<MstGender> records;
        records = mstGenderRepository.findByGenderNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{genderId}")
    public MstGender read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("genderId") Long genderId) {
        TenantContext.setCurrentTenant(tenantName);
        MstGender mstGender = mstGenderRepository.getById(genderId);
        return mstGender;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstGender mstGender) {
        TenantContext.setCurrentTenant(tenantName);
        mstGender.setGenderName(mstGender.getGenderName().trim());
        MstGender mstGenderObject = mstGenderRepository.findByGenderNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstGender.getGenderName());
        if (mstGenderObject == null) {
            mstGenderRepository.save(mstGender);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstGenderObject.getGenderId() == mstGender.getGenderId()) {
            mstGenderRepository.save(mstGender);
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
    public Iterable<MstGender> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "genderId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstGenderRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByGenderNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstGenderRepository.findByGenderNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByGenderNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{genderId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("genderId") Long genderId) {
        TenantContext.setCurrentTenant(tenantName);
        MstGender mstGender = mstGenderRepository.getById(genderId);
        if (mstGender != null) {
            mstGender.setIsDeleted(true);
            mstGenderRepository.save(mstGender);
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
        List<Tuple> items = mstGenderService.getMstGenderForDropdown(page, size, globalFilter);
        return items;
    }

}
            
