package com.cellbeans.hspa.mststaff;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mbillservice.MbillServiceRepository;
import com.cellbeans.hspa.mstorg.MstOrg;
import com.cellbeans.hspa.mstorg.MstOrgRepository;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mst_staff")
public class MstStaffController {
    // private static String UPLOADED_FOLDER = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.0\\webapps\\usersign_hspa\\";
    // Map<String, String> respMap = new HashMap<String, String>();
    Map<String, Object> respMapUser = null;

    @Autowired
    MstStaffRepository mstStaffRepository;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    MbillServiceRepository mbillServiceRepository;
    @Autowired
    MstUserRepository mstUserRepository;

    @Autowired
    MstOrgRepository mstOrgRepository;

    @Autowired
    private com.cellbeans.hspa.applicationproperty.Propertyconfig Propertyconfig;

    private String UPLOADED_FOLDER = Propertyconfig.getprofileimagepath();
    Map<String, String> respMap = new HashMap<String, String>();

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @RequestMapping("create")
    public Map<String, String> create(@RequestBody MstStaff mstStaff) {
        if (mstStaff.getStaffUserId().getUserFullname() != null) {
            MstUser mu = mstStaff.getStaffUserId();
            mu = mstUserRepository.save(mu);
            mstStaff.setStaffUserId(mu);
            mstStaff = mstStaffRepository.save(mstStaff);

//            MstUser mu = mstUserRepository.findOne(mstStaff.getStaffUserId().getUserId());
//                    .getStaffUserId().setUserId(mu.getUserId());
//            mstStaff.getVisitPatientId().setPatientUserId(mu);
//            mstStaffRepository.save(mstStaff);
            Long i = mstStaff.getStaffId();

            respMap.put("id", Long.toString(i));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("edoctorById/{UUID}")
    public MstStaff edoctorById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("UUID") Long UUID) {
        TenantContext.setCurrentTenant(tenantName);

        return mstStaffRepository.findAllByUUID(UUID);
    }

    @GetMapping
    @RequestMapping("doctorById/{staffId}")
    public MstStaff doctorById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId) {
        TenantContext.setCurrentTenant(tenantName);

        return mstStaffRepository.getById(staffId);
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstStaff> records;
        records = mstStaffRepository.findByStaffUserIdUserFullnameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{staffId}")
    public MstStaff read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        MstStaff mstStaff = mstStaffRepository.getById(staffId);
        return mstStaff;
    }

    @RequestMapping("byempid/{employeeId}")
    public List<MstStaff> getbyemployeeid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("employeeId") String employeeId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstStaff> mstStaff = mstStaffRepository.findByStaffErNoContains(employeeId);
        return mstStaff;
    }

    @Transactional
    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstStaff mstStaff) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstStaff.getStaffUserId().getUserFullname() != null) {
            MstUser muser = mstStaff.getStaffUserId();
            mstUserRepository.save(muser);
            mstStaff = mstStaffRepository.save(mstStaff);
            Long i = mstStaff.getStaffId();
            respMap.put("id", Long.toString(i));
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }


    @RequestMapping(value = "/update1/{staffId}", method = RequestMethod.PUT)
    public Map<String, Object> eupdateByUserId(@RequestHeader("X-tenantId") String tenantName,
                                               @PathVariable("staffId") Long staffId,
                                               @RequestBody Map<String, String> requestBody) {
        Map<String, Object> respMap = new HashMap<>();
        TenantContext.setCurrentTenant(tenantName);

        System.out.println("staffId"+staffId);
        String UUID = requestBody.get("UUID");
        System.out.println("UUID"+UUID);

        // Fetch patient matching the given patientId
        MstStaff mstStaff = mstStaffRepository.getById(staffId);

        // If patient exists, update its PUUID
        if (mstStaff != null) {
            mstStaff.setUUID(UUID);

            // Save the updated patient
            mstStaffRepository.save(mstStaff);

            // Populate response map
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            // Patient not found
            respMap.put("msg", "Patient not found");
            respMap.put("success", "0");
        }

        return respMap;
    }


    @GetMapping
    @RequestMapping("list")
    public Iterable<MstStaff> list(@RequestHeader(value = "X-tenantId", required = false) String tenantName,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                   @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                   @RequestParam(value = "qString", required = false) String qString,
                                   @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort,
                                   @RequestParam(value = "col", required = false, defaultValue = "staffId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstStaffRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByStaffIdAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstStaffRepository.findByStaffUserIdUserFullnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("DoctorSharelist")
    public List<MstStaff> DoctorSharelist(@RequestHeader("X-tenantId") String tenantName,
                                          @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "SELECT * FROM mst_staff s,mst_staff_staff_unit u where s.staff_id=u.mst_staff_staff_id " + "and s.is_medicaldepartment=true and s.doctor_shair=1 and u.staff_unit_unit_id=" + unitId + " and s.is_active=1 and s.is_deleted=0";
        //return mstStaffRepository.findByIsMedicaldepartmentTrueAndIsDeletedFalseAndDoctorShairTrueAndStaffUnitUnitIdEquals(unitId);
//        System.out.println("staff Doctor Query:" + query);
        return entityManager.createNativeQuery(query, MstStaff.class).getResultList();
    }

    @GetMapping
    @RequestMapping("listforauto")
    public List<MstStaff> listForAuto(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "userFirstname", required = false) String userFirstname, @RequestParam(value = "userLastname", required = false) String userLastName) {
        TenantContext.setCurrentTenant(tenantName);
        if ((userFirstname == null || userFirstname.equals("")) && (userLastName == null || userLastName.equals(""))) {
            return mstStaffRepository.findByIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc();
        } else {
            if (userFirstname.equals("")) {
                userFirstname = userLastName;
            }
            if (userLastName.equals("")) {
                userLastName = userFirstname;
            }
            return mstStaffRepository.findByStaffUserIdUserFirstnameContainsOrStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(userFirstname, userLastName);
        }
    }

    @GetMapping
    @RequestMapping("DoctorSharelistAutoComplete")
    public List<MstStaff> DoctorSharelistAutoComplete(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
//        String query = "SELECT * FROM mst_staff s inner join mst_user mu on mu.user_id = s.staff_user_id where  s.is_medicaldepartment=true and s.doctor_shair=1 and (mu.user_firstname like '%"+ userFirstname +"%' or  mu.user_lastname like '%"+ userFirstname +"%') and s.is_active=1 and s.is_deleted=0";
        String query = "SELECT * FROM mst_staff s  where  s.is_medicaldepartment=true and s.doctor_shair=1 and s.is_active=1 and s.is_deleted=0";
        return entityManager.createNativeQuery(query, MstStaff.class).getResultList();
    }

    @GetMapping
    @RequestMapping("listbysdid/{sdId}")
    public Iterable<MstStaff> list(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sdId") Long sdId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findByStaffSdIdSdIdAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(sdId);
    }

    @GetMapping
    @RequestMapping("listbysdidunitid/{sdId}/{unitId}")
    public Iterable<MstStaff> listbysdidunitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sdId") Long sdId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findByStaffSdIdSdIdAndStaffUnitUnitIdEqualsAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(sdId, unitId);
    }

    //Author : Rohan Get Doctor List by Unit
    @GetMapping
    @RequestMapping("listOfDoctorByunitid/{unitId}")
    public Iterable<MstStaff> listbyunitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findByStaffUnitUnitIdAndIsMedicaldepartmentTrueAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(unitId);
    }

    @GetMapping
    @RequestMapping("listbydepid/{sdId}")
    public Iterable<MstStaff> listbydepid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sdId") Long sdId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findByStaffDepartmentIdDepartmentIdEqualsAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(sdId);
    }

    @GetMapping
    @RequestMapping("getalldoctorlistByDepartment/{sdId}/{unitId}")
    public List<MstStaff> getalldoctorlist(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sdId") Long sdId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findByStaffDepartmentIdDepartmentIdEqualsAndStaffUnitUnitIdEqualsAndIsMedicaldepartmentTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(sdId, unitId);
    }

    @GetMapping
    @RequestMapping("listbydepidunit/{departmentId}")
    public Iterable<MstStaff> listbydepidunit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("departmentId") Long departmentId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findAllByStaffDepartmentIdDepartmentIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(departmentId);
    }

    @GetMapping
    @RequestMapping("getRecordByUnitList")
    public List<Object[]> getRecordByUnitList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<Long> unitList) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findByUnitId(unitList);
    }

    @PutMapping("delete/{staffId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        MstStaff mstStaff = mstStaffRepository.getById(staffId);
        if (mstStaff != null) {
            mstStaff.setIsDeleted(true);
            mstStaffRepository.save(mstStaff);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PostMapping("upload")
    public ResponseEntity<?> uploadFile(@RequestHeader("X-tenantId") String tenantName,
                                        @RequestPart("file") MultipartFile uploadfile) {
        TenantContext.setCurrentTenant(tenantName);
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(tenantName, Arrays.asList(uploadfile));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    private void saveUploadedFiles(@RequestHeader("X-tenantId") String tenantName, 
                                   List<MultipartFile> files) throws IOException {
        TenantContext.setCurrentTenant(tenantName);
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; //next pls
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }

    @RequestMapping("byiduserid/{id}")
    public MstStaff byiduserid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        MstStaff mstStaff = mstStaffRepository.findByStaffUserIdUserIdAndIsActiveTrueAndIsDeletedFalse(id);
        return mstStaff;
    }

    @RequestMapping("byunitbystaffid/{id}")
    public MstStaff unitByStaffId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
//        System.out.println("byunitbystaffid :" + id);
        MstStaff mstStaff = mstStaffRepository.findAllByStaffIdAndIsActiveTrueAndIsDeletedFalse(id);
//        System.out.println("mstStaff  :" + mstStaff);
        return mstStaff;
    }

    // Author: Priyanka
    @GetMapping
    @RequestMapping("getalldoctorlist")
    public List<MstStaff> getalldoctorlist(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findByIsMedicaldepartmentTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc();
    }

    //Author: Mohit
    @GetMapping("liststaffforconcessionbystaff")
    public List<MstStaff> getAllStaffForConcession(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "departmentId", defaultValue = "0", required = false) Long departmentId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findByStaffDepartmentIdDepartmentIdAndIsConcessionAuthorityTrueAndIsDeletedFalseAndIsActiveTrueOrderByStaffUserIdUserFirstnameAsc(departmentId);
    }

    //Author: Mohit
    @GetMapping("listunitwisestaffforconcessionbystaff/{departmentId}/{unitId}")
    public List<MstStaff> getunitwiseStaffForConcession(@RequestHeader("X-tenantId") String tenantName, @PathVariable("departmentId") Long departmentId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findByStaffDepartmentIdDepartmentIdAndStaffUnitUnitIdEqualsAndIsConcessionAuthorityTrueAndIsDeletedFalseAndIsActiveTrueOrderByStaffUserIdUserFirstnameAsc(departmentId, unitId);
    }
    //old method commented by hemant and rohan
//    @GetMapping
//    @RequestMapping("unitlistuserwise")
//    public List<MstStaff> unitlistByUsername(@RequestParam(value = "username") String username) {
//        System.out.println("username :" + username);
//        List<MstStaff> mstStaffs= new ArrayList<>();
//        TenantContext.setCurrentTenant("1");
//        List<MstOrg> orgList = mstOrgRepository.findAll();
//        System.out.println("orgList : " + orgList.size());
//        for (int i = 0; i < orgList.size(); i++) {
//            Long tenantName = orgList.get(i).getOrgId();
//            TenantContext.setCurrentTenant("" + tenantName);
//            MstStaff mstStaff = mstStaffRepository.findByStaffUserIdUserNameContainsAndIsActiveTrueAndIsDeletedFalse(username);
//            mstStaffs.add(mstStaff);
//        }
//        System.out.println("mstStaff : " + mstStaffs.size());
//        return mstStaffs;
//    }

    @GetMapping
    @RequestMapping("getAllOrgs")
    public List<MstOrg> getAllOrgs() {
        TenantContext.setCurrentTenant("1");
        List<MstOrg> orgList = mstOrgRepository.findAll();
        return orgList;
    }

    @GetMapping
    @RequestMapping("unitlistuserwise")
    public List<Object[]> unitlistByUsername(@RequestHeader("X-tenantId") String tenantName,
                                             @RequestParam(value = "username") String username) {
        TenantContext.setCurrentTenant(tenantName);
        List<Object[]> unitList = mstStaffRepository.findAllByStaffUnitUnitIdAndOrgData(Long.valueOf(tenantName), username);
        return unitList;
    }

    @GetMapping
    @RequestMapping("unitlistpatientusernamewise")
    public List<Object[]> unitlistByPatientUsername(@RequestHeader("X-tenantId") String tenantName,
                                                    @RequestParam(value = "username") String username) {
        TenantContext.setCurrentTenant(tenantName);
        List<Object[]> unitList = mstStaffRepository.findAllByPatientUnitIdAndOrgData(Long.valueOf(tenantName), username);
        return unitList;
    }
//    @GetMapping
//    @RequestMapping("listbydepidmargeunit/{departmentId}/{unitId}")
//    public List<MstStaff> listbydepidmargeunit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("departmentId") Long departmentId, @PathVariable("unitId") Long unitId) {
//        String Query="SELECT * FROM mst_staff s,mst_staff_staff_sd_id u,mst_staff_staff_unit i " +
//                " where s.staff_id=u.mst_staff_staff_id and u.staff_sd_id_sd_id="+departmentId+" and s.staff_id=i.mst_staff_staff_id and i.staff_unit_unit_id="+unitId+" ";
//        System.out.println("QUery"+Query);
//        entityManager.createNativeQuery(Query,MstStaff.class).getResultList();
//        return entityManager.createNativeQuery(Query,MstStaff.class).getResultList();
//    }

    @GetMapping
    @RequestMapping("listbydepidunitad/{departmentId}/{unitId}")
    public Iterable<MstStaff> listbydepidunitad(@RequestHeader("X-tenantId") String tenantName, @PathVariable("departmentId") Long departmentId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findAllByStaffDepartmentIdDepartmentIdEqualsAndIsActiveTrueAndIsDeletedFalse(departmentId);
    }

    @GetMapping
    @RequestMapping("listbydeptidandunitid/{departmentId}/{unitId}")
    public Iterable<MstStaff> listbydeptidandunitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("departmentId") Long departmentId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findAllByStaffDepartmentIdDepartmentIdEqualsAndStaffUnitUnitIdAndStaffTypeEqualsAndIsActiveTrueAndIsDeletedFalse(departmentId, unitId, 0);
    }

    @GetMapping
    @RequestMapping("listbyunit/{unitId}")
    public Iterable<MstStaff> listbyunit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findAllByStaffTypeEqualsAndStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(0, unitId);
    }

    @GetMapping
    @RequestMapping("stafflistbyunit/{unitId}")
    public Iterable<MstStaff> stafflistbyunit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findAllByStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(unitId);
    }

    //Created By Neha for EMR
    @GetMapping
    @RequestMapping("emrstafflistbyunit/{unitId}")
    public Iterable<MstStaff> emrstafflistbyunit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstStaff> staffList = new ArrayList<>();
        List<Object[]> list = mstStaffRepository.findAllByStaffUnitUnitId(unitId);
        for (int i = 0; i < list.size(); i++) {
            MstStaff ms = new MstStaff();
            ms.setStaffId(Long.parseLong(list.get(i)[0].toString()));
            ms.setStaffType(Integer.parseInt("" + list.get(i)[1]));
            MstUser mu = mstUserRepository.findbyUserID(Long.parseLong(list.get(i)[2].toString()));
            ms.setStaffUserId(mu);
            List<Object[]> staffServicesList = mstStaffRepository.findBystaffServicesStaffId(Long.parseLong(list.get(i)[0].toString()));
            List<MstStaffServices> servicesList = new ArrayList<>();
            for (int j = 0; j < staffServicesList.size(); j++) {
                String q = "select * from mst_staff_services where ss_id= " + Long.parseLong(staffServicesList.get(j)[1].toString());
                List<Object[]> mstStaffServices = entityManager.createNativeQuery(q).getResultList();
                MstStaffServices mstStaffServices1 = new MstStaffServices();
                MbillService mbillService = mbillServiceRepository.findbyID(Long.parseLong(mstStaffServices.get(0)[8].toString()));
                mstStaffServices1.setSsBaseRate(Double.parseDouble(mstStaffServices.get(0)[7].toString()));
                mstStaffServices1.setSsServiceId(mbillService);
                servicesList.add(mstStaffServices1);
            }
            ms.setStaffServiceId(servicesList);
            staffList.add(ms);
        }
        return staffList;
    }

    @GetMapping
    @RequestMapping("stafflistbyunitforDoctorShare/{unitId}")
    public Iterable<MstStaff> stafflistForDoctorSharebyunit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findAllByIsMedicaldepartmentTrueAndStaffUnitUnitIdEqualsAndDoctorShairTrueAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(unitId);
    }

    //abhijeet Ot
    @RequestMapping("bystafftype/{id}")
    public List<MstStaff> bystafftype(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Integer id) {
        TenantContext.setCurrentTenant(tenantName);
        //        System.out.println("i am in");
        return mstStaffRepository.findByStaffTypeEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(id);
    }

    @RequestMapping("bystafftype/{id}/{qstring}")
    public List<MstStaff> bystafftype(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Integer id, @PathVariable("qstring") String fstring, @PathVariable("qstring") String lstring) {
        TenantContext.setCurrentTenant(tenantName);
        //        System.out.println("i am in");
//        return mstStaffRepository.findByStaffTypeEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(id);
        return mstStaffRepository.findByStaffTypeEqualsAndStaffUserIdUserFirstnameContainsOrStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(id, fstring, lstring);
    }

    //abhijeet Ot
    @GetMapping
    @RequestMapping("usernamevalidate/{username}")
    public boolean usernamevalidate(@RequestHeader("X-tenantId") String tenantName, @PathVariable("username") String username) {
        TenantContext.setCurrentTenant(tenantName);
        MstStaff mstStaff = null;
        boolean check = false;
        try {
            mstStaff = new MstStaff();
            mstStaff = mstStaffRepository.findByStaffUserIdUserNameEqualsAndIsActiveTrueAndIsDeletedFalse(username);
            if (mstStaff != null) {
                check = true;
            }
        } catch (Exception e) {
        }
        System.out.println(check);
        return check;
    }

    @GetMapping
    @RequestMapping("GetAllDoctorStaffTechnicianByUnit/{unitId}")
    public Iterable<MstStaff> GetAllDoctorStaffTechnicianByUnit(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findAllByStaffUnitUnitIdEqualsAndStaffTypeEqualsOrStaffTypeEqualsOrStaffTypeEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(unitId, 0, 1, 2);
    }

    // vijay
    @GetMapping
    @RequestMapping("listbyunitid")
    public Iterable<MstStaff> listByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId", required = false) long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("unitId vj :"+unitId);
        return mstStaffRepository.findAllByStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(unitId);
    }
    // vijay

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List<MstStaffDto> getDropdown(@RequestHeader("X-tenantId") String tenantName,
                                         @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                         @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
                                         @RequestParam(value = "unitId", required = true) long unitId,
                                         @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstStaffDto> mstStaffDtoList = new ArrayList<MstStaffDto>();
        String query1 = "select s.staff_id,CONCAT(mu.user_firstname,' ',mu.user_lastname) AS FULLNAME from mst_staff s " + "inner join mst_staff_staff_unit u on u.mst_staff_staff_id = s.staff_id " + "inner join mst_user mu on mu.user_id = s.staff_user_id " + "where s.is_active = true and s.is_deleted = false and staff_type = 0 and " + "u.staff_unit_unit_id = " + unitId + " and (mu.user_firstname like '%" + globalFilter + "%' or  mu.user_lastname like '%" + globalFilter + "%') " + "order by s.staff_id DESC";
        List<Object[]> stafflist = entityManager.createNativeQuery(query1).setFirstResult(0).setMaxResults(20).getResultList();
        for (Object[] obj : stafflist) {
            MstStaffDto mstStaffDto = new MstStaffDto(Long.parseLong(obj[0].toString()), obj[1].toString());
            mstStaffDtoList.add(mstStaffDto);
        }
        return mstStaffDtoList;
    }

    @RequestMapping(value = "/doctorShareDropdown", method = RequestMethod.GET)
    public List<MstStaffDto> doctorShareDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "20", required = false) Integer size, @RequestParam(value = "unitId", required = true) long unitId, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstStaffDto> mstStaffDtoList = new ArrayList<MstStaffDto>();
        String query1 = "select s.staff_id,CONCAT(mu.user_firstname,' ',mu.user_lastname) AS FULLNAME from mst_staff s " + "inner join mst_staff_staff_unit u on u.mst_staff_staff_id = s.staff_id " + "inner join mst_user mu on mu.user_id = s.staff_user_id " + "where s.is_active = true and s.is_deleted = false and s.is_medicaldepartment=true and s.doctor_shair=1 and u.staff_unit_unit_id = " + unitId + " and (mu.user_firstname like '%" + globalFilter + "%' or  mu.user_lastname like '%" + globalFilter + "%') " + "order by s.staff_id DESC";
        List<Object[]> stafflist = entityManager.createNativeQuery(query1).setFirstResult(0).setMaxResults(20).getResultList();
        for (Object[] obj : stafflist) {
            MstStaffDto mstStaffDto = new MstStaffDto(Long.parseLong(obj[0].toString()), obj[1].toString());
            mstStaffDtoList.add(mstStaffDto);
        }
        return mstStaffDtoList;
    }

    @RequestMapping("unitwiseuser/{roleid}/{rolename}")
    public List<MstStaff> updatePreUser(@RequestHeader("X-tenantId") String tenantName, @RequestBody String unitList, @PathVariable("roleid") String roleId, @PathVariable("rolename") String roleName) {
        TenantContext.setCurrentTenant(tenantName);
//        System.out.println("unitList :" + unitList + " roleId" + roleId + " roleName :" + roleName);
        StringTokenizer st = new StringTokenizer(unitList, "[,]");
        ArrayList<MstStaff> staffList = new ArrayList<MstStaff>();
        while (st.hasMoreTokens()) {
            Long unitId = Long.parseLong(st.nextToken());
        /*    String qauryStr = "select mst_staff.staff_id,mst_user.user_firstname,mst_user.user_lastname from mst_staff\n" +
                    "left join mst_user on mst_user.user_id=mst_staff.staff_user_id\n" +
                    "left join mst_staff_staff_unit on mst_staff_staff_unit.mst_staff_staff_Id=mst_staff.staff_Id\n" +
                    "where mst_staff_staff_unit.staff_unit_unit_id=" + unitId + " and mst_staff.staff_role=" + roleId + " and mst_staff.is_active=true and mst_staff.is_deleted=false";
            Query q = entityManager.createNativeQuery(qauryStr);
            List<MstStaff> mstStaffList = q.getResultList();
            staffList.addAll(mstStaffList);*/
            staffList.addAll(mstStaffRepository.findAllByStaffUnitUnitIdAndStaffRoleRoleIdAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(unitId, Long.parseLong(roleId)));
        }
        return staffList;
    }

    @RequestMapping("unitwiseuser/{roleid}/{rolename}/{qString}")
    public List<MstStaff> updatePreUser(@RequestHeader("X-tenantId") String tenantName, @RequestBody String unitList, @PathVariable("roleid") String roleId, @PathVariable("rolename") String roleName, @PathVariable("qString") String qString) {
        TenantContext.setCurrentTenant(tenantName);
//        System.out.println("unitList :" + unitList + " roleId" + roleId + " roleName :" + roleName);
        StringTokenizer st = new StringTokenizer(unitList, "[,]");
        ArrayList<MstStaff> staffList = new ArrayList<MstStaff>();
        while (st.hasMoreTokens()) {
            Long unitId = Long.parseLong(st.nextToken());
            staffList.addAll(mstStaffRepository.findAllByStaffUserIdUserFirstnameContainsOrStaffUserIdUserLastnameContainsAndStaffUnitUnitIdAndStaffRoleRoleIdAndIsDeletedFalseAndIsActiveTrueOrderByStaffUserIdUserFirstnameAsc(qString, qString, unitId, Long.parseLong(roleId)));
        }
        List<MstStaff> employeesDistinctByName = staffList.stream().distinct().filter(e -> e.getIsDeleted() == false).collect(Collectors.toList());
        return employeesDistinctByName;
    }

    @GetMapping
    @RequestMapping("all_list_dropdown/{unitId}")
    public List<MstStaffDto> all_list_dropdown(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstStaffDto> mstStaffDtoList = new ArrayList<MstStaffDto>();
        String query1 = "select s.staff_id,CONCAT(mu.user_firstname,' ',mu.user_lastname) AS FULLNAME from mst_staff s " + "inner join mst_staff_staff_unit u on u.mst_staff_staff_id = s.staff_id " + "inner join mst_user mu on mu.user_id = s.staff_user_id " + "where s.is_active = true and s.is_deleted = false and staff_type = 0 and " + "u.staff_unit_unit_id = " + unitId + " " + "order by s.staff_id DESC";
        List<Object[]> stafflist = entityManager.createNativeQuery(query1).getResultList();
        for (Object[] obj : stafflist) {
            MstStaffDto mstStaffDto = new MstStaffDto(Long.parseLong(obj[0].toString()), obj[1].toString());
            mstStaffDtoList.add(mstStaffDto);
        }
        return mstStaffDtoList;
    }

    //vijay
    @RequestMapping("recordbystafflist")
    // @Produces(MediaType.APPLICATION_JSON)
    public List<MstStaff> recordByStaffList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<JSONObject> jsonList) {
        TenantContext.setCurrentTenant(tenantName);
        //        System.out.println("staffId count :" + jsonList + "  :..0 :" + jsonList.get(0).get("staffId"));
        List<MstStaff> staffList = new ArrayList<MstStaff>();
        MstStaff staff = new MstStaff();
        for (int i = 0; i < jsonList.size(); i++) {
            staff = mstStaffRepository.getById(Long.parseLong(String.valueOf(jsonList.get(i).get("staffId"))));
            staffList.add(staff);
//            System.out.println("staff List :" + staffList);
        }
        return staffList;
    }

    //rohan
    @GetMapping
    @RequestMapping("getDoctorBySpecialityId")
    public List<MstStaff> getDoctorBySpecialityId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "specialityId", required = false) long specialityId, @RequestParam(value = "unitId", required = false) long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return entityManager.createQuery("select p from MstStaff p INNER JOIN p.staffUnit t where p.staffSpecialityId.specialityId=" + specialityId + " and t.unitId=" + unitId + " and p.isActive=1 and p.isDeleted=0 ", MstStaff.class).getResultList();
    }

    @RequestMapping(value = "/get_staff_by_subdepartment_list", method = RequestMethod.GET)
    public List<MstStaffDto> get_staff_by_subdepartment_list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "20", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter, @RequestParam(value = "subDepartmentList", required = true) String subDepartmentList) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = size * (page - 1);
        int limit = size;
        List<MstStaffDto> mstStaffDtoList = new ArrayList<MstStaffDto>();
        String query1 = "select s.staff_id,CONCAT(mu.user_firstname,' ',mu.user_lastname) AS FULLNAME from mst_staff s " + "inner join mst_staff_staff_sd_id u on u.mst_staff_staff_id = s.staff_id " + "inner join mst_user mu on mu.user_id = s.staff_user_id " + "inner join mst_sub_department sd on sd.sd_id = u.staff_sd_id_sd_id " + "where s.is_active = true and s.is_deleted = false and (s.staff_type = 0 or s.staff_type = 1 or s.staff_type = 2 ) and " +
//                " (mu.user_firstname  like '%"+globalFilter+"'  or mu.user_lastname  like '%"+globalFilter+"' )  and" +
                " u.staff_sd_id_sd_id in (" + removeLastChar(subDepartmentList) + ") " + "order by s.staff_id DESC ";
        List<Object[]> stafflist = entityManager.createNativeQuery(query1).getResultList();
        for (Object[] obj : stafflist) {
            MstStaffDto mstStaffDto = new MstStaffDto(Long.parseLong(obj[0].toString()), obj[1].toString());
            mstStaffDtoList.add(mstStaffDto);
        }
        return mstStaffDtoList;
    }

    @RequestMapping(value = "/get_staff_by_speciality_list", method = RequestMethod.GET)
    public List<MstStaffDto> get_staff_by_speciality_list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "20", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter, @RequestParam(value = "specialityList", required = true) String specialityList) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = size * (page - 1);
        int limit = size;
        List<MstStaffDto> mstStaffDtoList = new ArrayList<MstStaffDto>();
        String query1 = "select s.staff_id,CONCAT(mu.user_firstname,' ',mu.user_lastname) AS FULLNAME from mst_staff s " + "inner join mst_user mu on mu.user_id = s.staff_user_id " + "where s.is_active = true and s.is_deleted = false and (s.staff_type = 0 or s.staff_type = 1 or s.staff_type = 2 ) and " +
//                " (mu.user_firstname  like '%"+globalFilter+"'  or mu.user_lastname  like '%"+globalFilter+"' )  and" +
                " s.staff_speciality_id in (" + removeLastChar(specialityList) + ") " + "order by s.staff_id DESC ";
        List<Object[]> stafflist = entityManager.createNativeQuery(query1).getResultList();
        for (Object[] obj : stafflist) {
            MstStaffDto mstStaffDto = new MstStaffDto(Long.parseLong(obj[0].toString()), obj[1].toString());
            mstStaffDtoList.add(mstStaffDto);
        }
        return mstStaffDtoList;
    }

    @RequestMapping(value = "/get_staff_by_cabin_list", method = RequestMethod.GET)
    public List<MstStaffDto> get_staff_by_cabin_list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "20", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter, @RequestParam(value = "cabinList", required = true) String cabinList) {
        TenantContext.setCurrentTenant(tenantName);
        int offset = size * (page - 1);
        int limit = size;
        List<MstStaffDto> mstStaffDtoList = new ArrayList<MstStaffDto>();
        String query1 = "select s.staff_id,CONCAT(mu.user_firstname,' ',mu.user_lastname) AS FULLNAME from mst_staff s " + "inner join mst_user mu on mu.user_id = s.staff_user_id " + "  inner join trn_staff_cabin_schedule scs on s.staff_id = scs.csd_staff_id " + "where s.is_active = true and s.is_deleted = false  and scs.is_active = true and scs.is_deleted = false and (s.staff_type = 0 or s.staff_type = 1 or s.staff_type = 2 ) and " +
//                " (mu.user_firstname  like '%"+globalFilter+"'  or mu.user_lastname  like '%"+globalFilter+"' )  and" +
                " scs.csd_cabin_id  in (" + removeLastChar(cabinList) + ") " + " group by mu.user_firstname,mu.user_lastname   order by s.staff_id DESC ";
        List<Object[]> stafflist = entityManager.createNativeQuery(query1).getResultList();
        for (Object[] obj : stafflist) {
            MstStaffDto mstStaffDto = new MstStaffDto(Long.parseLong(obj[0].toString()), obj[1].toString());
            mstStaffDtoList.add(mstStaffDto);
        }
        return mstStaffDtoList;
    }

    @GetMapping
    @RequestMapping("stafflistbyunitNonMedNonDoc/{unitId}")
    public Iterable<MstStaff> stafflistbyunitNonMedNonDoc(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findAllByStaffUnitUnitIdEqualsAndStaffTypeNotAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(unitId, (int) 0);
    }

    @GetMapping
    @RequestMapping("consultdrlist/{ssId}")
    public Iterable<MstStaff> consultdrlist(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ssId") Long ssId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstStaffRepository.findAllByStaffServiceIdSsServiceIdContainsAndIsActiveTrueAndIsDeletedFalseOrderByStaffIdAsc(ssId);
    }

    @GetMapping
    @RequestMapping("stafflistbyunitAutoComplete/{unitId}/{qString}")
    public List<MstStaffDto> stafflistbyunitAutoComplete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId, @PathVariable("qString") String qString) {
        TenantContext.setCurrentTenant(tenantName);
        //        return mstStaffRepository.findAllByStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(unitId);
        List<MstStaffDto> mstStaffDtoList = new ArrayList<MstStaffDto>();
        String query1 = "select s.staff_id,CONCAT(mu.user_firstname,' ',mu.user_lastname) AS FULLNAME from mst_staff s " + "inner join mst_staff_staff_unit u on u.mst_staff_staff_id = s.staff_id " + "inner join mst_user mu on mu.user_id = s.staff_user_id " + "where s.is_active = true and s.is_deleted = false and " + "u.staff_unit_unit_id = " + unitId + " and (mu.user_firstname like '%" + qString + "%' or  mu.user_lastname like '%" + qString + "%') " + "order by s.staff_id DESC";
        List<Object[]> stafflist = entityManager.createNativeQuery(query1).setFirstResult(0).setMaxResults(20).getResultList();
        for (Object[] obj : stafflist) {
            MstStaffDto mstStaffDto = new MstStaffDto(Long.parseLong(obj[0].toString()), obj[1].toString());
            mstStaffDtoList.add(mstStaffDto);
        }
        return mstStaffDtoList;
    }

    @RequestMapping("setisdoctoronline/{staffId}/{boolean}")
    @Transactional
    @Modifying
    public Integer updateBoolean(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId, @PathVariable("boolean") Boolean isOnline) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "update mst_staff ms SET ms.is_doctor_online = " + isOnline + " where ms.staff_id =" + staffId;
        int update = entityManager.createNativeQuery(query).executeUpdate();
        return update;
    }

}