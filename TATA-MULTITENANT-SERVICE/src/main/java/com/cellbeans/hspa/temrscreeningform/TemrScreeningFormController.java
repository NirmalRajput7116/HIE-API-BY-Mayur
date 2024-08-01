package com.cellbeans.hspa.temrscreeningform;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_screening_forms")
public class TemrScreeningFormController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrScreeningFormRepository temrScreeningFormRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateReport createReport;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrScreeningForm temrScreeningForm) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrScreeningForm.getSfVisitId().getVisitId() > 0) {
            temrScreeningFormRepository.save(temrScreeningForm);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byVisitId/{visitId}")
    public List<TemrScreeningForm> byVisitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrScreeningFormRepository.findAllBySfVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
    }

    @GetMapping
    @RequestMapping("listScreeningForm")
    public Iterable<TemrScreeningForm> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                            @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                            @RequestParam(value = "qString", required = false) String qString,
                                            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                            @RequestParam(value = "col", required = false, defaultValue = "sfId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return temrScreeningFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
    }
//    @RequestMapping("listScreeningFormPrint")
//    public Iterable<TemrScreeningForm> listPrint(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                            @RequestParam(value = "size", required = false, defaultValue = "20") String size,
//                                            @RequestParam(value = "qString", required = false) String qString,
//                                            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                            @RequestParam(value = "col", required = false, defaultValue = "sfId") String col) {
//        return temrScreeningFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//
//        if (consultantreferallSearchDTO.getPrint()) {
//            return createReport.createJasperReportXLS("ConsultantReferalReport", ds);
//        } else {
//            return createReport.createJasperReportPDF(
//                    "ConsultantReferalReport", ds);
//        }
//
//    }

    @RequestMapping("searchScreeningForm")
    public List<TemrScreeningForm> searchScreeningForm(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                       @RequestParam(value = "today", required = false, defaultValue = "0") boolean today,
                                                       @RequestParam(value = "formdate", required = false) String formdate,
                                                       @RequestParam(value = "todate", required = false) String todate,
                                                       @RequestParam(value = "userId", required = false) long userId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select t from TemrScreeningForm t where ";
        if (formdate.equals("null")) {
            formdate = "1998-12-31";
        }
        if (todate.equals("null")) {
            todate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        if (today) {
            query += " DATE_FORMAT(t.createdDate, '%Y-%m-%d')='" + todate + "' ";
        } else {
            query += " DATE_FORMAT(t.createdDate, '%Y-%m-%d') between '" + formdate + "' and '" + todate + "' ";
        }
        if (userId != 0) {
            query += " and t.sfVisitId.visitPatientId.patientUserId.userId=" + userId;
        }
        query += " order by t.sfId desc";
        List<TemrScreeningForm> list = entityManager.createQuery(query, TemrScreeningForm.class).setFirstResult(((page - 1) * size)).setMaxResults(size).getResultList();
        String countQuery = StringUtils.replace(query, " t fr", " count(t.sfId) fr");
        long count = (long) entityManager.createQuery(countQuery).getSingleResult();
        if (list.size() > 0) {
            list.get(0).setCount(count);
        }
        return list;
    }

    @RequestMapping("searchScreeningFormPrint")
    public String searchScreeningFormPrint(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                           @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                           @RequestParam(value = "today", required = false, defaultValue = "0") boolean today,
                                           @RequestParam(value = "formdate", required = false) String formdate,
                                           @RequestParam(value = "todate", required = false) String todate,
                                           @RequestParam(value = "userId", required = false) long userId,
                                           @RequestParam(value = "print", required = false) boolean print) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select t from TemrScreeningForm t where ";
        if (formdate.equals("null")) {
            formdate = "1998-12-31";
        }
        if (todate.equals("null")) {
            todate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        if (today) {
            query += " DATE_FORMAT(t.createdDate, '%Y-%m-%d')='" + todate + "' ";
        } else {
            query += " DATE_FORMAT(t.createdDate, '%Y-%m-%d') between '" + formdate + "' and '" + todate + "' ";
        }
        if (userId != 0) {
            query += " and t.sfVisitId.visitPatientId.patientUserId.userId=" + userId;
        }
        query += " order by t.sfId desc";
        List<TemrScreeningForm> list = entityManager.createQuery(query, TemrScreeningForm.class).getResultList();
//        String countQuery = StringUtils.replace(query, " t fr", " count(t.sfId) fr");
//        long count = (long) entityManager.createQuery(countQuery).getSingleResult();
//        if (list.size() > 0) {
//            list.get(0).setCount(count);
//        }
//        return list;
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
        if (print) {
            return createReport.createJasperReportXLS("ScreeningRegisterReport", ds);
        } else {
            return createReport.createJasperReportPDF("ScreeningRegisterReport", ds);
        }
    }

}
