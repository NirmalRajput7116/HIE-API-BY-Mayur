package com.cellbeans.hspa.mis.misinventorycurrentstockstatement;

public class InvCurrentStockSearchDTO {

    public String itemName;
    // public String[] MstUnitList;
    public long storeId;
    public long itemBatchCode;
    public long itemExpUltimatum;
    public String startdate;
    public String enddate;
    public long fromQuantity;
    public long toQuantity;
    public long unitId;
    public String[] unitList;
    public boolean todaydate;
    public int limit;
    public int offset;
    public boolean print;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
//    public String[] getMstUnitList() {     return MstUnitList;  }
//
//    public void setMstUnitList(String[] mstUnitList) {      MstUnitList = mstUnitList;   }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getItemBatchCode() {
        return itemBatchCode;
    }

    public void setItemBatchCode(long itemBatchCode) {
        this.itemBatchCode = itemBatchCode;
    }

    public long getItemExpUltimatum() {
        return itemExpUltimatum;
    }

    public void setItemExpUltimatum(long itemExpUltimatum) {
        this.itemExpUltimatum = itemExpUltimatum;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public long getFromQuantity() {
        return fromQuantity;
    }

    public void setFromQuantity(long fromQuantity) {
        this.fromQuantity = fromQuantity;
    }

    public long getToQuantity() {
        return toQuantity;
    }

    public void setToQuantity(long toQuantity) {
        this.toQuantity = toQuantity;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String[] getUnitList() {
        return unitList;
    }

    public void setUnitList(String[] unitList) {
        this.unitList = unitList;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public boolean getTodaydate() {
        return todaydate;
    }

    public void setTodaydate(boolean todaydate) {
        this.todaydate = todaydate;
    }

}
