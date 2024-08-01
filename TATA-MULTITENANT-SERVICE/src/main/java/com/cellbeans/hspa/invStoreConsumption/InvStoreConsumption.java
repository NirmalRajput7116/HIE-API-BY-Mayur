package com.cellbeans.hspa.invStoreConsumption;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "inv_store_consumption")
public class InvStoreConsumption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scId", unique = true, nullable = true)
    private long scId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "scItemName")
    private String scItemName;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "scQty")
    private double scQty;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "scRemark")
    private String scRemark;

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

    //	    @JsonIgnore
//	    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scStoreId")
    private InvStore scStoreId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scObiItemId")
    private TinvOpeningBalanceItem scObiItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scStaffId")
    private MstStaff scStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scUnitId")
    private MstUnit scUnitId;

    public long getScId() {
        return scId;
    }

    public void setScId(long scId) {
        this.scId = scId;
    }

    public String getScItemName() {
        return scItemName;
    }

    public void setScItemName(String scItemName) {
        this.scItemName = scItemName;
    }

    public double getScQty() {
        return scQty;
    }

    public void setScQty(double scQty) {
        this.scQty = scQty;
    }

    public String getScRemark() {
        return scRemark;
    }

    public void setScRemark(String scRemark) {
        this.scRemark = scRemark;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
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

    public InvStore getScStoreId() {
        return scStoreId;
    }

    public void setScStoreId(InvStore scStoreId) {
        this.scStoreId = scStoreId;
    }

    public TinvOpeningBalanceItem getScObiItemId() {
        return scObiItemId;
    }

    public void setScObiItemId(TinvOpeningBalanceItem scObiItemId) {
        this.scObiItemId = scObiItemId;
    }

    public MstStaff getScStaffId() {
        return scStaffId;
    }

    public void setScStaffId(MstStaff scStaffId) {
        this.scStaffId = scStaffId;
    }

    public MstUnit getScUnitId() {
        return scUnitId;
    }

    public void setScUnitId(MstUnit scUnitId) {
        this.scUnitId = scUnitId;
    }
}
