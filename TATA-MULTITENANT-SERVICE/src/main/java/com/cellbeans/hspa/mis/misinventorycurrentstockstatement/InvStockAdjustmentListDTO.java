package com.cellbeans.hspa.mis.misinventorycurrentstockstatement;

import com.cellbeans.hspa.mstunit.MstUnit;

public class InvStockAdjustmentListDTO {

    public String dateOfAdjust;
    public String unitName;
    public String storeName;
    public String itemName;
    public String itemBatchName;
    public String expDate;
    public String avbCurrentQty;
    public String adjQty;
    public String adjType;
    public String newAvbQty;
    public String stockAdjNo;
    public String remark;
    public String userName;
    public long count;
    public MstUnit objHeaderData;

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }

    public String getDateOfAdjust() {
        return dateOfAdjust;
    }

    public void setDateOfAdjust(String dateOfAdjust) {
        this.dateOfAdjust = dateOfAdjust;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBatchName() {
        return itemBatchName;
    }

    public void setItemBatchName(String itemBatchName) {
        this.itemBatchName = itemBatchName;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getAvbCurrentQty() {
        return avbCurrentQty;
    }

    public void setAvbCurrentQty(String avbCurrentQty) {
        this.avbCurrentQty = avbCurrentQty;
    }

    public String getAdjQty() {
        return adjQty;
    }

    public void setAdjQty(String adjQty) {
        this.adjQty = adjQty;
    }

    public String getAdjType() {
        return adjType;
    }

    public void setAdjType(String adjType) {
        this.adjType = adjType;
    }

    public String getNewAvbQty() {
        return newAvbQty;
    }

    public void setNewAvbQty(String newAvbQty) {
        this.newAvbQty = newAvbQty;
    }

    public String getStockAdjNo() {
        return stockAdjNo;
    }

    public void setStockAdjNo(String stockAdjNo) {
        this.stockAdjNo = stockAdjNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "InvStockAdjustmentListDTO{" +
                "dateOfAdjust='" + dateOfAdjust + '\'' +
                ", unitName='" + unitName + '\'' +
                ", storeName='" + storeName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemBatchName='" + itemBatchName + '\'' +
                ", expDate='" + expDate + '\'' +
                ", avbCurrentQty='" + avbCurrentQty + '\'' +
                ", adjQty='" + adjQty + '\'' +
                ", adjType='" + adjType + '\'' +
                ", newAvbQty='" + newAvbQty + '\'' +
                ", stockAdjNo='" + stockAdjNo + '\'' +
                ", remark='" + remark + '\'' +
                ", userName='" + userName + '\'' +
                ", count=" + count +
                '}';
    }
}
