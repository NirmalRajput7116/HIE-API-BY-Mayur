package com.cellbeans.hspa.tbillAgencyRate;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mpathagency.MpathAgency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/*
owner :vijay patil
date :26/6/2018
*/
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbill_agency_rate")
public class TbillAgencyRate {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atId", unique = true, nullable = true)
    private long atId;

    @JsonInclude(NON_NULL)
    @Column(name = "atServiceRate")
    private long atServiceRate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "atAgencyId")
    private MpathAgency atAgencyId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "atMbillServiceId")
    private MbillService atMbillServiceId;

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

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getAtId() {
        return atId;
    }

    public void setAtId(long atId) {
        this.atId = atId;
    }

    public long getAtServiceRate() {
        return atServiceRate;
    }

    public void setAtServiceRate(long atServiceRate) {
        this.atServiceRate = atServiceRate;
    }

    public MpathAgency getAtAgencyId() {
        return atAgencyId;
    }

    public void setAtAgencyId(MpathAgency atAgencyId) {
        this.atAgencyId = atAgencyId;
    }

    public MbillService getAtMbillServiceId() {
        return atMbillServiceId;
    }

    public void setAtMbillServiceId(MbillService atMbillServiceId) {
        this.atMbillServiceId = atMbillServiceId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "TbillAgencyRate{" + "atId=" + atId + ", atServiceRate=" + atServiceRate + ", atAgencyId=" + atAgencyId + ", atMbillServiceId=" + atMbillServiceId + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
