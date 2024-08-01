package com.cellbeans.hspa.trnsponsorcombination;

import com.cellbeans.hspa.mstcompanytype.MstCompanyType;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstvisit.MstVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.cellbeans.hspa.TenantContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_sponsor_combination")
public class TrnSponsorCombinationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnSponsorCombinationRepository trnSponsorCombinationRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnSponsorCombination trnSponsorCombination) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("i am sponcer combination");
        if (trnSponsorCombination.getScPolicyNo() != null) {
            trnSponsorCombinationRepository.save(trnSponsorCombination);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("createdefault")
    public Map<String, String> createdefault(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "userID", required = false) int userID, @RequestParam(value = "visitID", required = false) Long visitID) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("i am sponcer combination"+userID);
        TrnSponsorCombination trnSponsorCombination = new TrnSponsorCombination();
        MstVisit mstVisit = new MstVisit();
        mstVisit.setVisitId(visitID);
        trnSponsorCombination.setScVisitId(mstVisit);
        MstUser mstuser = new MstUser();
        mstuser.setUserId(userID);
        trnSponsorCombination.setScUserId(mstuser);
        MstCompanyType mstCompanyType = new MstCompanyType();
        mstCompanyType.setCtId(4);
        trnSponsorCombination.setScCtId(mstCompanyType);
        if (trnSponsorCombination.getScUserId().getUserId() != 0L) {
            trnSponsorCombinationRepository.save(trnSponsorCombination);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("createdefaultvalidate")
    public Map<String, String> createdefaultvalidate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "userID", required = false) Long userID, @RequestParam(value = "visitID", required = false) Long visitID) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("validate"+userID);
        Long as = (long) 4;
        String validate = null;
        try {
            trnSponsorCombinationRepository.findByScUserIdUserIdEqualsAndScVisitIdVisitIdEqualsAndScCtIdCtIdEqualsAndIsActiveTrueAndIsDeletedFalse(userID, visitID, as);
            validate = "done";
        } catch (Exception e) {
            validate = "error";
        }
        // System.out.println("validate resp 2");
        respMap.put("success", validate);
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TrnSponsorCombination> records;
        records = trnSponsorCombinationRepository.findByScPolicyCodeContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byuserid/{scId}")
    public String byuserid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("scId") int scId) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("======>" + scId);
        TrnSponsorCombination trnSponsorCombination = null;
        MstUser mstuser = null;
        mstuser.setUserId(scId);
        trnSponsorCombination.setScUserId(mstuser);
        trnSponsorCombinationRepository.save(trnSponsorCombination);
        return "";
    }

    @RequestMapping("byid/{scId}")
    public TrnSponsorCombination read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("scId") Long scId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnSponsorCombination trnSponsorCombination = trnSponsorCombinationRepository.getById(scId);
        return trnSponsorCombination;
    }

    @RequestMapping("update")
    public TrnSponsorCombination update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnSponsorCombination trnSponsorCombination) {
        TenantContext.setCurrentTenant(tenantName);
        return trnSponsorCombinationRepository.save(trnSponsorCombination);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnSponsorCombination> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "scId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnSponsorCombinationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnSponsorCombinationRepository.findByAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @GetMapping
    @RequestMapping("listbyuser")
    public Map<String, Object> listbyuser(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "userID", required = false) String userID) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println(userID);
        Long u_id = Long.parseLong(userID);
        Date curdate = new Date();
        Map<String, Object> usemapping = new HashMap<String, Object>();
        List<TrnSponsorCombination> records;
        records = trnSponsorCombinationRepository.findByScUserIdUserIdEqualsAndScExpiryDateIsGreaterThanAndIsDeletedFalse(u_id);
        usemapping.put("record", records);
        return usemapping;
    }

    @GetMapping
    @RequestMapping("listbyuserforedit")
    public Map<String, Object> listbyuserforedit(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "userID", required = false) String userID) {
        TenantContext.setCurrentTenant(tenantName);
        Long u_id = Long.parseLong(userID);
        Map<String, Object> usemapping = new HashMap<String, Object>();
        List<TrnSponsorCombination> records;
        records = trnSponsorCombinationRepository.findByScUserIdUserIdEqualsAndIsDeletedFalse(u_id);
        usemapping.put("record", records);
        return usemapping;
    }

    @GetMapping
    @RequestMapping("listbyvisitId")
    public List<TrnSponsorCombination> listbyvisitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "visitID", required = false) String visitID) {
        TenantContext.setCurrentTenant(tenantName);
        Long u_id = Long.parseLong(visitID);
        List<TrnSponsorCombination> records;
        records = trnSponsorCombinationRepository.findByScVisitIdVisitIdEqualsAndIsDeletedFalse(u_id);
        return records;
    }

    @PutMapping("delete/{scId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("scId") Long scId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnSponsorCombination trnSponsorCombination = trnSponsorCombinationRepository.getById(scId);
        if (trnSponsorCombination != null) {
            trnSponsorCombination.setIsDeleted(true);
            trnSponsorCombinationRepository.save(trnSponsorCombination);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    public List<TrnSponsorCombination> Listbyuserid(@RequestHeader("X-tenantId") String tenantName, long userID) {
        TenantContext.setCurrentTenant(tenantName);
        return trnSponsorCombinationRepository.findByScUserIdUserIdEqualsAndIsDeletedFalse(userID);
    }

    @GetMapping
    @RequestMapping("byvisitId")
    public Map<String, Object> byvisitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "visitID", required = false) String visitID) {
        TenantContext.setCurrentTenant(tenantName);
        Long u_id = Long.parseLong(visitID);
//        List<TrnSponsorCombination> records;
//        TrnSponsorCombination  records = trnSponsorCombinationRepository.findByScVisitIdVisitIdEqualsAndIsDeletedFalseAndIsActiveTrue(u_id);
        Map<String, Object> usemapping = new HashMap<String, Object>();
        List<TrnSponsorCombination> records;
        records = trnSponsorCombinationRepository.findByScVisitIdVisitIdEqualsAndIsDeletedFalseAndIsActiveTrue(u_id);
        usemapping.put("record", records);
        return usemapping;
    }
}
            
