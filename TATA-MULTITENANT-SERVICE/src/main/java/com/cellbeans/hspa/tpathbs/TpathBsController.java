package com.cellbeans.hspa.tpathbs;

import com.cellbeans.hspa.TenantContext;

import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.emailutility.Emailsend;
import com.cellbeans.hspa.mbillsubgroup.MbillSubGroup;
import com.cellbeans.hspa.mbillsubgroup.MbillSubGroupRepository;
import com.cellbeans.hspa.mpathTrnOutSource.MpathTrnOutSource;
import com.cellbeans.hspa.mpathTrnOutSource.MpathTrnOutSourceRepository;
import com.cellbeans.hspa.mpathsamplesuitability.MpathSampleSuitability;
import com.cellbeans.hspa.mpathtestresult.MpathTestResult;
import com.cellbeans.hspa.mpathtestresult.MpathTestResultRepository;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.rpathtestparameter.RpathTestParameter;
import com.cellbeans.hspa.rpathtestparameter.RpathTestParameterRepository;
import com.cellbeans.hspa.smsutility.Sendsms;
import com.cellbeans.hspa.tbillAgencyRate.TbillAgencyRate;
import com.cellbeans.hspa.tbillAgencyRate.TbillAgencyRateRepository;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItemRepository;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/* Owner : Vijay Patil
  Date :1-1-2018
*/
@RestController
@RequestMapping("/tpath_bs")
public class TpathBsController {

    Map<String, String> respMap = new HashMap<String, String>();

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Sendsms sendsms;
    @Autowired
    Emailsend emailsend;
    @Autowired
    TpathBsRepository tpathBsRepository;
    @Autowired
    RpathTestParameterRepository rpathTestParameterRepository;
    @Autowired
    MstStaffRepository mstStaffRepository;
    @Autowired
    TbillAgencyRateRepository tbillAgencyRateRepository;
    @Autowired
    MpathTrnOutSourceRepository mpathTrnOutSourceRepository;
    @Autowired
    MpathTestResultRepository mpathTestResultRepository;
    @Autowired
    TinvOpeningBalanceItemRepository tinvOpeningBalanceItemRepository;
    @Autowired
    MstUnitRepository mstUnitRepository;
    @Autowired
    MbillSubGroupRepository mbillSubGroupRepository;
    @Autowired
    CreateJSONObject createJSONObject;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TpathBs> tpathBs) {
        TenantContext.setCurrentTenant(tenantName);
        if (tpathBs.get(0).getPsBillId() != null) {
            List<TpathBs> newBillServiceList = tpathBsRepository.saveAll(tpathBs);
            List<Long> psIdList = new ArrayList<Long>();
            for (TpathBs bs : newBillServiceList) {
                psIdList.add(bs.getPsId());
            }
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
        List<TpathBs> records;
        records = tpathBsRepository.findByPsBillIdBillNumberContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{psId}")
    public TpathBs read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        TpathBs tpathBs = tpathBsRepository.getById(psId);
        return tpathBs;
    }

    @RequestMapping("update")
    public TpathBs update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBs tpathBs) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("********************" + tpathBs.getIsSampleCollected());
        if (tpathBs.getPsSampleCollectionDate() == null) {
            return tpathBsRepository.save(tpathBs);
        } else {
            Date currentDate = (Date) tpathBs.getPsSampleCollectionDate();
            tpathBs.setPsSampleCollectionDate(currentDate);
            return tpathBsRepository.save(tpathBs);
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TpathBs> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tpathBsRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return tpathBsRepository.findByPsBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.valueOf(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{psId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        TpathBs tpathBs = tpathBsRepository.getById(psId);
        if (tpathBs != null) {
            tpathBs.setIsDeleted(true);
            tpathBsRepository.save(tpathBs);
            respMap.put("msg", "Operation Successful");

        } else {
            respMap.put("msg", "Operation Failed");

        }
        return respMap;
    }

    //Iterable<TpathBsDTO>
    @RequestMapping("lol_listnew/{page}/{size}/{sort}/{col}")
    public Iterable<TpathBs> lolListnew(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBsFilter json, @PathVariable("page") String page, @PathVariable("size") String size, @PathVariable("sort") String sort, @PathVariable("col") String col) {
        TenantContext.setCurrentTenant(tenantName);
        Date dateFrom = new Date();
        Date dateTo = new Date();
        try {
            sort = "DESC";
            col = "psId";
//            System.out.println("json :" + json);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Setting the date to the given date
            c.setTime(sdf.parse(json.getDateTo()));
            //Number of Days to add
            c.add(Calendar.DAY_OF_MONTH, 1);
            //Date after adding the days to the given date
            String newDateTo = sdf.format(c.getTime());
            //Displaying the new Date after addition of Days
            dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(newDateTo);
            dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(json.getDateFrom());
            ArrayList<TpathBs> tpathBsArrayList = new ArrayList<TpathBs>();
            LinkedHashMap<String, TpathBs> tpathBsHashMap = new LinkedHashMap<String, TpathBs>();
            if (Boolean.parseBoolean(json.getIsIPD())) {
                List<TpathBs> tpathBsListCharge = null;
                List<TpathBs> tpathBsList = null;
                if (json.getUserRoleName().equalsIgnoreCase("Laboratory Technician") || json.getUserRoleName().equalsIgnoreCase("Lab Controller") || json.getUserRoleName().equals("Pathologist") || json.getUserRoleName().equalsIgnoreCase("Administrator") || json.getUserRoleName().equalsIgnoreCase("Super Administrator")) {
                    if (json.getPatientName() != null && json.getPatientName() != "") {
                        tpathBsListCharge = tpathBsRepository.findByMultiFilterPatientNameSuperUserForIPDCharge(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientName());
                        tpathBsList = tpathBsRepository.findByMultiFilterPatientNameSuperUserForIPDCharge(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientName());
                        tpathBsList.addAll(tpathBsListCharge);
                    } else if (json.getPatientMrno() != null && json.getPatientMrno() != "") {
                        tpathBsListCharge = tpathBsRepository.findByMultiFilterMrNoSuperUsersForIPDCharge(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientMrno());
                        tpathBsList = tpathBsRepository.findByMultiFilterMrNoSuperUsersForIPDBill(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientMrno());
                        tpathBsList.addAll(tpathBsListCharge);
                    } else if (json.getIsCompleted().equals("true") && json.getIsFinalized() == "true") {
                        tpathBsList = tpathBsRepository.findByMultiFilterFinalSuperUserForIPD(String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, Boolean.parseBoolean(json.getIsFinalized()));
                    } else {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologySuperUsersForIPD(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);
                    }
                } else {
                    if (json.getPatientName() != null && json.getPatientName() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterPatientNamePathologyForIPDCharge(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientName());
                        tpathBsListCharge = tpathBsRepository.findByMultiFilterPatientNamePathologyForIPDCharge(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientName());
                        tpathBsList.addAll(tpathBsListCharge);
                    } else if (json.getPatientMrno() != null && json.getPatientMrno() != "") {
                        tpathBsListCharge = tpathBsRepository.findByMultiFilterpatientMrnoPathologyForIPDChange(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientMrno());
                        tpathBsList = tpathBsRepository.findByMultiFilterpatientMrnoPathologyForIPDBill(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientMrno());
                        tpathBsList.addAll(tpathBsListCharge);
                    }
//                    if(json.getWorkOrderNo()!=null){
//                        return tpathBsRepository.findByMultiFilterWorkOrderNoPathologyForIPD(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo,json.getWorkOrderNo(), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//                    }
//                    if(json.getBillNo()!=null){
//                        return tpathBsRepository.findByMultiFilterBillNoPathologyForIPD(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo,json.getBillNo(), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//                    }
                    else if (json.getIsCompleted().equals("true") && json.getIsFinalized() == "true") {
                        tpathBsList = tpathBsRepository.findByMultiFilterIsFinalPathologyForIPD(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);
                    } else {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyForIPD(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);

                    }
                }
                for (TpathBs tpathBs : tpathBsList) {
                    if (tpathBs.getIsFinalized() == true) {
                        boolean status = true;
                        String key = "";
                        if (tpathBs.getMbillIPDCharge() == null) {
                            key = "bill" + tpathBs.getPsBillId().getBillId();
                        } else {
                            key = "mipdcharges" + tpathBs.getMbillIPDCharge().getIpdchargeId();
                        }
                        if (tpathBsHashMap.containsKey(key)) {
                            tpathBs = tpathBsHashMap.get(key);
                            if (tpathBs.getCompletedFlag() == null || tpathBs.getCompletedFlag() == 2) {
                                status = false;
                            }
                        }
                        if (status) {
                            tpathBs.setCompletedFlag(1);
                            tpathBsHashMap.put(key, tpathBs);
                        }
                    } else {
                        tpathBs.setCompletedFlag(2);
                        if (tpathBs.getMbillIPDCharge() != null) {
                            tpathBsHashMap.put("mipdcharges" + tpathBs.getMbillIPDCharge().getIpdchargeId(), tpathBs);
                        } else {
                            tpathBsHashMap.put("bill" + tpathBs.getPsBillId().getBillId(), tpathBs);
                        }
                    }
                }
            } else {
                List<TpathBs> tpathBsList = null;
                if (json.getUserRoleName().equalsIgnoreCase("Laboratory Technician") || json.getUserRoleName().equalsIgnoreCase("Lab Controller") || json.getUserRoleName().equalsIgnoreCase("Pathologist") || json.getUserRoleName().equalsIgnoreCase("Administrator") || json.getUserRoleName().equalsIgnoreCase("Super Administrator")) {
                    if (json.getPatientName() != null && json.getPatientName() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterOpdPatientNamePathologySuperUsers(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientName());
                    } else if (json.getPatientMrno() != null && json.getPatientMrno() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterOpdMrnoPathologySuperUsers(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientMrno());
                    } else if (json.getWorkOrderNo() != null && json.getWorkOrderNo() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterOpdWorkOrderNoPathologySuperUsers(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getWorkOrderNo());
                    } else if (json.getBillNo() != null && json.getBillNo() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterOpdBillNoPathologySuperUsers(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getBillNo());
                    } else if ((json.getIsCompleted().equals("true")) && json.getIsFinalized().equals("true")) {
                        tpathBsList = tpathBsRepository.findByMultiFilterCompletedPathologySuperUsers(String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);
                    } else if (json.getEmpNo() != null && json.getEmpNo() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterOpdEmpNoPathologySuperUsers(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getEmpNo());
                    } else {
                        System.out.println("2");
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologySuperUsersToGetTotalSize(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);
                    }
                } else {
                    if (json.getPatientName() != null && json.getPatientName() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyPatientName(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientName());
                    } else if (json.getPatientMrno() != null && json.getPatientMrno() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyMrNo(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientMrno());
                    } else if (json.getWorkOrderNo() != null && json.getWorkOrderNo() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyWorkOrderNo(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getWorkOrderNo());
                    } else if (json.getBillNo() != null && json.getBillNo() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyBillNo(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getBillNo());
                    } else if (!json.getIsCompleted().equals("false") && json.getIsFinalized() == "true") {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyIsFinal(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);
                    }
                    if (json.getEmpNo() != null && json.getEmpNo() != "") {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyEmpNo(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getEmpNo());
                    } else {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathology(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);
                    }
                }
                //By Chetan sir start
                System.out.println("tpathBsList : " + tpathBsList.size());
                for (TpathBs tpathBs : tpathBsList) {
                    if (tpathBs.getIsFinalized() == true) {
                        boolean status = true;
                        if (tpathBsHashMap.containsKey("" + tpathBs.getPsBillId().getBillId())) {
                            tpathBs = tpathBsHashMap.get("" + tpathBs.getPsBillId().getBillId());
                            if (tpathBs.getCompletedFlag() == null || tpathBs.getCompletedFlag() == 2) {
                                status = false;
                            }
                        }
                        if (status) {
                            tpathBs.setCompletedFlag(1);
                            tpathBsHashMap.put("" + tpathBs.getPsBillId().getBillId(), tpathBs);
                        } else {
                            tpathBs.setCompletedFlag(2);
                            tpathBsHashMap.put("" + tpathBs.getPsBillId().getBillId(), tpathBs);
                        }
                    } else {
                        tpathBs.setCompletedFlag(2);
                        tpathBsHashMap.put("" + tpathBs.getPsBillId().getBillId(), tpathBs);
                    }
                }
//                int startSize  = (Integer.parseInt(page) - 1) * 10;
//                int endSize = startSize + Integer.parseInt(size);
//                int i = 1;
//                int count = 1 ;
//                for (Map.Entry<Long, TpathBs> entry : tpathBsHashMap.entrySet()) {
//                    if(i > startSize){
//                        if(count > Integer.parseInt(size)){
//                            break;
//                        }
//                        tpathBsArrayList.add(entry.getValue());
//                        ++count;
//                    }
//                    ++i;
//                }
//                System.out.print("tpathBsArrayList : " + tpathBsHashMap.size());
//                TpathBs tpathbssize = tpathBsArrayList.get(0);
//                tpathbssize.setTotalSize((long)tpathBsHashMap.size());
//                return tpathBsArrayList ;        // By Chetan sir End
            }
            int startSize = (Integer.parseInt(page) - 1) * Integer.parseInt(size);
            int endSize = startSize + Integer.parseInt(size);
            int i = 1;
            int count = 1;
            for (Map.Entry<String, TpathBs> entry : tpathBsHashMap.entrySet()) {
                if (i > startSize) {
                    if (count > Integer.parseInt(size)) {
                        break;
                    }
                    tpathBsArrayList.add(entry.getValue());
                    ++count;
                }
                ++i;
            }
//            System.out.print("tpathBsArrayList : " + tpathBsHashMap.size());
            if (tpathBsArrayList.size() > 0) {
                TpathBs tpathbssize = tpathBsArrayList.get(0);
                tpathbssize.setTotalSize((long) tpathBsHashMap.size());
            }
            return tpathBsArrayList;        // By Chetan sir End

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* @RequestMapping("lol_listnew/{page}/{size}/{sort}/{col}")   /// new flow commented on 2812-19
     public List<LabOrderListDTO> lolListnew(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBsFilter json, @PathVariable("page") String page, @PathVariable("size") String size, @PathVariable("sort") String sort, @PathVariable("col") String col) {

         Date dateFrom = new Date();
         Date dateTo = new Date();
         try {
             sort = "DESC";
             col = "psId";
             String query ="";
             String queryIPDCharge="";
             String queryIPDBill="";

             Calendar c = Calendar.getInstance();
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             //Setting the date to the given date
             c.setTime(sdf.parse(json.getDateTo()));
             //Number of Days to add
             c.add(Calendar.DAY_OF_MONTH, 1);
             //Date after adding the days to the given date
             String newDateTo = sdf.format(c.getTime());
             //Displaying the new Date after addition of Days
             dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(newDateTo);
             dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(json.getDateFrom());
             List<Object[]> objTemp = null;
             List<LabOrderListDTO> labOrderListDTOList = new ArrayList<LabOrderListDTO>();


             queryIPDCharge = "SELECT  bs.ps_id AS psId, mic.created_date AS createdDate, ifnull(mic.bill_work_order_number,'') AS billWorkOrderNumber, mp.patient_mr_no AS patientMrNo, t.admission_ipd_no AS admissionIpdNo, mic.charge_number AS billNumber, CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname) AS PatientName," +
                     " bs.is_performed_by_unit_name AS unitName, " +
                     " mic.charge_sub_total AS billSubTotal, mic.charge_net_payable AS billNetPayable, " +
                     " mic.charge_amount_tobe_paid AS billAmountPaid, mic.charge_outstanding AS billOutstanding, bs.isipd AS isIPD, bs.is_finalized AS isFinalized, bs.is_lab_order_acceptance AS isLabOrderAcceptance, bs.is_performed_by AS isPerformedBy, bs.is_lab_order_acceptance_by AS isLabOrderAcceptanceBy," +
                     " IFNULL(bs.ps_bill_id,'') AS psBillId" +
                     " from tpath_bs bs " +
                     " INNER JOIN mbill_ipd_charge mic ON mic.ipdcharge_id = bs.mbillipdcharge" +
                     " INNER JOIN trn_admission t ON t.admission_id = mic.charge_admission_id" +
                     " INNER JOIN mst_patient mp ON mp.patient_id = t.admission_patient_id" +
                     " INNER JOIN mst_user mu ON mu.user_id = mp.patient_user_id" +
                     " INNER JOIN mst_title mt ON mt.title_id = mu.user_title_id" +
                     " where bs.is_active=1 and bs.is_deleted=0 " +
                     " and bs.isipd=1 and bs.ps_bill_id is NULL ";

             queryIPDBill = "UNION ALL SELECT  bs.ps_id AS psId, tb.created_date AS createdDate, ifnull(tb.bill_work_order_number,'') AS billWorkOrderNumber," +
                     " mp.patient_mr_no AS patientMrNo, t.admission_ipd_no AS admissionIpdNo," +
                     " tb.bill_number AS billNumber, CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname) AS PatientName, " +
                     " bs.is_performed_by_unit_name AS unitName, " +
                     " tb.bill_sub_total AS billSubTotal, tb.bill_net_payable AS billNetPayable, " +
                     " tb.bill_amount_paid AS billAmountPaid, tb.bill_outstanding AS billOutstanding,bs.isipd AS isIPD, bs.is_finalized AS isFinalized, bs.is_lab_order_acceptance AS isLabOrderAcceptance, bs.is_performed_by AS isPerformedBy, bs.is_lab_order_acceptance_by AS isLabOrderAcceptanceBy," +
                     " IFNULL(bs.ps_bill_id,'') AS psBillId" +
                     " from tpath_bs bs " +
                     " INNER JOIN tbill_bill tb ON tb.bill_id = bs.ps_bill_id" +
                     " INNER JOIN trn_admission t ON t.admission_id = tb.bill_admission_id" +
                     " INNER JOIN mst_patient mp ON mp.patient_id = t.admission_patient_id" +
                     " INNER JOIN mst_user mu ON mu.user_id = mp.patient_user_id" +
                     " INNER JOIN mst_title mt ON mt.title_id = mu.user_title_id" +
                     " where bs.is_active=1 and bs.is_deleted=0 " +
                     " and bs.isipd=1 ";


             if (Boolean.parseBoolean(json.getIsIPD())) {

                 if (json.getUserRoleName().equalsIgnoreCase("Laboratory Technician") || json.getUserRoleName().equalsIgnoreCase("Lab Controller") || json.getUserRoleName().equals("Pathologist") || json.getUserRoleName().equalsIgnoreCase("Administrator") || json.getUserRoleName().equalsIgnoreCase("Super Administrator")) {
                     if (json.getPatientName() != null || json.getPatientMrno() !=null) {
                         queryIPDCharge += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+" and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "') AND mp.patient_mr_no LIKE '%"+ json.getPatientMrno()+"%'" +
                         " AND CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname) LIKE '%"+json.getPatientName()+"%'" ;
                         queryIPDBill += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+"  and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "') AND mp.patient_mr_no LIKE '%"+ json.getPatientMrno()+"%'" +
                                 " AND CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname) LIKE '%"+json.getPatientName()+"%'" ;

                     } else if (json.getIsCompleted().equals("true") && json.getIsFinalized() == "true") {
                         queryIPDCharge += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+" and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "')" ;
                         queryIPDBill += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+"  and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "')" ;
                     } else {

                         queryIPDCharge += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized =0 and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+" and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "')" ;
                         queryIPDBill += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized =0 and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+"  and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "')" ;
                     }

                 } else {
                     if (json.getPatientName() != null || json.getPatientMrno() != null) {
                         queryIPDCharge += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+" and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "') AND mp.patient_mr_no LIKE '%"+ json.getPatientMrno()+"%'" +
                                 " AND CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname) LIKE '%"+json.getPatientName()+"%' AND bs.is_performed_by =" +json.getIsPerformedBy();
                         queryIPDBill += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+"  and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "') AND mp.patient_mr_no LIKE '%"+ json.getPatientMrno()+"%'" +
                                 " AND CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname) LIKE '%"+json.getPatientName()+"%' AND bs.is_performed_by =" +json.getIsPerformedBy();

                     }
                     else if (json.getIsCompleted().equals("true") && json.getIsFinalized() == "true") {
                         queryIPDCharge += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+" and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "') AND bs.is_performed_by =" +json.getIsPerformedBy() ;
                         queryIPDBill += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+"  and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "') AND bs.is_performed_by =" +json.getIsPerformedBy() ;
                     } else {
                         queryIPDCharge += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+" and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "') AND bs.is_performed_by =" +json.getIsPerformedBy() ;
                         queryIPDBill += " and  bs.is_performed_by_unit_id="+json.getIsPerformedByUnitId()+" AND bs.is_finalized ="+json.getIsFinalized()+"  and (date(bs.created_date) between '" + json.getDateFrom() + "' and '" + json.getDateTo() + "') AND bs.is_performed_by =" +json.getIsPerformedBy() ;

                     }
                 }

             *//*    for (LabOrderListDTO tpathBs : tpathBsList1) {
                    if (tpathBs.getIsFinalized() == true) {
                        boolean status = true;
                        String key = "";
                        if (tpathBs.getMbillIPDCharge() == null) {
                            key = "bill" + tpathBs.getPsBillId().getBillId();
                        } else {
                            key = "mipdcharges" + tpathBs.getMbillIPDCharge().getIpdchargeId();
                        }
                        if (tpathBsHashMap.containsKey(key)) {
                            tpathBs = tpathBsHashMap.get(key);
                            if (tpathBs.getCompletedFlag() == null || tpathBs.getCompletedFlag() == 2) {
                                status = false;
                            }
                        }
                        if (status) {
                            tpathBs.setCompletedFlag(1);
                            tpathBsHashMap.put(key, tpathBs);
                        }
                    } else {
                        tpathBs.setCompletedFlag(2);
                        if (tpathBs.getMbillIPDCharge() != null) {
                            tpathBsHashMap.put("mipdcharges" + tpathBs.getMbillIPDCharge().getIpdchargeId(), tpathBs);
                        } else {
                            tpathBsHashMap.put("bill" + tpathBs.getPsBillId().getBillId(), tpathBs);
                        }
                    }
                }*//*
                query= queryIPDCharge+queryIPDBill;
                System.out.println("Q1::" +query);
                objTemp=  entityManager.createNativeQuery(query).getResultList();
            } *//*else {

                List<TpathBs> tpathBsList = null;

                if (json.getUserRoleName().equalsIgnoreCase("Laboratory Technician") || json.getUserRoleName().equalsIgnoreCase("Lab Controller") || json.getUserRoleName().equalsIgnoreCase("Pathologist") || json.getUserRoleName().equalsIgnoreCase("Administrator") || json.getUserRoleName().equalsIgnoreCase("Super Administrator")) {

                    if (json.getPatientName() != null) {
                        tpathBsList = tpathBsRepository.findByMultiFilterOpdPatientNamePathologySuperUsers(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientName());
                    } else if (json.getPatientMrno() != null) {
                        tpathBsList = tpathBsRepository.findByMultiFilterOpdMrnoPathologySuperUsers(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientMrno());
                    } else if (json.getWorkOrderNo() != null) {
                        tpathBsList = tpathBsRepository.findByMultiFilterOpdWorkOrderNoPathologySuperUsers(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getWorkOrderNo());
                    } else if (json.getBillNo() != null) {
                        tpathBsList = tpathBsRepository.findByMultiFilterOpdBillNoPathologySuperUsers(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getBillNo());
                    } else if ((json.getIsCompleted().equals("true")) && json.getIsFinalized().equals("true")) {
                        tpathBsList = tpathBsRepository.findByMultiFilterCompletedPathologySuperUsers(String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);
                    } else {
                        System.out.println("2");
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologySuperUsersToGetTotalSize(Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);
                    }
                } else {
                    if (json.getPatientName() != null) {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyPatientName(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientName());
                    } else if (json.getPatientMrno() != null) {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyMrNo(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getPatientMrno());
                    } else if (json.getWorkOrderNo() != null) {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyWorkOrderNo(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getWorkOrderNo());
                    } else if (json.getBillNo() != null) {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyBillNo(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo, json.getBillNo());
                    } else if (!json.getIsCompleted().equals("false") && json.getIsFinalized() == "true") {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathologyIsFinal(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);
                    } else {
                        tpathBsList = tpathBsRepository.findByMultiFilterPathology(String.valueOf(json.getIsPerformedBy()), Boolean.parseBoolean(json.getIsFinalized()), String.valueOf(json.getIsPerformedByUnitId()), dateFrom, dateTo);
                    }
                }

                //By Chetan sir start

                System.out.println("tpathBsList : " + tpathBsList.size());


                for (TpathBs tpathBs : tpathBsList) {
                    if (tpathBs.getIsFinalized() == true) {

                        boolean status = true;
                        if (tpathBsHashMap.containsKey("" + tpathBs.getPsBillId().getBillId())) {
                            tpathBs = tpathBsHashMap.get("" + tpathBs.getPsBillId().getBillId());
                            if (tpathBs.getCompletedFlag() == null || tpathBs.getCompletedFlag() == 2) {
                                status = false;
                            }
                        }
                        if (status) {
                            tpathBs.setCompletedFlag(1);
                            tpathBsHashMap.put("" + tpathBs.getPsBillId().getBillId(), tpathBs);
                        } else {
                            tpathBs.setCompletedFlag(2);
                            tpathBsHashMap.put("" + tpathBs.getPsBillId().getBillId(), tpathBs);
                        }
                    } else {
                        tpathBs.setCompletedFlag(2);
                        tpathBsHashMap.put("" + tpathBs.getPsBillId().getBillId(), tpathBs);
                    }
                }
//                int startSize  = (Integer.parseInt(page) - 1) * 10;
//                int endSize = startSize + Integer.parseInt(size);
//                int i = 1;
//                int count = 1 ;
//                for (Map.Entry<Long, TpathBs> entry : tpathBsHashMap.entrySet()) {
//                    if(i > startSize){
//                        if(count > Integer.parseInt(size)){
//                            break;
//                        }
//                        tpathBsArrayList.add(entry.getValue());
//                        ++count;
//                    }
//                    ++i;
//                }
//                System.out.print("tpathBsArrayList : " + tpathBsHashMap.size());
//                TpathBs tpathbssize = tpathBsArrayList.get(0);
//                tpathbssize.setTotalSize((long)tpathBsHashMap.size());
//                return tpathBsArrayList ;        // By Chetan sir End
            }*//*
     *//* int startSize = (Integer.parseInt(page) - 1) * 10;
            int endSize = startSize + Integer.parseInt(size);
            int i = 1;
            int count = 1;
            for (Map.Entry<String, TpathBs> entry : tpathBsHashMap.entrySet()) {
                if (i > startSize) {
                    if (count > Integer.parseInt(size)) {
                        break;
                    }
                    tpathBsArrayList.add(entry.getValue());
                    ++count;
                }
                ++i;
            }
//            System.out.print("tpathBsArrayList : " + tpathBsHashMap.size());
            if (tpathBsArrayList.size() > 0) {
                TpathBs tpathbssize = tpathBsArrayList.get(0);
                tpathbssize.setTotalSize((long) tpathBsHashMap.size());
            }*//*
           for (Object[] ob : objTemp){
               LabOrderListDTO labTempObj = new LabOrderListDTO();
               labTempObj.setPsId(" " + ob[0]);
               labTempObj.setCreateDate(" " + ob[1]);
               labTempObj.setBillWorkOrderNumber(" " + ob[2]);
               labTempObj.setPatientMrNo(" " + ob[3]);
               labTempObj.setAdmissionIpdNo(" " + ob[4]);
               labTempObj.setBillNumber(" " + ob[5]);
               labTempObj.setPatientName(" " + ob[6]);
               labTempObj.setUnitName(" " + ob[7]);
               labTempObj.setBillSubTotal(Double.parseDouble(" " + ob[8]));
               labTempObj.setBillNetPayable(Double.parseDouble(" " + ob[9]));
               labTempObj.setBillAmountPaid(Double.parseDouble(" " + ob[10]));
               labTempObj.setBillOutstanding(Double.parseDouble(" " + ob[11]));
               labTempObj.setIsIPD(" " + ob[12]);
               labTempObj.setIsFinalized(" " + ob[13]);
               labTempObj.setIsLabOrderAcceptance(" " + ob[14]);
               labTempObj.setIsPerformedBy(" " + ob[15]);
               labTempObj.setIsLabOrderAcceptanceBy(" " + ob[16]);
               labTempObj.setPsBillId(" " + ob[17]);
               labOrderListDTOList.add(labTempObj);
           }
            return labOrderListDTOList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/
    @RequestMapping("finalized_lol_listnew/{page}/{size}/{sort}/{col}")
    public Iterable<TpathBs> finalizedLolListnew(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBsFilter json, @PathVariable("page") String page, @PathVariable("size") String size, @PathVariable("sort") String sort, @PathVariable("col") String col) {
        TenantContext.setCurrentTenant(tenantName);
        Iterable<TpathBs> list;
        sort = "DESC";
        col = "psId";
        Date dateFrom = new Date();
        Date dateTo = new Date();
        try {
            sort = "DESC";
            col = "psId";
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Setting the date to the given date
            if (!json.getDateTo().isEmpty()) {
                c.setTime(sdf.parse(json.getDateTo()));
            }
            //Number of Days to add
            c.add(Calendar.DAY_OF_MONTH, 1);
            //Date after adding the days to the given date
            String newDateTo = sdf.format(c.getTime());
            //Displaying the new Date after addition of Days
            dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(newDateTo);
            dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(json.getDateFrom() != null ? json.getDateFrom() : "1900-01-01");
            if (Boolean.parseBoolean(json.getIsIPD())) {
                if (json.getUserRoleName().equalsIgnoreCase("Laboratory Technician") || json.getUserRoleName().equalsIgnoreCase("Lab Controller") || json.getUserRoleName().equalsIgnoreCase("Pathologist") || json.getUserRoleName().equalsIgnoreCase("Administrator") || json.getUserRoleName().equalsIgnoreCase("Super Administrator")) {
                    if (json.getPatientMrno() == null || json.getPatientMrno().equalsIgnoreCase("null") || json.getPatientMrno().equalsIgnoreCase(null)) {
                        return tpathBsRepository.findByMultiFilterPathologyReportSuperUsersForIPD(String.valueOf(json.getIsPerformedByUnitId()), Boolean.parseBoolean(json.getIsFinalized()), dateFrom, dateTo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

                    } else {
                        System.out.println("by mro no");
                        return tpathBsRepository.findByMultiFilterPathologyReportSuperUsersForIPD('%' + json.getPatientMrno() + '%', String.valueOf(json.getIsPerformedByUnitId()), Boolean.parseBoolean(json.getIsFinalized()), dateFrom, dateTo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

                    }
                } else {
                    if (json.getPatientMrno() == null || json.getPatientMrno().equalsIgnoreCase("null") || json.getPatientMrno().equalsIgnoreCase(null)) {
                        return tpathBsRepository.findByMultiFilterPathologyReportForIPD(String.valueOf(json.getIsPerformedBy()), String.valueOf(json.getIsPerformedByUnitId()), Boolean.parseBoolean(json.getIsFinalized()), dateFrom, dateTo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                    } else {
                        return tpathBsRepository.findByMultiFilterPathologyReportForIPD('%' + json.getPatientMrno() + '%', String.valueOf(json.getIsPerformedBy()), String.valueOf(json.getIsPerformedByUnitId()), Boolean.parseBoolean(json.getIsFinalized()), dateFrom, dateTo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

                    }
                }
            } else {
                if (json.getUserRoleName().equalsIgnoreCase("Laboratory Technician") || json.getUserRoleName().equals("Lab Controller") || json.getUserRoleName().equals("Pathologist") || json.getUserRoleName().equals("Administrator") || json.getUserRoleName().equals("Super Administrator")) {
                    if (json.getPatientMrno() == null || json.getPatientMrno().equalsIgnoreCase("null") || json.getPatientMrno().equalsIgnoreCase(null)) {
                        return tpathBsRepository.findByMultiFilterPathologyReportSuperUsers(String.valueOf(json.getIsPerformedByUnitId()), Boolean.parseBoolean(json.getIsFinalized()), dateFrom, dateTo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                    } else {
                        return tpathBsRepository.findByMultiFilterPathologyReportSuperUsers('%' + json.getPatientMrno() + '%', String.valueOf(json.getIsPerformedByUnitId()), Boolean.parseBoolean(json.getIsFinalized()), dateFrom, dateTo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

                    }
                } else {
                    if (json.getPatientMrno() == null || json.getPatientMrno().equalsIgnoreCase("null") || json.getPatientMrno().equalsIgnoreCase(null)) {
                        return tpathBsRepository.findByMultiFilterPathologyReport(String.valueOf(json.getIsPerformedBy()), String.valueOf(json.getIsPerformedByUnitId()), Boolean.parseBoolean(json.getIsFinalized()), dateFrom, dateTo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                    } else {
                        return tpathBsRepository.findByMultiFilterPathologyReport('%' + json.getPatientMrno() + '%', String.valueOf(json.getIsPerformedBy()), String.valueOf(json.getIsPerformedByUnitId()), Boolean.parseBoolean(json.getIsFinalized()), dateFrom, dateTo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping
    @RequestMapping("lol_test_parameters_list")
    public Iterable<RpathTestParameter> lolTestParametersList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "testId", required = false) long testId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return rpathTestParameterRepository.findByTpTestIdTestIdLikeAndIsActiveTrueAndIsDeletedFalseOrderByTpParameterSequenceNumberAsc(testId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("test_order_list")
    public Iterable<TpathBs> testOrderList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "billId", required = false) long id, @RequestParam(value = "isIPD", required = false) String isIPD, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (Boolean.parseBoolean(isIPD)) {
            List<TpathBs> listOfAllocatedServices = tpathBsRepository.findByMbillIPDChargeIpdchargeIdEqualsAndIsFinalizedFalseAndIsActiveTrueAndIsDeletedFalse(id);
//            List<TpathBs> listOfAllocatedServices = tpathBsRepository.findByPsBillIdBillIdEqualsAndIsFinalizedFalseAndIsActiveTrueAndIsDeletedFalse(id);
            return listOfAllocatedServices;
        } else {
            List<TpathBs> listOfAllocatedServices = tpathBsRepository.findByPsBillIdBillIdEqualsAndIsFinalizedFalseAndIsActiveTrueAndIsDeletedFalse(id);
            return listOfAllocatedServices;
        }

    }

    @GetMapping
    @RequestMapping("test_order_list_by_bill_id")
    public Iterable<TpathBs> testOrderListByBillId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "billId", required = false) long id, @RequestParam(value = "isIPD", required = false) String isIPD, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        List<TpathBs> listOfAllocatedServices = tpathBsRepository.findByPsBillIdBillIdEqualsAndIsFinalizedFalseAndMbillIPDChargeIsNullAndIsActiveTrueAndIsDeletedFalse(id);
        return listOfAllocatedServices;

    }

    String getSampleNumer(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String lastSampleNo = String.valueOf(tpathBsRepository.findAllSampleNo().getPsId());
        //   System.out.println("LastSample No :" + lastSampleNo);
        String numStyle = "00000000";
        int len = lastSampleNo.length();
        int len2 = numStyle.length();
        int finallen = len2 - len;
        //   System.out.println(finallen);
        //   lastSampleNo = "000000" + lastSampleNo;
        for (int i = 0; i < finallen; i++) {
            lastSampleNo = "0" + lastSampleNo;
        }
        //   System.out.println("newSampleNo :" + lastSampleNo);
        return lastSampleNo;
    }

    //vijay
    @RequestMapping("addcollectedsample")
    public Map<String, String> addCollectedSample(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TpathBs> listOfCollectedSample) {
        TenantContext.setCurrentTenant(tenantName);
        // String Prefix = Propertyconfig.getmrnopre();
        boolean flag = false;
        //    boolean flag = false;
        // String newSampleNo = getSampleNumer();
        //  System.out.println("New Sample No :" + newSampleNo);
        //  if (newSampleNo != null)
        {
            for (int j = 0; j < listOfCollectedSample.size(); j++) {
                TpathBs tpathBsOb = listOfCollectedSample.get(j);
                MpathSampleSuitability suitabilityObj = new MpathSampleSuitability();
                suitabilityObj.setSsId(1);
                tpathBsOb.setPsSampleSuitabilityId(suitabilityObj);
                //     tpathBsOb.setPsId(tpathBsOb.getPsId());
                DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
                Date date = new Date();
                tpathBsOb.setPsSampleNumber(dateFormat.format(date));//tpathBsNew.setPsSampleNumber(newSampleNo);
                if (tpathBsOb.getIsSampleOutsource()) {
                    //Save Outsource test details in Mpath_trn_outsource master
                    flag = true;
                } else {
                    flag = false;
                }
                tpathBsRepository.save(tpathBsOb);
                respMap.put("success", "1");
                respMap.put("msg", "Sample Collection Successful");
                smsOnSampleCollection(tenantName, tpathBsOb);
                if (flag) {
                    onTrnTestOutSource(tenantName, tpathBsOb);
                }
                if (tpathBsOb == null) {
                    respMap.put("success", "0");
                    respMap.put("msg", "Sample Collection Failed");
                }
            }// for
        }//if
        return respMap;
    }

    boolean onTrnTestOutSource(@RequestHeader("X-tenantId") String tenantName, TpathBs tpathBsNew) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            MpathTrnOutSource tos = new MpathTrnOutSource();
            TbillAgencyRate agencyRate = tbillAgencyRateRepository.findByAtAgencyIdAgencyIdAndAtMbillServiceIdServiceIdAndIsActiveTrueAndIsDeletedFalse(tpathBsNew.getPsAgencyId().getAgencyId(), tpathBsNew.getPsTestId().getMbillServiceId().getServiceId());
            //    System.out.println("agency Rate " + agencyRate);
            tos.setTosPsId(tpathBsNew);
            tos.setTosAgencyRate(agencyRate);
            tos.setIsActive(true);
            tos.setIsDeleted(false);
            tos.setCreatedBy(String.valueOf(tpathBsNew.getIsSampleOutsourceBy()));
            tos.setCreatedDate(new Date());
            tos.setLastModifiedBy(String.valueOf(tpathBsNew.getIsSampleOutsourceBy()));
            tos.setLastModifiedDate(new Date());
            mpathTrnOutSourceRepository.save(tos);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    //vijay
    @RequestMapping("addacceptedsample")
    public Map<String, String> addAcceptedSample(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBs tpathBsOb) {
        TenantContext.setCurrentTenant(tenantName);
        if (tpathBsOb != null) {
            tpathBsRepository.save(tpathBsOb);
            respMap.put("success", "1");
            respMap.put("msg", "Sample Accept Successful");
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Sample Accept Failed");
        }
        return respMap;
    }

    //vijay
    @RequestMapping("addrejectedsample")
    public Map<String, String> addRejectedSample(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBs tpathBsOb) {
        TenantContext.setCurrentTenant(tenantName);
        //    System.out.println("addrejectedsample :" + tpathBsOb);
        if (tpathBsOb != null) {
            tpathBsRepository.save(tpathBsOb);
            //    System.out.println("tpathBsOb :" + tpathBsOb);
            MpathTestResult tr = new MpathTestResult();
//need to rework...deleted below object
            tr = mpathTestResultRepository.findAllByTrPsIdPsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(tpathBsOb.getPsId());
            if (tr != null) {
                tr.setIsDeleted(true);
                //     System.out.println("tpathBsOb111 :" + tr);
                mpathTestResultRepository.save(tr);
            }
            respMap.put("success", "1");
            respMap.put("msg", "Sample Accept Successful");
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Sample Accept Failed");
        }
        return respMap;
    }

    //vijay
    @RequestMapping("addresultentrysample")
    public Map<String, String> addResultEntry(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBs tpathBsOb) {
        TenantContext.setCurrentTenant(tenantName);
        //Do not remove below code
        tpathBsOb.setPsResultEntryDate(new Date());
        tpathBsOb.setIsResultEntry(true);
        tpathBsOb.setIsResultEntryDoneBy(tpathBsOb.getIsResultEntryDoneBy());
        tpathBsOb.setIsResultEntryDoneByName(tpathBsOb.getIsResultEntryDoneByName());
        tpathBsOb.setLastModifiedDate(new Date());
        tpathBsOb.setLastModifiedByName(tpathBsOb.getLastModifiedByName());
        tpathBsOb.setLastModifiedBy(tpathBsOb.getLastModifiedBy());
        if (tpathBsOb != null) {
            tpathBsRepository.save(tpathBsOb);
            respMap.put("success", "1");
            respMap.put("msg", "Result Entry Successful Done");
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Result Entry Failed");
        }
        return respMap;

    }

    //vijay
    @RequestMapping("laborderacceptance")
    public Map<String, String> addLabOrderAcceptance(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBs tpathBsOb) {
        TenantContext.setCurrentTenant(tenantName);
        TpathBs bs = null;
        List<TpathBs> list = null;
        System.out.println("addLabOrderAcceptance :" + tpathBsOb);
        if (tpathBsOb.getIsIPD() && (tpathBsOb.getMbillIPDCharge() != null)) {
            list = tpathBsRepository.findByMbillIPDChargeIpdchargeIdEqualsAndIsActiveTrueAndIsDeletedFalse(tpathBsOb.getMbillIPDCharge().getIpdchargeId());
        } else {
            list = tpathBsRepository.findByPsBillIdBillIdAndIsActiveTrueAndIsDeletedFalse(tpathBsOb.getPsBillId().getBillId());
        }
        for (int i = 0; i < list.size(); i++) {  //this code can not be reduce
            bs = new TpathBs();
            bs = list.get(i);
            bs.setIsLabOrderAcceptanceBy(tpathBsOb.getIsLabOrderAcceptanceBy());
            bs.setIsLabOrderAcceptanceByName(tpathBsOb.getIsLabOrderAcceptanceByName());
            bs.setIsLabOrderAcceptance(true);
            bs.setPsLabOrderAcceptanceDate(new Date());
            bs.setLastModifiedDate(new Date());
            bs.setLastModifiedByName(tpathBsOb.getLastModifiedByName());
            bs.setLastModifiedBy(tpathBsOb.getLastModifiedBy());
            tpathBsRepository.save(bs);
            respMap.put("success", "1");
            respMap.put("msg", "Lab Order Accepted Successfully");
            if (bs == null) {
                respMap.put("success", "0");
                respMap.put("msg", "Lab Order Acceptance Failed");
            }
        }
        return respMap;
    }
    //vijay

    @RequestMapping("adduploadreport")
    public boolean addUploadReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBs tpathBsOb) {
        {
            TenantContext.setCurrentTenant(tenantName);
            tpathBsOb.setPsReportUploadDate(new Date());
            tpathBsOb.setIsReportUploaded(true);
            tpathBsOb.setIsReportUploadedBy(tpathBsOb.getIsReportUploadedBy());
            tpathBsOb.setIsResultEntryDoneByName(tpathBsOb.getIsReportUploadedByName());
            tpathBsOb.setLastModifiedDate(new Date());
            tpathBsOb.setLastModifiedByName(tpathBsOb.getLastModifiedByName());
            tpathBsOb.setLastModifiedBy(tpathBsOb.getLastModifiedBy());
            tpathBsOb.setIsFinalized(true);
            tpathBsOb.setIsFinalizedBy(tpathBsOb.getIsFinalizedBy());
            tpathBsOb.setIsFinalizedByName(tpathBsOb.getIsFinalizedByName());
            tpathBsOb.setPsFinalizedDate(new Date());
            //    System.out.println("tpathBsnew adduploadreport :" + tpathBsOb);
            tpathBsRepository.save(tpathBsOb);
        }
        return true;
    }

    @GetMapping
    @RequestMapping("staffbyuserId")
    public MstStaff getStaffIdByUserId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "userid", required = false) long userId) {
        TenantContext.setCurrentTenant(tenantName);
        MstStaff staff = mstStaffRepository.findByStaffUserIdUserIdAndIsActiveTrueAndIsDeletedFalse(userId);
        return staff;
    }

    /*@RequestMapping("listbyipdchargeid/{ipdchargeId}")
    public List<TpathBs> getListByIpdChargeId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ipdchargeId") Long ipdchargeId) {
        return tpathBsRepository.findByMbillIPDChargeIpdchargeIdEqualsAndIsActiveTrueAndIsDeletedFalse(ipdchargeId);
    }

   *//* @GetMapping    gayatri
    @RequestMapping("listbyipdchargeid")
    public Iterable<TpathBs> getListByIpdChargeId (@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBs tpathBsOb) {
        TpathBs bs = null;
        List<TpathBs> list = null;
        System.out.println("addLabOrderAcceptance :" + tpathBsOb);
        if (tpathBsOb.getIsIPD() && (tpathBsOb.getMbillIPDCharge()!= null)) {
            list = tpathBsRepository.findByMbillIPDChargeIpdchargeIdEqualsAndIsActiveTrueAndIsDeletedFalse(tpathBsOb.getMbillIPDCharge().getIpdchargeId());
        }
        return tpathBsRepository.findByMbillIPDChargeIpdchargeIdEqualsAndIsActiveTrueAndIsDeletedFalse(mbillipdcharge);
    }*/

    public String smsOnSampleCollection(@RequestHeader("X-tenantId") String tenantName, TpathBs object) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            if (Propertyconfig.getSmsApi()) {
                if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                } else {
                    if (object.getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserMobile() != null) {
                        sendsms.sendmessage(String.valueOf(object.getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserMobile()), "Sample Id:" + object.getPsSampleNumber() + "Success: Sample Collected.");
                    } else if (object.getPsBillId().getBillAdmissionId().getAdmissionPatientId().getPatientUserId().getUserMobile() != null) {
                        sendsms.sendmessage(String.valueOf(object.getPsBillId().getBillAdmissionId().getAdmissionPatientId().getPatientUserId().getUserMobile()), "Sample Id:" + object.getPsSampleNumber() + "Success: Sample Collected.");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return "success";
    }

    public String smsOnReportFinalized(MpathTestResult object) {
        try {
            if (Propertyconfig.getSmsApi()) {
                if (object.getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserMobile() != null) {
                    if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                        String msg = "Your Pathology " + object.getTrPsId().getPsTestId().getTestPrintName() + " report has been generated. Request you to collect the same.";
                        sendsms.sendmessage1(object.getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserMobileCode() + object.getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserMobile(), msg);
                    } else {
                        sendsms.sendmessage(String.valueOf(object.getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserMobile()), "Test Id:" + object.getTrPsId().getPsTestId().getTestId() + "\nTest Name:" + object.getTrPsId().getPsTestId().getTestPrintName() + "\nSuccess: Your test report is ready.");
                    }
                } else if (object.getTrPsId().getMbillIPDCharge().getChargeAdmissionId().getAdmissionPatientId().getPatientUserId().getUserMobile() != null) {
                    sendsms.sendmessage(String.valueOf(object.getTrPsId().getMbillIPDCharge().getChargeAdmissionId().getAdmissionPatientId().getPatientUserId().getUserMobile()), "Test Id:" + object.getTrPsId().getPsTestId().getTestId() + "\nTest Name:" + object.getTrPsId().getPsTestId().getTestPrintName() + "\nSuccess: Your test report is ready.");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            if (Propertyconfig.getEmailApi()) {
                if (object.getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserEmail() != null) {
                    String msg = "";
                    String subject = "";
                    if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                        subject = "EHMS Portal - Lab Report Generated";
                        msg = "Dear " + object.getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserTitleId().getTitleName() + ". " + object.getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserFirstname() + " " + object.getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserLastname() + " <br><br>" +
                                " Your Pathology " + object.getTrPsId().getPsTestId().getTestPrintName() + " report has been generated and is available in the eHMS portal.<br><br>" +
                                " <a href=\"https://ehmstp.healthspring.in/\">Click to view details</a><br><br><br>" +
                                " Please share your feedback on clicking below URL <br><br>" +
                                " <a href=\"https://sprw.io/stt-4074cc\">https://sprw.io/stt-4074cc</a><br><br>" +
                                " <br><br> Thanks And Regards " +
                                " <br> eHMS Admin";
                        Emailsend emailsend1 = new Emailsend();
                        emailsend1.sendMAil1(object.getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserEmail(), msg, subject);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "success";
    }

    @RequestMapping("testcancel")
    public boolean addTestCancel(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBs tpathBsOb) {
        TenantContext.setCurrentTenant(tenantName);
        {
            //  System.out.println("test cancel :" + tpathBsOb);
            tpathBsOb.setIsLabOrderCancel(true);
            tpathBsOb.setPsLabOrderCancelDate(new Date());
            tpathBsOb.setPsTestCancelReason(tpathBsOb.getPsTestCancelReason());
            //     System.out.println("test cancel1111 :" + tpathBsOb);
            tpathBsRepository.save(tpathBsOb);
        }
        return true;
    }

    @RequestMapping(value = "/reduceitemqty", method = RequestMethod.POST)
    public Map<String, String> reduceItemQty(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject obj) {
        TenantContext.setCurrentTenant(tenantName);
        String obiIdStr = String.valueOf(obj.get("obiId"));
        String obiItemQuantityStr = String.valueOf(obj.get("obiItemQuantity"));
        //    System.out.println("reduceitemqty :obiId" + obiIdStr + " obiItemQuantity :" + obiItemQuantityStr);
        //    System.out.println("reduceitemqty :" + obj.get("obiItemQuantity") + ":" + tinvOpeningBalanceItemRepository.findAllByObiIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(obiIdStr)));
        TinvOpeningBalanceItem tinvOpeningBalanceItem = tinvOpeningBalanceItemRepository.findAllByObiIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(obiIdStr));
        if (tinvOpeningBalanceItem != null) {
            tinvOpeningBalanceItem.setObiItemQuantity(Integer.parseInt(obiItemQuantityStr));
            tinvOpeningBalanceItem.setObiId(Integer.parseInt(obiIdStr));
            tinvOpeningBalanceItemRepository.save(tinvOpeningBalanceItem);
            respMap.put("msg", "Quantity Reduce Successfully");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Item is not Available");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("getlabtest")
    public List<TpathBs> getLabTest(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject woNumber) {
        TenantContext.setCurrentTenant(tenantName);
        Object jsonStr = woNumber.get("wonumber");
        //    System.out.println("json :" + woNumber);
        //     System.out.println("jsonStr :" + jsonStr);
        if (woNumber != null) {
            return tpathBsRepository.findAllByPsBillIdBillWorkOrderNumberAndIsActiveTrueAndIsDeletedFalse(String.valueOf(jsonStr));
        } else {
            return null;
        }

    }

    @RequestMapping("labitemcount")
    // @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity labItemCount(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject json) {
        TenantContext.setCurrentTenant(tenantName);
        //     System.out.println("labitemcount :" + json + "  :..0 :" + json.get("dateFrom"));
        String query = "SELECT bs.ps_test_id,ts.test_name,count(bs.ps_test_id) as testcount FROM tpath_bs bs,mpath_test ts where  bs.ps_test_id=ts.test_id and (date(bs.created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "') and bs.ps_test_id is not null group by bs.ps_test_id";
        String[] col = {"testId", "testName", "testCount"};
        return ResponseEntity.ok(createJSONObject.createJsonObj(col, query));
    }

    @RequestMapping("labdashboard")
    // @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity labDashboard(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject json) {
        TenantContext.setCurrentTenant(tenantName);
        //   System.out.println("labDashboard :" + json);
        String query = "select loGeneratedTable.loGenerated,loGeneratedTestCountTable.loGeneratedTestCount,loGeneratedInSameUnitTable.loGeneratedInSameUnit,loAcceptanceTable.loAcceptance,loAcceptanceTestCountTable.loAcceptanceTestCount,loNotAcceptanceTable.loNotAcceptance,loNotAcceptanceTestCountTable.loNotAcceptanceTestCount,sampleCollectedTable.sampleCollected,sampleNotCollectedTable.sampleNotCollected,sampleAcceptedTable.sampleAccepted,sampleNotAcceptedTable.sampleNotAccepted,sampleRejectedTable.sampleRejected,resultEntryDoneTable.resultEntryDone,resultEntryNotDoneTable.resultEntryNotDone,labOrderCancelTable.labOrderTestCancel,sampleOutSourceTable.sampleOutSource,testForwardedTable.testForwarded,testReportApporvedTable.testReportApporved from " + "(select count(distinct(ps_bill_id)) as loGenerated from tpath_bs where ps_test_id is not null and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as loGeneratedTable," + "(select count(*) as loGeneratedTestCount from tpath_bs where ps_test_id is not null and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as loGeneratedTestCountTable," + "(select count(distinct(ps_bill_id)) as loGeneratedInSameUnit from tpath_bs where ps_test_id is not null and is_performed_by_unit_id=" + json.get("isPerformedByUnitId") + " and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as loGeneratedInSameUnitTable," + "(select count(distinct(ps_bill_id)) as loAcceptance from tpath_bs where ps_test_id is not null and is_lab_order_acceptance=true and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as loAcceptanceTable," + "(select count(*) as loAcceptanceTestCount from tpath_bs where ps_test_id is not null and is_lab_order_acceptance=true and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as loAcceptanceTestCountTable," + "(select count(distinct(ps_bill_id)) as loNotAcceptance from tpath_bs where ps_test_id is not null and is_lab_order_acceptance=false and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as loNotAcceptanceTable," + "(select count(*) as loNotAcceptanceTestCount from tpath_bs where ps_test_id is not null and is_lab_order_acceptance=false and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as loNotAcceptanceTestCountTable," + "(select count(*) as sampleCollected from tpath_bs where ps_test_id is not null and is_sample_collected=true and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as sampleCollectedTable," + "(select count(*) as sampleNotCollected from tpath_bs where ps_test_id is not null and is_sample_collected=false and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as sampleNotCollectedTable," + "(select count(*) as sampleAccepted from tpath_bs where ps_test_id is not null and is_sample_accepted=true and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as sampleAcceptedTable," + "(select count(*) as sampleNotAccepted from tpath_bs where ps_test_id is not null and is_sample_accepted=false and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as sampleNotAcceptedTable," + "(select count(*) as sampleRejected from tpath_bs where ps_test_id is not null and is_sample_rejected=true and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as sampleRejectedTable," + "(select count(*) as resultEntryDone from tpath_bs where ps_test_id is not null and is_result_entry=true and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as resultEntryDoneTable," + "(select count(*) as resultEntryNotDone from tpath_bs where ps_test_id is not null and is_result_entry=false and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as resultEntryNotDoneTable," + "(select count(*) as labOrderTestCancel from tpath_bs where ps_test_id is not null and is_lab_order_cancel=true and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as labOrderCancelTable," + "(select count(*) as sampleOutSource from tpath_bs where ps_test_id is not null and is_sample_outsource=true and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as sampleOutSourceTable," + "(select count(*) as testForwarded from tpath_bs where ps_test_id is not null and is_test_forwarded=true and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as testForwardedTable," + "(select count(*) as testReportApporved from tpath_bs where ps_test_id is not null and is_finalized=true and is_deleted=0 and (date(created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')) as testReportApporvedTable";
        String[] col = {"loGenerated", "loGeneratedTestCount", "loGeneratedInSameUnit", "loAcceptance", "loAcceptanceTestCount", "loNotAcceptance", "loNotAcceptanceTestCount", "sampleCollected", "sampleNotCollected", "sampleAccepted", "sampleNotAccepted", "sampleRejected", "resultEntryDone", "resultEntryNotDone", "labOrderTestCancel", "sampleOutSource", "testForwarded", "testReportApporved"};
        return ResponseEntity.ok(createJSONObject.createJsonObj(col, query));
    }

    @RequestMapping("pendingreportcount")
    public ArrayList<JSONObject> pendingReportCount(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBsFilter json) {
        TenantContext.setCurrentTenant(tenantName);
        //   System.out.println("pendingReportcount :" + json);
        MstUnit unit = new MstUnit();
        MbillSubGroup subGroup = new MbillSubGroup();
        Date dateFrom = new Date();
        Date dateTo = new Date();
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            c.setTime(sdf.parse(json.getDateTo()));
            c.add(Calendar.DAY_OF_MONTH, 1);
            String newDateTo = sdf.format(c.getTime());
            dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(newDateTo);
            dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(json.getDateFrom());
        } catch (Exception e) {
        }
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        ArrayList<JSONObject> sgList = new ArrayList<JSONObject>();
        StringTokenizer unitSt = new StringTokenizer(json.getMultiUnit(), ",");
        while (unitSt.hasMoreTokens()) {
            JSONObject jsonStr = new JSONObject();
            JSONObject jsonSg = new JSONObject();
            long unitId = Long.parseLong(unitSt.nextToken());
            if (unit.getUnitId() <= 0) {
                unit = mstUnitRepository.getById(unitId);
            } else if (unit.getUnitId() != unitId) {
                unit = mstUnitRepository.getById(unitId);
            }
            jsonStr.put("unitId", unit.getUnitId());
            jsonStr.put("unitName", unit.getUnitName());
            StringTokenizer subGroupSt = new StringTokenizer(json.getSubGroup(), ",");
            {
                while (subGroupSt.hasMoreTokens()) {
                    long subGroupId = Long.parseLong(subGroupSt.nextToken());
                    int pendingReportCount = tpathBsRepository.findByPendingReportCount(String.valueOf(unit.getUnitId()), Boolean.parseBoolean(json.getIsFinalized()), subGroupId, dateFrom, dateTo);
                    //     String[] col = {"loGenerated", "loGeneratedTestCount", "loGeneratedInSameUnit", "loAcceptance", "loAcceptanceTestCount", "loNotAcceptance", "loNotAcceptanceTestCount", "sampleCollected", "sampleNotCollected", "sampleAccepted", "sampleNotAccepted", "sampleRejected", "resultEntryDone", "resultEntryNotDone", "labOrderTestCancel", "sampleOutSource", "testForwarded", "testReportApporved"};
                    jsonSg = new JSONObject();
                    if (unit.getUnitId() == unitId) {
                        //       System.out.println("  unit :" + unit.getUnitName() + "  subgroup :" + subGroup.getSgName() + "  count :" + pendingReportCount);
                        if (subGroup.getSgId() <= 0) {
                            subGroup = mbillSubGroupRepository.getById(subGroupId);
                        } else if (subGroup.getSgId() != subGroupId) {
                            subGroup = mbillSubGroupRepository.getById(subGroupId);
                        }
                        //  System.out.println("  unit :" + unit.getUnitName()+"  subgroup :" + subGroup.getSgName() + "  count :" + pendingReportCount);
                        jsonSg.put("subGroupId", subGroup.getSgId());
                        jsonSg.put("subGroupName", subGroup.getSgName());
                        jsonSg.put("pendingReportCount", pendingReportCount);
                        sgList.add(jsonSg);
                    }
                }
                jsonStr.put("subgroup", sgList);
                list.add(jsonStr);
                sgList = new ArrayList<>();
            }

        }
        return list;
    }

    //EMR dignosis view
    @GetMapping
    @RequestMapping("get_by_patientid")
    public Iterable<TpathBs> get_by_patientid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = " (SELECT ps.*  " +
                "FROM tpath_bs ps  " +
                "LEFT JOIN tbill_bill tb ON tb.bill_id = ps.ps_bill_id  " +
                "LEFT JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id  " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id  " +
                "WHERE tb.ipd_bill = FALSE and mp.patient_id = " + qString + " )  " +
                "UNION All  " +
                "(SELECT ps.*  " +
                "FROM tpath_bs ps  " +
                "LEFT JOIN tbill_bill tb ON tb.bill_id = ps.ps_bill_id  " +
                "LEFT JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id  " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id  " +
                "WHERE ps.isipd = true and mp.patient_id =" + qString + " AND ps.ps_cs_id IS NULL)  " +
                "UNION ALL  " +
                "(SELECT ps.*  " +
                "FROM tpath_bs ps  " +
                "LEFT JOIN tbill_bill tb ON tb.bill_id = ps.ps_bill_id  " +
                "LEFT JOIN ipd_charges_service cs ON cs.cs_id = ps.ps_cs_id  " +
                "LEFT JOIN mbill_ipd_charge ic ON ic.ipdcharge_id = cs.cs_charge_id  " +
                "LEFT JOIN trn_admission ta ON ta.admission_id = ic.charge_admission_id  " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id  " +
                "WHERE ps.isipd = TRUE and mp.patient_id = " + qString + " AND ps.ps_cs_id IS NOT NULL) ";
        String CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        Query += "  limit " + ((Integer.parseInt(page) - 1) * Integer.parseInt(size)) + "," + Integer.parseInt(size);
        List<TpathBs> allList = entityManager.createNativeQuery(Query, TpathBs.class).getResultList();
//            return tbillServiceRadiologyRepository.findAllByBsrCsIdCsDateBetweenAndBsrCsIdCsChargeIdTbillUnitIdUnitIdAndBsrIpdBillTrue(StartDate, EndDate, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        BigInteger count = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        if (allList.size() > 0) {
            allList.get(0).setCount(count.longValue());
        }
        return allList;
//        return tpathBsRepository.findAllByPsBillIdBillVisitIdVisitPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @RequestMapping("onlyfinalizedtpathbs")
    public boolean makeOnlyTpathFinalized(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTestResult trObj) {
        TenantContext.setCurrentTenant(tenantName);
        //   System.out.println("finalizereport :" + trObj);
        TpathBs tpathBsOb = new TpathBs();
        if (trObj != null) {
            tpathBsOb = trObj.getTrPsId();
            tpathBsOb.setIsFinalized(true);
            //     System.out.println("tpathBs vijay trObj :" + tpathBsOb);
            tpathBsRepository.save(tpathBsOb);
            TpathBs temp = tpathBsRepository.getById(tpathBsOb.getPsId());
            if (!temp.getIsFinalized()) {
                temp.setIsFinalized(true);
                tpathBsRepository.save(temp);
            }
            TpathBsController tpath = new TpathBsController();
            tpath.smsOnReportFinalized(trObj);

        }
        return true;
    }

    @GetMapping
    @RequestMapping("getfilebasepath")
    public Map<String, String> getbasepath(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("BasePath" + Propertyconfig.getPatientFiles());
        respMap.put("path", Propertyconfig.getPatientFiles());
        return respMap;
    }

    @RequestMapping("updateteststatus")
    public Map<String, String> updateTestSatus(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBs tpathBs) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, String> respMap1 = new HashMap<String, String>();
        if (tpathBs.getPsBillId() != null) {
            tpathBs.setIsEmergency(tpathBs.getIsEmergency());
            tpathBsRepository.save(tpathBs);
            respMap1.put("success", "1");
            respMap1.put("msg", "Status Updated Successfully");
            return respMap1;
        } else {
            respMap1.put("success", "0");
            respMap1.put("msg", "Failed To Add Null Field");
            return respMap1;
        }
    }

    @RequestMapping("pendingreportcountnew")
    public ArrayList<JSONObject> pendingReportCountNew(@RequestHeader("X-tenantId") String tenantName, @RequestBody TpathBsFilter json) {
        TenantContext.setCurrentTenant(tenantName);
        //   System.out.println("pendingReportcount :" + json);
        MstUnit unit = new MstUnit();
        MbillSubGroup subGroup = new MbillSubGroup();
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        ArrayList<JSONObject> sgList = new ArrayList<JSONObject>();
        JSONObject jsonStr = new JSONObject();
        JSONObject jsonSg = new JSONObject();
        unit = mstUnitRepository.getById(Long.parseLong(json.getUnitId()));
        jsonStr.put("unitId", unit.getUnitId());
        jsonStr.put("unitName", unit.getUnitName());
        StringTokenizer subGroupSt = new StringTokenizer(json.getSubGroup(), ",");
        {
            while (subGroupSt.hasMoreTokens()) {
                String subGroupName = subGroupSt.nextToken();
                int pendingReportCount = tpathBsRepository.findByPendingTestReportCount(subGroupName, Long.parseLong(json.getUnitId()));
                //     String[] col = {"loGenerated", "loGeneratedTestCount", "loGeneratedInSameUnit", "loAcceptance", "loAcceptanceTestCount", "loNotAcceptance", "loNotAcceptanceTestCount", "sampleCollected", "sampleNotCollected", "sampleAccepted", "sampleNotAccepted", "sampleRejected", "resultEntryDone", "resultEntryNotDone", "labOrderTestCancel", "sampleOutSource", "testForwarded", "testReportApporved"};
                jsonSg = new JSONObject();
                jsonSg.put("subGroupName", subGroupName);
                jsonSg.put("pendingReportCount", pendingReportCount);
                sgList.add(jsonSg);
            }
        }
        jsonStr.put("subgroup", sgList);
        list.add(jsonStr);
        sgList = new ArrayList<>();
        return list;
    }

    @RequestMapping("labreport")
    public List<LabReportDao> LabReport(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "SELECT ifnull(mp.patient_mr_no,'') AS PatientId,  "
                + "ifnull(CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname,' (',mu.user_age,'-',mg.gender_name,' )'),'') AS PatientName, ifnull(mre.re_name,'') AS Referral, IFNULL(t.is_performed_by_unit_name,'') AS Organization, IFNULL(mtest.test_code,'') AS TestCode,IFNULL(mtest.test_name,'') AS TestName, "
                + "IFNULL(mparam.parameter_name,'') AS ParameterName, IFNULL(mr.result_value,'') AS ResultValue,  "
                + "IFNULL(mtr.tr_comments,'') AS TestComment " + "FROM tpath_bs t "
                + "INNER JOIN tbill_bill tb on t.ps_bill_id = tb.bill_id "
                + "LEFT JOIN mst_visit mv on tb.bill_visit_id = mv.visit_id "
                + "LEFT JOIN mst_referring_entity mre on mv.refer_by = mre.re_id "
                + "INNER JOIN mst_patient mp on mv.visit_patient_id = mp.patient_id "
                + "INNER JOIN mst_user mu on mp.patient_user_id = mu.user_id "
                + "LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id "
                + "LEFT JOIN mst_gender mg on mu.user_gender_id = mg.gender_id "
                + "LEFT JOIN mpath_test mtest ON t.ps_test_id = mtest.test_id "
                + "LEFT JOIN mpath_test_result mtr ON t.ps_id = mtr.tr_ps_id "
                + "LEFT JOIN mpath_result mr ON mtr.tr_id = mr.result_test_result_id "
                + "LEFT JOIN rpath_test_parameter rtp ON mr.result_test_parameter_id = rtp.tp_id "
                + "LEFT JOIN mpath_parameter mparam ON rtp.tp_parameter_id = mparam.parameter_id "
                + "WHERE t.isipd = 0 and t.is_active = 1 AND t.is_deleted = 0 " + "UNION ALL  "
                + "SELECT ifnull(mp.patient_mr_no,'') AS PatientId,  "
                + "ifnull(CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname,' (',mu.user_age,'-',mg.gender_name,' )'),'') AS PatientName, ifnull(mre.re_name,'') AS Referral, IFNULL(t.is_performed_by_unit_name,'') AS Organization, IFNULL(mtest.test_code,'') AS TestCode,IFNULL(mtest.test_name,'') AS TestName, "
                + "IFNULL(mparam.parameter_name,'') AS ParameterName, IFNULL(mr.result_value,'') AS ResultValue,  "
                + "IFNULL(mtr.tr_comments,'') AS TestComment " + "FROM tpath_bs t "
                + "INNER JOIN tbill_bill tb on t.ps_bill_id = tb.bill_id "
                + "LEFT JOIN trn_admission ta on tb.bill_admission_id = ta.admission_id "
                + "LEFT JOIN mst_referring_entity mre on ta.admission_re_id = mre.re_id "
                + "INNER JOIN mst_patient mp on ta.admission_patient_id = mp.patient_id "
                + "INNER JOIN mst_user mu on mp.patient_user_id = mu.user_id "
                + "LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id "
                + "LEFT JOIN mst_gender mg on mu.user_gender_id = mg.gender_id "
                + "LEFT JOIN mpath_test mtest ON t.ps_test_id = mtest.test_id "
                + "LEFT JOIN mpath_test_result mtr ON t.ps_id = mtr.tr_ps_id "
                + "LEFT JOIN mpath_result mr ON mtr.tr_id = mr.result_test_result_id "
                + "LEFT JOIN rpath_test_parameter rtp ON mr.result_test_parameter_id = rtp.tp_id "
                + "LEFT JOIN mpath_parameter mparam ON rtp.tp_parameter_id = mparam.parameter_id "
                + "WHERE t.isipd = 1 AND t.ps_bill_id IS NOT NULL and t.is_active = 1 AND t.is_deleted = 0 "
                + "UNION ALL  " + "SELECT ifnull(mp.patient_mr_no,'') AS PatientId,  "
                + "ifnull(CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname,' (',mu.user_age,'-',mg.gender_name,' )'),'') AS PatientName, ifnull(mre.re_name,'') AS Referral, IFNULL(t.is_performed_by_unit_name,'') AS Organization, IFNULL(mtest.test_code,'') AS TestCode,IFNULL(mtest.test_name,'') AS TestName, "
                + "IFNULL(mparam.parameter_name,'') AS ParameterName, IFNULL(mr.result_value,'') AS ResultValue,  "
                + "IFNULL(mtr.tr_comments,'') AS TestComment " + "FROM tpath_bs t "
                + "INNER JOIN mbill_ipd_charge mic on t.mbillipdcharge = mic.ipdcharge_id "
                + "LEFT JOIN trn_admission ta on mic.charge_admission_id = ta.admission_id "
                + "LEFT JOIN mst_referring_entity mre on ta.admission_re_id = mre.re_id "
                + "INNER JOIN mst_patient mp on ta.admission_patient_id = mp.patient_id "
                + "INNER JOIN mst_user mu on mp.patient_user_id = mu.user_id "
                + "LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id "
                + "LEFT JOIN mst_gender mg on mu.user_gender_id = mg.gender_id "
                + "LEFT JOIN mpath_test mtest ON t.ps_test_id = mtest.test_id "
                + "LEFT JOIN mpath_test_result mtr ON t.ps_id = mtr.tr_ps_id "
                + "LEFT JOIN mpath_result mr ON mtr.tr_id = mr.result_test_result_id "
                + "LEFT JOIN rpath_test_parameter rtp ON mr.result_test_parameter_id = rtp.tp_id "
                + "LEFT JOIN mpath_parameter mparam ON rtp.tp_parameter_id = mparam.parameter_id "
                + "WHERE t.isipd = 1 AND t.ps_bill_id IS NULL  and t.is_active = 1 AND t.is_deleted = 0";
        System.out.println("quey" + query);
        List<Object[]> response = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        HashMap<String, LabReportDao> list = new HashMap<>();
        for (int i = 0; i < response.size(); i++) {
            String patientId = "" + response.get(i)[0];
            if (list.containsKey(patientId)) {
                LabReportDao labReportDaos = list.get(patientId);
                List<PatientTest> patientTest = labReportDaos.getPatientTest();
                List<TestResult> testResult = null;
                boolean isAdd = true;
                for (int j = 0; j < patientTest.size(); j++) {
                    if (patientTest.get(j).getTestCode().equals("" + response.get(i)[4])) {
                        testResult = patientTest.get(j).getTestResult();
                        testResult.add(new TestResult("" + response.get(i)[6], "" + response.get(i)[7],
                                "" + response.get(i)[8]));
                        patientTest.get(j).setTestResult(testResult);
                        isAdd = false;
                    }
                }
                if (isAdd) {
                    testResult = new ArrayList<TestResult>();
                    testResult.add(
                            new TestResult("" + response.get(i)[6], "" + response.get(i)[7], "" + response.get(i)[8]));
                    patientTest.add(new PatientTest("" + response.get(i)[4], "" + response.get(i)[5], testResult));
                }
                list.put(patientId, labReportDaos);

            } else {
                LabReportDao labReportDaos;
                List<PatientTest> patientTest = new ArrayList();
                List<TestResult> testResult = null;
                boolean isAdd = true;
                for (int j = 0; j < patientTest.size(); j++) {
                    if (patientTest.get(j).getTestCode().equals("" + response.get(i)[4])) {
                        testResult = patientTest.get(j).getTestResult();
                        testResult.add(new TestResult("" + response.get(i)[6], "" + response.get(i)[7],
                                "" + response.get(i)[8]));
                        patientTest.get(j).setTestResult(testResult);
                        isAdd = false;
                    }
                }
                if (isAdd) {
                    testResult = new ArrayList<TestResult>();
                    testResult.add(
                            new TestResult("" + response.get(i)[6], "" + response.get(i)[7], "" + response.get(i)[8]));
                    patientTest.add(new PatientTest("" + response.get(i)[4], "" + response.get(i)[5], testResult));
                }
                labReportDaos = new LabReportDao("" + response.get(i)[0], "" + response.get(i)[1],
                        "" + response.get(i)[2], "" + response.get(i)[3], patientTest);
                list.put(patientId, labReportDaos);

            }
        }
        return (new ArrayList<LabReportDao>(list.values()));

    }

    @RequestMapping("labshort")
    public List<LabReportDao> LabShort(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "SELECT " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname, ' (', mg.gender_name,'- ',mu.user_age,')'),'') AS PatientName, IFNULL(tb.bill_work_order_number,'') AS AccessionNo, IFNULL(tb.bill_date,'') AS AccessionDate," +
                " IFNULL(mtest.test_name,'') AS TestName,  ifnull(mre.re_name,'') AS Referral," +
                " IFNULL(t.is_performed_by_unit_name,'') AS SOURCE1, IFNULL(t.ps_remark,'') AS Remark,mp.patient_id" +
                " FROM tpath_bs t" +
                " INNER JOIN tbill_bill tb ON t.ps_bill_id = tb.bill_id" +
                " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id" +
                " LEFT JOIN mst_referring_entity mre ON mv.refer_by = mre.re_id" +
                " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id" +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                " LEFT JOIN mst_gender mg on mu.user_gender_id = mg.gender_id" +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id = mtest.test_id" +
                " WHERE t.isipd=0 and t.is_active = 1 AND t.is_deleted = 0" +
                " UNION ALL" +
                " SELECT " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname, ' (', mg.gender_name,'- ',mu.user_age,')'),'') AS PatientName, IFNULL(tb.bill_work_order_number,'') AS AccessionNo, IFNULL(tb.bill_date,'') AS AccessionDate," +
                " IFNULL(mtest.test_name,'') AS TestName,  ifnull(mre.re_name,'') AS Referral," +
                " IFNULL(t.is_performed_by_unit_name,'') AS SOURCE1, IFNULL(t.ps_remark,'') AS Remark,mp.patient_id" +
                " FROM tpath_bs t" +
                " INNER JOIN tbill_bill tb ON t.ps_bill_id = tb.bill_id" +
                " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id" +
                " LEFT JOIN mst_referring_entity mre ON ta.admission_re_id = mre.re_id" +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id" +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                " LEFT JOIN mst_gender mg on mu.user_gender_id = mg.gender_id" +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id = mtest.test_id" +
                " WHERE t.isipd=1 AND t.ps_bill_id IS NOT NULL";
        System.out.println("quey" + query);
        List<Object[]> response = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        HashMap<String, LabReportDao> list = new HashMap<>();
        for (int i = 0; i < response.size(); i++) {
            String patientId = "" + response.get(i)[7];
            if (list.containsKey(patientId)) {
                LabReportDao labReportDaos = list.get(patientId);
                List<PatientTest> patientTest = labReportDaos.getPatientTest();
                patientTest.add(new PatientTest("" + response.get(i)[3], "" + response.get(i)[5], "" + response.get(i)[2], "" + response.get(i)[1], "" + response.get(i)[4], "" + response.get(i)[6]));
                labReportDaos.setPatientTest(patientTest);
                list.put(patientId, labReportDaos);

            } else {
                LabReportDao labReportDaos;
                List<PatientTest> patientTest = new ArrayList();
                patientTest.add(new PatientTest("" + response.get(i)[3], "" + response.get(i)[5], "" + response.get(i)[2], "" + response.get(i)[1], "" + response.get(i)[4], "" + response.get(i)[6]));
                labReportDaos = new LabReportDao("" + response.get(i)[0], patientTest);
                list.put(patientId, labReportDaos);

            }
        }
        return (new ArrayList<LabReportDao>(list.values()));
    }

    @RequestMapping("delete/byCsId/{psCsId}")
    public Map<String, String> deleteTpathByCsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psCsId") Long psCsId) {
        TenantContext.setCurrentTenant(tenantName);
        TpathBs tpathBsObj = tpathBsRepository.findByPsCsIdCsIdEqualsAndIsActiveTrueAndIsDeletedFalse(psCsId);
        if (tpathBsObj != null) {
            tpathBsObj.setIsDeleted(true);
            tpathBsRepository.save(tpathBsObj);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("cancel/byCsId/{psCsId}")
    public Map<String, String> cancelTpathByCsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psCsId") Long psCsId, @RequestBody TpathBs tpathBs) {
        TenantContext.setCurrentTenant(tenantName);
        TpathBs tpathBsObj = tpathBsRepository.findByPsCsIdCsIdEqualsAndIsActiveTrueAndIsDeletedFalse(psCsId);
        if (tpathBsObj != null && tpathBs != null) {
            tpathBsObj.setIsLabOrderCancel(true);
            tpathBsObj.setPsTestCancelReason(tpathBs.getPsTestCancelReason());
            tpathBsObj.setPsLabOrderCancelDate(new Date());
            tpathBsRepository.save(tpathBsObj);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("bycsid/{csId}")
    public TpathBs getDataByCsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("csId") Long csId) {
        TenantContext.setCurrentTenant(tenantName);
        TpathBs tpathBs = tpathBsRepository.findByPsCsIdCsIdEqualsAndIsActiveTrueAndIsDeletedFalse(csId);
        return tpathBs;
    }

    @RequestMapping("prevtesthistory/{patientvisitId}/{testId}")
    public String getPrevtestHistory(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientvisitId") Long patientvisitId, @PathVariable("testId") Long testId) {
        TenantContext.setCurrentTenant(tenantName);
        String values = "";
        String values1 = "";
        String values2 = "";
// OPD
        String query1 = "SELECT  max(mtr.tr_id), bs.ps_id" +
                " FROM tpath_bs bs" +
                " INNER JOIN tbill_bill tb ON tb.bill_id = bs.ps_bill_id " +
                " left JOIN mst_visit mv ON mv.visit_id= tb.bill_visit_id" +
                " inner JOIN mst_patient msp ON msp.patient_id= mv.visit_patient_id" +
                " inner JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id" +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 " +
                " AND mv.visit_patient_id = " + patientvisitId + " AND mt.test_id = " + testId + "  GROUP BY bs.ps_id";
        System.out.println("query1" + query1);
        List<Object[]> response1 = (List<Object[]>) entityManager.createNativeQuery(query1).getResultList();
        if (response1 != null) {
            int i = 0;
            for (Object[] objtemp : response1) {
                values += "" + objtemp[0];
                if (i < response1.size() - 1) {
                    values += ',';
                    i++;
                }
            }
            System.out.println("values " + values);
        }
// IPD Charge
        String query2 = "SELECT  max(mtr.tr_id), bs.ps_id" +
                " FROM tpath_bs bs" +
                " INNER JOIN mbill_ipd_charge mic ON mic.ipdcharge_id = bs.mbillipdcharge" +
                " left JOIN trn_admission ta ON ta.admission_id = mic.charge_admission_id" +
                " inner JOIN mst_patient msp ON msp.patient_id= ta.admission_patient_id" +
                " inner JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id" +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 " +
                " AND ta.admission_patient_id = " + patientvisitId + " AND mt.test_id = " + testId + "  GROUP BY bs.ps_id";
        System.out.println("query2" + query2);
        List<Object[]> response2 = (List<Object[]>) entityManager.createNativeQuery(query2).getResultList();
        HashSet<Integer> h = new HashSet<Integer>();
        if (response2 != null) {
            int i = 0;
            for (Object[] objtemp2 : response2) {
                values1 += "" + objtemp2[0];
                h.add(Integer.parseInt("" + objtemp2[0]));
                if (i < response2.size() - 1) {
                    values1 += ',';
                    i++;
                }
            }
            System.out.println("values1 " + values1);
        }
// IPD Bill
        String query3 = "SELECT  max(mtr.tr_id), bs.ps_id" +
                " FROM tpath_bs bs" +
                " INNER JOIN tbill_bill tb ON tb.bill_id = bs.ps_bill_id" +
                " left JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id" +
                " inner JOIN mst_patient msp ON msp.patient_id=ta.admission_patient_id" +
                " inner JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id" +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 AND bs.isipd = 1 AND tb.ipd_bill=1" +
                " AND ta.admission_patient_id = " + patientvisitId + " AND mt.test_id = " + testId + "  GROUP BY bs.ps_id";
        System.out.println("query3" + query3);
        List<Object[]> response3 = (List<Object[]>) entityManager.createNativeQuery(query3).getResultList();
        if (response3 != null) {
            int i = 0;
            for (Object[] objtemp3 : response3) {
                if (!h.contains(Integer.parseInt("" + objtemp3[0]))) {
                    values2 += "" + objtemp3[0];
                    if (i < response3.size() - 1) {
                        values2 += ',';
                        i++;
                    }
                }
            }
            values2 = (values2 == null) ? null : values2.replaceAll(",$", "");
            System.out.println("values2 " + values2);
        }
        String query = "";
        String queryOPD = " SELECT  mt.test_id,bs.ps_finalized_date,mt.test_name,mp.parameter_name,mr.result_value, msp.patient_mr_no " +
                " FROM tpath_bs bs " +
                " INNER JOIN tbill_bill tb ON tb.bill_id = bs.ps_bill_id " +
                " left JOIN mst_visit mv ON mv.visit_id= tb.bill_visit_id " +
                " inner JOIN mst_patient msp ON msp.patient_id= mv.visit_patient_id " +
                " INNER JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id  " +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id " +
                " inner JOIN mpath_result mr ON mr.result_test_result_id= mtr.tr_id " +
                " inner JOIN rpath_test_parameter rtp ON mr.result_test_parameter_id = rtp.tp_id" +
                " inner JOIN mpath_parameter mp ON mp.parameter_id= rtp.tp_parameter_id" +
                " inner JOIN mpath_parameter_unit mpu ON mpu.pu_id= mp.parameter_parameter_unit_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0" +
                " AND mv.visit_patient_id = " + patientvisitId + " AND mt.test_id = " + testId + " AND mr.result_test_result_id IN (" + values + ")";
        String queryIPDCharge = " SELECT  mt.test_id,bs.ps_finalized_date,mt.test_name,mp.parameter_name,mr.result_value, msp.patient_mr_no" +
                " FROM tpath_bs bs" +
                " INNER JOIN mbill_ipd_charge mic ON mic.ipdcharge_id = bs.mbillipdcharge" +
                " left JOIN trn_admission ta ON ta.admission_id = mic.charge_admission_id" +
                " inner JOIN mst_patient msp ON msp.patient_id= ta.admission_patient_id" +
                " INNER JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id" +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id" +
                " inner JOIN mpath_result mr ON mr.result_test_result_id= mtr.tr_id" +
                " inner JOIN rpath_test_parameter rtp ON mr.result_test_parameter_id = rtp.tp_id" +
                " inner JOIN mpath_parameter mp ON mp.parameter_id= rtp.tp_parameter_id" +
                " inner JOIN mpath_parameter_unit mpu ON mpu.pu_id= mp.parameter_parameter_unit_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 AND bs.isipd=1" +
                " AND ta.admission_patient_id = " + patientvisitId + " AND mt.test_id = " + testId + " AND mr.result_test_result_id IN (" + values1 + ")";
        String queryIPDBill = " SELECT  mt.test_id,bs.ps_finalized_date,mt.test_name,mp.parameter_name,mr.result_value, msp.patient_mr_no" +
                " FROM tpath_bs bs" +
                " INNER JOIN tbill_bill tb ON tb.bill_id = bs.ps_bill_id" +
                " left JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id" +
                " inner JOIN mst_patient msp ON msp.patient_id=ta.admission_patient_id" +
                " INNER JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id" +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id" +
                " inner JOIN mpath_result mr ON mr.result_test_result_id= mtr.tr_id" +
                " inner JOIN rpath_test_parameter rtp ON mr.result_test_parameter_id = rtp.tp_id" +
                " inner JOIN mpath_parameter mp ON mp.parameter_id= rtp.tp_parameter_id" +
                " inner JOIN mpath_parameter_unit mpu ON mpu.pu_id= mp.parameter_parameter_unit_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 and tb.ipd_bill=1 AND bs.isipd=1" +
                " AND ta.admission_patient_id = " + patientvisitId + " AND mt.test_id = " + testId + " AND mr.result_test_result_id IN (" + values2 + ")";
        if (!values.isEmpty() && !values1.isEmpty() && !values2.isEmpty()) {
            query += queryOPD + " UNION ALL " + queryIPDBill + " UNION ALL " + queryIPDCharge;
        } else if (!values.isEmpty() && values1.isEmpty() && values2.isEmpty()) {
            query += queryOPD;
        } else if (!values.isEmpty() && !values1.isEmpty() && values2.isEmpty()) {
            query += queryOPD + " UNION ALL " + queryIPDCharge;
        } else if (!values.isEmpty() && !values2.isEmpty() && values1.isEmpty()) {
            query += queryOPD + " UNION ALL " + queryIPDBill;
        }
        Map<String, JSONArray> hm = new HashMap<>();
        JSONArray resultArrayResp = new JSONArray();
        if (!query.isEmpty()) {
            System.out.println("quey" + query);
            List<Object[]> response = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
            for (Object[] ob : response) {
                String key = "" + ob[1];
                if (hm.containsKey(key)) {
                    JSONArray array = hm.get(key);
                    JSONObject jObj1 = new JSONObject();
                    jObj1.put("psFinalizedDate", "" + ob[1]);
                    jObj1.put("parameterName", "" + ob[3]);
                    jObj1.put("resultValue", "" + ob[4]);
                    array.put(jObj1);
                    hm.put(key, array);
                } else {
                    JSONArray resultArray = new JSONArray();
                    JSONObject jObj = new JSONObject();
                    jObj.put("psFinalizedDate", "" + ob[1]);
                    jObj.put("parameterName", "" + ob[3]);
                    jObj.put("resultValue", "" + ob[4]);
                    resultArray.put(jObj);
                    hm.put(key, resultArray);
                }
            }
            System.out.println(hm.values());
            resultArrayResp.put(hm.values());
            return resultArrayResp.toString();
        }
        return "0";
    }

    @RequestMapping("prevtesttophistory/{patientvisitId}")
    public String getPrevtestTopHistory(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientvisitId") Long patientvisitId) {
        TenantContext.setCurrentTenant(tenantName);
        String values = "";
        String values1 = "";
        String values2 = "";
// OPD
        String query1 = "SELECT  max(mtr.tr_id), bs.ps_id" +
                " FROM tpath_bs bs" +
                " INNER JOIN tbill_bill tb ON tb.bill_id = bs.ps_bill_id " +
                " left JOIN mst_visit mv ON mv.visit_id= tb.bill_visit_id" +
                " inner JOIN mst_patient msp ON msp.patient_id= mv.visit_patient_id" +
                " inner JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id" +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 " +
                " AND mv.visit_patient_id = " + patientvisitId + "  GROUP BY bs.ps_id";
        System.out.println("query1" + query1);
        List<Object[]> response1 = (List<Object[]>) entityManager.createNativeQuery(query1).getResultList();
        if (response1 != null) {
            int i = 0;
            for (Object[] objtemp : response1) {
                values += "" + objtemp[0];
                if (i < response1.size() - 1) {
                    values += ',';
                    i++;
                }
            }
            System.out.println("values " + values);
        }
// IPD Charge
        String query2 = "SELECT  max(mtr.tr_id), bs.ps_id" +
                " FROM tpath_bs bs" +
                " INNER JOIN mbill_ipd_charge mic ON mic.ipdcharge_id = bs.mbillipdcharge" +
                " left JOIN trn_admission ta ON ta.admission_id = mic.charge_admission_id" +
                " inner JOIN mst_patient msp ON msp.patient_id= ta.admission_patient_id" +
                " inner JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id" +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 " +
                " AND ta.admission_patient_id = " + patientvisitId + "  GROUP BY bs.ps_id";
        System.out.println("query2" + query2);
        List<Object[]> response2 = (List<Object[]>) entityManager.createNativeQuery(query2).getResultList();
        HashSet<Integer> h = new HashSet<Integer>();
        if (response2 != null) {
            int i = 0;
            for (Object[] objtemp2 : response2) {
                values1 += "" + objtemp2[0];
                h.add(Integer.parseInt("" + objtemp2[0]));
                if (i < response2.size() - 1) {
                    values1 += ',';
                    i++;
                }
            }
            System.out.println("values1 " + values1);
        }
// IPD Bill
        String query3 = "SELECT  max(mtr.tr_id), bs.ps_id" +
                " FROM tpath_bs bs" +
                " INNER JOIN tbill_bill tb ON tb.bill_id = bs.ps_bill_id" +
                " left JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id" +
                " inner JOIN mst_patient msp ON msp.patient_id=ta.admission_patient_id" +
                " inner JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id" +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 AND bs.isipd = 1 AND tb.ipd_bill=1" +
                " AND ta.admission_patient_id = " + patientvisitId + "  GROUP BY bs.ps_id";
        System.out.println("query3" + query3);
        List<Object[]> response3 = (List<Object[]>) entityManager.createNativeQuery(query3).getResultList();
        if (response3 != null) {
            int i = 0;
            for (Object[] objtemp3 : response3) {
                if (!h.contains(Integer.parseInt("" + objtemp3[0]))) {
                    values2 += "" + objtemp3[0];
                    if (i < response3.size() - 1) {
                        values2 += ',';
                        i++;
                    }
                }
            }
            values2 = (values2 == null) ? null : values2.replaceAll(",$", "");
            System.out.println("values2 " + values2);
        }
        String query = "";
        String queryOPD = " SELECT  mt.test_id,bs.ps_finalized_date,mt.test_name,mp.parameter_name,mr.result_value, msp.patient_mr_no, mpr.pr_upper_value, mpr.pr_lower_value " +
                " FROM tpath_bs bs " +
                " INNER JOIN tbill_bill tb ON tb.bill_id = bs.ps_bill_id " +
                " left JOIN mst_visit mv ON mv.visit_id= tb.bill_visit_id " +
                " inner JOIN mst_patient msp ON msp.patient_id= mv.visit_patient_id " +
                " INNER JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id  " +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id " +
                " inner JOIN mpath_result mr ON mr.result_test_result_id= mtr.tr_id " +
                " inner JOIN rpath_test_parameter rtp ON mr.result_test_parameter_id = rtp.tp_id" +
                " inner JOIN mpath_parameter mp ON mp.parameter_id= rtp.tp_parameter_id" +
                " inner JOIN mpath_parameter_unit mpu ON mpu.pu_id= mp.parameter_parameter_unit_id" +
                " inner JOIN mpath_parameter_range mpr ON mpr.pr_parameter_id = mp.parameter_id" +
                " LEFT JOIN mst_gender mg ON mg.gender_id = mpr.pr_gender_id" +
                " LEFT JOIN mst_user mu ON mu.user_id = msp.patient_user_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 AND mpr.pr_gender_id = mu.user_gender_id " +
                " AND mv.visit_patient_id = " + patientvisitId + " AND mr.result_test_result_id IN (" + values + ")";
        String queryIPDCharge = " SELECT  mt.test_id,bs.ps_finalized_date,mt.test_name,mp.parameter_name,mr.result_value, msp.patient_mr_no, mpr.pr_upper_value, mpr.pr_lower_value" +
                " FROM tpath_bs bs" +
                " INNER JOIN mbill_ipd_charge mic ON mic.ipdcharge_id = bs.mbillipdcharge" +
                " left JOIN trn_admission ta ON ta.admission_id = mic.charge_admission_id" +
                " inner JOIN mst_patient msp ON msp.patient_id= ta.admission_patient_id" +
                " INNER JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id" +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id" +
                " inner JOIN mpath_result mr ON mr.result_test_result_id= mtr.tr_id" +
                " inner JOIN rpath_test_parameter rtp ON mr.result_test_parameter_id = rtp.tp_id" +
                " inner JOIN mpath_parameter mp ON mp.parameter_id= rtp.tp_parameter_id" +
                " inner JOIN mpath_parameter_unit mpu ON mpu.pu_id= mp.parameter_parameter_unit_id" +
                " inner JOIN mpath_parameter_range mpr ON mpr.pr_parameter_id = mp.parameter_id" +
                " LEFT JOIN mst_gender mg ON mg.gender_id = mpr.pr_gender_id" +
                " LEFT JOIN mst_user mu ON mu.user_id = msp.patient_user_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 AND bs.isipd=1 AND mpr.pr_gender_id = mu.user_gender_id " +
                " AND ta.admission_patient_id = " + patientvisitId + " AND mr.result_test_result_id IN (" + values1 + ")";
        String queryIPDBill = " SELECT  mt.test_id,bs.ps_finalized_date,mt.test_name,mp.parameter_name,mr.result_value, msp.patient_mr_no, mpr.pr_upper_value, mpr.pr_lower_value " +
                " FROM tpath_bs bs" +
                " INNER JOIN tbill_bill tb ON tb.bill_id = bs.ps_bill_id" +
                " left JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id" +
                " inner JOIN mst_patient msp ON msp.patient_id=ta.admission_patient_id" +
                " INNER JOIN mpath_test_result mtr ON mtr.tr_ps_id= bs.ps_id" +
                " inner JOIN mpath_test mt ON mt.test_id= bs.ps_test_id" +
                " inner JOIN mpath_result mr ON mr.result_test_result_id= mtr.tr_id" +
                " inner JOIN rpath_test_parameter rtp ON mr.result_test_parameter_id = rtp.tp_id" +
                " inner JOIN mpath_parameter mp ON mp.parameter_id= rtp.tp_parameter_id" +
                " inner JOIN mpath_parameter_unit mpu ON mpu.pu_id= mp.parameter_parameter_unit_id" +
                " inner JOIN mpath_parameter_range mpr ON mpr.pr_parameter_id = mp.parameter_id" +
                " LEFT JOIN mst_gender mg ON mg.gender_id = mpr.pr_gender_id" +
                " LEFT JOIN mst_user mu ON mu.user_id = msp.patient_user_id" +
                " WHERE bs.is_lab_order_cancel = 0 and bs.is_performed_by_unit_id=1 AND bs.is_finalized=1 AND bs.is_deleted= 0 and tb.ipd_bill=1 AND bs.isipd=1 AND mpr.pr_gender_id = mu.user_gender_id " +
                " AND ta.admission_patient_id = " + patientvisitId + " AND mr.result_test_result_id IN (" + values2 + ")";
        if (!values.isEmpty() && !values1.isEmpty() && !values2.isEmpty()) {
            query += queryOPD + " UNION ALL " + queryIPDBill + " UNION ALL " + queryIPDCharge;
        } else if (!values.isEmpty() && values1.isEmpty() && values2.isEmpty()) {
            query += queryOPD;
        } else if (!values.isEmpty() && !values1.isEmpty() && values2.isEmpty()) {
            query += queryOPD + " UNION ALL " + queryIPDCharge;
        } else if (!values.isEmpty() && !values2.isEmpty() && values1.isEmpty()) {
            query += queryOPD + " UNION ALL " + queryIPDBill;
        }
        Map<String, JSONArray> hm = new HashMap<>();
        JSONArray resultArrayResp = new JSONArray();
        if (!query.isEmpty()) {
            System.out.println("quey" + query);
            List<Object[]> response = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
            for (Object[] ob : response) {
                String key = "" + ob[1];
                if (hm.containsKey(key)) {
                    JSONArray array = hm.get(key);
                    JSONObject jObj1 = new JSONObject();
                    jObj1.put("psFinalizedDate", "" + ob[1]);
                    jObj1.put("parameterName", "" + ob[3]);
                    jObj1.put("resultValue", "" + ob[4]);
                    jObj1.put("testName", "" + ob[2]);
                    jObj1.put("parameterRange", "" + ob[7] + " - " + ob[6]);
                    array.put(jObj1);
                    hm.put(key, array);
                } else {
                    JSONArray resultArray = new JSONArray();
                    JSONObject jObj = new JSONObject();
                    jObj.put("psFinalizedDate", "" + ob[1]);
                    jObj.put("parameterName", "" + ob[3]);
                    jObj.put("resultValue", "" + ob[4]);
                    jObj.put("testName", "" + ob[2]);
                    jObj.put("parameterRange", "" + ob[7] + " - " + ob[6]);
                    resultArray.put(jObj);
                    hm.put(key, resultArray);
                }
            }
            System.out.println(hm.values());
            resultArrayResp.put(hm.values());
            return resultArrayResp.toString();
        }
        return "0";
    }

}
