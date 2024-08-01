package com.cellbeans.hspa.tinvpurchasepurchaseorder;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invsupplier.InvSupplier;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpurchasepurchaseorderitem.TinvPurchasePurchaseOrderItem;
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
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_purchase_purchase_order")
public class TinvPurchasePurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Transient
    List<TinvPurchasePurchaseOrderItem> tinvPurchasePurchaseOrderItems;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppoId", unique = true, nullable = true)
    private long ppoId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppoPqId")
    private TinvPurchaseQuotation ppoPqId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppoStoreId")
    private InvStore ppoStoreId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppoSupplierId")
    private InvSupplier ppoSupplierId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppoDepartmentId")
    private MstDepartment ppoDepartmentId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppoStaffId")
    private MstStaff ppoStaffId;
    @Transient
    private long count;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoName")
    private String ppoName;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoTotalAmount")
    private String ppoTotalAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoConcessionPercentage")
    private String ppoConcessionPercentage;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoConcessionAmount")
    private String ppoConcessionAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoNetAmount")
    private String ppoNetAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoTaxPercentage")
    private String ppoTaxPercentage;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoNumberItem")
    private String ppoNumberItem;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoQuoattionNo")
    private String ppoQuoattionNo;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoTaxAmount")
    private String ppoTaxAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoRemark")
    private String ppoRemark;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoGuaranteeWarranty")
    private String ppoGuaranteeWarranty;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppoPmId")
    private MstPaymentMode ppoPmId;
    @JsonInclude(NON_NULL)
    @Column(name = "ppoIsPoApprove", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ppoIsPoApprove = false;
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
    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppoUnitId")
    private MstUnit ppoUnitId;

    @JsonIgnore
    @Column(name = "isRejected", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isRejected = false;

    @JsonInclude(NON_NULL)
    @Column(name = "poRejectReason")
    private String poRejectReason;

    @JsonInclude(NON_NULL)
    @Column(name = "ppoNo")
    private String ppoNo;

    @JsonInclude(NON_NULL)
    @Column(name = "ppoType")
    private String ppoType;

    public List<TinvPurchasePurchaseOrderItem> getTinvPurchasePurchaseOrderItems() {
        return tinvPurchasePurchaseOrderItems;
    }

    public void setTinvPurchasePurchaseOrderItems(List<TinvPurchasePurchaseOrderItem> tinvPurchasePurchaseOrderItems) {
        this.tinvPurchasePurchaseOrderItems = tinvPurchasePurchaseOrderItems;
    }

    public MstUnit getPpoUnitId() {
        return ppoUnitId;
    }

    public void setPpoUnitId(MstUnit ppoUnitId) {
        this.ppoUnitId = ppoUnitId;
    }

    public TinvPurchaseQuotation getPpoPqId() {
        return ppoPqId;
    }

    public void setPpoPqId(TinvPurchaseQuotation ppoPqId) {
        this.ppoPqId = ppoPqId;
    }

    public String getPpoTotalAmount() {
        return ppoTotalAmount;
    }

    public void setPpoTotalAmount(String ppoTotalAmount) {
        this.ppoTotalAmount = ppoTotalAmount;
    }

    public String getPpoConcessionPercentage() {
        return ppoConcessionPercentage;
    }

    public void setPpoConcessionPercentage(String ppoConcessionPercentage) {
        this.ppoConcessionPercentage = ppoConcessionPercentage;
    }

    public String getPpoConcessionAmount() {
        return ppoConcessionAmount;
    }

    public void setPpoConcessionAmount(String ppoConcessionAmount) {
        this.ppoConcessionAmount = ppoConcessionAmount;
    }

    public String getPpoNetAmount() {
        return ppoNetAmount;
    }

    public void setPpoNetAmount(String ppoNetAmount) {
        this.ppoNetAmount = ppoNetAmount;
    }

    public String getPpoTaxPercentage() {
        return ppoTaxPercentage;
    }

    public void setPpoTaxPercentage(String ppoTaxPercentage) {
        this.ppoTaxPercentage = ppoTaxPercentage;
    }

    public String getPpoTaxAmount() {
        return ppoTaxAmount;
    }

    public void setPpoTaxAmount(String ppoTaxAmount) {
        this.ppoTaxAmount = ppoTaxAmount;
    }

    public String getPpoRemark() {
        return ppoRemark;
    }

    public void setPpoRemark(String ppoRemark) {
        this.ppoRemark = ppoRemark;
    }

    public String getPpoGuaranteeWarranty() {
        return ppoGuaranteeWarranty;
    }

    public void setPpoGuaranteeWarranty(String ppoGuaranteeWarranty) {
        this.ppoGuaranteeWarranty = ppoGuaranteeWarranty;
    }

    public MstPaymentMode getPpoPmId() {
        return ppoPmId;
    }

    public void setPpoPmId(MstPaymentMode ppoPmId) {
        this.ppoPmId = ppoPmId;
    }

    public boolean getPpoIsPoApprove() {
        return ppoIsPoApprove;
    }

    public void setPpoIsPoApprove(Boolean ppoIsPoApprove) {
        this.ppoIsPoApprove = ppoIsPoApprove;
    }

    public void setPpoIsPoApprove(boolean ppoIsPoApprove) {
        this.ppoIsPoApprove = ppoIsPoApprove;
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

    public String getPpoName() {
        return ppoName;
    }

    public void setPpoName(String ppoName) {
        this.ppoName = ppoName;
    }

    public InvStore getPpoStoreId() {
        return ppoStoreId;
    }

    public void setPpoStoreId(InvStore ppoStoreId) {
        this.ppoStoreId = ppoStoreId;
    }

    public InvSupplier getPpoSupplierId() {
        return ppoSupplierId;
    }

    public void setPpoSupplierId(InvSupplier ppoSupplierId) {
        this.ppoSupplierId = ppoSupplierId;
    }

    public long getPpoId() {
        return ppoId;
    }

    public void setPpoId(long ppoId) {
        this.ppoId = ppoId;
    }

    public String getPpoNumberItem() {
        return ppoNumberItem;
    }

    public void setPpoNumberItem(String ppoNumberItem) {
        this.ppoNumberItem = ppoNumberItem;
    }

    public String getPpoQuoattionNo() {
        return ppoQuoattionNo;
    }

    public void setPpoQuoattionNo(String ppoQuoattionNo) {
        this.ppoQuoattionNo = ppoQuoattionNo;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public MstDepartment getPpoDepartmentId() {
        return ppoDepartmentId;
    }

    public void setPpoDepartmentId(MstDepartment ppoDepartmentId) {
        this.ppoDepartmentId = ppoDepartmentId;
    }

    public MstStaff getPpoStaffId() {
        return ppoStaffId;
    }

    public void setPpoStaffId(MstStaff ppoStaffId) {
        this.ppoStaffId = ppoStaffId;
    }

    public Boolean getRejected() {
        return isRejected;
    }

    public void setRejected(Boolean rejected) {
        isRejected = rejected;
    }

    public String getPoRejectReason() {
        return poRejectReason;
    }

    public void setPoRejectReason(String poRejectReason) {
        this.poRejectReason = poRejectReason;
    }

    public String getPpoNo() {
        return ppoNo;
    }

    public void setPpoNo(String ppoNo) {
        this.ppoNo = ppoNo;
    }

    public String getPpoType() {
        return ppoType;
    }

    public void setPpoType(String ppoType) {
        this.ppoType = ppoType;
    }
}


