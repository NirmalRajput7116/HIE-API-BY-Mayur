package com.cellbeans.hspa.tinvenquiryitem;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invitemdispensingtype.InvItemDispensingType;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpurchaseitemenquiry.TinvPurchaseItemEnquiry;
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
@Table(name = "tinv_enquiry_item")
public class TinvEnquiryItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eiId", unique = true, nullable = true)
    private long eiId;

    @Column(name = "eiName")
    private String eiName;

    @JsonInclude(NON_NULL)
    @Column(name = "eiItemQuantity")
    private int eiItemQuantity;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "eiIdtId")
    private InvItemDispensingType eiIdtId;

    @JsonInclude(NON_NULL)
    @Column(name = "eiRemark")
    private String eiRemark;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "eiItemId")
    private InvItem eiItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "eiPieId")
    private TinvPurchaseItemEnquiry eiPieId;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ieUnitId")
    private MstUnit ieUnitId;

    public MstUnit getIeUnitId() {
        return ieUnitId;
    }

    public void setIeUnitId(MstUnit ieUnitId) {
        this.ieUnitId = ieUnitId;
    }

    public long getEiId() {
        return eiId;
    }

    public void setEiId(int eiId) {
        this.eiId = eiId;
    }

    public int getEiItemQuantity() {
        return eiItemQuantity;
    }

    public void setEiItemQuantity(int eiItemQuantity) {
        this.eiItemQuantity = eiItemQuantity;
    }

    public String getEiRemark() {
        return eiRemark;
    }

    public void setEiRemark(String eiRemark) {
        this.eiRemark = eiRemark;
    }

    public InvItem getEiItemId() {
        return eiItemId;
    }

    public void setEiItemId(InvItem eiItemId) {
        this.eiItemId = eiItemId;
    }

    public TinvPurchaseItemEnquiry getEiPieId() {
        return eiPieId;
    }

    public void setEiPieId(TinvPurchaseItemEnquiry eiPieId) {
        this.eiPieId = eiPieId;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
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

    public String getEiName() {
        return eiName;
    }

    public void setEiName(String eiName) {
        this.eiName = eiName;
    }

    public InvItemDispensingType getEiIdtId() {
        return eiIdtId;
    }

    public void setEiIdtId(InvItemDispensingType eiIdtId) {
        this.eiIdtId = eiIdtId;
    }

}
