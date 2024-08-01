package com.cellbeans.hspa.trnstaffcabinschedule;

import com.cellbeans.hspa.mstday.MstDay;
import com.cellbeans.hspa.mstday.MstDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.cellbeans.hspa.TenantContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/trn_staff_cabin_schedule")
public class TrnStaffCabinScheduleController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnStaffCabinScheduleRepository trnStaffCabinScheduleRepository;

    @Autowired
    MstDayRepository mstDayRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnStaffCabinSchedule trnStaffCabinSchedule) {
        TenantContext.setCurrentTenant(tenantName);
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm:ss");
        try {
            trnStaffCabinSchedule.setCsdEndTime(date24Format.format(date12Format.parse(trnStaffCabinSchedule.getCsdEndTime())));
            trnStaffCabinSchedule.setCsdStartTime(date24Format.format(date12Format.parse(trnStaffCabinSchedule.getCsdStartTime())));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // System.out.println("----------start time------------------------"+trnStaffCabinSchedule.getCsdStartTime());
        // System.out.println("----------end time------------------------"+trnStaffCabinSchedule.getCsdEndTime());
        if (trnStaffCabinSchedule.getCsdStartTime() != null) {
            trnStaffCabinScheduleRepository.save(trnStaffCabinSchedule);
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
        List<TrnStaffCabinSchedule> records;
        records = trnStaffCabinScheduleRepository.findByCsdStartTimeContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{csdId}")
    public TrnStaffCabinSchedule read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("csdId") Long csdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnStaffCabinSchedule trnStaffCabinSchedule = trnStaffCabinScheduleRepository.getById(csdId);
        return trnStaffCabinSchedule;
    }

    @RequestMapping("update")
    public TrnStaffCabinSchedule update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnStaffCabinSchedule trnStaffCabinSchedule) {
        TenantContext.setCurrentTenant(tenantName);
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm:ss");
        try {
            // System.out.println(date24Format.format(date12Format.parse(trnStaffCabinSchedule.getCsdStartTime())));
            trnStaffCabinSchedule.setCsdEndTime(date24Format.format(date12Format.parse(trnStaffCabinSchedule.getCsdEndTime())));
            trnStaffCabinSchedule.setCsdStartTime(date24Format.format(date12Format.parse(trnStaffCabinSchedule.getCsdStartTime())));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // System.out.println("----------start time------------------------"+trnStaffCabinSchedule.getCsdStartTime());
        // System.out.println("----------end time------------------------"+trnStaffCabinSchedule.getCsdEndTime());
        return trnStaffCabinScheduleRepository.save(trnStaffCabinSchedule);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnStaffCabinSchedule> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "csdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnStaffCabinScheduleRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnStaffCabinScheduleRepository.findByCsdStartTimeContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{csdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("csdId") Long csdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnStaffCabinSchedule trnStaffCabinSchedule = trnStaffCabinScheduleRepository.getById(csdId);
        if (trnStaffCabinSchedule != null) {
            trnStaffCabinSchedule.setIsDeleted(true);
            trnStaffCabinScheduleRepository.save(trnStaffCabinSchedule);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("listbyid/{staffId}")
    public Iterable<TrnStaffCabinSchedule> listById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "csdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return trnStaffCabinScheduleRepository.findAllByCsdStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(staffId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @GetMapping
    @RequestMapping("schedulelist")
    public Iterable<TrnStaffCabinSchedule> ScheduleList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "csdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnStaffCabinScheduleRepository.findAllDistinctStaff(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return trnStaffCabinScheduleRepository.findAllDistinctStaff(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("getcabinbystaffid/{staffid}")
    public TrnStaffCabinSchedule getcabinbystaffid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffid") Long staffid) {
        TenantContext.setCurrentTenant(tenantName);
        DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String dateString = dateFormat.format(new Date()).toString();
        // System.out.println("-------------Current time in AM/PM:--------- "+dateString);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        // full name form of the day
        // System.out.println("----------day-----"+ day);
        MstDay mstday = mstDayRepository.findByDayNameContainsAndIsActiveTrueAndIsDeletedFalse(day);
        Long id = mstday.getDayId();
        // System.out.println("--------Day id-----------"+id);
        Long dayid = (long) 1;
        return trnStaffCabinScheduleRepository.findOneByIsActiveTrueAndIsDeletedFalse(staffid, dateString, dayid);

    }

}
            
