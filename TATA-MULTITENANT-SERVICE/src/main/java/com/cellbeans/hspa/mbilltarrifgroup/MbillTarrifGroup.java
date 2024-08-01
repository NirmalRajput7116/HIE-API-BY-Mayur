package com.cellbeans.hspa.mbilltarrifgroup;

import com.cellbeans.hspa.mbillgroup.MbillGroup;
import com.cellbeans.hspa.mbillsubgroup.MbillSubGroup;
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
@Table(name = "mbill_tarrif_group")
public class MbillTarrifGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tgId", unique = true, nullable = true)
    private long tgId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tgGroupId")
    private MbillGroup tgGroupId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tgSgId")
    private MbillSubGroup tgSgId;

    @JsonInclude(NON_NULL)
    @Column(name = "tgCoPay")
    private double tgCoPay;

    @JsonInclude(NON_NULL)
    @Column(name = "tgDiscount")
    private double tgDiscount;

    @JsonInclude(NON_NULL)
    @Column(name = "tgApplicableOnGroup")
    private String tgApplicableOnGroup;

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

    public long getTgId() {
        return tgId;
    }

    public void setTgId(int tgId) {
        this.tgId = tgId;
    }

    public MbillGroup getTgGroupId() {
        return tgGroupId;
    }

    public void setTgGroupId(MbillGroup tgGroupId) {
        this.tgGroupId = tgGroupId;
    }

    public MbillSubGroup getTgSgId() {
        return tgSgId;
    }

    public void setTgSgId(MbillSubGroup tgSgId) {
        this.tgSgId = tgSgId;
    }

    public double getTgCoPay() {
        return tgCoPay;
    }

    public void setTgCoPay(double tgCoPay) {
        this.tgCoPay = tgCoPay;
    }

    public double getTgDiscount() {
        return tgDiscount;
    }

    public void setTgDiscount(double tgDiscount) {
        this.tgDiscount = tgDiscount;
    }

    public String getTgApplicableOnGroup() {
        return tgApplicableOnGroup;
    }

    public void setTgApplicableOnGroup(String tgApplicableOnGroup) {
        this.tgApplicableOnGroup = tgApplicableOnGroup;
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
