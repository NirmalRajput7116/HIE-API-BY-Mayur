package com.cellbeans.hspa.mis.misbillingreport;

public class OpdIpdCollectionSummarySearchDTO {
    public String pmId; // Payment-mode list
    public String clclId; // Collection Mode
    public String fromdate;
    public String todate;
    public boolean todaydate;
    public String[] unitList;
    // public boolean IPDFlag;
    public int limit;
    public int offset;
    public boolean print;
    private long currentUnitId;
    private long userId;

    public String getClclId() {
        return clclId;
    }

    public void setClclId(String clclId) {
        this.clclId = clclId;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public boolean getTodaydate() {
        return todaydate;
    }

    public void setTodaydate(boolean todaydate) {
        this.todaydate = todaydate;
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
}
