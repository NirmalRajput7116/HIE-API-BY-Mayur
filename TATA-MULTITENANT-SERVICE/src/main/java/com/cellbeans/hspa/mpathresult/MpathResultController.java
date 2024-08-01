package com.cellbeans.hspa.mpathresult;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mpathtestresult.MpathTestResultRepository;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.tpathbs.TpathBsController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mpath_result")
public class MpathResultController {

    Map<String, String> respMap = new HashMap<String, String>();

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Autowired
    MpathResultRepository mpathResultRepository;

    @Autowired
    MpathTestResultRepository mpathTestResultRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private MpathResultService mpathResultService;

    @Autowired
    CreateReport createReport;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MpathResult> mpathResult) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathResult != null) {
            mpathResultRepository.saveAll(mpathResult);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("uploadreport")
    public Map<String, String> uploadReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathResult mpathResult) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("my json :");
        System.out.println(mpathResult);
        if (mpathResult != null) {
            mpathResultRepository.save(mpathResult);
            TpathBsController tpath = new TpathBsController();
            tpath.smsOnReportFinalized(mpathResult.getResultTestResultId());
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("listofpathreport")
    public List<MpathResult> lolTestList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "trId", required = false) long trId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MpathResult> listOfPathReport = mpathResultRepository.findTestResultByTrId(trId);
        System.out.println(listOfPathReport.size());
        for (int i = 0; i < listOfPathReport.size(); i++) {
            if (listOfPathReport.get(i).getResultTestParameterId() != null) {
                if (listOfPathReport.get(i).getResultTestParameterId().getTpParameterId().getIsRounded()) {
                    if (listOfPathReport.get(i).getResultValue() != null && !listOfPathReport.get(i).getResultValue().isEmpty()) {
                        String resultValue = "" + listOfPathReport.get(i).getResultValue();
                        listOfPathReport.get(i).setResultValue("" + Math.round(Double.parseDouble("" + resultValue)));
                    }
                } else {
                    if (listOfPathReport.get(i).getResultValue() != null && !listOfPathReport.get(i).getResultValue().isEmpty()) {
                        String resultValue = "" + listOfPathReport.get(i).getResultValue();
                        if (listOfPathReport.get(i).getResultTestParameterId().getTpParameterId().getDecimalpoint() != null) {
                            if (listOfPathReport.get(i).getResultTestParameterId().getTpParameterId().getDecimalpoint() == 1) {
                                decimalFormat = new DecimalFormat("#.#");
                            }
                            if (listOfPathReport.get(i).getResultTestParameterId().getTpParameterId().getDecimalpoint() == 2) {
                                decimalFormat = new DecimalFormat("#.##");
                            }
                            if (listOfPathReport.get(i).getResultTestParameterId().getTpParameterId().getDecimalpoint() == 3) {
                                decimalFormat = new DecimalFormat("#.###");
                            }
                        }
                      /* if (listOfPathReport.get(i).getResultTestParameterId().getTpParameterId().getDecimalpoint() == 1) {
                           decimalFormat = new DecimalFormat("#.#");
                       }
                       if (listOfPathReport.get(i).getResultTestParameterId().getTpParameterId().getDecimalpoint() == 2) {
                           decimalFormat = new DecimalFormat("#.##");
                       }
                       if (listOfPathReport.get(i).getResultTestParameterId().getTpParameterId().getDecimalpoint() == 3) {
                           decimalFormat = new DecimalFormat("#.###");
                       }*/
//                       listOfPathReport.get(i).setResultValue(decimalFormat.format(Double.parseDouble("" + resultValue)));
                        try {
                            listOfPathReport.get(i).setResultValue(decimalFormat.format(Double.parseDouble("" + resultValue)));
                        } catch (Exception e) {
//                            listOfPathReport.get(i).setResultValue(decimalFormat.format(Double.parseDouble("" + resultValue)));
                        }
                    }
                }
            }
        }
        return listOfPathReport;
    }

/*
    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MpathResult> records;
        records = mpathResultRepository.findByResultResultTypeContains(key);
        automap.put("record", records);
        return automap;
    }

*/

/*    @RequestMapping("edittestresultreport")
    public Map<String, String> editReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MpathResult> mpathResult) {
        try {
            respMap = new HashMap<String, String>();
            System.out.println("mpathResult :" + mpathResult);
            if (mpathResult != null) {
                // System.out.println("my json :");
                for (int i = 0; i < mpathResult.size(); i++) {
                    MpathResult newResult = mpathResult.get(i);
                    if (newResult != null) {
                       MpathResult mResult = mpathResultRepository.getById(newResult.getResultId());

                        if (!mResult.getResultValue().equals(newResult.getResultValue()) && mResult.getIsMachineValue() == true) {
                       //     mResult.setIsMachineValue(false);
                            mResult.setResultValue(newResult.getResultValue());
                            mResult.setLastModifiedBy(newResult.getLastModifiedBy());
                            mResult.setLastModifiedByName(newResult.getLastModifiedByName());
                            mResult.setLastModifiedDate(newResult.getLastModifiedDate());
                            System.out.println("2222 mResult:" + mResult);
                            mpathResultRepository.save(mResult);
                            System.out.println("1111 newResult:" + newResult);

                        } else if (!mResult.getResultValue().equals(newResult.getResultValue())) {
                            mResult.setResultValue(newResult.getResultValue());
                            mResult.setLastModifiedBy(newResult.getLastModifiedBy());
                            mResult.setLastModifiedByName(newResult.getLastModifiedByName());
                            mResult.setLastModifiedDate(newResult.getLastModifiedDate());
                            System.out.println("1111 newResult:" + newResult);
                            System.out.println("2222 mResult:" + mResult);
                            mpathResultRepository.save(mResult);
                        }
                    }
                }
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Added Failed");
                return respMap;
            }
        } catch (Exception e) {
        }
        return null;
    }*/

    @RequestMapping("edittestresultreport/{psId}")
    public Map<String, String> editReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MpathResult> mpathResult, @PathVariable("psId") String psId) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            respMap = new HashMap<String, String>();
            System.out.println("mpathResult :" + mpathResult);
            System.out.println("psId :" + psId);
            if (mpathResult != null) {
                for (int i = 0; i < mpathResult.size(); i++) {
                    MpathResult newResult = mpathResult.get(i);
                    if (newResult.getResultId() > 0) {
                        MpathResult mResult = mpathResultRepository.getById(newResult.getResultId());
                        mResult.setResultValue(newResult.getResultValue());
                        mResult.setLastModifiedBy(newResult.getLastModifiedBy());
                        mResult.setLastModifiedByName(newResult.getLastModifiedByName());
                        mResult.setLastModifiedDate(newResult.getLastModifiedDate());
                        System.out.println("2222 mResult:" + mResult);
                        mpathResultRepository.save(mResult);
                        System.out.println("1111 newResult:" + newResult);

                    } else {
                        List<MpathResult> preReportList = mpathResultRepository.findByResultTestResultIdTrPsIdPsIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(psId));
                        System.out.println("preReport List :" + preReportList);
                        boolean flag = false;
                        for (int j = 0; j < preReportList.size(); j++) {
                            if (!newResult.getResultValue().equals(preReportList.get(j).getResultValue()) && preReportList.get(j).getIsMachineValue() == true && newResult.getResultTestParameterId().getTpId() == preReportList.get(j).getResultTestParameterId().getTpId()) {
                              /*  System.out.println("--------------inside logic------------------");
                                System.out.println("is machine : " + preReportList.get(j).getIsMachineValue());
                                System.out.println("value != j value :" + newResult.getResultValue() + " : " + preReportList.get(j).getResultValue());
                                System.out.println();
                                System.out.println(" tpId :" + newResult.getResultTestParameterId().getTpId());
                                System.out.println(" j :tpId :" + preReportList.get(j).getResultTestParameterId().getTpId());
                                System.out.println("--------------End logic------------------");
                              */
                                newResult.setIsMachineValue(false);
                                mpathResultRepository.save(newResult);
                                flag = true;
                                break;
                            }
                        }
                        if (flag == false) {
                          /*  System.out.println("************inside not match**************");
                            System.out.println("newResult.getResultValue() :" + newResult.getResultValue());
                            System.out.println("************End not match**************");*/
                            mpathResultRepository.save(newResult);
                        }
                        //
                    }
                }
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Added Failed");
                return respMap;
            }
        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping("bybillidandtestid/{billid}/{testid}")
    public List<MpathResult> read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("billid") long billId, @PathVariable("testid") long testId) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("BillID :" + billId + " Test ID :" + testId);
        List<MpathResult> mpathResultList = mpathResultRepository.findByResultTestResultIdTrPsIdPsBillIdBillIdAndResultTestResultIdTrPsIdPsTestIdTestIdEquals(billId, testId);
        return mpathResultList;
    }

    @RequestMapping("update")
    public MpathResult update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathResult mpathResult) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathResultRepository.save(mpathResult);
    }

  /*  @GetMapping
    @RequestMapping("list")
    public Iterable<MpathResult> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                      @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                      @RequestParam(value = "qString", required = false) String qString,
                                      @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                      @RequestParam(value = "col", required = false, defaultValue = "resultId") String col) {

        if (qString == null || qString.equals("")) {
            return mpathResultRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {

            return mpathResultRepository.findByResultResultTypeContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }*/

    @PutMapping("delete/{resultId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("resultId") Long resultId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathResult mpathResult = mpathResultRepository.getById(resultId);
        if (mpathResult != null) {
            mpathResult.setIsDeleted(true);
            mpathResultRepository.save(mpathResult);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    //emr
    @RequestMapping("get_report_by_psid/{psId}")
    public List<MpathResult> get_report_by_psid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathResultRepository.findByResultTestResultIdTrPsIdPsIdAndIsActiveTrueAndIsDeletedFalseOrderByResultTestResultIdDesc(psId);
    }

    @RequestMapping("get_report_by_psid_for_pdf/{psId}")
    public String get_report_by_psid_for_pdf(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        File newFile = null;
        try {
            List<MpathResult> list = mpathResultRepository.findByResultTestResultIdTrPsIdPsIdAndIsActiveTrueAndIsDeletedFalseOrderByResultTestResultIdDesc(psId);
            System.out.println("list : " + list.toString());

     /* for (int i = 0; i < list.size(); i++) {
        for (MpathResult rs : list) {
          long trId = rs.getResultTestResultId().getTrId();
          jsonObject.put("trId", "" + trId);
          jsonObject.put("unitId", "" + rs.getResultTestResultId().getTrPsId().getIsPerformedByUnitId());
          jsonObject.put("patientMrNo", "" + rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientMrNo());
          jsonObject.put("titleName", "" + rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserTitleId().getTitleName());
          String titleName = rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserTitleId().getTitleName();
          jsonObject.put("genderName", "" + rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserTitleId().getTitleGenderId().getGenderName());
          jsonObject.put("billNumber", "" + rs.getResultTestResultId().getTrPsId().getPsBillId().getBillNumber());
          jsonObject.put("sampleNumber", "" + rs.getResultTestResultId().getTrPsId().getPsSampleNumber());
          jsonObject.put("userFirstname", "" + rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserFirstname());
          jsonObject.put("userLastname", "" + rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserLastname());
          //jsonObject.put("userFullname", rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserFullname());
          jsonObject.put("userMobile", "" + rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserMobile());
          jsonObject.put("userAge", "" + rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserAge());
          jsonObject.put("createdDate", "" + rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getCreatedDate());
          jsonObject.put("ReportDateTime", "" + rs.getResultTestResultId().getTrPsId().getPsFinalizedDate());
          //jsonObject.put("Reffered By", rs.getResultTestResultId().getTrPsId().getPsBillId());

          array.put(jsonObject);
        }
      }*/
            String titleName = "";
            String userFirstname = "";
            String userLastname = "";
            long trId = 0;
            String userAge = "";
            String genderName = "";
            String sampleNumber = "";
            String ReportDateTime = "";
            String userMobile = "";
            String patientMrNo = "";
            String refferedBy = "";
            String TestName = "";
            String TestCode = "";
            for (int i = 0; i < list.size(); i++) {
                for (MpathResult rs : list) {
                    titleName = rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserTitleId().getTitleName();
                    userFirstname = rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserFirstname();
                    userLastname = rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserLastname();
                    trId = rs.getResultTestResultId().getTrId();
                    userAge = rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserAge();
                    genderName = rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserTitleId().getTitleGenderId().getGenderName();
                    sampleNumber = rs.getResultTestResultId().getTrPsId().getPsSampleNumber();
                    ReportDateTime = String.valueOf(rs.getResultTestResultId().getTrPsId().getPsFinalizedDate());
                    userMobile = rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserMobile();
                    patientMrNo = rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getVisitPatientId().getPatientMrNo();
                    //refferedBy  = rs.getResultTestResultId().getTrPsId().getPsBillId().getBillVisitId().getReferBy().getReName();
                    TestName = rs.getResultTestParameterId().getTpTestId().getTestName();
                    TestCode = rs.getResultTestParameterId().getTpTestId().getTestCode();
                }
            }
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
            String curDate = dateFormat.format(date);
            //System.out.println(curDate);
            //String rootPath = System.getProperty("catalina.base") + File.separator + "webapps";
            String rootPath = "C:" + File.separator + "Program Files" + File.separator + "Apache Software Foundation" + File.separator + "Tomcat 8.0" + File.separator + "webapps";
            File dir = new File(rootPath + File.separator + "CMS" + File.separator + "documents" + File.separator + "Report");
            String generatedFileName = titleName + "_" + userFirstname + "_" + userLastname + "_" + trId + "_" + curDate + ".pdf";
            System.out.println("generatedFileName : " + generatedFileName);
            if (!dir.exists())
                dir.mkdirs();
            newFile = new File(dir.getPath() + File.separator + generatedFileName);
            System.out.println("file path : " + newFile.getAbsolutePath());
            if (!newFile.exists()) {
                try {
                    Document document = new Document();
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(newFile));
                    document.open();
                    //document.add(new Paragraph("Hello World"));
                    Paragraph paragraph = new Paragraph();
                    Paragraph para = new Paragraph();
                    paragraph.add(new Chunk("Lab Report Details "));
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE);
                    document.add(Chunk.NEWLINE);
                    Paragraph p4;
                    p4 = new Paragraph("PRN Number : " + patientMrNo);
                    p4.setAlignment(p4.ALIGN_JUSTIFIED);
                    document.add(p4);
                    // document.add(Chunk.NEWLINE);
                    p4 = new Paragraph("patient Name : " + titleName + " " + userFirstname + " " + userLastname);
                    p4.setAlignment(p4.ALIGN_JUSTIFIED);
                    // p4.setIndentationLeft(12);
                    document.add(p4);
                    p4 = new Paragraph("Sample Number : " + sampleNumber);
                    p4.setAlignment(p4.ALIGN_JUSTIFIED);
                    document.add(p4);
                    p4 = new Paragraph("Report Date Time : " + ReportDateTime);
                    p4.setAlignment(p4.ALIGN_JUSTIFIED);
                    document.add(p4);
                    p4 = new Paragraph("Registration Date : ");
                    p4.setAlignment(p4.ALIGN_JUSTIFIED);
                    document.add(p4);
                    p4 = new Paragraph("Age : " + userAge);
                    p4.setAlignment(p4.ALIGN_JUSTIFIED);
                    document.add(p4);
                    p4 = new Paragraph("Sex : " + genderName);
                    p4.setAlignment(p4.ALIGN_JUSTIFIED);
                    document.add(p4);
                    p4 = new Paragraph("Mobile Number : " + userMobile);
                    p4.setAlignment(p4.ALIGN_JUSTIFIED);
                    document.add(p4);

         /* p4 = new Paragraph("Refferd By : "+refferedBy);
          p4.setAlignment(p4.ALIGN_JUSTIFIED);
          document.add(p4);

          p4 = new Paragraph("Sample Acceptance Date : ");
          p4.setAlignment(p4.ALIGN_JUSTIFIED);
          document.add(p4);

          p4 = new Paragraph("Doctor Name : ");
          p4.setAlignment(p4.ALIGN_JUSTIFIED);
          document.add(p4);*/
                    document.add(Chunk.NEWLINE);
                    document.add(Chunk.NEWLINE);
                    //Tble code
                    PdfPTable tbl = new PdfPTable(3);
                    tbl.setWidthPercentage(100);
                    PdfPCell cell = new PdfPCell(new Phrase(TestName + "(" + TestCode + ")", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    cell.setColspan(3);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5.0f);
                    //cell.setBackgroundColor(BaseColor.RED);
                    tbl.addCell(cell);
                    PdfPCell cell1 = new PdfPCell(new Paragraph("Parameter"));
                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(cell1);
                    PdfPCell cell2 = new PdfPCell(new Paragraph("Result value"));
                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(cell2);

/*         PdfPCell cell3 = new PdfPCell(new Paragraph("Technique value"));
          cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
          cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
          tbl.addCell(cell2);

          PdfPCell cell4 = new PdfPCell(new Paragraph("Referral Value"));
          cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
          cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
          tbl.addCell(cell3);*/
                    PdfPCell cell5 = new PdfPCell(new Paragraph("unit"));
                    cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(cell5);
                    for (int i = 0; i < list.size(); i++) {
                        PdfPCell ccell = new PdfPCell(new Paragraph(list.get(i).getResultTestParameterId().getTpParameterId().getParameterName()));
                        ccell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ccell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(ccell);
                        ccell = new PdfPCell(new Paragraph(list.get(i).getResultValue()));
                        ccell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ccell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(ccell);
                        ccell = new PdfPCell(new Paragraph(list.get(i).getResultTestParameterId().getTpParameterId().getParameterParameterUnitId().getPuName()));
                        ccell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ccell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(ccell);

                    }/*else{
            PdfPCell pCell = new PdfPCell(new Phrase("No such activities recorded during this period"));
            pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pCell.setColspan(3);
            tbl.addCell(pCell);
          }*/
                    // }
                    document.add(tbl);
                    document.add(Chunk.NEWLINE);
                    document.add(Chunk.NEWLINE);
                    // PDF Generation code End...
                    document.close();
                    writer.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return newFile.getName();
    }

    @RequestMapping("listoflastpathreport")
    public MpathResult testList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "trId", required = false) long trId) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("inside lol_test_list11111 :" + trId);
        // List<MpathResult> listOfPathReport = entityManager.createQuery("SELECT m FROM MpathResult m where m.resultTestResultId.trId=" + trId + " and m.isActive = 1 and m.isDeleted = 0", MpathResult.class).getResultList();
        List<MpathResult> listOfPathReport = mpathResultRepository.findTestResultByTrId(trId);
        // System.out.println("List of Path Report1111 :" + listOfPathReport);
        return listOfPathReport.get(listOfPathReport.size() - 1);
    }

}

