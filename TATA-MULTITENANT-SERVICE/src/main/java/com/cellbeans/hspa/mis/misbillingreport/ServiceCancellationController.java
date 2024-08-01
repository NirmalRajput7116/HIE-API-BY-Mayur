/*package com.cellbeans.hspa.mis.misbillingreport;

public class ServiceCancellationController {
}*/
package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/servicecancellationReport")
public class ServiceCancellationController {

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

    // Service cancellation report Start
    @RequestMapping("getservicecancellationReport/{unitList}")
    public List<ServiceCancellationListDTO> searchservicecancellationreport(@RequestHeader("X-tenantId") String tenantName, @RequestBody ServiceCancellationSearchDTO servicecancellationSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<ServiceCancellationListDTO> serviceCancellationDTOList = new ArrayList<ServiceCancellationListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String columnName = "";
        // Main Query
        String Query = "SELECT ics.last_modified_date AS ServiceCancelledDate,mp.patient_mr_no AS MRNO, " +
                " concat(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) AS PatientName, ta.admission_ipd_no AS IPDNO, " +
                " ms.service_name, CONCAT(mt1.title_name,' ',mu1.user_firstname,' ',mu1.user_lastname) AS UserName, " +
                " ics.cs_cancel_remark AS Remark, mun.unit_name, ics.cs_qty_rate AS net_amt " +
                " from ipd_charges_service ics  " +
                " INNER JOIN mbill_ipd_charge mic ON ics.cs_charge_id = mic.ipdcharge_id " +
                " INNER JOIN trn_admission ta ON mic.charge_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " INNER JOIN mbill_service ms ON ics.cs_service_id = ms.service_id " +
                " LEFT JOIN mst_staff mst ON ics.cs_staff_id = mst.staff_id " +
                " INNER JOIN mst_user mu1 ON mst.staff_user_id = mu1.user_id " +
                " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                " LEFT JOIN mst_unit mun ON ta.admission_unit_id = mun.unit_id " +
                " WHERE ics.cs_cancel = 1 AND ics.is_active = 1 AND ics.is_deleted = 0 ";
        // Total Count
        String CountQuery = " SELECT COUNT(*) from ipd_charges_service ics  " +
                " INNER JOIN mbill_ipd_charge mic ON ics.cs_charge_id = mic.ipdcharge_id " +
                " INNER JOIN trn_admission ta ON mic.charge_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " INNER JOIN mbill_service ms ON ics.cs_service_id = ms.service_id " +
                " LEFT JOIN mst_staff mst ON ics.cs_staff_id = mst.staff_id " +
                " INNER JOIN mst_user mu1 ON mst.staff_user_id = mu1.user_id " +
                " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id " +
                " WHERE ics.cs_cancel = 1 AND ics.is_active = 1 AND ics.is_deleted = 0 ";
        if (servicecancellationSearchDTO.getFromdate().equals("") || servicecancellationSearchDTO.getFromdate().equals("null")) {
            servicecancellationSearchDTO.setFromdate("1990-06-07");
        }
        if (servicecancellationSearchDTO.getTodate().equals("") || servicecancellationSearchDTO.getTodate().equals("null")) {
            servicecancellationSearchDTO.setTodate(strDate);
        }
        if (servicecancellationSearchDTO.getTodaydate()) {
            Query += " and (date(ics.last_modified_date)=curdate()) ";
            CountQuery += " and (date(ics.last_modified_date)=curdate()) ";
        } else {
            Query += " and (date(ics.last_modified_date) between '" + servicecancellationSearchDTO.getFromdate() + "' and '" + servicecancellationSearchDTO.getTodate() + "')  ";
            CountQuery += " and (date(ics.last_modified_date) between '" + servicecancellationSearchDTO.getFromdate() + "' and '" + servicecancellationSearchDTO.getTodate() + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and ta.admission_unit_id in (" + values + ") ";
            CountQuery += " and ta.admission_unit_id in (" + values + ") ";
        }
        // service Name
        if (servicecancellationSearchDTO.getServiceName() != null && !servicecancellationSearchDTO.getServiceName().equals("0")) {
            Query += " and ms.service_name like '%" + servicecancellationSearchDTO.getServiceName() + "%'";
            CountQuery += " and ms.service_name like '%" + servicecancellationSearchDTO.getServiceName() + "%'";
        }
        // Mr No
        if (servicecancellationSearchDTO.getMrNo() != null && !servicecancellationSearchDTO.getMrNo().equals("0")) {
            Query += " and mp.patient_mr_no like '%" + servicecancellationSearchDTO.getMrNo() + "%'";
            CountQuery += " and mp.patient_mr_no like '%" + servicecancellationSearchDTO.getMrNo() + "%'";
        }
        // Patient Name
        if (servicecancellationSearchDTO.getPatientName() != null && !servicecancellationSearchDTO.getPatientName().equals("0")) {
            Query += " and CONCAT(mu.user_firstname,' ',mu.user_lastname) like '%" + servicecancellationSearchDTO.getPatientName() + "%'";
            CountQuery += " and CONCAT(mu.user_firstname,' ',mu.user_lastname) like '%" + servicecancellationSearchDTO.getPatientName() + "%'";
        }
        // User Name
        if (!String.valueOf(servicecancellationSearchDTO.getStaffId()).equals("0")) {
            Query += " and ics.cs_staff_id=" + servicecancellationSearchDTO.getStaffId() + " ";
            CountQuery += " and ics.cs_staff_id=" + servicecancellationSearchDTO.getStaffId() + " ";
        }
        System.out.println("CountQuery : " + CountQuery);
        try {
            Query += " limit " + ((servicecancellationSearchDTO.getOffset() - 1) * servicecancellationSearchDTO.getLimit()) + "," + servicecancellationSearchDTO.getLimit();
            List<Object[]> listofservicecancelreport = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listofservicecancelreport) {
                ServiceCancellationListDTO objServiceCancellationListDTO = new ServiceCancellationListDTO();
                objServiceCancellationListDTO.setServiceCancellDateAndTime("" + obj[0]);
                objServiceCancellationListDTO.setMrNo("" + obj[1]);
                objServiceCancellationListDTO.setPatientName("" + obj[2]);
                objServiceCancellationListDTO.setIpdNo("" + obj[3]);
                objServiceCancellationListDTO.setServiceName("" + obj[4]);
                objServiceCancellationListDTO.setUserName("" + obj[5]);
                objServiceCancellationListDTO.setRemark("" + obj[6]);
                objServiceCancellationListDTO.setUnitName("" + obj[7]);
                objServiceCancellationListDTO.setNetAmount("" + obj[8]);
                objServiceCancellationListDTO.setCount(count);                   // total count
                serviceCancellationDTOList.add(objServiceCancellationListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("Error:" + e);
            //return null;
        }
        return serviceCancellationDTOList;
    } // END Service
    // Service cancellation report End

}


