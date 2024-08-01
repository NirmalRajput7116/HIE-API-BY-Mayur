package com.cellbeans.hspa.mis.misinventoryexpiryreport;

public class MisitemwisePurchaseListDTO {
    public String UnitName;
    public String itemDate;
    public String ItemName;
    public String PoNo;
    public String PoDate;
    public String PoQty;
    public String ItemRate;
    public String MRP;
    public String BatchNo;
    public String ExpiryDate;
    public String SupplierName;
    public long unitId;
    public long count;

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getPoNo() {
        return PoNo;
    }

    public void setPoNo(String poNo) {
        PoNo = poNo;
    }

    public String getPoDate() {
        return PoDate;
    }

    public void setPoDate(String poDate) {
        PoDate = poDate;
    }

    public String getPoQty() {
        return PoQty;
    }

    public void setPoQty(String poQty) {
        PoQty = poQty;
    }

    public String getItemRate() {
        return ItemRate;
    }

    public void setItemRate(String itemRate) {
        ItemRate = itemRate;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}
