package com.cellbeans.hspa.tbillbillservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface TbillBillServiceRepository extends JpaRepository<TbillBillService, Long> {
    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long unitid, Pageable page);

    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientMrNoContains(Long department_id, Pageable page);

    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContains(Long department_id, Pageable page);

    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMobileContains(Long department_id, Pageable page);

    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserUidContains(Long department_id, Pageable page);

    Page<TbillBillService> findByBsBillIdBillNumberContainsOrBsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String serviceName, Pageable page);

    Page<TbillBillService> findAllByBsServiceIdServiceGroupIdGroupDepartmentIdDepartmentIdEquals(Long department_id, Pageable page);

    Page<TbillBillService> findAllByBsStaffIdStaffDepartmentIdDepartmentIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long department_id, Pageable page);

    Page<TbillBillService> findAllByBsServiceIdServiceSgIdSgGroupIdGroupSdIdSdDepartmentIdDepartmentIdEquals(Long department_id, Pageable page);

    Page<TbillBillService> findAllByBsServiceIdServiceSgIdSgGroupIdGroupSdIdSdDepartmentIdDepartmentIdEqualsAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContains(Long department_id, String mrno, Pageable page);

    Page<TbillBillService> findAllByBsServiceIdServiceSgIdSgGroupIdGroupSdIdSdDepartmentIdDepartmentIdEqualsAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContains(Long department_id, String mrno, Pageable page);

    Page<TbillBillService> findAllByBsServiceIdServiceSgIdSgGroupIdGroupSdIdSdDepartmentIdDepartmentIdEqualsAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMobileContains(Long department_id, String mno, Pageable page);

    Page<TbillBillService> findAllByBsServiceIdServiceSgIdSgGroupIdGroupSdIdSdDepartmentIdDepartmentIdEqualsAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserUidContains(Long department_id, String mno, Pageable page);

    Page<TbillBillService> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillBillService> findAllByBsBillIdBillAdmissionIdAdmissionIdEqualsAndBsCancelFalseAndIsActiveTrueAndIsDeletedFalse(Long admission_id);

    @Query("SELECT DISTINCT a.bsBillId FROM TbillBillService a where a.isActive = 1 and a.isDeleted = 0")
    List<TbillBillService> findByBsServiceIdServiceSgIdSgGroupIdGroupIdAndIsActiveTrueAndIsDeletedFalse(long groupId, Pageable page);

    Page<TbillBillService> findAllByBsServiceIdServiceSgIdSgGroupIdGroupSdIdSdDepartmentIdDepartmentNameEquals(String department, Pageable page);

    List<TbillBillService> findByBsBillIdBillVisitIdVisitId(Long visit);
//    List<TbillBillService> findAllByBsBillIdBillIdEqualsAndActiveTrueAndIsDeletedFalse(Long billId);

    List<TbillBillService> findByBsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(long billId);

    List<TbillBillService> findByBsBillIdBillIdEqualsAndBsCancelFalseAndIsActiveTrueAndIsDeletedFalse(long billId);

    Page<TbillBillService> findByBsBillIdBillIdEqualsAndBsCancelFalseAndIsActiveTrueAndIsDeletedFalse(long billId, Pageable page);
    // TbillBillService findByBsServiceIdServiceSgIdSgGroupIdGroupSdIdSdDepartmentIdDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalse(String departmentName);

    @Query(value = "SELECT * FROM tbill_bill_service where  tbill_bill_service.bs_date = :date and tbill_bill_service.bs_token_number != '' order by bs_id desc LIMIT 1", nativeQuery = true)
    TbillBillService findOneByBsDateAndIsActiveTrueAndIsDeletedFalse(@Param("date") Date date);

    // Author: Priyanka
    //to find next patient by staff id
    TbillBillService findFirst1ByBsStaffIdStaffIdAndBsStatusAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(Long staffid, int status, Date date);

    // Author: Priyanka
    TbillBillService findByBsStaffIdStaffIdAndBsStatusAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(Long staffid, int status, Date date);

    //priyanka
    Page<TbillBillService> findAllByBsServiceIdServiceSgIdSgGroupIdGroupDepartmentIdDepartmentNameEquals(Pageable page, String department);

    //priyanka
    @Query(value = "SELECT * FROM tbill_bill_service INNER JOIN mst_staff ON tbill_bill_service.bs_staff_id = mst_staff.staff_id where mst_staff.staff_id= :staffid AND tbill_bill_service.created_date = :date order by bs_id ASC ", nativeQuery = true)
    List<TbillBillService> findAllByCreatedDateAndIsActiveTrueAndIsDeletedFalse(@Param("staffid") Long staffid, @Param("date") Date date);

    //priyankazz
    @Query(value = "SELECT * FROM tbill_bill_service INNER JOIN mst_staff ON tbill_bill_service.bs_staff_id = mst_staff.staff_id where mst_staff.staff_id= :staffid AND tbill_bill_service.created_date = :date  AND tbill_bill_service.bs_status != 2 order by bs_id ASC ", nativeQuery = true)
    List<TbillBillService> findAllByCreatedDateAndBsStatusAndIsActiveTrueAndIsDeletedFalse(@Param("staffid") Long staffid, @Param("date") Date date);

    //patientqueue list   4.4 Abhijeet
    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    //patientqueue list   vijay
    Page<TbillBillService> findAllByBsDateAndBsBillIdIpdBillFalseAndIsActiveTrueAndIsDeletedFalse(Date currentDate, Pageable page);

    // Mrno filter
    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(String mrno, long unitid, Pageable page);

    // name filter
    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(String mrno, long unitid, Pageable page);

    // mobileno filter
    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMobileContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(String mrno, long unitid, Pageable page);

    // nationalid filter
    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserUidContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(String mrno, long unitid, Pageable page);

    // staff filter
    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long mrno, long unitid, Pageable page);

    //fromdate todate filter
    @Query(value = "SELECT * FROM tbill_bill_service where bs_date BETWEEN ?1 AND ?2", nativeQuery = true)
    List<TbillBillService> fromdatetodate(String startdate, String enddate);
    // @Query("SELECT tbs FROM TbillBillService tbs where bsDate BETWEEN '"+start+"' AND '"+end+"'")
    // Page<TbillBillService> finddatefilter(Long mrno,Pageable page);
    // queue filter by   4.4 abhijeet
    // patient queue Dto Query
//    default Map<String, Object> findqueuelist(Pageable page)
//    {
//        Page<TbillBillService> tbillbillservice = findAllByBsBillIdIpdBillFalseAndIsActiveTrueAndIsDeletedFalse( page);
//        List<PatientQueueDto> patientqueuedto = tbillbillservice.getContent().stream().map(this::valueset).collect(Collectors.toList());
//        Map<String, Object> stringObjectMap = new HashMap<>();
//        stringObjectMap.put("totalPages", tbillbillservice.getTotalPages());
//        stringObjectMap.put("totalElements", tbillbillservice.getTotalElements());
//        stringObjectMap.put("content", patientqueuedto);
//        return stringObjectMap;
//    }
//
//    default PatientQueueDto valueset(TbillBillService tbillBillService )
//    {
//        PatientQueueDto patientqueuedto = new PatientQueueDto();
//        patientqueuedto.setPatientmrno(tbillBillService.getBsBillId().getBillVisitId().getVisitPatientId().getPatientMrNo());
//        patientqueuedto.setPatientvisitid(tbillBillService.getBsBillId().getBillVisitId().getVisitId());
//        patientqueuedto.setPatientdepartment(tbillBillService.getBsBillId().getBillVisitId().getVisitDepartmentId().getDepartmentName());
//        patientqueuedto.setPatientname(tbillBillService.getBsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserTitleId().getTitleName()+" "+
//                tbillBillService.getBsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserFirstname()+ " " +
//                tbillBillService.getBsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserMiddlename() + " "+
//                tbillBillService.getBsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserLastname());
//        patientqueuedto.setPatientendtime(tbillBillService.getVisitEndTime());
//        patientqueuedto.setPatientstarttime(tbillBillService.getVisitStartTime());
//        patientqueuedto.setPatientosa(tbillBillService.getBsBillId().getBillOutstanding());
//        patientqueuedto.setPatientnetpayamt(tbillBillService.getBsBillId().getBillNetPayable());
//        patientqueuedto.setPatientbsstatus(tbillBillService.getBsStatus());
//        if(tbillBillService.getBsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserGenderId().getGenderName() != null){
//            patientqueuedto.setPatientgender(tbillBillService.getBsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserGenderId().getGenderName());
//        }
//          patientqueuedto.setPatientage(tbillBillService.getBsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getEuserAge());
//        patientqueuedto.setPatientmno(tbillBillService.getBsBillId().getBillVisitId().getVisitPatientId().getPatientUserId().getUserMobile());
//        patientqueuedto.setPatyientbillid(tbillBillService.getBsBillId().getBillId());
//        patientqueuedto.setBillid(tbillBillService.getBsId());
//        patientqueuedto.setPatientstaffid(tbillBillService.getBsStaffId().getStaffId());
//
//        return patientqueuedto;
//    }
    //Page<PatientQueueDto> findAllByBsBillIdIpdBillFalseAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    //Author; Priyanaka
    //Author: Priyanka
    //  MRno
//     Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientMrNoContains(String mrno, Pageable page);
    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsStaffIdStaffDepartmentIdDepartmentNameEqualsAndBsDateEquals(String mrno, String deptname, Date date, Pageable page);

    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsStaffIdStaffDepartmentIdDepartmentNameNotAndBsDateEquals(String mrno, String deptname, Date date, Pageable page);

    //pname
    // Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContains(String mrno, Pageable page);
    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsAndBsStaffIdStaffDepartmentIdDepartmentNameEqualsAndBsDateEquals(String pname, String deptname, Date date, Pageable page);

    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsAndBsStaffIdStaffDepartmentIdDepartmentNameNotAndBsDateEquals(String pname, String deptname, Date date, Pageable page);

    //pmno
    //  Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMobileContains(String mno, Pageable page);
    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMobileContainsAndBsStaffIdStaffDepartmentIdDepartmentNameEqualsAndBsDateEquals(String mno, String deptname, Date date, Pageable page);

    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMobileContainsAndBsStaffIdStaffDepartmentIdDepartmentNameNotAndBsDateEquals(String mno, String deptname, Date date, Pageable page);

    //pnid
    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserUidContainsAndBsStaffIdStaffDepartmentIdDepartmentNameEqualsAndBsDateEquals(String mrno, String deptname, Date date, Pageable page);

    Page<TbillBillService> findAllByBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserUidContainsAndBsStaffIdStaffDepartmentIdDepartmentNameNotAndBsDateEquals(String mrno, String deptname, Date date, Pageable page);

    //between start and end date
    Page<TbillBillService> findAllByBsStaffIdStaffDepartmentIdDepartmentNameEqualsAndBsDateBetween(String deptname, Date startdate, Date enddate, Pageable page);

    Page<TbillBillService> findAllByBsStaffIdStaffDepartmentIdDepartmentNameNotAndBsDateBetween(String deptname, Date startdate, Date enddate, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill_service bs INNER JOIN mst_staff ms ON bs.bs_staff_id = ms.staff_id  Inner JOIN mst_department md ON ms.staff_department_id = md.department_id where md.department_name = :deptname AND bs.bs_date = :date order by bs_id desc  \n#pageable\n", countQuery = "SELECT count(*) FROM tbill_bill_service bs INNER JOIN mst_staff ms ON bs.bs_staff_id = ms.staff_id  Inner JOIN mst_department md ON ms.staff_department_id = md.department_id where md.department_name = :deptname AND bs.bs_date = :date ", nativeQuery = true)
    Page<TbillBillService> findAllByBsDateAndIsActiveTrueAndIsDeletedFalse(@Param("date") Date date, @Param("deptname") String deptname, Pageable page);

    @Query(value = "SELECT * FROM tbill_bill_service bs INNER JOIN mst_staff ms ON bs.bs_staff_id = ms.staff_id  Inner JOIN mst_department md ON ms.staff_department_id = md.department_id where md.department_name != 'Oncology' AND bs.bs_date = :date order by bs_id desc  \n#pageable\n", countQuery = "SELECT count(*) FROM tbill_bill_service bs INNER JOIN mst_staff ms ON bs.bs_staff_id = ms.staff_id  Inner JOIN mst_department md ON ms.staff_department_id = md.department_id where md.department_name != 'Oncology' AND bs.bs_date = :date ", nativeQuery = true)
    Page<TbillBillService> findAllByBsDateAndIsActiveTrueAndIsDeletedFalse(@Param("date") Date date, Pageable page);

    List<TbillBillService> findAllByBsTbrIdTbrIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TbillBillService> findAllByBsBillIdBillIdAndIsDeletedFalseAndIsActiveTrue(Long billId);

    default Boolean checkToCancelBill(Long billId) {
        List<TbillBillService> tbillBillServices = findByBsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(billId);
        List<TbillBillService> cancelledServices = new ArrayList<>();
        for (TbillBillService tbillBillService : tbillBillServices) {
            if (tbillBillService.getBsCancel()) {
                cancelledServices.add(tbillBillService);
            }
        }
        if (cancelledServices.size() == tbillBillServices.size()) {
            return true;
        } else {
            return false;
        }
    }

    //priyanka
    //MIS
    //Normal List
    @Query(value = "select * from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  \n#pageable\n", countQuery = "select count(*) from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  ", nativeQuery = true)
    Page<TbillBillService> findAllbyTariff(Pageable page);

    //today without tariff search
    @Query(value = "select * from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  and bs_date  = CURDATE()  \n#pageable\n", countQuery = "select count(*) from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  and bs_date  = CURDATE()  ", nativeQuery = true)
    Page<TbillBillService> findAllbyTariffTodaySearch(Pageable page);

    //from to without tariff search
    @Query(value = "select * from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  and bs_date BETWEEN :sdate and :edate \n#pageable\n", countQuery = "select count(*) from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0   and bs_date BETWEEN :sdate and :edate  ", nativeQuery = true)
    Page<TbillBillService> findAllbyTariffFromToDate(@Param("sdate") String sdate, @Param("edate") String edate, Pageable page);

    //today with tariff search
    @Query(value = "select * from tbill_bill_service tbs  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id INNER JOIN mbill_tariff mt ON mt.tariff_id = tb.bill_tariff_id  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  and bs_date  = CURDATE()  and mt.tariff_id= :tariff   \n#pageable\n", countQuery = "select count(*) from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  and bs_date  = CURDATE()  and mt.tariff_id= :tariff ", nativeQuery = true)
    Page<TbillBillService> findAllbyTariffTodaySearchWithTariff(@Param("tariff") int tariff, Pageable page);

    //from to  with tariff search
    @Query(value = "select * from tbill_bill_service tbs  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id INNER JOIN mbill_tariff mt ON mt.tariff_id = tb.bill_tariff_id  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0 and mt.tariff_id = :tariff  and bs_date BETWEEN :sdate and :edate     \n#pageable\n", countQuery = "select count(*) from tbill_bill_service tbs  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id INNER JOIN mbill_tariff mt ON mt.tariff_id = tb.bill_tariff_id  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0 and mt.tariff_id = :tariff  and bs_date BETWEEN :sdate and :edate  ", nativeQuery = true)
    Page<TbillBillService> findAllbyTariffFromToDatewithTariff(@Param("sdate") String sdate, @Param("edate") String edate, @Param("tariff") int tariff, Pageable page);

    //PRINT
    //Normal List
//    @Query(value = "select * from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0",nativeQuery = true)
//    List<TbillBillService>  findAllbyTariffprint();
    //Normal List
    @Query(value = "select * from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0    ", nativeQuery = true)
    List<TbillBillService> findAllbyTariffPrint();

    //today without tariff search
    @Query(value = "select * from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  and bs_date  = CURDATE()   ", nativeQuery = true)
    List<TbillBillService> findAllbyTariffTodaySearchPrint();

    //from to without tariff search
    @Query(value = "select * from tbill_bill_service tbs  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  and bs_date BETWEEN :sdate and :edate  ", nativeQuery = true)
    List<TbillBillService> findAllbyTariffFromToDatePrint(@Param("sdate") String sdate, @Param("edate") String edate);

    //today with tariff search
    @Query(value = "select * from tbill_bill_service tbs  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id INNER JOIN mbill_tariff mt ON mt.tariff_id = tb.bill_tariff_id  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  and bs_date  = CURDATE()  and mt.tariff_id= :tariff    ", nativeQuery = true)
    List<TbillBillService> findAllbyTariffTodaySearchWithTariffPrint(@Param("tariff") int tariff);

    //from to  with tariff search
    @Query(value = "select * from tbill_bill_service tbs  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id INNER JOIN mbill_tariff mt ON mt.tariff_id = tb.bill_tariff_id  where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0 and mt.tariff_id= :tariff  and bs_date BETWEEN  :sdate and :edate    ", nativeQuery = true)
    List<TbillBillService> findAllbyTariffFromToDatewithTariffPrint(@Param("sdate") String sdate, @Param("edate") String edate, @Param("tariff") int tariff);

    //patient queue unit wise
    @Query(value = "select  mst_patient.patient_mr_no, tbill_bill_service.bs_token_number, mbill_tariff.tariff_name,tbill_bill_service.bs_status,concat(mst_title.title_name, a.user_firstname, ' ',a.user_middlename, ' ',a.user_lastname) as 'Patient Name' " + " ,a.user_mobile,a.user_age,mst_gender.gender_name,tbill_bill_service.visit_start_time,tbill_bill_service.visit_end_time, " + " concat(b.user_firstname, ' ',b.user_middlename, ' ',b.user_lastname) as 'Doctor Name',mst_department.department_name,mst_sub_department.sd_name,mst_patient_type.pt_name,mst_patient_source.ps_name " + " from tbill_bill " + " inner join mst_visit on mst_visit.visit_id=tbill_bill.bill_visit_id " + " inner join tbill_bill_service on tbill_bill_service.bs_bill_id=tbill_bill.bill_id " + " inner join mst_user a on a.user_id=mst_visit.visit_patient_id " + " inner join mst_user b on b.user_id=mst_visit.visit_staff_id " + " inner join mst_patient on mst_patient.patient_user_id=a.user_id " + " left join mst_staff on mst_staff.staff_user_id=b.user_id " + " inner join mst_title on mst_title.title_id=a.user_title_id " + " left join mbill_tariff on mbill_tariff.tariff_id=tbill_bill.bill_tariff_id " + " left join mst_gender on mst_gender.gender_id=a.user_gender_id " + " left join mst_department on mst_department.department_id=mst_staff.staff_department_id " + " left join mst_sub_department on mst_sub_department.sd_id=mst_staff.staff_sd_id " + " left join mst_patient_type on mst_patient_type.pt_id=mst_visit.patient_type " + " where tbill_bill_service.bs_date = CURDATE() and tbill_bill.ipd_bill =  false and tbill_bill.tbill_unit_id = :unitid " + "  " + " \n#pageable\n", countQuery = "select count(*)  " + "   from tbill_bill " + " inner join mst_visit on mst_visit.visit_id=tbill_bill.bill_visit_id " + "   inner join tbill_bill_service on tbill_bill_service.bs_bill_id=tbill_bill.bill_id " + "  inner join mst_user a on a.user_id=mst_visit.visit_patient_id " + "   inner join mst_user b on b.user_id=mst_visit.visit_staff_id " +
            "   inner join mst_patient on mst_patient.patient_user_id=a.user_id " + "   left join mst_staff on mst_staff.staff_user_id=b.user_id " + "   inner join mst_title on mst_title.title_id=a.user_title_id " + "   left join mbill_tariff on mbill_tariff.tariff_id=tbill_bill.bill_tariff_id " + "   left join mst_gender on mst_gender.gender_id=a.user_gender_id " + "    left join mst_department on mst_department.department_id=mst_staff.staff_department_id " + " left join mst_sub_department on mst_sub_department.sd_id=mst_staff.staff_sd_id " + "    left join mst_patient_type on mst_patient_type.pt_id=mst_visit.patient_type " + " left join mst_patient_source on mst_patient_source.ps_id=mst_visit.visit_ps_id" + "  where tbill_bill_service.bs_date = CURDATE() and tbill_bill.ipd_bill =  false and tbill_bill.tbill_unit_id = :unitid ", nativeQuery = true)
    Page<TbillBillService> findAllByUnit(@Param("unitid") long unitid, Pageable page);

    Page<TbillBillService> findAllByBsDateAndBsBillIdIpdBillFalseAndBsBillIdTbillUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Date currentDate, long unitid, Pageable page);

    //queue search start
    Page<TbillBillService> findAllByBsDateAndBsBillIdIpdBillFalseAndBsBillIdTbillUnitIdUnitIdEqualsAndBsStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(Date currentDate, long unitid, long staffid, Pageable page);

    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdTbillUnitIdUnitIdEqualsAndBsStaffIdStaffIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(long unitid, long staffid, Date sdate, Date edate, Pageable page);

    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(long unitid, Date sdate, Date edate, Pageable page);

    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateAndIsActiveTrueAndIsDeletedFalse(long staffid, long unit, Date currentDate, Pageable page);

    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(long staffid, long unit, Date sdate, Date edate, Pageable page);

    Page<TbillBillService> findAllByBsBillIdTbillUnitIdUnitIdEqualsAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(long unitid, Date currentDate, Pageable page);

    Page<TbillBillService> findAllByBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(long unitid, Date sdate, Date edate, Pageable page);

    Page<TbillBillService> findAllByBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(long staff, long unitid, Date currentDate, Pageable page);

    Page<TbillBillService> findAllByBsStaffIdStaffIdEqualsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(long staff, long unitid, Date sdate, Date edate, Pageable page);

    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(String mrno, long unit, Date today, Pageable page);

    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(String mrno, long unit, Date sd, Date ed, Pageable page);

    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsStaffIdStaffIdEqualsAndBsDateEqualsAndIsActiveTrueAndIsDeletedFalse(String mrno, long unit, long staff, Date today, Pageable page);

    Page<TbillBillService> findAllByBsBillIdIpdBillFalseAndBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBsBillIdTbillUnitIdUnitIdEqualsAndBsStaffIdStaffIdEqualsAndBsDateBetweenAndIsActiveTrueAndIsDeletedFalse(String mrno, long unit, long staff, Date sd, Date ed, Pageable page);
    //queue search end

    //nst
    Page<TbillBillService> findByBsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(long billId, Pageable page);

    List<TbillBillService> findByBsBillIdBillAdmissionIdAdmissionIdEqualsAndIsActiveTrueAndIsDeletedFalseAndBsCancelFalseOrderByBsDateAsc(long admissionId);

    //for Todays Visit list
    @Query(value = "select * from tbill_bill_service tbs  INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id INNER JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id where tbs.is_active = 1 and tbs.is_deleted = 0 and  tbs.bs_cancel= 0  and DATE(mv.created_date)= :date", nativeQuery = true)
    List<TbillBillService> findAlldailyvisit(@Param("date") String date);

}

/*





By mohit*/