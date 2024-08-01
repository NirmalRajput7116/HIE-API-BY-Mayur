package com.cellbeans.hspa.tinvreceiveissue;

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
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_receive_issue")
public class TinvReceiveIssue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "riId", unique = true, nullable = true)
    private long riId;

    @JsonInclude(NON_NULL)
    @Column(name = "riIssueDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date riIssueDate;

    @JsonInclude(NON_NULL)
    @Column(name = "riIssueNumber")
    private String riIssueNumber;
    @Transient
    private Long count;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "riRecivingStoreId")
    private InvStore riRecivingStoreId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "riFromStoreId")
    private InvStore riFromStoreId;

    @JsonInclude(NON_NULL)
    @Column(name = "riTotalAmount")
    private double riTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "riTotalItems")
    private int riTotalItems;

    @JsonInclude(NON_NULL)
    @Column(name = "riRemark")
    private String riRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "riTotalMrp")
    private double riTotalMrp;

    @JsonInclude(NON_NULL)
    @Column(name = "riNo")
    private String riNo;

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
    @JoinColumn(name = "riUnitId")
    private MstUnit riUnitId;

    public MstUnit getRiUnitId() {
        return riUnitId;
    }

    public void setRiUnitId(MstUnit riUnitId) {
        this.riUnitId = riUnitId;
    }

    public long getRiId() {
        return riId;
    }

    public void setRiId(int riId) {
        this.riId = riId;
    }

    public Date getRiIssueDate() {
        return riIssueDate;
    }

    public void setRiIssueDate(Date riIssueDate) {
        this.riIssueDate = riIssueDate;
    }

    public String getRiIssueNumber() {
        return riIssueNumber;
    }

    public void setRiIssueNumber(String riIssueNumber) {
        this.riIssueNumber = riIssueNumber;
    }

    public double getRiTotalAmount() {
        return riTotalAmount;
    }

    public void setRiTotalAmount(double riTotalAmount) {
        this.riTotalAmount = riTotalAmount;
    }

    public int getRiTotalItems() {
        return riTotalItems;
    }

    public void setRiTotalItems(int riTotalItems) {
        this.riTotalItems = riTotalItems;
    }

    public String getRiRemark() {
        return riRemark;
    }

    public void setRiRemark(String riRemark) {
        this.riRemark = riRemark;
    }

    public double getRiTotalMrp() {
        return riTotalMrp;
    }

    public void setRiTotalMrp(double riTotalMrp) {
        this.riTotalMrp = riTotalMrp;
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

    public InvStore getRiRecivingStoreId() {
        return riRecivingStoreId;
    }

    public void setRiRecivingStoreId(InvStore riRecivingStoreId) {
        this.riRecivingStoreId = riRecivingStoreId;
    }

    public InvStore getRiFromStoreId() {
        return riFromStoreId;
    }

    public void setRiFromStoreId(InvStore riFromStoreId) {
        this.riFromStoreId = riFromStoreId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getRiNo() {
        return riNo;
    }

    public void setRiNo(String riNo) {
        this.riNo = riNo;
    }
}
