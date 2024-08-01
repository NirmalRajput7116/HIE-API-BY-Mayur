
package com.cellbeans.hspa.TrnAdmissionRequest;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.emailutility.Emailsend;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.cellbeans.hspa.smsutility.Sendsms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/trn_admission_request")
public class TrnAdmissionRequestController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TrnAdmissionRequestRepository trnAdmissionRequestRepository;

    @Autowired
    MstUserRepository mstUserRepository;

    @Autowired
    Emailsend emailsend;

    @Autowired
    Sendsms sendsms;

    @Autowired
    MstStaffRepository mstStaffRepository;

    @Autowired
    private com.cellbeans.hspa.applicationproperty.Propertyconfig Propertyconfig;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmissionRequest trnAdmissionRequest) {
        TenantContext.setCurrentTenant(tenantName);
        Boolean flag = false;
        try {
            if (trnAdmissionRequest.getAdmissionrequestUserId().getUserId() > 0) {
                flag = true;
            }
        } catch (NullPointerException e) {
            flag = false;
        }
        if (!flag) {
            MstUser AddedMstUser = mstUserRepository.save(trnAdmissionRequest.getAdmissionrequestUserId());
            trnAdmissionRequest.setAdmissionrequestUserId(AddedMstUser);
        }
        if (trnAdmissionRequest.getAdmissionrequestDepartmentId().getDepartmentId() != 0L && trnAdmissionRequest.getAdmissionrequestStaffId().getStaffId() != 0L) {
            String Firstname = trnAdmissionRequest.getAdmissionrequestUserId().getUserFirstname();
            String Middlename = trnAdmissionRequest.getAdmissionrequestUserId().getUserMiddlename();
            if (Middlename != null) {
                String Middlenamecap = Middlename.substring(0, 1).toUpperCase() + Middlename.substring(1);
                trnAdmissionRequest.getAdmissionrequestUserId().setUserMiddlename(Middlenamecap);
            }
            String Lastname = trnAdmissionRequest.getAdmissionrequestUserId().getUserLastname();
            String Firstnamecap = Firstname.substring(0, 1).toUpperCase() + Firstname.substring(1);
            String Lastnamecap = Lastname.substring(0, 1).toUpperCase() + Lastname.substring(1);
            trnAdmissionRequest.getAdmissionrequestUserId().setUserFirstname(Firstnamecap);
            trnAdmissionRequest.getAdmissionrequestUserId().setUserLastname(Lastnamecap);

           /* String userAltContact=trnAdmissionRequest.getAdmissionrequestUserId().getUserAltContact();
            String userAltPhone=trnAdmissionRequest.getAdmissionrequestUserId().getUserAltPhone();

            trnAdmissionRequest.getAdmissionrequestUserId().setUserAltContact(userAltContact);
            trnAdmissionRequest.getAdmissionrequestUserId().setUserAltPhone(userAltPhone);
*/
            MstUser userId = trnAdmissionRequest.getAdmissionrequestUserId();
            MstUser AddedMstUserNew = mstUserRepository.save(trnAdmissionRequest.getAdmissionrequestUserId());
            trnAdmissionRequest.setAdmissionrequestUserId(AddedMstUserNew);
            TrnAdmissionRequest trnNewAdmissionRequest = trnAdmissionRequestRepository.save(trnAdmissionRequest);
//            if (Propertyconfig.getSmsApi()) {
//                if (trnAdmissionRequest.getAdmissionrequestUserId().getUserMobile() != null) {
//                    MstStaff mstStaff = new MstStaff();
//                    mstStaff = mstStaffRepository.getById(trnAdmissionRequest.getAdmissionrequestStaffId().getStaffId());
//                    sendsms.sendmessage(trnAdmissionRequest.getAdmissionrequestUserId().getUserMobile(), "Appointment with Dr " + mstStaff.getStaffUserId().getUserFirstname() + " " + mstStaff.getStaffUserId().getUserLastname() + " is booked successfully. " + trnAdmissionRequest.getAdmissionrequestDate() + " " + trnAdmissionRequest.getAppointmentSlot());
//                    // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
//                }
//            }
//
//            if (Propertyconfig.getEmailApi()) {
//                if (trnAdmissionRequest.getAdmissionrequestUserId().getUserEmail() != null) {
//                    System.out.println(trnAdmissionRequest.getAdmissionrequestUserId().getUserEmail());
//                    //     emailsend.sendSimpleMessage(trnAppointment.getAppointmentUserId().getUserEmail(), "WELCOME to HSPA", "Appointment with Dr " + trnAppointment.getAppointmentStaffId().getStaffUserId().getUserFirstname() + " " +
//                    //            trnAppointment.getAppointmentStaffId().getStaffUserId().getUserLastname() + " is scheduled, successfully. " + trnAppointment.getAppointmentDate() + " " + trnAppointment.getAppointmentSlot());
//                }
//            }
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("id", Long.toString(trnNewAdmissionRequest.getAdmissionrequestId()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            respMap.put("id", "null");
            return respMap;
        }

    }

    @RequestMapping("byid/{admissionRequestId}")
    public TrnAdmissionRequest read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionRequestId") Long admissionRequestId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmissionRequest trnAdmissionRequest = trnAdmissionRequestRepository.getById(admissionRequestId);
        return trnAdmissionRequest;
    }

    @RequestMapping("updateAdmissionrequestIsConfirmed/{admissionrequestId}")
    public Map<String, String> updateAdmissionrequestIsConfirmed(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionrequestId") Long admissionrequestId) {
        TenantContext.setCurrentTenant(tenantName);
        trnAdmissionRequestRepository.updateAdmissionrequestIsConfirmed(admissionrequestId);
        respMap.put("status", "success");
        return respMap;
    }

    @RequestMapping("update")
    public TrnAdmissionRequest update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmissionRequest trnAdmissionRequest) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRequestRepository.save(trnAdmissionRequest);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnAdmissionRequest> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                              @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                              @RequestParam(value = "qString", required = false) String qString,
                                              @RequestParam(value = "unitId", required = false) long unitId,
                                              @RequestParam(value = "filter", required = false) String filter,
                                              @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                              @RequestParam(value = "col", required = false, defaultValue = "admissionrequestDate") String col) {
        TenantContext.setCurrentTenant(tenantName);
        qString = qString.trim();
        filter = filter.trim();
        System.out.println("unit id" + unitId);
        if ((qString == null || qString.equals("")) && (filter == null || filter.equals(""))) {
            System.out.println("unit id" + unitId);
            return trnAdmissionRequestRepository.findAllByAdmissionrequestUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            if (filter.equals("mrno")) {
                System.out.println("appoinment search mr no ");
                return trnAdmissionRequestRepository.findAllByAdmissionrequestPatientIdPatientMrNoContainsAndAdmissionrequestUnitIdUnitIdEqualsAndIsActiveTrueAndAdmissionrequestIsCancelledTrue(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            } else if (filter.equals("name")) {
                return trnAdmissionRequestRepository.findByAdmissionrequestUserIdUserFirstnameContainsAndAdmissionrequestUnitIdUnitIdOrAdmissionrequestUserIdUserLastnameContainsAndAdmissionrequestUnitIdUnitIdAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(qString, unitId, qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else if (filter.equals("uid")) {
                return trnAdmissionRequestRepository.findByAdmissionrequestUnitIdUnitIdAndAdmissionrequestUserIdUserUidContainsAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(unitId, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            } else if (filter.equals("mobile")) {
                return trnAdmissionRequestRepository.findByAdmissionrequestUnitIdUnitIdAndAdmissionrequestUserIdUserMobileContainsAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(unitId, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            }
//            else if (filter.equals("reason")) {
//                return trnAdmissionRequestRepository.findByAdmissionrequestUnitIdUnitIdAndAdmissionrequestArIdArIdAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(unitId, Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//            }
            if (filter.equals("staff")) {
                return trnAdmissionRequestRepository.findByAdmissionrequestUnitIdUnitIdEqualsAndAdmissionrequestStaffIdStaffIdAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(unitId, Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            }
            if (filter.equals("dept")) {
                return trnAdmissionRequestRepository.findByAdmissionrequestUnitIdUnitIdAndAdmissionrequestDepartmentIdDepartmentIdAndIsActiveTrueAndAdmissionrequestIsCancelledFalse(unitId, Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            }
            if (filter.equals("date")) {
                String[] str = qString.split("/");
                try {
                    /*Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(str[0]);
                    Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(str[1]);*/
                    Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(str[0]);
                    Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(str[1]);
                    return trnAdmissionRequestRepository.findByAdmissionrequestDateBetweenAndIsActiveTrueAndIsDeletedFalseAndAdmissionrequestIsCancelledFalse(date1, date2, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                } catch (Exception e) {
                    List<TrnAdmissionRequest> objlist = new ArrayList<>();
                    return objlist;
                }
            }
            if (filter.equals("bookeddate")) {
                String[] str = qString.split("/");
                try {
                    Date date11 = new SimpleDateFormat("dd-MM-yyyy").parse(str[0]);
                    Date date12 = new SimpleDateFormat("dd-MM-yyyy").parse(str[1]);
                    return trnAdmissionRequestRepository.findByCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalseAndAdmissionrequestIsCancelledFalse(date11, date12, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                } catch (Exception e) {
                    List<TrnAdmissionRequest> objlist = new ArrayList<>();
                    return objlist;
                }
            } else {   //Added by Neha
                return trnAdmissionRequestRepository.findAllByAdmissionrequestUnitIdUnitIdAndIsActiveTrueAndAndAdmissionrequestIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
//            if (filter.equals("aptstatus")) {
//                if (qString.equals("1")) {
//                    return trnAdmissionRequestRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsBlockFalseAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//                }
//                if (qString.equals("2")) {
//                    return trnAdmissionRequestRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmTrueAndAppointmentIsCancelledreasoneIsNullAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//                }
//                if (qString.equals("3")) {
//                    return trnAdmissionRequestRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsCancelledTrue(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//                }
//                else{
//                    return trnAdmissionRequestRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsBlockTrueAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//                }
//            } else
//                {
//            return trnAdmissionRequestRepository.findAllByAdmissionrequestUnitIdUnitIdAndIsActiveTrueAndAndAdmissionrequestIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//            }
        }

    }

    @PutMapping("delete/{admissionRequestId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionRequestId") Long admissionRequestId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmissionRequest trnAdmissionRequest = trnAdmissionRequestRepository.getById(admissionRequestId);
        if (trnAdmissionRequest != null) {
            trnAdmissionRequest.setIsDeleted(true);
            trnAdmissionRequestRepository.save(trnAdmissionRequest);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("cancel")
    public Map<String, String> cancel(@RequestHeader("X-tenantId") String tenantName, @RequestParam("admissionrequestId") Long admissionrequestId, @RequestParam("admissionRequestreasone") String admissionRequestreasone) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmissionRequest trnAdmissionRequest = trnAdmissionRequestRepository.getById(admissionrequestId);
        if (trnAdmissionRequest != null) {
            trnAdmissionRequest.setAdmissionrequestIsCancelled(true);
            trnAdmissionRequest.setAdmissionrequestIsCancelledreasone(admissionRequestreasone);
            trnAdmissionRequestRepository.save(trnAdmissionRequest);
//            if (Propertyconfig.getSmsApi()) {
//                if (trnAdmissionRequest.getAppointmentUserId().getUserMobile() != null) {
//                    MstStaff mstStaff = new MstStaff();
//
//                    mstStaff = mstStaffRepository.getById(trnAppointment.getAppointmentStaffId().getStaffId());
//                    sendsms.sendmessage(trnAppointment.getAppointmentUserId().getUserMobile(), "Appointment with Dr " + mstStaff.getStaffUserId().getUserFirstname() + " " + mstStaff.getStaffUserId().getUserLastname() + " Cancelled successfully. " + trnAppointment.getAppointmentDate() + " " + trnAppointment.getAppointmentSlot());
//                    // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
//                }
//            }
            respMap.put("msg", "Admission Request Cancelled");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    //    @GetMapping
//    @RequestMapping("block")
//    public Map<String, String> block(@RequestHeader("X-tenantId") String tenantName, @RequestParam("appointmentId") Long appointmentId, @RequestParam("appointmentreasone") String appointmentreasone) {
//        TrnAppointment trnAppointment = trnAdmissionRequestRepository.getById(appointmentId);
//        if (trnAppointment != null) {
//            trnAppointment.setAppointmentIsBlock(true);
//           // trnAppointment.setAppointmentIsCancelled(true);
//            trnAppointment.setAppointmentIsCancelledreasone(appointmentreasone);
//            trnAdmissionRequestRepository.save(trnAppointment);
//
//            if (Propertyconfig.getSmsApi()) {
//                if (trnAppointment.getAppointmentUserId().getUserMobile() != null) {
//                    MstStaff mstStaff = new MstStaff();
//
//                    mstStaff = mstStaffRepository.getById(trnAppointment.getAppointmentStaffId().getStaffId());
//                    sendsms.sendmessage(trnAppointment.getAppointmentUserId().getUserMobile(), "Appointment with Dr " + mstStaff.getStaffUserId().getUserFirstname() + " " + mstStaff.getStaffUserId().getUserLastname() + " Cancelled successfully. " + trnAppointment.getAppointmentDate() + " " + trnAppointment.getAppointmentSlot());
//                    // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
//                }
//            }
//
//            respMap.put("msg", "Appointment Cancelled");
//            respMap.put("success", "1");
//        } else {
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
//
//    @GetMapping
//    @RequestMapping("unblock")
//    public Map<String, String> unblock(@RequestHeader("X-tenantId") String tenantName, @RequestParam("appointmentId") Long appointmentId, @RequestParam("appointmentreasone") String appointmentreasone) {
//        TrnAppointment trnAppointment = trnAdmissionRequestRepository.getById(appointmentId);
//        if (trnAppointment != null) {
//            trnAppointment.setAppointmentIsBlock(false);
//            // trnAppointment.setAppointmentIsCancelled(true);
//            trnAppointment.setAppointmentIsCancelledreasone(appointmentreasone);
//            trnAdmissionRequestRepository.save(trnAppointment);
//
//            /*if (Propertyconfig.getSmsApi()) {
//                if (trnAppointment.getAppointmentUserId().getUserMobile() != null) {
//                    MstStaff mstStaff = new MstStaff();
//
//                    mstStaff = mstStaffRepository.getById(trnAppointment.getAppointmentStaffId().getStaffId());
//                    sendsms.sendmessage(trnAppointment.getAppointmentUserId().getUserMobile(), "Appointment with Dr " + mstStaff.getStaffUserId().getUserFirstname() + " " + mstStaff.getStaffUserId().getUserLastname() + " Cancelled successfully. " + trnAppointment.getAppointmentDate() + " " + trnAppointment.getAppointmentSlot());
//                    // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
//                }
//            }
//*/
//            respMap.put("msg", "Appointment Unblocked");
//            respMap.put("success", "1");
//        } else {
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
    @PutMapping("confirm/{admissionrequestId}")
    public Map<String, String> confirm(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionrequestId") Long admissionrequestId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmissionRequest trnAdmissionRequest = trnAdmissionRequestRepository.getById(admissionrequestId);
        if (trnAdmissionRequest != null) {
            trnAdmissionRequest.setAdmissionrequestIsConfirm(true);
            trnAdmissionRequestRepository.save(trnAdmissionRequest);
            respMap.put("msg", "Admission Request Confirmed");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    //
//    @PutMapping("disconfirm/{appointmentId}")
//    public Map<String, String> disconfirm(@RequestHeader("X-tenantId") String tenantName, @PathVariable("appointmentId") Long appointmentId) {
//        TrnAppointment trnAppointment = trnAdmissionRequestRepository.getById(appointmentId);
//        if (trnAppointment != null) {
//            trnAppointment.setAppointmentIsConfirm(false);
//            trnAdmissionRequestRepository.save(trnAppointment);
//            respMap.put("msg", "Appointment Confirmed");
//            respMap.put("success", "1");
//        } else {
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
//
//    @GetMapping
//    @RequestMapping("AppoinmentBulkList")
//    public Iterable<TrnAppointment> AppoinmentBulkList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                                       @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                                       @RequestParam(value = "qString", required = false) String qString,
//                                                       @RequestParam(value = "filter", required = false) String filter,
//                                                       @RequestParam(value = "unitId", required = false) long unitId,
//                                                       @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                                       @RequestParam(value = "col", required = false, defaultValue = "appointmentId") String col) {
//
//
//            if(filter.equals("stafff"))
//            {
//                return trnAdmissionRequestRepository.findByAppointmentUnitIdUnitIdEqualsAndAppointmentStaffIdStaffIdAndAppointmentIsConfirmFalseAndAppointmentIsBlockFalseAndIsActiveTrueAndAppointmentIsCancelledFalse(unitId, Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//            }
//        /*    else if (filter.equals("ddate")){
//                return trnAppointmentRepository.findByAppointmentDateBetweenAndIsActiveTrueAndIsDeletedFalseAndAppointmentIsCancelledFalse(date, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }*/
//           else {
//                col = "appointment_id";
//                return trnAdmissionRequestRepository.findAllAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNullAndAppointmentUnitIdUnitIdAndAppointmentIsBlockFalseAndIsActiveTrueAndIsDeletedFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//            }
//    }
//
//    @GetMapping
//    @RequestMapping("AppoinmentTodaysList")
//    public List<TrnAppointment> AppoinmenttodaysList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId", required = false) String unitId, @RequestParam(value = "dateFrom", required = false) String dateFrom, @RequestParam(value = "dateTo", required = false) String dateTo) {
//
//        try {
//        	System.out.println("unitId :"+unitId);
//        	System.out.println("dateFrom :"+dateFrom);
//        	System.out.println("dateTo :"+dateTo);
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        c.setTime(sdf.parse(dateTo));
//        c.add(Calendar.DAY_OF_MONTH, 1);
//        String newDateTo = sdf.format(c.getTime());
//        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
//        String dateToStr = sd.format(sd.parse(newDateTo));
//        String dateFromStr = sd.format(sd.parse(dateFrom));
//
//        String query = "SELECT a From TrnAppointment a where"
//        		+ " a.isActive=1"
//        		+ " and a.isDeleted=0"
//        		+ " and a.appointmentIsConfirm=0"
//        	//	+ " and a.appointmentIsCancelled=0"
//     	        + " and a.appointmentIsBlock=0";
//        if(unitId!=null && unitId!="") {
// 		   query += " and a.appointmentUnitId.unitId="+Long.parseLong(unitId);
//		}
//        query +=  " and (a.createdDate BETWEEN '"+dateFromStr+"' and '"+dateToStr+"')";
//        System.out.println("Query :"+query);
//        List<TrnAppointment> jsonArray = (List<TrnAppointment>) entityManager.createQuery(query).getResultList();
//        return jsonArray;
//
//        }catch(Exception e) {
//        	System.out.println(e);
//        }
//        return null;
//            //return trnAppointmentRepository.findAllAppointmentIsConfirmFalseAndAppointmentreasoneIsNullAndAppointmentUnitIdUnitIdAndAppointmentIsBlockFalseAndIsActiveTrueAndIsDeletedFalse(unitId);
//    }
//
//    @GetMapping
//    @RequestMapping("AppoinmentTodaysstaffList")
//    public List<TrnAppointment> AppoinmentstaffwisetodaysList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId", required = false) long unitId, @RequestParam(value = "staffId", required = false) long staffId) {
//
//        return trnAdmissionRequestRepository.findAllAppointmentIsConfirmFalseAndIsNullAndAppointmentUnitIdUnitIdAndAppointmentIsBlockFalseAndIsActiveTrueAndIsDeletedFalse(unitId,staffId);
//    }
//
//
//
//
//    @GetMapping("getRecordByPatientIdAndDate")
//    public TrnAppointment getRecordByPatientIdAndDate(@RequestHeader("X-tenantId") String tenantName, @RequestParam("patientId") Long patientId, @RequestParam("followUpDate") String followUpDate) {
//
//        Date sdate = new Date();
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            sdate = sdf1.parse(followUpDate);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        TrnAppointment trnAppointment = trnAdmissionRequestRepository.findByAppointmentPatientIdPatientIdAndAppointmentDateAndAppointmentIsCancelledFalseAndIsActiveTrue(patientId,sdate);
//        return trnAppointment;
//    }
//
    @RequestMapping(value = "/billSearchByobj", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody
    List<TrnAdmissionRequest> billfilter(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmissionRequestSearch objTrnAdmissionRequestSearch) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAdmissionRequest> objTrnAdmissionRequest = new ArrayList<>();
        String Query = "";
        Query = "select t from TrnAdmissionRequest t where t.admissionrequestUnitId.unitId = " + objTrnAdmissionRequestSearch.getUnitid() + " and t.isActive = true and t.isDeleted = false";
        if (objTrnAdmissionRequestSearch.getMrno() != null) {
            Query += " and t.admissionrequestPatientId.patientMrNo like '%" + objTrnAdmissionRequestSearch.getMrno() + "%'";
        }
        if (objTrnAdmissionRequestSearch.getPtname() != null) {
            Query += " and (t.admissionrequestUserId.userFirstname like '%" + objTrnAdmissionRequestSearch.getPtname() + "%' or t.admissionrequestUserId.userLastname like '%" + objTrnAdmissionRequestSearch.getPtname() + "%')";
        }
        if (objTrnAdmissionRequestSearch.getUid() != null) {
            Query += " and t.admissionrequestUserId.userUid like '%" + objTrnAdmissionRequestSearch.getUid() + "%'";
        }
        if (objTrnAdmissionRequestSearch.getMobile() != null) {
            Query += " and t.admissionrequestUserId.userMobile like '%" + objTrnAdmissionRequestSearch.getMobile() + "%'";
        }
        if (objTrnAdmissionRequestSearch.getStaff() > 0) {
            Query += " and t.admissionrequestStaffId.staffId = " + objTrnAdmissionRequestSearch.getStaff();
        }
        if (objTrnAdmissionRequestSearch.getDept() > 0) {
            Query += " and t.admissionrequestDepartmentId.departmentId = " + objTrnAdmissionRequestSearch.getDept();
        }
        if (objTrnAdmissionRequestSearch.getWard() > 0) {
            Query += " and t.bedWardId.wardId = " + objTrnAdmissionRequestSearch.getWard();
        }
        if (objTrnAdmissionRequestSearch.getFromdate() != null) {
            String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(objTrnAdmissionRequestSearch.getFromdate()));
            String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(objTrnAdmissionRequestSearch.getTodate()));
            Query += " and t.admissionrequestDate between '" + date1 + "' And '" + date2 + "'";
        }
        if (objTrnAdmissionRequestSearch.getBookedfromdate() != null) {
            String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(objTrnAdmissionRequestSearch.getBookedfromdate()));
            String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(objTrnAdmissionRequestSearch.getBookedtodate()));
            Query += " and t.admissionrequestExpAdmDate between '" + date1 + "' And '" + date2 + "'";
        }
        Query += " order by t.createdDate desc";
        objTrnAdmissionRequest = entityManager.createQuery(Query, TrnAdmissionRequest.class).setFirstResult(objTrnAdmissionRequestSearch.getOffset() - 1).setMaxResults(objTrnAdmissionRequestSearch.getLimit()).getResultList();
        long count = 0;
        String CountQuery = StringUtils.replace(Query, "t fr", "count(t.admissionrequestId) fr");
        count = (long) entityManager.createQuery(CountQuery).getSingleResult();
        if (count != 0) {
            objTrnAdmissionRequest.get(0).setCount((int) count);
        }
        return objTrnAdmissionRequest;

    }
//
//    //vijay p.
//    @RequestMapping("allbulkappbyfilter/{page}/{size}/{sort}/{col}")
//    public Iterable<TrnAppointment> allBulkAppListbyfilter(@RequestHeader("X-tenantId") String tenantName, @RequestBody String jsonStr, @PathVariable("page") String page, @PathVariable("size") String size, @PathVariable("sort") String sort, @PathVariable("col") String col)  {
//
//    	JSONObject json=new JSONObject(jsonStr);
//    	System.out.println("json:" + json);
//        try {
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        c.setTime(sdf.parse(String.valueOf(json.getString("dateTo"))));
//        c.add(Calendar.DAY_OF_MONTH, 1);
//        String newDateTo = sdf.format(c.getTime());
//        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
//        String dateTo = sd.format(sd.parse(newDateTo));
//        String dateFrom = sd.format(sd.parse(json.getString("dateFrom")));
//           // filters - >unit id /staffid /block /unblock /department/subdepartment /between date/between time
//
//            String query = "SELECT a From TrnAppointment a where"
//            		+ " a.isActive=1"
//            		+ " and a.isDeleted=0"
//            		+ " and a.appointmentIsConfirm=0"
//            		+ " and a.appointmentIsCancelled=0"
//         	        + " and a.appointmentIsBlock="+json.getBoolean("isBlock");
//
//           if(!json.getString("staffId").isEmpty()) {
//        	   query += " and a.appointmentStaffId.staffId="+json.getString("staffId");
//            		}
//        	if(!json.getString("unitId").isEmpty()) {
//        		   query += " and a.appointmentUnitId.unitId="+json.getString("unitId");
//    		}
//        	if(!json.getString("departmentId").isEmpty()) {
//        		   query += " and a.appointmentDepartmentId.departmentId="+json.getString("departmentId");
//    		}
//        	if(!json.getString("subDepartmentId").isEmpty()) {
//        		   query += " and a.appointmentSdId.sdId="+json.getString("subDepartmentId");
//    		}
//
//        	 query +=  " and (a.createdDate BETWEEN '"+dateFrom+"' and '"+dateTo+"')";
//        	 query +=  " Order by a.appointmentId "+sort;
//          //   System.out.println("query 2:" + query);
//             String countQuery=StringUtils.replace(query, " a From", " count(a) from");
//             Long count = (Long)entityManager.createQuery(countQuery).getSingleResult();
//             // list for pagination below
//           //  List<TrnAppointment> jsonArray = (List<TrnAppointment>) entityManager.createQuery(query).setFirstResult(((Integer.parseInt(page) - 1) * Integer.parseInt(size))).setMaxResults(Integer.parseInt(size)).getResultList();
//            List<TrnAppointment> jsonArray = (List<TrnAppointment>) entityManager.createQuery(query).getResultList();
//            if(jsonArray.size()>0) {
//                jsonArray.get(0).setCount(Integer.parseInt(count.toString()));
//            }
//
//            return jsonArray;
//
//
//        } catch (Exception e) {
//        	System.out.println(e);
//        }
//        return null;
//
//        }
}



