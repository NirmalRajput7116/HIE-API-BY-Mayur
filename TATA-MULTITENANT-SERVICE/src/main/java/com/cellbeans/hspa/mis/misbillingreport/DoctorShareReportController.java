package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/misdoctorsharereport")
public class DoctorShareReportController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CreateReport createReport;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @RequestMapping("visitwisereport/{unitList}/{fromdate}/{todate}")
    public List searchListofgetDoctorShareReportVisitWiseForDoctor(@RequestHeader("X-tenantId") String tenantName, @RequestBody DoctorShareReportSearchDTO doctorShareReportSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        double grandTotal = 0.0;
        // JSONArray resultArrayResp = new JSONArray();
        List resultList = new ArrayList<>();
        HashMap<String, List<DoctorShareReportListDTO>> drvisitMap = new HashMap<String, List<DoctorShareReportListDTO>>();
        //  Map<String,Map<String,DoctorShareReportListDTO>> drvisitMap=new HashMap<String,Map<String,DoctorShareReportListDTO>>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = "";
        String Query1 = "select ifnull(mp.patient_mr_no, '') as mrNo,   ifnull(concat(mu.user_firstname,' ', mu.user_lastname), '') as PatientName,  ifnull(tdsi.dsi_cons_doc_id1,'') as consDoc1,ifnull(tdsi.dsi_cons_doc_id2,'') as consDoc2,ifnull(tdsi.dsi_cons_doc_id3,'') as consDoc3, " +
                " tdsi.dsi_doc_share_amt1, tdsi.dsi_doc_share_amt2, tdsi.dsi_doc_share_amt3,  ifnull(concat(mu1.user_firstname,' ', mu1.user_lastname), '') as Doctor1, mp.patient_id,ifnull(concat(mu2.user_firstname,' ', mu2.user_lastname), '') as Doctor2," +
                " ifnull(concat(mu3.user_firstname,' ', mu1.user_lastname), '') as Doctor3, ifnull(tdsi.dsi_re_id1, '') as refdr1, ifnull(tdsi.dsi_re_id2,'') as refdr2,  ifnull(concat(muref1.user_firstname,' ', muref1.user_lastname), '') as refDoctor1,  ifnull(concat(muref2.user_firstname,' ', muref2.user_lastname), '') as refDoctor2 " +
                " from trn_doctor_share_ipd tdsi " +
                " inner join tbill_bill tb on tb.bill_id = tdsi.dsi_bill_id " +
                " left join trn_admission ta on ta.admission_id = tb.bill_admission_id " +
                " left join  mst_patient mp ON mp.patient_id = ta.admission_patient_id " +
                " left join  mst_user mu ON mu.user_id = mp.patient_user_id " +
                " LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id  " +
                " Left join mst_staff ms1 on ms1.staff_id = tdsi.dsi_cons_doc_id1 " +
                " Left join mst_staff ms2 on ms2.staff_id = tdsi.dsi_cons_doc_id2 " +
                " Left join mst_staff ms3 on ms3.staff_id = tdsi.dsi_cons_doc_id3 " +
                " left join  mst_user mu1 ON mu1.user_id = ms1.staff_user_id " +
                " left join mst_staff refms1 on refms1.staff_id = tdsi.dsi_re_id1 " +
                " left join mst_staff refms2 on refms2.staff_id = tdsi.dsi_re_id2 " +
                " left join  mst_user muref1 ON muref1.user_id = refms1.staff_user_id " +
                " left join  mst_user muref2 ON muref2.user_id = refms2.staff_user_id " +
                " left join  mst_user mu2 ON mu2.user_id = ms2.staff_user_id " +
                " left join  mst_user mu3 ON mu3.user_id = ms3.staff_user_id " +
                " where tdsi.is_active = 1 and tdsi.is_deleted = 0  and tb.is_cancelled = 0 and tb.is_active =1 and tb.is_deleted = 0 and tb.final_bill = 1 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (doctorShareReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(tdsi.created_date)=curdate()) ";

        } else {
            Query1 += " and (date(tdsi.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (doctorShareReportSearchDTO.getPatientName() != null && !doctorShareReportSearchDTO.getPatientName().equals("")) {
            Query1 += " and CONCAT(mu.user_firstname,' ',mu.user_lastname) LIKE '%" + doctorShareReportSearchDTO.getPatientName() + "%'";

        }
        if (doctorShareReportSearchDTO.getMrNo() != null && !doctorShareReportSearchDTO.getMrNo().equals("")) {
            Query1 += " and mp.patient_mr_no LIKE '%" + doctorShareReportSearchDTO.getMrNo() + "%'";

        }
        // String CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        // String SummaryQuery = " select IFNULL(sum(Gross),0.0) AS totalgross, IFNULL(sum(Discount),0.0) AS totalDiscount, IFNULL(sum(Net),0.0) AS totalNet, IFNULL(sum(Paid),0.0) AS totalPaid,  IFNULL(sum(Outstanding),0.0) AS totalOutstanding from (" + MainQuery + " ) AS x";
        try {
            // Query1 += " limit " + ((doctorShareReportSearchDTO.getOffset() - 1) * doctorShareReportSearchDTO.getLimit()) + "," + doctorShareReportSearchDTO.getLimit();
            System.out.println(MainQuery);
            List<Object[]> bdoctorShareReport = entityManager.createNativeQuery(Query1).getResultList();
            //  BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            // count = cc.longValue();
            // List<Object[]>  billCumSummaryReport = entityManager.createNativeQuery(SummaryQuery).getResultList();
//                for (Object[] tempObj:billCumSummaryReport) {
//                    totalGross = Double.parseDouble("" + tempObj[0]);
//                    totalDiscount = Double.parseDouble("" + tempObj[1]);
//                    totalNet = Double.parseDouble("" + tempObj[2]);
//                    totalPaid =  Double.parseDouble("" + tempObj[3]);
//                    totalOutstanding = Double.parseDouble("" + tempObj[4]);
//                }
            for (Object[] obj : bdoctorShareReport) {
                // Map<String, DoctorShareReportListDTO> hm = new HashMap<String, DoctorShareReportListDTO>();
                //objDoctorSharevistwiseListDTO.setDrName("" + obj[8]);
                if (("" + obj[2] != null) && !("".equalsIgnoreCase(("" + obj[2])))) {
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO1 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList1 = new ArrayList<DoctorShareReportListDTO>();
                    String key1 = "" + obj[9] + "" + obj[2];
                    String key11 = "" + obj[2];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key11.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO1.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO1.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO1.setDrAmount(Double.parseDouble("" + obj[5]));
                            /// listOfDoctorShareDTOList.add(objDoctorSharevistwiseListDTO);
                            // hm.put(key1, objDoctorSharevistwiseListDTO1);
                            if (drvisitMap.containsKey(key11)) {
                                objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                                listOfDoctorShareDTOList1 = drvisitMap.get(key11);
                                listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
//                            for (DoctorShareReportListDTO obj1 : listOfDoctorShareDTOList1) {
//                                totaldrAmount1 = totaldrAmount1 + obj1.getDrAmount();
//                                obj1.setTotaldrAmount(totaldrAmount1);
//                            }
                                drvisitMap.put(key11, listOfDoctorShareDTOList1);
                            } else {
                                // objDoctorSharevistwiseListDTO1.setTotaldrAmount(objDoctorSharevistwiseListDTO1.getDrAmount());
                                objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                                listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
                                drvisitMap.put(key11, listOfDoctorShareDTOList1);
                            }
                        }

                    } else {
                        objDoctorSharevistwiseListDTO1.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO1.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO1.setDrAmount(Double.parseDouble("" + obj[5]));
                        /// listOfDoctorShareDTOList.add(objDoctorSharevistwiseListDTO);
                        // hm.put(key1, objDoctorSharevistwiseListDTO1);
                        if (drvisitMap.containsKey(key11)) {
                            objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                            listOfDoctorShareDTOList1 = drvisitMap.get(key11);
                            listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
//                            for (DoctorShareReportListDTO obj1 : listOfDoctorShareDTOList1) {
//                                totaldrAmount1 = totaldrAmount1 + obj1.getDrAmount();
//                                obj1.setTotaldrAmount(totaldrAmount1);
//                            }
                            drvisitMap.put(key11, listOfDoctorShareDTOList1);
                        } else {
                            // objDoctorSharevistwiseListDTO1.setTotaldrAmount(objDoctorSharevistwiseListDTO1.getDrAmount());
                            objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                            listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
                            drvisitMap.put(key11, listOfDoctorShareDTOList1);
                        }
                    }
                }
//                    if (hm.containsKey(key1)) {
//                            objDoctorSharevistwiseListDTO.setDrAmount(objDoctorSharevistwiseListDTO.getDrAmount() + Double.parseDouble("" + obj[6]));
//
//                        } else {
//                            objDoctorSharevistwiseListDTO.setDrAmount(Double.parseDouble("" + obj[5]));
//                            /// listOfDoctorShareDTOList.add(objDoctorSharevistwiseListDTO);
//                            hm.put(key1, objDoctorSharevistwiseListDTO);
//                            if (drvisitMap.containsKey(key11)) {
//                                listOfDoctorShareDTOList1 = drvisitMap.get(key11);
//                                listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO);
//                                drvisitMap.put(key11, listOfDoctorShareDTOList1);
//                            } else {
//                                listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO);
//                                drvisitMap.put(key11, listOfDoctorShareDTOList1);
//                            }
//                        }
                if (("" + obj[3] != null) && !("".equalsIgnoreCase(("" + obj[3])))) {
                    // double totaldrAmount2 = 0.0;
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO2 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList2 = new ArrayList<DoctorShareReportListDTO>();
                    String key2 = "" + obj[9] + "" + obj[3];
                    String key22 = "" + obj[3];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key22.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO2.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO2.setPatientName("" + obj[1]);
                            // if (hm.containsKey(key2)) {
//  objDoctorSharevistwiseListDTO.setDrAmount(objDoctorSharevistwiseListDTO.getDrAmount() + Double.parseDouble("" + obj[7]));
                            objDoctorSharevistwiseListDTO2.setDrAmount(Double.parseDouble("" + obj[6]));
                            // listOfDoctorShareDTOList.add(objDoctorSharevistwiseListDTO);
// hm.put(key2, objDoctorSharevistwiseListDTO2);
                            if (drvisitMap.containsKey(key22)) {
                                objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                                listOfDoctorShareDTOList2 = drvisitMap.get(key22);
                                listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
//                            for (DoctorShareReportListDTO obj2 : listOfDoctorShareDTOList2) {
//                                totaldrAmount2 = totaldrAmount2 + obj2.getDrAmount();
//                                obj2.setTotaldrAmount(totaldrAmount2);
//                            }
                                drvisitMap.put(key22, listOfDoctorShareDTOList2);
                            } else {
                                //  objDoctorSharevistwiseListDTO2.setTotaldrAmount(objDoctorSharevistwiseListDTO2.getDrAmount());
                                objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                                listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
                                drvisitMap.put(key22, listOfDoctorShareDTOList2);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO2.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO2.setPatientName("" + obj[1]);
                        // if (hm.containsKey(key2)) {
                        //  objDoctorSharevistwiseListDTO.setDrAmount(objDoctorSharevistwiseListDTO.getDrAmount() + Double.parseDouble("" + obj[7]));
                        objDoctorSharevistwiseListDTO2.setDrAmount(Double.parseDouble("" + obj[6]));
                        // listOfDoctorShareDTOList.add(objDoctorSharevistwiseListDTO);
                        // hm.put(key2, objDoctorSharevistwiseListDTO2);
                        if (drvisitMap.containsKey(key22)) {
                            objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                            listOfDoctorShareDTOList2 = drvisitMap.get(key22);
                            listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
//                            for (DoctorShareReportListDTO obj2 : listOfDoctorShareDTOList2) {
//                                totaldrAmount2 = totaldrAmount2 + obj2.getDrAmount();
//                                obj2.setTotaldrAmount(totaldrAmount2);
//                            }
                            drvisitMap.put(key22, listOfDoctorShareDTOList2);
                        } else {
                            //  objDoctorSharevistwiseListDTO2.setTotaldrAmount(objDoctorSharevistwiseListDTO2.getDrAmount());
                            objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                            listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
                            drvisitMap.put(key22, listOfDoctorShareDTOList2);
                        }
//                        } else {
//                            objDoctorSharevistwiseListDTO.setDrAmount(Double.parseDouble("" + obj[6]));
//                            // listOfDoctorShareDTOList.add(objDoctorSharevistwiseListDTO);
//                            hm.put(key2, objDoctorSharevistwiseListDTO);
//                            if (drvisitMap.containsKey(key22)) {
//                                listOfDoctorShareDTOList2 = drvisitMap.get(key22);
//                                listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO);
//                                drvisitMap.put(key22, listOfDoctorShareDTOList2);
//                            } else {
//                                listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO);
//                                drvisitMap.put(key22, listOfDoctorShareDTOList2);
//                            }
//                        }
                    }
                }
                if (("" + obj[4] != null) && !("".equalsIgnoreCase(("" + obj[4])))) {
                    // double totaldrAmount3 = 0.0;
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO3 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList3 = new ArrayList<DoctorShareReportListDTO>();
                    String key3 = "" + obj[9] + "" + obj[4];
                    String key33 = "" + obj[4];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key33.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO3.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO3.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO3.setDrAmount(Double.parseDouble("" + obj[7]));
                            if (drvisitMap.containsKey(key33)) {
                                objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                                listOfDoctorShareDTOList3 = drvisitMap.get(key33);
                                listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
//                            for (DoctorShareReportListDTO obj3 : listOfDoctorShareDTOList3) {
//                            totaldrAmount3 = totaldrAmount3 + obj3.getDrAmount();
//                            obj3.setTotaldrAmount(totaldrAmount3);
//                            }
                                drvisitMap.put(key33, listOfDoctorShareDTOList3);
                            } else {
                                // objDoctorSharevistwiseListDTO3.setTotaldrAmount(objDoctorSharevistwiseListDTO3.getDrAmount());
                                objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                                listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
                                drvisitMap.put(key33, listOfDoctorShareDTOList3);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO3.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO3.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO3.setDrAmount(Double.parseDouble("" + obj[7]));
                        if (drvisitMap.containsKey(key33)) {
                            objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                            listOfDoctorShareDTOList3 = drvisitMap.get(key33);
                            listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
//                            for (DoctorShareReportListDTO obj3 : listOfDoctorShareDTOList3) {
//                            totaldrAmount3 = totaldrAmount3 + obj3.getDrAmount();
//                            obj3.setTotaldrAmount(totaldrAmount3);
//                            }
                            drvisitMap.put(key33, listOfDoctorShareDTOList3);
                        } else {
                            // objDoctorSharevistwiseListDTO3.setTotaldrAmount(objDoctorSharevistwiseListDTO3.getDrAmount());
                            objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                            listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
                            drvisitMap.put(key33, listOfDoctorShareDTOList3);
                        }
                    }
//                        if (hm.containsKey(key3)) {
//                            objDoctorSharevistwiseListDTO.setDrAmount(objDoctorSharevistwiseListDTO.getDrAmount() + Double.parseDouble("" + obj[7]));
//
//                        } else {
//                            objDoctorSharevistwiseListDTO.setDrAmount(Double.parseDouble("" + obj[7]));
//                            // listOfDoctorShareDTOList.add(objDoctorSharevistwiseListDTO);
//                            hm.put(key3, objDoctorSharevistwiseListDTO);
//                            if (drvisitMap.containsKey(key33)) {
//                                listOfDoctorShareDTOList3 = drvisitMap.get(key33);
//                                listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO);
//                                drvisitMap.put(key33, listOfDoctorShareDTOList3);
//                            } else {
//                                listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO);
//                                drvisitMap.put(key33, listOfDoctorShareDTOList3);
//                            }
//                        }
                }
                if (("" + obj[12] != null) && !("".equalsIgnoreCase(("" + obj[12])))) {
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO4 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList4 = new ArrayList<DoctorShareReportListDTO>();
                    String key44 = "" + obj[12];
                    if (doctorShareReportSearchDTO.getRefDrId() != null && !doctorShareReportSearchDTO.getRefDrId().equals("")) {
                        if (key44.equalsIgnoreCase(doctorShareReportSearchDTO.getRefDrId())) {
                            objDoctorSharevistwiseListDTO4.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO4.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO4.setDrAmount(Double.parseDouble("" + obj[6]));
                            if (drvisitMap.containsKey(key44)) {
                                objDoctorSharevistwiseListDTO4.setDrName("" + obj[14]);
                                listOfDoctorShareDTOList4 = drvisitMap.get(key44);
                                listOfDoctorShareDTOList4.add(objDoctorSharevistwiseListDTO4);
//
                                drvisitMap.put(key44, listOfDoctorShareDTOList4);
                            } else {
                                objDoctorSharevistwiseListDTO4.setDrName("" + obj[14]);
                                listOfDoctorShareDTOList4.add(objDoctorSharevistwiseListDTO4);
                                drvisitMap.put(key44, listOfDoctorShareDTOList4);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO4.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO4.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO4.setDrAmount(Double.parseDouble("" + obj[6]));
                        if (drvisitMap.containsKey(key44)) {
                            objDoctorSharevistwiseListDTO4.setDrName("" + obj[14]);
                            listOfDoctorShareDTOList4 = drvisitMap.get(key44);
                            listOfDoctorShareDTOList4.add(objDoctorSharevistwiseListDTO4);
                            drvisitMap.put(key44, listOfDoctorShareDTOList4);
                        } else {
                            objDoctorSharevistwiseListDTO4.setDrName("" + obj[14]);
                            listOfDoctorShareDTOList4.add(objDoctorSharevistwiseListDTO4);
                            drvisitMap.put(key44, listOfDoctorShareDTOList4);
                        }

                    }
                }
                if (("" + obj[13] != null) && !("".equalsIgnoreCase(("" + obj[13])))) {
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO5 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList5 = new ArrayList<DoctorShareReportListDTO>();
                    String key55 = "" + obj[13];
                    if (doctorShareReportSearchDTO.getRefDrId() != null && !doctorShareReportSearchDTO.getRefDrId().equals("")) {
                        if (key55.equalsIgnoreCase(doctorShareReportSearchDTO.getRefDrId())) {
                            objDoctorSharevistwiseListDTO5.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO5.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO5.setDrAmount(Double.parseDouble("" + obj[6]));
                            if (drvisitMap.containsKey(key55)) {
                                objDoctorSharevistwiseListDTO5.setDrName("" + obj[15]);
                                listOfDoctorShareDTOList5 = drvisitMap.get(key55);
                                listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
//
                                drvisitMap.put(key55, listOfDoctorShareDTOList5);
                            } else {
                                objDoctorSharevistwiseListDTO5.setDrName("" + obj[15]);
                                listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
                                drvisitMap.put(key55, listOfDoctorShareDTOList5);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO5.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO5.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO5.setDrAmount(Double.parseDouble("" + obj[6]));
                        if (drvisitMap.containsKey(key55)) {
                            objDoctorSharevistwiseListDTO5.setDrName("" + obj[14]);
                            listOfDoctorShareDTOList5 = drvisitMap.get(key55);
                            listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
                            drvisitMap.put(key55, listOfDoctorShareDTOList5);
                        } else {
                            objDoctorSharevistwiseListDTO5.setDrName("" + obj[14]);
                            listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
                            drvisitMap.put(key55, listOfDoctorShareDTOList5);
                        }

                    }
                }

            }
            // objDoctorSharevistwiseListDTO.setDrName("" + obj[2]);
            // objDoctorSharevistwiseListDTO.setDrAmount(Double.parseDouble("" + obj[3]));
            // objDoctorSharevistwiseListDTO.setGrandTotal(Double.parseDouble("" + obj[28]));
            //objDoctorSharevistwiseListDTO.setCount(count);
            // listOfDoctorShareDTOList.add(drvisitMap.values());
        } catch (Exception e) {
            e.printStackTrace();

        }
        for (Map.Entry<String, List<DoctorShareReportListDTO>> entry : drvisitMap.entrySet()) {
            //String key = entry.getKey();
            count = drvisitMap.entrySet().size();
            List<DoctorShareReportListDTO> value = new ArrayList<DoctorShareReportListDTO>();
            value = entry.getValue();
            // value.subList((Integer.valueOf(doctorShareReportSearchDTO.getOffset()) - 1)*10,Integer.valueOf(doctorShareReportSearchDTO.getOffset() * Integer.valueOf(doctorShareReportSearchDTO.getLimit())) );
            //count = Long.valueOf(drvisitMap.size());
            double totaldrAmount = 0.0;
            for (DoctorShareReportListDTO obj3 : value) {
                totaldrAmount = totaldrAmount + obj3.getDrAmount();
                grandTotal = grandTotal + obj3.getDrAmount();
                obj3.setCount(count);
                // obj3.setTotaldrAmount(totaldrAmount);
            }
            for (DoctorShareReportListDTO obj31 : value) {
                obj31.setGrandTotal(grandTotal);
                obj31.setTotaldrAmount(totaldrAmount);
            }
            resultList.add(value);
            // continue here
        }
        int limit = (int) count;
        int diff = ((int) count - (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())));
        if (diff >= 0) {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()));
        } else {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())) + diff;
        }
        // List<DoctorShareReportListDTO> list = new ArrayList<DoctorShareReportListDTO>(drvisitMap.values());
        return resultList.subList((Integer.valueOf(doctorShareReportSearchDTO.getOffset()) - 1) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()), limit);
    }

    @RequestMapping("visitwisehospitalreport/{unitList}/{fromdate}/{todate}")
    public List searchListofgetDoctorShareReportVisitWiseForHospital(@RequestHeader("X-tenantId") String tenantName, @RequestBody DoctorShareReportSearchDTO doctorShareReportSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        double grandTotal = 0.0;
        JSONArray resultArrayResp = new JSONArray();
        List resultList = new ArrayList<>();
        HashMap<String, List<DoctorShareReportListDTO>> drvisitMap = new HashMap<String, List<DoctorShareReportListDTO>>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = "";
        String Query1 = "select ifnull(mp.patient_mr_no, '') as mrNo,   ifnull(concat(mu.user_firstname,' ', mu.user_lastname), '') as PatientName,  ifnull(tdsi.dsi_cons_doc_id1,'') as consDoc1,ifnull(tdsi.dsi_cons_doc_id2,'') as consDoc2,ifnull(tdsi.dsi_cons_doc_id3,'') as consDoc3," +
                "  tdsi.dsi_doc_share_amt1, tdsi.dsi_doc_share_amt2, tdsi.dsi_doc_share_amt3,  ifnull(concat(mu1.user_firstname,' ', mu1.user_lastname), '') as Doctor1, mp.patient_id," +
                "  ifnull(concat(mu2.user_firstname,' ', mu2.user_lastname), '') as Doctor2, " +
                "  ifnull(concat(mu3.user_firstname,' ', mu1.user_lastname), '') as Doctor3, ifnull(tdsi.dsi_re_id1, '') as refdr1, ifnull(tdsi.dsi_re_id2,'') as refdr2,  ifnull(mre1.re_name, '') as refDoctor1,  ifnull(mre2.re_name, '') as refDoctor2, tb.bill_number, ifnull(mbs1.service_name,'') as serviceName1, ifnull(mbs2.service_name,'') as serviceName2,ifnull(mbs3.service_name,'') as serviceName3  " +
                "  from trn_doctor_share_ipd tdsi  " +
                "  inner join tbill_bill tb on tb.bill_id = tdsi.dsi_bill_id " +
                "  left join trn_admission ta on ta.admission_id = tb.bill_admission_id   " +
                "  left join  mst_patient mp ON mp.patient_id = ta.admission_patient_id  " +
                " left join  mst_user mu ON mu.user_id = mp.patient_user_id " +
                " LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id   " +
                " Left join mst_staff ms1 on ms1.staff_id = tdsi.dsi_cons_doc_id1 " +
                " Left join mst_staff ms2 on ms2.staff_id = tdsi.dsi_cons_doc_id2 " +
                " Left join mst_staff ms3 on ms3.staff_id = tdsi.dsi_cons_doc_id3   " +
                " left join mst_referring_entity mre1 on mre1.re_id = tdsi.dsi_re_id1 " +
                " left join mst_referring_entity mre2 on mre2.re_id = tdsi.dsi_re_id2 " +
                " left join mbill_doctor_share mds1 on mds1.ds_staff_id = tdsi.dsi_cons_doc_id1 " +
                " left join mbill_doctor_share mds2 on mds2.ds_staff_id = tdsi.dsi_cons_doc_id2 " +
                " left join mbill_doctor_share mds3 on mds3.ds_staff_id = tdsi.dsi_cons_doc_id3 " +
                " left join mbill_service mbs1 on mbs1.service_id  = mds1.ds_serviceid " +
                " left join mbill_service mbs2 on mbs2.service_id  = mds2.ds_serviceid " +
                " left join mbill_service mbs3 on mbs3.service_id  = mds3.ds_serviceid " +
                " left join  mst_user mu1 ON mu1.user_id = ms1.staff_user_id          " +
                " left join  mst_user mu2 ON mu2.user_id = ms2.staff_user_id  " +
                " left join  mst_user mu3 ON mu3.user_id = ms3.staff_user_id  " +
                " where tdsi.is_active = 1 and tdsi.is_deleted = 0   and tb.is_cancelled = 0 and tb.is_active =1 and tb.is_deleted = 0 and tb.final_bill = 1 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (doctorShareReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(tdsi.created_date)=curdate()) ";

        } else {
            Query1 += " and (date(tdsi.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (doctorShareReportSearchDTO.getPatientName() != null && !doctorShareReportSearchDTO.getPatientName().equals("")) {
            Query1 += " and CONCAT(mu.user_firstname,' ',mu.user_lastname) LIKE '%" + doctorShareReportSearchDTO.getPatientName() + "%'";

        }
        if (doctorShareReportSearchDTO.getMrNo() != null && !doctorShareReportSearchDTO.getMrNo().equals("")) {
            Query1 += " and mp.patient_mr_no LIKE '%" + doctorShareReportSearchDTO.getMrNo() + "%'";

        }
        try {
            System.out.println(MainQuery);
            List<Object[]> bdoctorShareReport = entityManager.createNativeQuery(Query1).getResultList();
            // BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            for (Object[] obj : bdoctorShareReport) {
                if (("" + obj[2] != null) && !("".equalsIgnoreCase(("" + obj[2])))) {
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO1 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList1 = new ArrayList<DoctorShareReportListDTO>();
                    String key1 = "" + obj[9] + "" + obj[2];
                    String key11 = "" + obj[2];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key11.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO1.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO1.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO1.setDrAmount(Double.parseDouble("" + obj[5]));
                            objDoctorSharevistwiseListDTO1.setServiceName("" + obj[17]);
                            objDoctorSharevistwiseListDTO1.setBillNo("" + obj[16]);
                            if (drvisitMap.containsKey(key11)) {
                                objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                                listOfDoctorShareDTOList1 = drvisitMap.get(key11);
                                listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
                                drvisitMap.put(key11, listOfDoctorShareDTOList1);
                            } else {
                                // objDoctorSharevistwiseListDTO1.setTotaldrAmount(objDoctorSharevistwiseListDTO1.getDrAmount());
                                objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                                listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
                                drvisitMap.put(key11, listOfDoctorShareDTOList1);
                            }
                        }

                    } else {
                        objDoctorSharevistwiseListDTO1.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO1.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO1.setDrAmount(Double.parseDouble("" + obj[5]));
                        objDoctorSharevistwiseListDTO1.setServiceName("" + obj[17]);
                        objDoctorSharevistwiseListDTO1.setBillNo("" + obj[16]);
                        if (drvisitMap.containsKey(key11)) {
                            objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                            listOfDoctorShareDTOList1 = drvisitMap.get(key11);
                            listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
//
                            drvisitMap.put(key11, listOfDoctorShareDTOList1);
                        } else {
                            // objDoctorSharevistwiseListDTO1.setTotaldrAmount(objDoctorSharevistwiseListDTO1.getDrAmount());
                            objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                            listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
                            drvisitMap.put(key11, listOfDoctorShareDTOList1);
                        }
                    }
                }
                if (("" + obj[3] != null) && !("".equalsIgnoreCase(("" + obj[3])))) {
                    // double totaldrAmount2 = 0.0;
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO2 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList2 = new ArrayList<DoctorShareReportListDTO>();
                    String key2 = "" + obj[9] + "" + obj[3];
                    String key22 = "" + obj[3];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key22.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO2.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO2.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO2.setDrAmount(Double.parseDouble("" + obj[6]));
                            objDoctorSharevistwiseListDTO2.setServiceName("" + obj[18]);
                            objDoctorSharevistwiseListDTO2.setBillNo("" + obj[16]);
                            if (drvisitMap.containsKey(key22)) {
                                objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                                listOfDoctorShareDTOList2 = drvisitMap.get(key22);
                                listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
//
                                drvisitMap.put(key22, listOfDoctorShareDTOList2);
                            } else {
                                //  objDoctorSharevistwiseListDTO2.setTotaldrAmount(objDoctorSharevistwiseListDTO2.getDrAmount());
                                objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                                listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
                                drvisitMap.put(key22, listOfDoctorShareDTOList2);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO2.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO2.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO2.setDrAmount(Double.parseDouble("" + obj[6]));
                        objDoctorSharevistwiseListDTO2.setServiceName("" + obj[18]);
                        objDoctorSharevistwiseListDTO2.setBillNo("" + obj[16]);
                        if (drvisitMap.containsKey(key22)) {
                            objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                            listOfDoctorShareDTOList2 = drvisitMap.get(key22);
                            listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
//
                            drvisitMap.put(key22, listOfDoctorShareDTOList2);
                        } else {
                            //  objDoctorSharevistwiseListDTO2.setTotaldrAmount(objDoctorSharevistwiseListDTO2.getDrAmount());
                            objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                            listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
                            drvisitMap.put(key22, listOfDoctorShareDTOList2);
                        }
//
                    }
                }
                if (("" + obj[4] != null) && !("".equalsIgnoreCase(("" + obj[4])))) {
                    // double totaldrAmount3 = 0.0;
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO3 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList3 = new ArrayList<DoctorShareReportListDTO>();
                    String key3 = "" + obj[9] + "" + obj[4];
                    String key33 = "" + obj[4];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key33.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO3.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO3.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO3.setDrAmount(Double.parseDouble("" + obj[7]));
                            objDoctorSharevistwiseListDTO3.setServiceName("" + obj[19]);
                            objDoctorSharevistwiseListDTO3.setBillNo("" + obj[16]);
                            if (drvisitMap.containsKey(key33)) {
                                objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                                listOfDoctorShareDTOList3 = drvisitMap.get(key33);
                                listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
//
                                drvisitMap.put(key33, listOfDoctorShareDTOList3);
                            } else {
                                // objDoctorSharevistwiseListDTO3.setTotaldrAmount(objDoctorSharevistwiseListDTO3.getDrAmount());
                                objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                                listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
                                drvisitMap.put(key33, listOfDoctorShareDTOList3);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO3.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO3.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO3.setDrAmount(Double.parseDouble("" + obj[7]));
                        objDoctorSharevistwiseListDTO3.setServiceName("" + obj[19]);
                        objDoctorSharevistwiseListDTO3.setBillNo("" + obj[16]);
                        if (drvisitMap.containsKey(key33)) {
                            objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                            listOfDoctorShareDTOList3 = drvisitMap.get(key33);
                            listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
//
                            drvisitMap.put(key33, listOfDoctorShareDTOList3);
                        } else {
                            // objDoctorSharevistwiseListDTO3.setTotaldrAmount(objDoctorSharevistwiseListDTO3.getDrAmount());
                            objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                            listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
                            drvisitMap.put(key33, listOfDoctorShareDTOList3);
                        }
                    }
//
                }
//                    if (("" + obj[12] != null) && !("".equalsIgnoreCase(("" + obj[12])))) {
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        for (Map.Entry<String, List<DoctorShareReportListDTO>> entry : drvisitMap.entrySet()) {
            //String key = entry.getKey();
            count = drvisitMap.entrySet().size();
            List<DoctorShareReportListDTO> value = new ArrayList<DoctorShareReportListDTO>();
            value = entry.getValue();
            double totaldrAmount = 0.0;
            double totalbillAmount = 0.0;
            for (DoctorShareReportListDTO obj3 : value) {
                totaldrAmount = totaldrAmount + obj3.getDrAmount();
                grandTotal = grandTotal + obj3.getDrAmount();
                // totalbillAmount = totalbillAmount + Double.parseDouble(obj3.getBillAmount());
                obj3.setCount(count);
                // obj3.setTotaldrAmount(totaldrAmount);
            }
            for (DoctorShareReportListDTO obj31 : value) {
                obj31.setGrandTotal(grandTotal);
                obj31.setTotalbillAmount(totalbillAmount);
                obj31.setTotaldrAmount(totaldrAmount);
            }
            resultList.add(value);
            // continue here
        }
        int limit = (int) count;
        int diff = ((int) count - (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())));
        if (diff >= 0) {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()));
        } else {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())) + diff;
        }
        // List<DoctorShareReportListDTO> list = new ArrayList<DoctorShareReportListDTO>(drvisitMap.values());
        return resultList.subList((Integer.valueOf(doctorShareReportSearchDTO.getOffset()) - 1) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()), limit);
    }

    @RequestMapping("referwisedoctorreport/{unitList}/{fromdate}/{todate}")
    public List searchListofgetDoctorShareReferWiseForDoctor(@RequestHeader("X-tenantId") String tenantName, @RequestBody DoctorShareReportSearchDTO doctorShareReportSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        double grandTotal = 0.0;
        //JSONArray resultArrayResp = new JSONArray();
        List resultList = new ArrayList<>();
        HashMap<String, List<DoctorShareReportListDTO>> drvisitMap = new HashMap<String, List<DoctorShareReportListDTO>>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = "";
        String Query1 = "select ifnull(mp.patient_mr_no, '') as mrNo,  ifnull(concat(mu.user_firstname,' ', mu.user_lastname), '') as PatientName, ifnull(tdsi.dsi_doc_share_amt4, '') as doctor1, ifnull(tdsi.dsi_doc_share_amt5, '') as doctor2, " +
                " ifnull(tdsi.dsi_re_id1, '') as refdr1, ifnull(tdsi.dsi_re_id2,'') as refdr2,  ifnull(mre1.re_name, '') as refDoctor1,  ifnull(mre2.re_name, '') as refDoctor2 " +
                " from trn_doctor_share_ipd tdsi " +
                " inner join tbill_bill tb on tb.bill_id = tdsi.dsi_bill_id " +
                " left join trn_admission ta on ta.admission_id = tb.bill_admission_id " +
                " left join  mst_patient mp ON mp.patient_id = ta.admission_patient_id " +
                " left join  mst_user mu ON mu.user_id = mp.patient_user_id " +
                " left join mst_staff refms1 on refms1.staff_id = tdsi.dsi_re_id1 " +
                " left join mst_staff refms2 on refms2.staff_id = tdsi.dsi_re_id2 " +
                " left join mst_referring_entity mre1 on mre1.re_id = tdsi.dsi_re_id1 " +
                " left join mst_referring_entity mre2 on mre2.re_id = tdsi.dsi_re_id2 " +
                " where tdsi.is_active = 1 and tdsi.is_deleted = 0  and tb.is_cancelled = 0 and tb.is_active =1 and tb.is_deleted = 0 and tb.final_bill = 1 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (doctorShareReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(tdsi.created_date)=curdate()) ";

        } else {
            Query1 += " and (date(tdsi.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (doctorShareReportSearchDTO.getPatientName() != null && !doctorShareReportSearchDTO.getPatientName().equals("")) {
            Query1 += " and CONCAT(mu.user_firstname,' ',mu.user_lastname) LIKE '%" + doctorShareReportSearchDTO.getPatientName() + "%'";

        }
        if (doctorShareReportSearchDTO.getMrNo() != null && !doctorShareReportSearchDTO.getMrNo().equals("")) {
            Query1 += " and mp.patient_mr_no LIKE '%" + doctorShareReportSearchDTO.getMrNo() + "%'";

        }
        String CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        try {
            System.out.println(MainQuery);
            List<Object[]> bdoctorShareReport = entityManager.createNativeQuery(Query1).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            for (Object[] obj : bdoctorShareReport) {
                if (("" + obj[4] != null) && !("".equalsIgnoreCase(("" + obj[4])))) {
                    DoctorShareReportListDTO objDoctorShareRefdrwiseListDTO4 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList4 = new ArrayList<DoctorShareReportListDTO>();
                    String key44 = "" + obj[4];
                    if (doctorShareReportSearchDTO.getRefDrId() != null && !doctorShareReportSearchDTO.getRefDrId().equals("")) {
                        if (key44.equalsIgnoreCase(doctorShareReportSearchDTO.getRefDrId())) {
                            objDoctorShareRefdrwiseListDTO4.setMrNo("" + obj[0]);
                            objDoctorShareRefdrwiseListDTO4.setPatientName("" + obj[1]);
                            objDoctorShareRefdrwiseListDTO4.setDrAmount(Double.parseDouble("" + obj[2]));
                            if (drvisitMap.containsKey(key44)) {
                                objDoctorShareRefdrwiseListDTO4.setDrName("" + obj[6]);
                                listOfDoctorShareDTOList4 = drvisitMap.get(key44);
                                listOfDoctorShareDTOList4.add(objDoctorShareRefdrwiseListDTO4);
                                drvisitMap.put(key44, listOfDoctorShareDTOList4);
                            } else {
                                objDoctorShareRefdrwiseListDTO4.setDrName("" + obj[6]);
                                listOfDoctorShareDTOList4.add(objDoctorShareRefdrwiseListDTO4);
                                drvisitMap.put(key44, listOfDoctorShareDTOList4);
                            }
                        }
                    } else {
                        objDoctorShareRefdrwiseListDTO4.setMrNo("" + obj[0]);
                        objDoctorShareRefdrwiseListDTO4.setPatientName("" + obj[1]);
                        objDoctorShareRefdrwiseListDTO4.setDrAmount(Double.parseDouble("" + obj[2]));
                        if (drvisitMap.containsKey(key44)) {
                            objDoctorShareRefdrwiseListDTO4.setDrName("" + obj[6]);
                            listOfDoctorShareDTOList4 = drvisitMap.get(key44);
                            listOfDoctorShareDTOList4.add(objDoctorShareRefdrwiseListDTO4);
                            drvisitMap.put(key44, listOfDoctorShareDTOList4);
                        } else {
                            objDoctorShareRefdrwiseListDTO4.setDrName("" + obj[6]);
                            listOfDoctorShareDTOList4.add(objDoctorShareRefdrwiseListDTO4);
                            drvisitMap.put(key44, listOfDoctorShareDTOList4);
                        }

                    }
                }
                if (("" + obj[5] != null) && !("".equalsIgnoreCase(("" + obj[5])))) {
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO5 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList5 = new ArrayList<DoctorShareReportListDTO>();
                    String key55 = "" + obj[5];
                    if (doctorShareReportSearchDTO.getRefDrId() != null && !doctorShareReportSearchDTO.getRefDrId().equals("")) {
                        if (key55.equalsIgnoreCase(doctorShareReportSearchDTO.getRefDrId())) {
                            objDoctorSharevistwiseListDTO5.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO5.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO5.setDrAmount(Double.parseDouble("" + obj[3]));
                            if (drvisitMap.containsKey(key55)) {
                                objDoctorSharevistwiseListDTO5.setDrName("" + obj[7]);
                                listOfDoctorShareDTOList5 = drvisitMap.get(key55);
                                listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
                                drvisitMap.put(key55, listOfDoctorShareDTOList5);
                            } else {
                                objDoctorSharevistwiseListDTO5.setDrName("" + obj[7]);
                                listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
                                drvisitMap.put(key55, listOfDoctorShareDTOList5);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO5.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO5.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO5.setDrAmount(Double.parseDouble("" + obj[3]));
                        if (drvisitMap.containsKey(key55)) {
                            objDoctorSharevistwiseListDTO5.setDrName("" + obj[7]);
                            listOfDoctorShareDTOList5 = drvisitMap.get(key55);
                            listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
                            drvisitMap.put(key55, listOfDoctorShareDTOList5);
                        } else {
                            objDoctorSharevistwiseListDTO5.setDrName("" + obj[7]);
                            listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
                            drvisitMap.put(key55, listOfDoctorShareDTOList5);
                        }

                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        for (Map.Entry<String, List<DoctorShareReportListDTO>> entry : drvisitMap.entrySet()) {
            count = drvisitMap.entrySet().size();
            List<DoctorShareReportListDTO> value = new ArrayList<DoctorShareReportListDTO>();
            value = entry.getValue();
            double totaldrAmount = 0.0;
            for (DoctorShareReportListDTO obj3 : value) {
                totaldrAmount = totaldrAmount + obj3.getDrAmount();
                grandTotal = grandTotal + obj3.getDrAmount();
                obj3.setCount(count);
            }
            for (DoctorShareReportListDTO obj31 : value) {
                obj31.setGrandTotal(grandTotal);
                obj31.setTotaldrAmount(totaldrAmount);
            }
            resultList.add(value);
        }
        int limit = (int) count;
        int diff = ((int) count - (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())));
        if (diff >= 0) {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()));
        } else {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())) + diff;
        }
        // List<DoctorShareReportListDTO> list = new ArrayList<DoctorShareReportListDTO>(drvisitMap.values());
        return resultList.subList((Integer.valueOf(doctorShareReportSearchDTO.getOffset()) - 1) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()), limit);
    }

    @RequestMapping("refereentitywisereport/{unitList}/{fromdate}/{todate}")
    public List searchListofgetDoctorShareReferEntityWiseForHospital(@RequestHeader("X-tenantId") String tenantName, @RequestBody DoctorShareReportSearchDTO doctorShareReportSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        double grandTotal = 0.0;
        // JSONArray resultArrayResp = new JSONArray();
        List resultList = new ArrayList<>();
        HashMap<String, List<DoctorShareReportListDTO>> drvisitMap = new HashMap<String, List<DoctorShareReportListDTO>>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = "";
        String Query1 = " select ifnull(mp.patient_mr_no, '') as mrNo,  ifnull(concat(mu.user_firstname,' ', mu.user_lastname), '') as PatientName, ifnull(tdsi.dsi_doc_share_amt4, '') as doctor1, ifnull(tdsi.dsi_doc_share_amt5, '') as doctor2, " +
                " ifnull(tdsi.dsi_re_id1, '') as refdr1, ifnull(tdsi.dsi_re_id2,'') as refdr2,  ifnull(mre1.re_name, '') as refDoctor1,  ifnull(mre2.re_name, '') as refDoctor2, tb.gross_amount " +
                " from trn_doctor_share_ipd tdsi " +
                " inner join tbill_bill tb on tb.bill_id = tdsi.dsi_bill_id " +
                " left join trn_admission ta on ta.admission_id = tb.bill_admission_id " +
                " left join  mst_patient mp ON mp.patient_id = ta.admission_patient_id " +
                " left join  mst_user mu ON mu.user_id = mp.patient_user_id " +
                " left join mst_staff refms1 on refms1.staff_id = tdsi.dsi_re_id1 " +
                " left join mst_staff refms2 on refms2.staff_id = tdsi.dsi_re_id2 " +
                " left join mst_referring_entity mre1 on mre1.re_id = tdsi.dsi_re_id1 " +
                " left join mst_referring_entity mre2 on mre2.re_id = tdsi.dsi_re_id2 " +
                " where tdsi.is_active = 1 and tdsi.is_deleted = 0  and tb.is_cancelled = 0 and tb.is_active =1 and tb.is_deleted = 0 and tb.final_bill = 1 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (doctorShareReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(tdsi.created_date)=curdate()) ";

        } else {
            Query1 += " and (date(tdsi.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (doctorShareReportSearchDTO.getPatientName() != null && !doctorShareReportSearchDTO.getPatientName().equals("")) {
            Query1 += " and CONCAT(mu.user_firstname,' ',mu.user_lastname) LIKE '%" + doctorShareReportSearchDTO.getPatientName() + "%'";

        }
        if (doctorShareReportSearchDTO.getMrNo() != null && !doctorShareReportSearchDTO.getMrNo().equals("")) {
            Query1 += " and mp.patient_mr_no LIKE '%" + doctorShareReportSearchDTO.getMrNo() + "%'";

        }
        String CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        try {
            System.out.println(MainQuery);
            List<Object[]> bdoctorShareReport = entityManager.createNativeQuery(Query1).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            for (Object[] obj : bdoctorShareReport) {
                if (("" + obj[4] != null) && !("".equalsIgnoreCase(("" + obj[4])))) {
                    DoctorShareReportListDTO objDoctorShareRefdrwiseListDTO4 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList4 = new ArrayList<DoctorShareReportListDTO>();
                    String key44 = "" + obj[4];
                    if (doctorShareReportSearchDTO.getRefDrId() != null && !doctorShareReportSearchDTO.getRefDrId().equals("")) {
                        if (key44.equalsIgnoreCase(doctorShareReportSearchDTO.getRefDrId())) {
                            objDoctorShareRefdrwiseListDTO4.setMrNo("" + obj[0]);
                            objDoctorShareRefdrwiseListDTO4.setPatientName("" + obj[1]);
                            objDoctorShareRefdrwiseListDTO4.setDrAmount(Double.parseDouble("" + obj[2]));
                            objDoctorShareRefdrwiseListDTO4.setBillAmount("" + obj[8]);
                            if (drvisitMap.containsKey(key44)) {
                                objDoctorShareRefdrwiseListDTO4.setDrName("" + obj[6]);
                                listOfDoctorShareDTOList4 = drvisitMap.get(key44);
                                listOfDoctorShareDTOList4.add(objDoctorShareRefdrwiseListDTO4);
                                drvisitMap.put(key44, listOfDoctorShareDTOList4);
                            } else {
                                objDoctorShareRefdrwiseListDTO4.setDrName("" + obj[6]);
                                listOfDoctorShareDTOList4.add(objDoctorShareRefdrwiseListDTO4);
                                drvisitMap.put(key44, listOfDoctorShareDTOList4);
                            }
                        }
                    } else {
                        objDoctorShareRefdrwiseListDTO4.setMrNo("" + obj[0]);
                        objDoctorShareRefdrwiseListDTO4.setPatientName("" + obj[1]);
                        objDoctorShareRefdrwiseListDTO4.setDrAmount(Double.parseDouble("" + obj[2]));
                        objDoctorShareRefdrwiseListDTO4.setBillAmount("" + obj[8]);
                        if (drvisitMap.containsKey(key44)) {
                            objDoctorShareRefdrwiseListDTO4.setDrName("" + obj[6]);
                            listOfDoctorShareDTOList4 = drvisitMap.get(key44);
                            listOfDoctorShareDTOList4.add(objDoctorShareRefdrwiseListDTO4);
                            drvisitMap.put(key44, listOfDoctorShareDTOList4);
                        } else {
                            objDoctorShareRefdrwiseListDTO4.setDrName("" + obj[6]);
                            listOfDoctorShareDTOList4.add(objDoctorShareRefdrwiseListDTO4);
                            drvisitMap.put(key44, listOfDoctorShareDTOList4);
                        }

                    }
                }
                if (("" + obj[5] != null) && !("".equalsIgnoreCase(("" + obj[5])))) {
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO5 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList5 = new ArrayList<DoctorShareReportListDTO>();
                    String key55 = "" + obj[5];
                    if (doctorShareReportSearchDTO.getRefDrId() != null && !doctorShareReportSearchDTO.getRefDrId().equals("")) {
                        if (key55.equalsIgnoreCase(doctorShareReportSearchDTO.getRefDrId())) {
                            objDoctorSharevistwiseListDTO5.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO5.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO5.setBillAmount("" + obj[8]);
                            objDoctorSharevistwiseListDTO5.setDrAmount(Double.parseDouble("" + obj[3]));
                            if (drvisitMap.containsKey(key55)) {
                                objDoctorSharevistwiseListDTO5.setDrName("" + obj[7]);
                                listOfDoctorShareDTOList5 = drvisitMap.get(key55);
                                listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
//
                                drvisitMap.put(key55, listOfDoctorShareDTOList5);
                            } else {
                                objDoctorSharevistwiseListDTO5.setDrName("" + obj[7]);
                                listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
                                drvisitMap.put(key55, listOfDoctorShareDTOList5);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO5.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO5.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO5.setDrAmount(Double.parseDouble("" + obj[3]));
                        objDoctorSharevistwiseListDTO5.setBillAmount("" + obj[8]);
                        if (drvisitMap.containsKey(key55)) {
                            objDoctorSharevistwiseListDTO5.setDrName("" + obj[7]);
                            listOfDoctorShareDTOList5 = drvisitMap.get(key55);
                            listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
                            drvisitMap.put(key55, listOfDoctorShareDTOList5);
                        } else {
                            objDoctorSharevistwiseListDTO5.setDrName("" + obj[7]);
                            listOfDoctorShareDTOList5.add(objDoctorSharevistwiseListDTO5);
                            drvisitMap.put(key55, listOfDoctorShareDTOList5);
                        }

                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        for (Map.Entry<String, List<DoctorShareReportListDTO>> entry : drvisitMap.entrySet()) {
            count = drvisitMap.entrySet().size();
            List<DoctorShareReportListDTO> value = new ArrayList<DoctorShareReportListDTO>();
            value = entry.getValue();
            double totaldrAmount = 0.0;
            double totalbillAmount = 0.0;
            for (DoctorShareReportListDTO obj3 : value) {
                totaldrAmount = totaldrAmount + obj3.getDrAmount();
                totalbillAmount = totalbillAmount + Double.parseDouble(obj3.getBillAmount());
                grandTotal = grandTotal + obj3.getDrAmount();
                obj3.setCount(count);
            }
            for (DoctorShareReportListDTO obj31 : value) {
                obj31.setGrandTotal(grandTotal);
                obj31.setTotaldrAmount(totaldrAmount);
                obj31.setTotalbillAmount(totalbillAmount);
            }
            resultList.add(value);
        }
        int limit = (int) count;
        int diff = ((int) count - (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())));
        if (diff >= 0) {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()));
        } else {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())) + diff;
        }
        // List<DoctorShareReportListDTO> list = new ArrayList<DoctorShareReportListDTO>(drvisitMap.values());
        return resultList.subList((Integer.valueOf(doctorShareReportSearchDTO.getOffset()) - 1) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()), limit);
    }

    @RequestMapping("indivitualreport/{unitList}/{fromdate}/{todate}")
    public List searchListofgetDoctorShareIndivitualReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody DoctorShareReportSearchDTO doctorShareReportSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        double grandTotal = 0.0;
        //JSONArray resultArrayResp = new JSONArray();
        List resultList = new ArrayList<>();
        HashMap<String, List<DoctorShareReportListDTO>> drvisitMap = new HashMap<String, List<DoctorShareReportListDTO>>();
        //  Map<String,Map<String,DoctorShareReportListDTO>> drvisitMap=new HashMap<String,Map<String,DoctorShareReportListDTO>>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = "";
        String Query1 = "select ifnull(mp.patient_mr_no, '') as mrNo,   ifnull(concat(mu.user_firstname,' ', mu.user_lastname), '') as PatientName,  ifnull(tdsi.dsi_cons_doc_id1,'') as consDoc1,ifnull(tdsi.dsi_cons_doc_id2,'') as consDoc2,ifnull(tdsi.dsi_cons_doc_id3,'') as consDoc3, " +
                " tdsi.dsi_doc_share_amt1, tdsi.dsi_doc_share_amt2, tdsi.dsi_doc_share_amt3,  ifnull(concat(mu1.user_firstname,' ', mu1.user_lastname), '') as Doctor1, mp.patient_id,ifnull(concat(mu2.user_firstname,' ', mu2.user_lastname), '') as Doctor2," +
                " ifnull(concat(mu3.user_firstname,' ', mu1.user_lastname), '') as Doctor3, tb.gross_amount " +
                " from trn_doctor_share_ipd tdsi " +
                " inner join tbill_bill tb on tb.bill_id = tdsi.dsi_bill_id " +
                " left join trn_admission ta on ta.admission_id = tb.bill_admission_id " +
                " left join  mst_patient mp ON mp.patient_id = ta.admission_patient_id " +
                " left join  mst_user mu ON mu.user_id = mp.patient_user_id " +
                " LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id  " +
                " Left join mst_staff ms1 on ms1.staff_id = tdsi.dsi_cons_doc_id1 " +
                " Left join mst_staff ms2 on ms2.staff_id = tdsi.dsi_cons_doc_id2 " +
                " Left join mst_staff ms3 on ms3.staff_id = tdsi.dsi_cons_doc_id3 " +
                " left join  mst_user mu1 ON mu1.user_id = ms1.staff_user_id " +
                " left join  mst_user mu2 ON mu2.user_id = ms2.staff_user_id " +
                " left join  mst_user mu3 ON mu3.user_id = ms3.staff_user_id " +
                " where tdsi.is_active = 1 and tdsi.is_deleted = 0  and tb.is_cancelled = 0 and tb.is_active =1 and tb.is_deleted = 0 and tb.final_bill = 1 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (doctorShareReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(tdsi.created_date)=curdate()) ";

        } else {
            Query1 += " and (date(tdsi.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (doctorShareReportSearchDTO.getPatientName() != null && !doctorShareReportSearchDTO.getPatientName().equals("")) {
            Query1 += " and CONCAT(mu.user_firstname,' ',mu.user_lastname) LIKE '%" + doctorShareReportSearchDTO.getPatientName() + "%'";

        }
        if (doctorShareReportSearchDTO.getMrNo() != null && !doctorShareReportSearchDTO.getMrNo().equals("")) {
            Query1 += " and mp.patient_mr_no LIKE '%" + doctorShareReportSearchDTO.getMrNo() + "%'";

        }
        try {
            // Query1 += " limit " + ((doctorShareReportSearchDTO.getOffset() - 1) * doctorShareReportSearchDTO.getLimit()) + "," + doctorShareReportSearchDTO.getLimit();
            System.out.println(MainQuery);
            List<Object[]> bdoctorShareReport = entityManager.createNativeQuery(Query1).getResultList();
            // BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            // count = cc.longValue()
            for (Object[] obj : bdoctorShareReport) {
                if (("" + obj[2] != null) && !("".equalsIgnoreCase(("" + obj[2])))) {
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO1 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList1 = new ArrayList<DoctorShareReportListDTO>();
                    String key1 = "" + obj[9] + "" + obj[2];
                    String key11 = "" + obj[2];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key11.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO1.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO1.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO1.setDrAmount(Double.parseDouble("" + obj[5]));
                            objDoctorSharevistwiseListDTO1.setBillAmount("" + obj[12]);
                            if (drvisitMap.containsKey(key11)) {
                                objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                                listOfDoctorShareDTOList1 = drvisitMap.get(key11);
                                listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
//
                                drvisitMap.put(key11, listOfDoctorShareDTOList1);
                            } else {
                                // objDoctorSharevistwiseListDTO1.setTotaldrAmount(objDoctorSharevistwiseListDTO1.getDrAmount());
                                objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                                listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
                                drvisitMap.put(key11, listOfDoctorShareDTOList1);
                            }
                        }

                    } else {
                        objDoctorSharevistwiseListDTO1.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO1.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO1.setDrAmount(Double.parseDouble("" + obj[5]));
                        objDoctorSharevistwiseListDTO1.setBillAmount("" + obj[12]);
                        if (drvisitMap.containsKey(key11)) {
                            objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                            listOfDoctorShareDTOList1 = drvisitMap.get(key11);
                            listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
//
                            drvisitMap.put(key11, listOfDoctorShareDTOList1);
                        } else {
                            // objDoctorSharevistwiseListDTO1.setTotaldrAmount(objDoctorSharevistwiseListDTO1.getDrAmount());
                            objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                            listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
                            drvisitMap.put(key11, listOfDoctorShareDTOList1);
                        }
                    }
                }
                if (("" + obj[3] != null) && !("".equalsIgnoreCase(("" + obj[3])))) {
                    // double totaldrAmount2 = 0.0;
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO2 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList2 = new ArrayList<DoctorShareReportListDTO>();
                    String key2 = "" + obj[9] + "" + obj[3];
                    String key22 = "" + obj[3];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key22.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO2.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO2.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO2.setBillAmount("" + obj[12]);
                            objDoctorSharevistwiseListDTO2.setDrAmount(Double.parseDouble("" + obj[6]));
                            if (drvisitMap.containsKey(key22)) {
                                objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                                listOfDoctorShareDTOList2 = drvisitMap.get(key22);
                                listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
//
                                drvisitMap.put(key22, listOfDoctorShareDTOList2);
                            } else {
                                //  objDoctorSharevistwiseListDTO2.setTotaldrAmount(objDoctorSharevistwiseListDTO2.getDrAmount());
                                objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                                listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
                                drvisitMap.put(key22, listOfDoctorShareDTOList2);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO2.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO2.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO2.setBillAmount("" + obj[12]);
                        objDoctorSharevistwiseListDTO2.setDrAmount(Double.parseDouble("" + obj[6]));
                        if (drvisitMap.containsKey(key22)) {
                            objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                            listOfDoctorShareDTOList2 = drvisitMap.get(key22);
                            listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
//
                            drvisitMap.put(key22, listOfDoctorShareDTOList2);
                        } else {
                            //  objDoctorSharevistwiseListDTO2.setTotaldrAmount(objDoctorSharevistwiseListDTO2.getDrAmount());
                            objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                            listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
                            drvisitMap.put(key22, listOfDoctorShareDTOList2);
                        }
//
                    }
                }
                if (("" + obj[4] != null) && !("".equalsIgnoreCase(("" + obj[4])))) {
                    // double totaldrAmount3 = 0.0;
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO3 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList3 = new ArrayList<DoctorShareReportListDTO>();
                    String key3 = "" + obj[9] + "" + obj[4];
                    String key33 = "" + obj[4];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key33.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO3.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO3.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO3.setBillAmount("" + obj[12]);
                            objDoctorSharevistwiseListDTO3.setDrAmount(Double.parseDouble("" + obj[7]));
                            if (drvisitMap.containsKey(key33)) {
                                objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                                listOfDoctorShareDTOList3 = drvisitMap.get(key33);
                                listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
//                            for (DoctorShareReportListDTO obj3 : listOfDoctorShareDTOList3) {
//                            totaldrAmount3 = totaldrAmount3 + obj3.getDrAmount();
//                            obj3.setTotaldrAmount(totaldrAmount3);
//                            }
                                drvisitMap.put(key33, listOfDoctorShareDTOList3);
                            } else {
                                // objDoctorSharevistwiseListDTO3.setTotaldrAmount(objDoctorSharevistwiseListDTO3.getDrAmount());
                                objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                                listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
                                drvisitMap.put(key33, listOfDoctorShareDTOList3);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO3.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO3.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO3.setDrAmount(Double.parseDouble("" + obj[7]));
                        objDoctorSharevistwiseListDTO3.setBillAmount("" + obj[12]);
                        if (drvisitMap.containsKey(key33)) {
                            objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                            listOfDoctorShareDTOList3 = drvisitMap.get(key33);
                            listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
//
                            drvisitMap.put(key33, listOfDoctorShareDTOList3);
                        } else {
                            // objDoctorSharevistwiseListDTO3.setTotaldrAmount(objDoctorSharevistwiseListDTO3.getDrAmount());
                            objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                            listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
                            drvisitMap.put(key33, listOfDoctorShareDTOList3);
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        for (Map.Entry<String, List<DoctorShareReportListDTO>> entry : drvisitMap.entrySet()) {
            count = drvisitMap.entrySet().size();
            List<DoctorShareReportListDTO> value = new ArrayList<DoctorShareReportListDTO>();
            value = entry.getValue();
            double totaldrAmount = 0.0;
            double totalbillAmount = 0.0;
            for (DoctorShareReportListDTO obj3 : value) {
                totaldrAmount = totaldrAmount + obj3.getDrAmount();
                totalbillAmount = totalbillAmount + Double.parseDouble(obj3.getBillAmount());
                grandTotal = grandTotal + obj3.getDrAmount();
                obj3.setCount(count);
                // obj3.setTotaldrAmount(totaldrAmount);
            }
            for (DoctorShareReportListDTO obj31 : value) {
                obj31.setGrandTotal(grandTotal);
                obj31.setTotaldrAmount(totaldrAmount);
                obj31.setTotalbillAmount(totalbillAmount);
            }
            resultList.add(value);
            // continue here
        }
        // List<DoctorShareReportListDTO> list = new ArrayList<DoctorShareReportListDTO>(drvisitMap.values());
        int limit = (int) count;
        int diff = ((int) count - (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())));
        if (diff >= 0) {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()));
        } else {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())) + diff;
        }
        // List<DoctorShareReportListDTO> list = new ArrayList<DoctorShareReportListDTO>(drvisitMap.values());
        return resultList.subList((Integer.valueOf(doctorShareReportSearchDTO.getOffset()) - 1) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()), limit);
    }

    @RequestMapping("consultantwisereport/{unitList}/{fromdate}/{todate}")
    public List searchListofgetDoctorShareConsultWiseReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody DoctorShareReportSearchDTO doctorShareReportSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        double grandTotal = 0.0;
        //JSONArray resultArrayResp = new JSONArray();
        List resultList = new ArrayList<>();
        HashMap<String, List<DoctorShareReportListDTO>> drvisitMap = new HashMap<String, List<DoctorShareReportListDTO>>();
        //  Map<String,Map<String,DoctorShareReportListDTO>> drvisitMap=new HashMap<String,Map<String,DoctorShareReportListDTO>>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = "";
        String Query1 = "select ifnull(mp.patient_mr_no, '') as mrNo,   ifnull(concat(mu.user_firstname,' ', mu.user_lastname), '') as PatientName,  ifnull(tdsi.dsi_cons_doc_id1,'') as consDoc1,ifnull(tdsi.dsi_cons_doc_id2,'') as consDoc2,ifnull(tdsi.dsi_cons_doc_id3,'') as consDoc3, " +
                " tdsi.dsi_doc_share_amt1, tdsi.dsi_doc_share_amt2, tdsi.dsi_doc_share_amt3,  ifnull(concat(mu1.user_firstname,' ', mu1.user_lastname), '') as Doctor1, mp.patient_id,ifnull(concat(mu2.user_firstname,' ', mu2.user_lastname), '') as Doctor2," +
                " ifnull(concat(mu3.user_firstname,' ', mu1.user_lastname), '') as Doctor3, tb.gross_amount " +
                " from trn_doctor_share_ipd tdsi " +
                " inner join tbill_bill tb on tb.bill_id = tdsi.dsi_bill_id " +
                " left join trn_admission ta on ta.admission_id = tb.bill_admission_id " +
                " left join  mst_patient mp ON mp.patient_id = ta.admission_patient_id " +
                " left join  mst_user mu ON mu.user_id = mp.patient_user_id " +
                " LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id  " +
                " Left join mst_staff ms1 on ms1.staff_id = tdsi.dsi_cons_doc_id1 " +
                " Left join mst_staff ms2 on ms2.staff_id = tdsi.dsi_cons_doc_id2 " +
                " Left join mst_staff ms3 on ms3.staff_id = tdsi.dsi_cons_doc_id3 " +
                " left join  mst_user mu1 ON mu1.user_id = ms1.staff_user_id " +
                " left join  mst_user mu2 ON mu2.user_id = ms2.staff_user_id " +
                " left join  mst_user mu3 ON mu3.user_id = ms3.staff_user_id " +
                " where tdsi.is_active = 1 and tdsi.is_deleted = 0  and tb.is_cancelled = 0 and tb.is_active =1 and tb.is_deleted = 0 and tb.final_bill = 1 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (doctorShareReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(tdsi.created_date)=curdate()) ";

        } else {
            Query1 += " and (date(tdsi.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (doctorShareReportSearchDTO.getPatientName() != null && !doctorShareReportSearchDTO.getPatientName().equals("")) {
            Query1 += " and CONCAT(mu.user_firstname,' ',mu.user_lastname) LIKE '%" + doctorShareReportSearchDTO.getPatientName() + "%'";

        }
        if (doctorShareReportSearchDTO.getMrNo() != null && !doctorShareReportSearchDTO.getMrNo().equals("")) {
            Query1 += " and mp.patient_mr_no LIKE '%" + doctorShareReportSearchDTO.getMrNo() + "%'";

        }
        try {
            // Query1 += " limit " + ((doctorShareReportSearchDTO.getOffset() - 1) * doctorShareReportSearchDTO.getLimit()) + "," + doctorShareReportSearchDTO.getLimit();
            System.out.println(MainQuery);
            List<Object[]> bdoctorShareReport = entityManager.createNativeQuery(Query1).getResultList();
            // BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            // count = cc.longValue()
            for (Object[] obj : bdoctorShareReport) {
                if (("" + obj[2] != null) && !("".equalsIgnoreCase(("" + obj[2])))) {
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO1 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList1 = new ArrayList<DoctorShareReportListDTO>();
                    String key1 = "" + obj[9] + "" + obj[2];
                    String key11 = "" + obj[2];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key11.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO1.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO1.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO1.setDrAmount(Double.parseDouble("" + obj[5]));
                            objDoctorSharevistwiseListDTO1.setBillAmount("" + obj[12]);
                            if (drvisitMap.containsKey(key11)) {
                                objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                                listOfDoctorShareDTOList1 = drvisitMap.get(key11);
                                listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
//
                                drvisitMap.put(key11, listOfDoctorShareDTOList1);
                            } else {
                                // objDoctorSharevistwiseListDTO1.setTotaldrAmount(objDoctorSharevistwiseListDTO1.getDrAmount());
                                objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                                listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
                                drvisitMap.put(key11, listOfDoctorShareDTOList1);
                            }
                        }

                    } else {
                        objDoctorSharevistwiseListDTO1.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO1.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO1.setDrAmount(Double.parseDouble("" + obj[5]));
                        objDoctorSharevistwiseListDTO1.setBillAmount("" + obj[12]);
                        if (drvisitMap.containsKey(key11)) {
                            objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                            listOfDoctorShareDTOList1 = drvisitMap.get(key11);
                            listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
//
                            drvisitMap.put(key11, listOfDoctorShareDTOList1);
                        } else {
                            // objDoctorSharevistwiseListDTO1.setTotaldrAmount(objDoctorSharevistwiseListDTO1.getDrAmount());
                            objDoctorSharevistwiseListDTO1.setDrName("" + obj[8]);
                            listOfDoctorShareDTOList1.add(objDoctorSharevistwiseListDTO1);
                            drvisitMap.put(key11, listOfDoctorShareDTOList1);
                        }
                    }
                }
                if (("" + obj[3] != null) && !("".equalsIgnoreCase(("" + obj[3])))) {
                    // double totaldrAmount2 = 0.0;
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO2 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList2 = new ArrayList<DoctorShareReportListDTO>();
                    String key2 = "" + obj[9] + "" + obj[3];
                    String key22 = "" + obj[3];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key22.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO2.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO2.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO2.setBillAmount("" + obj[12]);
                            objDoctorSharevistwiseListDTO2.setDrAmount(Double.parseDouble("" + obj[6]));
                            if (drvisitMap.containsKey(key22)) {
                                objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                                listOfDoctorShareDTOList2 = drvisitMap.get(key22);
                                listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
//
                                drvisitMap.put(key22, listOfDoctorShareDTOList2);
                            } else {
                                //  objDoctorSharevistwiseListDTO2.setTotaldrAmount(objDoctorSharevistwiseListDTO2.getDrAmount());
                                objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                                listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
                                drvisitMap.put(key22, listOfDoctorShareDTOList2);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO2.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO2.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO2.setBillAmount("" + obj[12]);
                        objDoctorSharevistwiseListDTO2.setDrAmount(Double.parseDouble("" + obj[6]));
                        if (drvisitMap.containsKey(key22)) {
                            objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                            listOfDoctorShareDTOList2 = drvisitMap.get(key22);
                            listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
//
                            drvisitMap.put(key22, listOfDoctorShareDTOList2);
                        } else {
                            //  objDoctorSharevistwiseListDTO2.setTotaldrAmount(objDoctorSharevistwiseListDTO2.getDrAmount());
                            objDoctorSharevistwiseListDTO2.setDrName("" + obj[10]);
                            listOfDoctorShareDTOList2.add(objDoctorSharevistwiseListDTO2);
                            drvisitMap.put(key22, listOfDoctorShareDTOList2);
                        }
//
                    }
                }
                if (("" + obj[4] != null) && !("".equalsIgnoreCase(("" + obj[4])))) {
                    // double totaldrAmount3 = 0.0;
                    DoctorShareReportListDTO objDoctorSharevistwiseListDTO3 = new DoctorShareReportListDTO();
                    List<DoctorShareReportListDTO> listOfDoctorShareDTOList3 = new ArrayList<DoctorShareReportListDTO>();
                    String key3 = "" + obj[9] + "" + obj[4];
                    String key33 = "" + obj[4];
                    if (doctorShareReportSearchDTO.getDrId() != null && !doctorShareReportSearchDTO.getDrId().equals("")) {
                        if (key33.equalsIgnoreCase(doctorShareReportSearchDTO.getDrId())) {
                            objDoctorSharevistwiseListDTO3.setMrNo("" + obj[0]);
                            objDoctorSharevistwiseListDTO3.setPatientName("" + obj[1]);
                            objDoctorSharevistwiseListDTO3.setBillAmount("" + obj[12]);
                            objDoctorSharevistwiseListDTO3.setDrAmount(Double.parseDouble("" + obj[7]));
                            if (drvisitMap.containsKey(key33)) {
                                objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                                listOfDoctorShareDTOList3 = drvisitMap.get(key33);
                                listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
//                            for (DoctorShareReportListDTO obj3 : listOfDoctorShareDTOList3) {
//                            totaldrAmount3 = totaldrAmount3 + obj3.getDrAmount();
//                            obj3.setTotaldrAmount(totaldrAmount3);
//                            }
                                drvisitMap.put(key33, listOfDoctorShareDTOList3);
                            } else {
                                // objDoctorSharevistwiseListDTO3.setTotaldrAmount(objDoctorSharevistwiseListDTO3.getDrAmount());
                                objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                                listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
                                drvisitMap.put(key33, listOfDoctorShareDTOList3);
                            }
                        }
                    } else {
                        objDoctorSharevistwiseListDTO3.setMrNo("" + obj[0]);
                        objDoctorSharevistwiseListDTO3.setPatientName("" + obj[1]);
                        objDoctorSharevistwiseListDTO3.setDrAmount(Double.parseDouble("" + obj[7]));
                        objDoctorSharevistwiseListDTO3.setBillAmount("" + obj[12]);
                        if (drvisitMap.containsKey(key33)) {
                            objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                            listOfDoctorShareDTOList3 = drvisitMap.get(key33);
                            listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
//
                            drvisitMap.put(key33, listOfDoctorShareDTOList3);
                        } else {
                            // objDoctorSharevistwiseListDTO3.setTotaldrAmount(objDoctorSharevistwiseListDTO3.getDrAmount());
                            objDoctorSharevistwiseListDTO3.setDrName("" + obj[11]);
                            listOfDoctorShareDTOList3.add(objDoctorSharevistwiseListDTO3);
                            drvisitMap.put(key33, listOfDoctorShareDTOList3);
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        for (Map.Entry<String, List<DoctorShareReportListDTO>> entry : drvisitMap.entrySet()) {
            count = drvisitMap.entrySet().size();
            List<DoctorShareReportListDTO> value = new ArrayList<DoctorShareReportListDTO>();
            value = entry.getValue();
            double totaldrAmount = 0.0;
            double totalbillAmount = 0.0;
            for (DoctorShareReportListDTO obj3 : value) {
                totaldrAmount = totaldrAmount + obj3.getDrAmount();
                totalbillAmount = totalbillAmount + Double.parseDouble(obj3.getBillAmount());
                grandTotal = grandTotal + obj3.getDrAmount();
                obj3.setCount(count);
                // obj3.setTotaldrAmount(totaldrAmount);
            }
            for (DoctorShareReportListDTO obj31 : value) {
                obj31.setGrandTotal(grandTotal);
                obj31.setTotaldrAmount(totaldrAmount);
                obj31.setTotalbillAmount(totalbillAmount);
            }
            resultList.add(value);
            // continue here
        }
        int limit = (int) count;
        int diff = ((int) count - (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())));
        if (diff >= 0) {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()));
        } else {
            limit = (Integer.valueOf(doctorShareReportSearchDTO.getOffset()) * Integer.valueOf(doctorShareReportSearchDTO.getLimit())) + diff;
        }
        // List<DoctorShareReportListDTO> list = new ArrayList<DoctorShareReportListDTO>(drvisitMap.values());
        return resultList.subList((Integer.valueOf(doctorShareReportSearchDTO.getOffset()) - 1) * Integer.valueOf(doctorShareReportSearchDTO.getLimit()), limit);
    }

    @RequestMapping("doctorsharereportPrint/{unitList}/{fromdate}/{todate}")
    public String searchListofgetDoctorShareReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody DoctorShareReportSearchDTO doctorShareReportSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        double grandTotal = 0.0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = "";
        String Query1 = "select ifnull(mp.patient_mr_no, '') as patient_mr_no,  ifnull(concat(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') as Patient_Name, tdsi.dsi_doc_share_amt1, tdsi.dsi_doc_share_amt2, tdsi.dsi_doc_share_amt3 " +
                " from trn_doctor_share_ipd tdsi " +
                " inner join tbill_bill tb on tb.bill_id = tdsi.dsi_bill_id " +
                " left join trn_admission ta on ta.admission_id = tb.bill_admission_id " +
                " left join  mst_patient mp ON mp.patient_id = ta.admission_patient_id " +
                " left join  mst_user mu ON mu.user_id = mp.patient_user_id " +
                " LEFT JOIN mst_title mt on mu.user_title_id = mt.title_id " +
                " where tdsi.is_active = 1 and tdsi.is_deleted = 0 " +
                " group by tdsi.dsi_cons_doc_id1, tdsi.dsi_cons_doc_id2, tdsi.dsi_cons_doc_id3 ";
        List<DoctorShareReportListDTO> listOfDoctorShareDTOList = new ArrayList<DoctorShareReportListDTO>();
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (doctorShareReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(tdsi.created_date)=curdate()) ";

        } else {
            Query1 += " and (date(tdsi.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            // Query2 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
            // Query3 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
//            if (!unitList[0].equals("null")) {
//                String values = String.valueOf(unitList[0]);
//                for (int i = 1; i < unitList.length; i++) {
//                    values += "," + unitList[i];
//                }
//                Query1 += " and t.is_performed_by_unit_id in (" + values + ") ";
//               // Query2 += " and t.is_performed_by_unit_id in (" + values + ") ";
//               // Query3 += " and t.is_performed_by_unit_id in (" + values + ") ";
//            }
//            if (doctorShareReportSearchDTO.getPatientId() != null && !doctorShareReportSearchDTO.getPatientId().equals("0")) {
//                Query1 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
//                Query2 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
//                Query3 += " and mp.patient_mr_no LIKE  '%" + billCumTestReportSearchDTO.getPatientId() + "%' ";
//            }
//
        if (doctorShareReportSearchDTO.getPatientName() != null && !doctorShareReportSearchDTO.getPatientName().equals("")) {
            Query1 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + doctorShareReportSearchDTO.getPatientName() + "%'";

        }
        //  System.out.println("MainQuery:" + MainQuery);
        String CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        String SummaryQuery = " select IFNULL(sum(Gross),0.0) AS totalgross, IFNULL(sum(Discount),0.0) AS totalDiscount, IFNULL(sum(Net),0.0) AS totalNet, IFNULL(sum(Paid),0.0) AS totalPaid,  IFNULL(sum(Outstanding),0.0) AS totalOutstanding from (" + MainQuery + " ) AS x";
        //  System.out.println("MainQuery:" + MainQuery);
        // System.out.println("CountQuery:" + CountQuery);
        //   System.out.println("SummaryQuery:" + SummaryQuery);
        try {
            MainQuery += " limit " + ((doctorShareReportSearchDTO.getOffset() - 1) * doctorShareReportSearchDTO.getLimit()) + "," + doctorShareReportSearchDTO.getLimit();
            List<Object[]> bdoctorShareReport = entityManager.createNativeQuery(MainQuery).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            // List<Object[]>  billCumSummaryReport = entityManager.createNativeQuery(SummaryQuery).getResultList();
//                for (Object[] tempObj:billCumSummaryReport) {
//                    totalGross = Double.parseDouble("" + tempObj[0]);
//                    totalDiscount = Double.parseDouble("" + tempObj[1]);
//                    totalNet = Double.parseDouble("" + tempObj[2]);
//                    totalPaid =  Double.parseDouble("" + tempObj[3]);
//                    totalOutstanding = Double.parseDouble("" + tempObj[4]);
//                }
            for (Object[] obj : bdoctorShareReport) {
                DoctorShareReportListDTO objDoctorSharevistwiseListDTO = new DoctorShareReportListDTO();
                objDoctorSharevistwiseListDTO.setMrNo("" + obj[0]);
                objDoctorSharevistwiseListDTO.setPatientName("" + obj[1]);
                objDoctorSharevistwiseListDTO.setDrName("" + obj[2]);
                //objDoctorSharevistwiseListDTO.setDrAmount1(Double.parseDouble("" + obj[3]));
                objDoctorSharevistwiseListDTO.setGrandTotal(Double.parseDouble("" + obj[28]));
                objDoctorSharevistwiseListDTO.setCount(count);
                listOfDoctorShareDTOList.add(objDoctorSharevistwiseListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        // return listOfBillCumTestDTOList;
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(doctorShareReportSearchDTO.getUnitId());
        listOfDoctorShareDTOList.get(0).setObjHeaderData(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listOfDoctorShareDTOList);
        if (doctorShareReportSearchDTO.getPrint()) {
            return createReport.createJasperReportXLS("LabBillTestReport", ds);
        } else {
            return createReport.createJasperReportPDF("LabBillTestReport", ds);
        }
    } // END Service

}