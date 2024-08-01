package com.cellbeans.hspa.mis.misLab;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/tbill_bill")
public class MisLabController {

    Map<String, String> respMap = new HashMap<String, String>();

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    MstUserRepository mstUserRepository;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    CreateReport createReport;

    @GetMapping
    @RequestMapping("getSerachSponcerSettleReport/{fromdate}/{todate}/{todaydate}/{unitId}/{limit}/{offset}")
    public List<MisLabCountDTO> getSerachSponcerSettleReport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("todaydate") String todaydate, @PathVariable("unitId") String unitId, @PathVariable("limit") String limit, @PathVariable("offset") String offset) {
        TenantContext.setCurrentTenant(tenantName);
        String query2 = "SELECT collected.unit_id,collected.unit_name,COALESCE(collected.sampleCollectedCount,0) as collect,COALESCE(accepted.sampleAcceptedCount,0) as accept,COALESCE(rejected.sampleRejectedCount,0) as reject ,COALESCE(finalize.sampleFinalizedCount,0) as final,COALESCE(submission.sampleSubmissionCount,0) as submit from " + " (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleCollectedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u\n" + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_sample_collected=1 and s.is_active=1 and s.is_deleted=0 ";
        String countquery = "select count(*) from " + "(SELECT collected.unit_id,collected.unit_name,COALESCE(collected.sampleCollectedCount,0) as collect,COALESCE(accepted.sampleAcceptedCount,0) as accept,COALESCE(rejected.sampleRejectedCount,0) as reject ,COALESCE(finalize.sampleFinalizedCount,0) as final,COALESCE(submission.sampleSubmissionCount,0) as submit from " + " (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleCollectedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u\n" + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_sample_collected=1 and s.is_active=1 and s.is_deleted=0 ";
        List<MisLabCountDTO> mislabcountDTOList = new ArrayList<MisLabCountDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (!unitId.equals("0")) {
            query2 += " and  u.unit_id=" + unitId + " ";
            countquery += " and  u.unit_id=" + unitId + " ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (todaydate.equals("T")) {
            query2 += " and date(s.ps_sample_collection_date) ='" + todate + "'  group by u.unit_id) as collected " + " left join " + " (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleAcceptedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_sample_accepted=1 and s.is_active=1 and s.is_deleted=0 and date(s.ps_sample_accepted_date)='" + todate + "' group by u.unit_id) as accepted " + "on collected.unit_id=accepted.unit_id " + "left join " + "(SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleRejectedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_sample_rejected=1 and s.is_active=1 and s.is_deleted=0 and date(s.ps_sample_rejected_date)='" + todate + "' group by u.unit_id) as rejected  " + " on accepted.unit_id=rejected.unit_id " + " left join " + " (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleFinalizedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_finalized=1 and s.is_active=1 and s.is_deleted=0 and date(s.ps_finalized_date)='" + todate + "' group by u.unit_id) as finalize  " + " on accepted.unit_id=finalize.unit_id " + " left join " + "  (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleSubmissionCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_report_uploaded=1 and s.is_active=1 and s.is_deleted=0 and date(s.ps_report_delivery_date)='" + todate + "' group by u.unit_id) as submission " + " on accepted.unit_id=submission.unit_id ";
            countquery += " and date(s.ps_sample_collection_date) ='" + todate + "'  group by u.unit_id) as collected " + " left join " + " (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleAcceptedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_sample_accepted=1 and s.is_active=1 and s.is_deleted=0 and date(s.ps_sample_accepted_date)='" + todate + "' group by u.unit_id) as accepted " + "on collected.unit_id=accepted.unit_id " + "left join " + "(SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleRejectedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_sample_rejected=1 and s.is_active=1 and s.is_deleted=0 and date(s.ps_sample_rejected_date)='" + todate + "' group by u.unit_id) as rejected  " + " on accepted.unit_id=rejected.unit_id " + " left join " + " (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleFinalizedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_finalized=1 and s.is_active=1 and s.is_deleted=0 and date(s.ps_finalized_date)='" + todate + "' group by u.unit_id) as finalize  " + " on accepted.unit_id=finalize.unit_id " + " left join " + "  (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleSubmissionCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_report_uploaded=1 and s.is_active=1 and s.is_deleted=0 and date(s.ps_report_delivery_date)='" + todate + "' group by u.unit_id) as submission " + " on accepted.unit_id=submission.unit_id )  as complete ";
        } else {
            query2 += " and (date(s.ps_sample_collection_date) between '" + fromdate + "' and '" + todate + "')  group by u.unit_id) as collected " + " left join " + " (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleAcceptedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_sample_accepted=1 and s.is_active=1 and s.is_deleted=0 and (date(s.ps_sample_accepted_date) between '" + fromdate + "' and '" + todate + "') group by u.unit_id) as accepted " + "on collected.unit_id=accepted.unit_id " + "left join " + "(SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleRejectedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_sample_rejected=1 and s.is_active=1 and s.is_deleted=0 and (date(s.ps_sample_rejected_date) between between '" + fromdate + "' and '" + todate + "') group by u.unit_id) as rejected  " + " on accepted.unit_id=rejected.unit_id " + " left join " + " (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleFinalizedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_finalized=1 and s.is_active=1 and s.is_deleted=0 and (date(s.ps_finalized_date) between between '" + fromdate + "' and '" + todate + "') group by u.unit_id) as finalize  " + " on accepted.unit_id=finalize.unit_id " + " left join " + "  (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleSubmissionCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_report_uploaded=1 and s.is_active=1 and s.is_deleted=0 and (date(s.ps_report_delivery_date) between '" + fromdate + "' and '" + todate + "') group by u.unit_id) as submission " + " on accepted.unit_id=submission.unit_id limit " + limit + " offset " + (Integer.valueOf(offset) - 1);
            countquery += " and (date(s.ps_sample_collection_date) between '" + fromdate + "' and '" + todate + "')  group by u.unit_id) as collected " + " left join " + " (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleAcceptedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_sample_accepted=1 and s.is_active=1 and s.is_deleted=0 and (date(s.ps_sample_accepted_date) between '" + fromdate + "' and '" + todate + "') group by u.unit_id) as accepted " + "on collected.unit_id=accepted.unit_id " + "left join " + "(SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleRejectedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_sample_rejected=1 and s.is_active=1 and s.is_deleted=0 and (date(s.ps_sample_rejected_date) between between '" + fromdate + "' and '" + todate + "') group by u.unit_id) as rejected  " + " on accepted.unit_id=rejected.unit_id " + " left join " + " (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleFinalizedCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_finalized=1 and s.is_active=1 and s.is_deleted=0 and (date(s.ps_finalized_date) between between '" + fromdate + "' and '" + todate + "') group by u.unit_id) as finalize  " + " on accepted.unit_id=finalize.unit_id " + " left join " + "  (SELECT u.unit_id,u.unit_name,count(s.ps_id) as sampleSubmissionCount FROM Hspa_17072018_db.tpath_bs s,Hspa_17072018_db.mst_staff_staff_unit su,Hspa_17072018_db.mst_unit u " + " where s.ps_staff_id=su.mst_staff_staff_id and u.unit_id=su.staff_unit_unit_id and s.is_report_uploaded=1 and s.is_active=1 and s.is_deleted=0 and (date(s.ps_report_delivery_date) between '" + fromdate + "' and '" + todate + "') group by u.unit_id) as submission " + " on accepted.unit_id=submission.unit_id )  as complete ";
        }
        List<Object[]> finalDignosisCount = new ArrayList<Object[]>();
        finalDignosisCount = entityManager.createNativeQuery(query2).getResultList();
        BigInteger temp = (BigInteger) entityManager.createNativeQuery(countquery).getSingleResult();
        long count = temp.longValue();
        for (Object[] obj : finalDignosisCount) {
            MisLabCountDTO objmislabcountDTO = new MisLabCountDTO();
            objmislabcountDTO.setUnitName(obj[1].toString());
            objmislabcountDTO.setSamCollectionCount(obj[2].toString());
            objmislabcountDTO.setSamAcceptanceCount(obj[3].toString());
            objmislabcountDTO.setSamRejectionCount(obj[4].toString());
            objmislabcountDTO.setLabFinalizedCount(obj[5].toString());
            objmislabcountDTO.setLabSubmissionCount(obj[6].toString());
            objmislabcountDTO.setCount(count);
            mislabcountDTOList.add(objmislabcountDTO);
        }
        return mislabcountDTOList;
//        return listTrnBillBillSponsor;
    }
    // LAb Order Count Report END.

    // LAb Order Count Report Start
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getListOfLabCountReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchListOfLabCountReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisLabOrderListSearchDTO mislabOrderListSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT mu.unit_name," +
                " COUNT(CASE WHEN tb.is_sample_collected = 1 THEN 1 END) AS SampleCollected," +
                " COUNT(CASE WHEN tb.is_sample_accepted = 1 THEN 1 END) AS SampleAccepted," +
                " COUNT(CASE WHEN tb.is_sample_rejected = 1 THEN 1 END) AS SampleRejected," +
                " COUNT(CASE WHEN tb.is_finalized = 1 THEN 1 END) AS Finlazed," +
                " COUNT(CASE WHEN tb.is_report_delivered = 1 THEN 1 END) AS Submission" +
                " FROM tpath_bs tb" +
                " INNER JOIN tbill_bill tbl ON tbl.bill_id = tb.ps_bill_id" +
                " INNER JOIN mst_unit mu ON tbl.tbill_unit_id = mu.unit_id" +
                " WHERE tb.is_active=1 AND tb.is_deleted=0";
        String CountQuery = "";
        String columnName = "";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (mislabOrderListSearchDTO.getTodaydate()) {
            Query += " and (date(tb.created_date)=curdate()) ";
        } else {
            Query += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and tbl.tbill_unit_id in (" + values + ") ";
        }
        Query += "GROUP BY tbl.tbill_unit_id";
        System.out.println("Lab Count Report "+Query);
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,SampleCollected,SampleAccepted,SampleRejected,Finlazed,Submission";
        //  Query += " limit " + ((mislabOrderListSearchDTO.getOffset() - 1) * mislabOrderListSearchDTO.getLimit()) + "," + mislabOrderListSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }
    //Lab count Report Print Start

    @RequestMapping("getListOfLabCountReportPrint/{unitList}/{fromdate}/{todate}")
    public String searchListOfLabCountReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisLabOrderListSearchDTO mislabOrderListSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT mu.unit_name," +
                " COUNT(CASE WHEN tb.is_sample_collected = 1 THEN 1 END) AS SampleCollected," +
                " COUNT(CASE WHEN tb.is_sample_accepted = 1 THEN 1 END) AS SampleAccepted," +
                " COUNT(CASE WHEN tb.is_sample_rejected = 1 THEN 1 END) AS SampleRejected," +
                " COUNT(CASE WHEN tb.is_finalized = 1 THEN 1 END) AS Finlazed," +
                " COUNT(CASE WHEN tb.is_report_delivered = 1 THEN 1 END) AS Submission" +
                " FROM tpath_bs tb" +
                " INNER JOIN tbill_bill tbl ON tbl.bill_id = tb.ps_bill_id" +
                " INNER JOIN mst_unit mu ON tbl.tbill_unit_id = mu.unit_id" +
                " WHERE tb.is_active=1 AND tb.is_deleted=0";
        String CountQuery = "";
        String columnName = "";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (mislabOrderListSearchDTO.getTodaydate()) {
            Query += " and (date(tb.created_date)=curdate()) ";
        } else {
            Query += " and (date(tb.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and tbl.tbill_unit_id in (" + values + ") ";
        }
        Query += "GROUP BY tbl.tbill_unit_id";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unit_name,SampleCollected,SampleAccepted,SampleRejected,Finlazed,Submission";
        //Query += " limit " + ((mislabOrderListSearchDTO.getOffset() - 1) * mislabOrderListSearchDTO.getLimit()) + "," + mislabOrderListSearchDTO.getLimit();
        //    return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(mislabOrderListSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(mislabOrderListSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (mislabOrderListSearchDTO.getPrint()) {
            columnName = "unit_name,SampleCollected,SampleAccepted,SampleRejected,Finlazed,Submission";
            return createReport.generateExcel(columnName, "labOrderCountReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("labOrderCountReport", jsonArray.toString());
        }
    } //Lab count Report Print End

}
