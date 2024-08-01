package com.cellbeans.hspa.mstaddress;

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
@RequestMapping("/mst_address")
public class MstAddressController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstAddressRepository mstAddressRepository;

    @Autowired
    private MstAddressService mstAddressService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAddress mstAddress) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstAddress.getAddressAddress() != null) {
            mstAddressRepository.save(mstAddress);
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
        List<MstAddress> records;
        records = mstAddressRepository.findByAddressAddressContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{addressId}")
    public MstAddress read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("addressId") Long addressId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAddress mstAddress = mstAddressRepository.getById(addressId);
        return mstAddress;
    }

    @RequestMapping("update")
    public MstAddress update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAddress mstAddress) {
        TenantContext.setCurrentTenant(tenantName);
        return mstAddressRepository.save(mstAddress);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstAddress> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "addressId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstAddressRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstAddressRepository.findByAddressAddressContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{addressId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("addressId") Long addressId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAddress mstAddress = mstAddressRepository.getById(addressId);
        if (mstAddress != null) {
            mstAddress.setIsDeleted(true);
            mstAddressRepository.save(mstAddress);
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
        List<Tuple> items = mstAddressService.getMstAddressForDropdown(page, size, globalFilter);
        return items;
    }

}
            
