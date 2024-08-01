package com.cellbeans.hspa.tinvstoreindent;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvstoreindentitem.TinvStoreIndentItem;
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
@Table(name = "tinv_store_indent")
public class TinvStoreIndent implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonInclude(NON_NULL)
    @Transient
    List<TinvStoreIndentItem> tinvStoreIndentItems;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "siId", unique = true, nullable = true)
    private long siId;
    @JsonInclude(NON_NULL)
    @Column(name = "siTotalAmount")
    private double siTotalAmount;
    @Transient
    private long count;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "siFromStoreId")
    private InvStore siFromStoreId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "siToStoreId")
    private InvStore siToStoreId;
    @JsonInclude(NON_NULL)
    @Column(name = "siDueDate")
    private Date siDueDate;
    @JsonInclude(NON_NULL)
    @Column(name = "siIndentDate")
    private Date siIndentDate;
    @JsonInclude(NON_NULL)
    @Column(name = "siRemark")
    private String siRemark;
    @JsonInclude(NON_NULL)
    @Column(name = "siIndentNo")
    private String siIndentNo;
    @JsonInclude(NON_NULL)
    @Column(name = "siFreezeIndent", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean siFreezeIndent = false;
    @JsonInclude(NON_NULL)
    @Column(name = "siIndentApprove", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean siIndentApprove = false;
    @JsonInclude(NON_NULL)
    @Column(name = "siUrgencyFlag", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean siUrgencyFlag = false;
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
    @JoinColumn(name = "storeIndentUnitId")
    private MstUnit storeIndentUnitId;
    @JsonInclude(NON_NULL)
    @Column(name = "persionSiIndentDate")
    private Date persionSiIndentDate;
    @JsonInclude(NON_NULL)
    @Column(name = "persionSiDueDate")
    private Date persionSiDueDate;
    @JsonInclude(NON_NULL)
    @Column(name = "siIsCancle", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean siIsCancle = false;
    @JsonInclude(NON_NULL)
    @Column(name = "siCancleDate")
    private Date siCancleDate;

    public Boolean getSiUrgencyFlag() {
        return siUrgencyFlag;
    }

    public void setSiUrgencyFlag(Boolean siUrgencyFlag) {
        this.siUrgencyFlag = siUrgencyFlag;
    }

    public Date getPersionSiIndentDate() {
        return persionSiIndentDate;
    }

    public void setPersionSiIndentDate(Date persionSiIndentDate) {
        this.persionSiIndentDate = persionSiIndentDate;
    }

    public Date getPersionSiDueDate() {
        return persionSiDueDate;
    }

    public void setPersionSiDueDate(Date persionSiDueDate) {
        this.persionSiDueDate = persionSiDueDate;
    }

    public List<TinvStoreIndentItem> getTinvStoreIndentItems() {
        return tinvStoreIndentItems;
    }

    public void setTinvStoreIndentItems(List<TinvStoreIndentItem> tinvStoreIndentItems) {
        this.tinvStoreIndentItems = tinvStoreIndentItems;
    }

    public MstUnit getStoreIndentUnitId() {
        return storeIndentUnitId;
    }

    public void setStoreIndentUnitId(MstUnit storeIndentUnitId) {
        this.storeIndentUnitId = storeIndentUnitId;
    }

    public long getSiId() {
        return siId;
    }

    public void setSiId(int siId) {
        this.siId = siId;
    }

    public double getSiTotalAmount() {
        return siTotalAmount;
    }

    public void setSiTotalAmount(double siTotalAmount) {
        this.siTotalAmount = siTotalAmount;
    }

    public InvStore getSiFromStoreId() {
        return siFromStoreId;
    }

    public void setSiFromStoreId(InvStore siFromStoreId) {
        this.siFromStoreId = siFromStoreId;
    }

    public InvStore getSiToStoreId() {
        return siToStoreId;
    }

    public void setSiToStoreId(InvStore siToStoreId) {
        this.siToStoreId = siToStoreId;
    }

    public Date getSiDueDate() {
        return siDueDate;
    }

    public void setSiDueDate(Date siDueDate) {
        this.siDueDate = siDueDate;
    }

    public String getSiRemark() {
        return siRemark;
    }

    public void setSiRemark(String siRemark) {
        this.siRemark = siRemark;
    }

    public String getSiIndentNo() {
        return siIndentNo;
    }

    public void setSiIndentNo(String siIndentNo) {
        this.siIndentNo = siIndentNo;
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

    public Boolean getSiFreezeIndent() {
        return siFreezeIndent;
    }

    public void setSiFreezeIndent(Boolean siFreezeIndent) {
        this.siFreezeIndent = siFreezeIndent;
    }

    public Boolean getSiIndentApprove() {
        return siIndentApprove;
    }

    public void setSiIndentApprove(Boolean siIndentApprove) {
        this.siIndentApprove = siIndentApprove;
    }

    public Date getSiIndentDate() {
        return siIndentDate;
    }

    public void setSiIndentDate(Date siIndentDate) {
        this.siIndentDate = siIndentDate;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Boolean getSiIsCancle() {
        return siIsCancle;
    }

    public void setSiIsCancle(Boolean siIsCancle) {
        this.siIsCancle = siIsCancle;
    }

    public Date getSiCancleDate() {
        return siCancleDate;
    }

    public void setSiCancleDate(Date siCancleDate) {
        this.siCancleDate = siCancleDate;
    }
}
