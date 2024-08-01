package com.cellbeans.hspa.tatabiologylabreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "lis_patient")
public class TataBiologyLabReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @JsonInclude(NON_NULL)
    @Column(name = "patientname")
    private String patientname;

    @JsonInclude(NON_NULL)
    @Column(name = "messagetime")
    private String messagetime;

    @JsonInclude(NON_NULL)
    @Column(name = "machinename")
    private String machinename;

    @JsonInclude(NON_NULL)
    @Column(name = "machinemodel")
    private String machinemodel;

    @JsonInclude(NON_NULL)
    @Column(name = "patientid")
    private String patientid;

    @JsonInclude(NON_NULL)
    @Column(name = "patientsex")
    private String patientsex;

    @JsonInclude(NON_NULL)
    @Column(name = "patientdob")
    private String patientdob;

    @JsonInclude(NON_NULL)
    @Column(name = "fileorderno")
    private String fileorderno;

    @JsonInclude(NON_NULL)
    @Column(name = "priorityobr")
    private String priorityobr;

    @JsonInclude(NON_NULL)
    @Column(name = "observationaldate")
    private String observationaldate;

    @JsonInclude(NON_NULL)
    @Column(name = "specimendate")
    private String specimendate;

    @JsonInclude(NON_NULL)
    @Column(name = "specimensource")
    private String specimensource;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(String messagetime) {
        this.messagetime = messagetime;
    }

    public String getMachinename() {
        return machinename;
    }

    public void setMachinename(String machinename) {
        this.machinename = machinename;
    }

    public String getMachinemodel() {
        return machinemodel;
    }

    public void setMachinemodel(String machinemodel) {
        this.machinemodel = machinemodel;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getPatientsex() {
        return patientsex;
    }

    public void setPatientsex(String patientsex) {
        this.patientsex = patientsex;
    }

    public String getPatientdob() {
        return patientdob;
    }

    public void setPatientdob(String patientdob) {
        this.patientdob = patientdob;
    }

    public String getFileorderno() {
        return fileorderno;
    }

    public void setFileorderno(String fileorderno) {
        this.fileorderno = fileorderno;
    }

    public String getPriorityobr() {
        return priorityobr;
    }

    public void setPriorityobr(String priorityobr) {
        this.priorityobr = priorityobr;
    }

    public String getObservationaldate() {
        return observationaldate;
    }

    public void setObservationaldate(String observationaldate) {
        this.observationaldate = observationaldate;
    }

    public String getSpecimendate() {
        return specimendate;
    }

    public void setSpecimendate(String specimendate) {
        this.specimendate = specimendate;
    }

    public String getSpecimensource() {
        return specimensource;
    }

    public void setSpecimensource(String specimensource) {
        this.specimensource = specimensource;
    }

    @Override
    public String toString() {
        return "TataBiologyLabReport{" + "id=" + id + ", patientname='" + patientname + '\'' + ", messagetime='" + messagetime + '\'' + ", machinename='" + machinename + '\'' + ", machinemodel='" + machinemodel + '\'' + ", patientid='" + patientid + '\'' + ", patientsex='" + patientsex + '\'' + ", patientdob='" + patientdob + '\'' + ", fileorderno='" + fileorderno + '\'' + ", priorityobr='" + priorityobr + '\'' + ", observationaldate='" + observationaldate + '\'' + ", specimendate='" + specimendate + '\'' + ", specimensource='" + specimensource + '\'' + '}';
    }

}

