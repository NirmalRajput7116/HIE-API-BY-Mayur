package com.cellbeans.hspa.mstpatient;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.applicationproperty.ErNumberPrefix;
import com.cellbeans.hspa.applicationproperty.MrNumberPrefix;
import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import com.cellbeans.hspa.emailutility.Emailsend;
import com.cellbeans.hspa.mstCitizenIdProof.MstCitizenIdProof;
import com.cellbeans.hspa.mstCitizenIdProof.MstCitizenIdProofRepository;
import com.cellbeans.hspa.mstbloodgroup.MstBloodgroup;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstnationality.MstNationalityRepository;
import com.cellbeans.hspa.mstpatienttype.MstPatientType;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
import com.cellbeans.hspa.msttitle.MstTitleRepository;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.mstvisit.MstVisitSearchDto;
import com.cellbeans.hspa.opdpatientconfiguration.OPDPatientConfiguration;
import com.cellbeans.hspa.smsutility.Sendsms;
import com.cellbeans.hspa.util.GeneratePWD;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/mst_patient")
public class MstPatientController {

    Map<String, String> respMap = new HashMap<String, String>();

    /* @Autowired
     private MstPatientService mstPatientService;*/
    Map<String, Object> respMapanco = new HashMap<String, Object>();
    @Autowired
    MstPatientRepository mstPatientRepository;
    @Autowired
    MstUserRepository mstuserRepository;
    @Autowired
    MstVisitRepository mstvisitRepository;
    @Autowired
    MstUnitRepository mstUnitRepository;
    @Autowired
    MstTitleRepository mstTitleRepository;
    @Autowired
    MstNationalityRepository mstNationalityRepository;
    @Autowired
    MstCitizenIdProofRepository mstCitizenIdProofRepository;
    @Autowired
    Sendsms sendsms;
    @Autowired
    Emailsend emailsend;

    @Autowired
    GeneratePWD generatePWD;

    private Propertyconfig Propertyconfig;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @PostMapping("create")
    public Map<String, Object> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
        Map<String, Object> response = new HashMap<>();
        TenantContext.setCurrentTenant(tenantName);
        Boolean patientRmhn = mstVisit.getVisitPatientId().getPatientIsRmhn();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(mstVisit);
            System.out.println("Request Body: " + jsonBody);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error printing request body");
            return response;
        }

        System.out.println("patientRmhn =>" + patientRmhn);
        //        MstUnit mstUnit = mstUnitRepository.getById(mstVisit.getVisitUnitId().getUnitId());
        MstUnit mstUnit = mstUnitRepository.getById(mstVisit.getVisitPatientId().getPatientUserId().getUserUnitId().getUnitId());
        String new_user_id;
        String new_visit_id;
        String new_patient_id;
        String filePath = "";
        String sourceData[] = null;
        String imagepath = null;
        String Firstname = "";
        String Firstnamecap = "";
        String Middlename = "";
        String Lastname = "";
        String Lastnamecap = "";
        System.out.println("Is RMHN => " + mstVisit.getVisitPatientId().getPatientIsRmhn());
        mstVisit.getVisitPatientId().setPatientIsRmhn(mstVisit.getVisitPatientId().getPatientIsRmhn());
        if (!mstVisit.getUploadfile().equals("1")) {
            if (mstVisit.getVisitPatientId().getPatientUserId().getUserImage() != "") {
//                System.out.println("---------- patient imaged-----------" + mstVisit.getVisitPatientId().getPatientUserId().getUserImage());
                try {
                    sourceData = mstVisit.getVisitPatientId().getPatientUserId().getUserImage().split(",");
                    byte[] decodedImg = Base64.getDecoder().decode(sourceData[1].getBytes(StandardCharsets.UTF_8));
                    Path destinationFile = Paths.get(Propertyconfig.getPatientImagePath(), mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile() + ".jpg");
                    Files.write(destinationFile, decodedImg);
//                    System.out.println("image name : "+ mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile());
                    mstVisit.getVisitPatientId().getPatientUserId().setUserImage(mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile() + ".jpg");
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        }
        //to convert 1st letter into capital
        if (mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() != null) {
            Firstname = mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname();
            Firstnamecap = Firstname.substring(0, 1).toUpperCase() + Firstname.substring(1);
            mstVisit.getVisitPatientId().getPatientUserId().setUserFirstname(Firstnamecap);

        }
        if (mstVisit.getVisitPatientId().getPatientUserId().getUserMiddlename() != null) {
            Middlename = mstVisit.getVisitPatientId().getPatientUserId().getUserMiddlename();
            String Middlenamecap = Middlename.substring(0, 1).toUpperCase() + Middlename.substring(1);
            mstVisit.getVisitPatientId().getPatientUserId().setUserMiddlename(Middlenamecap);
        }
        if (mstVisit.getVisitPatientId().getPatientUserId().getUserLastname() != null) {
            Lastname = mstVisit.getVisitPatientId().getPatientUserId().getUserLastname();
            Lastnamecap = Lastname.substring(0, 1).toUpperCase() + Lastname.substring(1);
            mstVisit.getVisitPatientId().getPatientUserId().setUserLastname(Lastnamecap);
        }
        if (mstVisit.getVisitPatientId().getPatientUserId().getUserUid() != null) {
            mstVisit.getVisitPatientId().getPatientUserId().setUserUid(mstVisit.getVisitPatientId().getPatientUserId().getUserUid());
        }
        mstVisit.getVisitPatientId().getPatientUserId().setCreatedBy(mstVisit.getVisitPatientId().getPatientUserId().getCreatedBy());
        mstVisit.getVisitPatientId().setCreatedBy(mstVisit.getVisitPatientId().getCreatedBy());
        mstVisit.setCreatedBy(mstVisit.getCreatedBy());
        //
//        String Prefix = Propertyconfig.getmrnopre();
//
//        boolean isDate = Propertyconfig.getMrnoIsDate();
        String Prefix = Propertyconfig.getmrnopre();
        MrNumberPrefix mrNumberPrefix = new MrNumberPrefix();
        boolean isDate = Propertyconfig.getMrnoIsDate();
        String generatedMrNoPrefix = mrNumberPrefix.GenerateMRNO(Prefix, isDate, mstUnit.getUnitCode(), Propertyconfig.getUnitcode());
        int Prefixlength = Propertyconfig.getmrnoprefixstringlength();
        String newMrNo = "";
        String lastMrNo = "";
        try {
//            Iterable<MstPatient> ptList = mstPatientRepository.findAllByIsDeleted(false, PageRequest.of(0, 1));
            lastMrNo = "" + entityManager.createNativeQuery("select patient_mr_no from mst_patient where is_deleted=false order by patient_id desc limit 1").getSingleResult();
//            for (MstPatient mstPatient : ptList) {
//                lastMrNo = mstPatient.getPatientMrNo();
//            }
        } catch (Exception exception) {
            lastMrNo = "";
            System.out.println("Last MRNO is null");
        }
        System.out.println("---------- MRNO-----------" + lastMrNo);
        if (lastMrNo.equals("") || lastMrNo.equals(null)) {
            newMrNo = generatedMrNoPrefix + "00000001";
        } else {
            String number = lastMrNo.substring(Prefixlength);
            int newNumber = Integer.parseInt(number);
            String incNumber = String.format("%08d", newNumber + 1);
            newMrNo = generatedMrNoPrefix + incNumber;
        }
        mstVisit.getVisitPatientId().setPatientMrNo(newMrNo);
        //for Er no generation and send mail and sms by seetanshu
//        if(mstVisit.getVisitRegistrationSource()==2){
//            mstVisit= CreateERNumber(mstVisit);
//        }
        if (mstVisit.getVisitSubDepartmentId() == null || mstVisit.getVisitSubDepartmentId().getSdId() == 0L || mstVisit.getVisitDepartmentId() == null || mstVisit.getVisitDepartmentId().getDepartmentId() == 0L) {
            OPDPatientConfiguration objOPDPatientConfiguration = new OPDPatientConfiguration();
            try {
                String Query = "select p from OPDPatientConfiguration p where p.configUnitId.unitId=" + mstVisit.getVisitPatientId().getPatientUserId().getUserUnitId().getUnitId();
                objOPDPatientConfiguration = entityManager.createQuery(Query, OPDPatientConfiguration.class).getSingleResult();
                MstSubDepartment mstSubDepartment = new MstSubDepartment();
                MstDepartment mstDepartment = new MstDepartment();
                mstDepartment.setDepartmentId(objOPDPatientConfiguration.getConfigDepartmentId().getDepartmentId());
                mstSubDepartment.setSdId(objOPDPatientConfiguration.getConfigSubDepartmentId().getSdId());
                mstVisit.setVisitSubDepartmentId(mstSubDepartment);
                mstVisit.setVisitDepartmentId(mstDepartment);
                mstVisit.setVisitIsInConsultation(false);
                System.out.println(mstVisit);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        MstUser mu = mstuserRepository.save(mstVisit.getVisitPatientId().getPatientUserId());
        mstVisit.getVisitPatientId().setPatientUserId(mu);
        mstVisit = mstvisitRepository.save(mstVisit);
        new_visit_id = String.valueOf(mstVisit.getVisitId());
        new_user_id = String.valueOf(mstVisit.getVisitPatientId().getPatientUserId().getUserId());
        new_patient_id = String.valueOf(mstVisit.getVisitPatientId().getPatientId());
        if (Propertyconfig.getSmsApi()) {
            if (mstVisit.getVisitPatientId().getPatientUserId().getUserMobile() != null) {
                String msg = "";
                try {
                    if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                        MstUnit mstUnit1 = mstUnitRepository.getById(mstVisit.getVisitUnitId().getUnitId());
                        msg = "Thank you for registering to " + mstUnit1.getUnitName() + " is done successfully.";
                        sendsms.sendmessage1(mstVisit.getVisitPatientId().getPatientUserId().getUserMobileCode() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile(), msg);
                    } else {
                        sendsms.sendmessage(mstVisit.getVisitPatientId().getPatientUserId().getUserMobile(), "Registration Successful. MR. No. " + newMrNo);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        if (Propertyconfig.getEmailApi()) {
            if (mstVisit.getVisitPatientId().getPatientUserId().getUserEmail() != null) {
                String msg = "";
                String subject = "";
                try {
                    if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                        MstUnit mstUnit1 = mstUnitRepository.getById(mstVisit.getVisitUnitId().getUnitId());
                        subject = "EHMS Portal - Registration";
                        msg = "Dear " + mstVisit.getVisitPatientId().getPatientUserId().getUserTitleId().getTitleName() + ". " + mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + " " + mstVisit.getVisitPatientId().getPatientUserId().getUserLastname() + " <br><br>" +
                                "Thank you for registering to " + mstUnit1.getUnitName() + " is done successfully.<br><br>" +
                                " <a href=\"https://ehmstp.healthspring.in/\">Click to view details</a><br><br><br>" +
                                " Please share your feedback on clicking below URL <br><br>" +
                                " <a href=\"https://sprw.io/stt-4074cc\">https://sprw.io/stt-4074cc</a><br><br>" +
                                " <br><br> Thanks And Regards " +
                                " <br> eHMS Admin";
                        Emailsend emailsend1 = new Emailsend();
                        emailsend1.sendMAil1(mstVisit.getVisitPatientId().getPatientUserId().getUserEmail(), msg, subject);
                    } else {
//                           emailsend.sendSimpleMessage(mstVisit.getVisitPatientId().getPatientUserId().getUserEmail(), "WELCOME to HSPA", "Registration Successful. MR. No. " + newMrNo);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        respMapanco.put("success", "1");
        String reply = "Added Successfully MRNO:" + newMrNo + "";
        respMapanco.put("MRNO", newMrNo);
        respMapanco.put("msg", reply);
        respMapanco.put("user_id", new_user_id);
        respMapanco.put("visit_id", new_visit_id);
        respMapanco.put("patient_id", new_patient_id);
        respMapanco.put("patientdetails", mstVisit);
        return respMapanco;
    }

    @GetMapping
    @RequestMapping("epatientById/{puuId}")
    public MstPatient epatientById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("puuId") String puuId) {
        TenantContext.setCurrentTenant(tenantName);

        return mstPatientRepository.findAllBypuuId(puuId);
    }






    @PostMapping("revisit")
    public Map<String, String> revisit(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
        TenantContext.setCurrentTenant(tenantName);
        String new_user_id;
        String new_visit_id;
        String new_patient_id;
        String filePath = "";
        String sourceData[] = null;
        String imagepath = null;
        String uuid = UUID.randomUUID().toString();
        if (!mstVisit.getUploadfile().equals("1")) {
            if (mstVisit.getVisitPatientId().getPatientUserId().getUserImage() != "") {
//                System.out.println("---------- patient imaged-----------" + mstVisit.getVisitPatientId().getPatientUserId().getUserImage());
                try {
                    sourceData = mstVisit.getVisitPatientId().getPatientUserId().getUserImage().split(",");
                    byte[] decodedImg = Base64.getDecoder().decode(sourceData[1].getBytes(StandardCharsets.UTF_8));
                    Path destinationFile = Paths.get(Propertyconfig.getPatientImagePath(), uuid + ".jpg");
                    Files.write(destinationFile, decodedImg);
                    mstVisit.getVisitPatientId().getPatientUserId().setUserImage(uuid + ".jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        MstUser mstuser1 = mstuserRepository.getById(mstVisit.getVisitPatientId().getPatientUserId().getUserId());
        if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
            if (!mstVisit.getVisitPatientId().getPatientIsDependant()
                    && mstVisit.getVisitPatientId().getPatientType().getPtId() == 1
                    && mstuser1.getUserName() != null) {
                mstVisit.getVisitPatientId().getPatientUserId().setUserName(mstuser1.getUserName());
                mstVisit.getVisitPatientId().getPatientUserId().setPassword(mstuser1.getPassword());
            } else if (!mstVisit.getVisitPatientId().getPatientIsDependant()
                    && mstVisit.getVisitPatientId().getPatientType().getPtId() == 1
                    && mstuser1.getUserName() == null) {
                mstVisit.getVisitPatientId().getPatientUserId().setUserName(mstVisit.getVisitPatientId().getPatientUserId().getUserDrivingNo());
                mstVisit.getVisitPatientId().getPatientUserId().setPassword("Healthspring");
            }
        }
        MstUser mstuser = mstuserRepository.save(mstVisit.getVisitPatientId().getPatientUserId());
        mstVisit.getVisitPatientId().setPatientUserId(mstuser);
        MstPatient mstpat = mstPatientRepository.save(mstVisit.getVisitPatientId());
        mstVisit.setVisitPatientId(mstpat);
        if (mstVisit.getVisitSubDepartmentId() == null || mstVisit.getVisitSubDepartmentId().getSdId() == 0L || mstVisit.getVisitDepartmentId() == null || mstVisit.getVisitDepartmentId().getDepartmentId() == 0L) {
            OPDPatientConfiguration objOPDPatientConfiguration = new OPDPatientConfiguration();
            try {
                //String Query = "select p from OPDPatientConfiguration p where p.configUnitId.unitId=" + mstVisit.getVisitUnitId().getUnitId();
                String Query = " select  p from  OPDPatientConfiguration p  where p.configUnitId.unitId = " + mstVisit.getVisitUnitId().getUnitId();

                objOPDPatientConfiguration = entityManager.createQuery(Query, OPDPatientConfiguration.class).getSingleResult();
                MstSubDepartment mstSubDepartment = new MstSubDepartment();
                MstDepartment mstDepartment = new MstDepartment();
                mstDepartment.setDepartmentId(objOPDPatientConfiguration.getConfigDepartmentId().getDepartmentId());
                mstSubDepartment.setSdId(objOPDPatientConfiguration.getConfigSubDepartmentId().getSdId());
                mstVisit.setVisitSubDepartmentId(mstSubDepartment);
                mstVisit.setVisitDepartmentId(mstDepartment);
//                System.out.println(mstVisit);
            } catch (Exception e) {
                //e.printStackTrace();
                return null;
            }

        }
        mstVisit.getVisitPatientId().getPatientUserId().setCreatedBy(mstVisit.getVisitPatientId().getPatientUserId().getCreatedBy());
        mstVisit.getVisitPatientId().setCreatedBy(mstVisit.getVisitPatientId().getCreatedBy());
        mstVisit = mstvisitRepository.save(mstVisit);
        new_visit_id = String.valueOf(mstVisit.getVisitId());
        new_user_id = String.valueOf(mstVisit.getVisitPatientId().getPatientUserId().getUserId());
        new_patient_id = String.valueOf(mstVisit.getVisitPatientId().getPatientId());
        respMap.put("success", "1");
        String reply = "Added Successfully MRNO:";
        //   respMap.put("MRNO", newMrNo);
        respMap.put("msg", reply);
        respMap.put("user_id", new_user_id);
        respMap.put("visit_id", new_visit_id);
        respMap.put("patient_id", new_patient_id);
        return respMap;
    }

    @PostMapping("emrrevisit")
    public Map<String, String> emrrevisit(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
        TenantContext.setCurrentTenant(tenantName);
        String new_user_id;
        String new_visit_id;
        String new_patient_id;
        String filePath = "";
        String sourceData[] = null;
        String imagepath = null;
        if (!mstVisit.getUploadfile().equals("1")) {
            if (mstVisit.getVisitPatientId().getPatientUserId().getUserImage() != "") {
//                System.out.println("---------- patient imaged-----------" + mstVisit.getVisitPatientId().getPatientUserId().getUserImage());
                try {
                    sourceData = mstVisit.getVisitPatientId().getPatientUserId().getUserImage().split(",");
                    byte[] decodedImg = Base64.getDecoder().decode(sourceData[1].getBytes(StandardCharsets.UTF_8));
                    Path destinationFile = Paths.get(Propertyconfig.getPatientImagePath(), mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile() + ".jpg");
                    Files.write(destinationFile, decodedImg);
                    mstVisit.getVisitPatientId().getPatientUserId().setUserImage(mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile() + ".jpg");
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        }
        MstUser mstuser = mstuserRepository.save(mstVisit.getVisitPatientId().getPatientUserId());
        mstVisit.getVisitPatientId().setPatientUserId(mstuser);
        MstPatient mstpat = mstPatientRepository.save(mstVisit.getVisitPatientId());
        mstVisit.setVisitPatientId(mstpat);
        mstVisit.setVisitId((long) 0);
        MstVisit mstVisitnew = mstvisitRepository.save(mstVisit);
        new_visit_id = String.valueOf(mstVisitnew.getVisitId());
        new_user_id = String.valueOf(mstVisit.getVisitPatientId().getPatientUserId().getUserId());
        new_patient_id = String.valueOf(mstVisit.getVisitPatientId().getPatientId());
        respMap.put("success", "1");
        String reply = "Added Successfully";
        //   respMap.put("MRNO", newMrNo);
        respMap.put("msg", reply);
        respMap.put("user_id", new_user_id);
        respMap.put("visit_id", new_visit_id);
        respMap.put("patient_id", new_patient_id);
        return respMap;
    }

    private void saveUploadedFiles(@RequestHeader("X-tenantId") String tenantName, List<MultipartFile> files) throws IOException {
        TenantContext.setCurrentTenant(tenantName);
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; //next pls
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get("c:\\abc\\" + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }

    @GetMapping
    @RequestMapping("patientfilter")
    public Iterable<MstPatient> patientfilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        //        System.out.println("query parameter" + qString);
        return mstPatientRepository.findByPatientMrNoEqualsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("getbasepath")
    public String getbasepath(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        //        System.out.println("BasePath" + Propertyconfig.getbasepath());
        return Propertyconfig.getbasepath();
    }

    @GetMapping
    @RequestMapping("patientmnofilter")
    public Iterable<MstPatient> patientmnofilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        //        System.out.println("query parameter" + qString);
        return mstPatientRepository.findByPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("patientnamefilter")
    public Iterable<MstPatient> patientnamefilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        //        System.out.println("query parameter" + qString);
        return mstPatientRepository.findByPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("patientnidfilter")
    public Iterable<MstPatient> patientnidfilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        //        System.out.println("query parameter" + qString);
        return mstPatientRepository.findByPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstPatient> records;
        records = mstPatientRepository.findByPatientMrNoContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{patientId}")
    public MstPatient read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatient mstPatient = mstPatientRepository.getById(patientId);
        return mstPatient;
    }

    @RequestMapping("/patientbymrnumber/{patientmrno}")
    public MstPatient readByMRNumber(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientmrno") String patientMrNo) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatient mstPatient = mstPatientRepository.findByPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(patientMrNo);
        return mstPatient;
    }

    @RequestMapping("update")
    public MstPatient update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPatient mstPatient) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.save(mstPatient);
    }

    @RequestMapping("basepath/")
    public String read(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return Propertyconfig.getbasepath();
    }

    @PutMapping("delete/{patientId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatient mstPatient = mstPatientRepository.getById(patientId);
        if (mstPatient != null) {
            mstPatient.setIsDeleted(true);
            mstPatient.setIsActive(false);
            mstPatientRepository.save(mstPatient);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }


    @RequestMapping(value = "/update1/{patientId}", method = RequestMethod.PUT)
    public Map<String, Object> eupdateByUserId(@RequestHeader("X-tenantId") String tenantName,
                                               @PathVariable("patientId") Long patientId,
                                               @RequestBody Map<String, String> requestBody) {
        Map<String, Object> respMap = new HashMap<>();
        TenantContext.setCurrentTenant(tenantName);

        System.out.println("patientId"+patientId);
        String puuId = requestBody.get("puuId");
        System.out.println("puuId"+puuId);

        // Fetch patient matching the given patientId
        MstPatient mstPatient = mstPatientRepository.getById(patientId);

        // If patient exists, update its PUUID
        if (mstPatient != null) {
            mstPatient.setPuuId(puuId);

            // Save the updated patient
            mstPatientRepository.save(mstPatient);

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


//    @RequestMapping("update1/{patientId}")
//    public Map<String, Object> eupdateByUserId(@RequestHeader("X-tenantId") String tenantName,
//                                               @PathVariable("patientId") Long patientId,
//                                               @RequestParam("puuId") String puuId) {
//        Map<String, Object> respMap = new HashMap<>();
//        TenantContext.setCurrentTenant(tenantName);
//
//        System.out.println("patientId"+patientId);
//        System.out.println("puuId"+puuId);
//
//        // Fetch patient matching the given patientId
//        MstPatient mstPatient = mstPatientRepository.getById(patientId);
//
//        // If patient exists, update its PUUID
//        if (mstPatient != null) {
//            mstPatient.setPuuId(puuId);
//
//            // Save the updated patient
//            mstPatientRepository.save(mstPatient);
//
//            // Populate response map
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        } else {
//            // Patient not found
//            respMap.put("msg", "Patient not found");
//            respMap.put("success", "0");
//        }
//
//        return respMap;
//    }


    @GetMapping
    @RequestMapping("search")
    public Iterable<MstPatient> search(@RequestHeader("X-tenantId") String tenantName,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                       @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                       @RequestParam(value = "qString", required = false) String qString,
                                       @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                       @RequestParam(value = "col", required = false, defaultValue = "patientId") String col,
                                       @RequestParam(value = "colname", required = false) String colname) {
        TenantContext.setCurrentTenant(tenantName);
//        System.out.println("-----col name-----------------------" + colname);
        if (colname == "mrno" || colname.equals("mrno")) {
            return mstPatientRepository.findByPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (colname == "patientname" || colname.equals("patientname")) {
            return mstPatientRepository.findByPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (colname == "mobileno" || colname.equals("mobileno")) {
            return mstPatientRepository.findByPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (colname.equals("nameMr")) {
            return mstPatientRepository.findByPatientUserIdUserFirstnameContainsOrPatientUserIdUserMiddlenameContainsOrPatientUserIdUserLastnameContainsOrPatientMrNoContainsOrPatientUserIdUserMobileContainsOrPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstPatientRepository.findByPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("searchEmployeeNo")
    public List<MstPatient> searchEmployeeNo(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByDistinctDrivingNoContainsAndIsActiveTrueAndIsDeletedFalse(qString);
    }

    @GetMapping
    @RequestMapping("patientserch")
    public Iterable<MstPatient> patser(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "patientId") String col, @RequestParam(value = "colname", required = false) String colname) {
        TenantContext.setCurrentTenant(tenantName);
        //        System.out.println("-----col name-----------------------" + colname);
        if (colname == "mrno" || colname.equals("mrno")) {
            return mstPatientRepository.findByPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstPatientRepository.findAllByPatientMrNoContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString);
        }
    }

    @GetMapping
    @RequestMapping("getrecordservicebymrno")
    public MstPatient list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByPatientMrNoEqualsAndIsActiveTrueAndIsDeletedFalse(qString);
    }

    @GetMapping
    @RequestMapping("getrecordservicebynationalid")
    public Iterable<MstPatient> getrecordservicebynationalid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByPatientUserIdUserUidEqualsAndIsActiveTrueAndIsDeletedFalse(qString);
    }

    @GetMapping
    @RequestMapping("getrecordservicebymobileno")
    public Iterable<MstPatient> getrecordservicebymobileno(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByPatientUserIdUserMobileEqualsAndIsActiveTrueAndIsDeletedFalse(qString);
    }

    @GetMapping
    @RequestMapping("getrecordservicebyemailid")
    public Iterable<MstPatient> getrecordservicebyemailid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByPatientUserIdUserEmailEqualsAndIsActiveTrueAndIsDeletedFalse(qString);
    }

    @GetMapping
    @RequestMapping("getrecordservicebymykadid")
    public Iterable<MstPatient> getrecordservicebymykadid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByPatientUserIdUserGpMarNoEqualsAndIsActiveTrueAndIsDeletedFalse(qString);
    }

    @GetMapping("lastvisitautocompleteMR")
    public List<MstVisitSearchDto> getLastVisitByAutoSearchMR(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        List<Object[]> list = mstPatientRepository.findByVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
        List<MstVisitSearchDto> mstVisitSearchDto = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MstVisitSearchDto mstVisitSearchDto1 = new MstVisitSearchDto(Long.parseLong("" + list.get(i)[0]), "" + list.get(i)[1], "" + list.get(i)[2], "" + list.get(i)[3], "" + list.get(i)[4], "" + list.get(i)[5], Long.parseLong("" + list.get(i)[6]));
            mstVisitSearchDto.add(mstVisitSearchDto1);
        }
//        return mstPatientRepository.findByVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
        return mstVisitSearchDto;
    }

    @GetMapping("lastvisitautocompleteName")
    public List<MstVisitSearchDto> getLastVisitByAutoSearchName(@RequestHeader("X-tenantId") String tenantName,
                                                                @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        List<Object[]> list = mstPatientRepository.findByVisitPatientIdPatientNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
//        return mstPatientRepository.findByVisitPatientIdPatientUserIdUserFullnameContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, searchString, PageRequest.of(0, 10));
//        return list;
        List<MstVisitSearchDto> mstVisitSearchDto = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MstVisitSearchDto mstVisitSearchDto1 = new MstVisitSearchDto(Long.parseLong("" + list.get(i)[0]),
                    "" + list.get(i)[1],
                    "" + list.get(i)[2],
                    "" + list.get(i)[3],
                    "" + list.get(i)[4],
                    "" + list.get(i)[5],
                    Long.parseLong("" + list.get(i)[6]));
            mstVisitSearchDto.add(mstVisitSearchDto1);
        }
        return mstVisitSearchDto;
    }

    @GetMapping("lastvisitautocompletePhone")
    public List<MstVisitSearchDto> getLastVisitByAutoSearchPhone(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
       // return mstPatientRepository.findByVisitPatientIdPatientUserIdUserResidencePhoneContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
        List<Object[]> list = mstPatientRepository.findByVisitPatientIdPatientPhoneContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
//        return  list;
        List<MstVisitSearchDto> mstVisitSearchDto = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MstVisitSearchDto mstVisitSearchDto1 = new MstVisitSearchDto(Long.parseLong("" + list.get(i)[0]),
                    "" + list.get(i)[1],
                    "" + list.get(i)[2],
                    "" + list.get(i)[3],
                    "" + list.get(i)[4],
                    "" + list.get(i)[5],
                    Long.parseLong("" + list.get(i)[6]));
            mstVisitSearchDto.add(mstVisitSearchDto1);
        }
        return mstVisitSearchDto;
    }

    @GetMapping("lastvisitautocompleteMobile")
    public List<MstVisitSearchDto> getLastVisitByAutoSearchMobil(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        List<Object[]> list = mstPatientRepository.findByVisitPatientIdPatientMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
//        return list;
        //return mstPatientRepository.findByVisitPatientIdPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
        List<MstVisitSearchDto> mstVisitSearchDto = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MstVisitSearchDto mstVisitSearchDto1 = new MstVisitSearchDto(Long.parseLong("" + list.get(i)[0]),
                    "" + list.get(i)[1],
                    "" + list.get(i)[2],
                    "" + list.get(i)[3],
                    "" + list.get(i)[4],
                    "" + list.get(i)[5],
                    Long.parseLong("" + list.get(i)[6]));
            mstVisitSearchDto.add(mstVisitSearchDto1);
        }
        return mstVisitSearchDto;
    }

    @GetMapping("lastvisitautocompleteID")
    public List<MstVisitSearchDto> getLastVisitByAutoSearchID(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
       // return mstPatientRepository.findByVisitPatientIdPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
        List<Object[]> list = mstPatientRepository.findByVisitPatientIdPatientIdNumberContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
//        return list;

        List<MstVisitSearchDto> mstVisitSearchDto = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MstVisitSearchDto mstVisitSearchDto1 = new MstVisitSearchDto(Long.parseLong("" + list.get(i)[0]),
                    "" + list.get(i)[1],
                    "" + list.get(i)[2],
                    "" + list.get(i)[3],
                    "" + list.get(i)[4],
                    "" + list.get(i)[5],
                    Long.parseLong("" + list.get(i)[6]));
            mstVisitSearchDto.add(mstVisitSearchDto1);
        }
        return mstVisitSearchDto;
    }

    @GetMapping("lastvisitautocompleteEmail")
    public List<MstVisitSearchDto> getLastVisitByAutoSearchEmail(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
       // return mstPatientRepository.findByVisitPatientIdPatientUserIdUserEmailContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
        List<Object[]> list = mstPatientRepository.findByVisitPatientIdPatientIdEmailContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString);
//        return  list;
        List<MstVisitSearchDto> mstVisitSearchDto = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MstVisitSearchDto mstVisitSearchDto1 = new MstVisitSearchDto(Long.parseLong("" + list.get(i)[0]),
                    "" + list.get(i)[1],
                    "" + list.get(i)[2],
                    "" + list.get(i)[3],
                    "" + list.get(i)[4],
                    "" + list.get(i)[5],
                    Long.parseLong("" + list.get(i)[6]));
            mstVisitSearchDto.add(mstVisitSearchDto1);
        }
        return mstVisitSearchDto;
    }

    @GetMapping("lastvisitautocompleteEmpNo")
    public List<MstPatient> getLastVisitByAutoSearchEmpNo(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByVisitPatientIdPatientUserIdUserDrivingNoContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
    }
//
//    @PostMapping("saveemegencyuser")
//    public Map<String, Object> saveemegencyuser(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
//
//        String new_user_id;
//        String new_visit_id;
//        String new_patient_id;
//        String filePath = "";
//        String sourceData[] = null;
//        String imagepath = null;
//        if (!mstVisit.getUploadfile().equals("1")) {
//            if (mstVisit.getVisitPatientId().getPatientUserId().getUserImage() != "") {
//                try {
//                    sourceData = mstVisit.getVisitPatientId().getPatientUserId().getUserImage().split(",");
//                    byte[] decodedImg = Base64.getDecoder().decode(sourceData[1].getBytes(StandardCharsets.UTF_8));
//                    Path destinationFile = Paths.get(Propertyconfig.getprofileimagepath(), mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile() + ".jpg");
//                    Files.write(destinationFile, decodedImg);
//                    mstVisit.getVisitPatientId().getPatientUserId().setUserImage(mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile() + ".jpg");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    System.out.println(e);
//                }
//            }
//        }
//        String Prefix = Propertyconfig.getmrnopre();
//        MrNumberPrefix mrNumberPrefix = new MrNumberPrefix();
//        boolean isDate = Propertyconfig.getMrnoIsDate();
//        String generatedMrNoPrefix = mrNumberPrefix.GenerateMRNO(Prefix, isDate);
//        int Prefixlength = Propertyconfig.getmrnoprefixstringlength();
//        String newMrNo = "";
//        String lastMrNo = "";
//
//        try {
//            Iterable<MstPatient> ptList = mstPatientRepository.findAllByIsDeleted(false,
//                    new PageRequest(0, 1, Sort.Direction.fromStringOrNull("DESC"), "patientId"));
//            for (MstPatient mstPatient : ptList) {
//                lastMrNo = mstPatient.getPatientMrNo();
//            }
//        } catch (Exception exception) {
//            lastMrNo = "";
//            System.out.println("Last MRNO is null");
//        }
//        if (lastMrNo.equals("")) {
//            newMrNo = generatedMrNoPrefix + "00000001";
//        } else {
//            String number = lastMrNo.substring(Prefixlength);
//            int newNumber = Integer.parseInt(number);
//            String incNumber = String.format("%08d", newNumber + 1);
//            newMrNo = generatedMrNoPrefix + incNumber;
//        }
//
//
//        String newErNo = "";
//        String lastErNo = "";
//        String ERPrefix = Propertyconfig.getErnoPreFix();
//        ErNumberPrefix erNumberPrefix = new ErNumberPrefix();
//        boolean isErDate = Propertyconfig.getErnoIsDate();
//        String generatedErNoPrefix = erNumberPrefix.GenerateERNO(ERPrefix, isErDate);
//        int ERPrefixlength = Propertyconfig.getErnoPrefixstringLength();
//        try {
//            Iterable<MstPatient> ptList = mstPatientRepository.findAllByIsDeletedAndPatientErNoNotNull(false,
//                    new PageRequest(0, 1, Sort.Direction.fromStringOrNull("DESC"), "patientId"));
//            for (MstPatient mstPatient : ptList) {
//                lastErNo = mstPatient.getPatientErNo();
//            }
//        } catch (Exception exception) {
//            lastErNo = "";
//            System.out.println("Last ERNO is null");
//        }
//        if (lastErNo.equals("")) {
//            newErNo = generatedErNoPrefix + "00000001";
//        } else {
//            String number = lastErNo.substring(ERPrefixlength);
//            int newNumber = Integer.parseInt(number);
//            String incNumber = String.format("%08d", newNumber + 1);
//            newErNo = generatedErNoPrefix + incNumber;
//        }
//        mstVisit.getVisitPatientId().setPatientMrNo(newMrNo);
//        mstVisit.getVisitPatientId().setPatientErNo(newErNo);
//        mstVisit.getVisitPatientId().setEmergency(true);
//        mstVisit = mstvisitRepository.save(mstVisit);
////        TemrTimeline temrTimeline = new TemrTimeline();
////        temrTimeline.setTimelineVisitId(mstVisit);
////        temrTimeline.setTimelinePatientId(mstVisit.getVisitPatientId());
////        temrTimeline.setTimelineTime(new Date());
////        temrTimeline.setTimelineDate(new Date());
////        temrTimeline.setEMRFinal(false);
////        if (mstVisit.getVisitStaffId() != null) {
////            temrTimeline.setTimelineStaffId(mstVisit.getVisitStaffId());
////        }
////
////        temrTimeline = temrTimelineRepository.save(temrTimeline);
//        new_visit_id = String.valueOf(mstVisit.getVisitId());
//        new_user_id = String.valueOf(mstVisit.getVisitPatientId().getPatientUserId().getUserId());
//        new_patient_id = String.valueOf(mstVisit.getVisitPatientId().getPatientId());
//        respMapobj.put("success", "1");
//        String reply = "Added Successfully ERNO:" + newErNo + "";
//        respMapobj.put("MRNO", newMrNo);
//        respMapobj.put("ERNO", newErNo);
//        respMapobj.put("msg", reply);
//        respMapobj.put("user_id", new_user_id);
//        respMapobj.put("visit_id", new_visit_id);
//        respMapobj.put("patient_id", new_patient_id);
//        respMapobj.put("visitdetail", mstVisit);
//        return respMapobj;
//    }

    @PostMapping("saveemegencyuser")
    public Map<String, Object> saveemegencyuser(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisit mstVisit) {
        TenantContext.setCurrentTenant(tenantName);
        MstUnit mstUnit = mstUnitRepository.getById(mstVisit.getVisitUnitId().getUnitId());
        String new_user_id;
        String new_visit_id;
        String filePath = "";
        String sourceData[] = null;
        String imagepath = null;
        if (!mstVisit.getUploadfile().equals("1")) {
            if (mstVisit.getVisitPatientId().getPatientUserId().getUserImage() != "") {
//                System.out.println("---------- patient imaged-----------" + mstVisit.getVisitPatientId().getPatientUserId().getUserImage());
                try {
                    sourceData = mstVisit.getVisitPatientId().getPatientUserId().getUserImage().split(",");
                    byte[] decodedImg = Base64.getDecoder().decode(sourceData[1].getBytes(StandardCharsets.UTF_8));
                    Path destinationFile = Paths.get(Propertyconfig.getPatientImagePath(), mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile() + ".jpg");
                    Files.write(destinationFile, decodedImg);
                    mstVisit.getVisitPatientId().getPatientUserId().setUserImage(mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() + mstVisit.getVisitPatientId().getPatientUserId().getUserMobile() + ".jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            }
        }
        //to convert 1st letter into capital
//        System.out.println("------------------" + mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname());
        if (mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname() != null) {
            String Firstname = mstVisit.getVisitPatientId().getPatientUserId().getUserFirstname();
            String Firstnamecap = Firstname.substring(0, 1).toUpperCase() + Firstname.substring(1);
            mstVisit.getVisitPatientId().getPatientUserId().setUserFirstname(Firstnamecap);
        }
        if (mstVisit.getVisitPatientId().getPatientUserId().getUserMiddlename() != null) {
            String Middlename = mstVisit.getVisitPatientId().getPatientUserId().getUserMiddlename();
            if (Middlename != null) {
                String Middlenamecap = Middlename.substring(0, 1).toUpperCase() + Middlename.substring(1);
                mstVisit.getVisitPatientId().getPatientUserId().setUserMiddlename(Middlenamecap);
            }
        }
        if (mstVisit.getVisitPatientId().getPatientUserId().getUserLastname() != null) {
            String Lastname = mstVisit.getVisitPatientId().getPatientUserId().getUserLastname();
            String Lastnamecap = Lastname.substring(0, 1).toUpperCase() + Lastname.substring(1);
            mstVisit.getVisitPatientId().getPatientUserId().setUserLastname(Lastnamecap);
        }
        //
//        String Prefix = Propertyconfig.getmrnopre();
//
//        boolean isDate = Propertyconfig.getMrnoIsDate();
        String Prefix = Propertyconfig.getmrnopre();
        MrNumberPrefix mrNumberPrefix = new MrNumberPrefix();
        boolean isDate = Propertyconfig.getMrnoIsDate();
        String generatedMrNoPrefix = mrNumberPrefix.GenerateMRNO(Prefix, isDate, mstUnit.getUnitCode(), Propertyconfig.getUnitcode());
        int Prefixlength = Propertyconfig.getmrnoprefixstringlength();
        String newMrNo = "";
        String lastMrNo = "";
        try {
            Iterable<MstPatient> ptList = mstPatientRepository.findAllByIsDeleted(false, PageRequest.of(0, 1));
            for (MstPatient mstPatient : ptList) {
                lastMrNo = mstPatient.getPatientMrNo();
            }
        } catch (Exception exception) {
            lastMrNo = "";
            System.out.println("Last MRNO is null");
        }
        System.out.println("---------- MRNO-----------" + lastMrNo);
        if (lastMrNo.equals("") || lastMrNo.equals(null)) {
            newMrNo = generatedMrNoPrefix + "00000001";
        } else {
            String number = lastMrNo.substring(Prefixlength);
            int newNumber = Integer.parseInt(number);
            String incNumber = String.format("%08d", newNumber + 1);
            newMrNo = generatedMrNoPrefix + incNumber;
        }
        String newErNo = "";
        String lastErNo = "";
        String ERPrefix = Propertyconfig.getErnoPreFix();
        ErNumberPrefix erNumberPrefix = new ErNumberPrefix();
        boolean isErDate = Propertyconfig.getErnoIsDate();
        String generatedErNoPrefix = erNumberPrefix.GenerateERNO(ERPrefix, isErDate);
        int ERPrefixlength = Propertyconfig.getErnoPrefixstringLength();
        try {
            Iterable<MstPatient> ptList = mstPatientRepository.findAllByIsDeletedAndPatientErNoNotNull(false, PageRequest.of(0, 1));
            for (MstPatient mstPatient : ptList) {
                lastErNo = mstPatient.getPatientErNo();
//                System.out.println("-------last er no----" + lastErNo);
            }
        } catch (Exception exception) {
            lastErNo = "";
//            System.out.println("Last ERNO is null");
        }
        if (lastErNo.equals("")) {
            newErNo = generatedErNoPrefix + "00000001";
//            System.out.println("-------newErNo er no----" + newErNo);
        } else {
//            System.out.println("------ERPrefixlength---" + ERPrefixlength);
            String number = lastErNo.substring(ERPrefixlength);
//            System.out.println("------number---" + number);
            int newNumber = Integer.parseInt(number);
//            System.out.println("------newNumber---" + newNumber);
            String incNumber = String.format("%08d", newNumber + 1);
            newErNo = generatedErNoPrefix + incNumber;
        }
        mstVisit.getVisitPatientId().setPatientMrNo(newMrNo);
        mstVisit.getVisitPatientId().setPatientErNo(newErNo);
        mstVisit.getVisitPatientId().setEmergency(true);
        mstVisit = mstvisitRepository.save(mstVisit);
        new_visit_id = String.valueOf(mstVisit.getVisitId());
        new_user_id = String.valueOf(mstVisit.getVisitPatientId().getPatientUserId().getUserId());
        if (Propertyconfig.getSmsApi()) {
            if (mstVisit.getVisitPatientId().getPatientUserId().getUserMobile() != null) {
//                System.out.println(mstVisit.getVisitPatientId().getPatientUserId().getUserMobile());
                // sendsms.sendmessage(mstVisit.getVisitPatientId().getPatientUserId().getUserMobile(),"Your Registation Successfully");
                // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
                try {
                    sendsms.sendmessage(mstVisit.getVisitPatientId().getPatientUserId().getUserMobile(), "Registration Successful. MR. No. " + newMrNo);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        if (Propertyconfig.getEmailApi()) {
            if (mstVisit.getVisitPatientId().getPatientUserId().getUserEmail() != null) {
//                System.out.println(mstVisit.getVisitPatientId().getPatientUserId().getUserEmail());
                try {
                    //emailsend.sendSimpleMessage(mstVisit.getVisitPatientId().getPatientUserId().getUserEmail(), "WELCOME to HSPA", "Registration Successful. MR. No. " + newMrNo);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        respMapanco.put("success", "1");
        String reply = "Added Successfully MRNO:" + newMrNo + "";
        respMapanco.put("MRNO", newMrNo);
        respMapanco.put("msg", reply);
        respMapanco.put("user_id", new_user_id);
        respMapanco.put("visit_id", new_visit_id);
        respMapanco.put("patientdetails", mstVisit);
        return respMapanco;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstPatient> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstPatientRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstPatientRepository.findByPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping("getpatient/{patientId}")
    public MstPatient getPatientDetails(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatient mstPatient = new MstPatient();
        if (patientId != null) {
            mstPatient = mstPatientRepository.getById(patientId);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return mstPatient;
    }

    @GetMapping("getpatientbyuserid/{userId}")
    public MstPatient getPatientDetailsbyuserid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("userId") Long userId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatient mstPatient = new MstPatient();
        if (userId != null) {
            mstPatient = mstPatientRepository.findByPatientUserIdUserIdEqualsAndIsActiveTrueAndIsDeletedFalse(userId);
        } else {
            mstPatient.setPatientId(0);
        }
        return mstPatient;
    }

    //created by seetanshu for registration list
    @GetMapping
    @RequestMapping("listByUnitId")
    public Iterable<MstPatient> listByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                             @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                             @RequestParam(value = "qString", required = false) String qString,
                                             @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                             @RequestParam(value = "col", required = false, defaultValue = "patient_id") String col,
                                             @RequestParam(value = "unitId", required = false) long unitId,
                                             @RequestParam(value = "searchType", required = false) String searchType) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchType.equals("default")) {
            return mstPatientRepository.findAllPatientIdAndPatientUserId(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("mrno")) {
            return mstPatientRepository.findAllPatientIdAndPatientMrNo(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("pname")) {
            return mstPatientRepository.findAllPatientIdAndPatientUserIdUserFirstname(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("mno")) {
            return mstPatientRepository.findAllPatientIdAndPatientUserIdUserMobile(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("empno")) {
            return mstPatientRepository.findAllPatientIdAndPatientUserIdUserDrivingNo(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("patientType")) {
            return mstPatientRepository.findAllPatientIdAndPatientType(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstPatientRepository.findAllPatientIdAndPatientUserIdUserUid(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }



    @GetMapping
    @RequestMapping("listEMRPateintByUnitId")
    public Iterable<MstPatient> listEMRPateintByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                       @RequestParam(value = "qString", required = false) String qString,
                                                       @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                       @RequestParam(value = "col", required = false, defaultValue = "patient_id") String col,
                                                       @RequestParam(value = "unitId", required = false) long unitId,
                                                       @RequestParam(value = "searchType", required = false) String searchType) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchType.equals("default")) {
            return mstPatientRepository.findAllEMRPatientIdAndPatientUserId(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("mrno")) {
            return mstPatientRepository.findAllEMRPatientIdAndPatientMrNo(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("pname")) {
            return mstPatientRepository.findAllEMRPatientIdAndPatientUserIdUserFirstname(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("mno")) {
            return mstPatientRepository.findAllEMRPatientIdAndPatientUserIdUserMobile(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("pno")) {
            return mstPatientRepository.findAllEMRPatientIdAndPatientUserIdUserPhone(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("email")) {
            return mstPatientRepository.findAllEMRPatientIdAndPatientUserIdUserEmail(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstPatientRepository.findAllEMRPatientIdAndPatientUserIdUserUid(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("listpatientbystartenddate")
    public Iterable<MstPatient> listpatientbystartenddate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                          @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                          @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid,
                                                          @RequestParam(value = "startdate", required = false) String startdate,
                                                          @RequestParam(value = "enddate", required = false) String enddate,
                                                          @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                          @RequestParam(value = "col", required = false, defaultValue = "patient_id") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findAllByCreatedDateBetween(startdate, enddate, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("listEMRpatientbystartenddate")
    public Iterable<MstPatient> listEMRpatientbystartenddate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                             @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                             @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid,
                                                             @RequestParam(value = "startdate", required = false) String startdate,
                                                             @RequestParam(value = "enddate", required = false) String enddate,
                                                             @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                             @RequestParam(value = "col", required = false, defaultValue = "patient_id") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findAllEMRPatientListByCreatedDateBetween(startdate, enddate, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }


/*

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size,
            @RequestParam(value = "globalFilter", required = false) String globalFilter) {

        List<Tuple> items = mstPatientService.getMstPatientForDropdown(page, size, globalFilter);
        return items;
    }
*/

    public MstVisit CreateERNumber(@RequestHeader("X-tenantId") String tenantName, MstVisit objMstVisit) {
        TenantContext.setCurrentTenant(tenantName);
        String newErNo = "";
        String lastErNo = "";
        String ERPrefix = Propertyconfig.getErnoPreFix();
        ErNumberPrefix erNumberPrefix = new ErNumberPrefix();
        boolean isErDate = Propertyconfig.getErnoIsDate();
        String generatedErNoPrefix = erNumberPrefix.GenerateERNO(ERPrefix, isErDate);
        int ERPrefixlength = Propertyconfig.getErnoPrefixstringLength();
        try {
            Iterable<MstPatient> ptList = mstPatientRepository.findAllByIsDeletedAndPatientErNoNotNull(false, PageRequest.of(0, 1));
            for (MstPatient mstPatient : ptList) {
                lastErNo = mstPatient.getPatientErNo();
//                System.out.println("-------last er no----" + lastErNo);
            }
        } catch (Exception exception) {
            lastErNo = "";
//            System.out.println("Last ERNO is null");
        }
        if (lastErNo.equals("")) {
            newErNo = generatedErNoPrefix + "00000001";
//            System.out.println("-------newErNo er no----" + newErNo);
        } else {
//            System.out.println("------ERPrefixlength---" + ERPrefixlength);
            String number = lastErNo.substring(ERPrefixlength);
//            System.out.println("------number---" + number);
            int newNumber = Integer.parseInt(number);
//            System.out.println("------newNumber---" + newNumber);
            String incNumber = String.format("%08d", newNumber + 1);
            newErNo = generatedErNoPrefix + incNumber;
        }
        objMstVisit.getVisitPatientId().setPatientErNo(newErNo);
        objMstVisit.getVisitPatientId().setEmergency(true);
        return objMstVisit;
    }
    ////

    @GetMapping("lastemrvisitautocompleteMR")
    public List<MstPatient> getEmrLastVisitByAutoSearchMR(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByVisitPatientIdPatientMrNoContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
    }

    @GetMapping("lastemrvisitautocompleteName")
    public List<MstPatient> getEmrLastVisitByAutoSearchName(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByVisitPatientIdPatientUserIdUserFirstnameContainsOrVisitPatientIdPatientUserIdUserLastnameContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, searchString, PageRequest.of(0, 10));
    }

    @GetMapping("lastemrvisitautocompletePhone")
    public List<MstPatient> getEmrLastVisitByAutoSearchPhone(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByVisitPatientIdPatientUserIdUserResidencePhoneContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
    }

    @GetMapping("lastemrvisitautocompleteMobile")
    public List<MstPatient> getEmrLastVisitByAutoSearchMobil(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByVisitPatientIdPatientUserIdUserMobileContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
    }

    @GetMapping("lastemrvisitautocompleteID")
    public List<MstPatient> getEmrLastVisitByAutoSearchID(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByVisitPatientIdPatientUserIdUserUidContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
    }

    @GetMapping("lastemrvisitautocompleteEmail")
    public List<MstPatient> getEmrLastVisitByAutoSearchEmail(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findByVisitPatientIdPatientUserIdUserEmailContainsAndIsEmergencyTrueAndIsActiveTrueAndIsDeletedFalseOrderByVisitId(searchString, PageRequest.of(0, 10));
    }

    @RequestMapping("issueESmartCard/{patientId}")
    public Map<String, String> isseuESmartCard(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatient mstPatient = mstPatientRepository.getById(patientId);
        if (mstPatient != null) {
            if (!mstPatient.getIsCardIssued()) {
                mstPatient.setIsCardIssued(true);
                mstPatient.setCardIssuedDate(new Date());
                mstPatientRepository.save(mstPatient);
                respMap.put("msg", "Operation Successful");
                respMap.put("cardIssued", "1");
                respMap.put("success", "1");
            } else {
                respMap.put("msg", "Operation Failed");
                respMap.put("CardIssued", "0");
                respMap.put("success", "0");
            }
        }
        return respMap;
    }

    @GetMapping(value = {"searchPatientByautocompleteMRUnitId"})
    public List<MstPatient> searchPatientByautocompleteMRUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findAllByPatientMrNoContainsAndPatientUserIdUserUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, unitId, PageRequest.of(0, 30));
    }

    @GetMapping(value = {"searchPatientByautocompleteNameUnitId"})
    public List<MstPatient> searchPatientByautocompleteNameUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findAllByPatientUserIdUserFirstnameContainsOrPatientUserIdUserLastnameContainsAndPatientUserIdUserUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, qString, unitId, PageRequest.of(0, 30));
    }

    @GetMapping(value = {"searchPatientByautocompleteMobileUnitId"})
    public List<MstPatient> searchPatientByautocompleteMobileUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstPatientRepository.findAllByPatientUserIdUserMobileContainsAndPatientUserIdUserUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, unitId, PageRequest.of(0, 30));
    }

    @RequestMapping("/patientsearchbymrnamemobile/{key}/{unitid}")
    public Map<String, Object> patientsearch(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key, @PathVariable("unitid") Long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstPatient> records;
//        records = trnAdmissionRepository.findAllByAdmissionPatientIdPatientMrNoContainsOrAdmissionPatientIdPatientUserIdUserMobileContainsOrAdmissionPatientIdPatientUserIdUserFirstnameContainsOrAdmissionPatientIdPatientUserIdUserLastnameContainsAndAdmissionUnitIdUnitIdEqualsAndAdmissionStatusFalseAndIsActiveTrueAndIsDeletedFalse(key, key, key, key, unitid);
        records = mstPatientRepository.findAllByPatientMrNoContainsOrPatientUserIdUserMobileContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserLastnameContainsAndPatientUserIdUserUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(key, key, key, key, unitid);
        automap.put("record", records);
        return automap;
    }

    //    @GetMapping
    @RequestMapping("searchlistByUnitId")
    public Map<String, Object> searchListByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPatientDTO searchPatientDTO) {
//        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
//        int limit = Integer.parseInt(size);
//        List<String> queue = new ArrayList<String>();
        TenantContext.setCurrentTenant(tenantName);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (searchPatientDTO.getFromdate().equals("") || searchPatientDTO.getFromdate().equals("null")) {
            searchPatientDTO.setFromdate(strDate);
        }
        if (searchPatientDTO.getTodate().equals("") || searchPatientDTO.getTodate().equals("null")) {
//            todate = strDate;
            searchPatientDTO.setTodate(strDate);
        }
        String query = "SELECT mp.patient_id, mp.patient_mr_no,CONCAT(mu.user_firstname ,' ', mu.user_lastname) , " +
                " mg.gender_name , mu.user_dob , mu.user_age , mu.user_uid, mu.user_id_doc_image, mu.user_mobile , mp.created_date, " +
                " mu.created_by, mu.user_id, un.unit_name, mu.user_driving_no, mpt.pt_name, mu.user_email FROM mst_visit mv " +
                " INNER join mst_patient mp " +
                " INNER join mst_patient_type mpt " +
                " INNER JOIN mst_user mu " +
                " INNER JOIN mst_gender mg " +
                " Left JOIN mst_unit un ON un.unit_id = mu.user_unit_id " +
                " WHERE mp.patient_id=mv.visit_patient_id AND mu.user_id=mp.patient_user_id AND mp.patient_type=mpt.pt_id " +
                " AND mg.gender_id=mu.user_gender_id  AND mp.is_deleted=0";
//                "AND mu.user_unit_id = '" + searchPatientDTO.getUnitId() + "'";
        if (searchPatientDTO.getPatientMrNo() != null && !searchPatientDTO.getPatientMrNo().equals("")) {
            query += " and mp.patient_mr_no like  '%" + searchPatientDTO.getPatientMrNo() + "%' ";
        }
        if (!searchPatientDTO.getUnitId().equals("0") && searchPatientDTO.getUnitId() != null && !searchPatientDTO.getUnitId().equals("")) {
            query += " AND mu.user_unit_id =" + searchPatientDTO.getUnitId() + " ";
        }
        if (searchPatientDTO.getUserFirstname() != null && !searchPatientDTO.getUserFirstname().equals("")) {
            query += "  and (mu.user_firstname like '%" + searchPatientDTO.getUserFirstname() + "%' or mu.user_lastname like '%" + searchPatientDTO.getUserFirstname() + "%'  ) ";
        }
        if (searchPatientDTO.getUserMobile() != null && !searchPatientDTO.getUserMobile().equals("")) {
            query += " and mu.user_mobile like '%" + searchPatientDTO.getUserMobile() + "%'  ";
        }
        if (searchPatientDTO.getUseruId() != null && !searchPatientDTO.getUseruId().equals("")) {
            query += " and mu.user_uid like '%" + searchPatientDTO.getUseruId() + "%' ";
        }
        if (searchPatientDTO.getEmpNo() != null && !searchPatientDTO.getEmpNo().equals("")) {
            query += " and mu.user_driving_no like '%" + searchPatientDTO.getEmpNo() + "%' ";
        }
        if (searchPatientDTO.getPtId() != null && !searchPatientDTO.getPtId().equals("0")) {
            query += " and mp.patient_type = " + searchPatientDTO.getPtId() + " ";
        }
        if (!searchPatientDTO.getGenderId().equals("0") && searchPatientDTO.getGenderId() != null && !searchPatientDTO.getGenderId().equals("")) {
            query += " and mu.user_gender_id = " + searchPatientDTO.getGenderId() + " ";
        }
        if (!searchPatientDTO.getAgeFrom().equals("") && !searchPatientDTO.getAgeFrom().equals("0") && searchPatientDTO.getAgeFrom() != null && !searchPatientDTO.getAgeTo().equals("") && !searchPatientDTO.getAgeTo().equals("0") && searchPatientDTO.getAgeTo() != null) {
            query += " and mu.user_age BETWEEN '" + searchPatientDTO.getAgeFrom() + "' and '" + searchPatientDTO.getAgeTo() + "' ";
        }
        query += " and date(mp.created_date) BETWEEN '" + searchPatientDTO.getFromdate() + "' and '" + searchPatientDTO.getTodate() + "' ";
        query += " GROUP BY mp.patient_id ";
        query += " order by mp.patient_id desc ";
        String Cquery = "select count(*) from ( " + query + " ) as combine ";
        query += " limit " + ((searchPatientDTO.getOffset() - 1) * searchPatientDTO.getLimit()) + "," + searchPatientDTO.getLimit();
        System.out.println("query ==>" + query);
        List<Object[]> queue = entityManager.createNativeQuery(query).getResultList();
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(Cquery).getSingleResult();
        Map<String, Object> map = new HashMap<>();
        map.put("content", queue);
        map.put("count", cc);
        return map;

    }

    @RequestMapping("searchlistByUnitId1")
    public Map<String, Object> searchListByUnitId1(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPatientDTO searchPatientDTO) {
        TenantContext.setCurrentTenant(tenantName);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (searchPatientDTO.getFromdate().equals("") || searchPatientDTO.getFromdate().equals("null")) {
            searchPatientDTO.setFromdate(strDate);
        }
        if (searchPatientDTO.getTodate().equals("") || searchPatientDTO.getTodate().equals("null")) {
//            todate = strDate;
            searchPatientDTO.setTodate(strDate);
        }
        String query = "SELECT mp.patient_id, mp.patient_mr_no,CONCAT(mu.user_firstname ,' ', mu.user_lastname) , mg.gender_name , mu.user_dob , mu.user_age , mu.user_uid, mu.user_id_doc_image, mu.user_mobile , mp.created_date, mu.created_by, mu.user_id, un.unit_name, mu.user_driving_no, mpt.pt_name, mu.user_email " +
                " FROM mst_patient mp " +
                " INNER join mst_patient_type mpt " +
                " INNER JOIN mst_user mu ON mu.user_id = mp.patient_user_id" +
                " INNER JOIN mst_gender mg ON mg.gender_id = mu.user_gender_id" +
                " Left JOIN mst_unit un ON un.unit_id = mu.user_unit_id " +
                " WHERE mp.is_deleted=0 AND mp.patient_is_rmhn =0 AND mp.is_register_at_camp =0 ";
//        Commented by Amol for Display Migration Data
//        AND mp.patient_type=mpt.pt_id AND mg.gender_id=mu.user_gender_id
        if (searchPatientDTO.getPatientMrNo() != null && !searchPatientDTO.getPatientMrNo().equals("")) {
            query += " and mp.patient_mr_no like  '%" + searchPatientDTO.getPatientMrNo() + "%' ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (!searchPatientDTO.getUnitId().equals("0") && searchPatientDTO.getUnitId() != null && !searchPatientDTO.getUnitId().equals("")) {
            query += " AND mu.user_unit_id =" + searchPatientDTO.getUnitId() + " ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getUserFirstname() != null && !searchPatientDTO.getUserFirstname().equals("")) {
            query += "  and (mu.user_firstname like '%" + searchPatientDTO.getUserFirstname() + "%' or mu.user_lastname like '%" + searchPatientDTO.getUserFirstname() + "%'  ) ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getUserMobile() != null && !searchPatientDTO.getUserMobile().equals("")) {
            query += " and mu.user_mobile like '%" + searchPatientDTO.getUserMobile() + "%'  ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getUseruId() != null && !searchPatientDTO.getUseruId().equals("")) {
            query += " and mu.user_uid like '%" + searchPatientDTO.getUseruId() + "%' ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getEmpNo() != null && !searchPatientDTO.getEmpNo().equals("")) {
            query += " and mu.user_driving_no like '%" + searchPatientDTO.getEmpNo() + "%' ";
        }
        if (searchPatientDTO.getPtId() != null && !searchPatientDTO.getPtId().equals("0")) {
            query += " and mp.patient_type = " + searchPatientDTO.getPtId() + " ";
        }
        if (!searchPatientDTO.getGenderId().equals("0") && searchPatientDTO.getGenderId() != null && !searchPatientDTO.getGenderId().equals("")) {
            query += " and mu.user_gender_id = " + searchPatientDTO.getGenderId() + " ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (!searchPatientDTO.getAgeFrom().equals("") && searchPatientDTO.getAgeFrom() != null && !searchPatientDTO.getAgeTo().equals("") && !searchPatientDTO.getAgeTo().equals("0") && searchPatientDTO.getAgeTo() != null) {
            query += " and mu.user_age BETWEEN '" + searchPatientDTO.getAgeFrom() + "' and '" + searchPatientDTO.getAgeTo() + "' ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        query += " and date(mp.created_date) BETWEEN '" + searchPatientDTO.getFromdate() + "' and '" + searchPatientDTO.getTodate() + "' ";
        query += " group by mp.patient_id";
        query += " order by mp.patient_id desc ";
        String Cquery = "select count(*) from ( " + query + " ) as combine ";
        query += " limit " + ((searchPatientDTO.getOffset() - 1) * searchPatientDTO.getLimit()) + "," + searchPatientDTO.getLimit();
        System.out.println("query ==>" + query);
        List<Object[]> queue = entityManager.createNativeQuery(query).getResultList();
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(Cquery).getSingleResult();
        Map<String, Object> map = new HashMap<>();
        map.put("content", queue);
        map.put("count", cc);
        return map;

    }

    @RequestMapping("searchlistByUnitId2")
    public Map<String, Object> searchListByUnitId2(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPatientDTO searchPatientDTO) {
        TenantContext.setCurrentTenant(tenantName);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (searchPatientDTO.getFromdate().equals("") || searchPatientDTO.getFromdate().equals("null")) {
            searchPatientDTO.setFromdate(strDate);
        }
        if (searchPatientDTO.getTodate().equals("") || searchPatientDTO.getTodate().equals("null")) {
//            todate = strDate;
            searchPatientDTO.setTodate(strDate);
        }
        String query = "SELECT mp.patient_id, mp.patient_mr_no,CONCAT(mu.user_firstname ,' ', mu.user_lastname) , mg.gender_name , mu.user_dob , mu.user_age , mu.user_uid, mu.user_id_doc_image, mu.user_mobile , mp.created_date, mu.created_by, mu.user_id, un.unit_name, mu.user_driving_no, mpt.pt_name, mu.user_email " +
                " FROM mst_patient mp " +
                " INNER join mst_patient_type mpt " +
                " INNER JOIN mst_user mu " +
                " INNER JOIN mst_gender mg " +
                " Left JOIN mst_unit un ON un.unit_id = mu.user_unit_id " +
                " WHERE mu.user_id=mp.patient_user_id AND mp.patient_type=mpt.pt_id AND mg.gender_id=mu.user_gender_id  AND mp.is_deleted=0 AND mp.patient_is_rmhn =1 ";
        if (searchPatientDTO.getPatientMrNo() != null && !searchPatientDTO.getPatientMrNo().equals("")) {
            query += " and mp.patient_mr_no like  '%" + searchPatientDTO.getPatientMrNo() + "%' ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (!searchPatientDTO.getUnitId().equals("0") && searchPatientDTO.getUnitId() != null && !searchPatientDTO.getUnitId().equals("")) {
            query += " AND mu.user_unit_id =" + searchPatientDTO.getUnitId() + " ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getUserFirstname() != null && !searchPatientDTO.getUserFirstname().equals("")) {
            query += "  and (mu.user_firstname like '%" + searchPatientDTO.getUserFirstname() + "%' or mu.user_lastname like '%" + searchPatientDTO.getUserFirstname() + "%'  ) ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getUserMobile() != null && !searchPatientDTO.getUserMobile().equals("")) {
            query += " and mu.user_mobile like '%" + searchPatientDTO.getUserMobile() + "%'  ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getUseruId() != null && !searchPatientDTO.getUseruId().equals("")) {
            query += " and mu.user_uid like '%" + searchPatientDTO.getUseruId() + "%' ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getEmpNo() != null && !searchPatientDTO.getEmpNo().equals("")) {
            query += " and mu.user_driving_no like '%" + searchPatientDTO.getEmpNo() + "%' ";
        }
        if (searchPatientDTO.getPtId() != null && !searchPatientDTO.getPtId().equals("0")) {
            query += " and mp.patient_type = " + searchPatientDTO.getPtId() + " ";
        }
        if (!searchPatientDTO.getGenderId().equals("0") && searchPatientDTO.getGenderId() != null && !searchPatientDTO.getGenderId().equals("")) {
            query += " and mu.user_gender_id = " + searchPatientDTO.getGenderId() + " ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (!searchPatientDTO.getAgeFrom().equals("") && !searchPatientDTO.getAgeFrom().equals("0") && searchPatientDTO.getAgeFrom() != null && !searchPatientDTO.getAgeTo().equals("") && !searchPatientDTO.getAgeTo().equals("0") && searchPatientDTO.getAgeTo() != null) {
            query += " and mu.user_age BETWEEN '" + searchPatientDTO.getAgeFrom() + "' and '" + searchPatientDTO.getAgeTo() + "' ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        query += " and date(mp.created_date) BETWEEN '" + searchPatientDTO.getFromdate() + "' and '" + searchPatientDTO.getTodate() + "' ";
        query += " order by mp.patient_id desc ";
        String Cquery = "select count(*) from ( " + query + " ) as combine ";
        query += " limit " + ((searchPatientDTO.getOffset() - 1) * searchPatientDTO.getLimit()) + "," + searchPatientDTO.getLimit();
        System.out.println("query ==>" + query);
        List<Object[]> queue = entityManager.createNativeQuery(query).getResultList();
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(Cquery).getSingleResult();
        Map<String, Object> map = new HashMap<>();
        map.put("content", queue);
        map.put("count", cc);
        return map;

    }


    @RequestMapping("searchcamplistByUnitId")
    public Map<String, Object> searchcamplistByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPatientDTO searchPatientDTO) {
        TenantContext.setCurrentTenant(tenantName);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (searchPatientDTO.getFromdate().equals("") || searchPatientDTO.getFromdate().equals("null")) {
            searchPatientDTO.setFromdate(strDate);
        }
        if (searchPatientDTO.getTodate().equals("") || searchPatientDTO.getTodate().equals("null")) {
//            todate = strDate;
            searchPatientDTO.setTodate(strDate);
        }
        String query = "SELECT mp.patient_id, mp.patient_mr_no,CONCAT(mu.user_firstname ,' ', mu.user_lastname) , mg.gender_name , mu.user_dob , mu.user_age , mu.user_uid, mu.user_id_doc_image, mu.user_mobile , mp.created_date, mu.created_by, mu.user_id, un.unit_name, mu.user_driving_no, mpt.pt_name, mu.user_email, nmc.camp_name, ncv.cv_name " +
                " FROM mst_patient mp " +
                " INNER join mst_patient_type mpt " +
                " INNER JOIN mst_user mu " +
                " INNER JOIN mst_gender mg " +
                " Left JOIN mst_unit un ON un.unit_id = mu.user_unit_id " +
                " LEFT JOIN nmst_camp nmc ON nmc.camp_id = mp.patient_camp_id " +
                " LEFT JOIN nmst_camp_visit ncv ON ncv.cv_id = mp.patient_camp_visit_id " +
                " WHERE mu.user_id=mp.patient_user_id AND mp.patient_type=mpt.pt_id AND mg.gender_id=mu.user_gender_id  AND mp.is_deleted=0 AND mp.is_register_at_camp =1 ";
        if (searchPatientDTO.getPatientMrNo() != null && !searchPatientDTO.getPatientMrNo().equals("")) {
            query += " and mp.patient_mr_no like  '%" + searchPatientDTO.getPatientMrNo() + "%' ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (!searchPatientDTO.getUnitId().equals("0") && searchPatientDTO.getUnitId() != null && !searchPatientDTO.getUnitId().equals("")) {
            query += " AND mu.user_unit_id =" + searchPatientDTO.getUnitId() + " ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (!searchPatientDTO.getCampId().equals("0") && searchPatientDTO.getCampId() != null && !searchPatientDTO.getCampId().equals("")) {
            query += " AND mp.patient_camp_id =" + searchPatientDTO.getCampId() + " ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (!searchPatientDTO.getCvId().equals("0") && searchPatientDTO.getCvId() != null && !searchPatientDTO.getCvId().equals("")) {
            query += " AND mp.patient_camp_visit_id =" + searchPatientDTO.getCvId() + " ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getUserFirstname() != null && !searchPatientDTO.getUserFirstname().equals("")) {
            query += "  and (mu.user_firstname like '%" + searchPatientDTO.getUserFirstname() + "%' or mu.user_lastname like '%" + searchPatientDTO.getUserFirstname() + "%'  ) ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getUserMobile() != null && !searchPatientDTO.getUserMobile().equals("")) {
            query += " and mu.user_mobile like '%" + searchPatientDTO.getUserMobile() + "%'  ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getUseruId() != null && !searchPatientDTO.getUseruId().equals("")) {
            query += " and mu.user_uid like '%" + searchPatientDTO.getUseruId() + "%' ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (searchPatientDTO.getEmpNo() != null && !searchPatientDTO.getEmpNo().equals("")) {
            query += " and mu.user_driving_no like '%" + searchPatientDTO.getEmpNo() + "%' ";
        }
        if (searchPatientDTO.getPtId() != null && !searchPatientDTO.getPtId().equals("0")) {
            query += " and mp.patient_type = " + searchPatientDTO.getPtId() + " ";
        }
        if (!searchPatientDTO.getGenderId().equals("0") && searchPatientDTO.getGenderId() != null && !searchPatientDTO.getGenderId().equals("")) {
            query += " and mu.user_gender_id = " + searchPatientDTO.getGenderId() + " ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        if (!searchPatientDTO.getAgeFrom().equals("") && !searchPatientDTO.getAgeFrom().equals("0") && searchPatientDTO.getAgeFrom() != null && !searchPatientDTO.getAgeTo().equals("") && !searchPatientDTO.getAgeTo().equals("0") && searchPatientDTO.getAgeTo() != null) {
            query += " and mu.user_age BETWEEN '" + searchPatientDTO.getAgeFrom() + "' and '" + searchPatientDTO.getAgeTo() + "' ";
//            searchPatientDTO.setFromdate("2000-01-01");
        }
        query += " and date(mp.created_date) BETWEEN '" + searchPatientDTO.getFromdate() + "' and '" + searchPatientDTO.getTodate() + "' ";
        query += " order by mp.patient_id desc ";
        String Cquery = "select count(*) from ( " + query + " ) as combine ";
        query += " limit " + ((searchPatientDTO.getOffset() - 1) * searchPatientDTO.getLimit()) + "," + searchPatientDTO.getLimit();
        System.out.println("query ==>" + query);
        List<Object[]> queue = entityManager.createNativeQuery(query).getResultList();
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(Cquery).getSingleResult();
        Map<String, Object> map = new HashMap<>();
        map.put("content", queue);
        map.put("count", cc);
        return map;

    }

    @PostMapping("createPatient")
    public Map<String, Object> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody PatientDao patientDao) {
        TenantContext.setCurrentTenant(tenantName);
        String sourceData[] = null;
        try {
            MstPatient mstPatient = new MstPatient();
            mstPatient.setEmergency(false);
            mstPatient.setIsDeleted(false);
            mstPatient.setIsActive(true);
            mstPatient.setPatientIsBroughtDead(false);
            mstPatient.setCreatedBy(patientDao.getCreatedBy());
            MstPatientType patientType = new MstPatientType();
            patientType.setPtId(patientDao.getPatientType());
            mstPatient.setPatientType(patientType);
            MstUser mstUser = new MstUser();
//            mstUser.setIsUserOrStaff(false);
            mstUser.setCreatedBy(patientDao.getCreatedBy());
            mstUser.setUserFirstname(patientDao.getUsername());
//            mstUser.setIsInsurancePatient(patientDao.getInsurancePatient());
            mstUser.setUserAddress(patientDao.getAddress());
            mstUser.setUserFirstname(patientDao.getFirstName());
            mstUser.setUserLastname(patientDao.getLastName());
            mstUser.setUserTitleId(mstTitleRepository.getById(patientDao.getTitleId()));

            MstGender gender = new MstGender();
            gender.setGenderId(patientDao.getGender());
            mstUser.setUserGenderId(gender);

            mstUser.setUserMobileCode(patientDao.getUserMobileCode());
            mstUser.setUserMobile(patientDao.getMobileno());
            MstUnit mstUnit = new MstUnit();
            mstUnit.setUnitId(patientDao.getUnitId());
            mstUser.setUserUnitId(mstUnit);
            mstUser.setPassword(patientDao.getPassword());
            if (patientDao.getBloodgroup() != null) {
                MstBloodgroup bloodgroup = new MstBloodgroup();
                bloodgroup.setBloodgroupId(patientDao.getBloodgroup());
                mstUser.setUserBloodgroupId(bloodgroup);
            }
            mstUser.setUserAge(patientDao.getAge());
            mstUser.setuserMonth(patientDao.getUserMonth());
            mstUser.setuserDay(patientDao.getUserDay());
            mstUser.setUserArea(patientDao.getAddress());
            mstUser.setUserDob(patientDao.getDob());
            mstUser.setUserEmail(patientDao.getEmail());
//            mstUser.setUserUid(patientDao.getPid());
            mstUser.setUserFullname(patientDao.getFirstName() + " " + patientDao.getLastName());
            mstUser.setUserIsDobReal(true);
            mstUser.setuserPincode(patientDao.getPincode());
//            mstUser.setUserName(patientDao.getEmail());
            if(patientDao.getUserCardTypeId() != null){
                MstCitizenIdProof mstCitizenIdProof = new MstCitizenIdProof();
                mstCitizenIdProof.setCipId(patientDao.getUserCardTypeId());
                mstUser.setUserCitizenId(mstCitizenIdProof);
            }
            mstUser.setUserName(patientDao.getFirstName().toLowerCase() + "_" + patientDao.getLastName().toLowerCase());
            mstUser.setUserUid(patientDao.getUserUid());
            if (patientDao.getUserNationalityId() != null) {
                mstUser.setUserNationalityId(mstNationalityRepository.getById(patientDao.getUserNationalityId()));
            }
            if (patientDao.getCity() != null) {
                MstCity city = new MstCity();
                city.setCityId(patientDao.getCity());
                mstUser.setUserCityId(city);
            }
            String PWD = generatePWD.generateRandomPWD();
//            mstUser.setPassword(PWD);
            mstUser.setPassword(patientDao.getLastName().toLowerCase()+"123");
            mstPatient.setPatientUserId(mstUser);
//            mstPatient = mstPatientRepository.save(mstPatient);
            String Prefix = Propertyconfig.getmrnopre();
            MrNumberPrefix mrNumberPrefix = new MrNumberPrefix();
            boolean isDate = Propertyconfig.getMrnoIsDate();
            String generatedMrNoPrefix = mrNumberPrefix.GenerateMRNO(Prefix, isDate, mstUser.getUserUnitId().getUnitCode(), Propertyconfig.getUnitcode());
            int Prefixlength = Propertyconfig.getmrnoprefixstringlength();
            String newMrNo = "";
            String lastMrNo = "";
            try {
//                Iterable<MstPatient> ptList = mstPatientRepository.findAllByIsDeleted(false, PageRequest.of(0, 1));
                lastMrNo = "" + entityManager.createNativeQuery("select patient_mr_no from mst_patient where is_deleted=false order by patient_id desc limit 1").getSingleResult();
//                for (MstPatient mstPatient1 : ptList) {
//                    lastMrNo = mstPatient1.getPatientMrNo();
//                }
            } catch (Exception exception) {
                lastMrNo = "";
                System.out.println("Last MRNO is null");
            }
            System.out.println("---------- MRNO-----------" + lastMrNo);
            if (lastMrNo.equals("") || lastMrNo.equals(null)) {
                newMrNo = generatedMrNoPrefix + "00000001";
            } else {
                String number = lastMrNo.substring(Prefixlength);
                int newNumber = Integer.parseInt(number);
                String incNumber = String.format("%08d", newNumber + 1);
                newMrNo = generatedMrNoPrefix + incNumber;
            }
//            String newMrNo = Propertyconfig.getmrnopre() + "/" + mstPatient.getPatientId();
            mstPatient.setPatientMrNo(newMrNo);
            mstPatient.setPatientErNo(newMrNo);
            MstPatient mp = mstPatientRepository.save(mstPatient);
            MstUser mu = mp.getPatientUserId();
//            if (patientDao.getUserInsuranceId() != null) {
//                mu.setUserInsuranceId(mstCompanyRepository.getById(patientDao.getUserInsuranceId()));
//            }
//            mu.setUserInsuranceDateOfIssue(patientDao.getUserInsuranceDateOfIssue());
//            mu.setUserInsuranceDateOfExp(patientDao.getUserInsuranceDateOfExp());
//            mu.setUserInsuranceCardNo(patientDao.getUserInsuranceCardNo());
//            mu.setUserInsuranceCardFp(patientDao.getUserInsuranceCardFp());
//            mu.setUserIdDocFp(patientDao.getUserIdDocFp());
//            mu.setUserName(newMrNo);
            if (!patientDao.getUploadfile().equals("1")) {
                if (patientDao.getUserImage() != "" && patientDao.getUserImage() != null) {
                    System.out.println("--patient image--" + patientDao.getUserImage());
                    try {
                        sourceData = patientDao.getUserImage().split(",");
                        byte[] decodedImg = Base64.getDecoder().decode(sourceData[1].getBytes(StandardCharsets.UTF_8));
                        Path destinationFile = Paths.get(Propertyconfig.getPatientImagePath(), mu.getUserFirstname() + mu.getUserMobile() + ".jpg");
                        Files.write(destinationFile, decodedImg);
                        mu.setUserImage(mu.getUserFirstname() + mu.getUserMobile() + ".jpg");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (patientDao.getUserImage() != null) {
                    mu.setUserImage(patientDao.getUserImage());
                }
            }
            mstuserRepository.save(mu);
            respMapanco.put("status", 1);
            respMapanco.put("username", mstUser.getUserName());
            respMapanco.put("password", mstPatient.getPatientUserId().getPassword());
        } catch (Exception e) {
            respMapanco.put("status", 0);
            e.printStackTrace();
        }
        return respMapanco;
    }

    @PutMapping("getPatientIdByUserId/{userId}")
    public Map<String, String> findPatientIdByUserId(@PathVariable("userId") Long userId) {
        Long patientId = mstPatientRepository.findPatientIdByUserId(userId);
        respMap.put("patientId", "" + patientId);
        return respMap;
    }


    @GetMapping
    @RequestMapping("camplistByUnitId")
    public Iterable<MstPatient> camplistByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                             @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                             @RequestParam(value = "qString", required = false) String qString,
                                             @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                             @RequestParam(value = "col", required = false, defaultValue = "patient_id") String col,
                                             @RequestParam(value = "unitId", required = false) long unitId,
                                             @RequestParam(value = "searchType", required = false) String searchType) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchType.equals("default")) {
            return mstPatientRepository.findAllCampPatientIdAndPatientUserId(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("mrno")) {
            return mstPatientRepository.findAllPatientIdAndPatientMrNo(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("pname")) {
            return mstPatientRepository.findAllPatientIdAndPatientUserIdUserFirstname(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("mno")) {
            return mstPatientRepository.findAllPatientIdAndPatientUserIdUserMobile(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("empno")) {
            return mstPatientRepository.findAllPatientIdAndPatientUserIdUserDrivingNo(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (searchType.equals("patientType")) {
            return mstPatientRepository.findAllPatientIdAndPatientType(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstPatientRepository.findAllPatientIdAndPatientUserIdUserUid(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }


}
            
