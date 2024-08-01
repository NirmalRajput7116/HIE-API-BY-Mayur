package com.cellbeans.hspa.invServiceWiseConsumptionItems;

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
@Table(name = "inv_service_wise_package_items")
public class InvServiceWisePackageItems implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iswpiId", unique = true, nullable = true)
    private long iswpiId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "iswpiIswpId")
    private long iswpiIswpId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "itemId")
    private long itemId;

    @JsonInclude(NON_NULL)
    @Column(name = "itemQty", columnDefinition = "decimal default 0", nullable = true)
    private double itemQty;

    @JsonInclude(NON_NULL)
    @Column(name = "itemName")
    private String itemName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemStoreId")
    private InvStore itemStoreId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getIswpiId() {
        return iswpiId;
    }

    public void setIswpiId(long iswpiId) {
        this.iswpiId = iswpiId;
    }

    public long getIswpiIswpId() {
        return iswpiIswpId;
    }

    public void setIswpiIswpId(long iswpiIswpId) {
        this.iswpiIswpId = iswpiIswpId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public double getItemQty() {
        return itemQty;
    }

    public void setItemQty(double itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public InvStore getItemStoreId() {
        return itemStoreId;
    }

    public void setItemStoreId(InvStore itemStoreId) {
        this.itemStoreId = itemStoreId;
    }
}