package com.cellbeans.hspa.tinvpurchasegoodsreceivednoteitem;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpurchasegoodsreceivednote.TinvPurchaseGoodsReceivedNote;
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
@Table(name = "tinv_purchase_goods_received_note_item")
public class TinvPurchaseGoodsReceivedNoteItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pgrniId", unique = true, nullable = true)
    private long pgrniId;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniName", unique = true, nullable = true)
    private long pgrniName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrniPgrnId")
    private TinvPurchaseGoodsReceivedNote pgrniPgrnId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrniItemId")
    private InvItem pgrniItemId;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemMrp")
    private String pgrniItemMrp;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemQuantity")
    private int pgrniItemQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemBatchNo")
    private String pgrniItemBatchNo;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemExpiry", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date pgrniItemExpiry;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemPurRate")
    private String pgrniItemPurRate;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemFreeQuantity")
    private int pgrniItemFreeQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemConcessionPercentage")
    private String pgrniItemConcessionPercentage;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemConcessionAmount")
    private String pgrniItemConcessionAmount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrniItemTaxId")
    private InvTax pgrniItemTaxId;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemTaxAmount")
    private String pgrniItemTaxAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemNetAmount")
    private String pgrniItemNetAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemConversionFactor")
    private int pgrniItemConversionFactor;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrniUnitId")
    private MstUnit pgrniUnitId;

    public MstUnit getPgrniUnitId() {
        return pgrniUnitId;
    }

    public void setPgrniUnitId(MstUnit pgrniUnitId) {
        this.pgrniUnitId = pgrniUnitId;
    }

    public long getPgrniId() {
        return pgrniId;
    }

    public void setPgrniId(int pgrniId) {
        this.pgrniId = pgrniId;
    }

    public TinvPurchaseGoodsReceivedNote getPgrniPgrnId() {
        return pgrniPgrnId;
    }

    public void setPgrniPgrnId(TinvPurchaseGoodsReceivedNote pgrniPgrnId) {
        this.pgrniPgrnId = pgrniPgrnId;
    }

    public InvItem getPgrniItemId() {
        return pgrniItemId;
    }

    public void setPgrniItemId(InvItem pgrniItemId) {
        this.pgrniItemId = pgrniItemId;
    }

    public String getPgrniItemMrp() {
        return pgrniItemMrp;
    }

    public void setPgrniItemMrp(String pgrniItemMrp) {
        this.pgrniItemMrp = pgrniItemMrp;
    }

    public int getPgrniItemQuantity() {
        return pgrniItemQuantity;
    }

    public void setPgrniItemQuantity(int pgrniItemQuantity) {
        this.pgrniItemQuantity = pgrniItemQuantity;
    }

    public String getPgrniItemBatchNo() {
        return pgrniItemBatchNo;
    }

    public void setPgrniItemBatchNo(String pgrniItemBatchNo) {
        this.pgrniItemBatchNo = pgrniItemBatchNo;
    }

    public Date getPgrniItemExpiry() {
        return pgrniItemExpiry;
    }

    public void setPgrniItemExpiry(Date pgrniItemExpiry) {
        this.pgrniItemExpiry = pgrniItemExpiry;
    }

    public String getPgrniItemPurRate() {
        return pgrniItemPurRate;
    }

    public void setPgrniItemPurRate(String pgrniItemPurRate) {
        this.pgrniItemPurRate = pgrniItemPurRate;
    }

    public int getPgrniItemFreeQuantity() {
        return pgrniItemFreeQuantity;
    }

    public void setPgrniItemFreeQuantity(int pgrniItemFreeQuantity) {
        this.pgrniItemFreeQuantity = pgrniItemFreeQuantity;
    }

    public String getPgrniItemConcessionPercentage() {
        return pgrniItemConcessionPercentage;
    }

    public void setPgrniItemConcessionPercentage(String pgrniItemConcessionPercentage) {
        this.pgrniItemConcessionPercentage = pgrniItemConcessionPercentage;
    }

    public String getPgrniItemConcessionAmount() {
        return pgrniItemConcessionAmount;
    }

    public void setPgrniItemConcessionAmount(String pgrniItemConcessionAmount) {
        this.pgrniItemConcessionAmount = pgrniItemConcessionAmount;
    }

    public InvTax getPgrniItemTaxId() {
        return pgrniItemTaxId;
    }

    public void setPgrniItemTaxId(InvTax pgrniItemTaxId) {
        this.pgrniItemTaxId = pgrniItemTaxId;
    }

    public String getPgrniItemTaxAmount() {
        return pgrniItemTaxAmount;
    }

    public void setPgrniItemTaxAmount(String pgrniItemTaxAmount) {
        this.pgrniItemTaxAmount = pgrniItemTaxAmount;
    }

    public String getPgrniItemNetAmount() {
        return pgrniItemNetAmount;
    }

    public void setPgrniItemNetAmount(String pgrniItemNetAmount) {
        this.pgrniItemNetAmount = pgrniItemNetAmount;
    }

    public int getPgrniItemConversionFactor() {
        return pgrniItemConversionFactor;
    }

    public void setPgrniItemConversionFactor(int pgrniItemConversionFactor) {
        this.pgrniItemConversionFactor = pgrniItemConversionFactor;
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

    public long getPgrniName() {
        return pgrniName;
    }

    public void setPgrniName(long pgrniName) {
        this.pgrniName = pgrniName;
    }

}
