package com.cellbeans.hspa.mis.misinventoryexpiryreport;

public class MisItemMovementSearchDTO {

    public String icId;
    public String FromstoreId;
    public String TostoreId;
    public String itemName;
    public String[] unitList;
    public String fromdate;
    public String todate;
    public boolean todaydate;
    public int limit;
    public int offset;
    public boolean print;

    public long UnitId;
    public long UserId;

    public long getUnitId() {
        return UnitId;
    }

    public void setUnitId(long unitId) {
        UnitId = unitId;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getIcId() {
        return icId;
    }

    public void setIcId(String icId) {
        this.icId = icId;
    }

    public String getFromstoreId() {
        return FromstoreId;
    }

    public void setFromstoreId(String fromstoreId) {
        FromstoreId = fromstoreId;
    }

    public String getTostoreId() {
        return TostoreId;
    }

    public void setTostoreId(String tostoreId) {
        TostoreId = tostoreId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String[] getUnitList() {
        return unitList;
    }

    public void setUnitList(String[] unitList) {
        this.unitList = unitList;
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
