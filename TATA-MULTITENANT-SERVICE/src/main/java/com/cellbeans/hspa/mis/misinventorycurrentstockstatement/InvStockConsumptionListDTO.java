package com.cellbeans.hspa.mis.misinventorycurrentstockstatement;

import com.cellbeans.hspa.mstunit.MstUnit;

public class InvStockConsumptionListDTO {

    public String scDate;
    public String scNo;
    public String itemName;
    public String scBatchNo;
    public String expDate;
    public String uOM;
    public String scqty;
    public String mrp;
    public String totalAmount;
    public String remark;
    public String userName;
    public String storeName;
    public String unitName;
    public long count;
    public String grandTotal;
    public MstUnit objHeaderData;

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getScDate() {
        return scDate;
    }

    public void setScDate(String scDate) {
        this.scDate = scDate;
    }

    public String getScNo() {
        return scNo;
    }

    public void setScNo(String scNo) {
        this.scNo = scNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getScBatchNo() {
        return scBatchNo;
    }

    public void setScBatchNo(String scBatchNo) {
        this.scBatchNo = scBatchNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getuOM() {
        return uOM;
    }

    public void setuOM(String uOM) {
        this.uOM = uOM;
    }

    public String getScqty() {
        return scqty;
    }

    public void setScqty(String scqty) {
        this.scqty = scqty;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}
