package com.cellbeans.hspa.tatabiologylabreport;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.smsutility.Sendsms;
import com.cellbeans.hspa.tatabiologylabresult.TataBiologyResultRepository;
import com.cellbeans.hspa.tatabiologylabresult.TataBiologyTestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/* Owner : Vijay Patil
  Date :1-1-2018
*/
@RestController
@RequestMapping("/tata_biology_lab_report")
public class TataBiologyLabReportController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    Sendsms sendsms;

    @Autowired
    TataBiologyLabReportRepository tataBiologyLabReportRepository;

    @Autowired
    TataBiologyResultRepository tataBiologyResultRepository;

    @RequestMapping("byid/{id}")
    public TataBiologyLabReport read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        TataBiologyLabReport tpathBs = tataBiologyLabReportRepository.getById(id);
        return tpathBs;
    }

    @RequestMapping({"labreportworklist/{page}/{size}/{sort}/{col}"})
    public Iterable<TataBiologyLabReport> lolListnew(@RequestHeader("X-tenantId") String tenantName, @RequestBody TataBiologyLabReportFilter json, @PathVariable("page") String page, @PathVariable("size") String size, @PathVariable("sort") String sort, @PathVariable("col") String col) {
        TenantContext.setCurrentTenant(tenantName);
        Date dateFrom = new Date();
        Date dateTo = new Date();
        try {
            sort = "DESC";
            col = "id";
            System.out.println("json :" + json);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            c.setTime(sdf.parse(json.getDateTo()));
            c.add(Calendar.DAY_OF_MONTH, 1);
            String newDateTo = sdf.format(c.getTime());
            dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(newDateTo);
            dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(json.getDateFrom());
            System.out.println("date from :" + dateFrom);
            System.out.println("dateTo :" + dateTo);
            Iterable<TataBiologyLabReport> reportWOL = tataBiologyLabReportRepository.findByMultiFilterPathology(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            System.out.println("reportWOL :" + reportWOL);
            return reportWOL;
        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping({"labresult/{id}"})
    public List<TataBiologyTestResult> lolListnew(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") String id) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("  id :" + id);
        List<TataBiologyTestResult> reportResults = tataBiologyResultRepository.findAllByPatientId(id);
        System.out.println("reportResults :" + reportResults);
        return reportResults;

    }

}
