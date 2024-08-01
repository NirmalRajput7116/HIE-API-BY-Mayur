package com.cellbeans.hspa.tbillbillSponsor;

import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombination;
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
@Table(name = "trn_bill_bill_sponsor")
public class TrnBillBillSponsor implements Serializable {

    private static final long serialVersionUID = 1L;
    //    Rohan Start
    @Transient
    long count;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bbsId", unique = true, nullable = true)
    private long bbsId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bbsBillId")
    private TBillBill bbsBillId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bbsSCId")
    private TrnSponsorCombination bbsSCId;
    @JsonInclude(NON_NULL)
    @Column(name = "bbsAdvancedAmount", columnDefinition = "decimal default 0", nullable = true)
    private double bbsAdvancedAmount; // calculated amount from Sub Total
    @JsonInclude(NON_NULL)
    @Column(name = "bbsAgstAmount", columnDefinition = "decimal default 0", nullable = true)
    private double bbsAgstAmount; // Company paid amount against AdvanceAmount
    @JsonInclude(NON_NULL)
    @Column(name = "bbsRemaingAmount", columnDefinition = "decimal default 0", nullable = true)
    private double bbsRemaingAmount; // company oustanding amount = advanceamount - agstamount
    @JsonInclude(NON_NULL)
    @Column(name = "bbsAmountTobeRetrn", columnDefinition = "decimal default 0", nullable = true)
    private double bbsAmountTobeRetrn; // hsopital will return amount to Compnay . .
    @JsonInclude(NON_NULL)
    @Column(name = "bbsAdvanceFinalAmount", columnDefinition = "decimal default 0", nullable = true)
    private double bbsAdvanceFinalAmount;
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
//    Rohan End

    public long getBbsId() {
        return bbsId;
    }

    public void setBbsId(long bbsId) {
        this.bbsId = bbsId;
    }

    public TBillBill getBbsBillId() {
        return bbsBillId;
    }

    public void setBbsBillId(TBillBill bbsBillId) {
        this.bbsBillId = bbsBillId;
    }

    public TrnSponsorCombination getBbsSCId() {
        return bbsSCId;
    }

    public void setBbsSCId(TrnSponsorCombination bbsSCId) {
        this.bbsSCId = bbsSCId;
    }

    public double getBbsAdvancedAmount() {
        return bbsAdvancedAmount;
    }

    public void setBbsAdvancedAmount(double bbsAdvancedAmount) {
        this.bbsAdvancedAmount = bbsAdvancedAmount;
    }

    public double getBbsAgstAmount() {
        return bbsAgstAmount;
    }

    public void setBbsAgstAmount(double bbsAgstAmount) {
        this.bbsAgstAmount = bbsAgstAmount;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public double getBbsRemaingAmount() {
        return bbsRemaingAmount;
    }

    public void setBbsRemaingAmount(double bbsRemaingAmount) {
        this.bbsRemaingAmount = bbsRemaingAmount;
    }

    public double getBbsAmountTobeRetrn() {
        return bbsAmountTobeRetrn;
    }

    public void setBbsAmountTobeRetrn(double bbsAmountTobeRetrn) {
        this.bbsAmountTobeRetrn = bbsAmountTobeRetrn;
    }

    public double getBbsAdvanceFinalAmount() {
        return bbsAdvanceFinalAmount;
    }

    public void setBbsAdvanceFinalAmount(double bbsAdvanceFinalAmount) {
        this.bbsAdvanceFinalAmount = bbsAdvanceFinalAmount;
    }

}