package com.cellbeans.hspa.tinvpurchaseitemenquiry;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invsupplier.InvSupplier;
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
@Table(name = "tinv_purchase_item_enquiry")
public class TinvPurchaseItemEnquiry implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pieId", unique = true, nullable = true)
    private long pieId;

    @JsonInclude(NON_NULL)
    @Column(name = "picName")
    private String picName;

    @JsonInclude(NON_NULL)
    @Column(name = "pieEnquiryNo")
    private String pieEnquiryNo;

    @JsonInclude(NON_NULL)
    @Column(name = "pieDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date pieDate;

    @Transient
    private long count;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pieSupplierId")
    private InvSupplier pieSupplierId;

    @JsonInclude(NON_NULL)
    @Column(name = "pieHeader")
    private String pieHeader;

    @JsonInclude(NON_NULL)
    @Column(name = "pieNotes")
    private String pieNotes;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pieStoreId")
    private InvStore pieStoreId;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pieUnitId")
    private MstUnit pieUnitId;

    @JsonInclude(NON_NULL)
    @Column(name = "pieIsApproved", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean pieIsApproved = false;

    @JsonInclude(NON_NULL)
    @Column(name = "pieIsRejected", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean pieIsRejected = false;

    @JsonInclude(NON_NULL)
    @Column(name = "peiRejectReason")
    private String peiRejectReason;

    public long getPieId() {
        return pieId;
    }

    public void setPieId(int pieId) {
        this.pieId = pieId;
    }

    public MstUnit getPieUnitId() {
        return pieUnitId;
    }

    public void setPieUnitId(MstUnit pieUnitId) {
        this.pieUnitId = pieUnitId;
    }

    public String getPieEnquiryNo() {
        return pieEnquiryNo;
    }

    public void setPieEnquiryNo(String pieEnquiryNo) {
        this.pieEnquiryNo = pieEnquiryNo;
    }

    public Date getPieDate() {
        return pieDate;
    }

    public void setPieDate(Date pieDate) {
        this.pieDate = pieDate;
    }

    public InvSupplier getPieSupplierId() {
        return pieSupplierId;
    }

    public void setPieSupplierId(InvSupplier pieSupplierId) {
        this.pieSupplierId = pieSupplierId;
    }

    public String getPieHeader() {
        return pieHeader;
    }

    public void setPieHeader(String pieHeader) {
        this.pieHeader = pieHeader;
    }

    public String getPieNotes() {
        return pieNotes;
    }

    public void setPieNotes(String pieNotes) {
        this.pieNotes = pieNotes;
    }

    public InvStore getPieStoreId() {
        return pieStoreId;
    }

    public void setPieStoreId(InvStore pieStoreId) {
        this.pieStoreId = pieStoreId;
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

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Boolean getPieIsApproved() {
        return pieIsApproved;
    }

    public void setPieIsApproved(Boolean pieIsApproved) {
        this.pieIsApproved = pieIsApproved;
    }

    public Boolean getPieIsRejected() {
        return pieIsRejected;
    }

    public void setPieIsRejected(Boolean pieIsRejected) {
        this.pieIsRejected = pieIsRejected;
    }

    public String getPeiRejectReason() {
        return peiRejectReason;
    }

    public void setPeiRejectReason(String peiRejectReason) {
        this.peiRejectReason = peiRejectReason;
    }
}
