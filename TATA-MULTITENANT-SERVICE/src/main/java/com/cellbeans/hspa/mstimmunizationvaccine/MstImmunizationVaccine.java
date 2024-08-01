
package com.cellbeans.hspa.mstimmunizationvaccine;

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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_immunization_vaccine")
public class MstImmunizationVaccine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ivId", unique = true, nullable = false)
    private long ivId;

    @Column(name = "ivName")
    private String ivName;

    @Column(name = "ivWhenToGive")
    private String ivWhenToGive;

    @ManyToOne
    @JoinColumn(name = "ivIcId")
    private MstImmunizationCategory ivIcId;

//    @Column(name = "ivGivenAfterBornInDays")
//    private int ivGivenAfterBornInDays;
//
//    @Column(name = "ivGivenTillBornInDays")
//    private int ivGivenTillBornInDays;

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

    public long getIvId() {
        return this.ivId;
    }

    public void setIvId(int ivId) {
        this.ivId = ivId;
    }

    public String getIvName() {
        return this.ivName;
    }

    public void setIvName(String ivName) {
        this.ivName = ivName;
    }

    public String getIvWhenToGive() {
        return ivWhenToGive;
    }

    public void setIvWhenToGive(String ivWhenToGive) {
        this.ivWhenToGive = ivWhenToGive;
    }

    public MstImmunizationCategory getIvIcId() {
        return this.ivIcId;
    }

    public void setIvIcId(MstImmunizationCategory ivIcId) {
        this.ivIcId = ivIcId;
    }

//    public int getIvGivenAfterBornInDays() {
//        return this.ivGivenAfterBornInDays;
//    }
//
//    public void setIvGivenAfterBornInDays(int ivGivenAfterBornInDays) {
//        this.ivGivenAfterBornInDays = ivGivenAfterBornInDays;
//    }
//
//    public int getIvGivenTillBornInDays() {
//        return this.ivGivenTillBornInDays;
//    }
//
//    public void setIvGivenTillBornInDays(int ivGivenTillBornInDays) {
//        this.ivGivenTillBornInDays = ivGivenTillBornInDays;
//    }

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

}
