package com.cellbeans.hspa.tinvreturnissueitem;

import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvreceiveissue.TinvReceiveIssue;
import com.cellbeans.hspa.tinvreturnissue.TinvReturnIssue;
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
@Table(name = "tinv_return_issue_item")
public class TinvReturnIssueItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reiItId", unique = true, nullable = true)
    private long reiItId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "reiItReiId")
    private TinvReturnIssue reiItReiId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "reiItRiId")
    private TinvReceiveIssue reiItRiId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "iciItemId")
    private TinvOpeningBalanceItem reiItItemId;

    @JsonInclude(NON_NULL)
    @Column(name = "reiItReceviedQuantity")
    private int reiItReceviedQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "reiItReturnQuantity")
    private int reiItReturnQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "reiItReturnQuantityRemark")
    private String reiItReturnQuantityRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "reiItTotalAmount")
    private double reiItTotalAmount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "reiItUnitId")
    private MstUnit reiItUnitId;

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

    public double getReiItTotalAmount() {
        return reiItTotalAmount;
    }

    public void setReiItTotalAmount(double reiItTotalAmount) {
        this.reiItTotalAmount = reiItTotalAmount;
    }

    public int getReiItReceviedQuantity() {
        return reiItReceviedQuantity;
    }

    public void setReiItReceviedQuantity(int reiItReceviedQuantity) {
        this.reiItReceviedQuantity = reiItReceviedQuantity;
    }

    public long getReiItId() {
        return reiItId;
    }

    public void setReiItId(long reiItId) {
        this.reiItId = reiItId;
    }

    public TinvReturnIssue getReiItReiId() {
        return reiItReiId;
    }

    public void setReiItReiId(TinvReturnIssue reiItReiId) {
        this.reiItReiId = reiItReiId;
    }

    public TinvReceiveIssue getReiItRiId() {
        return reiItRiId;
    }

    public void setReiItRiId(TinvReceiveIssue reiItRiId) {
        this.reiItRiId = reiItRiId;
    }

    public TinvOpeningBalanceItem getReiItItemId() {
        return reiItItemId;
    }

    public void setReiItItemId(TinvOpeningBalanceItem reiItItemId) {
        this.reiItItemId = reiItItemId;
    }

    public int getReiItReturnQuantity() {
        return reiItReturnQuantity;
    }

    public void setReiItReturnQuantity(int reiItReturnQuantity) {
        this.reiItReturnQuantity = reiItReturnQuantity;
    }

    public String getReiItReturnQuantityRemark() {
        return reiItReturnQuantityRemark;
    }

    public void setReiItReturnQuantityRemark(String reiItReturnQuantityRemark) {
        this.reiItReturnQuantityRemark = reiItReturnQuantityRemark;
    }

    public MstUnit getReiItUnitId() {
        return reiItUnitId;
    }

    public void setReiItUnitId(MstUnit reiItUnitId) {
        this.reiItUnitId = reiItUnitId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

}
