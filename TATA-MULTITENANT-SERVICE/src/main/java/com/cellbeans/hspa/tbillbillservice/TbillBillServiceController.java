package com.cellbeans.hspa.tbillbillservice;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mbillipdcharges.MbillIPDCharge;
import com.cellbeans.hspa.mbillipdcharges.MbillIPDChargeRepository;
import com.cellbeans.hspa.mpathparameterrange.MpathParameterRange;
import com.cellbeans.hspa.mpathparameterrange.MpathParameterRangeController;
import com.cellbeans.hspa.mpathparameterrange.MpathParameterRangeFilter;
import com.cellbeans.hspa.mpathresult.MpathResult;
import com.cellbeans.hspa.mpathresult.MpathResultRepository;
import com.cellbeans.hspa.mpathtest.MpathTest;
import com.cellbeans.hspa.mpathtest.MpathTestRepository;
import com.cellbeans.hspa.mpathtestresult.MpathTestResult;
import com.cellbeans.hspa.mpathtestresult.MpathTestResultRepository;
import com.cellbeans.hspa.mstday.MstDay;
import com.cellbeans.hspa.mstday.MstDayRepository;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import com.cellbeans.hspa.rpathparametertextlink.RpathParameterTextLink;
import com.cellbeans.hspa.rpathparametertextlink.RpathParameterTextLinkRepository;
import com.cellbeans.hspa.rpathtestparameter.RpathTestParameter;
import com.cellbeans.hspa.rpathtestparameter.RpathTestParameterRepository;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tbillbill.TbillBillRepository;
import com.cellbeans.hspa.tbillservicegeneral.TbillServiceGeneral;
import com.cellbeans.hspa.tbillservicegeneral.TbillServiceGeneralRepository;
import com.cellbeans.hspa.tbillserviceradiology.TbillServiceRadiology;
import com.cellbeans.hspa.tbillserviceradiology.TbillServiceRadiologyRepository;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.cellbeans.hspa.temrtimeline.TemrTimelineRepository;
import com.cellbeans.hspa.temrvisitinvestigation.TemrVisitInvestigation;
import com.cellbeans.hspa.temrvisitinvestigation.TemrVisitInvestigationRespository;
import com.cellbeans.hspa.tpathbs.TpathBs;
import com.cellbeans.hspa.tpathbs.TpathBsRepository;
import com.cellbeans.hspa.trnappointment.TrnAppointment;
import com.cellbeans.hspa.trnappointment.TrnAppointmentRepository;
import com.cellbeans.hspa.trndoctorscheduledetail.TrnDoctorScheduleDetail;
import com.cellbeans.hspa.trndoctorscheduledetail.TrnDoctorScheduleDetailRepository;
import com.cellbeans.hspa.trnservicecabinschedule.TrnServiceCabinSchedule;
import com.cellbeans.hspa.trnservicecabinschedule.TrnServiceCabinScheduleRepository;
import com.cellbeans.hspa.trnstaffcabinschedule.TrnStaffCabinScheduleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/tbill_service")
public class TbillBillServiceController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TbillBillRepository tbillBillRepository;

    @Autowired
    TbillBillServiceRepository tbillBillServiceRepository;

    @Autowired
    TbillServiceRadiologyRepository tbillServiceRadiologyRepository;

    @Autowired
    TbillServiceGeneralRepository tbillServiceGeneralRepository;

    @Autowired
    TpathBsRepository tpathBsRepository;

    @Autowired
    RpathTestParameterRepository rpathTestParameterRepository;

    @Autowired
    MstDayRepository mstDayRepository;

    @Autowired
    TrnStaffCabinScheduleRepository trnStaffCabinScheduleRepository;

    @Autowired
    TrnServiceCabinScheduleRepository trnServiceCabinScheduleRepository;

    @Autowired
    MstStaffRepository mstStaffRepository;

    @Autowired
    MpathTestRepository mpathTestRepository;

    @Autowired
    TemrTimelineRepository temrTimelineRepository;

    @Autowired
    TrnAppointmentRepository trnAppointmentRepository;

    @Autowired
    TrnDoctorScheduleDetailRepository trnDoctorScheduleDetailRepository;

    @Autowired
    MbillIPDChargeRepository mbillIPDBillCharge;

    @Autowired
    TemrVisitInvestigationRespository temrVisitInvestigationRespository;

    @Autowired
    MpathParameterRangeController mpathParameterRangeController;

    @Autowired
    MpathTestResultRepository mpathTestResultRepository;

    @Autowired
    MpathResultRepository mpathResultRepository;

    @Autowired
    RpathParameterTextLinkRepository rpathParameterTextLinkRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TbillBillService> tbillBillServices) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("in create ");
        List<TbillBillService> finalBillServiceList = new ArrayList<TbillBillService>();
        for (int i = 0; i < tbillBillServices.size(); i++) {
            java.sql.Date todaysdate;
            LocalDate today = LocalDate.now();
            todaysdate = java.sql.Date.valueOf(today);
            MstStaff staff = new MstStaff();
            staff = tbillBillServices.get(i).getBsStaffId();
            Long staffid = staff.getStaffId();
            // System.out.println("staff id =------------- "+staffid);
            // System.out.println("toda date =------------- "+todaysdate);
            String Prefix = "";
            String newTokenNo = "";
            String lastTokenNo = "";
            try {
                TbillBillService serviceList = tbillBillServiceRepository
                        .findOneByBsDateAndIsActiveTrueAndIsDeletedFalse(todaysdate);
                lastTokenNo = serviceList.getBsTokenNumber();
            } catch (Exception exception) {
                lastTokenNo = "";
            }
            if (lastTokenNo == null) {
                lastTokenNo = "";
            }
            if (lastTokenNo.equals("")) {
                newTokenNo = Prefix + "001";
            } else {
                String number = lastTokenNo.substring(1);
                int newNumber = Integer.parseInt(number);
                String incNumber = String.format("%03d", newNumber + 1);
                newTokenNo = incNumber;
            }
            tbillBillServices.get(i).setBsTokenNumber(newTokenNo);
            tbillBillServices.get(i).setBsDate(new Date());
            TbillBillService newBillServiceList = tbillBillServiceRepository.save(tbillBillServices.get(i));
            TBillBill tbill = new TBillBill();
            tbill = tbillBillRepository.findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(
                    tbillBillServices.get(i).getBsBillId().getBillId());
            System.out.println("----------------------Timeline------------------------------ ");
            System.out.println(tbill.getBillVisitId().getVisitId());
            System.out.println(tbill.getBillVisitId().getVisitPatientId().getPatientId());
            System.out.println("----------------------Timeline------------------------------ ");
            TemrTimeline temrTimeline = new TemrTimeline();
            temrTimeline.setTimelineVisitId(tbill.getBillVisitId());
            temrTimeline.setTimelinePatientId(tbill.getBillVisitId().getVisitPatientId());
            temrTimeline.setTimelineTime(new Date());
            temrTimeline.setTimelineDate(new Date());
            temrTimeline.setEMRFinal(false);
            temrTimeline.setTimelineServiceId(newBillServiceList);
            temrTimeline.setTimelineStaffId(tbillBillServices.get(i).getBsStaffId());
            temrTimeline = temrTimelineRepository.save(temrTimeline);
            finalBillServiceList.add(newBillServiceList);
        }
        if (finalBillServiceList.size() != 0) {
            getTbillServiceRadiologyByDepartmentName(tenantName, finalBillServiceList,
                    (finalBillServiceList.get(0).getBsBillId().getBillOutstanding() > 0 ? true : false),
                    finalBillServiceList.get(0).getBsBillId().getIpdBill());
            // getCabinForService(finalBillServiceList);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @RequestMapping("createIndivisual")
    public Map<String, String> createIndivisual(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillBillService tbillBillServices) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("----------------____AT start------- " + tbillBillServices.getBsTokenNumber());
        List<TbillBillService> finalBillServiceList = new ArrayList<TbillBillService>();
        TbillBillService newBillServiceList;
        tbillBillServices.setBsDate(new Date());
        newBillServiceList = new TbillBillService();
        if (tbillBillServices.getBsStaffId().getStaffId() == 0) {
            tbillBillServices.setBsStaffId(null);
        }
        newBillServiceList = tbillBillServiceRepository.save(tbillBillServices);
        if (newBillServiceList.getBsId() != 0) {
            TBillBill tbill = new TBillBill();
            tbill = tbillBillRepository
                    .findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(tbillBillServices.getBsBillId().getBillId());
            TemrTimeline temrTimeline = new TemrTimeline();
            temrTimeline.setTimelineVisitId(tbill.getBillVisitId());
            temrTimeline.setTimelinePatientId(tbill.getBillVisitId().getVisitPatientId());
            temrTimeline.setTimelineTime(new Date());
            temrTimeline.setTimelineDate(new Date());
            temrTimeline.setEMRFinal(false);
            temrTimeline.setTimelineServiceId(newBillServiceList);
            temrTimeline.setTimelineStaffId(tbillBillServices.getBsStaffId());
            temrTimeline = temrTimelineRepository.save(temrTimeline);
            finalBillServiceList.add(newBillServiceList);
        }
        if (finalBillServiceList.size() != 0) {
            getTbillServiceRadiologyByDepartmentName(tenantName, finalBillServiceList,
                    (finalBillServiceList.get(0).getBsBillId().getBillOutstanding() > 0 ? true : false),
                    false);
            // if(tbillBillServices.getBsServiceId().getServiceIsTokenDisplay()
            // == true)
            // {
            // getCabinForService(finalBillServiceList);
            // }
            System.out.println("--------added------------------ ");
            System.out.println(newBillServiceList.getBsId());
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("bsId", Long.toString(newBillServiceList.getBsId()));
            respMap.put("serviceIsTokenDisplay",
                    String.valueOf(newBillServiceList.getBsServiceId().getServiceIsTokenDisplay()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @RequestMapping("createIndivisualForImport")
    public Map<String, String> createIndivisualForImport(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillBillService tbillBillServices) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("----------------____AT start------- " + tbillBillServices.getBsTokenNumber());
        List<TbillBillService> finalBillServiceList = new ArrayList<TbillBillService>();
        TbillBillService newBillServiceList;
        tbillBillServices.setBsDate(new Date());
        newBillServiceList = new TbillBillService();
        if (tbillBillServices.getBsStaffId().getStaffId() == 0) {
            tbillBillServices.setBsStaffId(null);
        }
        newBillServiceList = tbillBillServiceRepository.save(tbillBillServices);
        if (newBillServiceList.getBsId() != 0) {
            TBillBill tbill = new TBillBill();
            tbill = tbillBillRepository
                    .findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(tbillBillServices.getBsBillId().getBillId());
            TemrTimeline temrTimeline = new TemrTimeline();
            if (tbill.getIpdBill()) {
                temrTimeline.setTimelineAdmissionId(tbill.getBillAdmissionId());
                temrTimeline.setTimelinePatientId(tbill.getBillAdmissionId().getAdmissionPatientId());

            } else {
                temrTimeline.setTimelineVisitId(tbill.getBillVisitId());
                temrTimeline.setTimelinePatientId(tbill.getBillVisitId().getVisitPatientId());
            }
            temrTimeline.setTimelineTime(new Date());
            temrTimeline.setTimelineDate(new Date());
            temrTimeline.setEMRFinal(false);
            temrTimeline.setTimelineServiceId(newBillServiceList);
            temrTimeline.setTimelineStaffId(tbillBillServices.getBsStaffId());
            temrTimeline = temrTimelineRepository.save(temrTimeline);
            finalBillServiceList.add(newBillServiceList);
        }
        if (finalBillServiceList.size() != 0) {
            getTbillServiceRadiologyByDepartmentNameForImport(tenantName, finalBillServiceList,
                    (finalBillServiceList.get(0).getBsBillId().getBillOutstanding() > 0 ? true : false),
                    finalBillServiceList.get(0).getBsBillId().getIpdBill());
            // if(tbillBillServices.getBsServiceId().getServiceIsTokenDisplay()
            // == true)
            // {
            // getCabinForService(finalBillServiceList);
            // }
            System.out.println("--------added------------------ ");
            System.out.println(newBillServiceList.getBsId());
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("bsId", Long.toString(newBillServiceList.getBsId()));
            respMap.put("serviceIsTokenDisplay",
                    String.valueOf(newBillServiceList.getBsServiceId().getServiceIsTokenDisplay()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @RequestMapping("createIndivisualForEMR")
    public Map<String, String> createIndivisualForEMR(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillBillService tbillBillServices) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("----------------____AT start EMR------- " + tbillBillServices.getBsTokenNumber());
        List<TbillBillService> finalBillServiceList = new ArrayList<TbillBillService>();
        TbillBillService newBillServiceList;
        tbillBillServices.setBsDate(new Date());
        newBillServiceList = new TbillBillService();
        newBillServiceList = tbillBillServiceRepository.save(tbillBillServices);
        if (newBillServiceList.getBsId() != 0) {
//			TBillBill tbill = new TBillBill();
//			tbill = tbillBillRepository
//					.findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(tbillBillServices.getBsBillId().getBillId());
//			TemrTimeline temrTimeline = new TemrTimeline();
//			temrTimeline.setTimelineVisitId(tbill.getBillVisitId());
//			temrTimeline.setTimelinePatientId(tbill.getBillVisitId().getVisitPatientId());
//			temrTimeline.setTimelineTime(new Date());
//			temrTimeline.setTimelineDate(new Date());
//			temrTimeline.setEMRFinal(false);
//			temrTimeline.setTimelineServiceId(newBillServiceList);
//			temrTimeline.setTimelineStaffId(tbillBillServices.getBsStaffId());
//			temrTimeline = newBillServiceList.save(temrTimeline);
            TBillBill tbill = tbillBillRepository.findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(tbillBillServices.getBsBillId().getBillId());
            TemrTimeline temrTimeline = temrTimelineRepository.findByTimelineVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(tbill.getBillVisitId().getVisitId());
//            TemrTimeline temrTimeline = temrTimelineRepository.findBybsId(newBillServiceList.getBsId());
            TemrVisitInvestigation temrVisitInvestigation = temrVisitInvestigationRespository.findByViTimelineIdTimelineIdEqualsAndViVisitIdVisitIdEqualsAndViServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(temrTimeline.getTimelineId(), tbill.getBillVisitId().getVisitId(), tbillBillServices.getBsServiceId().getServiceId());
            if (temrVisitInvestigation != null) {
                temrVisitInvestigationRespository.updateTemrVisitInvestigationForBill(temrVisitInvestigation.getViId(), 1);

            }
            finalBillServiceList.add(newBillServiceList);
        }
        if (finalBillServiceList.size() != 0) {
            getTbillServiceRadiologyByDepartmentName(tenantName, finalBillServiceList,
                    (finalBillServiceList.get(0).getBsBillId().getBillOutstanding() > 0 ? true : false),
                    false);
            // if(tbillBillServices.getBsServiceId().getServiceIsTokenDisplay()
            // == true)
            // {
            // getCabinForService(finalBillServiceList);
            // }
            System.out.println("--------added------------------ ");
            System.out.println(newBillServiceList.getBsId());
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("bsId", Long.toString(newBillServiceList.getBsId()));
            respMap.put("serviceIsTokenDisplay",
                    String.valueOf(newBillServiceList.getBsServiceId().getServiceIsTokenDisplay()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @RequestMapping("createIPD")
    public Map<String, String> createIPD(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TbillBillService> tbillBillServices) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("tbillBillServices : " + tbillBillServices.size());
        List<TbillBillService> finalBillServiceList = new ArrayList<TbillBillService>();
        for (int i = 0; i < tbillBillServices.size(); i++) {
            tbillBillServices.get(i).setBsId(0);
            java.sql.Date todaysdate;
            LocalDate today = LocalDate.now();
            todaysdate = java.sql.Date.valueOf(today);
            MstStaff staff = new MstStaff();
            if (tbillBillServices.get(i).getBsStaffId().getStaffId() != 0) {
                staff = tbillBillServices.get(i).getBsStaffId();
                Long staffid = staff.getStaffId();
            } else {
                tbillBillServices.get(i).setBsStaffId(null);
            }
            if (tbillBillServices.get(i).getBsDate() == null) {
                tbillBillServices.get(i).setBsDate(new Date());
            }
            TbillBillService newBillServiceList = tbillBillServiceRepository.save(tbillBillServices.get(i));
            TBillBill tbill = new TBillBill();
            tbill = tbillBillRepository.findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(
                    tbillBillServices.get(i).getBsBillId().getBillId());
            finalBillServiceList.add(newBillServiceList);

        }
        if (finalBillServiceList.size() != 0) {
            getTbillServiceRadiologyByDepartmentName(tenantName, finalBillServiceList,
                    (finalBillServiceList.get(0).getBsBillId().getBillOutstanding() > 0 ? true : false),
                    true);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @RequestMapping("createIPDByMibillIpd/{ipd_id}")
    public Map<String, String> createIPDById(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TbillBillService> tbillBillServices,
                                             @PathVariable("ipd_id") Long ipd_id) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("tbillBillServices : " + ipd_id);
        List<TbillBillService> finalBillServiceList = new ArrayList<TbillBillService>();
        for (int i = 0; i < tbillBillServices.size(); i++) {
            tbillBillServices.get(i).setBsId(0);
            java.sql.Date todaysdate;
            LocalDate today = LocalDate.now();
            todaysdate = java.sql.Date.valueOf(today);
            if (tbillBillServices.get(i).getBsStaffId() != null) {
                MstStaff staff = new MstStaff();
                staff = tbillBillServices.get(i).getBsStaffId();
                Long staffid = staff.getStaffId();
            }
            if (tbillBillServices.get(i).getBsDate() == null) {
                tbillBillServices.get(i).setBsDate(new Date());
            }
            TbillBillService newBillServiceList = tbillBillServiceRepository.save(tbillBillServices.get(i));
            TBillBill tbill = new TBillBill();
            tbill = tbillBillRepository.findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(
                    tbillBillServices.get(i).getBsBillId().getBillId());
            finalBillServiceList.add(newBillServiceList);

        }
        if (finalBillServiceList.size() != 0) {
            getTbillServiceRadiologyByDepartmentName(tenantName, finalBillServiceList,
                    (finalBillServiceList.get(0).getBsBillId().getBillOutstanding() > 0 ? true : false),
                    true, ipd_id);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @RequestMapping("updateIPD")
    public Map<String, String> updateIPD(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TbillBillService> tbillBillServices) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("in create ");
        List<TbillBillService> finalBillServiceList = new ArrayList<TbillBillService>();
        for (int i = 0; i < tbillBillServices.size(); i++) {
            java.sql.Date todaysdate;
            LocalDate today = LocalDate.now();
            todaysdate = java.sql.Date.valueOf(today);
            MstStaff staff = new MstStaff();
            staff = tbillBillServices.get(i).getBsStaffId();
            Long staffid = staff.getStaffId();
            if (tbillBillServices.get(i).getBsDate() == null) {
                tbillBillServices.get(i).setBsDate(new Date());
            }
            TbillBillService newBillServiceList = tbillBillServiceRepository.save(tbillBillServices.get(i));
            TBillBill tbill = new TBillBill();
            tbill = tbillBillRepository.findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(
                    tbillBillServices.get(i).getBsBillId().getBillId());
            finalBillServiceList.add(newBillServiceList);
        }
        if (finalBillServiceList.size() != 0) {
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{bsId}")
    public TbillBillService read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsId") Long bsId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillBillService tbillBillService = tbillBillServiceRepository.getById(bsId);
        return tbillBillService;
    }

    @GetMapping
    @RequestMapping("listbybill/{billId}")
    public List<TbillBillService> getAllServiceByBillId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("billId") Long billId) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillServiceRepository
                .findByBsBillIdBillIdEqualsAndBsCancelFalseAndIsActiveTrueAndIsDeletedFalse(billId);
    }

    @GetMapping
    @RequestMapping("listbybillId")
    public Map<String, Object> listbybillId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                            @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
                                            @RequestParam(value = "billId", required = false) Long billId) {
        TenantContext.setCurrentTenant(tenantName);
   /*  Iterable<TbillBillService> pagelist = tbillBillServiceRepository
               .findByBsBillIdBillIdEqualsAndBsCancelFalseAndIsActiveTrueAndIsDeletedFalse(billId, new PageRequest(Integer.parseInt(page) - 1, Integer.parseInt(size),
                       Sort.Direction.fromStringOrNull(sort), col));*/
        Map<String, Object> map = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        Double gr = 0.00;
        Double da = 0.00;
        Double ds = 0.00;
        Double ha = 0.00;
//     map = mapper.convertValue(pagelist, new TypeReference<Map<String, Object>>() {});
        List<TbillBillService> list = tbillBillServiceRepository
                .findByBsBillIdBillIdEqualsAndBsCancelFalseAndIsActiveTrueAndIsDeletedFalse(billId);
        for (TbillBillService billservice : list) {
            gr += billservice.getBsGrossRate();
            da += billservice.getBsDiscountAmount();
            ds += billservice.getBsDocShareAmt();
            ha += billservice.getBsHospitalAmt();
        }
        map.put("content", list);
        map.put("grossAmount", gr);
        map.put("discountAmount", da);
        map.put("DocShareAmount", ds);
        map.put("HospitalAmount", ha);
        return map;

    }

    @RequestMapping("update")
    public TbillBillService update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillBillService tbillBillService) {
        TenantContext.setCurrentTenant(tenantName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String currenttime = dateFormat.format(new Date()).toString();
        if (tbillBillService.getBsStatus() == 5) {
            tbillBillService.setVisitEndTime(currenttime);
        }
        return tbillBillServiceRepository.save(tbillBillService);
    }

    @RequestMapping("updatevisitcancel")
    public TbillBillService updatevisitcancel(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillBillService tbillBillService) {
        TenantContext.setCurrentTenant(tenantName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String currenttime = dateFormat.format(new Date()).toString();
        if (tbillBillService.getBsStatus() == 5) {
            tbillBillService.setVisitEndTime(currenttime);
        }
        TemrTimeline temrTimeline = temrTimelineRepository.findBybsId(tbillBillService.getBsId());
        if (temrTimeline != null) {
//            TrnAppointment trnAppointment = trnAppointmentRepository.getById(Long.parseLong(temrTimeline.getTimelineAppointmentId()));
            TrnAppointment trnAppointment = trnAppointmentRepository.findByAppointmentTimelineIdTimelineIdEquals(temrTimeline.getTimelineId());
            if (trnAppointment != null) {
                if (trnAppointment.getIsAppointmentReferral()) {
                    trnAppointment.setAppointmentIsCancelled(true);
                    trnAppointment.setIsReferralCancelled(true);
                    trnAppointmentRepository.save(trnAppointment);
                }
            }
        }
        return tbillBillServiceRepository.save(tbillBillService);
    }

    @RequestMapping("updatelist")
    public Map<String, String> updateList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TbillBillService> tbillBillServices) {
        TenantContext.setCurrentTenant(tenantName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String currenttime = dateFormat.format(new Date()).toString();
        for (TbillBillService tbillBillService : tbillBillServices) {
            tbillBillService.setVisitStartTime(currenttime);
        }
        tbillBillServiceRepository.saveAll(tbillBillServices);
        Boolean aBoolean = tbillBillServiceRepository
                .checkToCancelBill(tbillBillServices.get(0).getBsBillId().getBillId());
        if (aBoolean) {
            tbillBillRepository.updateBillForCancellation(tbillBillServices.get(0).getBsBillId().getBillId(), true);
        }
        respMap.put("success", "1");
        respMap.put("msg", "Record Updates Successfully !");
        return respMap;
    }
    // @RequestMapping("endvisit")
    // public TbillBillService endvisit(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillBillService
    // tbillBillService) {
    // SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
    // String currenttime = dateFormat.format(new Date()).toString();
    // tbillBillService.setVisitEndTime(currenttime);
    // return tbillBillServiceRepository.save(tbillBillService);
    // }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillBillService> list(@RequestHeader("X-tenantId") String tenantName,
                                           @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                           @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                           @RequestParam(value = "qString", required = false) String qString,
                                           @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                           @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillBillServiceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillBillServiceRepository
                    .findByBsBillIdBillNumberContainsOrBsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(
                            qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{bsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsId") Long bsId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillBillService tbillBillService = tbillBillServiceRepository.getById(bsId);
        if (tbillBillService != null) {
            tbillBillService.setDeleted(true);
            tbillBillServiceRepository.save(tbillBillService);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("gettokenbydepartment")
    public Iterable<TbillBillService> gettokenbydepartment(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "dept") String dept,
                                                           @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                           @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                           @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                           @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillServiceRepository
                .findAllByBsServiceIdServiceSgIdSgGroupIdGroupSdIdSdDepartmentIdDepartmentNameEquals(dept,
                        PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    // Author: Priyanka
    @GetMapping
    @RequestMapping("findPreviousPatient/{staffid}")
    public TbillBillService findPreviousPatient(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffid") Long staffid) {
        TenantContext.setCurrentTenant(tenantName);
        java.sql.Date todaysdate;
        LocalDate today = LocalDate.now();
        todaysdate = java.sql.Date.valueOf(today);
        return tbillBillServiceRepository
                .findByBsStaffIdStaffIdAndBsStatusAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(staffid, 1,
                        todaysdate);
    }

    // Author: Priyanka
    @GetMapping
    @RequestMapping("findnextpatient/{staffid}")
    public TbillBillService findnextpatient(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffid") Long staffid) {
        TenantContext.setCurrentTenant(tenantName);
        java.sql.Date todaysdate;
        LocalDate today = LocalDate.now();
        todaysdate = java.sql.Date.valueOf(today);
        return tbillBillServiceRepository
                .findFirst1ByBsStaffIdStaffIdAndBsStatusAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(staffid, 0,
                        todaysdate);
    }

    @GetMapping
    @RequestMapping("gettokendisplaybydepartment")
    public Iterable<TbillBillService> gettokendisplaybydepartment(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "dept") String dept,
                                                                  @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                                  @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                                  @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                                  @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        Date todaysdate;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
        Date today = cal.getTime();
        String date = sdf.format(today);
        // LocalDate today = LocalDate.now();
        // todaysdate = java.sql.Date.valueOf(today);
        return tbillBillServiceRepository.findAllByBsServiceIdServiceSgIdSgGroupIdGroupDepartmentIdDepartmentNameEquals(
                PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)),
                dept);
    }

    @GetMapping
    @RequestMapping("gettokendisplaybystaff")
    public Iterable<TbillBillService> gettokendisplaybydoctor(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "staffid") Long staffid,
                                                              @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                              @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                              @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                              @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        java.sql.Date todaysdate;
        LocalDate today = LocalDate.now();
        todaysdate = java.sql.Date.valueOf(today);
        return tbillBillServiceRepository.findAllByCreatedDateAndIsActiveTrueAndIsDeletedFalse(staffid, todaysdate);
    }

    @GetMapping
    @RequestMapping("getalltokendisplaybystaff")
    public Iterable<TbillBillService> getAlltokendisplaybystaff(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "staffid") Long staffid) {
        TenantContext.setCurrentTenant(tenantName);
        java.sql.Date todaysdate;
        LocalDate today = LocalDate.now();
        todaysdate = java.sql.Date.valueOf(today);
        return tbillBillServiceRepository.findAllByCreatedDateAndBsStatusAndIsActiveTrueAndIsDeletedFalse(staffid,
                todaysdate);
    }

    Boolean getTbillServiceRadiologyByDepartmentName(@RequestHeader("X-tenantId") String tenantName, List<TbillBillService> tbillBillServiceList, Boolean isPaid,
                                                     Boolean ipdBill) {
        TenantContext.setCurrentTenant(tenantName);
        List<TbillServiceRadiology> serviceRadiologyList = new ArrayList<>();
        List<TbillServiceGeneral> tbillServiceGenerallist = new ArrayList<>();
        List<TpathBs> tpathBList = new ArrayList<>();
        Boolean retrn = false;
        try {
            for (TbillBillService tmpBillService : tbillBillServiceList) {
//                System.out.println("" + tmpBillService.getBsStaffId().getIsMedicaldepartment());
//                System.out.println("" + tmpBillService.getBsStaffId().getStaffUserId().getUserFirstname() + " "
//                        + tmpBillService.getBsStaffId().getStaffUserId().getUserLastname());
                if (tmpBillService.getBsServiceId().getServiceIsRadiology()) {
                    TbillServiceRadiology tbillServiceRadiology = new TbillServiceRadiology();
                    tbillServiceRadiology.setBsrBsId(tmpBillService);
                    tbillServiceRadiology.setBsrIsPaid(isPaid);
                    tbillServiceRadiology.setBsrIpdBill(ipdBill);
                    serviceRadiologyList.add(tbillServiceRadiology);
                } else if (tmpBillService.getBsServiceId().getServiceIsLaboratory()) {
                    TpathBs tpathBs = new TpathBs();
                    tpathBs.setIsIPD(ipdBill);
                    tpathBs.setIsPerformed(true);
                    tpathBs.setPsPerformedByDate(new Date());
                    tpathBs.setPsBillId(tmpBillService.getBsBillId());
                    if (tmpBillService.getBsStaffId() != null) {
                        tpathBs.setIsPerformedBy(String.valueOf(tmpBillService.getBsStaffId().getStaffId()));
                        tpathBs.setIsPerformedByName(tmpBillService.getBsStaffId().getStaffUserId().getUserFirstname() + " "
                                + tmpBillService.getBsStaffId().getStaffUserId().getUserLastname());
                        tpathBs.setIsSampleAcceptedByName(tmpBillService.getBsStaffId().getStaffUserId().getUserFirstname()
                                + " " + tmpBillService.getBsStaffId().getStaffUserId().getUserLastname());
                        tpathBs.setIsLabOrderAcceptanceBy("" + tmpBillService.getBsStaffId().getStaffId());
                        tpathBs.setPsStaffName(
                                String.valueOf(tmpBillService.getBsStaffId().getStaffUserId().getUserFullname()));
                    }
                    TBillBill billobj = new TBillBill();
                    billobj = tbillBillRepository.getOne(tmpBillService.getBsBillId().getBillId());
                    System.out.println(billobj);
                    if (billobj != null) {
                        tpathBs.setIsPerformedByUnitId(String.valueOf(billobj.getTbillUnitId().getUnitId()));
                        tpathBs.setIsPerformedByUnitName(String.valueOf(billobj.getTbillUnitId().getUnitName()));
                    }
                    List<MpathTest> testObj;
                    testObj = mpathTestRepository.findByMbillServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(
                            tmpBillService.getBsServiceId().getServiceId());
                    tpathBs.setPsTestId(testObj.get(0));
                    System.out.println("Lab WorkOrder Generated For :" + tpathBs);
                    tpathBList.add(tpathBs);

                } else {
                    TbillServiceGeneral tbillServiceGeneral = new TbillServiceGeneral();
                    tbillServiceGeneral.setBsgBsId(tmpBillService);
                    tbillServiceGeneral.setBsgIsPaid(isPaid);
                    tbillServiceGenerallist.add(tbillServiceGeneral);
                }
                retrn = true;
            }
            if (tpathBList.size() > 0) {
                tpathBsRepository.saveAll(tpathBList);
            }
            if (serviceRadiologyList.size() > 0) {
                List<TbillServiceRadiology> list = tbillServiceRadiologyRepository.saveAll(serviceRadiologyList);
// code eadded for nmmc HMIS integration with PACS 07.11.2019 start
                for (int i = 0; i < list.size(); i++) {
                    TBillBill tBillBill = tbillBillRepository
                            .findByBillId(list.get(i).getBsrBsId().getBsBillId().getBillId());
//                    try {
//                        Connection con = null;
//                        PreparedStatement ps = null;
//
//                        String acceionNo = "" + list.get(i).getBsrId();
//                        String serviceName = "" + list.get(i).getBsrBsId().getBsServiceId().getServiceName();
//                        String admissionId = "" + tBillBill.getBillVisitId().getVisitId();
//                        String patientName = tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId()
//                                .getUserFirstname() + " "
//                                + tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getUserLastname();
//                        String patientId = "" + tBillBill.getBillVisitId().getVisitPatientId().getPatientId();
//                        String mrNO = "" + tBillBill.getBillVisitId().getVisitPatientId().getPatientMrNo();
//                        String birthDate = "";
//                        if (tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getUserDob() == null) {
//                            birthDate = "00000000";
//                        }else if(!tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getUserIsDobReal()){
//                            birthDate = tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getUserDob() + "0101";
//                        }  else {
//                            birthDate = tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getUserDob()
//                                    .replace("-", "");
//                        }
//                        System.out.println(birthDate);
//                        StringBuilder age = new StringBuilder();
//                        StringBuilder suffix = new StringBuilder();
//                        String sb = "";
//
//                        if (tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getUserAge() != null
//                                && !tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getUserAge()
//                                .equals("0")) {
//                            sb = tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getUserAge();
//                            suffix = new StringBuilder("Y");
//                        } else if (tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId()
//                                .getuserMonth() != null
//                                && !tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getuserMonth()
//                                .equals("0")) {
//                            sb = tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getuserMonth();
//                            suffix = new StringBuilder("M");
//                        } else if (tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId()
//                                .getUserDay() != null
//                                && !tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getUserDay()
//                                .equals("0")) {
//                            sb = tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId().getUserDay();
//                            suffix = new StringBuilder("D");
//                        }
//                        if (sb.length() == 1) {
//                            age.append("00" + sb + suffix.toString());
//                        } else if (sb.length() == 2) {
//                            age.append("0" + sb + suffix.toString());
//                        } else {
//                            age.append(sb + suffix.toString());
//                        }
//
//                        String genderName = tBillBill.getBillVisitId().getVisitPatientId().getPatientUserId()
//                                .getUserGenderId().getGenderName();
//                        String referBy = "";
//                        if (tBillBill.getBillVisitId().getReferBy() != null) {
//                            referBy = tBillBill.getBillVisitId().getReferBy().getReName();
//                        }
//                        String modality = "";
//
//                        if (serviceName.toLowerCase().contains("ct")) {
//                            modality = "CT";
//                        }
//                        if (serviceName.toLowerCase().contains("xray")) {
//                            modality = "CR";
//                        }
//                        if (serviceName.toLowerCase().contains("mri")) {
//                            modality = "MR";
//                        }
//
//                        try {
//
//                            Date date = new Date();
//                            String pattern = "yyyyMMdd";
//                            String timePattern = "hhmmss";
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(timePattern);
//                            StringBuffer startDate = new StringBuffer(simpleDateFormat.format(date));
//                            StringBuffer startTime = new StringBuffer(simpleDateFormat1.format(date));
//
//                            Class.forName("com.mysql.jdbc.Driver");
//                            con = DriverManager.getConnection("jdbc:mysql://localhost:3309/his_order", "root",
//                                    "");
//                            String sql = "INSERT INTO HIS_ORDER"
//                                    + "(Accession_Number,Modality,Institution_Name,Ref_Physician_Name,Patient_Name,Patient_ID,Patient_Birth_Date,Patient_Age,Patient_Sex,Patient_Weight,Requesting_Physician,Req_Proc_Desc,Admission_ID,Sch_Station_AE_Title,Sch_Station_Name,Sch_Proc_Step_Start_Date,Sch_Proc_Step_Start_Time,Sch_Perf_Physician_Name,Sch_Proc_Step_Desc,Sch_Proc_Step_ID,Sch_Proc_Step_Location,Req_Proc_ID,Reason_for_the_Req_Proc,Req_Proc_Priority,Order_Status,Error_Desc,IPD_Field1,IPD_Field2,IPD_Field3,IPD_Field4,IPD_Field5,IPD_Field6,IPD_Field7,IPD_Field8,IPD_Field9,IPD_Field10,IPD_Field11,IPD_Field12,IPD_Field13,IPD_Field14,IPD_Field15)"
//                                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//                            ps = con.prepareStatement(sql);
//                            ps.setString(1, acceionNo);
//                            ps.setString(2, modality);
//                            ps.setString(3, "NMC");
//                            ps.setString(4, referBy);
//                            ps.setString(5, patientName);
//                            ps.setString(6, patientId);
//                            ps.setString(7, birthDate);
//                            ps.setString(8, "");
//                            ps.setString(9, genderName);
//                            ps.setString(10, "000.000");
//                            ps.setString(11, "");
//                            ps.setString(12, serviceName);
//                            ps.setString(13, admissionId);
//                            ps.setString(14, modality);
//                            ps.setString(15, modality);
//                            ps.setString(16, startDate.toString());
//                            ps.setString(17, startTime.toString());
//                            ps.setString(18, "");
//                            ps.setString(19, serviceName);
//                            ps.setString(20, acceionNo);
//                            ps.setString(21, "");
//                            ps.setString(22, patientId);
//                            ps.setString(23, "");
//                            ps.setString(24, "");
//                            ps.setString(25, "1");
//                            ps.setString(26, "");
//                            ps.setString(27, "");
//                            ps.setString(28, "");
//                            ps.setString(29, "");
//                            ps.setString(30, "");
//                            ps.setString(31, "");
//                            ps.setString(32, "");
//                            ps.setString(33, "");
//                            ps.setString(34, "");
//                            ps.setString(35, "");
//                            ps.setString(36, "");
//                            ps.setString(37, "");
//                            ps.setString(38, "");
//                            ps.setString(39, "");
//                            ps.setString(40, "");
//                            ps.setString(41, "");
//                            ps.executeUpdate();
//                            ps.close();
//                            con.close();
//                        } catch (Exception e) {
//                            if (con != null) {
//                                con.close();
//                            }
//                            if (ps != null) {
//                                ps.close();
//                            }
//                            e.printStackTrace();
//                        }
//                    } catch (Exception e) {
//
//                    }
                }
// code eadded for nmmc HMIS integration with PACS 07.11.2019 END
            }
            if (tbillServiceGenerallist.size() > 0) {
                tbillServiceGeneralRepository.saveAll(tbillServiceGenerallist);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return retrn;
    }

    Boolean getTbillServiceRadiologyByDepartmentNameForImport(@RequestHeader("X-tenantId") String tenantName, List<TbillBillService> tbillBillServiceList, Boolean isPaid,
                                                              Boolean ipdBill) {
        TenantContext.setCurrentTenant(tenantName);
        List<TbillServiceRadiology> serviceRadiologyList = new ArrayList<>();
        List<TbillServiceGeneral> tbillServiceGenerallist = new ArrayList<>();
        List<TpathBs> tpathBList = new ArrayList<>();
        Boolean retrn = false;
        try {
            for (TbillBillService tmpBillService : tbillBillServiceList) {
//                System.out.println("" + tmpBillService.getBsStaffId().getIsMedicaldepartment());
//                System.out.println("" + tmpBillService.getBsStaffId().getStaffUserId().getUserFirstname() + " "
//                        + tmpBillService.getBsStaffId().getStaffUserId().getUserLastname());
                if (tmpBillService.getBsServiceId().getServiceIsRadiology()) {
                    TbillServiceRadiology tbillServiceRadiology = new TbillServiceRadiology();
                    tbillServiceRadiology.setBsrBsId(tmpBillService);
                    tbillServiceRadiology.setBsrIsPaid(isPaid);
                    tbillServiceRadiology.setBsrIpdBill(ipdBill);
                    serviceRadiologyList.add(tbillServiceRadiology);
                } else if (tmpBillService.getBsServiceId().getServiceIsLaboratory()) {
                    TpathBs tpathBs = new TpathBs();
                    tpathBs.setIsIPD(ipdBill);
                    tpathBs.setIsPerformed(true);
                    tpathBs.setPsPerformedByDate(new Date());
                    tpathBs.setPsBillId(tmpBillService.getBsBillId());
                    if (tmpBillService.getBsStaffId() != null) {
                        tpathBs.setIsPerformedBy(String.valueOf(tmpBillService.getBsStaffId().getStaffId()));
                        tpathBs.setIsPerformedByName(tmpBillService.getBsStaffId().getStaffUserId().getUserFirstname() + " "
                                + tmpBillService.getBsStaffId().getStaffUserId().getUserLastname());
                        tpathBs.setIsSampleAcceptedByName(tmpBillService.getBsStaffId().getStaffUserId().getUserFirstname()
                                + " " + tmpBillService.getBsStaffId().getStaffUserId().getUserLastname());
                        tpathBs.setIsLabOrderAcceptanceBy("" + tmpBillService.getBsStaffId().getStaffId());
                        tpathBs.setPsStaffName(
                                String.valueOf(tmpBillService.getBsStaffId().getStaffUserId().getUserFullname()));
                    }
                    TBillBill billobj = new TBillBill();
                    billobj = tbillBillRepository.getOne(tmpBillService.getBsBillId().getBillId());
                    System.out.println(billobj);
                    if (billobj != null) {
                        tpathBs.setIsPerformedByUnitId(String.valueOf(billobj.getTbillUnitId().getUnitId()));
                        tpathBs.setIsPerformedByUnitName(String.valueOf(billobj.getTbillUnitId().getUnitName()));
                    }
                    List<MpathTest> testObj;
                    testObj = mpathTestRepository.findByMbillServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(
                            tmpBillService.getBsServiceId().getServiceId());
                    tpathBs.setPsTestId(testObj.get(0));
                    System.out.println("Lab WorkOrder Generated For :" + tpathBs);
                    tpathBs.setIsPerformed(true);
                    tpathBs.setIsSampleAccepted(true);
                    tpathBs.setIsSampleCollected(true);
                    tpathBs.setIsLabOrderAcceptance(true);
                    tpathBs.setIsResultEntry(true);
                    tpathBs.setIsIPD(ipdBill);
                    tpathBs.setIsLabOrderAcceptanceBy(String.valueOf(tmpBillService.getBsStaffId().getStaffId()));
                    tpathBs.setIsPerformedBy(String.valueOf(tmpBillService.getBsStaffId().getStaffId()));
                    tpathBs.setIsSampleAcceptedBy(String.valueOf(tmpBillService.getBsStaffId().getStaffId()));
                    tpathBs.setIsSampleCollectedBy(String.valueOf(tmpBillService.getBsStaffId().getStaffId()));
                    tpathBs.setIsResultEntryDoneBy(String.valueOf(tmpBillService.getBsStaffId().getStaffId()));
                    tpathBs.setIsResultEntryDoneByName(String.valueOf(tmpBillService.getBsStaffId().getStaffUserId().getUserFullname()));
                    tpathBs.setIsLabOrderAcceptanceByName(String.valueOf(tmpBillService.getBsStaffId().getStaffUserId().getUserFullname()));
                    tpathBs.setIsPerformedByName(String.valueOf(tmpBillService.getBsStaffId().getStaffUserId().getUserFullname()));
                    tpathBs.setIsSampleAcceptedByName(String.valueOf(tmpBillService.getBsStaffId().getStaffUserId().getUserFullname()));
                    tpathBs.setIsSampleCollectedByName(String.valueOf(tmpBillService.getBsStaffId().getStaffUserId().getUserFullname()));
                    tpathBList.add(tpathBs);

                } else {
                    TbillServiceGeneral tbillServiceGeneral = new TbillServiceGeneral();
                    tbillServiceGeneral.setBsgBsId(tmpBillService);
                    tbillServiceGeneral.setBsgIsPaid(isPaid);
                    tbillServiceGenerallist.add(tbillServiceGeneral);
                }
                retrn = true;
            }
            if (tpathBList.size() > 0) {
                List<TpathBs> list = tpathBsRepository.saveAll(tpathBList);
                for (int i = 0; i < list.size(); i++) {
                    MpathTestResult mpathTestResult = new MpathTestResult();
                    mpathTestResult.setTrPsId(list.get(i));
                    mpathTestResult.setCreatedDate(new Date());
                    mpathTestResult.setCreatedBy(String.valueOf(list.get(i).getIsPerformedBy()));
                    mpathTestResult.setCreatedByName(String.valueOf(list.get(i).getIsPerformedByName()));
                    mpathTestResult.setLastModifiedDate(new Date());
                    mpathTestResult.setLastModifiedBy(String.valueOf(list.get(i).getIsPerformedBy()));
                    mpathTestResult.setLastModifiedByName(String.valueOf(list.get(i).getIsPerformedByName()));
                    mpathTestResult = mpathTestResultRepository.save(mpathTestResult);
                    if (list.get(i).getPsTestId().getIsTemplateTest()) {
                        MpathResult mpathResult = new MpathResult();
                        mpathResult.setCreatedDate(new Date());
                        mpathResult.setCreatedBy(String.valueOf(list.get(i).getIsPerformedBy()));
                        mpathResult.setLastModifiedDate(new Date());
                        mpathResult.setLastModifiedBy(String.valueOf(list.get(i).getIsPerformedBy()));
                        mpathResult.setLastModifiedByName(String.valueOf(list.get(i).getIsPerformedByName()));
                        mpathResult.setResultValue("ABC");
                        mpathResult.setResultTestTemplateId(list.get(i).getPsTestId().getTestTemplateId());
                        mpathResult.setResultTestResultId(mpathTestResult);
                        mpathResultRepository.save(mpathResult);

                    } else {
                        List<MpathResult> mpathResultList = new ArrayList<>();
                        List<RpathTestParameter> parameterList = rpathTestParameterRepository.findByTpTestIdTestIdLikeAndIsActiveTrueAndIsDeletedFalseOrderByTpParameterSequenceNumberAsc(list.get(i).getPsTestId().getTestId());
                        for (int j = 0; j < parameterList.size(); j++) {
                            MpathResult mpathResult = new MpathResult();
                            mpathResult.setResultTestParameterId(parameterList.get(j));
                            mpathResult.setResultTestResultId(mpathTestResult);
                            mpathResult.setCreatedDate(new Date());
                            mpathResult.setCreatedBy(String.valueOf(list.get(i).getIsPerformedBy()));
                            mpathResult.setLastModifiedDate(new Date());
                            mpathResult.setLastModifiedBy(String.valueOf(list.get(i).getIsPerformedBy()));
                            mpathResult.setLastModifiedByName(String.valueOf(list.get(i).getIsPerformedByName()));
                            if (parameterList.get(j).getTpParameterId().getIsTextValue()) {
                                List<RpathParameterTextLink> textValueList = rpathParameterTextLinkRepository.findByPtlParameterIdParameterIdEqualsAndIsActiveTrueAndIsDeletedFalse(parameterList.get(j).getTpParameterId().getParameterId());
                                if (textValueList.size() > 0) {
                                    mpathResult.setResultValue(textValueList.get(0).getPtlPtId().getPtName());
                                }
                            } else {
                                MpathParameterRangeFilter mpathParameterRangeFilter = new MpathParameterRangeFilter();
                                mpathParameterRangeFilter.setPrParameterId(parameterList.get(j).getTpParameterId().getParameterId());
                                if (list.get(i).getPsBillId().getIpdBill()) {
                                    mpathParameterRangeFilter.setPrGenderId(list.get(i).getPsBillId().getBillAdmissionId().getAdmissionPatientId().getPatientUserId().getUserGenderId().getGenderId());
                                    mpathParameterRangeFilter.setPrAge(Long.parseLong(list.get(i).getPsBillId().getBillAdmissionId().getAdmissionPatientId().getPatientUserId().getUserAge()));

                                } else {
                                    mpathParameterRangeFilter.setPrGenderId(list.get(i).getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserGenderId().getGenderId());
                                    mpathParameterRangeFilter.setPrAge(Long.parseLong(list.get(i).getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserAge()));
                                }
                                List<MpathParameterRange> parameterRangeList = mpathParameterRangeController.paramByAgeandGender(tenantName, mpathParameterRangeFilter);
                                for (int k = 0; k < parameterRangeList.size(); k++) {
                                    if (parameterRangeList.get(k).getPrIsNormalRange()) {
                                        mpathResult.setResultPrId(parameterRangeList.get(0));
                                    }
                                }
                                if (mpathResult.getResultPrId() != null) {
                                    Random random = new Random();
                                    if (mpathResult.getResultPrId().getPrLowerValue() == 0 && mpathResult.getResultPrId().getPrUpperValue() == 0) {
                                        mpathResult.setResultValue(mpathResult.getResultPrId().getPrLowerValue() + "");
                                    } else {
                                        int value = random.nextInt((int) mpathResult.getResultPrId().getPrUpperValue() - (int) mpathResult.getResultPrId().getPrLowerValue()) + (int) mpathResult.getResultPrId().getPrLowerValue();
                                        mpathResult.setResultValue(value + "");
                                    }
                                } else {
                                    Random random = new Random();
                                    mpathResult.setResultValue(random.nextInt(100) + "");

                                }

                            }
                            mpathResultList.add(mpathResult);
                        }
                        mpathResultRepository.saveAll(mpathResultList);

                    }
                }
            }
            if (serviceRadiologyList.size() > 0) {
                List<TbillServiceRadiology> list = tbillServiceRadiologyRepository.saveAll(serviceRadiologyList);
// code eadded for nmmc HMIS integration with PACS 07.11.2019 start
                for (int i = 0; i < list.size(); i++) {
                    TBillBill tBillBill = tbillBillRepository
                            .findByBillId(list.get(i).getBsrBsId().getBsBillId().getBillId());

                }
// code eadded for nmmc HMIS integration with PACS 07.11.2019 END
            }
            if (tbillServiceGenerallist.size() > 0) {
                tbillServiceGeneralRepository.saveAll(tbillServiceGenerallist);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return retrn;
    }

    Boolean getTbillServiceRadiologyByDepartmentName(@RequestHeader("X-tenantId") String tenantName, List<TbillBillService> tbillBillServiceList, Boolean isPaid,
                                                     Boolean ipdBill, Long ipd_id) {
        TenantContext.setCurrentTenant(tenantName);
        List<TbillServiceRadiology> serviceRadiologyList = new ArrayList<>();
        List<TbillServiceGeneral> tbillServiceGenerallist = new ArrayList<>();
        List<TpathBs> tpathBList = new ArrayList<>();
        Boolean retrn = false;
        try {
            for (TbillBillService tmpBillService : tbillBillServiceList) {
                System.out.println("regex :" + tmpBillService.getBsServiceId().getServiceGroupId()
                        .getGroupDepartmentId().getDepartmentName());
//                tmpBillService.getBsServiceId().getServiceGroupId().getGroupDepartmentId().getDepartmentName().matches("(?i:.*(RAD).*)")
                if (tmpBillService.getBsServiceId().getServiceIsRadiology()) {
                    TbillServiceRadiology tbillServiceRad = tbillServiceRadiologyRepository.findByBsrCsIdCsIdAndIsActiveTrueAndIsDeletedFalse(tmpBillService.getCsId());
                    if (tbillServiceRad == null) {
                        TbillServiceRadiology tbillServiceRadiology = new TbillServiceRadiology();
                        tbillServiceRadiology.setBsrBsId(tmpBillService);
                        tbillServiceRadiology.setBsrIsPaid(isPaid);
                        tbillServiceRadiology.setBsrIpdBill(ipdBill);
                        serviceRadiologyList.add(tbillServiceRadiology);
                    } else {
                        tbillServiceRad.setBsrBsId(tmpBillService);
                        tbillServiceRad.setBsrIsPaid(isPaid);
                        tbillServiceRad.setBsrIpdBill(ipdBill);
                        serviceRadiologyList.add(tbillServiceRad);
                    }

                }
//                (tmpBillService.getBsServiceId().getServiceGroupId().getGroupDepartmentId().getDepartmentName().matches("(?i:.*(LAB).*)"))|| (tmpBillService.getBsServiceId().getServiceGroupId().getGroupDepartmentId().getDepartmentName().matches("(?i:.*(PATH).*)"))
                else if (tmpBillService.getBsServiceId().getServiceIsLaboratory()) {
                    System.out.println("hello vj :" + tmpBillService);
                    if (tmpBillService.getCsId() > 0) {
                        TpathBs tpathBsTemp = tpathBsRepository.findByIsActiveTrueAndIsDeletedFalseAndMbillIPDChargeIpdchargeIdEqualsAndPsCsIdCsIdEquals(ipd_id, tmpBillService.getCsId());
                        if (tpathBsTemp == null) {
                            TpathBs tpathBs = new TpathBs();
                            tpathBs.setIsIPD(true);
                            tpathBs.setIsPerformed(true);
                            tpathBs.setPsPerformedByDate(new Date());
                            tpathBs.setPsBillId(tmpBillService.getBsBillId());
                            MbillIPDCharge mbillIPDCharge = mbillIPDBillCharge
                                    .findAllByIpdchargeIdAndIsActiveTrueAndIsDeletedFalse(ipd_id);
                            tpathBs.setMbillIPDCharge(mbillIPDCharge);
                            // MbillIPDBillCharges miCharges =
                            // mbillIPDBillCharges.findByIpdbcId(ipd_id);
                            if (tmpBillService.getBsStaffId() != null) {
                                tpathBs.setIsPerformedBy(String.valueOf(tmpBillService.getBsStaffId().getStaffId()));
                                tpathBs.setIsPerformedByName(tmpBillService.getBsStaffId().getStaffUserId().getUserFirstname() + " "
                                        + tmpBillService.getBsStaffId().getStaffUserId().getUserLastname());
                                tpathBs.setIsSampleAcceptedByName(tmpBillService.getBsStaffId().getStaffUserId().getUserFirstname()
                                        + " " + tmpBillService.getBsStaffId().getStaffUserId().getUserLastname());
                                tpathBs.setIsLabOrderAcceptanceBy("" + tmpBillService.getBsStaffId().getStaffId());
                                MstStaff staffObj = new MstStaff();
                                staffObj = mstStaffRepository.getOne(tmpBillService.getBsStaffId().getStaffId());
                                tpathBs.setIsPerformedByName(String.valueOf(staffObj.getStaffUserId().getUserFullname()));
                                tpathBs.setPsStaffName(String.valueOf(staffObj.getStaffUserId().getUserFullname()));
                            }
                            TBillBill billobj = new TBillBill();
                            billobj = tbillBillRepository.getOne(tmpBillService.getBsBillId().getBillId());
                            System.out.println(billobj);
                            if (billobj != null) {
                                tpathBs.setIsPerformedByUnitId(String.valueOf(billobj.getTbillUnitId().getUnitId()));
                                tpathBs.setIsPerformedByUnitName(String.valueOf(billobj.getTbillUnitId().getUnitName()));
                            }
                            List<MpathTest> testObj;
                            testObj = mpathTestRepository.findByMbillServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(
                                    tmpBillService.getBsServiceId().getServiceId());
                            tpathBs.setPsTestId(testObj.get(0));
                            System.out.println("Lab WorkOrder Generated For BY Chage :" + tpathBs);
                            tpathBList.add(tpathBs);
                        } else {
                            tpathBsTemp.setPsBillId(tmpBillService.getBsBillId());
                            tpathBList.add(tpathBsTemp);
                        }
                    }

                } else {
                    TbillServiceGeneral tbillServiceGeneral = new TbillServiceGeneral();
                    tbillServiceGeneral.setBsgBsId(tmpBillService);
                    tbillServiceGeneral.setBsgIsPaid(isPaid);
                    tbillServiceGenerallist.add(tbillServiceGeneral);
                }
                retrn = true;
            }
            if (tpathBList.size() > 0) {
                tpathBsRepository.saveAll(tpathBList);
            }
            if (serviceRadiologyList.size() > 0) {
                tbillServiceRadiologyRepository.saveAll(serviceRadiologyList);
            }
            if (tbillServiceGenerallist.size() > 0) {
                tbillServiceGeneralRepository.saveAll(tbillServiceGenerallist);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return retrn;
    }

    void getCabinForService(@RequestHeader("X-tenantId") String tenantName, TbillBillService newTbillBillService) {
        TenantContext.setCurrentTenant(tenantName);
        // List<TrnServiceCabinSchedule> finalerviceCabinScheduleList = new
        // ArrayList<TrnServiceCabinSchedule>();
        try {
            System.out.println("-----------getCabinForService:--------- ");
            int csdid = 0;
            Long staff;
            TrnServiceCabinSchedule trnServiceCabinSchedule = new TrnServiceCabinSchedule();
            // get staffcabinschedule id
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String dateString = dateFormat.format(new Date()).toString();
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
            // full name form of the day
            System.out.println("----------day-----" + day);
            MstDay mstday = mstDayRepository.findByDayNameContainsAndIsActiveTrueAndIsDeletedFalse(day);
            Long id = mstday.getDayId();
            TrnDoctorScheduleDetail foundStaffCabinSchedule = null;
            staff = newTbillBillService.getBsStaffId().getStaffId();
            System.out.println("-------staff-----------" + staff);
            System.out.println("--------dateString-----------" + dateString);
            System.out.println("--------dayid-----------" + id);
            try {
                // foundStaffCabinSchedule =
                // trnDoctorScheduleDetailRepository.findByIsActiveTrueAndIsDeletedFalse(staff,
                // dateString, id);
                String query = "SELECT  * from trn_doctor_schedule_detail  where  dsd_day_id = " + id
                        + " and dsd_staff_id = " + staff + "  and is_active = 1 and is_deleted = 0  and  '" + dateString
                        + "'  between STR_TO_DATE( dsd_start_time ,'%h:%i %p') and STR_TO_DATE( dsd_end_time , '%h:%i %p')  ";
                System.out.println("----------Query-----");
                System.out.println(query);
                foundStaffCabinSchedule = (TrnDoctorScheduleDetail) entityManager
                        .createNativeQuery(query, TrnDoctorScheduleDetail.class).getSingleResult();
                System.out.println("----------cabin id-----" + foundStaffCabinSchedule.getDsdCabinId().getCabinName());
            } catch (Exception exception) {
            }
            trnServiceCabinSchedule.setScsBsId(newTbillBillService);
            trnServiceCabinSchedule.setScsDate(new Date());
            trnServiceCabinSchedule.setScsBsId(newTbillBillService);
            // set staffcabinschedule id
            if (foundStaffCabinSchedule != null) {
                System.out.println("----------cabin id-----" + foundStaffCabinSchedule.getDsdCabinId().getCabinName());
                trnServiceCabinSchedule.setScsCabinId(foundStaffCabinSchedule);

            } else {
            }
            trnServiceCabinScheduleRepository.save(trnServiceCabinSchedule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("listbytbrId/{tbrId}")
    public List<TbillBillService> getListOfItemById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tbrId") Long tbrId) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillServiceRepository.findAllByBsTbrIdTbrIdAndIsActiveTrueAndIsDeletedFalse(tbrId);
    }
    // @RequestMapping("editVisitTimeAndCallPatient")
    // public TbillBillService editVisitTimeAndCallPatient(@RequestHeader("X-tenantId") String tenantName, @RequestBody
    // TbillBillService tbillBillService) {
    // SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
    // String currenttime = dateFormat.format(new Date()).toString();
    // tbillBillService.setVisitStartTime(currenttime);
    // return tbillBillServiceRepository.save(tbillBillService);
    // }

    @RequestMapping("editVisitTimeAndCallPatient/{status}/{billserviceid}")
    public TbillBillService editVisitTimeAndCallPatient(@RequestHeader("X-tenantId") String tenantName, @PathVariable("status") int status,
                                                        @PathVariable("billserviceid") Long billserviceid) {
        TenantContext.setCurrentTenant(tenantName);
        TbillBillService tbillBillService = new TbillBillService();
        tbillBillService = this.tbillBillServiceRepository.getById(billserviceid);
        TbillBillService tbillBillServiceNew = new TbillBillService();
        if (tbillBillService != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
            String currenttime = dateFormat.format(new Date()).toString();
            tbillBillService.setBsStatus(status);
            tbillBillService.setVisitStartTime(currenttime);
            tbillBillServiceNew = tbillBillServiceRepository.save(tbillBillService);
        }
        return tbillBillServiceNew;
    }

    @RequestMapping("endvisit/{status}/{billserviceid}")
    public TbillBillService endvisit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("status") int status,
                                     @PathVariable("billserviceid") Long billserviceid) {
        TenantContext.setCurrentTenant(tenantName);
        TbillBillService tbillBillService = new TbillBillService();
        tbillBillService = this.tbillBillServiceRepository.getById(billserviceid);
        TbillBillService tbillBillServiceNew = new TbillBillService();
        if (tbillBillService != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
            String currenttime = dateFormat.format(new Date()).toString();
            tbillBillService.setBsStatus(status);
            tbillBillService.setVisitEndTime(currenttime);
            TemrTimeline temrTimeline = temrTimelineRepository.findTopByTimelineVisitIdVisitId(tbillBillService.getBsBillId().getBillVisitId().getVisitId());
            if (temrTimeline != null) {
                temrTimeline.setEMRFinal(true);
                temrTimeline.setTimelineFinalizedDate(new Date());
                temrTimelineRepository.save(temrTimeline);
            }
            tbillBillServiceNew = tbillBillServiceRepository.save(tbillBillService);
        }
        return tbillBillServiceNew;
    }

    // Author: NST
    @GetMapping
    @RequestMapping("listbyBillIdPage")
    public Iterable<TbillBillService> listByBillId(@RequestHeader("X-tenantId") String tenantName,
                                                   @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                   @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                   @RequestParam(value = "qString", required = false) String qString,
                                                   @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                   @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillBillServiceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillBillServiceRepository.findByBsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(
                    Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    // emrgencyservice added
    @RequestMapping("createIndivisualForEmergency")
    public Map<String, String> createIndivisualForEmergency(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillBillService tbillBillServices) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("in create ");
        List<TbillBillService> finalBillServiceList = new ArrayList<TbillBillService>();
        java.sql.Date todaysdate;
        LocalDate today = LocalDate.now();
        todaysdate = java.sql.Date.valueOf(today);
        tbillBillServices.setBsDate(new Date());
        TbillBillService newBillServiceList = tbillBillServiceRepository.save(tbillBillServices);
        TBillBill tbill = new TBillBill();
        tbill = tbillBillRepository
                .findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(tbillBillServices.getBsBillId().getBillId());
        System.out.println("----------------------Timeline------------------------------ ");
        System.out.println(tbill.getBillVisitId().getVisitId());
        System.out.println(tbill.getBillVisitId().getVisitPatientId().getPatientId());
        System.out.println("----------------------Timeline------------------------------ ");
        TemrTimeline temrTimeline = new TemrTimeline();
        temrTimeline.setTimelineVisitId(tbill.getBillVisitId());
        temrTimeline.setTimelinePatientId(tbill.getBillVisitId().getVisitPatientId());
        temrTimeline.setTimelineTime(new Date());
        temrTimeline.setTimelineDate(new Date());
        temrTimeline.setEMRFinal(false);
        temrTimeline.setTimelineRegistrationSource(2);
        temrTimeline.setTimelineServiceId(newBillServiceList);
        temrTimeline.setTimelineStaffId(tbillBillServices.getBsStaffId());
        temrTimeline = temrTimelineRepository.save(temrTimeline);
        finalBillServiceList.add(newBillServiceList);
        if (finalBillServiceList.size() != 0) {
            getTbillServiceRadiologyByDepartmentName(tenantName, finalBillServiceList,
                    (finalBillServiceList.get(0).getBsBillId().getBillOutstanding() > 0 ? true : false),
                    finalBillServiceList.get(0).getBsBillId().getIpdBill());
            // getCabinForService(finalBillServiceList);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @GetMapping("getIPDsevrvicelistbyadmission")
    List<TbillBillService> iPDservicelistbyadmission(@RequestHeader("X-tenantId") String tenantName,
                                                     @RequestParam(value = "admissionid", defaultValue = "", required = false) long qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        List<TbillBillService> tbillbillService = new ArrayList<>();
        try {
            tBillBills = tbillBillRepository
                    .findByBillAdmissionIdAdmissionIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
                            qString);
            if (tBillBills.size() > 0) {
                for (TBillBill tbillbill : tBillBills) {
                    List<TbillBillService> services = tbillBillServiceRepository
                            .findByBsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(tbillbill.getBillId());
                    if (services.size() > 0) {
                        tbillbillService.addAll(services);
                    }
                }
            }
            return tbillbillService;
        } catch (Exception e) {
            return tbillbillService;
        }
    }
    // http://localhost:8090/tbill_service/getIPDsevrvicelistbyadmission/?admissionid=2

    @RequestMapping("SetNewTokenNumber")
    public Map<String, String> SetNewTokenNumber(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TBillBillServiceDto> tBillBillServiceDto) {
        TenantContext.setCurrentTenant(tenantName);
        // JSONArray jsonArray = new JSONArray(listServices);
        for (int i = 0; i < tBillBillServiceDto.size(); i++) {
            for (int j = i + 1; j < tBillBillServiceDto.size(); j++) {
                TBillBillServiceDto temp;
                if (tBillBillServiceDto.get(i).getBsId() > tBillBillServiceDto.get(j).getBsId()) {
                    temp = tBillBillServiceDto.get(i);
                    tBillBillServiceDto.set(i, tBillBillServiceDto.get(j));
                    tBillBillServiceDto.set(j, temp);
                }
            }
        }
        System.out.println("----------------Sorted");
        for (int i = 0; i < tBillBillServiceDto.size(); i++) {
            System.out.println(i);
            System.out.println(tBillBillServiceDto.get(i).getBsId());
            System.out.println(tBillBillServiceDto.get(i).getServiceIsTokenDisplay());
        }
        for (int i = 0; i < tBillBillServiceDto.size(); i++) {
            Long bsId = tBillBillServiceDto.get(i).getBsId();
            Boolean serviceIsTokenDisplay = tBillBillServiceDto.get(i).getServiceIsTokenDisplay();
            TbillBillService tbillBillService;
            if (serviceIsTokenDisplay == true) {
                System.out.println(
                        "=-----------------token cretaed for " + tBillBillServiceDto.get(i).getServiceIsTokenDisplay());
                tbillBillService = new TbillBillService();
                tbillBillService = tbillBillServiceRepository.getById(bsId);
                String Prefix = "";
                String newTokenNo = "";
                String lastTokenNo = "";
                TbillBillService serviceList;
                java.sql.Date todaysdate;
                LocalDate today = LocalDate.now();
                todaysdate = java.sql.Date.valueOf(today);
                try {
                    serviceList = new TbillBillService();
                    serviceList = tbillBillServiceRepository
                            .findOneByBsDateAndIsActiveTrueAndIsDeletedFalse(todaysdate);
                    lastTokenNo = serviceList.getBsTokenNumber();
                } catch (Exception exception) {
                    lastTokenNo = "";
                }
                if (lastTokenNo == null) {
                    lastTokenNo = "";
                }
                if (lastTokenNo.equals("")) {
                    newTokenNo = Prefix + "001";
                } else {
                    String number = lastTokenNo.substring(1);
                    int newNumber = Integer.parseInt(number);
                    String incNumber = String.format("%03d", newNumber + 1);
                    newTokenNo = incNumber;
                }
                tbillBillService.setBsTokenNumber(newTokenNo);
                System.out.println("--------------Cabin creation before-------------------------------------------");
                getCabinForService(tenantName, tbillBillService);
                System.out.println("--------------Cabin creation After-------------------------------------------");
                tbillBillServiceRepository.save(tbillBillService);

            }
        }
        respMap.put("success", "1");
        return respMap;
    }

    @RequestMapping("allservices/{admissionId}")
    public List<TbillBillService> Servicelistbyadmission(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillServiceRepository
                .findByBsBillIdBillAdmissionIdAdmissionIdEqualsAndIsActiveTrueAndIsDeletedFalseAndBsCancelFalseOrderByBsDateAsc(admissionId);
    }

    @SuppressWarnings("unlikely-arg-type")
    @GetMapping
    @RequestMapping("allservices/consolation/{admissionId}")
    public List<TbillBillService> getConsolation(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TbillBillService> allServicesList = tbillBillServiceRepository
                .findByBsBillIdBillAdmissionIdAdmissionIdEqualsAndIsActiveTrueAndIsDeletedFalseAndBsCancelFalseOrderByBsDateAsc(
                        admissionId);
        Map<String, TbillBillService> tbillAllservicesMap = new HashMap<String, TbillBillService>();
        for (TbillBillService tbillBillService : allServicesList) {
            String serviceCode = tbillBillService.getBsServiceId().getServiceCode();
            double serviceBaseRate = tbillBillService.getBsServiceId().getServiceBaseRate();
            String key = serviceCode + "" + serviceBaseRate;
            if ((tbillAllservicesMap != null) && (tbillAllservicesMap.containsKey(key))) {
                if (tbillAllservicesMap.containsKey(key)) {
                    TbillBillService mapValueByKey = tbillAllservicesMap.get(key);
                    tbillBillService.setBsQuantity(mapValueByKey.getBsQuantity() + tbillBillService.getBsQuantity());
                    tbillBillService.setBsBaseRate(mapValueByKey.getBsServiceId().getServiceBaseRate()
                            + tbillBillService.getBsServiceId().getServiceBaseRate());
                    // tbillBillService.setBsGrossRate((mapValueByKey.getBsQuantity() +
                    // tbillBillService.getBsQuantity())
                    // * tbillBillService.getBsServiceId().getServiceBaseRate());
                    tbillBillService.setBsGrossRate(tbillBillService.getBsQuantity()
                            * tbillBillService.getBsServiceId().getServiceBaseRate());
                    tbillAllservicesMap.put(key, tbillBillService);

                }

            } else {
                tbillAllservicesMap.put(key, tbillBillService);
            }

        }
        List<TbillBillService> newListTbillAllservices = new ArrayList<TbillBillService>(tbillAllservicesMap.values());
        return newListTbillAllservices;
    }

    @GetMapping
    @RequestMapping("servicelist/{admissionId}")
    public List<TbillServiceDTO> listservice(@RequestHeader("X-tenantId") String tenantName, @PathVariable(value = "admissionId") long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select t.bs_service_id,sum(t.bs_quantity),t.bs_base_rate,sum(t.bs_gross_rate),sum(t.bs_concession_percentage),sum(t.bs_discount_amount),sum(t.bs_tariffcprate) from tbill_bill_service t where t.bs_bill_id in (select bill_id from tbill_bill where bill_admission_id = "
                + admissionId + ") group by t.bs_service_id";
        List<Object[]> objectlist = entityManager.createNativeQuery(query).getResultList();
        List<TbillServiceDTO> servicelist = new ArrayList<>();
        for (int i = 0; i <= objectlist.size() - 1; i++) {
            TbillBillService service = tbillBillServiceRepository
                    .getOne(Long.parseLong(String.valueOf(objectlist.get(i)[0])));
            TbillServiceDTO obj = new TbillServiceDTO(service.getBsServiceId());
            obj.setQty(Double.parseDouble(String.valueOf(objectlist.get(i)[1])));
            obj.setBaserate(Double.parseDouble(String.valueOf(objectlist.get(i)[2])));
            obj.setGrossrate(Double.parseDouble(String.valueOf(objectlist.get(i)[3])));
            obj.setConcessionper(Double.parseDouble(String.valueOf(objectlist.get(i)[4])));
            obj.setConcessionamt(Double.parseDouble(String.valueOf(objectlist.get(i)[5])));
            obj.setTariffcprate(Double.parseDouble(String.valueOf(objectlist.get(i)[6])));
            servicelist.add(obj);
        }
        return servicelist;
    }

    @RequestMapping("createBilleServiceFromAppointment/{appointmentId}/{visit_id}")
    public Map<String, String> createIndivisual(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillBillService tbillBillServices, @PathVariable("appointmentId") Long appointmentId, @PathVariable("visit_id") Long visit_id) {
        TenantContext.setCurrentTenant(tenantName);
        Long timeLineId = 0L;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<TbillBillService> finalBillServiceList = new ArrayList<TbillBillService>();
        TbillBillService newBillServiceList;
        tbillBillServices.setBsDate(new Date());
        newBillServiceList = new TbillBillService();
        newBillServiceList = tbillBillServiceRepository.save(tbillBillServices);
        if (newBillServiceList.getBsId() != 0) {
            TBillBill tbill = new TBillBill();
            tbill = tbillBillRepository
                    .findAllByBillIdAndIsActiveTrueAndIsDeletedFalse(tbillBillServices.getBsBillId().getBillId());
            TemrTimeline temrTimeline = new TemrTimeline();
            temrTimeline.setTimelineVisitId(tbill.getBillVisitId());
            temrTimeline.setTimelinePatientId(tbill.getBillVisitId().getVisitPatientId());
            temrTimeline.setTimelineTime(new Date());
            temrTimeline.setTimelineDate(new Date());
            temrTimeline.setEMRFinal(false);
            temrTimeline.setTimelineServiceId(newBillServiceList);
            temrTimeline.setTimelineStaffId(tbillBillServices.getBsStaffId());
            temrTimeline = temrTimelineRepository.save(temrTimeline);
            timeLineId = temrTimeline.getTimelineId();
            trnAppointmentRepository.updateTrnAppointMent("" + visit_id, appointmentId, temrTimeline.getTimelineId());
            finalBillServiceList.add(newBillServiceList);
        }
//        TrnAppointment trnAppointment = trnAppointmentRepository.getById(appointmentId);
        if (tbillBillServices.getBsStaffId() != null) {
//            MstStaff mstStaff = mstStaffRepository.getById(tbillBillServices.getBsStaffId().getStaffId());
//			String msg =  trnAppointment.getAppointmentUserId().getUserFullname() + ". Appointment is confirmed on "+ formatter.format(trnAppointment.getAppointmentDate()) +" - " + trnAppointment.getAppointmentSlot() +" (Date-Time) with Dr " + mstStaff.getStaffUserId().getUserFirstname() + " " + mstStaff.getStaffUserId().getUserLastname() ;
//            String msg =  "Your Appointment has been confirmed with Dr. "+ mstStaff.getStaffUserId().getUserFirstname() + " " + mstStaff.getStaffUserId().getUserLastname() +" at "+ trnAppointment.getAppointmentSlot() +" on " + formatter.format(trnAppointment.getAppointmentDate()) +", please complete the payment using the link \"XXXXXXXXX\" .";
           /* try{
                System.out.println("mobile : " + trnAppointment.getAppointmentUserId().getUserMobile());
                System.out.println("email : " + trnAppointment.getAppointmentUserId().getUserEmail());
                if (Propertyconfig.getSmsApi()) {
                    sendsms.sendPasswordSMS(trnAppointment.getAppointmentUserId().getUserMobile(), msg.replaceAll(" ","%20"));
                    sendsms.sendPasswordSMS("7975516864",  msg.replaceAll(" ","%20"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }*/
          /*  try{
                if (Propertyconfig.getEmailApi()) {
                    Emailsend emailsend = new Emailsend();
                    emailsend.sendMAil(trnAppointment.getAppointmentUserId().getUserEmail(),msg);
                    emailsend.sendMAil("support@mycareever.com",msg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }*/
        }
        if (finalBillServiceList.size() != 0) {
            getTbillServiceRadiologyByDepartmentName(tenantName, finalBillServiceList,
                    (finalBillServiceList.get(0).getBsBillId().getBillOutstanding() > 0 ? true : false),
                    finalBillServiceList.get(0).getBsBillId().getIpdBill());
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("bsId", Long.toString(newBillServiceList.getBsId()));
            respMap.put("timeLineId", "" + timeLineId);
            respMap.put("serviceIsTokenDisplay",
                    String.valueOf(newBillServiceList.getBsServiceId().getServiceIsTokenDisplay()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

}
