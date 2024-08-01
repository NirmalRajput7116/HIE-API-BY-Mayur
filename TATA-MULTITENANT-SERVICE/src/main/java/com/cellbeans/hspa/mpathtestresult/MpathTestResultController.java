package com.cellbeans.hspa.mpathtestresult;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tpathbs.TpathBs;
import com.cellbeans.hspa.tpathbs.TpathBsController;
import com.cellbeans.hspa.tpathbs.TpathBsRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mpath_test_result")
public class MpathTestResultController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MpathTestResultRepository mpathTestResultRepository;
    @Autowired
    TpathBsRepository tpathBsRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private MpathTestResultService mpathTestResultService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTestResult mpathTestResult) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathTestResult != null) {
            TpathBs tpathBs = tpathBsRepository.getById(mpathTestResult.getTrPsId().getPsId());
            mpathTestResult.setTrTestId(tpathBs.getPsTestId().getTestId());
            MpathTestResult mpathTestResult2 = mpathTestResultRepository.save(mpathTestResult);
            respMap.put("success", "1");
            // System.out.println("trID :"+ Long.toString(mpathTestResult2.getTrId()));
            respMap.put("trId", Long.toString(mpathTestResult2.getTrId()));
            respMap.put("trPsId", Long.toString(mpathTestResult2.getTrPsId().getPsId()));
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @Transactional
    @RequestMapping("editreport")
    public Map<String, String> editreport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTestResult mpathTestResult) {
        TenantContext.setCurrentTenant(tenantName);
        long trId = 0;
        MpathTestResult mpathTestResult3 = new MpathTestResult();
        if (mpathTestResult != null) {
            TpathBs tpathBs = tpathBsRepository.getById(mpathTestResult.getTrPsId().getPsId());
            mpathTestResult.setTrTestId(tpathBs.getPsTestId().getTestId());
            mpathTestResult3 = entityManager.merge(mpathTestResult);
            respMap.put("success", "1");
            respMap.put("trId", String.valueOf(mpathTestResult3.getTrId()));
            respMap.put("trPsId", Long.toString(mpathTestResult3.getTrPsId().getPsId()));
            respMap.put("msg", "Edited Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Edit Report");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("getbyvisitid")
    public Iterable<MpathTestResult> ByVisitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "psId", required = false) Long psId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "trId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("inside lol_test_list :"+psId);
        Iterable<MpathTestResult> listOfPathReport = mpathTestResultRepository.findByTrPsIdPsBillIdBillVisitIdVisitId(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        // System.out.println("List of Path Report :"+listOfPathReport);
        return listOfPathReport;
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MpathTestResult> records;
        records = mpathTestResultRepository.findByTrPsIdPsTestIdTestIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{trId}")
    public MpathTestResult read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("trId") Long trId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathTestResult mpathTestResult = mpathTestResultRepository.getById(trId);
        return mpathTestResult;
    }

    @GetMapping
    @RequestMapping("gettridbypsid")
    public Iterable<MpathTestResult> lolTestList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "psId", required = false) Long psId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "profileId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("inside lol_test_list :"+psId);
        List<MpathTestResult> listOfPathReport = mpathTestResultRepository.findByTrPsIdPsIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByTrIdDesc(psId);
        // System.out.println("List of Path Report :"+listOfPathReport);
        return listOfPathReport;
    }

    @GetMapping
    @RequestMapping("test_reports")
    public Iterable<MpathTestResult> reportsList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "billId", required = false) long id, @RequestParam(value = "isIPD", required = false) String isIPD, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("helloo : " + id);
        if (Boolean.parseBoolean(isIPD)) {
            return mpathTestResultRepository.findAllIPDReport(id, id);
        } else {
            return mpathTestResultRepository.findAllOPDReport(id);
        }

    }

    @RequestMapping("update")
    public MpathTestResult update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTestResult mpathTestResult) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathTestResultRepository.save(mpathTestResult);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathTestResult> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "trId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mpathTestResultRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mpathTestResultRepository.findByTrPsIdPsTestIdTestIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("searchall")
    public Iterable<MpathTestResult> searchAllCol(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "trId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("searchall :"+qString);
        if (qString == null || qString.equals("")) {
            return mpathTestResultRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return null;// mpathTestResultRepository.findByTrBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrTrBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(qString,qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{trId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("trId") Long trId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathTestResult mpathTestResult = mpathTestResultRepository.getById(trId);
        if (mpathTestResult != null) {
            mpathTestResult.setIsDeleted(true);
            mpathTestResultRepository.save(mpathTestResult);
            respMap.put("msg", "Operation Successful");

        } else {
            respMap.put("msg", "Operation Failed");

        }
        return respMap;
    }

    //vijay
    @RequestMapping("finalizereport")
    public boolean addFinalizedReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTestResult trObj) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("finalizereport :" + trObj);
        TpathBs tpathBsOb = new TpathBs();
        if (trObj != null) {
            System.out.println("finalized  trObj :" + trObj);
            mpathTestResultRepository.save(trObj);
            tpathBsOb = trObj.getTrPsId();
            System.out.println("tpathBs vijay trObj :" + tpathBsOb);
            tpathBsRepository.save(tpathBsOb);
            TpathBsController tpath = new TpathBsController();
            tpath.smsOnReportFinalized(trObj);

        }
        return true;
    }

    @RequestMapping("adduploadreport")
    public boolean addUploadReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTestResult trObj) {
        TenantContext.setCurrentTenant(tenantName);
        boolean flag = false;
        System.out.println("adduploadreport :" + trObj);
        TpathBs tpathBsOb = new TpathBs();
        if (trObj != null) {
            tpathBsOb = trObj.getTrPsId();
            tpathBsOb.setPsReportUploadDate(new Date());
            tpathBsOb.setIsReportUploaded(true);
            tpathBsOb.setIsReportUploadedBy(tpathBsOb.getIsReportUploadedBy());
            tpathBsOb.setIsFinalized(true);
            tpathBsOb.setPsFinalizedDate(new Date());
            System.out.println("tpathBs vijay trObj :" + tpathBsOb);
            tpathBsRepository.save(tpathBsOb);
            TpathBsController tpath = new TpathBsController();
            tpath.smsOnReportFinalized(trObj);

        }
        return true;
    }

    //vijay
    @RequestMapping("submissionreport")
    public boolean addSubmissionEntry(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTestResult trObj) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("submissionreport :" + trObj);
        TpathBs tpathBsOb = new TpathBs();
        if (trObj != null) {
            MpathTestResult tempTrObj = mpathTestResultRepository.save(trObj);
            System.out.println("temp TrObj :" + tempTrObj);
            tpathBsOb = trObj.getTrPsId();
            System.out.println("tpathBs TrObj :" + tpathBsOb);
            tpathBsRepository.save(tpathBsOb);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mpathTestResultService.getMpathTestResultForDropdown(page, size, globalFilter);
        return items;
    }

    @RequestMapping("getByPsId/{psId}")
    public MpathTestResult getByPsId(@RequestHeader("X-tenantId") String tenantName, Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathTestResult tempTrObj = mpathTestResultRepository.findTopByTrPsIdPsIdEqualsAndIsActiveTrueAndIsDeletedFalseAndTrPsIdIsFinalizedTrueOrderByTrIdDesc(psId);
        return tempTrObj;
    }
}
            
