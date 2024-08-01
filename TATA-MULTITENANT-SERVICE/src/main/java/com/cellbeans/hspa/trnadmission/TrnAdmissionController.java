package com.cellbeans.hspa.trnadmission;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import com.cellbeans.hspa.dailyoccupancy.DailyoccupancyController;
import com.cellbeans.hspa.mbillipdbillcharges.MbillIPDBillCharges;
import com.cellbeans.hspa.mbillipdbillcharges.MbillIPDBillChargesRepository;
import com.cellbeans.hspa.mbillipdcharges.IPDChargesService;
import com.cellbeans.hspa.mbillipdcharges.IPDChargesServiceRepository;
import com.cellbeans.hspa.mbillipdcharges.MbillIPDCharge;
import com.cellbeans.hspa.mbillipdcharges.MbillIPDChargeRepository;
import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.nstnursingstationdefination.NstNursingStationDefination;
import com.cellbeans.hspa.nstnursingstationdefination.NstNursingStationDefinationRepository;
import com.cellbeans.hspa.smsutility.Sendsms;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tbillbill.TbillBillController;
import com.cellbeans.hspa.tbillbill.TbillBillRepository;
import com.cellbeans.hspa.tbillbillservice.TbillBillService;
import com.cellbeans.hspa.tbillbillservice.TbillBillServiceRepository;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySale;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySaleController;
import com.cellbeans.hspa.tipdadvanceamount.TIpdAdvanceAmount;
import com.cellbeans.hspa.tipdadvanceamount.TIpdAdvanceAmountController;
import com.cellbeans.hspa.trnbedstatus.TrnBedStatusRepository;
import com.cellbeans.hspa.trnbedtransfer.TrnBedTransferRepository;
import com.cellbeans.hspa.trndischarge.TrnDischarge;
import com.cellbeans.hspa.trndischarge.TrnDischargeRepository;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombination;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombinationController;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombinationRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/trn_admission")
public class TrnAdmissionController {

    Map<String, String> respMap = new HashMap<String, String>();

    Map<String, Object> respMapcreate = new HashMap<String, Object>();
    @Autowired
    TrnAdmissionRepository trnAdmissionRepository;

    @Autowired
    TrnDischargeRepository trndischargeRepository;

    @Autowired
    MstVisitRepository mstVisitRepository;

    @Autowired
    TrnBedStatusRepository trnBedStatusRepository;

    @Autowired
    TrnSponsorCombinationRepository trnSponsorCombinationRepository;

    @Autowired
    TrnBedTransferRepository trnBedTransferRepository;

    @Autowired
    NstNursingStationDefinationRepository nstNursingStationDefinationRepository;

    @Autowired
    TbillBillServiceRepository tbillBillServiceRepository;

    @Autowired
    DailyoccupancyController dailyoccupancyController;

    @Autowired
    TrnSponsorCombinationController trnSponsorCombinationController;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MstPatientRepository mstPatientRepository;

    @Autowired
    MbillIPDBillChargesRepository mbillIPDBillChargesRepository;

    @Autowired
    TinvPharmacySaleController tinvPharmacySaleController;

    @Autowired
    TbillBillController tbillBillController;

    @Autowired
    Sendsms sendsms;

    @Autowired
    MbillIPDChargeRepository mbillIPDChargeRepository;

    @Autowired
    IPDChargesServiceRepository ipdChargesServiceRepository;

    @Autowired
    TbillBillRepository tbillBillRepository;

    @Autowired
    TIpdAdvanceAmountController tIpdAdvanceAmountController;

    @RequestMapping("getwardwisebedsummay")
    public List<WardWiseBedSummary> getwardwisebedsummay(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        HashMap<String, WardWiseBedSummary> hashMap = new HashMap<>();
        List<WardWiseBedSummary> resp = new ArrayList<WardWiseBedSummary>();
        List<Object[]> occ_bedList = (List<Object[]>) entityManager.createNativeQuery("SELECT w.ward_name AS Ward_Name,COUNT(tbs.tbs_status) AS Occ_Bed FROM trn_bed_status tbs INNER JOIN mipd_bed b ON b.bed_id = tbs.tbs_bed_id INNER JOIN mipd_ward w ON w.ward_id = b.bed_ward_id WHERE b.is_active=1 AND b.is_deleted=0 AND tbs.tbs_status=1 GROUP BY w.ward_id ORDER BY COUNT(tbs.tbs_bed_id) DESC").getResultList();
        for (int i = 0; i < occ_bedList.size(); i++) {
            WardWiseBedSummary obj = new WardWiseBedSummary();
            obj.setWardName("" + occ_bedList.get(i)[0]);
            obj.setOccBed("" + occ_bedList.get(i)[1]);
            hashMap.put("" + occ_bedList.get(i)[0], obj);
        }
        List<Object[]> available_bedList = (List<Object[]>) entityManager.createNativeQuery("SELECT w.ward_name AS Ward_Name,COUNT(tbs.tbs_status) AS Available_Bed FROM trn_bed_status tbs INNER JOIN mipd_bed b ON b.bed_id = tbs.tbs_bed_id INNER JOIN mipd_ward w ON w.ward_id = b.bed_ward_id WHERE b.is_active=1 AND b.is_deleted=0 AND tbs.tbs_status=0 GROUP BY w.ward_id ORDER BY COUNT(tbs.tbs_bed_id) DESC").getResultList();
        for (int i = 0; i < available_bedList.size(); i++) {
            if (hashMap.containsKey("" + available_bedList.get(i)[0])) {
                WardWiseBedSummary obj = hashMap.get("" + available_bedList.get(i)[0]);
                obj.setWardName("" + available_bedList.get(i)[0]);
                obj.setAvailableBed("" + available_bedList.get(i)[1]);
                hashMap.put("" + available_bedList.get(i)[0], obj);
            } else {
                WardWiseBedSummary obj = new WardWiseBedSummary();
                obj.setWardName("" + available_bedList.get(i)[0]);
                obj.setAvailableBed("" + available_bedList.get(i)[1]);
                hashMap.put("" + available_bedList.get(i)[0], obj);
            }

        }
        List<Object[]> housekeeping_bedList = (List<Object[]>) entityManager.createNativeQuery("SELECT w.ward_name AS Ward_Name,COUNT(tbs.tbs_status) AS Housekeeping_Bed FROM trn_bed_status tbs INNER JOIN mipd_bed b ON b.bed_id = tbs.tbs_bed_id INNER JOIN mipd_ward w ON w.ward_id = b.bed_ward_id WHERE b.is_active=1 AND b.is_deleted=0 AND tbs.tbs_status=2 GROUP BY w.ward_id ORDER BY COUNT(tbs.tbs_bed_id) DESC").getResultList();
        for (int i = 0; i < housekeeping_bedList.size(); i++) {
            if (hashMap.containsKey("" + housekeeping_bedList.get(i)[0])) {
                WardWiseBedSummary obj = hashMap.get("" + housekeeping_bedList.get(i)[0]);
                obj.setWardName("" + housekeeping_bedList.get(i)[0]);
                obj.setHouseKeepingBed("" + housekeeping_bedList.get(i)[1]);
                hashMap.put("" + housekeeping_bedList.get(i)[0], obj);
            } else {
                WardWiseBedSummary obj = new WardWiseBedSummary();
                obj.setWardName("" + housekeeping_bedList.get(i)[0]);
                obj.setHouseKeepingBed("" + housekeeping_bedList.get(i)[1]);
                hashMap.put("" + housekeeping_bedList.get(i)[0], obj);
            }
        }
        List<Object[]> underM_bedList = (List<Object[]>) entityManager.createNativeQuery("SELECT w.ward_name AS Ward_Name,COUNT(tbs.tbs_status) AS UnderM_Bed FROM trn_bed_status tbs INNER JOIN mipd_bed b ON b.bed_id = tbs.tbs_bed_id INNER JOIN mipd_ward w ON w.ward_id = b.bed_ward_id WHERE b.is_active=1 AND b.is_deleted=0 AND tbs.tbs_status=3 GROUP BY w.ward_id ORDER BY COUNT(tbs.tbs_bed_id) DESC").getResultList();
        for (int i = 0; i < underM_bedList.size(); i++) {
            if (hashMap.containsKey("" + underM_bedList.get(i)[0])) {
                WardWiseBedSummary obj = hashMap.get("" + underM_bedList.get(i)[0]);
                obj.setWardName("" + underM_bedList.get(i)[0]);
                obj.setUnderMaintenanceBed("" + underM_bedList.get(i)[1]);
                hashMap.put("" + underM_bedList.get(i)[0], obj);
            } else {
                WardWiseBedSummary obj = new WardWiseBedSummary();
                obj.setWardName("" + underM_bedList.get(i)[0]);
                obj.setUnderMaintenanceBed("" + underM_bedList.get(i)[1]);
                hashMap.put("" + underM_bedList.get(i)[0], obj);
            }
        }
        resp.addAll(hashMap.values());
        return resp;
    }

    @RequestMapping("createeposide")
    public Map<String, String> createEposide(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") String admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        return respMap;
    }
        /*if (trnAdmission.getAdmissionPatientId() != null) {
            trnAdmissionRepository.save(trnAdmission);
			respMap.put("success", "1");
			respMap.put("msg", "Added Successfully");
			return respMap;
		} else {
			respMap.put("success", "0");
			respMap.put("msg", "Failed To Add Null Field");
			return respMap;
		}
	}*/

    @RequestMapping("create")
    public Map<String, Object> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmission trnAdmission) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        if (trnAdmission.getAdmissionPatientId() != null) {
            String ipdNo = trnAdmissionRepository.makeIPDNumberForAddmission();
            trnAdmission.setAdmissionIpdNo(ipdNo);
            // SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            // trnAdmission.setAdmissionDate(formatter.format(formatter.parse(trnAdmission.getAdmissionDate())));
            TrnAdmission trnAdmissionnew = new TrnAdmission();
            trnAdmissionnew = trnAdmissionRepository.save(trnAdmission);
//            SMS Code For IILDS in (Confirm Admission By RohanAnd Chetan 25.07.2019) Start
//            MstPatient mstPatient = mstPatientRepository
//                    .findByPatientId(trnAdmission.getAdmissionPatientId().getPatientId());
//            String mobileno = mstPatient.getPatientUserId().getUserMobile();
//            String name =mstPatient.getPatientUserId().getUserFirstname() + " " + mstPatient.getPatientUserId().getUserLastname();
//            String mrno = mstPatient.getPatientMrNo();
//            String ipno = trnAdmissionnew.getAdmissionIpdNo();
//            String wardname = trnAdmissionnew.getAdmissionCurrentBedId().get(0).getBedWardId().getWardName();
//            String bedname = trnAdmissionnew.getAdmissionCurrentBedId().get(0).getBedName();
//            String admtime = trnAdmissionnew.getAdmissionDate();
//            String message = "Patient Admission Successfull Name " + name + " MR No " + mrno + " IPD No" + ipno
//                    + " Ward" + wardname + " AdmitTime " + admtime + " ";
//            sendsms.sendMessage(mobileno, message.replace(" ", "%20"));
//            SMS Code For IILDS in (Confirm Admission By RohanAnd Chetan 25.07.2019) End
            if (Propertyconfig.getSmsApi()) {
                if (trnAdmission.getAdmissionPatientId().getPatientUserId().getUserMobile() != null) {
                    sendsms.sendMessage(trnAdmission.getAdmissionPatientId().getPatientUserId().getUserMobile(), "Your admission has been successful");
                }
                if (trnAdmission.getAdmissionStaffId().getStaffUserId().getUserMobile() != null) {
                    sendsms.sendMessage(trnAdmission.getAdmissionStaffId().getStaffUserId().getUserMobile(), "Patient Admission Successful, patientId " + trnAdmission.getAdmissionPatientId().getPatientId());
                }
            }
            trnBedStatusRepository.bedstatusupdate(1, trnAdmission.getAdmissionPatientBedId().getBedId());
            respMapcreate.put("success", "1");
            respMapcreate.put("object", trnAdmissionnew);
            respMapcreate.put("msg", "Added Successfully");
            return respMapcreate;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMapcreate;
        }
    }
//    public String generate_ipd_no(){
//        String ipdNO;
//        return ipdNo;
//    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TrnAdmission> records;
        records = trnAdmissionRepository.findAllByAdmissionPatientIdPatientMrNoContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("/patientsearchipd/{key}/{unitid}")
    public Map<String, Object> patientsearchipd(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key, @PathVariable("unitid") Long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TrnAdmission> records;
//        records = trnAdmissionRepository.findAllByAdmissionPatientIdPatientMrNoContainsOrAdmissionPatientIdPatientUserIdUserMobileContainsOrAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndAdmissionUnitIdUnitIdEqualsAndAdmissionStatusFalseAndIsActiveTrueAndIsDeletedFalse(key, key, key, key, unitid);
        records = trnAdmissionRepository.findAllByAdmissionIsCancelFalseAndAdmissionStatusFalseAndAdmissionPatientIdPatientMrNoContainsOrAdmissionPatientIdPatientUserIdUserMobileContainsOrAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndAdmissionUnitIdUnitIdEqualsAndAdmissionStatusFalseAndIsActiveTrueAndIsDeletedFalse(key, key, key, key, unitid);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{admissionId}")
    public TrnAdmission read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmission trnAdmission = trnAdmissionRepository.getById(admissionId);
        List<TrnSponsorCombination> trnSponsorCombinations = trnSponsorCombinationRepository.findByScUserIdUserIdEqualsAndIsDeletedFalse(trnAdmission.getAdmissionPatientId().getPatientUserId().getUserId());
        trnAdmission.setTrnSponsorCombinationList(trnSponsorCombinations);
        return trnAdmission;
    }

    @RequestMapping("admissioncanclebyid/{admissionId}")
    public void admissioncanclebyid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        String currentdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        trnAdmissionRepository.markasdischarge(currentdate, true, admissionId);
    }

    @RequestMapping("cancleadmissionbyid")
    public Map<String, Object> cancleadmissionbyid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "admissionId") Long admissionId, @RequestParam(value = "admissionCancelReason") String admissionCancelReason) {
        TenantContext.setCurrentTenant(tenantName);
        String currentdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        trnAdmissionRepository.markascancel(currentdate, admissionCancelReason, admissionId);
        respMapcreate.put("success", "1");
        respMapcreate.put("msg", "Cancelled Successfully");
        return respMapcreate;
    }

    @RequestMapping("bypatientid/{patientId}")
    public TrnAdmission bypatientid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAdmission> listtrnAdmission = trnAdmissionRepository.findByAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByAdmissionIdDesc(patientId);
        TrnAdmission trnAdmission = listtrnAdmission.get(0);
        return trnAdmission;
    }

    @RequestMapping("patientbedrealease/{admissionId}")
    public TrnAdmission patientbedrealease(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmission trnAdmission = trnAdmissionRepository.getById(admissionId);
        MipdBed mbed = new MipdBed();
        trnAdmission.setAdmissionPatientBedId(mbed);
        trnAdmissionRepository.save(trnAdmission);
        return trnAdmission;
    }

    @RequestMapping("patientbedtransfer/{admissionId}/{bedId}/{bedlist}")
    @ResponseBody
    public TrnAdmission patientbedtransfer(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId, @PathVariable("bedId") Long bedId, @PathVariable("bedlist") List<MipdBed> bedlist) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmission trnAdmission = trnAdmissionRepository.getById(admissionId);
        MipdBed mbed = new MipdBed();
        mbed.setBedId(bedId);
        trnAdmission.setAdmissionPatientBedId(mbed);
        trnAdmission.setAdmissionCurrentBedId(bedlist);
        trnAdmissionRepository.save(trnAdmission);
        return trnAdmission;
    }

    @RequestMapping("patientmultibed")
    public Map<String, Object> patientmultibed(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "admissionId", required = false, defaultValue = "1") Long admissionId, @RequestBody List<MipdBed> bedlist) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmission trnAdmission = trnAdmissionRepository.getById(admissionId);
        trnAdmission.setAdmissionCurrentBedId(bedlist);
        trnAdmissionRepository.save(trnAdmission);
        respMapcreate.put("success", "1");
        respMapcreate.put("msg", "Added Successfully");
        return respMapcreate;
    }

    @RequestMapping("performbylist/{admissionId}")
    public Iterable<TbillBillService> performbylist(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillServiceRepository.findAllByBsBillIdBillAdmissionIdAdmissionIdEqualsAndBsCancelFalseAndIsActiveTrueAndIsDeletedFalse(admissionId);
    }

    @RequestMapping("update")
    public TrnAdmission update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmission trnAdmission) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.save(trnAdmission);
    }

    @GetMapping
    @RequestMapping("allocatedbed")
    public Iterable<TrnAdmission> allocatedbed(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.findAllByAdmissionStatusFalse();
    }

    @GetMapping
    @RequestMapping("list")
    public Map<String, Object> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                    @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                    @RequestParam(value = "qString", required = false) String qString,
                                    @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                    @RequestParam(value = "col", required = false, defaultValue = "admissionId") String col,
                                    @RequestParam(value = "type", required = false) int type,
                                    @RequestBody TrnAdmissionDTO trnAdmissionDTO) {
        TenantContext.setCurrentTenant(tenantName);
        Iterable<TrnAdmission> admissionlist = null;
        Map<String, Object> respMapList = new HashMap<String, Object>();
        long count = 0;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String currDate = sd.format(new Date());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // Converting POJO to Map
        Map<String, Object> map = null;
        ArrayList<Object> arr = new ArrayList<Object>();
        if (qString == null || qString.equals("")) {
            admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            if (type == 1) {
                admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 2) {
//                admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientBedIdBedWardIdWardIdEqualsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                String query = " select ta.* from trn_admission ta LEFT JOIN mipd_bed ib ON ib.bed_id = ta.admission_patient_bed_id LEFT JOIN mipd_ward iw ON iw.ward_id = ib.bed_ward_id LEFT JOIN mipd_room ir ON ir.room_id = ib.bed_room_id where ta.admission_status = false and ta.admission_is_cancel = false and ta.is_active = true and ta.is_deleted = false  ";
                if (trnAdmissionDTO.getWordId() != null) {
                    query += " and iw.ward_id = " + trnAdmissionDTO.getWordId();
                }
                if (trnAdmissionDTO.getRoomId() != null) {
                    query += " and ir.room_id = " + trnAdmissionDTO.getRoomId();
                }
                if (trnAdmissionDTO.getBedNme() != null) {
                    query += " and ib.bed_name like '%" + trnAdmissionDTO.getBedNme() + "%' ";
                }
                query += " order by ta.admission_id desc ";
                String CountQuery = " select count(*) from ( " + query + " ) as combine ";
                query += " limit " + ((Integer.parseInt(page) - 1) * Integer.parseInt(size)) + "," + Integer.parseInt(size);
                count = ((BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult()).longValue();
                admissionlist = entityManager.createNativeQuery(query, TrnAdmission.class).getResultList();
//                admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientBedIdBedWardIdWardIdEqualsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 3) {
                admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserMobileContainsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 4) {
                admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientMrNoContainsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 5) {
                admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionStaffIdStaffIdEqualsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 6) {
                admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionIpdNoContainsAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 0) {
                admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionIsCancelFalseAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 7) {
                String fromDate = "";
                String toDate = "";
                String[] parts = qString.split("_");
                int len = parts.length;
                if (len >= 1) {
                    fromDate = parts[0];
                } else {
                    fromDate = currDate;
                }
                if (len > 1) {
                    toDate = parts[1];
                } else {
                    toDate = currDate;
                }
                admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionDateBetweenAndIsActiveTrueAndIsDeletedFalse1(fromDate, toDate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
            }
            if (type == 8) {
                admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionDateBetweenAndIsActiveTrueAndIsDeletedFalse1(currDate, currDate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
            }

        }
        for (TrnAdmission singleadmission : admissionlist) {
            Double totalbalanace = 0.0;
            map = mapper.convertValue(singleadmission, new TypeReference<Map<String, Object>>() {
            });
            map.put("tariffName", dailyoccupancyController.gettariffname(tenantName, singleadmission.getAdmissionPatientId().getPatientId()));
            map.put("companyName", trnSponsorCombinationController.Listbyuserid(tenantName, singleadmission.getAdmissionPatientId().getPatientUserId().getUserId()));
            List<TIpdAdvanceAmount> tIpdAdvanceAmountList = tIpdAdvanceAmountController.getbyadmission(tenantName, singleadmission);
            for (TIpdAdvanceAmount tIpdAdvanceAmountObj : tIpdAdvanceAmountList) {
                totalbalanace = totalbalanace + tIpdAdvanceAmountObj.getAdvancedBalance();
            }
            Double totalCoPay = mbillIPDChargeRepository.getTotalCoPay(singleadmission.getAdmissionId());
            totalCoPay = totalCoPay != null ? totalCoPay : (totalCoPay = 0.0);
            map.put("advanceBalance", totalbalanace);
            map.put("totalCoPay", totalCoPay);
            arr.add(map);
        }
        respMapList.put("content", arr);
        if (type == 2) {
            respMapList.put("numberOfElements", count);
            respMapList.put("totalElements", count);
//            respMapList.put("size", ((Page<TrnAdmission>) admissionlist).getNumberOfElements());
//            respMapList.put("totalElements", ((Page<TrnAdmission>) admissionlist).getSize());
//            respMapList.put("totalPages", ((Page<TrnAdmission>) admissionlist).getTotalPages());
//            respMapList.put("sort", ((Page<TrnAdmission>) admissionlist).getSort());
        } else {
            respMapList.put("numberOfElements", ((Page<TrnAdmission>) admissionlist).getNumberOfElements());
            respMapList.put("size", ((Page<TrnAdmission>) admissionlist).getNumberOfElements());
            respMapList.put("totalElements", ((Page<TrnAdmission>) admissionlist).getTotalElements());
            respMapList.put("totalPages", ((Page<TrnAdmission>) admissionlist).getTotalPages());
            respMapList.put("sort", ((Page<TrnAdmission>) admissionlist).getSort());
        }
        return respMapList;
    }


   /* @GetMapping
    @RequestMapping("list")
    public Iterable<TrnAdmission> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "admissionDate") String col, @RequestParam(value = "type", required = false, defaultValue = "type") int type) {

        if (type == 0) {
            return trnAdmissionRepository.findAllByAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
        if (type == 1) {
            return trnAdmissionRepository.findByAdmissionStatusIsTrueAndAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 2){
            return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientBedIdBedWardIdWardIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 3) {
            return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 4) {
            return trnAdmissionRepository.findAllByPatientMrNoContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 5) {
            return trnAdmissionRepository.findByAdmissionStatusIsTrueAndAdmissionIpdNoContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 6) {
            return trnAdmissionRepository.findByAdmissionStatusIsTrueAndAdmissionStaffIdStaffUserIdUserFirstnameContainsOrAdmissionStaffIdStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

        return trnAdmissionRepository.findAllByAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }
*/

    @GetMapping
    @RequestMapping("cancelledlist")
    public Iterable<TrnAdmission> cancelledlist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "admissionId") String col, @RequestParam(value = "type", required = false) int type) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            if (type == 1) {
                return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserFirstnameContainsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 2) {
                return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientBedIdBedWardIdWardIdEqualsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 3) {
                return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserMobileContainsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 4) {
                return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionPatientIdPatientMrNoContainsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 5) {
                return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionStaffIdStaffIdEqualsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 6) {
                return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionIpdNoContainsAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 0) {
                return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
            if (type == 7) {
                String[] parts = qString.split("_");
                String fromDate = parts[0];
                String toDate = parts[1];
                return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionDateBetweenAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse1(fromDate, toDate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
            }
            if (type == 8) {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                String currDate = sd.format(new Date());
                return trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionDateBetweenAndAdmissionIsCancelTrueAndIsActiveTrueAndIsDeletedFalse1(currDate, currDate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
            }
            return null;
        }
    }

    @GetMapping
    @RequestMapping("admissionlist")
    public Iterable<TrnAdmission> admissionlist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                                @RequestParam(value = "qString", required = false) String qString,
                                                @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                @RequestParam(value = "col", required = false, defaultValue = "admissionId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnAdmissionRepository.
                    findAllByAdmissionStatusIsFalseAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return trnAdmissionRepository.findByAdmissionPatientIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("Dischargelist")
    public Iterable<TrnDischarge> Dischargelist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "createdDate") String col, @RequestParam(value = "type", required = false, defaultValue = "type") int type) {
        TenantContext.setCurrentTenant(tenantName);
        if (type == 0) {
            return trndischargeRepository.findAllByDischargeAdmissionIdAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 1) {
            return trndischargeRepository.findByDischargeAdmissionIdAdmissionStatusIsTrueAndDischargeAdmissionIdAdmissionPatientIdPatientUserIdUserFirstnameContainsOrDischargeAdmissionIdAdmissionPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 2) {
            return trndischargeRepository.findByDischargeAdmissionIdAdmissionStatusIsTrueAndDischargeAdmissionIdAdmissionIpdNoContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 3) {
            return trndischargeRepository.findByDischargeAdmissionIdAdmissionStatusIsTrueAndDischargeAdmissionIdAdmissionPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (type == 4) {
            return trndischargeRepository.findByDischargeAdmissionIdAdmissionStatusIsTrueAndDischargeTypeDtIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        return trndischargeRepository.findAllByDischargeAdmissionIdAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @PutMapping("delete/{admissionId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmission trnAdmission = trnAdmissionRepository.getById(admissionId);
        if (trnAdmission != null) {
            trnAdmission.setIsDeleted(true);
            trnAdmissionRepository.save(trnAdmission);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping(value = {"searchadmission"})
    public List<TrnAdmission> searchAdmission(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString) {
        TenantContext.setCurrentTenant(tenantName);
        if ((qString == null) || (qString.equalsIgnoreCase(""))) {
            return trnAdmissionRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        } else {
            return trnAdmissionRepository.findAllByAdmissionPatientIdPatientMrNoContainsOrAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsOrAdmissionPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByCreatedDate(qString, qString, qString, qString);
        }
    }

    @GetMapping(value = {"searchadmissionByUnitId"})
    public List<TrnAdmission> searchadmissionByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.findAllByAdmissionPatientIdPatientMrNoContainsOrAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsOrAdmissionPatientIdPatientUserIdUserMobileContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseAndAdmissionStatusFalse(qString, qString, qString, qString, unitId, PageRequest.of(0, 30));
    }

    // Start Auto search By Gayatri
    @GetMapping(value = {"searchadmissionByautocompleteMRUnitId"})
    public List<TrnAdmission> searchadmissionByautocompleteMRUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientMrNoContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, unitId, PageRequest.of(0, 30));
    }

    @GetMapping(value = {"searchadmissionByautocompleteNameUnitId"})
    public List<TrnAdmission> searchadmissionByautocompleteNameUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, qString, unitId, PageRequest.of(0, 30));
    }

    @GetMapping(value = {"searchadmissionByautocompleteMobileUnitId"})
    public List<TrnAdmission> searchadmissionByautocompleteMobileUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserMobileContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, unitId, PageRequest.of(0, 30));
    }

    @GetMapping(value = {"searchadmissionByautocompleteIDUnitId"})
    public List<TrnAdmission> searchadmissionByautocompleteIDUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserUidContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, unitId, PageRequest.of(0, 30));
    }

    @GetMapping(value = {"searchadmissionByautocompletePhoneUnitId"})
    public List<TrnAdmission> searchadmissionByautocompletePhoneUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserResidencePhoneContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, unitId, PageRequest.of(0, 30));
    }

    @GetMapping(value = {"searchadmissionByautocompleteEmailUnitId"})
    public List<TrnAdmission> searchadmissionByautocompleteEmailUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.findAllByAdmissionStatusIsFalseAndAdmissionPatientIdPatientUserIdUserEmailContainsAndAdmissionUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, unitId, PageRequest.of(0, 30));
    }
    // End By GS

    @GetMapping(value = "searchlastadmission")
    public List<TrnAdmission> searchLastAdmissionList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", defaultValue = "") String qString) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.listTrnAdmissions(qString, mstVisitRepository, trnSponsorCombinationRepository);
    }

    /**
     * This method is used for getting addmited bed list
     *
     * @param nsdId : nursing station id
     * @return
     */
    @GetMapping(value = "onlyadmittedlist/{nsdId}")
    public List<TrnAdmission> onlyAdmittedList(@RequestHeader("X-tenantId") String tenantName, @PathVariable("nsdId") Long nsdId) {
        TenantContext.setCurrentTenant(tenantName);
        // get all bed on selected nst
        NstNursingStationDefination nsd = nstNursingStationDefinationRepository.findByNsdIdAndIsActiveTrueAndIsDeletedFalse(nsdId);
        System.out.println("Selected NST: ");
        System.out.println(nsd);
        List<Long> nsdBedId = new ArrayList<>();
        for (MipdBed temp : nsd.getNstBedId()) {
            nsdBedId.add(temp.getBedId());
        }
        System.out.println("NstBedId:");
        System.out.println(nsdBedId.toString());
        // get all admited patient list
        List<TrnAdmission> dto = trnAdmissionRepository.findAllByAdmissionStatusIsFalseAndIsActiveTrueAndIsDeletedFalseOrderByAdmissionIdDesc();
        List<TrnAdmission> filtedAdmission = new ArrayList<>();
        // find common between the list
        for (TrnAdmission temp : dto) {
            for (int i = 0; i < nsdBedId.size(); i++) {
                if (temp.getAdmissionPatientBedId() != null) {
                    if (temp.getAdmissionPatientBedId().getBedId() == nsdBedId.get(i)) {
                        filtedAdmission.add(temp);
                    }
                }
            }

        }
        System.out.println("NST BEDS: " + nsdBedId);
        return filtedAdmission;
    }

    @GetMapping("listadmissionsearchbymr")
    List<TrnAdmission> listAdmissionSearchMr(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAdmission> admissionlist;
        admissionlist = trnAdmissionRepository.findByAdmissionPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(qString);
        return admissionlist;
    }

    // 28-1-2020 by jitendra
    @GetMapping("listadmissionsearchbyMrNoNonDischarge")
    List<TrnAdmission> listAdmissionSearchMrNonDischarge(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAdmission> admissionlist;
        admissionlist = trnAdmissionRepository.findByAdmissionPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionStatusEqualsAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(qString, false);
        return admissionlist;
    }

    @GetMapping("listadmissionsearchbyname")
    List<TrnAdmission> listAdmissionSearchName(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAdmission> admissionlist;
        admissionlist = trnAdmissionRepository.findByAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(qString, qString);
        return admissionlist;
    }

    @GetMapping("listadmissionsearchbymobile")
    List<TrnAdmission> listAdmissionSearchMobile(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAdmission> admissionlist;
        admissionlist = trnAdmissionRepository.findByAdmissionPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(qString);
        return admissionlist;
    }

    @GetMapping("listadmissionsearchbyphone")
    List<TrnAdmission> listAdmissionSearchPhone(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAdmission> admissionlist;
        admissionlist = trnAdmissionRepository.findByAdmissionPatientIdPatientUserIdUserResidencePhoneContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(qString);
        return admissionlist;
    }

    @GetMapping("listadmissionsearchbyid")
    List<TrnAdmission> listAdmissionSearchId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAdmission> admissionlist;
        admissionlist = trnAdmissionRepository.findByAdmissionPatientIdPatientUserIdUserPassportNoContainsAndIsActiveTrueAndIsDeletedFalseOrAdmissionPatientIdPatientUserIdUserDrivingNoContainsAndIsActiveTrueAndIsDeletedFalseOrAdmissionPatientIdPatientUserIdUserPanNoContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(qString, qString, qString);
        return admissionlist;
    }

    @GetMapping("listadmissionsearchbymail")
    List<TrnAdmission> listAdmissionSearchmail(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAdmission> admissionlist;
        admissionlist = trnAdmissionRepository.findByAdmissionPatientIdPatientUserIdUserEmailContainsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalseOrderByAdmissionIdDesc(qString);
        return admissionlist;
    }

    @RequestMapping("getAdmissionListByPatientId/{patientId}")
    public List<TrnAdmission> getAdmissionListByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.findAllByAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseAndAdmissionIsCancelFalse(patientId);

    }

    @GetMapping(value = "expecteddischargedate/{admissionId}/{Expdate}")
    public int expecteddischargedate(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId, @PathVariable("Expdate") String Expdate) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.expecteddischargedate(Expdate, admissionId);
    }

    @GetMapping(value = "dischage_patient/{admissionId}")
    public Map<String, String> dischage_patient(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String strDate = formatter.format(new Date());
        TrnAdmission obj = trnAdmissionRepository.getById(admissionId);
        obj.setAdmissionDischargeDate(strDate);
        obj.setAdmissionDischargeStatus("1");
        obj.setAdmissionStatus(true);
        trnAdmissionRepository.save(obj);
        //release bed
        for (MipdBed bedobj : obj.getAdmissionCurrentBedId()) {
            TenantContext.setCurrentTenant(tenantName);
            trnBedStatusRepository.bedstatusupdate(0, bedobj.getBedId());
            //Update in Auto charges
            String updatequery = "select * from mst_ipd_bill_charges where ipdbc_bed_id=" + bedobj.getBedId() + " and ipdbc_adminsion_id=" + admissionId;
            List<MbillIPDBillCharges> MbillIPDBillCharges = entityManager.createNativeQuery(updatequery, MbillIPDBillCharges.class).getResultList();
            for (MbillIPDBillCharges obj1 : MbillIPDBillCharges) {
                obj1.setIpdbcBedReleaseDateTime(new Date());
            }
            mbillIPDBillChargesRepository.saveAll(MbillIPDBillCharges);
        }
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
        // return trnAdmissionRepository.dischage_patient(strDate,admissionId);
    }

    @RequestMapping("dischargecancel/{admissionId}/{bedId}")
    @ResponseBody
    public TrnAdmission patientbedtransfer(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId, @PathVariable("bedId") Long bedId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmission trnAdmission = trnAdmissionRepository.getById(admissionId);
        MipdBed mbed = new MipdBed();
        mbed.setBedId(bedId);
        trnAdmission.setAdmissionDischargeDate("");
        trnAdmission.setAdmissionStatus(false);
        trnAdmission.setAdmissionPatientBedId(mbed);
        trnAdmissionRepository.save(trnAdmission);
        trnBedStatusRepository.bedstatusupdate(1, bedId);
        return trnAdmission;
    }

    @GetMapping
    @RequestMapping("getDischargeListByDate")
    public Iterable<TrnDischarge> getDischargeListByDate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "today", required = false, defaultValue = "0") Boolean today,
                                                         @RequestParam(value = "startdate", required = false) String startdate,
                                                         @RequestParam(value = "endate", required = false) String endate,
                                                         @RequestParam(value = "unitId", required = false) long unitId,
                                                         @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                         @RequestParam(value = "size", required = false, defaultValue = "20") String size) {
        TenantContext.setCurrentTenant(tenantName);
        if (today) {
            return trndischargeRepository.findTodaysDischargePatient(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
        } else {
            return trndischargeRepository.findByDischargeDateBetweenAndIsActiveTrueAndIsDeletedFalse(startdate, endate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
        }

    }

    @GetMapping
    @RequestMapping("getAdmissionListByDate")
    public Iterable<TrnAdmission> getAdmissionListByDate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "today", required = false, defaultValue = "0") Boolean today, @RequestParam(value = "startdate", required = false) String startdate, @RequestParam(value = "endate", required = false) String endate, @RequestParam(value = "unitId", required = false) long unitId, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size) {
        TenantContext.setCurrentTenant(tenantName);
        if (today) {
            return trnAdmissionRepository.findAllByAdmissionDischargeDateAndAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
        } else {
            return trnAdmissionRepository.findAllByAdmissionDischargeDateBetweenAndAdmissionStatusIsTrueAndIsActiveTrueAndIsDeletedFalse(startdate, endate, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
        }

    }

    @GetMapping
    @RequestMapping("getPharIPDOutstanding/{admissionId}")
    public Map<String, Object> getPharIPDOutstanding(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respOutstanding = new HashMap<String, Object>();
        Map<String, Double> pharma = tinvPharmacySaleController.pharmacyCallist(tenantName, admissionId);
        Map<String, Double> bill = tbillBillController.billCallist(tenantName, admissionId);
        Boolean checkOutstanding = tbillBillController.checkoutstanding(tenantName, admissionId);
        List<TinvPharmacySale> pharmaOutStanding = tinvPharmacySaleController.getPatientOutstandingListByAdmisionId(tenantName, admissionId);
        List<MbillIPDCharge> mbillIPDCharge = mbillIPDChargeRepository.findAllByChargeAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(admissionId);
        List<IPDChargesService> iPDChargesService = null;
        Double csCoPayQtyRate = 0.0;
        Double paidAmount = 0.0;
        Double oustandingAmount = 0.0;
        Double subTotal = 0.0;
        for (MbillIPDCharge ipdCharge : mbillIPDCharge) {
            iPDChargesService = ipdChargesServiceRepository.findAllByCsChargeIdIpdchargeIdAndIsActiveTrueAndIsDeletedFalseOrderByCsId(ipdCharge.getIpdchargeId());
            for (IPDChargesService iPDChargesServiceObj : iPDChargesService) {
                csCoPayQtyRate = csCoPayQtyRate + iPDChargesServiceObj.getCsCoPayQtyRate();
            }
        }
        List<TBillBill> tBillBillList = tbillBillRepository.findByBillAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(admissionId);
        for (TBillBill tBillBill : tBillBillList) {
            paidAmount = paidAmount + tBillBill.getBillAmountPaid();
            oustandingAmount = oustandingAmount + tBillBill.getBillOutstanding();
            subTotal = subTotal + tBillBill.getBillNetPayable();
        }
        respOutstanding.put("tBillBillList", tBillBillList);
        respOutstanding.put("paidAmount", paidAmount);
        respOutstanding.put("oustandingAmount", oustandingAmount);
        respOutstanding.put("subTotal", subTotal);
        respOutstanding.put("iPDChargesService", iPDChargesService);
        respOutstanding.put("csCoPayQtyRate", csCoPayQtyRate);
        respOutstanding.put("pharamcy", pharma);
//        respOutstanding.put("pharmacyOutstanding",pharmaOutStanding);
//        respOutstanding.put("checkOutstanding", checkOutstanding);
//        respOutstanding.put("bill", bill);
        return respOutstanding;
    }

    @GetMapping
    @RequestMapping("checkIsAlreadyAdmitted/{patientId}")
    public Boolean checkIsAlreadyAdmitted(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmission trnAdmission = trnAdmissionRepository.findByAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseAndAdmissionStatusFalseAndAdmissionIsCancelFalse(patientId);
        if (trnAdmission != null) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping
    @RequestMapping("listadmission")
    public Iterable<TrnAdmission> listadmission(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                @RequestParam(value = "size", required = false, defaultValue = "10") String size,
                                                @RequestParam(value = "qString", required = false) String qString,
                                                @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                @RequestParam(value = "col", required = false, defaultValue = "admission_id") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnAdmissionRepository.findAdmittedPatientByCreatedDateBetween(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return trnAdmissionRepository.findAdmittedPatientByCreatedDateBetween(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @RequestMapping("listtodaysadmission")
    public Iterable<TrnAdmission> listtodaysadmission(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                      @RequestParam(value = "size", required = false, defaultValue = "10") String size,
                                                      @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                      @RequestParam(value = "col", required = false, defaultValue = "admission_id") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionRepository.findTodaysAdmittedPatientByCreatedDateBetween(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }
}