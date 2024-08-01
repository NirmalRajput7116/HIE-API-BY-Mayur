package com.cellbeans.hspa.trnpayout;

import com.cellbeans.hspa.mstbank.MstBank;
import com.cellbeans.hspa.mstcashcounter.MstCashCounter;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
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
@Table(name = "trn_payout")
public class TrnPayout implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payoutId", unique = true, nullable = true)
    private long payoutId;

    @JsonInclude(NON_NULL)
    @Column(name = "payoutDateTime")
    private String payoutDateTime;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "payoutUnitId")
    private MstUnit payoutUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "payoutStaffId")
    private MstStaff payoutStaffId;

    @JsonInclude(NON_NULL)
    @Column
    private String payoutServiceType;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "payoutfromCcId")
    private MstCashCounter payoutFromCcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "payoutFromStaffId")
    private MstStaff payoutFromStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "payoutToCcId")
    private MstCashCounter payoutToCcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "payoutToStaffId")
    private MstStaff payoutToStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "payoutToBankId")
    private MstBank payoutToBankId;

    @JsonInclude(NON_NULL)
    @Column(name = "payoutPayType")
    private String payoutPayType;

    @JsonInclude(NON_NULL)
    @Column(name = "payoutAmount", columnDefinition = "decimal default 0", nullable = true)
    private double payoutAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "payoutChequeNo")
    private String payoutChequeNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "payoutBankId")
    private MstBank payoutBankId;

    @JsonInclude(NON_NULL)
    @Column(name = "payoutRemarks")
    private String payoutRemarks;
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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getPayoutId() {
        return payoutId;
    }

    public void setPayoutId(long payoutId) {
        this.payoutId = payoutId;
    }

    public MstUnit getPayoutUnitId() {
        return payoutUnitId;
    }

    public void setPayoutUnitId(MstUnit payoutUnitId) {
        this.payoutUnitId = payoutUnitId;
    }

    public MstStaff getPayoutStaffId() {
        return payoutStaffId;
    }

    public void setPayoutStaffId(MstStaff payoutStaffId) {
        this.payoutStaffId = payoutStaffId;
    }

    public String getPayoutServiceType() {
        return payoutServiceType;
    }

    public void setPayoutServiceType(String payoutServiceType) {
        this.payoutServiceType = payoutServiceType;
    }

    public MstCashCounter getPayoutFromCcId() {
        return payoutFromCcId;
    }

    public void setPayoutFromCcId(MstCashCounter payoutFromCcId) {
        this.payoutFromCcId = payoutFromCcId;
    }

    public MstStaff getPayoutFromStaffId() {
        return payoutFromStaffId;
    }

    public void setPayoutFromStaffId(MstStaff payoutFromStaffId) {
        this.payoutFromStaffId = payoutFromStaffId;
    }

    public MstCashCounter getPayoutToCcId() {
        return payoutToCcId;
    }

    public void setPayoutToCcId(MstCashCounter payoutToCcId) {
        this.payoutToCcId = payoutToCcId;
    }

    public MstStaff getPayoutToStaffId() {
        return payoutToStaffId;
    }

    public void setPayoutToStaffId(MstStaff payoutToStaffId) {
        this.payoutToStaffId = payoutToStaffId;
    }

    public MstBank getPayoutToBankId() {
        return payoutToBankId;
    }

    public void setPayoutToBankId(MstBank payoutToBankId) {
        this.payoutToBankId = payoutToBankId;
    }

    public String getPayoutPayType() {
        return payoutPayType;
    }

    public void setPayoutPayType(String payoutPayType) {
        this.payoutPayType = payoutPayType;
    }

    public double getPayoutAmount() {
        return payoutAmount;
    }

    public void setPayoutAmount(double payoutAmount) {
        this.payoutAmount = payoutAmount;
    }

    public String getPayoutChequeNo() {
        return payoutChequeNo;
    }

    public void setPayoutChequeNo(String payoutChequeNo) {
        this.payoutChequeNo = payoutChequeNo;
    }

    public MstBank getPayoutBankId() {
        return payoutBankId;
    }

    public void setPayoutBankId(MstBank payoutBankId) {
        this.payoutBankId = payoutBankId;
    }

    public String getPayoutRemarks() {
        return payoutRemarks;
    }

    public void setPayoutRemarks(String payoutRemarks) {
        this.payoutRemarks = payoutRemarks;
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

    public String getPayoutDateTime() {
        return payoutDateTime;
    }

    public void setPayoutDateTime(String payoutDateTime) {
        this.payoutDateTime = payoutDateTime;
    }
}