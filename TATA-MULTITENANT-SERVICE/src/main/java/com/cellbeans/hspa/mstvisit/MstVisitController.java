package com.cellbeans.hspa.mstvisit;

import com.cellbeans.hspa.EmgPatientList.EmgPatientList;
import com.cellbeans.hspa.EmgPatientList.EmgPatientListRepository;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.emailutility.Emailsend;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatient.MstPatientController;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.mstpatient.SearchPatientDTO;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import com.cellbeans.hspa.smsutility.Sendsms;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.cellbeans.hspa.temrtimeline.TemrTimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Transactional
@RestController
@RequestMapping("/mst_visit")
public class MstVisitController {

    Map<String, String> respMap = new HashMap<String, String>();
    Map<String, Iterable<?>> respMap1 = new HashMap<String, Iterable<?>>();

    @Autowired
    MstVisitRepository mstVisitRepository;

    @Autowired
    EmgPatientListRepository emgPatientListRepository;

    @Autowired
    MstPatientRepository mstPatientRepository;

    @Autowired
    TemrTimelineRepository temrTimelineRepository;

    @Autowired
    MstStaffRepository mstStaffRepository;

    @Autowired
    MstPatientController mstPatientController;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Sendsms sendsms;

    @Autowired
    Emailsend emailsend;

    @Autowired
    CreateJSONObject createJSONObject;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
        TenantContext.setCurrentTenant(tenantName);
        if ((mstVisit.getVisitPatientId().getPatientUserId() != null) || (mstVisit.getVisitPatientId().getPatientId() > 0)) {
            mstVisitRepository.save(mstVisit);
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
        List<MstVisit> records;
        records = mstVisitRepository.findByVisitPatientIdPatientUserIdUserFullnameContains(key);
        automap.put("record", records);
        return automap;
    }
//    @RequestMapping("/getvisit/{key}")
//    public Map<String, Object> getvisit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") Long key) {
//        Map<String, Object> automap = new HashMap<String, Object>();
//        MstVisit records = mstVisitRepository.findByVisitIdIsActiveTrueAndIsDeletedFalse(key);
//        automap.put("record", records);
//        return automap;
//    }

    @RequestMapping("byid/{visitId}")
    public MstVisit read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVisit mstVisit = mstVisitRepository.getById(visitId);
        return mstVisit;
    }

//    @RequestMapping("byPatientId/{patientId}")
//    public List<MstVisit> readByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
//        TenantContext.setCurrentTenant(tenantName);
//        List<MstVisit> mstVisit = mstVisitRepository.getByVisitPatientIdPatientIdEquals(patientId);
//        return mstVisit;
//    }

    @RequestMapping("byPatientId/{patientId}")
    public List<VisitPatientDto> readByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        List<Object[]> mstVisit = mstVisitRepository.getByVisitPatientIdPatientIdEquals(patientId);

        ArrayList<VisitPatientDto> list = new ArrayList<>();

        for(int i = 0 ; i < mstVisit.size() ; i++){
            VisitPatientDto visitPatientDto = new VisitPatientDto();
            visitPatientDto.setCreatedDate(mstVisit.get(i)[0]);
            visitPatientDto.setUnitName(mstVisit.get(i)[1]);
            visitPatientDto.setUserFullname(mstVisit.get(i)[2]);
            visitPatientDto.setVisitType(mstVisit.get(i)[3]);
            visitPatientDto.setIsInPerson(Boolean.parseBoolean(""+mstVisit.get(i)[4]));
            visitPatientDto.setTitleName(mstVisit.get(i)[5]);
            visitPatientDto.setUserFirstname(mstVisit.get(i)[6]);
            visitPatientDto.setUserMiddlename(mstVisit.get(i)[7]);
            visitPatientDto.setUserLastname(mstVisit.get(i)[8]);
            visitPatientDto.setVisitPatientId(mstVisit.get(i)[9]);

            list.add(visitPatientDto);
        }

        return list;
    }

    @RequestMapping("byPatientIdForDemographic/{patientId}")
    public List<MstVisit> readByPatientIdForDemogrphic(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstVisit> mstVisit = mstVisitRepository.getByVisitPatientIdPatientIdEqualsForDemographic(patientId);
        return mstVisit;
    }

    @RequestMapping("visitbypatientidsearch/{visitId}")
    public MstVisit visitbypatientidsearch(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVisit mstVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsOrderByVisitIdDesc(visitId);
        return mstVisit;
    }

    @RequestMapping("lasteposide/{patientId}/{visitRegistraionSource}")
    public MstVisit LastEposide(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId, @PathVariable("visitRegistraionSource") Integer visitRegistraionSource) {
        TenantContext.setCurrentTenant(tenantName);
        MstVisit lastMstVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsAndVisitRegistrationSourceOrderByVisitIdDesc(patientId, visitRegistraionSource);
        return lastMstVisit;
    }

    @RequestMapping("update")
    public MstVisit update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
        TenantContext.setCurrentTenant(tenantName);
        mstVisit = entityManager.merge(mstVisit);
        String msg = "";
        try {
            if (Propertyconfig.getSmsApi()) {
                if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                    msg = "Doctor is waiting for your consultation. Please visit to Dr. " + mstVisit.getVisitStaffId().getStaffUserId().getUserFullname() + ".";
                    sendsms.sendmessage1(mstVisit.getVisitPatientId().getPatientUserId().getUserMobileCode() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile(), msg);
                }
            }
            if (Propertyconfig.getEmailApi()) {
                String msg1 = "";
                String subject = "";
                if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                    subject = "EHMS Portal - Call in Queue";
                    msg1 = "Dear " + mstVisit.getVisitPatientId().getPatientUserId().getUserTitleId().getTitleName() + ". " + mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + " " + mstVisit.getVisitPatientId().getPatientUserId().getUserLastname() + " <br><br>" +
                            " Doctor is waiting for your consultation. Please visit to Dr. " + mstVisit.getVisitStaffId().getStaffUserId().getUserFullname() + "." +
                            " <br><br> <a href=\"https://ehmstp.healthspring.in/\">Click to view details</a><br><br><br>" +
                            " Please share your feedback on clicking below URL <br><br>" +
                            " <a href=\"https://sprw.io/stt-4074cc\">https://sprw.io/stt-4074cc</a><br><br>" +
                            " <br><br> Thanks And Regards " +
                            " <br> eHMS Admin";
                    Emailsend emailsend1 = new Emailsend();
                    emailsend1.sendMAil1(mstVisit.getVisitPatientId().getPatientUserId().getUserEmail(), msg1, subject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mstVisit;
    }

    @RequestMapping("addNewVisit")
    public MstVisit addNewVisit(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstVisit.getVisitRegistrationSource() == 2) {
            mstVisit = mstPatientController.CreateERNumber(tenantName, mstVisit);
        }
        MstPatient mstPatient = mstVisit.getVisitPatientId();
        mstPatientRepository.save(mstPatient);
        MstVisit obj = mstVisitRepository.save(mstVisit);
        if (obj.getVisitRegistrationSource() == 2) {
            EmgPatientList epl = new EmgPatientList();
            epl.setEplVisitId(obj);
            //	epl.setCreatedBy(obj.getCreatedBy());
            emgPatientListRepository.save(epl);
        }
        return obj;

    }

    @RequestMapping("createEmergencyPatient")
    public MstVisit createEmergencyPatient(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstVisit.getVisitRegistrationSource() == 2) {
            mstVisit = mstPatientController.CreateERNumber(tenantName, mstVisit);
        }
        MstPatient mstPatient = mstVisit.getVisitPatientId();
        mstPatientRepository.save(mstPatient);
        mstVisit = mstVisitRepository.save(mstVisit);
        if (mstVisit.getVisitRegistrationSource() == 2) {
            EmgPatientList epl = new EmgPatientList();
            epl.setEplVisitId(mstVisit);
            //	epl.setCreatedBy(obj.getCreatedBy());
            emgPatientListRepository.save(epl);
        }
        return mstVisit;

    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstVisit> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "visitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstVisitRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstVisitRepository.findByVisitPatientIdPatientUserIdUserFullnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("listoffinalizedemr")
    public Iterable<MstVisit> listofFinalizedEmr(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "unitId", required = false) Long unitId, @RequestParam(value = "patientId", required = false) Long patientId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "visitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndVisitUnitIdUnitIdEqualsAndVisitIsEmrFinalizedTrueAndIsActiveTrueAndIsDeletedFalse(patientId, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("msrkasvisit")
    public int msrkasvisit(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "visitStatus", required = false, defaultValue = "0") Boolean visitstatus, @RequestParam(value = "visitId", required = false, defaultValue = "0") Long visitId) {
        return mstVisitRepository.markasvisit(visitstatus, visitId);
    }

    //mohit : do not replace this method.
    @GetMapping("lastvisitautocomplete")
    public List<MstVisit> getLastVisitByAutoSearch(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstVisitRepository.findByVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(searchString, searchString, searchString, searchString);
    }

    @GetMapping("lastvisitautocompleteMR")
    public List<MstVisit> getLastVisitByAutoSearchMR(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        return mstVisitRepository.findByVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
    }

    @GetMapping("lastvisitautocompleteName")
    public List<MstVisit> getLastVisitByAutoSearchName(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        return mstVisitRepository.findByVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, searchString);
    }

    @GetMapping("lastvisitautocompletePhone")
    public List<MstVisit> getLastVisitByAutoSearchPhone(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        return mstVisitRepository.findByVisitPatientIdPatientUserIdUserResidencePhoneContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
    }

    @GetMapping("lastvisitautocompleteMobile")
    public List<MstVisit> getLastVisitByAutoSearchMobil(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        return mstVisitRepository.findByVisitPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
    }

    @GetMapping("lastvisitautocompleteID")
    public List<MstVisit> getLastVisitByAutoSearchID(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        return mstVisitRepository.findByVisitPatientIdPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
    }

    @GetMapping("lastvisitautocompleteEmail")
    public List<MstVisit> getLastVisitByAutoSearchEmail(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        return mstVisitRepository.findByVisitPatientIdPatientUserIdUserEmailContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
    }

    @GetMapping("lastvisitautocompleteById")
    public List<MstVisit> lastvisitautocompleteById(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString, @RequestParam(value = "unitId", required = false) long unitId) {
        return mstVisitRepository.findByVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseAndVisitUnitIdUnitIdOrderByVisitIdDesc(searchString, searchString, searchString, searchString, unitId);
    }

    @GetMapping("lastvisitautocompletebyidbydto")
    public List<MstVisitDTO> lastvisitautocompleteByIdByDTO(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString, @RequestParam(value = "unitId", required = false) long unitId) {
        System.out.println("searchString :" + searchString + "  unitId :" + unitId);
        return mstVisitRepository.findByPatientDetailsbyUnitIDAndSearch(unitId, searchString, searchString, searchString, searchString, searchString, PageRequest.of(0, 50));
    }

    @PutMapping("delete/{visitId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        MstVisit mstVisit = mstVisitRepository.getById(visitId);
        if (mstVisit != null) {
            mstVisit.setIsDeleted(true);
            mstVisitRepository.save(mstVisit);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("patientmnofilter")
    public Page<MstVisit> patientmnofilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "pmno", required = false) String pmno, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
        System.out.println("query parameter" + pmno);
        return mstVisitRepository.findAllByVisitPatientIdPatientUserIdUserMobileContains(pmno, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("patientmrnofilter")
    public Page<MstVisit> patientmrnofilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "pmrno", required = false) String pmrno, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "patientId") String col, @RequestParam(value = "unitId", required = false) long unitId) {
        System.out.println("query parameter mrno" + pmrno + "i am in for mr no ");
        return mstVisitRepository.findAllByVisitPatientIdPatientMrNo(pmrno, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
    }

    @GetMapping
    @RequestMapping("patientnamefilter")
    public Page<MstVisit> patientnamefilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "pname", required = false) String pname, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
        System.out.println("query parameter" + qString);
        return mstVisitRepository.findAllByVisitPatientIdPatientUserIdUserFirstnameContains(pname, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("patientuidfilter")
    public Page<MstVisit> patientuidfilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "puid", required = false) String puid, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
        System.out.println("query parameter" + qString);
        return mstVisitRepository.findAllByVisitPatientIdPatientUserIdUserUidContains(puid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("todayslistbydept")
    public Iterable<MstVisit> todayslistbydept(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "visit_id") String col) {
        java.sql.Date todaysdate;
        LocalDate today = LocalDate.now();
        todaysdate = java.sql.Date.valueOf(today);
        System.out.println(todaysdate);
        System.out.println(unitid);
        System.out.println("unit id");
        //return mstVisitRepository.findAllByIsActiveTrueAndIsDeletedFalseAndCreatedDateEqualsAndVisitRegistrationSourceEqualsAndVisitUnitIdUnitIdEquals(todaysdate,0,unitid,PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        return mstVisitRepository.findAllBytodaysdate(unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("todaypatientnamefilter")
    public Iterable<MstVisit> todaypatientnamefilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid, @RequestParam(value = "pname", required = false) String pname, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "visitId") String col) {
        java.sql.Date todaysdate;
        LocalDate today = LocalDate.now();
        todaysdate = java.sql.Date.valueOf(today);
        System.out.println("query parameter" + qString);
        return mstVisitRepository.findAllByVisitPatientIdPatientUserIdUserFirstnameContainsAndVisitTariffIdIsNotNullAndVisitUnitIdUnitIdEquals(pname, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @GetMapping
    @RequestMapping("todaypatientmnofilter")
    public Iterable<MstVisit> todaypatientmnofilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid, @RequestParam(value = "mno", required = false) String mno, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "visitId") String col) {
        java.sql.Date todaysdate;
        LocalDate today = LocalDate.now();
        todaysdate = java.sql.Date.valueOf(today);
        System.out.println("query parameter mno" + mno);
        return mstVisitRepository.findAllByVisitPatientIdPatientUserIdUserMobileContainsAndVisitTariffIdIsNotNullAndVisitUnitIdUnitIdEquals(mno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("todaypatientuidfilter")
    public Iterable<MstVisit> todaypatientuidfilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid, @RequestParam(value = "puid", required = false) String puid, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "visitId") String col) {
        java.sql.Date todaysdate;
        LocalDate today = LocalDate.now();
        todaysdate = java.sql.Date.valueOf(today);
        System.out.println("query parameter" + qString);
        return mstVisitRepository.findAllByVisitPatientIdPatientUserIdUserUidContainsAndVisitTariffIdIsNotNullAndVisitUnitIdUnitIdEquals(puid, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @GetMapping
    @RequestMapping("listrecordbystartenddate")
    public Iterable<MstVisit> listrecordbystartenddate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid, @RequestParam(value = "startdate", required = false) String startdate, @RequestParam(value = "enddate", required = false) String enddate, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "visitId") String col) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date StartDate = df.parse(startdate);
        Date EndDate = df.parse(enddate);
        System.out.println("start======>" + StartDate);
        System.out.println("end======>" + EndDate);
        return mstVisitRepository.findAllByVisitTariffIdIsNotNullAndCreatedDateBetweenAndVisitUnitIdUnitIdEquals(StartDate, EndDate, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

//    @RequestMapping("bypatientid/{visitId}")
//    public MstVisit readpatient(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
//        //mstVisitRepository.
//        List<MstVisit> mstVisit = mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(visitId);
//        return mstVisit.get(0);
//    }



    @RequestMapping("bypatientid/{patientId}")
    public MstVisit readpatient(@PathVariable("patientId") Long patientId) {
        //mstVisitRepository.
        List<MstVisit> mstVisit = mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(patientId);
        if (mstVisit.size() == 0) {
            MstPatient mstPatient = mstPatientRepository.getById(patientId);
//            MstVisit mstVisit = mstVisitRepository.getById(visitId);
            MstVisit newVisit = new MstVisit();
            newVisit.setVisitPatientId(mstPatient);
            newVisit.setVisitUnitId(mstPatient.getPatientUserId().getUserUnitId());
            newVisit = mstVisitRepository.save(newVisit);
            return newVisit;
        } else {
            return mstVisit.get(0);
        }

    }

    @RequestMapping("byEmrpatientid/{patientId}")
    public List<MstVisit> readEmrpatient(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        //mstVisitRepository.
        List<MstVisit> mstVisit = mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(patientId);
        return mstVisit;
    }

    @RequestMapping("getVisitByPatientIdOfIpdPatient/{patientId}/{patientSource}")
    public MstVisit readpatient(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId, @PathVariable("patientSource") int patientSource) {
        //mstVisitRepository.
        List<MstVisit> mstVisit = mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndVisitRegistrationSourceEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(patientId, patientSource);
        return mstVisit.get(0);
    }

    /**
     * This Method is used in Nursing Station for autocomplete to get New Born Child for Certificate Generation
     *
     * @param searchString: search via {@link MstPatient#getPatientId()}  {@link MstPatient#getPatientMrNo()}
     * @author: Romil Badhe
     * @return: MstVisit Object
     */

    @GetMapping("findbynewborn")
    public List<MstVisit> getNewBornAutoComplete(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString") String searchString) {
        return mstVisitRepository.findByVisitPatientIdPatientMrNoContainsAndVisitNewBornStatusTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserFirstnameContainsAndVisitNewBornStatusTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserLastnameContainsAndVisitNewBornStatusTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserMobileContainsAndVisitNewBornStatusTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(searchString, searchString, searchString, searchString);
    }

    /**
     * This Method is used in Nursing Station for AutoComplete to get Dead Paitent for Certification Generation
     *
     * @param searchString search via {@link MstPatient#getPatientMrNo()}
     * @return
     * @author Romil Badhe
     */
    @GetMapping("findByIsBroughtDead")
    public List<MstVisit> getIsBroughtDeadAutoComplete(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString") String searchString) {
        /* return mstVisitRepository.findByVisitPatientIdPatientMrNoContainsAndVisitPatientIdPatientIsBroughtDeadTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserFirstnameContainsAndVisitPatientIdPatientIsBroughtDeadTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserLastnameContainsAndVisitPatientIdPatientIsBroughtDeadTrueAndIsActiveTrueAndIsDeletedFalseOrVisitPatientIdPatientUserIdUserMobileContainsAndVisitPatientIdPatientIsBroughtDeadTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(searchString, searchString, searchString, searchString);*/
        return mstVisitRepository.findDistinctByVisitPatientIdPatientMrNoContainsAndVisitPatientIdPatientIsBroughtDeadTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(searchString);
    }

    @RequestMapping("getVisitAndTimelineByPatientId/{visitId}/{page}/{size}/{linkSource}")
    public Map<String, Iterable<?>> read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId, @PathVariable("page") String page, @PathVariable("size") String size, @PathVariable("linkSource") String linkSource) {
        MstVisit mstVisit = mstVisitRepository.getById(visitId);
        Iterable<MstVisit> mstVisitList = null;
        Iterable<TemrTimeline> temrTimelineList = null;
        mstVisitList = new ArrayList<>();
        temrTimelineList = new ArrayList<>();
        ((ArrayList<MstVisit>) mstVisitList).add(mstVisit);
        String sort = "DESC";
        String col = "timelineId";
        long patientid = mstVisit.getVisitPatientId().getPatientId();
        if (linkSource.equals("ipd")) {
            temrTimelineList = temrTimelineRepository.findByTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalseAndTimelineSIdServiceIsConsultionTrue(patientid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (linkSource.equals("opd")) {
            temrTimelineList = temrTimelineRepository.findByTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalseAndTimelineServiceIdBsServiceIdServiceIsConsultionTrue(patientid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            // timeline obj reduce size here
//            while(((Page<TemrTimeline>) temrTimelineList).hasContent())
/*
            for(int i=0; i< ((Page<TemrTimeline>) temrTimelineList).getContent().size(); i++)
            {
//                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineStaffId().setConcessionAuthority(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineStaffId().setStaffStore(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineStaffId().setStaffClusterIdList(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineStaffId().setStaffUnit(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineStaffId().setStaffServiceId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineStaffId().setStaffDepartmentId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineStaffId().setStaffSdId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineStaffId().setStaffDesignationId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineVisitId().getVisitStaffId().setStaffServiceId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineVisitId().getVisitStaffId().setStaffSdId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineVisitId().getVisitStaffId().setStaffDesignationId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineVisitId().getVisitStaffId().setStaffDepartmentId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineVisitId().getVisitStaffId().setStaffSdId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineVisitId().getVisitStaffId().setStaffStore(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineServiceId().setBsBillId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineServiceId().setBsServiceId(null);
                ((Page<TemrTimeline>) temrTimelineList).getContent().get(i).getTimelineServiceId().setBsStaffId(null);

            }*/
        }
        respMap1.put("visit", mstVisitList);
        respMap1.put("timeline", temrTimelineList);
        return respMap1;
    }

    @RequestMapping("visitbypatientid/{patientId}")
    public MstVisit visitbypatientid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        List<MstVisit> mstVisit = mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(patientId);
        mstVisit.get(0).getVisitTariffId().setTariffServices(null);
        return mstVisit.get(0);
    }

    public MstVisit lastvisitbypatientid(Long patientId) {
        List<MstVisit> mstVisit = mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByVisitIdDesc(patientId);
        mstVisit.get(0).getVisitTariffId().setTariffServices(null);
        return mstVisit.get(0);
    }

    @Transactional
    @RequestMapping("deleteById/{visitId}")
    public Map<String, String> deleteById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        if (visitId != null) {
            String query = "delete from mst_visit  where visit_id=" + visitId;
            entityManager.createNativeQuery(query).executeUpdate();
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("emergencylist")
    public List<MstVisit> emergenylist() {
        List<MstVisit> mstVisit = mstVisitRepository.findByVisitRegistrationSourceEqualsAndIsActiveTrueAndIsDeletedFalse(1);
        return mstVisit;
    }

    @RequestMapping("/patientsearchByAll/{key}/{unitid}")
    public Map<String, Object> patientsearchAll(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key, @PathVariable("unitid") Long unitid) {
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstVisit> records;
//        int queue = mstVisitRepository.findCountByVisitIsEmrTrueAndVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsAndVisitUnitIdUnitIdEqualsAndVisitIsEmrFinalizedTrue(key, key, key, unitid);
        records = mstVisitRepository.findAllByVisitIsEmrTrueAndVisitPatientIdPatientMrNoContainsOrVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsOrVisitPatientIdPatientUserIdUserMobileContainsAndVisitUnitIdUnitIdEquals(key, key, key, key, unitid);
        automap.put("record", records);
//        automap.put("queue", queue);
        return automap;
    }

    @GetMapping
    @RequestMapping("listEMRVisitpatientlistbystartenddate")
    public Iterable<MstVisit> listEMRpatientbystartenddate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                           @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                           @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid,
                                                           @RequestParam(value = "enddate", required = false) String enddate,
                                                           @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                           @RequestParam(value = "col", required = false, defaultValue = "visit_id") String col) throws ParseException {
        int queue = mstVisitRepository.findInQueueCount(enddate, unitid);
        int addressedCount = mstVisitRepository.findInAddressedCount(enddate, unitid);
        Iterable<MstVisit> mstVisitList = mstVisitRepository.findAllEMRVisitPatientListByCreatedDate(enddate, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        if (((Page<MstVisit>) mstVisitList).getContent().size() != 0) {
            MstVisit mstvisit = ((Page<MstVisit>) mstVisitList).getContent().get(0);
            mstvisit.setQueue(queue);
            mstvisit.setAddressed(addressedCount);
        }
        return mstVisitList;
    }

    @RequestMapping("getLastVisit/{patientId}")
    public MstVisit getLastVisit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        List<MstVisit> visitList = mstVisitRepository.findByVisitPatientIdPatientIdEqualsAndVisitTariffIdNotNull(patientId);
        MstVisit lastMstVisit = new MstVisit();
        if (visitList.size() >= 2) {
            lastMstVisit = visitList.get(visitList.size() - 2);
        }
        return lastMstVisit;
    }

    @RequestMapping("setpatientcall/{visitId}/{boolean}")
    public Integer updateBoolean(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId, @PathVariable("boolean") Boolean booleanValue) {
        try {
            MstVisit mstVisit = mstVisitRepository.getById(visitId);
            mstVisit.setVisitIsCalled(true);
            Long diff = (new Date().getTime() - mstVisit.getCreatedDate().getTime()) / 1000;
            diff /= 60;
            diff = Math.abs(Math.round(diff.doubleValue()));
            mstVisit.setVisitWaitingTime(diff);
            mstVisitRepository.save(mstVisit);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @RequestMapping("updateVisit")
    public MstVisit updateVisit(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
        return mstVisitRepository.save(mstVisit);
    }

    @RequestMapping("setvideocallend/{visitId}")
    public MstVisit SetVideoCallEnd(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        MstVisit mVisit = mstVisitRepository.getById(visitId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mVisit.setVisitWts(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Long diff = 0L;
        try {
            if (mVisit.getVisitWts() != null && mVisit.getVisitWte() != null) {
            diff = (simpleDateFormat.parse(mVisit.getVisitWts()).getTime() - simpleDateFormat.parse(mVisit.getVisitWte()).getTime()) / 1000;
            diff /= 60;
            diff = Math.abs(Math.round(diff.doubleValue()));
            mVisit.setVisitConsultationDuration(diff);
            mVisit.setVisitIsInConsultation(false);
            mstVisitRepository.save(mVisit);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mVisit;

    }

    @RequestMapping("updatVisitPriority")
    public Map<String, String> setVisitPriority(@RequestHeader("X-tenantId") String tenantName, @RequestBody String data) {
        org.json.JSONObject object = new org.json.JSONObject(data);
        Map<String, String> respMap = new HashMap<String, String>();
        int count = mstVisitRepository.updateVisitPriority(object.getLong("visitId"), object.getInt("VisitPriority"), object.getString("VisitPriorityComment"));
        if (count == 0) {
            respMap.put("status", "0");
            respMap.put("message", "fail");
        } else {
            respMap.put("status", "1");
            respMap.put("message", "success");
        }
        return respMap;
    }

    @RequestMapping("searchVisitListByUnitId")
    public Map<String, Object> searchListByUnitId1(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPatientDTO searchPatientDTO) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (searchPatientDTO.getFromdate().equals("") || searchPatientDTO.getFromdate().equals("null")) {
            searchPatientDTO.setFromdate(strDate);
        }
        if (searchPatientDTO.getTodate().equals("") || searchPatientDTO.getTodate().equals("null")) {
//            todate = strDate;
            searchPatientDTO.setTodate(strDate);
        }
        String query = "SELECT mv.visit_id, mp.patient_id, mp.patient_mr_no,CONCAT(mu.user_firstname ,' ', mu.user_lastname), " +
                " mg.gender_name , mu.user_dob , mu.user_age , mu.user_uid, mu.user_id_doc_image, mu.user_mobile , mv.created_date, " +
                " mu.created_by, mu.user_id, un.unit_name, mu.user_driving_no, mpt.pt_name, mu.user_email,mu.user_image, CONCAT(mu1.user_firstname,' ', mu1.user_lastname) AS DocName " +
                " FROM mst_visit mv " +
                " INNER join mst_patient mp  " +
                " INNER join mst_patient_type mpt " +
                " INNER JOIN mst_user mu " +
                " INNER JOIN mst_staff ms " +
                " INNER JOIN mst_user mu1 " +
                " INNER JOIN mst_gender mg " +
                " Left JOIN mst_unit un ON un.unit_id = mu.user_unit_id " +
                " WHERE mv.visit_patient_id=mp.patient_id AND mu.user_id=mp.patient_user_id " +
                " AND mp.patient_type=mpt.pt_id AND mg.gender_id=mu.user_gender_id AND mv.visit_staff_id = ms.staff_id AND ms.staff_user_id = mu1.user_id " +
                " AND mp.is_deleted=0 ";
        if (searchPatientDTO.getPatientMrNo() != null && !searchPatientDTO.getPatientMrNo().equals("")) {
            query += " and mp.patient_mr_no like  '%" + searchPatientDTO.getPatientMrNo() + "%' ";
        }
        if (!searchPatientDTO.getUnitId().equals("0") && searchPatientDTO.getUnitId() != null && !searchPatientDTO.getUnitId().equals("")) {
            query += " AND mv.visit_unit_id =" + searchPatientDTO.getUnitId() + " ";
        }
        if (searchPatientDTO.getUserFirstname() != null && !searchPatientDTO.getUserFirstname().equals("")) {
            query += "  and (mu.user_firstname like '%" + searchPatientDTO.getUserFirstname() + "%' or mu.user_lastname like '%" + searchPatientDTO.getUserFirstname() + "%'  ) ";
        }
        if (searchPatientDTO.getUserMobile() != null && !searchPatientDTO.getUserMobile().equals("")) {
            query += " and mu.user_mobile like '%" + searchPatientDTO.getUserMobile() + "%'  ";
        }
        if (searchPatientDTO.getUseruId() != null && !searchPatientDTO.getUseruId().equals("")) {
            query += " and mu.user_uid like '%" + searchPatientDTO.getUseruId() + "%' ";
        }
        if (searchPatientDTO.getEmpNo() != null && !searchPatientDTO.getEmpNo().equals("")) {
            query += " and mu.user_driving_no like '%" + searchPatientDTO.getEmpNo() + "%' ";
        }
        if (searchPatientDTO.getPtId() != null && !searchPatientDTO.getPtId().equals("0")) {
            query += " and mp.patient_type = " + searchPatientDTO.getPtId() + " ";
        }
        if (!searchPatientDTO.getGenderId().equals("0") && searchPatientDTO.getGenderId() != null && !searchPatientDTO.getGenderId().equals("")) {
            query += " and mu.user_gender_id = " + searchPatientDTO.getGenderId() + " ";
        }
        if (!searchPatientDTO.getAgeFrom().equals("") && !searchPatientDTO.getAgeFrom().equals("0") && searchPatientDTO.getAgeFrom() != null && !searchPatientDTO.getAgeTo().equals("") && !searchPatientDTO.getAgeTo().equals("0") && searchPatientDTO.getAgeTo() != null) {
            query += " and mu.user_age BETWEEN '" + searchPatientDTO.getAgeFrom() + "' and '" + searchPatientDTO.getAgeTo() + "' ";
        }
        query += " and date(mv.created_date) BETWEEN '" + searchPatientDTO.getFromdate() + "' and '" + searchPatientDTO.getTodate() + "' ";
        query += " order by mv.visit_id desc ";
        String Cquery = "select count(*) from ( " + query + " ) as combine ";
        query += " limit " + ((searchPatientDTO.getOffset() - 1) * searchPatientDTO.getLimit()) + "," + searchPatientDTO.getLimit();
        System.out.println("query ==>" + query);
        List<Object[]> queue = entityManager.createNativeQuery(query).getResultList();
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(Cquery).getSingleResult();
        Map<String, Object> map = new HashMap<>();
        map.put("content", queue);
        map.put("count", cc);
        return map;

    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofpastvisits")
    public ResponseEntity searchListofReferrals(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
                                                @RequestParam(value = "unitId", required = false, defaultValue = "0") Long unitId,
                                                @RequestParam(value = "patientId", required = false, defaultValue = "0") Long patientId,
                                                @RequestParam(value = "followupDays", required = false, defaultValue = "7") String followupDays,
                                                @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                @RequestParam(value = "col", required = false, defaultValue = "visit_id") String col) throws ParseException {
        String Query = " SELECT mv.visit_date AS visitDate, mu.user_fullname as staffName, mun.unit_name AS unitName,case when mv.is_virtual = 1 then 'true' ELSE 'false' END AS visitType, " +
                " mv.visit_is_emr_finalized AS isEmrFinalized, ta.refer_history_id As isReferral, mser.service_name As serviceName, tt.timeline_id as timelineId, ms.staff_id AS staffId FROM mst_visit mv INNER JOIN mst_unit mun ON mun.unit_id = mv.visit_unit_id INNER JOIN mst_staff ms ON ms.staff_id = mv.visit_staff_id  " +
                " INNER JOIN mst_user mu ON ms.staff_user_id = mu.user_id INNER JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
                " INNER JOIN trn_appointment ta ON ta.appointment_timeline_id =  tt.timeline_id" +
                " INNER JOIN mbill_service mser ON mser.service_id = ta.appointment_service_id " +
                " where DATE(mv.visit_date - INTERVAL " + followupDays + "  DAY) AND tt.isemrfinal = 1 AND mv.visit_unit_id =" + unitId + " AND mv.visit_patient_id=" + patientId;
        Query += " GROUP BY mv.visit_id order by mv.visit_date desc";
        String CountQuery = "";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        String columnName = "visitDate,staffName,unitName,visitType,isEmrFinalized,isReferral,serviceName,timelineId,staffId";
        Query += " limit " + ((page - 1) * size) + "," + size;
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));

    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistoffinalizedvisits")
    public ResponseEntity getListofFinalizedvisits(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                   @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
                                                   @RequestParam(value = "unitId", required = false, defaultValue = "0") Long unitId,
                                                   @RequestParam(value = "patientId", required = false, defaultValue = "0") Long patientId,
                                                   @RequestParam(value = "complaintId", required = false, defaultValue = "0") Long complaintId,
                                                   @RequestParam(value = "symtomId", required = false, defaultValue = "0") Long symtomId,
                                                   @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                   @RequestParam(value = "col", required = false, defaultValue = "visit_id") String col) throws ParseException {
        String Query = " SELECT DATE(mv.visit_date) AS visitDate, mu.user_fullname AS staffName, mun.unit_name AS unitName, " +
                " tt.timeline_id AS timellineId,  " +
                "(SELECT ifnull(GROUP_CONCAT(ch.cc_name),'') FROM  temr_visit_chief_complaint tc inner JOIN memr_chief_complaint ch WHERE cc_id = tc.vcc_cc_id AND tt.timeline_id = tc.vcc_timeline_id ) As chiefComplaint, " +
                "  (SELECT ifnull(GROUP_CONCAT(ms.symptom_name),'') FROM temr_visit_symptom ts INNER JOIN memr_symptom ms WHERE symptom_id = ts.vs_symptom_id and tt.timeline_id = ts.vs_timeline_id) AS Symtoms " +
                " FROM mst_visit mv " +
                " left JOIN mst_unit mun ON mun.unit_id = mv.visit_unit_id " +
                " left JOIN mst_staff ms ON ms.staff_id = mv.visit_staff_id " +
                " left JOIN mst_user mu ON ms.staff_user_id = mu.user_id " +
                " left JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
                " LEFT JOIN temr_visit_symptom vs ON vs.vs_timeline_id = tt.timeline_id " +
                " LEFT JOIN temr_visit_chief_complaint cc ON cc.vcc_timeline_id = tt.timeline_id " +
                " WHERE tt.isemrfinal = 1  AND mv.visit_unit_id =" + unitId + " AND mv.visit_patient_id=" + patientId;
        if (complaintId != 0 && complaintId != null) {
            Query += " And cc.vcc_cc_id =" + complaintId;
        }
        if (symtomId != 0 && symtomId != null) {
            Query += " And vs.vs_symptom_id =" + symtomId;
        }
        Query += " GROUP BY mv.visit_id order by mv.visit_date desc";
        String CountQuery = "";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        String columnName = "visitDate,staffName,unitName,timelineId,chiefComplaint,Symtoms";
        Query += " limit " + ((page - 1) * size) + "," + size;
//        List<Object[]> response = (List<Object[]>) entityManager.createNativeQuery(Query).getResultList();

       /* if(response != null) {
            for (int j = 0; j < response.size()-1; j++) {
                JSONArray chiefComplaint = new JSONArray();
                List<Object[]> chiefComplaintList = (List<Object[]>) entityManager.createNativeQuery("SELECT ch.cc_id,ch.cc_name, tt.timeline_id FROM temr_visit_chief_complaint tc INNER JOIN mst_visit mv INNER JOIN temr_timeline tt ON tt.timeline_id = tc.vcc_timeline_id INNER JOIN memr_chief_complaint ch WHERE mv.visit_id=tc.vcc_visit_id AND ch.cc_id=tc.vcc_cc_id AND tt.timeline_id ="+ response.get(j)[3] + "AND tt.isemrfinal= 1 AND  tc.is_active=1 AND tc.is_deleted=0ORDER BY tc.created_date DESC").getResultList();

                for (int i = 0; i < chiefComplaintList.size(); i++) {
                    org.json.JSONObject object = new org.json.JSONObject();
                    object.put("cc_id", "" + chiefComplaintList.get(i)[0]);
                    object.put("cc_name", "" + chiefComplaintList.get(i)[2]);
                    object.put("timeline_id", "" + chiefComplaintList.get(i)[1]);
                    chiefComplaint.put(object);
                }

            }
       }*/
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));

    }

    @RequestMapping("setpatientisinconsultation/{visitId}/{boolean}/{waitDuration}")
    public Integer setIsinconsultation(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId, @PathVariable("boolean") Boolean booleanValue,@PathVariable("waitDuration") Long waitDuration) {
        try {
            TenantContext.setCurrentTenant(tenantName);
            MstVisit mstVisit = mstVisitRepository.getById(visitId);
            mstVisit.setVisitIsInConsultation(booleanValue);
            mstVisit.setVisitIsCalled(false);
            mstVisit.setVisitWaitingDuration(waitDuration);
            mstVisitRepository.save(mstVisit);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @RequestMapping("setpatientisinconsultation1/{visitId}/{boolean}")
    public Integer setIsinconsultation1(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId, @PathVariable("boolean") Boolean booleanValue) {
        try {
            TenantContext.setCurrentTenant(tenantName);
            MstVisit mstVisit = mstVisitRepository.getById(visitId);
            mstVisit.setVisitIsInConsultation(booleanValue);
            mstVisit.setVisitIsCalled(false);
            mstVisitRepository.save(mstVisit);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

}
