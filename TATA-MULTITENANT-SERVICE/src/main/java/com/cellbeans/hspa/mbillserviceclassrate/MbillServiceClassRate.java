package com.cellbeans.hspa.mbillserviceclassrate;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mstclass.MstClass;
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
@Table(name = "mbill_service_class_rate")
public class MbillServiceClassRate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrId", unique = true, nullable = true)
    private long scrId;

    @JsonInclude(NON_NULL)
    @Column(name = "scrAdjustPercentage")
    private double scrAdjustPercentage;

    @JsonInclude(NON_NULL)
    @Column(name = "scrAdjustAmount")
    private double scrAdjustAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "scrBaseRate")
    private double scrBaseRate;

    @JsonInclude(NON_NULL)
    @Column(name = "scrFunction")
    private String scrFunction;

    @JsonInclude(NON_NULL)
    @Column(name = "scrGross")
    private String scrGross;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scrClassId")
    private MstClass scrClassId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scrMbillServiceId")
    private MbillService scrMbillServiceId;

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

    public MbillService getScrMbillServiceId() {
        return scrMbillServiceId;
    }

    public void setScrMbillServiceId(MbillService scrMbillServiceId) {
        this.scrMbillServiceId = scrMbillServiceId;
    }

    public long getScrId() {
        return scrId;
    }

    public void setScrId(int scrId) {
        this.scrId = scrId;
    }

    public double getScrAdjustPercentage() {
        return scrAdjustPercentage;
    }

    public void setScrAdjustPercentage(double scrAdjustPercentage) {
        this.scrAdjustPercentage = scrAdjustPercentage;
    }

    public double getScrAdjustAmount() {
        return scrAdjustAmount;
    }

    public void setScrAdjustAmount(double scrAdjustAmount) {
        this.scrAdjustAmount = scrAdjustAmount;
    }

    public double getScrBaseRate() {
        return scrBaseRate;
    }

    public void setScrBaseRate(double scrBaseRate) {
        this.scrBaseRate = scrBaseRate;
    }

    public String getScrFunction() {
        return scrFunction;
    }

    public void setScrFunction(String scrFunction) {
        this.scrFunction = scrFunction;
    }

    public String getScrGross() {
        return scrGross;
    }

    public void setScrGross(String scrGross) {
        this.scrGross = scrGross;
    }

    public MstClass getScrClassId() {
        return scrClassId;
    }

    public void setScrClassId(MstClass scrClassId) {
        this.scrClassId = scrClassId;
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
