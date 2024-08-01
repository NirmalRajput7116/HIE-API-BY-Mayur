package com.cellbeans.hspa.mstbookingtype;

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
@RequestMapping("/mst_booking_type")
public class MstBookingTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstBookingTypeRepository mstBookingTypeRepository;
    @Autowired
    private MstBookingTypeService mstBookingTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstBookingType mstBookingType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstBookingType.getBtName() != null) {
            mstBookingType.setBtName(mstBookingType.getBtName().trim());
            MstBookingType mstBookingTypeObject = mstBookingTypeRepository.findByBtNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstBookingType.getBtName());
            if (mstBookingTypeObject == null) {
                mstBookingTypeRepository.save(mstBookingType);
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
        List<MstBookingType> records;
        records = mstBookingTypeRepository.findByBtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{btId}")
    public MstBookingType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("btId") Long btId) {
        TenantContext.setCurrentTenant(tenantName);
        MstBookingType mstBookingType = mstBookingTypeRepository.getById(btId);
        return mstBookingType;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstBookingType mstBookingType) {
        TenantContext.setCurrentTenant(tenantName);
        mstBookingType.setBtName(mstBookingType.getBtName().trim());
        MstBookingType mstBookingTypeObject = mstBookingTypeRepository.findByBtNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstBookingType.getBtName());
        if (mstBookingTypeObject == null) {
            mstBookingTypeRepository.save(mstBookingType);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstBookingTypeObject.getBtId() == mstBookingType.getBtId()) {
            mstBookingTypeRepository.save(mstBookingType);
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
    public Iterable<MstBookingType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "btId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstBookingTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstBookingTypeRepository.findByBtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{btId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("btId") Long btId) {
        TenantContext.setCurrentTenant(tenantName);
        MstBookingType mstBookingType = mstBookingTypeRepository.getById(btId);
        if (mstBookingType != null) {
            mstBookingType.setIsDeleted(true);
            mstBookingTypeRepository.save(mstBookingType);
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
        List<Tuple> items = mstBookingTypeService.getMstBookingTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
