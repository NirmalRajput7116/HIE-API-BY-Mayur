package com.cellbeans.hspa.tinvreturnissue;

import com.cellbeans.hspa.invstore.InvStore;

public class SearchReturnIssue {

    private int offset;
    private int limit;
    private String searchFromDate;
    private String searchToDate;
    private String searchReturnNo;
    private InvStore searchToStoreId;
    private InvStore searchFromStoreId;
    private long unitId;
    private boolean today;

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

    public String getSearchFromDate() {
        return searchFromDate;
    }

    public void setSearchFromDate(String searchFromDate) {
        this.searchFromDate = searchFromDate;
    }

    public String getSearchToDate() {
        return searchToDate;
    }

    public void setSearchToDate(String searchToDate) {
        this.searchToDate = searchToDate;
    }

    public String getSearchReturnNo() {
        return searchReturnNo;
    }

    public void setSearchReturnNo(String searchReturnNo) {
        this.searchReturnNo = searchReturnNo;
    }

    public InvStore getSearchToStoreId() {
        return searchToStoreId;
    }

    public void setSearchToStoreId(InvStore searchToStoreId) {
        this.searchToStoreId = searchToStoreId;
    }

    public InvStore getSearchFromStoreId() {
        return searchFromStoreId;
    }

    public void setSearchFromStoreId(InvStore searchFromStoreId) {
        this.searchFromStoreId = searchFromStoreId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public boolean isToday() {
        return today;
    }

    public void setToday(boolean today) {
        this.today = today;
    }

}
