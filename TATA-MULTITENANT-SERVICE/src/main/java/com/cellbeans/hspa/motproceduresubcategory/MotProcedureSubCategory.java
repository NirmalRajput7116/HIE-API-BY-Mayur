package com.cellbeans.hspa.motproceduresubcategory;

import com.cellbeans.hspa.motprocedurecategory.MotProcedureCategory;
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
@Table(name = "mot_procedure_sub_category")
public class MotProcedureSubCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pscId", unique = true, nullable = true)
    private long pscId;

    @JsonInclude(NON_NULL)
    @Column(name = "pscName")
    private String pscName;

    @JsonInclude(NON_NULL)
    @Column(name = "pscCode")
    private String pscCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pscCategoryId")
    private MotProcedureCategory pscCategoryId;

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

    public long getPscId() {
        return pscId;
    }

    public void setPscId(long pscId) {
        this.pscId = pscId;
    }

    public String getPscName() {
        return pscName;
    }

    public void setPscName(String pscName) {
        this.pscName = pscName;
    }

    public String getPscCode() {
        return pscCode;
    }

    public void setPscCode(String pscCode) {
        this.pscCode = pscCode;
    }

    public MotProcedureCategory getPscCategoryId() {
        return pscCategoryId;
    }

    public void setPscCategoryId(MotProcedureCategory pscCategoryId) {
        this.pscCategoryId = pscCategoryId;
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
