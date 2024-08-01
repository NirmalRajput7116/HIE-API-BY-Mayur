package com.cellbeans.hspa.trnpatientqueue;

public class PatientQueueDto {
    private static final long serialVersionUID = 1L;

    public Long billid;

    public String patientmrno;

    public String patienttitle;
    public String patientfname;
    public String patientmame;
    public String patientlame;

    public String patientgender;

    public String patientmno;

    public String patientage;
    public String patientday;
    public String patientmonth;

    public Long patientvisitid;

    public String patientvisitdate;

    public String patientstarttime;

    public String patienttariffname;

    public String patientendtime;

    public Long patyientbillid;

    public String stafftitle;
    public String staffname;
    public String staffmmame;
    public String stafflame;

    public Long patientstaffid;

    public String patientdepartment;

    public String patientsubdepartment;

    public String patientunitname;

    public String patienttokenid;

    public int patientbsstatus;

    public String patienttype;

    public String patientsource;
    public String patientUid;
    public String userDrivingNo;
    public String staffId;
    public String fromdate;
    public String todate;
    public String hubId;
    public String visitIdList;
    public Long createdById;

    public PatientQueueDto(long patientvisitid, String patientmrno, int patientbsstatus, String patienttokenid, long patyientbillid, String patienttariffname, String patienttitle, String patientfname, String patientmame, String patientlame, String patientmno, String patientage, String patientmonth, String patientday, String patientgender, String patientvisitdate, String patientstarttime, String patientendtime, long patientstaffid, String patientdepartment, String patientsubdepartment, String patientunitname, String patienttype, String patientsource, String stafftitle, String staffname, String stafflame) {
        this.patientvisitid = patientvisitid;
        this.patientmrno = patientmrno;
        this.patientbsstatus = patientbsstatus;
        this.patienttokenid = patienttokenid;
        this.patyientbillid = patyientbillid;
        this.patienttariffname = patienttariffname;
        this.patienttitle = patienttitle;
        this.patientfname = patientfname;
        this.patientmame = patientmame;
        this.patientlame = patientlame;
        this.patientmno = patientmno;
        this.patientage = patientage;
        this.patientmonth = patientmonth;
        this.patientday = patientday;
        this.patientgender = patientgender;
        this.patientvisitdate = patientvisitdate;
        this.patientstarttime = patientstarttime;
        this.patientendtime = patientendtime;
        this.patientstaffid = patientstaffid;
        this.patientdepartment = patientdepartment;
        this.patientsubdepartment = patientsubdepartment;
        this.patientunitname = patientunitname;
        this.patienttype = patienttype;
        this.patientsource = patientsource;
        this.stafftitle = stafftitle;
        this.staffname = staffname;
        this.stafflame = stafflame;

    }

    public PatientQueueDto() {
    }

    public Long getBillid() {
        return billid;
    }

    public void setBillid(Long billid) {
        this.billid = billid;
    }

    public String getPatientmrno() {
        return patientmrno;
    }

    public void setPatientmrno(String patientmrno) {
        this.patientmrno = patientmrno;
    }

    public String getPatienttitle() {
        return patienttitle;
    }

    public void setPatienttitle(String patienttitle) {
        this.patienttitle = patienttitle;
    }

    public String getPatientfname() {
        return patientfname;
    }

    public void setPatientfname(String patientfname) {
        this.patientfname = patientfname;
    }

    public String getPatientmame() {
        return patientmame;
    }

    public void setPatientmame(String patientmame) {
        this.patientmame = patientmame;
    }

    public String getPatientlame() {
        return patientlame;
    }

    public void setPatientlame(String patientlame) {
        this.patientlame = patientlame;
    }

    public String getPatientgender() {
        return patientgender;
    }

    public void setPatientgender(String patientgender) {
        this.patientgender = patientgender;
    }

    public String getPatientmno() {
        return patientmno;
    }

    public void setPatientmno(String patientmno) {
        this.patientmno = patientmno;
    }

    public String getPatientage() {
        return patientage;
    }

    public void setPatientage(String patientage) {
        this.patientage = patientage;
    }

    public String getPatientday() {
        return patientday;
    }

    public void setPatientday(String patientday) {
        this.patientday = patientday;
    }

    public String getPatientmonth() {
        return patientmonth;
    }

    public void setPatientmonth(String patientmonth) {
        this.patientmonth = patientmonth;
    }

    public Long getPatientvisitid() {
        return patientvisitid;
    }

    public void setPatientvisitid(Long patientvisitid) {
        this.patientvisitid = patientvisitid;
    }

    public String getPatientvisitdate() {
        return patientvisitdate;
    }

    public void setPatientvisitdate(String patientvisitdate) {
        this.patientvisitdate = patientvisitdate;
    }

    public String getPatientstarttime() {
        return patientstarttime;
    }

    public void setPatientstarttime(String patientstarttime) {
        this.patientstarttime = patientstarttime;
    }

    public String getPatienttariffname() {
        return patienttariffname;
    }

    public void setPatienttariffname(String patienttariffname) {
        this.patienttariffname = patienttariffname;
    }

    public String getPatientendtime() {
        return patientendtime;
    }

    public void setPatientendtime(String patientendtime) {
        this.patientendtime = patientendtime;
    }

    public Long getPatyientbillid() {
        return patyientbillid;
    }

    public void setPatyientbillid(Long patyientbillid) {
        this.patyientbillid = patyientbillid;
    }

    public String getStafftitle() {
        return stafftitle;
    }

    public void setStafftitle(String stafftitle) {
        this.stafftitle = stafftitle;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getStaffmmame() {
        return staffmmame;
    }

    public void setStaffmmame(String staffmmame) {
        this.staffmmame = staffmmame;
    }

    public String getStafflame() {
        return stafflame;
    }

    public void setStafflame(String stafflame) {
        this.stafflame = stafflame;
    }

    public Long getPatientstaffid() {
        return patientstaffid;
    }

    public void setPatientstaffid(Long patientstaffid) {
        this.patientstaffid = patientstaffid;
    }

    public String getPatientdepartment() {
        return patientdepartment;
    }

    public void setPatientdepartment(String patientdepartment) {
        this.patientdepartment = patientdepartment;
    }

    public String getPatientsubdepartment() {
        return patientsubdepartment;
    }

    public void setPatientsubdepartment(String patientsubdepartment) {
        this.patientsubdepartment = patientsubdepartment;
    }

    public String getPatientunitname() {
        return patientunitname;
    }

    public void setPatientunitname(String patientunitname) {
        this.patientunitname = patientunitname;
    }

    public String getPatienttokenid() {
        return patienttokenid;
    }

    public void setPatienttokenid(String patienttokenid) {
        this.patienttokenid = patienttokenid;
    }

    public int getPatientbsstatus() {
        return patientbsstatus;
    }

    public void setPatientbsstatus(int patientbsstatus) {
        this.patientbsstatus = patientbsstatus;
    }

    public String getPatienttype() {
        return patienttype;
    }

    public void setPatienttype(String patienttype) {
        this.patienttype = patienttype;
    }

    public String getPatientsource() {
        return patientsource;
    }

    public void setPatientsource(String patientsource) {
        this.patientsource = patientsource;
    }

    public String getPatientUid() {
        return patientUid;
    }

    public void setPatientUid(String patientUid) {
        this.patientUid = patientUid;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getUserDrivingNo() {
        return userDrivingNo;
    }

    public void setUserDrivingNo(String userDrivingNo) {
        this.userDrivingNo = userDrivingNo;
    }

    public String getHubId() {
        return hubId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
    }

    public String getVisitIdList() {
        return visitIdList;
    }

    public void setVisitIdList(String visitIdList) {
        this.visitIdList = visitIdList;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    @Override
    public String toString() {
        return "PatientQueueDto{" +
                "billid=" + billid +
                ", patientmrno='" + patientmrno + '\'' +
                ", patienttitle='" + patienttitle + '\'' +
                ", patientfname='" + patientfname + '\'' +
                ", patientmame='" + patientmame + '\'' +
                ", patientlame='" + patientlame + '\'' +
                ", patientgender='" + patientgender + '\'' +
                ", patientmno='" + patientmno + '\'' +
                ", patientage='" + patientage + '\'' +
                ", patientday='" + patientday + '\'' +
                ", patientmonth='" + patientmonth + '\'' +
                ", patientvisitid=" + patientvisitid +
                ", patientvisitdate='" + patientvisitdate + '\'' +
                ", patientstarttime='" + patientstarttime + '\'' +
                ", patienttariffname='" + patienttariffname + '\'' +
                ", patientendtime='" + patientendtime + '\'' +
                ", patyientbillid=" + patyientbillid +
                ", stafftitle='" + stafftitle + '\'' +
                ", staffname='" + staffname + '\'' +
                ", staffmmame='" + staffmmame + '\'' +
                ", stafflame='" + stafflame + '\'' +
                ", patientstaffid=" + patientstaffid +
                ", patientdepartment='" + patientdepartment + '\'' +
                ", patientsubdepartment='" + patientsubdepartment + '\'' +
                ", patientunitname='" + patientunitname + '\'' +
                ", patienttokenid='" + patienttokenid + '\'' +
                ", patientbsstatus=" + patientbsstatus +
                ", patienttype='" + patienttype + '\'' +
                ", patientsource='" + patientsource + '\'' +
                ", patientUid='" + patientUid + '\'' +
                ", staffId='" + staffId + '\'' +
                ", fromdate='" + fromdate + '\'' +
                ", todate='" + todate + '\'' +
                ", userDrivingNo='" + userDrivingNo + '\'' +
                ", visitIdList='" + visitIdList + '\'' +
                '}';
    }
}
