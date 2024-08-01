package com.cellbeans.hspa.tinvpurchasequotationitem;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invitemdispensingtype.InvItemDispensingType;
import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpurchasequotation.TinvPurchaseQuotation;
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
@Table(name = "tinv_purchase_quotation_item")
public class TinvPurchaseQuotationItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pqiId", unique = true, nullable = true)
    private long pqiId;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiName")
    private String pqiName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pqiPqId")
    private TinvPurchaseQuotation pqiPqId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pqiItemId")
    private InvItem pqiItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pqiIdtId")
    private InvItemDispensingType pqiIdtId;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiRate")
    private String pqiRate;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiTaxName")
    private String pqiTaxName;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiTaxValue")
    private String pqiTaxValue;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiQuantity")
    private String pqiQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiFreeQuantity")
    private String pqiFreeQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiConcessionPercentage")
    private String pqiConcessionPercentage;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pqiTaxId")
    private InvTax pqiTaxId;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiTaxAmount")
    private String pqiTaxAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiTotalAmount")
    private String pqiTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiItemSpecification")
    private String pqiItemSpecification;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiNetAmount")
    private String pqiNetAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pqiConcessionAmount")
    private String pqiConcessionAmount;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isActive = true;

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
    @JoinColumn(name = "pqiUnitId")
    private MstUnit pqiUnitId;

    public MstUnit getPqiUnitId() {
        return pqiUnitId;
    }

    public void setPqiUnitId(MstUnit pqiUnitId) {
        this.pqiUnitId = pqiUnitId;
    }

    public long getPqiId() {
        return pqiId;
    }

    public void setPqiId(int pqiId) {
        this.pqiId = pqiId;
    }

    public TinvPurchaseQuotation getPqiPqId() {
        return pqiPqId;
    }

    public void setPqiPqId(TinvPurchaseQuotation pqiPqId) {
        this.pqiPqId = pqiPqId;
    }

    public InvItem getPqiItemId() {
        return pqiItemId;
    }

    public void setPqiItemId(InvItem pqiItemId) {
        this.pqiItemId = pqiItemId;
    }

    public String getPqiRate() {
        return pqiRate;
    }

    public void setPqiRate(String pqiRate) {
        this.pqiRate = pqiRate;
    }

    public String getPqiQuantity() {
        return pqiQuantity;
    }

    public void setPqiQuantity(String pqiQuantity) {
        this.pqiQuantity = pqiQuantity;
    }

    public String getPqiConcessionPercentage() {
        return pqiConcessionPercentage;
    }

    public void setPqiConcessionPercentage(String pqiConcessionPercentage) {
        this.pqiConcessionPercentage = pqiConcessionPercentage;
    }

    public InvTax getPqiTaxId() {
        return pqiTaxId;
    }

    public void setPqiTaxId(InvTax pqiTaxId) {
        this.pqiTaxId = pqiTaxId;
    }

    public String getPqiTaxAmount() {
        return pqiTaxAmount;
    }

    public void setPqiTaxAmount(String pqiTaxAmount) {
        this.pqiTaxAmount = pqiTaxAmount;
    }

    public String getPqiItemSpecification() {
        return pqiItemSpecification;
    }

    public void setPqiItemSpecification(String pqiItemSpecification) {
        this.pqiItemSpecification = pqiItemSpecification;
    }

    public String getPqiNetAmount() {
        return pqiNetAmount;
    }

    public void setPqiNetAmount(String pqiNetAmount) {
        this.pqiNetAmount = pqiNetAmount;
    }

    public String getPqiConcessionAmount() {
        return pqiConcessionAmount;
    }

    public void setPqiConcessionAmount(String pqiConcessionAmount) {
        this.pqiConcessionAmount = pqiConcessionAmount;
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

    public String getPqiName() {
        return pqiName;
    }

    public void setPqiName(String pqiName) {
        this.pqiName = pqiName;
    }

    public String getPqiFreeQuantity() {
        return pqiFreeQuantity;
    }

    public void setPqiFreeQuantity(String pqiFreeQuantity) {
        this.pqiFreeQuantity = pqiFreeQuantity;
    }

    public String getPqiTotalAmount() {
        return pqiTotalAmount;
    }

    public void setPqiTotalAmount(String pqiTotalAmount) {
        this.pqiTotalAmount = pqiTotalAmount;
    }

    public String getPqiTaxName() {
        return pqiTaxName;
    }

    public void setPqiTaxName(String pqiTaxName) {
        this.pqiTaxName = pqiTaxName;
    }

    public String getPqiTaxValue() {
        return pqiTaxValue;
    }

    public void setPqiTaxValue(String pqiTaxValue) {
        this.pqiTaxValue = pqiTaxValue;
    }

}
