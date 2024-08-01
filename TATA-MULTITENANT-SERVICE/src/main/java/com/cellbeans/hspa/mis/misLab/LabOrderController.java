package com.cellbeans.hspa.mis.misLab;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mis.misbillingreport.KeyValueDto;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/labOrderReport")
public class LabOrderController {

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

    @RequestMapping("getLabOrderReport/{unitList}/{fromdate}/{todate}")
    public List<LabOrderListDTO> searchListofLaborderReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody LabOrderSearchDTO labOrderSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        // String fromdate = labOrderSearchDTO.getFromdate(), todate = labOrderSearchDTO.getTodate();
        List<LabOrderListDTO> listOfLabOrderListDTOList = new ArrayList<LabOrderListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String columnName = "";
        String MainQuery = " ";
        String MainCountQuery = " ";
        String Query1 = "  SELECT IFNULL(t.is_performed_by_unit_name,'') AS unit_name,   IFNULL(t.ps_performed_by_date,'') AS performed_date,   IFNULL(mp.patient_mr_no,'') AS mr_no2,   IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name2,   IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name,   IFNULL(mtest.test_name,'') AS test_name, IFNULL(t.is_performed_by_name,'') AS UserName,   " +
                " case " +
                " when t.is_report_delivered = 1 then 'Submitted' " +
                " when t.is_finalized = 1 then 'Finalized' " +
                " when t.is_result_entry = 1 then 'Saved' " +
                " when t.is_sample_accepted = 1 then 'Sample Accepted' " +
                " when t.is_sample_rejected = 1 then 'Sample Rejected' " +
                " when t.is_sample_collected = 1 then 'Sample Collected' " +
                " when t.is_lab_order_acceptance = 1 then 'LO Accepted' " +
                " ELSE 'LO Not Accepted' " +
                " end  " +
                " FROM tpath_bs t   " +
                " left JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id   " +
                " LEFT JOIN mst_visit v ON tb.bill_visit_id = v.visit_id   " +
                " INNER JOIN mst_patient mp ON v.visit_patient_id = mp.patient_id   " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id  " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id   " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id   " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id   " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id   " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id  " +
                " WHERE t.isipd = 0  ";
        String Query2 = "  UNION ALL SELECT  " +
                "  IFNULL(t.is_performed_by_unit_name,'') AS unit_name,   IFNULL(t.ps_performed_by_date,'') AS performed_date,   IFNULL(mp.patient_mr_no,'') AS mr_no2,   IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name2,   IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name,   IFNULL(mtest.test_name,'') AS test_name, IFNULL(t.is_performed_by_name,'') AS UserName,   " +
                " case " +
                " when t.is_report_delivered = 1 then 'Submitted' " +
                " when t.is_finalized = 1 then 'Finalized' " +
                " when t.is_result_entry = 1 then 'Saved' " +
                " when t.is_sample_accepted = 1 then 'Sample Accepted' " +
                " when t.is_sample_rejected = 1 then 'Sample Rejected' " +
                " when t.is_sample_collected = 1 then 'Sample Collected' " +
                " when t.is_lab_order_acceptance = 1 then 'LO Accepted' " +
                " ELSE 'LO Not Accepted' " +
                " end  " +
                " FROM tpath_bs t " +
                " left JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id   " +
                " LEFT JOIN mbill_ipd_charge ipdc ON t.mbillipdcharge = ipdc.ipdcharge_id   " +
                " LEFT JOIN trn_admission ta ON ipdc.charge_admission_id = ta.admission_id  " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id   " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id  " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id   " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id   " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id   " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id   " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id   " +
                " WHERE t.isipd = 1 AND t.ps_bill_id is NULL ";
        String Query3 = "  UNION ALL SELECT " +
                " IFNULL(t.is_performed_by_unit_name,'') AS unit_name,   IFNULL(t.ps_performed_by_date,'') AS performed_date,   IFNULL(mp.patient_mr_no,'') AS mr_no2,   IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name2,   IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name,   IFNULL(mtest.test_name,'') AS test_name, IFNULL(t.is_performed_by_name,'') AS UserName,   " +
                " case " +
                " when t.is_report_delivered = 1 then 'Submitted' " +
                " when t.is_finalized = 1 then 'Finalized' " +
                " when t.is_result_entry = 1 then 'Saved' " +
                " when t.is_sample_accepted = 1 then 'Sample Accepted' " +
                " when t.is_sample_rejected = 1 then 'Sample Rejected' " +
                " when t.is_sample_collected = 1 then 'Sample Collected' " +
                " when t.is_lab_order_acceptance = 1 then 'LO Accepted' " +
                " ELSE 'LO Not Accepted' " +
                " end  " +
                " FROM tpath_bs t   " +
                " LEFT JOIN mst_unit mun ON t.is_performed_by_unit_id = mun.unit_id   " +
                " left JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id   " +
                " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id   " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id   " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id   " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id   " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id   " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id   " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id   " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id  " +
                " WHERE t.isipd = 1 AND ps_bill_id IS NOT NULL and t.is_active = 1 and t.is_deleted = 0    ";
        String CountQuery1 = " SELECT COUNT(*) AS sg_count, IFNULL(msg.sg_name,'') AS subgroup_name " +
                " FROM tpath_bs t " +
                " LEFT JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " LEFT JOIN mst_visit v ON tb.bill_visit_id = v.visit_id " +
                " INNER JOIN mst_patient mp ON v.visit_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id" +
                " WHERE t.isipd = 0 ";
        String CountQuery2 = " UNION ALL " +
                " SELECT COUNT(*) AS sg_count, IFNULL(msg.sg_name,'') AS subgroup_name " +
                " FROM tpath_bs t " +
                " LEFT JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " LEFT JOIN mbill_ipd_charge ipdc ON t.mbillipdcharge = ipdc.ipdcharge_id " +
                " LEFT JOIN trn_admission ta ON ipdc.charge_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 1 AND t.ps_bill_id IS NULL ";
        String CountQuery3 = " UNION ALL " +
                " SELECT COUNT(*) AS sg_count, IFNULL(msg.sg_name,'') AS subgroup_name " +
                " FROM tpath_bs t " +
                " LEFT JOIN mst_unit mun ON t.is_performed_by_unit_id = mun.unit_id " +
                " LEFT JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 1 AND ps_bill_id IS NOT NULL AND t.is_performed_by_unit_id IN (1) AND t.is_active = 1 AND t.is_deleted = 0 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (labOrderSearchDTO.getTodaydate()) {
            Query1 += " and (date(t.ps_performed_by_date)=curdate()) ";
            Query2 += " and (date(t.ps_performed_by_date)=curdate()) ";
            Query3 += " and (date(t.ps_performed_by_date)=curdate()) ";
            CountQuery1 += " and (date(t.ps_performed_by_date)=curdate()) ";
            CountQuery2 += " and (date(t.ps_performed_by_date)=curdate()) ";
            CountQuery3 += " and (date(t.ps_performed_by_date)=curdate()) ";
        } else {
            Query1 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query2 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
            Query3 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery1 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery2 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery3 += " and (date(t.ps_performed_by_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and t.is_performed_by_unit_id in  (" + values + ") ";
            Query2 += " and t.is_performed_by_unit_id in  (" + values + ") ";
            Query3 += " and t.is_performed_by_unit_id in  (" + values + ") ";
            CountQuery1 += " and t.is_performed_by_unit_id in  (" + values + ") ";
            CountQuery2 += " and t.is_performed_by_unit_id in  (" + values + ") ";
            CountQuery3 += " and t.is_performed_by_unit_id in  (" + values + ") ";

        }
        // User Name
        if (labOrderSearchDTO.getUserId() != 0) {
            Query1 += " and t.is_performed_by = " + labOrderSearchDTO.getUserId() + " ";
            Query2 += " and t.is_performed_by = " + labOrderSearchDTO.getUserId() + " ";
            Query3 += " and t.is_performed_by = " + labOrderSearchDTO.getUserId() + " ";
            CountQuery1 += " and t.is_performed_by = " + labOrderSearchDTO.getUserId() + " ";
            CountQuery2 += " and t.is_performed_by = " + labOrderSearchDTO.getUserId() + " ";
            CountQuery3 += " and t.is_performed_by = " + labOrderSearchDTO.getUserId() + " ";
        }
        // Group Name
        if (labOrderSearchDTO.getGroupId() != 0) {
            Query1 += " and mg.group_id = " + labOrderSearchDTO.getGroupId() + " ";
            Query2 += " and mg.group_id = " + labOrderSearchDTO.getGroupId() + " ";
            Query3 += " and mg.group_id = " + labOrderSearchDTO.getGroupId() + " ";
            CountQuery1 += " and mg.group_id = " + labOrderSearchDTO.getGroupId() + " ";
            CountQuery2 += " and mg.group_id = " + labOrderSearchDTO.getGroupId() + " ";
            CountQuery3 += " and mg.group_id = " + labOrderSearchDTO.getGroupId() + " ";
        }
        // Sub Group Name
        if (labOrderSearchDTO.getSubGroupId() != 0) {
            Query1 += " and msg.sg_id = " + labOrderSearchDTO.getSubGroupId() + " ";
            Query2 += " and msg.sg_id = " + labOrderSearchDTO.getSubGroupId() + " ";
            Query3 += " and msg.sg_id = " + labOrderSearchDTO.getSubGroupId() + " ";
            CountQuery1 += " and msg.sg_id = " + labOrderSearchDTO.getSubGroupId() + " ";
            CountQuery2 += " and msg.sg_id = " + labOrderSearchDTO.getSubGroupId() + " ";
            CountQuery3 += " and msg.sg_id = " + labOrderSearchDTO.getSubGroupId() + " ";
        }
        // Status
        if (labOrderSearchDTO.getStatus() != 0) {
            switch (labOrderSearchDTO.getStatus()) {
                case 1:
                    Query1 += " and t.is_report_delivered = 1 ";
                    Query2 += " and t.is_report_delivered = 1 ";
                    Query3 += " and t.is_report_delivered = 1 ";
                    CountQuery1 += " and t.is_report_delivered = 1 ";
                    CountQuery2 += " and t.is_report_delivered = 1 ";
                    CountQuery3 += " and t.is_report_delivered = 1 ";
                    break;
                case 2:
                    Query1 += " and t.is_finalized = 1 AND t.is_report_delivered !=1 ";
                    Query2 += " and t.is_finalized = 1 AND t.is_report_delivered !=1 ";
                    Query3 += " and t.is_finalized = 1 AND t.is_report_delivered !=1 ";
                    CountQuery1 += " and t.is_finalized = 1 AND t.is_report_delivered !=1 ";
                    CountQuery2 += " and t.is_finalized = 1 AND t.is_report_delivered !=1 ";
                    CountQuery3 += " and t.is_finalized = 1 AND t.is_report_delivered !=1 ";
                    break;
                case 3:
                    Query1 += " and t.is_result_entry = 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query2 += " and t.is_result_entry = 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query3 += " and t.is_result_entry = 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery1 += " and t.is_result_entry = 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery2 += " and t.is_result_entry = 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery3 += " and t.is_result_entry = 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    break;
                case 4:
                    Query1 += " and t.is_sample_accepted = 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query2 += " and t.is_sample_accepted = 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query3 += " and t.is_sample_accepted = 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery1 += " and t.is_sample_accepted = 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery2 += " and t.is_sample_accepted = 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery3 += " and t.is_sample_accepted = 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    break;
                case 5:
                    Query1 += " and t.is_sample_rejected = 1 and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query2 += " and t.is_sample_rejected = 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query3 += " and t.is_sample_rejected = 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery1 += " and t.is_sample_rejected = 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery2 += " and t.is_sample_rejected = 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery3 += " and t.is_sample_rejected = 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    break;
                case 6:
                    Query1 += " and t.is_sample_collected = 1 and t.is_sample_rejected != 1 and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query2 += " and t.is_sample_collected = 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query3 += " and t.is_sample_collected = 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery1 += " and t.is_sample_collected = 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery2 += " and t.is_sample_collected = 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery3 += " and t.is_sample_collected = 1  and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    break;
                case 7:
                    Query1 += " and t.is_lab_order_acceptance = 1 and t.is_sample_collected != 1 and t.is_sample_rejected != 1 and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query2 += " and t.is_lab_order_acceptance = 1 and t.is_sample_collected != 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query3 += " and t.is_lab_order_acceptance = 1 and t.is_sample_collected != 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery1 += " and t.is_lab_order_acceptance = 1 and t.is_sample_collected != 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery2 += " and t.is_lab_order_acceptance = 1 and t.is_sample_collected != 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery3 += " and t.is_lab_order_acceptance = 1 and t.is_sample_collected != 1  and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    break;
                case 8:
                    Query1 += " and t.is_lab_order_acceptance != 1 and t.is_sample_collected != 1 and t.is_sample_rejected != 1 and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query2 += " and t.is_lab_order_acceptance != 1 and t.is_sample_collected != 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    Query3 += " and t.is_lab_order_acceptance != 1 and t.is_sample_collected != 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery1 += " and t.is_lab_order_acceptance != 1 and t.is_sample_collected != 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery2 += " and t.is_lab_order_acceptance != 1 and t.is_sample_collected != 1 and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    CountQuery3 += " and t.is_lab_order_acceptance != 1 and t.is_sample_collected != 1  and t.is_sample_rejected != 1  and t.is_sample_accepted != 1 and t.is_result_entry != 1 and t.is_finalized != 1 AND t.is_report_delivered !=1 ";
                    break;
            }
        }
        MainQuery = Query1 + Query2 + Query3;
         System.out.println("Lab Order Detail Report" + MainQuery);
         System.out.println("Lab Order Detail Report" + Query1);
         System.out.println("Lab Order Detail Report" + Query2);
         System.out.println("Lab Order Detail Report" + Query3);
        MainCountQuery = CountQuery1 + " group by msg.sg_name " + CountQuery2 + " group by msg.sg_name " + CountQuery3 + " group by msg.sg_name ";
        // System.out.println("MainCountQuery " + MainCountQuery);
        try {
            List<HashMap<String, Integer>> list1 = new ArrayList<HashMap<String, Integer>>();
            HashMap<String, Integer> subCount = new HashMap<String, Integer>();
            MainQuery += " limit " + ((labOrderSearchDTO.getOffset() - 1) * labOrderSearchDTO.getLimit()) + "," + labOrderSearchDTO.getLimit();
            List<Object[]> labOrderreport = entityManager.createNativeQuery(MainQuery).getResultList();
            List<KeyValueDto> subgroupWise = new ArrayList<>();
            List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(MainCountQuery).getResultList();
            //  System.out.println("MainCountQuery...." + MainCountQuery);
            for (Object[] obj : list) {
                count += Integer.parseInt(obj[0].toString());
                String subgName = obj[1].toString();
                if (subCount.containsKey(subgName)) {
                    //  System.out.println("subgroup name : " + subgName + "   " + Integer.parseInt(obj[0].toString()));
                    subCount.put(subgName, subCount.get(subgName) + Integer.parseInt(obj[0].toString()));
                } else {
                    // System.out.println("subgroup name : " + subgName + "   " + Integer.parseInt(obj[0].toString()));
                    subCount.put(subgName, Integer.parseInt(obj[0].toString()));
                }
            }
            List<KeyValueDto> groupWiseList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : subCount.entrySet()) {
                groupWiseList.add(new KeyValueDto("" + entry.getKey(), "" + entry.getValue()));
            }
            String json = new ObjectMapper().writeValueAsString(subCount);
            //  System.out.println("subCount.." + subCount);
            for (Object[] obj : labOrderreport) {
                LabOrderListDTO objlaborderListDTO = new LabOrderListDTO();
                objlaborderListDTO.setUnitName("" + obj[0]);
                objlaborderListDTO.setDate("" + obj[1]);
                objlaborderListDTO.setMrNo("" + obj[2]);
                objlaborderListDTO.setPatientName("" + obj[3]);
                objlaborderListDTO.setGroupName("" + obj[4]);
                objlaborderListDTO.setSubGroupName("" + obj[5]);
                objlaborderListDTO.setTestName("" + obj[6]);
                objlaborderListDTO.setUserName("" + obj[7]);
                objlaborderListDTO.setStatus("" + obj[8]);
                objlaborderListDTO.setCount(count);        // total count
                objlaborderListDTO.setSubgroupWise(groupWiseList);
                listOfLabOrderListDTOList.add(objlaborderListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfLabOrderListDTOList;
    }

    // Sample Collection Report start 10.11.2019
    @RequestMapping("getSampleCollectionReport/{unitList}/{userList}/{fromdate}/{todate}")
    public List<SampleCollectionListDTO> searchListofSampleCollectionReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody SampleCollectionSearchDTO sampleCollectionSearchDTO, @PathVariable String[] unitList, @PathVariable String[] userList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String MainQuery = "";
        // String fromdate = sampleCollectionSearchDTO.getFromdate(), todate = sampleCollectionSearchDTO.getTodate();
        List<SampleCollectionListDTO> sampleCollectionListDTO = new ArrayList<SampleCollectionListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " SELECT IFNULL(t.is_performed_by_unit_name,'') AS unit_name, IFNULL(t.ps_sample_collection_date,'') AS sampleColl_date, IFNULL(mp.patient_mr_no,'') AS mr_no, " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name, IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name, " +
                " IFNULL(mtest.test_name,'') AS test_name, IFNULL(t.ps_sample_number,'') AS SampleNo, IFNULL(msample.sample_name,'') AS SampleType, IFNULL(mcon.container_name,'') AS ContainerType," +
                " IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectedBy " +
                " FROM tpath_bs t " +
                " left JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id  " +
                " LEFT JOIN mst_visit v ON tb.bill_visit_id = v.visit_id  " +
                " INNER JOIN mst_patient mp ON v.visit_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id  " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mpath_container mcon ON mtest.test_container_id= mcon.container_id " +
                " LEFT JOIN mpath_sample msample ON mtest.test_sample_id = msample.sample_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id  " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 0 and t.is_sample_collected = 1 and t.is_active = 1 and t.is_deleted = 0 ";
        String Query2 = " SELECT IFNULL(t.is_performed_by_unit_name,'') AS unit_name, IFNULL(t.ps_sample_collection_date,'') AS sampleColl_date, IFNULL(mp.patient_mr_no,'') AS mr_no, " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name, IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name, " +
                " IFNULL(mtest.test_name,'') AS test_name, IFNULL(t.ps_sample_number,'') AS SampleNo, IFNULL(msample.sample_name,'') AS SampleType, IFNULL(mcon.container_name,'') AS ContainerType, " +
                " IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectedBy " +
                " FROM tpath_bs t " +
                " LEFT JOIN mbill_ipd_charge ipdc ON t.mbillipdcharge = ipdc.ipdcharge_id " +
                " LEFT JOIN trn_admission ta ON ipdc.charge_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mpath_container mcon ON mtest.test_container_id= mcon.container_id " +
                " LEFT JOIN mpath_sample msample ON mtest.test_sample_id = msample.sample_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 1 AND t.ps_bill_id is NULL  and t.is_sample_collected = 1 and t.is_active = 1 and t.is_deleted = 0  ";
        String Query3 = " SELECT  IFNULL(t.is_performed_by_unit_name,'') AS unit_name,   IFNULL(t.ps_sample_collection_date,'') AS sampleColl_date, IFNULL(mp.patient_mr_no,'') AS mr_no, " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name, IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name, " +
                " IFNULL(mtest.test_name,'') AS test_name, IFNULL(t.ps_sample_number,'') AS SampleNo, IFNULL(msample.sample_name,'') AS SampleType, IFNULL(mcon.container_name,'') AS ContainerType, " +
                " IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectedBy " +
                " FROM tpath_bs t   " +
                " left JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mpath_container mcon ON mtest.test_container_id= mcon.container_id " +
                " LEFT JOIN mpath_sample msample ON mtest.test_sample_id = msample.sample_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 1 AND ps_bill_id IS NOT NULL  and t.is_sample_collected = 1 and t.is_active = 1 and t.is_deleted = 0 ";
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
            Query2 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
            Query3 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
        }
        if (sampleCollectionSearchDTO.getSampleType() != null && !sampleCollectionSearchDTO.getSampleType().equals("0")) {
            Query1 += " and mtest.test_sample_id = " + sampleCollectionSearchDTO.getSampleType() + " ";
            Query2 += " and mtest.test_sample_id = " + sampleCollectionSearchDTO.getSampleType() + " ";
            Query3 += " and mtest.test_sample_id = " + sampleCollectionSearchDTO.getSampleType() + " ";
        }
        if (!userList[0].equals("null")) {
            String values = userList[0];
            for (int i = 1; i < userList.length; i++) {
                values += "," + userList[i];
            }
            Query1 += " and t.is_sample_collected_by in (" + values + ") ";
            Query2 += " and t.is_sample_collected_by in (" + values + ") ";
            Query3 += " and t.is_sample_collected_by in (" + values + ") ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        Query1 += " and (date(t.ps_sample_collection_date) between '" + fromdate + "' and '" + todate + "')  ";
        Query2 += " and (date(t.ps_sample_collection_date) between '" + fromdate + "' and '" + todate + "')  ";
        Query3 += " and (date(t.ps_sample_collection_date) between '" + fromdate + "' and '" + todate + "')  ";
        if (sampleCollectionSearchDTO.getServiceType() == 0) {
            MainQuery = Query1 + " ";
        } else if (sampleCollectionSearchDTO.getServiceType() == 1) {
            MainQuery = Query2 + " UNION ALL " + Query3 + " ";
        } else {
            MainQuery = Query1 + " UNION ALL " + Query2 + " UNION ALL " + Query3 + " ";
        }
        String CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        System.out.println("Sample Collection Report:" + MainQuery);
        try {
            MainQuery += " limit " + ((sampleCollectionSearchDTO.getOffset() - 1) * sampleCollectionSearchDTO.getLimit()) + "," + sampleCollectionSearchDTO.getLimit();
            List<Object[]> sampleCollectionReport = entityManager.createNativeQuery(MainQuery).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : sampleCollectionReport) {
                SampleCollectionListDTO objSampleCollectionListDTO = new SampleCollectionListDTO();
                objSampleCollectionListDTO.setUnitName("" + obj[0]);
                objSampleCollectionListDTO.setSampleCollectedDate("" + obj[1]);
                objSampleCollectionListDTO.setMrNo("" + obj[2]);
                objSampleCollectionListDTO.setPatientName("" + obj[3]);
                objSampleCollectionListDTO.setGroupName("" + obj[4]);
                objSampleCollectionListDTO.setSubGroupName("" + obj[5]);
                objSampleCollectionListDTO.setTestName("" + obj[6]);
                objSampleCollectionListDTO.setSampleNumber("" + obj[7]);
                objSampleCollectionListDTO.setSampleType("" + obj[8]);
                objSampleCollectionListDTO.setContainerType("" + obj[9]);
                objSampleCollectionListDTO.setSampleCollectedBy("" + obj[10]);
                objSampleCollectionListDTO.setCount(count);        // total count
                sampleCollectionListDTO.add(objSampleCollectionListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return sampleCollectionListDTO;
    } // END Service
// Sample Collection Report End 10.11.2019

    // Sample Collection Report Print start 10.11.2019
    @RequestMapping("getSampleCollectionReportPrint/{unitList}/{userList}/{fromdate}/{todate}")
    public String searchListofSampleCollectionReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody SampleCollectionSearchDTO sampleCollectionSearchDTO, @PathVariable String[] unitList, @PathVariable String[] userList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String MainQuery = "";
        //  String fromdate = sampleCollectionSearchDTO.getFromdate(), todate = sampleCollectionSearchDTO.getTodate();
        List<SampleCollectionListDTO> sampleCollectionListDTO = new ArrayList<SampleCollectionListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " SELECT IFNULL(t.is_performed_by_unit_name,'') AS unit_name, IFNULL(t.ps_sample_collection_date,'') AS sampleColl_date, IFNULL(mp.patient_mr_no,'') AS mr_no, " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name, IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name, " +
                " IFNULL(mtest.test_name,'') AS test_name, IFNULL(t.ps_sample_number,'') AS SampleNo, IFNULL(msample.sample_name,'') AS SampleType, IFNULL(mcon.container_name,'') AS ContainerType," +
                " IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectedBy " +
                " FROM tpath_bs t " +
                " left JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id  " +
                " LEFT JOIN mst_visit v ON tb.bill_visit_id = v.visit_id  " +
                " INNER JOIN mst_patient mp ON v.visit_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id  " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mpath_container mcon ON mtest.test_container_id= mcon.container_id " +
                " LEFT JOIN mpath_sample msample ON mtest.test_sample_id = msample.sample_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id  " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 0 and t.is_sample_collected = 1 and t.is_active = 1 and t.is_deleted = 0 ";
        String Query2 = " SELECT IFNULL(t.is_performed_by_unit_name,'') AS unit_name, IFNULL(t.ps_sample_collection_date,'') AS sampleColl_date, IFNULL(mp.patient_mr_no,'') AS mr_no, " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name, IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name, " +
                " IFNULL(mtest.test_name,'') AS test_name, IFNULL(t.ps_sample_number,'') AS SampleNo, IFNULL(msample.sample_name,'') AS SampleType, IFNULL(mcon.container_name,'') AS ContainerType, " +
                " IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectedBy " +
                " FROM tpath_bs t " +
                " LEFT JOIN mbill_ipd_charge ipdc ON t.mbillipdcharge = ipdc.ipdcharge_id " +
                " LEFT JOIN trn_admission ta ON ipdc.charge_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mpath_container mcon ON mtest.test_container_id= mcon.container_id " +
                " LEFT JOIN mpath_sample msample ON mtest.test_sample_id = msample.sample_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 1 AND t.ps_bill_id is NULL  and t.is_sample_collected = 1 and t.is_active = 1 and t.is_deleted = 0  ";
        String Query3 = " SELECT  IFNULL(t.is_performed_by_unit_name,'') AS unit_name,   IFNULL(t.ps_sample_collection_date,'') AS sampleColl_date, IFNULL(mp.patient_mr_no,'') AS mr_no, " +
                " IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name, IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name, " +
                " IFNULL(mtest.test_name,'') AS test_name, IFNULL(t.ps_sample_number,'') AS SampleNo, IFNULL(msample.sample_name,'') AS SampleType, IFNULL(mcon.container_name,'') AS ContainerType, " +
                " IFNULL(t.is_sample_collected_by_name,'') AS SampleCollectedBy " +
                " FROM tpath_bs t   " +
                " left JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mpath_container mcon ON mtest.test_container_id= mcon.container_id " +
                " LEFT JOIN mpath_sample msample ON mtest.test_sample_id = msample.sample_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 1 AND ps_bill_id IS NOT NULL  and t.is_sample_collected = 1 and t.is_active = 1 and t.is_deleted = 0 ";
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
            Query2 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
            Query3 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
        }
        if (sampleCollectionSearchDTO.getSampleType() != null && !sampleCollectionSearchDTO.getSampleType().equals("0")) {
            Query1 += " and mtest.test_sample_id = " + sampleCollectionSearchDTO.getSampleType() + " ";
            Query2 += " and mtest.test_sample_id = " + sampleCollectionSearchDTO.getSampleType() + " ";
            Query3 += " and mtest.test_sample_id = " + sampleCollectionSearchDTO.getSampleType() + " ";
        }
        if (!userList[0].equals("null")) {
            String values = userList[0];
            for (int i = 1; i < userList.length; i++) {
                values += "," + userList[i];
            }
            Query1 += " and t.is_sample_collected_by in (" + values + ") ";
            Query2 += " and t.is_sample_collected_by in (" + values + ") ";
            Query3 += " and t.is_sample_collected_by in (" + values + ") ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        Query1 += " and (date(t.ps_sample_collection_date) between '" + fromdate + "' and '" + todate + "')  ";
        Query2 += " and (date(t.ps_sample_collection_date) between '" + fromdate + "' and '" + todate + "')  ";
        Query3 += " and (date(t.ps_sample_collection_date) between '" + fromdate + "' and '" + todate + "')  ";
        if (sampleCollectionSearchDTO.getServiceType() == 0) {
            MainQuery = Query1 + " ";
        } else if (sampleCollectionSearchDTO.getServiceType() == 1) {
            MainQuery = Query2 + " UNION ALL " + Query3 + " ";
        } else {
            MainQuery = Query1 + " UNION ALL " + Query2 + " UNION ALL " + Query3 + " ";
        }
        String CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        System.out.println("MainQuery:" + MainQuery);
        try {
            // MainQuery += " limit " + ((sampleCollectionSearchDTO.getOffset() - 1) * sampleCollectionSearchDTO.getLimit()) + "," + sampleCollectionSearchDTO.getLimit();
            List<Object[]> sampleCollectionReport = entityManager.createNativeQuery(MainQuery).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : sampleCollectionReport) {
                SampleCollectionListDTO objSampleCollectionListDTO = new SampleCollectionListDTO();
                objSampleCollectionListDTO.setUnitName("" + obj[0]);
                objSampleCollectionListDTO.setSampleCollectedDate("" + obj[1]);
                objSampleCollectionListDTO.setMrNo("" + obj[2]);
                objSampleCollectionListDTO.setPatientName("" + obj[3]);
                objSampleCollectionListDTO.setGroupName("" + obj[4]);
                objSampleCollectionListDTO.setSubGroupName("" + obj[5]);
                objSampleCollectionListDTO.setTestName("" + obj[6]);
                objSampleCollectionListDTO.setSampleNumber("" + obj[7]);
                objSampleCollectionListDTO.setSampleType("" + obj[8]);
                objSampleCollectionListDTO.setContainerType("" + obj[9]);
                objSampleCollectionListDTO.setSampleCollectedBy("" + obj[10]);
                objSampleCollectionListDTO.setCount(count);        // total count
                sampleCollectionListDTO.add(objSampleCollectionListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        // return listOfBillCumTestDTOList;
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(sampleCollectionSearchDTO.getUnitId());
        sampleCollectionListDTO.get(0).setObjHeaderData(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(sampleCollectionListDTO);
        if (sampleCollectionSearchDTO.getPrint()) {
            return createReport.createJasperReportXLS("SampleCollectionReport", ds);
        } else {
            return createReport.createJasperReportPDF("SampleCollectionReport", ds);
        }
    } // END Service
// Sample Collection Report Print End 10.11.2019

    // Sample Acceptance Report start 22.11.2019
    @RequestMapping("getSampleAcceptanceReport/{unitList}/{userList}/{fromdate}/{todate}")
    public List<SampleAcceptanceListDTO> searchListofSampleCollectionReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody SampleAcceptanceSearchDTO sampleAcceptanceSearchDTO, @PathVariable String[] unitList, @PathVariable String[] userList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String MainQuery = "";
        //  String fromdate = sampleAcceptanceSearchDTO.getFromdate(), todate = sampleAcceptanceSearchDTO.getTodate();
        List<SampleAcceptanceListDTO> sampleAcceptanceListDTO = new ArrayList<SampleAcceptanceListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        String strDate = formatter.format(date);
        String Query1 = " SELECT IFNULL(t.is_performed_by_unit_name,'') AS unit_name, IFNULL(t.ps_sample_accepted_date,'') AS sampleAccept_date, " +
                " IFNULL(mp.patient_mr_no,'') AS mr_no, IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name, " +
                " IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name, IFNULL(mtest.test_name,'') AS test_name, " +
                " IFNULL(t.ps_sample_number,'') AS SampleNo, IFNULL(msample.sample_name,'') AS SampleType, IFNULL(mcon.container_name,'') AS ContainerType," +
                " IFNULL(t.is_sample_accepted_by_name,'') AS SampleAcceptedBy " +
                " FROM tpath_bs t " +
                " LEFT JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " LEFT JOIN mst_visit v ON tb.bill_visit_id = v.visit_id " +
                " INNER JOIN mst_patient mp ON v.visit_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mpath_container mcon ON mtest.test_container_id= mcon.container_id " +
                " LEFT JOIN mpath_sample msample ON mtest.test_sample_id = msample.sample_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 0 AND t.is_sample_accepted = 1 AND t.is_sample_rejected !=1 AND t.is_sample_outsource !=1 AND t.is_finalized !=1 " +
                " AND t.is_result_entry !=1 AND t.is_report_delivered !=1 AND t.is_report_uploaded !=1 AND t.is_active = 1 AND t.is_deleted = 0 ";
        String Query2 = " SELECT IFNULL(t.is_performed_by_unit_name,'') AS unit_name, IFNULL(t.ps_sample_accepted_date,'') AS sampleAccept_date, " +
                " IFNULL(mp.patient_mr_no,'') AS mr_no, IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name, " +
                " IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name, IFNULL(mtest.test_name,'') AS test_name, " +
                " IFNULL(t.ps_sample_number,'') AS SampleNo, IFNULL(msample.sample_name,'') AS SampleType, IFNULL(mcon.container_name,'') AS ContainerType, " +
                " IFNULL(t.is_sample_accepted_by_name,'') AS SampleAcceptedBy " +
                " FROM tpath_bs t " +
                " LEFT JOIN mbill_ipd_charge ipdc ON t.mbillipdcharge = ipdc.ipdcharge_id " +
                " LEFT JOIN trn_admission ta ON ipdc.charge_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mpath_container mcon ON mtest.test_container_id= mcon.container_id " +
                " LEFT JOIN mpath_sample msample ON mtest.test_sample_id = msample.sample_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 1 AND t.ps_bill_id IS NULL AND t.is_sample_accepted = 1 AND t.is_sample_rejected !=1 " +
                " AND t.is_sample_outsource !=1 AND t.is_finalized !=1 AND t.is_result_entry !=1 AND t.is_report_delivered !=1 " +
                " AND t.is_report_uploaded !=1 AND t.is_active = 1 AND t.is_deleted = 0 ";
        String Query3 = " SELECT IFNULL(t.is_performed_by_unit_name,'') AS unit_name, IFNULL(t.ps_sample_accepted_date,'') AS sampleAccept_date, " +
                " IFNULL(mp.patient_mr_no,'') AS mr_no, IFNULL(CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname),'') AS patient_name, " +
                " IFNULL(mg.group_name,'') AS group_name, IFNULL(msg.sg_name,'') AS subgroup_name, IFNULL(mtest.test_name,'') AS test_name, " +
                " IFNULL(t.ps_sample_number,'') AS SampleNo, IFNULL(msample.sample_name,'') AS SampleType, IFNULL(mcon.container_name,'') AS ContainerType, " +
                " IFNULL(t.is_sample_accepted_by_name,'') AS SampleAcceptedBy " +
                " FROM tpath_bs t " +
                " LEFT JOIN tbill_bill tb ON tb.bill_id = t.ps_bill_id " +
                " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id= mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id= mt.title_id " +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id= mtest.test_id " +
                " LEFT JOIN mpath_container mcon ON mtest.test_container_id= mcon.container_id " +
                " LEFT JOIN mpath_sample msample ON mtest.test_sample_id = msample.sample_id " +
                " LEFT JOIN mbill_service ms ON mtest.m_bill_service_id= ms.service_id " +
                " LEFT JOIN mbill_group mg ON ms.service_group_id= mg.group_id " +
                " LEFT JOIN mbill_sub_group msg ON ms.service_sg_id= msg.sg_id " +
                " WHERE t.isipd = 1 AND ps_bill_id IS NOT NULL AND t.is_sample_accepted = 1 AND t.is_sample_rejected !=1 AND t.is_sample_outsource !=1 " +
                " AND t.is_finalized !=1 AND t.is_result_entry !=1 AND t.is_report_delivered !=1 AND t.is_report_uploaded !=1 AND t.is_active = 1 " +
                " AND t.is_deleted = 0 ";
        if (!unitList[0].equals("null") || !unitList[0].equals("")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
            Query2 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
            Query3 += " and  t.is_performed_by_unit_id in   (" + values + ") ";
        }
        if (!userList[0].equals("null") && !userList[0].equals("")) {
            String values = userList[0];
            for (int i = 1; i < userList.length; i++) {
                values += "," + userList[i];
            }
            Query1 += " and  t.is_sample_accepted_by in   (" + values + ") ";
            Query2 += " and  t.is_sample_accepted_by in   (" + values + ") ";
            Query3 += " and  t.is_sample_accepted_by in   (" + values + ") ";
        }
        if (sampleAcceptanceSearchDTO.getSampleType() != null && !sampleAcceptanceSearchDTO.getSampleType().equals("0")) {
            Query1 += " and mtest.test_sample_id = " + sampleAcceptanceSearchDTO.getSampleType() + " ";
            Query2 += " and mtest.test_sample_id = " + sampleAcceptanceSearchDTO.getSampleType() + " ";
            Query3 += " and mtest.test_sample_id = " + sampleAcceptanceSearchDTO.getSampleType() + " ";
        }
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (sampleAcceptanceSearchDTO.getFromtime().equals("null") || sampleAcceptanceSearchDTO.getFromtime().equals("")) {
            fromdate = fromdate + " 00:00:00";
        } else {
            fromdate = fromdate + " " + sampleAcceptanceSearchDTO.getFromtime() + ":00";
        }
        if (sampleAcceptanceSearchDTO.getTotime().equals("null") || sampleAcceptanceSearchDTO.getTotime().equals("")) {
            todate = todate + " 23:59:59";
        } else {
            todate = todate + " " + sampleAcceptanceSearchDTO.getTotime() + ":59";
        }
        if (sampleAcceptanceSearchDTO.getTodaydate()) {
            Query1 += " where t.ps_sample_accepted_date =curdate() ";
            Query2 += " where t.ps_sample_accepted_date)=curdate() ";
            Query3 += " where t.ps_sample_accepted_date)=curdate() ";
            // CountQuery += " where (date(v.created_date)=curdate()) ";
        } else {
            Query1 += " and t.ps_sample_accepted_date between '" + fromdate + "' and '" + todate + "'  ";
            Query2 += " and t.ps_sample_accepted_date between '" + fromdate + "' and '" + todate + "'  ";
            Query3 += " and t.ps_sample_accepted_date between '" + fromdate + "' and '" + todate + "'  ";
            // CountQuery += " where (date(rh.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }

       /* // User Name
        if (sampleAcceptanceSearchDTO.getUserId() != 0) {
            Query1 += " and t.is_sample_accepted_by_name = " + sampleAcceptanceSearchDTO.getUserId() + " ";
            Query2 += " and t.is_sample_accepted_by_name = " + sampleAcceptanceSearchDTO.getUserId() + " ";
            Query3 += " and t.is_sample_accepted_by_name = " + sampleAcceptanceSearchDTO.getUserId() + " ";
        }*/
        if (sampleAcceptanceSearchDTO.getServiceType() == 0) {
            MainQuery = Query1 + " ";
        } else if (sampleAcceptanceSearchDTO.getServiceType() == 1) {
            MainQuery = Query2 + " UNION ALL " + Query3 + " ";
        } else {
            MainQuery = Query1 + " UNION ALL " + Query2 + " UNION ALL " + Query3 + " ";
        }
        String CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        System.out.println("Sample Acceptance Report:" + MainQuery);
        try {
            MainQuery += " limit " + ((sampleAcceptanceSearchDTO.getOffset() - 1) * sampleAcceptanceSearchDTO.getLimit()) + "," + sampleAcceptanceSearchDTO.getLimit();
            List<Object[]> sampleAcceptanceReport = entityManager.createNativeQuery(MainQuery).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : sampleAcceptanceReport) {
                SampleAcceptanceListDTO objSampleAcceptanceListDTO = new SampleAcceptanceListDTO();
                objSampleAcceptanceListDTO.setUnitName("" + obj[0]);
                objSampleAcceptanceListDTO.setSampleAcceptanceDate("" + obj[1]);
                objSampleAcceptanceListDTO.setMrNo("" + obj[2]);
                objSampleAcceptanceListDTO.setPatientName("" + obj[3]);
                objSampleAcceptanceListDTO.setGroupName("" + obj[4]);
                objSampleAcceptanceListDTO.setSubGroupName("" + obj[5]);
                objSampleAcceptanceListDTO.setTestName("" + obj[6]);
                objSampleAcceptanceListDTO.setSampleNumber("" + obj[7]);
                objSampleAcceptanceListDTO.setSampleType("" + obj[8]);
                objSampleAcceptanceListDTO.setContainerType("" + obj[9]);
                objSampleAcceptanceListDTO.setSampleAcceptanceBy("" + obj[10]);
                objSampleAcceptanceListDTO.setCount(count);        // total count
                sampleAcceptanceListDTO.add(objSampleAcceptanceListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return sampleAcceptanceListDTO;
    } // END Service
// Sample Acceptance Report End 22.11.2019

    // Pathology Result ReportData Print start
    @RequestMapping("pathologyReport")
    public String getPathologyResultReportDataPrint(@RequestHeader("X-tenantId") String tenantName) throws ArrayIndexOutOfBoundsException {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject responseObj = new JSONObject();
        JSONArray taxKey = new JSONArray();
        HashMap<String, Object> parameters = new LinkedHashMap<>();
        JSONArray parametersKey = new JSONArray();
        List<Object[]> list2 = entityManager.createNativeQuery("SELECT mp.parameter_name,mp.parameter_id FROM mpath_parameter mp WHERE mp.is_active=TRUE AND mp.is_deleted=false; ").getResultList();
        JSONArray paramterNameArray = new JSONArray();
        for (int k = 0; k < list2.size(); k++) {
            parameters.put("parmeter_id", list2.get(k)[1]);
            parameters.put("value", 0);
            JSONObject obj = new JSONObject();
            obj.put("name", list2.get(k)[0]);
            obj.put("id", list2.get(k)[1]);
            parametersKey.put(obj);
        }
        List<Object[]> list1 = entityManager.createNativeQuery("SELECT ps.ps_id,mu.user_driving_no AS EmpNmber, " +
                "CONCAT(mu.user_firstname,' ',mu.user_lastname) AS PatientName, " +
                "ps.ps_finalized_date AS finzalizedDate FROM tpath_bs ps " +
                "INNER JOIN tbill_bill tb ON ps.ps_bill_id = tb.bill_id " +
                "INNER JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id " +
                "INNER JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id " +
                "INNER JOIN mst_user mu ON mu.user_id = mp.patient_user_id " +
                "WHERE ps.is_deleted=0 AND ps.isipd=0 AND ps.is_lab_order_cancel=0 AND ps.is_finalized=1; ").getResultList();
        for (int i = 0; i < list1.size(); i++) {
            JSONObject key = new JSONObject();
            key.put("psId", "" + list1.get(i)[0]);
            key.put("empNo", "" + list1.get(i)[1]);
            key.put("patientName", "" + list1.get(i)[2]);
            key.put("finalizedDate", "" + list1.get(i)[3]);
            List<Object[]> list = entityManager.createNativeQuery("SELECT mparam.parameter_name AS Parameter,mr.result_value,mparam.parameter_id FROM mpath_result mr " +
                    "INNER JOIN mpath_test_result mtr ON mtr.tr_id = mr.result_test_result_id " +
                    "INNER JOIN rpath_test_parameter rtp ON rtp.tp_id=mr.result_test_parameter_id " +
                    "INNER JOIN mpath_parameter mparam ON mparam.parameter_id=rtp.tp_parameter_id " +
                    "WHERE mr.is_deleted=0 AND mtr.tr_ps_id='" + list1.get(i)[0] + "' ").getResultList();
            JSONArray paramterArray = new JSONArray();
            for (int j = 0; j < list.size(); j++) {
                HashMap<String, Object> paramter = parameters;
                if (list.get(j)[1] != null) {
                    paramter.put("" + list.get(j)[2], list.get(j)[1]);
                    key.put("paramter", paramter);
                }
            }
            taxKey.put(key);
        }
        responseObj.put("response", taxKey);
        responseObj.put("paramterArray", parametersKey);
        return responseObj.toString();
    }
    // Pathology Result ReportData Print end
}