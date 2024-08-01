package com.cellbeans.hspa.trnservicecabinschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.cellbeans.hspa.TenantContext;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_service_cabin_schedule")
public class TrnServiceCabinScheduleController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnServiceCabinScheduleRepository trnservicecabinscheduleRepository;

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnServiceCabinSchedule trnservicecabinschedule) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnservicecabinschedule.getScsBsId() != null) {
            trnservicecabinschedule.setScsDate(new Date());
            trnservicecabinscheduleRepository.save(trnservicecabinschedule);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{dayId}")
    public TrnServiceCabinSchedule read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dayId") Long dayId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnServiceCabinSchedule trnservicecabinschedule = trnservicecabinscheduleRepository.getById(dayId);
        return trnservicecabinschedule;
    }

    @RequestMapping("update")
    public TrnServiceCabinSchedule update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnServiceCabinSchedule mstDay) {
        TenantContext.setCurrentTenant(tenantName);
        return trnservicecabinscheduleRepository.save(mstDay);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnServiceCabinSchedule> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dayId") String col) {
        TenantContext.setCurrentTenant(tenantName);
//        if (qString == null || qString.equals("")) {
        return trnservicecabinscheduleRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        }
//        else {
//
//            return trnservicecabinscheduleRepository.findByDayNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        }
    }

    @PutMapping("delete/{dayId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dayId") Long dayId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnServiceCabinSchedule trnservicecabinschedule = trnservicecabinscheduleRepository.getById(dayId);
        if (trnservicecabinschedule != null) {
            trnservicecabinschedule.setIsDeleted(true);
            trnservicecabinscheduleRepository.save(trnservicecabinschedule);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("gettokendisplayforallstaff")
    public Iterable<TrnServiceCabinSchedule> gettokendisplayforallstaff(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return trnservicecabinscheduleRepository.findAllByIsActiveTrueAndIsDeletedFalse();
    }

    @RequestMapping("gettokendisplayforoncology/{id}")
    public Iterable<TrnServiceCabinSchedule> gettokendisplayforoncology(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        //Oncology
        if (id == 0) {
            return trnservicecabinscheduleRepository.findAllByIsActiveTrueAndIsDeletedFalse("Oncology");
        }//Multi OPD
        else {
            return trnservicecabinscheduleRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        }
    }

    @RequestMapping("getTokenDisplayByUnitId/{id}")
    public Iterable<TrnServiceCabinSchedule> getTokenDisplayByUnitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        java.sql.Date todaysdate;
        LocalDate today = LocalDate.now();
        todaysdate = java.sql.Date.valueOf(today);
        return trnservicecabinscheduleRepository.findAllByIsActiveTrueAndIsDeletedFalseAndScsBsIdBsStatusEqulasOrScsBsIdBsStatusEqulasAndScsBsIdBsDateEqualsAndScsBsIdBsBillIdIpdBillFalse(id);

    }

    @RequestMapping("getRecordServiceId/{id}")
    public TrnServiceCabinSchedule getRecordServiceId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return trnservicecabinscheduleRepository.findAllByScsBsIdBsIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

    @RequestMapping("get_token_by_doctor_list/{doctorList}")
    public List<TrnServiceCabinSchedule> get_token_by_doctor_list(@RequestHeader("X-tenantId") String tenantName, @PathVariable("doctorList") Long[] doctorList) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnServiceCabinSchedule> trnServiceCabinSchedulelist;
        // String doclist =   removeLastChar(doctorList);
        System.out.println("----------doclist--------------");
        // System.out.println(doclist);
        trnServiceCabinSchedulelist = trnservicecabinscheduleRepository.findAllByDoctorList(doctorList);
        System.out.println("--------trnServiceCabinSchedulelist.size()--------");
        System.out.println(trnServiceCabinSchedulelist.size());
        for (int i = 0; i < trnServiceCabinSchedulelist.size(); i++) {
            System.out.println(trnServiceCabinSchedulelist.get(i).getScsBsId().getBsStaffId().getStaffUserId().getUserFirstname());
        }
        return trnServiceCabinSchedulelist;

    }

}


