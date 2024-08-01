package com.cellbeans.hspa.tinvstoreindentitem;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvstoreindent.TinvStoreIndent;
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
@Table(name = "tinv_store_indent_item")
public class TinvStoreIndentItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "siiId", unique = true, nullable = true)
    private long siiId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "siiItemId")
    private InvItem siiItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "siiSiId")
    private TinvStoreIndent siiSiId;

    @JsonInclude(NON_NULL)
    @Column(name = "siiQuantity")
    private String siiQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "siiMrp")
    private double siiMrp;

    @JsonInclude(NON_NULL)
    @Column(name = "siiRate")
    private double siiRate;

    @JsonInclude(NON_NULL)
    @Column(name = "siiTotalMrp")
    private double siiTotalMrp;

    @JsonInclude(NON_NULL)
    @Column(name = "siiTotalAmount")
    private double siiTotalAmount;

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

    public MstUnit getStoreIndentUnitId() {
        return storeIndentUnitId;
    }

    public void setStoreIndentUnitId(MstUnit storeIndentUnitId) {
        this.storeIndentUnitId = storeIndentUnitId;
    }

    public long getSiiId() {
        return siiId;
    }

    public void setSiiId(int siiId) {
        this.siiId = siiId;
    }

    public InvItem getSiiItemId() {
        return siiItemId;
    }

    public void setSiiItemId(InvItem siiItemId) {
        this.siiItemId = siiItemId;
    }

    public String getSiiQuantity() {
        return siiQuantity;
    }

    public void setSiiQuantity(String siiQuantity) {
        this.siiQuantity = siiQuantity;
    }

    public double getSiiMrp() {
        return siiMrp;
    }

    public void setSiiMrp(double siiMrp) {
        this.siiMrp = siiMrp;
    }

    public double getSiiRate() {
        return siiRate;
    }

    public void setSiiRate(double siiRate) {
        this.siiRate = siiRate;
    }

    public double getSiiTotalAmount() {
        return siiTotalAmount;
    }

    public void setSiiTotalAmount(double siiTotalAmount) {
        this.siiTotalAmount = siiTotalAmount;
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

    public TinvStoreIndent getSiiSiId() {
        return siiSiId;
    }

    public void setSiiSiId(TinvStoreIndent siiSiId) {
        this.siiSiId = siiSiId;
    }

    public double getSiiTotalMrp() {
        return siiTotalMrp;
    }

    public void setSiiTotalMrp(double siiTotalMrp) {
        this.siiTotalMrp = siiTotalMrp;
    }

}
