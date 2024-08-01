package com.cellbeans.hspa.tinvpurchasegoodsreceivednote;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invsupplier.InvSupplier;
import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvpurchasepurchaseorder.TinvPurchasePurchaseOrder;
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
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_purchase_goods_received_note")
public class TinvPurchaseGoodsReceivedNote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Transient
    List<TinvOpeningBalanceItem> tinvOpeningBalanceItems;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pgrnId", unique = true, nullable = true)
    private long pgrnId;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnGrnNo")
    private String pgrnGrnNo;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnName")
    private String pgrnName;
    @Transient
    private long count;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrnStoreId")
    private InvStore pgrnStoreId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrnPmId")
    private MstPaymentMode pgrnPmId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrnSupplierId")
    private InvSupplier pgrnSupplierId;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnGateEntryNo")
    private String pgrnGateEntryNo;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnGrnDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date pgrnGrnDate;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnInvoiceDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date pgrnInvoiceDate;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnInvoiceNo")
    private String pgrnInvoiceNo;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnInvoicePath")
    private String pgrnInvoicePath;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnBarCodeNo")
    private String pgrnBarCodeNo;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrnPpoId")
    private TinvPurchasePurchaseOrder pgrnPpoId;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnIsDirectPurchase", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean pgrnIsDirectPurchase = false;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnTotalAmount")
    private String pgrnTotalAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnConcessionPercentage")
    private String pgrnConcessionPercentage;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnConcessionAmount")
    private String pgrnConcessionAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnRoundUpAmount")
    private String pgrnRoundUpAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnNetAmount")
    private String pgrnNetAmount;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrnStaffId")
    private MstStaff pgrnStaffId;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnTaxAmount")
    private String pgrnTaxAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "pgrnRemark")
    private String pgrnRemark;
    @JsonInclude(NON_NULL)
    @Column(name = "grnIsApprove", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean grnIsApprove = false;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isRejected", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isRejected = false;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnRejectReason")
    private String pgrnRejectReason;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrnStatus")
    private String pgrnStatus;

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
    @JoinColumn(name = "grnUnitId")
    private MstUnit grnUnitId;

    public List<TinvOpeningBalanceItem> getTinvOpeningBalanceItems() {
        return tinvOpeningBalanceItems;
    }

    public void setTinvOpeningBalanceItems(List<TinvOpeningBalanceItem> tinvOpeningBalanceItems) {
        this.tinvOpeningBalanceItems = tinvOpeningBalanceItems;
    }

    public MstUnit getGrnUnitId() {
        return grnUnitId;
    }

    public void setGrnUnitId(MstUnit grnUnitId) {
        this.grnUnitId = grnUnitId;
    }

    public long getPgrnId() {
        return pgrnId;
    }

    public void setPgrnId(int pgrnId) {
        this.pgrnId = pgrnId;
    }

    public String getPgrnGrnNo() {
        return pgrnGrnNo;
    }

    public void setPgrnGrnNo(String pgrnGrnNo) {
        this.pgrnGrnNo = pgrnGrnNo;
    }

    public InvStore getPgrnStoreId() {
        return pgrnStoreId;
    }

    public void setPgrnStoreId(InvStore pgrnStoreId) {
        this.pgrnStoreId = pgrnStoreId;
    }

    public MstPaymentMode getPgrnPmId() {
        return pgrnPmId;
    }

    public void setPgrnPmId(MstPaymentMode pgrnPmId) {
        this.pgrnPmId = pgrnPmId;
    }

    public InvSupplier getPgrnSupplierId() {
        return pgrnSupplierId;
    }

    public void setPgrnSupplierId(InvSupplier pgrnSupplierId) {
        this.pgrnSupplierId = pgrnSupplierId;
    }

    public String getPgrnGateEntryNo() {
        return pgrnGateEntryNo;
    }

    public void setPgrnGateEntryNo(String pgrnGateEntryNo) {
        this.pgrnGateEntryNo = pgrnGateEntryNo;
    }

    public Date getPgrnGrnDate() {
        return pgrnGrnDate;
    }

    public void setPgrnGrnDate(Date pgrnGrnDate) {
        this.pgrnGrnDate = pgrnGrnDate;
    }

    public Date getPgrnInvoiceDate() {
        return pgrnInvoiceDate;
    }

    public void setPgrnInvoiceDate(Date pgrnInvoiceDate) {
        this.pgrnInvoiceDate = pgrnInvoiceDate;
    }

    public String getPgrnInvoiceNo() {
        return pgrnInvoiceNo;
    }

    public void setPgrnInvoiceNo(String pgrnInvoiceNo) {
        this.pgrnInvoiceNo = pgrnInvoiceNo;
    }

    public String getPgrnInvoicePath() {
        return pgrnInvoicePath;
    }

    public void setPgrnInvoicePath(String pgrnInvoicePath) {
        this.pgrnInvoicePath = pgrnInvoicePath;
    }

    public String getPgrnBarCodeNo() {
        return pgrnBarCodeNo;
    }

    public void setPgrnBarCodeNo(String pgrnBarCodeNo) {
        this.pgrnBarCodeNo = pgrnBarCodeNo;
    }

    public TinvPurchasePurchaseOrder getPgrnPpoId() {
        return pgrnPpoId;
    }

    public void setPgrnPpoId(TinvPurchasePurchaseOrder pgrnPpoId) {
        this.pgrnPpoId = pgrnPpoId;
    }

    public boolean getPgrnIsDirectPurchase() {
        return pgrnIsDirectPurchase;
    }

    public void setPgrnIsDirectPurchase(boolean pgrnIsDirectPurchase) {
        this.pgrnIsDirectPurchase = pgrnIsDirectPurchase;
    }

    public String getPgrnTotalAmount() {
        return pgrnTotalAmount;
    }

    public void setPgrnTotalAmount(String pgrnTotalAmount) {
        this.pgrnTotalAmount = pgrnTotalAmount;
    }

    public String getPgrnConcessionPercentage() {
        return pgrnConcessionPercentage;
    }

    public void setPgrnConcessionPercentage(String pgrnConcessionPercentage) {
        this.pgrnConcessionPercentage = pgrnConcessionPercentage;
    }

    public String getPgrnConcessionAmount() {
        return pgrnConcessionAmount;
    }

    public void setPgrnConcessionAmount(String pgrnConcessionAmount) {
        this.pgrnConcessionAmount = pgrnConcessionAmount;
    }

    public String getPgrnRoundUpAmount() {
        return pgrnRoundUpAmount;
    }

    public void setPgrnRoundUpAmount(String pgrnRoundUpAmount) {
        this.pgrnRoundUpAmount = pgrnRoundUpAmount;
    }

    public String getPgrnNetAmount() {
        return pgrnNetAmount;
    }

    public void setPgrnNetAmount(String pgrnNetAmount) {
        this.pgrnNetAmount = pgrnNetAmount;
    }

    public MstStaff getPgrnStaffId() {
        return pgrnStaffId;
    }

    public void setPgrnStaffId(MstStaff pgrnStaffId) {
        this.pgrnStaffId = pgrnStaffId;
    }

    public String getPgrnTaxAmount() {
        return pgrnTaxAmount;
    }

    public void setPgrnTaxAmount(String pgrnTaxAmount) {
        this.pgrnTaxAmount = pgrnTaxAmount;
    }

    public String getPgrnRemark() {
        return pgrnRemark;
    }

    public void setPgrnRemark(String pgrnRemark) {
        this.pgrnRemark = pgrnRemark;
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

    public String getPgrnName() {
        return pgrnName;
    }

    public void setPgrnName(String pgrnName) {
        this.pgrnName = pgrnName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Boolean getGrnIsApprove() {
        return grnIsApprove;
    }

    public void setGrnIsApprove(Boolean grnIsApprove) {
        this.grnIsApprove = grnIsApprove;
    }

    public Boolean getRejected() {
        return isRejected;
    }

    public void setRejected(Boolean rejected) {
        isRejected = rejected;
    }

    public String getPgrnRejectReason() {
        return pgrnRejectReason;
    }

    public void setPgrnRejectReason(String pgrnRejectReason) {
        this.pgrnRejectReason = pgrnRejectReason;
    }

    public String getPgrnStatus() {
        return pgrnStatus;
    }

    public void setPgrnStatus(String pgrnStatus) {
        this.pgrnStatus = pgrnStatus;
    }
}
