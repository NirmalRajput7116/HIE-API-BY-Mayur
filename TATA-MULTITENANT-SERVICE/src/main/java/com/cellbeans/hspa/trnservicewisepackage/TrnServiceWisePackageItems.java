package com.cellbeans.hspa.trnservicewisepackage;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invstore.InvStore;
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
@Table(name = "trn_service_wise_package_items")
public class TrnServiceWisePackageItems implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tswpiId", unique = true, nullable = true)
    private long tswpiId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tswpiTswpId")
    private TrnServiceWisePackage tswpiTswpId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tswpiItemId")
    private InvItem tswpiItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tswpiStoreId")
    private InvStore tswpiStoreId;

    @JsonInclude(NON_NULL)
    @Column(name = "tswpiPredefinedQty", columnDefinition = "decimal default 0", nullable = true)
    private double tswpiPredefinedQty;

    @JsonInclude(NON_NULL)
    @Column(name = "tswpiConsumedQty", columnDefinition = "decimal default 0", nullable = true)
    private double tswpiConsumedQty;

    @JsonInclude(NON_NULL)
    @Column(name = "tswpiRemarks")
    private String tswpiRemarks;

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

    public long getTswpiId() {
        return tswpiId;
    }

    public void setTswpiId(long tswpiId) {
        this.tswpiId = tswpiId;
    }

    public TrnServiceWisePackage getTswpiTswpId() {
        return tswpiTswpId;
    }

    public void setTswpiTswpId(TrnServiceWisePackage tswpiTswpId) {
        this.tswpiTswpId = tswpiTswpId;
    }

    public InvItem getTswpiItemId() {
        return tswpiItemId;
    }

    public void setTswpiItemId(InvItem tswpiItemId) {
        this.tswpiItemId = tswpiItemId;
    }

    public InvStore getTswpiStoreId() {
        return tswpiStoreId;
    }

    public void setTswpiStoreId(InvStore tswpiStoreId) {
        this.tswpiStoreId = tswpiStoreId;
    }

    public double getTswpiPredefinedQty() {
        return tswpiPredefinedQty;
    }

    public void setTswpiPredefinedQty(double tswpiPredefinedQty) {
        this.tswpiPredefinedQty = tswpiPredefinedQty;
    }

    public double getTswpiConsumedQty() {
        return tswpiConsumedQty;
    }

    public void setTswpiConsumedQty(double tswpiConsumedQty) {
        this.tswpiConsumedQty = tswpiConsumedQty;
    }

    public String getTswpiRemarks() {
        return tswpiRemarks;
    }

    public void setTswpiRemarks(String tswpiRemarks) {
        this.tswpiRemarks = tswpiRemarks;
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

}