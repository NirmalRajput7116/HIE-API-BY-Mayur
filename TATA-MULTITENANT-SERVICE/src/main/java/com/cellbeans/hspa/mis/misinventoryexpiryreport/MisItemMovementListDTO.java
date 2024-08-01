package com.cellbeans.hspa.mis.misinventoryexpiryreport;

public class MisItemMovementListDTO {

    public String UnitName;
    public String itemDate;
    public String TransactionType;
    public String ItemName;
    public String ItemCategory;
    public String ItemFromStore;
    public String ItemToStore;
    public String IssuedReturnBy;
    public String RecievedBy;
    public String Qty;
    public String RefferIndentNo;
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

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemCategory() {
        return ItemCategory;
    }

    public void setItemCategory(String itemCategory) {
        ItemCategory = itemCategory;
    }

    public String getItemFromStore() {
        return ItemFromStore;
    }

    public void setItemFromStore(String itemFromStore) {
        ItemFromStore = itemFromStore;
    }

    public String getItemToStore() {
        return ItemToStore;
    }

    public void setItemToStore(String itemToStore) {
        ItemToStore = itemToStore;
    }

    public String getIssuedReturnBy() {
        return IssuedReturnBy;
    }

    public void setIssuedReturnBy(String issuedReturnBy) {
        IssuedReturnBy = issuedReturnBy;
    }

    public String getRecievedBy() {
        return RecievedBy;
    }

    public void setRecievedBy(String recievedBy) {
        RecievedBy = recievedBy;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getRefferIndentNo() {
        return RefferIndentNo;
    }

    public void setRefferIndentNo(String refferIndentNo) {
        RefferIndentNo = refferIndentNo;
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
