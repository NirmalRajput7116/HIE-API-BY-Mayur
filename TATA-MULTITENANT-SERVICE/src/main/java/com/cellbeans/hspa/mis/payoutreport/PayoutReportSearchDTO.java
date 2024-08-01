package com.cellbeans.hspa.mis.payoutreport;

public class PayoutReportSearchDTO {

    public String fromdate;
    public String todate;
    public String clclId;
    public String fromccId;
    public String toccId;
    public String fromstaffId;
    public String tostaffId;
    public String userId;
    public boolean todaydate;
    public String[] unitList;
    public int limit;
    public int offset;
    public boolean print;

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

    public String getClclId() {
        return clclId;
    }

    public void setClclId(String clclId) {
        this.clclId = clclId;
    }

    public String getFromccId() {
        return fromccId;
    }

    public void setFromccId(String fromccId) {
        this.fromccId = fromccId;
    }

    public String getToccId() {
        return toccId;
    }

    public void setToccId(String toccId) {
        this.toccId = toccId;
    }

    public String getFromstaffId() {
        return fromstaffId;
    }

    public void setFromstaffId(String fromstaffId) {
        this.fromstaffId = fromstaffId;
    }

    public String getTostaffId() {
        return tostaffId;
    }

    public void setTostaffId(String tostaffId) {
        this.tostaffId = tostaffId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

}
