
package com.cellbeans.hspa.trnpatientimmunizationchart;

import java.io.Serializable;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import com.cellbeans.hspa.trnpatientimmunization.TrnPatientImmunization;
import com.cellbeans.hspa.mstimmunizationvaccine.MstImmunizationVaccine;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_patient_immunization_chart")
public class TrnPatientImmunizationChart implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picId", unique = true, nullable = false)
    private long picId;

    @Column(name = "test")
    private String test;

    @Column(name = "picAgeOf")
    private String picAgeOf;

    @Column(name = "picDateOfVaccinationFrom")
    private Date picDateOfVaccinationFrom;

    @Column(name = "picDateOfVaccinationTill")
    private Date picDateOfVaccinationTill;

    @Column(name = "picDateOfVaccinationTaken")
    private Date picDateOfVaccinationTaken;

    @Column(name = "picDateOfNextVisit")
    private Date picDateOfNextVisit;

    @ManyToOne
    @JoinColumn(name = "picPiId")
    private TrnPatientImmunization picPiId;

    @ManyToOne
    @JoinColumn(name = "picIvId")
    private MstImmunizationVaccine picIvId;

    @Column(name = "picIsTaken", columnDefinition = "binary(1) default false", nullable = false)
    private Boolean picIsTaken = false;

    @ManyToOne
    @JoinColumn(name = "picStaffId")
    private MstStaff picStaffId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = false)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = false)
    private Boolean isDeleted = false;

    @JsonIgnore
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @JsonIgnore
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getPicId() {
        return this.picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getTest() {
        return this.test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getPicAgeOf() {
        return picAgeOf;
    }

    public void setPicAgeOf(String picAgeOf) {
        this.picAgeOf = picAgeOf;
    }

    public TrnPatientImmunization getPicPiId() {
        return this.picPiId;
    }

    public void setPicPiId(TrnPatientImmunization picPiId) {
        this.picPiId = picPiId;
    }

    public MstImmunizationVaccine getPicIvId() {
        return this.picIvId;
    }

    public void setPicIvId(MstImmunizationVaccine picIvId) {
        this.picIvId = picIvId;
    }

    public boolean getPicIsTaken() {
        return this.picIsTaken;
    }

    public void setPicIsTaken(boolean picIsTaken) {
        this.picIsTaken = picIsTaken;
    }

    public MstStaff getPicStaffId() {
        return picStaffId;
    }

    public void setPicStaffId(MstStaff picStaffId) {
        this.picStaffId = picStaffId;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getPicDateOfVaccinationFrom() {
        return picDateOfVaccinationFrom;
    }

    public void setPicDateOfVaccinationFrom(Date picDateOfVaccinationFrom) {
        this.picDateOfVaccinationFrom = picDateOfVaccinationFrom;
    }

    public Date getPicDateOfVaccinationTill() {
        return picDateOfVaccinationTill;
    }

    public void setPicDateOfVaccinationTill(Date picDateOfVaccinationTill) {
        this.picDateOfVaccinationTill = picDateOfVaccinationTill;
    }

    public Date getPicDateOfVaccinationTaken() {
        return picDateOfVaccinationTaken;
    }

    public void setPicDateOfVaccinationTaken(Date picDateOfVaccinationTaken) {
        this.picDateOfVaccinationTaken = picDateOfVaccinationTaken;
    }

    public Date getPicDateOfNextVisit() {
        return picDateOfNextVisit;
    }

    public void setPicDateOfNextVisit(Date picDateOfNextVisit) {
        this.picDateOfNextVisit = picDateOfNextVisit;
    }
}
