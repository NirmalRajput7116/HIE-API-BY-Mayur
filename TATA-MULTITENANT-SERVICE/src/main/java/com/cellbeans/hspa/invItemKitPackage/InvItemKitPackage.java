package com.cellbeans.hspa.invItemKitPackage;

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
@Table(name = "inv_item_kit_package")
public class InvItemKitPackage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ikpId", unique = true, nullable = true)
    private long ikpId;

    @JsonInclude(NON_NULL)
    @Column(name = "ikpName")
    private String ikpName;

    @JsonInclude(NON_NULL)
    @Column(name = "ikpCode")
    private String ikpCode;

    @JsonInclude(NON_NULL)
    @Column(name = "ikpAmount", columnDefinition = "decimal default 0", nullable = true)
    private double ikpAmount;

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
//	@OneToMany(cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY,
//            mappedBy = "invItemKitPackage")
//    private List<InvItemKitPackageItems> invItemKitPackageItems;
//	
//
//    public List<InvItemKitPackageItems> getInvItemKitPackageItems() {
//		return invItemKitPackageItems;
//	}
//
//	public void setInvItemKitPackageItems(List<InvItemKitPackageItems> invItemKitPackageItems) {
//		this.invItemKitPackageItems = invItemKitPackageItems;
//	}

    public long getIkpId() {
        return ikpId;
    }

    public void setIkpId(long ikpId) {
        this.ikpId = ikpId;
    }

    public String getIkpName() {
        return ikpName;
    }

    public void setIkpName(String ikpName) {
        this.ikpName = ikpName;
    }

    public double getIkpAmount() {
        return ikpAmount;
    }

    public void setIkpAmount(double ikpAmount) {
        this.ikpAmount = ikpAmount;
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

    public String getIkpCode() {
        return ikpCode;
    }

    public void setIkpCode(String ikpCode) {
        this.ikpCode = ikpCode;
    }
}