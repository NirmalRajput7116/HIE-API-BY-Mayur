package com.cellbeans.hspa.trndoctorscheduledetail;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstday.MstDay;
import com.cellbeans.hspa.mstday.MstDayRepository;
import com.cellbeans.hspa.mststaff.MstStaffDto;
import com.cellbeans.hspa.trnappointment.TrnAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/trn_doctor_schedule_detail")
public class TrnDoctorScheduleDetailController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnDoctorScheduleDetailRepository trnDoctorScheduleDetailRepository;
    @Autowired
    TrnAppointmentRepository trnAppointmentRepository;

    @Autowired
    MstDayRepository mstDayRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDoctorScheduleDetail trnDoctorScheduleDetail) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("Request Schedule: " + trnDoctorScheduleDetail.toString());

      /*  Boolean flag = false;
        TrnDoctorScheduleDetail trnDoctorScheduleDetail2 = trnDoctorScheduleDetailRepository
                .findOneByDsdDayIdDayIdAndDsdStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(
                        trnDoctorScheduleDetail.getDsdDayId().getDayId(),
                        trnDoctorScheduleDetail.getDsdStaffId().getStaffId());

        try {
            if (trnDoctorScheduleDetail2.getDsdId() > 0) {
                flag = true;
            }
        } catch (NullPointerException e) {
            flag = false;
        }*/
//        if (!flag) {
        java.sql.Time ppstime = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a"); //if 24 hour format
            java.util.Date d1 = (java.util.Date) format.parse(trnDoctorScheduleDetail.getDsdStartTime());
            ppstime = new java.sql.Time(d1.getTime());
            System.out.println("Time: " + ppstime);

        } catch (Exception e) {
        }
        List<TrnDoctorScheduleDetail> TrnDoctorScheduleDetaillist = trnDoctorScheduleDetailRepository.findAllByDsdDayIdDayIdEqualsAndIsActiveTrueAndIsDeletedFalse(trnDoctorScheduleDetail.getDsdCabinId().getCabinId(), ppstime, trnDoctorScheduleDetail.getDsdDayId().getDayId());
        if (TrnDoctorScheduleDetaillist.size() == 0) {
            if (trnDoctorScheduleDetail.getDsdStaffId().getStaffId() > 0) {
                // System.out.println(trnDoctorScheduleDetail.toString());
                trnDoctorScheduleDetailRepository.save(trnDoctorScheduleDetail);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
                return respMap;
            }
        } else {
//            int i = TrnDoctorScheduleDetaillist.size();
            respMap.put("success", "0");
            respMap.put("day", TrnDoctorScheduleDetaillist.get(0).getDsdDayId().getDayName());
            respMap.put("starttime", TrnDoctorScheduleDetaillist.get(0).getDsdStartTime());
            respMap.put("endtime", TrnDoctorScheduleDetaillist.get(0).getDsdEndTime());
            respMap.put("cabin", TrnDoctorScheduleDetaillist.get(0).getDsdCabinId().getCabinName());
            respMap.put("msg", "Schedule Already Available For " + TrnDoctorScheduleDetaillist.get(0).getDsdDayId().getDayName() + " In Between " + TrnDoctorScheduleDetaillist.get(0).getDsdStartTime() + " And " + TrnDoctorScheduleDetaillist.get(0).getDsdEndTime() + " In Cabin " + TrnDoctorScheduleDetaillist.get(0).getDsdCabinId().getCabinName() + " ");
            return respMap;
        }

       /* }
        else {
            respMap.put("success", "0");
            respMap.put("msg", "Schedule Day Already Exists");
            return respMap;

        }*/
    }

    /*
     * @RequestMapping("/autocomplete/{key}") public Map<String, Object>
     * listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) { Map<String, Object> automap =
     * new HashMap<String, Object>(); List<TrnDoctorScheduleDetail> records;
     * records =
     * trnDoctorScheduleDetailRepository.findByUserFullnameContains(key);
     * automap.put("record", records); return automap; }
     */

    @RequestMapping("byid/{staffId}")
    public TrnDoctorScheduleDetail read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnDoctorScheduleDetail trnDoctorScheduleDetail = trnDoctorScheduleDetailRepository.getById(staffId);
        return trnDoctorScheduleDetail;
    }

    @RequestMapping("findbydayandstaff/{dayId}/{staffId}")
    public Boolean findByDayAndStaff(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dayId") Long dayId, @PathVariable("staffId") Long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnDoctorScheduleDetail trnDoctorScheduleDetail = trnDoctorScheduleDetailRepository.findByDsdDayIdDayIdAndDsdStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(dayId, staffId);
        if (trnDoctorScheduleDetail != null)
            return true;
        return false;
    }

    @RequestMapping("update")
    public TrnDoctorScheduleDetail update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDoctorScheduleDetail trnDoctorScheduleDetail) {
        TenantContext.setCurrentTenant(tenantName);
        return trnDoctorScheduleDetailRepository.save(trnDoctorScheduleDetail);
    }

    @GetMapping
    @RequestMapping("schedulelist")
    public List<MstStaffDto> ScheduleList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dsdId") String col, @RequestParam(value = "unitId", required = false, defaultValue = "0") String unitId) {
        TenantContext.setCurrentTenant(tenantName);
        int count = 0;
        String query1 = "";
        String countQuery = "";
        List<MstStaffDto> mstStaffDtoList = new ArrayList<MstStaffDto>();
        if (qString == null || qString.equals("")) {
            int limit = (Integer.parseInt(page) - 1) * Integer.parseInt(size);
            int offset = Integer.parseInt(size);
            if (!unitId.equals("0")) {
                query1 = "SELECT  s.staff_id,mu.unit_name, d.department_name, sd.sd_name, CONCAT(u.user_firstname,' ',u.user_lastname) AS FULLNAME  FROM trn_doctor_schedule_detail t " + " inner join mst_staff s on s.staff_id = t.dsd_staff_id " + " inner join mst_user u on s.staff_user_id = u.user_id " +
                        //"inner join mst_staff_staff_unit msu on msu.mst_staff_staff_id = s.staff_id " +
                        " inner join mst_unit mu on mu.unit_id = t.dsd_unit_id " + " inner join mst_department d on d.department_id = t.dsd_department_id " + " inner join mst_sub_department sd on sd.sd_id = t.dsd_sub_department_id  " +
                        " where t.is_active = 1 and t.is_deleted = 0  and  " + " t.dsd_unit_id = " + unitId + " group by  t.dsd_staff_id ORDER by t.dsd_id " + sort + "  limit " + limit + " , " + offset;
                countQuery = "select count(*) from (SELECT  s.staff_id,mu.unit_name, d.department_name, sd.sd_name, CONCAT(u.user_firstname,' ',u.user_lastname) AS FULLNAME  FROM trn_doctor_schedule_detail t " + " inner join mst_staff s on s.staff_id = t.dsd_staff_id" + " inner join mst_user u on s.staff_user_id = u.user_id" +
                        //"inner join mst_staff_staff_unit msu on msu.mst_staff_staff_id = s.staff_id " +
                        " inner join mst_unit mu on mu.unit_id = t.dsd_unit_id" + " inner join mst_department d on d.department_id = t.dsd_department_id " + " inner join mst_sub_department sd on sd.sd_id = t.dsd_sub_department_id  " +
                        " where t.is_active = 1 and t.is_deleted = 0  and  " + " t.dsd_unit_id = " + unitId + " group by  t.dsd_staff_id) as test ";
            } else {
                query1 = "SELECT  s.staff_id,mu.unit_name, d.department_name, sd.sd_name, CONCAT(u.user_firstname,' ',u.user_lastname) AS FULLNAME  FROM trn_doctor_schedule_detail t " + " inner join mst_staff s on s.staff_id = t.dsd_staff_id " + " inner join mst_user u on s.staff_user_id = u.user_id " +
                        //"inner join mst_staff_staff_unit msu on msu.mst_staff_staff_id = s.staff_id " +
                        " inner join mst_unit mu on mu.unit_id = t.dsd_unit_id " + " inner join mst_department d on d.department_id = t.dsd_department_id " + " inner join mst_sub_department sd on sd.sd_id = t.dsd_sub_department_id  " +
                        " where t.is_active = 1 and t.is_deleted = 0 group by  t.dsd_staff_id ORDER by t.dsd_id " + sort + "  limit " + limit + " , " + offset;
                countQuery = "select count(*) from (SELECT  s.staff_id,mu.unit_name, d.department_name, sd.sd_name, CONCAT(u.user_firstname,' ',u.user_lastname) AS FULLNAME  FROM trn_doctor_schedule_detail t " + " inner join mst_staff s on s.staff_id = t.dsd_staff_id" + " inner join mst_user u on s.staff_user_id = u.user_id" +
                        //"inner join mst_staff_staff_unit msu on msu.mst_staff_staff_id = s.staff_id " +
                        " inner join mst_unit mu on mu.unit_id = t.dsd_unit_id" + " inner join mst_department d on d.department_id = t.dsd_department_id " + " inner join mst_sub_department sd on sd.sd_id = t.dsd_sub_department_id  " +
                        " where t.is_active = 1 and t.is_deleted = 0 group by  t.dsd_staff_id) as test ";
            }
        } else {
            int limit = (Integer.parseInt(page) - 1) * Integer.parseInt(size);
            int offset = Integer.parseInt(size);
            if (!unitId.equals("0")) {
                query1 = "SELECT  ifnull(s.staff_id,0) as staff_id,mu.unit_name, d.department_name, sd.sd_name, CONCAT(u.user_firstname,' ',u.user_lastname) AS FULLNAME  FROM trn_doctor_schedule_detail t " + " inner join mst_staff s on s.staff_id = t.dsd_staff_id " + " inner join mst_user u on s.staff_user_id = u.user_id " +
                        " inner join mst_unit mu on mu.unit_id = t.dsd_unit_id " + " inner join mst_department d on d.department_id = t.dsd_department_id " + " inner join mst_sub_department sd on sd.sd_id = t.dsd_sub_department_id  " +
                        " where t.is_active = 1 and t.is_deleted = 0 and (u.user_firstname like '%" + qString + "%' or u.user_lastname like  '%" + qString + "%')  and " + " t.dsd_unit_id = " + unitId + " group by  t.dsd_staff_id  ORDER by t.dsd_id " + sort + " limit " + limit + "," + offset;
                countQuery = "select count(*) from (SELECT  s.staff_id, mu.unit_name, d.department_name, sd.sd_name, CONCAT(u.user_firstname,' ',u.user_lastname) AS FULLNAME  FROM trn_doctor_schedule_detail t " + " inner join mst_staff s on s.staff_id = t.dsd_staff_id " + " inner join mst_user u on s.staff_user_id = u.user_id " +
                        " inner join mst_unit mu on mu.unit_id = t.dsd_unit_id " + " inner join mst_department d on d.department_id = t.dsd_department_id " + " inner join mst_sub_department sd on sd.sd_id = t.dsd_sub_department_id  " +
                        " where t.is_active = 1 and t.is_deleted = 0 and (u.user_firstname like '%" + qString + "%' or u.user_lastname like  '%" + qString + "%')  and " + " t.dsd_unit_id = " + unitId + " group by  t.dsd_staff_id) as test";
            } else {
                query1 = "SELECT  ifnull(s.staff_id,0) as staff_id,mu.unit_name, d.department_name, sd.sd_name, CONCAT(u.user_firstname,' ',u.user_lastname) AS FULLNAME  FROM trn_doctor_schedule_detail t " + " inner join mst_staff s on s.staff_id = t.dsd_staff_id " + " inner join mst_user u on s.staff_user_id = u.user_id " +
                        " inner join mst_unit mu on mu.unit_id = t.dsd_unit_id " + " inner join mst_department d on d.department_id = t.dsd_department_id " + " inner join mst_sub_department sd on sd.sd_id = t.dsd_sub_department_id  " +
                        " where t.is_active = 1 and t.is_deleted = 0 and (u.user_firstname like '%" + qString + "%' or u.user_lastname like  '%" + qString + "%') group by  t.dsd_staff_id  ORDER by t.dsd_id " + sort + " limit " + limit + "," + offset;
                countQuery = "select count(*) from (SELECT  s.staff_id, mu.unit_name, d.department_name, sd.sd_name, CONCAT(u.user_firstname,' ',u.user_lastname) AS FULLNAME  FROM trn_doctor_schedule_detail t " + " inner join mst_staff s on s.staff_id = t.dsd_staff_id " + " inner join mst_user u on s.staff_user_id = u.user_id " +
                        " inner join mst_unit mu on mu.unit_id = t.dsd_unit_id " + " inner join mst_department d on d.department_id = t.dsd_department_id " + " inner join mst_sub_department sd on sd.sd_id = t.dsd_sub_department_id  " +
                        " where t.is_active = 1 and t.is_deleted = 0 and (u.user_firstname like '%" + qString + "%' or u.user_lastname like  '%" + qString + "%') group by  t.dsd_staff_id) as test";

            }

        }
        List<Object[]> stafflist = entityManager.createNativeQuery(query1).getResultList();
        BigInteger temp = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
        count = temp.intValue();
        for (Object[] obj : stafflist) {
            MstStaffDto mstStaffDto = new MstStaffDto(obj[4].toString(), obj[1].toString(), obj[2].toString(), obj[3].toString(), Long.parseLong(String.valueOf(obj[0])));
            mstStaffDtoList.add(mstStaffDto);
        }
        if (mstStaffDtoList.size() != 0) {
            mstStaffDtoList.get(0).setCount(count);
        }
        return mstStaffDtoList;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnDoctorScheduleDetail> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dsdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnDoctorScheduleDetailRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnDoctorScheduleDetailRepository.findByDsdStaffIdStaffUserIdUserFullnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listbyid/{staffId}")
    public Iterable<TrnDoctorScheduleDetail> listById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dsdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return trnDoctorScheduleDetailRepository.findAllByDsdStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(staffId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @PutMapping("delete/{dsdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsdId") Long dsdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnDoctorScheduleDetail trnDoctorScheduleDetail = trnDoctorScheduleDetailRepository.getById(dsdId);
        if (trnDoctorScheduleDetail != null) {
            trnDoctorScheduleDetail.setDeleted(true);
            trnDoctorScheduleDetailRepository.save(trnDoctorScheduleDetail);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PostMapping("time")
//    public Map<String, Object> time(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId, @PathVariable("dur") int dur, @PathVariable("dayOfWeek") int dayOfWeek) {
    public Map<String, Object> time(@RequestHeader("X-tenantId") String tenantName, @RequestBody Map<String, String> model) {
        TenantContext.setCurrentTenant(tenantName);
        Long staffId = Long.parseLong(model.get("staffId"));
        int dur = Integer.parseInt(model.get("dur"));
        int dayOfWeek = Integer.parseInt(model.get("dayOfWeek"));
        String currAppDate = model.get("currAppDate");
        //fetching booked slots for the date = currAppDate & staff = staffId
        List<String> BookedSlots = trnAppointmentRepository.findAllByAppointmentDateAndIsDeletedIsFalse(currAppDate, staffId);
        List<String> BlockedSlots = trnAppointmentRepository.findAllByAppointmentDateAndIsDeletedIsFalseAndAppointmentIsBlockTrue(currAppDate, staffId);
        /*List<String> BookedSlots = trnAppointmentRepository.findAllByAppointmentDateAndIsDeletedIsFalseAndAppointmentIsBlockFalse(currAppDate, staffId);*/
//
//        //now regular all slots
//
        Map<String, Object> trnMap = new HashMap<String, Object>();
//
        List<TrnDoctorScheduleDetail> trnDoctorScheduleDetail;
        String varStartTime = "";
        String varEndTime = "";
        Date startTime;
        Date endTime;
        Calendar cal = Calendar.getInstance();
//
//
        int Day = dayOfWeek;
        String dayName = "";
        if (Day == 0) {
            Day = 7;
        }
        switch (Day) {
            case 7:
                dayName = "Sunday";
                break;
            case 1:
                dayName = "Monday";
                break;
            case 2:
                dayName = "Tuesday";
                break;
            case 3:
                dayName = "Wednesday";
                break;
            case 4:
                dayName = "Thursday";
                break;
            case 5:
                dayName = "Friday";
                break;
            case 6:
                dayName = "Saturday";
                break;
        }
        System.out.println("-------day--name---------------------------");
        DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String dateString = dateFormat.format(new Date()).toString();
        System.out.println(dateString);
        MstDay mstday = mstDayRepository.findByDayNameContainsAndIsActiveTrueAndIsDeletedFalse(dayName);
        Long id = mstday.getDayId();
        System.out.println("-------day id-----------------------------");
        System.out.println(id);
        String dateString1 = dateString.replace('.', ':');
//            trnDoctorScheduleDetail = trnDoctorScheduleDetailRepository
//                    .findOneByStaffSchedule(staffId, dateString1, id);
 /*
            System.out.println("-------Schedule found----------------------------");
            System.out.println(trnDoctorScheduleDetail.getDsdId());
*/
        trnDoctorScheduleDetail = (List<TrnDoctorScheduleDetail>) entityManager.createNativeQuery("  SELECT  *  from trn_doctor_schedule_detail  tds " + " left join  mst_day md on md.day_id =  tds.dsd_day_id where md.day_name like  '%" + dayName + "' and tds.dsd_staff_id =" + staffId + "  and tds.is_active = 1 and tds.is_deleted = 0 ", TrnDoctorScheduleDetail.class).getResultList();
        if (trnDoctorScheduleDetail.size() > 0) {
            ArrayList<Map<String, String>> times = new ArrayList<Map<String, String>>();
            for (TrnDoctorScheduleDetail TrnDoctorScheduleDetail : trnDoctorScheduleDetail) {
                varStartTime = TrnDoctorScheduleDetail.getDsdStartTime();
                varEndTime = TrnDoctorScheduleDetail.getDsdEndTime();
                int startHrs, endHrs = 0;
                startHrs = Integer.parseInt(varStartTime.substring(0, 2));
                if (varStartTime.substring(6, 8).equals("PM")) {
                    if (startHrs == 12) {
                    } else {
                        startHrs = startHrs + 12;
                    }
                } else if (varStartTime.substring(6, 8).equals("AM")) {
                    if (startHrs == 12) {
                        startHrs = 00;
                    }
                }
                cal.set(Calendar.HOUR_OF_DAY, startHrs);
                cal.set(Calendar.MINUTE, Integer.parseInt(varStartTime.substring(3, 5)));
                startTime = cal.getTime();
                endHrs = Integer.parseInt(varEndTime.substring(0, 2));
                if (varEndTime.substring(6, 8).equals("PM")) {
                    if (endHrs == 12) {
                    } else {
                        endHrs = endHrs + 12;
                    }
                } else if (varEndTime.substring(6, 8).equals("AM")) {
                    if (endHrs == 12) {
                        endHrs = 00;
                    }
                }
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, endHrs);
                cal.set(Calendar.MINUTE, Integer.parseInt(varEndTime.substring(3, 5)));
                endTime = cal.getTime();
//        Map<String, Object> SlotList = new HashMap<String, Object>();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                Calendar calendar = GregorianCalendar.getInstance();
                Calendar currCalendar = GregorianCalendar.getInstance();
                calendar.setTime(startTime);
                while (calendar.getTime().before(endTime)) {
                    Map<String, String> singleSlot = new HashMap<String, String>();
                    if (calendar.getTime().before(currCalendar.getTime())) {
                        int slotTimeAMPM = calendar.get(Calendar.AM_PM); //chk am/pm for start time
                        String slot = "";
                        if (slotTimeAMPM == 0) {
                            slot = sdf.format(calendar.getTime()) + " AM - ";

                        } else {
                            slot = sdf.format(calendar.getTime()) + " PM - ";

                        }
                        calendar.add(Calendar.MINUTE, dur); //add cal iterator
                        slotTimeAMPM = calendar.get(Calendar.AM_PM); //chk am/pm for end time
                        if (slotTimeAMPM == 0) {
                            slot += sdf.format(calendar.getTime()) + " AM";

                        } else {
                            slot += sdf.format(calendar.getTime()) + " PM";

                        }
                        singleSlot.put("slot", slot);
                        if (BookedSlots.size() > 0) {
                            for (String bookSlot : BookedSlots) {
                                if (bookSlot != null) {
                                    if (bookSlot.equals(slot)) {
                                        singleSlot.put("status", "2");
                                        break;
                                    } else {
                                        singleSlot.put("status", "0");
                                    }
                                }
                            }
                        } else {
                            singleSlot.put("status", "0");
                        }

                    } else {
                        int slotTimeAMPM2 = calendar.get(Calendar.AM_PM); //chk am/pm for start time
                        String slot = "";
                        if (slotTimeAMPM2 == 0) {
                            slot = sdf.format(calendar.getTime()) + " AM - ";

                        } else {
                            slot = sdf.format(calendar.getTime()) + " PM - ";

                        }
                        calendar.add(Calendar.MINUTE, dur);
                        slotTimeAMPM2 = calendar.get(Calendar.AM_PM); //chk am/pm for end time
                        if (slotTimeAMPM2 == 0) {
                            slot += sdf.format(calendar.getTime()) + " AM";
                        } else {
                            slot += sdf.format(calendar.getTime()) + " PM";
                        }
                        singleSlot.put("slot", slot);
                        if (BookedSlots.size() > 0) {
                            for (String bookSlot : BookedSlots) {
                                if (bookSlot != null) {
                                    if (bookSlot.equals(slot)) {
                                        singleSlot.put("status", "2");
                                        break;
                                    } else {
                                        singleSlot.put("status", "1");
                                    }
                                }
                            }

                        } else {
                            singleSlot.put("status", "1");
                        }

                    }
                    times.add(singleSlot);
                }
                if (times.size() > 0) {
                    times.remove(times.size() - 1);
                }
                //for(int i =0; i<=times.size()-1;i++){
                if (BlockedSlots.size() > 0) {
                    for (String slot : BlockedSlots) {
                        for (int i = 0; i <= times.size() - 1; i++) {
                            Map<String, String> singleSlot = (Map<String, String>) times.get(i);
                            if (singleSlot.get("slot").equals(slot)) {
                                times.remove(i);
                                Map<String, String> singleSlot1 = new HashMap<String, String>();
                                singleSlot1.put("slot", slot);
                                singleSlot1.put("status", "3");
                                times.add(singleSlot1);
                            }
                        }
                    }
                }
                //}
                respMap.put("msg", "Operation Successful");
                respMap.put("success", "1");
                trnMap.put("msg", respMap);
                trnMap.put("content", times);
            }
        } else {
            respMap.put("msg", "Operation Unsuccessful");
            respMap.put("success", "0");
            trnMap.put("msg", respMap);
        }
        return trnMap;
    }

    @PostMapping("timeslots")
    public Map<String, Object> timeslots(@RequestHeader("X-tenantId") String tenantName, @RequestBody Map<String, String> model) {
        TenantContext.setCurrentTenant(tenantName);
        String firstStartTime = model.get("f1");
        String firstEndTime = model.get("f2");
        String secStartTime = model.get("s1");
        String secEndTime = model.get("s2");
        int dur = Integer.parseInt(model.get("dur"));
        int dayOfWeek = Integer.parseInt(model.get("dayOfWeek"));
        String currAppDate = model.get("currAppDate");
        Map<String, Object> trnMap = new HashMap<String, Object>();
        String varStartTime = "";
        String varEndTime = "";
        String varStartTime1 = "";
        String varEndTime1 = "";
        Date startTime;
        Date endTime;
        Calendar cal = Calendar.getInstance();
        int Day = dayOfWeek;
        String dayName = "";
        switch (Day) {
            case 7:
                dayName = "Sunday";
                break;
            case 1:
                dayName = "Monday";
                break;
            case 2:
                dayName = "Tuesday";
                break;
            case 3:
                dayName = "Wednesday";
                break;
            case 4:
                dayName = "Thursday";
                break;
            case 5:
                dayName = "Friday";
                break;
            case 6:
                dayName = "Saturday";
                break;
        }
        DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String dateString = dateFormat.format(new Date()).toString();
        MstDay mstday = mstDayRepository.findByDayNameContainsAndIsActiveTrueAndIsDeletedFalse(dayName);
        Long id = mstday.getDayId();
        String dateString1 = dateString.replace('.', ':');
//
        ArrayList<Object> times = new ArrayList<Object>();
        varStartTime = firstStartTime;
        varEndTime = firstEndTime;
        varStartTime1 = secStartTime;
        varEndTime1 = secEndTime;
        {
            int startHrs, endHrs = 0;
            startHrs = Integer.parseInt(varStartTime.substring(0, 2));
            if (varStartTime.substring(6, 8).equals("PM")) {
                if (startHrs == 12) {
                } else {
                    startHrs = startHrs + 12;
                }
            } else if (varStartTime.substring(6, 8).equals("AM")) {
                if (startHrs == 12) {
                    startHrs = 00;
                }
            }
            cal.set(Calendar.HOUR_OF_DAY, startHrs);
            cal.set(Calendar.MINUTE, Integer.parseInt(varStartTime.substring(3, 5)));
            startTime = cal.getTime();
            endHrs = Integer.parseInt(varEndTime.substring(0, 2));
            if (varEndTime.substring(6, 8).equals("PM")) {
                if (endHrs == 12) {
                } else {
                    endHrs = endHrs + 12;
                }
            } else if (varEndTime.substring(6, 8).equals("AM")) {
                if (endHrs == 12) {
                    endHrs = 00;
                }
            }
            cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, endHrs);
            cal.set(Calendar.MINUTE, Integer.parseInt(varEndTime.substring(3, 5)));
            endTime = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            Calendar calendar = GregorianCalendar.getInstance();
            Calendar currCalendar = GregorianCalendar.getInstance();
            calendar.setTime(startTime);
            while (calendar.getTime().before(endTime)) {
                Map<String, String> singleSlot = new HashMap<String, String>();
                if (calendar.getTime().before(currCalendar.getTime())) {
                    int slotTimeAMPM = calendar.get(Calendar.AM_PM); //chk am/pm for start time
                    String slot = "";
                    if (slotTimeAMPM == 0) {
                        slot = sdf.format(calendar.getTime()) + " AM - ";

                    } else {
                        slot = sdf.format(calendar.getTime()) + " PM - ";

                    }
                    calendar.add(Calendar.MINUTE, dur); //add cal iterator
                    slotTimeAMPM = calendar.get(Calendar.AM_PM); //chk am/pm for end time
                    if (slotTimeAMPM == 0) {
                        slot += sdf.format(calendar.getTime()) + " AM";

                    } else {
                        slot += sdf.format(calendar.getTime()) + " PM";

                    }
                    singleSlot.put("slot", slot);
                    singleSlot.put("status", "0");
                } else {
                    int slotTimeAMPM2 = calendar.get(Calendar.AM_PM); //chk am/pm for start time
                    String slot = "";
                    if (slotTimeAMPM2 == 0) {
                        slot = sdf.format(calendar.getTime()) + " AM - ";

                    } else {
                        slot = sdf.format(calendar.getTime()) + " PM - ";

                    }
                    calendar.add(Calendar.MINUTE, dur);
                    slotTimeAMPM2 = calendar.get(Calendar.AM_PM); //chk am/pm for end time
                    if (slotTimeAMPM2 == 0) {
                        slot += sdf.format(calendar.getTime()) + " AM";
                    } else {
                        slot += sdf.format(calendar.getTime()) + " PM";
                    }
                    singleSlot.put("slot", slot);
                    singleSlot.put("status", "1");
                }
                times.add(singleSlot);
            }
        }
        {
            int startHrs, endHrs = 0;
            startHrs = Integer.parseInt(varStartTime1.substring(0, 2));
            if (varStartTime1.substring(6, 8).equals("PM")) {
                if (startHrs == 12) {
                } else {
                    startHrs = startHrs + 12;
                }
            } else if (varStartTime1.substring(6, 8).equals("AM")) {
                if (startHrs == 12) {
                    startHrs = 00;
                }
            }
            cal.set(Calendar.HOUR_OF_DAY, startHrs);
            cal.set(Calendar.MINUTE, Integer.parseInt(varStartTime1.substring(3, 5)));
            startTime = cal.getTime();
            endHrs = Integer.parseInt(varEndTime1.substring(0, 2));
            if (varEndTime1.substring(6, 8).equals("PM")) {
                if (endHrs == 12) {
                } else {
                    endHrs = endHrs + 12;
                }
            } else if (varEndTime1.substring(6, 8).equals("AM")) {
                if (endHrs == 12) {
                    endHrs = 00;
                }
            }
            cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, endHrs);
            cal.set(Calendar.MINUTE, Integer.parseInt(varEndTime1.substring(3, 5)));
            endTime = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            Calendar calendar = GregorianCalendar.getInstance();
            Calendar currCalendar = GregorianCalendar.getInstance();
            calendar.setTime(startTime);
            while (calendar.getTime().before(endTime)) {
                Map<String, String> singleSlot = new HashMap<String, String>();
                if (calendar.getTime().before(currCalendar.getTime())) {
                    int slotTimeAMPM = calendar.get(Calendar.AM_PM); //chk am/pm for start time
                    String slot = "";
                    if (slotTimeAMPM == 0) {
                        slot = sdf.format(calendar.getTime()) + " AM - ";

                    } else {
                        slot = sdf.format(calendar.getTime()) + " PM - ";

                    }
                    calendar.add(Calendar.MINUTE, dur); //add cal iterator
                    slotTimeAMPM = calendar.get(Calendar.AM_PM); //chk am/pm for end time
                    if (slotTimeAMPM == 0) {
                        slot += sdf.format(calendar.getTime()) + " AM";

                    } else {
                        slot += sdf.format(calendar.getTime()) + " PM";

                    }
                    singleSlot.put("slot", slot);
                    singleSlot.put("status", "0");
                } else {
                    int slotTimeAMPM2 = calendar.get(Calendar.AM_PM); //chk am/pm for start time
                    String slot = "";
                    if (slotTimeAMPM2 == 0) {
                        slot = sdf.format(calendar.getTime()) + " AM - ";

                    } else {
                        slot = sdf.format(calendar.getTime()) + " PM - ";

                    }
                    calendar.add(Calendar.MINUTE, dur);
                    slotTimeAMPM2 = calendar.get(Calendar.AM_PM); //chk am/pm for end time
                    if (slotTimeAMPM2 == 0) {
                        slot += sdf.format(calendar.getTime()) + " AM";
                    } else {
                        slot += sdf.format(calendar.getTime()) + " PM";
                    }
                    singleSlot.put("slot", slot);
                    singleSlot.put("status", "1");
                }
                times.add(singleSlot);
            }
        }
        //times.remove(times.size() - 1);
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        trnMap.put("msg", respMap);
        trnMap.put("content", times);
        return trnMap;
    }

    @GetMapping
    @RequestMapping("schedulelist_count")
    public BigInteger schedulelist_count(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId", required = false, defaultValue = "0") String unitId, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        BigInteger count;
        String query1 = "SELECT count(s.staff_id)  FROM trn_doctor_schedule_detail t " + "inner join mst_staff s on s.staff_id = t.dsd_staff_id " + "inner join mst_user u on s.staff_user_id = u.user_id " + "inner join mst_staff_staff_unit msu on msu.mst_staff_staff_id = s.staff_id " + " where t.is_active = 1 and t.is_deleted = 0 and (u.user_firstname like'%+qString+%' or u.user_lastname like'%+qString+%') and " + " msu.staff_unit_unit_id = " + unitId + " group by  t.dsd_staff_id";
        try {
            count = (BigInteger) entityManager.createNativeQuery(query1).getSingleResult();
        } catch (NoResultException e) {
            count = BigInteger.valueOf(0);
        }
        return count;

    }

    @PutMapping("delete_by_staffid/{id}")
    public Map<String, String> delete_by_staffid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnDoctorScheduleDetail> trnDoctorScheduleDetail;
        trnDoctorScheduleDetail = trnDoctorScheduleDetailRepository.findAllByDsdStaffIdStaffIdAndIsActiveTrueAndIsDeletedFalse(id);
        for (int i = 0; i < trnDoctorScheduleDetail.size(); i++) {
            trnDoctorScheduleDetail.get(i).setDeleted(true);
            trnDoctorScheduleDetailRepository.save(trnDoctorScheduleDetail.get(i));
        }
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

    @GetMapping
    @RequestMapping("getStaffList")
    // @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity list(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "  select s.staff_id, u.user_firstname as staff,us.user_firstname,a.appointment_slot from trn_appointment a,mst_staff s,mst_user u,mst_user us\n" +
                "    where a.appointment_staff_id=s.staff_id and s.staff_user_id=u.user_id and a.appointment_user_id=us.user_id\n" +
                "    and date(a.appointment_date)=curdate() group by  a.appointment_id ";
        String col = "staff_id,staff_name,userName,slot";
        //JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(col, query,null));
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(col, query, null));
    }

    @GetMapping
    @RequestMapping("getDoctorList/{serviceId}/{userId}/{unitId}/{city}/{speciality}/{gender}/{unitFlag}/{isVirtual}/{appointDate}")
    // @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDoctorList(@RequestHeader("X-tenantId") String tenantName, @PathVariable("serviceId") Long serviceId, @PathVariable("userId") Long userId, @PathVariable("unitId") Long unitId, @PathVariable("city") Long city, @PathVariable("speciality") Long speciality, @PathVariable("gender") Long gender, @PathVariable("unitFlag") int unitFlag, @PathVariable("isVirtual") String isVirtual, @PathVariable("appointDate") String appointDate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respMap = new HashMap<String, Object>();
        String currencyName = "" + entityManager.createNativeQuery("SELECT mc.currency_name FROM mac_currency mc WHERE mc.is_deleted=false LIMIT 1").getSingleResult();
        List<DoctorListDto> doctorList = new ArrayList<>();
        String unitQuery = "";
        try {
            Date date1 = new Date();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(appointDate);
            Long appday = Long.parseLong(String.valueOf(date.getDay())) + 1;
            String mainQuery = " SELECT  CONCAT(mu.user_firstname,' ',mu.user_lastname) AS name,sp.speciality_name AS sname,ms.staff_education,mc.city_name, " +
                    " ms.staffexprience,ms.staff_id,mss.ss_base_rate,ms.staffnmcno,ml.language_name,(SELECT msu.unit_name FROM staff_configuration sc INNER JOIN mst_unit msu WHERE sc.sc_staff_id=ms.staff_id AND sc.sc_unit_id=msu.unit_id) as unit, mu.user_image, case when ms.is_doctor_online = 1 then 'true' ELSE 'false' end, " +
                    " (SELECT mu.unit_parent_id FROM mst_unit mu where mu.unit_id = " + unitId + ") AS parent_unit, ms.staff_min_duration AS staffMinDuration " +
                    " FROM mst_staff ms  INNER JOIN mst_user mu INNER JOIN mst_language ml INNER JOIN mst_speciality sp INNER JOIN mst_city mc INNER JOIN staff_service_id ss " +
                    " INNER JOIN mst_staff_services mss INNER JOIN mst_staff_staff_unit mssu INNER JOIN trn_doctor_schedule_detail sd WHERE sd.dsd_staff_id=ms.staff_id AND mu.user_id=ms.staff_user_id AND mu.user_language_id=ml.language_id AND ms.staff_speciality_id=sp.speciality_id " +
                    " and mu.user_city_id=mc.city_id AND ss.mst_staff_staff_id=ms.staff_id AND mss.ss_id=ss.staff_service_id_ss_id AND mss.ss_service_id= " + serviceId +
                    " AND mssu.mst_staff_staff_id = ms.staff_id AND  ms.is_virtual = " + isVirtual + " AND ms.is_active = true AND ms.is_deleted = false AND sd.dsd_day_id= " + appday + "  AND sd.is_deleted=0";
                   /* " AND ((mssu.staff_unit_unit_id = " + unitId + ") or " +
                    " (mssu.staff_unit_unit_id = (SELECT mu.unit_parent_id FROM mst_unit mu WHERE mu.unit_id = " + unitId + ")) OR " +
                    " (mssu.staff_unit_unit_id IN (SELECT mu.unit_id FROM mst_unit mu WHERE mu.unit_parent_id = (SELECT mu.unit_parent_id FROM mst_unit mu WHERE mu.unit_id = " + unitId + ")) OR " +
                    " (ms.staff_cluster_id = (SELECT mu.unit_cluster_id FROM mst_unit mu WHERE mu.unit_id = " + unitId + ")))) ";*/
            if (unitFlag == 0) {
                unitQuery += " AND mssu.staff_unit_unit_id  = " + unitId;
            }
            if (unitFlag == 1) {
                unitQuery += " AND mssu.staff_unit_unit_id = (SELECT mu.unit_parent_id FROM mst_unit mu WHERE mu.unit_id =  " + unitId + ")";
            }
            if (unitFlag == 2) {
                unitQuery += " AND mssu.staff_unit_unit_id IN (SELECT mu.unit_id FROM mst_unit mu WHERE mu.unit_parent_id = (SELECT mu.unit_parent_id FROM mst_unit mu WHERE mu.unit_id = " + unitId + " )) ";
            }
            if (unitFlag == 3) {
//                unitQuery += " AND ms.staff_cluster_id = (SELECT mu.unit_cluster_id FROM mst_unit mu WHERE mu.unit_id = " + unitId + ")";
                unitQuery += " AND (SELECT COUNT(*) FROM mst_staff_staff_cluster_id_list sl INNER join mst_unit_unit_cluster_id_list mul " +
                        "WHERE sl.staff_cluster_id_list_cluster_id=mul.unit_cluster_id_list_cluster_id AND sl.mst_staff_staff_id=ms.staff_id AND mul.mst_unit_unit_id= " + unitId + ")>0";
            }
            mainQuery += unitQuery;
            if (city != 0) {
                mainQuery += " AND mc.city_id = " + city;
            }
            if (speciality != 0) {
                mainQuery += " AND sp.speciality_id = " + speciality;
            }
            if (serviceId != 0) {
                mainQuery += " AND mss.ss_service_id= " + serviceId;
            }
            if (gender != 0) {
                mainQuery += " AND user_gender_id =  " + gender;
            }
            mainQuery += " GROUP BY ms.staff_id ";
            List<Object[]> list = entityManager.createNativeQuery(mainQuery).getResultList();
//    List<Object[]> list =  trnDoctorScheduleDetailRepository.getDoctorList(serviceId, unitId);
            for (int i = 0; i < list.size(); i++) {
                DoctorListDto doctor = new DoctorListDto();
                doctor.setName("" + list.get(i)[0]);
                doctor.setSname("" + list.get(i)[1]);
                if (list.get(i)[2] == null) {
                    doctor.setEducation("");
                } else {
                    doctor.setEducation("" + list.get(i)[2]);
                }
                doctor.setCity_name("" + list.get(i)[3]);
                if (list.get(i)[4] == null) {
                    doctor.setExp("");
                } else {
                    doctor.setExp("" + list.get(i)[4]);
                }
                doctor.setStaff_id(Long.parseLong("" + list.get(i)[5]));
                doctor.setDoctorRate(Double.parseDouble("" + list.get(i)[6]));
                if (list.get(i)[7] == null) {
                    doctor.setStaffnmcno("");
                } else {
                    doctor.setStaffnmcno("" + list.get(i)[7]);
                }
//                doctor.setStaffnmcno("" + list.get(i)[7]);
                doctor.setLanguage_name("" + list.get(i)[8]);
                if (list.get(i)[9] == null) {
                    doctor.setUnit_name("");
                } else {
                    doctor.setUnit_name("" + list.get(i)[9]);
                }
                if (list.get(i)[10] == null) {
                    doctor.setUserImage("");
                } else {
                    doctor.setUserImage("" + list.get(i)[10]);
                }
//                String dayQuery = "SELECT  md.*  from trn_doctor_schedule_detail  tds  left join  mst_day md on md.day_id =  tds.dsd_day_id WHERE tds.dsd_staff_id = "+ list.get(i)[5] + " AND tds.is_active = true";
                System.out.println("Amol SELECT  md.*  from trn_doctor_schedule_detail  tds  left join  mst_day md on md.day_id =  tds.dsd_day_id WHERE tds.dsd_staff_id = " + list.get(i)[5] + " AND tds.is_active = true AND tds.is_deleted = false GROUP BY md.day_id");
                String dayQuery = "SELECT  md.*  from trn_doctor_schedule_detail  tds  left join  mst_day md on md.day_id =  tds.dsd_day_id WHERE tds.dsd_staff_id = " + list.get(i)[5] + " AND tds.is_active = true AND tds.is_deleted = false GROUP BY md.day_id";
                List<MstDay> day = entityManager.createNativeQuery(dayQuery, MstDay.class).getResultList();
                List<Object> dayArray = new ArrayList<>();
//                for (int j = 0; j < day.size(); j++) {
//                    Map<String, Object> map = new HashMap<>();
////                    String str =  day.get(j)[0];
//                    map.put("day", day.get(j)[0]);
//                    dayArray.add(map);
//                }
                doctor.setDayList(day);
//                if(list.get(i)[10] == null){
//                    doctor.setUnitId(0L);
//                }else{
//                    doctor.setUnitId(Long.parseLong("" + list.get(i)[10]));
//                }
                doctor.setCurrencyName(currencyName);
                doctor.setIsDoctorOnline("" + list.get(i)[11]);
                doctor.setParentUnitId("" + list.get(i)[12]);
                doctor.setStaffMinDuration("" + list.get(i)[13]);
                doctorList.add(doctor);
            }
            respMap.put("list", doctorList);
            respMap.put("day", date.getDay());
            return respMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("getDoctorListByHubAndStaffIds/{serviceId}/{userId}/{unitId}/{city}/{speciality}/{gender}/{unitFlag}/{clusterId}/{staffIdList}/{isVirtual}/{appointDate}")
    // @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDoctorListByHubAndStaffIds(@RequestHeader("X-tenantId") String tenantName, @PathVariable("serviceId") Long serviceId, @PathVariable("userId") Long userId, @PathVariable("unitId") Long unitId, @PathVariable("city") Long city, @PathVariable("speciality") Long speciality, @PathVariable("gender") Long gender, @PathVariable("unitFlag") int unitFlag, @PathVariable("clusterId") String clusterId, @PathVariable("staffIdList") String[] staffIdList, @PathVariable("isVirtual") String isVirtual, @PathVariable("appointDate") String appointDate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respMap = new HashMap<String, Object>();
        String currencyName = "" + entityManager.createNativeQuery("SELECT mc.currency_name FROM mac_currency mc WHERE mc.is_deleted=false LIMIT 1").getSingleResult();
        List<DoctorListDto> doctorList = new ArrayList<>();
        String unitQuery = "";
        try {
            Date date1 = new Date();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(appointDate);
            Long appday = Long.parseLong(String.valueOf(date.getDay())) + 1;
            String mainQuery = " SELECT  CONCAT(mu.user_firstname,' ',mu.user_lastname) AS name,sp.speciality_name AS sname,ms.staff_education,mc.city_name, " +
                    " ms.staffexprience,ms.staff_id,mss.ss_base_rate,ms.staffnmcno,ml.language_name,(SELECT msu.unit_name FROM staff_configuration sc INNER JOIN mst_unit msu WHERE sc.sc_staff_id=ms.staff_id AND sc.sc_unit_id=msu.unit_id) as unit, mu.user_image, case when ms.is_doctor_online = 1 then 'true' ELSE 'false' end, ms.staff_min_duration AS staffMinDuration " +
                    " FROM mst_staff ms  INNER JOIN mst_user mu INNER JOIN mst_language ml INNER JOIN mst_speciality sp INNER JOIN mst_city mc INNER JOIN staff_service_id ss " +
                    " INNER JOIN mst_staff_services mss INNER JOIN mst_staff_staff_unit mssu INNER JOIN trn_doctor_schedule_detail sd WHERE sd.dsd_staff_id=ms.staff_id AND mu.user_id=ms.staff_user_id AND mu.user_language_id=ml.language_id AND ms.staff_speciality_id=sp.speciality_id " +
                    " and mu.user_city_id=mc.city_id AND ss.mst_staff_staff_id=ms.staff_id AND mss.ss_id=ss.staff_service_id_ss_id AND mss.ss_service_id= " + serviceId +
                    " AND mssu.mst_staff_staff_id = ms.staff_id AND  ms.is_virtual = " + isVirtual + " AND ms.is_active = true AND ms.is_deleted = false AND sd.dsd_day_id= " + appday + "  AND sd.is_deleted=0";
                   /* " AND ((mssu.staff_unit_unit_id = " + unitId + ") or " +
                    " (mssu.staff_unit_unit_id = (SELECT mu.unit_parent_id FROM mst_unit mu WHERE mu.unit_id = " + unitId + ")) OR " +
                    " (mssu.staff_unit_unit_id IN (SELECT mu.unit_id FROM mst_unit mu WHERE mu.unit_parent_id = (SELECT mu.unit_parent_id FROM mst_unit mu WHERE mu.unit_id = " + unitId + ")) OR " +
                    " (ms.staff_cluster_id = (SELECT mu.unit_cluster_id FROM mst_unit mu WHERE mu.unit_id = " + unitId + ")))) ";*/
            if (unitFlag == 0) {
                unitQuery += " AND mssu.staff_unit_unit_id  = " + unitId;
            }
            if (unitFlag == 1) {
                unitQuery += " AND mssu.staff_unit_unit_id = (SELECT mu.unit_parent_id FROM mst_unit mu WHERE mu.unit_id =  " + unitId + ")";
            }
            if (unitFlag == 2) {
                unitQuery += " AND mssu.staff_unit_unit_id IN (SELECT mu.unit_id FROM mst_unit mu WHERE mu.unit_parent_id = (SELECT mu.unit_parent_id FROM mst_unit mu WHERE mu.unit_id = " + unitId + " )) ";
            }
            if (unitFlag == 3) {
//                unitQuery += " AND ms.staff_cluster_id = (SELECT mu.unit_cluster_id FROM mst_unit mu WHERE mu.unit_id = " + unitId + ")";
                unitQuery += " AND (SELECT COUNT(*) FROM mst_staff_staff_cluster_id_list sl INNER join mst_unit_unit_cluster_id_list mul " +
                        "WHERE sl.staff_cluster_id_list_cluster_id=mul.unit_cluster_id_list_cluster_id AND sl.mst_staff_staff_id=ms.staff_id AND mul.mst_unit_unit_id=" + unitId;
                if (!clusterId.equals("null") || !clusterId.equals("0")) {
                    /*String values = String.valueOf(clusterIdList[0]);
                    for (int i = 1; i < clusterIdList.length; i++) {
                        values += "," + clusterIdList[i];
                    }*/
                    unitQuery += " AND sl.staff_cluster_id_list_cluster_id = (" + clusterId + ")";
                }
                unitQuery += " )>0";
            }
            mainQuery += unitQuery;
            if (city != 0) {
                mainQuery += " AND mc.city_id = " + city;
            }
            if (speciality != 0) {
                mainQuery += " AND sp.speciality_id = " + speciality;
            }
            if (serviceId != 0) {
                mainQuery += " AND mss.ss_service_id= " + serviceId;
            }
            if (gender != 0) {
                mainQuery += " AND user_gender_id =  " + gender;
            }
            mainQuery += " GROUP BY ms.staff_id ";
            List<Object[]> list = entityManager.createNativeQuery(mainQuery).getResultList();
//    List<Object[]> list =  trnDoctorScheduleDetailRepository.getDoctorList(serviceId, unitId);
            for (int i = 0; i < list.size(); i++) {
                DoctorListDto doctor = new DoctorListDto();
                doctor.setName("" + list.get(i)[0]);
                doctor.setSname("" + list.get(i)[1]);
                if (list.get(i)[2] == null) {
                    doctor.setEducation("");
                } else {
                    doctor.setEducation("" + list.get(i)[2]);
                }
                doctor.setCity_name("" + list.get(i)[3]);
                if (list.get(i)[4] == null) {
                    doctor.setExp("");
                } else {
                    doctor.setExp("" + list.get(i)[4]);
                }
                doctor.setStaff_id(Long.parseLong("" + list.get(i)[5]));
                doctor.setDoctorRate(Double.parseDouble("" + list.get(i)[6]));
                if (list.get(i)[7] == null) {
                    doctor.setStaffnmcno("");
                } else {
                    doctor.setStaffnmcno("" + list.get(i)[7]);
                }
//                doctor.setStaffnmcno("" + list.get(i)[7]);
                doctor.setLanguage_name("" + list.get(i)[8]);
                if (list.get(i)[9] == null) {
                    doctor.setUnit_name("");
                } else {
                    doctor.setUnit_name("" + list.get(i)[9]);
                }
                if (list.get(i)[10] == null) {
                    doctor.setUserImage("");
                } else {
                    doctor.setUserImage("" + list.get(i)[10]);
                }
//                String dayQuery = "SELECT  md.*  from trn_doctor_schedule_detail  tds  left join  mst_day md on md.day_id =  tds.dsd_day_id WHERE tds.dsd_staff_id = "+ list.get(i)[5] + " AND tds.is_active = true";
                String dayQuery = "SELECT  md.*  from trn_doctor_schedule_detail  tds  left join  mst_day md on md.day_id =  tds.dsd_day_id WHERE tds.dsd_staff_id = " + list.get(i)[5] + " AND tds.is_active = true AND tds.is_deleted = false GROUP BY md.day_id";
                List<MstDay> day = entityManager.createNativeQuery(dayQuery, MstDay.class).getResultList();
                List<Object> dayArray = new ArrayList<>();
//                for (int j = 0; j < day.size(); j++) {
//                    Map<String, Object> map = new HashMap<>();
////                    String str =  day.get(j)[0];
//                    map.put("day", day.get(j)[0]);
//                    dayArray.add(map);
//                }
                doctor.setDayList(day);
//                if(list.get(i)[10] == null){
//                    doctor.setUnitId(0L);
//                }else{
//                    doctor.setUnitId(Long.parseLong("" + list.get(i)[10]));
//                }
                doctor.setCurrencyName(currencyName);
                doctor.setIsDoctorOnline("" + list.get(i)[11]);
                doctor.setStaffMinDuration(""+list.get(i)[12]);
                doctorList.add(doctor);
            }
            respMap.put("list", doctorList);
            respMap.put("day", date.getDay());
            return respMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("getDoctorListByServiceAndUnitId/{serviceId}/{userId}/{unitId}/{appointDate}")
    // @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDoctorListByServiceAndUnitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("serviceId") Long serviceId, @PathVariable("userId") Long userId, @PathVariable("unitId") Long unitId, @PathVariable("appointDate") String appointDate) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respMap = new HashMap<String, Object>();
        String currencyName = "" + entityManager.createNativeQuery("SELECT mc.currency_name FROM mac_currency mc WHERE mc.is_deleted=false LIMIT 1").getSingleResult();
        List<DoctorListDto> doctorList = new ArrayList<>();
        try {
            Date date1 = new Date();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(appointDate);
            Long appday = Long.parseLong(String.valueOf(date.getDay())) + 1;
            List<Object[]> list = trnDoctorScheduleDetailRepository.getDoctorListByServiceAndUnitId(serviceId, unitId, appday);
            for (int i = 0; i < list.size(); i++) {
                DoctorListDto doctor = new DoctorListDto();
                doctor.setName("" + list.get(i)[0]);
                doctor.setSname("" + list.get(i)[1]);
                if (list.get(i)[2] == null) {
                    doctor.setEducation("");
                } else {
                    doctor.setEducation("" + list.get(i)[2]);
                }
                doctor.setCity_name("" + list.get(i)[3]);
                if (list.get(i)[4] == null) {
                    doctor.setExp("");
                } else {
                    doctor.setExp("" + list.get(i)[4]);
                }
                doctor.setStaff_id(Long.parseLong("" + list.get(i)[5]));
                doctor.setDoctorRate(Double.parseDouble("" + list.get(i)[6]));
//                if(list.get(i)[10] == null){
//                    doctor.setUnitId(0L);
//                }else{
//                    doctor.setUnitId(Long.parseLong("" + list.get(i)[10]));
//                }
                doctor.setStaffMinDuration(""+list.get(i)[8]);
                doctor.setCurrencyName(currencyName);
                doctorList.add(doctor);
            }
            respMap.put("list", doctorList);
            respMap.put("day", date1.getDay());
            return respMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("getDoctorListByFilter/{date}/{serviceId}/{userId}/{sId}/{cityId}/{genderId}/{priceFilter}/{ratingFilter}/{followupFilter}")
    // @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDoctorListByFilter(@RequestHeader("X-tenantId") String tenantName, @PathVariable("date") String date, @PathVariable("serviceId") Long serviceId, @PathVariable("userId") Long userId, @PathVariable("sId") Long sId, @PathVariable("cityId") Long cityId, @PathVariable("genderId") Long genderId, @PathVariable("priceFilter") String priceFilter, @PathVariable("ratingFilter") String ratingFilter, @PathVariable("followupFilter") String followupFilter) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respMap = new HashMap<String, Object>();
        String currencyName = "" + entityManager.createNativeQuery("SELECT mc.currency_name FROM mac_currency mc WHERE mc.is_deleted=false LIMIT 1").getSingleResult();
        List<DoctorListDto> doctorList = new ArrayList<>();
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            String query = "";
            if (priceFilter.equalsIgnoreCase("LOW")) {
                query = query + "select * from ( ";
            }
            if (priceFilter.equalsIgnoreCase("HIGH")) {
                query = query + "select * from ( ";
            }
            if (ratingFilter.equalsIgnoreCase("LOWR")) {
                query = query + "select * from ( ";
            }
            if (ratingFilter.equalsIgnoreCase("HIGHR")) {
                query = query + "select * from ( ";
            }
            if (followupFilter.equalsIgnoreCase("LOWF")) {
                query = query + "select * from ( ";
            }
            if (followupFilter.equalsIgnoreCase("HIGHF")) {
                query = query + "select * from ( ";
            }
            query = query + "SELECT  CONCAT(mu.user_firstname,' ',mu.user_lastname) AS name,sp.speciality_name AS sname, " +
                    "ms.staff_education,mc.city_name,(SELECT GROUP_CONCAT(l.language_name) AS lang FROM  " +
                    "mst_staff_staff_languages ml INNER join mst_language l WHERE l.language_id=ml.staff_languages_language_id  " +
                    "AND ml.mst_staff_staff_id=ms.staff_id) AS lang, (SELECT COUNT(tr.dr_id) FROM trn_doctor_rating tr  " +
                    "WHERE tr.dr_staff_id=ms.staff_id and tr.is_deleted=FALSE) AS rating, (SELECT COUNT(tr.f_id) FROM  " +
                    "trn_followup tr WHERE tr.f_staff_id=ms.staff_id and tr.is_deleted=FALSE) AS followup_count,ms.staffexprience, " +
                    "ms.staff_id,mss.ss_base_rate,(SELECT sc.sc_unit_id FROM staff_configuration sc WHERE  " +
                    "sc.sc_staff_id=ms.staff_id) as unit,(SELECT COUNT(tf.f_id) FROM trn_followup tf WHERE  " +
                    "tf.f_staff_id=ms.staff_id AND tf.f_user_id=" + userId + ") c FROM mst_staff ms INNER JOIN  " +
                    "trn_doctor_schedule_detail sd INNER JOIN mst_user mu left JOIN mst_city mc on mu.user_city_id=mc.city_id INNER JOIN mst_speciality sp  " +
                    " INNER JOIN staff_service_id ss INNER JOIN mst_staff_services mss WHERE sd.dsd_staff_id=ms.staff_id and  " +
                    " mu.user_id=ms.staff_user_id AND ms.staff_speciality_id=sp.speciality_id   " +
                    "AND ss.mst_staff_staff_id=ms.staff_id AND mss.ss_id=ss.staff_service_id_ss_id AND  " +
                    "mss.ss_service_id=" + serviceId +
                    " AND sd.dsd_day_id=" + date1.getDay();
            if (genderId != 0) {
                query = query + " AND mu.user_gender_id=" + genderId;
            }
            if (sId != 0) {
                query = query + " AND sp.speciality_id=" + sId;
            }
            if (cityId != 0) {
                query = query + " AND mc.city_id=" + cityId;
            }
            query = query + " GROUP BY ms.staff_id";
            if (priceFilter.equalsIgnoreCase("LOW")) {
                query = query + " ) a ORDER by a.ss_base_rate asc";
            }
            if (priceFilter.equalsIgnoreCase("HIGH")) {
                query = query + " ) a ORDER by a.ss_base_rate desc";
            }
            if (ratingFilter.equalsIgnoreCase("LOWR")) {
                query = query + " ) a ORDER by a.rating asc";
            }
            if (ratingFilter.equalsIgnoreCase("HIGHR")) {
                query = query + " ) a ORDER by a.rating desc";
            }
            if (followupFilter.equalsIgnoreCase("LOWF")) {
                query = query + " ) a ORDER by a.followup_count asc ";
            }
            if (followupFilter.equalsIgnoreCase("HIGHF")) {
                query = query + " ) a ORDER by a.followup_count desc";
            }
            System.out.println("Query : " + query);
            List<Object[]> list = entityManager.createNativeQuery(query).getResultList();
            for (int i = 0; i < list.size(); i++) {
                DoctorListDto doctor = new DoctorListDto();
                doctor.setName("" + list.get(i)[0]);
                doctor.setSname("" + list.get(i)[1]);
                if (list.get(i)[2] == null) {
                    doctor.setEducation("");
                } else {
                    doctor.setEducation("" + list.get(i)[2]);
                }
                doctor.setCity_name("" + list.get(i)[3]);
                if (list.get(i)[7] == null) {
                    doctor.setExp("");
                } else {
                    doctor.setExp("" + list.get(i)[7]);
                }
                doctor.setStaff_id(Long.parseLong("" + list.get(i)[8]));
                doctor.setDoctorRate(Double.parseDouble("" + list.get(i)[9]));
                if (list.get(i)[10] == null) {
                    doctor.setUnitId(0L);
                } else {
                    doctor.setUnitId(Long.parseLong("" + list.get(i)[10]));
                }
                doctor.setCurrencyName(currencyName);
                doctorList.add(doctor);
            }
            respMap.put("list", doctorList);
            respMap.put("day", date1.getDay());
            return respMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMap;

    }

}
