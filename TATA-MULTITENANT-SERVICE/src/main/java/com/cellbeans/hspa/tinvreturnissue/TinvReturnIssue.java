package com.cellbeans.hspa.tinvreturnissue;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_return_issue")
public class TinvReturnIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reiId", unique = true, nullable = true)
    private long reiId;

    @JsonInclude(NON_NULL)
    @Column(name = "reiReturnNo")
    private String reiReturnNo;

    @JsonInclude(NON_NULL)
    @Column(name = "reiReturnDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date reiReturnDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "reiFromStoreId")
    private InvStore reiFromStoreId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "reiToStoreId")
    private InvStore reiToStoreId;

    @JsonInclude(NON_NULL)
    @Column(name = "reiTotalAmount")
    private double reiTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "reiTotalItems")
    private int reiTotalItems;

    @JsonInclude(NON_NULL)
    @Column(name = "reiRemark")
    private String reiRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "reiTotalMrp")
    private double reiTotalMrp;

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
    @JoinColumn(name = "reiUnitId")
    private MstUnit reiUnitId;

    @Transient
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getReiId() {
        return reiId;
    }

    public void setReiId(long reiId) {
        this.reiId = reiId;
    }

    public String getReiReturnNo() {
        return reiReturnNo;
    }

    public void setReiReturnNo(String reiReturnNo) {
        this.reiReturnNo = reiReturnNo;
    }

    public Date getReiReturnDate() {
        return reiReturnDate;
    }

    public void setReiReturnDate(Date reiReturnDate) {
        this.reiReturnDate = reiReturnDate;
    }

    public InvStore getReiFromStoreId() {
        return reiFromStoreId;
    }

    public void setReiFromStoreId(InvStore reiFromStoreId) {
        this.reiFromStoreId = reiFromStoreId;
    }

    public InvStore getReiToStoreId() {
        return reiToStoreId;
    }

    public void setReiToStoreId(InvStore reiToStoreId) {
        this.reiToStoreId = reiToStoreId;
    }

    public double getReiTotalAmount() {
        return reiTotalAmount;
    }

    public void setReiTotalAmount(double reiTotalAmount) {
        this.reiTotalAmount = reiTotalAmount;
    }

    public int getReiTotalItems() {
        return reiTotalItems;
    }

    public void setReiTotalItems(int reiTotalItems) {
        this.reiTotalItems = reiTotalItems;
    }

    public String getReiRemark() {
        return reiRemark;
    }

    public void setReiRemark(String reiRemark) {
        this.reiRemark = reiRemark;
    }

    public double getReiTotalMrp() {
        return reiTotalMrp;
    }

    public void setReiTotalMrp(double reiTotalMrp) {
        this.reiTotalMrp = reiTotalMrp;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        isDeleted = this.isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
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

    public MstUnit getReiUnitId() {
        return reiUnitId;
    }

    public void setReiUnitId(MstUnit reiUnitId) {
        this.reiUnitId = reiUnitId;
    }

}
