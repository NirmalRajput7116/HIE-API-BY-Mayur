package com.cellbeans.hspa.login;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.actionuserrole.ActionUserRoleMst;
import com.cellbeans.hspa.actionuserrole.ActionUserRoleMstRepository;
import com.cellbeans.hspa.actionuserrole.UserActionsForLogin;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.emailutility.Emailsend;
import com.cellbeans.hspa.loginlogout.LoginLogout;
import com.cellbeans.hspa.loginlogout.LoginLogoutRepository;
import com.cellbeans.hspa.mstorg.MstOrg;
import com.cellbeans.hspa.mstorg.MstOrgRepository;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.smsutility.Sendsms;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/loginaction")
public class LoginAuthenticationController {
    Map<String, Object> respMap = null;
    MstStaff User = null;
    MstPatient Patient = null;
    MstStaff UserCC = null;
    List<Boolean> listallaction = null;
    boolean login_status;

    @Autowired
    MstStaffRepository MstStaffRepository;

    @Autowired
    MstPatientRepository mstPatientRepository;

    @Autowired
    ActionUserRoleMstRepository actionUserRoleMstRepository;

    @Autowired
    LoginLogoutRepository loginLogoutRepository;

    LoginLogout loginLogout = null;

    @Autowired
    Emailsend emailsend;

    @Autowired
    MstOrgRepository mstOrgRepository;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    Sendsms sendsms;

    @Autowired
    CreateReport createReport;

    @Autowired
    CreateJSONObject createJSONObject;

    @PersistenceContext
    EntityManager entityManager;

    String columnName = "";

//    @PostMapping("authuserlogin")
//    @Transactional
//    public Map<String, Object> authUserLogin(@RequestHeader("X-tenantId") String tenantName, @RequestBody login login) {
//        TenantContext.setCurrentTenant(tenantName);
//        System.gc();
//        Runtime.getRuntime().gc();
//        boolean loginFlag = false;
//        login_status = false;
//        User = new MstStaff();
//        UserCC = new MstStaff();
//        MstStaff UserDetailObj = new MstStaff();
//        List<ActionUserRoleMst> userRoleList = null;
//        respMap = new HashMap<String, Object>();
//        listallaction = new ArrayList<Boolean>();
//        System.out.println(login.getUserName() + "==>" + login.getPassword());
//
//
//
//        try {
//            //Method By Chetan 02.08.2019
//            User = MstStaffRepository
//                    .findByStaffUserIdUserNameEqualsAndStaffUserIdPasswordEqualsAndStaffUnitUnitIdAndStaffUserIdIsActiveTrueAndStaffUnitIsActiveTrue(
//                            login.getUserName(), login.getPassword(), login.getUnitId());
//            System.out.print(User.toString());
//            if (User != null) {
//                loginFlag = true;
//            }
//            if (loginFlag == true) {
//                if (User.getStaffUserId().getUserName().equals(login.getUserName())
//                        && User.getStaffUserId().getPassword().equals(login.getPassword())) {
//                    {
//                        login_status = true;
//                        try {
//                            userRoleList = actionUserRoleMstRepository
//                                    .findAllByAurStaffIdAndIsActiveTrueAndIsDeletedFalse(
//                                            String.valueOf(User.getStaffId()));
//                            loginLogout = new LoginLogout();
//                            loginLogout.setLltUnitId("" + login.getUnitId());
//                            loginLogout.setLltLoginDate(new Date());
//                            loginLogout.setLltUnitName(login.getUserName());
//                            loginLogout.setLltUserId("" + User.getStaffUserId().getUserId());
//                            loginLogout.setLltUserName("" + login.getUserName());
//                            InetAddress myIP = InetAddress.getLocalHost();
//
//                            loginLogout.setIpAddress(myIP.getHostAddress());
//                            loginLogout = loginLogoutRepository.save(loginLogout);
//
//                        } catch (Exception e) {
//                            System.err.println("Error is userRole fetching :" + e);
//                        }
//                    }
//                    MstOrg mstOrg = mstOrgRepository.getById(login.getOrgId());
//                    MstUnit mstUnit = mstUnitRepository.getById(login.getUnitId());
//                    respMap.put("staff_id", User.getStaffId());
//                    respMap.put("user_id", User.getStaffUserId().getUserId());
//                    respMap.put("user_detail", User);
//                    respMap.put("user_action", userRoleList);
//                    respMap.put("login_status", login_status);
//                    respMap.put("login_json", loginLogout);
//                    respMap.put("mstOrg", mstOrg);
//                    respMap.put("mstUnit", mstUnit);
//                    respMap.put("patient", "0");
//                    entityManager.createNativeQuery("UPDATE mst_city SET city_state_id = \"36\" WHERE city_state_id IS NULL;").executeUpdate();
//                    entityManager.createNativeQuery("UPDATE mst_state SET state_country_id = \"1\" WHERE state_country_id IS NULL;").executeUpdate();
//                    entityManager.createNativeQuery("UPDATE mst_unit SET unit_city_id = \"48699\" WHERE unit_city_id IS NULL;").executeUpdate();
//                    entityManager.createNativeQuery("UPDATE mst_unit SET unit_org_id = \"1\" WHERE unit_org_id IS NULL;").executeUpdate();
//                    entityManager.createNativeQuery("UPDATE mst_user SET user_city_id = \"48699\" WHERE user_city_id IS NULL;").executeUpdate();
//                    entityManager.createNativeQuery("UPDATE mst_user SET user_language_id = \"3\" WHERE user_language_id IS NULL;").executeUpdate();
//                    System.out.println("Successfully");
//                }
//            }
//
//        } catch (Exception e) {
//            respMap.put("login_status", login_status);
//            System.out.println("not Successfully"+ e);
//        }
//
//        return respMap;
//    }


    @PostMapping("authuserlogin")
    @Transactional
    public Map<String, Object> authUserLogin(@RequestHeader("X-tenantId") String tenantName, @RequestBody login login) {
        TenantContext.setCurrentTenant(tenantName);
        boolean loginFlag = false;
        login_status = false;
        respMap = new HashMap<>();
        listallaction = new ArrayList<>();

        try {
            User = MstStaffRepository.findByStaffUserIdUserNameEqualsAndStaffUserIdPasswordEqualsAndStaffUnitUnitIdAndStaffUserIdIsActiveTrueAndStaffUnitIsActiveTrue(
                    login.getUserName(), login.getPassword(), login.getUnitId());

            if (User != null) {
                loginFlag = true;
            }

            if (loginFlag) {
                if (User.getStaffUserId().getUserName().equals(login.getUserName())
                        && User.getStaffUserId().getPassword().equals(login.getPassword())) {

                    login_status = true;
                    try {
                        List<ActionUserRoleMst> userRoleList = actionUserRoleMstRepository
                                .findAllByAurStaffIdAndIsActiveTrueAndIsDeletedFalse(String.valueOf(User.getStaffId()));

                        loginLogout = new LoginLogout();
                        loginLogout.setLltUnitId(String.valueOf(login.getUnitId()));
                        loginLogout.setLltLoginDate(new Date());
                        loginLogout.setLltUnitName(login.getUserName());
                        loginLogout.setLltUserId(String.valueOf(User.getStaffUserId().getUserId()));
                        loginLogout.setLltUserName(login.getUserName());
                        InetAddress myIP = InetAddress.getLocalHost();
                        loginLogout.setIpAddress(myIP.getHostAddress());
                        loginLogout = loginLogoutRepository.save(loginLogout);

                        MstOrg mstOrg = mstOrgRepository.getById(login.getOrgId());
                        MstUnit mstUnit = mstUnitRepository.getById(login.getUnitId());

                        respMap.put("staff_id", User.getStaffId());
                        respMap.put("user_id", User.getStaffUserId().getUserId());
                        respMap.put("user_detail", User);
                        respMap.put("user_action", userRoleList);
                        respMap.put("login_status", login_status);
                        respMap.put("login_json", loginLogout);
                        respMap.put("mstOrg", mstOrg);
                        respMap.put("mstUnit", mstUnit);
                        respMap.put("patient", "0");

                        // Update queries
                        entityManager.createNativeQuery("UPDATE mst_city SET city_state_id = '36' WHERE city_state_id IS NULL;").executeUpdate();
                        entityManager.createNativeQuery("UPDATE mst_state SET state_country_id = '1' WHERE state_country_id IS NULL;").executeUpdate();
                        entityManager.createNativeQuery("UPDATE mst_unit SET unit_city_id = '48699' WHERE unit_city_id IS NULL;").executeUpdate();
                        entityManager.createNativeQuery("UPDATE mst_unit SET unit_org_id = '1' WHERE unit_org_id IS NULL;").executeUpdate();
                        entityManager.createNativeQuery("UPDATE mst_user SET user_city_id = '48699' WHERE user_city_id IS NULL;").executeUpdate();
                        entityManager.createNativeQuery("UPDATE mst_user SET user_language_id = '3' WHERE user_language_id IS NULL;").executeUpdate();

                        System.out.println("Successfully");
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        respMap.put("login_status", login_status);
        return respMap;
    }




    @PostMapping("authuserloginpatient")
    public Map<String, Object> authUserLoginPatient(@RequestHeader("X-tenantId") String tenantName, @RequestBody login login) {
        TenantContext.setCurrentTenant(tenantName);
        System.gc();
        Runtime.getRuntime().gc();
        boolean loginFlag = false;
        login_status = false;
        Patient = new MstPatient();
        respMap = new HashMap<String, Object>();
        System.out.println(login.getUserName() + "==>" + login.getPassword());
        try {

            System.out.println("Inside Auth User Login");
            Patient = mstPatientRepository
                    .findByPatientUserIdUserNameEqualsAndPatientUserIdPasswordEqualsAndAndPatientUserIdIsActiveTrue(
                            login.getUserName(), login.getPassword(), tenantName);
            System.out.print("Query Ends here");

            System.out.print(Patient.toString());
            if (Patient != null) {
                loginFlag = true;
            }
            if (loginFlag == true) {
                if (Patient.getPatientUserId().getUserName().equals(login.getUserName())
                        && Patient.getPatientUserId().getPassword().equals(login.getPassword())) {
                    {
                        login_status = true;
                        try {
                            loginLogout = new LoginLogout();
                            loginLogout.setLltUnitId("" + login.getUnitId());
                            loginLogout.setLltLoginDate(new Date());
                            loginLogout.setLltUnitName(login.getUserName());
                            loginLogout.setLltUserId("" + Patient.getPatientUserId().getUserId());
                            loginLogout.setLltUserName("" + login.getUserName());
                            InetAddress myIP = InetAddress.getLocalHost();
//                            loginLogout.setIpAddress(login.getIpAddress());
                            loginLogout.setIpAddress(myIP.getHostAddress());
                            loginLogout = loginLogoutRepository.save(loginLogout);
                        } catch (Exception e) {
                            System.err.println("Error is userRole fetching :" + e);
                        }
                    }
                    MstOrg mstOrg = mstOrgRepository.getById(Patient.getPatientUserId().getUserUnitId().getUnitOrgId().getOrgId());
                    MstUnit mstUnit = mstUnitRepository.getById(Patient.getPatientUserId().getUserUnitId().getUnitId());
                    respMap.put("user_detail", Patient);
                    respMap.put("login_status", login_status);
                    respMap.put("login_json", loginLogout);
                    respMap.put("mstOrg", mstOrg);
                    respMap.put("mstUnit", mstUnit);
                    respMap.put("patient", "1");
                    System.out.println("Successfully");
                }
            }

        } catch (Exception e) {
            respMap.put("login_status", login_status);
            System.out.println("not Successfully");
        }
        return respMap;
    }

    @PostMapping("authUserLoginMobile")
    public Map<String, Object> authUserLoginMobile(@RequestBody login login) {
//        TenantContext.setCurrentTenant(tenantName);
        boolean loginFlag = false;
        login_status = false;
        respMap = new HashMap<String, Object>();
        User = new MstStaff();
        System.out.println(login.getUserName() + "==>" + login.getPassword());
        try {
            User = MstStaffRepository
                    .findByStaffUserIdUserNameEqualsAndStaffUserIdPasswordEqualsAndStaffUnitUnitIdAndStaffUserIdIsActiveTrueAndStaffUnitIsActiveTrue(
                            login.getUserName(), login.getPassword(), login.getUnitId());
            if (User != null) {
                loginFlag = true;
            }
            if (loginFlag == true) {
                if (User.getStaffUserId().getUserName().equals(login.getUserName())
                        && User.getStaffUserId().getPassword().equals(login.getPassword())) {
                    {
                        login_status = true;
                    }
                    respMap.put("user_detail", User);
                    respMap.put("login_status", login_status);
                    System.out.println("Successfully");
                }
            }

        } catch (Exception e) {
            respMap.put("login_status", login_status);
            System.out.println("not Successfully");
        }
        return respMap;
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getuserauditReport/{unitList}/{userList}/{fromdate}/{todate}")
    public ResponseEntity getuserauditReportList(@RequestHeader("X-tenantId") String tenantName, @RequestBody userAuditSearch userSearchDTO, @PathVariable String[] unitList, @PathVariable String[] userList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        String CountQuery = "";
        String Query = "SELECT IFNULL( l.ip_address, '') AS ipaddress, l.llt_unit_name AS unitName, l.llt_unit_id " +
                " AS unitId, l.llt_user_id AS userId, l.llt_user_name AS unserName, ifNULL(l.llt_login_date, '') AS loginDate, " +
                " ifnull(l.llt_logout_date,'') AS logoutDate FROM loginlogouttrail l WHERE ";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 0; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and l.llt_unit_id in (" + values + ") ";
        }
        if (!userList[0].equals("null")) {
            String values = String.valueOf(userList[0]);
            for (int i = 0; i < userList.length; i++) {
                values += "," + userList[i];
            }
            Query += " and l.llt_user_id in (" + values + ") ";
        }
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "ipaddress,unitName,unitId,userId,unserName,loginDate,logoutDate";
        Query += " limit " + ((userSearchDTO.getOffset() - 1) * userSearchDTO.getLimit()) + "," + userSearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
        return ResponseEntity.ok(jsonArray.toString());
    }

}
