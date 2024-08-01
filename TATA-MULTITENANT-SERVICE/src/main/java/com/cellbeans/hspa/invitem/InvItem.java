package com.cellbeans.hspa.invitem;

import com.cellbeans.hspa.invItemsubcategory.InvItemSubCategory;
import com.cellbeans.hspa.invcompoundname.InvCompoundName;
import com.cellbeans.hspa.invgenericname.InvGenericName;
import com.cellbeans.hspa.invitemcategory.InvItemCategory;
import com.cellbeans.hspa.invitemcompany.InvItemCompany;
import com.cellbeans.hspa.invitemdispensingtype.InvItemDispensingType;
import com.cellbeans.hspa.invitemgroup.InvItemGroup;
import com.cellbeans.hspa.invitemstoragetype.InvItemStorageType;
import com.cellbeans.hspa.invitemsubgroup.InvItemSubGroup;
import com.cellbeans.hspa.invitemsubtype.InvItemSubType;
import com.cellbeans.hspa.invitemtype.InvItemType;
import com.cellbeans.hspa.invpregnancyclass.InvPregnancyClass;
import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.invunitofmeasurment.InvUnitOfMeasurment;
import com.cellbeans.hspa.mstroute.MstRoute;
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
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Inya Gaikwad
 * This Class is responsible for
 * For Creating An item ,Which is Starting point of inventory
 * Class has
 * {@link ManyToOne} with {@link InvItemDispensingType},{@link InvItemStorageType},{@link InvUnitOfMeasurment},{@link InvItemCategory},{@link InvTax}
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "inv_item")
public class InvItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId", unique = true, nullable = true)
    private long itemId;

    @JsonInclude(NON_NULL)
    @Column(name = "itemBrandName")
    private String itemBrandName;

    @JsonInclude(NON_NULL)
    @Column(name = "itemCode")
    private String itemCode;

    @JsonInclude(NON_NULL)
    @Column(name = "itemGenericName")
    private String itemGenericName;

    @JsonInclude(NON_NULL)
    @Column(name = "itemMrpOfPUOM")
    private String itemMrpOfPUOM;

    @JsonInclude(NON_NULL)
    @Transient
    private long Count;

    @JsonInclude(NON_NULL)
    @Column(name = "itemBatchCode")
    private String itemBatchCode;

    @JsonInclude(NON_NULL)
    @Column(name = "itemExpiryDate")
    private Date itemExpiryDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemIgId")
    private InvItemGroup itemIgId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemIdtId")
    private InvItemDispensingType itemIdtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemPurchaseUmoId")
    private InvItemDispensingType itemPurchaseUmoId;

    @JsonInclude(NON_NULL)
    @Column(name = "itemConversionFactor")
    private String itemConversionFactor;

    @JsonInclude(NON_NULL)
    @Column(name = "itemCostPrice")
    private String itemCostPrice;

    @JsonInclude(NON_NULL)
    @Column(name = "itemGuideCost")
    private String itemGuideCost;

    @JsonInclude(NON_NULL)
    @Column(name = "itemDiscountOnSale")
    private String itemDiscountOnSale;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemIstId")
    private InvItemStorageType itemIstId;

    @JsonInclude(NON_NULL)
    @Column(name = "itemFilePathAddress")
    private String itemFilePathAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "itemNote")
    private String itemNote;

    @JsonInclude(NON_NULL)
    @Column(name = "itemRemark")
    private String itemRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "itemInventoryCode")
    private String itemInventoryCode;

    @JsonInclude(NON_NULL)
    @Column(name = "itemStrength")
    private String itemStrength;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemSaleUomId")
    private InvItemDispensingType itemSaleUomId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemStrengthUomId")
    private InvUnitOfMeasurment itemStrengthUomId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemTcId")
    private InvItemCategory itemTcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemMarketedBy")
    private InvItemCompany itemMarketedBy;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemStockingUomId")
    private InvItemDispensingType itemStockingUomId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemRouteId")
    private MstRoute itemRouteId;

    @JsonInclude(NON_NULL)
    @Column(name = "itemLookLike")
    private String itemLookLike;

    @JsonInclude(NON_NULL)
    @Column(name = "itemSoundLike")
    private String itemSoundLike;

    @JsonInclude(NON_NULL)
    @Column(name = "itemLocation")
    private String itemLocation;

    @JsonInclude(NON_NULL)
    @Column(name = "itemMrp")
    private String itemMrp;

    @JsonInclude(NON_NULL)
    @Column(name = "itemVed")
    private String itemVed;

    @JsonInclude(NON_NULL)
    @Column(name = "itemEconomyQuantity")
    private String itemEconomyQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "itemQuantity")
    private String itemQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "itemAbc")
    private String itemAbc;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemTaxId")
    private InvTax itemTaxId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemPcId")
    private InvPregnancyClass itemPcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemIcId")
    private InvItemCategory itemIcId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "itemIscId")
    private InvItemSubCategory itemIscId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "itemGenericId")
    private InvGenericName itemGenericId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemIsgId")
    private InvItemSubGroup itemIsgId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemItId")
    private InvItemType itemItId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemIstyId")
    private InvItemSubType itemIstyId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemStoreId")
    private InvStore itemStoreId;

    @JsonInclude(NON_NULL)
    @Column(name = "itemName")
    private String itemName;

    @JsonInclude(NON_NULL)
    @Column(name = "itemMinimumQuantity")
    private String itemMinimumQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "itemBatchesRequired", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean itemBatchesRequired = false;

    @JsonInclude(NON_NULL)
    @Column(name = "itemLifeSaving", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean itemLifeSaving = false;

    @JsonInclude(NON_NULL)
    @Column(name = "itemHighRisk", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean itemHighRisk = false;

    @JsonInclude(NON_NULL)
    @Column(name = "itemHighCost", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean itemHighCost = false;

    @JsonInclude(NON_NULL)
    @Column(name = "itemH1Drug", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean itemH1Drug = false;

    @JsonInclude(NON_NULL)
    @Column(name = "itemNarcotics", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean itemNarcotics = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isShowPharmacy", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isShowPharmacy = false;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

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

    //seetanshu added
    @JsonInclude(NON_NULL)
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "icComponentId")
    private List<InvCompoundName> icComponentId;

    public List<InvCompoundName> getIcComponentId() {
        return icComponentId;
    }

    public void setIcComponentId(List<InvCompoundName> icComponentId) {
        this.icComponentId = icComponentId;
    }

    @JsonInclude(NON_NULL)
    @ManyToMany(cascade = CascadeType.MERGE)
   /* @JoinTable(name= "InvItemItemStoreIdList", joinColumns = @JoinColumn(name = "invItemItemId", referencedColumnName = "itemId"),
            inverseJoinColumns = @JoinColumn(name = "itemStoreIdListStoreId", referencedColumnName = "storeId"))*/
    @JoinColumn(name = "itemStoreIdList")
    private List<InvStore> itemStoreIdList;

    public List<InvStore> getItemStoreIdList() {
        return itemStoreIdList;
    }

    public void setItemStoreIdList(List<InvStore> itemStoreIdList) {
        this.itemStoreIdList = itemStoreIdList;
    }

    public InvStore getItemStoreId() {
        return itemStoreId;
    }

    public void setItemStoreId(InvStore itemStoreId) {
        this.itemStoreId = itemStoreId;
    }
    //    @JsonInclude(NON_NULL)
//    @OneToMany
//    @JoinColumn(name = "itemCompositionName")
//    private List<InvCompositionName> itemCompositionName;
//
//    public List<InvCompositionName> getItemCompositionName() {
//        return itemCompositionName;
//    }
//
//    public void setItemCompositionName(List<InvCompositionName> itemCompositionName) {
//        this.itemCompositionName = itemCompositionName;
//    }
    //    @ManyToOne
//    @JoinColumn(name = "itemUnitId")
//    private MstUnit itemUnitId;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemBrandName() {
        return itemBrandName;
    }

    public void setItemBrandName(String itemBrandName) {
        this.itemBrandName = itemBrandName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemGenericName() {
        return itemGenericName;
    }

    public void setItemGenericName(String itemGenericName) {
        this.itemGenericName = itemGenericName;
    }

    public String getItemMrpOfPUOM() {
        return itemMrpOfPUOM;
    }

    public void setItemMrpOfPUOM(String itemMrpOfPUOM) {
        this.itemMrpOfPUOM = itemMrpOfPUOM;
    }

    public long getCount() {
        return Count;
    }

    public void setCount(long count) {
        Count = count;
    }

    public String getItemBatchCode() {
        return itemBatchCode;
    }

    public void setItemBatchCode(String itemBatchCode) {
        this.itemBatchCode = itemBatchCode;
    }

    public Date getItemExpiryDate() {
        return itemExpiryDate;
    }

    public void setItemExpiryDate(Date itemExpiryDate) {
        this.itemExpiryDate = itemExpiryDate;
    }

    public InvItemGroup getItemIgId() {
        return itemIgId;
    }

    public void setItemIgId(InvItemGroup itemIgId) {
        this.itemIgId = itemIgId;
    }

    public InvItemDispensingType getItemIdtId() {
        return itemIdtId;
    }

    public void setItemIdtId(InvItemDispensingType itemIdtId) {
        this.itemIdtId = itemIdtId;
    }

    public InvItemDispensingType getItemPurchaseUmoId() {
        return itemPurchaseUmoId;
    }

    public void setItemPurchaseUmoId(InvItemDispensingType itemPurchaseUmoId) {
        this.itemPurchaseUmoId = itemPurchaseUmoId;
    }

    public String getItemConversionFactor() {
        return itemConversionFactor;
    }

    public void setItemConversionFactor(String itemConversionFactor) {
        this.itemConversionFactor = itemConversionFactor;
    }

    public String getItemCostPrice() {
        return itemCostPrice;
    }

    public void setItemCostPrice(String itemCostPrice) {
        this.itemCostPrice = itemCostPrice;
    }

    public String getItemGuideCost() {
        return itemGuideCost;
    }

    public void setItemGuideCost(String itemGuideCost) {
        this.itemGuideCost = itemGuideCost;
    }

    public String getItemDiscountOnSale() {
        return itemDiscountOnSale;
    }

    public void setItemDiscountOnSale(String itemDiscountOnSale) {
        this.itemDiscountOnSale = itemDiscountOnSale;
    }

    public InvItemStorageType getItemIstId() {
        return itemIstId;
    }

    public void setItemIstId(InvItemStorageType itemIstId) {
        this.itemIstId = itemIstId;
    }

    public String getItemFilePathAddress() {
        return itemFilePathAddress;
    }

    public void setItemFilePathAddress(String itemFilePathAddress) {
        this.itemFilePathAddress = itemFilePathAddress;
    }

    public String getItemNote() {
        return itemNote;
    }

    public void setItemNote(String itemNote) {
        this.itemNote = itemNote;
    }

    public String getItemRemark() {
        return itemRemark;
    }

    public void setItemRemark(String itemRemark) {
        this.itemRemark = itemRemark;
    }

    public String getItemInventoryCode() {
        return itemInventoryCode;
    }

    public void setItemInventoryCode(String itemInventoryCode) {
        this.itemInventoryCode = itemInventoryCode;
    }

    public String getItemStrength() {
        return itemStrength;
    }

    public void setItemStrength(String itemStrength) {
        this.itemStrength = itemStrength;
    }

    public InvItemDispensingType getItemSaleUomId() {
        return itemSaleUomId;
    }

    public void setItemSaleUomId(InvItemDispensingType itemSaleUomId) {
        this.itemSaleUomId = itemSaleUomId;
    }

    public InvUnitOfMeasurment getItemStrengthUomId() {
        return itemStrengthUomId;
    }

    public void setItemStrengthUomId(InvUnitOfMeasurment itemStrengthUomId) {
        this.itemStrengthUomId = itemStrengthUomId;
    }

    public InvItemCategory getItemTcId() {
        return itemTcId;
    }

    public void setItemTcId(InvItemCategory itemTcId) {
        this.itemTcId = itemTcId;
    }

    public InvItemCompany getItemMarketedBy() {
        return itemMarketedBy;
    }

    public void setItemMarketedBy(InvItemCompany itemMarketedBy) {
        this.itemMarketedBy = itemMarketedBy;
    }

    public InvItemDispensingType getItemStockingUomId() {
        return itemStockingUomId;
    }

    public void setItemStockingUomId(InvItemDispensingType itemStockingUomId) {
        this.itemStockingUomId = itemStockingUomId;
    }

    public MstRoute getItemRouteId() {
        return itemRouteId;
    }

    public void setItemRouteId(MstRoute itemRouteId) {
        this.itemRouteId = itemRouteId;
    }

    public String getItemLookLike() {
        return itemLookLike;
    }

    public void setItemLookLike(String itemLookLike) {
        this.itemLookLike = itemLookLike;
    }

    public String getItemSoundLike() {
        return itemSoundLike;
    }

    public void setItemSoundLike(String itemSoundLike) {
        this.itemSoundLike = itemSoundLike;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public String getItemMrp() {
        return itemMrp;
    }

    public void setItemMrp(String itemMrp) {
        this.itemMrp = itemMrp;
    }

    public String getItemVed() {
        return itemVed;
    }

    public void setItemVed(String itemVed) {
        this.itemVed = itemVed;
    }

    public String getItemEconomyQuantity() {
        return itemEconomyQuantity;
    }

    public void setItemEconomyQuantity(String itemEconomyQuantity) {
        this.itemEconomyQuantity = itemEconomyQuantity;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemAbc() {
        return itemAbc;
    }

    public void setItemAbc(String itemAbc) {
        this.itemAbc = itemAbc;
    }

    public InvTax getItemTaxId() {
        return itemTaxId;
    }

    public void setItemTaxId(InvTax itemTaxId) {
        this.itemTaxId = itemTaxId;
    }

    public InvPregnancyClass getItemPcId() {
        return itemPcId;
    }

    public void setItemPcId(InvPregnancyClass itemPcId) {
        this.itemPcId = itemPcId;
    }

    public InvItemCategory getItemIcId() {
        return itemIcId;
    }

    public void setItemIcId(InvItemCategory itemIcId) {
        this.itemIcId = itemIcId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemMinimumQuantity() {
        return itemMinimumQuantity;
    }

    public void setItemMinimumQuantity(String itemMinimumQuantity) {
        this.itemMinimumQuantity = itemMinimumQuantity;
    }

    public Boolean getItemBatchesRequired() {
        return itemBatchesRequired;
    }

    public void setItemBatchesRequired(Boolean itemBatchesRequired) {
        this.itemBatchesRequired = itemBatchesRequired;
    }

    public Boolean getItemLifeSaving() {
        return itemLifeSaving;
    }

    public void setItemLifeSaving(Boolean itemLifeSaving) {
        this.itemLifeSaving = itemLifeSaving;
    }

    public Boolean getItemHighRisk() {
        return itemHighRisk;
    }

    public void setItemHighRisk(Boolean itemHighRisk) {
        this.itemHighRisk = itemHighRisk;
    }

    public Boolean getItemHighCost() {
        return itemHighCost;
    }

    public void setItemHighCost(Boolean itemHighCost) {
        this.itemHighCost = itemHighCost;
    }

    public Boolean getItemH1Drug() {
        return itemH1Drug;
    }

    public void setItemH1Drug(Boolean itemH1Drug) {
        this.itemH1Drug = itemH1Drug;
    }

    public Boolean getIsShowPharmacy() {
        return isShowPharmacy;
    }

    public void setIsShowPharmacy(Boolean isShowPharmacy) {
        this.isShowPharmacy = isShowPharmacy;
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

    public Boolean getItemNarcotics() {
        return itemNarcotics;
    }

    public void setItemNarcotics(Boolean itemNarcotics) {
        this.itemNarcotics = itemNarcotics;
    }

    public InvItemSubCategory getItemIscId() {
        return itemIscId;
    }

    public void setItemIscId(InvItemSubCategory itemIscId) {
        this.itemIscId = itemIscId;
    }

    public InvItemSubGroup getItemIsgId() {
        return itemIsgId;
    }

    public void setItemIsgId(InvItemSubGroup itemIsgId) {
        this.itemIsgId = itemIsgId;
    }

    public InvItemType getItemItId() {
        return itemItId;
    }

    public void setItemItId(InvItemType itemItId) {
        this.itemItId = itemItId;
    }

    public InvItemSubType getItemIstyId() {
        return itemIstyId;
    }

    public void setItemIstyId(InvItemSubType itemIstyId) {
        this.itemIstyId = itemIstyId;
    }
    //    public MstUnit getItemUnitId() {
//        return itemUnitId;
//    }
//
//    public void setItemUnitId(MstUnit itemUnitId) {
//        this.itemUnitId = itemUnitId;
//    }
}
// select * from tinv_opening_balance_item as obi INNER JOIN inv_item as item ON obi.obi_item_id = item.item_id where((obi.obi_item_name like "%1%" OR obi.obi_item_batch_code like "%1%" Or obi_item_code like "%1%" AND item.item_code like "%1%") AND (obi.obi_item_expiry_date > "2020/12/12" AND obi.obi_item_quantity >0 AND item.is_show_pharmacy = false))
