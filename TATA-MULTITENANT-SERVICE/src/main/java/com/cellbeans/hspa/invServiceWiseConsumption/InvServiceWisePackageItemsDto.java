package com.cellbeans.hspa.invServiceWiseConsumption;

public class InvServiceWisePackageItemsDto {

    public long iswpiId;
    public long iswpiIswpId;
    public long itemId;
    public long itemStoreId;
    public double itemQty;
    public double preDefineQty;
    public String uOM;
    public String itemName;

    public double getPreDefineQty() {
        return preDefineQty;
    }

    public void setPreDefineQty(double preDefineQty) {
        this.preDefineQty = preDefineQty;
    }

    public String getuOM() {
        return uOM;
    }

    public void setuOM(String uOM) {
        this.uOM = uOM;
    }

    public long getItemStoreId() {
        return itemStoreId;
    }

    public void setItemStoreId(long itemStoreId) {
        this.itemStoreId = itemStoreId;
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

    public long getIswpiId() {
        return iswpiId;
    }

    public void setIswpiId(long iswpiId) {
        this.iswpiId = iswpiId;
    }
}
