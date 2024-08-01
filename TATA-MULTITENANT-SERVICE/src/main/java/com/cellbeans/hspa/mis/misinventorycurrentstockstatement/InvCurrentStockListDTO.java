package com.cellbeans.hspa.mis.misinventorycurrentstockstatement;

public class InvCurrentStockListDTO {

    public String itemName;
    public String unitName;
    public String storeName;
    public String batchCode;
    public String itemExpUltimatum;
    public String availableStock;
    public String minimumQuantity;
    public String expiryDate;
    public String mrp;
    public String purchaseRate;
    public String stockdate;
    public long count;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getAvailableStock() {
        return availableStock;
    }

    public String getItemExpUltimatum() {
        return itemExpUltimatum;
    }

    public void setItemExpUltimatum(String itemExpUltimatum) {
        this.itemExpUltimatum = itemExpUltimatum;
    }

    public void setAvailableStock(String availableStock) {
        this.availableStock = availableStock;
    }

    public String getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(String minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(String purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public String getStockdate() {
        return stockdate;
    }

    public void setStockdate(String stockdate) {
        this.stockdate = stockdate;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "InvCurrentStockListDTO{" +
                "itemName='" + itemName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", storeName='" + storeName + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", itemExpUltimatum='" + itemExpUltimatum + '\'' +
                ", availableStock='" + availableStock + '\'' +
                ", minimumQuantity='" + minimumQuantity + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", mrp='" + mrp + '\'' +
                ", purchaseRate='" + purchaseRate + '\'' +
                ", stockdate='" + stockdate + '\'' +
                ", count=" + count +
                '}';
    }

}
