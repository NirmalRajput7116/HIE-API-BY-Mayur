package com.cellbeans.hspa.mis.misinventoryitemindent;

import com.cellbeans.hspa.invstore.InvStore;

public class SearchItemIndent {
    private int offset;
    private int limit;
    private String searchFromDate;
    private String searchToDate;
    private String searchIndentNo;
    private InvStore searchToStoreId;
    private InvStore searchFromStoreId;
    private long unitId;
    private boolean print;
    private String dueDate;

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    public String getSearchIndentNo() {
        return searchIndentNo;
    }

    public void setSearchIndentNo(String searchIndentNo) {
        this.searchIndentNo = searchIndentNo;
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

}
