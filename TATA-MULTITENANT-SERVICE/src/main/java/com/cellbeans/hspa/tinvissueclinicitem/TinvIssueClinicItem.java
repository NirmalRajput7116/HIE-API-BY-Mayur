package com.cellbeans.hspa.tinvissueclinicitem;

import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvissueclinic.TinvIssueClinic;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvreceiveissue.TinvReceiveIssue;
import com.cellbeans.hspa.tinvstoreindent.TinvStoreIndent;
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
@Table(name = "tinv_issue_clinic_item")
public class TinvIssueClinicItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iciId", unique = true, nullable = true)
    private long iciId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "iciItemId")
    private TinvOpeningBalanceItem iciItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "iciIcId")
    private TinvIssueClinic iciIcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "iciRiId")
    private TinvReceiveIssue iciRiId;

    @JsonInclude(NON_NULL)
    @Column(name = "iciItemMrp")
    private double iciItemMrp;

    @JsonInclude(NON_NULL)
    @Column(name = "iciItemRate")
    private double iciItemRate;

    @JsonInclude(NON_NULL)
    @Column(name = "iciTotalAmount")
    private double iciTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "iciItemExpiryDate")
    private Date iciItemExpiryDate;

    @JsonInclude(NON_NULL)
    @Column(name = "iciItemBatchCode")
    private String iciItemBatchCode;

    @JsonInclude(NON_NULL)
    @Column(name = "iciIssueQuantity")
    private int iciIssueQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "iciIndentQuantity")
    private int iciIndentQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "iciReceiveQuantity")
    private int iciReceiveQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "iciReturnQuantity")
    private int iciReturnQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "iciReturnQuantityRemark")
    private String iciReturnQuantityRemark;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "iciSiId")
    private TinvStoreIndent iciSiId;

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
    @JoinColumn(name = "issueClinicUnitId")
    private MstUnit issueClinicUnitId;

    public TinvStoreIndent getIciSiId() {
        return iciSiId;
    }

    public void setIciSiId(TinvStoreIndent iciSiId) {
        this.iciSiId = iciSiId;
    }

    public MstUnit getIssueClinicUnitId() {
        return issueClinicUnitId;
    }

    public void setIssueClinicUnitId(MstUnit issueClinicUnitId) {
        this.issueClinicUnitId = issueClinicUnitId;
    }

    public long getIciId() {
        return iciId;
    }

    public void setIciId(int iciId) {
        this.iciId = iciId;
    }

    public double getIciItemMrp() {
        return iciItemMrp;
    }

    public void setIciItemMrp(double iciItemMrp) {
        this.iciItemMrp = iciItemMrp;
    }

    public double getIciItemRate() {
        return iciItemRate;
    }

    public void setIciItemRate(double iciItemRate) {
        this.iciItemRate = iciItemRate;
    }

    public double getIciTotalAmount() {
        return iciTotalAmount;
    }

    public void setIciTotalAmount(double iciTotalAmount) {
        this.iciTotalAmount = iciTotalAmount;
    }

    public Date getIciItemExpiryDate() {
        return iciItemExpiryDate;
    }

    public void setIciItemExpiryDate(Date iciItemExpiryDate) {
        this.iciItemExpiryDate = iciItemExpiryDate;
    }

    public int getIciIssueQuantity() {
        return iciIssueQuantity;
    }

    public void setIciIssueQuantity(int iciIssueQuantity) {
        this.iciIssueQuantity = iciIssueQuantity;
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

    public TinvOpeningBalanceItem getIciItemId() {
        return iciItemId;
    }

    public void setIciItemId(TinvOpeningBalanceItem iciItemId) {
        this.iciItemId = iciItemId;
    }

    public String getIciItemBatchCode() {
        return iciItemBatchCode;
    }

    public void setIciItemBatchCode(String iciItemBatchCode) {
        this.iciItemBatchCode = iciItemBatchCode;
    }

    public TinvIssueClinic getIciIcId() {
        return iciIcId;
    }

    public void setIciIcId(TinvIssueClinic iciIcId) {
        this.iciIcId = iciIcId;
    }

    public int getIciIndentQuantity() {
        return iciIndentQuantity;
    }

    public void setIciIndentQuantity(int iciIndentQuantity) {
        this.iciIndentQuantity = iciIndentQuantity;
    }

    public int getIciReceiveQuantity() {
        return iciReceiveQuantity;
    }

    public void setIciReceiveQuantity(int iciReceiveQuantity) {
        this.iciReceiveQuantity = iciReceiveQuantity;
    }

    public int getIciReturnQuantity() {
        return iciReturnQuantity;
    }

    public void setIciReturnQuantity(int iciReturnQuantity) {
        this.iciReturnQuantity = iciReturnQuantity;
    }

    public String getIciReturnQuantityRemark() {
        return iciReturnQuantityRemark;
    }

    public void setIciReturnQuantityRemark(String iciReturnQuantityRemark) {
        this.iciReturnQuantityRemark = iciReturnQuantityRemark;
    }

    public TinvReceiveIssue getIciRiId() {
        return iciRiId;
    }

    public void setIciRiId(TinvReceiveIssue iciRiId) {
        this.iciRiId = iciRiId;
    }

    @Override
    public String toString() {
        return "TinvIssueClinicItem{" + "iciId=" + iciId + ", iciItemId=" + iciItemId + ", iciIcId=" + iciIcId + ", iciRiId=" + iciRiId + ", iciItemMrp=" + iciItemMrp + ", iciItemRate=" + iciItemRate + ", iciTotalAmount=" + iciTotalAmount + ", iciItemExpiryDate=" + iciItemExpiryDate + ", iciItemBatchCode='" + iciItemBatchCode + '\'' + ", iciIssueQuantity=" + iciIssueQuantity + ", iciIndentQuantity=" + iciIndentQuantity + ", iciReceiveQuantity=" + iciReceiveQuantity + ", iciReturnQuantity=" + iciReturnQuantity + ", iciReturnQuantityRemark='" + iciReturnQuantityRemark + '\'' + ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", issueClinicUnitId=" + issueClinicUnitId + '}';
    }

}
