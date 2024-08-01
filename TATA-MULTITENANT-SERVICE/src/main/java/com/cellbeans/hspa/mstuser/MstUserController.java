package com.cellbeans.hspa.mstuser;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_user")
public class MstUserController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstUserRepository mstUserRepository;
    @Autowired
    private MstUserService mstUserService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstUser mstUser) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstUser.getUserFullname() != null) {
            mstUserRepository.save(mstUser);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstUser> records;
        records = mstUserRepository.findByUserFullnameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{userId}")
    public MstUser read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("userId") Long userId) {
        TenantContext.setCurrentTenant(tenantName);
        MstUser mstUser = mstUserRepository.getById(userId);
        return mstUser;
    }

    @RequestMapping("update")
    public MstUser update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstUser mstUser) {
        TenantContext.setCurrentTenant(tenantName);
        return mstUserRepository.save(mstUser);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstUser> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "userId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstUserRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstUserRepository.findByUserFullnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{userId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("userId") Long userId) {
        TenantContext.setCurrentTenant(tenantName);
        MstUser mstUser = mstUserRepository.getById(userId);
        if (mstUser != null) {
            mstUser.setIsDeleted(true);
            mstUserRepository.save(mstUser);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstUserService.getMstUserForDropdown(page, size, globalFilter);
        return items;
    }

    @RequestMapping("getUserHistoryByUserDrivingNo/{userDrivingNo}")
    public Map<String, Object> getUserHistoryByUserDrivingNo(@RequestHeader("X-tenantId") String tenantName, @PathVariable("userDrivingNo") Long userDrivingNo) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String hpng_medtrans_list_query = "SELECT * FROM hpng_medtrans_list hml WHERE hml.patient_id =" + userDrivingNo;
        String hpng_pathology_radiology_test_query = "SELECT * FROM hpng_pathology_radiology_test hprt WHERE hprt.EmpId =" + userDrivingNo;
        String hpng_patient_treatment_history_query = "SELECT * FROM hpng_patient_treatment_history hpth WHERE hpth.empid =" + userDrivingNo;
        String hpng_swasth_documents_query = "SELECT * FROM hpng_swasth_documents hsd WHERE hsd.patient_no =" + userDrivingNo;
        String hpng_swasth_medicine_query = "SELECT * FROM hpng_swasth_medicine  hsm WHERE hsm.patient_id =" + userDrivingNo;
        String hpng_swasth_profile_query = "SELECT * FROM hpng_swasth_profile hsp WHERE hsp.patient_no =" + userDrivingNo;
        String columnName1 = "patient_id,patient_name,unit,schedule,price,unique_number_ref,dose,medicine_name,quantity,status_modify,due_date,med_id,date_of_approval,request_no,medicine_status,doctor_name";
        List<Object> hpng_medtrans_list_objects = createJSONObject.createJson(columnName1, hpng_medtrans_list_query);
        String columnName2 = "EmpId,C_ID,RegNo,DateOfEntry,intial,FirstName,Tests,Reportdate,exam_date,exam_time,Coll_Code,CompCode,EmailID,DocName,pat_id,TestCharges,Username,SampleType,RegistratonDateTime,compname,PePatID,OPDNo,FamilySrno,testname,emprelation,relation";
        List<Object> hpng_pathology_radiology_test_objects = (createJSONObject.createJson(columnName2, hpng_pathology_radiology_test_query));
        String columnName3 = "empid,OPDno,PePatID,DoctorIncharge,Entrydate,ref_doctor,Diagnosis,pno,username,barcode,Appointment_Date,Appointment_Time,FamilySrno,Note,CampDate,PassNo,TradeNd,ContractorOrComp,ContactNo,LocationOfAccident,DoingWhat,AccidentDiagno,InjuryClassification,TreatementGiven,AccidentDisposal,AttendedBy,Remarks,ArrivalTime,ReferTo,HospitalName,Remark,tDateTime,CaseMajorMinor,Findings,OrderInvestigation,AMEPreEmpId,AMENo,emprelation,relation";
        List<Object> hpng_patient_treatment_history_objects = (createJSONObject.createJson(columnName3, hpng_patient_treatment_history_query));
        String columnName4 = "patient_no,patient_name,object_name,document_type,relation";
        List<Object> hpng_swasth_documents_objects = (createJSONObject.createJson(columnName4, hpng_swasth_documents_query));
        String columnName5 = "patient_id,patient_name,unit,schedule,price,dose,medicine_name,quantity,due_date";
        List<Object> hpng_swasth_medicine_objects = (createJSONObject.createJson(columnName5, hpng_swasth_medicine_query));
        String columnName6 = "patient_no,object_name,patient_dept,patient_company,date_of_case_paper,approval_date_doctor,patient_dept_code,approval_level,doctor,active_status,cost_center,patient_name,retired_on,emp_address,approval_date_chemist,nature_of_desease,age,abnormal_invest_report_dr,gender,contact_no,status,relation,approval_date_admin,date_of_birth,approver_level,detail_of_desease,mail_to_doctor,chemists,patient_type,request_by,patient_request_number,email,patient_id,date_of_request,patient_company_code,unique_number,quantity,r_object_type,r_creation_date,r_modify_date";
        List<Object> hpng_swasth_profile_objects = (createJSONObject.createJson(columnName6, hpng_swasth_profile_query));
        resultMap.put("hpng_medtrans_list_objects", hpng_medtrans_list_objects);
        resultMap.put("hpng_pathology_radiology_test_objects", hpng_pathology_radiology_test_objects);
        resultMap.put("hpng_patient_treatment_history_objects", hpng_patient_treatment_history_objects);
        resultMap.put("hpng_swasth_documents_objects", hpng_swasth_documents_objects);
        resultMap.put("hpng_swasth_medicine_objects", hpng_swasth_medicine_objects);
        resultMap.put("hpng_swasth_profile_objects", hpng_swasth_profile_objects);
        return resultMap;
    }

    @RequestMapping("checkExistByMobileNo")
    public String read(@RequestHeader("X-tenantId") String tenantName, @RequestParam("mobileNo") String mobileNo) {
        TenantContext.setCurrentTenant(tenantName);
        return mstUserService.checkExistByMobileNo(mobileNo);
    }

}
            
