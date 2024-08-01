//package com.cellbeans.hspa.SchedulerUtility;
//
//import com.cellbeans.hspa.emailformat.EmailFormatCreateController;
//import com.cellbeans.hspa.emailutility.Emailsend;
//import com.cellbeans.hspa.mis.misbillingreport.OpdIpdCollectionDetailController;
//import com.cellbeans.hspa.mis.misipdreports.MisAdmissionController;
//import com.cellbeans.hspa.mststaff.MstStaff;
//import com.cellbeans.hspa.mststaff.MstStaffRepository;
//import com.cellbeans.hspa.mstuser.MstUserRepository;
//import com.cellbeans.hspa.passwordsecurity.PasswordSecurity;
//import com.cellbeans.hspa.passwordsecurity.PasswordSecurityRepository;
//import com.cellbeans.hspa.smsutility.Sendsms;
//import com.cellbeans.hspa.tbillbill.TbillBillRepository;
//import com.cellbeans.hspa.tbillbillSponsor.TrnBillBillSponsorRepository;
//import com.cellbeans.hspa.tbillbillservice.TbillBillServiceRepository;
//import com.cellbeans.hspa.temrtimeline.TemrTimelineRepository;
//import com.sun.org.apache.xml.internal.utils.URI;
//import org.apache.commons.lang.RandomStringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.*;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
//import javax.mail.internet.MimeBodyPart;
//import javax.persistence.EntityManager;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static com.cellbeans.hspa.applicationproperty.Propertyconfig.pdfStoreUrl;
//
//@Component
//public class ScheduledTasks {
//    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//
//    @Autowired
//    EntityManager entityManager;
//
//    @Autowired
//    OpdIpdCollectionDetailController opdIpdCollectionDetailController;
//    @Autowired
//    EmailFormatCreateController emailFormatCreateController;
//    @Autowired
//    Sendsms sendsms;
//    @Autowired
//    private com.cellbeans.hspa.applicationproperty.Propertyconfig Propertyconfig;
//
//    @Autowired
//    TbillBillServiceRepository tbillBillServiceRepository;
//
//    @Autowired
//    TbillBillRepository tbillBillRepository;
//
//    @Autowired
//    TemrTimelineRepository temrTimelineRepository;
//
//    @Autowired
//    TrnBillBillSponsorRepository trnBillBillSponsorRepository;
//
//    @Autowired
//    MisAdmissionController misAdmissionController;
//
//    @Autowired
//    RestTemplate restTemplate;
//
//    @Autowired
//    MstUserRepository mstUserRepository;
//
//    @Autowired
//    MstStaffRepository mstStaffRepository;
//
//    @Autowired
//    PasswordSecurityRepository passwordSecurityRepository;
//
///*
//  @Scheduled(fixedRate = 20000)
//    @Scheduled(cron = "0 01 20 * * ?")
//    public void nmcSMS() {
//        if (Propertyconfig.getSmsApiNMMC().equals("true")) {
//            String sms_text = opdIpdCollectionDetailController.sendSmsToNMC();
////            sendsms.sendMessage("9420226264", sms_text.replace(" ", "%20"));
//
//            sendsms.sendMessage("9822502240", sms_text.replace(" ", "%20"));
//            sendsms.sendMessage("9879966255", sms_text.replace(" ", "%20"));
//            sendsms.sendMessage("9766672981", sms_text.replace(" ", "%20"));
//        }
//    }
//
//
//    // Enable scheduled in SpringRestApplication by adding - > @EnableScheduling
//    //@Scheduled(fixedRate = 5000)
//    @Scheduled(cron = "0 26 17 * * *")
//    public void reportCurrentTime() {
//
//        EmailsendUPHC ob = new EmailsendUPHC();
//
//        try {
//
//            System.out.println("Mail Activity Started...!");
//            String currDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
//
//            String smsStr = emailFormatCreateController.patientCountDetails() + " \n\n" + emailFormatCreateController.patientAgeWiseCountDetails();
//
//            smsStr = smsStr.replaceAll("\t", " ");
//            smsStr = smsStr.replaceAll(":", "- ");
//
//            System.out.println("smsStr " + smsStr);
//            if (Propertyconfig.getSmsApi()) {
//                if (smsStr != null) {
//                    System.out.println("SMS service started " + smsStr);
////              sendsms.sendmessage("8793111991", smsStr);   //vijay
////                    sendsms.sendmessage("9886544225", smsStr);  //nitin
////                    sendsms.sendmessage("8983491983", smsStr);  //amar
////                sendsms.sendmessage("9405514335", smsStr);  //tikesh
//                    System.out.println("SMS send successfully ....................!!! " + smsStr);
//                }
//            }
//
//
//            String mailToBcc[] = {"apurvap@cellbeanshealthcare.com", "nkanade@tatatrusts.org", "anawkar@tatatrusts.org", "vijayp@cellbeanshealthcare.com", "vijaypatil2212@gmail.com", "pragatichawardol@gmail.com", "pragatic@cellbeanshealthcare.com", "sagarp@cellbeanshealthcare.com", "shubhangir@cellbeanshealthcare.com", "the.saagar@exitosys.com", "rajeshd@cellbeanshealthcare.com", "pachporapurva@gmail.com"};
//            //   String mailToBcc[] = {"vijayp@cellbeanshealthcare.com"};
//            //String mailToBcc[] = {"apurvap@cellbeanshealthcare.com", "vijayp@cellbeanshealthcare.com", "vijaypatil2212@gmail.com", "pragatichawardol@gmail.com", "pragatic@cellbeanshealthcare.com", "sagarp@cellbeanshealthcare.com", "shubhangir@cellbeanshealthcare.com", "the.saagar@exitosys.com", "pachporapurva@gmail.com"};
//
//            String mailStr = emailFormatCreateController.getEmailData();
//            if (Propertyconfig.getEmailApi()) {
//                if (mailStr != null) {
//                    //    ob.sendMail("Model.uphc.project@gmail.com", "tbisen@tatatrusts.org", mailToBcc, "Test Mail from Medseva UPHC ", mailStr);
//
//                }
//
//                System.out.println("Mail Send Successfully...!");
//                log.info("The time is now {}", dateFormat.format(new Date()));
//
//            }
//            String mailStrOther = emailFormatCreateController.getEmailDataForOthers();
//            if (Propertyconfig.getEmailApi()) {
//                if (mailStrOther != null) {
//                    //    ob.sendMail("Model.uphc.project@gmail.com", "tbisen@tatatrusts.org", mailToBcc, "Test Mail from Medseva UPHC ", mailStrOther);
//
//                }
//
//                System.out.println("Mail Send Successfully...!");
//                log.info("The time is now {}", dateFormat.format(new Date()));
//
//            }
//
//        } catch (Exception e) {
//            System.err.println("error in sms and email :" + e);
//        }
//    }
//
//
//    //    @Scheduled(fixedRate = 2000)
//    @Scheduled(cron = "0 59 23 * * *")
//    public void autoCloseVisit() {
//        System.out.println("Auto close Visit Working!!!!!");
//        java.sql.Date todaysdate;
//        LocalDate today = LocalDate.now();
//        todaysdate = java.sql.Date.valueOf(today);
////        System.out.println("TODAY DATE: " + todaysdate);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
//        String currenttime = dateFormat.format(new Date()).toString();
//        Iterable<TbillBillService> dailyVisitList = tbillBillServiceRepository.findAlldailyvisit(todaysdate.toString());
//        for (TbillBillService tbillBillService : dailyVisitList) {
////            System.out.println( "Todays Visit:  " + tbillBillService);
//            tbillBillService.setBsStatus(4);
//            tbillBillService.setVisitEndTime(currenttime);
//            TbillBillService tbillBillServiceNew = tbillBillServiceRepository.save(tbillBillService);
//        }
//
//    }
//
//    @Scheduled(cron = "0 0 08 * * *")
//    public void sendFollowupSms() {
//        List<TemrTimeline> temrTimelineList = temrTimelineRepository.findAllByTimelineFollowDate();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String currenttime = dateFormat.format(new Date()).toString();
//        for (TemrTimeline temrTimelineObj : temrTimelineList) {
//            if (temrTimelineObj.getTimelinePatientId() != null) {
//                String smsStr = "";
//                String mobileNumber = temrTimelineObj.getTimelinePatientId().getPatientUserId().getUserMobile();
//                smsStr += emailFormatCreateController.getFollowUpSmsByPatientId(temrTimelineObj.getTimelinePatientId().getPatientId());
//                smsStr += " FollowUp Date: " + temrTimelineObj.getTimelineFollowDate();
////                    System.out.println("SMS : " + smsStr);
//                sendsms.sendMessage(mobileNumber, smsStr);   //Neha
//
//            } else if (temrTimelineObj.getTimelineAdmissionId() != null) {
//                String smsStr = "";
//                smsStr += emailFormatCreateController.getFollowUpSmsByAdmissionId(temrTimelineObj.getTimelineAdmissionId().getAdmissionPatientId().getPatientId());
//                smsStr += " FollowUp Date: " + temrTimelineObj.getTimelineFollowDate();
//                String mobileNumber = temrTimelineObj.getTimelineAdmissionId().getAdmissionPatientId().getPatientUserId().getUserMobile();
//                sendsms.sendMessage(mobileNumber, smsStr);   //Neha
////                    System.out.println("SMS : " + smsStr);
//            }
//
//        }
//    }
//    //    @Scheduled(fixedRate = 2000)
//    @Scheduled(cron = "0 0/10 * * * *")
//    public void autoBillApproved() {
//        System.out.println("Auto Bill Approval Working!!!!!");
//        java.sql.Date todaysdate;
//        LocalDate today = LocalDate.now();
//        todaysdate = java.sql.Date.valueOf(today);
////        System.out.println("TODAY DATE: " + todaysdate);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String currenttime = dateFormat.format(new Date()).toString();
//        List<TBillBill> billLiast = tbillBillRepository.findByIsActiveTrueAndIsDeletedFalseAndIsApproveFalseAndIsRejectedFalse();
//        for (TBillBill tbillBill : billLiast) {
//            List<TrnBillBillSponsor> trnBillBillSponsor = trnBillBillSponsorRepository.findByBbsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(tbillBill.getBillId());
//            if (trnBillBillSponsor.size() > 0 && trnBillBillSponsor.get(0).getBbsSCId().getScCompanyId().getCompanyBillApproval()) {
//                tbillBill.setIsApprove(true);
//                tbillBill.setBillAutoApproved(true);
//                tbillBill.setIsApprovedDate(currenttime);
//                tbillBillRepository.save(tbillBill);
//            }
//        }
//    }
//*/
//
//    @Configuration
//    public class MyConfig {
//        @Bean
//        public RestTemplate restTemplate(RestTemplateBuilder builder) {
//            // Do any additional configuration here
//            return builder.build();
//        }
//    }
//
////    @Scheduled(cron = "0 55 23 * * *")
////    //@Scheduled(cron = "0 46 11 * * *")
////    public void phamacyDetailsSaleReportAuto() throws Exception {
////        System.out.println("Auto Mail Schedule Activity Started...!");
////
////        if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
////      /*LocalDate todate = LocalDate.now();
////      LocalDate fromdate = todate.minusDays( 30 );*/
////            LocalDate todate = LocalDate.now();
////            LocalDate fromdate = todate.withDayOfMonth(1);
////            System.out.println("minus date : " + fromdate);
////            System.out.println("current date : " + todate);
////
////            RestTemplate restTemplate = new RestTemplate();
////            final String baseUrl = Propertyconfig.getServiceUrl() + "mis_ipd_reports/getipdopdpharmdetailReportPrintAll/" + fromdate + "/" + todate;
////            URI uri = new URI(baseUrl);
////            String uri_to_string = uri.toString();
////
////            HttpHeaders headers = new HttpHeaders();
////            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
////            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
////            ResponseEntity<String> result = restTemplate.exchange(uri_to_string, HttpMethod.GET, requestEntity, String.class);
////            //System.out.println("final result : "+result);
////            System.out.println("final result : " + result.getBody());
////
////            String msgContent = "";
////            String subject = "";
////            try {
////                if (Propertyconfig.getEmailApiForMgmt()) {
////                    subject = "EHMS - Auto MIS Pharmacy Daily Sales Report From " + fromdate + " To " + todate;
////                    msgContent = "Dear User \" <br><br>" +
////                            "Please find attached Pharmacy Daily Sales Report From " + fromdate + " To " + todate + "<br><br>" +
////                            " <br><br> Thanks And Regards, " +
////                            " <br> eHMS Admin";
////
////                    MimeBodyPart attachment = new MimeBodyPart();
////                    String filename = pdfStoreUrl + "IpdOpdPharmacyDetailReport.xls";//change accordingly
////                    //System.out.println("filename = "+filename);
////                    DataSource source = new FileDataSource(filename);
////                    attachment.setDataHandler(new DataHandler(source));
////                    attachment.setFileName("IpdOpdPharmacyDetailReport.xls");
////
////                    Emailsend emailsend1 = new Emailsend();
////                    emailsend1.sendMailWithAttachment(Propertyconfig.getDirectorEmail1(), msgContent, subject, attachment);
////                }
////            } catch (Exception e) {
////                System.out.println(e);
////            }
////        } else {
////            System.out.println("No schedule activity for this client");
////        }
////    }
////
////    @Scheduled(cron = "0 10 23 * * *")
////    public void getOpdServiceBillListExcelReportAuto() throws Exception {
////        System.out.println("Auto Mail Schedule Activity Started...!");
////
////        if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
////            LocalDate todate = LocalDate.now();
////            LocalDate fromdate = todate.withDayOfMonth(1);
////            System.out.println("minus date : " + fromdate);
////            System.out.println("current date : " + todate);
////
////            RestTemplate restTemplate = new RestTemplate();
////            final String baseUrl = Propertyconfig.getServiceUrl() + "mis_opd_ipd_userwise_billlist/getOpdServiceBillListExcel/" + fromdate + "/" + todate;
////            URI uri = new URI(baseUrl);
////            String uri_to_string = uri.toString();
////
////            HttpHeaders headers = new HttpHeaders();
////            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
////            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
////            ResponseEntity<String> result = restTemplate.exchange(uri_to_string, HttpMethod.GET, requestEntity, String.class);
////            //System.out.println("final result : "+result);
////            System.out.println("final result : " + result.getBody());
////
////            String msgContent = "";
////            String subject = "";
////            try {
////                if (Propertyconfig.getEmailApiForMgmt()) {
////                    subject = "EHMS - Auto OPD Service BillList Report From " + fromdate + " To " + todate;
////                    msgContent = "Dear User \" <br><br>" +
////                            "Please find attached BillList Daily Report From " + fromdate + " To " + todate + "<br><br>" +
////                            " <br><br> Thanks And Regards, " +
////                            " <br> eHMS Admin";
////
////                    MimeBodyPart attachment = new MimeBodyPart();
////                    String filename = pdfStoreUrl + "OpdServiceBillListDailyReport.xls";//change accordingly
////                    System.out.println("filename = " + filename);
////                    DataSource source = new FileDataSource(filename);
////                    attachment.setDataHandler(new DataHandler(source));
////                    attachment.setFileName("OpdServiceBillListDailyReport.xls");
////
////                    System.out.println("mail id : " + Propertyconfig.getDirectorEmail1());
////                    Emailsend emailsend1 = new Emailsend();
////                    emailsend1.sendMailWithAttachment(Propertyconfig.getDirectorEmail1(), msgContent, subject, attachment);
////                }
////            } catch (Exception e) {
////                System.out.println(e);
////            }
////        } else {
////            System.out.println("No schedule activity for this client");
////        }
////    }
////
////    @Scheduled(cron = "0 55 23 * * *")
////    //@Scheduled(cron = "0 46 11 * * *")
////    public void inventoryExpiryReportAuto() throws Exception {
////        System.out.println("Auto Mail NearBy Expiry Schedule Activity Started...!");
////        if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
////            LocalDate todayDate = LocalDate.now();
////
////            RestTemplate restTemplate = new RestTemplate();
////            final String baseUrl = Propertyconfig.getServiceUrl() + "mis_inventory_expiry_report/searchAutoMailNearbyExpiryItemsPrint";
////            URI uri = new URI(baseUrl);
////            String uri_to_string = uri.toString();
////
////            HttpHeaders headers = new HttpHeaders();
////            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
////            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
////            ResponseEntity<String> result = restTemplate.exchange(uri_to_string, HttpMethod.GET, requestEntity, String.class);
////            //System.out.println("final result : "+result);
////            System.out.println("final result : " + result.getBody());
////
////            String msgContent = "";
////            String subject = "";
////            try {
////                if (Propertyconfig.getEmailApiForMgmt()) {
////                    subject = "EHMS - Auto Inventory MIS - Daily Nearby Expiry Report Dated on " + todayDate;
////                    msgContent = "Dear User \" <br><br>" +
////                            "Please find attached Pharmacy Daily Nearby Expiry Report  on " + todayDate + "<br><br>" +
////                            " <br><br> Thanks And Regards, " +
////                            " <br> eHMS Admin";
////
////                    MimeBodyPart attachment = new MimeBodyPart();
////                    String filename = pdfStoreUrl + "NearByExpiryReport.xls";//change accordingly
////                    //System.out.println("filename = "+filename);
////                    DataSource source = new FileDataSource(filename);
////                    attachment.setDataHandler(new DataHandler(source));
////                    attachment.setFileName("NearByExpiryReport.xls");
////
////                    Emailsend emailsend1 = new Emailsend();
////                    emailsend1.sendMailWithAttachment(Propertyconfig.getDirectorEmail1(), msgContent, subject, attachment);
////                }
////            } catch (Exception e) {
////                System.out.println(e);
////            }
////        } else {
////            System.out.println("No schedule activity for this client");
////        }
////    }
////
////    //  @Scheduled(cron = "0 45 15 * * *")
////    @Scheduled(cron = "0 00 23 * * *")
////    public void clinicalHandOverAutoGenerateMail() throws Exception {
////        System.out.println("Auto Mail Schedule Activity Started...!");
////
////        if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
////
////            Date date = new Date();
////            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
////            String strDate = formatter.format(date);
////
////            CasePaperSchedular casePaperSchedular = new CasePaperSchedular();
////            Object emailContent = casePaperSchedular.createCasePaper(entityManager, strDate);
////            System.out.println("emailContent : " + emailContent);
////
////            LocalDate todayDate = LocalDate.now();
////            String subject = "";
////            String msgContent = "";
////            try {
////                if (Propertyconfig.getEmailApiForMgmt()) {
////                    subject = "Clinical Handover for " + strDate + " (Full Day)";
////                    msgContent = "Dear User <br><br>" +
////                            emailContent + "<br><br>" +
////                            " <br><br> Thanks And Regards, " +
////                            " <br> eHMS Admin";
////
////                    Emailsend emailsend1 = new Emailsend();
////                    emailsend1.sendMAil1(Propertyconfig.getDirectorEmail2(), msgContent, subject);
////                }
////            } catch (Exception e) {
////                System.out.println(e);
////            }
////        } else {
////            System.out.println("No schedule activity for this client");
////        }
////    }
////
////    @Scheduled(cron = "0 00 22 ? * SAT")
////    public void updateAllUserAge() throws Exception {
////        System.out.println("Auto update All User Started...!");
////
////        if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
////
////            CasePaperSchedular casePaperSchedular = new CasePaperSchedular();
////            casePaperSchedular.updateAllUserAge(entityManager, mstUserRepository);
////            System.out.println("In Update All User Age");
////
////        } else {
////            System.out.println("No schedule activity for this client");
////        }
////    }
////
////    @Scheduled(cron = "0 0 01 * * *")
////    public void forcePasswordReset() throws Exception {
////        System.out.println("force Password Reset Started...!");
////        PasswordSecurity passwordSecurity = passwordSecurityRepository.findAllByActiveTrueAndIdEquals(1L);
////        Date curDate = new Date();
////        if (passwordSecurity.getForceReset() && (curDate.after(passwordSecurity.getPwdEndDate()) && !passwordSecurity.getResetDone())) {
////            List<MstStaff> mstStaffList = mstStaffRepository.findByIsActiveTrueAndIsDeletedFalse();
////            for (MstStaff mstStaff : mstStaffList) {
//////                System.out.println(RandomStringUtils.randomAlphanumeric(6));
////                if (!mstStaff.getStaffRole().getRoleName().equals("Super Administrator")) {
////                    mstStaff.getStaffUserId().setPassword(RandomStringUtils.randomAlphanumeric(6));
////                }
////            }
////            mstStaffRepository.saveAll(mstStaffList);
////            passwordSecurity.setResetDone(true);
////            passwordSecurityRepository.save(passwordSecurity);
////        }
////    }
//}
