package com.cellbeans.hspa.mstdoctorschedulemaster;

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
@RequestMapping("/mst_doctor_schedule_master")
public class MstDoctorScheduleMasterController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstDoctorScheduleMasterRepository mstDoctorScheduleMasterScheduleRepository;

    @Autowired
    private MstDoctorScheduleMasterService mstDoctorScheduleMasterService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MstDoctorScheduleMaster> mstDoctorScheduleMaster) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDoctorScheduleMaster.get(0).getDsmDayName() != null) {
            mstDoctorScheduleMasterScheduleRepository.saveAll(mstDoctorScheduleMaster);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{dmId}")
    public MstDoctorScheduleMaster read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dmId") Long dmId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoctorScheduleMaster mstDoctorScheduleMaster = mstDoctorScheduleMasterScheduleRepository.getById(dmId);
        return mstDoctorScheduleMaster;
    }

    @RequestMapping("listbyid/{dmId}")
    public List<MstDoctorScheduleMaster> readbyId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dmId") Long dmId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDoctorScheduleMasterScheduleRepository.findAllByDsmDmIdDmIdAndIsActiveTrueAndIsDeletedFalse(dmId);
    }

    @RequestMapping("update")
    public MstDoctorScheduleMaster update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoctorScheduleMaster mstDoctorScheduleMaster) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDoctorScheduleMasterScheduleRepository.save(mstDoctorScheduleMaster);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDoctorScheduleMaster> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dsmId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDoctorScheduleMasterScheduleRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDoctorScheduleMasterScheduleRepository.findByDsmDayNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listbydaydoc")
    public Iterable<MstDoctorScheduleMaster> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "dayName") String dayName, @RequestParam(value = "dmId", required = false, defaultValue = "nan") String dmId) {
        TenantContext.setCurrentTenant(tenantName);
        if (dmId.equals("nan")) {
            return mstDoctorScheduleMasterScheduleRepository.findAllByDsmDayNameContainsAndIsActiveTrueAndIsDeletedFalse(dayName);

        } else {
            return mstDoctorScheduleMasterScheduleRepository.findAllByDsmDayNameContainsAndDsmDmIdDmIdAndIsActiveTrueAndIsDeletedFalse(dayName, Long.valueOf(dmId));
        }

    }

    @PutMapping("delete/{dsmId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsmId") Long dsmId) {
        TenantContext.setCurrentTenant(tenantName);
        mstDoctorScheduleMasterScheduleRepository.deleteById(dsmId);
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
//        MstDoctorScheduleMaster mstDoctorScheduleMaster = mstDoctorScheduleMasterScheduleRepository.getById(departmentId);
//        if (mstDoctorScheduleMaster != null) {
//            mstDoctorScheduleMaster.setDeleted(true);
//            mstDoctorScheduleMasterScheduleRepository.save(mstDoctorScheduleMaster);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        } else {
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstDoctorScheduleMasterService.getMstDoctorScheduleMasterForDropdown(page, size, globalFilter);
        return items;
    }

}
            
