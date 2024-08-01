package com.cellbeans.hspa.maccostcategory;

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
@Table(name = "mac_cost_category")
public class MacCostCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ccId", unique = true, nullable = true)
    private long ccId;

    @JsonInclude(NON_NULL)
    @Column(name = "ccName")
    private String ccName;

    @JsonInclude(NON_NULL)
    @Column(name = "ccAllocatedRevenueItems", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ccAllocatedRevenueItems = false;

    @JsonInclude(NON_NULL)
    @Column(name = "ccActivateNonRevenueItems", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ccActivateNonRevenueItems = false;

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

    public long getCcId() {
        return ccId;
    }

    public void setCcId(long ccId) {
        this.ccId = ccId;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public boolean getCcAllocatedRevenueItems() {
        return ccAllocatedRevenueItems;
    }

    public void setCcAllocatedRevenueItems(boolean ccAllocatedRevenueItems) {
        this.ccAllocatedRevenueItems = ccAllocatedRevenueItems;
    }

    public boolean getCcActivateNonRevenueItems() {
        return ccActivateNonRevenueItems;
    }

    public void setCcActivateNonRevenueItems(boolean ccActivateNonRevenueItems) {
        this.ccActivateNonRevenueItems = ccActivateNonRevenueItems;
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
