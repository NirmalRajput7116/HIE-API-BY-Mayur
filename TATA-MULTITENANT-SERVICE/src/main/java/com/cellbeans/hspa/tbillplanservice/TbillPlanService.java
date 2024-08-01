package com.cellbeans.hspa.tbillplanservice;

import com.cellbeans.hspa.mbillgroup.MbillGroup;
import com.cellbeans.hspa.mbillplan.MbillPlan;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mbillsubgroup.MbillSubGroup;
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
@Table(name = "tbill_plan_service")
public class TbillPlanService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "psId", unique = true, nullable = true)
    private long psId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psServiceId")
    private MbillService psServiceId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psClassId")
    private MstClass psClassId;

    @JsonInclude(NON_NULL)
    @Column(name = "psCoPay")
    private double psCoPay;

    @JsonInclude(NON_NULL)
    @Column(name = "psDiscount")
    private double psDiscount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psGroupId")
    private MbillGroup psGroupId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psSgId")
    private MbillSubGroup psSgId;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psPlanId")
    private MbillPlan psPlanId;

    public long getPsId() {
        return psId;
    }

    public void setPsId(int psId) {
        this.psId = psId;
    }

    public MbillService getPsServiceId() {
        return psServiceId;
    }

    public void setPsServiceId(MbillService psServiceId) {
        this.psServiceId = psServiceId;
    }

    public MstClass getPsClassId() {
        return psClassId;
    }

    public void setPsClassId(MstClass psClassId) {
        this.psClassId = psClassId;
    }

    public double getPsCoPay() {
        return psCoPay;
    }

    public void setPsCoPay(double psCoPay) {
        this.psCoPay = psCoPay;
    }

    public double getPsDiscount() {
        return psDiscount;
    }

    public void setPsDiscount(double psDiscount) {
        this.psDiscount = psDiscount;
    }

    public MbillGroup getPsGroupId() {
        return psGroupId;
    }

    public void setPsGroupId(MbillGroup psGroupId) {
        this.psGroupId = psGroupId;
    }

    public MbillSubGroup getPsSgId() {
        return psSgId;
    }

    public void setPsSgId(MbillSubGroup psSgId) {
        this.psSgId = psSgId;
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

    public MbillPlan getPsPlanId() {
        return psPlanId;
    }

    public void setPsPlanId(MbillPlan psPlanId) {
        this.psPlanId = psPlanId;
    }

}            
