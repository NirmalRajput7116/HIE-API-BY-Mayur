package com.cellbeans.hspa.mbillipdbillcharges;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mbillipdcharges.*;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mbillservice.MbillServiceRepository;
import com.cellbeans.hspa.mbillsponsorpaydetails.MbillSponcerPayDetails;
import com.cellbeans.hspa.mbilltariffService.MBillTariffServiceClass;
import com.cellbeans.hspa.mbilltariffService.MbillTariffService;
import com.cellbeans.hspa.mbilltariffService.MbillTariffServiceRepository;
import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mipdbed.MipdBedRepository;
import com.cellbeans.hspa.mpathtest.MpathTest;
import com.cellbeans.hspa.mpathtest.MpathTestRepository;
import com.cellbeans.hspa.mstautocharges.MstAutoCharges;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.tbillservicegeneral.TbillServiceGeneral;
import com.cellbeans.hspa.tbillserviceradiology.TbillServiceRadiology;
import com.cellbeans.hspa.tpathbs.TpathBs;
import com.cellbeans.hspa.tpathbs.TpathBsRepository;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.cellbeans.hspa.trnadmission.TrnAdmissionRepository;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/mbillipdbillcharges")
public class MbillIPDBillChargesController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MbillIPDBillChargesRepository mbillIPDBillChargesRepository;

    @Autowired
    MstVisitRepository mstVisitRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TrnAdmissionRepository trnAdmissionRepository;

    @Autowired
    MipdBedRepository mipdBedRepository;

    @Autowired
    MbillTariffServiceRepository mbillTariffServiceRepository;

    @Autowired
    MbillIPDChargeController mbillIPDChargeController;

    @Autowired
    MstStaffRepository mstStaffRepository;

    @Autowired
    TpathBsRepository tpathBsRepository;

    @Autowired
    MpathTestRepository mpathTestRepository;

    @Autowired
    MbillIPDChargeRepository mbillIPDChargeRepository;

    @Autowired
    IPDChargesServiceController iPDChargesServiceController;

    @Autowired
    MbillServiceRepository mbillServiceRepository;

    @Autowired
    IPDChargesServiceRepository ipdChargesServiceRepository;

    public static long DateDifferenceCal(String dateStart, String dateStop) {
        String dateStartTime = dateStart;
        String dateStopTime = dateStop;
        String calDate = "";
        String Seconds = "";
        String Minutes = "";
        String Hours = "";
        long calHr = 0;
        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStartTime);
            d2 = format.parse(dateStopTime);
            //in milliseconds
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffSeconds < 10) {
                Seconds = "0" + diffSeconds;
            } else {
                Seconds = String.valueOf(diffSeconds);
            }
            if (diffMinutes < 10) {
                Minutes = "0" + diffMinutes;
            } else {
                Minutes = String.valueOf(diffMinutes);
            }
            if (diffHours < 10) {
                Hours = "0" + diffHours;
            } else {
                Hours = String.valueOf(diffHours);
            }
            if (diffMinutes >= 30) {
                diffHours += 1;
            }
            calHr = (diffDays * 24) + diffHours;
            System.out.println("Hour Calutated:" + calHr);
            calDate = diffDays + " Days " + Hours + ":" + Minutes + ":" + Seconds + " Hr";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calHr;
    }

    @GetMapping
    @RequestMapping("ipd_billAutoCharges_PullUpdate")
    public Map<String, String> updateBilling(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "adminsionId") long adminsionId) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            String updateQuery = "select p from MbillIPDBillCharges p where p.ipdbcAdminsionId.admissionId=" + adminsionId + " and p.isActive=1 and p.isDeleted=0";
            List<MbillIPDBillCharges> billChargesList = entityManager.createQuery(updateQuery, MbillIPDBillCharges.class).getResultList();
            for (MbillIPDBillCharges obj : billChargesList) {
                obj.setIsPulled(true);
                obj.setLastModifiedDate(new Date());
            }
            mbillIPDBillChargesRepository.saveAll(billChargesList);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } catch (Exception e) {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("ipd_bill_list")
    public List<MbillIPDCharge> finalList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = " ") String col) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select p from MbillIPDBillCharges p where p.ipdbcAdminsionId.admissionId=" + qString + " and p.isActive=1 and p.isDeleted=0";
        String AutoChargeQUery = "select a from MstAutoCharges a where a.isActive=1 and a.isDeleted=0";
        System.out.println("QUery:" + query);
        List<MbillIPDBillCharges> billChargesList = entityManager.createQuery(query, MbillIPDBillCharges.class).getResultList();
        List<MstAutoCharges> listAutoCharges = entityManager.createQuery(AutoChargeQUery, MstAutoCharges.class).getResultList();
        List<MbillIPDCharge> newMbillIPDCharge = new ArrayList<MbillIPDCharge>();
        List<IPDChargesService> calculatedServiceList = new ArrayList<IPDChargesService>();
        List<TrnSponsorCombination> listSponsor = new ArrayList<TrnSponsorCombination>();
        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long calculateHr = 0;
        long calfact = 0;
        if (billChargesList.size() > 0) {
            String soncerQuery = "select p from TrnSponsorCombination p where p.scUserId.userId=" + billChargesList.get(0).getIpdbcAdminsionId().getAdmissionPatientId().getPatientUserId().getUserId() + " and p.isActive=1 and p.isDeleted=0";
            listSponsor = entityManager.createQuery(soncerQuery, TrnSponsorCombination.class).getResultList();
        }
        for (MbillIPDBillCharges obj : billChargesList) {
            MbillIPDCharge newObj = new MbillIPDCharge();
            newObj.setChargeAdmissionId(obj.getIpdbcAdminsionId());
            newObj.setChargeUserId(obj.getIpdbcAdminsionId().getAdmissionStaffId());
            if (obj.getIsPulled()) {
                if (obj.getIpdbcBedReleaseDateTime() != null) {
                    calculateHr = DateDifferenceCal(formatdate.format(obj.getLastModifiedDate()), formatdate.format(obj.getIpdbcBedReleaseDateTime()));
                } else {
                    calculateHr = DateDifferenceCal(formatdate.format(obj.getLastModifiedDate()), formatdate.format(new Date()));
                }

            } else {
                calculateHr = DateDifferenceCal(formatdate.format(obj.getIpdbcBedAllocationDateTime()), formatdate.format(new Date()));
            }
            if (calculateHr >= Integer.parseInt(obj.getIpdbcBedBillRoutingFact())) {
                calfact = calculateHr / Integer.parseInt(obj.getIpdbcBedBillRoutingFact());
                newObj.setCalculatedFact(calfact);
                List<MstVisit> mstVisit = mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(obj.getIpdbcAdminsionId().getAdmissionPatientId().getPatientId());
                MstVisit objmstVisit = mstVisit.get(0);
                /*if (listSponsor.size() > 0) {
                    for (TrnSponsorCombination sponsorCombination : listSponsor) {
                        if (sponsorCombination.getScPriorityId().getPriorityName().equals("1")) {
                            objmstVisit.setVisitTariffId(sponsorCombination.getScCompanyId().getCompanyTariffId());
                        }
                    }
                }*/
                List<MbillSponcerPayDetails> subgroup = new ArrayList<>();
                if (listSponsor.size() > 0) {
                    subgroup = listSponsor.get(0).getScSpdId();
                }
                //last visit data
                for (MstAutoCharges autoobj : listAutoCharges) {
                    List<MbillTariffService> tariffservicelist;
                    tariffservicelist = mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndTsServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.toString(objmstVisit.getVisitTariffId().getTariffId()), autoobj.getAcServiceId().getServiceId());
                    /*for(MbillTariffService visitObjservics :objmstVisit.getVisitTariffId().getTariffServices()){
                        if(autoobj.getAcServiceId().getServiceId()==visitObjservics.getTsServiceId().getServiceId()){
                    */
                    if (tariffservicelist.size() > 0) {
                        for (MBillTariffServiceClass objclass : tariffservicelist.get(0).getTsTariffServiceClassList()) {
                            if (obj.getIpdbcBedId().getBedBcId().getClassId() == objclass.getTscClassId().getClassId()) {
                                MbillSponcerPayDetails mbillSponcerPayDetails = new MbillSponcerPayDetails();
                                double comppay = 0;
                                double tariffpay = 0;
                                if (listSponsor.size() > 0) {
                                    for (MbillSponcerPayDetails objsp : subgroup) {
                                        if (objsp.getSpdSgId().getSgId() == tariffservicelist.get(0).getTsServiceId().getServiceSgId().getSgId()) {
                                            comppay = objsp.getSpdCompanyShare();
                                        }
                                    }
                                    tariffpay = objclass.getTscClassRate() * (comppay / 100);
                                }
                                IPDChargesService newCalculated = new IPDChargesService();
                                newCalculated.setCsClassRate(objclass.getTscClassRate());
                                newCalculated.setCsTariffCoPay(tariffpay * calfact);
                                newCalculated.setCsTariffCPRate(tariffpay);
                                newCalculated.setCsBaseRate(objclass.getTscClassRate());
                                newCalculated.setCsCoPayQtyRate((objclass.getTscClassRate() - tariffpay) * calfact);
                                newCalculated.setCsQuantity(calfact);
                                newCalculated.setCsQRRate((objclass.getTscClassRate() - tariffpay) * calfact);
                                newCalculated.setCsQtyRate((objclass.getTscClassRate() - tariffpay) * calfact);
                                newCalculated.setCsGrossRate((objclass.getTscClassRate() - tariffpay) * calfact);
                                newCalculated.setCsServiceId(autoobj.getAcServiceId());
                                newCalculated.setCsCancel(false);
                                newCalculated.setCsPriority(false);
                                newCalculated.setCsStaffId(objmstVisit.getVisitStaffId());
                                calculatedServiceList.add(newCalculated);
                                newObj.setChargeClassId(objclass.getTscClassId());
                                newObj.setChargeTariffId(objmstVisit.getVisitTariffId());

                            }
                        }
                    }
                      /*  }
                    }*/
                }
                newObj.setIPDChargesServiceList(calculatedServiceList);
                newMbillIPDCharge.add(newObj);
            }
        }
        return newMbillIPDCharge;
    }

    @RequestMapping("autochargeforallocation/{admissionId}/{bedId}")
    public List<String> autochargeforallocation(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId, @PathVariable("bedId") Long bedId) throws ParseException {
        List<String> op = new ArrayList<>();
        TrnAdmission trnAdmission = trnAdmissionRepository.getById(admissionId);
        MipdBed mipdBed = mipdBedRepository.getById(bedId);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date admissionDate = formatter6.parse(trnAdmission.getAdmissionDate());
        MbillIPDBillCharges mbillIPDBillCharges = new MbillIPDBillCharges();
        mbillIPDBillCharges.setIpdbcAdminsionId(trnAdmission);
        mbillIPDBillCharges.setIpdbcBedId(mipdBed);
        mbillIPDBillCharges.setIpdbcBedAllocationDateTime(admissionDate);
        mbillIPDBillCharges.setIpdbcChargesType(trnAdmission.getAdmissionAutoChargeType());
        mbillIPDBillCharges.setIpdbcBedBillRoutingFact(trnAdmission.getAdmissionAutoChargeFrequency());
        mbillIPDBillCharges.setCreatedDate(admissionDate);
        mbillIPDBillCharges.setIpdbcBedAllocationDateTime(admissionDate);
        //mbillIPDBillCharges.setIpdbcBedRoutineStartTime(trnAdmission.getAdmissionAutoChargeTime());
        mbillIPDBillChargesRepository.save(mbillIPDBillCharges);
        op.add("done");
        return op;
    }

    @RequestMapping("autochargeforrelease/{admissionId}/{bedId}")
    public List<String> autochargeforrelease(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId, @PathVariable("bedId") Long bedId) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        List<String> op = new ArrayList<>();
        TrnAdmission trnAdmission = trnAdmissionRepository.getById(admissionId);
        MipdBed mipdBed = mipdBedRepository.getById(bedId);
        SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date currentdate = formatter6.parse(formatter6.format(new Date()));
        // Timestamp timestamp = new Timestamp(currentdate);
        MbillIPDBillCharges mbillIPDBillCharges = new MbillIPDBillCharges();
        mbillIPDBillCharges.setIpdbcAdminsionId(trnAdmission);
        mbillIPDBillCharges.setIpdbcBedId(mipdBed);
        mbillIPDBillCharges.setIpdbcBedReleaseDateTime(currentdate);
        mbillIPDBillCharges.setIpdbcChargesType(trnAdmission.getAdmissionAutoChargeType());
        mbillIPDBillCharges.setIpdbcBedBillRoutingFact(trnAdmission.getAdmissionAutoChargeFrequency());
        //mbillIPDBillCharges.setIpdbcBedRoutineStartTime(trnAdmission.getAdmissionAutoChargeTime());
        mbillIPDBillCharges.setCreatedDate(trnAdmission.getCreatedDate());
        mbillIPDBillCharges.setLastModifiedDate(currentdate);
        mbillIPDBillChargesRepository.save(mbillIPDBillCharges);
        op.add("done");
        return op;
    }

    // http://localhost:8090/mbillipdbillcharges/OT_service_create/5/2/1
    @GetMapping
    @RequestMapping("OT_service_create/{patientId}/{serviceId}/{staffId}")
    public MbillIPDCharge OTserviceList(@RequestHeader("X-tenantId") String tenantName, @PathVariable(value = "patientId") long patientid, @PathVariable(value = "serviceId") long serviceid, @PathVariable(value = "staffId") int staffid) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmission admission;
        List<TrnAdmission> listadmission = trnAdmissionRepository.findByAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByAdmissionIdDesc(patientid);
        admission = listadmission.get(0);
        List<MstVisit> mstVisit = mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(admission.getAdmissionPatientId().getPatientId());
        admission.setMbillTariff(mstVisit.get(0).getVisitTariffId());
        MbillIPDCharge newObj = new MbillIPDCharge();
        newObj.setChargeAdmissionId(admission);
        newObj.setTbillUnitId(admission.getAdmissionUnitId());
        MstStaff newstaff = new MstStaff();
        newstaff.setStaffId(staffid);
        newObj.setChargeUserId(newstaff);
        List<IPDChargesService> calculatedServiceList = new ArrayList<>();
        List<MbillTariffService> tariffservicelist;
        tariffservicelist = mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndTsServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.toString(admission.getMbillTariff().getTariffId()), serviceid);
        for (MBillTariffServiceClass objclass : tariffservicelist.get(0).getTsTariffServiceClassList()) {
            if (admission.getAdmissionPatientBedId().getBedBcId().getClassId() == objclass.getTscClassId().getClassId()) {
                IPDChargesService newCalculated = new IPDChargesService();
                newCalculated.setCsTariffCoPay(objclass.getTscCoPay());
                newCalculated.setCsClassRate(objclass.getTscClassRate());
                newCalculated.setCsCoPayQtyRate((objclass.getTscClassRate()) - newCalculated.getCsTariffCoPay());
                newCalculated.setCsBaseRate(objclass.getTscClassRate());
                newCalculated.setCsQuantity(1);
                newCalculated.setCsQRRate(objclass.getTscClassRate());
                newCalculated.setCsQtyRate(objclass.getTscClassRate());
                newCalculated.setCsGrossRate((objclass.getTscClassRate()) - newCalculated.getCsTariffCoPay());
                newCalculated.setCsTariffCPRate((objclass.getTscClassRate() - objclass.getTscCoPay()));
                MbillService newsevice = new MbillService();
                newsevice.setServiceId(serviceid);
                newCalculated.setCsServiceId(newsevice);
                newCalculated.setCsCancel(false);
                newCalculated.setCsPriority(false);
                newCalculated.setCsStaffId(admission.getAdmissionStaffId());
                calculatedServiceList.add(newCalculated);
                newObj.setChargeClassId(objclass.getTscClassId());
                newObj.setChargeTariffId(admission.getMbillTariff());
                newObj.setChargeNetPayable(newCalculated.getCsQRRate());
                newObj.setChargeOutstanding(newCalculated.getCsQRRate());
                newObj.setChargeAdvance(0);
                newObj.setChargeDiscountAmount(newCalculated.getCsDiscountAmount());
                newObj.setChargeAmountTobePaid(newCalculated.getCsGrossRate());
                newObj.setChargeDiscountPercentage(newCalculated.getCsConcessionPercentage());
                newObj.setChargeSubTotal(newCalculated.getCsGrossRate());
                newObj.setBilled(false);
                newObj.setSettled(false);
            }
        }
        newObj.setIPDChargesServiceList(calculatedServiceList);
        Map<String, String> obj = mbillIPDChargeController.create(tenantName, newObj);
        String chargeid = obj.get("csChargeId");
        MbillIPDCharge ipdchargeobj = new MbillIPDCharge();
        ipdchargeobj.setIpdchargeId(Long.parseLong(chargeid));
        calculatedServiceList.get(0).setCsChargeId(ipdchargeobj);
        iPDChargesServiceController.create(tenantName, calculatedServiceList);
        return newObj;
    }

    @GetMapping
    @RequestMapping("EMR_service_create/{admissionId}/{serviceId}/{staffId}")
    public MbillIPDCharge EMRserviceList(@RequestHeader("X-tenantId") String tenantName, @PathVariable(value = "admissionId") long admissionid, @PathVariable(value = "serviceId") long[] serviceid, @PathVariable(value = "staffId") int staffid) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmission admission;
        admission = trnAdmissionRepository.findByAdmissionIdAndIsActiveTrueAndIsDeletedFalse(admissionid);
        List<MstVisit> mstVisit = mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(admission.getAdmissionPatientId().getPatientId());
        admission.setMbillTariff(mstVisit.get(0).getVisitTariffId());
        MbillIPDCharge newObj = new MbillIPDCharge();
        newObj.setChargeAdmissionId(admission);
        newObj.setTbillUnitId(admission.getAdmissionUnitId());
        MstStaff newstaff = new MstStaff();
        newstaff.setStaffId(staffid);
        newObj.setChargeUserId(newstaff);
        List<IPDChargesService> calculatedServiceList = new ArrayList<>();
        double netpayable = 0;
        double outstanding = 0;
        double discount = 0;
        double topaid = 0;
        double discper = 0;
        double subtotal = 0;
        for (int i = 0; i < serviceid.length; i++) {
            List<MbillTariffService> tariffservicelist;
            tariffservicelist = mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndTsServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.toString(admission.getMbillTariff().getTariffId()), serviceid[i]);
            if (tariffservicelist.size() != 0) {
                for (MBillTariffServiceClass objclass : tariffservicelist.get(0).getTsTariffServiceClassList()) {
                    if (admission.getAdmissionPatientBedId().getBedBcId().getClassId() == objclass.getTscClassId().getClassId()) {
                        IPDChargesService newCalculated = new IPDChargesService();
                        newCalculated.setCsTariffCoPay(objclass.getTscCoPay());
                        newCalculated.setCsClassRate(objclass.getTscClassRate());
                        newCalculated.setCsCoPayQtyRate((objclass.getTscClassRate()) - newCalculated.getCsTariffCoPay());
                        newCalculated.setCsBaseRate(objclass.getTscClassRate());
                        newCalculated.setCsQuantity(1);
                        newCalculated.setCsQRRate(objclass.getTscClassRate());
                        newCalculated.setCsQtyRate(objclass.getTscClassRate());
                        newCalculated.setCsGrossRate((objclass.getTscClassRate()) - newCalculated.getCsTariffCoPay());
                        newCalculated.setCsTariffCPRate((objclass.getTscClassRate() - objclass.getTscCoPay()));
                        MbillService newsevice = new MbillService();
                        newsevice.setServiceId(serviceid[i]);
                        newCalculated.setCsServiceId(newsevice);
                        newCalculated.setCsCancel(false);
                        newCalculated.setCsPriority(false);
                        newCalculated.setCsStaffId(admission.getAdmissionStaffId());
                        calculatedServiceList.add(newCalculated);
                        newObj.setChargeClassId(objclass.getTscClassId());
                        newObj.setChargeTariffId(admission.getMbillTariff());
                        netpayable = netpayable + newCalculated.getCsQRRate();
                        outstanding = outstanding + newCalculated.getCsQRRate();
                        discount = discount + newCalculated.getCsDiscountAmount();
                        topaid = topaid + newCalculated.getCsGrossRate();
                        discper = discper + newCalculated.getCsConcessionPercentage();
                        subtotal = subtotal + newCalculated.getCsGrossRate();
                        newObj.setBilled(true);
                        newObj.setSettled(false);
                    }
                }
            } else {
                MbillIPDCharge newObj1 = new MbillIPDCharge();
                return newObj1;
            }
        }
        newObj.setChargeNetPayable(netpayable);
        newObj.setChargeOutstanding(outstanding);
        newObj.setChargeAdvance(0);
        newObj.setChargeDiscountAmount(discount);
        newObj.setChargeAmountTobePaid(topaid);
        newObj.setChargeDiscountPercentage(discper);
        newObj.setChargeSubTotal(subtotal);
        newObj.setIPDChargesServiceList(calculatedServiceList);
        Map<String, String> obj = mbillIPDChargeController.create(tenantName, newObj);
        String chargeid = obj.get("csChargeId");
        MbillIPDCharge ipdchargeobj = new MbillIPDCharge();
        ipdchargeobj.setIpdchargeId(Long.parseLong(chargeid));
        for (IPDChargesService ipdChargesService : calculatedServiceList) {
            ipdChargesService.setCsChargeId(ipdchargeobj);
        }
        iPDChargesServiceController.create(tenantName, calculatedServiceList);
        getchargeServiceRadiologyByDepartmentName(tenantName, calculatedServiceList);
        return newObj;
    }

    Boolean getchargeServiceRadiologyByDepartmentName(@RequestHeader("X-tenantId") String tenantName, List<IPDChargesService> ipdChargesService) {
        TenantContext.setCurrentTenant(tenantName);
        List<TbillServiceRadiology> serviceRadiologyList = new ArrayList<>();
        List<TbillServiceGeneral> tbillServiceGenerallist = new ArrayList<>();
        List<TpathBs> tpathBList = new ArrayList<>();
        // System.out.println("Hello getTbillServiceRadiologyByDepartmentName");
        Boolean retrn = false;
        try {
            for (IPDChargesService tmpBillService : ipdChargesService) {
                // System.out.println("regex :" +tmpBillService.getBsServiceId().getServiceGroupId().getGroupDepartmentId().getDepartmentName());
                tmpBillService.setCsServiceId(mbillServiceRepository.getById(tmpBillService.getCsServiceId().getServiceId()));
                if (tmpBillService.getCsServiceId().getServiceGroupId().getGroupDepartmentId().getDepartmentName().matches("(?i:.*(RAD).*)")) {
                    /*TbillServiceRadiology tbillServiceRadiology = new TbillServiceRadiology();
                    tbillServiceRadiology.setBsrBsId(tmpBillService);
                    tbillServiceRadiology.setBsrIsPaid(isPaid);
                    tbillServiceRadiology.setBsrIpdBill(ipdBill);
                    serviceRadiologyList.add(tbillServiceRadiology);*/
                } else if ((tmpBillService.getCsServiceId().getServiceGroupId().getGroupDepartmentId().getDepartmentName().matches("(?i:.*(LAB).*)")) || (tmpBillService.getCsServiceId().getServiceGroupId().getGroupDepartmentId().getDepartmentName().matches("(?i:.*(PATH).*)"))) {
                    System.out.println("hello vj :" + tmpBillService);
                    TpathBs tpathBs = new TpathBs();
                    //    tpathBs.setPsBsId(tmpBillService);
                    tpathBs.setIsPerformed(true);
                    tpathBs.setPsPerformedByDate(new Date());
                    tpathBs.setMbillIPDCharge(tmpBillService.getCsChargeId());
                    tpathBs.setIsIPD(true);
                    //     tpathBs.setPsServiceId(tmpBillService.getBsServiceId());
                    tpathBs.setIsPerformedBy(String.valueOf(tmpBillService.getCsStaffId().getStaffId()));
                    MbillIPDCharge billobj = new MbillIPDCharge();
                    billobj = mbillIPDChargeRepository.getById(tmpBillService.getCsChargeId().getIpdchargeId());
                    System.out.println(billobj);
                    if (billobj != null) {
                        tpathBs.setIsPerformedByUnitId(String.valueOf(billobj.getTbillUnitId().getUnitId()));
                        tpathBs.setIsPerformedByUnitName(String.valueOf(billobj.getTbillUnitId().getUnitName()));
                    }
                    MstStaff staffObj = new MstStaff();
                    staffObj = mstStaffRepository.getById(tmpBillService.getCsStaffId().getStaffId());
                    tpathBs.setIsPerformedByName(String.valueOf(staffObj.getStaffUserId().getUserFullname()));
                    tpathBs.setPsStaffName(String.valueOf(staffObj.getStaffUserId().getUserFullname()));
                    //  System.out.println("hello vijay :"+tmpBillService.getCsServiceId().getServiceId());
                    List<MpathTest> testObj = new ArrayList<MpathTest>();
                    testObj = mpathTestRepository.findByMbillServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(tmpBillService.getCsServiceId().getServiceId());
                    if (testObj.size() > 0) {
                        tpathBs.setPsTestId(testObj.get(0));
                    }
                    //   System.out.println("Lab WorkOrder Generated For :" + tpathBs);
                    tpathBList.add(tpathBs);

                } else {
                    /*TbillServiceGeneral tbillServiceGeneral = new TbillServiceGeneral();
                    tbillServiceGeneral.setBsgBsId(tmpBillService);
                    tbillServiceGeneral.setBsgIsPaid(false);
                    tbillServiceGenerallist.add(tbillServiceGeneral);*/
                }
                retrn = true;
            }
            if (tpathBList.size() > 0) {
                tpathBsRepository.saveAll(tpathBList);
            }
            if (serviceRadiologyList.size() > 0) {
                //tbillServiceRadiologyRepository.save(serviceRadiologyList);
            }
            if (tbillServiceGenerallist.size() > 0) {
                //tbillServiceGeneralRepository.save(tbillServiceGenerallist);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return retrn;
    }

    @GetMapping
    @RequestMapping("update_ipd_charge/{chargeId}/{serviceId}")
    public Map<String, String> updatecharge(@RequestHeader("X-tenantId") String tenantName, @PathVariable(value = "chargeId") long chargeId, @PathVariable(value = "serviceId") long serviceId) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            List<IPDChargesService> servicelist = ipdChargesServiceRepository.findAllByCsChargeIdIpdchargeIdEqualsAndCsServiceIdServiceIdAndIsActiveTrueAndIsDeletedFalseAndCsBilledTrue(chargeId, serviceId);
            servicelist.get(0).setCsBilled(false);
            ipdChargesServiceRepository.save(servicelist.get(0));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } catch (Exception e) {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

}
