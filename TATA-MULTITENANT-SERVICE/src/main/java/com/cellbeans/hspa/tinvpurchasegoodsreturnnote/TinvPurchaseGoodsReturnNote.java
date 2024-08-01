package com.cellbeans.hspa.tinvpurchasegoodsreturnnote;

import com.cellbeans.hspa.invsupplier.InvSupplier;
import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySale;
import com.cellbeans.hspa.tinvpurchasegoodsreceivednote.TinvPurchaseGoodsReceivedNote;
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
@Table(name = "tinv_purchase_goods_return_note")
public class TinvPurchaseGoodsReturnNote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pgrnId", unique = true, nullable = true)
    private long pgrnId;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnGoodsReturnNo")
    private String pgrnGoodsReturnNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrnPgrnId")
    private TinvPurchaseGoodsReceivedNote pgrnPgrnId;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnRemark")
    private String pgrnRemark;

    @Transient
    private long count;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date pgrnDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrnSupplierId")
    private InvSupplier pgrnSupplierId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrnPmId")
    private MstPaymentMode pgrnPmId;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnConcessionAmount")
    private double pgrnConcessionAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnTotalAmount")
    private double pgrnTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnTotalTaxAmount")
    private double pgrnTotalTaxAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnNetAmount")
    private double pgrnNetAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnIsExpieredItem", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean pgrnIsExpieredItem = false;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnIsSalesItem", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean pgrnIsSalesItem = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrnPsId")
    private TinvPharmacySale pgrnPsId;

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
    @JoinColumn(name = "pgrnUnitId")
    private MstUnit pgrnUnitId;

    public MstUnit getPgrnUnitId() {
        return pgrnUnitId;
    }

    public void setPgrnUnitId(MstUnit pgrnUnitId) {
        this.pgrnUnitId = pgrnUnitId;
    }

    public long getPgrnId() {
        return pgrnId;
    }

    public void setPgrnId(int pgrnId) {
        this.pgrnId = pgrnId;
    }

    public String getPgrnGoodsReturnNo() {
        return pgrnGoodsReturnNo;
    }

    public void setPgrnGoodsReturnNo(String pgrnGoodsReturnNo) {
        this.pgrnGoodsReturnNo = pgrnGoodsReturnNo;
    }

    public TinvPurchaseGoodsReceivedNote getPgrnPgrnId() {
        return pgrnPgrnId;
    }

    public void setPgrnPgrnId(TinvPurchaseGoodsReceivedNote pgrnPgrnId) {
        this.pgrnPgrnId = pgrnPgrnId;
    }

    public String getPgrnRemark() {
        return pgrnRemark;
    }

    public void setPgrnRemark(String pgrnRemark) {
        this.pgrnRemark = pgrnRemark;
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

    public Date getPgrnDate() {
        return pgrnDate;
    }

    public void setPgrnDate(Date pgrnDate) {
        this.pgrnDate = pgrnDate;
    }

    public InvSupplier getPgrnSupplierId() {
        return pgrnSupplierId;
    }

    public void setPgrnSupplierId(InvSupplier pgrnSupplierId) {
        this.pgrnSupplierId = pgrnSupplierId;
    }

    public MstPaymentMode getPgrnPmId() {
        return pgrnPmId;
    }

    public void setPgrnPmId(MstPaymentMode pgrnPmId) {
        this.pgrnPmId = pgrnPmId;
    }

    public double getPgrnTotalAmount() {
        return pgrnTotalAmount;
    }

    public void setPgrnTotalAmount(double pgrnTotalAmount) {
        this.pgrnTotalAmount = pgrnTotalAmount;
    }

    public double getPgrnTotalTaxAmount() {
        return pgrnTotalTaxAmount;
    }

    public void setPgrnTotalTaxAmount(double pgrnTotalTaxAmount) {
        this.pgrnTotalTaxAmount = pgrnTotalTaxAmount;
    }

    public double getPgrnNetAmount() {
        return pgrnNetAmount;
    }

    public void setPgrnNetAmount(double pgrnNetAmount) {
        this.pgrnNetAmount = pgrnNetAmount;
    }

    public boolean getPgrnIsExpieredItem() {
        return pgrnIsExpieredItem;
    }

    public void setPgrnIsExpieredItem(boolean pgrnIsExpieredItem) {
        this.pgrnIsExpieredItem = pgrnIsExpieredItem;
    }

    public boolean getPgrnIsSalesItem() {
        return pgrnIsSalesItem;
    }

    public void setPgrnIsSalesItem(boolean pgrnIsSalesItem) {
        this.pgrnIsSalesItem = pgrnIsSalesItem;
    }

    public TinvPharmacySale getPgrnPsId() {
        return pgrnPsId;
    }

    public void setPgrnPsId(TinvPharmacySale pgrnPsId) {
        this.pgrnPsId = pgrnPsId;
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

    public double getPgrnConcessionAmount() {
        return pgrnConcessionAmount;
    }

    public void setPgrnConcessionAmount(double pgrnConcessionAmount) {
        this.pgrnConcessionAmount = pgrnConcessionAmount;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}
