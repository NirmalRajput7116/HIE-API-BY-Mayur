package com.cellbeans.hspa.tinvpurchasepurchaseorderitem;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invitemdispensingtype.InvItemDispensingType;
import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpurchasepurchaseorder.TinvPurchasePurchaseOrder;
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
@Table(name = "tinv_purchase_purchase_order_item")
public class TinvPurchasePurchaseOrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppotId", unique = true, nullable = true)
    private long ppotId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppotItemId")
    private InvItem ppotItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppotPpoId")
    private TinvPurchasePurchaseOrder ppotPpoId;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotName")
    private String ppotName;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemRate")
    private double ppotItemRate;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemQuantity")
    private int ppotItemQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemRatePerQty")
    private double ppotItemRatePerQty;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemFreeQuantity")
    private int ppotItemFreeQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemTotalAmount")
    private double ppotItemTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemNetAmount")
    private double ppotItemNetAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemConcessionPercenatge")
    private double ppotItemConcessionPercenatge;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemConcessionAmount")
    private double ppotItemConcessionAmount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppotTaxId")
    private InvTax ppotTaxId;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemTaxValue")
    private double ppotItemTaxValue;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemTaxAmount")
    private double ppotItemTaxAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemSpecifiction")
    private String ppotItemSpecifiction;

    @JsonInclude(NON_NULL)
    @Column(name = "ppotItemRemark")
    private String ppotItemRemark;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ppotIdtId")
    private InvItemDispensingType ppotIdtId;

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
    @JoinColumn(name = "ppotUnitId")
    private MstUnit ppotUnitId;

    @JsonIgnore
    @Column(name = "isRejected", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isRejected = false;

    public MstUnit getPpotUnitId() {
        return ppotUnitId;
    }

    public void setPpotUnitId(MstUnit ppotUnitId) {
        this.ppotUnitId = ppotUnitId;
    }

    public InvItem getPpotItemId() {
        return ppotItemId;
    }

    public void setPpotItemId(InvItem ppotItemId) {
        this.ppotItemId = ppotItemId;
    }

    public InvTax getPpotTaxId() {
        return ppotTaxId;
    }

    public void setPpotTaxId(InvTax ppotTaxId) {
        this.ppotTaxId = ppotTaxId;
    }

    public String getPpotItemSpecifiction() {
        return ppotItemSpecifiction;
    }

    public void setPpotItemSpecifiction(String ppotItemSpecifiction) {
        this.ppotItemSpecifiction = ppotItemSpecifiction;
    }

    public String getPpotItemRemark() {
        return ppotItemRemark;
    }

    public void setPpotItemRemark(String ppotItemRemark) {
        this.ppotItemRemark = ppotItemRemark;
    }

    public InvItemDispensingType getPpotIdtId() {
        return ppotIdtId;
    }

    public void setPpotIdtId(InvItemDispensingType ppotIdtId) {
        this.ppotIdtId = ppotIdtId;
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

    public String getPpotName() {
        return ppotName;
    }

    public void setPpotName(String ppotName) {
        this.ppotName = ppotName;
    }

    public TinvPurchasePurchaseOrder getPpotPpoId() {
        return ppotPpoId;
    }

    public void setPpotPpoId(TinvPurchasePurchaseOrder ppotPpoId) {
        this.ppotPpoId = ppotPpoId;
    }

    public long getPpotId() {
        return ppotId;
    }

    public void setPpotId(long ppotId) {
        this.ppotId = ppotId;
    }

    public double getPpotItemRate() {
        return ppotItemRate;
    }

    public void setPpotItemRate(double ppotItemRate) {
        this.ppotItemRate = ppotItemRate;
    }

    public int getPpotItemQuantity() {
        return ppotItemQuantity;
    }

    public void setPpotItemQuantity(int ppotItemQuantity) {
        this.ppotItemQuantity = ppotItemQuantity;
    }

    public double getPpotItemRatePerQty() {
        return ppotItemRatePerQty;
    }

    public void setPpotItemRatePerQty(double ppotItemRatePerQty) {
        this.ppotItemRatePerQty = ppotItemRatePerQty;
    }

    public int getPpotItemFreeQuantity() {
        return ppotItemFreeQuantity;
    }

    public void setPpotItemFreeQuantity(int ppotItemFreeQuantity) {
        this.ppotItemFreeQuantity = ppotItemFreeQuantity;
    }

    public double getPpotItemTotalAmount() {
        return ppotItemTotalAmount;
    }

    public void setPpotItemTotalAmount(double ppotItemTotalAmount) {
        this.ppotItemTotalAmount = ppotItemTotalAmount;
    }

    public double getPpotItemNetAmount() {
        return ppotItemNetAmount;
    }

    public void setPpotItemNetAmount(double ppotItemNetAmount) {
        this.ppotItemNetAmount = ppotItemNetAmount;
    }

    public double getPpotItemConcessionPercenatge() {
        return ppotItemConcessionPercenatge;
    }

    public void setPpotItemConcessionPercenatge(double ppotItemConcessionPercenatge) {
        this.ppotItemConcessionPercenatge = ppotItemConcessionPercenatge;
    }

    public double getPpotItemConcessionAmount() {
        return ppotItemConcessionAmount;
    }

    public void setPpotItemConcessionAmount(double ppotItemConcessionAmount) {
        this.ppotItemConcessionAmount = ppotItemConcessionAmount;
    }

    public double getPpotItemTaxValue() {
        return ppotItemTaxValue;
    }

    public void setPpotItemTaxValue(double ppotItemTaxValue) {
        this.ppotItemTaxValue = ppotItemTaxValue;
    }

    public double getPpotItemTaxAmount() {
        return ppotItemTaxAmount;
    }

    public void setPpotItemTaxAmount(double ppotItemTaxAmount) {
        this.ppotItemTaxAmount = ppotItemTaxAmount;
    }

    public Boolean getRejected() {
        return isRejected;
    }

    public void setRejected(Boolean rejected) {
        isRejected = rejected;
    }
}
