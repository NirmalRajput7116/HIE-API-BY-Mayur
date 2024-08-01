package com.cellbeans.hspa.mis.misinventoryexpiryreport;

public class SearchMisinventoryExpiry {

    private int offset;
    private int limit;
    private String searchFromExpdate;
    private String searchToExpdate;
    private long storeId;
    private long unitId;
    private long currentUnitId;
    private long userId;
    private int showType;
    private boolean print;

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

    public String getSearchFromExpdate() {
        return searchFromExpdate;
    }

    public void setSearchFromExpdate(String searchFromExpdate) {
        this.searchFromExpdate = searchFromExpdate;
    }

    public String getSearchToExpdate() {
        return searchToExpdate;
    }

    public void setSearchToExpdate(String searchToExpdate) {
        this.searchToExpdate = searchToExpdate;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getCurrentUnitId() {
        return currentUnitId;
    }

    public void setCurrentUnitId(long currentUnitId) {
        this.currentUnitId = currentUnitId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

}
