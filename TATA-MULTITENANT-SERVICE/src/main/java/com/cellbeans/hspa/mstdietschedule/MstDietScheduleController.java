package com.cellbeans.hspa.mstdietschedule;

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
@RequestMapping("/mst_diet_schedule")
public class MstDietScheduleController {

    @Autowired
    MstDietScheduleRepository mstDietScheduleRepository;
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    private MstDietScheduleService mstDietScheduleService;

    @GetMapping
    @RequestMapping("schedulelist")
    public List<MstDietSchedule> list() {
        return mstDietScheduleRepository.findAll();
    }

    @GetMapping
    @RequestMapping("/getTimeSchedule/{id}")
    public MstDietSchedule getTimeSchedule(@RequestHeader("X-tenantId") String tenantName, @PathVariable String id) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDietScheduleRepository.findByDsId(Long.parseLong(id));

    }

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDietSchedule mstDietSchedule) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDietSchedule.getDsDietName() != null) {
            mstDietSchedule.setDsDietName(mstDietSchedule.getDsDietName().trim());
            MstDietSchedule mstDietScheduleObject = mstDietScheduleRepository.findByDsDietNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstDietSchedule.getDsDietName());
            if (mstDietScheduleObject == null) {
                mstDietSchedule.setIsDeleted(false);
                mstDietSchedule.setIsActive(true);
                mstDietScheduleRepository.save(mstDietSchedule);
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
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") Long key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstDietSchedule> records;
        records = mstDietScheduleRepository.findByDsIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dsId}")
    public MstDietSchedule read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsId") Long dsId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDietSchedule mstDietSchedule = mstDietScheduleRepository.getById(dsId);
        return mstDietSchedule;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDietSchedule mstDietSchedule) {
        TenantContext.setCurrentTenant(tenantName);
        mstDietSchedule.setDsDietName(mstDietSchedule.getDsDietName().trim());
        MstDietSchedule mstDietScheduleObject = mstDietScheduleRepository.findByDsDietNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstDietSchedule.getDsDietName());
        if (mstDietScheduleObject == null) {
            mstDietSchedule.setIsDeleted(false);
            mstDietSchedule.setIsActive(true);
            mstDietScheduleRepository.save(mstDietSchedule);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstDietScheduleObject.getDsId() == mstDietSchedule.getDsId()) {
            mstDietSchedule.setIsDeleted(false);
            mstDietSchedule.setIsActive(true);
            mstDietScheduleRepository.save(mstDietSchedule);
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
    public Iterable<MstDietSchedule> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDietScheduleRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDietScheduleRepository.findByDsDietNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsId") Long dsId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDietSchedule mstDietSchedule = mstDietScheduleRepository.getById(dsId);
        if (mstDietSchedule != null) {
            mstDietSchedule.setIsDeleted(true);
            mstDietScheduleRepository.save(mstDietSchedule);
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
        List<Tuple> items = mstDietScheduleService.getMstDietScheduleForDropdown(page, size, globalFilter);
        return items;
    }

}
