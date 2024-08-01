package com.cellbeans.hspa.mis.misbillingreport;

public class OpdIpdCollectionDetailSearchDTO {
    public String ccId; // CashcounterID
    public String bbsId; // Sponsor ID
    public String fromdate;
    public String todate;
    public boolean todaydate;
    public String[] unitList;
    public String[] mstuserlist;
    // public boolean IPDFlag;
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
    //    public boolean getIPDFlag() {
//        return IPDFlag;
//    }
//
//    public void setIPDFlag(boolean IPDFlag) {
//        this.IPDFlag = IPDFlag;
//    }

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getBbsId() {
        return bbsId;
    }

    public void setBbsId(String bbsId) {
        this.bbsId = bbsId;
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
