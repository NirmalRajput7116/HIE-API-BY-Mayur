package com.cellbeans.hspa.mstdoctormaster;

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
@RequestMapping("/mst_doctor_master")
public class MstDoctorMasterController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstDoctorMasterRepository mstDoctorMasterRepository;

    @Autowired
    private MstDoctorMasterService mstDoctorMasterService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoctorMaster mstDoctorMaster) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoctorMaster dm;
        if (mstDoctorMaster.getDmFirstName() != null) {
            dm = mstDoctorMasterRepository.save(mstDoctorMaster);
            respMap.put("success", "1");
            respMap.put("dmId", Long.toString(dm.getDmId()));
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{dmId}")
    public MstDoctorMaster read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dmId") Long dmId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoctorMaster mstDoctorMaster = mstDoctorMasterRepository.getById(dmId);
        return mstDoctorMaster;
    }

    @RequestMapping("update")
    public MstDoctorMaster update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoctorMaster mstDoctorMaster) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDoctorMasterRepository.save(mstDoctorMaster);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDoctorMaster> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dmId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDoctorMasterRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDoctorMasterRepository.findByDmFirstNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{departmentId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("departmentId") Long departmentId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoctorMaster mstDoctorMaster = mstDoctorMasterRepository.getById(departmentId);
        if (mstDoctorMaster != null) {
            mstDoctorMaster.setDeleted(true);
            mstDoctorMasterRepository.save(mstDoctorMaster);
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
        List<Tuple> items = mstDoctorMasterService.getMstDoctorMasterForDropdown(page, size, globalFilter);
        return items;
    }

}
            
