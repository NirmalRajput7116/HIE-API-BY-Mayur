package com.cellbeans.hspa.tinvscrapesale;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invsupplier.InvSupplier;
import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;
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
@Table(name = "tinv_scrape_sale")
public class TinvScrapeSale implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ssId", unique = true, nullable = true)
    private long ssId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ssStaffId")
    private MstStaff ssStaffId;
    @Transient
    private Long count;
    @JsonInclude(NON_NULL)
    @Column(name = "ssTotalAmount")
    private double ssTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "ssTotalTaxAmount")
    private double ssTotalTaxAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "ssTotalNetAmount")
    private double ssTotalNetAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "ssTotalConcessionAmount")
    private double ssTotalConcessionAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "ssRemark")
    private String ssRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "ssIsApprove", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ssIsApprove = false;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ssStoreId")
    private InvStore ssStoreId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ssSupplierId")
    private InvSupplier ssSupplierId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ssPaymentMode")
    private MstPaymentMode ssPaymentMode;

    @JsonInclude(NON_NULL)
    @Column(name = "ssReasone")
    private String ssReasone;

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
    @JoinColumn(name = "ssUnitId")
    private MstUnit ssUnitId;

    public MstUnit getSsUnitId() {
        return ssUnitId;
    }

    public void setSsUnitId(MstUnit ssUnitId) {
        this.ssUnitId = ssUnitId;
    }

    public long getSsId() {
        return ssId;
    }

    public void setSsId(int ssId) {
        this.ssId = ssId;
    }

    public MstStaff getSsStaffId() {
        return ssStaffId;
    }

    public void setSsStaffId(MstStaff ssStaffId) {
        this.ssStaffId = ssStaffId;
    }

    public double getSsTotalAmount() {
        return ssTotalAmount;
    }

    public void setSsTotalAmount(double ssTotalAmount) {
        this.ssTotalAmount = ssTotalAmount;
    }

    public double getSsTotalTaxAmount() {
        return ssTotalTaxAmount;
    }

    public void setSsTotalTaxAmount(double ssTotalTaxAmount) {
        this.ssTotalTaxAmount = ssTotalTaxAmount;
    }

    public double getSsTotalNetAmount() {
        return ssTotalNetAmount;
    }

    public void setSsTotalNetAmount(double ssTotalNetAmount) {
        this.ssTotalNetAmount = ssTotalNetAmount;
    }

    public double getSsTotalConcessionAmount() {
        return ssTotalConcessionAmount;
    }

    public void setSsTotalConcessionAmount(double ssTotalConcessionAmount) {
        this.ssTotalConcessionAmount = ssTotalConcessionAmount;
    }

    public String getSsRemark() {
        return ssRemark;
    }

    public void setSsRemark(String ssRemark) {
        this.ssRemark = ssRemark;
    }

    public boolean getSsIsApprove() {
        return ssIsApprove;
    }

    public void setSsIsApprove(boolean ssIsApprove) {
        this.ssIsApprove = ssIsApprove;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public InvStore getSsStoreId() {
        return ssStoreId;
    }

    public void setSsStoreId(InvStore ssStoreId) {
        this.ssStoreId = ssStoreId;
    }

    public InvSupplier getSsSupplierId() {
        return ssSupplierId;
    }

    public void setSsSupplierId(InvSupplier ssSupplierId) {
        this.ssSupplierId = ssSupplierId;
    }

    public MstPaymentMode getSsPaymentMode() {
        return ssPaymentMode;
    }

    public void setSsPaymentMode(MstPaymentMode ssPaymentMode) {
        this.ssPaymentMode = ssPaymentMode;
    }

    public String getSsReasone() {
        return ssReasone;
    }

    public void setSsReasone(String ssReasone) {
        this.ssReasone = ssReasone;
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
