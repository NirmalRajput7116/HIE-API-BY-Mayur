package com.cellbeans.hspa.advancesearch;

import java.io.Serializable;
import java.util.Date;

public class AdvanceSearch implements Serializable {

    private String type;

    private String patientMrNo;

    private String userFirstname;

    private String userMiddlename;

    private String userLastname;

    private String userMobile;

    private String userEmail;

    private String userResidencePhone;

    private long nationalityId;

    private String userDobTo;

    private String userDobFrom;

    private long genderId;

    private long bloodgroupId;

    private String userUid;

    private long cityId;

    private long stateId;

    private long countryId;

    private long reId;

    private String reName;

    private String registrationDateTo;

    private String registrationDateFrom;

    private Date visitDateFrom;

    private Date visitDateTo;

    private Date admissionDateFrom;

    private Date admissionDateTo;

    public Date getAdmissionDateFrom() {
        return admissionDateFrom;
    }

    public void setAdmissionDateFrom(Date admissionDateFrom) {
        this.admissionDateFrom = admissionDateFrom;
    }

    public Date getAdmissionDateTo() {
        return admissionDateTo;
    }

    public void setAdmissionDateTo(Date admissionDateTo) {
        this.admissionDateTo = admissionDateTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getVisitDateFrom() {
        return visitDateFrom;
    }

    public void setVisitDateFrom(Date visitDateFrom) {
        this.visitDateFrom = visitDateFrom;
    }

    public Date getVisitDateTo() {
        return visitDateTo;
    }

    public void setVisitDateTo(Date visitDateTo) {
        this.visitDateTo = visitDateTo;
    }

    public String getPatientMrNo() {
        return patientMrNo;
    }

    public void setPatientMrNo(String patientMrNo) {
        this.patientMrNo = patientMrNo;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserMiddlename() {
        return userMiddlename;
    }

    public void setUserMiddlename(String userMiddlename) {
        this.userMiddlename = userMiddlename;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserResidencePhone() {
        return userResidencePhone;
    }

    public void setUserResidencePhone(String userResidencePhone) {
        this.userResidencePhone = userResidencePhone;
    }

    public String getUserDobTo() {
        return userDobTo;
    }

    public void setUserDobTo(String userDobTo) {
        this.userDobTo = userDobTo;
    }

    public String getUserDobFrom() {
        return userDobFrom;
    }

    public void setUserDobFrom(String userDobFrom) {
        this.userDobFrom = userDobFrom;
    }

    public long getGenderId() {
        return genderId;
    }

    public void setGenderId(long genderId) {
        this.genderId = genderId;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public long getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(long nationalityId) {
        this.nationalityId = nationalityId;
    }

    public long getBloodgroupId() {
        return bloodgroupId;
    }

    public void setBloodgroupId(long bloodgroupId) {
        this.bloodgroupId = bloodgroupId;
    }

    public long getReId() {
        return reId;
    }

    public void setReId(long reId) {
        this.reId = reId;
    }

    public String getReName() {
        return reName;
    }

    public void setReName(String reName) {
        this.reName = reName;
    }

    public String getRegistrationDateTo() {
        return registrationDateTo;
    }

    public void setRegistrationDateTo(String registrationDateTo) {
        this.registrationDateTo = registrationDateTo;
    }

    public String getRegistrationDateFrom() {
        return registrationDateFrom;
    }

    public void setRegistrationDateFrom(String registrationDateFrom) {
        this.registrationDateFrom = registrationDateFrom;
    }

    @Override
    public String toString() {
        return "MstPatientSearch [type=" + type + ", patientMrNo=" + patientMrNo + ", userFirstname=" + userFirstname + ", userMiddlename=" + userMiddlename + ", userLastname=" + userLastname + ", userMobile=" + userMobile + ", userEmail=" + userEmail + ", userResidencePhone=" + userResidencePhone + ", nationalityId=" + nationalityId + ", userDobTo=" + userDobTo + ", userDobFrom=" + userDobFrom + ", genderId=" + genderId + ", bloodgroupId=" + bloodgroupId + ", userUid=" + userUid + ", cityId=" + cityId + ", stateId=" + stateId + ", countryId=" + countryId + ", reId=" + reId + ", reName=" + reName + ", registrationDateTo=" + registrationDateTo + ", registrationDateFrom=" + registrationDateFrom + ", visitDateFrom=" + visitDateFrom + ", visitDateTo=" + visitDateTo + ", admissionDateFrom=" + admissionDateFrom + ", admissionDateTo=" + admissionDateTo + "]";
    }

}
