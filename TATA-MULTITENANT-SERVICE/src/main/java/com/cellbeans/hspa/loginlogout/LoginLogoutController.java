package com.cellbeans.hspa.loginlogout;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login_logout_trail")
public class LoginLogoutController {

    @Autowired
    LoginLogoutRepository loginLogoutRepository;

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

    @RequestMapping("/login")
    public String login(@RequestHeader("X-tenantId") String tenantName, @RequestBody LoginLogout loginLogout) {
        TenantContext.setCurrentTenant(tenantName);
        loginLogoutRepository.save(loginLogout);
        return "";
    }

    @RequestMapping("/logout")
    public String logout(@RequestBody LoginLogout loginLogout) {
//        TenantContext.setCurrentTenant(tenantName);
        loginLogoutRepository.save(loginLogout);
        return "";
    }

    @CrossOrigin
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlist/{unitList}/{staffList}")
//	@RequestMapping("getlist")
    public ResponseEntity searchListofOutBoundReferrals(@RequestHeader("X-tenantId") String tenantName, @PathVariable String[] unitList, @PathVariable String[] staffList) {
        TenantContext.setCurrentTenant(tenantName);
		/*Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(date);
*/
        String Query = " SELECT ut.unit_name AS unitName, mu.user_name AS userName,  " +
                " IFNULL (CONCAT(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') AS staffName,  " +
                " SUM(CASE WHEN (l.llt_login_date > DATE(NOW() - INTERVAL 15 DAY)) THEN 1 ELSE 0 END) AS last15Days, " +
                " SUM(CASE WHEN (l.llt_login_date > DATE(NOW() - INTERVAL 30 DAY)) THEN 1 ELSE 0 END) AS last30Days, " +
                " MAX(l.llt_login_date) AS lastLogin,  " +
                " DATEDIFF(CURDATE(), MAX(DATE(l.llt_login_date))) AS daysSinceLastLogin " +
                " FROM loginlogouttrail l " +
                " LEFT JOIN mst_user mu ON mu.user_id = l.llt_user_id " +
                " LEFT JOIN mst_staff ms ON ms.staff_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_unit ut ON ut.unit_id= l.llt_unit_id " +
                " WHERE ut.is_active = 1 AND ut.is_deleted = 0 AND mu.is_active= 1 AND mu.is_deleted= 0 ";
//				" AND ut.unit_id IN(1,2) AND mu.user_id in(1,2) ";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and ut.unit_id IN (" + values + ") ";

        }
        if (!staffList[0].equals("null")) {
            String values = String.valueOf(staffList[0]);
            for (int i = 1; i < staffList.length; i++) {
                values += "," + staffList[i];
            }
            Query += " and mu.user_id in (" + values + ") ";

        }
        Query += " GROUP BY ut.unit_id, mu.user_id " +
                " UNION ALL " +
                " SELECT ut.unit_name AS unitName,mu.user_name AS userName, " +
                " IFNULL (CONCAT(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') AS staffName, " +
                " 0 AS last15Days, 0 AS last30Days, 0 AS lastLogin,0  AS daysSinceLastLogin " +
                " from mst_staff ms  " +
                " LEFT JOIN mst_user mu ON mu.user_id=ms.staff_user_id " +
                " LEFT JOIN mst_staff_staff_unit sun ON ms.staff_id = sun.mst_staff_staff_id " +
                " LEFT JOIN mst_unit ut ON ut.unit_id = sun.staff_unit_unit_id " +
                " LEFT JOIN loginlogouttrail l ON l.llt_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " WHERE l.llt_user_id IS NULL AND ut.is_active = 1 AND ut.is_deleted = 0 AND mu.is_active= 1 AND mu.is_deleted= 0 ";
//				" AND ut.unit_id IN(1,2) AND mu.user_id in(1,2) " +
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and ut.unit_id IN (" + values + ") ";

        }
        if (!staffList[0].equals("null")) {
            String values = String.valueOf(staffList[0]);
            for (int i = 1; i < staffList.length; i++) {
                values += "," + staffList[i];
            }
            Query += " and mu.user_id in (" + values + ") ";

        }
        Query += " GROUP BY ut.unit_id, mu.user_id ";
        System.out.println("System Usage Report On 2022-01-10"+ Query);
        String CountQuery = "";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        String columnName = "unitName,userName,staffName,last15Days,last30Days,lastLogin,daysSinceLastLogin";
        return ResponseEntity.ok(createJSONObject.createJson(columnName, Query));
    }

    @CrossOrigin
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistPrint/{unitList}/{staffList}")
//	@RequestMapping("getlist")
    public String searchListofOutBoundReferralsPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody loginlogoutSearchDTO loginlogoutSearchDTO, @PathVariable String[] unitList, @PathVariable String[] staffList) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
		/*Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(date);
*/
        String Query = " SELECT ut.unit_name AS unitName, mu.user_name AS userName,  " +
                " IFNULL (CONCAT(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') AS staffName,  " +
                " SUM(CASE WHEN (l.llt_login_date > DATE(NOW() - INTERVAL 15 DAY)) THEN 1 ELSE 0 END) AS last15Days, " +
                " SUM(CASE WHEN (l.llt_login_date > DATE(NOW() - INTERVAL 30 DAY)) THEN 1 ELSE 0 END) AS last30Days, " +
                " MAX((l.llt_login_date)) AS lastLogin,  " +
                " DATEDIFF(CURDATE(), MAX(DATE(l.llt_login_date))) AS daysSinceLastLogin " +
                " FROM loginlogouttrail l " +
                " LEFT JOIN mst_user mu ON mu.user_id = l.llt_user_id " +
                " LEFT JOIN mst_staff ms ON ms.staff_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " LEFT JOIN mst_unit ut ON ut.unit_id= l.llt_unit_id " +
                " WHERE ut.is_active = 1 AND ut.is_deleted = 0 AND mu.is_active= 1 AND mu.is_deleted= 0 ";
//				" AND ut.unit_id IN(1,2) AND mu.user_id in(1,2) ";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and ut.unit_id IN (" + values + ") ";

        }
        if (!staffList[0].equals("null")) {
            String values = String.valueOf(staffList[0]);
            for (int i = 1; i < staffList.length; i++) {
                values += "," + staffList[i];
            }
            Query += " and mu.user_id in (" + values + ") ";

        }
        Query += " GROUP BY ut.unit_id, mu.user_id " +
                " UNION ALL " +
                " SELECT ut.unit_name AS unitName,mu.user_name AS userName, " +
                " IFNULL (CONCAT(mt.title_name,' ', mu.user_firstname,' ', mu.user_lastname), '') AS staffName, " +
                " 0 AS last15Days, 0 AS last30Days, 0 AS lastLogin,0  AS daysSinceLastLogin " +
                " from mst_staff ms  " +
                " LEFT JOIN mst_user mu ON mu.user_id=ms.staff_user_id " +
                " LEFT JOIN mst_staff_staff_unit sun ON ms.staff_id = sun.mst_staff_staff_id " +
                " LEFT JOIN mst_unit ut ON ut.unit_id = sun.staff_unit_unit_id " +
                " LEFT JOIN loginlogouttrail l ON l.llt_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " WHERE l.llt_user_id IS NULL AND ut.is_active = 1 AND ut.is_deleted = 0 AND mu.is_active= 1 AND mu.is_deleted= 0 ";
//				" AND ut.unit_id IN(1,2) AND mu.user_id in(1,2) " +
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and ut.unit_id IN (" + values + ") ";

        }
        if (!staffList[0].equals("null")) {
            String values = String.valueOf(staffList[0]);
            for (int i = 1; i < staffList.length; i++) {
                values += "," + staffList[i];
            }
            Query += " and mu.user_id in (" + values + ") ";

        }
        Query += " GROUP BY ut.unit_id, mu.user_id ";
        String CountQuery = "";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        String columnName = "unitName,userName,staffName,last15Days,last30Days,lastLogin,daysSinceLastLogin";
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(loginlogoutSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(loginlogoutSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (loginlogoutSearchDTO.getPrint()) {
            columnName = "unitName,userName,staffName,last15Days,last30Days,lastLogin,daysSinceLastLogin";
            return createReport.generateExcel(columnName, "SystemUsageReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("SystemUsageReport", jsonArray.toString());
        }
    }
}
