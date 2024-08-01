package com.cellbeans.hspa.applicationproperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@ConfigurationProperties("app") // prefix app, find app.* values
public class Propertyconfig {
    private static String profileimage;
    private static String bsae_path;
    private static String patient_image;
    private static String mrnoprefix;
    private static int mrnoprefixlength;
    private static String smartcard_head_name;
    private static String footerheading;
    private static String providedby;
    private static boolean mrnoDate;
    private static String paitentFiles;
    private static String ernoprefix;
    private static int ernoprefixlength;
    private static boolean ernoDate;
    private static boolean SmsApi;
    private static boolean EmailApi;
    private static boolean EmailApiForMgmt;
    private static String paitentFilesBasePath;
    public static String pdfStoreUrl;
    private static String jrxUrl;
    private static String unitcode;
    private static String billservice;
    private static String SmsApiNMMC;
    private static String directorMobile1;
    private static String directorMobile2;
    private static String directorMobile3;
    private static String directorMobile4;
    private static String directorMobile5;
    private static String directorEmail1;
    private static String directorEmail2;
    private static String serviceUrl;
    private static String SmsURL;
    private static String clientName;
    private static String emailUsername;
    private static String emailPassword;
    private static String client;


    public static String getJrxUrl() {
        return jrxUrl;
    }

    public static void setJrxUrl(String jrxUrl) {
        Propertyconfig.jrxUrl = jrxUrl;
    }
//    private static String databaseUrl;
//    private static String dbUserName;
//    private static String dbPassword;
//    private static String databaseName;

    @Autowired
    public Propertyconfig(@Value("${paitentFilesBasePath}") String paitentFilesBasePath, @Value("${providedby}") String providedby,
                          @Value("${footerheading}") String footerheading, @Value("${smartcard_head_name}") String smartcard_head_name,
                          @Value("${mrnoprefixlength}") int mrnoprefixlength, @Value("${profile_image_path}") String profileimage,
                          @Value("${bsae_path}") String basepath, @Value("${mrnoprefix}") String mrnoprefix, @Value("${patient_image}") String patientimage,
                          @Value("${mrnoDate}") boolean mrnoDate, @Value("${paitentFiles}") String paitentFiles,
                          @Value("${ernoprefixlength}") int ernoprefixlength, @Value("${ernoprefix}") String ernoprefix,
                          @Value("${ernoDate}") boolean ernoDate, @Value("${SmsApi}") boolean SmsApi,
                          @Value("${EmailApi}") boolean EmailApi, @Value("${pdfStoreUrl}") String pdfStoreUrl,
                          @Value("${jrxUrl}") String jrxUrl, @Value("${unitcode}") String unitcode, @Value("${billservice}") String billservice,
                          @Value("${SmsApiNMMC}") String SmsApiNMMC, @Value("${SmsURL}") String SmsURL, @Value("${clientName}") String clientName,
                          @Value("${emailUsername}") String emailUsername, @Value("${emailPassword}") String emailPassword,
                          @Value("${directorEmail1}") String directorEmail1, @Value("${serviceUrl}") String serviceUrl,
                          @Value("${directorEmail2}") String directorEmail2, @Value("${EmailApiForMgmt}") boolean EmailApiForMgmt,
                          @Value("${client}") String client) {
//   @Value("${paitentFilesBasePath}") String paitentFilesBasePath,
        this.profileimage = profileimage;
        this.bsae_path = basepath;
        this.mrnoprefix = mrnoprefix;
        this.mrnoprefixlength = mrnoprefixlength;
        this.patient_image = patientimage;
        this.mrnoDate = mrnoDate;
        this.smartcard_head_name = smartcard_head_name;
        this.footerheading = footerheading;
        this.providedby = providedby;
        this.paitentFiles = paitentFiles;
        this.ernoprefix = ernoprefix;
        this.ernoprefixlength = ernoprefixlength;
        this.ernoDate = ernoDate;
        this.SmsApi = SmsApi;
        this.EmailApi = EmailApi;
        this.paitentFilesBasePath = paitentFilesBasePath;
        this.pdfStoreUrl = pdfStoreUrl;
        this.jrxUrl = jrxUrl;
        this.unitcode = unitcode;
        this.billservice = billservice;
        this.SmsApiNMMC = SmsApiNMMC;
        this.SmsURL = SmsURL;
        this.clientName = clientName;
        this.emailUsername = emailUsername;
        this.emailPassword = emailPassword;
        this.directorEmail1 = directorEmail1;
        this.serviceUrl = serviceUrl;
        this.directorEmail2 = directorEmail2;
        this.EmailApiForMgmt = EmailApiForMgmt;
        this.client = client;
        // System.out.println("================== " + patientimage + "================== ");
    }

    public static String getBillservice() {
        return billservice;
    }

    public static void setBillservice(String billservice) {
        Propertyconfig.billservice = billservice;
    }

    public static String getUnitcode() {
        return unitcode;
    }

    public static void setUnitcode(String unitcode) {
        Propertyconfig.unitcode = unitcode;
    }

    public static String getprofileimagepath() {
        return profileimage;
    }

    public static String getbasepath() {
        return bsae_path;
    }

    public static String getmrnopre() {
        return mrnoprefix;
    }

    public static int getmrnoprefixstringlength() {
        return mrnoprefixlength;
    }

    public static String getPatientImagePath() {
        return patient_image;
    }

    public static boolean getMrnoIsDate() {
        return mrnoDate;
    }

    public static String getsmartcardhead() {
        return smartcard_head_name;
    }

    public static String getfooterheading() {
        return footerheading;
    }

    public static String getprovidedby() {
        return providedby;
    }

    public static String getPatientFiles() {
        return paitentFiles;
    }

    public static String getErnoPreFix() {
        return ernoprefix;
    }

    public static int getErnoPrefixstringLength() {
        return ernoprefixlength;
    }

    public static boolean getErnoIsDate() {
        return ernoDate;
    }

    public static boolean getSmsApi() {
        return SmsApi;
    }

    public static boolean getEmailApi() {
        return EmailApi;
    }

    public static String getPatientFileBasePath() {
        return paitentFilesBasePath;
    }

    public static String getPdfStoreUrl() {
        return pdfStoreUrl;
    }

    public static void setPdfStoreUrl(String pdfStoreUrl) {
        Propertyconfig.pdfStoreUrl = pdfStoreUrl;
    }

    public static String getSmsApiNMMC() {
        return SmsApiNMMC;
    }

    public static void setSmsApiNMMC(String smsApiNMMC) {
        SmsApiNMMC = smsApiNMMC;
    }

    public static String getDirectorMobile1() {
        return directorMobile1;
    }

    public static void setDirectorMobile1(String directorMobile1) {
        Propertyconfig.directorMobile1 = directorMobile1;
    }

    public static String getDirectorMobile2() {
        return directorMobile2;
    }

    public static void setDirectorMobile2(String directorMobile2) {
        Propertyconfig.directorMobile2 = directorMobile2;
    }

    public static String getDirectorMobile3() {
        return directorMobile3;
    }

    public static void setDirectorMobile3(String directorMobile3) {
        Propertyconfig.directorMobile3 = directorMobile3;
    }

    public static String getDirectorMobile4() {
        return directorMobile4;
    }

    public static void setDirectorMobile4(String directorMobile4) {
        Propertyconfig.directorMobile4 = directorMobile4;
    }

    public static String getDirectorMobile5() {
        return directorMobile5;
    }

    public static void setDirectorMobile5(String directorMobile5) {
        Propertyconfig.directorMobile5 = directorMobile5;
    }

    public static String getSmsURL() {
        return SmsURL;
    }

    public static String getClientName() {
        return clientName;
    }

    public static String getEmailUsername() {
        return emailUsername;
    }

    public static String getEmailPassword() {
        return emailPassword;
    }

    public static String getDirectorEmail1() {
        return directorEmail1;
    }

    public static String getServiceUrl() {
        return serviceUrl;
    }

    public static String getDirectorEmail2() {
        return directorEmail2;
    }

    public static boolean getEmailApiForMgmt() {
        return EmailApiForMgmt;
    }

    public static void setEmailApiForMgmt(boolean emailApiForMgmt) {
        EmailApiForMgmt = emailApiForMgmt;
    }

    public static String getClient() {
        return client;
    }
}
