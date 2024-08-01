package com.cellbeans.hspa.invServiceWiseConsumptionItems;

public class InvServiceWisepackageitemsDto {

    public long iswpiId;
    public long iswpiIswpId;
    public long itemId;
    public double itemQty;
    public String itemName;
    public String itemUOM;
    public long itmStoreId;

    public long getItmStoreId() {
        return itmStoreId;
    }

    public void setItmStoreId(long itmStoreId) {
        this.itmStoreId = itmStoreId;
    }

    public long getIswpiId() {
        return iswpiId;
    }

    public void setIswpiId(long iswpiId) {
        this.iswpiId = iswpiId;
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

    public String getItemUOM() {
        return itemUOM;
    }

    public void setItemUOM(String itemUOM) {
        this.itemUOM = itemUOM;
    }
}
