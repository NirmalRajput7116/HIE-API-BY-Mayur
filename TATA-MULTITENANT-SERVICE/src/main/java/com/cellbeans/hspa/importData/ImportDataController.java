/*
package com.cellbeans.hspa.importData;

import com.cellbeans.hspa.applicationproperty.MrNumberPrefix;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mbillservice.MbillServiceRepository;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mbilltariff.MbillTariffRepository;
import com.cellbeans.hspa.mipdadmissionpurpose.MipdAdmissionPurpose;
import com.cellbeans.hspa.mipdadmissionpurpose.MipdAdmissionPurposeRepository;
import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mpathtest.MpathTest;
import com.cellbeans.hspa.mpathtest.MpathTestRepository;
import com.cellbeans.hspa.mstcashcounter.MstCashCounter;
import com.cellbeans.hspa.mstcashcounter.MstCashCounterRepository;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstcity.MstCityRepository;
import com.cellbeans.hspa.mstclass.MstClass;
import com.cellbeans.hspa.mstclass.MstClassRepository;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstdepartment.MstDepartmentRepository;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstgender.MstGenderRepository;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartmentRepository;
import com.cellbeans.hspa.msttitle.MstTitle;
import com.cellbeans.hspa.msttitle.MstTitleRepository;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.rpathtestparameter.RpathTestParameter;
import com.cellbeans.hspa.rpathtestparameter.RpathTestParameterRepository;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tbillbill.TbillBillController;
import com.cellbeans.hspa.tbillbill.TbillBillRepository;
import com.cellbeans.hspa.tbillbillservice.TbillBillService;
import com.cellbeans.hspa.tbillbillservice.TbillBillServiceController;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.cellbeans.hspa.trnadmission.TrnAdmissionRepository;
import com.cellbeans.hspa.trnbedstatus.TrnBedStatus;
import com.cellbeans.hspa.trnbedstatus.TrnBedStatusRepository;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@RestController
public class ImportDataController {
    @Autowired
    MstGenderRepository mstGenderRepository;

    @Autowired
    MstTitleRepository mstTitleRepository;

    @Autowired
    MstCityRepository mstCityRepository;

    @Autowired
    MstPatientRepository mstPatientRepository;

    @Autowired
    MstVisitRepository mstVisitRepository;

    @Autowired
    MstUserRepository mstUserRepository;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    MbillServiceRepository mbillServiceRepository;

    @Autowired
    TbillBillController tbillBillController;

    @Autowired
    MbillTariffRepository mbillTariffRepository;

    @Autowired
    MstClassRepository mstClassRepository;

    @Autowired
    MstStaffRepository mstStaffRepository;

    @Autowired
    TbillBillServiceController tbillBillServiceController;

    @Autowired
    TbillBillRepository tbillBillRepository;

    @Autowired
    MstCashCounterRepository mstCashCounterRepository;

    @Autowired
    MpathTestRepository mpathTestRepository;

    @Autowired
    RpathTestParameterRepository rpathTestParameterRepository;

    @Autowired
    TrnAdmissionRepository trnAdmissionRepository;

    @Autowired
    TrnBedStatusRepository trnBedStatusRepository;

    @Autowired
    MstDepartmentRepository mstDepartmentRepository;

    @Autowired
    MipdAdmissionPurposeRepository mipdAdmissionPurposeRepository;
    @Autowired
    private com.cellbeans.hspa.applicationproperty.Propertyconfig Propertyconfig;
    private String UPLOADED_FOLDER = Propertyconfig.getprofileimagepath();


    @PostMapping(value = "/api/impotExcel/{unitId}/{count}/{isRadiology}/{isIPD}")
    public ResponseEntity<?> impotExcel(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "file",required = false) MultipartFile uploadfile, @PathVariable("unitId") Long unitId, @PathVariable("count") int count, @PathVariable("isRadiology") String isRadiology, @PathVariable("isIPD") Boolean isIPD) {
        System.out.println("i am in ");
        try {

        String extension = "";
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        } else {
            byte[] bytes = uploadfile.getBytes();
            File destFile = new File(UPLOADED_FOLDER);
            destFile.mkdir();
            Path path = Paths.get(destFile.getAbsolutePath() + "\\" + uploadfile.getOriginalFilename());
            // Path path = Paths.get(UPLOADED_PATIENT_FILES + file.getOriginalFilename());
            Files.write(path, bytes);
//                File file = new File(uploadfile.getOriginalFilename());
            System.out.println("i am in " + uploadfile.getOriginalFilename().split("."));
            String fileName = uploadfile.getOriginalFilename();

            int index = fileName.lastIndexOf('.');
            if (index > 0) {
                extension = fileName.substring(index + 1);
                System.out.println("File extension is " + extension);
            }

            if (extension.equals("xlsx")) {
                readXlSX(destFile.getAbsolutePath() + "\\" + uploadfile.getOriginalFilename(), unitId, count, isRadiology, isIPD);
            } else if (extension.equals("xls")) {
                readXLS(destFile.getAbsolutePath() + "\\" + uploadfile.getOriginalFilename(), unitId, count, isRadiology, isIPD);
            }
        }

            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


            return new ResponseEntity(uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
        }


    public void readXlSX(String path, Long unitId, int count, String isRadiology, Boolean isIPD) {
        try {
            MstUnit mstUnit = mstUnitRepository.getById(unitId);

            FileInputStream fis = new FileInputStream(new File(path));

            XSSFWorkbook wb = new XSSFWorkbook(fis);
            //creating a Sheet object to retrieve the object
            XSSFSheet sheet = wb.getSheetAt(0);
            //evaluating cell type
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file
            int starRow = sheet.getFirstRowNum();
            int endRow = count;

            for (int i = starRow + 1; i < endRow + 1; i++) {
                if(sheet.getRow(i) != null){
                    MstUser mstUser = new MstUser();
                    Cell title = sheet.getRow(i).getCell(0);
                    if (title != null) {
                        System.out.print(title.getStringCellValue() + "\t\t\t");
                        MstTitle mstTitle = mstTitleRepository.findByTitleNameEqualsAndIsActiveTrueAndIsDeletedFalse(title.getStringCellValue());
                        if(mstTitle != null){
                            mstUser.setUserTitleId(mstTitle);
                        }
                    }

                    Cell patientName = sheet.getRow(i).getCell(1);
                    if (patientName != null) {
                        System.out.print(patientName.getStringCellValue() + "\t\t\t");
                        String[] str = patientName.getStringCellValue().trim().split("\\s+");
                        mstUser.setUserFirstname(str[0]);
                        mstUser.setUserLastname(str[1]);
                    }
                    Cell DOB = sheet.getRow(i).getCell(2);
                    if (DOB != null) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dob = formatter.format(DOB.getDateCellValue());
                        System.out.print(dob + "\t\t\t");
                        mstUser.setUserDob(dob);
                        Date d = formatter.parse(dob);
                        Calendar c = Calendar.getInstance();
                        c.setTime(d);
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH) + 1;
                        int date = c.get(Calendar.DATE);
                        LocalDate l1 = LocalDate.of(year, month, date);
                        LocalDate now1 = LocalDate.now();
                        Period diff1 = Period.between(l1, now1);
                        mstUser.setUserAge(Integer.toString(diff1.getYears()));
                        mstUser.setuserMonth(Integer.toString(diff1.getMonths()));
                        mstUser.setuserDay(Integer.toString(diff1.getDays()));
                    }
                    Cell gender = sheet.getRow(i).getCell(3);
                    if (gender != null) {
                        System.out.print(gender.getStringCellValue() + "\t\t\t");
                        MstGender mstGender = mstGenderRepository.findByGenderNameEqualsAndIsActiveTrueAndIsDeletedFalse(gender.getStringCellValue());
                        if(mstGender != null){
                            mstUser.setUserGenderId(mstGender);
                        }
                    }
                    Cell address = sheet.getRow(i).getCell(4);
                    if (address != null) {
                        System.out.print(address.getStringCellValue() + "\t\t\t");
                        mstUser.setUserAddress(address.getStringCellValue());
                    }
                    Cell city = sheet.getRow(i).getCell(5);
                    if (city != null) {
                        System.out.print(city.getStringCellValue() + "\t\t\t");
                        MstCity mstCity = mstCityRepository.findByCityNameEqualsAndIsActiveTrueAndIsDeletedFalse(city.getStringCellValue());
                        if(mstCity != null){
                            mstUser.setUserCityId(mstCity);
                        }
                    }
                    Cell mobileNo = sheet.getRow(i).getCell(6);
                    if (mobileNo != null) {
                        System.out.print(mobileNo.getStringCellValue() + "\t\t\t");
                        mstUser.setUserMobile(mobileNo.getStringCellValue());
                    }
                    MstVisit mstVisit = new MstVisit();
                    String Prefix = Propertyconfig.getmrnopre();
                    MrNumberPrefix mrNumberPrefix = new MrNumberPrefix();
                    boolean isDate = Propertyconfig.getMrnoIsDate();
                    String generatedMrNoPrefix = mrNumberPrefix.GenerateMRNO(Prefix, isDate, mstUnit.getUnitCode(),Propertyconfig.getUnitcode());
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
                    }
                    else {
                        String number = lastMrNo.substring(Prefixlength);
                        int newNumber = Integer.parseInt(number);
                        String incNumber = String.format("%08d", newNumber + 1);
                        newMrNo = generatedMrNoPrefix + incNumber;
                    }
                    mstUser = mstUserRepository.save(mstUser);
                    MstPatient mstPatient = new MstPatient();
                    mstPatient.setPatientMrNo(newMrNo);
                    mstPatient.setPatientUserId(mstUser);
                    mstPatient = mstPatientRepository.save(mstPatient);
                    mstVisit.setVisitPatientId(mstPatient);
                    mstVisit.setVisitUnitId(mstUnit);
                    List<MstStaff> mstStaffList = mstStaffRepository.findAllByStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(mstVisit.getVisitUnitId().getUnitId());
                    int randomStaffId =  new Random().nextInt(mstStaffList.size());
                    MstStaff mstStaff = mstStaffList.get(randomStaffId);
                    mstVisit.setVisitStaffId(mstStaff);
                    mstVisit.setVisitTariffId(mbillTariffRepository.getById(1L));
                    mstVisit = mstVisitRepository.save(mstVisit);
                    System.out.println("");
                    TrnAdmission trnAdmission = new TrnAdmission();
                    if(isIPD){
                        trnAdmission = makeAdmission(mstVisit);
                    }
                    if(isRadiology.equals("true")){
                        makeServiceBill(mstVisit, trnAdmission, true, isIPD);
                    }else if(isRadiology.equals("false")){
                        makeServiceBill(mstVisit, trnAdmission, false, isIPD);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    public void readXLS(String path, Long unitId, int count, String isRadiology, Boolean isIPD) {
        try {
            MstUnit mstUnit = mstUnitRepository.getById(unitId);

            FileInputStream fis = new FileInputStream(new File(path));

            HSSFWorkbook wb = new HSSFWorkbook(fis);
            //creating a Sheet object to retrieve the object
            HSSFSheet sheet = wb.getSheetAt(0);
            //evaluating cell type
            FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
            int starRow = sheet.getFirstRowNum();
            int endRow = count;

            for (int i = starRow + 1; i < endRow + 1; i++) {
                if(sheet.getRow(i) != null){
                    MstUser mstUser = new MstUser();
                    Cell title = sheet.getRow(i).getCell(0);
                    if (title != null) {
                        System.out.print(title.getStringCellValue() + "\t\t\t");
                        MstTitle mstTitle = mstTitleRepository.findByTitleNameEqualsAndIsActiveTrueAndIsDeletedFalse(title.getStringCellValue());
                        if(mstTitle != null){
                            mstUser.setUserTitleId(mstTitle);
                        }
                    }

                    Cell patientName = sheet.getRow(i).getCell(1);
                    if (patientName != null) {
                        System.out.print(patientName.getStringCellValue() + "\t\t\t");
                        String[] str = patientName.getStringCellValue().trim().split("\\s+");
                        mstUser.setUserFirstname(str[0]);
                        mstUser.setUserLastname(str[1]);
                    }
                    Cell DOB = sheet.getRow(i).getCell(2);
                    if (DOB != null) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dob = formatter.format(DOB.getDateCellValue());
                        System.out.print(dob + "\t\t\t");
                        mstUser.setUserDob(dob);
                        Date d = formatter.parse(dob);
                        Calendar c = Calendar.getInstance();
                        c.setTime(d);
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH) + 1;
                        int date = c.get(Calendar.DATE);
                        LocalDate l1 = LocalDate.of(year, month, date);
                        LocalDate now1 = LocalDate.now();
                        Period diff1 = Period.between(l1, now1);
                        mstUser.setUserAge(Integer.toString(diff1.getYears()));
                        mstUser.setuserMonth(Integer.toString(diff1.getMonths()));
                        mstUser.setuserDay(Integer.toString(diff1.getDays()));
                    }
                    Cell gender = sheet.getRow(i).getCell(3);
                    if (gender != null) {
                        System.out.print(gender.getStringCellValue() + "\t\t\t");
                        MstGender mstGender = mstGenderRepository.findByGenderNameEqualsAndIsActiveTrueAndIsDeletedFalse(gender.getStringCellValue());
                        if(mstGender != null){
                            mstUser.setUserGenderId(mstGender);
                        }
                    }
                    Cell address = sheet.getRow(i).getCell(4);
                    if (address != null) {
                        System.out.print(address.getStringCellValue() + "\t\t\t");
                        mstUser.setUserAddress(address.getStringCellValue());
                    }
                    Cell city = sheet.getRow(i).getCell(5);
                    if (city != null) {
                        System.out.print(city.getStringCellValue() + "\t\t\t");
                        MstCity mstCity = mstCityRepository.findByCityNameEqualsAndIsActiveTrueAndIsDeletedFalse(city.getStringCellValue());
                        if(mstCity != null){
                            mstUser.setUserCityId(mstCity);
                        }
                    }
                    Cell mobileNo = sheet.getRow(i).getCell(6);
                    if (mobileNo != null) {
                        System.out.print(mobileNo.getStringCellValue() + "\t\t\t");
                        mstUser.setUserMobile(mobileNo.getStringCellValue());
                    }
                    MstVisit mstVisit = new MstVisit();
                    String Prefix = Propertyconfig.getmrnopre();
                    MrNumberPrefix mrNumberPrefix = new MrNumberPrefix();
                    boolean isDate = Propertyconfig.getMrnoIsDate();
                    String generatedMrNoPrefix = mrNumberPrefix.GenerateMRNO(Prefix, isDate, mstUnit.getUnitCode(),Propertyconfig.getUnitcode());
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
                    }
                    else {
                        String number = lastMrNo.substring(Prefixlength);
                        int newNumber = Integer.parseInt(number);
                        String incNumber = String.format("%08d", newNumber + 1);
                        newMrNo = generatedMrNoPrefix + incNumber;
                    }
                    mstUser = mstUserRepository.save(mstUser);
                    MstPatient mstPatient = new MstPatient();
                    mstPatient.setPatientMrNo(newMrNo);
                    mstPatient.setPatientUserId(mstUser);
                    mstPatient = mstPatientRepository.save(mstPatient);
                    mstVisit.setVisitPatientId(mstPatient);
                    mstVisit.setVisitUnitId(mstUnit);
                    List<MstStaff> mstStaffList = mstStaffRepository.findAllByStaffUnitUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByStaffUserIdUserFirstnameAsc(mstVisit.getVisitUnitId().getUnitId());
                    int randomStaffId =  new Random().nextInt(mstStaffList.size());
                    MstStaff mstStaff = mstStaffList.get(randomStaffId);
                    mstVisit.setVisitStaffId(mstStaff);
                    mstVisit.setVisitTariffId(mbillTariffRepository.getById(1L));
                    mstVisit = mstVisitRepository.save(mstVisit);
                    TrnAdmission trnAdmission = new TrnAdmission();
                    if(isIPD){
                        trnAdmission = makeAdmission(mstVisit);
                    }
                    if(isRadiology.equals("true")){
                        makeServiceBill(mstVisit,trnAdmission, true, isIPD);
                    }else if(isRadiology.equals("false")){
                        makeServiceBill(mstVisit,trnAdmission, false, isIPD);
                    }
                    System.out.println("");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public void makeServiceBill(MstVisit mstVisit,TrnAdmission trnAdmission,  Boolean isRadiology, Boolean isIPD) {

        List<MbillService> mbillServices = new ArrayList<>();
        if(isRadiology){
            mbillServices = mbillServiceRepository.findByServiceIsRadiologyTrueAndIsActiveTrueAndIsDeletedFalse();
        }else {
            mbillServices = mbillServiceRepository.findByServiceIsLaboratoryTrueAndIsActiveTrueAndIsDeletedFalse();
        }
        int serviceIndex = new Random().nextInt(mbillServices.size());
        MbillService mbillService = mbillServices.get(serviceIndex);


        TBillBill tBillBill = new TBillBill();
        tBillBill.setTbillUnitId(mstVisit.getVisitUnitId());
        tBillBill.setBillTariffId(mbillTariffRepository.getById(1L));
        tBillBill.setBillClassId(mstClassRepository.getById(1L));
        tBillBill.setBillSubTotal(mbillService.getServiceBaseRate());
        tBillBill.setBillNetPayable(mbillService.getServiceBaseRate());
        tBillBill.setBillOutstanding(mbillService.getServiceBaseRate());
        tBillBill.setBillTotCoPay(mbillService.getServiceBaseRate());
        tBillBill.setBillTotCoPayOutstanding(mbillService.getServiceBaseRate());
        tBillBill.setBillUserId(mstVisit.getVisitStaffId());
        if(isIPD){
            tBillBill.setBillAdmissionId(trnAdmission);
        }else{
            tBillBill.setBillVisitId(mstVisit);
        }
        tBillBill.setIpdBill(isIPD);
        tBillBill.setBillNill(false);
        tBillBill.setFinalBill(false);
        tBillBill.setGrossAmount(mbillService.getServiceBaseRate());
        tBillBill.setBillCashCounterId(mstCashCounterRepository.getById(1L));
        Map<String, String> billMap = tbillBillController.create(tBillBill);

        String billId = billMap.get("bsBillId");
        TbillBillService tbillBillService  = new TbillBillService();
        tbillBillService.setBsBillId(tbillBillRepository.getById(Long.parseLong(billId)));
        tbillBillService.setBsDate(new Date());
        tbillBillService.setBsClassRate(mbillService.getServiceBaseRate());
        tbillBillService.setBsGrossRate(mbillService.getServiceBaseRate());
        tbillBillService.setBsQtyRate(mbillService.getServiceBaseRate());
        tbillBillService.setBsCoPayQtyRate(mbillService.getServiceBaseRate());
        tbillBillService.setBsTariffCoPay(mbillService.getServiceBaseRate());
        tbillBillService.setBsTariffCPRate(mbillService.getServiceBaseRate());
        tbillBillService.setBsQRRate(mbillService.getServiceBaseRate());
        tbillBillService.setGrossAmount(mbillService.getServiceBaseRate());
        tbillBillService.setBsTaxValue(0);
        tbillBillService.setBsConcessionPercentage(0);
        tbillBillService.setBsConTempPercentage(0);
        tbillBillService.setBsQuantity(1);
        tbillBillService.setBsPriority(false);
        tbillBillService.setBsCancel(false);
        tbillBillService.setBsDiscountAmount(0);
        tbillBillService.setBsPackageName("nop");
        tbillBillService.setBsServiceId(mbillService);
        tbillBillService.setBsStaffId(mstVisit.getVisitStaffId());
        Map<String, String> billServiceMap = tbillBillServiceController.createIndivisualForImport(tbillBillService);
        System.out.println(billServiceMap.get("msg"));
        System.out.println(billServiceMap.get("bsId"));
    }

    @PostMapping("/api/getRowCount")
    public Map<String, Object> getRowCount(@RequestHeader("X-tenantId") String tenantName, @RequestPart("file") MultipartFile uploadfile) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        System.out.println("i am in ");
        String extension = "";
        Long rowCount = 0L;
        if (uploadfile.isEmpty()) {
            respMap.put("msg", "please select a file!");
            return respMap;
        } else {
            try {
                byte[] bytes = uploadfile.getBytes();
                File destFile = new File(UPLOADED_FOLDER);
                destFile.mkdir();
                Path path = Paths.get(destFile.getAbsolutePath() + "\\" + uploadfile.getOriginalFilename());
                // Path path = Paths.get(UPLOADED_PATIENT_FILES + file.getOriginalFilename());
                Files.write(path, bytes);
//                File file = new File(uploadfile.getOriginalFilename());
                System.out.println("i am in "+uploadfile.getOriginalFilename().split("."));
                String fileName = uploadfile.getOriginalFilename();

                int index = fileName.lastIndexOf('.');
                if(index > 0) {
                    extension = fileName.substring(index + 1);
                    System.out.println("File extension is " + extension);
                }

                if(extension.equals("xlsx")){
                   rowCount = checkRowCountXlSX(destFile.getAbsolutePath() + "\\" + uploadfile.getOriginalFilename());
                }else if(extension.equals("xls")){
                   rowCount = checkRowCountXLS(destFile.getAbsolutePath() + "\\" + uploadfile.getOriginalFilename());
                }
                respMap.put("count", rowCount);
                return respMap;

            } catch (IOException e) {
                e.printStackTrace();
                respMap.put("msg", "Exception");
                return respMap;
            }

        }
    }
    public Long checkRowCountXlSX(String path) {
        Long rowcount = 0L;
        try {

            FileInputStream fis = new FileInputStream(new File(path));

            XSSFWorkbook wb = new XSSFWorkbook(fis);
            //creating a Sheet object to retrieve the object
            XSSFSheet sheet = wb.getSheetAt(0);
            //evaluating cell type
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file
            int starRow = sheet.getFirstRowNum();
            int endRow = sheet.getLastRowNum();

            for (int i = starRow + 1; i < endRow + 1; i++) {
                if(sheet.getRow(i) != null){
                    rowcount++;
                }
            }
            return rowcount;

        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }

    }
    public Long checkRowCountXLS(String path) {
        Long rowcount = 0L;

        try {
            FileInputStream fis = new FileInputStream(new File(path));

            HSSFWorkbook wb = new HSSFWorkbook(fis);
            //creating a Sheet object to retrieve the object
            HSSFSheet sheet = wb.getSheetAt(0);
            //evaluating cell type
//            FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
            for (Row row : sheet)     //iteration over row using for each loop
            {
                if(row != null){
                    rowcount++;
                }

            }
            return rowcount - 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 0L;

//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public TrnAdmission makeAdmission(MstVisit mstVisit){
        TrnAdmission trnAdmission = new TrnAdmission();
        String ipdNo = trnAdmissionRepository.makeIPDNumberForAddmission();
        trnAdmission.setAdmissionPatientId(mstVisit.getVisitPatientId());
        trnAdmission.setMbillTariff(mstVisit.getVisitTariffId());
        trnAdmission.setAdmissionStaffId(mstVisit.getVisitStaffId());
        trnAdmission.setAdmissionIpdNo(ipdNo);
        List<TrnBedStatus> trnBedStatusesList = trnBedStatusRepository.findAllByTbsStatusEqualsAndIsActiveTrueAndIsDeletedFalse(0);
        int bedIndex =  new Random().nextInt(trnBedStatusesList.size());
        TrnBedStatus trnBedStatus = trnBedStatusesList.get(bedIndex);
        trnAdmission.setAdmissionPatientBedId(trnBedStatus.getTbsBedId());
        trnBedStatusRepository.bedstatusupdate(1, trnAdmission.getAdmissionPatientBedId().getBedId());
        List<MstDepartment> mstDepartmentList = mstDepartmentRepository.findAllByDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsMedicaldepartmentTrueOrderByDepartmentNameAsc(mstVisit.getVisitUnitId().getUnitId());
        int departmentIndex =  new Random().nextInt(mstDepartmentList.size());
        MstDepartment mstDepartment = mstDepartmentList.get(departmentIndex);
        trnAdmission.setAdmissionDepartmentId(mstDepartment);
        trnAdmission.setAdmissionClassId(trnBedStatus.getTbsBedId().getBedBcId());
        trnAdmission.setAdmissionUnitId(mstVisit.getVisitUnitId());
        List<MipdAdmissionPurpose> mipdAdmissionPurposesList = mipdAdmissionPurposeRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        int apIndex =  new Random().nextInt(mipdAdmissionPurposesList.size());
        MipdAdmissionPurpose mipdAdmissionPurpose = mipdAdmissionPurposesList.get(apIndex);
        trnAdmission.setAdmissionPurposeId(mipdAdmissionPurpose);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        trnAdmission.setAdmissionDate(formatter.format(date));
        trnAdmission.setAutoCharge(false);
        trnAdmission.setAdmissionDischargeStatus("0");
        trnAdmission.setCreatedUser(String.valueOf(mstVisit.getVisitStaffId().getStaffUserId().getUserFullname()));
        List<MipdBed> currentBedList = new ArrayList<>();
        currentBedList.add(trnAdmission.getAdmissionPatientBedId());
        trnAdmission.setAdmissionCurrentBedId(currentBedList);
        trnAdmission = trnAdmissionRepository.save(trnAdmission);
        return trnAdmission;
    }

}*/
