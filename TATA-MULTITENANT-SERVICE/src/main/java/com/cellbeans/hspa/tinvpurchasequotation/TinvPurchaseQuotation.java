package com.cellbeans.hspa.tinvpurchasequotation;

import com.cellbeans.hspa.invitemstoragetype.InvItemStorageType;
import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invsupplier.InvSupplier;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpurchaseitemenquiry.TinvPurchaseItemEnquiry;
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
@Table(name = "tinv_purchase_quotation")
public class TinvPurchaseQuotation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pqId", unique = true, nullable = true)
    private long pqId;

    @JsonInclude(NON_NULL)
    @Column(name = "pqName")
    private String pqName;

    @Transient
    private long count;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pqSupplierId")
    private InvSupplier pqSupplierId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pqStoreId")
    private InvStore pqStoreId;

    @JsonInclude(NON_NULL)
    @Column(name = "pqTaxAmount")
    private String pqTaxAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pqQuatationRefNo")
    private String pqQuatationRefNo;

    @JsonInclude(NON_NULL)
    @Column(name = "pqIsApprove", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean pqIsApprove = false;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "pqQuatationFile")
    private byte[] pqQuatationFile;

    @JsonInclude(NON_NULL)
    @Column(name = "pqQuatationSentBy")
    private String pqQuatationSentBy;

    @JsonInclude(NON_NULL)
    @Column(name = "pqMobileNo")
    private String pqMobileNo;

    @JsonInclude(NON_NULL)
    @Column(name = "pqEmailId")
    private String pqEmailId;

    @JsonInclude(NON_NULL)
    @Column(name = "pqReference")
    private String pqReference;

    @JsonInclude(NON_NULL)
    @Column(name = "pqTotalAmount")
    private String pqTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pqTotalConcession")
    private String pqTotalConcession;

    @JsonInclude(NON_NULL)
    @Column(name = "pqTotalNetAmount")
    private String pqTotalNetAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pqOther")
    private String pqOther;

    @JsonInclude(NON_NULL)
    @Column(name = "pqQuotationRefNo")
    private String pqQuotationRefNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pqPieId")
    private TinvPurchaseItemEnquiry pqPieId;

    @JsonInclude(NON_NULL)
    @Column(name = "pqDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date pqDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemIstId")
    private InvItemStorageType itemIstId;

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
    @JoinColumn(name = "pqUnitId")
    private MstUnit pqUnitId;

    public MstUnit getPqUnitId() {
        return pqUnitId;
    }

    public void setPqUnitId(MstUnit pqUnitId) {
        this.pqUnitId = pqUnitId;
    }

    public long getPqId() {
        return pqId;
    }

    public void setPqId(int pqId) {
        this.pqId = pqId;
    }

    public String getPqName() {
        return pqName;
    }

    public void setPqName(String pqName) {
        this.pqName = pqName;
    }

    public InvSupplier getPqSupplierId() {
        return pqSupplierId;
    }

    public void setPqSupplierId(InvSupplier pqSupplierId) {
        this.pqSupplierId = pqSupplierId;
    }

    public String getPqTaxAmount() {
        return pqTaxAmount;
    }

    public void setPqTaxAmount(String pqTaxAmount) {
        this.pqTaxAmount = pqTaxAmount;
    }

    public String getPqTotalAmount() {
        return pqTotalAmount;
    }

    public void setPqTotalAmount(String pqTotalAmount) {
        this.pqTotalAmount = pqTotalAmount;
    }

    public String getPqTotalConcession() {
        return pqTotalConcession;
    }

    public void setPqTotalConcession(String pqTotalConcession) {
        this.pqTotalConcession = pqTotalConcession;
    }

    public String getPqTotalNetAmount() {
        return pqTotalNetAmount;
    }

    public void setPqTotalNetAmount(String pqTotalNetAmount) {
        this.pqTotalNetAmount = pqTotalNetAmount;
    }

    public String getPqOther() {
        return pqOther;
    }

    public void setPqOther(String pqOther) {
        this.pqOther = pqOther;
    }

    public TinvPurchaseItemEnquiry getPqPieId() {
        return pqPieId;
    }

    public void setPqPieId(TinvPurchaseItemEnquiry pqPieId) {
        this.pqPieId = pqPieId;
    }

    public Date getPqDate() {
        return pqDate;
    }

    public void setPqDate(Date pqDate) {
        this.pqDate = pqDate;
    }

    public InvItemStorageType getItemIstId() {
        return itemIstId;
    }

    public void setItemIstId(InvItemStorageType itemIstId) {
        this.itemIstId = itemIstId;
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

    public String getPqQuatationRefNo() {
        return pqQuatationRefNo;
    }

    public void setPqQuatationRefNo(String pqQuatationRefNo) {
        this.pqQuatationRefNo = pqQuatationRefNo;
    }

    public byte[] getPqQuatationFile() {
        return pqQuatationFile;
    }

    public void setPqQuatationFile(byte[] pqQuatationFile) {
        this.pqQuatationFile = pqQuatationFile;
    }

    public String getPqQuatationSentBy() {
        return pqQuatationSentBy;
    }

    public void setPqQuatationSentBy(String pqQuatationSentBy) {
        this.pqQuatationSentBy = pqQuatationSentBy;
    }

    public String getPqMobileNo() {
        return pqMobileNo;
    }

    public void setPqMobileNo(String pqMobileNo) {
        this.pqMobileNo = pqMobileNo;
    }

    public String getPqEmailId() {
        return pqEmailId;
    }

    public void setPqEmailId(String pqEmailId) {
        this.pqEmailId = pqEmailId;
    }

    public String getPqReference() {
        return pqReference;
    }

    public void setPqReference(String pqReference) {
        this.pqReference = pqReference;
    }

    public InvStore getPqStoreId() {
        return pqStoreId;
    }

    public void setPqStoreId(InvStore pqStoreId) {
        this.pqStoreId = pqStoreId;
    }

    public String getPqQuotationRefNo() {
        return pqQuotationRefNo;
    }

    public void setPqQuotationRefNo(String pqQuotationRefNo) {
        this.pqQuotationRefNo = pqQuotationRefNo;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Boolean getPqIsApprove() {
        return pqIsApprove;
    }

    public void setPqIsApprove(Boolean pqIsApprove) {
        this.pqIsApprove = pqIsApprove;
    }

}
