package com.cellbeans.hspa.trnpatientqueue;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tbillbillservice.TbillBillService;
import com.cellbeans.hspa.tbillbillservice.TbillBillServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/patientqueue")
public class PatientqueueController {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TbillBillServiceRepository tbillBillServiceRepository;

    @PersistenceContext
    EntityManager entityManager;

    @PersistenceContext
    EntityManager objEntityManager;

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
//    @GetMapping
//    @RequestMapping("ListRecordByUnitId")
//    public Iterable<TbillBillService> ListRecordByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                         @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                         @RequestParam(value = "qString", required = false) Long qString,
//                                         @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                         @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
//                                         @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid,
//                                         @RequestParam(value = "staff", required = false, defaultValue = "0") long staff,
//                                         @RequestParam(value = "startdate", required = false, defaultValue = "0") String startdate,
//                                         @RequestParam(value = "enddate", required = false, defaultValue = "0") String enddate    ) {
//        Date sdate = new Date();
//        Date edate= new Date();
//        try
//        {
//            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//            sdate = sdf1.parse(startdate);
//            edate = sdf1.parse(enddate);
//
//
//        }catch (Exception e)
//        {
//
//        }
//        if (staff != 0)
//        {
//            if (startdate.equals(enddate) || enddate.equals(startdate)) {
//                //today
//                return tbillBillServiceRepository.findAllByBsDateAndBsBillIdIpdBillFalseAndBsBillIdTbillUnitIdUnitIdEqualsAndBsStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(new Date(), unitid, staff, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//            } else {
//                // from to
//
//                return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdTbillUnitIdUnitIdEqualsAndBsStaffIdStaffIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse( unitid, staff, sdate, edate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//            }
//      }
//      else
//        {
//            if (startdate.equals(enddate) || enddate.equals(startdate)) {
//                //today
//                return tbillBillServiceRepository.findAllByBsDateAndBsBillIdIpdBillFalseAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(new Date(), unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//            } else {
//                // from to
//                return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse( unitid,  sdate, edate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//            }
//        }
//
//    }
//    @GetMapping
//    @RequestMapping("bymrno")
//    public Iterable<TbillBillService> bymrno(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                             @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                             @RequestParam(value = "qString", required = false) Long qString,
//                                             @RequestParam(value = "pmrno", required = false) String pmrno,
//                                             @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                             @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
//                                             @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
//        System.out.println("type Mr no ==>" + pmrno);
//        return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(pmrno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//    }
//
//    @GetMapping
//    @RequestMapping("bypname")
//    public Iterable<TbillBillService> bypname(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                              @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                              @RequestParam(value = "qString", required = false) Long qString,
//                                              @RequestParam(value = "pname", required = false) String pname,
//                                              @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                              @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
//                                              @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
//        return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(pname, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//    }
//    @GetMapping
//    @RequestMapping("bymno")
//    public Iterable<TbillBillService> bymno(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                            @RequestParam(value = "qString", required = false) Long qString,
//                                            @RequestParam(value = "mno", required = false) String mno,
//                                            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                            @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
//                                            @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
//        return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMobileContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(mno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//    }
//    @GetMapping
//    @RequestMapping("byuid")
//    public Iterable<TbillBillService> byuid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                            @RequestParam(value = "qString", required = false) Long qString,
//                                            @RequestParam(value = "uid", required = false) String uid,
//                                            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                            @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
//                                            @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
//        return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserUidContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(uid, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//    }
//    @GetMapping
//    @RequestMapping("byperformidid")
//    public Iterable<TbillBillService> byperformidid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                            @RequestParam(value = "qString", required = false) Long qString,
//                                            @RequestParam(value = "staffid", required = false) Long staffid,
//                                            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                            @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
//                                                    @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
//        return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(staffid,unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//    }
//    @GetMapping
//    @RequestMapping("byperformidid")
//    public Iterable<TbillBillService> byperformidid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                                    @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                                    @RequestParam(value = "qString", required = false) Long qString,
//                                                    @RequestParam(value = "staffid", required = false) Long staffid,
//                                                    @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                                    @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
//                                                    @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid,
//                                                    @RequestParam(value = "startdate", required = false, defaultValue = "0") String startdate,
//                                                    @RequestParam(value = "enddate", required = false, defaultValue = "0") String enddate    ) {
//
//
//
//        Date sdate = new Date();
//        Date edate= new Date();
//        if (startdate.equals(enddate) || enddate.equals(startdate)) {
//            //today
//            return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateAndIsActiveTrueAndIsDeletedFalse(staffid,unitid,new Date(), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        } else {
//            // from to
//            try
//            {
//                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//                sdate = sdf1.parse(startdate);
//                 edate = sdf1.parse(enddate);
//
//
//            }catch (Exception e)
//            {
//
//            }
//
//            return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(staffid,unitid,sdate, edate,PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
//
//    }
//    @GetMapping
//    @RequestMapping("byfromdate")
//    public Iterable<TbillBillService> byfromdate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                                    @RequestParam(value = "size", required = false, defaultValue = "10") String size,
//                                                    @RequestParam(value = "qString", required = false) Long qString,
//                                                    @RequestParam(value = "start", required = false) String start,
//                                                    @RequestParam(value = "end", required = false) String end,
//                                                    @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                                    @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
//                                                   @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid,
//                                                   @RequestParam(value = "staff", required = false, defaultValue = "0") long staff) {
//
//        System.out.println("start "+start);
//        System.out.println("end "+end);
//        System.out.println("page "+page);
//
//        String Query = "SELECT tbs FROM TbillBillService tbs where bsBillId.tbillUnitId.unitId = '"+unitid+"' AND bsDate BETWEEN '"+start+"' AND '"+end+"' order by bsId DESC " ;
//     return entityManager.createQuery(Query).setMaxResults(Integer.parseInt(size)).setFirstResult(Integer.parseInt(page)).getResultList();
//
//
//    }
//    @GetMapping
//    @RequestMapping("byfromdate")
//    public Iterable<TbillBillService> byfromdate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                                 @RequestParam(value = "size", required = false, defaultValue = "10") String size,
//                                                 @RequestParam(value = "qString", required = false) Long qString,
//                                                 @RequestParam(value = "start", required = false) String start,
//                                                 @RequestParam(value = "end", required = false) String end,
//                                                 @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                                 @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
//                                                 @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid,
//                                                 @RequestParam(value = "staff", required = false, defaultValue = "0") long staff) {
//
//        System.out.println("start " + start);
//        System.out.println("end " + end);
//        System.out.println("page " + page);
//
//        if (staff == 0) {
//            if (start.equals(end) || end.equals(start)) {
//                //today
//                return tbillBillServiceRepository.findAllByBsBillIdTbillUnitIdUnitIdEqualsAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(unitid, new Date(), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//            } else {
//                Date sdate = new Date();
//                Date edate = new Date();
//                try {
//                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//                    sdate = sdf1.parse(start);
//                    edate = sdf1.parse(end);
//
//
//                } catch (Exception e) {
//
//                }
//                // from to
//                return tbillBillServiceRepository.findAllByBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(unitid, sdate, edate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//            }
//        } else {
//            if (start.equals(end) || end.equals(start)) {
//                //today
//                return tbillBillServiceRepository.findAllByBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(staff, unitid, new Date(), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//            } else {
//                Date sdate = new Date();
//                Date edate = new Date();
//                try {
//                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//                    sdate = sdf1.parse(start);
//                    edate = sdf1.parse(end);
//
//
//                } catch (Exception e) {
//
//                }
//                // from to
//                return tbillBillServiceRepository.findAllByBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(staff, unitid, sdate, edate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//            }
//
//        }
//
//
//    }
//    @RequestMapping("byuserid/{departmentid}")
//    public List<TbillBillService> byuserid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("departmentid") Long departmentid) {
//        System.out.println("======>"+departmentid);ListRecordByUnitId
//        return tbillBillServiceRepository.findAllByBsServiceIdServiceSgIdSgGroupIdGroupSdIdSdDepartmentIdDepartmentIdEquals(departmentid);
//     }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillBillService> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillServiceRepository.findAllByBsDateAndBsBillIdIpdBillFalseAndIsActiveTrueAndIsDeletedFalse(new Date(), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }
//    @GetMapping
//    @RequestMapping("allpatientlistbymrno")
//    public Iterable<TbillBillService> allpatientlistbymrno(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                             @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                             @RequestParam(value = "qString", required = false) Long qString,
//                                             @RequestParam(value = "pmrno", required = false) String pmrno,
//                                             @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                             @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
//
//    	  java.sql.Date todaysdate ;
//          LocalDate today = LocalDate.now();
//          todaysdate = java.sql.Date.valueOf(today);
//
//          return tbillBillServiceRepository.findAllByBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsDateEquals(pmrno,todaysdate,PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//    }
//    @GetMapping
//    @RequestMapping("allpatientlistbypname")
//    public Iterable<TbillBillService> allpatientlistbypname(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                              @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                              @RequestParam(value = "qString", required = false) Long qString,
//                                              @RequestParam(value = "pname", required = false) String pname,
//                                              @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                              @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
//
//  	  java.sql.Date todaysdate ;
//      LocalDate today = LocalDate.now();
//      todaysdate = java.sql.Date.valueOf(today);
//      return tbillBillServiceRepository.findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsAndBsDateEquals(pname,todaysdate,PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//    }
//    @GetMapping
//    @RequestMapping("allpatientlistbymno")
//    public Iterable<TbillBillService> allpatientlistbymno(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                            @RequestParam(value = "qString", required = false) Long qString,
//                                            @RequestParam(value = "mno", required = false) String mno,
//                                            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                            @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
//
//    	 java.sql.Date todaysdate ;
//         LocalDate today = LocalDate.now();
//         todaysdate = java.sql.Date.valueOf(today);
//  		  return tbillBillServiceRepository.findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMobileContainsAndBsDateEquals(mno,todaysdate,PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//    }
//    @GetMapping
//    @RequestMapping("allpatientlistbyuid")
//    public Iterable<TbillBillService> allpatientlistbyuid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                            @RequestParam(value = "qString", required = false) Long qString,
//                                            @RequestParam(value = "uid", required = false) String uid,
//                                            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                            @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
//   	 java.sql.Date todaysdate ;
//     LocalDate today = LocalDate.now();
//     todaysdate = java.sql.Date.valueOf(today);
//	  return tbillBillServiceRepository.findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserUidContainsAndBsDateEquals(uid,todaysdate,PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//    }
//    public Iterable<TbillBillService>  getByTodaysDate()
//    {
//    	return tbillBillServiceRepository.findByEndLessThanEqual()
//    }
//    @GetMapping
//    @RequestMapping("patientlistbydepartment")
//    public Iterable<TbillBillService> patientlistbydepartment(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                           @RequestParam(value = "size", required = false, defaultValue = "10") String size,
//                                           @RequestParam(value = "qString", required = false) int qString,
//                                           @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                           @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
//
//    	  java.sql.Date todaysdate ;
//          LocalDate today = LocalDate.now();
//          todaysdate = java.sql.Date.valueOf(today);
//
//     	 return tbillBillServiceRepository.findAllByBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(todaysdate,new PageRequest(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//
//    }
//    @GetMapping
//    @RequestMapping("listrecordbystartenddate")
//    public Iterable<TbillBillService> listrecordbystartenddate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                            @RequestParam(value = "startdate", required = false) String startdate,
//                                            @RequestParam(value = "enddate", required = false) String enddate,
//                                            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                            @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) throws ParseException {
//
//
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date StartDate = df.parse(startdate);
//        Date EndDate = df.parse(enddate);
//        System.out.println("date======>"+StartDate);
//        return tbillBillServiceRepository.findAllByBsDateBetween(StartDate,EndDate,PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//    }
//
//

    @GetMapping
    @RequestMapping("allpatientlist")
    public Iterable<TbillBillService> allpatientlist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillServiceRepository.findAll(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        // return tbillBillServiceRepository.findAllByBsServiceIdServiceSgIdSgGroupIdGroupSdIdSdDepartmentIdDepartmentIdEquals(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("ListRecordByUnitId")
    public Map<String, Object> ListRecordByUnitId(@RequestHeader("X-tenantId") String tenantName,
                                                  @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                  @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                  @RequestParam(value = "qString", required = false) Long qString,
                                                  @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                  @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
                                                  @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid,
                                                  @RequestParam(value = "staff", required = false, defaultValue = "0") long staff,
                                                  @RequestParam(value = "startdate", required = false, defaultValue = "0") String startdate,
                                                  @RequestParam(value = "enddate", required = false, defaultValue = "0") String enddate,
                                                  @RequestParam(value = "staffList", required = false, defaultValue = " ") String staffList,
                                                  @RequestParam(value = "patientStatus", required = false, defaultValue = "0") int patientStatus,
                                                  @RequestParam(value = "visitIdList", required = false, defaultValue = "") String visitIdList,
                                                  @RequestParam(value = "createdById", required = false, defaultValue = "") Long createdById) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        List<String> queue = new ArrayList<String>();
        List<String> queuecount = new ArrayList<String>();
        String CountQuery = "";
        if (staff != 0) {
            if (startdate.equals(enddate) || enddate.equals(startdate)) {
                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname,tb.bill_outstanding,tb.bill_net_payable  ,tbbs.bill_cancel_reason,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card,case when mv.is_virtual=1 then 'true' ELSE 'false' end,ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte), CASE WHEN  mv.is_visit_urgent=1 THEN 'true' ELSE 'false' END AS isUrgent, mv.is_visit_urgent_comments AS prioritycomment, CASE WHEN tap.is_appointment_referral=1 THEN 'true' ELSE 'false' END AS isReferral, tap.refer_history_id, trh.created_date AS rhDate, muu.user_fullname, trh.rh_subject ,CONCAT(muu.user_fullname,' on ',date(trh.created_date), ' Reason ', trh.rh_subject) as Reason, tap.appointment_followup_timeline_id, mbs.service_name, CASE WHEN mv.visit_is_in_consultation =1 THEN true ELSE false END AS visit_is_in_consultation" +
                        " FROM tbill_bill_service  tbbs LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                        " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                        " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                        " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                        " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                        " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                        " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                        " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                        " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                        " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                        " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                        " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                        " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                        " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                        " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                        " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                        " LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " LEFT JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
                        " inner JOIN trn_appointment tap ON tap.appointment_timeline_id = tt.timeline_id " +
                        " LEFT JOIN temr_referral_history trh ON trh.rh_id = tap.refer_history_id " +
                        " LEFT JOIN mst_staff msst ON msst.staff_id = trh.rh_staff_id " +
                        " LEFT JOIN mst_user muu ON muu.user_id = msst.staff_user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tbbs.bs_staff_id= " + staff +
                        " and (date(tbbs.bs_date)= CURDATE()) and mbs.service_is_consultion = 1 ";
//                        " and tb.tbill_unit_id = " + unitid + "  and (date(tbbs.bs_date)= CURDATE()) ";
//                        " and tb.tbill_unit_id = " + unitid;
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (!visitIdList.equals("null")) {
                    query = query + " and  mv.visit_id in (" + visitIdList + ")";
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                queuecount = entityManager.createNativeQuery(query).getResultList();
                query = query + " order by tbbs.bs_id asc  limit " + limit + " offset " + offset;
                System.out.println("Patient Waiting Room Query 1 " + query);
                queue = entityManager.createNativeQuery(query).getResultList();

            } else {
                // from to
                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname  as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname, tb.bill_outstanding,tb.bill_net_payable ,tbbs.bill_cancel_reason ,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card,case when mv.is_virtual=1 then 'true' ELSE 'false' end,ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte), CASE WHEN  mv.is_visit_urgent=1 THEN 'true' ELSE 'false' END AS isUrgent, mv.is_visit_urgent_comments AS prioritycomment, CASE WHEN tap.is_appointment_referral=1 THEN 'true' ELSE 'false' END AS isReferral, tap.refer_history_id, trh.created_date AS rhDate, muu.user_fullname, trh.rh_subject ,CONCAT(muu.user_fullname,' on ',date(trh.created_date), ' Reason ', trh.rh_subject) as Reason,  tap.appointment_followup_timeline_id, mbs.service_name, CASE WHEN mv.visit_is_in_consultation =1 THEN true ELSE false END AS visit_is_in_consultation" +
                        " FROM tbill_bill_service  tbbs LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                        " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                        " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                        " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                        " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                        " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                        " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                        " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                        " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                        " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                        " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                        " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                        " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                        " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                        " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                        " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                        " LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " LEFT JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
                        " inner JOIN trn_appointment tap ON tap.appointment_timeline_id = tt.timeline_id " +
                        " LEFT JOIN temr_referral_history trh ON trh.rh_id = tap.refer_history_id " +
                        " LEFT JOIN mst_staff msst ON msst.staff_id = trh.rh_staff_id " +
                        " LEFT JOIN mst_user muu ON muu.user_id = msst.staff_user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tbbs.bs_staff_id= " + staff +
                        " and tb.tbill_unit_id = " + unitid + "  and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "' and mbs.service_is_consultion = 1 ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                CountQuery = " select count(*) from ( " + query + " ) as combine ";
                queuecount = entityManager.createNativeQuery(query).getResultList();
                query = query + " order by tbbs.bs_id asc  limit " + limit + " offset " + offset;
                System.out.println("Patient Waiting Room Query 2 " + query);
                queue = entityManager.createNativeQuery(query).getResultList();

            }
            Map<String, Object> map = new HashMap<>();
            map.put("content", queue);
            map.put("count", queuecount.size());
            return map;
//            return queue;
        } else {
            if (startdate.equals(enddate) || enddate.equals(startdate)) {
                //today
//                String query = " SELECT     tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" +
//                " FROM TbillBillService  tbbs " +
//                        "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " +
//                        " tbbs.bsStaffId.staffId in (" + removeLastChar(staffList) + ")  and  tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsDate= CURDATE()   ";
//
//
//                if(patientStatus == 4 || patientStatus == 5)
//                {
//                    query = query +  " and tbbs.bsStatus = "+patientStatus;
//                }
//                else
//                {
//                    query = query +  " and tbbs.bsStatus != 5 and  tbbs.bsStatus != 4";
//                }
//
//                query = query +  " order by tbbs.bsId desc ";
//                System.out.println(query);
//                queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname,tb.bill_outstanding,tb.bill_net_payable  ,tbbs.bill_cancel_reason,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card,case when mv.is_virtual=1 then 'true' ELSE 'false' end,ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte), CASE WHEN  mv.is_visit_urgent=1 THEN 'true' ELSE 'false' END AS isUrgent, mv.is_visit_urgent_comments AS prioritycomment, CASE WHEN tap.is_appointment_referral=1 THEN 'true' ELSE 'false' END AS isReferral, tap.refer_history_id, trh.created_date AS rhDate, muu.user_fullname, trh.rh_subject ,CONCAT(muu.user_fullname,' on ',date(trh.created_date), ' Reason ', trh.rh_subject) as Reason, tap.appointment_followup_timeline_id, mbs.service_name, CASE WHEN mv.visit_is_in_consultation =1 THEN true ELSE false END AS visit_is_in_consultation" +
                        " FROM tbill_bill_service  tbbs LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                        " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                        " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                        " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                        " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                        " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                        " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                        " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                        " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                        " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                        " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                        " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                        " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                        " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                        " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                        " LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                        " LEFT JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
                        " inner JOIN trn_appointment tap ON tap.appointment_timeline_id = tt.timeline_id " +
                        " LEFT JOIN temr_referral_history trh ON trh.rh_id = tap.refer_history_id " +
                        " LEFT JOIN mst_staff msst ON msst.staff_id = trh.rh_staff_id " +
                        " LEFT JOIN mst_user muu ON muu.user_id = msst.staff_user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tb.tbill_unit_id = " + unitid + "  and (date(tbbs.bs_date)= CURDATE()) ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                CountQuery = " select count(*) from ( " + query + " ) as combine ";
                query = query + " order by tbbs.bs_id asc  limit " + limit + " offset " + offset;
                System.out.println("Patient Waiting Room Query 3 " + query);
                queue = entityManager.createNativeQuery(query).getResultList();
                BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
                Map<String, Object> map = new HashMap<>();
                map.put("content", queue);
                map.put("count", cc);
                return map;
//                return queue;
            } else {
                // from to
//
//                String query = " SELECT     tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" +
//                        " FROM TbillBillService  tbbs " +
//                        "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " +
//                        " tbbs.bsStaffId.staffId in (" + removeLastChar(staffList) + ")  and  tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsDate BETWEEN '" + startdate + "' and '" + enddate + "' ";
//
//
//                if(patientStatus == 4 || patientStatus == 5)
//                {
//                    query = query +  " and tbbs.bsStatus = "+patientStatus;
//                }
//                else
//                {
//                    query = query +  " and tbbs.bsStatus != 5 and  tbbs.bsStatus != 4";
//                }
//                query = query+ " order by tbbs.bsId desc  ";
//                System.out.println(query);
//                queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname  as fname,mu.user_middlename  as mname,mu.user_lastname  as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname, tb.bill_outstanding,tb.bill_net_payable ,tbbs.bill_cancel_reason ,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card, case when mv.is_virtual=1 then 'true' ELSE 'false' end ,tap.appointment_followup_timeline_id, mbs.service_name, CASE WHEN mv.visit_is_in_consultation =1 THEN true ELSE false END AS visit_is_in_consultation" + " FROM tbill_bill_service  tbbs " + "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " + " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " + " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" + " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " + "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " + "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " + "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " + "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tb.tbill_unit_id = " + unitid +
                        " and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "' ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                CountQuery = " select count(*) from ( " + query + " ) as combine ";
                query = query + " order by tbbs.bs_id asc  limit " + limit + " offset " + offset;
                System.out.println("Patient Waiting Room Query 4 " + query);
                queue = entityManager.createNativeQuery(query).getResultList();
                BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
                Map<String, Object> map = new HashMap<>();
                map.put("content", queue);
                map.put("count", cc);
                return map;
//                return queue;
            }
        }

    }

    @GetMapping
    @RequestMapping("ListRecordByUnitIdWithFilter")
    public Map<String, Object> ListRecordByUnitIdWithFilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                            @RequestParam(value = "qString", required = false) Long qString,
                                                            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                            @RequestParam(value = "col", required = false, defaultValue = "bsId") String col,
                                                            @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid,
                                                            @RequestParam(value = "staff", required = false, defaultValue = "0") long staff,
                                                            @RequestParam(value = "startdate", required = false, defaultValue = "0") String startdate,
                                                            @RequestParam(value = "enddate", required = false, defaultValue = "0") String enddate,
                                                            @RequestParam(value = "staffList", required = false, defaultValue = " ") String staffList,
                                                            @RequestParam(value = "patientStatus", required = false, defaultValue = "0") int patientStatus,
                                                            @RequestParam(value = "visitIdList", required = false, defaultValue = "") String visitIdList,
                                                            @RequestParam(value = "createdById", required = false, defaultValue = "") Long createdById,
                                                            @RequestParam(value = "patientName", required = false, defaultValue = "") String patientName,
                                                            @RequestParam(value = "mrNo", required = false, defaultValue = "") String mrNo,
                                                            @RequestParam(value = "mobNo", required = false, defaultValue = "") String mobNo) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        List<String> queue = new ArrayList<String>();
        List<String> queuecount = new ArrayList<String>();
        String CountQuery = "";
        if (staff != 0) {
            if (startdate.equals(enddate) || enddate.equals(startdate)) {
                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname,tb.bill_outstanding,tb.bill_net_payable  ,tbbs.bill_cancel_reason,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card,case when mv.is_virtual=1 then 'true' ELSE 'false' end,ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte), CASE WHEN  mv.is_visit_urgent=1 THEN 'true' ELSE 'false' END AS isUrgent, mv.is_visit_urgent_comments AS prioritycomment, CASE WHEN tap.is_appointment_referral=1 THEN 'true' ELSE 'false' END AS isReferral, tap.refer_history_id, trh.created_date AS rhDate, muu.user_fullname, trh.rh_subject ,CONCAT(muu.user_fullname,' on ',date(trh.created_date), ' Reason ', trh.rh_subject) as Reason, tap.appointment_followup_timeline_id, mbs.service_name,mp.patient_id,tt.isemrfinal " +
                        " FROM tbill_bill_service  tbbs LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                        " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                        " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                        " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                        " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                        " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                        " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                        " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                        " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                        " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                        " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                        " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                        " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                        " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                        " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                        " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                        " LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " LEFT JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
                        " inner JOIN trn_appointment tap ON tap.appointment_timeline_id = tt.timeline_id " +
                        " LEFT JOIN temr_referral_history trh ON trh.rh_id = tap.refer_history_id " +
                        " LEFT JOIN mst_staff msst ON msst.staff_id = trh.rh_staff_id " +
                        " LEFT JOIN mst_user muu ON muu.user_id = msst.staff_user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tbbs.bs_staff_id= " + staff +
                        " and (date(tbbs.bs_date)= CURDATE()) and mbs.service_is_consultion = 1 ";
//                        " and tb.tbill_unit_id = " + unitid + "  and (date(tbbs.bs_date)= CURDATE()) ";
//                        " and tb.tbill_unit_id = " + unitid;
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientName != "") {
                    query += " and concat(mu.user_firstname,' ', mu.user_lastname) LIKE '%" + patientName + "%' ";
                }
                if (mrNo != "") {
                    query += " and mp.patient_mr_no LIKE '%" + mrNo + "%' ";
                }
                if (mobNo != "") {
                    query += " and mu.user_mobile LIKE '%" + mobNo + "%' ";
                }
//                if (!visitIdList.equals("null")) {
//                    query = query + " and  mv.visit_id in (" + visitIdList + ")";
//                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
//                queuecount = entityManager.createNativeQuery(query).getResultList();
                query = query + " order by tbbs.bs_id desc  limit " + limit + " offset " + offset;
                System.out.println("Patient Waiting Room Query 1 " + query);
                queue = entityManager.createNativeQuery(query).getResultList();

            } else {
                // from to
                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname  as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname, tb.bill_outstanding,tb.bill_net_payable ,tbbs.bill_cancel_reason ,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card,case when mv.is_virtual=1 then 'true' ELSE 'false' end,ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte), CASE WHEN  mv.is_visit_urgent=1 THEN 'true' ELSE 'false' END AS isUrgent, mv.is_visit_urgent_comments AS prioritycomment, CASE WHEN tap.is_appointment_referral=1 THEN 'true' ELSE 'false' END AS isReferral, tap.refer_history_id, trh.created_date AS rhDate, muu.user_fullname, trh.rh_subject ,CONCAT(muu.user_fullname,' on ',date(trh.created_date), ' Reason ', trh.rh_subject) as Reason,  tap.appointment_followup_timeline_id, mbs.service_name,mp.patient_id,tt.isemrfinal " +
                        " FROM tbill_bill_service  tbbs LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                        " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                        " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                        " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                        " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                        " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                        " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                        " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                        " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                        " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                        " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                        " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                        " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                        " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                        " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                        " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                        " LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " LEFT JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
                        " inner JOIN trn_appointment tap ON tap.appointment_timeline_id = tt.timeline_id " +
                        " LEFT JOIN temr_referral_history trh ON trh.rh_id = tap.refer_history_id " +
                        " LEFT JOIN mst_staff msst ON msst.staff_id = trh.rh_staff_id " +
                        " LEFT JOIN mst_user muu ON muu.user_id = msst.staff_user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tbbs.bs_staff_id= " + staff +
                        " and tb.tbill_unit_id = " + unitid + "  and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "' and mbs.service_is_consultion = 1 ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                if (patientName != "") {
                    query += " and concat(mu.user_firstname,' ', mu.user_lastname) LIKE '%" + patientName + "%' ";
                }
                if (mrNo != "") {
                    query += " and mp.patient_mr_no LIKE '%" + mrNo + "%' ";
                }
                if (mobNo != "") {
                    query += " and mu.user_mobile LIKE '%" + mobNo + "%' ";
                }
                CountQuery = " select count(*) from ( " + query + " ) as combine ";
                queuecount = entityManager.createNativeQuery(query).getResultList();
                query = query + " order by tbbs.bs_id asc  limit " + limit + " offset " + offset;
                System.out.println("Patient Waiting Room Query 2 " + query);
                queue = entityManager.createNativeQuery(query).getResultList();
            }
            Map<String, Object> map = new HashMap<>();
            map.put("content", queue);
            map.put("count", queuecount.size());
            return map;
//            return queue;
        } else {
            if (startdate.equals(enddate) || enddate.equals(startdate)) {
                //today
//                String query = " SELECT     tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" +
//                " FROM TbillBillService  tbbs " +
//                        "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " +
//                        " tbbs.bsStaffId.staffId in (" + removeLastChar(staffList) + ")  and  tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsDate= CURDATE()   ";
//
//
//                if(patientStatus == 4 || patientStatus == 5)
//                {
//                    query = query +  " and tbbs.bsStatus = "+patientStatus;
//                }
//                else
//                {
//                    query = query +  " and tbbs.bsStatus != 5 and  tbbs.bsStatus != 4";
//                }
//
//                query = query +  " order by tbbs.bsId desc ";
//                System.out.println(query);
//                queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
                String query = "SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname,tb.bill_outstanding,tb.bill_net_payable  ,tbbs.bill_cancel_reason,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card,case when mv.is_virtual=1 then 'true' ELSE 'false' end,ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte), CASE WHEN  mv.is_visit_urgent=1 THEN 'true' ELSE 'false' END AS isUrgent, mv.is_visit_urgent_comments AS prioritycomment, CASE WHEN tap.is_appointment_referral=1 THEN 'true' ELSE 'false' END AS isReferral, tap.refer_history_id, trh.created_date AS rhDate, muu.user_fullname, trh.rh_subject ,CONCAT(muu.user_fullname,' on ',date(trh.created_date), ' Reason ', trh.rh_subject) as Reason, tap.appointment_followup_timeline_id, mbs.service_name,mp.patient_id,tt.isemrfinal " +
                        " FROM tbill_bill_service  tbbs LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                        " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                        " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                        " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                        " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                        " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                        " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                        " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                        " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                        " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                        " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                        " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                        " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                        " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                        " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                        " LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                        " LEFT JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
                        " inner JOIN trn_appointment tap ON tap.appointment_timeline_id = tt.timeline_id " +
                        " LEFT JOIN temr_referral_history trh ON trh.rh_id = tap.refer_history_id " +
                        " LEFT JOIN mst_staff msst ON msst.staff_id = trh.rh_staff_id " +
                        " LEFT JOIN mst_user muu ON muu.user_id = msst.staff_user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tb.tbill_unit_id = " + unitid +
                        " and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "' ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                if (patientName != "") {
                    query += " and concat(mu.user_firstname,' ', mu.user_lastname) LIKE '%" + patientName + "%' ";
                }
                if (mrNo != "") {
                    query += " and mp.patient_mr_no LIKE '%" + mrNo + "%' ";
                }
                if (mobNo != "") {
                    query += " and mu.user_mobile LIKE '%" + mobNo + "%' ";
                }
                CountQuery = " select count(*) from ( " + query + " ) as combine ";
                query = query + " order by tbbs.bs_id desc limit " + limit + " offset " + offset;
                System.out.println("Patient Waiting Room Query 3 " + query);
                queue = entityManager.createNativeQuery(query).getResultList();
                BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
                Map<String, Object> map = new HashMap<>();
                map.put("content", queue);
                map.put("count", cc);
                return map;
//                return queue;
            } else {
                // from to
//
//                String query = " SELECT     tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" +
//                        " FROM TbillBillService  tbbs " +
//                        "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " +
//                        " tbbs.bsStaffId.staffId in (" + removeLastChar(staffList) + ")  and  tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsDate BETWEEN '" + startdate + "' and '" + enddate + "' ";
//
//
//                if(patientStatus == 4 || patientStatus == 5)
//                {
//                    query = query +  " and tbbs.bsStatus = "+patientStatus;
//                }
//                else
//                {
//                    query = query +  " and tbbs.bsStatus != 5 and  tbbs.bsStatus != 4";
//                }
//                query = query+ " order by tbbs.bsId desc  ";
//                System.out.println(query);
//                queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number," +
                        " tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname  as fname,mu.user_middlename  as mname," +
                        " mu.user_lastname  as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name," +
                        " mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name," +
                        " msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename," +
                        " us.user_lastname, tb.bill_outstanding,tb.bill_net_payable ,tbbs.bill_cancel_reason ,tbbs.bs_id," +
                        " ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card, " +
                        " case when mv.is_virtual=1 then 'true' ELSE 'false' end ,tap.appointment_followup_timeline_id, " +
                        " mbs.service_name,mp.patient_id,tt.isemrfinal " +
                        " FROM tbill_bill_service  tbbs " +
                        " LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                        " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                        " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                        " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                        " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                        " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                        " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                        " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                        " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                        " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                        " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                        " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                        " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                        " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                        " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                        " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                        " LEFT JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
                        " inner JOIN trn_appointment tap ON tap.appointment_timeline_id = tt.timeline_id " +
                        " LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tb.tbill_unit_id = " + unitid +
                        " and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "' ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                if (patientName != "") {
                    query += " and concat(mu.user_firstname,' ', mu.user_lastname) LIKE '%" + patientName + "%' ";
                }
                if (mrNo != "") {
                    query += " and mp.patient_mr_no LIKE '%" + mrNo + "%' ";
                }
                if (mobNo != "") {
                    query += " and mu.user_mobile LIKE '%" + mobNo + "%' ";
                }
                CountQuery = " select count(*) from ( " + query + " ) as combine ";
                query = query + " order by tbbs.bs_id desc limit " + limit + " offset " + offset;
                System.out.println("Patient Waiting Room Query 4 " + query);
                queue = entityManager.createNativeQuery(query).getResultList();
                BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
                Map<String, Object> map = new HashMap<>();
                map.put("content", queue);
                map.put("count", cc);
                return map;
//                return queue;
            }
        }

    }

    @GetMapping
    @RequestMapping("byperformidid")
    public List<?> byperformidid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "staffid", required = false) Long staffid, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid, @RequestParam(value = "startdate", required = false, defaultValue = "0") String startdate, @RequestParam(value = "enddate", required = false, defaultValue = "0") String enddate, @RequestParam(value = "staffList", required = false, defaultValue = " ") String staffList) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        List<String> queue = new ArrayList<String>();
        System.out.println("----------staffList-------------------");
        System.out.println(staffList);
        Date sdate = new Date();
        Date edate = new Date();
        if (startdate.equals(enddate) || enddate.equals(startdate)) {
            //today
            String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname,mu.user_middlename,mu.user_lastname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,tbbs.bs_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mpt.pt_name,mps.ps_name,(select mtitle.title_name FROM mst_staff mstaff LEFT JOIN mst_user muser on muser.user_id =mstaff.staff_user_id   LEFT JOIN mst_title mtitle ON mtitle.title_id = muser.user_title_id where mstaff.staff_id = " + staffid + "),(select muser.user_firstname FROM mst_staff mstaff LEFT JOIN mst_user muser on muser.user_id =mstaff.staff_user_id where mstaff.staff_id =  " + staffid + " ),(select muser.user_lastname FROM mst_staff mstaff LEFT JOIN mst_user muser on muser.user_id =mstaff.staff_user_id  where mstaff.staff_id =  " + staffid + " ),tb.bill_outstanding,tb.bill_net_payable ,tbbs.bill_cancel_reason ,tbbs.bs_id,ms.staff_id" + " FROM tbill_bill_service  tbbs " + "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " + " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " + " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" + " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " + "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                    "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " + "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " + "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + "  where tbbs.is_active = 1 and tbbs.is_deleted = 0 and  tbbs.bs_cancel= 0 " + "  and " + " tbbs.bs_staff_id= " + staffid + " and tb.tbill_unit_id = " + unitid + "  and tbbs.bs_date= CURDATE()  order by tbbs.bs_id desc limit " + limit + " offset " + offset;
            queue = entityManager.createNativeQuery(query).getResultList();
//            List<PatientQueueDto> dto = new ArrayList<>();
//            for(Object obj:queue){
//               PatientQueueDto temp=new PatientQueueDto();
//                obj.
//                dto.add(temp);
//            }
//            for(int i=0;i<= queue.size();i++)
//            {
//              String q = queue[0];0
//                new PatientQueueDto(queue[0]);
//            }
            return queue;

        } else {
            // from to
            String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname,mu.user_middlename,mu.user_lastname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,tbbs.bs_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mpt.pt_name,mps.ps_name,(select mtitle.title_name FROM mst_staff mstaff LEFT JOIN mst_user muser on muser.user_id =mstaff.staff_user_id   LEFT JOIN mst_title mtitle ON mtitle.title_id = muser.user_title_id where mstaff.staff_id = " + staffid + "),(select muser.user_firstname FROM mst_staff mstaff LEFT JOIN mst_user muser on muser.user_id =mstaff.staff_user_id where mstaff.staff_id =  " + staffid + " ),(select muser.user_lastname FROM mst_staff mstaff LEFT JOIN mst_user muser on muser.user_id =mstaff.staff_user_id  where mstaff.staff_id =  " + staffid + " ) ,tb.bill_outstanding,tb.bill_net_payable,tbbs.bill_cancel_reason ,tbbs.bs_id ,ms.staff_id" + " FROM tbill_bill_service  tbbs " + "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " + " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " + " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" + " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " + "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " + "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " + "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " + "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + "  where tbbs.is_active = 1 and tbbs.is_deleted = 0 and  tbbs.bs_cancel= 0 " + "  and " + " tbbs.bs_staff_id= " + staffid + " and tb.tbill_unit_id = " + unitid + "  and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "'  order by tbbs.bs_id desc limit " + limit + " offset " + offset;
            queue = entityManager.createNativeQuery(query).getResultList();
            return queue;

        }

    }

    @GetMapping
    @RequestMapping("byperformididcnt")
    public BigInteger byperformididcnt(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "staffid", required = false) Long staffid, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid, @RequestParam(value = "startdate", required = false, defaultValue = "0") String startdate, @RequestParam(value = "enddate", required = false, defaultValue = "0") String enddate, @RequestParam(value = "staffList", required = false, defaultValue = " ") String staffList) {
        TenantContext.setCurrentTenant(tenantName);
        BigInteger count;
        System.out.println("------u---staffList---------------------");
        System.out.println(staffList);
        Date sdate = new Date();
        Date edate = new Date();
        if (startdate.equals(enddate) || enddate.equals(startdate)) {
            //todaya
            System.out.println("-----TODAY------------------");
            String query = " SELECT count(tbbs.bs_id)" + " FROM tbill_bill_service  tbbs " + "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " + " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " + " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" + " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " + "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " + "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " + "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " + "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + "  where tbbs.is_active = 1 and tbbs.is_deleted = 0 and  tbbs.bs_cancel= 0 " + "  and " + " tbbs.bs_staff_id= " + staffid + " and tb.tbill_unit_id = " + unitid + "  and tbbs.bs_date= CURDATE()  ";
            count = (BigInteger) entityManager.createNativeQuery(query).getSingleResult();
            System.out.println(count);
            return count;

        } else {
            // from to
            System.out.println("-----from to------------------");
            String query = " SELECT count(tbbs.bs_id)" + " FROM tbill_bill_service  tbbs " + "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " + " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " + " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" + " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " + "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " + "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " + "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " + "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + "  where tbbs.is_active = 1 and tbbs.is_deleted = 0 and  tbbs.bs_cancel= 0 " + "  and " + " tbbs.bs_staff_id= " + staffid + " and tb.tbill_unit_id = " + unitid + "  and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "'  ";
            count = (BigInteger) entityManager.createNativeQuery(query).getSingleResult();
            System.out.println(count);
            return count;
        }
    }

    @GetMapping
    @RequestMapping("ListRecordByUnitIdCnt")
    public BigInteger ListRecordByUnitIdCnt(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid, @RequestParam(value = "staff", required = false, defaultValue = "0") long staff, @RequestParam(value = "startdate", required = false, defaultValue = "0") String startdate, @RequestParam(value = "enddate", required = false, defaultValue = "0") String enddate, @RequestParam(value = "staffList", required = false, defaultValue = " ") String staffList, @RequestParam(value = "patientStatus", required = false, defaultValue = "0") int patientStatus, @RequestParam(value = "createdById", required = false, defaultValue = "") Long createdById) {
        TenantContext.setCurrentTenant(tenantName);
        BigInteger count;
        if (staff != 0) {
            if (startdate.equals(enddate) || enddate.equals(startdate)) {
                //today
                String query = " SELECT count(tbbs.bs_id)" + " FROM tbill_bill_service  tbbs " + "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " + " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " + " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" + " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " + "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " + "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " + "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " + "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + "  where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " + "  and (tb.ipd_bill = false or mv.visit_status = 0) and" + " tbbs.bs_staff_id= " + staff + " and tb.tbill_unit_id = " + unitid + "  and tbbs.bs_date= CURDATE()  ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                count = (BigInteger) entityManager.createNativeQuery(query).getSingleResult();
            } else {
                // from to
                String query = " SELECT count(tbbs.bs_id)" + " FROM tbill_bill_service  tbbs " + "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " + " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " + " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" + " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " + "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " + "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " + "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " + "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + "  where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " + "  and (tb.ipd_bill = false or mv.visit_status = 0) and" + " tbbs.bs_staff_id= " + staff + " and tb.tbill_unit_id = " + unitid + "  and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "'  ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                count = (BigInteger) entityManager.createNativeQuery(query).getSingleResult();
            }
            return count;
        } else {
            if (startdate.equals(enddate) || enddate.equals(startdate)) {
                //today
                String query = " SELECT count(tbbs.bs_id)" + " FROM tbill_bill_service  tbbs " + "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " + " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " + " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" + " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " + "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " + "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " + "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " + "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + "  where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " + "  and (tb.ipd_bill = false or mv.visit_status = 0) and" + " tbbs.bs_staff_id= " + staff + " and tb.tbill_unit_id = " + unitid + "  and tbbs.bs_date= CURDATE()  ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                count = (BigInteger) entityManager.createNativeQuery(query).getSingleResult();
                return count;
            } else {
                // from to
                String query = " SELECT count(tbbs.bs_id)" + " FROM tbill_bill_service  tbbs " + "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " + " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " + " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" + " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " + "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " + "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " + "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " + "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + "  where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " + "  and (tb.ipd_bill = false or mv.visit_status = 0) and" + " tbbs.bs_staff_id=  " + staff + " and tb.tbill_unit_id = " + unitid + "  and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "'  ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                count = (BigInteger) entityManager.createNativeQuery(query).getSingleResult();
                return count;
            }
        }

    }

    @GetMapping
    @RequestMapping("byfromdate")
    public Iterable<TbillBillService> byfromdate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "start", required = false) String start, @RequestParam(value = "end", required = false) String end, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid, @RequestParam(value = "staff", required = false, defaultValue = "0") long staff, @RequestParam(value = "staffList", required = false, defaultValue = " ") String staffList) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("start " + start);
        System.out.println("end " + end);
        System.out.println("page " + page);
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        if (staff == 0) {
            if (start.equals(end) || end.equals(start)) {
                //today
                return tbillBillServiceRepository.findAllByBsBillIdTbillUnitIdUnitIdEqualsAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(unitid, new Date(), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            } else {
                Date sdate = new Date();
                Date edate = new Date();
                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    sdate = sdf1.parse(start);
                    edate = sdf1.parse(end);

                } catch (Exception e) {
                }
                // from to
                return tbillBillServiceRepository.findAllByBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(unitid, sdate, edate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            }
        } else {
            if (start.equals(end) || end.equals(start)) {
                //today
                return tbillBillServiceRepository.findAllByBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(staff, unitid, new Date(), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            } else {
                Date sdate = new Date();
                Date edate = new Date();
                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    sdate = sdf1.parse(start);
                    edate = sdf1.parse(end);

                } catch (Exception e) {
                }
                // from to
                return tbillBillServiceRepository.findAllByBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(staff, unitid, sdate, edate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            }

        }

    }

    @GetMapping
    @RequestMapping("bymrno")
    public List<?> bymrno(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "pmrno", required = false) String pmrno, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("type Mr no ==>" + pmrno);
        // return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(pmrno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        List<String> queue = new ArrayList<String>();
        String query = " SELECT     tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" + " FROM TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and  (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo like  '%" + pmrno + "%' order by tbbs.bsId desc  ";
        queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
        return queue;
    }

    @GetMapping
    @RequestMapping("bymrnocnt")
    public Long bymrnocnt(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "pmrno", required = false) String pmrno, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("type Mr no ==>" + pmrno);
        // return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(pmrno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        Long count;
        String query = " SELECT  count(tbbs.bsId)  " + " FROM TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo like '%" + pmrno + "%'   ";
        count = (Long) entityManager.createQuery(query).getSingleResult();
        return count;
    }

    @GetMapping
    @RequestMapping("bypnamecnt")
    public Long bypnamecnt(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "pname", required = false) String pname, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        Long count;
        String query = " SELECT  count(tbbs.bsId)  " + " FROM TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and (tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname like '%" + pname + "%' or tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname like '%" + pname + "%'  or tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename like '%" + pname + "%' )  ";
        count = (Long) entityManager.createQuery(query).getSingleResult();
        return count;

    }

    @GetMapping
    @RequestMapping("bymnocnt")
    public Long bymnocnt(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "mno", required = false) String mno, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        // return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMobileContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(mno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        Long count;
        String query = " SELECT  count(tbbs.bsId)  " + " FROM TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile like '%" + mno + "%' ";
        count = (Long) entityManager.createQuery(query).getSingleResult();
        return count;

    }

    @GetMapping
    @RequestMapping("byuidcnt")
    public Long byuidcnt(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "uid", required = false) String uid, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        // return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserUidContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(uid, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        Long count;
        String query = " SELECT  count(tbbs.bsId)  " + " FROM TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0  (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userUid like '%" + uid + "%'  ";
        count = (Long) entityManager.createQuery(query).getSingleResult();
        return count;
    }

    @GetMapping
    @RequestMapping("bypname")
    public List<?> bypname(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "pname", required = false) String pname, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        List<String> queue = new ArrayList<String>();
        String query = " SELECT tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" + " FROM TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and (tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname like '%" + pname + "%' or tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname like '%" + pname + "%'  or tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename like '%" + pname + "%' ) order by tbbs.bsId desc  ";
        queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
        return queue;
    }

    @GetMapping
    @RequestMapping("bymno")
    public List<?> bymno(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "mno", required = false) String mno, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        // return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMobileContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(mno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        List<String> queue = new ArrayList<String>();
        String query = " SELECT     tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" + " FROM TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile like '%" + mno + "%' order by tbbs.bsId desc  ";
        queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
        return queue;
    }

    @GetMapping
    @RequestMapping("byuid")
    public List<?> byuid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "uid", required = false) String uid, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        // return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserUidContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(uid, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        List<String> queue = new ArrayList<String>();
        String query = " SELECT tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" + " FROM TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userUid like '%" + uid + "%' order by tbbs.bsId desc  ";
        queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
        return queue;
    }
//    @GetMapping
//    @RequestMapping("searchList")
//    public Map<String, Object> searchList(@RequestHeader("X-tenantId") String tenantName, @RequestBody PatientQueueDto patientQueueDto, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
//        System.out.println("patientQueueDto ==>" + patientQueueDto.toString());
//        // return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(pmrno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
//        int limit = Integer.parseInt(size);
//        List<String> queue = new ArrayList<String>();
//
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = formatter.format(date);
//        if (patientQueueDto.getFromdate().equals("") || patientQueueDto.getFromdate().equals("null")) {
//            patientQueueDto.setFromdate(strDate);
//        }
//        if (patientQueueDto.getTodate().equals("") || patientQueueDto.getTodate().equals("null")) {
////            todate = strDate;
//            patientQueueDto.setTodate(strDate);
//        }
//
//
//        String query = "SELECT tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo," +
//                " tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName," +
//                " tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName," +
//                " tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename," +
//                " tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile," +
//                " tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth," +
//                " tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName," +
//                " tbbs.bsBillId.billVisitId.createdDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName," +
//                " tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit," +
//                " tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname," +
//                " tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, " +
//                " tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark, tbbs.bsBillId.tbillUnitId,tbbs.bsBillId.billVisitId.sponsorCombination,case when tbbs.bsBillId.billVisitId.isVirtual=1 then 'true' ELSE 'false' end ,tbbs.bsBillId.billVisitId.visitWaitingTime,tbbs.bsBillId.billVisitId.visitPatientId,CONCAT(tbbs.bsBillId.billVisitId.visitWts,'-',tbbs.bsBillId.billVisitId.visitWte)" + " FROM TbillBillService  tbbs " +
//                " where tbbs.isActive = 1 and tbbs.isDeleted = 0 and tbbs.bsServiceId.serviceIsConsultion = 1 and tbbs.bsCancel= 0 " +
//                " and  (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) " +
//                " and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + " ";
//        if (patientQueueDto.getPatientmrno() != null && !patientQueueDto.getPatientmrno().equals("")) {
//            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo like  '%" + patientQueueDto.getPatientmrno() + "%' ";
//        }
//        if (patientQueueDto.getPatientfname() != null && !patientQueueDto.getPatientfname().equals("")) {
//            query += "  and (tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname like '%" + patientQueueDto.getPatientfname() + "%' or tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname like '%" + patientQueueDto.getPatientfname() + "%'  ) ";
//        }
//        if (patientQueueDto.patientmno != null && !patientQueueDto.patientmno.equals("")) {
//            query += " and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile like '%" + patientQueueDto.patientmno + "%'  ";
//        }
//        if (patientQueueDto.getPatientUid() != null && !patientQueueDto.getPatientUid().equals("")) {
//            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userUid like '%" + patientQueueDto.getPatientUid() + "%' ";
//        }
//        if (patientQueueDto.getStaffId() != null && !patientQueueDto.getStaffId().equals("")) {
//            query += "  and tbbs.bsStaffId.staffId= " + patientQueueDto.getStaffId() + " ";
//        }
//        if (patientQueueDto.getPatientbsstatus() == 4 || patientQueueDto.getPatientbsstatus() == 5) {
//            query += " and tbbs.bsStatus = " + patientQueueDto.getPatientbsstatus();
//        } else {
//            query += " and tbbs.bsStatus != 5 and  tbbs.bsStatus != 4";
//        }
//
//        query += "   and tbbs.bsDate BETWEEN '" + patientQueueDto.getFromdate() + "' and '" + patientQueueDto.getTodate() + "' ";
//
//        query += " order by tbbs.bsId asc ";
//        List<String> queuecount = entityManager.createQuery(query).getResultList();
//
////        query += " limit " + limit + ", " + offset;
//
//        System.out.println("query 1==>" + query);
//
////        queue = entityManager.createQuery(query).getResultList();
//        queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
////        BigInteger cc = (BigInteger)entityManager.createQuery(query).getSingleResult();
//
////        String CountQuery = " select count(*) from ( " + query + " ) as combine ";
////        BigInteger cc = (BigInteger)entityManager.createQuery(CountQuery).getSingleResult();
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("content", queue);
//        map.put("count", queuecount.size());
//
//        return map;
//    }

    @GetMapping
    @RequestMapping("searchList")
    public Map<String, Object> searchList(@RequestHeader("X-tenantId") String tenantName, @RequestBody PatientQueueDto patientQueueDto, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("patientQueueDto ==>" + patientQueueDto.toString());
        // return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(pmrno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        List<String> queue = new ArrayList<String>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (patientQueueDto.getFromdate().equals("") || patientQueueDto.getFromdate().equals("null")) {
            patientQueueDto.setFromdate(strDate);
        } else {
            try {
//                Date date_f = formatter.parse(patientQueueDto.getFromdate());
//                patientQueueDto.setFromdate(formatter.format(date_f));
//                LocalDate date_f = LocalDate.parse(patientQueueDto.getFromdate(), formatter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (patientQueueDto.getTodate().equals("") || patientQueueDto.getTodate().equals("null")) {
//            todate = strDate;
            patientQueueDto.setTodate(strDate);
        }
//        else{
//            Date date_f = formatter.parse(patientQueueDto.getTodate());
//            patientQueueDto.setTodate(formatter.format(date_f));
//        }

/*
        String query = "SELECT tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo," +
                " tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName," +
                " tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName," +
                " tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename," +
                " tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile," +
                " tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth," +
                " tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName," +
                " tbbs.bsBillId.billVisitId.createdDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName," +
                " tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit," +
                " tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname," +
                " tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, " +
                " tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark, tbbs.bsBillId.tbillUnitId,tbbs.bsBillId.billVisitId.sponsorCombination,case when tbbs.bsBillId.billVisitId.isVirtual=1 then 'true' ELSE 'false' end ,tbbs.bsBillId.billVisitId.visitWaitingTime,tbbs.bsBillId.billVisitId.visitPatientId,CONCAT(tbbs.bsBillId.billVisitId.visitWts,'-',tbbs.bsBillId.billVisitId.visitWte)" + " FROM TbillBillService  tbbs " +
                " where tbbs.isActive = 1 and tbbs.isDeleted = 0 and tbbs.bsServiceId.serviceIsConsultion = 1 and tbbs.bsCancel= 0 " +
                " and  (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) " +
                " and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + " ";
        if (patientQueueDto.getPatientmrno() != null && !patientQueueDto.getPatientmrno().equals("")) {
            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo like  '%" + patientQueueDto.getPatientmrno() + "%' ";
        }
        if (patientQueueDto.getPatientfname() != null && !patientQueueDto.getPatientfname().equals("")) {
            query += "  and (tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname like '%" + patientQueueDto.getPatientfname() + "%' or tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname like '%" + patientQueueDto.getPatientfname() + "%'  ) ";
        }
        if (patientQueueDto.patientmno != null && !patientQueueDto.patientmno.equals("")) {
            query += " and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile like '%" + patientQueueDto.patientmno + "%'  ";
        }
        if (patientQueueDto.getPatientUid() != null && !patientQueueDto.getPatientUid().equals("")) {
            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userUid like '%" + patientQueueDto.getPatientUid() + "%' ";
        }
        if (patientQueueDto.getStaffId() != null && !patientQueueDto.getStaffId().equals("")) {
            query += "  and tbbs.bsStaffId.staffId= " + patientQueueDto.getStaffId() + " ";
        }
        if (patientQueueDto.getPatientbsstatus() == 4 || patientQueueDto.getPatientbsstatus() == 5) {
            query += " and tbbs.bsStatus = " + patientQueueDto.getPatientbsstatus();
        } else {
            query += " and tbbs.bsStatus != 5 and  tbbs.bsStatus != 4";
        }

        query += "   and tbbs.bsDate BETWEEN '" + patientQueueDto.getFromdate() + "' and '" + patientQueueDto.getTodate() + "' ";

        query += " order by tbbs.bsId asc ";

        System.out.println("query ==>" + query);

        queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
        List<String> queuecount = entityManager.createQuery(query).getResultList();
//        BigInteger cc = (BigInteger)entityManager.createQuery(query).getSingleResult();*/
        String query1 = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name," +
                "mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname  as lname,mu.user_mobile,ifnull(mu.user_age,0), ifnull(mu.user_month,0),ifnull(mu.user_day,0)," +
                "mg.gender_name,tbbs.bs_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name," +
                "mv.reason_visit,CASE WHEN con.country_id=tb.tbill_unit_id THEN 'Domestic' ELSE 'International' END,us.user_firstname,us.user_middlename,us.user_lastname, tb.bill_outstanding,tb.bill_net_payable ," +
                "tbbs.bill_cancel_reason ,tbbs.bs_id,ms.staff_id,mv.visit_remark,false,'test',case when mv.is_virtual=1 then 'true' ELSE 'false' end,ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte),  CASE WHEN  mv.is_visit_urgent=1 THEN 'true' ELSE 'false' END AS isUrgent, mv.is_visit_urgent_comments AS prioritycomment, CASE WHEN tap.is_appointment_referral=1 THEN 'true' ELSE 'false' END AS isReferral, tap.refer_history_id, trh.created_date AS rhDate, muu.user_fullname, trh.rh_subject, CONCAT(muu.user_fullname,' on ',date(trh.created_date), ' Reason ', trh.rh_subject) as Reason,  tap.appointment_followup_timeline_id, mbs.service_name, CASE WHEN mv.visit_is_in_consultation =1 THEN true ELSE false END AS visit_is_in_consultation" +
                " FROM tbill_bill_service  tbbs " +
                " LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id  LEFT JOIN mst_city ct ON mu.user_city_id = ct.city_id " +
                " LEFT JOIN mst_state st ON ct.city_state_id = st.state_id " +
                " LEFT JOIN mst_country con ON st.state_country_id = con.country_id " +
                " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                " LEFT JOIN temr_timeline tm ON tm.timeline_visit_id = mv.visit_id  " +
                " LEFT JOIN mst_staff_staff_department_id sdept ON sdept.mst_staff_staff_id = tbbs.bs_staff_id " +
                " LEFT JOIN mst_department mstd ON mstd.department_id = sdept.staff_department_id_department_id " +
                " LEFT JOIN mst_staff_staff_sd_id ssubd ON ssubd.mst_staff_staff_id = tbbs.bs_staff_id " +
                " LEFT JOIN mst_sub_department mstsb ON mstsb.sd_id = ssubd.staff_sd_id_sd_id " +
                " LEFT JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
                " inner JOIN trn_appointment tap ON tap.appointment_timeline_id = tt.timeline_id " +
                " LEFT JOIN temr_referral_history trh ON trh.rh_id = tap.refer_history_id " +
                " LEFT JOIN mst_staff msst ON msst.staff_id = trh.rh_staff_id " +
                " LEFT JOIN mst_user muu ON muu.user_id = msst.staff_user_id " +
                "  where tbbs.is_active = 1 and tbbs.is_deleted = 0 and  tbbs.bs_cancel= 0 " +
                " and (tb.ipd_bill = false or mv.visit_status = 0) and mbs.service_is_consultion = 1  ";
        if (patientQueueDto.getCreatedById() != 0) {
            query1 += " and mv.createdby_id =" + patientQueueDto.getCreatedById();
        }
//        String query = " SELECT     tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" + " FROM TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and  (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + " " ;
        if (!patientQueueDto.getVisitIdList().equals("null")) {
//            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo like  '%" + patientQueueDto.getPatientmrno() + "%' ";
            query1 += " and mv.visit_id in (" + patientQueueDto.getVisitIdList() + ") ";
        }
        if (patientQueueDto.getPatientmrno() != null && !patientQueueDto.getPatientmrno().equals("")) {
//            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo like  '%" + patientQueueDto.getPatientmrno() + "%' ";
            query1 += "  and mp.patient_mr_no like  '%" + patientQueueDto.getPatientmrno() + "%' ";
        }
        if (patientQueueDto.getPatientfname() != null && !patientQueueDto.getPatientfname().equals("")) {
//            query+="  and (tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname like '%" + patientQueueDto.getPatientfname() + "%' or tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname like '%" + patientQueueDto.getPatientfname() + "%'  ) ";
            query1 += "  and (mu.user_firstname like '%" + patientQueueDto.getPatientfname() + "%' or mu.user_lastname like '%" + patientQueueDto.getPatientfname() + "%'  ) ";
        }
        if (patientQueueDto.patientmno != null && !patientQueueDto.patientmno.equals("")) {
//            query +=" and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile like '%" + patientQueueDto.patientmno + "%'  ";
            query1 += " and mu.user_mobile like '%" + patientQueueDto.patientmno + "%'  ";
        }
        if (patientQueueDto.getPatientUid() != null && !patientQueueDto.getPatientUid().equals("")) {
//            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userUid like '%" + patientQueueDto.getPatientUid() + "%' ";
            query1 += "  and mu.user_uid like '%" + patientQueueDto.getPatientUid() + "%' ";
        }
        if (patientQueueDto.getUserDrivingNo() != null && !patientQueueDto.getUserDrivingNo().equals("")) {
//            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userUid like '%" + patientQueueDto.getPatientUid() + "%' ";
            query1 += "  and mu.user_driving_no like '%" + patientQueueDto.getUserDrivingNo() + "%' ";
        }
        if (patientQueueDto.getStaffId() != null && !patientQueueDto.getStaffId().equals("")) {
//            query1ry += "  and tbbs.bsStaffId.staffId= " + patientQueueDto.getStaffId() + " " ;
            query1 += "  and tbbs.bs_staff_id = " + patientQueueDto.getStaffId() + " ";
//            MstStaff mststaff = mstStaffRepository.getById(Long.parseLong(patientQueueDto.getStaffId()));
//            query1 += " and temrc.consultation_cluster_id = " + mststaff.getStaffClusterId().getClusterId();
        }
        if (patientQueueDto.getHubId() != null && !patientQueueDto.getHubId().equals("")) {
            query1 += " and tap.hub_id = '" + patientQueueDto.getHubId() + "'";
        }
        if (patientQueueDto.getPatientbsstatus() == 4 || patientQueueDto.getPatientbsstatus() == 5) {
            query1 += " and tbbs.bs_status = " + patientQueueDto.getPatientbsstatus();
        } else {
            query1 += " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
        }
        if (patientQueueDto.getPatientbsstatus() == 4) {
            query1 += " and tm.isemrfinal=true ";

        }
//        query += "   and (tbbs.bsDate BETWEEN '" + patientQueueDto.getFromdate() + "' and '" + patientQueueDto.getTodate() + "' ) ";
        query1 += "   and (date(tbbs.bs_date) BETWEEN '" + patientQueueDto.getFromdate() + "' and '" + patientQueueDto.getTodate() + "' ) ";
//        query1 += "   and (date(tbbs.bs_date) BETWEEN DATE_FORMAT(" + patientQueueDto.getFromdate() + ", '%Y-%m-%d') and "  + " DATE_FORMAT(" + patientQueueDto.getTodate() + ", '%Y-%m-%d')";
//        query += " order by tbbs.bsId asc ";
        query1 += " GROUP BY mv.visit_id  order by tbbs.bs_id asc ";
        String CountQuery = " select count(*) from ( " + query1 + " ) as combine ";
        query1 += " limit " + offset + " ," + limit;
        System.out.println("query ==>1" + query1);
        List<Object[]> objList = entityManager.createNativeQuery(query1).getResultList();
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        Map<String, Object> map = new HashMap<>();
        map.put("content", objList);
        map.put("count", cc);
        return map;
    }


  /*  @GetMapping
    @RequestMapping("searchList")
    public HashMap<String,Object> searchList(@RequestHeader("X-tenantId") String tenantName, @RequestBody PatientQueueDto patientQueueDto, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid, @RequestParam(value = "loginStaff", required = false, defaultValue = "0") Long loginStaff) {
        System.out.println("patientQueueDto ==>" + patientQueueDto.toString());
        // return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(pmrno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        List<String> queue = new ArrayList<String>();
        List<Object[]> list = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<String,Object>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (patientQueueDto.getFromdate().equals("") || patientQueueDto.getFromdate().equals("null")) {
            patientQueueDto.setFromdate(strDate);
        }
        if (patientQueueDto.getTodate().equals("") || patientQueueDto.getTodate().equals("null")) {
//            todate = strDate;
            patientQueueDto.setTodate(strDate);
        }

        String query1 = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name," +
                "mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname  as lname,mu.user_mobile,ifnull(mu.user_age,0),ifnull(mu.user_month,0),ifnull(mu.user_day,0)," +
                "mg.gender_name,tbbs.bs_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name," +
                "mv.reason_visit,CASE WHEN con.country_id=tb.tbill_unit_id THEN 'Domestic' ELSE 'International' END,us.user_firstname,us.user_middlename,us.user_lastname, tb.bill_outstanding,tb.bill_net_payable ," +
                "tbbs.bill_cancel_reason ,tbbs.bs_id,ms.staff_id,mv.visit_remark,false, case when mv.is_virtual=1 then 'true' ELSE 'false' end,'test',ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte) " + " FROM tbill_bill_service  tbbs " +
                "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id  LEFT JOIN mst_city ct ON mu.user_city_id = ct.city_id " +
                " LEFT JOIN mst_state st ON ct.city_state_id = st.state_id " +
                " LEFT JOIN mst_country con ON st.state_country_id = con.country_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                " LEFT JOIN temr_consultation temrc ON temrc.consultation_visit_id = mv.visit_id " +
                " LEFT JOIN temr_timeline tm ON tm.timeline_visit_id = mv.visit_id  " +
                " LEFT JOIN mst_staff_staff_department_id sdept ON sdept.mst_staff_staff_id = tbbs.bs_staff_id " +
                " LEFT JOIN mst_department mstd ON mstd.department_id = sdept.staff_department_id_department_id " +
                " LEFT JOIN mst_staff_staff_sd_id ssubd ON ssubd.mst_staff_staff_id = tbbs.bs_staff_id " +
                " LEFT JOIN mst_sub_department mstsb ON mstsb.sd_id = ssubd.staff_sd_id_sd_id " +
                "  where tbbs.is_active = 1 and tbbs.is_deleted = 0 and  tbbs.bs_cancel= 0 and ta.appointment_is_cancelled = 0 " + "  and (tb.ipd_bill = false or mv.visit_status = 0)  " ;

//        String query = " SELECT     tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" + " FROM TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and  (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + " " ;
        if(patientQueueDto.getPatientmrno() != null && !patientQueueDto.getPatientmrno().equals("")){
//            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo like  '%" + patientQueueDto.getPatientmrno() + "%' ";
            query1 += "  and mp.patient_mr_no like  '%" + patientQueueDto.getPatientmrno() + "%' ";
        }
        if(patientQueueDto.getPatientfname() != null && !patientQueueDto.getPatientfname().equals("")){
//            query+="  and (tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname like '%" + patientQueueDto.getPatientfname() + "%' or tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname like '%" + patientQueueDto.getPatientfname() + "%'  ) ";
            query1+="  and (mu.user_firstname like '%" + patientQueueDto.getPatientfname() + "%' or mu.user_lastname like '%" + patientQueueDto.getPatientfname() + "%'  ) ";
        }
        if(patientQueueDto.patientmno != null && !patientQueueDto.patientmno.equals("") ){
//            query +=" and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile like '%" + patientQueueDto.patientmno + "%'  ";
            query1 +=" and mu.user_mobile like '%" + patientQueueDto.patientmno + "%'  ";
        }
        if(patientQueueDto.getPatientUid() != null && !patientQueueDto.getPatientUid().equals("")){
//            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userUid like '%" + patientQueueDto.getPatientUid() + "%' ";
            query1 += "  and mu.user_uid like '%" + patientQueueDto.getPatientUid() + "%' ";
        }
        if(patientQueueDto.getStaffId() !=null && !patientQueueDto.getStaffId().equals("")){
//            query1ry += "  and tbbs.bsStaffId.staffId= " + patientQueueDto.getStaffId() + " " ;
            query1 += "  and tbbs.bs_staff_id = " + patientQueueDto.getStaffId() + " " ;
//            MstStaff mststaff = mstStaffRepository.getById(Long.parseLong(patientQueueDto.getStaffId()));
//            query1 += " and temrc.consultation_cluster_id = " + mststaff.getStaffClusterId().getClusterId();
        }
        if (patientQueueDto.getPatientbsstatus() == 4 || patientQueueDto.getPatientbsstatus() == 5) {
//            query += " and tbbs.bsStatus = " + patientQueueDto.getPatientbsstatus();
            query1 += " and tbbs.bs_status = " + patientQueueDto.getPatientbsstatus();
        }
        else {
//            query += " and tbbs.bsStatus != 5 and  tbbs.bsStatus != 4";
            query1 += " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
        }

        if (Propertyconfig.getVIBA().trim().equalsIgnoreCase("true")) {
            query1 += " and ta.payment_status = 1 ";
        }
//        query += "   and (tbbs.bsDate BETWEEN '" + patientQueueDto.getFromdate() + "' and '" + patientQueueDto.getTodate() + "' ) ";
        query1 += "   and (date(ta.appointment_date) BETWEEN '" + patientQueueDto.getFromdate() + "' and '" + patientQueueDto.getTodate() + "' ) ";

//        query += " order by tbbs.bsId asc ";
        query1 += " GROUP BY mv.visit_id  order by tbbs.bs_id desc ";
        String CountQuery = " select count(*) from ( " + query1 + " ) as combine ";

        query1 += " limit " + offset +" ," + limit ;


//        System.out.println("query ==>" + query);
        System.out.println("query ==>" + query1);
//        queue = entityManager.createQuery(query1).setFirstResult(Integer.parseInt(page)).setMaxResults(Integer.parseInt(size)).getResultList();
//        queue = entityManager.createNativeQuery(query1).getResultList();

        List<Object[]> objList = entityManager.createNativeQuery(query1).getResultList();
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();

        if(loginStaff != 0 ){
            for (Object[] obj : objList) {
                ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
                temp.add(checkPatientConsultedtoDoctor(Long.parseLong(obj[37].toString()),loginStaff));
                list.add(temp.toArray());
            }

            map.put("list",list);
        }else{
            map.put("list",objList);
        }
        map.put("count",cc.longValue());
        return map;
    }*/


  /*  @GetMapping
    @RequestMapping("searchListCount")
    public Long SearchListCount(@RequestHeader("X-tenantId") String tenantName, @RequestBody PatientQueueDto patientQueueDto, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid) {
        System.out.println("patientQueueDto ==>" + patientQueueDto.toString());
        // return tbillBillServiceRepository.findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(pmrno, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (patientQueueDto.getFromdate().equals("") || patientQueueDto.getFromdate().equals("null")) {
            patientQueueDto.setFromdate(strDate);
        }
        if (patientQueueDto.getTodate().equals("") || patientQueueDto.getTodate().equals("null")) {
//            todate = strDate;
            patientQueueDto.setTodate(strDate);
        }
        Long count;

        String query = " SELECT  count(tbbs.bsId) " + " FROM  TbillBillService  tbbs " + "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and tbbs.bsServiceId.serviceIsConsultion = 1 and tbbs.bsCancel= 0 and  (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " + " tbbs.bsBillId.tbillUnitId.unitId = " + unitid + " ";
        if (patientQueueDto.getPatientmrno() != null) {
            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo like  '%" + patientQueueDto.getPatientmrno() + "%' ";
        }
        if (patientQueueDto.getPatientfname() != null && !patientQueueDto.getPatientfname().equals("")) {
            query += "  and (tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname like '%" + patientQueueDto.getPatientfname() + "%' or tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname like '%" + patientQueueDto.getPatientfname() + "%'  ) ";
        }
        if (patientQueueDto.patientmno != null && !patientQueueDto.patientmno.equals("")) {
            query += " and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile like '%" + patientQueueDto.patientmno + "%'  ";
        }
        if (patientQueueDto.getPatientUid() != null && !patientQueueDto.getPatientUid().equals("")) {
            query += "  and tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userUid like '%" + patientQueueDto.getPatientUid() + "%' ";
        }
        if (patientQueueDto.getStaffId() != null && !patientQueueDto.getStaffId().equals("")) {
            query += "  and tbbs.bsStaffId.staffId= " + patientQueueDto.getStaffId() + " ";
        }
        if (patientQueueDto.getPatientbsstatus() == 4 || patientQueueDto.getPatientbsstatus() == 5) {
            query += " and tbbs.bsStatus = " + patientQueueDto.getPatientbsstatus();
        } else {
            query += " and tbbs.bsStatus != 5 and  tbbs.bsStatus != 4";
        }
        query += "   and tbbs.bsDate BETWEEN '" + patientQueueDto.getFromdate() + "' and '" + patientQueueDto.getTodate() + "' ";
        query += " order by tbbs.bsId asc ";

        System.out.println("query ==>" + query);
        count = (Long) entityManager.createQuery(query).getSingleResult();
        return count;
    }*/

    @GetMapping
    @RequestMapping("getJoinCallNotification")
    public List<?> getJoinCallNotification(@RequestHeader("X-tenantId") String tenantName,
                                           @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid,
                                           @RequestParam(value = "staffList", required = false, defaultValue = " ") String staffList) {
        TenantContext.setCurrentTenant(tenantName);
        List<String> queue = new ArrayList<String>();
        //Queue Query
        String query1 = " SELECT mv.visit_id,mp.patient_mr_no,mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname as lname " +
                " FROM tbill_bill_service  tbbs " +
                " LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                " where tbbs.is_active = 1 " +
                " and tbbs.is_deleted = 0 " +
                " and  tbbs.bs_cancel= 0 " +
                " and mv.is_join_call = true " +
                " and (tb.ipd_bill = false " +
                "      or mv.visit_status = 0) " +
                " and tbbs.bs_staff_id in ( " + removeLastChar(staffList) + " )  " +
                " and tb.tbill_unit_id = " + unitid + "  " +
                " and tbbs.bs_date= CURDATE(); ";
        System.out.print(query1 + "\n");
        queue = entityManager.createNativeQuery(query1).getResultList();
        return queue;
    }

    @GetMapping
    @RequestMapping("ListofCalledPatientByUnitId")
    public Map<String, Object> ListofCalledPatientByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsId") String col, @RequestParam(value = "unitid", required = false, defaultValue = "0") long unitid, @RequestParam(value = "staff", required = false, defaultValue = "0") long staff, @RequestParam(value = "startdate", required = false, defaultValue = "0") String startdate, @RequestParam(value = "enddate", required = false, defaultValue = "0") String enddate, @RequestParam(value = "staffList", required = false, defaultValue = " ") String staffList, @RequestParam(value = "patientStatus", required = false, defaultValue = "0") int patientStatus, @RequestParam(value = "createdById", required = false, defaultValue = "") Long createdById) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        List<String> queue = new ArrayList<String>();
        List<String> queuecount = new ArrayList<String>();
        String CountQuery = "";
        if (staff != 0) {
            if (startdate.equals(enddate) || enddate.equals(startdate)) {
                //today
//                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname,tb.bill_outstanding,tb.bill_net_payable  ,tbbs.bill_cancel_reason,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card,case when mv.is_virtual=1 then 'true' ELSE 'false' end,ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte)" +
                String query = " SELECT mv.visit_id,mp.patient_mr_no,mt.title_name,mu.user_firstname AS fname,IFNULL(mu.user_middlename,'') AS mname,mu.user_lastname AS lname,mu.user_age,mg.gender_name,us.user_firstname,us.user_lastname,mv.visit_wte " +
                        " FROM tbill_bill_service  tbbs LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                        " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                        " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                        " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                        " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                        " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" +
                        " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                        " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                        " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                        " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                        " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                        " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                        " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                        " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                        " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                        " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                        " LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tbbs.bs_staff_id= " + staff +
                        " and tb.tbill_unit_id = " + unitid + "  and (date(tbbs.bs_date)= CURDATE()) and mv.visit_is_called = 1 ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                queuecount = entityManager.createNativeQuery(query).getResultList();
                query = query + " order by tbbs.bs_id desc  limit " + limit + " offset " + offset;
                System.out.println("Notification call => " + query);
                queue = entityManager.createNativeQuery(query).getResultList();

            } else {
                // from to
//                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname  as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname, tb.bill_outstanding,tb.bill_net_payable ,tbbs.bill_cancel_reason ,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card,case when mv.is_virtual=1 then 'true' ELSE 'false' end,ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte)" +
                String query = " SELECT mv.visit_id,mp.patient_mr_no,mt.title_name,mu.user_firstname AS fname,IFNULL(mu.user_middlename,'') AS mname,mu.user_lastname AS lname,mu.user_age,mg.gender_name,us.user_firstname,us.user_lastname,mv.visit_wte " +
                        " FROM tbill_bill_service  tbbs LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                        " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                        " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                        " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                        " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                        " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                        " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                        " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                        " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                        " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                        " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                        " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                        " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                        " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                        " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                        " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                        " LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tbbs.bs_staff_id= " + staff +
                        " and tb.tbill_unit_id = " + unitid + " and mv.visit_is_called= 1  and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "' ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                CountQuery = " select count(*) from ( " + query + " ) as combine ";
                queuecount = entityManager.createNativeQuery(query).getResultList();
                query = query + " order by tbbs.bs_id asc  limit " + limit + " offset " + offset;
                System.out.println(query);
                queue = entityManager.createNativeQuery(query).getResultList();

            }
            /*BigInteger cc = (BigInteger)entityManager.createNativeQuery(CountQuery).getSingleResult();
            Map<String, Object> map = new HashMap<>();
            map.put("content", queue);
            map.put("count", cc);*/
//        BigInteger cc = (BigInteger)entityManager.createQuery(query).getSingleResult();
            Map<String, Object> map = new HashMap<>();
            map.put("content", queue);
            map.put("count", queuecount.size());
            return map;
//            return queue;
        } else {
            if (startdate.equals(enddate) || enddate.equals(startdate)) {
                //today
//                String query = " SELECT     tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" +
//                " FROM TbillBillService  tbbs " +
//                        "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " +
//                        " tbbs.bsStaffId.staffId in (" + removeLastChar(staffList) + ")  and  tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsDate= CURDATE()   ";
//
//
//                if(patientStatus == 4 || patientStatus == 5)
//                {
//                    query = query +  " and tbbs.bsStatus = "+patientStatus;
//                }
//                else
//                {
//                    query = query +  " and tbbs.bsStatus != 5 and  tbbs.bsStatus != 4";
//                }
//
//                query = query +  " order by tbbs.bsId desc ";
//                System.out.println(query);
//                queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
//                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname as fname,mu.user_middlename as mname,mu.user_lastname as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname,tb.bill_outstanding,tb.bill_net_payable  ,tbbs.bill_cancel_reason,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card,case when mv.is_virtual=1 then 'true' ELSE 'false' end,ifnull(mv.visit_waiting_time,0),mv.visit_patient_id,CONCAT(mv.visit_wts,'-',mv.visit_wte), mv.visit_wte" +
                String query = " SELECT mv.visit_id,mp.patient_mr_no,mt.title_name,mu.user_firstname AS fname,IFNULL(mu.user_middlename,'') AS mname,mu.user_lastname AS lname,mu.user_age,mg.gender_name,us.user_firstname,us.user_lastname,mv.visit_wte " +
                        " FROM tbill_bill_service  tbbs LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " +
                        " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                        " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                        " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                        " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " +
                        " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                        " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                        " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                        " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                        " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                        " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                        " LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " +
                        " LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " +
                        " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                        " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                        " LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                        " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tb.tbill_unit_id = " + unitid + " and mv.visit_is_called =1 and (date(tbbs.bs_date)= CURDATE()) and mv.visit_notification_deleted = false ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                CountQuery = " select count(*) from ( " + query + " ) as combine ";
                query = query + " order by tbbs.bs_id asc  limit " + limit + " offset " + offset;
                System.out.println(query);
                queue = entityManager.createNativeQuery(query).getResultList();
                BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
                Map<String, Object> map = new HashMap<>();
                map.put("content", queue);
                map.put("count", cc);
                return map;
//                return queue;
            } else {
                // from to
//
//                String query = " SELECT     tbbs.bsBillId.billVisitId.visitId,tbbs.bsBillId.billVisitId.visitPatientId.patientMrNo,tbbs.bsStatus,tbbs.bsTokenNumber,tbbs.bsBillId.billId,tbbs.bsBillId.billTariffId.tariffName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userTitleId.titleName,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userFirstname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMiddlename,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userLastname,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMobile,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userAge,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userMonth,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userDay,tbbs.bsBillId.billVisitId.visitPatientId.patientUserId.userGenderId.genderName,tbbs.bsDate,tbbs.visitStartTime,tbbs.visitEndTime,tbbs.bsStaffId.staffId, tbbs.bsServiceId.serviceGroupId.groupDepartmentId.departmentName,tbbs.bsServiceId.serviceGroupId.groupSdId.sdName,tbbs.bsBillId.tbillUnitId.unitName,tbbs.bsBillId.billVisitId.reasonVisit,tbbs.bsBillId.billVisitId.visitPsId.psName,tbbs.bsStaffId.staffUserId.userTitleId.titleName,tbbs.bsStaffId.staffUserId.userFirstname,tbbs.bsStaffId.staffUserId.userLastname,tbbs.bsBillId.billOutstanding,tbbs.bsBillId.billNetPayable ,tbbs.billCancelReason,tbbs.bsId, tbbs.bsStaffId.staffId,tbbs.bsBillId.billVisitId.visitRemark" +
//                        " FROM TbillBillService  tbbs " +
//                        "  where tbbs.isActive = 1 and tbbs.isDeleted = 0 and  tbbs.bsCancel= 0 and (tbbs.bsBillId.ipdBill = false or tbbs.bsBillId.billVisitId.visitStatus = 0) and " +
//                        " tbbs.bsStaffId.staffId in (" + removeLastChar(staffList) + ")  and  tbbs.bsBillId.tbillUnitId.unitId = " + unitid + "  and tbbs.bsDate BETWEEN '" + startdate + "' and '" + enddate + "' ";
//
//
//                if(patientStatus == 4 || patientStatus == 5)
//                {
//                    query = query +  " and tbbs.bsStatus = "+patientStatus;
//                }
//                else
//                {
//                    query = query +  " and tbbs.bsStatus != 5 and  tbbs.bsStatus != 4";
//                }
//                query = query+ " order by tbbs.bsId desc  ";
//                System.out.println(query);
//                queue = entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
//                String query = " SELECT mv.visit_id,mp.patient_mr_no,tbbs.bs_status,tbbs.bs_token_number,tb.bill_id,mbt.tariff_name,mt.title_name,mu.user_firstname  as fname,mu.user_middlename  as mname,mu.user_lastname  as lname,mu.user_mobile,mu.user_age,mu.user_month,mu.user_day,mg.gender_name,mv.created_date,tbbs.visit_start_time,tbbs.visit_end_time,tbbs.bs_staff_id, md.department_name,msd.sd_name,u.unit_name,mv.reason_visit,mps.ps_name,us.user_firstname,us.user_middlename,us.user_lastname, tb.bill_outstanding,tb.bill_net_payable ,tbbs.bill_cancel_reason ,tbbs.bs_id,ms.staff_id,mv.visit_remark,mu.user_id,tsc.sc_sponcer_card, case when mv.is_virtual=1 then 'true' ELSE 'false' end" + " FROM tbill_bill_service  tbbs " + "   LEFT JOIN  tbill_bill tb ON tbbs.bs_bill_id = tb.bill_id " + "  LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " + " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " + "LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " + " LEFT JOIN  mbill_tariff  mbt ON tb.bill_tariff_id = mbt.tariff_id " + "  LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id" + " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " + "  LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " + "  LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " + " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " + "  LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " + "   LEFT JOIN  mst_patient_type  mpt ON mv.patient_type = mpt.pt_id " + "   LEFT JOIN  mst_patient_source  mps ON  mv.visit_ps_id = mps.ps_id " + "  LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " + "   LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " + " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id LEFT JOIN trn_sponsor_combination tsc on tsc.sc_user_id=mu.user_id " +
                String query = " SELECT mv.visit_id,mp.patient_mr_no,mt.title_name,mu.user_firstname AS fname,IFNULL(mu.user_middlename,'') AS mname,mu.user_lastname AS lname,mu.user_age,mg.gender_name,us.user_firstname,us.user_lastname,mv.visit_wte " +
                        " where tbbs.is_active = 1 and tbbs.is_deleted = 0 and mbs.service_is_consultion = 1 and tbbs.bs_cancel= 0 and mv.visit_is_called=1 " +
                        " and (tb.ipd_bill = false or mv.visit_status = 0) and tb.tbill_unit_id = " + unitid +
                        " and tbbs.bs_date BETWEEN '" + startdate + "' and '" + enddate + "' ";
                if (createdById != 0) {
                    query += " and mv.createdby_id =" + createdById;
                }
                if (patientStatus == 4 || patientStatus == 5) {
                    query = query + " and tbbs.bs_status = " + patientStatus;
                } else {
                    query = query + " and tbbs.bs_status != 5 and  tbbs.bs_status != 4";
                }
                CountQuery = " select count(*) from ( " + query + " ) as combine ";
                query = query + " order by tbbs.bs_id asc  limit " + limit + " offset " + offset;
                System.out.println(query);
                queue = entityManager.createNativeQuery(query).getResultList();
                BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
                Map<String, Object> map = new HashMap<>();
                map.put("content", queue);
                map.put("count", cc);
                return map;
//                return queue;
            }
        }

    }

}