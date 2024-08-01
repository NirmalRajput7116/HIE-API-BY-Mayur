package com.cellbeans.hspa.temrdiagnosis;

import com.cellbeans.hspa.memrcomplexcategory.MemrComplexCategory;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "temr_diagnosis")
public class TemrDiagnosis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosisId", unique = true, nullable = true)
    private long diagnosisId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "diagnosisVisitId")
    private MstVisit diagnosisVisitId;

    @JsonInclude(NON_NULL)
    @Column(name = "diagnosisDescription")
    private String diagnosisDescription;

    @JsonInclude(NON_NULL)
    @Column(name = "diagnosisOtherDescription")
    private String diagnosisOtherDescription;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "diagnosisCcId")
    private MemrComplexCategory diagnosisCcId;

    @JsonInclude(NON_NULL)
    @Column(name = "diagnosisIsLifeLong")
    private int diagnosisIsLifeLong;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public MstVisit getDiagnosisVisitId() {
        return diagnosisVisitId;
    }

    public void setDiagnosisVisitId(MstVisit diagnosisVisitId) {
        this.diagnosisVisitId = diagnosisVisitId;
    }

    public String getDiagnosisDescription() {
        return diagnosisDescription;
    }

    public void setDiagnosisDescription(String diagnosisDescription) {
        this.diagnosisDescription = diagnosisDescription;
    }

    public String getDiagnosisOtherDescription() {
        return diagnosisOtherDescription;
    }

    public void setDiagnosisOtherDescription(String diagnosisOtherDescription) {
        this.diagnosisOtherDescription = diagnosisOtherDescription;
    }

    public MemrComplexCategory getDiagnosisCcId() {
        return diagnosisCcId;
    }

    public void setDiagnosisCcId(MemrComplexCategory diagnosisCcId) {
        this.diagnosisCcId = diagnosisCcId;
    }

    public int getDiagnosisIsLifeLong() {
        return diagnosisIsLifeLong;
    }

    public void setDiagnosisIsLifeLong(int diagnosisIsLifeLong) {
        this.diagnosisIsLifeLong = diagnosisIsLifeLong;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}            
