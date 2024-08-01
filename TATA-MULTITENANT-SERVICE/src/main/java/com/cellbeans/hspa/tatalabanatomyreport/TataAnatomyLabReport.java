package com.cellbeans.hspa.tatalabanatomyreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "lis_specimeninfo")
public class TataAnatomyLabReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @JsonInclude(NON_NULL)
    @Column(name = "inspectdate")
    private String inspectdate;

    @JsonInclude(NON_NULL)
    @Column(name = "specimenid")
    private String specimenid;

    @JsonInclude(NON_NULL)
    @Column(name = "requestid")
    private String requestid;

    @JsonInclude(NON_NULL)
    @Column(name = "patienttype")
    private String patienttype;

    @JsonInclude(NON_NULL)
    @Column(name = "patientid")
    private String patientid;

    @JsonInclude(NON_NULL)
    @Column(name = "patientname")
    private String patientname;

    @JsonInclude(NON_NULL)
    @Column(name = "patientgender")
    private String patientgender;

    @JsonInclude(NON_NULL)
    @Column(name = "patientage")
    private String patientage;

    @JsonInclude(NON_NULL)
    @Column(name = "ageunit")
    private String ageunit;

    @JsonInclude(NON_NULL)
    @Column(name = "birthday")
    private String birthday;

    @JsonInclude(NON_NULL)
    @Column(name = "chargetype")
    private String chargetype;

    @JsonInclude(NON_NULL)
    @Column(name = "deliveroffice")
    private String deliveroffice;

    @JsonInclude(NON_NULL)
    @Column(name = "patientdistrict")
    private String patientdistrict;

    @JsonInclude(NON_NULL)
    @Column(name = "bedno")
    private String bedno;

    @JsonInclude(NON_NULL)
    @Column(name = "specimentype")
    private String specimentype;

    @JsonInclude(NON_NULL)
    @Column(name = "specimenappraise")
    private String specimenappraise;

    @JsonInclude(NON_NULL)
    @Column(name = "specimendeliverer")
    private String specimendeliverer;

    @JsonInclude(NON_NULL)
    @Column(name = "delivertime")
    private String delivertime;

    @JsonInclude(NON_NULL)
    @Column(name = "inspector")
    private String inspector;

    @JsonInclude(NON_NULL)
    @Column(name = "auditor")
    private String auditor;

    @JsonInclude(NON_NULL)
    @Column(name = "diagnosis")
    private String diagnosis;

    @JsonInclude(NON_NULL)
    @Column(name = "remark")
    private String remark;

    @JsonInclude(NON_NULL)
    @Column(name = "sampletime")
    private String sampletime;

    @JsonInclude(NON_NULL)
    @Column(name = "reporttime")
    private String reporttime;

    @JsonInclude(NON_NULL)
    @Column(name = "saved")
    private String saved;

    @JsonInclude(NON_NULL)
    @Column(name = "customrecord1")
    private String customrecord1;

    @JsonInclude(NON_NULL)
    @Column(name = "customrecord2")
    private String customrecord2;

    @JsonInclude(NON_NULL)
    @Column(name = "customrecord3")
    private String customrecord3;

    @JsonInclude(NON_NULL)
    @Column(name = "customrecord4")
    private String customrecord4;

    @JsonInclude(NON_NULL)
    @Column(name = "patientlastname")
    private String patientlastname;

    @JsonInclude(NON_NULL)
    @Column(name = "audittime")
    private String audittime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInspectdate() {
        return inspectdate;
    }

    public void setInspectdate(String inspectdate) {
        this.inspectdate = inspectdate;
    }

    public String getSpecimenid() {
        return specimenid;
    }

    public void setSpecimenid(String specimenid) {
        this.specimenid = specimenid;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getPatienttype() {
        return patienttype;
    }

    public void setPatienttype(String patienttype) {
        this.patienttype = patienttype;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getPatientgender() {
        return patientgender;
    }

    public void setPatientgender(String patientgender) {
        this.patientgender = patientgender;
    }

    public String getPatientage() {
        return patientage;
    }

    public void setPatientage(String patientage) {
        this.patientage = patientage;
    }

    public String getAgeunit() {
        return ageunit;
    }

    public void setAgeunit(String ageunit) {
        this.ageunit = ageunit;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getChargetype() {
        return chargetype;
    }

    public void setChargetype(String chargetype) {
        this.chargetype = chargetype;
    }

    public String getDeliveroffice() {
        return deliveroffice;
    }

    public void setDeliveroffice(String deliveroffice) {
        this.deliveroffice = deliveroffice;
    }

    public String getPatientdistrict() {
        return patientdistrict;
    }

    public void setPatientdistrict(String patientdistrict) {
        this.patientdistrict = patientdistrict;
    }

    public String getBedno() {
        return bedno;
    }

    public void setBedno(String bedno) {
        this.bedno = bedno;
    }

    public String getSpecimentype() {
        return specimentype;
    }

    public void setSpecimentype(String specimentype) {
        this.specimentype = specimentype;
    }

    public String getSpecimenappraise() {
        return specimenappraise;
    }

    public void setSpecimenappraise(String specimenappraise) {
        this.specimenappraise = specimenappraise;
    }

    public String getSpecimendeliverer() {
        return specimendeliverer;
    }

    public void setSpecimendeliverer(String specimendeliverer) {
        this.specimendeliverer = specimendeliverer;
    }

    public String getDelivertime() {
        return delivertime;
    }

    public void setDelivertime(String delivertime) {
        this.delivertime = delivertime;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSampletime() {
        return sampletime;
    }

    public void setSampletime(String sampletime) {
        this.sampletime = sampletime;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }

    public String getCustomrecord1() {
        return customrecord1;
    }

    public void setCustomrecord1(String customrecord1) {
        this.customrecord1 = customrecord1;
    }

    public String getCustomrecord2() {
        return customrecord2;
    }

    public void setCustomrecord2(String customrecord2) {
        this.customrecord2 = customrecord2;
    }

    public String getCustomrecord3() {
        return customrecord3;
    }

    public void setCustomrecord3(String customrecord3) {
        this.customrecord3 = customrecord3;
    }

    public String getCustomrecord4() {
        return customrecord4;
    }

    public void setCustomrecord4(String customrecord4) {
        this.customrecord4 = customrecord4;
    }

    public String getPatientlastname() {
        return patientlastname;
    }

    public void setPatientlastname(String patientlastname) {
        this.patientlastname = patientlastname;
    }

    public String getAudittime() {
        return audittime;
    }

    public void setAudittime(String audittime) {
        this.audittime = audittime;
    }

    @Override
    public String toString() {
        return "TataAnatomyLabReport{" + "id=" + id + ", inspectdate=" + inspectdate + ", specimenid='" + specimenid + '\'' + ", requestid='" + requestid + '\'' + ", patienttype='" + patienttype + '\'' + ", patientid='" + patientid + '\'' + ", patientname='" + patientname + '\'' + ", patientgender='" + patientgender + '\'' + ", patientage='" + patientage + '\'' + ", ageunit='" + ageunit + '\'' + ", birthday='" + birthday + '\'' + ", chargetype='" + chargetype + '\'' + ", deliveroffice='" + deliveroffice + '\'' + ", patientdistrict='" + patientdistrict + '\'' + ", bedno='" + bedno + '\'' + ", specimentype='" + specimentype + '\'' + ", specimenappraise='" + specimenappraise + '\'' + ", specimendeliverer='" + specimendeliverer + '\'' + ", delivertime='" + delivertime + '\'' + ", inspector='" + inspector + '\'' + ", auditor='" + auditor + '\'' + ", diagnosis='" + diagnosis + '\'' + ", remark='" + remark + '\'' + ", sampletime='" + sampletime + '\'' + ", reporttime='" + reporttime + '\'' + ", saved='" + saved + '\'' + ", customrecord1='" + customrecord1 + '\'' + ", customrecord2='" + customrecord2 + '\'' + ", customrecord3='" + customrecord3 + '\'' + ", customrecord4='" + customrecord4 + '\'' + ", patientlastname='" + patientlastname + '\'' + ", audittime='" + audittime + '\'' + '}';
    }

}

