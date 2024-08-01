package com.cellbeans.hspa.dashboard;

import com.cellbeans.hspa.SchedulerUtility.CasePaperSchedular;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import com.cellbeans.hspa.emailutility.Emailsend;
import com.cellbeans.hspa.mstday.MstDay;
import com.cellbeans.hspa.mstday.MstDayRepository;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.tbillbill.TbillBillRepository;
import com.cellbeans.hspa.tbillbillservice.TbillBillServiceRepository;
import com.cellbeans.hspa.tbillserviceradiology.TbillServiceRadiologyRepository;
import com.cellbeans.hspa.tinvitemsalesreturn.TinvItemSalesReturnRepository;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySaleRepository;
import com.cellbeans.hspa.tpathbs.TpathBsRepository;
import com.cellbeans.hspa.trnappointment.TrnAppointmentRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

//import java.sql.Date;
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    TrnAppointmentRepository trnAppointmentRepository;

    @Autowired
    MstPatientRepository mstPatientRepository;

    @Autowired
    TbillServiceRadiologyRepository tbillServiceRadiologyRepository;

    @Autowired
    TbillBillRepository tBillBillRepository;

    @Autowired
    TbillBillServiceRepository tbillBillServiceRepository;

    @Autowired
    TinvPharmacySaleRepository tinvPharmacySaleRepository;

    @Autowired
    TinvItemSalesReturnRepository tinvItemSalesReturnRepository;

    @Autowired
    MstDayRepository mstDayRepository;

    @Autowired
    TpathBsRepository tpathBsRepository;

    @PersistenceContext
    EntityManager objEntityManager;

    @Autowired
    EntityManager entityManager;

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @RequestMapping("gettariffdetails/{unitid}/{unitlistid}")
    public Map<String, List> gettariffdetails(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid,
                                              @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> tarifflist = new ArrayList<String>();
        List<String> tarifflistCnt = new ArrayList<String>();
        if (unitid != 0) {
            tarifflist = objEntityManager.createNativeQuery("select count(mbill_tariff.tariff_name) as name ,mbill_tariff.tariff_name as cnt  from mst_visit RIGHT JOIN mbill_tariff ON mbill_tariff.tariff_id = mst_visit.visit_tariff_id where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and   mst_visit.visit_unit_id = " + unitid + " and DATE_FORMAT(mst_visit.created_date,'%y-%m-%d') = CURDATE() group by mbill_tariff.tariff_name").getResultList();
            tarifflistCnt = objEntityManager.createNativeQuery("	select count(mst_visit.visit_id) as totalcnt from mst_visit RIGHT JOIN mbill_tariff ON mbill_tariff.tariff_id = mst_visit.visit_tariff_id  where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and   mst_visit.visit_unit_id = " + unitid + " and  DATE_FORMAT(mst_visit.created_date,'%y-%m-%d') = CURDATE() ").getResultList();

        } else {
            tarifflist = objEntityManager.createNativeQuery("select count(mbill_tariff.tariff_name) as name ,mbill_tariff.tariff_name as cnt  from mst_visit RIGHT JOIN mbill_tariff ON mbill_tariff.tariff_id = mst_visit.visit_tariff_id where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and   mst_visit.visit_unit_id in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(mst_visit.created_date,'%y-%m-%d') = CURDATE() group by mbill_tariff.tariff_name").getResultList();
            tarifflistCnt = objEntityManager.createNativeQuery("	select count(mst_visit.visit_id) as totalcnt from mst_visit RIGHT JOIN mbill_tariff ON mbill_tariff.tariff_id = mst_visit.visit_tariff_id  where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and   mst_visit.visit_unit_id in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(mst_visit.created_date,'%y-%m-%d') = CURDATE() ").getResultList();
        }
        respMap.put("tarifflist", tarifflist);
        respMap.put("tarifflistCnt", tarifflistCnt);
        return respMap;
    }

    @RequestMapping("getappoinmentdetailsbydoctor/{unitid}/{unitlistid}")
    public Map<String, List> getappoinmentdetailsbydoctor(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("----------------------IN doctor details--------------------");
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> stafflist = new ArrayList<String>();
        if (unitid != 0) {
            String query = "select count(tra.appointmentStaffId.staffUserId) , tra.appointmentStaffId.staffUserId.userFirstname ,tra.appointmentStaffId.staffUserId.userLastname from TrnAppointment tra where tra.isActive = 1 and tra.isDeleted = 0 and tra.appointmentUnitId = " + unitid + " and  DATE_FORMAT(tra.createdDate,'%y-%m-%d') = CURDATE() group by tra.appointmentStaffId.staffUserId";
            stafflist = objEntityManager.createQuery(query).getResultList();

        } else {
            String query = "select count(tra.appointmentStaffId.staffUserId) , tra.appointmentStaffId.staffUserId.userFirstname ,tra.appointmentStaffId.staffUserId.userLastname from TrnAppointment tra where tra.isActive = 1 and tra.isDeleted = 0 and tra.appointmentUnitId in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(tra.createdDate,'%y-%m-%d') = CURDATE() group by tra.appointmentStaffId.staffUserId";
            stafflist = objEntityManager.createQuery(query).getResultList();

        }
        respMap.put("stafflist", stafflist);
        return respMap;
    }

    @RequestMapping("getvisitdetailsbydepartment/{unitid}/{unitlistid}")
    public Map<String, List> getvisitdetailsbydepartment(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> doctorlist = new ArrayList<String>();
        if (unitid != 0) {
            //String query2 = "select count(mv.visitStaffId.staffUserId), mv.visitStaffId.staffUserId.userFirstname , mv.visitStaffId.staffUserId.userLastname , mv.visitStaffId.staffDepartmentId.departmentName , mv.visitStaffId.staffSdId.sdName from  MstVisit mv where mv.isActive = 1 and mv.isDeleted = 0 and mv.visitUnitId = " + unitid + " and DATE_FORMAT(mv.visitDate,'%y-%m-%d') = CURDATE() group by mv.visitStaffId.staffUserId";
            String query2 = "select count(vi.visit_id) as count, ifnull(u.user_firstname,'') as userFirstname , ifnull(u.user_lastname,'') as userLastname , " + "ifnull(d.department_name,'') as departmentName , ifnull(sd.sd_name,'') as subDepartmentName " + "from mst_visit vi , mst_staff s, " + "mst_sub_department sd, " + " mst_department d,mst_user u " + "where s.staff_id = vi.visit_staff_id and  sd.sd_id = vi.visit_sub_department_id " + "and d.department_id = sd.sd_department_id and  u.user_id = s.staff_user_id " + "and date(vi.visit_date) = CURDATE()   and vi.visit_unit_id = " + unitid + "  and  vi.is_active = 1 and vi.is_deleted = 0 " + "group by visit_staff_id,visit_sub_department_id ";
            doctorlist = (List<String>) objEntityManager.createNativeQuery(query2).getResultList();

        } else {
            String query2 = "select count(vi.visit_id) as count, ifnull(u.user_firstname,'') as userFirstname , ifnull(u.user_lastname,'') as userLastname , " + "ifnull(d.department_name,'') as departmentName , ifnull(sd.sd_name,'') as subDepartmentName " + "from mst_visit vi , mst_staff s, " + "mst_sub_department sd, " + " mst_department d,mst_user u " + "where s.staff_id = vi.visit_staff_id and  sd.sd_id = vi.visit_sub_department_id " + "and d.department_id = sd.sd_department_id and  u.user_id = s.staff_user_id " + "and date(vi.visit_date) = CURDATE()   and vi.visit_unit_id  in (" + removeLastChar(unitlistid) + ")  and  vi.is_active = 1 and vi.is_deleted = 0 " + "group by visit_staff_id,visit_sub_department_id ";
            //String query2 = "select count(mv.visitStaffId.staffUserId), mv.visitStaffId.staffUserId.userFirstname , mv.visitStaffId.staffUserId.userLastname , mv.visitStaffId.staffDepartmentId.departmentName , mv.visitStaffId.staffSdId.sdName from  MstVisit mv where mv.isActive = 1 and mv.isDeleted = 0 and mv.visitUnitId in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(mv.visitDate,'%y-%m-%d') = CURDATE() group by mv.visitStaffId.staffUserId";
            doctorlist = (List<String>) objEntityManager.createQuery(query2).getResultList();
        }
        respMap.put("doctorlist", doctorlist);
        return respMap;
    }
//
//        @RequestMapping("getcollectiontype/{unitid}")
//        public List  getcollectiontype(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid) {
//    TenantContext.setCurrentTenant(tenantName);
//        // System.out.println("---object------");
//
//        List<String> billCollectiondetails = new ArrayList<String>();
//
//
//            String query2 = "select tbr.brPmId.pmName from  TbillReciept tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and tbr.isCancelled = 0 and tbr.brBillId.tbillUnitId = "+unitid+" and tbr.isCancelled = false and  DATE_FORMAT(tbr.createdDate,'%y-%m-%d') = CURDATE() group by tbr.brPmId.pmName";
//            billCollectiondetails = objEntityManager.createQuery(query2).getResultList();
//
//        return billCollectiondetails;
//    }

    @RequestMapping("getbillingdetails/{unitid}/{unitlistid}")
    public Map<String, List> getbillingdetails(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> billdetails = new ArrayList<String>();
        if (unitid != 0) {
            String query2 = " select count(tbb.billId) , SUM(COALESCE(billNetPayable,0.00)),  " +
                    " SUM(COALESCE(billAmountPaid,0.00)) ,  SUM(COALESCE(billOutstanding,0.00))," +
                    " tbb.billTariffId.tariffName , SUM(COALESCE(companyNetPay,0.00)) ,SUM(COALESCE(companyPaidAmount,0.00)) " +
                    " from  TBillBill tbb where tbb.isActive = 1 " +
                    " and tbb.isDeleted = 0  and tbb.tbillUnitId = " + unitid + " " +
                    " and tbb.isCancelled = false and   date(tbb.lastModifiedDate) = CURDATE() " +
                    " group by tbb.billTariffId.tariffName";
            billdetails = objEntityManager.createQuery(query2).getResultList();
        } else {
            String query2 = " select count(tbb.billId) , SUM(COALESCE(billNetPayable,0.00)),  " +
                    " SUM(COALESCE(billAmountPaid,0.00)) ,  SUM(COALESCE(billOutstanding,0.00)),tbb.billTariffId.tariffName ," +
                    " SUM(COALESCE(companyNetPay,0.00)) ,SUM(COALESCE(companyPaidAmount,0.00)) from  TBillBill tbb where tbb.isActive = 1 and tbb.isDeleted = 0  " +
                    " and tbb.tbillUnitId in (" + removeLastChar(unitlistid) + ") and tbb.isCancelled = false " +
                    " and   date(tbb.lastModifiedDate) = CURDATE()  " +
                    " group by tbb.billTariffId.tariffName";
            billdetails = objEntityManager.createQuery(query2).getResultList();
        }
        respMap.put("billdetails", billdetails);
        return respMap;
    }

    @RequestMapping("getCollectionType/{unitid}/{unitlistid}")
    public List getCollectionType(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        List<String> billCollectiondetails = new ArrayList<String>();
        if (unitid != 0) {
            String query2 = "select tbr.brPmId.pmName from  TbillReciept tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and tbr.isCancelled = 0 and tbr.brBillId.tbillUnitId = " + unitid + " and tbr.isCancelled = false and  DATE_FORMAT(tbr.createdDate,'%y-%m-%d') = CURDATE() group by tbr.brPmId.pmName";
            billCollectiondetails = objEntityManager.createQuery(query2).getResultList();

        } else {
            String query2 = "select tbr.brPmId.pmName from  TbillReciept tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and tbr.isCancelled = 0 and tbr.brBillId.tbillUnitId in (" + removeLastChar(unitlistid) + ") and tbr.isCancelled = false and  DATE_FORMAT(tbr.createdDate,'%y-%m-%d') = CURDATE() group by tbr.brPmId.pmName";
            billCollectiondetails = objEntityManager.createQuery(query2).getResultList();
        }
        return billCollectiondetails;
    }

    @RequestMapping("getCollectionSum/{unitid}/{unitlistid}")
    public List getCollectionSum(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        List<String> billCollectionCount = new ArrayList<String>();
        if (unitid != 0) {
            String query2 = "select  SUM(COALESCE(tbr.brPaymentAmount,0.00)) from  TbillReciept tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and tbr.brBillId.tbillUnitId = " + unitid + " and tbr.isCancelled = 0 and  DATE_FORMAT(tbr.createdDate,'%y-%m-%d') = CURDATE() group by tbr.brPmId.pmName";
            billCollectionCount = objEntityManager.createQuery(query2).getResultList();

        } else {
            String query2 = "select  SUM(COALESCE(tbr.brPaymentAmount,0.00)) from  TbillReciept tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and tbr.brBillId.tbillUnitId   in (" + removeLastChar(unitlistid) + ") and tbr.isCancelled = 0 and  DATE_FORMAT(tbr.createdDate,'%y-%m-%d') = CURDATE() group by tbr.brPmId.pmName";
            billCollectionCount = objEntityManager.createQuery(query2).getResultList();

        }
        return billCollectionCount;
    }

    @RequestMapping("getcollectiontypeandsum/{unitid}/{unitlistid}")
    public Map<String, List> getcollectiontypeandsum(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> coldetails = new ArrayList<String>();
        List<String> colsum = new ArrayList<String>();
        if (unitid != 0) {
            String query1 = "select tbr.brPmId.pmName,SUM(COALESCE(tbr.brPaymentAmount,0.00))  from  TbillReciept tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and tbr.isCancelled = 0  and tbr.brBillId.tbillUnitId = " + unitid + " and  DATE_FORMAT(tbr.createdDate,'%y-%m-%d') = CURDATE() group by tbr.brPmId";
            coldetails = objEntityManager.createQuery(query1).getResultList();

        } else {
            String query1 = "select tbr.brPmId.pmName,SUM(COALESCE(tbr.brPaymentAmount,0.00))  from  TbillReciept tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and tbr.isCancelled = 0  and tbr.brBillId.tbillUnitId  in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(tbr.createdDate,'%y-%m-%d') = CURDATE() group by tbr.brPmId";
            coldetails = objEntityManager.createQuery(query1).getResultList();

        }
        respMap.put("coldetails", coldetails);
        return respMap;
    }

    @RequestMapping("getdepartmentwisseervicereport/{unitid}/{tariff}/{unitlistid}")
    public Map<String, List> getdepartmentwisseervicereport(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("tariff") Long tariff, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> servicedetails = new ArrayList<String>();
        if (unitid != 0) {
            if (tariff == 0) {
                // System.out.println("--without tarriff------");
                String query2 = "select tbb.bsServiceId.serviceName,tbb.bsServiceId.serviceGroupId.groupName,tbb.bsServiceId.serviceSgId.sgName,count(tbb.bsId) from  TbillBillService tbb where tbb.isActive = 1 and tbb.isDeleted = 0  and tbb.bsCancel = false and tbb.bsBillId.tbillUnitId = " + unitid + " and tbb.bsDate = CURDATE() group by tbb.bsServiceId";
                servicedetails = objEntityManager.createQuery(query2).getResultList();

            } else {
                // System.out.println("---with tariff------");
                String query2 = "select tbb.bsServiceId.serviceName,tbb.bsServiceId.serviceGroupId.groupName,tbb.bsServiceId.serviceSgId.sgName,count(tbb.bsId) from  TbillBillService tbb where tbb.isActive = 1 and tbb.isDeleted = 0  and tbb.bsCancel = false  and tbb.bsBillId.tbillUnitId = " + unitid + " and  tbb.bsDate = CURDATE() and tbb.bsBillId.billTariffId.tariffId = " + tariff + "  group by tbb.bsServiceId";
                servicedetails = objEntityManager.createQuery(query2).getResultList();
            }

        } else {
            if (tariff == 0) {
                // System.out.println("--without tarriff------");
                String query2 = "select tbb.bsServiceId.serviceName,tbb.bsServiceId.serviceGroupId.groupName,tbb.bsServiceId.serviceSgId.sgName,count(tbb.bsId) from  TbillBillService tbb where tbb.isActive = 1 and tbb.isDeleted = 0  and tbb.bsCancel = false and tbb.bsBillId.tbillUnitId in (" + removeLastChar(unitlistid) + ") and tbb.bsDate = CURDATE() group by tbb.bsServiceId";
                servicedetails = objEntityManager.createQuery(query2).getResultList();

            } else {
                // System.out.println("---with tariff------");
                String query2 = "select tbb.bsServiceId.serviceName,tbb.bsServiceId.serviceGroupId.groupName,tbb.bsServiceId.serviceSgId.sgName,count(tbb.bsId) from  TbillBillService tbb where tbb.isActive = 1 and tbb.isDeleted = 0  and tbb.bsCancel = false  and tbb.bsBillId.tbillUnitId in (" + removeLastChar(unitlistid) + ") and  tbb.bsDate = CURDATE() and tbb.bsBillId.billTariffId.tariffId = " + tariff + "  group by tbb.bsServiceId";
                servicedetails = objEntityManager.createQuery(query2).getResultList();
            }

        }
        respMap.put("servicedetails", servicedetails);
        return respMap;
    }

    @RequestMapping("gettarifflist")
    public List gettarifflist(@RequestHeader("X-tenantId") String tenantName) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        String query2 = "select mbt from MbillTariff mbt where mbt.isActive = 1 and mbt.isDeleted = 0  ";
        return objEntityManager.createQuery(query2).getResultList();

    }

    @RequestMapping("getpharmacybillingdetails/{unitid}/{unitlistid}")
    public Map<String, List> getpharmacybillingdetails(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> pharmacybilldetails = new ArrayList<String>();
        if (unitid != 0) {
            String query2 = "select  (SUM(COALESCE(t.psPatientPayFromFromTarrif,0.00))+SUM(COALESCE(t.psOutStandingAmountForCompany,0.00)) ), (SUM(COALESCE(t.psPatientPayFromFromTarrif,0.00))- SUM(COALESCE(t.psOutStandingAmountForPatient,0.00)))  ,  SUM(COALESCE(t.psOutStandingAmountForPatient,0.00)) , SUM(COALESCE(t.psOutStandingAmountForCompany,0.00))  from  TinvPharmacySale t where t.isActive = 1 and t.isDeleted = 0  and t.pharmacyUnitId = " + unitid + " and DATE_FORMAT(t.createdDate,'%y-%m-%d')  = CURDATE() ";
            pharmacybilldetails = objEntityManager.createQuery(query2).getResultList();
        } else {
            String query2 = "select  (SUM(COALESCE(t.psPatientPayFromFromTarrif,0.00))+SUM(COALESCE(t.psOutStandingAmountForCompany,0.00))) , (SUM(COALESCE(t.psPatientPayFromFromTarrif,0.00))- SUM(COALESCE(t.psOutStandingAmountForPatient,0.00)))  ,  SUM(COALESCE(t.psOutStandingAmountForPatient,0.00)) , SUM(COALESCE(t.psOutStandingAmountForCompany,0.00))   from  TinvPharmacySale t where t.isActive = 1 and t.isDeleted = 0  and t.pharmacyUnitId    in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(t.createdDate,'%y-%m-%d')  = CURDATE() ";
            pharmacybilldetails = objEntityManager.createQuery(query2).getResultList();
        }
        respMap.put("pharmacybilldetails", pharmacybilldetails);
        return respMap;
    }

    @RequestMapping("getcurrency")
    public Object getcurrency(@RequestHeader("X-tenantId") String tenantName) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        String query2 = "select mc from MacCurrency mc where mc.isActive = 1 and mc.isDeleted = 0 and mc.isCurrencyDefault = 1 ";
        return objEntityManager.createQuery(query2).getSingleResult();

    }
//
//        @RequestMapping("getemrdetails/{unitid}")
//        public Map<String,BigInteger>  getemrdetails(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid) throws JSONException {
//        // System.out.println("---object------");
//        Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();
//
//        BigInteger emrtimelinegenerated_today,emrtimelinegenerated_weekly,emrtimelinegenerated_monthly,emrfinalized_today,emrfinalized_weekly,emrfinalized_monthly,doctororder_today,doctororder_weekly,doctororder_monthly,doctororderservice_today,doctororderservice_weekly,doctororderservice_monthly,prescriptiongenerated_today,prescriptiongenerated_weekly,prescriptiongenerated_monthly;
//
//
//        String e1 = "select count(t.timeline_id) from  temr_timeline t where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.timeline_registration_source = 0 ";
//        emrtimelinegenerated_today= new BigInteger(objEntityManager.createNativeQuery(e1).getSingleResult().toString());
//
//        String e2 = "select count(t.timeline_id) from  temr_timeline t where t.is_active = 1 and t.is_deleted = 0  and t.timeline_registration_source = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d') between adddate(now(),- 7) and now()  ";
//        emrtimelinegenerated_weekly = new BigInteger(objEntityManager.createNativeQuery(e2).getSingleResult().toString());
//        String e3 = "select count(t.timeline_id) from  temr_timeline t where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  and t.timeline_registration_source = 0 ";
//        emrtimelinegenerated_monthly =new BigInteger(objEntityManager.createNativeQuery(e3).getSingleResult().toString());
//
//
//        String f1 = "select count(t.timeline_id) from  temr_timeline t where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.timeline_registration_source = 0  and t.isemrfinal = 1";
//        emrfinalized_today =new BigInteger(objEntityManager.createNativeQuery(f1).getSingleResult().toString());
//        String f2 = "select count(t.timeline_id) from  temr_timeline t where t.is_active = 1 and t.is_deleted = 0  and t.timeline_registration_source = 0 and t.isemrfinal = 1 and DATE_FORMAT(t.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() ";
//        emrfinalized_weekly = new BigInteger(objEntityManager.createNativeQuery(f2).getSingleResult().toString());
//        String f3 = "select count(t.timeline_id) from  temr_timeline t where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  and t.timeline_registration_source = 0 and t.isemrfinal = 1";
//        emrfinalized_monthly = new BigInteger(objEntityManager.createNativeQuery(f3).getSingleResult().toString());
//
//
//
//        String p1 = "select count(t.ip_id) from  temr_item_prescription t where t.is_active = 1 and t.is_active = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() ";
//        prescriptiongenerated_today =new BigInteger(objEntityManager.createNativeQuery(p1).getSingleResult().toString());
//        String p2 = "select count(t.ip_id) from  temr_item_prescription t where t.is_active = 1 and t.is_active = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ";
//        prescriptiongenerated_weekly = new BigInteger(objEntityManager.createNativeQuery(p2).getSingleResult().toString());
//        String p3 = "select count(t.ip_id) from  temr_item_prescription t where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) ";
//        prescriptiongenerated_monthly =new BigInteger(objEntityManager.createNativeQuery(p3).getSingleResult().toString());
//
//
//        String dos1 = "select count(t.vi_id) from  temr_visit_investigation t where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.vi_registration_source = 0";
//        doctororderservice_today =new BigInteger(objEntityManager.createNativeQuery(dos1).getSingleResult().toString());
//        String dos2 = "select count(t.vi_id) from  temr_visit_investigation t where t.is_active = 1 and t.is_deleted = 0   and t.vi_registration_source = 0 and DATE_FORMAT(t.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ";
//        doctororderservice_weekly =new BigInteger(objEntityManager.createNativeQuery(dos2).getSingleResult().toString());
//        String dos3 = "select count(t.vi_id) from  temr_visit_investigation t where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and t.vi_registration_source = 0";
//        doctororderservice_monthly =  new BigInteger(objEntityManager.createNativeQuery(dos3).getSingleResult().toString());
//
//
//        String do1 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.vi_registration_source = 0 ";
//        doctororder_today = new BigInteger(objEntityManager.createNativeQuery(do1).getSingleResult().toString());
//        String do2 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t where t.is_active = 1 and t.is_deleted = 0   and t.vi_registration_source = 0 and DATE_FORMAT(t.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now()   ";
//        doctororder_weekly =new BigInteger(objEntityManager.createNativeQuery(do2).getSingleResult().toString());
//        String do3 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and t.vi_registration_source = 0 ";
//        doctororder_monthly =new BigInteger(objEntityManager.createNativeQuery(do3).getSingleResult().toString());
//
//
//
//        respMap.put("emrtimelinegenerated_today", emrtimelinegenerated_today);
//        respMap.put("emrtimelinegenerated_weekly", emrtimelinegenerated_weekly);
//        respMap.put("emrtimelinegenerated_monthly", emrtimelinegenerated_monthly);
//
//        respMap.put("emrfinalized_today", emrfinalized_today);
//        respMap.put("emrfinalized_weekly", emrfinalized_weekly);
//        respMap.put("emrfinalized_monthly", emrfinalized_monthly);
//
//
//        respMap.put("prescriptiongenerated_today", prescriptiongenerated_today);
//        respMap.put("prescriptiongenerated_weekly", prescriptiongenerated_weekly);
//        respMap.put("prescriptiongenerated_monthly", prescriptiongenerated_monthly);
//
//        respMap.put("doctororderservice_today", doctororderservice_today);
//        respMap.put("doctororderservice_weekly", doctororderservice_weekly);
//        respMap.put("doctororderservice_monthly", doctororderservice_monthly);
//
//        respMap.put("doctororder_today", doctororder_today);
//        respMap.put("doctororder_weekly", doctororder_weekly);
//        respMap.put("doctororder_monthly", doctororder_monthly);
//
//
//        return respMap;
//    }

    @RequestMapping("getoverallbillingsummery/{unitid}/{tariff}/{type}/{value}/{unitlistid}")
    public Map<String, List> getoverallbillingsummery(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("tariff") Long tariff, @PathVariable("type") String type, @PathVariable("value") int value, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> overallbilldetails = new ArrayList<String>();
        LocalDate calculatedate = LocalDate.now();
        if (unitid != 0) {
            if (tariff != 0) {
                if (type.equals("today")) {
                    String query2 = "select count(tbb.billId) , SUM(COALESCE(billNetPayable,0.00)),  SUM(COALESCE(billAmountPaid,0.00)) ,  SUM(COALESCE(billOutstanding,0.00)),tbb.lastModifiedDate, SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  TBillBill tbb where tbb.isActive = 1 and tbb.isDeleted = 0  and tbb.isCancelled = false and tbb.tbillUnitId= " + unitid + " and  date(tbb.lastModifiedDate) = CURDATE()  and tbb.billTariffId.tariffId = " + tariff;
                    overallbilldetails = objEntityManager.createQuery(query2).getResultList();
                } else if (type.equals("day")) {
                    calculatedate = calculatedate.minusDays(value);
                    String query2 = "select count(tbb.billId) , SUM(COALESCE(billNetPayable,0.00)),  SUM(COALESCE(billAmountPaid,0.00)) ,  SUM(COALESCE(billOutstanding,0.00)),tbb.lastModifiedDate, SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  TBillBill tbb where tbb.isActive = 1 and tbb.isDeleted = 0  and tbb.isCancelled = false and tbb.tbillUnitId= " + unitid + " and  date(tbb.lastModifiedDate) between adddate(now(),- " + value + ") and now()  and tbb.billTariffId.tariffId = " + tariff + " group by tbb.lastModifiedDate";
                    overallbilldetails = objEntityManager.createQuery(query2).getResultList();
                } else if (type.equals("month")) {
                    value = value - 1;
                    String query2 = "select count(tbb.bill_id) , SUM(COALESCE(bill_net_payable,0.00)),  SUM(COALESCE(bill_amount_paid,0.00)) ,  SUM(COALESCE(bill_outstanding,0.00)), MONTH(tbb.last_modified_date), SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  tbill_bill tbb  INNER JOIN mbill_tariff mbt ON mbt.tariff_id  = tbb.bill_tariff_id where tbb.is_active = 1 and tbb.is_deleted = 0  and tbb.tbill_unit_id= " + unitid + " and tbb.is_cancelled = false and date(tbb.last_modified_date) >= DATE(NOW() - INTERVAL " + value + " MONTH)  and mbt.tariff_id = " + tariff + " group by  MONTH(tbb.last_modified_date) ";
                    overallbilldetails = objEntityManager.createNativeQuery(query2).getResultList();
                } else if (type.equals("year")) {
                    value = value - 1;
                    String query2 = "select count(tbb.bill_id) , SUM(COALESCE(bill_net_payable,0.00)),  SUM(COALESCE(bill_amount_paid,0.00)) ,  SUM(COALESCE(bill_outstanding,0.00)), YEAR(tbb.last_modified_date), SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  tbill_bill tbb  INNER JOIN mbill_tariff mbt ON mbt.tariff_id  = tbb.bill_tariff_id where tbb.is_active = 1 and tbb.is_deleted = 0 and tbb.tbill_unit_id= " + unitid + " and tbb.is_cancelled = false and date(tbb.last_modified_date) >= DATE(NOW() - INTERVAL " + value + " YEAR)  and mbt.tariff_id = " + tariff + " group by  YEAR(tbb.last_modified_date) ";
                    overallbilldetails = objEntityManager.createNativeQuery(query2).getResultList();
                }
            } else {
                if (type.equals("today")) {
                    String query2 = "select count(tbb.billId) , SUM(COALESCE(billNetPayable,0.00)),  SUM(COALESCE(billAmountPaid,0.00)) ,  SUM(COALESCE(billOutstanding,0.00)),tbb.lastModifiedDate, SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  TBillBill tbb where tbb.isActive = 1 and tbb.isDeleted = 0 and tbb.tbillUnitId= " + unitid + "  and tbb.isCancelled = false and date(tbb.lastModifiedDate)= CURDATE()  ";
                    overallbilldetails = objEntityManager.createQuery(query2).getResultList();
                } else if (type.equals("day")) {
                    calculatedate = calculatedate.minusDays(value);
                    String query2 = "select count(tbb.billId) , SUM(COALESCE(billNetPayable,0.00)),  SUM(COALESCE(billAmountPaid,0.00)) ,  SUM(COALESCE(billOutstanding,0.00)),tbb.lastModifiedDate, SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  TBillBill tbb where tbb.isActive = 1 and tbb.isDeleted = 0  and tbb.isCancelled = false and tbb.tbillUnitId= " + unitid + " and date(tbb.lastModifiedDate) between adddate(now(),- " + value + ") and now()  group by tbb.lastModifiedDate ";
                    overallbilldetails = objEntityManager.createQuery(query2).getResultList();
                } else if (type.equals("month")) {
                    value = value - 1;
                    String query2 = "select count(tbb.bill_id) , SUM(COALESCE(bill_net_payable,0.00)),  SUM(COALESCE(bill_amount_paid,0.00)) ,  SUM(COALESCE(bill_outstanding,0.00)), MONTH(tbb.last_modified_date), SUM(COALESCE(company_net_pay,0.00)), SUM(COALESCE(company_paid_amount,0.00)), SUM(COALESCE(bill_company_out_standing,0.00)) from  tbill_bill tbb where tbb.is_active = 1 and tbb.is_deleted = 0  and tbb.is_cancelled = false and tbb.tbill_unit_id= " + unitid + " and  date(tbb.last_modified_date) >= DATE(NOW() - INTERVAL " + value + " MONTH)  group by  MONTH(tbb.last_modified_date) ";
                    overallbilldetails = objEntityManager.createNativeQuery(query2).getResultList();
                } else if (type.equals("year")) {
                    value = value - 1;
                    String query2 = "select count(tbb.bill_id) , SUM(COALESCE(bill_net_payable,0.00)),  SUM(COALESCE(bill_amount_paid,0.00)) ,  SUM(COALESCE(bill_outstanding,0.00)), YEAR(tbb.last_modified_date), SUM(COALESCE(company_net_pay,0.00)), SUM(COALESCE(company_paid_amount,0.00)), SUM(COALESCE(bill_company_out_standing,0.00)) from  tbill_bill tbb where tbb.is_active = 1 and tbb.is_deleted = 0  and tbb.is_cancelled = false and tbb.tbill_unit_id= " + unitid + " and  date(tbb.last_modified_date) >= DATE(NOW() - INTERVAL " + value + " YEAR)  group by  YEAR(tbb.last_modified_date) ";
                    overallbilldetails = objEntityManager.createNativeQuery(query2).getResultList();
                }

            }

        } else {
            if (tariff != 0) {
                if (type.equals("today")) {
                    String query2 = "select count(tbb.billId) , SUM(COALESCE(billNetPayable,0.00)),  SUM(COALESCE(billAmountPaid,0.00)) ,  SUM(COALESCE(billOutstanding,0.00)),tbb.lastModifiedDate, SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  TBillBill tbb where tbb.isActive = 1 and tbb.isDeleted = 0  and tbb.isCancelled = false and tbb.tbillUnitId in (" + removeLastChar(unitlistid) + ") and  date(tbb.lastModifiedDate) = CURDATE()  and tbb.billTariffId.tariffId = " + tariff;
                    overallbilldetails = objEntityManager.createQuery(query2).getResultList();
                } else if (type.equals("day")) {
                    calculatedate = calculatedate.minusDays(value);
                    String query2 = "select count(tbb.billId) , SUM(COALESCE(billNetPayable,0.00)),  SUM(COALESCE(billAmountPaid,0.00)) ,  SUM(COALESCE(billOutstanding,0.00)),tbb.lastModifiedDate, SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  TBillBill tbb where tbb.isActive = 1 and tbb.isDeleted = 0  and tbb.isCancelled = false and tbb.tbillUnitId in (" + removeLastChar(unitlistid) + ") and  date(tbb.lastModifiedDate) between adddate(now(),- " + value + ") and now()  and tbb.billTariffId.tariffId = " + tariff + " group by tbb.lastModifiedDate";
                    overallbilldetails = objEntityManager.createQuery(query2).getResultList();
                } else if (type.equals("month")) {
                    value = value - 1;
                    String query2 = "select count(tbb.bill_id) , SUM(COALESCE(bill_net_payable,0.00)),  SUM(COALESCE(bill_amount_paid,0.00)) ,  SUM(COALESCE(bill_outstanding,0.00)), MONTH(tbb.last_modified_date), SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  tbill_bill tbb  INNER JOIN mbill_tariff mbt ON mbt.tariff_id  = tbb.bill_tariff_id where tbb.is_active = 1 and tbb.is_deleted = 0  and tbb.tbill_unit_id in (" + removeLastChar(unitlistid) + ") and tbb.is_cancelled = false and  date(tbb.last_modified_date) >= DATE(NOW() - INTERVAL " + value + " MONTH)  and mbt.tariff_id = " + tariff + " group by  MONTH(tbb.last_modified_date) ";
                    overallbilldetails = objEntityManager.createNativeQuery(query2).getResultList();
                } else if (type.equals("year")) {
                    value = value - 1;
                    String query2 = "select count(tbb.bill_id) , SUM(COALESCE(bill_net_payable,0.00)),  SUM(COALESCE(bill_amount_paid,0.00)) ,  SUM(COALESCE(bill_outstanding,0.00)), YEAR(tbb.last_modified_date), SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  tbill_bill tbb  INNER JOIN mbill_tariff mbt ON mbt.tariff_id  = tbb.bill_tariff_id where tbb.is_active = 1 and tbb.is_deleted = 0 and tbb.tbill_unit_id in (" + removeLastChar(unitlistid) + ") and tbb.is_cancelled = false and  date(tbb.last_modified_date) >= DATE(NOW() - INTERVAL " + value + " YEAR)  and mbt.tariff_id = " + tariff + " group by  YEAR(tbb.last_modified_date) ";
                    overallbilldetails = objEntityManager.createNativeQuery(query2).getResultList();
                }
            } else {
                if (type.equals("today")) {
                    String query2 = "select count(tbb.billId) , SUM(COALESCE(billNetPayable,0.00)),  SUM(COALESCE(billAmountPaid,0.00)) ,  SUM(COALESCE(billOutstanding,0.00)),tbb.lastModifiedDate, SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  TBillBill tbb where tbb.isActive = 1 and tbb.isDeleted = 0 and tbb.tbillUnitId in (" + removeLastChar(unitlistid) + ") and tbb.isCancelled = false and  date(tbb.lastModifiedDate) = CURDATE()  ";
                    overallbilldetails = objEntityManager.createQuery(query2).getResultList();
                } else if (type.equals("day")) {
                    calculatedate = calculatedate.minusDays(value);
                    String query2 = "select count(tbb.billId) , SUM(COALESCE(billNetPayable,0.00)),  SUM(COALESCE(billAmountPaid,0.00)) ,  SUM(COALESCE(billOutstanding,0.00)),tbb.lastModifiedDate, SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  TBillBill tbb where tbb.isActive = 1 and tbb.isDeleted = 0  and tbb.isCancelled = false and tbb.tbillUnitId in (" + removeLastChar(unitlistid) + ") and  date(tbb.lastModifiedDate) between adddate(now(),- " + value + ") and now()  group by tbb.lastModifiedDate ";
                    overallbilldetails = objEntityManager.createQuery(query2).getResultList();
                } else if (type.equals("month")) {
                    value = value - 1;
                    String query2 = "select count(tbb.bill_id) , SUM(COALESCE(bill_net_payable,0.00)),  SUM(COALESCE(bill_amount_paid,0.00)) ,  SUM(COALESCE(bill_outstanding,0.00)), MONTH(tbb.last_modified_date),SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  tbill_bill tbb where tbb.is_active = 1 and tbb.is_deleted = 0  and tbb.is_cancelled = false and tbb.tbill_unit_id in (" + removeLastChar(unitlistid) + ") and  date(tbb.last_modified_date) >= DATE(NOW() - INTERVAL " + value + " MONTH)  group by  MONTH(tbb.last_modified_date) ";
                    overallbilldetails = objEntityManager.createNativeQuery(query2).getResultList();
                } else if (type.equals("year")) {
                    value = value - 1;
                    String query2 = "select count(tbb.bill_id) , SUM(COALESCE(bill_net_payable,0.00)),  SUM(COALESCE(bill_amount_paid,0.00)) ,  SUM(COALESCE(bill_outstanding,0.00)), YEAR(tbb.last_modified_date),SUM(COALESCE(companyNetPay,0.00)), SUM(COALESCE(companyPaidAmount,0.00)), SUM(COALESCE(billCompanyOutStanding,0.00)) from  tbill_bill tbb where tbb.is_active = 1 and tbb.is_deleted = 0  and tbb.is_cancelled = false and tbb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")and  date(tbb.last_modified_date) >= DATE(NOW() - INTERVAL " + value + " YEAR)  group by  YEAR(tbb.last_modified_date) ";
                    overallbilldetails = objEntityManager.createNativeQuery(query2).getResultList();
                }

            }

        }
        respMap.put("overallbilldetails", overallbilldetails);
        return respMap;
    }
//    @RequestMapping("getemrdetails/{unitid}")
//    public Map<String,BigInteger>  getemrdetails(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid) throws JSONException {
//        // System.out.println("---object------");
//        Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();
//
//        BigInteger emrtimelinegenerated_today,emrtimelinegenerated_weekly,emrtimelinegenerated_monthly,emrfinalized_today,emrfinalized_weekly,emrfinalized_monthly,doctororder_today,doctororder_weekly,doctororder_monthly,doctororderservice_today,doctororderservice_weekly,doctororderservice_monthly,prescriptiongenerated_today,prescriptiongenerated_weekly,prescriptiongenerated_monthly;
//
//
//        String e1 = "select count(t.timelineId) from  TemrTimeline t where t.isActive = 1 and t.isDeleted = 0  and DATE_FORMAT(t.createdDate,'%y-%m-%d')  = CURDATE() and t.timelineVisitId.visitUnitId = "+unitid+" and t.timelineRegistrationSource = 0 ";
//        emrtimelinegenerated_today= new BigInteger(objEntityManager.createQuery(e1).getSingleResult().toString());
//
//        String e2 = "select count(t.timelineId) from  TemrTimeline t where t.isActive = 1 and t.isDeleted = 0  and t.timelineRegistrationSource = 0 and t.timelineVisitId.visitUnitId = "+unitid+" and DATE_FORMAT(t.createdDate,'%y-%m-%d') between adddate(now(),- 7) and now()  ";
//        emrtimelinegenerated_weekly = new BigInteger(objEntityManager.createQuery(e2).getSingleResult().toString());
//        String e3 = "select count(t.timelineId) from  TemrTimeline t where t.isActive = 1 and t.isDeleted = 0  and t.timelineVisitId.visitUnitId = "+unitid+" and DATE_FORMAT(t.createdDate,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  and t.timelineRegistrationSource = 0 ";
//        emrtimelinegenerated_monthly =new BigInteger(objEntityManager.createQuery(e3).getSingleResult().toString());
//
//
//        String f1 = "select count(t.timelineId) from  TemrTimeline t where t.isActive = 1 and t.isDeleted = 0 and t.timelineVisitId.visitUnitId = "+unitid+" and DATE_FORMAT(t.createdDate,'%y-%m-%d')  = CURDATE() and t.timelineRegistrationSource = 0  and t.isemrfinal = 1";
//        emrfinalized_today =new BigInteger(objEntityManager.createQuery(f1).getSingleResult().toString());
//        String f2 = "select count(t.timelineId) from  TemrTimeline t where t.isActive = 1 and t.isDeleted = 0 and t.timelineVisitId.visitUnitId = "+unitid+"  and t.timelineRegistrationSource = 0 and t.isemrfinal = 1 and DATE_FORMAT(t.createdDate,'%y-%m-%d') between adddate(now(),- 7) and now() ";
//        emrfinalized_weekly = new BigInteger(objEntityManager.createQuery(f2).getSingleResult().toString());
//        String f3 = "select count(t.timelineId) from  TemrTimeline t where t.isActive = 1 and t.isDeleted = 0  and t.timelineVisitId.visitUnitId = "+unitid+" and DATE_FORMAT(t.createdDate,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  and t.timelineRegistrationSource = 0 and t.isemrfinal = 1";
//        emrfinalized_monthly = new BigInteger(objEntityManager.createQuery(f3).getSingleResult().toString());
//
//
//        String p1 = "select count(t.ipId) from  temrItemPrescription t where t.isActive = 1 and t.isDeleted = 0  and DATE_FORMAT(t.createdDate,'%y-%m-%d')  = CURDATE() and t.ipInvItemIditemUnitId ="+unitid;
//        prescriptiongenerated_today =new BigInteger(objEntityManager.createQuery(p1).getSingleResult().toString());
//        String p2 = "select count(t.ipId) from  temrItemPrescription t where t.isActive = 1 and t.isDeleted = 0   and t.ipInvItemIditemUnitId = "+unitid+" and DATE_FORMAT(t.createdDate,'%y-%m-%d')  between adddate(now(),- 7) and now() ";
//        prescriptiongenerated_weekly = new BigInteger(objEntityManager.createQuery(p2).getSingleResult().toString());
//        String p3 = "select count(t.ipId) from  temrItemPrescription t where t.isActive = 1 and t.isDeleted = 0  and t.ipInvItemIditemUnitId = "+unitid+"  and DATE_FORMAT(t.createdDate,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) ";
//        prescriptiongenerated_monthly =new BigInteger(objEntityManager.createQuery(p3).getSingleResult().toString());
//
//
//        String dos1 = "select count(t.viId) from  temrVisitInvestigation t where t.isActive = 1 and t.isDeleted = 0  and DATE_FORMAT(t.createdDate,'%y-%m-%d')  = CURDATE() and viVisitId.visitUnitId = "+unitid+" and t.vi_registration_source = 0";
//        doctororderservice_today =new BigInteger(objEntityManager.createQuery(dos1).getSingleResult().toString());
//        String dos2 = "select count(t.viId) from  temrVisitInvestigation t where t.isActive = 1 and t.isDeleted = 0   and t.vi_registration_source = 0  and viVisitId.visitUnitId = "+unitid+" and DATE_FORMAT(t.createdDate,'%y-%m-%d')  between adddate(now(),- 7) and now() ";
//        doctororderservice_weekly =new BigInteger(objEntityManager.createQuery(dos2).getSingleResult().toString());
//        String dos3 = "select count(t.viId) from  temrVisitInvestigation t where t.isActive = 1 and t.isDeleted = 0   and viVisitId.visitUnitId = "+unitid+" and DATE_FORMAT(t.createdDate,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and t.vi_registration_source = 0";
//        doctororderservice_monthly =  new BigInteger(objEntityManager.createQuery(dos3).getSingleResult().toString());
//
//
//        String do1 = "select COUNT(Distinct(t.viVisitId))  from  temrVisitInvestigation t where t.isActive = 1 and t.isDeleted = 0  and viVisitId.visitUnitId = "+unitid+" and DATE_FORMAT(t.createdDate,'%y-%m-%d')  = CURDATE() and t.vi_registration_source = 0 ";
//        doctororder_today = new BigInteger(objEntityManager.createQuery(do1).getSingleResult().toString());
//        String do2 = "select COUNT(Distinct(t.viVisitId))  from  temrVisitInvestigation t where t.isActive = 1 and t.isDeleted = 0  and viVisitId.visitUnitId = "+unitid+"  and t.vi_registration_source = 0 and DATE_FORMAT(t.createdDate,'%y-%m-%d')  between adddate(now(),- 7) and now()   ";
//        doctororder_weekly =new BigInteger(objEntityManager.createQuery(do2).getSingleResult().toString());
//        String do3 = "select COUNT(Distinct(t.viVisitId))  from  temrVisitInvestigation t where t.isActive = 1 and t.isDeleted = 0  and viVisitId.visitUnitId = "+unitid+" and DATE_FORMAT(t.createdDate,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and t.vi_registration_source = 0 ";
//        doctororder_monthly =new BigInteger(objEntityManager.createQuery(do3).getSingleResult().toString());
//
//
//
//        respMap.put("emrtimelinegenerated_today", emrtimelinegenerated_today);
//        respMap.put("emrtimelinegenerated_weekly", emrtimelinegenerated_weekly);
//        respMap.put("emrtimelinegenerated_monthly", emrtimelinegenerated_monthly);
//
//        respMap.put("emrfinalized_today", emrfinalized_today);
//        respMap.put("emrfinalized_weekly", emrfinalized_weekly);
//        respMap.put("emrfinalized_monthly", emrfinalized_monthly);
//
//
//        respMap.put("prescriptiongenerated_today", prescriptiongenerated_today);
//        respMap.put("prescriptiongenerated_weekly", prescriptiongenerated_weekly);
//        respMap.put("prescriptiongenerated_monthly", prescriptiongenerated_monthly);
//
//        respMap.put("doctororderservice_today", doctororderservice_today);
//        respMap.put("doctororderservice_weekly", doctororderservice_weekly);
//        respMap.put("doctororderservice_monthly", doctororderservice_monthly);
//
//        respMap.put("doctororder_today", doctororder_today);
//        respMap.put("doctororder_weekly", doctororder_weekly);
//        respMap.put("doctororder_monthly", doctororder_monthly);
//
//
//        return respMap;
//    }

    @RequestMapping("getemrdetails/{unitid}/{unitlistid}")
    public Map<String, BigInteger> getemrdetails(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();
        Map<String, String> respMap1 = new HashMap<String, String>();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        String FirstDayOfMonth;
        String LastDayOfMonth;
        cal.setTime(date);
//        int month = cal.get(Calendar.MONTH);
//        int year = cal.get(Calendar.YEAR);
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        int numOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        FirstDayOfMonth = year + "-" + month + "-" + "1";
        LastDayOfMonth = year + "-" + month + "-" + numOfDaysInMonth;
        System.out.println("--------LastDayOfMonth ---------- " + LastDayOfMonth);
        System.out.println("---------FirstDayOfMonth -------- " + FirstDayOfMonth);
        DateDto dateDto = new DateDto();
        dateDto = getStartAndEndDateOfWeek(tenantName);
        System.out.println("--------respMap1 ---------- " + dateDto.getStartDateOfWeek());
        System.out.println("--------respMap1 ---------- " + dateDto.getEndDateOfWeek());
        BigInteger emrtimelinegenerated_today, emrtimelinegenerated_weekly, emrtimelinegenerated_monthly, emrfinalized_today, emrfinalized_weekly, emrfinalized_monthly, doctororder_today, doctororder_weekly, doctororder_monthly, doctororderservice_today, doctororderservice_weekly, doctororderservice_monthly, prescriptiongenerated_today, prescriptiongenerated_weekly, prescriptiongenerated_monthly, emrtimelinegenerated_latest_week, prescriptiongenerated_latest_week, emrfinalized_latest_week, doctororder_latest_week, doctororderservice_latest_week, emrtimelinegenerated_last_30_days, prescriptiongenerated_last_30_days, emrfinalized_last_30_days, doctororder_last_30_days, doctororderservice_last_30_days;
        if (unitid != 0) {
            String e1 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id  where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.timeline_registration_source = 0  and m.visit_unit_id = " + unitid;
            emrtimelinegenerated_today = new BigInteger(objEntityManager.createNativeQuery(e1).getSingleResult().toString());
            String e2 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and t.timeline_registration_source = 0  and m.visit_unit_id =  " + unitid + " and DATE_FORMAT(t.created_date,'%y-%m-%d') between adddate(now(),- 7) and now()  ";
            emrtimelinegenerated_weekly = new BigInteger(objEntityManager.createNativeQuery(e2).getSingleResult().toString());
            String e3 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + " and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  and t.timeline_registration_source = 0 ";
            emrtimelinegenerated_monthly = new BigInteger(objEntityManager.createNativeQuery(e3).getSingleResult().toString());
            String e4 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + " and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  and t.timeline_registration_source = 0 ";
            emrtimelinegenerated_last_30_days = new BigInteger(objEntityManager.createNativeQuery(e4).getSingleResult().toString());
            String e5 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + " and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  and t.timeline_registration_source = 0 ";
            emrtimelinegenerated_latest_week = new BigInteger(objEntityManager.createNativeQuery(e5).getSingleResult().toString());
            String f1 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0 and m.visit_unit_id =  " + unitid + "  and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.timeline_registration_source = 0  and t.isemrfinal = 1";
            emrfinalized_today = new BigInteger(objEntityManager.createNativeQuery(f1).getSingleResult().toString());
            String f2 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + " and t.timeline_registration_source = 0 and t.isemrfinal = 1 and DATE_FORMAT(t.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() ";
            emrfinalized_weekly = new BigInteger(objEntityManager.createNativeQuery(f2).getSingleResult().toString());
            String f3 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + " and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')   and t.timeline_registration_source = 0 and t.isemrfinal = 1";
            emrfinalized_monthly = new BigInteger(objEntityManager.createNativeQuery(f3).getSingleResult().toString());
            String f4 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + " and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  and t.timeline_registration_source = 0 and t.isemrfinal = 1";
            emrfinalized_last_30_days = new BigInteger(objEntityManager.createNativeQuery(f4).getSingleResult().toString());
            String f5 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + " and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')   and t.timeline_registration_source = 0 and t.isemrfinal = 1";
            emrfinalized_latest_week = new BigInteger(objEntityManager.createNativeQuery(f5).getSingleResult().toString());
            String p1 = "select count(t.ip_id) from  temr_item_prescription t INNER JOIN inv_item i  ON i.item_id = t.ip_inv_item_id where t.is_active = 1 and t.is_active = 0 and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() ";
            prescriptiongenerated_today = new BigInteger(objEntityManager.createNativeQuery(p1).getSingleResult().toString());
            String p2 = "select count(t.ip_id) from  temr_item_prescription t INNER JOIN inv_item i  ON i.item_id = t.ip_inv_item_id where t.is_active = 1 and t.is_active = 0 and DATE_FORMAT(t.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ";
            prescriptiongenerated_weekly = new BigInteger(objEntityManager.createNativeQuery(p2).getSingleResult().toString());
            String p3 = "select count(t.ip_id) from  temr_item_prescription t INNER JOIN inv_item i  ON i.item_id = t.ip_inv_item_id where t.is_active = 1 and t.is_deleted = 0  and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') ";
            prescriptiongenerated_monthly = new BigInteger(objEntityManager.createNativeQuery(p3).getSingleResult().toString());
            String p4 = "select count(t.ip_id) from  temr_item_prescription t INNER JOIN inv_item i  ON i.item_id = t.ip_inv_item_id where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) ";
            prescriptiongenerated_last_30_days = new BigInteger(objEntityManager.createNativeQuery(p4).getSingleResult().toString());
            String p5 = "select count(t.ip_id) from  temr_item_prescription t INNER JOIN inv_item i  ON i.item_id = t.ip_inv_item_id where t.is_active = 1 and t.is_deleted = 0  and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') ";
            prescriptiongenerated_latest_week = new BigInteger(objEntityManager.createNativeQuery(p5).getSingleResult().toString());
            String dos1 = "select count(t.vi_id) from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + " and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.vi_registration_source = 0";
            doctororderservice_today = new BigInteger(objEntityManager.createNativeQuery(dos1).getSingleResult().toString());
            String dos2 = "select count(t.vi_id) from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0   and m.visit_unit_id =  " + unitid + "  and t.vi_registration_source = 0 and DATE_FORMAT(t.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ";
            doctororderservice_weekly = new BigInteger(objEntityManager.createNativeQuery(dos2).getSingleResult().toString());
            String dos3 = "select count(t.vi_id) from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + "  and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  and t.vi_registration_source = 0";
            doctororderservice_monthly = new BigInteger(objEntityManager.createNativeQuery(dos3).getSingleResult().toString());
            String dos4 = "select count(t.vi_id) from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + "  and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and t.vi_registration_source = 0";
            doctororderservice_last_30_days = new BigInteger(objEntityManager.createNativeQuery(dos4).getSingleResult().toString());
            String dos5 = "select count(t.vi_id) from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id =  " + unitid + "  and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  and t.vi_registration_source = 0";
            doctororderservice_latest_week = new BigInteger(objEntityManager.createNativeQuery(dos5).getSingleResult().toString());
            String do1 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0   and m.visit_unit_id =  " + unitid + "  and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.vi_registration_source = 0 ";
            doctororder_today = new BigInteger(objEntityManager.createNativeQuery(do1).getSingleResult().toString());
            String do2 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0    and m.visit_unit_id =  " + unitid + "  and t.vi_registration_source = 0 and DATE_FORMAT(t.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now()   ";
            doctororder_weekly = new BigInteger(objEntityManager.createNativeQuery(do2).getSingleResult().toString());
            String do3 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id   where t.is_active = 1 and t.is_deleted = 0   and m.visit_unit_id =  " + unitid + "  and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  and t.vi_registration_source = 0 ";
            doctororder_monthly = new BigInteger(objEntityManager.createNativeQuery(do3).getSingleResult().toString());
            String do4 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id   where t.is_active = 1 and t.is_deleted = 0   and m.visit_unit_id =  " + unitid + "  and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and t.vi_registration_source = 0 ";
            doctororder_last_30_days = new BigInteger(objEntityManager.createNativeQuery(do4).getSingleResult().toString());
            String do5 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id   where t.is_active = 1 and t.is_deleted = 0   and m.visit_unit_id =  " + unitid + "  and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  and t.vi_registration_source = 0 ";
            doctororder_latest_week = new BigInteger(objEntityManager.createNativeQuery(do5).getSingleResult().toString());

        } else {
            String e1 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id  where t.is_active = 1 and t.is_deleted = 0  and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.timeline_registration_source = 0  and m.visit_unit_id in (\"+removeLastChar(unitlistid)+\") ";
            emrtimelinegenerated_today = new BigInteger(objEntityManager.createNativeQuery(e1).getSingleResult().toString());
            String e2 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and t.timeline_registration_source = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(t.created_date,'%y-%m-%d') between adddate(now(),- 7) and now()  ";
            emrtimelinegenerated_weekly = new BigInteger(objEntityManager.createNativeQuery(e2).getSingleResult().toString());
            String e3 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')   and t.timeline_registration_source = 0 ";
            emrtimelinegenerated_monthly = new BigInteger(objEntityManager.createNativeQuery(e3).getSingleResult().toString());
            String e4 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  and t.timeline_registration_source = 0 ";
            emrtimelinegenerated_last_30_days = new BigInteger(objEntityManager.createNativeQuery(e4).getSingleResult().toString());
            String e5 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')   and t.timeline_registration_source = 0 ";
            emrtimelinegenerated_latest_week = new BigInteger(objEntityManager.createNativeQuery(e5).getSingleResult().toString());
            String f1 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0 and m.visit_unit_id  in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.timeline_registration_source = 0  and t.isemrfinal = 1";
            emrfinalized_today = new BigInteger(objEntityManager.createNativeQuery(f1).getSingleResult().toString());
            String f2 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and t.timeline_registration_source = 0 and t.isemrfinal = 1 and DATE_FORMAT(t.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() ";
            emrfinalized_weekly = new BigInteger(objEntityManager.createNativeQuery(f2).getSingleResult().toString());
            String f3 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')   and t.timeline_registration_source = 0 and t.isemrfinal = 1";
            emrfinalized_monthly = new BigInteger(objEntityManager.createNativeQuery(f3).getSingleResult().toString());
            String f4 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  and t.timeline_registration_source = 0 and t.isemrfinal = 1";
            emrfinalized_last_30_days = new BigInteger(objEntityManager.createNativeQuery(f4).getSingleResult().toString());
            String f5 = "select count(t.timeline_id) from  temr_timeline t  INNER JOIN mst_visit m ON m.visit_id = t.timeline_visit_id where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')   and t.timeline_registration_source = 0 and t.isemrfinal = 1";
            emrfinalized_latest_week = new BigInteger(objEntityManager.createNativeQuery(f5).getSingleResult().toString());
            String p1 = "select count(t.ip_id) from  temr_item_prescription t INNER JOIN inv_item i  ON i.item_id = t.ip_inv_item_id where t.is_active = 1 and t.is_active = 0  and   i.item_unit_id in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() ";
            prescriptiongenerated_today = new BigInteger(objEntityManager.createNativeQuery(p1).getSingleResult().toString());
            String p2 = "select count(t.ip_id) from  temr_item_prescription t INNER JOIN inv_item i  ON i.item_id = t.ip_inv_item_id where t.is_active = 1 and t.is_active = 0  and   i.item_unit_id in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(t.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ";
            prescriptiongenerated_weekly = new BigInteger(objEntityManager.createNativeQuery(p2).getSingleResult().toString());
            String p3 = "select count(t.ip_id) from  temr_item_prescription t INNER JOIN inv_item i  ON i.item_id = t.ip_inv_item_id where t.is_active = 1 and t.is_deleted = 0 and   i.item_unit_id in (" + removeLastChar(unitlistid) + ")  and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') ";
            prescriptiongenerated_monthly = new BigInteger(objEntityManager.createNativeQuery(p3).getSingleResult().toString());
            String p4 = "select count(t.ip_id) from  temr_item_prescription t INNER JOIN inv_item i  ON i.item_id = t.ip_inv_item_id where t.is_active = 1 and t.is_deleted = 0 and   i.item_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) ";
            prescriptiongenerated_last_30_days = new BigInteger(objEntityManager.createNativeQuery(p4).getSingleResult().toString());
            String p5 = "select count(t.ip_id) from  temr_item_prescription t INNER JOIN inv_item i  ON i.item_id = t.ip_inv_item_id where t.is_active = 1 and t.is_deleted = 0 and   i.item_unit_id in (" + removeLastChar(unitlistid) + ")  and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') ";
            prescriptiongenerated_latest_week = new BigInteger(objEntityManager.createNativeQuery(p5).getSingleResult().toString());
            String dos1 = "select count(t.vi_id) from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.vi_registration_source = 0";
            doctororderservice_today = new BigInteger(objEntityManager.createNativeQuery(dos1).getSingleResult().toString());
            String dos2 = "select count(t.vi_id) from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0   and m.visit_unit_id in (" + removeLastChar(unitlistid) + ")  and t.vi_registration_source = 0 and DATE_FORMAT(t.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ";
            doctororderservice_weekly = new BigInteger(objEntityManager.createNativeQuery(dos2).getSingleResult().toString());
            String dos3 = "select count(t.vi_id) from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ")  and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  and t.vi_registration_source = 0";
            doctororderservice_monthly = new BigInteger(objEntityManager.createNativeQuery(dos3).getSingleResult().toString());
            String dos4 = "select count(t.vi_id) from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and t.vi_registration_source = 0";
            doctororderservice_last_30_days = new BigInteger(objEntityManager.createNativeQuery(dos4).getSingleResult().toString());
            String dos5 = "select count(t.vi_id) from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0  and m.visit_unit_id in (" + removeLastChar(unitlistid) + ")  and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  and t.vi_registration_source = 0";
            doctororderservice_latest_week = new BigInteger(objEntityManager.createNativeQuery(dos3).getSingleResult().toString());
            String do1 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0   and m.visit_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(t.created_date,'%y-%m-%d')  = CURDATE() and t.vi_registration_source = 0 ";
            doctororder_today = new BigInteger(objEntityManager.createNativeQuery(do1).getSingleResult().toString());
            String do2 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id  where t.is_active = 1 and t.is_deleted = 0    and m.visit_unit_id in (" + removeLastChar(unitlistid) + ")  and t.vi_registration_source = 0 and DATE_FORMAT(t.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now()   ";
            doctororder_weekly = new BigInteger(objEntityManager.createNativeQuery(do2).getSingleResult().toString());
            String do3 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id   where t.is_active = 1 and t.is_deleted = 0   and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  and t.vi_registration_source = 0 ";
            doctororder_monthly = new BigInteger(objEntityManager.createNativeQuery(do3).getSingleResult().toString());
            String do4 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id   where t.is_active = 1 and t.is_deleted = 0   and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and t.vi_registration_source = 0 ";
            doctororder_last_30_days = new BigInteger(objEntityManager.createNativeQuery(do4).getSingleResult().toString());
            String do5 = "select COUNT(Distinct(t.vi_visit_id))  from  temr_visit_investigation t INNER JOIN mst_visit m  ON m.visit_id = t.vi_visit_id   where t.is_active = 1 and t.is_deleted = 0   and m.visit_unit_id in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  and t.vi_registration_source = 0 ";
            doctororder_latest_week = new BigInteger(objEntityManager.createNativeQuery(do5).getSingleResult().toString());

        }
        respMap.put("emrtimelinegenerated_today", emrtimelinegenerated_today);
        respMap.put("emrtimelinegenerated_weekly", emrtimelinegenerated_weekly);
        respMap.put("emrtimelinegenerated_monthly", emrtimelinegenerated_monthly);
        respMap.put("emrtimelinegenerated_last_30_days", emrtimelinegenerated_last_30_days);
        respMap.put("emrtimelinegenerated_latest_week", emrtimelinegenerated_latest_week);
        respMap.put("emrfinalized_today", emrfinalized_today);
        respMap.put("emrfinalized_weekly", emrfinalized_weekly);
        respMap.put("emrfinalized_monthly", emrfinalized_monthly);
        respMap.put("emrfinalized_last_30_days", emrfinalized_last_30_days);
        respMap.put("emrfinalized_latest_week", emrfinalized_latest_week);
        respMap.put("prescriptiongenerated_today", prescriptiongenerated_today);
        respMap.put("prescriptiongenerated_weekly", prescriptiongenerated_weekly);
        respMap.put("prescriptiongenerated_monthly", prescriptiongenerated_monthly);
        respMap.put("prescriptiongenerated_last_30_days", prescriptiongenerated_last_30_days);
        respMap.put("prescriptiongenerated_latest_week", prescriptiongenerated_latest_week);
        respMap.put("doctororderservice_today", doctororderservice_today);
        respMap.put("doctororderservice_weekly", doctororderservice_weekly);
        respMap.put("doctororderservice_monthly", doctororderservice_monthly);
        respMap.put("doctororderservice_last_30_days", doctororderservice_last_30_days);
        respMap.put("doctororderservice_latest_week", doctororderservice_latest_week);
        respMap.put("doctororder_today", doctororder_today);
        respMap.put("doctororder_weekly", doctororder_weekly);
        respMap.put("doctororder_monthly", doctororder_monthly);
        respMap.put("doctororder_last_30_days", doctororder_last_30_days);
        respMap.put("doctororder_latest_week", doctororder_latest_week);
        return respMap;
    }

    @RequestMapping("getPathologyDetails/{unitid}/{unitlistid}")
    public Map<String, List> getPathologyDetails(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        String FirstDayOfMonth;
        String LastDayOfMonth;
        cal.setTime(date);
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        int numOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        FirstDayOfMonth = year + "-" + month + "-" + "1";
        LastDayOfMonth = year + "-" + month + "-" + numOfDaysInMonth;
        System.out.println("--------LastDayOfMonth ---------- " + LastDayOfMonth);
        System.out.println("---------FirstDayOfMonth -------- " + FirstDayOfMonth);
        DateDto dateDto = new DateDto();
        dateDto = getStartAndEndDateOfWeek(tenantName);
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> pordergenerated = new ArrayList<String>();
        List<String> preportfinalized = new ArrayList<String>();
        List<String> ppendingreport = new ArrayList<String>();
        List<String> ptestcount = new ArrayList<String>();
        if (unitid != 0) {
            String p1 = "select  (select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.tbill_unit_id = " + unitid + " and DATE_FORMAT(tp.created_date,'%y-%m-%d')  = CURDATE() and tb.ipd_bill = 0 ) as pordergenerated_today," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id = " + unitid + "  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as pordergenerated_weekly, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id = " + unitid + "  and  DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') )as pordergenerated_latest_week, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id = " + unitid + "  and  DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') )as pordergenerated_monthly, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id = " + unitid + "  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  )as pordergenerated_last_30_days ";
            ptestcount = objEntityManager.createNativeQuery(p1).getResultList();
            String p2 = "select  (select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0   and tb.tbill_unit_id = " + unitid + "  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  = CURDATE() and tb.ipd_bill = 0  and tp.is_finalized = 1) as preportfinalized_today," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id = " + unitid + "  and tp.is_finalized = 1 and DATE_FORMAT(tp.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as preportfinalized_weekly, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id = " + unitid + "  and tp.is_finalized = 1 and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') )as preportfinalized_latest_week, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id = " + unitid + "  and tp.is_finalized = 1 and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') )as preportfinalized_monthly, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id = " + unitid + "  and tp.is_finalized = 1 and DATE_FORMAT(tp.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  )as preportfinalized_last_30_days ";
            preportfinalized = objEntityManager.createNativeQuery(p2).getResultList();
            String p3 = "select  (select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0  and tb.tbill_unit_id = " + unitid + "   and DATE_FORMAT(tp.created_date,'%y-%m-%d')  = CURDATE() and tb.ipd_bill = 0  and tp.is_finalized = 0) as ppendingreport_today," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id = " + unitid + "  and tp.is_finalized = 0 and DATE_FORMAT(tp.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as ppendingreport_weekly," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id = " + unitid + "  and tp.is_finalized = 0 and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  )as ppendingreport_latest_week, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id = " + unitid + "  and tp.is_finalized = 0 and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  )as ppendingreport_monthly, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id = " + unitid + "  and tp.is_finalized = 0 and DATE_FORMAT(tp.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  )as ppendingreport_last_30_days";
            ppendingreport = objEntityManager.createNativeQuery(p3).getResultList();
            String p4 = "select  (select COUNT(Distinct(tp.ps_bill_id)) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0   and tb.tbill_unit_id = " + unitid + "  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  = CURDATE() and tb.ipd_bill = 0 ) as pordergenerated_today," + "(select COUNT(Distinct(tp.ps_bill_id)) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id = " + unitid + "  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as pordergenerated_weekly, " + "(select COUNT(Distinct(tp.ps_bill_id)) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id = " + unitid + "  and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  )as pordergenerated_latest_week ," + "(select COUNT(Distinct(tp.ps_bill_id)) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id = " + unitid + "  and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  )as pordergenerated_monthly ," + "(select COUNT(Distinct(tp.ps_bill_id)) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id = " + unitid + "  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  )as pordergenerated_last_30_days ";
            pordergenerated = objEntityManager.createNativeQuery(p4).getResultList();

        } else {
            String p1 = "select  (select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(tp.created_date,'%y-%m-%d')  = CURDATE() and tb.ipd_bill = 0 ) as pordergenerated_today," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(tp.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as pordergenerated_weekly, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  )as pordergenerated_latest_week ," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  )as pordergenerated_monthly ," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  )as pordergenerated_last_30_days ";
            ptestcount = objEntityManager.createNativeQuery(p1).getResultList();
            String p2 = "select  (select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0   and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  = CURDATE() and tb.ipd_bill = 0  and tp.is_finalized = 1) as preportfinalized_today," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and tp.is_finalized = 1 and DATE_FORMAT(tp.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as preportfinalized_weekly, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and tp.is_finalized = 1 and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  )as preportfinalized_latest_week, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and tp.is_finalized = 1 and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  )as preportfinalized_monthly, " + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and tp.is_finalized = 1 and DATE_FORMAT(tp.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  )as preportfinalized_last_30_days ";
            preportfinalized = objEntityManager.createNativeQuery(p2).getResultList();
            String p3 = "select  (select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0  and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")   and DATE_FORMAT(tp.created_date,'%y-%m-%d')  = CURDATE() and tb.ipd_bill = 0  and tp.is_finalized = 0) as ppendingreport_today," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and tp.is_finalized = 0 and DATE_FORMAT(tp.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as ppendingreport_weekly," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and tp.is_finalized = 0 and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  )as ppendingreport_latest_week ," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and tp.is_finalized = 0 and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  )as ppendingreport_monthly ," + "(select count(tp.ps_id) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0   and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and tp.is_finalized = 0 and DATE_FORMAT(tp.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  )as ppendingreport_last_30_days";
            ppendingreport = objEntityManager.createNativeQuery(p3).getResultList();
            String p4 = "select  (select COUNT(Distinct(tp.ps_bill_id)) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0   and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  = CURDATE() and tb.ipd_bill = 0 ) as pordergenerated_today," + "(select COUNT(Distinct(tp.ps_bill_id)) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as pordergenerated_weekly, " + "(select COUNT(Distinct(tp.ps_bill_id)) from tpath_bs tp" + " INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  )as pordergenerated_latest_week, " + "(select COUNT(Distinct(tp.ps_bill_id)) from tpath_bs tp" + " INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(tp.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  )as pordergenerated_monthly, " + "(select COUNT(Distinct(tp.ps_bill_id)) from tpath_bs tp INNER JOIN tbill_bill tb ON tb.bill_id = tp.ps_bill_id  where tp.is_active = 1 and tp.is_deleted = 0 and tb.ipd_bill = 0  and tb.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(tp.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  )as pordergenerated_last_30_days ";
            pordergenerated = objEntityManager.createNativeQuery(p4).getResultList();
        }
        respMap.put("pordergenerated", pordergenerated);
        respMap.put("preportfinalized", preportfinalized);
        respMap.put("ppendingreport", ppendingreport);
        respMap.put("ptestcount", ptestcount);
        return respMap;
    }

    @RequestMapping("getRadiologyDetails")
    public Map<String, List> getRadiologyDetails(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, List> respMap = new HashMap<String, List>();
        JSONObject json = new JSONObject(search_query);
        // System.out.println(json);
        List<String> rordergenerated = new ArrayList<String>();
        List<String> rreportfinalized = new ArrayList<String>();
        List<String> rpendingreport = new ArrayList<String>();
        List<String> rtestcount = new ArrayList<String>();
        String p1 = "select(select count(tsr.bsr_id) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0  and DATE_FORMAT(tsr.created_date,'%y-%m-%d')  = CURDATE()) as  rordergenerated_today," + "(select count(tsr.bsr_id) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0  and DATE_FORMAT(tsr.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as  rordergenerated_weekly," + "(select count(tsr.bsr_id) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0  and DATE_FORMAT(tsr.created_date,'%y-%m-%d')    >= DATE(NOW() - INTERVAL 1 MONTH)) as  rordergenerated_monthly";
        rtestcount = objEntityManager.createNativeQuery(p1).getResultList();
        String p2 = "select(select count(tsr.bsr_id) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0 and tsr.bsr_is_reported = 1 and DATE_FORMAT(tsr.created_date,'%y-%m-%d')  = CURDATE()) as  rfinalized_today," + "(select count(tsr.bsr_id) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0  and tsr.bsr_is_reported = 1 and DATE_FORMAT(tsr.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as  rfinalized_weekly," + "(select count(tsr.bsr_id) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0 and tsr.bsr_is_reported = 1 and DATE_FORMAT(tsr.created_date,'%y-%m-%d')    >= DATE(NOW() - INTERVAL 1 MONTH)) as  rfinalized_monthly ";
        rreportfinalized = objEntityManager.createNativeQuery(p2).getResultList();
        String p3 = "select(select count(tsr.bsr_id) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0 and tsr.bsr_is_reported = 0 and DATE_FORMAT(tsr.created_date,'%y-%m-%d')  = CURDATE()) as  rfinalized_today," + "(select count(tsr.bsr_id) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0  and tsr.bsr_is_reported = 0 and DATE_FORMAT(tsr.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as  rfinalized_weekly," + "(select count(tsr.bsr_id) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0 and tsr.bsr_is_reported = 0 and DATE_FORMAT(tsr.created_date,'%y-%m-%d')    >= DATE(NOW() - INTERVAL 1 MONTH)) as  rfinalized_monthly";
        rpendingreport = objEntityManager.createNativeQuery(p3).getResultList();
        String p4 = "select(select COUNT(Distinct(tbs.bs_bill_id)) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0  and DATE_FORMAT(tsr.created_date,'%y-%m-%d')  = CURDATE()) as  rordergenerated_today," + "(select COUNT(Distinct(tbs.bs_bill_id)) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0  and DATE_FORMAT(tsr.created_date,'%y-%m-%d')  between adddate(now(),- 7) and now() ) as  rordergenerated_weekly," + "(select COUNT(Distinct(tbs.bs_bill_id)) from tbill_service_radiology tsr INNER JOIN tbill_bill_service tbs ON tbs.bs_id = tsr.bsr_bs_id  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id  where  tsr.is_active = 1 and tsr.is_deleted = 0 and tb.ipd_bill = 0  and DATE_FORMAT(tsr.created_date,'%y-%m-%d')    >= DATE(NOW() - INTERVAL 1 MONTH)) as  rordergenerated_monthly";
        rordergenerated = objEntityManager.createNativeQuery(p4).getResultList();
        respMap.put("rordergenerated", rordergenerated);
        respMap.put("rreportfinalized", rreportfinalized);
        respMap.put("rpendingreport", rpendingreport);
        respMap.put("rtestcount", rtestcount);
        return respMap;
    }
//    @GetMapping
//    @RequestMapping("getActivityPatientCount")
//    public  Map<String,Integer> getActivityPatientCount() throws JSONException {

    @RequestMapping("getpharmacytypeandsum/{unitid}/{unitlistid}")
    public Map<String, List> getpharmacytypeandsum(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> pharmacytypeandsum = new ArrayList<String>();
        if (unitid != 0) {
            try {
                String query1 = "select ifnull(pm.pm_name,'') as pm_name,SUM(COALESCE(tbr.cash,0.00)) as sumcash " + " from  tinv_pharmacy_bill_recepit tbr,mst_payment_mode pm where tbr.pbr_pm_id=pm.pm_id and tbr.is_active = 1 and tbr.is_deleted = 0 " + " and pbr_unit_id = " + unitid + " and  date(tbr.created_date) = CURDATE()" + " group by pm.pm_name ";
                //   String query1 = "select tbr.pbrPmId.pmName,SUM(COALESCE(tbr.cash,0.00)) from  PharmacyBillRecepit tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and pbrUnitId = " + unitid + " and  DATE_FORMAT(tbr.createdDate,'%y-%m-%d') = CURDATE() group by tbr.pbrPmId.pmName";
                pharmacytypeandsum = (List<String>) objEntityManager.createNativeQuery(query1).getResultList();
//
//
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String query1 = "select ifnull(pm.pm_name,'') as pm_name,SUM(COALESCE(tbr.cash,0.00)) as sumcash " + " from  tinv_pharmacy_bill_recepit tbr,mst_payment_mode pm where tbr.pbr_pm_id=pm.pm_id and tbr.is_active = 1 and tbr.is_deleted = 0 " + " and pbr_unit_id in (" + removeLastChar(unitlistid) + ") and  date(tbr.created_date) = CURDATE()" + " group by pm.pm_name ";
            // String query1 = "select tbr.pbrPmId.pmName,SUM(COALESCE(tbr.cash,0.00)) from  PharmacyBillRecepit tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and pbrUnitId in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(tbr.createdDate,'%y-%m-%d') = CURDATE() group by tbr.pbrPmId.pmName";
            pharmacytypeandsum = (List<String>) objEntityManager.createNativeQuery(query1).getResultList();

        }
//
//        Boolean today = search_query.contains("today") ? json.getBoolean("today") : false;
//        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : null;
//        String enddate = search_query.contains("enddate") ? json.getString("enddate") : null;
//        if(today.equals(true))
//        {
//            String query1 = "select tbr.brPmId.pmName,SUM(COALESCE(tbr.brPaymentAmount,0.00))  from  TbillReciept tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and tbr.isCancelled = 0 and tbr.isCancelled = false and  DATE_FORMAT(tbr.createdDate,'%y-%m-%d') = CURDATE() group by tbr.brPmId.pmName";
//            coldetails = objEntityManager.createQuery(query1).getResultList();
//
//
//        }
//        else
//        if (enddate.equals("") ||  enddate.equals(null) || enddate == "" || enddate == null)
//        {
//            String query1 = "select tbr.brPmId.pmName,SUM(COALESCE(tbr.brPaymentAmount,0.00))   from  TbillReciept tbr where tbr.isActive = 1 and tbr.isDeleted = 0 and tbr.isCancelled = 0  group by tbr.brPmId.pmName";
//            coldetails = objEntityManager.createQuery(query1).getResultList();
//
//
//        }
//        else
//        {
//            String query1 = "select tbr.brPmId.pmName , SUM((tbr.brPaymentAmount,0.00))   from  TbillReciept tbr where tbr.isActive = 1 and tbr.isDeleted = 0  and tbb.isCancelled = false and   DATE_FORMAT(tbr.createdDate,'%y-%m-%d')   BETWEEN CAST("+startdate+" as DATE) and CAST("+enddate+" as DATE) group by tbr.brPmId.pmName ";
//            coldetails = objEntityManager.createQuery(query1).getResultList();
//
//        }
        respMap.put("pharmacytypeandsum", pharmacytypeandsum);
        return respMap;
    }
//    @RequestMapping("getActivityPatientCount/{unitid}/{unitlistid}")
//    public Map<String, BigInteger> getActivityPatientCount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
//        Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();
//        Map<String, String> respMap1 = new HashMap<String, String>();
//        Date date = new Date();
//        Calendar cal = Calendar.getInstance();
//        String FirstDayOfMonth;
//        String LastDayOfMonth;
//
//        cal.setTime(date);
////        int month = cal.get(Calendar.MONTH);
////        int year = cal.get(Calendar.YEAR);
//        LocalDate currentDate = LocalDate.now();
//        int month = currentDate.getMonthValue();
//        int year = currentDate.getYear();
//
//        int numOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//
//        FirstDayOfMonth = year + "-" + month + "-" + "1";
//        LastDayOfMonth = year + "-" + month + "-" + numOfDaysInMonth;
//        System.out.println("If Activity--------LastDayOfMonth ---------- " + LastDayOfMonth);
//        System.out.println("---------FirstDayOfMonth -------- " + FirstDayOfMonth);
//        DateDto dateDto = new DateDto();
//        dateDto = getStartAndEndDateOfWeek();
//        System.out.println("--------respMap1 ---------- " + dateDto.getStartDateOfWeek());
//        System.out.println("--------respMap1 ---------- " + dateDto.getEndDateOfWeek());
//
//        BigInteger appointment_today= null, appointment_weekly= null, appointment_monthly= null, appointment_latest_week= null, appointment_last_30_days= null,
//                registred_today= null, registred_weekly= null, registred_monthly= null, registred_latest_week= null, registred_last_30_days= null,
//                visited_today= null, visited_weekly= null, visited_monthly= null, visited_latest_week= null, visited_last_30_days= null,laborder_today= null,
//                laborder_weekly= null, laborder_monthly= null, laborder_latest_week= null, laborder_last_30_days = null;
//
//        /*Integer appoinmentCount = 0;
//        Integer visitCount = 0;
//        Integer registrationCount = 0;
//        Integer labCount = 0;*/
//        try {
//            if (unitid != 0) {
//               // appoinmentCount = Integer.parseInt(objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  date(trn_appointment.appointment_date) = CURDATE() and trn_appointment.appointment_unit_id =  " + unitid).getSingleResult().toString());
//                appointment_today = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  date(trn_appointment.appointment_date) = CURDATE() and trn_appointment.appointment_unit_id =" + unitid).getSingleResult().toString());
//                appointment_weekly = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  DATE_FORMAT(trn_appointment.appointment_date,'%y-%m-%d') between adddate(now(),- 7) and now() and trn_appointment.appointment_unit_id =  " + unitid).getSingleResult().toString());
//                appointment_monthly = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  DATE_FORMAT(trn_appointment.appointment_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "') and DATE('" + LastDayOfMonth + "') and trn_appointment.appointment_unit_id =  " + unitid).getSingleResult().toString());
//                appointment_latest_week = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  DATE_FORMAT(trn_appointment.appointment_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "') and DATE('" + dateDto.getEndDateOfWeek() + "') and trn_appointment.appointment_unit_id =  " + unitid).getSingleResult().toString());
//                appointment_last_30_days = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  DATE_FORMAT(trn_appointment.appointment_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and trn_appointment.appointment_unit_id =  " + unitid).getSingleResult().toString());
//
//                //visitCount = Integer.parseInt(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  date(mst_visit.created_date) = CURDATE() and mst_visit.visit_unit_id =  " + unitid).getSingleResult().toString());
//                visited_today = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  date(mst_visit.created_date) = CURDATE() and mst_visit.visit_unit_id =  " + unitid).getSingleResult().toString());
//                visited_weekly = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  DATE_FORMAT(mst_visit.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() and mst_visit.visit_unit_id =  " + unitid).getSingleResult().toString());
//                visited_monthly = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  DATE_FORMAT(mst_visit.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth +"') and DATE('" + LastDayOfMonth +"') and mst_visit.visit_unit_id =" + unitid).getSingleResult().toString());
//                visited_latest_week = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  DATE_FORMAT(mst_visit.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() +"') and DATE('" + dateDto.getEndDateOfWeek() + "') and mst_visit.visit_unit_id =  " + unitid).getSingleResult().toString());
//                visited_last_30_days = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  DATE_FORMAT(mst_visit.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and mst_visit.visit_unit_id = " + unitid).getSingleResult().toString());
//
//
//                //String query1 = "select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and date(m.created_date) = CURDATE() and v.visit_unit_id =  " + unitid;
//               // registrationCount = Integer.parseInt(objEntityManager.createNativeQuery(query1).getSingleResult().toString());
//                registred_today = new BigInteger(objEntityManager.createNativeQuery("select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and date(m.created_date) = CURDATE() and v.visit_unit_id =  " + unitid).getSingleResult().toString());
//                registred_weekly = new BigInteger(objEntityManager.createNativeQuery("select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and DATE_FORMAT(m.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() and v.visit_unit_id =  " + unitid).getSingleResult().toString());
//                registred_monthly = new BigInteger(objEntityManager.createNativeQuery("select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and DATE_FORMAT(m.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth +"') and v.visit_unit_id =  " + unitid).getSingleResult().toString());
//                registred_latest_week = new BigInteger(objEntityManager.createNativeQuery("select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and DATE_FORMAT(m.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() +"') and v.visit_unit_id =  " + unitid).getSingleResult().toString());
//                registred_last_30_days = new BigInteger(objEntityManager.createNativeQuery("select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and DATE_FORMAT(m.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and v.visit_unit_id =  " + unitid).getSingleResult().toString());
//
////                String query2 = "select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id  = " + unitid + " and date(t.created_date)= CURDATE() ";
////                labCount = Integer.parseInt(objEntityManager.createNativeQuery(query2).getSingleResult().toString());
//
//                laborder_today = new BigInteger(objEntityManager.createNativeQuery("select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id = " + unitid + " and date(t.created_date)= CURDATE() ").getSingleResult().toString());
//                laborder_weekly = new BigInteger(objEntityManager.createNativeQuery("select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id  = " + unitid + " and DATE_FORMAT(t.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() ").getSingleResult().toString());
//                laborder_monthly = new BigInteger(objEntityManager.createNativeQuery("select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id  = " + unitid + " and DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') ").getSingleResult().toString());
//                laborder_latest_week = new BigInteger(objEntityManager.createNativeQuery("select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id  = " + unitid + " and DATE_FORMAT(t.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') ").getSingleResult().toString());
//                laborder_last_30_days = new BigInteger(objEntityManager.createNativeQuery("select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id  = " + unitid + " and DATE_FORMAT(t.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) ").getSingleResult().toString());
//            }
//            else {                                                                           //"select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  date(trn_appointment.appointment_date) = CURDATE() and trn_appointment.appointment_unit_id =  " + unitid)
//                //appoinmentCount = Integer.parseInt(objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  date(trn_appointment.appointment_date) = CURDATE() and trn_appointment.appointment_unit_id in (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//                appointment_today = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  date(trn_appointment.appointment_date) = CURDATE() and trn_appointment.appointment_unit_id in (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//                appointment_weekly = new BigInteger (objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  DATE_FORMAT(trn_appointment.appointment_date,'%y-%m-%d') between adddate(now(),- 7) and now() and trn_appointment.appointment_unit_id in (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//                appointment_monthly = new BigInteger (objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  DATE_FORMAT(trn_appointment.appointment_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') and trn_appointment.appointment_unit_idin (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//                appointment_latest_week = new BigInteger (objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  DATE_FORMAT(trn_appointment.appointment_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') and trn_appointment.appointment_unit_idin (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//                appointment_last_30_days = new BigInteger (objEntityManager.createNativeQuery("select CAST(count(trn_appointment.appointment_id) as UNSIGNED) from trn_appointment where trn_appointment.is_active = 1 and trn_appointment.is_deleted = 0 and  DATE_FORMAT(trn_appointment.appointment_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and trn_appointment.appointment_unit_id in (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//
//               // visitCount = Integer.parseInt(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  date(mst_visit.created_date) = CURDATE() and mst_visit.visit_unit_id in (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//                visited_today = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  date(mst_visit.created_date) = CURDATE() and mst_visit.visit_unit_id in (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//                visited_weekly = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  DATE_FORMAT(mst_visit.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() and mst_visit.visit_unit_id in (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//                visited_monthly = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  DATE_FORMAT(mst_visit.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth +"')   and   DATE('" + LastDayOfMonth + "') and mst_visit.visit_unit_idin (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//                visited_latest_week = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  DATE_FORMAT(mst_visit.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') and mst_visit.visit_unit_idin (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//                visited_last_30_days = new BigInteger(objEntityManager.createNativeQuery("select CAST(count(mst_visit.visit_id) as UNSIGNED) from mst_visit where mst_visit.is_active = 1 and mst_visit.is_deleted = 0 and  DATE_FORMAT(mst_visit.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and mst_visit.visit_unit_id in (" + removeLastChar(unitlistid) + ")").getSingleResult().toString());
//
//
////                String query1 = "select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and date(m.created_date) = CURDATE() and v.visit_unit_id in   (" + removeLastChar(unitlistid) + ")  ";
////                registrationCount = Integer.parseInt(objEntityManager.createNativeQuery(query1).getSingleResult().toString()); registrationCount = Integer.parseInt(objEntityManager.createNativeQuery(query1).getSingleResult().toString());
//
//                registred_today = new BigInteger(objEntityManager.createNativeQuery("select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and date(m.created_date) = CURDATE() and v.visit_unit_id in   (" + removeLastChar(unitlistid) + ")  ").getSingleResult().toString());
//                registred_weekly = new BigInteger(objEntityManager.createNativeQuery("select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and DATE_FORMAT(m.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() and v.visit_unit_id in   (" + removeLastChar(unitlistid) + ")  ").getSingleResult().toString());
//                registred_monthly = new BigInteger(objEntityManager.createNativeQuery("select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and DATE_FORMAT(m.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth +"')   and   DATE('" + LastDayOfMonth + "') and v.visit_unit_id in   (" + removeLastChar(unitlistid) + ")  ").getSingleResult().toString());
//                registred_latest_week = new BigInteger(objEntityManager.createNativeQuery("select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and DATE_FORMAT(m.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') and v.visit_unit_idin   (" + removeLastChar(unitlistid) + ")  ").getSingleResult().toString());
//                registred_last_30_days = new BigInteger(objEntityManager.createNativeQuery("select " + "CAST(count(m.patient_id) as UNSIGNED) " + " from  mst_visit v " + " left join mst_patient m on m.patient_id = v.visit_patient_id " + " where m.is_active = 1 and m.is_deleted = 0 and DATE_FORMAT(m.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and v.visit_unit_id in   (" + removeLastChar(unitlistid) + ")  ").getSingleResult().toString());
//
////
////                String query2 = " select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t,tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id in (" + removeLastChar(unitlistid) + ")  and date(t.created_date)= CURDATE() ";
////                labCount = Integer.parseInt(objEntityManager.createNativeQuery(query2).getSingleResult().toString());
//                                                                                                //SELECT CAST(COUNT(t.ps_id) AS UNSIGNED) FROM tpath_bs t, tbill_bill b WHERE t.is_active = 1 AND t.is_deleted = 0 AND t.ps_bill_id=b.bill_id AND b.tbill_unit_id IN(30 ,31,32,33)  AND date(t.created_date)= CURDATE()
//                laborder_today = new BigInteger(objEntityManager.createNativeQuery("select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id in (" + removeLastChar(unitlistid) + " and date(t.created_date)= CURDATE()").getSingleResult().toString());
//                laborder_weekly = new BigInteger(objEntityManager.createNativeQuery("select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id  in (" + removeLastChar(unitlistid) + " and DATE_FORMAT(m.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() ").getSingleResult().toString());
//                laborder_monthly = new BigInteger(objEntityManager.createNativeQuery("select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id in (" + removeLastChar(unitlistid) + " and DATE_FORMAT(m.created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') ").getSingleResult().toString());
//                laborder_latest_week = new BigInteger(objEntityManager.createNativeQuery("select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id  in (" + removeLastChar(unitlistid) + " and DATE_FORMAT(m.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') ").getSingleResult().toString());
//                laborder_last_30_days = new BigInteger(objEntityManager.createNativeQuery("select  CAST(count(t.ps_id) as UNSIGNED)  from  tpath_bs t, tbill_bill b where t.is_active = 1 and t.is_deleted = 0 and t.ps_bill_id=b.bill_id and b.tbill_unit_id  in (" + removeLastChar(unitlistid) + " and DATE_FORMAT(m.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) ").getSingleResult().toString());
//
//
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
///*
//        respMap.put("appoinmentCount", appoinmentCount);
//        respMap.put("visitCount", visitCount);
//        respMap.put("registrationCount", registrationCount);
//        respMap.put("labCount", labCount);*/
//
//        respMap.put("appointment_today", appointment_today);
//        respMap.put("appointment_weekly", appointment_weekly);
//        respMap.put("appointment_monthly", appointment_monthly);
//        respMap.put("appointment_latest_week", appointment_latest_week);
//        respMap.put("appointment_last_30_days", appointment_last_30_days);
//
//        respMap.put("registred_today", registred_today);
//        respMap.put("registred_weekly", registred_weekly);
//        respMap.put("registred_monthly", registred_monthly);
//        respMap.put("registred_latest_week", registred_latest_week);
//        respMap.put("registred_last_30_days", registred_last_30_days);
//
//        respMap.put("visited_today", visited_today);
//        respMap.put("visited_weekly", visited_weekly);
//        respMap.put("visited_monthly", visited_monthly);
//        respMap.put("visited_latest_week", visited_latest_week);
//        respMap.put("visited_last_30_days", visited_last_30_days);
//
//        respMap.put("laborder_today", laborder_today);
//        respMap.put("laborder_weekly", laborder_weekly);
//        respMap.put("laborder_monthly", laborder_monthly);
//        respMap.put("laborder_latest_week", laborder_latest_week);
//        respMap.put("laborder_last_30_days", laborder_last_30_days);
//
//
//        return respMap;
//    }

    @RequestMapping("getQueueDetailsByUnit/{unitid}/{unitlistid}")
    public Map<String, List> getQueueDetailsByUnit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, List> respMap = new HashMap<String, List>();
        List<String> queueDetails = new ArrayList<String>();
        if (unitid != 0) {
            String query2 = "SELECT concat(mst_user.user_firstname,' ',mst_user.user_lastname) as Doctor, " + "    COUNT(CASE WHEN tbbs.bs_status != 4 and tbbs.bs_status != 5 THEN 1 END) AS notclose, " + "    COUNT(CASE WHEN tbbs.bs_status = 4 and tbbs.bs_status != 5  THEN 1 END) AS close, " + "    COUNT(CASE WHEN tbbs.bs_status = 5  THEN 1 END) AS cancel " + "FROM tbill_bill_service tbbs " + "left join tbill_bill t on t.bill_id = tbbs.bs_bill_id " + "left join mst_staff on  mst_staff.staff_id = tbbs.bs_staff_id " + "left join mst_user on mst_user.user_id = mst_staff.staff_user_id " + "where DATE_FORMAT(tbbs.created_date,'%y-%m-%d')  = CURDATE()  and t.tbill_unit_id =  " + unitid + "   " + "group by  tbbs.bs_staff_id  ";
            queueDetails = (List<String>) objEntityManager.createNativeQuery(query2).getResultList();

        } else {
            String query2 = "SELECT concat(mst_user.user_firstname,' ',mst_user.user_lastname) as Doctor, " + "    COUNT(CASE WHEN tbbs.bs_status != 4 and tbbs.bs_status != 5 THEN 1 END) AS notclose, " + "    COUNT(CASE WHEN tbbs.bs_status = 4 and tbbs.bs_status != 5  THEN 1 END) AS close, " + "    COUNT(CASE WHEN tbbs.bs_status = 5  THEN 1 END) AS cancel " + "FROM tbill_bill_service tbbs " + "left join tbill_bill t on t.bill_id = tbbs.bs_bill_id " + "left join mst_staff on  mst_staff.staff_id = tbbs.bs_staff_id " + "left join mst_user on mst_user.user_id = mst_staff.staff_user_id " + "where DATE_FORMAT(tbbs.created_date,'%y-%m-%d')  = CURDATE()  and t.tbill_unit_id  in (" + removeLastChar(unitlistid) + ") " + "group by  tbbs.bs_staff_id  ";
            queueDetails = (List<String>) objEntityManager.createQuery(query2).getResultList();
        }
        System.out.println("queueDetails " + queueDetails);
        respMap.put("queueDetails", queueDetails);
        return respMap;
    }

    public DateDto getStartAndEndDateOfWeek(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String startDate, endDate;
        String startDayName, EndDayName;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        MstDay mstday;
        mstday = new MstDay();
        try {
            //Setting the date to the given date
            mstday = mstDayRepository.getOneByIsActiveTrueAndIsDeletedFalseAndStartDayTrue();
            startDayName = mstday.getDayName();
            mstday = new MstDay();
            mstday = mstDayRepository.getOneByIsActiveTrueAndIsDeletedFalseAndEndDayTrue();
            EndDayName = mstday.getDayName();
        } catch (java.lang.NullPointerException exception) {
            startDayName = "Monday";
        }
        String p3;
        if (startDayName.equals("Sunday") || startDayName.equals("sunday")) {
            p3 = "select date_add(curdate(),INTERVAL(1-DAYOFWEEK(curdate()))day)";
        } else if (startDayName.equals("Monday") || startDayName.equals("monday")) {
            p3 = "select date_add(curdate(),INTERVAL(2-DAYOFWEEK(curdate()))day)";
        } else if (startDayName.equals("Tuesday") || startDayName.equals("tuesday")) {
            p3 = "select date_add(curdate(),INTERVAL(3-DAYOFWEEK(curdate()))day)";
        } else if (startDayName.equals("Wednesday") || startDayName.equals("wednesday")) {
            p3 = "select date_add(curdate(),INTERVAL(4-DAYOFWEEK(curdate()))day)";
        } else if (startDayName.equals("Thursday") || startDayName.equals("thursday")) {
            p3 = "select date_add(curdate(),INTERVAL(5-DAYOFWEEK(curdate()))day)";
        } else if (startDayName.equals("Friday") || startDayName.equals("friday")) {
            p3 = "select date_add(curdate(),INTERVAL(6-DAYOFWEEK(curdate()))day)";
        } else {
            p3 = "select date_add(curdate(),INTERVAL(7-DAYOFWEEK(curdate()))day)";
        }
        startDate = String.valueOf(objEntityManager.createNativeQuery(p3).getSingleResult());
        System.out.println("------------------------------Start of week");
        System.out.println(startDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            //Setting the date to the given date
            c.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, 6);
        //Date after adding the days to the given date
        endDate = sdf.format(c.getTime());
        //Displaying the new Date after addition of Days
        System.out.println("Date after Addition: " + endDate);
        DateDto dateDto = new DateDto();
        dateDto.setStartDateOfWeek(startDate);
        dateDto.setEndDateOfWeek(endDate);
        return dateDto;
    }

    @RequestMapping("getpatdetails/{unitid}/{unitlistid}")
    public Map<String, BigInteger> getpatdetails(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitid") Long unitid, @PathVariable("unitlistid") String unitlistid) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("---object------");
        Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();
        Map<String, String> respMap1 = new HashMap<String, String>();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        String FirstDayOfMonth;
        String LastDayOfMonth;
        cal.setTime(date);
//        int month = cal.get(Calendar.MONTH);
//        int year = cal.get(Calendar.YEAR);
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        int numOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        FirstDayOfMonth = year + "-" + month + "-" + "1";
        LastDayOfMonth = year + "-" + month + "-" + numOfDaysInMonth;
        System.out.println("--------LastDayOfMonth ---------- " + LastDayOfMonth);
        System.out.println("---------FirstDayOfMonth -------- " + FirstDayOfMonth);
        DateDto dateDto = new DateDto();
        dateDto = getStartAndEndDateOfWeek(tenantName);
        System.out.println("--------respMap1 ---------- " + dateDto.getStartDateOfWeek());
        System.out.println("--------respMap1 ---------- " + dateDto.getEndDateOfWeek());
        BigInteger patappoinment_today, patappoinment_weekly, patappoinment_monthly, patappoinment_last_30_days, patappoinment_latest_week,
                patregiter_today, patregiter_weekly, patregiter_monthly, patregiter_last_30_days, patregiter_latest_week,
                patvisit_today, patvisit_weekly, patvisit_monthly, patvisit_last_30_days, patvisit_latest_week,
                patlaborders_today, patlaborders_weekly, patlaborders_monthly, patlaborders_last_30_days, patlaborders_latest_week;
        if (unitid != 0) {
            String a1 = "select count(appointment_id) from  trn_appointment where is_active = 1 and is_deleted = 0  and DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE() and  appointment_unit_id  = " + unitid;
            patappoinment_today = new BigInteger(objEntityManager.createNativeQuery(a1).getSingleResult().toString());
            System.out.println("Today " + patappoinment_today);
            String a2 = "select count(appointment_id) from  trn_appointment where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') between adddate(now(),- 7) and now()  and  appointment_unit_id  = " + unitid;
            patappoinment_weekly = new BigInteger(objEntityManager.createNativeQuery(a2).getSingleResult().toString());
            System.out.println("Weekly  " + patappoinment_weekly);
            String a3 = "select count(appointment_id) from  trn_appointment where is_active = 1 and is_deleted = 0  and  DATE_FORMAT(created_date,'%y-%m-%d')  BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "')  and  appointment_unit_id  = " + unitid;
            patappoinment_monthly = new BigInteger(objEntityManager.createNativeQuery(a3).getSingleResult().toString());
            System.out.println("monthly  " + patappoinment_monthly);
            String a4 = "select count(appointment_id) from  trn_appointment where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)  and  appointment_unit_id  = " + unitid;
            patappoinment_last_30_days = new BigInteger(objEntityManager.createNativeQuery(a4).getSingleResult().toString());
            String a5 = "select count(appointment_id) from  trn_appointment where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')  BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')  and  appointment_unit_id  = " + unitid;
            patappoinment_latest_week = new BigInteger(objEntityManager.createNativeQuery(a5).getSingleResult().toString());
            String b1 = " select count(Distinct(patient_id)) from mst_patient INNER JOIN mst_visit ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.is_active = 1 and mst_patient.is_deleted = 0 and DATE_FORMAT(mst_patient.created_date,'%y-%m-%d') = CURDATE()  and mst_visit.visit_unit_id  = " + unitid;
            patregiter_today = new BigInteger(objEntityManager.createNativeQuery(b1).getSingleResult().toString());
            String b2 = "select count(Distinct(patient_id)) from mst_patient  INNER JOIN mst_visit ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.is_active = 1 and mst_patient.is_deleted = 0 and DATE_FORMAT(mst_patient.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() and mst_visit.visit_unit_id  = " + unitid;
            patregiter_weekly = new BigInteger(objEntityManager.createNativeQuery(b2).getSingleResult().toString());
            String b3 = "select count(Distinct(patient_id)) from mst_patient  INNER JOIN mst_visit ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.is_active = 1 and mst_patient.is_deleted = 0  and  DATE_FORMAT(mst_patient.created_date,'%y-%m-%d')  BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') and mst_visit.visit_unit_id  = " + unitid;
            patregiter_monthly = new BigInteger(objEntityManager.createNativeQuery(b3).getSingleResult().toString());
            String b4 = "select count(Distinct(patient_id)) from mst_patient  INNER JOIN mst_visit ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.is_active = 1 and mst_patient.is_deleted = 0 and DATE_FORMAT(mst_patient.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and mst_visit.visit_unit_id  = " + unitid;
            patregiter_last_30_days = new BigInteger(objEntityManager.createNativeQuery(b4).getSingleResult().toString());
            String b5 = "select count(Distinct(patient_id)) from mst_patient  INNER JOIN mst_visit ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.is_active = 1 and mst_patient.is_deleted = 0 and  DATE_FORMAT(mst_patient.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') and mst_visit.visit_unit_id  = " + unitid;
            patregiter_latest_week = new BigInteger(objEntityManager.createNativeQuery(b5).getSingleResult().toString());
            String c1 = "select count(DISTINCT ps_bill_id) from tpath_bs where is_active = 1 and is_deleted = 0  and DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE()  and is_performed_by_unit_id  = " + unitid;
            patlaborders_today = new BigInteger(objEntityManager.createNativeQuery(c1).getSingleResult().toString());
            String c2 = " select count(DISTINCT ps_bill_id) from tpath_bs where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d') between adddate(now(),- 7) and now() and is_performed_by_unit_id  = " + unitid;
            patlaborders_weekly = new BigInteger(objEntityManager.createNativeQuery(c2).getSingleResult().toString());
            String c3 = " select count(DISTINCT ps_bill_id) from tpath_bs where is_active = 1 and is_deleted = 0  and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') and is_performed_by_unit_id  = " + unitid;
            patlaborders_monthly = new BigInteger(objEntityManager.createNativeQuery(c3).getSingleResult().toString());
            String c4 = " select count(DISTINCT ps_bill_id) from tpath_bs where is_active = 1 and is_deleted = 0 and DATE_FORMAT(created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and is_performed_by_unit_id  = " + unitid;
            patlaborders_last_30_days = new BigInteger(objEntityManager.createNativeQuery(c4).getSingleResult().toString());
            String c5 = " select count(DISTINCT ps_bill_id) from tpath_bs where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') and is_performed_by_unit_id  = " + unitid;
            patlaborders_latest_week = new BigInteger(objEntityManager.createNativeQuery(c5).getSingleResult().toString());
            String d1 = "select count(visit_id) from mst_visit where is_active = 1 and is_deleted = 0 and !ISNULL(visit_tariff_id) and DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE() and  visit_unit_id  = " + unitid;
            patvisit_today = new BigInteger(objEntityManager.createNativeQuery(d1).getSingleResult().toString());
            String d2 = " select count(visit_id) from mst_visit where is_active = 1 and is_deleted = 0 and !ISNULL(visit_tariff_id) and DATE_FORMAT(created_date,'%y-%m-%d') between adddate(now(),- 7) and now() and  visit_unit_id  = " + unitid;
            patvisit_weekly = new BigInteger(objEntityManager.createNativeQuery(d2).getSingleResult().toString());
            String d3 = "select count(visit_id) from mst_visit where is_active = 1 and is_deleted = 0 and !ISNULL(visit_tariff_id) and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') and  visit_unit_id  = " + unitid;
            patvisit_monthly = new BigInteger(objEntityManager.createNativeQuery(d3).getSingleResult().toString());
            String d4 = " select count(visit_id) from mst_visit  where is_active = 1 and is_deleted = 0 and !ISNULL(visit_tariff_id) and DATE_FORMAT(created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and  visit_unit_id  = " + unitid;
            patvisit_last_30_days = new BigInteger(objEntityManager.createNativeQuery(d4).getSingleResult().toString());
            String d5 = "select count(visit_id) from mst_visit where is_active = 1 and is_deleted = 0 and !ISNULL(visit_tariff_id) and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') and  visit_unit_id  = " + unitid;
            patvisit_latest_week = new BigInteger(objEntityManager.createNativeQuery(d5).getSingleResult().toString());
        } else {
            String a1 = "select count(appointment_id) from  trn_appointment where is_active = 1 and is_deleted = 0 and appointment_unit_id  in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE()";
            patappoinment_today = new BigInteger(objEntityManager.createNativeQuery(a1).getSingleResult().toString());
            String a2 = "select count(appointment_id) from  trn_appointment where is_active = 1 and is_deleted = 0 and appointment_unit_id  in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(created_date,'%y-%m-%d') between adddate(now(),- 7) and now() ";
            patappoinment_weekly = new BigInteger(objEntityManager.createNativeQuery(a2).getSingleResult().toString());
            String a3 = "select count(appointment_id) from  trn_appointment where is_active = 1 and is_deleted = 0 and appointment_unit_id  in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(created_date,'%y-%m-%d')  BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') ";
            patappoinment_monthly = new BigInteger(objEntityManager.createNativeQuery(a3).getSingleResult().toString());
            String a4 = "select count(appointment_id) from  trn_appointment where is_active = 1 and is_deleted = 0 and appointment_unit_id  in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)";
            patappoinment_last_30_days = new BigInteger(objEntityManager.createNativeQuery(a4).getSingleResult().toString());
            String a5 = "select count(appointment_id) from  trn_appointment where is_active = 1 and is_deleted = 0 and appointment_unit_id  in (" + removeLastChar(unitlistid) + ") and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')";
            patappoinment_latest_week = new BigInteger(objEntityManager.createNativeQuery(a5).getSingleResult().toString());
            String b1 = " select count(patient_id) from mst_patient INNER JOIN mst_visit ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.is_active = 1 and mst_patient.is_deleted = 0  and mst_visit.visit_unit_id  in (" + removeLastChar(unitlistid) + ") and DATE_FORMAT(mst_patient.created_date,'%y-%m-%d') = CURDATE()  and mst_visit.visit_unit_id  = " + unitid;
            patregiter_today = new BigInteger(objEntityManager.createNativeQuery(b1).getSingleResult().toString());
            String b2 = "select count(patient_id) from mst_patient  INNER JOIN mst_visit ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.is_active = 1 and mst_patient.is_deleted = 0 and mst_visit.visit_unit_id  in (" + removeLastChar(unitlistid) + ") DATE_FORMAT(mst_patient.created_date,'%y-%m-%d') between adddate(now(),- 7) and now() and mst_visit.visit_unit_id  = " + unitid;
            patregiter_weekly = new BigInteger(objEntityManager.createNativeQuery(b2).getSingleResult().toString());
            String b3 = "select count(patient_id) from mst_patient  INNER JOIN mst_visit ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.is_active = 1 and mst_patient.is_deleted = 0  and mst_visit.visit_unit_id  in (" + removeLastChar(unitlistid) + ")  DATE_FORMAT(mst_patient.created_date,'%y-%m-%d')  BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') and mst_visit.visit_unit_id  = " + unitid;
            patregiter_monthly = new BigInteger(objEntityManager.createNativeQuery(b3).getSingleResult().toString());
            String b4 = "select count(patient_id) from mst_patient  INNER JOIN mst_visit ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.is_active = 1 and mst_patient.is_deleted = 0 and mst_visit.visit_unit_id  in (" + removeLastChar(unitlistid) + ") DATE_FORMAT(mst_patient.created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH) and mst_visit.visit_unit_id  = " + unitid;
            patregiter_last_30_days = new BigInteger(objEntityManager.createNativeQuery(b4).getSingleResult().toString());
            String b5 = "select count(patient_id) from mst_patient  INNER JOIN mst_visit ON mst_visit.visit_patient_id = mst_patient.patient_id where mst_patient.is_active = 1 and mst_patient.is_deleted = 0 and mst_visit.visit_unit_id  in (" + removeLastChar(unitlistid) + ")  DATE_FORMAT(mst_patient.created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "') and mst_visit.visit_unit_id  = " + unitid;
            patregiter_latest_week = new BigInteger(objEntityManager.createNativeQuery(b5).getSingleResult().toString());
            String c1 = "select count(DISTINCT ps_bill_id) from tpath_bs where is_active = 1 and is_deleted = 0  and is_performed_by_unit_id  in (" + removeLastChar(unitlistid) + ")  and DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE()";
            patlaborders_today = new BigInteger(objEntityManager.createNativeQuery(c1).getSingleResult().toString());
            String c2 = " select count(DISTINCT ps_bill_id) from tpath_bs where is_active = 1 and is_deleted = 0 and is_performed_by_unit_id  in (" + removeLastChar(unitlistid) + ")  DATE_FORMAT(created_date,'%y-%m-%d') between adddate(now(),- 7) and now() ";
            patlaborders_weekly = new BigInteger(objEntityManager.createNativeQuery(c2).getSingleResult().toString());
            String c3 = " select count(DISTINCT ps_bill_id) from tpath_bs where is_active = 1 and is_deleted = 0  and is_performed_by_unit_id  in (" + removeLastChar(unitlistid) + ")   DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') ";
            patlaborders_monthly = new BigInteger(objEntityManager.createNativeQuery(c3).getSingleResult().toString());
            String c4 = "select count(DISTINCT ps_bill_id) from tpath_bs where is_active = 1 and is_deleted = 0 and is_performed_by_unit_id  in (" + removeLastChar(unitlistid) + ")  DATE_FORMAT(created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)";
            patlaborders_last_30_days = new BigInteger(objEntityManager.createNativeQuery(c4).getSingleResult().toString());
            String c5 = "select count(DISTINCT ps_bill_id) from tpath_bs where is_active = 1 and is_deleted = 0 and is_performed_by_unit_id  in (" + removeLastChar(unitlistid) + ")   DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')";
            patlaborders_latest_week = new BigInteger(objEntityManager.createNativeQuery(c5).getSingleResult().toString());
            String d1 = "select count(visit_id) from mst_visit where is_active = 1 and is_deleted = 0   and  visit_unit_id  in (" + removeLastChar(unitlistid) + ")  DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE()";
            patvisit_today = new BigInteger(objEntityManager.createNativeQuery(d1).getSingleResult().toString());
            String d2 = " select count(visit_id) from mst_visit where is_active = 1 and is_deleted = 0 and  visit_unit_id  in (" + removeLastChar(unitlistid) + ") DATE_FORMAT(created_date,'%y-%m-%d') between adddate(now(),- 7) and now() ";
            patvisit_weekly = new BigInteger(objEntityManager.createNativeQuery(d2).getSingleResult().toString());
            String d3 = "select count(visit_id) from mst_visit where is_active = 1 and is_deleted = 0  and  visit_unit_id  in (" + removeLastChar(unitlistid) + ")  DATE_FORMAT(created_date,'%y-%m-%d')  BETWEEN DATE('" + FirstDayOfMonth + "')   and   DATE('" + LastDayOfMonth + "') ";
            patvisit_monthly = new BigInteger(objEntityManager.createNativeQuery(d3).getSingleResult().toString());
            String d4 = "select count(visit_id) from mst_visit where is_active = 1 and is_deleted = 0 and  visit_unit_id  in (" + removeLastChar(unitlistid) + ") DATE_FORMAT(created_date,'%y-%m-%d')  >= DATE(NOW() - INTERVAL 1 MONTH)";
            patvisit_last_30_days = new BigInteger(objEntityManager.createNativeQuery(d4).getSingleResult().toString());
            String d5 = "select count(visit_id) from mst_visit where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN DATE('" + dateDto.getStartDateOfWeek() + "')   and   DATE('" + dateDto.getEndDateOfWeek() + "')";
            patvisit_latest_week = new BigInteger(objEntityManager.createNativeQuery(d5).getSingleResult().toString());
        }
        respMap.put("patappoinment_today", patappoinment_today);
        respMap.put("patappoinment_weekly", patappoinment_weekly);
        respMap.put("patappoinment_monthly", patappoinment_monthly);
        respMap.put("patappoinment_last_30_days", patappoinment_last_30_days);
        respMap.put("patappoinment_latest_week", patappoinment_latest_week);
        respMap.put("patregiter_today", patregiter_today);
        respMap.put("patregiter_weekly", patregiter_weekly);
        respMap.put("patregiter_monthly", patregiter_monthly);
        respMap.put("patregiter_last_30_days", patregiter_last_30_days);
        respMap.put("patregiter_latest_week", patregiter_latest_week);
        respMap.put("patvisit_today", patvisit_today);
        respMap.put("patvisit_weekly", patvisit_weekly);
        respMap.put("patvisit_monthly", patvisit_monthly);
        respMap.put("patvisit_last_30_days", patvisit_last_30_days);
        respMap.put("patvisit_latest_week", patvisit_latest_week);
        respMap.put("patlaborders_today", patlaborders_today);
        respMap.put("patlaborders_weekly", patlaborders_weekly);
        respMap.put("patlaborders_monthly", patlaborders_monthly);
        respMap.put("patlaborders_last_30_days", patlaborders_last_30_days);
        respMap.put("patlaborders_latest_week", patlaborders_latest_week);
        return respMap;
    }

    @RequestMapping("getAdmissionAndDischargeCountDetails")
    public Map<String, BigInteger> getAdmissionAndDischargeCountDetails(@RequestHeader("X-tenantId") String tenantName)
            throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, BigInteger> respMap = new HashMap<String, BigInteger>();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        BigInteger todaysAdmissionCount, totalAdmissionCount, todaysDischargeCount, totalDischargeCount, normalDischarge, deathDischarge;
        String a1 = "select count(admission_id) from trn_admission ta WHERE ta.admission_status=0 AND ta.admission_is_cancel=0 AND ta.is_active=1 AND ta.is_deleted=0 and DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE()";
        todaysAdmissionCount = new BigInteger(objEntityManager.createNativeQuery(a1).getSingleResult().toString());
        System.out.println("Today's Current Admission " + todaysAdmissionCount);
        String a2 = "select count(admission_id) from trn_admission ta WHERE ta.admission_status=0 AND ta.admission_is_cancel=0 AND ta.is_active=1 AND ta.is_deleted=0";
        totalAdmissionCount = new BigInteger(objEntityManager.createNativeQuery(a2).getSingleResult().toString());
        System.out.println("total current AdmissionCount " + totalAdmissionCount);
        String a3 = "select count(discharge_id) from trn_discharge td WHERE td.is_active=1 AND td.is_deleted=0 and DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE()";
        todaysDischargeCount = new BigInteger(objEntityManager.createNativeQuery(a3).getSingleResult().toString());
        System.out.println("Today's Discharge  " + todaysDischargeCount);
        String a4 = "select count(discharge_id) from trn_discharge td WHERE td.is_active=1 AND td.is_deleted=0";
        totalDischargeCount = new BigInteger(objEntityManager.createNativeQuery(a4).getSingleResult().toString());
        String a5 = "select count(discharge_id) from trn_discharge td INNER JOIN mipd_discharge_type mdt ON mdt.dt_id = td.discharge_type WHERE td.is_active=1 AND td.is_deleted=0 AND mdt.dt_name LIKE '%normal%'";
        normalDischarge = new BigInteger(objEntityManager.createNativeQuery(a5).getSingleResult().toString());
        String b1 = "select count(discharge_id) from trn_discharge td INNER JOIN mipd_discharge_type mdt ON mdt.dt_id = td.discharge_type WHERE td.is_active=1 AND td.is_deleted=0 AND mdt.dt_name LIKE '%death%'";
        deathDischarge = new BigInteger(objEntityManager.createNativeQuery(b1).getSingleResult().toString());
        respMap.put("todaysAdmissionCount", todaysAdmissionCount);
        respMap.put("totalAdmissionCount", totalAdmissionCount);
        respMap.put("todaysDischargeCount", todaysDischargeCount);
        respMap.put("totalDischargeCount", totalDischargeCount);
        respMap.put("normalDischarge", normalDischarge);
        respMap.put("deathDischarge", deathDischarge);
        return respMap;
    }

    @RequestMapping("sendMailClinicalHandover/{todate}")
    public Map<String, Object> getvisitdetailsbydepartment(@RequestHeader("X-tenantId") String tenantName, @PathVariable("todate") String todate) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("Manual Handover Mail Schedule Activity Started...!");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        System.out.println("selected date " + todate);
        if (todate.equals("") || todate.equals("null") || todate.equals("undefined")) {
            todate = strDate;
        }
        if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
            CasePaperSchedular casePaperSchedular = new CasePaperSchedular();
            Object emailContent = casePaperSchedular.createCasePaper(entityManager, todate);
            System.out.println("emailContent : " + emailContent);
            LocalDate todayDate = LocalDate.now();
            String subject = "";
            String msgContent = "";
            try {
                if (Propertyconfig.getEmailApi()) {
                    subject = "Clinical Handover for " + todate + " (Full Day)";
                    msgContent = "Dear User <br><br>" +
                            emailContent + "<br><br>" +
                            " <br><br> Thanks And Regards, " +
                            " <br> eHMS Admin";
                    Emailsend emailsend1 = new Emailsend();
                    emailsend1.sendMAil1(Propertyconfig.getDirectorEmail2(), msgContent, subject);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        Map<String, Object> respMapanco = new HashMap<String, Object>();
        respMapanco.put("success", "1");
        return respMapanco;

    }
}