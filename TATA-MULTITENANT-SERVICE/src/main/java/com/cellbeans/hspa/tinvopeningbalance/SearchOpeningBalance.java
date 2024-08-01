package com.cellbeans.hspa.tinvopeningbalance;

import com.cellbeans.hspa.mstunit.MstUnit;

public class SearchOpeningBalance {
    private int offset;
    private int limit;
    private String searchFromDate;
    private String searchToDate;
    private MstUnit searchUnitId;
    private long unitId;

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
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

    public MstUnit getSearchUnitId() {
        return searchUnitId;
    }

    public void setSearchUnitId(MstUnit searchUnitId) {
        this.searchUnitId = searchUnitId;
    }

}
