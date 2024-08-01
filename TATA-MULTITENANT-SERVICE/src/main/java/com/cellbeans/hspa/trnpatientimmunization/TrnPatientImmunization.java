
package com.cellbeans.hspa.trnpatientimmunization;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import com.cellbeans.hspa.mstimmunizationcategory.MstImmunizationCategory;

import com.cellbeans.hspa.mstpatient.MstPatient;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_patient_immunization")
public class TrnPatientImmunization implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "piId", unique = true, nullable = false)
    private long piId;

    @Column(name = "piStartDate")
    private Date piStartDate;

    @ManyToOne
    @JoinColumn(name = "piIcId")
    private MstImmunizationCategory piIcId;

    @ManyToOne
    @JoinColumn(name = "piPatientId")
    private MstPatient piPatientId;

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

    public long getPiId() {
        return this.piId;
    }

    public void setPiId(int piId) {
        this.piId = piId;
    }

    public MstImmunizationCategory getPiIcId() {
        return this.piIcId;
    }

    public void setPiIcId(MstImmunizationCategory piIcId) {
        this.piIcId = piIcId;
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

    public Date getPiStartDate() {
        return piStartDate;
    }

    public void setPiStartDate(Date piStartDate) {
        this.piStartDate = piStartDate;
    }

    public MstPatient getPiPatientId() {
        return piPatientId;
    }

    public void setPiPatientId(MstPatient piPatientId) {
        this.piPatientId = piPatientId;
    }
}
