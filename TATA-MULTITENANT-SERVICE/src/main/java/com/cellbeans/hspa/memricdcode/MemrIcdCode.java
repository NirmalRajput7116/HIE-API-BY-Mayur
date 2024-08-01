package com.cellbeans.hspa.memricdcode;

import com.cellbeans.hspa.memrdiseasecategory.MemrDiseaseCategory;
import com.cellbeans.hspa.memrdiseasesubcategory.MemrDiseaseSubCategory;
import com.cellbeans.hspa.memrdiseasetype.MemrDiseaseType;
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
@Table(name = "memr_icd_code")
public class MemrIcdCode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icId", unique = true, nullable = true)
    private long icId;

    @JsonInclude(NON_NULL)
    @Column(name = "icCode")
    private String icCode;

    @JsonInclude(NON_NULL)
    @Column(name = "icDescription")
    private String icDescription;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icDtId")
    private MemrDiseaseType icDtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icDscId")
    private MemrDiseaseSubCategory icDscId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icDcId")
    private MemrDiseaseCategory icDcId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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

    public long getIcId() {
        return icId;
    }

    public void setIcId(long icId) {
        this.icId = icId;
    }

    public String getIcCode() {
        return icCode;
    }

    public void setIcCode(String icCode) {
        this.icCode = icCode;
    }

    public String getIcDescription() {
        return icDescription;
    }

    public void setIcDescription(String icDescription) {
        this.icDescription = icDescription;
    }

    public MemrDiseaseType getIcDtId() {
        return icDtId;
    }

    public void setIcDtId(MemrDiseaseType icDtId) {
        this.icDtId = icDtId;
    }

    public MemrDiseaseSubCategory getIcDscId() {
        return icDscId;
    }

    public void setIcDscId(MemrDiseaseSubCategory icDscId) {
        this.icDscId = icDscId;
    }

    public MemrDiseaseCategory getIcDcId() {
        return icDcId;
    }

    public void setIcDcId(MemrDiseaseCategory icDcId) {
        this.icDcId = icDcId;
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
