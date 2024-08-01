package com.cellbeans.hspa.mstdoctorroaster;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_doctor_roaster")
public class MstDoctorRoasterController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstDoctorRoasterRepository mstDoctorRoasterRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoctorRoaster mstDoctorRoaster) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoctorRoaster dr;
        if (mstDoctorRoaster.getDrDmId() != null) {
            dr = mstDoctorRoasterRepository.save(mstDoctorRoaster);
            respMap.put("success", "1");
            respMap.put("drId", Long.toString(dr.getDrId()));
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{dmId}")
    public MstDoctorRoaster read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dmId") Long dmId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDoctorRoaster mstDoctorRoaster = mstDoctorRoasterRepository.getById(dmId);
        return mstDoctorRoaster;
    }

    @RequestMapping("listbydate")
    public List<MstDoctorRoaster> readbyDate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "date") Date date) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDoctorRoasterRepository.findByDate(date);
    }

    @RequestMapping("update")
    public MstDoctorRoaster update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDoctorRoaster mstDoctorRoaster) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDoctorRoasterRepository.save(mstDoctorRoaster);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDoctorRoaster> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dmId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDoctorRoasterRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDoctorRoasterRepository.findByDrDmIdDmIdAndIsActiveTrueAndIsDeletedFalse(Long.valueOf(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{drId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("drId") Long drId) {
        TenantContext.setCurrentTenant(tenantName);
        mstDoctorRoasterRepository.deleteById(drId);
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

}
            
