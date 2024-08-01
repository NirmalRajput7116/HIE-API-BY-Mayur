package com.cellbeans.hspa.mstemergencypatient;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*import com.cellbeans.hspa.mstemergencyvisit.MstEmergencyVisit;
import com.cellbeans.hspa.mstemergencyvisit.MstEmergencyVisitRepository;*/

@RestController
@RequestMapping("/mst_emergency_patient")
public class MstEmergencyPatientController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstEmergencyPatientRepository mstEmergencyPatientRepository;
    @Autowired
    MstUserRepository mstuserRepository;
    private Propertyconfig app;

    @Autowired
    public void setApp(Propertyconfig app) {
        this.app = app;
    }

/*    @Autowired
    MstEmergencyVisitRepository mstEmergencyVisitRepository;*/

/*    @PostMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstEmergencyVisit mstEmergencyVisit) {
        String new_user_id;
        String new_visit_id;
        String filePath = "";
        String sourceData[] = null;
        String imagepath = null;
        if(!mstEmergencyVisit.getUploadfile().equals("1")){
            if(mstEmergencyVisit.getEvPatientId().getEpUserId().getUserImage() != ""){
                try {
                    sourceData = mstEmergencyVisit.getEvPatientId().getEpUserId().getUserImage().split(",");
                    byte[] decodedImg = Base64.getDecoder().decode(sourceData[1].getBytes(StandardCharsets.UTF_8));
//                    Path destinationFile = Paths.get("c:\\abc\\", mstEmergencyVisit.getEvPatientId().getEpUserId().getUserFirstname()+mstEmergencyVisit.getEvPatientId().getEpUserId().getUserMobile()+".jpg");
//                    imagepath = "c:\\abc\\"+mstEmergencyVisit.getEvPatientId().getEpUserId().getUserFirstname()+mstEmergencyVisit.getEvPatientId().getEpUserId().getUserMobile()+".jpg";
//                    mstEmergencyVisit.getEvPatientId().getEpUserId().setUserImage(imagepath);
                    // System.out.println("----------Path---------"+Propertyconfig.getprofileimagepath());
                    Path destinationFile = Paths.get(Propertyconfig.getprofileimagepath(), mstEmergencyVisit.getEvPatientId().getEpUserId().getUserFirstname()+mstEmergencyVisit.getEvPatientId().getEpUserId().getUserMobile()+".jpg");
                    Files.write(destinationFile, decodedImg);
                    mstEmergencyVisit.getEvPatientId().getEpUserId().setUserImage(destinationFile.toString());
                    Files.write(destinationFile, decodedImg);
                }catch (Exception e) {
                    e.printStackTrace();
                    // System.out.println(e);
                }
            }
        }
        String Prefix = "E";
        String newErNo = "";
        String lastErNo = "";
        try {
            Iterable<MstEmergencyPatient> ptList = mstEmergencyPatientRepository.findAllByIsDeleted(false,
                    new PageRequest(0, 1, Sort.Direction.fromStringOrNull("DESC"), "epId"));
            for (MstEmergencyPatient mstEmergencyPatient : ptList) {
                lastErNo = mstEmergencyPatient.getEpErNo();
            }
        } catch (Exception exception) {
            lastErNo = "";
            // System.out.println("Last ERNO is null");
        }
        if (lastErNo.equals("")) {
            newErNo = Prefix + "00000001";
        } else {
            String number = lastErNo.substring(4);
            int newNumber = Integer.parseInt(number);
            String incNumber = String.format("%08d", newNumber + 1);
            newErNo = Prefix + incNumber;
        }
        mstEmergencyVisit.getEvPatientId().setEpErNo(newErNo);
        mstEmergencyVisit =  mstEmergencyVisitRepository.save(mstEmergencyVisit);
        new_visit_id = String.valueOf(mstEmergencyVisit.getEvId());
        new_user_id = String.valueOf(mstEmergencyVisit.getEvPatientId().getEpUserId().getUserId());
        respMap.put("success", "1");
        String reply = "Added Successfully ERNO:" + newErNo + "";
        respMap.put("ERNO", newErNo);
        respMap.put("msg", reply);
        respMap.put("user_id", new_user_id);
        respMap.put("visit_id", new_visit_id);
        return respMap;
    }*/

    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; //next pls
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get("c:\\abc\\" + file.getOriginalFilename());
            Files.write(path, bytes);

        }

    }



    /*@GetMapping
    @RequestMapping("testfilter")
    public Iterable<mstEmergencyPatient> testfilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                           @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                           @RequestParam(value = "qString", required = false) String qString,
                                           @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                           @RequestParam(value = "col", required = false, defaultValue = "patientId") String col){

    }
*/
//    @GetMapping
//    @RequestMapping("patientfilter")
//    public Iterable<MstEmergencyPatient> patientfilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                              @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                              @RequestParam(value = "qString", required = false) String qString,
//                                              @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                              @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
//        // System.out.println("query parameter" + qString);
//        //return mstEmergencyPatient.findAll();
//        return mstEmergencyPatientRepository.findByPatientMrNoEqualsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//    }
//    @GetMapping
//    @RequestMapping("patientmnofilter")
//    public Iterable<MstEmergencyPatient> patientmnofilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                                 @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                                 @RequestParam(value = "qString", required = false) String qString,
//                                                 @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                                 @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
//        // System.out.println("query parameter" + qString);
//        return mstEmergencyPatientRepository.findByPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//    }
//    @GetMapping
//    @RequestMapping("patientnamefilter")
//    public Iterable<MstEmergencyPatient> patientnamefilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                                  @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                                  @RequestParam(value = "qString", required = false) String qString,
//                                                  @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                                  @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
//        // System.out.println("query parameter" + qString);
//        return mstEmergencyPatientRepository.findByPatientUserIdUserFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//    }
//    @GetMapping
//    @RequestMapping("patientnidfilter")
//    public Iterable<MstEmergencyPatient> patientnidfilter(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                                 @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                                 @RequestParam(value = "qString", required = false) String qString,
//                                                 @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                                 @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
//        // System.out.println("query parameter" + qString);
//        return mstEmergencyPatientRepository.findByPatientUserIdUserUidContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//    }
//    @RequestMapping("/autocomplete/{key}")
//    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//        Map<String, Object> automap = new HashMap<String, Object>();
//        List<MstEmergencyPatient> records;
//        records = mstEmergencyPatientRepository.findByPatientMrNoContains(key);
//        automap.put("record", records);
//        return automap;
//    }
//    @RequestMapping("byid/{patientId}")
//    public MstEmergencyPatient read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
//        MstEmergencyPatient mstEmergencyPatient = mstEmergencyPatientRepository.getById(patientId);
//        return mstEmergencyPatient;
//    }
//    @RequestMapping("/patientbymrnumber/{patientmrno}")
//    public MstEmergencyPatient readByMRNumber(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientmrno") String patientMrNo) {
//        MstEmergencyPatient mstEmergencyPatient = mstEmergencyPatientRepository.findByPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(patientMrNo);
//        return mstEmergencyPatient;
//    }
//    @RequestMapping("update")
//    public MstEmergencyPatient update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstEmergencyPatient mstEmergencyPatient) {
//        return mstEmergencyPatientRepository.save(mstEmergencyPatient);
//    }
//    @GetMapping
//    @RequestMapping("list")
//    public Iterable<MstEmergencyPatient> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                     @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                     @RequestParam(value = "qString", required = false) String qString,
//                                     @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                     @RequestParam(value = "col", required = false, defaultValue = "patientId") String col) {
//
//        if (qString == null || qString.equals("")) {
//            return mstEmergencyPatientRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//
//        } else {
//
//            return mstEmergencyPatientRepository.findByPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//
//           // return mstPatientRepository.findByPatientMrNoContainsOrPatienUserFirstnamtUserIdeContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//
//        }
//
//    }
//    @PutMapping("delete/{patientId}")
//    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
//        MstEmergencyPatient mstEmergencyPatient = mstEmergencyPatientRepository.getById(patientId);
//        if (mstEmergencyPatient != null) {
//            mstEmergencyPatient.setIsDeleted(true);
//            mstEmergencyPatientRepository.save(mstEmergencyPatient);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        } else {
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }

    @GetMapping
    @RequestMapping("getbasepath")
    public String getPatientImagePath(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("BasePath" + Propertyconfig.getPatientImagePath());
        return Propertyconfig.getPatientImagePath();
    }

}
            
