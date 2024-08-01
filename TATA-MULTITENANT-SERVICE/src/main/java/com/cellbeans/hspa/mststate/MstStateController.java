package com.cellbeans.hspa.mststate;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_state")
public class MstStateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstStateRepository mstStateRepository;

    @Autowired
    private MstStateService mstStateService;

//    @RequestMapping("create")
//    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstState mstState) {
//        TenantContext.setCurrentTenant(tenantName);
//        if (mstState.getStateName() != null) {
//            mstState.setStateName(mstState.getStateName().trim());
//            MstState mstStateObject = mstStateRepository.findByAllOrderByStateName(mstState.getStateName(), mstState.getStateCountryId().getCountryId());
//            if (mstStateObject == null) {
//                mstStateRepository.save(mstState);
//                respMap.put("success", "1");
//                respMap.put("msg", "Added Successfully");
//                return respMap;
//            } else {
//                respMap.put("success", "2");
//                respMap.put("msg", "Already Added");
//                return respMap;
//            }
//        } else {
//            respMap.put("success", "0");
//            respMap.put("msg", "Failed To Add Null Field");
//            return respMap;
//        }
//    }
//

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MstState> mstStateList) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, String> respMap = new HashMap<>();

        for (MstState mstState : mstStateList) {
            if (mstState.getStateName() != null) {
                mstState.setStateName(mstState.getStateName().trim());
                MstState mstStateObject = mstStateRepository.findByAllOrderByStateName(mstState.getStateName(), mstState.getStateCountryId().getCountryId());
                if (mstStateObject == null) {
                    mstStateRepository.save(mstState);
                    respMap.put("success", "1");
                    respMap.put("msg", "Added Successfully");
                } else {
                    respMap.put("success", "2");
                    respMap.put("msg", "Already Added");
                }
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
            }
        }
        return respMap;
    }


    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstState> records;
        records = mstStateRepository.findByStateNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{stateId}")
    public MstState read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("stateId") Long stateId) {
        TenantContext.setCurrentTenant(tenantName);
        MstState mstState = mstStateRepository.getById(stateId);
        return mstState;
    }

    @RequestMapping("statebyID/{stateId}")
    public List<MstState> statebyID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("stateId") Long stateId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStateRepository.findByStateCountryIdCountryIdEqualsAndIsActiveTrueAndIsDeletedFalse(stateId);

    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstState mstState) {
        TenantContext.setCurrentTenant(tenantName);
        mstState.setStateName(mstState.getStateName().trim());
        MstState mstStateObject = mstStateRepository.findByAllOrderByStateName(mstState.getStateName(), mstState.getStateCountryId().getCountryId());
        if (mstStateObject == null) {
            mstStateRepository.save(mstState);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstStateObject.getStateId() == mstState.getStateId()) {
            mstStateRepository.save(mstState);
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
    public Iterable<MstState> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort, @RequestParam(value = "col", required = false, defaultValue = "stateId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstStateRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByStateNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstStateRepository.findByStateNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByStateNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{stateId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("stateId") Long stateId) {
        TenantContext.setCurrentTenant(tenantName);
        MstState mstState = mstStateRepository.getById(stateId);
        if (mstState != null) {
            mstState.setIsDeleted(true);
            mstStateRepository.save(mstState);
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
        List<Tuple> items = mstStateService.getMstStateForDropdown(page, size, globalFilter);
        return items;
    }

}

