package com.cellbeans.hspa.tinvpharmacyreturnbillrecepit;

import com.cellbeans.hspa.mstbank.MstBank;
import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySale;
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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_pharmacy_bill_return_recepit")
public class PharmacyReturnBillRecepit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pbrrId", unique = true, nullable = true)
    private long pbrrId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pbrrPmId")
    private MstPaymentMode pbrrPmId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pbrrBankId")
    private MstBank pbrrBankId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pbrrPsId")
    private TinvPharmacySale pbrrPsId;

    @JsonInclude(NON_NULL)
    private String transcationId;

    @JsonInclude(NON_NULL)
    private double cash;

    @JsonInclude(NON_NULL)
    private double returnAmount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pbrrUnitId")
    private MstUnit pbrrUnitId;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
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

    public long getPbrrId() {
        return pbrrId;
    }

    public void setPbrrId(long pbrrId) {
        this.pbrrId = pbrrId;
    }

    public MstPaymentMode getPbrrPmId() {
        return pbrrPmId;
    }

    public void setPbrrPmId(MstPaymentMode pbrrPmId) {
        this.pbrrPmId = pbrrPmId;
    }

    public MstBank getPbrrBankId() {
        return pbrrBankId;
    }

    public void setPbrrBankId(MstBank pbrrBankId) {
        this.pbrrBankId = pbrrBankId;
    }

    public TinvPharmacySale getPbrrPsId() {
        return pbrrPsId;
    }

    public void setPbrrPsId(TinvPharmacySale pbrrPsId) {
        this.pbrrPsId = pbrrPsId;
    }

    public String getTranscationId() {
        return transcationId;
    }

    public void setTranscationId(String transcationId) {
        this.transcationId = transcationId;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public MstUnit getPbrrUnitId() {
        return pbrrUnitId;
    }

    public void setPbrrUnitId(MstUnit pbrrUnitId) {
        this.pbrrUnitId = pbrrUnitId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
