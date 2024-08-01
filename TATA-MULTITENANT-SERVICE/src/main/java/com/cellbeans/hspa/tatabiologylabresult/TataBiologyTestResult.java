package com.cellbeans.hspa.tatabiologylabresult;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "lis_patient_observation")
public class TataBiologyTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "patientid")
    private String patientid;

    @Column(name = "obxid")
    private String obxid;

    @Column(name = "obxvaluetype")
    private String obxvaluetype;

    @Column(name = "observationidentifier")
    private String observationidentifier;

    @Column(name = "observationsubid")
    private String observationsubid;

    @Column(name = "observationvalue")
    private String observationvalue;

    @Column(name = "observationunit")
    private String observationunit;

    @Column(name = "abnormalflag")
    private String abnormalflag;

    @Column(name = "observationstatus")
    private String observationstatus;

    @Column(name = "accesscheck")
    private String accesscheck;

    @Column(name = "observationtime")
    private String observationtime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getObxid() {
        return obxid;
    }

    public void setObxid(String obxid) {
        this.obxid = obxid;
    }

    public String getObxvaluetype() {
        return obxvaluetype;
    }

    public void setObxvaluetype(String obxvaluetype) {
        this.obxvaluetype = obxvaluetype;
    }

    public String getObservationidentifier() {
        return observationidentifier;
    }

    public void setObservationidentifier(String observationidentifier) {
        this.observationidentifier = observationidentifier;
    }

    public String getObservationsubid() {
        return observationsubid;
    }

    public void setObservationsubid(String observationsubid) {
        this.observationsubid = observationsubid;
    }

    public String getObservationvalue() {
        return observationvalue;
    }

    public void setObservationvalue(String observationvalue) {
        this.observationvalue = observationvalue;
    }

    public String getObservationunit() {
        return observationunit;
    }

    public void setObservationunit(String observationunit) {
        this.observationunit = observationunit;
    }

    public String getAbnormalflag() {
        return abnormalflag;
    }

    public void setAbnormalflag(String abnormalflag) {
        this.abnormalflag = abnormalflag;
    }

    public String getObservationstatus() {
        return observationstatus;
    }

    public void setObservationstatus(String observationstatus) {
        this.observationstatus = observationstatus;
    }

    public String getAccesscheck() {
        return accesscheck;
    }

    public void setAccesscheck(String accesscheck) {
        this.accesscheck = accesscheck;
    }

    public String getObservationtime() {
        return observationtime;
    }

    public void setObservationtime(String observationtime) {
        this.observationtime = observationtime;
    }

    @Override
    public String toString() {
        return "TataBiologyLabReport{" + "id=" + id + ", patientid='" + patientid + '\'' + ", obxid='" + obxid + '\'' + ", obxvaluetype='" + obxvaluetype + '\'' + ", observationidentifier='" + observationidentifier + '\'' + ", observationsubid='" + observationsubid + '\'' + ", observationvalue='" + observationvalue + '\'' + ", observationunit='" + observationunit + '\'' + ", abnormalflag='" + abnormalflag + '\'' + ", observationstatus='" + observationstatus + '\'' + ", accesscheck='" + accesscheck + '\'' + ", observationtime='" + observationtime + '\'' + '}';
    }

}
