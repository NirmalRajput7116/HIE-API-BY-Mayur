package com.cellbeans.hspa.mbillipdcharges;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mpathtest.MpathTest;
import com.cellbeans.hspa.mpathtest.MpathTestRepository;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import com.cellbeans.hspa.tbillbill.TbillBillRepository;
import com.cellbeans.hspa.tbillservicegeneral.TbillServiceGeneral;
import com.cellbeans.hspa.tbillserviceradiology.TbillServiceRadiology;
import com.cellbeans.hspa.tbillserviceradiology.TbillServiceRadiologyRepository;
import com.cellbeans.hspa.tpathbs.TpathBs;
import com.cellbeans.hspa.tpathbs.TpathBsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/tbill_charges_services")
public class IPDChargesServiceController {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TbillBillRepository tbillBillRepository;

    @Autowired
    TpathBsRepository tpathBsRepository;

    @Autowired
    MpathTestRepository mpathTestRepository;

    @Autowired
    TbillServiceRadiologyRepository tbillServiceRadiologyRepository;

    @Autowired
    IPDChargesServiceRepository ipdChargesServiceRepository;

    @Autowired
    MbillIPDChargeRepository mbillIPDChargeRepository;

    @Autowired
    MstStaffRepository mstStaffRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<IPDChargesService> ipdChargesService) {
        TenantContext.setCurrentTenant(tenantName);
        MbillIPDCharge mbillIPDCharge = null;
        List<IPDChargesService> finalBillServiceList = new ArrayList<IPDChargesService>();
        for (int i = 0; i < ipdChargesService.size(); i++) {
            java.sql.Date todaysdate;
            LocalDate today = LocalDate.now();
            todaysdate = java.sql.Date.valueOf(today);
            if (ipdChargesService.get(i).getCsStaffId().getStaffId() != 0) {
                MstStaff staff = new MstStaff();
                staff = ipdChargesService.get(i).getCsStaffId();
                Long staffid = staff.getStaffId();
            } else {
                ipdChargesService.get(i).setCsStaffId(null);
            }
            String Prefix = "";
            String newTokenNo = "";
            String lastTokenNo = "";
            try {
                IPDChargesService serviceList = ipdChargesServiceRepository
                        .findOneByCsDateAndIsActiveTrueAndIsDeletedFalse(todaysdate);
                lastTokenNo = serviceList.getCsTokenNumber();
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
            ipdChargesService.get(i).setCsTokenNumber(newTokenNo);
            if (ipdChargesService.get(i).getCsDate() == null) {
                ipdChargesService.get(i).setCsDate(new Date());
            }
            ipdChargesService.get(i).setCsBilled(false);
            IPDChargesService newBillServiceList = ipdChargesServiceRepository.save(ipdChargesService.get(i));
            mbillIPDCharge = mbillIPDChargeRepository.findAllByIpdchargeIdAndIsActiveTrueAndIsDeletedFalse(
                    ipdChargesService.get(i).getCsChargeId().getIpdchargeId());
            finalBillServiceList.add(newBillServiceList);
        }
        if (finalBillServiceList.size() != 0) {
            getTbillServiceRadiologyByDepartmentName(tenantName, finalBillServiceList, true, mbillIPDCharge);
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

    Boolean getTbillServiceRadiologyByDepartmentName(@RequestHeader("X-tenantId") String tenantName, List<IPDChargesService> ipdChargesServices, Boolean ipdBill,
                                                     MbillIPDCharge mbillIPDCharge) {
        TenantContext.setCurrentTenant(tenantName);
        List<TbillServiceRadiology> serviceRadiologyList = new ArrayList<>();
        List<TbillServiceGeneral> tbillServiceGenerallist = new ArrayList<>();
        List<TpathBs> tpathBList = new ArrayList<>();
        Boolean retrn = false;
        try {
            for (IPDChargesService ipdChargesService : ipdChargesServices) {

                /*ipdChargesService.getCsServiceId().getServiceGroupId().getGroupDepartmentId().getDepartmentName().matches("(?i:.*(RAD).*)")*/
/*				ipdChargesService.getCsServiceId().getServiceGroupId().getGroupDepartmentId().getDepartmentName().matches("(?i:.*(LAB).*)"))||
						(ipdChargesService.getCsServiceId().getServiceGroupId().getGroupDepartmentId().getDepartmentName().matches("(?i:.*(PATH).*)")*/
                if (ipdChargesService.getCsServiceId().getServiceIsRadiology()) {
                    TbillServiceRadiology tbillServiceRadiology = new TbillServiceRadiology();
                    // tbillServiceRadiology.setBsrBsId(tmpBillService);
                    tbillServiceRadiology.setBsrCsId(ipdChargesServiceRepository.getById(ipdChargesService.getCsId()));
                    tbillServiceRadiology.setBsrIsPaid(false);
                    tbillServiceRadiology.setBsrIpdBill(ipdBill);
                    serviceRadiologyList.add(tbillServiceRadiology);
                } else if (ipdChargesService.getCsServiceId().getServiceIsLaboratory()) {
                    TpathBs tpathBs = new TpathBs();
                    tpathBs.setIsIPD(ipdBill);
                    tpathBs.setIsPerformed(true);
                    tpathBs.setPsPerformedByDate(new Date());
                    if (ipdChargesService.getCsStaffId() != null) {
                        MstStaff selectedStaff = mstStaffRepository.getById(ipdChargesService.getCsStaffId().getStaffId());
                        tpathBs.setIsPerformedBy(String.valueOf(ipdChargesService.getCsStaffId().getStaffId()));
                        tpathBs.setIsPerformedByName(selectedStaff.getStaffUserId().getUserFirstname()
                                + " " + selectedStaff.getStaffUserId().getUserLastname());
                        tpathBs.setIsSampleAcceptedByName(
                                selectedStaff.getStaffUserId().getUserFirstname() + " "
                                        + selectedStaff.getStaffUserId().getUserLastname());
                        tpathBs.setIsLabOrderAcceptanceBy("" + ipdChargesService.getCsStaffId().getStaffId());
                        tpathBs.setIsLabOrderAcceptanceByName(
                                selectedStaff.getStaffUserId().getUserFirstname() + " "
                                        + selectedStaff.getStaffUserId().getUserLastname());
//						tpathBs.setIsPerformedByUnitId(String.valueOf(selectedStaff.getStaffUnit().get(0).getUnitId()));
//						tpathBs.setIsPerformedByUnitName(String.valueOf(selectedStaff.getStaffUnit().get(0).getUnitName()));
                        tpathBs.setPsStaffName(
                                String.valueOf(selectedStaff.getStaffUserId().getUserFullname()));
                    }
                    tpathBs.setIsPerformedByUnitId(String.valueOf(mbillIPDChargeRepository.getById(ipdChargesService.getCsChargeId().getIpdchargeId()).getChargeAdmissionId().getAdmissionUnitId().getUnitId()));
                    tpathBs.setIsPerformedByUnitName(String.valueOf(mbillIPDChargeRepository.getById(ipdChargesService.getCsChargeId().getIpdchargeId()).getChargeAdmissionId().getAdmissionUnitId().getUnitName()));
                    List<MpathTest> testObj;
                    testObj = mpathTestRepository.findByMbillServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(
                            ipdChargesService.getCsServiceId().getServiceId());
                    tpathBs.setPsTestId(testObj.get(0));
                    tpathBs.setMbillIPDCharge(mbillIPDCharge);
                    tpathBs.setPsCsId(ipdChargesServiceRepository.getById(ipdChargesService.getCsId()));
                    tpathBList.add(tpathBs);

                } else {
                    // TbillServiceGeneral tbillServiceGeneral = new
                    // TbillServiceGeneral();
                    // tbillServiceGeneral.setBsgBsId(tmpBillService);
                    // tbillServiceGeneral.setBsgIsPaid(isPaid);
                    // tbillServiceGenerallist.add(tbillServiceGeneral);
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
                // tbillServiceGeneralRepository.save(tbillServiceGenerallist);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return retrn;
    }

    @GetMapping("getservicesbychargeid")
    Iterable<IPDChargesService> getservicesbychargeid(@RequestHeader("X-tenantId") String tenantName,
                                                      @RequestParam(value = "ipdchargeId", defaultValue = "", required = false) long ipdchargeId) {
        TenantContext.setCurrentTenant(tenantName);
        Iterable<IPDChargesService> ipdChargesService;
        ipdChargesService = ipdChargesServiceRepository
                .findAllByCsChargeIdIpdchargeIdAndIsActiveTrueAndIsDeletedFalse(ipdchargeId);
        return ipdChargesService;
    }

    @GetMapping("getservicesbychargeidservicename")
    Iterable<IPDChargesService> getservicesbychargeidservicename(@RequestHeader("X-tenantId") String tenantName,
                                                                 @RequestParam(value = "ipdchargeId", defaultValue = "", required = false) long ipdchargeId, @RequestParam(value = "qString", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        Iterable<IPDChargesService> ipdChargesService;
        ipdChargesService = ipdChargesServiceRepository
                .findAllByCsChargeIdIpdchargeIdAndCsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalseAndCsCancelFalse(ipdchargeId, qString);
        return ipdChargesService;
    }

    @GetMapping("getunbilledservicesbychargeid")
    List<IPDChargesService> getunbilledservicesbychargeid(@RequestHeader("X-tenantId") String tenantName,
                                                          @RequestParam(value = "ipdchargeId", defaultValue = "", required = false) long ipdchargeId) {
        TenantContext.setCurrentTenant(tenantName);
        List<IPDChargesService> ipdChargesServiceList;
        // Object obj[];
        ipdChargesServiceList = ipdChargesServiceRepository
                .findAllByCsChargeIdIpdchargeIdAndCsBilledFalseAndIsActiveTrueAndIsDeletedFalseAndCsCancelFalseOrderByCsId(ipdchargeId);

        /*
         * for(Object[] obj: list){ IPDChargesService iPDChargesService =
         * (IPDChargesService)obj[1]; //iPDChargesService.setCsId((long)obj[0]);
         * ipdChargesServiceList.add(iPDChargesService); break; }
         */
        // ipdChargesService
        return ipdChargesServiceList;
    }

    @PutMapping("delete/{billId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("billId") Long billId, @RequestBody String remark) {
        TenantContext.setCurrentTenant(tenantName);
        IPDChargesService ipdChargesService = ipdChargesServiceRepository.getById(billId);
        if (ipdChargesService != null) {
//			ipdChargesService.setDeleted(true);
//			ipdChargesService.setActive(false);
            ipdChargesService.setCsCancel(true);
            ipdChargesService.setCsCancelRemark(remark);
            ipdChargesServiceRepository.save(ipdChargesService);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("updatelist")
    public Map<String, String> updateList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<IPDChargesService> ipdChargesService) {
        TenantContext.setCurrentTenant(tenantName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String currenttime = dateFormat.format(new Date()).toString();
        for (IPDChargesService iPDChargesService : ipdChargesService) {
            iPDChargesService.setCsBilled(true);
        }
        ipdChargesServiceRepository.saveAll(ipdChargesService);
        /*
         * Boolean aBoolean =
         * tbillBillServiceRepository.checkToCancelBill(ipdChargesService.get(0)
         * .getBsBillId().getBillId()); if(aBoolean) {
         * tbillBillRepository.updateBillForCancellation(tbillBillServices.get(0
         * ).getBsBillId().getBillId(), true); }
         */
        respMap.put("success", "1");
        respMap.put("msg", "Record Updates Successfully !");
        return respMap;
    }

    @RequestMapping("modifyRates")
    public Map<String, String> modifyRates(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<IPDChargesService> ipdChargesService) {
        TenantContext.setCurrentTenant(tenantName);
        ipdChargesServiceRepository.saveAll(ipdChargesService);
        respMap.put("success", "1");
        respMap.put("msg", "Record Updates Successfully !");
        return respMap;
    }
}
