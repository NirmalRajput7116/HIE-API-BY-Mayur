package com.cellbeans.hspa.mis.misinventorycurrentstockstatement;

public class InvCurrenSstockitemValuationListDTO {

    public String itemName;
    public String unitName;
    public String batch;
    public String qty;
    public String unitPurchaceCost;
    public String taxPerUnit;
    public String taxAmountPerUnit;
    public String purchaseAmountPerUnit;
    public String purchaseAmount;
    public String taxAmount;
    public String taxAmountTt;
    public String totalPurchaseAmountTax;
    public String unitMrp;
    public String totalMrp;
    public long count;

    public String getTaxAmountTt() {
        return taxAmountTt;
    }

    public void setTaxAmountTt(String taxAmountTt) {
        this.taxAmountTt = taxAmountTt;
    }

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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnitPurchaceCost() {
        return unitPurchaceCost;
    }

    public void setUnitPurchaceCost(String unitPurchaceCost) {
        this.unitPurchaceCost = unitPurchaceCost;
    }

    public String getTaxPerUnit() {
        return taxPerUnit;
    }

    public void setTaxPerUnit(String taxPerUnit) {
        this.taxPerUnit = taxPerUnit;
    }

    public String getTaxAmountPerUnit() {
        return taxAmountPerUnit;
    }

    public void setTaxAmountPerUnit(String taxAmountPerUnit) {
        this.taxAmountPerUnit = taxAmountPerUnit;
    }

    public String getPurchaseAmountPerUnit() {
        return purchaseAmountPerUnit;
    }

    public void setPurchaseAmountPerUnit(String purchaseAmountPerUnit) {
        this.purchaseAmountPerUnit = purchaseAmountPerUnit;
    }

    public String getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(String purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTotalPurchaseAmountTax() {
        return totalPurchaseAmountTax;
    }

    public void setTotalPurchaseAmountTax(String totalPurchaseAmountTax) {
        this.totalPurchaseAmountTax = totalPurchaseAmountTax;
    }

    public String getUnitMrp() {
        return unitMrp;
    }

    public void setUnitMrp(String unitMrp) {
        this.unitMrp = unitMrp;
    }

    public String getTotalMrp() {
        return totalMrp;
    }

    public void setTotalMrp(String totalMrp) {
        this.totalMrp = totalMrp;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
