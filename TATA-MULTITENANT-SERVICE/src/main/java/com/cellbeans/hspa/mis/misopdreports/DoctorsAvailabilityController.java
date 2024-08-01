package com.cellbeans.hspa.mis.misopdreports;

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
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/listdoctorsavailabilityserviceReport")
public class DoctorsAvailabilityController {

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

    @Autowired
    MstUserRepository mstUserRepository;

    // Group Sub Group Wise Service Report Start
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getdoctorsavailabilityserviceReport/{unitList}")
    public ResponseEntity searchDoctorsAvailabilityService(@RequestHeader("X-tenantId") String tenantName, @RequestBody DoctorsAvailabilitySearchDTO doctorsAvailabilitySearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        String CountQuery = "";
        String columnName = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        // Main Query
        String Query = " SELECT un.unit_name AS Unit, dept.department_name AS Dept, sd.sd_name AS SubDept, " +
                " concat(us.user_firstname, ' ',us.user_lastname) AS FullName, c.cabin_name AS Cabin, d.day_name AS DAY1,  " +
                " dsd.dsd_start_time AS Start_Time, dsd.dsd_end_time AS End_Time " +
                " FROM trn_doctor_schedule_detail dsd " +
                " INNER JOIN mst_cabin c ON c.cabin_id = dsd.dsd_cabin_id " +
                " INNER JOIN mst_day d ON d.day_id = dsd.dsd_day_id " +
                " INNER JOIN mst_department dept ON dept.department_id = dsd.dsd_department_id " +
                " INNER JOIN mst_sub_department sd ON sd.sd_id = dsd.dsd_sub_department_id " +
                " INNER JOIN mst_staff st ON st.staff_id = dsd.dsd_staff_id " +
                " INNER JOIN mst_user us ON us.user_id = st.staff_user_id " +
                " INNER JOIN mst_unit un ON un.unit_id = dsd.dsd_unit_id " +
                " WHERE dsd.is_active = 1 AND dsd.is_deleted = 0 ";
//        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and dsd_unit_id in (" + values + ") ";
        }
        // Department
        if (!doctorsAvailabilitySearchDTO.getDepartment().equals("null") && !doctorsAvailabilitySearchDTO.getDepartment().equals("0")) {
            Query += " and dsd.dsd_department_id =  " + doctorsAvailabilitySearchDTO.getDepartment() + " ";
        }
        //  Sub Department
        if (!doctorsAvailabilitySearchDTO.getSubDepartment().equals("null") && !doctorsAvailabilitySearchDTO.getSubDepartment().equals("0")) {
            Query += " and dsd.dsd_sub_department_id =  " + doctorsAvailabilitySearchDTO.getSubDepartment() + " ";
        }
        // doctor
        if (doctorsAvailabilitySearchDTO.getStaffId() != null && !doctorsAvailabilitySearchDTO.getStaffId().equals("0")) {
            Query += " and dsd.dsd_staff_id = " + doctorsAvailabilitySearchDTO.getStaffId() + " ";
        }
        // day name
        if (doctorsAvailabilitySearchDTO.getDayName() != null && !doctorsAvailabilitySearchDTO.getDayName().equals("0")) {
            Query += " and d.day_name like '%" + doctorsAvailabilitySearchDTO.getDayName() + "%'";
        }
        System.out.println("Doctors Availability Report"+Query);
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unitName,departmentName,subDepartmentName,doctorsName,cabin,day,startTime,endTime";
        Query += " limit " + ((doctorsAvailabilitySearchDTO.getOffset() - 1) * doctorsAvailabilitySearchDTO.getLimit()) + "," + doctorsAvailabilitySearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));

    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getdoctorsavailabilityserviceReportPrint/{unitList}")
    public String searchDoctorsAvailabilityServicePrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody DoctorsAvailabilitySearchDTO doctorsAvailabilitySearchDTO, @PathVariable String[] unitList) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        String CountQuery = "";
        String columnName = "";
        // Main Query
        String Query = " SELECT un.unit_name AS Unit, dept.department_name AS Dept, sd.sd_name AS SubDept, " +
                " concat(us.user_firstname, ' ',us.user_lastname) AS FullName, c.cabin_name AS Cabin, d.day_name AS DAY1,  " +
                " dsd.dsd_start_time AS Start_Time, dsd.dsd_end_time AS End_Time " +
                " FROM trn_doctor_schedule_detail dsd " +
                " INNER JOIN mst_cabin c ON c.cabin_id = dsd.dsd_cabin_id " +
                " INNER JOIN mst_day d ON d.day_id = dsd.dsd_day_id " +
                " INNER JOIN mst_department dept ON dept.department_id = dsd.dsd_department_id " +
                " INNER JOIN mst_sub_department sd ON sd.sd_id = dsd.dsd_sub_department_id " +
                " INNER JOIN mst_staff st ON st.staff_id = dsd.dsd_staff_id " +
                " INNER JOIN mst_user us ON us.user_id = st.staff_user_id " +
                " INNER JOIN mst_unit un ON un.unit_id = dsd.dsd_unit_id " +
                " WHERE dsd.is_active = 1 AND dsd.is_deleted = 0 ";
//        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and dsd_unit_id in (" + values + ") ";
        }
        // Department
        if (!doctorsAvailabilitySearchDTO.getDepartment().equals("null") && !doctorsAvailabilitySearchDTO.getDepartment().equals("0")) {
            Query += " and dsd.dsd_department_id =  " + doctorsAvailabilitySearchDTO.getDepartment() + " ";
        }
        //  Sub Department
        if (!doctorsAvailabilitySearchDTO.getSubDepartment().equals("null") && !doctorsAvailabilitySearchDTO.getSubDepartment().equals("0")) {
            Query += " and dsd.dsd_sub_department_id =  " + doctorsAvailabilitySearchDTO.getSubDepartment() + " ";
        }
        // doctor
        if (doctorsAvailabilitySearchDTO.getStaffId() != null && !doctorsAvailabilitySearchDTO.getStaffId().equals("0")) {
            Query += " and dsd.dsd_staff_id = " + doctorsAvailabilitySearchDTO.getStaffId() + " ";
        }
        // day name
        if (doctorsAvailabilitySearchDTO.getDayName() != null && !doctorsAvailabilitySearchDTO.getDayName().equals("0")) {
            Query += " and d.day_name like '%" + doctorsAvailabilitySearchDTO.getDayName() + "%'";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "unitName,departmentName,subDepartmentName,doctorsName,cabin,day,startTime,endTime";
        // Query += " limit " + ((doctorsAvailabilitySearchDTO.getOffset() - 1) * doctorsAvailabilitySearchDTO.getLimit()) + "," + doctorsAvailabilitySearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(doctorsAvailabilitySearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(doctorsAvailabilitySearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (doctorsAvailabilitySearchDTO.getPrint()) {
            columnName = "unitName,departmentName,subDepartmentName,doctorsName,cabin,day,startTime,endTime";
            return createReport.generateExcel(columnName, "DoctorsAvailabilityReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("DoctorsAvailabilityReport", jsonArray.toString());
        }
//        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

}