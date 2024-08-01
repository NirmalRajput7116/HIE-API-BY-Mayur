package com.cellbeans.hspa.mis.misinventorycurrentstockstatement;

import com.cellbeans.hspa.invstore.InvStore;

public class SearchInventoryCurrentStock {

    private String itemName;
    private String itemBatchCode;
    //private String itemExpiryDate;
    private String itemExpiryFromDate;
    private String itemExpiryToDate;
    private InvStore itemStoreId;
    private String startdate;
    private String enddate;
    private int offset;
    private int limit;
    private long unitId;
    private boolean print;
    private int itemExpUltimatum;

    private Integer fromQuantity;
    private Integer toQuantity;

    public Integer getFromQuantity() {
        return fromQuantity;
    }

    public void setFromQuantity(Integer fromQuantity) {
        this.fromQuantity = fromQuantity;
    }

    public Integer getToQuantity() {
        return toQuantity;
    }

    public void setToQuantity(Integer toQuantity) {
        this.toQuantity = toQuantity;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBatchCode() {
        return itemBatchCode;
    }

    public void setItemBatchCode(String itemBatchCode) {
        this.itemBatchCode = itemBatchCode;
    }
//    public String getItemExpiryDate() {
//        return itemExpiryDate;
//    }
//
//    public void setItemExpiryDate(String itemExpiryDate) {
//        this.itemExpiryDate = itemExpiryDate;
//    }

    public InvStore getItemStoreId() {
        return itemStoreId;
    }

    public void setItemStoreId(InvStore itemStoreId) {
        this.itemStoreId = itemStoreId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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

    public int getItemExpUltimatum() {
        return itemExpUltimatum;
    }

    public void setItemExpUltimatum(int itemExpUltimatum) {
        this.itemExpUltimatum = itemExpUltimatum;
    }
}