package com.cellbeans.hspa.invStoreConsumption;

public class InvStoreConsumptionDto {

    public String toDate;
    public String fromDate;
    public String storeId;
    public String ScItems;
    public String scItemName;
    public double scQty;
    public String scRemark;
    public boolean todaydate;
    public long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getScItemName() {
        return scItemName;
    }

    public void setScItemName(String scItemName) {
        this.scItemName = scItemName;
    }

    public double getScQty() {
        return scQty;
    }

    public void setScQty(double scQty) {
        this.scQty = scQty;
    }

    public String getScRemark() {
        return scRemark;
    }

    public void setScRemark(String scRemark) {
        this.scRemark = scRemark;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getScItems() {
        return ScItems;
    }

    public void setScItems(String scItems) {
        ScItems = scItems;
    }

    public boolean getTodaydate() {
        return todaydate;
    }

    public void setTodaydate(boolean todaydate) {
        this.todaydate = todaydate;
    }

}
