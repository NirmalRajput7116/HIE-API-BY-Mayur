package com.cellbeans.hspa.tatalabanatomyreport;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.smsutility.Sendsms;
import com.cellbeans.hspa.tatalabanatomyresult.TataAnatomyResultRepository;
import com.cellbeans.hspa.tatalabanatomyresult.TataAnatomyTestResult;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.text.SimpleDateFormat;
import java.util.*;

/* Owner : Vijay Patil
  Date :1-1-2018
*/
@RestController
@RequestMapping("/tata_anatomy_lab_report")
public class TataAnatomyLabReportController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    Sendsms sendsms;
    @Autowired
    TataAnatomyLabReportRepository tataAnatomyLabReportRepository;
    @Autowired
    TataAnatomyResultRepository tataAnatomyResultRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    CreateJSONObject createJSONObject;
    @Autowired
    private com.cellbeans.hspa.applicationproperty.Propertyconfig Propertyconfig;

    @RequestMapping("byid/{id}")
    public TataAnatomyLabReport read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        TataAnatomyLabReport tpathBs = tataAnatomyLabReportRepository.getById(id);
        return tpathBs;
    }

    @RequestMapping({"labreportworklist/{page}/{size}/{sort}/{col}"})
    public Iterable<TataAnatomyLabReport> lolListnew(@RequestHeader("X-tenantId") String tenantName, @RequestBody TataAnatomyLabReportFilter json, @PathVariable("page") String page, @PathVariable("size") String size, @PathVariable("sort") String sort, @PathVariable("col") String col) {
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
            Iterable<TataAnatomyLabReport> reportWOL = tataAnatomyLabReportRepository.findByMultiFilterPathology(dateFrom, dateTo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            System.out.println("reportWOL :" + reportWOL);
            return reportWOL;
        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping({"labresult/{id}/{inspectdate}"})
    public List<TataAnatomyTestResult> lolListnew(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") String id, @PathVariable("inspectdate") String inspectDate) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("inspectDate :" + inspectDate + "  id :" + id);
        List<TataAnatomyTestResult> reportResults = tataAnatomyResultRepository.findAllBySpeciId(id, inspectDate);
        System.out.println("reportResults :" + reportResults);
        return reportResults;

    }

    @RequestMapping({"patientinfo/{sampleid}"})
    public List<TataAnatomyLabReport> lolPatientInfo(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sampleid") String sampleid) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("patientid :" + sampleid);
        List<TataAnatomyLabReport> reportResults = tataAnatomyLabReportRepository.findAllByPatientId(sampleid);
        System.out.println("reportResults :" + reportResults);
        return reportResults;
    }

    @RequestMapping("patientandlabresult")
    // @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity patientAndLabresult(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject json) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("labitemcount :" + json + "  :..0 :" + json.get("dateFrom"));
        String query = "select ls.PatientID,ls.InspectDate,ls.SpecimenID,ls.PatientName,ls.PatientGender,ls.PatientAge,ls.PatientLastName,ls.AgeUnit,lt.ItemCode,lt.Value,lt.Unit,lt.Low,lt.High,lt.InspectTime,lt.HighLowFlag from lis_specimeninfo ls inner join lis_testresult lt on lt.SpecimenID=ls.SpecimenID where ls.PatientID='" + json.get("sampleId") + "' and lt.InspectTime like '" + json.get("inspectDate") + "%'";
        String[] col = {"patientId", "specimenId", "patientInspectDateTime", "patientName", "patientGender", "patientAge", "patientLastName", "patientAgeUnit", "paramShortName", "paramValue", "paramUnit", "paramLowValue", "paramHighValue", "paramInspectDateTime", "paramHighLowFlag"};
        return ResponseEntity.ok(createJSONObject.createJsonObj(col, query));
    }

}
