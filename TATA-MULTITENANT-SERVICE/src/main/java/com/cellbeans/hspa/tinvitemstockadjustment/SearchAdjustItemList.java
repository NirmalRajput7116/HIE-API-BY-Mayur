package com.cellbeans.hspa.tinvitemstockadjustment;

import com.cellbeans.hspa.invgenericname.InvGenericName;
import com.cellbeans.hspa.invitemcategory.InvItemCategory;
import com.cellbeans.hspa.invitemcompany.InvItemCompany;
import com.cellbeans.hspa.invitemdispensingtype.InvItemDispensingType;
import com.cellbeans.hspa.invitemgroup.InvItemGroup;
import com.cellbeans.hspa.invitemstoragetype.InvItemStorageType;
import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.mstunit.MstUnit;

public class SearchAdjustItemList {
    private MstUnit itemUnitId;
    private String itemName;
    private String itemQty;
    private String itemId;
    private String itemBatchCode;
    private String itemBrandName;
    private String itemCode;
    private String itemExpiryDate;
    private String itemExpiryFromDate;
    private String itemExpiryToDate;
    private InvStore itemStoreId;
    private InvItemGroup itemGroupId;
    private InvItemCategory itemCategoryId;
    private InvGenericName itemGenericId;
    private InvItemStorageType itemStorageId;
    private InvItemDispensingType itemDespensingId;
    private InvItemCompany itemCompanyId;
    private int offset;
    private int limit;
    private long unitId;

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public MstUnit getItemUnitId() {
        return itemUnitId;
    }

    public void setItemUnitId(MstUnit itemUnitId) {
        this.itemUnitId = itemUnitId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBatchCode() {
        return itemBatchCode;
    }

    public void setItemBatchCode(String itemBatchCode) {
        this.itemBatchCode = itemBatchCode;
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

    public String getItemExpiryDate() {
        return itemExpiryDate;
    }

    public void setItemExpiryDate(String itemExpiryDate) {
        this.itemExpiryDate = itemExpiryDate;
    }

    public InvStore getItemStoreId() {
        return itemStoreId;
    }

    public void setItemStoreId(InvStore itemStoreId) {
        this.itemStoreId = itemStoreId;
    }

    public InvItemGroup getItemGroupId() {
        return itemGroupId;
    }

    public void setItemGroupId(InvItemGroup itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    public InvItemCategory getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(InvItemCategory itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public InvGenericName getItemGenericId() {
        return itemGenericId;
    }

    public void setItemGenericId(InvGenericName itemGenericId) {
        this.itemGenericId = itemGenericId;
    }

    public InvItemStorageType getItemStorageId() {
        return itemStorageId;
    }

    public void setItemStorageId(InvItemStorageType itemStorageId) {
        this.itemStorageId = itemStorageId;
    }

    public InvItemDispensingType getItemDespensingId() {
        return itemDespensingId;
    }

    public void setItemDespensingId(InvItemDispensingType itemDespensingId) {
        this.itemDespensingId = itemDespensingId;
    }

    public InvItemCompany getItemCompanyId() {
        return itemCompanyId;
    }

    public void setItemCompanyId(InvItemCompany itemCompanyId) {
        this.itemCompanyId = itemCompanyId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getItemExpiryFromDate() {
        return itemExpiryFromDate;
    }

    public void setItemExpiryFromDate(String itemExpiryFromDate) {
        this.itemExpiryFromDate = itemExpiryFromDate;
    }

    public String getItemExpiryToDate() {
        return itemExpiryToDate;
    }

    public void setItemExpiryToDate(String itemExpiryToDate) {
        this.itemExpiryToDate = itemExpiryToDate;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}