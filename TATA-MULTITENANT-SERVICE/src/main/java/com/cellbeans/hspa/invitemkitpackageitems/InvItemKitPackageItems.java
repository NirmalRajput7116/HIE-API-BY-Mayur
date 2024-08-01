package com.cellbeans.hspa.invitemkitpackageitems;

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
@Table(name = "inv_item_kit_package_items")
public class InvItemKitPackageItems implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ikpiId", unique = true, nullable = true)
    private long ikpiId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "ikpiIkpId")
    private long ikpiIkpId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "ikpiItemId")
    private long ikpiItemId;

    @JsonInclude(NON_NULL)
    @Column(name = "ikpiItemQty", columnDefinition = "decimal default 0", nullable = true)
    private double ikpiItemQty;

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
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ikpId", nullable = true)
//    private InvItemKitPackage invItemKitPackage;
//   
//
//    public InvItemKitPackage getInvItemKitPackage() {
//		return invItemKitPackage;
//	}
//
//	public void setInvItemKitPackage(InvItemKitPackage invItemKitPackage) {
//		this.invItemKitPackage = invItemKitPackage;
//	}

    public long getIkpiId() {
        return ikpiId;
    }

    public void setIkpiId(long ikpiId) {
        this.ikpiId = ikpiId;
    }

    public long getIkpiIkpId() {
        return ikpiIkpId;
    }

    public void setIkpiIkpId(long ikpiIkpId) {
        this.ikpiIkpId = ikpiIkpId;
    }

    public long getIkpiItemId() {
        return ikpiItemId;
    }

    public void setIkpiItemId(long ikpiItemId) {
        this.ikpiItemId = ikpiItemId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public double getIkpiItemQty() {
        return ikpiItemQty;
    }

    public void setIkpiItemQty(double ikpiItemQty) {
        this.ikpiItemQty = ikpiItemQty;
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
}