package com.cellbeans.hspa.invItemKitPackage;

public class InvItemKitPackageItemsDto {

    public long ikpiId;
    public long ikpiIkpId;
    public long ikpiItemId;
    public double ikpiItemQty;
    public String itemName;

    public long getIkpiId() {
        return ikpiId;
    }

    public void setIkpiId(long ikpiId) {
        this.ikpiId = ikpiId;
    }

    public long getIkpiIkpId() {
        return ikpiIkpId;
    }

    public void setIkpiIkpId(long ikpiIkpId) {
        this.ikpiIkpId = ikpiIkpId;
    }

    public long getIkpiItemId() {
        return ikpiItemId;
    }

    public void setIkpiItemId(long ikpiItemId) {
        this.ikpiItemId = ikpiItemId;
    }

    public double getIkpiItemQty() {
        return ikpiItemQty;
    }

    public void setIkpiItemQty(double ikpiItemQty) {
        this.ikpiItemQty = ikpiItemQty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String item_name) {
        this.itemName = item_name;
    }

}
