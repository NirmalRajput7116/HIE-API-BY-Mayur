package com.cellbeans.hspa.tinvopeningbalanceitem;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invitemcategory.InvItemCategory;
import com.cellbeans.hspa.invitemdispensingtype.InvItemDispensingType;
import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvopeningbalance.TinvOpeningBalance;
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
@Table(name = "tinv_opening_balance_item")
public class TinvOpeningBalanceItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obiId", unique = true, nullable = true)
    private long obiId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "obiItemId")
    private InvItem obiItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "obiPgrnId")
    private TinvPurchaseGoodsReceivedNote obiPgrnId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "obiStoreId")
    private InvStore obiStoreId;

    @Transient
    private InvStore issueStoreId;

    @Transient
    private int takenQty;

    @Transient
    private Long count;

    @Transient
    private Long changeUnitId;

    @Transient
    private int retunQty;

    @Transient
    private Double consumeQty;

    @JsonInclude(NON_NULL)
    @Column(name = "obiItemBatchCode")
    private String obiItemBatchCode;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemName")
    private String obiItemName;
    /*@ManyToOne
    @JoinColumn(name = "obiObId")
    private TinvOpeningBalance obiObId;
*/
    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "obiItemExpiryDate")
    private Date obiItemExpiryDate;
    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "obiItemManufactureDate")
    private Date obiItemManufactureDate;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "obiItemPurchaseIdtId")
    private InvItemDispensingType obiItemPurchaseIdtId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "obiItemCategoryId")
    private InvItemCategory obiItemCategoryId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "obiobId")
    private TinvOpeningBalance obiobId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "obiItemDespensingIdtId")
    private InvItemDispensingType obiItemDespensingIdtId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "obiItemSaleIdtId")
    private InvItemDispensingType obiItemSaleIdtId;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemConversionFactor")
    private int obiItemConversionFactor;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemQuantity")
    private double obiItemQuantity;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemOriginalQuantity")
    private double obiItemOriginalQuantity;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemGRNQuantity", columnDefinition = "Decimal(10,2) default '0'")
    private double obiItemGRNQuantity;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemFreeQuantity")
    private int obiItemFreeQuantity;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemCode")
    private String obiItemCode;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemCost")
    private double obiItemCost;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemMrp")
    private double obiItemMrp;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemIpdMrp", columnDefinition = "Decimal(10,2) default '0'")
    private double obiItemIpdMrp;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "obiItemTaxId")
    private InvTax obiItemTaxId;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemAmount")
    private double obiItemAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemDiscountOnSalePercentage")
    private double obiItemDiscountOnSalePercentage;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemTaxAmount")
    private double obiItemTaxAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemNetAmount")
    private double obiItemNetAmount;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    @JsonIgnore
    @Column(name = "isRejected", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isRejected = false;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemDiscountOnSaleAmount")
    private double obiItemDiscountOnSaleAmount;
    @JsonIgnore
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;
    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;
    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;
    @JsonInclude(NON_NULL)
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "openingBalanceUnitId")
    private MstUnit openingBalanceUnitId;
    @JsonInclude(NON_NULL)
    @Column(name = "totalQty", columnDefinition = "Decimal(10,2) default '0'")
    private double totalQty;
    @JsonInclude(NON_NULL)
    @Column(name = "obiGrossPurchaseValue", columnDefinition = "Decimal(10,2) default '0'")
    private double obiGrossPurchaseValue;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemPerchaseQty", columnDefinition = "Decimal(10,2) default '0'")
    private double obiItemPerchaseQty;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemPerchaseFreeQty", columnDefinition = "Decimal(10,2) default '0'")
    private double obiItemPerchaseFreeQty;
    @JsonInclude(NON_NULL)
    @Column(name = "obiItemUnitCostMrp", columnDefinition = "Decimal(10,2) default '0'")
    private double obiItemUnitCostMrp;
    @JsonInclude(NON_NULL)
    @Column(name = "isApproved", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isApproved = false;

    public Double getConsumeQty() {
        return consumeQty;
    }

    public void setConsumeQty(Double consumeQty) {
        this.consumeQty = consumeQty;
    }

    public String getObiItemName() {
        return obiItemName;
    }

    public void setObiItemName(String obiItemName) {
        this.obiItemName = obiItemName;
    }

    public void setObiTtemName(String obiTtemName) {
        this.obiItemName = obiTtemName;
    }

    public Long getChangeUnitId() {
        return changeUnitId;
    }

    public void setChangeUnitId(Long changeUnitId) {
        this.changeUnitId = changeUnitId;
    }

    public InvItemDispensingType getObiItemDespensingIdtId() {
        return obiItemDespensingIdtId;
    }

    public void setObiItemDespensingIdtId(InvItemDispensingType obiItemDespensingIdtId) {
        this.obiItemDespensingIdtId = obiItemDespensingIdtId;
    }

    public String getObiItemCode() {
        return obiItemCode;
    }

    public void setObiItemCode(String obiItemCode) {
        this.obiItemCode = obiItemCode;
    }

    public double getObiItemGRNQuantity() {
        return obiItemGRNQuantity;
    }

    public void setObiItemGRNQuantity(double obiItemGRNQuantity) {
        this.obiItemGRNQuantity = obiItemGRNQuantity;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Date getObiItemManufactureDate() {
        return obiItemManufactureDate;
    }

    public void setObiItemManufactureDate(Date obiItemManufactureDate) {
        this.obiItemManufactureDate = obiItemManufactureDate;
    }

    public double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(double totalQty) {
        this.totalQty = totalQty;
    }

    public double getObiGrossPurchaseValue() {
        return obiGrossPurchaseValue;
    }

    public void setObiGrossPurchaseValue(double obiGrossPurchaseValue) {
        this.obiGrossPurchaseValue = obiGrossPurchaseValue;
    }

    public double getObiItemPerchaseQty() {
        return obiItemPerchaseQty;
    }

    public void setObiItemPerchaseQty(double obiItemPerchaseQty) {
        this.obiItemPerchaseQty = obiItemPerchaseQty;
    }

    public double getObiItemPerchaseFreeQty() {
        return obiItemPerchaseFreeQty;
    }

    public void setObiItemPerchaseFreeQty(double obiItemPerchaseFreeQty) {
        this.obiItemPerchaseFreeQty = obiItemPerchaseFreeQty;
    }

    public double getObiItemUnitCostMrp() {
        return obiItemUnitCostMrp;
    }

    public void setObiItemUnitCostMrp(double obiItemUnitCostMrp) {
        this.obiItemUnitCostMrp = obiItemUnitCostMrp;
    }

    public int getRetunQty() {
        return retunQty;
    }

    public void setRetunQty(int retunQty) {
        this.retunQty = retunQty;
    }

    public MstUnit getOpeningBalanceUnitId() {
        return openingBalanceUnitId;
    }

    public void setOpeningBalanceUnitId(MstUnit openingBalanceUnitId) {
        this.openingBalanceUnitId = openingBalanceUnitId;
    }

    public TinvOpeningBalance getObiobId() {
        return obiobId;
    }

    public void setObiobId(TinvOpeningBalance obiobId) {
        this.obiobId = obiobId;
    }

    public long getObiId() {
        return obiId;
    }

    public void setObiId(int obiId) {
        this.obiId = obiId;
    }

    public InvItem getObiItemId() {
        return obiItemId;
    }

    public void setObiItemId(InvItem obiItemId) {
        this.obiItemId = obiItemId;
    }

    public String getObiItemBatchCode() {
        return obiItemBatchCode;
    }

    public void setObiItemBatchCode(String obiItemBatchCode) {
        this.obiItemBatchCode = obiItemBatchCode;
    }

    public Date getObiItemExpiryDate() {
        return obiItemExpiryDate;
    }

    public void setObiItemExpiryDate(Date obiItemExpiryDate) {
        this.obiItemExpiryDate = obiItemExpiryDate;
    }

    public InvItemDispensingType getObiItemPurchaseIdtId() {
        return obiItemPurchaseIdtId;
    }

    public void setObiItemPurchaseIdtId(InvItemDispensingType obiItemPurchaseIdtId) {
        this.obiItemPurchaseIdtId = obiItemPurchaseIdtId;
    }

    public InvItemDispensingType getObiItemSaleIdtId() {
        return obiItemSaleIdtId;
    }

    public void setObiItemSaleIdtId(InvItemDispensingType obiItemSaleIdtId) {
        this.obiItemSaleIdtId = obiItemSaleIdtId;
    }

    public int getObiItemConversionFactor() {
        return obiItemConversionFactor;
    }

    public void setObiItemConversionFactor(int obiItemConversionFactor) {
        this.obiItemConversionFactor = obiItemConversionFactor;
    }

    public double getObiItemQuantity() {
        return obiItemQuantity;
    }

    public void setObiItemQuantity(double obiItemQuantity) {
        this.obiItemQuantity = obiItemQuantity;
    }

    public double getObiItemCost() {
        return obiItemCost;
    }

    public void setObiItemCost(double obiItemCost) {
        this.obiItemCost = obiItemCost;
    }

    public double getObiItemMrp() {
        return obiItemMrp;
    }

    public void setObiItemMrp(double obiItemMrp) {
        this.obiItemMrp = obiItemMrp;
    }

    public InvTax getObiItemTaxId() {
        return obiItemTaxId;
    }

    public void setObiItemTaxId(InvTax obiItemTaxId) {
        this.obiItemTaxId = obiItemTaxId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public double getObiItemAmount() {
        return obiItemAmount;
    }

    public void setObiItemAmount(double obiItemAmount) {
        this.obiItemAmount = obiItemAmount;
    }

    public double getObiItemDiscountOnSalePercentage() {
        return obiItemDiscountOnSalePercentage;
    }

    public void setObiItemDiscountOnSalePercentage(double obiItemDiscountOnSalePercentage) {
        this.obiItemDiscountOnSalePercentage = obiItemDiscountOnSalePercentage;
    }

    public double getObiItemTaxAmount() {
        return obiItemTaxAmount;
    }

    public void setObiItemTaxAmount(double obiItemTaxAmount) {
        this.obiItemTaxAmount = obiItemTaxAmount;
    }

    public double getObiItemNetAmount() {
        return obiItemNetAmount;
    }

    public void setObiItemNetAmount(double obiItemNetAmount) {
        this.obiItemNetAmount = obiItemNetAmount;
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

    public double getObiItemDiscountOnSaleAmount() {
        return obiItemDiscountOnSaleAmount;
    }

    public void setObiItemDiscountOnSaleAmount(double obiItemDiscountOnSaleAmount) {
        this.obiItemDiscountOnSaleAmount = obiItemDiscountOnSaleAmount;
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

    public InvItemCategory getObiItemCategoryId() {
        return obiItemCategoryId;
    }

    public void setObiItemCategoryId(InvItemCategory obiItemCategoryId) {
        this.obiItemCategoryId = obiItemCategoryId;
    }

    public TinvPurchaseGoodsReceivedNote getObiPgrnId() {
        return obiPgrnId;
    }

    public void setObiPgrnId(TinvPurchaseGoodsReceivedNote obiPgrnId) {
        this.obiPgrnId = obiPgrnId;
    }

    public InvStore getObiStoreId() {
        return obiStoreId;
    }

    public void setObiStoreId(InvStore obiStoreId) {
        this.obiStoreId = obiStoreId;
    }

    public InvStore getIssueStoreId() {
        return issueStoreId;
    }

    public void setIssueStoreId(InvStore issueStoreId) {
        this.issueStoreId = issueStoreId;
    }

    public int getTakenQty() {
        return takenQty;
    }

    public void setTakenQty(int takenQty) {
        this.takenQty = takenQty;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public int getObiItemFreeQuantity() {
        return obiItemFreeQuantity;
    }

    public void setObiItemFreeQuantity(int obiItemFreeQuantity) {
        this.obiItemFreeQuantity = obiItemFreeQuantity;
    }

    public double getObiItemOriginalQuantity() {
        return obiItemOriginalQuantity;
    }

    public void setObiItemOriginalQuantity(double obiItemOriginalQuantity) {
        this.obiItemOriginalQuantity = obiItemOriginalQuantity;
    }

    public Boolean getRejected() {
        return isRejected;
    }

    public void setRejected(Boolean rejected) {
        isRejected = rejected;
    }

    public double getObiItemIpdMrp() {
        return obiItemIpdMrp;
    }

    public void setObiItemIpdMrp(double obiItemIpdMrp) {
        this.obiItemIpdMrp = obiItemIpdMrp;
    }

    @Override
    public String toString() {
        return "TinvOpeningBalanceItem{" + "obiId=" + obiId + ", obiItemId=" + obiItemId + ", obiPgrnId=" + obiPgrnId + ", obiStoreId=" + obiStoreId + ", issueStoreId=" + issueStoreId + ", takenQty=" + takenQty + ", count=" + count + ", obiItemBatchCode='" + obiItemBatchCode + '\'' + ", obiItemName='" + obiItemName + '\'' + ", obiItemExpiryDate=" + obiItemExpiryDate + ", obiItemPurchaseIdtId=" + obiItemPurchaseIdtId + ", obiItemCategoryId=" + obiItemCategoryId + ", obiobId=" + obiobId + ", obiItemDespensingIdtId=" + obiItemDespensingIdtId + ", obiItemSaleIdtId=" + obiItemSaleIdtId + ", obiItemConversionFactor=" + obiItemConversionFactor + ", obiItemQuantity=" + obiItemQuantity + ", obiItemFreeQuantity=" + obiItemFreeQuantity + ", obiItemCode='" + obiItemCode + '\'' + ", obiItemCost=" + obiItemCost + ", obiItemMrp=" + obiItemMrp + ", obiItemTaxId=" + obiItemTaxId + ", obiItemAmount=" + obiItemAmount + ", obiItemDiscountOnSalePercentage=" + obiItemDiscountOnSalePercentage + ", obiItemTaxAmount=" + obiItemTaxAmount + ", obiItemNetAmount=" + obiItemNetAmount + ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", obiItemDiscountOnSaleAmount=" + obiItemDiscountOnSaleAmount + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", openingBalanceUnitId=" + openingBalanceUnitId + '}';
    }

}