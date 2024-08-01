package com.cellbeans.hspa.tbillplanitem;

import com.cellbeans.hspa.mbillitem.MbillItem;
import com.cellbeans.hspa.mbillitemcategory.MbillItemCategory;
import com.cellbeans.hspa.mbillitemgroup.MbillItemGroup;
import com.cellbeans.hspa.mbillplan.MbillPlan;
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
@Table(name = "tbill_plan_item")
public class TbillPlanItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "piId", unique = true, nullable = true)
    private long piId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "piItemId")
    private MbillItem piItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "piIgId")
    private MbillItemGroup piIgId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "piIcId")
    private MbillItemCategory piIcId;

    @JsonInclude(NON_NULL)
    @Column(name = "piCoPay")
    private double piCoPay;

    @JsonInclude(NON_NULL)
    @Column(name = "piDiscount")
    private double piDiscount;

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
    @JoinColumn(name = "piPlanId")
    private MbillPlan piPlanId;

    public long getPiId() {
        return piId;
    }

    public void setPiId(int piId) {
        this.piId = piId;
    }

    public MbillItem getPiItemId() {
        return piItemId;
    }

    public void setPiItemId(MbillItem piItemId) {
        this.piItemId = piItemId;
    }

    public MbillItemGroup getPiIgId() {
        return piIgId;
    }

    public void setPiIgId(MbillItemGroup piIgId) {
        this.piIgId = piIgId;
    }

    public MbillItemCategory getPiIcId() {
        return piIcId;
    }

    public void setPiIcId(MbillItemCategory piIcId) {
        this.piIcId = piIcId;
    }

    public double getPiCoPay() {
        return piCoPay;
    }

    public void setPiCoPay(double piCoPay) {
        this.piCoPay = piCoPay;
    }

    public double getPiDiscount() {
        return piDiscount;
    }

    public void setPiDiscount(double piDiscount) {
        this.piDiscount = piDiscount;
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

    public MbillPlan getPiPlanId() {
        return piPlanId;
    }

    public void setPiPlanId(MbillPlan piPlanId) {
        this.piPlanId = piPlanId;
    }

}            
