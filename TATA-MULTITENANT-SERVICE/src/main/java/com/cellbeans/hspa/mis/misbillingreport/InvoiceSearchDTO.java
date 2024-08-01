package com.cellbeans.hspa.mis.misbillingreport;

public class InvoiceSearchDTO {
    public String mrNo;
    public String user_firstname;
    public String user_lastname;
    public String ccId; // CashcounterID
    public String clclId; // Collection mode
    public String fromdate;
    public String todate;
    public boolean todaydate;
    public String[] unitList;
    public String[] mstuserlist;
    public int limit;
    public int offset;
    public boolean print;
    public long unitId;
    public long userId;

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getClclId() {
        return clclId;
    }

    public void setClclId(String clclId) {
        this.clclId = clclId;
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

    public String[] getMstuserlist() {
        return mstuserlist;
    }

    public void setMstuserlist(String[] mstuserlist) {
        this.mstuserlist = mstuserlist;
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

    public boolean getPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }
}


