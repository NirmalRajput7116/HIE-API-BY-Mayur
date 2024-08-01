package com.cellbeans.hspa.mstvisit;

public class MstVisitSearchDto {

    private Long visitId;

    private Long patientId;

    private String userFirstname;

    private String userLastname;

    private String patientMrNo;

    private String userMobile;

    private String userAge;

    public MstVisitSearchDto() {
    }

    public MstVisitSearchDto(Long visitId, String userFirstname, String userLastname, String patientMrNo, String userMobile, String userAge, Long patientId) {
        this.visitId = visitId;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
        this.patientMrNo = patientMrNo;
        this.userMobile = userMobile;
        this.userAge = userAge;
        this.patientId = patientId;
    }

    public MstVisitSearchDto(Long visitId, String userFirstname, String userLastname, String patientMrNo, String userMobile, String userAge) {
        this.visitId = visitId;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
        this.patientMrNo = patientMrNo;
        this.userMobile = userMobile;
        this.userAge = userAge;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getPatientMrNo() {
        return patientMrNo;
    }

    public void setPatientMrNo(String patientMrNo) {
        this.patientMrNo = patientMrNo;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

}
